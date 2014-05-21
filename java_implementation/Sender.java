import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.List;

/**
 * A channel sender.
 *
 * @author    Timothy Heard
 */
public class Sender
{
    private static final int MIN_CMD_ARGS = 2;
    private static final int MAX_CMD_ARGS = 6;
    private static final String NUM_BYTES_ARG_FLAG = "-b";
    private static final int NUM_BYTES_INDEX = 0;
    private static final String WAIT_INTERVAL_ARG_FLAG = "-w";
    private static final int WAIT_INTERVAL_INDEX = 1;

    private LinkedBlockingQueue<Integer> notificationQueue;
    private List<Channel> pipes;

    public Sender(List<Channel> pipes)
	throws IllegalArgumentException
    {
	if(pipes == null || pipes.size() == 0)
	{
	    throw new IllegalArgumentException("Unable to create Sender: at least one channel pipe required");
	}

	this.pipes = pipes;
	notificationQueue = new LinkedBlockingQueue<Integer>();
    }

    public void sendMessage(String msg, int numPipes, long sleepInterval)
	throws IllegalArgumentException
    {
	// Used for profiling how long it takes to send the message
	long startTime, endTime;
	int numBytes = msg.getBytes().length;

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
		startTime = System.nanoTime();
		messageChannel.sendMessage(msg);
		endTime = System.nanoTime();
	    }
	    catch(IOException e)
	    {
		ChannelUtils.output("In Sender.sendMessage(): IOException occurred while attempting to send message on single channel");
		return;
	    }
	    catch(InterruptedException e)
	    {
		ChannelUtils.output("In Sender.sendMessage(): InterruptedException occurred while attempting to send message on single channel");
		return;
	    }	    
	}
	else
	{
	    startTime = System.nanoTime();

	    String[] msgs = splitMessage(msg, numPipes);
	    for(int i = 0; i < msgs.length; i++)
	    {
		// TODO: Add a message queue for acknowledging when a message had been sent
		MessageSenderThread sender = new MessageSenderThread(pipes.get(i), msgs[i], notificationQueue, i);
		sender.start();
	    }

	    boolean[] msgStatus = new boolean[numPipes];
	    for(int i = 0; i < msgStatus.length; i++)
	    {
		msgStatus[i] = false; // No message pieces have been sent as far as the sender knows at this point
	    }
	    
	    boolean transmissionComplete = false;
	
	    while(!transmissionComplete)
	    {
		int sequenceNumber;
		try
		{
		    sequenceNumber = notificationQueue.take().intValue();
		}
		catch(InterruptedException e)
		{
		    ChannelUtils.output("In Sender.sendMessage(): InterruptedException occurred while attempting to retrieve a sequence number from the notification queue");
		    continue;
		}
		
		// TODO: Remove
		//System.out.println("Message block " + sequenceNumber + " sent");
		
		msgStatus[sequenceNumber] = true;
		
		transmissionComplete = true;	    
		for(int i = 0; i < msgStatus.length; i++)
		{
		    if(!msgStatus[i])
		    {
			// There is at least one part of the message which has not finished sending, so
			// the transmission is not complete.
			transmissionComplete = false;
		    }
		}
	    }

	    endTime = System.nanoTime();
	}
	
	long totalNanos = endTime - startTime;
	long totalMillis = totalNanos / 1000000;

	BigDecimal bigNumBytes = new BigDecimal(numBytes);
	BigDecimal bigTotalNanos = new BigDecimal(totalNanos);
	BigDecimal bigTotalSeconds = bigTotalNanos.divide(new BigDecimal(1000000000), BigDecimal.ROUND_HALF_UP);

	// Transmission rate in bytes per second
	BigDecimal transRate = bigNumBytes.divide(bigTotalSeconds, BigDecimal.ROUND_HALF_UP);
	
	//
	// Transmission report
	//
	ChannelUtils.output("\n--------------------------------------------------------------------------------");
	ChannelUtils.output("Message transmission complete.\n");
	ChannelUtils.output("Number of pipes used: " + numPipes);
	ChannelUtils.output("Wait interval: " + sleepInterval + " milliseconds");
	ChannelUtils.output("Sent " + numBytes + " bytes in " + totalNanos + " nanoseconds (" + totalMillis + ") milliseconds\n");
	ChannelUtils.output("Transmission rate: " + transRate.toPlainString() + " bytes per second\n");
	ChannelUtils.output("\n--------------------------------------------------------------------------------");
    }

    private class MessageSenderThread extends Thread
    {
	private Channel messageChannel;
	private String msg;
	private LinkedBlockingQueue<Integer> notificationQueue;
	private int sequenceNumber;

	public MessageSenderThread(Channel messageChannel, String msg, LinkedBlockingQueue<Integer> notificationQueue, int sequenceNumber)
	{
	    this.messageChannel = messageChannel;
	    this.msg = msg;
	    this.notificationQueue = notificationQueue;
	    this.sequenceNumber = sequenceNumber;
	}
	
	@Override
	public void run()
	{
	    try
	    {
		// TODO: Uncomment or remove
		//messageChannel.openChannel(); // Make sure that the channel is open
		messageChannel.sendMessage(msg);

		// TODO: Remove
		//System.out.println("Message \"" + msg + "\" sent! Closing the channel");

		messageChannel.closeChannel(); // Tells the receiver that the transmission is complete 

		// Acknowledges that this part of the message has been sent
		notificationQueue.put(sequenceNumber);
	    }
	    catch(IOException e)
	    {
		ChannelUtils.output("In MessageSenderThread.run(): IOException occurred while attempting to use channel");
	    }
	    catch(InterruptedException e)
	    {
		ChannelUtils.output("In Sender.run(): InterruptedException occurred while attempting to send message");
	    }
	}

	public int getSequenceNumber()
	{
	    return sequenceNumber;
	}
    }

    public static void main(String[] args)
    {
	// TODO: Refactor to allow a REPL message sending loop

	if(args.length < MIN_CMD_ARGS || args.length > MAX_CMD_ARGS)
	{
	    usage(); // Print usage message and exit    
	}

	int numPipes = Integer.valueOf(args[1]);
		
	if(!ChannelUtils.isValidNumberOfPipes(numPipes))
	{
	    ChannelUtils.output("numPipes must be greater than zero and less than or equal to " + ChannelUtils.getMaxNumPipes());
	    System.exit(0);
	}

	int numBytes;
	long sleepInterval;

	int[] optArgs = parseOptionalArguments(args);
	numBytes = optArgs[NUM_BYTES_INDEX];
	sleepInterval = (long) optArgs[WAIT_INTERVAL_INDEX];
	
	if(sleepInterval <= 0)
	{
	    sleepInterval = ChannelUtils.DEFAULT_SLEEP_INTERVAL;
	}

	List<Channel> pipes = null;
	try
	{
	    /* TODO: Uncomment or remove
	    if(sleepInterval > 0)
	    {		
		pipes = ChannelUtils.getPipes(sleepInterval, Channel.CHANNEL_MODE.SENDER);
	    }
	    else
	    {
		pipes = ChannelUtils.getPipes(Channel.CHANNEL_MODE.SENDER);
	    }
	    */

	    pipes = ChannelUtils.getPipes(sleepInterval, Channel.CHANNEL_MODE.SENDER);
	}
	catch(IOException e)
	{
	    ChannelUtils.output("In Sender.main(): IOException occurred while retrieving channel pipes");
	}

	String msg = null;

	try
	{
	    if(numBytes > 0)
	    {		
		msg = readFile(args[0], numBytes);
	    }
	    else
	    {
		msg = readFile(args[0]);
	    }
	}
	catch(FileNotFoundException e)
	{
	    ChannelUtils.output("In Sender.main(): FileNotFoundException occurred while attemptin to read input file \"" + args[0] + "\"");
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

	Sender messageSender = new Sender(pipes);
	messageSender.sendMessage(msg, numPipes, sleepInterval);
    }

    private static void usage()
    {
	System.out.println("java Sender <message_file> <num_pipes> [-b num_bytes] [-w wait_interval]");
	System.exit(0);
    }

    /**
     * Parses out the optional command line arguments (if there are any) and returns them
     * in an array of integers. Any values not provided will default to -1.
     * 
     * index 0: Number of bytes to send
     * index 1: wait/sleep interval (how long to wait between rechecking a file)
     */
    private static int[] parseOptionalArguments(String[] args)
    {
	// 0: number of bytes to send
	// 1: wait/sleep interval
	int[] optArgs = {-1, -1};
	
	for(int i = MIN_CMD_ARGS; i < args.length; i += 2)
	{
	    if(args[i].equals(NUM_BYTES_ARG_FLAG))
	    {
		int numBytes = Integer.valueOf(args[i + 1]);

		if(numBytes <= 0)
		{
		    System.out.println("Error: num_bytes must be greater than zero");
		    System.exit(0);
		}

		optArgs[NUM_BYTES_INDEX] = numBytes;
	    }
	    else if(args[i].equals(WAIT_INTERVAL_ARG_FLAG))
	    {
		int waitInterval = Integer.valueOf(args[i + 1]);

		if(waitInterval <= 0)
		{
		    System.out.println("Error: wait_interval must be greater than zero");
		    System.exit(0);
		}

		optArgs[WAIT_INTERVAL_INDEX] = Integer.valueOf(args[i + 1]);
	    }
	    else
	    {
		System.out.println("Error: invalid command line argument flag \'" + args[i] + "\'");
		System.exit(0);
	    }
	}

	return optArgs;
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
     * Function for reading the specified number of bytes from the given file.
     */
    private static String readFile(String path, int numBytes)
	throws FileNotFoundException, IOException
    {	
	byte[] msgBytes = new byte[numBytes];
	FileInputStream msgStream = new FileInputStream(path);

	// TODO: Handle the cases where the number of bytes read is less than the size of the buffer
	// and/or the end of the input file is reached (will return -1 in that case)
	int bytesRead = msgStream.read(msgBytes);

	return new String(msgBytes);
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
