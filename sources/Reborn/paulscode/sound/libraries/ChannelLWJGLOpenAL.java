package paulscode.sound.libraries;

import paulscode.sound.*;
import org.lwjgl.openal.*;
import javax.sound.sampled.*;
import java.util.*;
import org.lwjgl.*;
import java.nio.*;

public class ChannelLWJGLOpenAL extends Channel
{
    public IntBuffer ALSource;
    public int ALformat;
    public int sampleRate;
    public float millisPreviouslyPlayed;
    
    public ChannelLWJGLOpenAL(final int n, final IntBuffer alSource) {
        super(n);
        this.millisPreviouslyPlayed = 0.0f;
        this.libraryType = LibraryLWJGLOpenAL.class;
        this.ALSource = alSource;
    }
    
    public void cleanup() {
        if (this.ALSource != null) {
            try {
                AL10.alSourceStop(this.ALSource);
                AL10.alGetError();
            }
            catch (Exception ex) {}
            try {
                AL10.alDeleteSources(this.ALSource);
                AL10.alGetError();
            }
            catch (Exception ex2) {}
            this.ALSource.clear();
        }
        this.ALSource = null;
        super.cleanup();
    }
    
    public boolean attachBuffer(final IntBuffer intBuffer) {
        if (this.errorCheck(this.channelType != 0, "Sound buffers may only be attached to normal sources.")) {
            return false;
        }
        AL10.alSourcei(this.ALSource.get(0), 4105, intBuffer.get(0));
        if (this.attachedSource != null && this.attachedSource.soundBuffer != null && this.attachedSource.soundBuffer.audioFormat != null) {
            this.setAudioFormat(this.attachedSource.soundBuffer.audioFormat);
        }
        return this.checkALError();
    }
    
    public void setAudioFormat(final AudioFormat audioFormat) {
        int aLformat;
        if (audioFormat.getChannels() == 1) {
            if (audioFormat.getSampleSizeInBits() == 8) {
                aLformat = 4352;
            }
            else {
                if (audioFormat.getSampleSizeInBits() != 16) {
                    this.errorMessage("Illegal sample size in method 'setAudioFormat'");
                    return;
                }
                aLformat = 4353;
            }
        }
        else {
            if (audioFormat.getChannels() != 2) {
                this.errorMessage("Audio data neither mono nor stereo in method 'setAudioFormat'");
                return;
            }
            if (audioFormat.getSampleSizeInBits() == 8) {
                aLformat = 4354;
            }
            else {
                if (audioFormat.getSampleSizeInBits() != 16) {
                    this.errorMessage("Illegal sample size in method 'setAudioFormat'");
                    return;
                }
                aLformat = 4355;
            }
        }
        this.ALformat = aLformat;
        this.sampleRate = (int)audioFormat.getSampleRate();
    }
    
    public void setFormat(final int aLformat, final int sampleRate) {
        this.ALformat = aLformat;
        this.sampleRate = sampleRate;
    }
    
    public boolean preLoadBuffers(final LinkedList list) {
        if (this.errorCheck(this.channelType != 1, "Buffers may only be queued for streaming sources.")) {
            return false;
        }
        if (this.errorCheck(list == null, "Buffer List null in method 'preLoadBuffers'")) {
            return false;
        }
        final boolean playing = this.playing();
        if (playing) {
            AL10.alSourceStop(this.ALSource.get(0));
            this.checkALError();
        }
        final int alGetSourcei = AL10.alGetSourcei(this.ALSource.get(0), 4118);
        if (alGetSourcei > 0) {
            final IntBuffer intBuffer = BufferUtils.createIntBuffer(alGetSourcei);
            AL10.alGenBuffers(intBuffer);
            if (this.errorCheck(this.checkALError(), "Error clearing stream buffers in method 'preLoadBuffers'")) {
                return false;
            }
            AL10.alSourceUnqueueBuffers(this.ALSource.get(0), intBuffer);
            if (this.errorCheck(this.checkALError(), "Error unqueuing stream buffers in method 'preLoadBuffers'")) {
                return false;
            }
        }
        if (playing) {
            AL10.alSourcePlay(this.ALSource.get(0));
            this.checkALError();
        }
        final IntBuffer intBuffer2 = BufferUtils.createIntBuffer(list.size());
        AL10.alGenBuffers(intBuffer2);
        if (this.errorCheck(this.checkALError(), "Error generating stream buffers in method 'preLoadBuffers'")) {
            return false;
        }
        for (int i = 0; i < list.size(); ++i) {
            final ByteBuffer data = (ByteBuffer)BufferUtils.createByteBuffer(list.get(i).length).put(list.get(i)).flip();
            try {
                AL10.alBufferData(intBuffer2.get(i), this.ALformat, data, this.sampleRate);
            }
            catch (Exception ex) {
                this.errorMessage("Error creating buffers in method 'preLoadBuffers'");
                this.printStackTrace(ex);
                return false;
            }
            if (this.errorCheck(this.checkALError(), "Error creating buffers in method 'preLoadBuffers'")) {
                return false;
            }
        }
        try {
            AL10.alSourceQueueBuffers(this.ALSource.get(0), intBuffer2);
        }
        catch (Exception ex2) {
            this.errorMessage("Error queuing buffers in method 'preLoadBuffers'");
            this.printStackTrace(ex2);
            return false;
        }
        if (this.errorCheck(this.checkALError(), "Error queuing buffers in method 'preLoadBuffers'")) {
            return false;
        }
        AL10.alSourcePlay(this.ALSource.get(0));
        return !this.errorCheck(this.checkALError(), "Error playing source in method 'preLoadBuffers'");
    }
    
    public boolean queueBuffer(final byte[] array) {
        if (this.errorCheck(this.channelType != 1, "Buffers may only be queued for streaming sources.")) {
            return false;
        }
        final ByteBuffer data = (ByteBuffer)BufferUtils.createByteBuffer(array.length).put(array).flip();
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        AL10.alSourceUnqueueBuffers(this.ALSource.get(0), intBuffer);
        if (this.checkALError()) {
            return false;
        }
        if (AL10.alIsBuffer(intBuffer.get(0))) {
            this.millisPreviouslyPlayed += this.millisInBuffer(intBuffer.get(0));
        }
        this.checkALError();
        AL10.alBufferData(intBuffer.get(0), this.ALformat, data, this.sampleRate);
        if (this.checkALError()) {
            return false;
        }
        AL10.alSourceQueueBuffers(this.ALSource.get(0), intBuffer);
        return !this.checkALError();
    }
    
    public int feedRawAudioData(final byte[] array) {
        if (this.errorCheck(this.channelType != 1, "Raw audio data can only be fed to streaming sources.")) {
            return -1;
        }
        final ByteBuffer data = (ByteBuffer)BufferUtils.createByteBuffer(array.length).put(array).flip();
        final int alGetSourcei = AL10.alGetSourcei(this.ALSource.get(0), 4118);
        IntBuffer intBuffer;
        if (alGetSourcei > 0) {
            intBuffer = BufferUtils.createIntBuffer(alGetSourcei);
            AL10.alGenBuffers(intBuffer);
            if (this.errorCheck(this.checkALError(), "Error clearing stream buffers in method 'feedRawAudioData'")) {
                return -1;
            }
            AL10.alSourceUnqueueBuffers(this.ALSource.get(0), intBuffer);
            if (this.errorCheck(this.checkALError(), "Error unqueuing stream buffers in method 'feedRawAudioData'")) {
                return -1;
            }
            if (AL10.alIsBuffer(intBuffer.get(0))) {
                this.millisPreviouslyPlayed += this.millisInBuffer(intBuffer.get(0));
            }
            this.checkALError();
        }
        else {
            intBuffer = BufferUtils.createIntBuffer(1);
            AL10.alGenBuffers(intBuffer);
            if (this.errorCheck(this.checkALError(), "Error generating stream buffers in method 'preLoadBuffers'")) {
                return -1;
            }
        }
        AL10.alBufferData(intBuffer.get(0), this.ALformat, data, this.sampleRate);
        if (this.checkALError()) {
            return -1;
        }
        AL10.alSourceQueueBuffers(this.ALSource.get(0), intBuffer);
        if (this.checkALError()) {
            return -1;
        }
        if (this.attachedSource != null && this.attachedSource.channel == this && this.attachedSource.active() && !this.playing()) {
            AL10.alSourcePlay(this.ALSource.get(0));
            this.checkALError();
        }
        return alGetSourcei;
    }
    
    public float millisInBuffer(final int n) {
        return AL10.alGetBufferi(n, 8196) / AL10.alGetBufferi(n, 8195) / (AL10.alGetBufferi(n, 8194) / 8.0f) / this.sampleRate * 1000.0f;
    }
    
    public float millisecondsPlayed() {
        final float n = AL10.alGetSourcei(this.ALSource.get(0), 4134);
        float n2 = 1.0f;
        switch (this.ALformat) {
            case 4352: {
                n2 = 1.0f;
                break;
            }
            case 4353: {
                n2 = 2.0f;
                break;
            }
            case 4354: {
                n2 = 2.0f;
                break;
            }
            case 4355: {
                n2 = 4.0f;
                break;
            }
        }
        float n3 = n / n2 / this.sampleRate * 1000.0f;
        if (this.channelType == 1) {
            n3 += this.millisPreviouslyPlayed;
        }
        return n3;
    }
    
    public int buffersProcessed() {
        if (this.channelType != 1) {
            return 0;
        }
        final int alGetSourcei = AL10.alGetSourcei(this.ALSource.get(0), 4118);
        if (this.checkALError()) {
            return 0;
        }
        return alGetSourcei;
    }
    
    public void flush() {
        if (this.channelType != 1) {
            return;
        }
        int i = AL10.alGetSourcei(this.ALSource.get(0), 4117);
        if (this.checkALError()) {
            return;
        }
        final IntBuffer intBuffer = BufferUtils.createIntBuffer(1);
        while (i > 0) {
            try {
                AL10.alSourceUnqueueBuffers(this.ALSource.get(0), intBuffer);
            }
            catch (Exception ex) {
                return;
            }
            if (this.checkALError()) {
                return;
            }
            --i;
        }
        this.millisPreviouslyPlayed = 0.0f;
    }
    
    public void close() {
        try {
            AL10.alSourceStop(this.ALSource.get(0));
            AL10.alGetError();
        }
        catch (Exception ex) {}
        if (this.channelType == 1) {
            this.flush();
        }
    }
    
    public void play() {
        AL10.alSourcePlay(this.ALSource.get(0));
        this.checkALError();
    }
    
    public void pause() {
        AL10.alSourcePause(this.ALSource.get(0));
        this.checkALError();
    }
    
    public void stop() {
        AL10.alSourceStop(this.ALSource.get(0));
        if (!this.checkALError()) {
            this.millisPreviouslyPlayed = 0.0f;
        }
    }
    
    public void rewind() {
        if (this.channelType == 1) {
            return;
        }
        AL10.alSourceRewind(this.ALSource.get(0));
        if (!this.checkALError()) {
            this.millisPreviouslyPlayed = 0.0f;
        }
    }
    
    public boolean playing() {
        final int alGetSourcei = AL10.alGetSourcei(this.ALSource.get(0), 4112);
        return !this.checkALError() && alGetSourcei == 4114;
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
}
