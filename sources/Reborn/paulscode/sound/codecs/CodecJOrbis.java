package paulscode.sound.codecs;

import javax.sound.sampled.*;
import com.jcraft.jogg.*;
import com.jcraft.jorbis.*;
import java.io.*;
import java.net.*;
import paulscode.sound.*;

public class CodecJOrbis implements ICodec
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    protected URL url;
    protected URLConnection urlConnection;
    private InputStream inputStream;
    private AudioFormat audioFormat;
    private boolean endOfStream;
    private boolean initialized;
    private byte[] buffer;
    private int bufferSize;
    private int count;
    private int index;
    private int convertedBufferSize;
    private byte[] convertedBuffer;
    private float[][][] pcmInfo;
    private int[] pcmIndex;
    private Packet joggPacket;
    private Page joggPage;
    private StreamState joggStreamState;
    private SyncState joggSyncState;
    private DspState jorbisDspState;
    private Block jorbisBlock;
    private Comment jorbisComment;
    private Info jorbisInfo;
    private SoundSystemLogger logger;
    
    public CodecJOrbis() {
        this.urlConnection = null;
        this.endOfStream = false;
        this.initialized = false;
        this.buffer = null;
        this.count = 0;
        this.index = 0;
        this.convertedBuffer = null;
        this.joggPacket = new Packet();
        this.joggPage = new Page();
        this.joggStreamState = new StreamState();
        this.joggSyncState = new SyncState();
        this.jorbisDspState = new DspState();
        this.jorbisBlock = new Block(this.jorbisDspState);
        this.jorbisComment = new Comment();
        this.jorbisInfo = new Info();
        this.logger = SoundSystemConfig.getLogger();
    }
    
    public void reverseByteOrder(final boolean b) {
    }
    
    public boolean initialize(final URL url) {
        this.initialized(true, false);
        if (this.joggStreamState != null) {
            this.joggStreamState.clear();
        }
        if (this.jorbisBlock != null) {
            this.jorbisBlock.clear();
        }
        if (this.jorbisDspState != null) {
            this.jorbisDspState.clear();
        }
        if (this.jorbisInfo != null) {
            this.jorbisInfo.clear();
        }
        if (this.joggSyncState != null) {
            this.joggSyncState.clear();
        }
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            }
            catch (IOException ex4) {}
        }
        this.url = url;
        this.bufferSize = 8192;
        this.buffer = null;
        this.count = 0;
        this.index = 0;
        this.joggStreamState = new StreamState();
        this.jorbisBlock = new Block(this.jorbisDspState);
        this.jorbisDspState = new DspState();
        this.jorbisInfo = new Info();
        this.joggSyncState = new SyncState();
        try {
            this.urlConnection = url.openConnection();
        }
        catch (UnknownServiceException ex) {
            this.errorMessage("Unable to create a UrlConnection in method 'initialize'.");
            this.printStackTrace(ex);
            this.cleanup();
            return false;
        }
        catch (IOException ex2) {
            this.errorMessage("Unable to create a UrlConnection in method 'initialize'.");
            this.printStackTrace(ex2);
            this.cleanup();
            return false;
        }
        if (this.urlConnection != null) {
            try {
                this.inputStream = this.openInputStream();
            }
            catch (IOException ex3) {
                this.errorMessage("Unable to acquire inputstream in method 'initialize'.");
                this.printStackTrace(ex3);
                this.cleanup();
                return false;
            }
        }
        this.endOfStream(true, false);
        this.joggSyncState.init();
        this.joggSyncState.buffer(this.bufferSize);
        this.buffer = this.joggSyncState.data;
        try {
            if (!this.readHeader()) {
                this.errorMessage("Error reading the header");
                return false;
            }
        }
        catch (IOException ex5) {
            this.errorMessage("Error reading the header");
            return false;
        }
        this.convertedBufferSize = this.bufferSize * 2;
        this.jorbisDspState.synthesis_init(this.jorbisInfo);
        this.jorbisBlock.init(this.jorbisDspState);
        this.audioFormat = new AudioFormat(this.jorbisInfo.rate, 16, this.jorbisInfo.channels, true, false);
        this.pcmInfo = new float[1][][];
        this.pcmIndex = new int[this.jorbisInfo.channels];
        this.initialized(true, true);
        return true;
    }
    
    protected InputStream openInputStream() {
        return this.urlConnection.getInputStream();
    }
    
    public boolean initialized() {
        return this.initialized(false, false);
    }
    
    public SoundBuffer read() {
        byte[] array = null;
        while (!this.endOfStream(false, false) && (array == null || array.length < SoundSystemConfig.getStreamingBufferSize())) {
            if (array == null) {
                array = this.readBytes();
            }
            else {
                array = appendByteArrays(array, this.readBytes());
            }
        }
        if (array == null) {
            return null;
        }
        return new SoundBuffer(array, this.audioFormat);
    }
    
    public SoundBuffer readAll() {
        byte[] array = null;
        while (!this.endOfStream(false, false)) {
            if (array == null) {
                array = this.readBytes();
            }
            else {
                array = appendByteArrays(array, this.readBytes());
            }
        }
        if (array == null) {
            return null;
        }
        return new SoundBuffer(array, this.audioFormat);
    }
    
    public boolean endOfStream() {
        return this.endOfStream(false, false);
    }
    
    public void cleanup() {
        this.joggStreamState.clear();
        this.jorbisBlock.clear();
        this.jorbisDspState.clear();
        this.jorbisInfo.clear();
        this.joggSyncState.clear();
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            }
            catch (IOException ex) {}
        }
        this.joggStreamState = null;
        this.jorbisBlock = null;
        this.jorbisDspState = null;
        this.jorbisInfo = null;
        this.joggSyncState = null;
        this.inputStream = null;
    }
    
    public AudioFormat getAudioFormat() {
        return this.audioFormat;
    }
    
    private boolean readHeader() {
        this.index = this.joggSyncState.buffer(this.bufferSize);
        int read = this.inputStream.read(this.joggSyncState.data, this.index, this.bufferSize);
        if (read < 0) {
            read = 0;
        }
        this.joggSyncState.wrote(read);
        if (this.joggSyncState.pageout(this.joggPage) != 1) {
            if (read < this.bufferSize) {
                return true;
            }
            this.errorMessage("Ogg header not recognized in method 'readHeader'.");
            return false;
        }
        else {
            this.joggStreamState.init(this.joggPage.serialno());
            this.jorbisInfo.init();
            this.jorbisComment.init();
            if (this.joggStreamState.pagein(this.joggPage) < 0) {
                this.errorMessage("Problem with first Ogg header page in method 'readHeader'.");
                return false;
            }
            if (this.joggStreamState.packetout(this.joggPacket) != 1) {
                this.errorMessage("Problem with first Ogg header packet in method 'readHeader'.");
                return false;
            }
            if (this.jorbisInfo.synthesis_headerin(this.jorbisComment, this.joggPacket) < 0) {
                this.errorMessage("File does not contain Vorbis header in method 'readHeader'.");
                return false;
            }
            int i = 0;
            while (i < 2) {
                while (i < 2) {
                    final int pageout = this.joggSyncState.pageout(this.joggPage);
                    if (pageout == 0) {
                        break;
                    }
                    if (pageout != 1) {
                        continue;
                    }
                    this.joggStreamState.pagein(this.joggPage);
                    while (i < 2) {
                        final int packetout = this.joggStreamState.packetout(this.joggPacket);
                        if (packetout == 0) {
                            break;
                        }
                        if (packetout == -1) {
                            this.errorMessage("Secondary Ogg header corrupt in method 'readHeader'.");
                            return false;
                        }
                        this.jorbisInfo.synthesis_headerin(this.jorbisComment, this.joggPacket);
                        ++i;
                    }
                }
                this.index = this.joggSyncState.buffer(this.bufferSize);
                int read2 = this.inputStream.read(this.joggSyncState.data, this.index, this.bufferSize);
                if (read2 < 0) {
                    read2 = 0;
                }
                if (read2 == 0 && i < 2) {
                    this.errorMessage("End of file reached before finished readingOgg header in method 'readHeader'");
                    return false;
                }
                this.joggSyncState.wrote(read2);
            }
            this.index = this.joggSyncState.buffer(this.bufferSize);
            this.buffer = this.joggSyncState.data;
            return true;
        }
    }
    
    private byte[] readBytes() {
        if (!this.initialized(false, false)) {
            return null;
        }
        if (this.endOfStream(false, false)) {
            return null;
        }
        if (this.convertedBuffer == null) {
            this.convertedBuffer = new byte[this.convertedBufferSize];
        }
        byte[] appendByteArrays = null;
        switch (this.joggSyncState.pageout(this.joggPage)) {
            case -1:
            case 0: {
                break;
            }
            default: {
                this.joggStreamState.pagein(this.joggPage);
                if (this.joggPage.granulepos() == 0L) {
                    this.endOfStream(true, true);
                    return null;
                }
            Label_0152:
                while (true) {
                    switch (this.joggStreamState.packetout(this.joggPacket)) {
                        case 0: {
                            break Label_0152;
                        }
                        case -1: {
                            continue;
                        }
                        default: {
                            if (this.jorbisBlock.synthesis(this.joggPacket) == 0) {
                                this.jorbisDspState.synthesis_blockin(this.jorbisBlock);
                            }
                            int synthesis_pcmout;
                            while ((synthesis_pcmout = this.jorbisDspState.synthesis_pcmout(this.pcmInfo, this.pcmIndex)) > 0) {
                                final float[][] array = this.pcmInfo[0];
                                final int n = (synthesis_pcmout < this.convertedBufferSize) ? synthesis_pcmout : this.convertedBufferSize;
                                for (int i = 0; i < this.jorbisInfo.channels; ++i) {
                                    int n2 = i * 2;
                                    final int n3 = this.pcmIndex[i];
                                    for (int j = 0; j < n; ++j) {
                                        int n4 = (int)(array[i][n3 + j] * 32767.0);
                                        if (n4 > 32767) {
                                            n4 = 32767;
                                        }
                                        if (n4 < -32768) {
                                            n4 = -32768;
                                        }
                                        if (n4 < 0) {
                                            n4 |= 0x8000;
                                        }
                                        this.convertedBuffer[n2] = (byte)n4;
                                        this.convertedBuffer[n2 + 1] = (byte)(n4 >>> 8);
                                        n2 += 2 * this.jorbisInfo.channels;
                                    }
                                }
                                this.jorbisDspState.synthesis_read(n);
                                appendByteArrays = appendByteArrays(appendByteArrays, this.convertedBuffer, 2 * this.jorbisInfo.channels * n);
                            }
                            continue;
                        }
                    }
                }
                if (this.joggPage.eos() != 0) {
                    this.endOfStream(true, true);
                    break;
                }
                break;
            }
        }
        if (!this.endOfStream(false, false)) {
            this.index = this.joggSyncState.buffer(this.bufferSize);
            this.buffer = this.joggSyncState.data;
            try {
                this.count = this.inputStream.read(this.buffer, this.index, this.bufferSize);
            }
            catch (Exception ex) {
                this.printStackTrace(ex);
                return null;
            }
            if (this.count == -1) {
                return appendByteArrays;
            }
            this.joggSyncState.wrote(this.count);
            if (this.count == 0) {
                this.endOfStream(true, true);
            }
        }
        return appendByteArrays;
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
    
    private static byte[] appendByteArrays(final byte[] array, final byte[] array2, final int n) {
        int length = n;
        if (array2 == null || array2.length == 0) {
            length = 0;
        }
        else if (array2.length < n) {
            length = array2.length;
        }
        if (array == null && (array2 == null || length <= 0)) {
            return null;
        }
        byte[] array3;
        if (array == null) {
            array3 = new byte[length];
            System.arraycopy(array2, 0, array3, 0, length);
        }
        else if (array2 == null || length <= 0) {
            array3 = new byte[array.length];
            System.arraycopy(array, 0, array3, 0, array.length);
        }
        else {
            array3 = new byte[array.length + length];
            System.arraycopy(array, 0, array3, 0, array.length);
            System.arraycopy(array2, 0, array3, array.length, length);
        }
        return array3;
    }
    
    private static byte[] appendByteArrays(final byte[] array, final byte[] array2) {
        if (array == null && array2 == null) {
            return null;
        }
        byte[] array3;
        if (array == null) {
            array3 = new byte[array2.length];
            System.arraycopy(array2, 0, array3, 0, array2.length);
        }
        else if (array2 == null) {
            array3 = new byte[array.length];
            System.arraycopy(array, 0, array3, 0, array.length);
        }
        else {
            array3 = new byte[array.length + array2.length];
            System.arraycopy(array, 0, array3, 0, array.length);
            System.arraycopy(array2, 0, array3, array.length, array2.length);
        }
        return array3;
    }
    
    private void errorMessage(final String s) {
        this.logger.errorMessage("CodecJOrbis", s, 0);
    }
    
    private void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
}
