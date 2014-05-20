import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 5/11/2014.
 */
public class Channel
{
    public enum CHANNEL_MODE {SENDER, RECEIVER}

    // TODO: Remove both of these
    private static final String TAG = "com.example.filecovertchannellib.lib.Channel";
    private static final byte ONE_BYTE = (byte) 1;

    private File readReady, writeReady, dataFile;
    //private Context cxt;
    private CHANNEL_MODE mode;
    private long sleepInterval;
    private long readReadyLastUpdateTime, writeReadyLastUpdateTime, dataFileLastUpdateTime;

    // Used to record whether or not the channel is currently open; only used in sender
    // mode since the opening on closing of channels is managed through the sender and
    // the receivers simply observe the files involved in the channel.
    private boolean channelOpen;

    //public Channel(File readReady, File writeReady, File dataFile, Context cxt, long sleepInterval, CHANNEL_MODE mode)
    public Channel(File readReady, File writeReady, File dataFile, long sleepInterval, CHANNEL_MODE mode)
        throws IOException
    {
        this.readReady = readReady;
        this.writeReady = writeReady;
        this.dataFile = dataFile;
        this.sleepInterval = sleepInterval;
        this.mode = mode;
        //this.cxt = cxt;

        channelOpen = false;

	/* TODO: Uncomment or remove
        readReadyLastUpdateTime = getLastUpdateTime(readReady);
        writeReadyLastUpdateTime = getLastUpdateTime(readReady);
        dataFileLastUpdateTime = getLastUpdateTime(readReady);
	*/
    }

    /**
     * Sends the given message string. Note that messages are limited to 2^32 bytes.
     *
     * @return  The number of bytes successfully sent.
     */
    public int sendMessage(String message)
	throws IOException, InterruptedException
    {
        throwIfNotSender();

	// TODO: Uncomment or remove
        //throwIfNotOpen();


	// Channel will be opened (if necessary) in sendMessage(byte[] message)
	// and then closed once the message transmission is complete
        return sendMessage(message.getBytes());
    }

    /**
     * Sends the given message.
     *
     * @return  The number of bytes successfully sent. Note that messages are limited to 2^32 bytes.
     */
    public int sendMessage(byte[] message)
	throws IOException, InterruptedException
    {
        throwIfNotSender();
        
	// TODO: Uncomment or remove
        //throwIfNotOpen();

	if(!channelOpen)
	{
	    openChannel();
	}

        int bytesSent = 0;
        try
        {
            for (int i = 0; i < message.length; i++) 
	    {
                sendByte(message[i]);
            }
        }
        catch(IOException e)
        {
            //Log.e(TAG, "sendMessate(byte[]) error: " + e.getMessage());
	    ChannelUtils.output(TAG, "sendMessate(byte[]) error: " + e.getMessage());
        }
        catch(InterruptedException e)
        {
            //Log.e(TAG, "sendMessate(byte[]) error: " + e.getMessage());
	    ChannelUtils.output(TAG, "sendMessate(byte[]) error: " + e.getMessage());
        }

	// Wait for the last bit to be read and then close the channel
	ChannelUtils.waitOn(writeReady, writeReadyLastUpdateTime, sleepInterval);
	closeChannel();

        return bytesSent;
    }

    public void sendByte(byte msgByte)
        throws IOException, InterruptedException
    {
        throwIfNotSender();
        throwIfNotOpen();

        for(int i = 0; i < 8; i++)
        {
            sendBit(((msgByte >> i) & ONE_BYTE) == 1);
        }
    }

    public void sendBit(boolean bit)
        throws IOException, InterruptedException
    {
        throwIfNotSender();
        throwIfNotOpen();

	// TODO: Remove
        char bitAsChar = bit ? '1' : '0';
	//ChannelUtils.output(TAG, "Sending bit [" + bitAsChar + "]");
	//ChannelUtils.output("Sending bit [" + bitAsChar + "]");

        writeReadyLastUpdateTime = ChannelUtils.waitOn(writeReady, writeReadyLastUpdateTime, sleepInterval);
        if(bit)
        {
            //ChannelUtils.touch(dataFile, cxt);
	    ChannelUtils.touch(dataFile);
        }

        //ChannelUtils.touch(readReady, cxt);
	ChannelUtils.touch(readReady);

	//ChannelUtils.output(TAG, "Bit sent");
    }

    public String receiveMessage()
    {
        throwIfNotReceiver(); 
        throwIfNotOpen(); 

        byte[] msgData = receiveMessageBytes();

        return new String(msgData);
    }

    public byte[] receiveMessageBytes()
    {
        throwIfNotReceiver();
        throwIfNotOpen(); 

        List<Byte> dataBytes = new ArrayList<Byte>();

        try {
            while(channelIsOpen())
            {
		byte dataByte = receiveByte();

		if(!channelOpen)
		{
		    // Channel was closed while reading that byte
		    break;
		}

		dataBytes.add(dataByte);
            }
        }
        catch(InterruptedIOException e)
        {
	    ChannelUtils.output("In Channel.receiveMessageBytes(): InterruptedIOException occurred; " + e.getMessage());
        }
	catch(IOException e)
	{
	    ChannelUtils.output("In Channel.receiveMessageBytes(): IOException occurred; " + e.getMessage());
	}

        byte[] message = new byte[dataBytes.size()];
        for(int i = 0; i < dataBytes.size(); i++)
        {
            message[i] = dataBytes.get(i);
        }

        return message;
    }

    public byte receiveByte()
        throws InterruptedIOException, IOException
    {
        throwIfNotReceiver();
        throwIfNotOpen();

        byte dataByte = (byte) 0;
        for(int i = 0; i < 8; i++)
        {
            if(!channelIsOpen())
            {
		channelOpen = false;
                throw new InterruptedIOException("Incomplete data read: channel closed while reading a byte (bit position " + i + ")");
            }

            byte bit;

            try
            {
                bit = receiveBit();
            }
            catch(IOException e)
            {
                throw new InterruptedIOException("Incomplete data read: IOException occurred while reading a byte (bit position " + i + ")");
            }
            catch(InterruptedException e)
            {
                throw new InterruptedIOException("Incomplete data read: InterruptedException occurred while reading a byte (bit position " + i + ")");
            }

	    if(!channelOpen)
	    {
		// TODO: Remove
		//System.out.println("Channel closed at bit " + i);
		//

		/* TODO: Uncomment or remove
		if(i > 0)
		{
		    // If this wasn't the first bit read for the current byte, assume the transmission got
		    // interrupted
		    throw new InterruptedIOException("Incomplete data read: channel closed while reading a byte (bit position " + i + ")");
		}
		else
		{
		    break;
		}
		*/

		break;
	    }

	    /* TODO: Uncomment or remove
            if(bit >= 0)
            {
		// TODO: Remove
                //dataByte |= (ONE_BYTE << i);
		dataByte |= (bit << i);
            }
	    else
	    {
		// Channel has been closed

		// TODO: Uncomment or remove
		//dataByte = (byte) -1; 

		throw new InterruptedIOException("Incomplete data read: channel closed while reading a byte");
	    }	   
	    */

	    dataByte |= (bit << i);
        }

	// TODO: Remove
	//String s1 = String.format("%8s", Integer.toBinaryString(dataByte & 0xFF)).replace(' ', '0');
	//System.out.println("dataByte = " + s1);
	//


        return dataByte;
    }

    public byte receiveBit()
        throws InterruptedException, IOException
    {
        throwIfNotReceiver();
	throwIfNotOpen(); 

	// TODO: Remove
	//ChannelUtils.output(TAG, "Receiving bit...");
	ChannelUtils.touch(writeReady);

        byte bit;

	/* TODO: Uncomment or remove
	if(!channelOpen)
	{
	    waitForChannelToOpen();
	    readReadyLastUpdateTime = readReady.lastModified();
	}
	else
	{
	    readReadyLastUpdateTime = ChannelUtils.waitOn(readReady, readReadyLastUpdateTime, sleepInterval);
	}
	*/

	readReadyLastUpdateTime = ChannelUtils.waitOn(readReady, readReadyLastUpdateTime, sleepInterval);

	if(readReadyLastUpdateTime < 0 || !dataFile.exists())
	{
	    // The channel has been closed by the sender
	    channelOpen = false;

	    // TODO: Remove
	    //ChannelUtils.output("Done receiving: channel closed");

	    return (byte) -1;
	}

        long dataFileTime = dataFile.lastModified();

        if(dataFileTime > dataFileLastUpdateTime)
        {
            bit = (byte) 1;
            dataFileLastUpdateTime = dataFileTime;
        }
        else
        {
            bit = (byte) 0;
        }


	// TODO: Remove
        //char bitAsChar = bit ? '1' : '0';
	//ChannelUtils.output(TAG, "Received bit [" + bitAsChar + "]");
	//ChannelUtils.output("Received bit [" + bit + "]");

        return bit;
    }

    public void openChannel()
        throws IOException, InterruptedException
    {
        throwIfNotSender();

	if(channelOpen)
	{
	    return; // Channel is already open
	}

        // Note that the order of creation for these files is potentially significant since
        // any receiver process which is observing this channel will start attempting to
        // read data when it observes the presence of the readReady file, so this file should
        // be created last.

	/*
        ChannelUtils.touch(writeReady, cxt);
        ChannelUtils.touch(dataFile, cxt);
        ChannelUtils.touch(readReady, cxt);
	*/

	// TODO: Make sure that this is the correct ordering
        writeReadyLastUpdateTime = ChannelUtils.touch(writeReady);
        dataFileLastUpdateTime = ChannelUtils.touch(dataFile);
        readReadyLastUpdateTime = ChannelUtils.touch(readReady);

        channelOpen = true;

	// TODO: Figure out if this is still necessary
	sendBit(false); // Used to sync up the sender and receiver (otherwise the receiver will miss the first data bit)
    }

    public void closeChannel()
        throws IOException
    {
        throwIfNotSender();

	if(!channelOpen)
	{
	    return; // Nothing to do
	}

        // Note that the order of deletion for these files is potentially significant since
        // any receiver process which is observing this channel will check for the existence
        // of the data file before attempting to read a bit from the channel, so this file should
        // be deleted first.
        dataFile.delete();
        writeReady.delete();
        readReady.delete();

        dataFile = null;
        writeReady = null;
        readReady = null;

        channelOpen = false;
    }

    public void waitForChannelToOpen()
	throws InterruptedException, IOException
    {
	throwIfNotReceiver();
	
	// TODO: Remove
	System.out.println("Waiting for channel to open...");

	while(!readReady.exists() && !dataFile.exists() && !writeReady.exists())
	{
	    Thread.sleep(sleepInterval); // Wait for the read ready file to exist
	}

	readReadyLastUpdateTime = readReady.lastModified();
	writeReadyLastUpdateTime = writeReady.lastModified();
	dataFileLastUpdateTime = dataFile.lastModified();

	channelOpen = true;

	// TODO: Uncomment or remove
	// The first "bit" transmitted over the channel is just used for initiating the transmission
	// and is not actually a data bit and therefore it is ignored
	receiveBit();
    }

    private static long getLastUpdateTime(File file)
        throws IllegalArgumentException, IOException
    {
        if(file == null)
        {
            throw new IllegalArgumentException(TAG + ": files cannot be null");
        }

        if(!file.exists())
        {
            File parent = file.getParentFile();
            if(parent != null)
                if(!parent.exists())
                    if(!parent.mkdirs())
                        throw new IOException(TAG + ": Cannot create parent directories for file: " + file);

            file.createNewFile();
        }

        return file.lastModified();
    }

    private void throwIfNotSender() {
        if (mode != CHANNEL_MODE.SENDER)
        {
            throw new IllegalStateException("Cannot send data, open channels, or close channels on a receiver channel");
        }
    }

    private void throwIfNotReceiver() {
        if (mode != CHANNEL_MODE.RECEIVER)
        {
            throw new IllegalStateException("Cannot receive data on a sender channel");
        }
    }

    private void throwIfNotOpen() {
        if(!channelIsOpen())
        {
            throw new IllegalStateException("Invalid state: channel must be open before sending or receiving data");
        }
    }

    private boolean channelIsOpen()
    {
        if(channelOpen)
        {
            return true;
        }
        else
        {
            return (dataFile != null && dataFile.exists());
        }
    }
}
