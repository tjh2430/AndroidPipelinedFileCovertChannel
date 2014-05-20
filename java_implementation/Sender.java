import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A channel sender.
 *
 * @author    Timothy Heard
 */
public class Sender
{
    private List<Channel> pipes;

    public Sender(List<Channel> pipes)
	throws IllegalArgumentException
    {
	if(pipes == null || pipes.size() == 0)
	{
	    throw new IllegalArgumentException("Unable to create Sender: at least one channel pipe required");
	}

	this.pipes = pipes;
    }

    public void sendMessage(String msg, int numPipes)
	throws IllegalArgumentException
    {
	if(numPipes <= 0 || numPipes > pipes.size())
	{
	    throw new IllegalArgumentException("In Sender.sendMessage(): numPipes value " + numPipes + " is invalid");
	}

	if(msg.length() < numPipes)
	{
	    ChannelUtils.output("Length of message \"" + msg + "\" is less than the number of pipes requested [" + numPipes + "]; defaulting to using only one pipe");
	    Channel messageChannel = pipes.get(0);

	    try
	    {
		messageChannel.openChannel(); // Make sure that the channel is open
	    }
	    catch(IOException e)
	    {
		ChannelUtils.output("In Sender.sendMessage(): IOException occurred while attempting to open channel");
		return;
	    }

	    messageChannel.sendMessage(msg);
	}
	else
	{
	    String[] msgs = splitMessage(msg, numPipes);
	    for(int i = 0; i < msgs.length; i++)
	    {
		// TODO: Add a message queue for acknowledging when a message had been sent
		MessageSenderThread sender = new MessageSenderThread(pipes.get(i), msgs[i]);
		sender.run();

		// TODO: Remove
		System.out.println("HERE");
	    }
	}
    }

    private class MessageSenderThread extends Thread
    {
	private Channel messageChannel;
	private String msg;

	public MessageSenderThread(Channel messageChannel, String msg)
	{
	    this.messageChannel = messageChannel;
	    this.msg = msg;
	}
	
	@Override
	public void run()
	{
	    try
	    {
		messageChannel.openChannel(); // Make sure that the channel is open
		messageChannel.sendMessage(msg);
		messageChannel.closeChannel(); // Tells the receiver that the transmission is complete 
	    }
	    catch(IOException e)
	    {
		ChannelUtils.output("In MessageSenderThread.run(): IOException occurred while attempting to use channel");
		return;
	    }

	    // TODO: Remove
	    ChannelUtils.output("Message block \"" + msg + "\" sent");
	}
    }

    public static void main(String[] args)
    {
	// TODO: Refactor to allow a REPL message sending loop

	if(args.length < 2 || args.length > 3)
	{
	    usage(); // Print usage message and exit    
	}

	String msg = null;

	try
	{
	    msg = readFile(args[0]);
	}
	catch(IOException e)
	{
	    ChannelUtils.output("In Sender.main(): IOException occurred while attemptin to read input file \"" + args[0] + "\"");
	}

	if(msg.length() <= 0)
	{
	    ChannelUtils.output("Message cannot be empty");
	    System.exit(0);
	}

	int numPipes = Integer.valueOf(args[1]);
		
	if(!ChannelUtils.isValidNumberOfPipes(numPipes))
	{
	    ChannelUtils.output("numPipes must be greater than zero and less than or equal to " + ChannelUtils.getMaxNumPipes());
	    System.exit(0);
	}

	List<Channel> pipes = null;

	try
	{
	    if(args.length == 3)
	    {
		long sleepInterval = Long.valueOf(args[2]);
		pipes = ChannelUtils.getPipes(sleepInterval, Channel.CHANNEL_MODE.SENDER);
	    }
	    else
	    {
		pipes = ChannelUtils.getPipes(Channel.CHANNEL_MODE.SENDER);
	    }
	}
	catch(IOException e)
	{
	    ChannelUtils.output("In Sender.main(): IOException occurred while retrieving channel pipes");
	}

	Sender messageSender = new Sender(pipes);
	messageSender.sendMessage(msg, numPipes);
    }

    private static void usage()
    {
	System.out.println("java Sender <message_file> <num_pipes> [wait_interval]");
	System.exit(0);
    }

    /**
     * Function for read the contents of a given file to a string.
     * Taken from http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
     *
     * Note: Only works in Java 7 and later.
     */
    private static String readFile(String path) 
	throws IOException 
    {
	byte[] encoded = Files.readAllBytes(Paths.get(path));
	return new String(encoded);
    }
    
    /**
     * Splits the given message string into {@param numBlocks} 
     * individual strings. Assumes that the message is at least
     * {@param numBlocks} characters long.
     */
    private static String[] splitMessage(String msg, int numBlocks)
    {
	String[] messages = new String[numBlocks];

	int startIndex = 0;
	int blockSize = msg.length() / numBlocks;
	int endIndex = blockSize;
	for(int i = 0; i < numBlocks; i++)
	{
	    // TODO: Refactor this function to handle this better
	    //
	    // Handles the last block differently in case the last message
	    // block is not a "full" block (i.e not the same size as the
	    // other message blocks); as a result the last block sent could
	    // be up to blockSize - 1 characters longer than the other blocks
	    if(i == numBlocks - 1)
	    {
		messages[i] = msg.substring(startIndex, msg.length());
	    }
	    else
	    {
		messages[i] = msg.substring(startIndex, endIndex);
	    }

	    startIndex = endIndex;
	    endIndex += blockSize;
	}

	return messages;
    }
}