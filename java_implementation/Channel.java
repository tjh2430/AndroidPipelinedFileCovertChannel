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
    // # TODO: Make sure that the files get opened and closed properly and that
    // the waitOn() function takes into account the fact that the various files
    // can be deleted at any time

    // # TODO: Add logic for waiting for the channel to be opened by the receiver
    // and then handling when the channel is closed by the sender properly

    public enum CHANNEL_MODE {SENDER, RECEIVER}

    private static final String TAG = "com.example.filecovertchannellib.lib.Channel";
    private static final byte ONE_BYTE = (byte) 1;

    private File readReady, writeReady, dataFile;
    //private Context cxt;
    private CHANNEL_MODE mode;
    private long sleepInterval;
    private long readReadyLastUpdate, writeReadyLastUpdate, dataFileLastUpdateTime;

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

        readReadyLastUpdate = getLastUpdateTime(readReady);
        writeReadyLastUpdate = getLastUpdateTime(readReady);
        dataFileLastUpdateTime = getLastUpdateTime(readReady);
    }

    /**
     * Sends the given message string. Note that messages are limited to 2^32 bytes.
     *
     * @return  The number of bytes successfully sent.
     */
    public int sendMessage(String message)
    {
        throwIfNotSender();
        throwIfNotOpen();
        return sendMessage(message.getBytes());
    }

    /**
     * Sends the given message.
     *
     * @return  The number of bytes successfully sent. Note that messages are limited to 2^32 bytes.
     */
    public int sendMessage(byte[] message)
    {
        throwIfNotSender();
        throwIfNotOpen();

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

        char bitAsChar = bit ? '1' : '0';
	//ChannelUtils.output(TAG, "Sending bit [" + bitAsChar + "]");

        writeReadyLastUpdate = ChannelUtils.waitOn(writeReady, writeReadyLastUpdate, sleepInterval);
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
        //throwIfNotOpen(); TODO: Uncomment or remove

        byte[] msgData = receiveMessageBytes();

        return new String(msgData);
    }

    public byte[] receiveMessageBytes()
    {
        throwIfNotReceiver();
        //throwIfNotOpen(); TODO: Uncomment or remove

        List<Byte> dataBytes = new ArrayList<Byte>();

        try {
            while (channelIsOpen())
            {
                dataBytes.add(receiveByte());
            }
        }
        catch(InterruptedIOException e)
        {
            //Log.e(TAG, "Incomplete byte at byte position " + dataBytes.size());
	    //ChannelUtils.output(TAG, "Incomplete byte at byte position " + dataBytes.size());
	    ChannelUtils.output("In Channel.receiveMessageBytes(): InterruptedIOException occurred; " + e.getMessage());
        }
	catch(IOException e)
	{
	    //ChannelUtils.output(TAG, "Incomplete byte at byte position " + dataBytes.size());
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
        //throwIfNotOpen(); TODO: Uncomment or remove

        byte dataByte = (byte) 0;
        for(int i = 0; i < 8; i++)
        {
            if(!channelIsOpen())
            {
                throw new InterruptedIOException("Incomplete data read: channel closed while reading a byte");
            }

            boolean oneBit;

            try
            {
                oneBit = receiveBit();
            }
            catch(InterruptedException e)
            {
                throw new InterruptedIOException("Incomplete data read: IOException occurred while reading a byte");
            }

            if(oneBit)
            {
                dataByte |= (ONE_BYTE << i);
            }
        }

        return dataByte;
    }

    public boolean receiveBit()
        throws InterruptedException, IOException
    {
        throwIfNotReceiver();
        //throwIfNotOpen(); TODO: Uncomment or remove

	//ChannelUtils.output(TAG, "Receiving bit...");
	ChannelUtils.touch(writeReady);

        boolean oneBit;
        readReadyLastUpdate = ChannelUtils.waitOn(readReady, readReadyLastUpdate, sleepInterval);
        long dataFileTime = dataFile.lastModified();
        if(dataFileTime > dataFileLastUpdateTime)
        {
            oneBit = true;
            dataFileLastUpdateTime = dataFileTime;
        }
        else
        {
            oneBit = false;
        }


        char bitAsChar = oneBit ? '1' : '0';
	//ChannelUtils.output(TAG, "Received bit [" + bitAsChar + "]");

        return oneBit;
    }

    public void openChannel()
        throws IOException
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

        ChannelUtils.touch(writeReady);
        ChannelUtils.touch(dataFile);
        ChannelUtils.touch(readReady);

        channelOpen = true;
    }

    public void closeChannel()
        throws IOException
    {
        throwIfNotSender();

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
    {
	throwIfNotReceiver();

	// TODO: # implement
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
        if((mode == CHANNEL_MODE.SENDER && !channelOpen) || !channelIsOpen())
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
