package paulscode.sound.libraries;

import paulscode.sound.*;
import java.util.*;
import javax.sound.sampled.*;

public class ChannelJavaSound extends Channel
{
    public Clip clip;
    SoundBuffer soundBuffer;
    public SourceDataLine sourceDataLine;
    private List streamBuffers;
    private int processed;
    private Mixer myMixer;
    private AudioFormat myFormat;
    private FloatControl gainControl;
    private FloatControl panControl;
    private FloatControl sampleRateControl;
    private float initialGain;
    private float initialSampleRate;
    private boolean toLoop;
    
    public ChannelJavaSound(final int n, final Mixer myMixer) {
        super(n);
        this.clip = null;
        this.sourceDataLine = null;
        this.processed = 0;
        this.myMixer = null;
        this.myFormat = null;
        this.gainControl = null;
        this.panControl = null;
        this.sampleRateControl = null;
        this.initialGain = 0.0f;
        this.initialSampleRate = 0.0f;
        this.toLoop = false;
        this.libraryType = LibraryJavaSound.class;
        this.myMixer = myMixer;
        this.clip = null;
        this.sourceDataLine = null;
        this.streamBuffers = new LinkedList();
    }
    
    public void cleanup() {
        if (this.streamBuffers != null) {
            while (!this.streamBuffers.isEmpty()) {
                this.streamBuffers.remove(0).cleanup();
            }
            this.streamBuffers.clear();
        }
        this.clip = null;
        this.soundBuffer = null;
        this.sourceDataLine = null;
        this.streamBuffers.clear();
        this.myMixer = null;
        this.myFormat = null;
        this.streamBuffers = null;
        super.cleanup();
    }
    
    public void newMixer(final Mixer myMixer) {
        if (this.myMixer != myMixer) {
            try {
                if (this.clip != null) {
                    this.clip.close();
                }
                else if (this.sourceDataLine != null) {
                    this.sourceDataLine.close();
                }
            }
            catch (SecurityException ex) {}
            this.myMixer = myMixer;
            if (this.attachedSource != null) {
                if (this.channelType == 0 && this.soundBuffer != null) {
                    this.attachBuffer(this.soundBuffer);
                }
                else if (this.myFormat != null) {
                    this.resetStream(this.myFormat);
                }
            }
        }
    }
    
    public boolean attachBuffer(final SoundBuffer soundBuffer) {
        if (this.errorCheck(this.channelType != 0, "Buffers may only be attached to non-streaming sources")) {
            return false;
        }
        if (this.errorCheck(this.myMixer == null, "Mixer null in method 'attachBuffer'")) {
            return false;
        }
        if (this.errorCheck(soundBuffer == null, "Buffer null in method 'attachBuffer'")) {
            return false;
        }
        if (this.errorCheck(soundBuffer.audioData == null, "Buffer missing audio data in method 'attachBuffer'")) {
            return false;
        }
        if (this.errorCheck(soundBuffer.audioFormat == null, "Buffer missing format information in method 'attachBuffer'")) {
            return false;
        }
        final DataLine.Info info = new DataLine.Info(Clip.class, soundBuffer.audioFormat);
        if (this.errorCheck(!AudioSystem.isLineSupported(info), "Line not supported in method 'attachBuffer'")) {
            return false;
        }
        Clip clip;
        try {
            clip = (Clip)this.myMixer.getLine(info);
        }
        catch (Exception ex) {
            this.errorMessage("Unable to create clip in method 'attachBuffer'");
            this.printStackTrace(ex);
            return false;
        }
        if (this.errorCheck(clip == null, "New clip null in method 'attachBuffer'")) {
            return false;
        }
        if (this.clip != null) {
            this.clip.stop();
            this.clip.flush();
            this.clip.close();
        }
        this.clip = clip;
        this.soundBuffer = soundBuffer;
        this.myFormat = soundBuffer.audioFormat;
        try {
            this.clip.open(this.myFormat, soundBuffer.audioData, 0, soundBuffer.audioData.length);
        }
        catch (Exception ex2) {
            this.errorMessage("Unable to attach buffer to clip in method 'attachBuffer'");
            this.printStackTrace(ex2);
            return false;
        }
        this.resetControls();
        return true;
    }
    
    public void setAudioFormat(final AudioFormat audioFormat) {
        this.resetStream(audioFormat);
        if (this.attachedSource != null && this.attachedSource.rawDataStream && this.attachedSource.active() && this.sourceDataLine != null) {
            this.sourceDataLine.start();
        }
    }
    
    public boolean resetStream(final AudioFormat myFormat) {
        if (this.errorCheck(this.myMixer == null, "Mixer null in method 'resetStream'")) {
            return false;
        }
        if (this.errorCheck(myFormat == null, "AudioFormat null in method 'resetStream'")) {
            return false;
        }
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, myFormat);
        if (this.errorCheck(!AudioSystem.isLineSupported(info), "Line not supported in method 'resetStream'")) {
            return false;
        }
        SourceDataLine sourceDataLine;
        try {
            sourceDataLine = (SourceDataLine)this.myMixer.getLine(info);
        }
        catch (Exception ex) {
            this.errorMessage("Unable to create a SourceDataLine in method 'resetStream'");
            this.printStackTrace(ex);
            return false;
        }
        if (this.errorCheck(sourceDataLine == null, "New SourceDataLine null in method 'resetStream'")) {
            return false;
        }
        this.streamBuffers.clear();
        this.processed = 0;
        if (this.sourceDataLine != null) {
            this.sourceDataLine.stop();
            this.sourceDataLine.flush();
            this.sourceDataLine.close();
        }
        this.sourceDataLine = sourceDataLine;
        this.myFormat = myFormat;
        try {
            this.sourceDataLine.open(this.myFormat);
        }
        catch (Exception ex2) {
            this.errorMessage("Unable to open the new SourceDataLine in method 'resetStream'");
            this.printStackTrace(ex2);
            return false;
        }
        this.resetControls();
        return true;
    }
    
    private void resetControls() {
        switch (this.channelType) {
            case 0: {
                try {
                    if (!this.clip.isControlSupported(FloatControl.Type.PAN)) {
                        this.panControl = null;
                    }
                    else {
                        this.panControl = (FloatControl)this.clip.getControl(FloatControl.Type.PAN);
                    }
                }
                catch (IllegalArgumentException ex) {
                    this.panControl = null;
                }
                try {
                    if (!this.clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                        this.gainControl = null;
                        this.initialGain = 0.0f;
                    }
                    else {
                        this.gainControl = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
                        this.initialGain = this.gainControl.getValue();
                    }
                }
                catch (IllegalArgumentException ex2) {
                    this.gainControl = null;
                    this.initialGain = 0.0f;
                }
                try {
                    if (!this.clip.isControlSupported(FloatControl.Type.SAMPLE_RATE)) {
                        this.sampleRateControl = null;
                        this.initialSampleRate = 0.0f;
                    }
                    else {
                        this.sampleRateControl = (FloatControl)this.clip.getControl(FloatControl.Type.SAMPLE_RATE);
                        this.initialSampleRate = this.sampleRateControl.getValue();
                    }
                }
                catch (IllegalArgumentException ex3) {
                    this.sampleRateControl = null;
                    this.initialSampleRate = 0.0f;
                }
                break;
            }
            case 1: {
                try {
                    if (!this.sourceDataLine.isControlSupported(FloatControl.Type.PAN)) {
                        this.panControl = null;
                    }
                    else {
                        this.panControl = (FloatControl)this.sourceDataLine.getControl(FloatControl.Type.PAN);
                    }
                }
                catch (IllegalArgumentException ex4) {
                    this.panControl = null;
                }
                try {
                    if (!this.sourceDataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                        this.gainControl = null;
                        this.initialGain = 0.0f;
                    }
                    else {
                        this.gainControl = (FloatControl)this.sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
                        this.initialGain = this.gainControl.getValue();
                    }
                }
                catch (IllegalArgumentException ex5) {
                    this.gainControl = null;
                    this.initialGain = 0.0f;
                }
                try {
                    if (!this.sourceDataLine.isControlSupported(FloatControl.Type.SAMPLE_RATE)) {
                        this.sampleRateControl = null;
                        this.initialSampleRate = 0.0f;
                    }
                    else {
                        this.sampleRateControl = (FloatControl)this.sourceDataLine.getControl(FloatControl.Type.SAMPLE_RATE);
                        this.initialSampleRate = this.sampleRateControl.getValue();
                    }
                }
                catch (IllegalArgumentException ex6) {
                    this.sampleRateControl = null;
                    this.initialSampleRate = 0.0f;
                }
                break;
            }
            default: {
                this.errorMessage("Unrecognized channel type in method 'resetControls'");
                this.panControl = null;
                this.gainControl = null;
                this.sampleRateControl = null;
                break;
            }
        }
    }
    
    public void setLooping(final boolean toLoop) {
        this.toLoop = toLoop;
    }
    
    public void setPan(final float n) {
        if (this.panControl == null) {
            return;
        }
        float value = n;
        if (value < -1.0f) {
            value = -1.0f;
        }
        if (value > 1.0f) {
            value = 1.0f;
        }
        this.panControl.setValue(value);
    }
    
    public void setGain(final float n) {
        if (this.gainControl == null) {
            return;
        }
        float n2 = n;
        if (n2 < 0.0f) {
            n2 = 0.0f;
        }
        if (n2 > 1.0f) {
            n2 = 1.0f;
        }
        final double n3 = this.gainControl.getMinimum();
        final double n4 = 0.5 * this.initialGain - n3;
        final double n5 = Math.log(10.0) / 20.0;
        this.gainControl.setValue((float)(n3 + 1.0 / n5 * Math.log(1.0 + (Math.exp(n5 * n4) - 1.0) * n2)));
    }
    
    public void setPitch(final float n) {
        if (this.sampleRateControl == null) {
            return;
        }
        float n2 = n;
        if (n2 < 0.5f) {
            n2 = 0.5f;
        }
        if (n2 > 2.0f) {
            n2 = 2.0f;
        }
        this.sampleRateControl.setValue(n2 * this.initialSampleRate);
    }
    
    public boolean preLoadBuffers(final LinkedList list) {
        if (this.errorCheck(this.channelType != 1, "Buffers may only be queued for streaming sources.")) {
            return false;
        }
        if (this.errorCheck(this.sourceDataLine == null, "SourceDataLine null in method 'preLoadBuffers'.")) {
            return false;
        }
        this.sourceDataLine.start();
        if (list.isEmpty()) {
            return true;
        }
        final byte[] array = list.remove(0);
        if (this.errorCheck(array == null, "Missing sound-bytes in method 'preLoadBuffers'.")) {
            return false;
        }
        while (!list.isEmpty()) {
            this.streamBuffers.add(new SoundBuffer(list.remove(0), this.myFormat));
        }
        this.sourceDataLine.write(array, 0, array.length);
        this.processed = 0;
        return true;
    }
    
    public boolean queueBuffer(final byte[] array) {
        if (this.errorCheck(this.channelType != 1, "Buffers may only be queued for streaming sources.")) {
            return false;
        }
        if (this.errorCheck(this.sourceDataLine == null, "SourceDataLine null in method 'queueBuffer'.")) {
            return false;
        }
        if (this.errorCheck(this.myFormat == null, "AudioFormat null in method 'queueBuffer'")) {
            return false;
        }
        this.streamBuffers.add(new SoundBuffer(array, this.myFormat));
        this.processBuffer();
        this.processed = 0;
        return true;
    }
    
    public boolean processBuffer() {
        if (this.errorCheck(this.channelType != 1, "Buffers are only processed for streaming sources.")) {
            return false;
        }
        if (this.errorCheck(this.sourceDataLine == null, "SourceDataLine null in method 'processBuffer'.")) {
            return false;
        }
        if (this.streamBuffers == null || this.streamBuffers.isEmpty()) {
            return false;
        }
        final SoundBuffer soundBuffer = this.streamBuffers.remove(0);
        this.sourceDataLine.write(soundBuffer.audioData, 0, soundBuffer.audioData.length);
        if (!this.sourceDataLine.isActive()) {
            this.sourceDataLine.start();
        }
        soundBuffer.cleanup();
        return true;
    }
    
    public int feedRawAudioData(final byte[] array) {
        if (this.errorCheck(this.channelType != 1, "Raw audio data can only be processed by streaming sources.")) {
            return -1;
        }
        if (this.errorCheck(this.streamBuffers == null, "StreamBuffers queue null in method 'feedRawAudioData'.")) {
            return -1;
        }
        this.streamBuffers.add(new SoundBuffer(array, this.myFormat));
        return this.buffersProcessed();
    }
    
    public int buffersProcessed() {
        this.processed = 0;
        if (this.errorCheck(this.channelType != 1, "Buffers may only be queued for streaming sources.")) {
            if (this.streamBuffers != null) {
                this.streamBuffers.clear();
            }
            return 0;
        }
        if (this.sourceDataLine == null) {
            if (this.streamBuffers != null) {
                this.streamBuffers.clear();
            }
            return 0;
        }
        if (this.sourceDataLine.available() > 0) {
            this.processed = 1;
        }
        return this.processed;
    }
    
    public void flush() {
        if (this.channelType != 1) {
            return;
        }
        if (this.errorCheck(this.sourceDataLine == null, "SourceDataLine null in method 'flush'.")) {
            return;
        }
        this.sourceDataLine.stop();
        this.sourceDataLine.flush();
        this.sourceDataLine.drain();
        this.streamBuffers.clear();
        this.processed = 0;
    }
    
    public void close() {
        switch (this.channelType) {
            case 0: {
                if (this.clip != null) {
                    this.clip.stop();
                    this.clip.flush();
                    this.clip.close();
                    break;
                }
                break;
            }
            case 1: {
                if (this.sourceDataLine != null) {
                    this.flush();
                    this.sourceDataLine.close();
                    break;
                }
                break;
            }
        }
    }
    
    public void play() {
        switch (this.channelType) {
            case 0: {
                if (this.clip == null) {
                    break;
                }
                if (this.toLoop) {
                    this.clip.stop();
                    this.clip.loop(-1);
                    break;
                }
                this.clip.stop();
                this.clip.start();
                break;
            }
            case 1: {
                if (this.sourceDataLine != null) {
                    this.sourceDataLine.start();
                    break;
                }
                break;
            }
        }
    }
    
    public void pause() {
        switch (this.channelType) {
            case 0: {
                if (this.clip != null) {
                    this.clip.stop();
                    break;
                }
                break;
            }
            case 1: {
                if (this.sourceDataLine != null) {
                    this.sourceDataLine.stop();
                    break;
                }
                break;
            }
        }
    }
    
    public void stop() {
        switch (this.channelType) {
            case 0: {
                if (this.clip != null) {
                    this.clip.stop();
                    this.clip.setFramePosition(0);
                    break;
                }
                break;
            }
            case 1: {
                if (this.sourceDataLine != null) {
                    this.sourceDataLine.stop();
                    break;
                }
                break;
            }
        }
    }
    
    public void rewind() {
        switch (this.channelType) {
            case 0: {
                if (this.clip != null) {
                    final boolean running = this.clip.isRunning();
                    this.clip.stop();
                    this.clip.setFramePosition(0);
                    if (running) {
                        if (this.toLoop) {
                            this.clip.loop(-1);
                        }
                        else {
                            this.clip.start();
                        }
                    }
                    break;
                }
                break;
            }
        }
    }
    
    public float millisecondsPlayed() {
        switch (this.channelType) {
            case 0: {
                if (this.clip == null) {
                    return -1.0f;
                }
                return this.clip.getMicrosecondPosition() / 1000.0f;
            }
            case 1: {
                if (this.sourceDataLine == null) {
                    return -1.0f;
                }
                return this.sourceDataLine.getMicrosecondPosition() / 1000.0f;
            }
            default: {
                return -1.0f;
            }
        }
    }
    
    public boolean playing() {
        switch (this.channelType) {
            case 0: {
                return this.clip != null && this.clip.isActive();
            }
            case 1: {
                return this.sourceDataLine != null && this.sourceDataLine.isActive();
            }
            default: {
                return false;
            }
        }
    }
}
