package paulscode.sound.libraries;

import org.lwjgl.*;
import org.lwjgl.openal.*;
import java.util.*;
import java.nio.*;
import java.net.*;
import javax.sound.sampled.*;
import paulscode.sound.*;

public class LibraryLWJGLOpenAL extends Library
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private FloatBuffer listenerPositionAL;
    private FloatBuffer listenerOrientation;
    private FloatBuffer listenerVelocity;
    private HashMap ALBufferMap;
    private static boolean alPitchSupported;
    
    public LibraryLWJGLOpenAL() {
        this.listenerPositionAL = null;
        this.listenerOrientation = null;
        this.listenerVelocity = null;
        this.ALBufferMap = null;
        this.ALBufferMap = new HashMap();
        this.reverseByteOrder = true;
    }
    
    public void init() {
        boolean checkALError;
        try {
            AL.create();
            checkALError = this.checkALError();
        }
        catch (LWJGLException ex) {
            this.errorMessage("Unable to initialize OpenAL.  Probable cause: OpenAL not supported.");
            this.printStackTrace(ex);
            throw new LibraryLWJGLOpenAL$Exception(ex.getMessage(), 101);
        }
        if (checkALError) {
            this.importantMessage("OpenAL did not initialize properly!");
        }
        else {
            this.message("OpenAL initialized.");
        }
        this.listenerPositionAL = BufferUtils.createFloatBuffer(3).put(new float[] { this.listener.position.x, this.listener.position.y, this.listener.position.z });
        this.listenerOrientation = BufferUtils.createFloatBuffer(6).put(new float[] { this.listener.lookAt.x, this.listener.lookAt.y, this.listener.lookAt.z, this.listener.up.x, this.listener.up.y, this.listener.up.z });
        this.listenerVelocity = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
        this.listenerPositionAL.flip();
        this.listenerOrientation.flip();
        this.listenerVelocity.flip();
        AL10.alListener(4100, this.listenerPositionAL);
        final boolean b = this.checkALError() || checkALError;
        AL10.alListener(4111, this.listenerOrientation);
        final boolean b2 = this.checkALError() || b;
        AL10.alListener(4102, this.listenerVelocity);
        final boolean b3 = this.checkALError() || b2;
        AL10.alDopplerFactor(SoundSystemConfig.getDopplerFactor());
        final boolean b4 = this.checkALError() || b3;
        AL10.alDopplerVelocity(SoundSystemConfig.getDopplerVelocity());
        if (this.checkALError() || b4) {
            this.importantMessage("OpenAL did not initialize properly!");
            throw new LibraryLWJGLOpenAL$Exception("Problem encountered while loading OpenAL or creating the listener.  Probable cause:  OpenAL not supported", 101);
        }
        super.init();
        final ChannelLWJGLOpenAL channelLWJGLOpenAL = this.normalChannels.get(1);
        try {
            AL10.alSourcef(channelLWJGLOpenAL.ALSource.get(0), 4099, 1.0f);
            if (this.checkALError()) {
                alPitchSupported(true, false);
                throw new LibraryLWJGLOpenAL$Exception("OpenAL: AL_PITCH not supported.", 108);
            }
            alPitchSupported(true, true);
        }
        catch (Exception ex2) {
            alPitchSupported(true, false);
            throw new LibraryLWJGLOpenAL$Exception("OpenAL: AL_PITCH not supported.", 108);
        }
    }
    
    public static boolean libraryCompatible() {
        if (AL.isCreated()) {
            return true;
        }
        try {
            AL.create();
        }
        catch (Exception ex) {
            return false;
        }
        try {
            AL.destroy();
        }
        catch (Exception ex2) {}
        return true;
    }
    
    protected Channel createChannel(final int n) {
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        try {
            AL10.alGenSources(intBuffer);
        }
        catch (Exception ex) {
            AL10.alGetError();
            return null;
        }
        if (AL10.alGetError() != 0) {
            return null;
        }
        return new ChannelLWJGLOpenAL(n, intBuffer);
    }
    
    public void cleanup() {
        super.cleanup();
        final Iterator<String> iterator = this.bufferMap.keySet().iterator();
        while (iterator.hasNext()) {
            final IntBuffer buffers = this.ALBufferMap.get(iterator.next());
            if (buffers != null) {
                AL10.alDeleteBuffers(buffers);
                this.checkALError();
                buffers.clear();
            }
        }
        this.bufferMap.clear();
        AL.destroy();
        this.bufferMap = null;
        this.listenerPositionAL = null;
        this.listenerOrientation = null;
        this.listenerVelocity = null;
    }
    
    public boolean loadSound(final FilenameURL filenameURL) {
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'loadSound'");
        }
        if (this.ALBufferMap == null) {
            this.ALBufferMap = new HashMap();
            this.importantMessage("Open AL Buffer Map was null in method'loadSound'");
        }
        if (this.errorCheck(filenameURL == null, "Filename/URL not specified in method 'loadSound'")) {
            return false;
        }
        if (this.bufferMap.get(filenameURL.getFilename()) != null) {
            return true;
        }
        final ICodec codec = SoundSystemConfig.getCodec(filenameURL.getFilename());
        if (this.errorCheck(codec == null, "No codec found for file '" + filenameURL.getFilename() + "' in method 'loadSound'")) {
            return false;
        }
        codec.reverseByteOrder(true);
        final URL url = filenameURL.getURL();
        if (this.errorCheck(url == null, "Unable to open file '" + filenameURL.getFilename() + "' in method 'loadSound'")) {
            return false;
        }
        codec.initialize(url);
        final SoundBuffer all = codec.readAll();
        codec.cleanup();
        if (this.errorCheck(all == null, "Sound buffer null in method 'loadSound'")) {
            return false;
        }
        this.bufferMap.put(filenameURL.getFilename(), all);
        final AudioFormat audioFormat = all.audioFormat;
        int format;
        if (audioFormat.getChannels() == 1) {
            if (audioFormat.getSampleSizeInBits() == 8) {
                format = 4352;
            }
            else {
                if (audioFormat.getSampleSizeInBits() != 16) {
                    this.errorMessage("Illegal sample size in method 'loadSound'");
                    return false;
                }
                format = 4353;
            }
        }
        else {
            if (audioFormat.getChannels() != 2) {
                this.errorMessage("File neither mono nor stereo in method 'loadSound'");
                return false;
            }
            if (audioFormat.getSampleSizeInBits() == 8) {
                format = 4354;
            }
            else {
                if (audioFormat.getSampleSizeInBits() != 16) {
                    this.errorMessage("Illegal sample size in method 'loadSound'");
                    return false;
                }
                format = 4355;
            }
        }
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        AL10.alGenBuffers(intBuffer);
        if (this.errorCheck(AL10.alGetError() != 0, "alGenBuffers error when loading " + filenameURL.getFilename())) {
            return false;
        }
        AL10.alBufferData(intBuffer.get(0), format, (ByteBuffer)BufferUtils.createByteBuffer(all.audioData.length).put(all.audioData).flip(), (int)audioFormat.getSampleRate());
        if (this.errorCheck(AL10.alGetError() != 0, "alBufferData error when loading " + filenameURL.getFilename()) && this.errorCheck(intBuffer == null, "Sound buffer was not created for " + filenameURL.getFilename())) {
            return false;
        }
        this.ALBufferMap.put(filenameURL.getFilename(), intBuffer);
        return true;
    }
    
    public boolean loadSound(final SoundBuffer soundBuffer, final String s) {
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'loadSound'");
        }
        if (this.ALBufferMap == null) {
            this.ALBufferMap = new HashMap();
            this.importantMessage("Open AL Buffer Map was null in method'loadSound'");
        }
        if (this.errorCheck(s == null, "Identifier not specified in method 'loadSound'")) {
            return false;
        }
        if (this.bufferMap.get(s) != null) {
            return true;
        }
        if (this.errorCheck(soundBuffer == null, "Sound buffer null in method 'loadSound'")) {
            return false;
        }
        this.bufferMap.put(s, soundBuffer);
        final AudioFormat audioFormat = soundBuffer.audioFormat;
        int format;
        if (audioFormat.getChannels() == 1) {
            if (audioFormat.getSampleSizeInBits() == 8) {
                format = 4352;
            }
            else {
                if (audioFormat.getSampleSizeInBits() != 16) {
                    this.errorMessage("Illegal sample size in method 'loadSound'");
                    return false;
                }
                format = 4353;
            }
        }
        else {
            if (audioFormat.getChannels() != 2) {
                this.errorMessage("File neither mono nor stereo in method 'loadSound'");
                return false;
            }
            if (audioFormat.getSampleSizeInBits() == 8) {
                format = 4354;
            }
            else {
                if (audioFormat.getSampleSizeInBits() != 16) {
                    this.errorMessage("Illegal sample size in method 'loadSound'");
                    return false;
                }
                format = 4355;
            }
        }
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        AL10.alGenBuffers(intBuffer);
        if (this.errorCheck(AL10.alGetError() != 0, "alGenBuffers error when saving " + s)) {
            return false;
        }
        AL10.alBufferData(intBuffer.get(0), format, (ByteBuffer)BufferUtils.createByteBuffer(soundBuffer.audioData.length).put(soundBuffer.audioData).flip(), (int)audioFormat.getSampleRate());
        if (this.errorCheck(AL10.alGetError() != 0, "alBufferData error when saving " + s) && this.errorCheck(intBuffer == null, "Sound buffer was not created for " + s)) {
            return false;
        }
        this.ALBufferMap.put(s, intBuffer);
        return true;
    }
    
    public void unloadSound(final String s) {
        this.ALBufferMap.remove(s);
        super.unloadSound(s);
    }
    
    public void setMasterVolume(final float masterVolume) {
        super.setMasterVolume(masterVolume);
        AL10.alListenerf(4106, masterVolume);
        this.checkALError();
    }
    
    public void newSource(final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final float n, final float n2, final float n3, final int n4, final float n5) {
        IntBuffer intBuffer = null;
        if (!b2) {
            if (this.ALBufferMap.get(filenameURL.getFilename()) == null && !this.loadSound(filenameURL)) {
                this.errorMessage("Source '" + s + "' was not created " + "because an error occurred while loading " + filenameURL.getFilename());
                return;
            }
            intBuffer = this.ALBufferMap.get(filenameURL.getFilename());
            if (intBuffer == null) {
                this.errorMessage("Source '" + s + "' was not created " + "because a sound buffer was not found for " + filenameURL.getFilename());
                return;
            }
        }
        SoundBuffer soundBuffer = null;
        if (!b2) {
            if (this.bufferMap.get(filenameURL.getFilename()) == null && !this.loadSound(filenameURL)) {
                this.errorMessage("Source '" + s + "' was not created " + "because an error occurred while loading " + filenameURL.getFilename());
                return;
            }
            soundBuffer = this.bufferMap.get(filenameURL.getFilename());
            if (soundBuffer == null) {
                this.errorMessage("Source '" + s + "' was not created " + "because audio data was not found for " + filenameURL.getFilename());
                return;
            }
        }
        this.sourceMap.put(s, new SourceLWJGLOpenAL(this.listenerPositionAL, intBuffer, b, b2, b3, s, filenameURL, soundBuffer, n, n2, n3, n4, n5, false));
    }
    
    public void rawDataStream(final AudioFormat audioFormat, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.sourceMap.put(s, new SourceLWJGLOpenAL(this.listenerPositionAL, audioFormat, b, s, n, n2, n3, n4, n5));
    }
    
    public void quickPlay(final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b4) {
        IntBuffer intBuffer = null;
        if (!b2) {
            if (this.ALBufferMap.get(filenameURL.getFilename()) == null) {
                this.loadSound(filenameURL);
            }
            intBuffer = this.ALBufferMap.get(filenameURL.getFilename());
            if (intBuffer == null) {
                this.errorMessage("Sound buffer was not created for " + filenameURL.getFilename());
                return;
            }
        }
        SoundBuffer soundBuffer = null;
        if (!b2) {
            if (this.bufferMap.get(filenameURL.getFilename()) == null && !this.loadSound(filenameURL)) {
                this.errorMessage("Source '" + s + "' was not created " + "because an error occurred while loading " + filenameURL.getFilename());
                return;
            }
            soundBuffer = this.bufferMap.get(filenameURL.getFilename());
            if (soundBuffer == null) {
                this.errorMessage("Source '" + s + "' was not created " + "because audio data was not found for " + filenameURL.getFilename());
                return;
            }
        }
        final SourceLWJGLOpenAL sourceLWJGLOpenAL = new SourceLWJGLOpenAL(this.listenerPositionAL, intBuffer, b, b2, b3, s, filenameURL, soundBuffer, n, n2, n3, n4, n5, false);
        this.sourceMap.put(s, sourceLWJGLOpenAL);
        this.play(sourceLWJGLOpenAL);
        if (b4) {
            sourceLWJGLOpenAL.setTemporary(true);
        }
    }
    
    public void copySources(final HashMap hashMap) {
        if (hashMap == null) {
            return;
        }
        final Iterator<String> iterator = hashMap.keySet().iterator();
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'copySources'");
        }
        if (this.ALBufferMap == null) {
            this.ALBufferMap = new HashMap();
            this.importantMessage("Open AL Buffer Map was null in method'copySources'");
        }
        this.sourceMap.clear();
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final Source source = (Source)hashMap.get(s);
            if (source != null) {
                SoundBuffer soundBuffer = null;
                if (!source.toStream) {
                    this.loadSound(source.filenameURL);
                    soundBuffer = this.bufferMap.get(source.filenameURL.getFilename());
                }
                if (!source.toStream && soundBuffer == null) {
                    continue;
                }
                this.sourceMap.put(s, new SourceLWJGLOpenAL(this.listenerPositionAL, this.ALBufferMap.get(source.filenameURL.getFilename()), source, soundBuffer));
            }
        }
    }
    
    public void setListenerPosition(final float n, final float n2, final float n3) {
        super.setListenerPosition(n, n2, n3);
        this.listenerPositionAL.put(0, n);
        this.listenerPositionAL.put(1, n2);
        this.listenerPositionAL.put(2, n3);
        AL10.alListener(4100, this.listenerPositionAL);
        this.checkALError();
    }
    
    public void setListenerAngle(final float listenerAngle) {
        super.setListenerAngle(listenerAngle);
        this.listenerOrientation.put(0, this.listener.lookAt.x);
        this.listenerOrientation.put(2, this.listener.lookAt.z);
        AL10.alListener(4111, this.listenerOrientation);
        this.checkALError();
    }
    
    public void setListenerOrientation(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super.setListenerOrientation(n, n2, n3, n4, n5, n6);
        this.listenerOrientation.put(0, n);
        this.listenerOrientation.put(1, n2);
        this.listenerOrientation.put(2, n3);
        this.listenerOrientation.put(3, n4);
        this.listenerOrientation.put(4, n5);
        this.listenerOrientation.put(5, n6);
        AL10.alListener(4111, this.listenerOrientation);
        this.checkALError();
    }
    
    public void setListenerData(final ListenerData listenerData) {
        super.setListenerData(listenerData);
        this.listenerPositionAL.put(0, listenerData.position.x);
        this.listenerPositionAL.put(1, listenerData.position.y);
        this.listenerPositionAL.put(2, listenerData.position.z);
        AL10.alListener(4100, this.listenerPositionAL);
        this.checkALError();
        this.listenerOrientation.put(0, listenerData.lookAt.x);
        this.listenerOrientation.put(1, listenerData.lookAt.y);
        this.listenerOrientation.put(2, listenerData.lookAt.z);
        this.listenerOrientation.put(3, listenerData.up.x);
        this.listenerOrientation.put(4, listenerData.up.y);
        this.listenerOrientation.put(5, listenerData.up.z);
        AL10.alListener(4111, this.listenerOrientation);
        this.checkALError();
        this.listenerVelocity.put(0, listenerData.velocity.x);
        this.listenerVelocity.put(1, listenerData.velocity.y);
        this.listenerVelocity.put(2, listenerData.velocity.z);
        AL10.alListener(4102, this.listenerVelocity);
        this.checkALError();
    }
    
    public void setListenerVelocity(final float n, final float n2, final float n3) {
        super.setListenerVelocity(n, n2, n3);
        this.listenerVelocity.put(0, this.listener.velocity.x);
        this.listenerVelocity.put(1, this.listener.velocity.y);
        this.listenerVelocity.put(2, this.listener.velocity.z);
        AL10.alListener(4102, this.listenerVelocity);
    }
    
    public void dopplerChanged() {
        super.dopplerChanged();
        AL10.alDopplerFactor(SoundSystemConfig.getDopplerFactor());
        this.checkALError();
        AL10.alDopplerVelocity(SoundSystemConfig.getDopplerVelocity());
        this.checkALError();
    }
    
    private boolean checkALError() {
        switch (AL10.alGetError()) {
            case 0: {
                return false;
            }
            case 40961: {
                this.errorMessage("Invalid name parameter.");
                return true;
            }
            case 40962: {
                this.errorMessage("Invalid parameter.");
                return true;
            }
            case 40963: {
                this.errorMessage("Invalid enumerated parameter value.");
                return true;
            }
            case 40964: {
                this.errorMessage("Illegal call.");
                return true;
            }
            case 40965: {
                this.errorMessage("Unable to allocate memory.");
                return true;
            }
            default: {
                this.errorMessage("An unrecognized error occurred.");
                return true;
            }
        }
    }
    
    public static boolean alPitchSupported() {
        return alPitchSupported(false, false);
    }
    
    private static synchronized boolean alPitchSupported(final boolean b, final boolean alPitchSupported) {
        if (b) {
            LibraryLWJGLOpenAL.alPitchSupported = alPitchSupported;
        }
        return LibraryLWJGLOpenAL.alPitchSupported;
    }
    
    public static String getTitle() {
        return "LWJGL OpenAL";
    }
    
    public static String getDescription() {
        return "The LWJGL binding of OpenAL.  For more information, see http://www.lwjgl.org";
    }
    
    public String getClassName() {
        return "LibraryLWJGLOpenAL";
    }
    
    static {
        LibraryLWJGLOpenAL.alPitchSupported = true;
    }
}
