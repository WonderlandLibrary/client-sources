package paulscode.sound.codecs;

import java.net.*;
import java.io.*;
import paulscode.sound.*;
import javax.sound.sampled.*;
import java.nio.*;

public class CodecWav implements ICodec
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private boolean endOfStream;
    private boolean initialized;
    private AudioInputStream myAudioInputStream;
    private SoundSystemLogger logger;
    
    public void reverseByteOrder(final boolean b) {
    }
    
    public CodecWav() {
        this.endOfStream = false;
        this.initialized = false;
        this.myAudioInputStream = null;
        this.logger = SoundSystemConfig.getLogger();
    }
    
    public boolean initialize(final URL url) {
        this.initialized(true, false);
        this.cleanup();
        if (url == null) {
            this.errorMessage("url null in method 'initialize'");
            this.cleanup();
            return false;
        }
        try {
            this.myAudioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(url.openStream()));
        }
        catch (UnsupportedAudioFileException ex) {
            this.errorMessage("Unsupported audio format in method 'initialize'");
            this.printStackTrace(ex);
            return false;
        }
        catch (IOException ex2) {
            this.errorMessage("Error setting up audio input stream in method 'initialize'");
            this.printStackTrace(ex2);
            return false;
        }
        this.endOfStream(true, false);
        this.initialized(true, true);
        return true;
    }
    
    public boolean initialized() {
        return this.initialized(false, false);
    }
    
    public SoundBuffer read() {
        if (this.myAudioInputStream == null) {
            return null;
        }
        final AudioFormat format = this.myAudioInputStream.getFormat();
        if (format == null) {
            this.errorMessage("Audio Format null in method 'read'");
            return null;
        }
        int n = 0;
        byte[] trimArray = new byte[SoundSystemConfig.getStreamingBufferSize()];
        try {
            while (!this.endOfStream(false, false) && n < trimArray.length) {
                final int read;
                if ((read = this.myAudioInputStream.read(trimArray, n, trimArray.length - n)) <= 0) {
                    this.endOfStream(true, true);
                    break;
                }
                n += read;
            }
        }
        catch (IOException ex) {
            this.endOfStream(true, true);
            return null;
        }
        if (n <= 0) {
            return null;
        }
        if (n < trimArray.length) {
            trimArray = trimArray(trimArray, n);
        }
        return new SoundBuffer(convertAudioBytes(trimArray, format.getSampleSizeInBits() == 16), format);
    }
    
    public SoundBuffer readAll() {
        if (this.myAudioInputStream == null) {
            this.errorMessage("Audio input stream null in method 'readAll'");
            return null;
        }
        final AudioFormat format = this.myAudioInputStream.getFormat();
        if (format == null) {
            this.errorMessage("Audio Format null in method 'readAll'");
            return null;
        }
        byte[] appendByteArrays = null;
        if (format.getChannels() * (int)this.myAudioInputStream.getFrameLength() * format.getSampleSizeInBits() / 8 > 0) {
            appendByteArrays = new byte[format.getChannels() * (int)this.myAudioInputStream.getFrameLength() * format.getSampleSizeInBits() / 8];
            int n = 0;
            try {
                int read;
                while ((read = this.myAudioInputStream.read(appendByteArrays, n, appendByteArrays.length - n)) != -1 && n < appendByteArrays.length) {
                    n += read;
                }
            }
            catch (IOException ex) {
                this.errorMessage("Exception thrown while reading from the AudioInputStream (location #1).");
                this.printStackTrace(ex);
                return null;
            }
        }
        else {
            int n2 = 0;
            int i;
            for (byte[] array = new byte[SoundSystemConfig.getFileChunkSize()]; !this.endOfStream(false, false) && n2 < SoundSystemConfig.getMaxFileSize(); n2 += i, appendByteArrays = appendByteArrays(appendByteArrays, array, i)) {
                i = 0;
                try {
                    while (i < array.length) {
                        final int read2;
                        if ((read2 = this.myAudioInputStream.read(array, i, array.length - i)) <= 0) {
                            this.endOfStream(true, true);
                            break;
                        }
                        i += read2;
                    }
                }
                catch (IOException ex2) {
                    this.errorMessage("Exception thrown while reading from the AudioInputStream (location #2).");
                    this.printStackTrace(ex2);
                    return null;
                }
            }
        }
        final SoundBuffer soundBuffer = new SoundBuffer(convertAudioBytes(appendByteArrays, format.getSampleSizeInBits() == 16), format);
        try {
            this.myAudioInputStream.close();
        }
        catch (IOException ex3) {}
        return soundBuffer;
    }
    
    public boolean endOfStream() {
        return this.endOfStream(false, false);
    }
    
    public void cleanup() {
        if (this.myAudioInputStream != null) {
            try {
                this.myAudioInputStream.close();
            }
            catch (Exception ex) {}
        }
        this.myAudioInputStream = null;
    }
    
    public AudioFormat getAudioFormat() {
        if (this.myAudioInputStream == null) {
            return null;
        }
        return this.myAudioInputStream.getFormat();
    }
    
    private synchronized boolean initialized(final boolean b, final boolean initialized) {
        if (b) {
            this.initialized = initialized;
        }
        return this.initialized;
    }
    
    private synchronized boolean endOfStream(final boolean b, final boolean endOfStream) {
        if (b) {
            this.endOfStream = endOfStream;
        }
        return this.endOfStream;
    }
    
    private static byte[] trimArray(final byte[] array, final int n) {
        Object o = null;
        if (array != null && array.length > n) {
            o = new byte[n];
            System.arraycopy(array, 0, o, 0, n);
        }
        return (byte[])o;
    }
    
    private static byte[] convertAudioBytes(final byte[] array, final boolean b) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(array.length);
        allocateDirect.order(ByteOrder.nativeOrder());
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        if (b) {
            final ShortBuffer shortBuffer = allocateDirect.asShortBuffer();
            final ShortBuffer shortBuffer2 = wrap.asShortBuffer();
            while (shortBuffer2.hasRemaining()) {
                shortBuffer.put(shortBuffer2.get());
            }
        }
        else {
            while (wrap.hasRemaining()) {
                allocateDirect.put(wrap.get());
            }
        }
        allocateDirect.rewind();
        if (!allocateDirect.hasArray()) {
            final byte[] array2 = new byte[allocateDirect.capacity()];
            allocateDirect.get(array2);
            allocateDirect.clear();
            return array2;
        }
        return allocateDirect.array();
    }
    
    private static byte[] appendByteArrays(final byte[] array, final byte[] array2, final int n) {
        if (array == null && array2 == null) {
            return null;
        }
        byte[] array3;
        if (array == null) {
            array3 = new byte[n];
            System.arraycopy(array2, 0, array3, 0, n);
        }
        else if (array2 == null) {
            array3 = new byte[array.length];
            System.arraycopy(array, 0, array3, 0, array.length);
        }
        else {
            array3 = new byte[array.length + n];
            System.arraycopy(array, 0, array3, 0, array.length);
            System.arraycopy(array2, 0, array3, array.length, n);
        }
        return array3;
    }
    
    private void errorMessage(final String s) {
        this.logger.errorMessage("CodecWav", s, 0);
    }
    
    private void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
}
