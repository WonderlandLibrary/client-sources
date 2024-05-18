package paulscode.sound.libraries;

import java.nio.*;
import javax.sound.sampled.*;
import org.lwjgl.openal.*;
import org.lwjgl.*;
import paulscode.sound.*;
import java.util.*;

public class SourceLWJGLOpenAL extends Source
{
    private ChannelLWJGLOpenAL channelOpenAL;
    private IntBuffer myBuffer;
    private FloatBuffer listenerPosition;
    private FloatBuffer sourcePosition;
    private FloatBuffer sourceVelocity;
    
    public SourceLWJGLOpenAL(final FloatBuffer listenerPosition, final IntBuffer myBuffer, final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final SoundBuffer soundBuffer, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b4) {
        super(b, b2, b3, s, filenameURL, soundBuffer, n, n2, n3, n4, n5, b4);
        this.channelOpenAL = (ChannelLWJGLOpenAL)this.channel;
        if (this.codec != null) {
            this.codec.reverseByteOrder(true);
        }
        this.listenerPosition = listenerPosition;
        this.myBuffer = myBuffer;
        this.libraryType = LibraryLWJGLOpenAL.class;
        this.pitch = 1.0f;
        this.resetALInformation();
    }
    
    public SourceLWJGLOpenAL(final FloatBuffer listenerPosition, final IntBuffer myBuffer, final Source source, final SoundBuffer soundBuffer) {
        super(source, soundBuffer);
        this.channelOpenAL = (ChannelLWJGLOpenAL)this.channel;
        if (this.codec != null) {
            this.codec.reverseByteOrder(true);
        }
        this.listenerPosition = listenerPosition;
        this.myBuffer = myBuffer;
        this.libraryType = LibraryLWJGLOpenAL.class;
        this.pitch = 1.0f;
        this.resetALInformation();
    }
    
    public SourceLWJGLOpenAL(final FloatBuffer listenerPosition, final AudioFormat audioFormat, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        super(audioFormat, b, s, n, n2, n3, n4, n5);
        this.channelOpenAL = (ChannelLWJGLOpenAL)this.channel;
        this.listenerPosition = listenerPosition;
        this.libraryType = LibraryLWJGLOpenAL.class;
        this.pitch = 1.0f;
        this.resetALInformation();
    }
    
    public void cleanup() {
        super.cleanup();
    }
    
    public void changeSource(final FloatBuffer listenerPosition, final IntBuffer myBuffer, final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final SoundBuffer soundBuffer, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b4) {
        super.changeSource(b, b2, b3, s, filenameURL, soundBuffer, n, n2, n3, n4, n5, b4);
        this.listenerPosition = listenerPosition;
        this.myBuffer = myBuffer;
        this.pitch = 1.0f;
        this.resetALInformation();
    }
    
    public boolean incrementSoundSequence() {
        if (!this.toStream) {
            this.errorMessage("Method 'incrementSoundSequence' may only be used for streaming sources.");
            return false;
        }
        synchronized (this.soundSequenceLock) {
            if (this.soundSequenceQueue != null && this.soundSequenceQueue.size() > 0) {
                this.filenameURL = this.soundSequenceQueue.remove(0);
                if (this.codec != null) {
                    this.codec.cleanup();
                }
                this.codec = SoundSystemConfig.getCodec(this.filenameURL.getFilename());
                if (this.codec != null) {
                    this.codec.reverseByteOrder(true);
                    if (this.codec.getAudioFormat() == null) {
                        this.codec.initialize(this.filenameURL.getURL());
                    }
                    final AudioFormat audioFormat = this.codec.getAudioFormat();
                    if (audioFormat == null) {
                        this.errorMessage("Audio Format null in method 'incrementSoundSequence'");
                        return false;
                    }
                    int n;
                    if (audioFormat.getChannels() == 1) {
                        if (audioFormat.getSampleSizeInBits() == 8) {
                            n = 4352;
                        }
                        else {
                            if (audioFormat.getSampleSizeInBits() != 16) {
                                this.errorMessage("Illegal sample size in method 'incrementSoundSequence'");
                                return false;
                            }
                            n = 4353;
                        }
                    }
                    else {
                        if (audioFormat.getChannels() != 2) {
                            this.errorMessage("Audio data neither mono nor stereo in method 'incrementSoundSequence'");
                            return false;
                        }
                        if (audioFormat.getSampleSizeInBits() == 8) {
                            n = 4354;
                        }
                        else {
                            if (audioFormat.getSampleSizeInBits() != 16) {
                                this.errorMessage("Illegal sample size in method 'incrementSoundSequence'");
                                return false;
                            }
                            n = 4355;
                        }
                    }
                    this.channelOpenAL.setFormat(n, (int)audioFormat.getSampleRate());
                    this.preLoad = true;
                }
                return true;
            }
        }
        return false;
    }
    
    public void listenerMoved() {
        this.positionChanged();
    }
    
    public void setPosition(final float n, final float n2, final float n3) {
        super.setPosition(n, n2, n3);
        if (this.sourcePosition == null) {
            this.resetALInformation();
        }
        else {
            this.positionChanged();
        }
        this.sourcePosition.put(0, n);
        this.sourcePosition.put(1, n2);
        this.sourcePosition.put(2, n3);
        if (this.channel != null && this.channel.attachedSource == this && this.channelOpenAL != null && this.channelOpenAL.ALSource != null) {
            AL10.alSource(this.channelOpenAL.ALSource.get(0), 4100, this.sourcePosition);
            this.checkALError();
        }
    }
    
    public void positionChanged() {
        this.calculateDistance();
        this.calculateGain();
        if (this.channel != null && this.channel.attachedSource == this && this.channelOpenAL != null && this.channelOpenAL.ALSource != null) {
            AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4106, this.gain * this.sourceVolume * Math.abs(this.fadeOutGain) * this.fadeInGain);
            this.checkALError();
        }
        this.checkPitch();
    }
    
    private void checkPitch() {
        if (this.channel != null && this.channel.attachedSource == this && LibraryLWJGLOpenAL.alPitchSupported() && this.channelOpenAL != null && this.channelOpenAL.ALSource != null) {
            AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4099, this.pitch);
            this.checkALError();
        }
    }
    
    public void setLooping(final boolean looping) {
        super.setLooping(looping);
        if (this.channel != null && this.channel.attachedSource == this && this.channelOpenAL != null && this.channelOpenAL.ALSource != null) {
            if (looping) {
                AL10.alSourcei(this.channelOpenAL.ALSource.get(0), 4103, 1);
            }
            else {
                AL10.alSourcei(this.channelOpenAL.ALSource.get(0), 4103, 0);
            }
            this.checkALError();
        }
    }
    
    public void setAttenuation(final int attenuation) {
        super.setAttenuation(attenuation);
        if (this.channel != null && this.channel.attachedSource == this && this.channelOpenAL != null && this.channelOpenAL.ALSource != null) {
            if (attenuation == 1) {
                AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4129, this.distOrRoll);
            }
            else {
                AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4129, 0.0f);
            }
            this.checkALError();
        }
    }
    
    public void setDistOrRoll(final float distOrRoll) {
        super.setDistOrRoll(distOrRoll);
        if (this.channel != null && this.channel.attachedSource == this && this.channelOpenAL != null && this.channelOpenAL.ALSource != null) {
            if (this.attModel == 1) {
                AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4129, distOrRoll);
            }
            else {
                AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4129, 0.0f);
            }
            this.checkALError();
        }
    }
    
    public void setVelocity(final float n, final float n2, final float n3) {
        super.setVelocity(n, n2, n3);
        (this.sourceVelocity = BufferUtils.createFloatBuffer(3).put(new float[] { n, n2, n3 })).flip();
        if (this.channel != null && this.channel.attachedSource == this && this.channelOpenAL != null && this.channelOpenAL.ALSource != null) {
            AL10.alSource(this.channelOpenAL.ALSource.get(0), 4102, this.sourceVelocity);
            this.checkALError();
        }
    }
    
    public void setPitch(final float pitch) {
        super.setPitch(pitch);
        this.checkPitch();
    }
    
    public void play(final Channel channel) {
        if (!this.active()) {
            if (this.toLoop) {
                this.toPlay = true;
            }
            return;
        }
        if (channel == null) {
            this.errorMessage("Unable to play source, because channel was null");
            return;
        }
        boolean b = this.channel != channel;
        if (this.channel != null && this.channel.attachedSource != this) {
            b = true;
        }
        final boolean paused = this.paused();
        super.play(channel);
        this.channelOpenAL = (ChannelLWJGLOpenAL)this.channel;
        if (b) {
            this.setPosition(this.position.x, this.position.y, this.position.z);
            this.checkPitch();
            if (this.channelOpenAL != null && this.channelOpenAL.ALSource != null) {
                if (LibraryLWJGLOpenAL.alPitchSupported()) {
                    AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4099, this.pitch);
                    this.checkALError();
                }
                AL10.alSource(this.channelOpenAL.ALSource.get(0), 4100, this.sourcePosition);
                this.checkALError();
                AL10.alSource(this.channelOpenAL.ALSource.get(0), 4102, this.sourceVelocity);
                this.checkALError();
                if (this.attModel == 1) {
                    AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4129, this.distOrRoll);
                }
                else {
                    AL10.alSourcef(this.channelOpenAL.ALSource.get(0), 4129, 0.0f);
                }
                this.checkALError();
                if (this.toLoop && !this.toStream) {
                    AL10.alSourcei(this.channelOpenAL.ALSource.get(0), 4103, 1);
                }
                else {
                    AL10.alSourcei(this.channelOpenAL.ALSource.get(0), 4103, 0);
                }
                this.checkALError();
            }
            if (!this.toStream) {
                if (this.myBuffer == null) {
                    this.errorMessage("No sound buffer to play");
                    return;
                }
                this.channelOpenAL.attachBuffer(this.myBuffer);
            }
        }
        if (!this.playing()) {
            if (this.toStream && !paused) {
                if (this.codec == null) {
                    this.errorMessage("Decoder null in method 'play'");
                    return;
                }
                if (this.codec.getAudioFormat() == null) {
                    this.codec.initialize(this.filenameURL.getURL());
                }
                final AudioFormat audioFormat = this.codec.getAudioFormat();
                if (audioFormat == null) {
                    this.errorMessage("Audio Format null in method 'play'");
                    return;
                }
                int n;
                if (audioFormat.getChannels() == 1) {
                    if (audioFormat.getSampleSizeInBits() == 8) {
                        n = 4352;
                    }
                    else {
                        if (audioFormat.getSampleSizeInBits() != 16) {
                            this.errorMessage("Illegal sample size in method 'play'");
                            return;
                        }
                        n = 4353;
                    }
                }
                else {
                    if (audioFormat.getChannels() != 2) {
                        this.errorMessage("Audio data neither mono nor stereo in method 'play'");
                        return;
                    }
                    if (audioFormat.getSampleSizeInBits() == 8) {
                        n = 4354;
                    }
                    else {
                        if (audioFormat.getSampleSizeInBits() != 16) {
                            this.errorMessage("Illegal sample size in method 'play'");
                            return;
                        }
                        n = 4355;
                    }
                }
                this.channelOpenAL.setFormat(n, (int)audioFormat.getSampleRate());
                this.preLoad = true;
            }
            this.channel.play();
            if (this.pitch != 1.0f) {
                this.checkPitch();
            }
        }
    }
    
    public boolean preLoad() {
        if (this.codec == null) {
            return false;
        }
        this.codec.initialize(this.filenameURL.getURL());
        final LinkedList<byte[]> list = new LinkedList<byte[]>();
        for (int i = 0; i < SoundSystemConfig.getNumberStreamingBuffers(); ++i) {
            this.soundBuffer = this.codec.read();
            if (this.soundBuffer == null) {
                break;
            }
            if (this.soundBuffer.audioData == null) {
                break;
            }
            list.add(this.soundBuffer.audioData);
        }
        this.positionChanged();
        this.channel.preLoadBuffers(list);
        this.preLoad = false;
        return true;
    }
    
    private void resetALInformation() {
        this.sourcePosition = BufferUtils.createFloatBuffer(3).put(new float[] { this.position.x, this.position.y, this.position.z });
        this.sourceVelocity = BufferUtils.createFloatBuffer(3).put(new float[] { this.velocity.x, this.velocity.y, this.velocity.z });
        this.sourcePosition.flip();
        this.sourceVelocity.flip();
        this.positionChanged();
    }
    
    private void calculateDistance() {
        if (this.listenerPosition != null) {
            final double n = this.position.x - this.listenerPosition.get(0);
            final double n2 = this.position.y - this.listenerPosition.get(1);
            final double n3 = this.position.z - this.listenerPosition.get(2);
            this.distanceFromListener = (float)Math.sqrt(n * n + n2 * n2 + n3 * n3);
        }
    }
    
    private void calculateGain() {
        if (this.attModel == 2) {
            if (this.distanceFromListener <= 0.0f) {
                this.gain = 1.0f;
            }
            else if (this.distanceFromListener >= this.distOrRoll) {
                this.gain = 0.0f;
            }
            else {
                this.gain = 1.0f - this.distanceFromListener / this.distOrRoll;
            }
            if (this.gain > 1.0f) {
                this.gain = 1.0f;
            }
            if (this.gain < 0.0f) {
                this.gain = 0.0f;
            }
        }
        else {
            this.gain = 1.0f;
        }
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
