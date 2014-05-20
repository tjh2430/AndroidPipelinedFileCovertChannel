import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

/**
 * Receives a message across one or more message channel pipes.
 *
 * @author    Timothy Heard
 */
public class Receiver extends Thread
{
    private LinkedBlockingQueue<MessageBlock> messageQueue;
    private List<Channel> pipes;
    private MessageBuilder msgBuilder;

    public Receiver(List<Channel> pipes)
	throws IllegalArgumentException
    {
	if(pipes == null || pipes.size() == 0)
	{
	    throw new IllegalArgumentException("Unable to create Receiver: at least one channel pipe required");
	}

	this.pipes = pipes;
	messageQueue = new LinkedBlockingQueue<MessageBlock>();
	msgBuilder = new MessageBuilder(pipes.size());
    }

    public void run()
    {
	for(int i = 0; i < pipes.size(); i++)
	{
	    MessageReceiverThread receiver = new MessageReceiverThread(pipes.get(i), messageQueue, i);
	    receiver.run();
	}

	while(true)
	{
	    // TODO: Read from the message queue, construct complete
	    // messages, and display once complete (can also print each
	    // block so long as they are arriving in sequence)
	    
	    MessageBlock msgBlock = null;
	    try
	    {
		msgBlock = messageQueue.take(); // Blocks until the queue is non-empty
	    }
	    catch(InterruptedException e)
	    {
		ChannelUtils.output("InterruptedException in ReceiverThread.run(): Received message block \"" + msgBlock + "\" but could not add it to the message builder");
	    }

	    // TODO: Remove
	    ChannelUtils.output("Received message block \"" + msgBlock + "\"");

	    msgBuilder.addMessageBlock(msgBlock);

	    // TODO: Print message blocks in sequence if first message block(s)
	    // is/are available
	}
    }

    private class MessageReceiverThread extends Thread
    {
	private LinkedBlockingQueue<MessageBlock> messageQueue;
	private Channel messageChannel;
	private int sequenceNumber;

	public MessageReceiverThread(Channel messageChannel, LinkedBlockingQueue<MessageBlock> messageQueue, int sequenceNumber)
	{
	    this.messageChannel = messageChannel;
	    this.messageQueue = messageQueue;
	    this.sequenceNumber = sequenceNumber;
	}
	
	@Override
	public void run()	    
	{
	    String msgString = messageChannel.receiveMessage();

	    try
	    {
		messageQueue.put(new MessageBlock(sequenceNumber, msgString));
	    }
	    catch(InterruptedException e)
	    {
		ChannelUtils.output("InterruptedException in MessageReceiverThread.run(): Received message block \"" + msgString + "\" but could not add it to the message queue");
	    }
	}
    }

    private class MessageBlock
    {
	private int sequenceNumber;
	private String msgString;

	public MessageBlock(int sequenceNumber, String msgString)
	{
	    this.sequenceNumber = sequenceNumber;
	    this.msgString = msgString;
	}

	public int getSequenceNumber()
	{
	    return sequenceNumber;
	}

	public String getMessage()
	{
	    return msgString;
	}
    }

    private class MessageBuilder
    {
	private Map<Integer, List<String>> msgBlocks;
	private int numPipes;

	public MessageBuilder(int numPipes)
	{
	    this.numPipes = numPipes;
	    msgBlocks = new HashMap<Integer, List<String>>();
	}

	public void addMessageBlock(MessageBlock msgBlock)
	    throws IllegalStateException
	{
	    String msg = msgBlock.getMessage();	
	    Integer blockListKey = new Integer(msgBlock.getSequenceNumber());

	    if(blockListKey < 0 || blockListKey > numPipes)
	    {
		throw new IllegalStateException("Error in Receiver.MessageBuider: sequence numbers must be greater than or equal to zero and less than the number of pipes; current number of pipes for this builder is " + numPipes);
	    }

	    List<String> blockList = msgBlocks.get(blockListKey);
	    
	    if(blockList == null)
	    {
		if(msgBlocks.size() >= numPipes) // Should really only need to do ==, but I'm paranoid
		{
		    throw new IllegalStateException("Error in Receiver.MessageBuider: cannot have have more sequence numbers than pipes; current number of pipes for this builder is " + numPipes);
		}

		blockList = new LinkedList<String>();
	    }

	    blockList.add(msg);
	    msgBlocks.put(blockListKey, blockList);
	}

	/**
	 * Gets the next message which has been received. Note
	 * that this operation changes the state of the MessageBuilder
	 * object as it removes a message block from each pipleline 
	 * message queue (if it exists). This method also assumes
	 * that an empty message queue in the sequence indicates the
	 * end of that message.
	 */
	public String getNextMessage()
	{
	    StringBuilder msgString = new StringBuilder();

	    for(Integer key: msgBlocks.keySet())
	    {
		List<String> blockList = msgBlocks.get(key);
		
		if(blockList != null && blockList.size() > 0)
		{
		    String msgBlock = blockList.get(0);
		    blockList.remove(0);
		    msgString.append(msgBlock);
		}
		else
		{
		    // Reached the end of the current message
		    break;
		}
	    }

	    return msgString.toString();
	}

	public boolean hasMessageBlock(int sequenceNumber)
	{
	    if(sequenceNumber < 0 || sequenceNumber >= msgBlocks.size())
	    {
		throw new IllegalArgumentException("Error in Receiver.MessageBuilder.hasMessageBlock: invalid sequence number " + sequenceNumber + "; sequence number must be greater than or equal to zero and less than the number of pipes [" + numPipes + "]");
	    }

	    List<String> blockStrings = msgBlocks.get(sequenceNumber);	    
	    return (blockStrings != null && blockStrings.size() > 0);
	}

	/**
	 * Retrieves the next message block for the message pipe with the
	 * given sequence number. Will return null if no such message block
	 * exists. Note that this method removes the message block from
	 * the queue for that channel.
	 */
	public String getNextMessageBlock(int sequenceNumber)
	{
	    if(sequenceNumber < 0 || sequenceNumber >= msgBlocks.size())
	    {
		throw new IllegalArgumentException("Error in Receiver.MessageBuilder.getNextMessageBlock: invalid sequence number " + sequenceNumber + "; sequence number must be greater than or equal to zero and less than the number of pipes [" + numPipes + "]");
	    }

	    List<String> blockStrings = msgBlocks.get(sequenceNumber);
	    
	    if(blockStrings == null || blockStrings.size() == 0)
	    {
		return null;
	    }

	    String msgBlock = blockStrings.get(0);
	    blockStrings.remove(0);
	    return msgBlock;
	}

	/**
	 * Returns a string formed by concatenating all of the currently
	 * available message blocks after the given sequence number or 
	 * null if none are available. Will stop when a non-existent message
	 * block is encountered (ex: if there is no message block for sequence number
	 * n but there is a message block for sequence number n + 1, that message
	 * block will not be included in the string returned by this method).
	 */
	/* TODO: Uncomment and implement or remove
	public String getMessageFrom(int sequenceNumber)
	{
	    if(sequenceNumber < 0 || sequenceNumber >= msgBlocks.size())
	    {
		throw new IllegalArgumentException("Error in Receiver.MessageBuilder.getMessageFrom: invalid sequence number " + sequenceNumber + "; sequence number must be greater than or equal to zero and less than the number of pipes [" + numPipes + "]");
	    }

	    for(int i = sequenceNumber; i < ??; i++)
	    {

	    }
	}
	*/

	/**
	 * Checks whether or not this MessageBuilder contains a 
	 * complete message (i.e. has at least one message block
	 * for every possible sequence number).
	 */
	public boolean hasFullMessage()
	{
	    if(msgBlocks.size() < numPipes)
	    {
		return false;
	    }

	    // TODO: Make sure that this iterates in numerical order
	    for(Integer key: msgBlocks.keySet())
	    {
		List<String> blockList = msgBlocks.get(key);
		
		if(blockList == null || blockList.size() > 0)
		{
		    return false;
		}
	    }

	    return true;
	}
    }

    public static void main(String[] args)
    {
	// TODO: Refactor to allow a REPL message sending loop

	if(args.length < 1 || args.length > 2)
	{
	    usage(); // Print usage message and exit    
	}

	int numPipes = Integer.valueOf(args[0]);
		
	if(!ChannelUtils.isValidNumberOfPipes(numPipes))
	{
	    ChannelUtils.output("numPipes must be greater than zero and less than or equal to " + ChannelUtils.getMaxNumPipes());
	    System.exit(0);
	}

	List<Channel> pipes = null;

	try
	{
	    
	    if(args.length == 2)
	    {
		long sleepInterval = Long.valueOf(args[1]);
		pipes = ChannelUtils.getPipes(sleepInterval, Channel.CHANNEL_MODE.RECEIVER);	    
	    }
	    else
	    {
		pipes = ChannelUtils.getPipes(Channel.CHANNEL_MODE.RECEIVER);
	    }
	}
	catch(IOException e)
	{
	    ChannelUtils.output("In Receiver.main(): IOException occurred while retrieving channel pipes");
	}

	Receiver messageReceiver = new Receiver(pipes);
	messageReceiver.run();
    }

    private static void usage()
    {
	// TODO: Update
	System.out.println("java Receiver <num_pipes> [wait_interval]");
	System.exit(0);
    }    
}