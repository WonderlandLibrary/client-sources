/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package paulscode.sound;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;
import javax.sound.sampled.AudioFormat;
import paulscode.sound.CommandObject;
import paulscode.sound.CommandThread;
import paulscode.sound.FilenameURL;
import paulscode.sound.Library;
import paulscode.sound.ListenerData;
import paulscode.sound.MidiChannel;
import paulscode.sound.SoundBuffer;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.Source;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SoundSystem {
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    protected SoundSystemLogger logger = SoundSystemConfig.getLogger();
    protected Library soundLibrary;
    protected List<CommandObject> commandQueue;
    private List<CommandObject> sourcePlayList;
    protected CommandThread commandThread;
    public Random randomNumberGenerator;
    protected String className = "SoundSystem";
    private static Class currentLibrary = null;
    private static boolean initialized = false;
    private static SoundSystemException lastException = null;

    public SoundSystem() {
        if (this.logger == null) {
            this.logger = new SoundSystemLogger();
            SoundSystemConfig.setLogger(this.logger);
        }
        this.linkDefaultLibrariesAndCodecs();
        LinkedList<Class> libraries = SoundSystemConfig.getLibraries();
        if (libraries != null) {
            ListIterator i = libraries.listIterator();
            while (i.hasNext()) {
                Class c = (Class)i.next();
                try {
                    this.init(c);
                    return;
                } catch (SoundSystemException sse) {
                    this.logger.printExceptionMessage(sse, 1);
                }
            }
        }
        try {
            this.init(Library.class);
            return;
        } catch (SoundSystemException sse) {
            this.logger.printExceptionMessage(sse, 1);
            return;
        }
    }

    public SoundSystem(Class libraryClass) throws SoundSystemException {
        if (this.logger == null) {
            this.logger = new SoundSystemLogger();
            SoundSystemConfig.setLogger(this.logger);
        }
        this.linkDefaultLibrariesAndCodecs();
        this.init(libraryClass);
    }

    protected void linkDefaultLibrariesAndCodecs() {
    }

    protected void init(Class libraryClass) throws SoundSystemException {
        this.message("", 0);
        this.message("Starting up " + this.className + "...", 0);
        this.randomNumberGenerator = new Random();
        this.commandQueue = new LinkedList<CommandObject>();
        this.sourcePlayList = new LinkedList<CommandObject>();
        this.commandThread = new CommandThread(this);
        this.commandThread.start();
        SoundSystem.snooze(200L);
        this.newLibrary(libraryClass);
        this.message("", 0);
    }

    public void cleanup() {
        boolean killException = false;
        this.message("", 0);
        this.message(this.className + " shutting down...", 0);
        try {
            this.commandThread.kill();
            this.commandThread.interrupt();
        } catch (Exception e) {
            killException = true;
        }
        if (!killException) {
            for (int i = 0; i < 50 && this.commandThread.alive(); ++i) {
                SoundSystem.snooze(100L);
            }
        }
        if (killException || this.commandThread.alive()) {
            this.errorMessage("Command thread did not die!", 0);
            this.message("Ignoring errors... continuing clean-up.", 0);
        }
        SoundSystem.initialized(true, false);
        SoundSystem.currentLibrary(true, null);
        try {
            if (this.soundLibrary != null) {
                this.soundLibrary.cleanup();
            }
        } catch (Exception e) {
            this.errorMessage("Problem during Library.cleanup()!", 0);
            this.message("Ignoring errors... continuing clean-up.", 0);
        }
        try {
            if (this.commandQueue != null) {
                this.commandQueue.clear();
            }
        } catch (Exception e) {
            this.errorMessage("Unable to clear the command queue!", 0);
            this.message("Ignoring errors... continuing clean-up.", 0);
        }
        try {
            if (this.sourcePlayList != null) {
                this.sourcePlayList.clear();
            }
        } catch (Exception e) {
            this.errorMessage("Unable to clear the source management list!", 0);
            this.message("Ignoring errors... continuing clean-up.", 0);
        }
        this.randomNumberGenerator = null;
        this.soundLibrary = null;
        this.commandQueue = null;
        this.sourcePlayList = null;
        this.commandThread = null;
        this.importantMessage("Author: Paul Lamb, www.paulscode.com", 1);
        this.message("", 0);
    }

    public void interruptCommandThread() {
        if (this.commandThread == null) {
            this.errorMessage("Command Thread null in method 'interruptCommandThread'", 0);
            return;
        }
        this.commandThread.interrupt();
    }

    public void loadSound(String filename) {
        this.CommandQueue(new CommandObject(2, new FilenameURL(filename)));
        this.commandThread.interrupt();
    }

    public void loadSound(URL url, String identifier) {
        this.CommandQueue(new CommandObject(2, new FilenameURL(url, identifier)));
        this.commandThread.interrupt();
    }

    public void loadSound(byte[] data, AudioFormat format, String identifier) {
        this.CommandQueue(new CommandObject(3, identifier, new SoundBuffer(data, format)));
        this.commandThread.interrupt();
    }

    public void unloadSound(String filename) {
        this.CommandQueue(new CommandObject(4, filename));
        this.commandThread.interrupt();
    }

    public void queueSound(String sourcename, String filename) {
        this.CommandQueue(new CommandObject(5, sourcename, new FilenameURL(filename)));
        this.commandThread.interrupt();
    }

    public void queueSound(String sourcename, URL url, String identifier) {
        this.CommandQueue(new CommandObject(5, sourcename, new FilenameURL(url, identifier)));
        this.commandThread.interrupt();
    }

    public void dequeueSound(String sourcename, String filename) {
        this.CommandQueue(new CommandObject(6, sourcename, filename));
        this.commandThread.interrupt();
    }

    public void fadeOut(String sourcename, String filename, long milis) {
        FilenameURL fu = null;
        if (filename != null) {
            fu = new FilenameURL(filename);
        }
        this.CommandQueue(new CommandObject(7, sourcename, fu, milis));
        this.commandThread.interrupt();
    }

    public void fadeOut(String sourcename, URL url, String identifier, long milis) {
        FilenameURL fu = null;
        if (url != null && identifier != null) {
            fu = new FilenameURL(url, identifier);
        }
        this.CommandQueue(new CommandObject(7, sourcename, fu, milis));
        this.commandThread.interrupt();
    }

    public void fadeOutIn(String sourcename, String filename, long milisOut, long milisIn) {
        this.CommandQueue(new CommandObject(8, sourcename, new FilenameURL(filename), milisOut, milisIn));
        this.commandThread.interrupt();
    }

    public void fadeOutIn(String sourcename, URL url, String identifier, long milisOut, long milisIn) {
        this.CommandQueue(new CommandObject(8, sourcename, new FilenameURL(url, identifier), milisOut, milisIn));
        this.commandThread.interrupt();
    }

    public void checkFadeVolumes() {
        this.CommandQueue(new CommandObject(9));
        this.commandThread.interrupt();
    }

    public void backgroundMusic(String sourcename, String filename, boolean toLoop) {
        this.CommandQueue(new CommandObject(12, true, true, toLoop, sourcename, new FilenameURL(filename), 0.0f, 0.0f, 0.0f, 0, 0.0f, false));
        this.CommandQueue(new CommandObject(24, sourcename));
        this.commandThread.interrupt();
    }

    public void backgroundMusic(String sourcename, URL url, String identifier, boolean toLoop) {
        this.CommandQueue(new CommandObject(12, true, true, toLoop, sourcename, new FilenameURL(url, identifier), 0.0f, 0.0f, 0.0f, 0, 0.0f, false));
        this.CommandQueue(new CommandObject(24, sourcename));
        this.commandThread.interrupt();
    }

    public void newSource(boolean priority, String sourcename, String filename, boolean toLoop, float x, float y, float z, int attmodel, float distOrRoll) {
        this.CommandQueue(new CommandObject(10, priority, false, toLoop, sourcename, new FilenameURL(filename), x, y, z, attmodel, distOrRoll));
        this.commandThread.interrupt();
    }

    public void newSource(boolean priority, String sourcename, URL url, String identifier, boolean toLoop, float x, float y, float z, int attmodel, float distOrRoll) {
        this.CommandQueue(new CommandObject(10, priority, false, toLoop, sourcename, new FilenameURL(url, identifier), x, y, z, attmodel, distOrRoll));
        this.commandThread.interrupt();
    }

    public void newStreamingSource(boolean priority, String sourcename, String filename, boolean toLoop, float x, float y, float z, int attmodel, float distOrRoll) {
        this.CommandQueue(new CommandObject(10, priority, true, toLoop, sourcename, new FilenameURL(filename), x, y, z, attmodel, distOrRoll));
        this.commandThread.interrupt();
    }

    public void newStreamingSource(boolean priority, String sourcename, URL url, String identifier, boolean toLoop, float x, float y, float z, int attmodel, float distOrRoll) {
        this.CommandQueue(new CommandObject(10, priority, true, toLoop, sourcename, new FilenameURL(url, identifier), x, y, z, attmodel, distOrRoll));
        this.commandThread.interrupt();
    }

    public void rawDataStream(AudioFormat audioFormat, boolean priority, String sourcename, float x, float y, float z, int attModel, float distOrRoll) {
        this.CommandQueue(new CommandObject(11, audioFormat, priority, sourcename, x, y, z, attModel, distOrRoll));
        this.commandThread.interrupt();
    }

    public String quickPlay(boolean priority, String filename, boolean toLoop, float x, float y, float z, int attmodel, float distOrRoll) {
        String sourcename = "Source_" + this.randomNumberGenerator.nextInt() + "_" + this.randomNumberGenerator.nextInt();
        this.CommandQueue(new CommandObject(12, priority, false, toLoop, sourcename, new FilenameURL(filename), x, y, z, attmodel, distOrRoll, true));
        this.CommandQueue(new CommandObject(24, sourcename));
        this.commandThread.interrupt();
        return sourcename;
    }

    public String quickPlay(boolean priority, URL url, String identifier, boolean toLoop, float x, float y, float z, int attmodel, float distOrRoll) {
        String sourcename = "Source_" + this.randomNumberGenerator.nextInt() + "_" + this.randomNumberGenerator.nextInt();
        this.CommandQueue(new CommandObject(12, priority, false, toLoop, sourcename, new FilenameURL(url, identifier), x, y, z, attmodel, distOrRoll, true));
        this.CommandQueue(new CommandObject(24, sourcename));
        this.commandThread.interrupt();
        return sourcename;
    }

    public String quickStream(boolean priority, String filename, boolean toLoop, float x, float y, float z, int attmodel, float distOrRoll) {
        String sourcename = "Source_" + this.randomNumberGenerator.nextInt() + "_" + this.randomNumberGenerator.nextInt();
        this.CommandQueue(new CommandObject(12, priority, true, toLoop, sourcename, new FilenameURL(filename), x, y, z, attmodel, distOrRoll, true));
        this.CommandQueue(new CommandObject(24, sourcename));
        this.commandThread.interrupt();
        return sourcename;
    }

    public String quickStream(boolean priority, URL url, String identifier, boolean toLoop, float x, float y, float z, int attmodel, float distOrRoll) {
        String sourcename = "Source_" + this.randomNumberGenerator.nextInt() + "_" + this.randomNumberGenerator.nextInt();
        this.CommandQueue(new CommandObject(12, priority, true, toLoop, sourcename, new FilenameURL(url, identifier), x, y, z, attmodel, distOrRoll, true));
        this.CommandQueue(new CommandObject(24, sourcename));
        this.commandThread.interrupt();
        return sourcename;
    }

    public void setPosition(String sourcename, float x, float y, float z) {
        this.CommandQueue(new CommandObject(13, sourcename, x, y, z));
        this.commandThread.interrupt();
    }

    public void setVolume(String sourcename, float value) {
        this.CommandQueue(new CommandObject(14, sourcename, value));
        this.commandThread.interrupt();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public float getVolume(String sourcename) {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            if (this.soundLibrary != null) {
                return this.soundLibrary.getVolume(sourcename);
            }
            return 0.0f;
        }
    }

    public void setPitch(String sourcename, float value) {
        this.CommandQueue(new CommandObject(15, sourcename, value));
        this.commandThread.interrupt();
    }

    public float getPitch(String sourcename) {
        if (this.soundLibrary != null) {
            return this.soundLibrary.getPitch(sourcename);
        }
        return 1.0f;
    }

    public void setPriority(String sourcename, boolean pri) {
        this.CommandQueue(new CommandObject(16, sourcename, pri));
        this.commandThread.interrupt();
    }

    public void setLooping(String sourcename, boolean lp) {
        this.CommandQueue(new CommandObject(17, sourcename, lp));
        this.commandThread.interrupt();
    }

    public void setAttenuation(String sourcename, int model) {
        this.CommandQueue(new CommandObject(18, sourcename, model));
        this.commandThread.interrupt();
    }

    public void setDistOrRoll(String sourcename, float dr) {
        this.CommandQueue(new CommandObject(19, sourcename, dr));
        this.commandThread.interrupt();
    }

    public void changeDopplerFactor(float dopplerFactor) {
        this.CommandQueue(new CommandObject(20, dopplerFactor));
        this.commandThread.interrupt();
    }

    public void changeDopplerVelocity(float dopplerVelocity) {
        this.CommandQueue(new CommandObject(21, dopplerVelocity));
        this.commandThread.interrupt();
    }

    public void setVelocity(String sourcename, float x, float y, float z) {
        this.CommandQueue(new CommandObject(22, sourcename, x, y, z));
        this.commandThread.interrupt();
    }

    public void setListenerVelocity(float x, float y, float z) {
        this.CommandQueue(new CommandObject(23, x, y, z));
        this.commandThread.interrupt();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public float millisecondsPlayed(String sourcename) {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            return this.soundLibrary.millisecondsPlayed(sourcename);
        }
    }

    public void feedRawAudioData(String sourcename, byte[] buffer) {
        this.CommandQueue(new CommandObject(25, sourcename, buffer));
        this.commandThread.interrupt();
    }

    public void play(String sourcename) {
        this.CommandQueue(new CommandObject(24, sourcename));
        this.commandThread.interrupt();
    }

    public void pause(String sourcename) {
        this.CommandQueue(new CommandObject(26, sourcename));
        this.commandThread.interrupt();
    }

    public void stop(String sourcename) {
        this.CommandQueue(new CommandObject(27, sourcename));
        this.commandThread.interrupt();
    }

    public void rewind(String sourcename) {
        this.CommandQueue(new CommandObject(28, sourcename));
        this.commandThread.interrupt();
    }

    public void flush(String sourcename) {
        this.CommandQueue(new CommandObject(29, sourcename));
        this.commandThread.interrupt();
    }

    public void cull(String sourcename) {
        this.CommandQueue(new CommandObject(30, sourcename));
        this.commandThread.interrupt();
    }

    public void activate(String sourcename) {
        this.CommandQueue(new CommandObject(31, sourcename));
        this.commandThread.interrupt();
    }

    public void setTemporary(String sourcename, boolean temporary) {
        this.CommandQueue(new CommandObject(32, sourcename, temporary));
        this.commandThread.interrupt();
    }

    public void removeSource(String sourcename) {
        this.CommandQueue(new CommandObject(33, sourcename));
        this.commandThread.interrupt();
    }

    public void moveListener(float x, float y, float z) {
        this.CommandQueue(new CommandObject(34, x, y, z));
        this.commandThread.interrupt();
    }

    public void setListenerPosition(float x, float y, float z) {
        this.CommandQueue(new CommandObject(35, x, y, z));
        this.commandThread.interrupt();
    }

    public void turnListener(float angle) {
        this.CommandQueue(new CommandObject(36, angle));
        this.commandThread.interrupt();
    }

    public void setListenerAngle(float angle) {
        this.CommandQueue(new CommandObject(37, angle));
        this.commandThread.interrupt();
    }

    public void setListenerOrientation(float lookX, float lookY, float lookZ, float upX, float upY, float upZ) {
        this.CommandQueue(new CommandObject(38, lookX, lookY, lookZ, upX, upY, upZ));
        this.commandThread.interrupt();
    }

    public void setMasterVolume(float value) {
        this.CommandQueue(new CommandObject(39, value));
        this.commandThread.interrupt();
    }

    public float getMasterVolume() {
        return SoundSystemConfig.getMasterGain();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ListenerData getListenerData() {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            return this.soundLibrary.getListenerData();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean switchLibrary(Class libraryClass) throws SoundSystemException {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            SoundSystem.initialized(true, false);
            HashMap<String, Source> sourceMap = null;
            ListenerData listenerData = null;
            boolean wasMidiChannel = false;
            MidiChannel midiChannel = null;
            FilenameURL midiFilenameURL = null;
            String midiSourcename = "";
            boolean midiToLoop = true;
            if (this.soundLibrary != null) {
                SoundSystem.currentLibrary(true, null);
                sourceMap = this.copySources(this.soundLibrary.getSources());
                listenerData = this.soundLibrary.getListenerData();
                midiChannel = this.soundLibrary.getMidiChannel();
                if (midiChannel != null) {
                    wasMidiChannel = true;
                    midiToLoop = midiChannel.getLooping();
                    midiSourcename = midiChannel.getSourcename();
                    midiFilenameURL = midiChannel.getFilenameURL();
                }
                this.soundLibrary.cleanup();
                this.soundLibrary = null;
            }
            this.message("", 0);
            this.message("Switching to " + SoundSystemConfig.getLibraryTitle(libraryClass), 0);
            this.message("(" + SoundSystemConfig.getLibraryDescription(libraryClass) + ")", 1);
            try {
                this.soundLibrary = (Library)libraryClass.newInstance();
            } catch (InstantiationException ie) {
                this.errorMessage("The specified library did not load properly", 1);
            } catch (IllegalAccessException iae) {
                this.errorMessage("The specified library did not load properly", 1);
            } catch (ExceptionInInitializerError eiie) {
                this.errorMessage("The specified library did not load properly", 1);
            } catch (SecurityException se) {
                this.errorMessage("The specified library did not load properly", 1);
            }
            if (this.errorCheck(this.soundLibrary == null, "Library null after initialization in method 'switchLibrary'", 1)) {
                SoundSystemException sse = new SoundSystemException(this.className + " did not load properly.  " + "Library was null after initialization.", 4);
                SoundSystem.lastException(true, sse);
                SoundSystem.initialized(true, true);
                throw sse;
            }
            try {
                this.soundLibrary.init();
            } catch (SoundSystemException sse) {
                SoundSystem.lastException(true, sse);
                SoundSystem.initialized(true, true);
                throw sse;
            }
            this.soundLibrary.setListenerData(listenerData);
            if (wasMidiChannel) {
                if (midiChannel != null) {
                    midiChannel.cleanup();
                }
                midiChannel = new MidiChannel(midiToLoop, midiSourcename, midiFilenameURL);
                this.soundLibrary.setMidiChannel(midiChannel);
            }
            this.soundLibrary.copySources(sourceMap);
            this.message("", 0);
            SoundSystem.lastException(true, null);
            SoundSystem.initialized(true, true);
            return true;
        }
    }

    public boolean newLibrary(Class libraryClass) throws SoundSystemException {
        SoundSystem.initialized(true, false);
        this.CommandQueue(new CommandObject(40, libraryClass));
        this.commandThread.interrupt();
        for (int x = 0; !SoundSystem.initialized(false, false) && x < 100; ++x) {
            SoundSystem.snooze(400L);
            this.commandThread.interrupt();
        }
        if (!SoundSystem.initialized(false, false)) {
            SoundSystemException sse = new SoundSystemException(this.className + " did not load after 30 seconds.", 4);
            SoundSystem.lastException(true, sse);
            throw sse;
        }
        SoundSystemException sse = SoundSystem.lastException(false, null);
        if (sse != null) {
            throw sse;
        }
        return true;
    }

    private void CommandNewLibrary(Class libraryClass) {
        SoundSystem.initialized(true, false);
        String headerMessage = "Initializing ";
        if (this.soundLibrary != null) {
            SoundSystem.currentLibrary(true, null);
            headerMessage = "Switching to ";
            this.soundLibrary.cleanup();
            this.soundLibrary = null;
        }
        this.message(headerMessage + SoundSystemConfig.getLibraryTitle(libraryClass), 0);
        this.message("(" + SoundSystemConfig.getLibraryDescription(libraryClass) + ")", 1);
        try {
            this.soundLibrary = (Library)libraryClass.newInstance();
        } catch (InstantiationException ie) {
            this.errorMessage("The specified library did not load properly", 1);
        } catch (IllegalAccessException iae) {
            this.errorMessage("The specified library did not load properly", 1);
        } catch (ExceptionInInitializerError eiie) {
            this.errorMessage("The specified library did not load properly", 1);
        } catch (SecurityException se) {
            this.errorMessage("The specified library did not load properly", 1);
        }
        if (this.errorCheck(this.soundLibrary == null, "Library null after initialization in method 'newLibrary'", 1)) {
            SoundSystem.lastException(true, new SoundSystemException(this.className + " did not load properly.  " + "Library was null after initialization.", 4));
            this.importantMessage("Switching to silent mode", 1);
            try {
                this.soundLibrary = new Library();
            } catch (SoundSystemException sse) {
                SoundSystem.lastException(true, new SoundSystemException("Silent mode did not load properly.  Library was null after initialization.", 4));
                SoundSystem.initialized(true, true);
                return;
            }
        }
        try {
            this.soundLibrary.init();
        } catch (SoundSystemException sse) {
            SoundSystem.lastException(true, sse);
            SoundSystem.initialized(true, true);
            return;
        }
        SoundSystem.lastException(true, null);
        SoundSystem.initialized(true, true);
    }

    private void CommandInitialize() {
        try {
            if (this.errorCheck(this.soundLibrary == null, "Library null after initialization in method 'CommandInitialize'", 1)) {
                SoundSystemException sse = new SoundSystemException(this.className + " did not load properly.  " + "Library was null after initialization.", 4);
                SoundSystem.lastException(true, sse);
                throw sse;
            }
            this.soundLibrary.init();
        } catch (SoundSystemException sse) {
            SoundSystem.lastException(true, sse);
            SoundSystem.initialized(true, true);
        }
    }

    private void CommandLoadSound(FilenameURL filenameURL) {
        if (this.soundLibrary != null) {
            this.soundLibrary.loadSound(filenameURL);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandLoadSound'", 0);
        }
    }

    private void CommandLoadSound(SoundBuffer buffer, String identifier) {
        if (this.soundLibrary != null) {
            this.soundLibrary.loadSound(buffer, identifier);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandLoadSound'", 0);
        }
    }

    private void CommandUnloadSound(String filename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.unloadSound(filename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandLoadSound'", 0);
        }
    }

    private void CommandQueueSound(String sourcename, FilenameURL filenameURL) {
        if (this.soundLibrary != null) {
            this.soundLibrary.queueSound(sourcename, filenameURL);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandQueueSound'", 0);
        }
    }

    private void CommandDequeueSound(String sourcename, String filename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.dequeueSound(sourcename, filename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandDequeueSound'", 0);
        }
    }

    private void CommandFadeOut(String sourcename, FilenameURL filenameURL, long milis) {
        if (this.soundLibrary != null) {
            this.soundLibrary.fadeOut(sourcename, filenameURL, milis);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandFadeOut'", 0);
        }
    }

    private void CommandFadeOutIn(String sourcename, FilenameURL filenameURL, long milisOut, long milisIn) {
        if (this.soundLibrary != null) {
            this.soundLibrary.fadeOutIn(sourcename, filenameURL, milisOut, milisIn);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandFadeOutIn'", 0);
        }
    }

    private void CommandCheckFadeVolumes() {
        if (this.soundLibrary != null) {
            this.soundLibrary.checkFadeVolumes();
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandCheckFadeVolumes'", 0);
        }
    }

    private void CommandNewSource(boolean priority, boolean toStream, boolean toLoop, String sourcename, FilenameURL filenameURL, float x, float y, float z, int attModel, float distORroll) {
        if (this.soundLibrary != null) {
            if (filenameURL.getFilename().matches(SoundSystemConfig.EXTENSION_MIDI) && !SoundSystemConfig.midiCodec()) {
                this.soundLibrary.loadMidi(toLoop, sourcename, filenameURL);
            } else {
                this.soundLibrary.newSource(priority, toStream, toLoop, sourcename, filenameURL, x, y, z, attModel, distORroll);
            }
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandNewSource'", 0);
        }
    }

    private void CommandRawDataStream(AudioFormat audioFormat, boolean priority, String sourcename, float x, float y, float z, int attModel, float distOrRoll) {
        if (this.soundLibrary != null) {
            this.soundLibrary.rawDataStream(audioFormat, priority, sourcename, x, y, z, attModel, distOrRoll);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandRawDataStream'", 0);
        }
    }

    private void CommandQuickPlay(boolean priority, boolean toStream, boolean toLoop, String sourcename, FilenameURL filenameURL, float x, float y, float z, int attModel, float distORroll, boolean temporary) {
        if (this.soundLibrary != null) {
            if (filenameURL.getFilename().matches(SoundSystemConfig.EXTENSION_MIDI) && !SoundSystemConfig.midiCodec()) {
                this.soundLibrary.loadMidi(toLoop, sourcename, filenameURL);
            } else {
                this.soundLibrary.quickPlay(priority, toStream, toLoop, sourcename, filenameURL, x, y, z, attModel, distORroll, temporary);
            }
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandQuickPlay'", 0);
        }
    }

    private void CommandSetPosition(String sourcename, float x, float y, float z) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setPosition(sourcename, x, y, z);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandMoveSource'", 0);
        }
    }

    private void CommandSetVolume(String sourcename, float value) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setVolume(sourcename, value);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetVolume'", 0);
        }
    }

    private void CommandSetPitch(String sourcename, float value) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setPitch(sourcename, value);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetPitch'", 0);
        }
    }

    private void CommandSetPriority(String sourcename, boolean pri) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setPriority(sourcename, pri);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetPriority'", 0);
        }
    }

    private void CommandSetLooping(String sourcename, boolean lp) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setLooping(sourcename, lp);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetLooping'", 0);
        }
    }

    private void CommandSetAttenuation(String sourcename, int model) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setAttenuation(sourcename, model);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetAttenuation'", 0);
        }
    }

    private void CommandSetDistOrRoll(String sourcename, float dr) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setDistOrRoll(sourcename, dr);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetDistOrRoll'", 0);
        }
    }

    private void CommandChangeDopplerFactor(float dopplerFactor) {
        if (this.soundLibrary != null) {
            SoundSystemConfig.setDopplerFactor(dopplerFactor);
            this.soundLibrary.dopplerChanged();
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetDopplerFactor'", 0);
        }
    }

    private void CommandChangeDopplerVelocity(float dopplerVelocity) {
        if (this.soundLibrary != null) {
            SoundSystemConfig.setDopplerVelocity(dopplerVelocity);
            this.soundLibrary.dopplerChanged();
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetDopplerFactor'", 0);
        }
    }

    private void CommandSetVelocity(String sourcename, float x, float y, float z) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setVelocity(sourcename, x, y, z);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandVelocity'", 0);
        }
    }

    private void CommandSetListenerVelocity(float x, float y, float z) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setListenerVelocity(x, y, z);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetListenerVelocity'", 0);
        }
    }

    private void CommandPlay(String sourcename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.play(sourcename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandPlay'", 0);
        }
    }

    private void CommandFeedRawAudioData(String sourcename, byte[] buffer) {
        if (this.soundLibrary != null) {
            this.soundLibrary.feedRawAudioData(sourcename, buffer);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandFeedRawAudioData'", 0);
        }
    }

    private void CommandPause(String sourcename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.pause(sourcename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandPause'", 0);
        }
    }

    private void CommandStop(String sourcename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.stop(sourcename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandStop'", 0);
        }
    }

    private void CommandRewind(String sourcename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.rewind(sourcename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandRewind'", 0);
        }
    }

    private void CommandFlush(String sourcename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.flush(sourcename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandFlush'", 0);
        }
    }

    private void CommandSetTemporary(String sourcename, boolean temporary) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setTemporary(sourcename, temporary);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetActive'", 0);
        }
    }

    private void CommandRemoveSource(String sourcename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.removeSource(sourcename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandRemoveSource'", 0);
        }
    }

    private void CommandMoveListener(float x, float y, float z) {
        if (this.soundLibrary != null) {
            this.soundLibrary.moveListener(x, y, z);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandMoveListener'", 0);
        }
    }

    private void CommandSetListenerPosition(float x, float y, float z) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setListenerPosition(x, y, z);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetListenerPosition'", 0);
        }
    }

    private void CommandTurnListener(float angle) {
        if (this.soundLibrary != null) {
            this.soundLibrary.turnListener(angle);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandTurnListener'", 0);
        }
    }

    private void CommandSetListenerAngle(float angle) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setListenerAngle(angle);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetListenerAngle'", 0);
        }
    }

    private void CommandSetListenerOrientation(float lookX, float lookY, float lookZ, float upX, float upY, float upZ) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setListenerOrientation(lookX, lookY, lookZ, upX, upY, upZ);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetListenerOrientation'", 0);
        }
    }

    private void CommandCull(String sourcename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.cull(sourcename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandCull'", 0);
        }
    }

    private void CommandActivate(String sourcename) {
        if (this.soundLibrary != null) {
            this.soundLibrary.activate(sourcename);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandActivate'", 0);
        }
    }

    private void CommandSetMasterVolume(float value) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setMasterVolume(value);
        } else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetMasterVolume'", 0);
        }
    }

    protected void ManageSources() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean CommandQueue(CommandObject newCommand) {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            if (newCommand == null) {
                CommandObject commandObject;
                boolean activations = false;
                block49: while (this.commandQueue != null && this.commandQueue.size() > 0) {
                    commandObject = this.commandQueue.remove(0);
                    if (commandObject == null) continue;
                    switch (commandObject.Command) {
                        case 1: {
                            this.CommandInitialize();
                            continue block49;
                        }
                        case 2: {
                            this.CommandLoadSound((FilenameURL)commandObject.objectArgs[0]);
                            continue block49;
                        }
                        case 3: {
                            this.CommandLoadSound((SoundBuffer)commandObject.objectArgs[0], commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 4: {
                            this.CommandUnloadSound(commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 5: {
                            this.CommandQueueSound(commandObject.stringArgs[0], (FilenameURL)commandObject.objectArgs[0]);
                            continue block49;
                        }
                        case 6: {
                            this.CommandDequeueSound(commandObject.stringArgs[0], commandObject.stringArgs[1]);
                            continue block49;
                        }
                        case 7: {
                            this.CommandFadeOut(commandObject.stringArgs[0], (FilenameURL)commandObject.objectArgs[0], commandObject.longArgs[0]);
                            continue block49;
                        }
                        case 8: {
                            this.CommandFadeOutIn(commandObject.stringArgs[0], (FilenameURL)commandObject.objectArgs[0], commandObject.longArgs[0], commandObject.longArgs[1]);
                            continue block49;
                        }
                        case 9: {
                            this.CommandCheckFadeVolumes();
                            continue block49;
                        }
                        case 10: {
                            this.CommandNewSource(commandObject.boolArgs[0], commandObject.boolArgs[1], commandObject.boolArgs[2], commandObject.stringArgs[0], (FilenameURL)commandObject.objectArgs[0], commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2], commandObject.intArgs[0], commandObject.floatArgs[3]);
                            continue block49;
                        }
                        case 11: {
                            this.CommandRawDataStream((AudioFormat)commandObject.objectArgs[0], commandObject.boolArgs[0], commandObject.stringArgs[0], commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2], commandObject.intArgs[0], commandObject.floatArgs[3]);
                            continue block49;
                        }
                        case 12: {
                            this.CommandQuickPlay(commandObject.boolArgs[0], commandObject.boolArgs[1], commandObject.boolArgs[2], commandObject.stringArgs[0], (FilenameURL)commandObject.objectArgs[0], commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2], commandObject.intArgs[0], commandObject.floatArgs[3], commandObject.boolArgs[3]);
                            continue block49;
                        }
                        case 13: {
                            this.CommandSetPosition(commandObject.stringArgs[0], commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2]);
                            continue block49;
                        }
                        case 14: {
                            this.CommandSetVolume(commandObject.stringArgs[0], commandObject.floatArgs[0]);
                            continue block49;
                        }
                        case 15: {
                            this.CommandSetPitch(commandObject.stringArgs[0], commandObject.floatArgs[0]);
                            continue block49;
                        }
                        case 16: {
                            this.CommandSetPriority(commandObject.stringArgs[0], commandObject.boolArgs[0]);
                            continue block49;
                        }
                        case 17: {
                            this.CommandSetLooping(commandObject.stringArgs[0], commandObject.boolArgs[0]);
                            continue block49;
                        }
                        case 18: {
                            this.CommandSetAttenuation(commandObject.stringArgs[0], commandObject.intArgs[0]);
                            continue block49;
                        }
                        case 19: {
                            this.CommandSetDistOrRoll(commandObject.stringArgs[0], commandObject.floatArgs[0]);
                            continue block49;
                        }
                        case 20: {
                            this.CommandChangeDopplerFactor(commandObject.floatArgs[0]);
                            continue block49;
                        }
                        case 21: {
                            this.CommandChangeDopplerVelocity(commandObject.floatArgs[0]);
                            continue block49;
                        }
                        case 22: {
                            this.CommandSetVelocity(commandObject.stringArgs[0], commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2]);
                            continue block49;
                        }
                        case 23: {
                            this.CommandSetListenerVelocity(commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2]);
                            continue block49;
                        }
                        case 24: {
                            this.sourcePlayList.add(commandObject);
                            continue block49;
                        }
                        case 25: {
                            this.sourcePlayList.add(commandObject);
                            continue block49;
                        }
                        case 26: {
                            this.CommandPause(commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 27: {
                            this.CommandStop(commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 28: {
                            this.CommandRewind(commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 29: {
                            this.CommandFlush(commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 30: {
                            this.CommandCull(commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 31: {
                            activations = true;
                            this.CommandActivate(commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 32: {
                            this.CommandSetTemporary(commandObject.stringArgs[0], commandObject.boolArgs[0]);
                            continue block49;
                        }
                        case 33: {
                            this.CommandRemoveSource(commandObject.stringArgs[0]);
                            continue block49;
                        }
                        case 34: {
                            this.CommandMoveListener(commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2]);
                            continue block49;
                        }
                        case 35: {
                            this.CommandSetListenerPosition(commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2]);
                            continue block49;
                        }
                        case 36: {
                            this.CommandTurnListener(commandObject.floatArgs[0]);
                            continue block49;
                        }
                        case 37: {
                            this.CommandSetListenerAngle(commandObject.floatArgs[0]);
                            continue block49;
                        }
                        case 38: {
                            this.CommandSetListenerOrientation(commandObject.floatArgs[0], commandObject.floatArgs[1], commandObject.floatArgs[2], commandObject.floatArgs[3], commandObject.floatArgs[4], commandObject.floatArgs[5]);
                            continue block49;
                        }
                        case 39: {
                            this.CommandSetMasterVolume(commandObject.floatArgs[0]);
                            continue block49;
                        }
                        case 40: {
                            this.CommandNewLibrary(commandObject.classArgs[0]);
                            continue block49;
                        }
                    }
                }
                if (activations) {
                    this.soundLibrary.replaySources();
                }
                while (this.sourcePlayList != null && this.sourcePlayList.size() > 0) {
                    commandObject = this.sourcePlayList.remove(0);
                    if (commandObject == null) continue;
                    switch (commandObject.Command) {
                        case 24: {
                            this.CommandPlay(commandObject.stringArgs[0]);
                            break;
                        }
                        case 25: {
                            this.CommandFeedRawAudioData(commandObject.stringArgs[0], commandObject.buffer);
                        }
                    }
                }
                return this.commandQueue != null && this.commandQueue.size() > 0;
            }
            if (this.commandQueue == null) {
                return false;
            }
            this.commandQueue.add(newCommand);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeTemporarySources() {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            if (this.soundLibrary != null) {
                this.soundLibrary.removeTemporarySources();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean playing(String sourcename) {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            if (this.soundLibrary == null) {
                return false;
            }
            Source src = this.soundLibrary.getSources().get(sourcename);
            if (src == null) {
                return false;
            }
            return src.playing();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean playing() {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            if (this.soundLibrary == null) {
                return false;
            }
            HashMap<String, Source> sourceMap = this.soundLibrary.getSources();
            if (sourceMap == null) {
                return false;
            }
            Set<String> keys = sourceMap.keySet();
            for (String sourcename : keys) {
                Source source = sourceMap.get(sourcename);
                if (source == null || !source.playing()) continue;
                return true;
            }
            return false;
        }
    }

    private HashMap<String, Source> copySources(HashMap<String, Source> sourceMap) {
        Set<String> keys = sourceMap.keySet();
        Iterator<String> iter = keys.iterator();
        HashMap<String, Source> returnMap = new HashMap<String, Source>();
        while (iter.hasNext()) {
            String sourcename = iter.next();
            Source source = sourceMap.get(sourcename);
            if (source == null) continue;
            returnMap.put(sourcename, new Source(source, null));
        }
        return returnMap;
    }

    public static boolean libraryCompatible(Class libraryClass) {
        SoundSystemLogger logger = SoundSystemConfig.getLogger();
        if (logger == null) {
            logger = new SoundSystemLogger();
            SoundSystemConfig.setLogger(logger);
        }
        logger.message("", 0);
        logger.message("Checking if " + SoundSystemConfig.getLibraryTitle(libraryClass) + " is compatible...", 0);
        boolean comp = SoundSystemConfig.libraryCompatible(libraryClass);
        if (comp) {
            logger.message("...yes", 1);
        } else {
            logger.message("...no", 1);
        }
        return comp;
    }

    public static Class currentLibrary() {
        return SoundSystem.currentLibrary(false, null);
    }

    public static boolean initialized() {
        return SoundSystem.initialized(false, false);
    }

    public static SoundSystemException getLastException() {
        return SoundSystem.lastException(false, null);
    }

    public static void setException(SoundSystemException e) {
        SoundSystem.lastException(true, e);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean initialized(boolean action, boolean value) {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            if (action) {
                initialized = value;
            }
            return initialized;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Class currentLibrary(boolean action, Class value) {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            if (action) {
                currentLibrary = value;
            }
            return currentLibrary;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static SoundSystemException lastException(boolean action, SoundSystemException e) {
        Object object = SoundSystemConfig.THREAD_SYNC;
        synchronized (object) {
            if (action) {
                lastException = e;
            }
            return lastException;
        }
    }

    protected static void snooze(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    protected void message(String message, int indent) {
        this.logger.message(message, indent);
    }

    protected void importantMessage(String message, int indent) {
        this.logger.importantMessage(message, indent);
    }

    protected boolean errorCheck(boolean error, String message, int indent) {
        return this.logger.errorCheck(error, this.className, message, indent);
    }

    protected void errorMessage(String message, int indent) {
        this.logger.errorMessage(this.className, message, indent);
    }
}

