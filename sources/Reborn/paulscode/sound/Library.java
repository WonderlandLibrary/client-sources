package paulscode.sound;

import java.util.*;
import javax.sound.sampled.*;

public class Library
{
    private SoundSystemLogger logger;
    protected ListenerData listener;
    protected HashMap bufferMap;
    protected HashMap sourceMap;
    private MidiChannel midiChannel;
    protected List streamingChannels;
    protected List normalChannels;
    private String[] streamingChannelSourceNames;
    private String[] normalChannelSourceNames;
    private int nextStreamingChannel;
    private int nextNormalChannel;
    protected StreamThread streamThread;
    protected boolean reverseByteOrder;
    
    public Library() {
        this.bufferMap = null;
        this.nextStreamingChannel = 0;
        this.nextNormalChannel = 0;
        this.reverseByteOrder = false;
        this.logger = SoundSystemConfig.getLogger();
        this.bufferMap = new HashMap();
        this.sourceMap = new HashMap();
        this.listener = new ListenerData(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f);
        this.streamingChannels = new LinkedList();
        this.normalChannels = new LinkedList();
        this.streamingChannelSourceNames = new String[SoundSystemConfig.getNumberStreamingChannels()];
        this.normalChannelSourceNames = new String[SoundSystemConfig.getNumberNormalChannels()];
        (this.streamThread = new StreamThread()).start();
    }
    
    public void cleanup() {
        this.streamThread.kill();
        this.streamThread.interrupt();
        for (int n = 0; n < 50 && this.streamThread.alive(); ++n) {
            try {
                Thread.sleep(100L);
            }
            catch (Exception ex) {}
        }
        if (this.streamThread.alive()) {
            this.errorMessage("Stream thread did not die!");
            this.message("Ignoring errors... continuing clean-up.");
        }
        if (this.midiChannel != null) {
            this.midiChannel.cleanup();
            this.midiChannel = null;
        }
        if (this.streamingChannels != null) {
            while (!this.streamingChannels.isEmpty()) {
                final Channel channel = this.streamingChannels.remove(0);
                channel.close();
                channel.cleanup();
            }
            this.streamingChannels.clear();
            this.streamingChannels = null;
        }
        if (this.normalChannels != null) {
            while (!this.normalChannels.isEmpty()) {
                final Channel channel2 = this.normalChannels.remove(0);
                channel2.close();
                channel2.cleanup();
            }
            this.normalChannels.clear();
            this.normalChannels = null;
        }
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null) {
                source.cleanup();
            }
        }
        this.sourceMap.clear();
        this.sourceMap = null;
        this.listener = null;
        this.streamThread = null;
    }
    
    public void init() {
        for (int i = 0; i < SoundSystemConfig.getNumberStreamingChannels(); ++i) {
            final Channel channel = this.createChannel(1);
            if (channel == null) {
                break;
            }
            this.streamingChannels.add(channel);
        }
        for (int j = 0; j < SoundSystemConfig.getNumberNormalChannels(); ++j) {
            final Channel channel2 = this.createChannel(0);
            if (channel2 == null) {
                break;
            }
            this.normalChannels.add(channel2);
        }
    }
    
    public static boolean libraryCompatible() {
        return true;
    }
    
    protected Channel createChannel(final int n) {
        return new Channel(n);
    }
    
    public boolean loadSound(final FilenameURL filenameURL) {
        return true;
    }
    
    public boolean loadSound(final SoundBuffer soundBuffer, final String s) {
        return true;
    }
    
    public LinkedList getAllLoadedFilenames() {
        final LinkedList<Object> list = new LinkedList<Object>();
        final Iterator<Object> iterator = this.bufferMap.keySet().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
    
    public LinkedList getAllSourcenames() {
        final LinkedList<String> list = new LinkedList<String>();
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        if (this.midiChannel != null) {
            list.add(this.midiChannel.getSourcename());
        }
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
    
    public void unloadSound(final String s) {
        this.bufferMap.remove(s);
    }
    
    public void rawDataStream(final AudioFormat audioFormat, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.sourceMap.put(s, new Source(audioFormat, b, s, n, n2, n3, n4, n5));
    }
    
    public void newSource(final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.sourceMap.put(s, new Source(b, b2, b3, s, filenameURL, null, n, n2, n3, n4, n5, false));
    }
    
    public void quickPlay(final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b4) {
        this.sourceMap.put(s, new Source(b, b2, b3, s, filenameURL, null, n, n2, n3, n4, n5, b4));
    }
    
    public void setTemporary(final String s, final boolean temporary) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.setTemporary(temporary);
        }
    }
    
    public void setPosition(final String s, final float n, final float n2, final float n3) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.setPosition(n, n2, n3);
        }
    }
    
    public void setPriority(final String s, final boolean priority) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.setPriority(priority);
        }
    }
    
    public void setLooping(final String s, final boolean looping) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.setLooping(looping);
        }
    }
    
    public void setAttenuation(final String s, final int attenuation) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.setAttenuation(attenuation);
        }
    }
    
    public void setDistOrRoll(final String s, final float distOrRoll) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.setDistOrRoll(distOrRoll);
        }
    }
    
    public void setVelocity(final String s, final float n, final float n2, final float n3) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.setVelocity(n, n2, n3);
        }
    }
    
    public void setListenerVelocity(final float n, final float n2, final float n3) {
        this.listener.setVelocity(n, n2, n3);
    }
    
    public void dopplerChanged() {
    }
    
    public float millisecondsPlayed(final String s) {
        if (s == null || s.equals("")) {
            this.errorMessage("Sourcename not specified in method 'millisecondsPlayed'");
            return -1.0f;
        }
        if (this.midiSourcename(s)) {
            this.errorMessage("Unable to calculate milliseconds for MIDI source.");
            return -1.0f;
        }
        final Source source = this.sourceMap.get(s);
        if (source == null) {
            this.errorMessage("Source '" + s + "' not found in " + "method 'millisecondsPlayed'");
        }
        return source.millisecondsPlayed();
    }
    
    public int feedRawAudioData(final String s, final byte[] array) {
        if (s == null || s.equals("")) {
            this.errorMessage("Sourcename not specified in method 'feedRawAudioData'");
            return -1;
        }
        if (this.midiSourcename(s)) {
            this.errorMessage("Raw audio data can not be fed to the MIDI channel.");
            return -1;
        }
        final Source source = this.sourceMap.get(s);
        if (source == null) {
            this.errorMessage("Source '" + s + "' not found in " + "method 'feedRawAudioData'");
        }
        return this.feedRawAudioData(source, array);
    }
    
    public int feedRawAudioData(final Source attachedSource, final byte[] array) {
        if (attachedSource == null) {
            this.errorMessage("Source parameter null in method 'feedRawAudioData'");
            return -1;
        }
        if (!attachedSource.toStream) {
            this.errorMessage("Only a streaming source may be specified in method 'feedRawAudioData'");
            return -1;
        }
        if (!attachedSource.rawDataStream) {
            this.errorMessage("Streaming source already associated with a file or URL in method'feedRawAudioData'");
            return -1;
        }
        if (!attachedSource.playing() || attachedSource.channel == null) {
            Channel channel;
            if (attachedSource.channel != null && attachedSource.channel.attachedSource == attachedSource) {
                channel = attachedSource.channel;
            }
            else {
                channel = this.getNextChannel(attachedSource);
            }
            final int feedRawAudioData = attachedSource.feedRawAudioData(channel, array);
            channel.attachedSource = attachedSource;
            this.streamThread.watch(attachedSource);
            this.streamThread.interrupt();
            return feedRawAudioData;
        }
        return attachedSource.feedRawAudioData(attachedSource.channel, array);
    }
    
    public void play(final String s) {
        if (s == null || s.equals("")) {
            this.errorMessage("Sourcename not specified in method 'play'");
            return;
        }
        if (this.midiSourcename(s)) {
            this.midiChannel.play();
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source == null) {
                this.errorMessage("Source '" + s + "' not found in " + "method 'play'");
            }
            this.play(source);
        }
    }
    
    public void play(final Source attachedSource) {
        if (attachedSource == null) {
            return;
        }
        if (attachedSource.rawDataStream) {
            return;
        }
        if (!attachedSource.active()) {
            return;
        }
        if (!attachedSource.playing()) {
            final Channel nextChannel = this.getNextChannel(attachedSource);
            if (attachedSource != null && nextChannel != null) {
                if (attachedSource.channel != null && attachedSource.channel.attachedSource != attachedSource) {
                    attachedSource.channel = null;
                }
                (nextChannel.attachedSource = attachedSource).play(nextChannel);
                if (attachedSource.toStream) {
                    this.streamThread.watch(attachedSource);
                    this.streamThread.interrupt();
                }
            }
        }
    }
    
    public void stop(final String s) {
        if (s == null || s.equals("")) {
            this.errorMessage("Sourcename not specified in method 'stop'");
            return;
        }
        if (this.midiSourcename(s)) {
            this.midiChannel.stop();
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                source.stop();
            }
        }
    }
    
    public void pause(final String s) {
        if (s == null || s.equals("")) {
            this.errorMessage("Sourcename not specified in method 'stop'");
            return;
        }
        if (this.midiSourcename(s)) {
            this.midiChannel.pause();
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                source.pause();
            }
        }
    }
    
    public void rewind(final String s) {
        if (this.midiSourcename(s)) {
            this.midiChannel.rewind();
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                source.rewind();
            }
        }
    }
    
    public void flush(final String s) {
        if (this.midiSourcename(s)) {
            this.errorMessage("You can not flush the MIDI channel");
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                source.flush();
            }
        }
    }
    
    public void cull(final String s) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.cull();
        }
    }
    
    public void activate(final String s) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.activate();
            if (source.toPlay) {
                this.play(source);
            }
        }
    }
    
    public void setMasterVolume(final float masterGain) {
        SoundSystemConfig.setMasterGain(masterGain);
        if (this.midiChannel != null) {
            this.midiChannel.resetGain();
        }
    }
    
    public void setVolume(final String s, final float volume) {
        if (this.midiSourcename(s)) {
            this.midiChannel.setVolume(volume);
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                float sourceVolume = volume;
                if (sourceVolume < 0.0f) {
                    sourceVolume = 0.0f;
                }
                else if (sourceVolume > 1.0f) {
                    sourceVolume = 1.0f;
                }
                source.sourceVolume = sourceVolume;
                source.positionChanged();
            }
        }
    }
    
    public float getVolume(final String s) {
        if (this.midiSourcename(s)) {
            return this.midiChannel.getVolume();
        }
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            return source.sourceVolume;
        }
        return 0.0f;
    }
    
    public void setPitch(final String s, final float n) {
        if (!this.midiSourcename(s)) {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                float pitch = n;
                if (pitch < 0.5f) {
                    pitch = 0.5f;
                }
                else if (pitch > 2.0f) {
                    pitch = 2.0f;
                }
                source.setPitch(pitch);
                source.positionChanged();
            }
        }
    }
    
    public float getPitch(final String s) {
        if (!this.midiSourcename(s)) {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                return source.getPitch();
            }
        }
        return 1.0f;
    }
    
    public void moveListener(final float n, final float n2, final float n3) {
        this.setListenerPosition(this.listener.position.x + n, this.listener.position.y + n2, this.listener.position.z + n3);
    }
    
    public void setListenerPosition(final float n, final float n2, final float n3) {
        this.listener.setPosition(n, n2, n3);
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null) {
                source.positionChanged();
            }
        }
    }
    
    public void turnListener(final float n) {
        this.setListenerAngle(this.listener.angle + n);
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null) {
                source.positionChanged();
            }
        }
    }
    
    public void setListenerAngle(final float angle) {
        this.listener.setAngle(angle);
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null) {
                source.positionChanged();
            }
        }
    }
    
    public void setListenerOrientation(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.listener.setOrientation(n, n2, n3, n4, n5, n6);
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null) {
                source.positionChanged();
            }
        }
    }
    
    public void setListenerData(final ListenerData data) {
        this.listener.setData(data);
    }
    
    public void copySources(final HashMap hashMap) {
        if (hashMap == null) {
            return;
        }
        final Iterator<String> iterator = hashMap.keySet().iterator();
        this.sourceMap.clear();
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final Source source = (Source)hashMap.get(s);
            if (source != null) {
                this.loadSound(source.filenameURL);
                this.sourceMap.put(s, new Source(source, null));
            }
        }
    }
    
    public void removeSource(final String s) {
        final Source source = this.sourceMap.get(s);
        if (source != null) {
            source.cleanup();
        }
        this.sourceMap.remove(s);
    }
    
    public void removeTemporarySources() {
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null && source.temporary && !source.playing()) {
                source.cleanup();
                iterator.remove();
            }
        }
    }
    
    private Channel getNextChannel(final Source source) {
        if (source == null) {
            return null;
        }
        final String sourcename = source.sourcename;
        if (sourcename == null) {
            return null;
        }
        int n;
        List list;
        String[] array;
        if (source.toStream) {
            n = this.nextStreamingChannel;
            list = this.streamingChannels;
            array = this.streamingChannelSourceNames;
        }
        else {
            n = this.nextNormalChannel;
            list = this.normalChannels;
            array = this.normalChannelSourceNames;
        }
        final int size = list.size();
        for (int i = 0; i < size; ++i) {
            if (sourcename.equals(array[i])) {
                return list.get(i);
            }
        }
        int n2 = n;
        for (int j = 0; j < size; ++j) {
            final String s = array[n2];
            Source source2;
            if (s == null) {
                source2 = null;
            }
            else {
                source2 = this.sourceMap.get(s);
            }
            if (source2 == null || !source2.playing()) {
                if (source.toStream) {
                    this.nextStreamingChannel = n2 + 1;
                    if (this.nextStreamingChannel >= size) {
                        this.nextStreamingChannel = 0;
                    }
                }
                else {
                    this.nextNormalChannel = n2 + 1;
                    if (this.nextNormalChannel >= size) {
                        this.nextNormalChannel = 0;
                    }
                }
                array[n2] = sourcename;
                return list.get(n2);
            }
            if (++n2 >= size) {
                n2 = 0;
            }
        }
        int n3 = n;
        for (int k = 0; k < size; ++k) {
            final String s2 = array[n3];
            Source source3;
            if (s2 == null) {
                source3 = null;
            }
            else {
                source3 = this.sourceMap.get(s2);
            }
            if (source3 == null || !source3.playing() || !source3.priority) {
                if (source.toStream) {
                    this.nextStreamingChannel = n3 + 1;
                    if (this.nextStreamingChannel >= size) {
                        this.nextStreamingChannel = 0;
                    }
                }
                else {
                    this.nextNormalChannel = n3 + 1;
                    if (this.nextNormalChannel >= size) {
                        this.nextNormalChannel = 0;
                    }
                }
                array[n3] = sourcename;
                return list.get(n3);
            }
            if (++n3 >= size) {
                n3 = 0;
            }
        }
        return null;
    }
    
    public void replaySources() {
        for (final String s : this.sourceMap.keySet()) {
            final Source source = this.sourceMap.get(s);
            if (source != null && source.toPlay && !source.playing()) {
                this.play(s);
                source.toPlay = false;
            }
        }
    }
    
    public void queueSound(final String s, final FilenameURL filenameURL) {
        if (this.midiSourcename(s)) {
            this.midiChannel.queueSound(filenameURL);
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                source.queueSound(filenameURL);
            }
        }
    }
    
    public void dequeueSound(final String s, final String s2) {
        if (this.midiSourcename(s)) {
            this.midiChannel.dequeueSound(s2);
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                source.dequeueSound(s2);
            }
        }
    }
    
    public void fadeOut(final String s, final FilenameURL filenameURL, final long n) {
        if (this.midiSourcename(s)) {
            this.midiChannel.fadeOut(filenameURL, n);
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                source.fadeOut(filenameURL, n);
            }
        }
    }
    
    public void fadeOutIn(final String s, final FilenameURL filenameURL, final long n, final long n2) {
        if (this.midiSourcename(s)) {
            this.midiChannel.fadeOutIn(filenameURL, n, n2);
        }
        else {
            final Source source = this.sourceMap.get(s);
            if (source != null) {
                source.fadeOutIn(filenameURL, n, n2);
            }
        }
    }
    
    public void checkFadeVolumes() {
        if (this.midiChannel != null) {
            this.midiChannel.resetGain();
        }
        for (int i = 0; i < this.streamingChannels.size(); ++i) {
            final Channel channel = this.streamingChannels.get(i);
            if (channel != null) {
                final Source attachedSource = channel.attachedSource;
                if (attachedSource != null) {
                    attachedSource.checkFadeOut();
                }
            }
        }
    }
    
    public void loadMidi(final boolean b, final String s, final FilenameURL filenameURL) {
        if (filenameURL == null) {
            this.errorMessage("Filename/URL not specified in method 'loadMidi'.");
            return;
        }
        if (!filenameURL.getFilename().matches(SoundSystemConfig.EXTENSION_MIDI)) {
            this.errorMessage("Filename/identifier doesn't end in '.mid' or'.midi' in method loadMidi.");
            return;
        }
        if (this.midiChannel == null) {
            this.midiChannel = new MidiChannel(b, s, filenameURL);
        }
        else {
            this.midiChannel.switchSource(b, s, filenameURL);
        }
    }
    
    public void unloadMidi() {
        if (this.midiChannel != null) {
            this.midiChannel.cleanup();
        }
        this.midiChannel = null;
    }
    
    public boolean midiSourcename(final String s) {
        return this.midiChannel != null && s != null && this.midiChannel.getSourcename() != null && !s.equals("") && s.equals(this.midiChannel.getSourcename());
    }
    
    public Source getSource(final String s) {
        return this.sourceMap.get(s);
    }
    
    public MidiChannel getMidiChannel() {
        return this.midiChannel;
    }
    
    public void setMidiChannel(final MidiChannel midiChannel) {
        if (this.midiChannel != null && this.midiChannel != midiChannel) {
            this.midiChannel.cleanup();
        }
        this.midiChannel = midiChannel;
    }
    
    public void listenerMoved() {
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null) {
                source.listenerMoved();
            }
        }
    }
    
    public HashMap getSources() {
        return this.sourceMap;
    }
    
    public ListenerData getListenerData() {
        return this.listener;
    }
    
    public boolean reverseByteOrder() {
        return this.reverseByteOrder;
    }
    
    public static String getTitle() {
        return "No Sound";
    }
    
    public static String getDescription() {
        return "Silent Mode";
    }
    
    public String getClassName() {
        return "Library";
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
