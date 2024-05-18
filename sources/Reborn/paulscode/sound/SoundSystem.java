package paulscode.sound;

import java.net.*;
import javax.sound.sampled.*;
import java.util.*;

public class SoundSystem
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    protected SoundSystemLogger logger;
    protected Library soundLibrary;
    protected List commandQueue;
    private List sourcePlayList;
    protected CommandThread commandThread;
    public Random randomNumberGenerator;
    protected String className;
    private static Class currentLibrary;
    private static boolean initialized;
    private static SoundSystemException lastException;
    
    public SoundSystem() {
        this.className = "SoundSystem";
        this.logger = SoundSystemConfig.getLogger();
        if (this.logger == null) {
            SoundSystemConfig.setLogger(this.logger = new SoundSystemLogger());
        }
        this.linkDefaultLibrariesAndCodecs();
        final LinkedList libraries = SoundSystemConfig.getLibraries();
        if (libraries != null) {
            final ListIterator<Class> listIterator = (ListIterator<Class>)libraries.listIterator();
            while (listIterator.hasNext()) {
                final Class clazz = listIterator.next();
                try {
                    this.init(clazz);
                    return;
                }
                catch (SoundSystemException ex) {
                    this.logger.printExceptionMessage(ex, 1);
                    continue;
                }
                break;
            }
        }
        try {
            this.init(Library.class);
        }
        catch (SoundSystemException ex2) {
            this.logger.printExceptionMessage(ex2, 1);
        }
    }
    
    public SoundSystem(final Class clazz) {
        this.className = "SoundSystem";
        this.logger = SoundSystemConfig.getLogger();
        if (this.logger == null) {
            SoundSystemConfig.setLogger(this.logger = new SoundSystemLogger());
        }
        this.linkDefaultLibrariesAndCodecs();
        this.init(clazz);
    }
    
    protected void linkDefaultLibrariesAndCodecs() {
    }
    
    protected void init(final Class clazz) {
        this.message("", 0);
        this.message("Starting up " + this.className + "...", 0);
        this.randomNumberGenerator = new Random();
        this.commandQueue = new LinkedList();
        this.sourcePlayList = new LinkedList();
        (this.commandThread = new CommandThread(this)).start();
        snooze(200L);
        this.newLibrary(clazz);
        this.message("", 0);
    }
    
    public void cleanup() {
        boolean b = false;
        this.message("", 0);
        this.message(this.className + " shutting down...", 0);
        try {
            this.commandThread.kill();
            this.commandThread.interrupt();
        }
        catch (Exception ex) {
            b = true;
        }
        if (!b) {
            for (int i = 0; i < 50; ++i) {
                if (!this.commandThread.alive()) {
                    break;
                }
                snooze(100L);
            }
        }
        if (b || this.commandThread.alive()) {
            this.errorMessage("Command thread did not die!", 0);
            this.message("Ignoring errors... continuing clean-up.", 0);
        }
        initialized(true, false);
        currentLibrary(true, null);
        try {
            if (this.soundLibrary != null) {
                this.soundLibrary.cleanup();
            }
        }
        catch (Exception ex2) {
            this.errorMessage("Problem during Library.cleanup()!", 0);
            this.message("Ignoring errors... continuing clean-up.", 0);
        }
        try {
            if (this.commandQueue != null) {
                this.commandQueue.clear();
            }
        }
        catch (Exception ex3) {
            this.errorMessage("Unable to clear the command queue!", 0);
            this.message("Ignoring errors... continuing clean-up.", 0);
        }
        try {
            if (this.sourcePlayList != null) {
                this.sourcePlayList.clear();
            }
        }
        catch (Exception ex4) {
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
    
    public void loadSound(final String s) {
        this.CommandQueue(new CommandObject(2, new FilenameURL(s)));
        this.commandThread.interrupt();
    }
    
    public void loadSound(final URL url, final String s) {
        this.CommandQueue(new CommandObject(2, new FilenameURL(url, s)));
        this.commandThread.interrupt();
    }
    
    public void loadSound(final byte[] array, final AudioFormat audioFormat, final String s) {
        this.CommandQueue(new CommandObject(3, s, new SoundBuffer(array, audioFormat)));
        this.commandThread.interrupt();
    }
    
    public void unloadSound(final String s) {
        this.CommandQueue(new CommandObject(4, s));
        this.commandThread.interrupt();
    }
    
    public void queueSound(final String s, final String s2) {
        this.CommandQueue(new CommandObject(5, s, new FilenameURL(s2)));
        this.commandThread.interrupt();
    }
    
    public void queueSound(final String s, final URL url, final String s2) {
        this.CommandQueue(new CommandObject(5, s, new FilenameURL(url, s2)));
        this.commandThread.interrupt();
    }
    
    public void dequeueSound(final String s, final String s2) {
        this.CommandQueue(new CommandObject(6, s, s2));
        this.commandThread.interrupt();
    }
    
    public void fadeOut(final String s, final String s2, final long n) {
        Object o = null;
        if (s2 != null) {
            o = new FilenameURL(s2);
        }
        this.CommandQueue(new CommandObject(7, s, o, n));
        this.commandThread.interrupt();
    }
    
    public void fadeOut(final String s, final URL url, final String s2, final long n) {
        Object o = null;
        if (url != null && s2 != null) {
            o = new FilenameURL(url, s2);
        }
        this.CommandQueue(new CommandObject(7, s, o, n));
        this.commandThread.interrupt();
    }
    
    public void fadeOutIn(final String s, final String s2, final long n, final long n2) {
        this.CommandQueue(new CommandObject(8, s, new FilenameURL(s2), n, n2));
        this.commandThread.interrupt();
    }
    
    public void fadeOutIn(final String s, final URL url, final String s2, final long n, final long n2) {
        this.CommandQueue(new CommandObject(8, s, new FilenameURL(url, s2), n, n2));
        this.commandThread.interrupt();
    }
    
    public void checkFadeVolumes() {
        this.CommandQueue(new CommandObject(9));
        this.commandThread.interrupt();
    }
    
    public void backgroundMusic(final String s, final String s2, final boolean b) {
        this.CommandQueue(new CommandObject(12, true, true, b, s, new FilenameURL(s2), 0.0f, 0.0f, 0.0f, 0, 0.0f, false));
        this.CommandQueue(new CommandObject(24, s));
        this.commandThread.interrupt();
    }
    
    public void backgroundMusic(final String s, final URL url, final String s2, final boolean b) {
        this.CommandQueue(new CommandObject(12, true, true, b, s, new FilenameURL(url, s2), 0.0f, 0.0f, 0.0f, 0, 0.0f, false));
        this.CommandQueue(new CommandObject(24, s));
        this.commandThread.interrupt();
    }
    
    public void newSource(final boolean b, final String s, final String s2, final boolean b2, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.CommandQueue(new CommandObject(10, b, false, b2, s, new FilenameURL(s2), n, n2, n3, n4, n5));
        this.commandThread.interrupt();
    }
    
    public void newSource(final boolean b, final String s, final URL url, final String s2, final boolean b2, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.CommandQueue(new CommandObject(10, b, false, b2, s, new FilenameURL(url, s2), n, n2, n3, n4, n5));
        this.commandThread.interrupt();
    }
    
    public void newStreamingSource(final boolean b, final String s, final String s2, final boolean b2, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.CommandQueue(new CommandObject(10, b, true, b2, s, new FilenameURL(s2), n, n2, n3, n4, n5));
        this.commandThread.interrupt();
    }
    
    public void newStreamingSource(final boolean b, final String s, final URL url, final String s2, final boolean b2, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.CommandQueue(new CommandObject(10, b, true, b2, s, new FilenameURL(url, s2), n, n2, n3, n4, n5));
        this.commandThread.interrupt();
    }
    
    public void rawDataStream(final AudioFormat audioFormat, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.CommandQueue(new CommandObject(11, audioFormat, b, s, n, n2, n3, n4, n5));
        this.commandThread.interrupt();
    }
    
    public String quickPlay(final boolean b, final String s, final boolean b2, final float n, final float n2, final float n3, final int n4, final float n5) {
        final String string = "Source_" + this.randomNumberGenerator.nextInt() + "_" + this.randomNumberGenerator.nextInt();
        this.CommandQueue(new CommandObject(12, b, false, b2, string, new FilenameURL(s), n, n2, n3, n4, n5, true));
        this.CommandQueue(new CommandObject(24, string));
        this.commandThread.interrupt();
        return string;
    }
    
    public String quickPlay(final boolean b, final URL url, final String s, final boolean b2, final float n, final float n2, final float n3, final int n4, final float n5) {
        final String string = "Source_" + this.randomNumberGenerator.nextInt() + "_" + this.randomNumberGenerator.nextInt();
        this.CommandQueue(new CommandObject(12, b, false, b2, string, new FilenameURL(url, s), n, n2, n3, n4, n5, true));
        this.CommandQueue(new CommandObject(24, string));
        this.commandThread.interrupt();
        return string;
    }
    
    public String quickStream(final boolean b, final String s, final boolean b2, final float n, final float n2, final float n3, final int n4, final float n5) {
        final String string = "Source_" + this.randomNumberGenerator.nextInt() + "_" + this.randomNumberGenerator.nextInt();
        this.CommandQueue(new CommandObject(12, b, true, b2, string, new FilenameURL(s), n, n2, n3, n4, n5, true));
        this.CommandQueue(new CommandObject(24, string));
        this.commandThread.interrupt();
        return string;
    }
    
    public String quickStream(final boolean b, final URL url, final String s, final boolean b2, final float n, final float n2, final float n3, final int n4, final float n5) {
        final String string = "Source_" + this.randomNumberGenerator.nextInt() + "_" + this.randomNumberGenerator.nextInt();
        this.CommandQueue(new CommandObject(12, b, true, b2, string, new FilenameURL(url, s), n, n2, n3, n4, n5, true));
        this.CommandQueue(new CommandObject(24, string));
        this.commandThread.interrupt();
        return string;
    }
    
    public void setPosition(final String s, final float n, final float n2, final float n3) {
        this.CommandQueue(new CommandObject(13, s, n, n2, n3));
        this.commandThread.interrupt();
    }
    
    public void setVolume(final String s, final float n) {
        this.CommandQueue(new CommandObject(14, s, n));
        this.commandThread.interrupt();
    }
    
    public float getVolume(final String s) {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary != null) {
                return this.soundLibrary.getVolume(s);
            }
            return 0.0f;
        }
    }
    
    public void setPitch(final String s, final float n) {
        this.CommandQueue(new CommandObject(15, s, n));
        this.commandThread.interrupt();
    }
    
    public float getPitch(final String s) {
        if (this.soundLibrary != null) {
            return this.soundLibrary.getPitch(s);
        }
        return 1.0f;
    }
    
    public void setPriority(final String s, final boolean b) {
        this.CommandQueue(new CommandObject(16, s, b));
        this.commandThread.interrupt();
    }
    
    public void setLooping(final String s, final boolean b) {
        this.CommandQueue(new CommandObject(17, s, b));
        this.commandThread.interrupt();
    }
    
    public void setAttenuation(final String s, final int n) {
        this.CommandQueue(new CommandObject(18, s, n));
        this.commandThread.interrupt();
    }
    
    public void setDistOrRoll(final String s, final float n) {
        this.CommandQueue(new CommandObject(19, s, n));
        this.commandThread.interrupt();
    }
    
    public void changeDopplerFactor(final float n) {
        this.CommandQueue(new CommandObject(20, n));
        this.commandThread.interrupt();
    }
    
    public void changeDopplerVelocity(final float n) {
        this.CommandQueue(new CommandObject(21, n));
        this.commandThread.interrupt();
    }
    
    public void setVelocity(final String s, final float n, final float n2, final float n3) {
        this.CommandQueue(new CommandObject(22, s, n, n2, n3));
        this.commandThread.interrupt();
    }
    
    public void setListenerVelocity(final float n, final float n2, final float n3) {
        this.CommandQueue(new CommandObject(23, n, n2, n3));
        this.commandThread.interrupt();
    }
    
    public float millisecondsPlayed(final String s) {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            return this.soundLibrary.millisecondsPlayed(s);
        }
    }
    
    public void feedRawAudioData(final String s, final byte[] array) {
        this.CommandQueue(new CommandObject(25, s, array));
        this.commandThread.interrupt();
    }
    
    public void play(final String s) {
        this.CommandQueue(new CommandObject(24, s));
        this.commandThread.interrupt();
    }
    
    public void pause(final String s) {
        this.CommandQueue(new CommandObject(26, s));
        this.commandThread.interrupt();
    }
    
    public void stop(final String s) {
        this.CommandQueue(new CommandObject(27, s));
        this.commandThread.interrupt();
    }
    
    public void rewind(final String s) {
        this.CommandQueue(new CommandObject(28, s));
        this.commandThread.interrupt();
    }
    
    public void flush(final String s) {
        this.CommandQueue(new CommandObject(29, s));
        this.commandThread.interrupt();
    }
    
    public void cull(final String s) {
        this.CommandQueue(new CommandObject(30, s));
        this.commandThread.interrupt();
    }
    
    public void activate(final String s) {
        this.CommandQueue(new CommandObject(31, s));
        this.commandThread.interrupt();
    }
    
    public void setTemporary(final String s, final boolean b) {
        this.CommandQueue(new CommandObject(32, s, b));
        this.commandThread.interrupt();
    }
    
    public void removeSource(final String s) {
        this.CommandQueue(new CommandObject(33, s));
        this.commandThread.interrupt();
    }
    
    public void moveListener(final float n, final float n2, final float n3) {
        this.CommandQueue(new CommandObject(34, n, n2, n3));
        this.commandThread.interrupt();
    }
    
    public void setListenerPosition(final float n, final float n2, final float n3) {
        this.CommandQueue(new CommandObject(35, n, n2, n3));
        this.commandThread.interrupt();
    }
    
    public void turnListener(final float n) {
        this.CommandQueue(new CommandObject(36, n));
        this.commandThread.interrupt();
    }
    
    public void setListenerAngle(final float n) {
        this.CommandQueue(new CommandObject(37, n));
        this.commandThread.interrupt();
    }
    
    public void setListenerOrientation(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.CommandQueue(new CommandObject(38, n, n2, n3, n4, n5, n6));
        this.commandThread.interrupt();
    }
    
    public void setMasterVolume(final float n) {
        this.CommandQueue(new CommandObject(39, n));
        this.commandThread.interrupt();
    }
    
    public float getMasterVolume() {
        return SoundSystemConfig.getMasterGain();
    }
    
    public ListenerData getListenerData() {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            return this.soundLibrary.getListenerData();
        }
    }
    
    public boolean switchLibrary(final Class clazz) {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            initialized(true, false);
            HashMap copySources = null;
            ListenerData listenerData = null;
            boolean b = false;
            MidiChannel midiChannel = null;
            FilenameURL filenameURL = null;
            String sourcename = "";
            boolean looping = true;
            if (this.soundLibrary != null) {
                currentLibrary(true, null);
                copySources = this.copySources(this.soundLibrary.getSources());
                listenerData = this.soundLibrary.getListenerData();
                midiChannel = this.soundLibrary.getMidiChannel();
                if (midiChannel != null) {
                    b = true;
                    looping = midiChannel.getLooping();
                    sourcename = midiChannel.getSourcename();
                    filenameURL = midiChannel.getFilenameURL();
                }
                this.soundLibrary.cleanup();
                this.soundLibrary = null;
            }
            this.message("", 0);
            this.message("Switching to " + SoundSystemConfig.getLibraryTitle(clazz), 0);
            this.message("(" + SoundSystemConfig.getLibraryDescription(clazz) + ")", 1);
            try {
                this.soundLibrary = clazz.newInstance();
            }
            catch (InstantiationException ex3) {
                this.errorMessage("The specified library did not load properly", 1);
            }
            catch (IllegalAccessException ex4) {
                this.errorMessage("The specified library did not load properly", 1);
            }
            catch (ExceptionInInitializerError exceptionInInitializerError) {
                this.errorMessage("The specified library did not load properly", 1);
            }
            catch (SecurityException ex5) {
                this.errorMessage("The specified library did not load properly", 1);
            }
            if (this.errorCheck(this.soundLibrary == null, "Library null after initialization in method 'switchLibrary'", 1)) {
                final SoundSystemException ex = new SoundSystemException(this.className + " did not load properly.  " + "Library was null after initialization.", 4);
                lastException(true, ex);
                initialized(true, true);
                throw ex;
            }
            try {
                this.soundLibrary.init();
            }
            catch (SoundSystemException ex2) {
                lastException(true, ex2);
                initialized(true, true);
                throw ex2;
            }
            this.soundLibrary.setListenerData(listenerData);
            if (b) {
                if (midiChannel != null) {
                    midiChannel.cleanup();
                }
                this.soundLibrary.setMidiChannel(new MidiChannel(looping, sourcename, filenameURL));
            }
            this.soundLibrary.copySources(copySources);
            this.message("", 0);
            lastException(true, null);
            initialized(true, true);
            return true;
        }
    }
    
    public boolean newLibrary(final Class clazz) {
        initialized(true, false);
        this.CommandQueue(new CommandObject(40, clazz));
        this.commandThread.interrupt();
        for (int n = 0; !initialized(false, false) && n < 100; ++n) {
            snooze(400L);
            this.commandThread.interrupt();
        }
        if (!initialized(false, false)) {
            final SoundSystemException ex = new SoundSystemException(this.className + " did not load after 30 seconds.", 4);
            lastException(true, ex);
            throw ex;
        }
        final SoundSystemException lastException = lastException(false, null);
        if (lastException != null) {
            throw lastException;
        }
        return true;
    }
    
    private void CommandNewLibrary(final Class clazz) {
        initialized(true, false);
        String s = "Initializing ";
        if (this.soundLibrary != null) {
            currentLibrary(true, null);
            s = "Switching to ";
            this.soundLibrary.cleanup();
            this.soundLibrary = null;
        }
        this.message(s + SoundSystemConfig.getLibraryTitle(clazz), 0);
        this.message("(" + SoundSystemConfig.getLibraryDescription(clazz) + ")", 1);
        try {
            this.soundLibrary = clazz.newInstance();
        }
        catch (InstantiationException ex2) {
            this.errorMessage("The specified library did not load properly", 1);
        }
        catch (IllegalAccessException ex3) {
            this.errorMessage("The specified library did not load properly", 1);
        }
        catch (ExceptionInInitializerError exceptionInInitializerError) {
            this.errorMessage("The specified library did not load properly", 1);
        }
        catch (SecurityException ex4) {
            this.errorMessage("The specified library did not load properly", 1);
        }
        if (this.errorCheck(this.soundLibrary == null, "Library null after initialization in method 'newLibrary'", 1)) {
            lastException(true, new SoundSystemException(this.className + " did not load properly.  " + "Library was null after initialization.", 4));
            this.importantMessage("Switching to silent mode", 1);
            try {
                this.soundLibrary = new Library();
            }
            catch (SoundSystemException ex5) {
                lastException(true, new SoundSystemException("Silent mode did not load properly.  Library was null after initialization.", 4));
                initialized(true, true);
                return;
            }
        }
        try {
            this.soundLibrary.init();
        }
        catch (SoundSystemException ex) {
            lastException(true, ex);
            initialized(true, true);
            return;
        }
        lastException(true, null);
        initialized(true, true);
    }
    
    private void CommandInitialize() {
        try {
            if (this.errorCheck(this.soundLibrary == null, "Library null after initialization in method 'CommandInitialize'", 1)) {
                final SoundSystemException ex = new SoundSystemException(this.className + " did not load properly.  " + "Library was null after initialization.", 4);
                lastException(true, ex);
                throw ex;
            }
            this.soundLibrary.init();
        }
        catch (SoundSystemException ex2) {
            lastException(true, ex2);
            initialized(true, true);
        }
    }
    
    private void CommandLoadSound(final FilenameURL filenameURL) {
        if (this.soundLibrary != null) {
            this.soundLibrary.loadSound(filenameURL);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandLoadSound'", 0);
        }
    }
    
    private void CommandLoadSound(final SoundBuffer soundBuffer, final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.loadSound(soundBuffer, s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandLoadSound'", 0);
        }
    }
    
    private void CommandUnloadSound(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.unloadSound(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandLoadSound'", 0);
        }
    }
    
    private void CommandQueueSound(final String s, final FilenameURL filenameURL) {
        if (this.soundLibrary != null) {
            this.soundLibrary.queueSound(s, filenameURL);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandQueueSound'", 0);
        }
    }
    
    private void CommandDequeueSound(final String s, final String s2) {
        if (this.soundLibrary != null) {
            this.soundLibrary.dequeueSound(s, s2);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandDequeueSound'", 0);
        }
    }
    
    private void CommandFadeOut(final String s, final FilenameURL filenameURL, final long n) {
        if (this.soundLibrary != null) {
            this.soundLibrary.fadeOut(s, filenameURL, n);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandFadeOut'", 0);
        }
    }
    
    private void CommandFadeOutIn(final String s, final FilenameURL filenameURL, final long n, final long n2) {
        if (this.soundLibrary != null) {
            this.soundLibrary.fadeOutIn(s, filenameURL, n, n2);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandFadeOutIn'", 0);
        }
    }
    
    private void CommandCheckFadeVolumes() {
        if (this.soundLibrary != null) {
            this.soundLibrary.checkFadeVolumes();
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandCheckFadeVolumes'", 0);
        }
    }
    
    private void CommandNewSource(final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final float n, final float n2, final float n3, final int n4, final float n5) {
        if (this.soundLibrary != null) {
            if (filenameURL.getFilename().matches(SoundSystemConfig.EXTENSION_MIDI) && !SoundSystemConfig.midiCodec()) {
                this.soundLibrary.loadMidi(b3, s, filenameURL);
            }
            else {
                this.soundLibrary.newSource(b, b2, b3, s, filenameURL, n, n2, n3, n4, n5);
            }
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandNewSource'", 0);
        }
    }
    
    private void CommandRawDataStream(final AudioFormat audioFormat, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        if (this.soundLibrary != null) {
            this.soundLibrary.rawDataStream(audioFormat, b, s, n, n2, n3, n4, n5);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandRawDataStream'", 0);
        }
    }
    
    private void CommandQuickPlay(final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b4) {
        if (this.soundLibrary != null) {
            if (filenameURL.getFilename().matches(SoundSystemConfig.EXTENSION_MIDI) && !SoundSystemConfig.midiCodec()) {
                this.soundLibrary.loadMidi(b3, s, filenameURL);
            }
            else {
                this.soundLibrary.quickPlay(b, b2, b3, s, filenameURL, n, n2, n3, n4, n5, b4);
            }
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandQuickPlay'", 0);
        }
    }
    
    private void CommandSetPosition(final String s, final float n, final float n2, final float n3) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setPosition(s, n, n2, n3);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandMoveSource'", 0);
        }
    }
    
    private void CommandSetVolume(final String s, final float n) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setVolume(s, n);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetVolume'", 0);
        }
    }
    
    private void CommandSetPitch(final String s, final float n) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setPitch(s, n);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetPitch'", 0);
        }
    }
    
    private void CommandSetPriority(final String s, final boolean b) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setPriority(s, b);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetPriority'", 0);
        }
    }
    
    private void CommandSetLooping(final String s, final boolean b) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setLooping(s, b);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetLooping'", 0);
        }
    }
    
    private void CommandSetAttenuation(final String s, final int n) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setAttenuation(s, n);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetAttenuation'", 0);
        }
    }
    
    private void CommandSetDistOrRoll(final String s, final float n) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setDistOrRoll(s, n);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetDistOrRoll'", 0);
        }
    }
    
    private void CommandChangeDopplerFactor(final float dopplerFactor) {
        if (this.soundLibrary != null) {
            SoundSystemConfig.setDopplerFactor(dopplerFactor);
            this.soundLibrary.dopplerChanged();
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetDopplerFactor'", 0);
        }
    }
    
    private void CommandChangeDopplerVelocity(final float dopplerVelocity) {
        if (this.soundLibrary != null) {
            SoundSystemConfig.setDopplerVelocity(dopplerVelocity);
            this.soundLibrary.dopplerChanged();
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetDopplerFactor'", 0);
        }
    }
    
    private void CommandSetVelocity(final String s, final float n, final float n2, final float n3) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setVelocity(s, n, n2, n3);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandVelocity'", 0);
        }
    }
    
    private void CommandSetListenerVelocity(final float n, final float n2, final float n3) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setListenerVelocity(n, n2, n3);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetListenerVelocity'", 0);
        }
    }
    
    private void CommandPlay(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.play(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandPlay'", 0);
        }
    }
    
    private void CommandFeedRawAudioData(final String s, final byte[] array) {
        if (this.soundLibrary != null) {
            this.soundLibrary.feedRawAudioData(s, array);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandFeedRawAudioData'", 0);
        }
    }
    
    private void CommandPause(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.pause(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandPause'", 0);
        }
    }
    
    private void CommandStop(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.stop(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandStop'", 0);
        }
    }
    
    private void CommandRewind(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.rewind(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandRewind'", 0);
        }
    }
    
    private void CommandFlush(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.flush(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandFlush'", 0);
        }
    }
    
    private void CommandSetTemporary(final String s, final boolean b) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setTemporary(s, b);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetActive'", 0);
        }
    }
    
    private void CommandRemoveSource(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.removeSource(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandRemoveSource'", 0);
        }
    }
    
    private void CommandMoveListener(final float n, final float n2, final float n3) {
        if (this.soundLibrary != null) {
            this.soundLibrary.moveListener(n, n2, n3);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandMoveListener'", 0);
        }
    }
    
    private void CommandSetListenerPosition(final float n, final float n2, final float n3) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setListenerPosition(n, n2, n3);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetListenerPosition'", 0);
        }
    }
    
    private void CommandTurnListener(final float n) {
        if (this.soundLibrary != null) {
            this.soundLibrary.turnListener(n);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandTurnListener'", 0);
        }
    }
    
    private void CommandSetListenerAngle(final float listenerAngle) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setListenerAngle(listenerAngle);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetListenerAngle'", 0);
        }
    }
    
    private void CommandSetListenerOrientation(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setListenerOrientation(n, n2, n3, n4, n5, n6);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetListenerOrientation'", 0);
        }
    }
    
    private void CommandCull(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.cull(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandCull'", 0);
        }
    }
    
    private void CommandActivate(final String s) {
        if (this.soundLibrary != null) {
            this.soundLibrary.activate(s);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandActivate'", 0);
        }
    }
    
    private void CommandSetMasterVolume(final float masterVolume) {
        if (this.soundLibrary != null) {
            this.soundLibrary.setMasterVolume(masterVolume);
        }
        else {
            this.errorMessage("Variable 'soundLibrary' null in method 'CommandSetMasterVolume'", 0);
        }
    }
    
    protected void ManageSources() {
    }
    
    public boolean CommandQueue(final CommandObject commandObject) {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (commandObject == null) {
                boolean b = false;
                while (this.commandQueue != null && this.commandQueue.size() > 0) {
                    final CommandObject commandObject2 = this.commandQueue.remove(0);
                    if (commandObject2 != null) {
                        switch (commandObject2.Command) {
                            case 1: {
                                this.CommandInitialize();
                                continue;
                            }
                            case 2: {
                                this.CommandLoadSound((FilenameURL)commandObject2.objectArgs[0]);
                                continue;
                            }
                            case 3: {
                                this.CommandLoadSound((SoundBuffer)commandObject2.objectArgs[0], commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 4: {
                                this.CommandUnloadSound(commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 5: {
                                this.CommandQueueSound(commandObject2.stringArgs[0], (FilenameURL)commandObject2.objectArgs[0]);
                                continue;
                            }
                            case 6: {
                                this.CommandDequeueSound(commandObject2.stringArgs[0], commandObject2.stringArgs[1]);
                                continue;
                            }
                            case 7: {
                                this.CommandFadeOut(commandObject2.stringArgs[0], (FilenameURL)commandObject2.objectArgs[0], commandObject2.longArgs[0]);
                                continue;
                            }
                            case 8: {
                                this.CommandFadeOutIn(commandObject2.stringArgs[0], (FilenameURL)commandObject2.objectArgs[0], commandObject2.longArgs[0], commandObject2.longArgs[1]);
                                continue;
                            }
                            case 9: {
                                this.CommandCheckFadeVolumes();
                                continue;
                            }
                            case 10: {
                                this.CommandNewSource(commandObject2.boolArgs[0], commandObject2.boolArgs[1], commandObject2.boolArgs[2], commandObject2.stringArgs[0], (FilenameURL)commandObject2.objectArgs[0], commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2], commandObject2.intArgs[0], commandObject2.floatArgs[3]);
                                continue;
                            }
                            case 11: {
                                this.CommandRawDataStream((AudioFormat)commandObject2.objectArgs[0], commandObject2.boolArgs[0], commandObject2.stringArgs[0], commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2], commandObject2.intArgs[0], commandObject2.floatArgs[3]);
                                continue;
                            }
                            case 12: {
                                this.CommandQuickPlay(commandObject2.boolArgs[0], commandObject2.boolArgs[1], commandObject2.boolArgs[2], commandObject2.stringArgs[0], (FilenameURL)commandObject2.objectArgs[0], commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2], commandObject2.intArgs[0], commandObject2.floatArgs[3], commandObject2.boolArgs[3]);
                                continue;
                            }
                            case 13: {
                                this.CommandSetPosition(commandObject2.stringArgs[0], commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2]);
                                continue;
                            }
                            case 14: {
                                this.CommandSetVolume(commandObject2.stringArgs[0], commandObject2.floatArgs[0]);
                                continue;
                            }
                            case 15: {
                                this.CommandSetPitch(commandObject2.stringArgs[0], commandObject2.floatArgs[0]);
                                continue;
                            }
                            case 16: {
                                this.CommandSetPriority(commandObject2.stringArgs[0], commandObject2.boolArgs[0]);
                                continue;
                            }
                            case 17: {
                                this.CommandSetLooping(commandObject2.stringArgs[0], commandObject2.boolArgs[0]);
                                continue;
                            }
                            case 18: {
                                this.CommandSetAttenuation(commandObject2.stringArgs[0], commandObject2.intArgs[0]);
                                continue;
                            }
                            case 19: {
                                this.CommandSetDistOrRoll(commandObject2.stringArgs[0], commandObject2.floatArgs[0]);
                                continue;
                            }
                            case 20: {
                                this.CommandChangeDopplerFactor(commandObject2.floatArgs[0]);
                                continue;
                            }
                            case 21: {
                                this.CommandChangeDopplerVelocity(commandObject2.floatArgs[0]);
                                continue;
                            }
                            case 22: {
                                this.CommandSetVelocity(commandObject2.stringArgs[0], commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2]);
                                continue;
                            }
                            case 23: {
                                this.CommandSetListenerVelocity(commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2]);
                                continue;
                            }
                            case 24: {
                                this.sourcePlayList.add(commandObject2);
                                continue;
                            }
                            case 25: {
                                this.sourcePlayList.add(commandObject2);
                                continue;
                            }
                            case 26: {
                                this.CommandPause(commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 27: {
                                this.CommandStop(commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 28: {
                                this.CommandRewind(commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 29: {
                                this.CommandFlush(commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 30: {
                                this.CommandCull(commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 31: {
                                b = true;
                                this.CommandActivate(commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 32: {
                                this.CommandSetTemporary(commandObject2.stringArgs[0], commandObject2.boolArgs[0]);
                                continue;
                            }
                            case 33: {
                                this.CommandRemoveSource(commandObject2.stringArgs[0]);
                                continue;
                            }
                            case 34: {
                                this.CommandMoveListener(commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2]);
                                continue;
                            }
                            case 35: {
                                this.CommandSetListenerPosition(commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2]);
                                continue;
                            }
                            case 36: {
                                this.CommandTurnListener(commandObject2.floatArgs[0]);
                                continue;
                            }
                            case 37: {
                                this.CommandSetListenerAngle(commandObject2.floatArgs[0]);
                                continue;
                            }
                            case 38: {
                                this.CommandSetListenerOrientation(commandObject2.floatArgs[0], commandObject2.floatArgs[1], commandObject2.floatArgs[2], commandObject2.floatArgs[3], commandObject2.floatArgs[4], commandObject2.floatArgs[5]);
                                continue;
                            }
                            case 39: {
                                this.CommandSetMasterVolume(commandObject2.floatArgs[0]);
                                continue;
                            }
                            case 40: {
                                this.CommandNewLibrary(commandObject2.classArgs[0]);
                                continue;
                            }
                            default: {
                                continue;
                            }
                        }
                    }
                }
                if (b) {
                    this.soundLibrary.replaySources();
                }
                while (this.sourcePlayList != null && this.sourcePlayList.size() > 0) {
                    final CommandObject commandObject3 = this.sourcePlayList.remove(0);
                    if (commandObject3 != null) {
                        switch (commandObject3.Command) {
                            case 24: {
                                this.CommandPlay(commandObject3.stringArgs[0]);
                                continue;
                            }
                            case 25: {
                                this.CommandFeedRawAudioData(commandObject3.stringArgs[0], commandObject3.buffer);
                                continue;
                            }
                        }
                    }
                }
                return this.commandQueue != null && this.commandQueue.size() > 0;
            }
            if (this.commandQueue == null) {
                return false;
            }
            this.commandQueue.add(commandObject);
            return true;
        }
    }
    
    public void removeTemporarySources() {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary != null) {
                this.soundLibrary.removeTemporarySources();
            }
        }
    }
    
    public boolean playing(final String s) {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary == null) {
                return false;
            }
            final Source source = this.soundLibrary.getSources().get(s);
            return source != null && source.playing();
        }
    }
    
    public boolean playing() {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary == null) {
                return false;
            }
            final HashMap sources = this.soundLibrary.getSources();
            if (sources == null) {
                return false;
            }
            final Iterator<String> iterator = sources.keySet().iterator();
            while (iterator.hasNext()) {
                final Source source = (Source)sources.get(iterator.next());
                if (source != null && source.playing()) {
                    return true;
                }
            }
            return false;
        }
    }
    
    private HashMap copySources(final HashMap hashMap) {
        final Iterator<String> iterator = hashMap.keySet().iterator();
        final HashMap<String, Source> hashMap2 = new HashMap<String, Source>();
        while (iterator.hasNext()) {
            final String s = iterator.next();
            final Source source = (Source)hashMap.get(s);
            if (source != null) {
                hashMap2.put(s, new Source(source, null));
            }
        }
        return hashMap2;
    }
    
    public static boolean libraryCompatible(final Class clazz) {
        SoundSystemLogger logger = SoundSystemConfig.getLogger();
        if (logger == null) {
            logger = new SoundSystemLogger();
            SoundSystemConfig.setLogger(logger);
        }
        logger.message("", 0);
        logger.message("Checking if " + SoundSystemConfig.getLibraryTitle(clazz) + " is compatible...", 0);
        final boolean libraryCompatible = SoundSystemConfig.libraryCompatible(clazz);
        if (libraryCompatible) {
            logger.message("...yes", 1);
        }
        else {
            logger.message("...no", 1);
        }
        return libraryCompatible;
    }
    
    public static Class currentLibrary() {
        return currentLibrary(false, null);
    }
    
    public static boolean initialized() {
        return initialized(false, false);
    }
    
    public static SoundSystemException getLastException() {
        return lastException(false, null);
    }
    
    public static void setException(final SoundSystemException ex) {
        lastException(true, ex);
    }
    
    private static boolean initialized(final boolean b, final boolean initialized) {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (b) {
                SoundSystem.initialized = initialized;
            }
            return SoundSystem.initialized;
        }
    }
    
    private static Class currentLibrary(final boolean b, final Class currentLibrary) {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (b) {
                SoundSystem.currentLibrary = currentLibrary;
            }
            return SoundSystem.currentLibrary;
        }
    }
    
    private static SoundSystemException lastException(final boolean b, final SoundSystemException lastException) {
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (b) {
                SoundSystem.lastException = lastException;
            }
            return SoundSystem.lastException;
        }
    }
    
    protected static void snooze(final long n) {
        try {
            Thread.sleep(n);
        }
        catch (InterruptedException ex) {}
    }
    
    protected void message(final String s, final int n) {
        this.logger.message(s, n);
    }
    
    protected void importantMessage(final String s, final int n) {
        this.logger.importantMessage(s, n);
    }
    
    protected boolean errorCheck(final boolean b, final String s, final int n) {
        return this.logger.errorCheck(b, this.className, s, n);
    }
    
    protected void errorMessage(final String s, final int n) {
        this.logger.errorMessage(this.className, s, n);
    }
    
    static {
        SoundSystem.currentLibrary = null;
        SoundSystem.initialized = false;
        SoundSystem.lastException = null;
    }
}
