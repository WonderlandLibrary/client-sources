package paulscode.sound;

import javax.sound.sampled.*;
import java.util.*;

public class Source
{
    protected Class libraryType;
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private SoundSystemLogger logger;
    public boolean rawDataStream;
    public AudioFormat rawDataFormat;
    public boolean temporary;
    public boolean priority;
    public boolean toStream;
    public boolean toLoop;
    public boolean toPlay;
    public String sourcename;
    public FilenameURL filenameURL;
    public Vector3D position;
    public int attModel;
    public float distOrRoll;
    public Vector3D velocity;
    public float gain;
    public float sourceVolume;
    protected float pitch;
    public float distanceFromListener;
    public Channel channel;
    public SoundBuffer soundBuffer;
    private boolean active;
    private boolean stopped;
    private boolean paused;
    protected ICodec codec;
    protected ICodec nextCodec;
    protected LinkedList nextBuffers;
    protected LinkedList soundSequenceQueue;
    protected final Object soundSequenceLock;
    public boolean preLoad;
    protected float fadeOutGain;
    protected float fadeInGain;
    protected long fadeOutMilis;
    protected long fadeInMilis;
    protected long lastFadeCheck;
    
    public Source(final boolean priority, final boolean toStream, final boolean toLoop, final String sourcename, final FilenameURL filenameURL, final SoundBuffer soundBuffer, final float n, final float n2, final float n3, final int attModel, final float distOrRoll, final boolean temporary) {
        this.libraryType = Library.class;
        this.rawDataStream = false;
        this.rawDataFormat = null;
        this.temporary = false;
        this.priority = false;
        this.toStream = false;
        this.toLoop = false;
        this.toPlay = false;
        this.sourcename = "";
        this.filenameURL = null;
        this.attModel = 0;
        this.distOrRoll = 0.0f;
        this.gain = 1.0f;
        this.sourceVolume = 1.0f;
        this.pitch = 1.0f;
        this.distanceFromListener = 0.0f;
        this.channel = null;
        this.soundBuffer = null;
        this.active = true;
        this.stopped = true;
        this.paused = false;
        this.codec = null;
        this.nextCodec = null;
        this.nextBuffers = null;
        this.soundSequenceQueue = null;
        this.soundSequenceLock = new Object();
        this.preLoad = false;
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.logger = SoundSystemConfig.getLogger();
        this.priority = priority;
        this.toStream = toStream;
        this.toLoop = toLoop;
        this.sourcename = sourcename;
        this.filenameURL = filenameURL;
        this.soundBuffer = soundBuffer;
        this.position = new Vector3D(n, n2, n3);
        this.attModel = attModel;
        this.distOrRoll = distOrRoll;
        this.velocity = new Vector3D(0.0f, 0.0f, 0.0f);
        this.temporary = temporary;
        if (toStream && filenameURL != null) {
            this.codec = SoundSystemConfig.getCodec(filenameURL.getFilename());
        }
    }
    
    public Source(final Source source, final SoundBuffer soundBuffer) {
        this.libraryType = Library.class;
        this.rawDataStream = false;
        this.rawDataFormat = null;
        this.temporary = false;
        this.priority = false;
        this.toStream = false;
        this.toLoop = false;
        this.toPlay = false;
        this.sourcename = "";
        this.filenameURL = null;
        this.attModel = 0;
        this.distOrRoll = 0.0f;
        this.gain = 1.0f;
        this.sourceVolume = 1.0f;
        this.pitch = 1.0f;
        this.distanceFromListener = 0.0f;
        this.channel = null;
        this.soundBuffer = null;
        this.active = true;
        this.stopped = true;
        this.paused = false;
        this.codec = null;
        this.nextCodec = null;
        this.nextBuffers = null;
        this.soundSequenceQueue = null;
        this.soundSequenceLock = new Object();
        this.preLoad = false;
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.logger = SoundSystemConfig.getLogger();
        this.priority = source.priority;
        this.toStream = source.toStream;
        this.toLoop = source.toLoop;
        this.sourcename = source.sourcename;
        this.filenameURL = source.filenameURL;
        this.position = source.position.clone();
        this.attModel = source.attModel;
        this.distOrRoll = source.distOrRoll;
        this.velocity = source.velocity.clone();
        this.temporary = source.temporary;
        this.sourceVolume = source.sourceVolume;
        this.rawDataStream = source.rawDataStream;
        this.rawDataFormat = source.rawDataFormat;
        this.soundBuffer = soundBuffer;
        if (this.toStream && this.filenameURL != null) {
            this.codec = SoundSystemConfig.getCodec(this.filenameURL.getFilename());
        }
    }
    
    public Source(final AudioFormat rawDataFormat, final boolean priority, final String sourcename, final float n, final float n2, final float n3, final int attModel, final float distOrRoll) {
        this.libraryType = Library.class;
        this.rawDataStream = false;
        this.rawDataFormat = null;
        this.temporary = false;
        this.priority = false;
        this.toStream = false;
        this.toLoop = false;
        this.toPlay = false;
        this.sourcename = "";
        this.filenameURL = null;
        this.attModel = 0;
        this.distOrRoll = 0.0f;
        this.gain = 1.0f;
        this.sourceVolume = 1.0f;
        this.pitch = 1.0f;
        this.distanceFromListener = 0.0f;
        this.channel = null;
        this.soundBuffer = null;
        this.active = true;
        this.stopped = true;
        this.paused = false;
        this.codec = null;
        this.nextCodec = null;
        this.nextBuffers = null;
        this.soundSequenceQueue = null;
        this.soundSequenceLock = new Object();
        this.preLoad = false;
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.logger = SoundSystemConfig.getLogger();
        this.priority = priority;
        this.toStream = true;
        this.toLoop = false;
        this.sourcename = sourcename;
        this.filenameURL = null;
        this.soundBuffer = null;
        this.position = new Vector3D(n, n2, n3);
        this.attModel = attModel;
        this.distOrRoll = distOrRoll;
        this.velocity = new Vector3D(0.0f, 0.0f, 0.0f);
        this.temporary = false;
        this.rawDataStream = true;
        this.rawDataFormat = rawDataFormat;
    }
    
    public void cleanup() {
        if (this.codec != null) {
            this.codec.cleanup();
        }
        synchronized (this.soundSequenceLock) {
            if (this.soundSequenceQueue != null) {
                this.soundSequenceQueue.clear();
            }
            this.soundSequenceQueue = null;
        }
        this.sourcename = null;
        this.filenameURL = null;
        this.position = null;
        this.soundBuffer = null;
        this.codec = null;
    }
    
    public void queueSound(final FilenameURL filenameURL) {
        if (!this.toStream) {
            this.errorMessage("Method 'queueSound' may only be used for streaming and MIDI sources.");
            return;
        }
        if (filenameURL == null) {
            this.errorMessage("File not specified in method 'queueSound'");
            return;
        }
        synchronized (this.soundSequenceLock) {
            if (this.soundSequenceQueue == null) {
                this.soundSequenceQueue = new LinkedList();
            }
            this.soundSequenceQueue.add(filenameURL);
        }
    }
    
    public void dequeueSound(final String s) {
        if (!this.toStream) {
            this.errorMessage("Method 'dequeueSound' may only be used for streaming and MIDI sources.");
            return;
        }
        if (s == null || s.equals("")) {
            this.errorMessage("Filename not specified in method 'dequeueSound'");
            return;
        }
        synchronized (this.soundSequenceLock) {
            if (this.soundSequenceQueue != null) {
                final ListIterator listIterator = this.soundSequenceQueue.listIterator();
                while (listIterator.hasNext()) {
                    if (listIterator.next().getFilename().equals(s)) {
                        listIterator.remove();
                        break;
                    }
                }
            }
        }
    }
    
    public void fadeOut(final FilenameURL filenameURL, final long fadeOutMilis) {
        if (!this.toStream) {
            this.errorMessage("Method 'fadeOut' may only be used for streaming and MIDI sources.");
            return;
        }
        if (fadeOutMilis < 0L) {
            this.errorMessage("Miliseconds may not be negative in method 'fadeOut'.");
            return;
        }
        this.fadeOutMilis = fadeOutMilis;
        this.fadeInMilis = 0L;
        this.fadeOutGain = 1.0f;
        this.lastFadeCheck = System.currentTimeMillis();
        synchronized (this.soundSequenceLock) {
            if (this.soundSequenceQueue != null) {
                this.soundSequenceQueue.clear();
            }
            if (filenameURL != null) {
                if (this.soundSequenceQueue == null) {
                    this.soundSequenceQueue = new LinkedList();
                }
                this.soundSequenceQueue.add(filenameURL);
            }
        }
    }
    
    public void fadeOutIn(final FilenameURL filenameURL, final long fadeOutMilis, final long fadeInMilis) {
        if (!this.toStream) {
            this.errorMessage("Method 'fadeOutIn' may only be used for streaming and MIDI sources.");
            return;
        }
        if (filenameURL == null) {
            this.errorMessage("Filename/URL not specified in method 'fadeOutIn'.");
            return;
        }
        if (fadeOutMilis < 0L || fadeInMilis < 0L) {
            this.errorMessage("Miliseconds may not be negative in method 'fadeOutIn'.");
            return;
        }
        this.fadeOutMilis = fadeOutMilis;
        this.fadeInMilis = fadeInMilis;
        this.fadeOutGain = 1.0f;
        this.lastFadeCheck = System.currentTimeMillis();
        synchronized (this.soundSequenceLock) {
            if (this.soundSequenceQueue == null) {
                this.soundSequenceQueue = new LinkedList();
            }
            this.soundSequenceQueue.clear();
            this.soundSequenceQueue.add(filenameURL);
        }
    }
    
    public boolean checkFadeOut() {
        if (!this.toStream) {
            return false;
        }
        if (this.fadeOutGain == -1.0f && this.fadeInGain == 1.0f) {
            return false;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final long n = currentTimeMillis - this.lastFadeCheck;
        this.lastFadeCheck = currentTimeMillis;
        if (this.fadeOutGain >= 0.0f) {
            if (this.fadeOutMilis == 0L) {
                this.fadeOutGain = -1.0f;
                this.fadeInGain = 0.0f;
                if (!this.incrementSoundSequence()) {
                    this.stop();
                }
                this.positionChanged();
                this.preLoad = true;
                return false;
            }
            this.fadeOutGain -= n / this.fadeOutMilis;
            if (this.fadeOutGain <= 0.0f) {
                this.fadeOutGain = -1.0f;
                this.fadeInGain = 0.0f;
                if (!this.incrementSoundSequence()) {
                    this.stop();
                }
                this.positionChanged();
                this.preLoad = true;
                return false;
            }
            this.positionChanged();
            return true;
        }
        else {
            if (this.fadeInGain < 1.0f) {
                this.fadeOutGain = -1.0f;
                if (this.fadeInMilis == 0L) {
                    this.fadeOutGain = -1.0f;
                    this.fadeInGain = 1.0f;
                }
                else {
                    this.fadeInGain += n / this.fadeInMilis;
                    if (this.fadeInGain >= 1.0f) {
                        this.fadeOutGain = -1.0f;
                        this.fadeInGain = 1.0f;
                    }
                }
                this.positionChanged();
                return true;
            }
            return false;
        }
    }
    
    public boolean incrementSoundSequence() {
        if (!this.toStream) {
            this.errorMessage("Method 'incrementSoundSequence' may only be used for streaming and MIDI sources.");
            return false;
        }
        synchronized (this.soundSequenceLock) {
            if (this.soundSequenceQueue != null && this.soundSequenceQueue.size() > 0) {
                this.filenameURL = this.soundSequenceQueue.remove(0);
                if (this.codec != null) {
                    this.codec.cleanup();
                }
                this.codec = SoundSystemConfig.getCodec(this.filenameURL.getFilename());
                return true;
            }
        }
        return false;
    }
    
    public boolean readBuffersFromNextSoundInSequence() {
        if (!this.toStream) {
            this.errorMessage("Method 'readBuffersFromNextSoundInSequence' may only be used for streaming sources.");
            return false;
        }
        synchronized (this.soundSequenceLock) {
            if (this.soundSequenceQueue != null && this.soundSequenceQueue.size() > 0) {
                if (this.nextCodec != null) {
                    this.nextCodec.cleanup();
                }
                (this.nextCodec = SoundSystemConfig.getCodec(this.soundSequenceQueue.get(0).getFilename())).initialize(this.soundSequenceQueue.get(0).getURL());
                for (int n = 0; n < SoundSystemConfig.getNumberStreamingBuffers() && !this.nextCodec.endOfStream(); ++n) {
                    final SoundBuffer read = this.nextCodec.read();
                    if (read != null) {
                        if (this.nextBuffers == null) {
                            this.nextBuffers = new LinkedList();
                        }
                        this.nextBuffers.add(read);
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public int getSoundSequenceQueueSize() {
        if (this.soundSequenceQueue == null) {
            return 0;
        }
        return this.soundSequenceQueue.size();
    }
    
    public void setTemporary(final boolean temporary) {
        this.temporary = temporary;
    }
    
    public void listenerMoved() {
    }
    
    public void setPosition(final float x, final float y, final float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
    
    public void positionChanged() {
    }
    
    public void setPriority(final boolean priority) {
        this.priority = priority;
    }
    
    public void setLooping(final boolean toLoop) {
        this.toLoop = toLoop;
    }
    
    public void setAttenuation(final int attModel) {
        this.attModel = attModel;
    }
    
    public void setDistOrRoll(final float distOrRoll) {
        this.distOrRoll = distOrRoll;
    }
    
    public void setVelocity(final float x, final float y, final float z) {
        this.velocity.x = x;
        this.velocity.y = y;
        this.velocity.z = z;
    }
    
    public float getDistanceFromListener() {
        return this.distanceFromListener;
    }
    
    public void setPitch(final float n) {
        float pitch = n;
        if (pitch < 0.5f) {
            pitch = 0.5f;
        }
        else if (pitch > 2.0f) {
            pitch = 2.0f;
        }
        this.pitch = pitch;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean reverseByteOrder() {
        return SoundSystemConfig.reverseByteOrder(this.libraryType);
    }
    
    public void changeSource(final boolean priority, final boolean toStream, final boolean toLoop, final String sourcename, final FilenameURL filenameURL, final SoundBuffer soundBuffer, final float x, final float y, final float z, final int attModel, final float distOrRoll, final boolean temporary) {
        this.priority = priority;
        this.toStream = toStream;
        this.toLoop = toLoop;
        this.sourcename = sourcename;
        this.filenameURL = filenameURL;
        this.soundBuffer = soundBuffer;
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
        this.attModel = attModel;
        this.distOrRoll = distOrRoll;
        this.temporary = temporary;
    }
    
    public int feedRawAudioData(final Channel channel, final byte[] array) {
        if (!this.active(false, false)) {
            this.toPlay = true;
            return -1;
        }
        if (this.channel != channel) {
            (this.channel = channel).close();
            this.channel.setAudioFormat(this.rawDataFormat);
            this.positionChanged();
        }
        this.stopped(true, false);
        this.paused(true, false);
        return this.channel.feedRawAudioData(array);
    }
    
    public void play(final Channel channel) {
        if (!this.active(false, false)) {
            if (this.toLoop) {
                this.toPlay = true;
            }
            return;
        }
        if (this.channel != channel) {
            (this.channel = channel).close();
        }
        this.stopped(true, false);
        this.paused(true, false);
    }
    
    public boolean stream() {
        if (this.channel == null) {
            return false;
        }
        if (this.preLoad) {
            if (!this.rawDataStream) {
                return this.preLoad();
            }
            this.preLoad = false;
        }
        if (this.rawDataStream) {
            if (this.stopped() || this.paused()) {
                return true;
            }
            if (this.channel.buffersProcessed() > 0) {
                this.channel.processBuffer();
            }
            return true;
        }
        else {
            if (this.codec == null) {
                return false;
            }
            if (this.stopped()) {
                return false;
            }
            if (this.paused()) {
                return true;
            }
            for (int buffersProcessed = this.channel.buffersProcessed(), i = 0; i < buffersProcessed; ++i) {
                final SoundBuffer read = this.codec.read();
                if (read != null) {
                    if (read.audioData != null) {
                        this.channel.queueBuffer(read.audioData);
                    }
                    read.cleanup();
                    return true;
                }
                if (this.codec.endOfStream()) {
                    synchronized (this.soundSequenceLock) {
                        if (SoundSystemConfig.getStreamQueueFormatsMatch()) {
                            if (this.soundSequenceQueue != null && this.soundSequenceQueue.size() > 0) {
                                if (this.codec != null) {
                                    this.codec.cleanup();
                                }
                                this.filenameURL = this.soundSequenceQueue.remove(0);
                                (this.codec = SoundSystemConfig.getCodec(this.filenameURL.getFilename())).initialize(this.filenameURL.getURL());
                                final SoundBuffer read2 = this.codec.read();
                                if (read2 != null) {
                                    if (read2.audioData != null) {
                                        this.channel.queueBuffer(read2.audioData);
                                    }
                                    read2.cleanup();
                                    return true;
                                }
                            }
                            else if (this.toLoop) {
                                this.codec.initialize(this.filenameURL.getURL());
                                final SoundBuffer read3 = this.codec.read();
                                if (read3 != null) {
                                    if (read3.audioData != null) {
                                        this.channel.queueBuffer(read3.audioData);
                                    }
                                    read3.cleanup();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
    
    public boolean preLoad() {
        if (this.channel == null) {
            return false;
        }
        if (this.codec == null) {
            return false;
        }
        boolean b = false;
        synchronized (this.soundSequenceLock) {
            if (this.nextBuffers == null || this.nextBuffers.isEmpty()) {
                b = true;
            }
        }
        if (this.nextCodec != null && !b) {
            this.codec = this.nextCodec;
            this.nextCodec = null;
            synchronized (this.soundSequenceLock) {
                while (!this.nextBuffers.isEmpty()) {
                    final SoundBuffer soundBuffer = this.nextBuffers.remove(0);
                    if (soundBuffer != null) {
                        if (soundBuffer.audioData != null) {
                            this.channel.queueBuffer(soundBuffer.audioData);
                        }
                        soundBuffer.cleanup();
                    }
                }
            }
        }
        else {
            this.nextCodec = null;
            this.codec.initialize(this.filenameURL.getURL());
            for (int i = 0; i < SoundSystemConfig.getNumberStreamingBuffers(); ++i) {
                final SoundBuffer read = this.codec.read();
                if (read != null) {
                    if (read.audioData != null) {
                        this.channel.queueBuffer(read.audioData);
                    }
                    read.cleanup();
                }
            }
        }
        return true;
    }
    
    public void pause() {
        this.toPlay = false;
        this.paused(true, true);
        if (this.channel != null) {
            this.channel.pause();
        }
        else {
            this.errorMessage("Channel null in method 'pause'");
        }
    }
    
    public void stop() {
        this.toPlay = false;
        this.stopped(true, true);
        this.paused(true, false);
        if (this.channel != null) {
            this.channel.stop();
        }
        else {
            this.errorMessage("Channel null in method 'stop'");
        }
    }
    
    public void rewind() {
        if (this.paused(false, false)) {
            this.stop();
        }
        if (this.channel != null) {
            final boolean playing = this.playing();
            this.channel.rewind();
            if (this.toStream && playing) {
                this.stop();
                this.play(this.channel);
            }
        }
        else {
            this.errorMessage("Channel null in method 'rewind'");
        }
    }
    
    public void flush() {
        if (this.channel != null) {
            this.channel.flush();
        }
        else {
            this.errorMessage("Channel null in method 'flush'");
        }
    }
    
    public void cull() {
        if (!this.active(false, false)) {
            return;
        }
        if (this.playing() && this.toLoop) {
            this.toPlay = true;
        }
        if (this.rawDataStream) {
            this.toPlay = true;
        }
        this.active(true, false);
        if (this.channel != null) {
            this.channel.close();
        }
        this.channel = null;
    }
    
    public void activate() {
        this.active(true, true);
    }
    
    public boolean active() {
        return this.active(false, false);
    }
    
    public boolean playing() {
        return this.channel != null && this.channel.attachedSource == this && !this.paused() && !this.stopped() && this.channel.playing();
    }
    
    public boolean stopped() {
        return this.stopped(false, false);
    }
    
    public boolean paused() {
        return this.paused(false, false);
    }
    
    public float millisecondsPlayed() {
        if (this.channel == null) {
            return -1.0f;
        }
        return this.channel.millisecondsPlayed();
    }
    
    private synchronized boolean active(final boolean b, final boolean active) {
        if (b) {
            this.active = active;
        }
        return this.active;
    }
    
    private synchronized boolean stopped(final boolean b, final boolean stopped) {
        if (b) {
            this.stopped = stopped;
        }
        return this.stopped;
    }
    
    private synchronized boolean paused(final boolean b, final boolean paused) {
        if (b) {
            this.paused = paused;
        }
        return this.paused;
    }
    
    public String getClassName() {
        final String libraryTitle = SoundSystemConfig.getLibraryTitle(this.libraryType);
        if (libraryTitle.equals("No Sound")) {
            return "Source";
        }
        return "Source" + libraryTitle;
    }
    
    protected void message(final String s) {
        this.logger.message(s, 0);
    }
    
    protected void importantMessage(final String s) {
        this.logger.importantMessage(s, 0);
    }
    
    protected boolean errorCheck(final boolean b, final String s) {
        return this.logger.errorCheck(b, this.getClassName(), s, 0);
    }
    
    protected void errorMessage(final String s) {
        this.logger.errorMessage(this.getClassName(), s, 0);
    }
    
    protected void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
}
