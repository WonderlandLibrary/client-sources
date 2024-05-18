package paulscode.sound.libraries;

import javax.sound.sampled.*;
import java.util.*;
import paulscode.sound.*;

public class SourceJavaSound extends Source
{
    protected ChannelJavaSound channelJavaSound;
    public ListenerData listener;
    private float pan;
    
    public SourceJavaSound(final ListenerData listener, final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final SoundBuffer soundBuffer, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b4) {
        super(b, b2, b3, s, filenameURL, soundBuffer, n, n2, n3, n4, n5, b4);
        this.channelJavaSound = (ChannelJavaSound)this.channel;
        this.pan = 0.0f;
        this.libraryType = LibraryJavaSound.class;
        this.listener = listener;
        this.positionChanged();
    }
    
    public SourceJavaSound(final ListenerData listener, final Source source, final SoundBuffer soundBuffer) {
        super(source, soundBuffer);
        this.channelJavaSound = (ChannelJavaSound)this.channel;
        this.pan = 0.0f;
        this.libraryType = LibraryJavaSound.class;
        this.listener = listener;
        this.positionChanged();
    }
    
    public SourceJavaSound(final ListenerData listener, final AudioFormat audioFormat, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        super(audioFormat, b, s, n, n2, n3, n4, n5);
        this.channelJavaSound = (ChannelJavaSound)this.channel;
        this.pan = 0.0f;
        this.libraryType = LibraryJavaSound.class;
        this.listener = listener;
        this.positionChanged();
    }
    
    public void cleanup() {
        super.cleanup();
    }
    
    public void changeSource(final boolean b, final boolean b2, final boolean looping, final String s, final FilenameURL filenameURL, final SoundBuffer soundBuffer, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b3) {
        super.changeSource(b, b2, looping, s, filenameURL, soundBuffer, n, n2, n3, n4, n5, b3);
        if (this.channelJavaSound != null) {
            this.channelJavaSound.setLooping(looping);
        }
        this.positionChanged();
    }
    
    public void listenerMoved() {
        this.positionChanged();
    }
    
    public void setVelocity(final float n, final float n2, final float n3) {
        super.setVelocity(n, n2, n3);
        this.positionChanged();
    }
    
    public void setPosition(final float n, final float n2, final float n3) {
        super.setPosition(n, n2, n3);
        this.positionChanged();
    }
    
    public void positionChanged() {
        this.calculateGain();
        this.calculatePan();
        this.calculatePitch();
    }
    
    public void setPitch(final float pitch) {
        super.setPitch(pitch);
        this.calculatePitch();
    }
    
    public void setAttenuation(final int attenuation) {
        super.setAttenuation(attenuation);
        this.calculateGain();
    }
    
    public void setDistOrRoll(final float distOrRoll) {
        super.setDistOrRoll(distOrRoll);
        this.calculateGain();
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
        final boolean stopped = this.stopped();
        super.play(channel);
        this.channelJavaSound = (ChannelJavaSound)this.channel;
        if (b) {
            if (this.channelJavaSound != null) {
                this.channelJavaSound.setLooping(this.toLoop);
            }
            if (!this.toStream) {
                if (this.soundBuffer == null) {
                    this.errorMessage("No sound buffer to play");
                    return;
                }
                this.channelJavaSound.attachBuffer(this.soundBuffer);
            }
        }
        this.positionChanged();
        if (stopped || !this.playing()) {
            if (this.toStream && !paused) {
                this.preLoad = true;
            }
            this.channel.play();
        }
    }
    
    public boolean preLoad() {
        if (this.codec == null) {
            return false;
        }
        boolean b = false;
        synchronized (this.soundSequenceLock) {
            if (this.nextBuffers == null || this.nextBuffers.isEmpty()) {
                b = true;
            }
        }
        final LinkedList<byte[]> list = new LinkedList<byte[]>();
        if (this.nextCodec != null && !b) {
            this.codec = this.nextCodec;
            this.nextCodec = null;
            synchronized (this.soundSequenceLock) {
                while (!this.nextBuffers.isEmpty()) {
                    this.soundBuffer = this.nextBuffers.remove(0);
                    if (this.soundBuffer != null && this.soundBuffer.audioData != null) {
                        list.add(this.soundBuffer.audioData);
                    }
                }
            }
        }
        else {
            this.codec.initialize(this.filenameURL.getURL());
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
            this.channelJavaSound.resetStream(this.codec.getAudioFormat());
        }
        this.positionChanged();
        this.channel.preLoadBuffers(list);
        this.preLoad = false;
        return true;
    }
    
    public void calculateGain() {
        final float n = this.position.x - this.listener.position.x;
        final float n2 = this.position.y - this.listener.position.y;
        final float n3 = this.position.z - this.listener.position.z;
        this.distanceFromListener = (float)Math.sqrt(n * n + n2 * n2 + n3 * n3);
        switch (this.attModel) {
            case 2: {
                if (this.distanceFromListener <= 0.0f) {
                    this.gain = 1.0f;
                    break;
                }
                if (this.distanceFromListener >= this.distOrRoll) {
                    this.gain = 0.0f;
                    break;
                }
                this.gain = 1.0f - this.distanceFromListener / this.distOrRoll;
                break;
            }
            case 1: {
                if (this.distanceFromListener <= 0.0f) {
                    this.gain = 1.0f;
                    break;
                }
                float n4 = this.distOrRoll * this.distanceFromListener * this.distanceFromListener * 5.0E-4f;
                if (n4 < 0.0f) {
                    n4 = 0.0f;
                }
                this.gain = 1.0f / (1.0f + n4);
                break;
            }
            default: {
                this.gain = 1.0f;
                break;
            }
        }
        if (this.gain > 1.0f) {
            this.gain = 1.0f;
        }
        if (this.gain < 0.0f) {
            this.gain = 0.0f;
        }
        this.gain *= this.sourceVolume * SoundSystemConfig.getMasterGain() * Math.abs(this.fadeOutGain) * this.fadeInGain;
        if (this.channel != null && this.channel.attachedSource == this && this.channelJavaSound != null) {
            this.channelJavaSound.setGain(this.gain);
        }
    }
    
    public void calculatePan() {
        final Vector3D cross = this.listener.up.cross(this.listener.lookAt);
        cross.normalize();
        this.pan = (float)(-Math.sin((float)Math.atan2(this.position.dot(this.position.subtract(this.listener.position), cross), this.position.dot(this.position.subtract(this.listener.position), this.listener.lookAt))));
        if (this.channel != null && this.channel.attachedSource == this && this.channelJavaSound != null) {
            if (this.attModel == 0) {
                this.channelJavaSound.setPan(0.0f);
            }
            else {
                this.channelJavaSound.setPan(this.pan);
            }
        }
    }
    
    public void calculatePitch() {
        if (this.channel != null && this.channel.attachedSource == this && this.channelJavaSound != null) {
            if (SoundSystemConfig.getDopplerFactor() == 0.0f) {
                this.channelJavaSound.setPitch(this.pitch);
            }
            else {
                final float n = 343.3f;
                final Vector3D velocity = this.velocity;
                final Vector3D velocity2 = this.listener.velocity;
                final float dopplerVelocity = SoundSystemConfig.getDopplerVelocity();
                final float dopplerFactor = SoundSystemConfig.getDopplerFactor();
                final Vector3D subtract = this.listener.position.subtract(this.position);
                float pitch = this.pitch * (n * dopplerVelocity - dopplerFactor * this.min(subtract.dot(velocity2) / subtract.length(), n / dopplerFactor)) / (n * dopplerVelocity - dopplerFactor * this.min(subtract.dot(velocity) / subtract.length(), n / dopplerFactor));
                if (pitch < 0.5f) {
                    pitch = 0.5f;
                }
                else if (pitch > 2.0f) {
                    pitch = 2.0f;
                }
                this.channelJavaSound.setPitch(pitch);
            }
        }
    }
    
    public float min(final float n, final float n2) {
        if (n < n2) {
            return n;
        }
        return n2;
    }
}
