package paulscode.sound;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.sound.midi.*;

public class MidiChannel implements MetaEventListener
{
    private SoundSystemLogger logger;
    private FilenameURL filenameURL;
    private String sourcename;
    private static final int CHANGE_VOLUME = 7;
    private static final int END_OF_TRACK = 47;
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    private MidiDevice synthDevice;
    private Sequence sequence;
    private boolean toLoop;
    private float gain;
    private boolean loading;
    private LinkedList sequenceQueue;
    private final Object sequenceQueueLock;
    protected float fadeOutGain;
    protected float fadeInGain;
    protected long fadeOutMilis;
    protected long fadeInMilis;
    protected long lastFadeCheck;
    private MidiChannel$FadeThread fadeThread;
    
    public MidiChannel(final boolean looping, final String s, final String s2) {
        this.sequencer = null;
        this.synthesizer = null;
        this.synthDevice = null;
        this.sequence = null;
        this.toLoop = true;
        this.gain = 1.0f;
        this.loading = true;
        this.sequenceQueue = null;
        this.sequenceQueueLock = new Object();
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.fadeThread = null;
        this.loading(true, true);
        this.logger = SoundSystemConfig.getLogger();
        this.filenameURL(true, new FilenameURL(s2));
        this.sourcename(true, s);
        this.setLooping(looping);
        this.init();
        this.loading(true, false);
    }
    
    public MidiChannel(final boolean looping, final String s, final URL url, final String s2) {
        this.sequencer = null;
        this.synthesizer = null;
        this.synthDevice = null;
        this.sequence = null;
        this.toLoop = true;
        this.gain = 1.0f;
        this.loading = true;
        this.sequenceQueue = null;
        this.sequenceQueueLock = new Object();
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.fadeThread = null;
        this.loading(true, true);
        this.logger = SoundSystemConfig.getLogger();
        this.filenameURL(true, new FilenameURL(url, s2));
        this.sourcename(true, s);
        this.setLooping(looping);
        this.init();
        this.loading(true, false);
    }
    
    public MidiChannel(final boolean looping, final String s, final FilenameURL filenameURL) {
        this.sequencer = null;
        this.synthesizer = null;
        this.synthDevice = null;
        this.sequence = null;
        this.toLoop = true;
        this.gain = 1.0f;
        this.loading = true;
        this.sequenceQueue = null;
        this.sequenceQueueLock = new Object();
        this.fadeOutGain = -1.0f;
        this.fadeInGain = 1.0f;
        this.fadeOutMilis = 0L;
        this.fadeInMilis = 0L;
        this.lastFadeCheck = 0L;
        this.fadeThread = null;
        this.loading(true, true);
        this.logger = SoundSystemConfig.getLogger();
        this.filenameURL(true, filenameURL);
        this.sourcename(true, s);
        this.setLooping(looping);
        this.init();
        this.loading(true, false);
    }
    
    private void init() {
        this.getSequencer();
        this.setSequence(this.filenameURL(false, null).getURL());
        this.getSynthesizer();
        this.resetGain();
    }
    
    public void cleanup() {
        this.loading(true, true);
        this.setLooping(true);
        if (this.sequencer != null) {
            try {
                this.sequencer.stop();
                this.sequencer.close();
                this.sequencer.removeMetaEventListener(this);
            }
            catch (Exception ex) {}
        }
        this.logger = null;
        this.sequencer = null;
        this.synthesizer = null;
        this.sequence = null;
        synchronized (this.sequenceQueueLock) {
            if (this.sequenceQueue != null) {
                this.sequenceQueue.clear();
            }
            this.sequenceQueue = null;
        }
        if (this.fadeThread != null) {
            boolean b = false;
            try {
                this.fadeThread.kill();
                this.fadeThread.interrupt();
            }
            catch (Exception ex2) {
                b = true;
            }
            if (!b) {
                for (int i = 0; i < 50; ++i) {
                    if (!this.fadeThread.alive()) {
                        break;
                    }
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException ex3) {}
                }
            }
            if (b || this.fadeThread.alive()) {
                this.errorMessage("MIDI fade effects thread did not die!");
                this.message("Ignoring errors... continuing clean-up.");
            }
        }
        this.fadeThread = null;
        this.loading(true, false);
    }
    
    public void queueSound(final FilenameURL filenameURL) {
        if (filenameURL == null) {
            this.errorMessage("Filename/URL not specified in method 'queueSound'");
            return;
        }
        synchronized (this.sequenceQueueLock) {
            if (this.sequenceQueue == null) {
                this.sequenceQueue = new LinkedList();
            }
            this.sequenceQueue.add(filenameURL);
        }
    }
    
    public void dequeueSound(final String s) {
        if (s == null || s.equals("")) {
            this.errorMessage("Filename not specified in method 'dequeueSound'");
            return;
        }
        synchronized (this.sequenceQueueLock) {
            if (this.sequenceQueue != null) {
                final ListIterator listIterator = this.sequenceQueue.listIterator();
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
        if (fadeOutMilis < 0L) {
            this.errorMessage("Miliseconds may not be negative in method 'fadeOut'.");
            return;
        }
        this.fadeOutMilis = fadeOutMilis;
        this.fadeInMilis = 0L;
        this.fadeOutGain = 1.0f;
        this.lastFadeCheck = System.currentTimeMillis();
        synchronized (this.sequenceQueueLock) {
            if (this.sequenceQueue != null) {
                this.sequenceQueue.clear();
            }
            if (filenameURL != null) {
                if (this.sequenceQueue == null) {
                    this.sequenceQueue = new LinkedList();
                }
                this.sequenceQueue.add(filenameURL);
            }
        }
        if (this.fadeThread == null) {
            (this.fadeThread = new MidiChannel$FadeThread(this, null)).start();
        }
        this.fadeThread.interrupt();
    }
    
    public void fadeOutIn(final FilenameURL filenameURL, final long fadeOutMilis, final long fadeInMilis) {
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
        synchronized (this.sequenceQueueLock) {
            if (this.sequenceQueue == null) {
                this.sequenceQueue = new LinkedList();
            }
            this.sequenceQueue.clear();
            this.sequenceQueue.add(filenameURL);
        }
        if (this.fadeThread == null) {
            (this.fadeThread = new MidiChannel$FadeThread(this, null)).start();
        }
        this.fadeThread.interrupt();
    }
    
    private synchronized boolean checkFadeOut() {
        if (this.fadeOutGain == -1.0f && this.fadeInGain == 1.0f) {
            return false;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final long n = currentTimeMillis - this.lastFadeCheck;
        this.lastFadeCheck = currentTimeMillis;
        if (this.fadeOutGain < 0.0f) {
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
                this.resetGain();
            }
            return false;
        }
        if (this.fadeOutMilis == 0L) {
            this.fadeOutGain = 0.0f;
            this.fadeInGain = 0.0f;
            if (!this.incrementSequence()) {
                this.stop();
            }
            this.rewind();
            this.resetGain();
            return false;
        }
        this.fadeOutGain -= n / this.fadeOutMilis;
        if (this.fadeOutGain <= 0.0f) {
            this.fadeOutGain = -1.0f;
            this.fadeInGain = 0.0f;
            if (!this.incrementSequence()) {
                this.stop();
            }
            this.rewind();
            this.resetGain();
            return false;
        }
        this.resetGain();
        return true;
    }
    
    private boolean incrementSequence() {
        synchronized (this.sequenceQueueLock) {
            if (this.sequenceQueue != null && this.sequenceQueue.size() > 0) {
                this.filenameURL(true, this.sequenceQueue.remove(0));
                this.loading(true, true);
                if (this.sequencer == null) {
                    this.getSequencer();
                }
                else {
                    this.sequencer.stop();
                    this.sequencer.setMicrosecondPosition(0L);
                    this.sequencer.removeMetaEventListener(this);
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException ex) {}
                }
                if (this.sequencer == null) {
                    this.errorMessage("Unable to set the sequence in method 'incrementSequence', because there wasn't a sequencer to use.");
                    this.loading(true, false);
                    return false;
                }
                this.setSequence(this.filenameURL(false, null).getURL());
                this.sequencer.start();
                this.resetGain();
                this.sequencer.addMetaEventListener(this);
                this.loading(true, false);
                return true;
            }
        }
        return false;
    }
    
    public void play() {
        if (!this.loading()) {
            if (this.sequencer == null) {
                return;
            }
            try {
                this.sequencer.start();
                this.sequencer.addMetaEventListener(this);
            }
            catch (Exception ex) {
                this.errorMessage("Exception in method 'play'");
                this.printStackTrace(ex);
                SoundSystem.setException(new SoundSystemException(ex.getMessage()));
            }
        }
    }
    
    public void stop() {
        if (!this.loading()) {
            if (this.sequencer == null) {
                return;
            }
            try {
                this.sequencer.stop();
                this.sequencer.setMicrosecondPosition(0L);
                this.sequencer.removeMetaEventListener(this);
            }
            catch (Exception ex) {
                this.errorMessage("Exception in method 'stop'");
                this.printStackTrace(ex);
                SoundSystem.setException(new SoundSystemException(ex.getMessage()));
            }
        }
    }
    
    public void pause() {
        if (!this.loading()) {
            if (this.sequencer == null) {
                return;
            }
            try {
                this.sequencer.stop();
            }
            catch (Exception ex) {
                this.errorMessage("Exception in method 'pause'");
                this.printStackTrace(ex);
                SoundSystem.setException(new SoundSystemException(ex.getMessage()));
            }
        }
    }
    
    public void rewind() {
        if (!this.loading()) {
            if (this.sequencer == null) {
                return;
            }
            try {
                this.sequencer.setMicrosecondPosition(0L);
            }
            catch (Exception ex) {
                this.errorMessage("Exception in method 'rewind'");
                this.printStackTrace(ex);
                SoundSystem.setException(new SoundSystemException(ex.getMessage()));
            }
        }
    }
    
    public void setVolume(final float gain) {
        this.gain = gain;
        this.resetGain();
    }
    
    public float getVolume() {
        return this.gain;
    }
    
    public void switchSource(final boolean looping, final String s, final String s2) {
        this.loading(true, true);
        this.filenameURL(true, new FilenameURL(s2));
        this.sourcename(true, s);
        this.setLooping(looping);
        this.reset();
        this.loading(true, false);
    }
    
    public void switchSource(final boolean looping, final String s, final URL url, final String s2) {
        this.loading(true, true);
        this.filenameURL(true, new FilenameURL(url, s2));
        this.sourcename(true, s);
        this.setLooping(looping);
        this.reset();
        this.loading(true, false);
    }
    
    public void switchSource(final boolean looping, final String s, final FilenameURL filenameURL) {
        this.loading(true, true);
        this.filenameURL(true, filenameURL);
        this.sourcename(true, s);
        this.setLooping(looping);
        this.reset();
        this.loading(true, false);
    }
    
    private void reset() {
        synchronized (this.sequenceQueueLock) {
            if (this.sequenceQueue != null) {
                this.sequenceQueue.clear();
            }
        }
        if (this.sequencer == null) {
            this.getSequencer();
        }
        else {
            this.sequencer.stop();
            this.sequencer.setMicrosecondPosition(0L);
            this.sequencer.removeMetaEventListener(this);
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException ex) {}
        }
        if (this.sequencer == null) {
            this.errorMessage("Unable to set the sequence in method 'reset', because there wasn't a sequencer to use.");
            return;
        }
        this.setSequence(this.filenameURL(false, null).getURL());
        this.sequencer.start();
        this.resetGain();
        this.sequencer.addMetaEventListener(this);
    }
    
    public void setLooping(final boolean b) {
        this.toLoop(true, b);
    }
    
    public boolean getLooping() {
        return this.toLoop(false, false);
    }
    
    private synchronized boolean toLoop(final boolean b, final boolean toLoop) {
        if (b) {
            this.toLoop = toLoop;
        }
        return this.toLoop;
    }
    
    public boolean loading() {
        return this.loading(false, false);
    }
    
    private synchronized boolean loading(final boolean b, final boolean loading) {
        if (b) {
            this.loading = loading;
        }
        return this.loading;
    }
    
    public void setSourcename(final String s) {
        this.sourcename(true, s);
    }
    
    public String getSourcename() {
        return this.sourcename(false, null);
    }
    
    private synchronized String sourcename(final boolean b, final String sourcename) {
        if (b) {
            this.sourcename = sourcename;
        }
        return this.sourcename;
    }
    
    public void setFilenameURL(final FilenameURL filenameURL) {
        this.filenameURL(true, filenameURL);
    }
    
    public String getFilename() {
        return this.filenameURL(false, null).getFilename();
    }
    
    public FilenameURL getFilenameURL() {
        return this.filenameURL(false, null);
    }
    
    private synchronized FilenameURL filenameURL(final boolean b, final FilenameURL filenameURL) {
        if (b) {
            this.filenameURL = filenameURL;
        }
        return this.filenameURL;
    }
    
    public void meta(final MetaMessage metaMessage) {
        if (metaMessage.getType() == 47) {
            SoundSystemConfig.notifyEOS(this.sourcename, this.sequenceQueue.size());
            if (this.toLoop) {
                if (!this.checkFadeOut()) {
                    if (!this.incrementSequence()) {
                        try {
                            this.sequencer.setMicrosecondPosition(0L);
                            this.sequencer.start();
                            this.resetGain();
                        }
                        catch (Exception ex) {}
                    }
                }
                else if (this.sequencer != null) {
                    try {
                        this.sequencer.setMicrosecondPosition(0L);
                        this.sequencer.start();
                        this.resetGain();
                    }
                    catch (Exception ex2) {}
                }
            }
            else if (!this.checkFadeOut()) {
                if (!this.incrementSequence()) {
                    try {
                        this.sequencer.stop();
                        this.sequencer.setMicrosecondPosition(0L);
                        this.sequencer.removeMetaEventListener(this);
                    }
                    catch (Exception ex3) {}
                }
            }
            else {
                try {
                    this.sequencer.stop();
                    this.sequencer.setMicrosecondPosition(0L);
                    this.sequencer.removeMetaEventListener(this);
                }
                catch (Exception ex4) {}
            }
        }
    }
    
    public void resetGain() {
        if (this.gain < 0.0f) {
            this.gain = 0.0f;
        }
        if (this.gain > 1.0f) {
            this.gain = 1.0f;
        }
        final int n = (int)(this.gain * SoundSystemConfig.getMasterGain() * Math.abs(this.fadeOutGain) * this.fadeInGain * 127.0f);
        if (this.synthesizer != null) {
            final javax.sound.midi.MidiChannel[] channels = this.synthesizer.getChannels();
            for (int n2 = 0; channels != null && n2 < channels.length; ++n2) {
                channels[n2].controlChange(7, n);
            }
        }
        else if (this.synthDevice != null) {
            try {
                final ShortMessage shortMessage = new ShortMessage();
                for (int i = 0; i < 16; ++i) {
                    shortMessage.setMessage(176, i, 7, n);
                    this.synthDevice.getReceiver().send(shortMessage, -1L);
                }
            }
            catch (Exception ex) {
                this.errorMessage("Error resetting gain on MIDI device");
                this.printStackTrace(ex);
            }
        }
        else if (this.sequencer != null && this.sequencer instanceof Synthesizer) {
            this.synthesizer = (Synthesizer)this.sequencer;
            final javax.sound.midi.MidiChannel[] channels2 = this.synthesizer.getChannels();
            for (int n3 = 0; channels2 != null && n3 < channels2.length; ++n3) {
                channels2[n3].controlChange(7, n);
            }
        }
        else {
            try {
                final Receiver receiver = MidiSystem.getReceiver();
                final ShortMessage shortMessage2 = new ShortMessage();
                for (int j = 0; j < 16; ++j) {
                    shortMessage2.setMessage(176, j, 7, n);
                    receiver.send(shortMessage2, -1L);
                }
            }
            catch (Exception ex2) {
                this.errorMessage("Error resetting gain on default receiver");
                this.printStackTrace(ex2);
            }
        }
    }
    
    private void getSequencer() {
        try {
            this.sequencer = MidiSystem.getSequencer();
            if (this.sequencer != null) {
                try {
                    this.sequencer.getTransmitter();
                }
                catch (MidiUnavailableException ex2) {
                    this.message("Unable to get a transmitter from the default MIDI sequencer");
                }
                this.sequencer.open();
            }
        }
        catch (MidiUnavailableException ex3) {
            this.message("Unable to open the default MIDI sequencer");
            this.sequencer = null;
        }
        catch (Exception ex) {
            if (ex instanceof InterruptedException) {
                this.message("Caught InterruptedException while attempting to open the default MIDI sequencer.  Trying again.");
                this.sequencer = null;
            }
            try {
                this.sequencer = MidiSystem.getSequencer();
                if (this.sequencer != null) {
                    try {
                        this.sequencer.getTransmitter();
                    }
                    catch (MidiUnavailableException ex4) {
                        this.message("Unable to get a transmitter from the default MIDI sequencer");
                    }
                    this.sequencer.open();
                }
            }
            catch (MidiUnavailableException ex5) {
                this.message("Unable to open the default MIDI sequencer");
                this.sequencer = null;
            }
            catch (Exception ex6) {
                this.message("Unknown error opening the default MIDI sequencer");
                this.sequencer = null;
            }
        }
        if (this.sequencer == null) {
            this.sequencer = this.openSequencer("Real Time Sequencer");
        }
        if (this.sequencer == null) {
            this.sequencer = this.openSequencer("Java Sound Sequencer");
        }
        if (this.sequencer == null) {
            this.errorMessage("Failed to find an available MIDI sequencer");
        }
    }
    
    private void setSequence(final URL url) {
        if (this.sequencer == null) {
            this.errorMessage("Unable to update the sequence in method 'setSequence', because variable 'sequencer' is null");
            return;
        }
        if (url == null) {
            this.errorMessage("Unable to load Midi file in method 'setSequence'.");
            return;
        }
        try {
            this.sequence = MidiSystem.getSequence(url);
        }
        catch (IOException ex) {
            this.errorMessage("Input failed while reading from MIDI file in method 'setSequence'.");
            this.printStackTrace(ex);
            return;
        }
        catch (InvalidMidiDataException ex2) {
            this.errorMessage("Invalid MIDI data encountered, or not a MIDI file in method 'setSequence' (1).");
            this.printStackTrace(ex2);
            return;
        }
        if (this.sequence == null) {
            this.errorMessage("MidiSystem 'getSequence' method returned null in method 'setSequence'.");
        }
        else {
            try {
                this.sequencer.setSequence(this.sequence);
            }
            catch (InvalidMidiDataException ex3) {
                this.errorMessage("Invalid MIDI data encountered, or not a MIDI file in method 'setSequence' (2).");
                this.printStackTrace(ex3);
            }
            catch (Exception ex4) {
                this.errorMessage("Problem setting sequence from MIDI file in method 'setSequence'.");
                this.printStackTrace(ex4);
            }
        }
    }
    
    private void getSynthesizer() {
        if (this.sequencer == null) {
            this.errorMessage("Unable to load a Synthesizer in method 'getSynthesizer', because variable 'sequencer' is null");
            return;
        }
        final String overrideMIDISynthesizer = SoundSystemConfig.getOverrideMIDISynthesizer();
        if (overrideMIDISynthesizer != null && !overrideMIDISynthesizer.equals("")) {
            this.synthDevice = this.openMidiDevice(overrideMIDISynthesizer);
            if (this.synthDevice != null) {
                try {
                    this.sequencer.getTransmitter().setReceiver(this.synthDevice.getReceiver());
                    return;
                }
                catch (MidiUnavailableException ex) {
                    this.errorMessage("Unable to link sequencer transmitter with receiver for MIDI device '" + overrideMIDISynthesizer + "'");
                }
            }
        }
        if (this.sequencer instanceof Synthesizer) {
            this.synthesizer = (Synthesizer)this.sequencer;
        }
        else {
            try {
                (this.synthesizer = MidiSystem.getSynthesizer()).open();
            }
            catch (MidiUnavailableException ex2) {
                this.message("Unable to open the default synthesizer");
                this.synthesizer = null;
            }
            if (this.synthesizer == null) {
                this.synthDevice = this.openMidiDevice("Java Sound Synthesizer");
                if (this.synthDevice == null) {
                    this.synthDevice = this.openMidiDevice("Microsoft GS Wavetable");
                }
                if (this.synthDevice == null) {
                    this.synthDevice = this.openMidiDevice("Gervill");
                }
                if (this.synthDevice == null) {
                    this.errorMessage("Failed to find an available MIDI synthesizer");
                    return;
                }
            }
            if (this.synthesizer == null) {
                try {
                    this.sequencer.getTransmitter().setReceiver(this.synthDevice.getReceiver());
                }
                catch (MidiUnavailableException ex3) {
                    this.errorMessage("Unable to link sequencer transmitter with MIDI device receiver");
                }
            }
            else if (this.synthesizer.getDefaultSoundbank() == null) {
                try {
                    this.sequencer.getTransmitter().setReceiver(MidiSystem.getReceiver());
                }
                catch (MidiUnavailableException ex4) {
                    this.errorMessage("Unable to link sequencer transmitter with default receiver");
                }
            }
            else {
                try {
                    this.sequencer.getTransmitter().setReceiver(this.synthesizer.getReceiver());
                }
                catch (MidiUnavailableException ex5) {
                    this.errorMessage("Unable to link sequencer transmitter with synthesizer receiver");
                }
            }
        }
    }
    
    private Sequencer openSequencer(final String s) {
        final Sequencer sequencer = (Sequencer)this.openMidiDevice(s);
        if (sequencer == null) {
            return null;
        }
        try {
            sequencer.getTransmitter();
        }
        catch (MidiUnavailableException ex) {
            this.message("    Unable to get a transmitter from this sequencer");
            return null;
        }
        return sequencer;
    }
    
    private MidiDevice openMidiDevice(final String s) {
        this.message("Searching for MIDI device with name containing '" + s + "'");
        final MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < midiDeviceInfo.length; ++i) {
            MidiDevice midiDevice;
            try {
                midiDevice = MidiSystem.getMidiDevice(midiDeviceInfo[i]);
            }
            catch (MidiUnavailableException ex) {
                this.message("    Problem in method 'getMidiDevice':  MIDIUnavailableException was thrown");
                midiDevice = null;
            }
            if (midiDevice != null && midiDeviceInfo[i].getName().contains(s)) {
                this.message("    Found MIDI device named '" + midiDeviceInfo[i].getName() + "'");
                if (midiDevice instanceof Synthesizer) {
                    this.message("        *this is a Synthesizer instance");
                }
                if (midiDevice instanceof Sequencer) {
                    this.message("        *this is a Sequencer instance");
                }
                try {
                    midiDevice.open();
                }
                catch (MidiUnavailableException ex2) {
                    this.message("    Unable to open this MIDI device");
                    midiDevice = null;
                }
                return midiDevice;
            }
        }
        this.message("    MIDI device not found");
        return null;
    }
    
    protected void message(final String s) {
        this.logger.message(s, 0);
    }
    
    protected void importantMessage(final String s) {
        this.logger.importantMessage(s, 0);
    }
    
    protected boolean errorCheck(final boolean b, final String s) {
        return this.logger.errorCheck(b, "MidiChannel", s, 0);
    }
    
    protected void errorMessage(final String s) {
        this.logger.errorMessage("MidiChannel", s, 0);
    }
    
    protected void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
}
