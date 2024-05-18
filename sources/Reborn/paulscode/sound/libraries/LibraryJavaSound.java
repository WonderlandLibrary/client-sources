package paulscode.sound.libraries;

import java.net.*;
import javax.sound.sampled.*;
import paulscode.sound.*;
import java.util.*;

public class LibraryJavaSound extends Library
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final int XXX = 0;
    private final int maxClipSize = 1048576;
    private static Mixer myMixer;
    private static LibraryJavaSound$MixerRanking myMixerRanking;
    private static LibraryJavaSound instance;
    private static int minSampleRate;
    private static int maxSampleRate;
    private static int lineCount;
    private static boolean useGainControl;
    private static boolean usePanControl;
    private static boolean useSampleRateControl;
    
    public LibraryJavaSound() {
        LibraryJavaSound.instance = this;
    }
    
    public void init() {
        LibraryJavaSound$MixerRanking libraryJavaSound$MixerRanking = null;
        if (LibraryJavaSound.myMixer == null) {
            final Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            final int length = mixerInfo.length;
            int i = 0;
            while (i < length) {
                final Mixer.Info info = mixerInfo[i];
                if (info.getName().equals("Java Sound Audio Engine")) {
                    libraryJavaSound$MixerRanking = new LibraryJavaSound$MixerRanking();
                    try {
                        libraryJavaSound$MixerRanking.rank(info);
                    }
                    catch (LibraryJavaSound$Exception ex) {
                        break;
                    }
                    if (libraryJavaSound$MixerRanking.rank < 14) {
                        break;
                    }
                    LibraryJavaSound.myMixer = AudioSystem.getMixer(info);
                    mixerRanking(true, libraryJavaSound$MixerRanking);
                    break;
                }
                else {
                    ++i;
                }
            }
            if (LibraryJavaSound.myMixer == null) {
                LibraryJavaSound$MixerRanking libraryJavaSound$MixerRanking2 = libraryJavaSound$MixerRanking;
                for (final Mixer.Info info2 : AudioSystem.getMixerInfo()) {
                    final LibraryJavaSound$MixerRanking libraryJavaSound$MixerRanking3 = new LibraryJavaSound$MixerRanking();
                    try {
                        libraryJavaSound$MixerRanking3.rank(info2);
                    }
                    catch (LibraryJavaSound$Exception ex2) {}
                    if (libraryJavaSound$MixerRanking2 == null || libraryJavaSound$MixerRanking3.rank > libraryJavaSound$MixerRanking2.rank) {
                        libraryJavaSound$MixerRanking2 = libraryJavaSound$MixerRanking3;
                    }
                }
                if (libraryJavaSound$MixerRanking2 == null) {
                    throw new LibraryJavaSound$Exception("No useable mixers found!", new LibraryJavaSound$MixerRanking());
                }
                try {
                    LibraryJavaSound.myMixer = AudioSystem.getMixer(libraryJavaSound$MixerRanking2.mixerInfo);
                    mixerRanking(true, libraryJavaSound$MixerRanking2);
                }
                catch (Exception ex3) {
                    throw new LibraryJavaSound$Exception("No useable mixers available!", new LibraryJavaSound$MixerRanking());
                }
            }
        }
        this.setMasterVolume(1.0f);
        this.message("JavaSound initialized.");
        super.init();
    }
    
    public static boolean libraryCompatible() {
        final Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        for (int length = mixerInfo.length, i = 0; i < length; ++i) {
            if (mixerInfo[i].getName().equals("Java Sound Audio Engine")) {
                return true;
            }
        }
        return false;
    }
    
    protected Channel createChannel(final int n) {
        return new ChannelJavaSound(n, LibraryJavaSound.myMixer);
    }
    
    public void cleanup() {
        super.cleanup();
        LibraryJavaSound.instance = null;
        LibraryJavaSound.myMixer = null;
        LibraryJavaSound.myMixerRanking = null;
    }
    
    public boolean loadSound(final FilenameURL filenameURL) {
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'loadSound'");
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
        final URL url = filenameURL.getURL();
        if (this.errorCheck(url == null, "Unable to open file '" + filenameURL.getFilename() + "' in method 'loadSound'")) {
            return false;
        }
        codec.initialize(url);
        final SoundBuffer all = codec.readAll();
        codec.cleanup();
        if (all != null) {
            this.bufferMap.put(filenameURL.getFilename(), all);
        }
        else {
            this.errorMessage("Sound buffer null in method 'loadSound'");
        }
        return true;
    }
    
    public boolean loadSound(final SoundBuffer soundBuffer, final String s) {
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'loadSound'");
        }
        if (this.errorCheck(s == null, "Identifier not specified in method 'loadSound'")) {
            return false;
        }
        if (this.bufferMap.get(s) != null) {
            return true;
        }
        if (soundBuffer != null) {
            this.bufferMap.put(s, soundBuffer);
        }
        else {
            this.errorMessage("Sound buffer null in method 'loadSound'");
        }
        return true;
    }
    
    public void setMasterVolume(final float masterVolume) {
        super.setMasterVolume(masterVolume);
        final Iterator<String> iterator = this.sourceMap.keySet().iterator();
        while (iterator.hasNext()) {
            final Source source = this.sourceMap.get(iterator.next());
            if (source != null) {
                source.positionChanged();
            }
        }
    }
    
    public void newSource(final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final float n, final float n2, final float n3, final int n4, final float n5) {
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
        if (!b2 && soundBuffer != null) {
            soundBuffer.trimData(1048576);
        }
        this.sourceMap.put(s, new SourceJavaSound(this.listener, b, b2, b3, s, filenameURL, soundBuffer, n, n2, n3, n4, n5, false));
    }
    
    public void rawDataStream(final AudioFormat audioFormat, final boolean b, final String s, final float n, final float n2, final float n3, final int n4, final float n5) {
        this.sourceMap.put(s, new SourceJavaSound(this.listener, audioFormat, b, s, n, n2, n3, n4, n5));
    }
    
    public void quickPlay(final boolean b, final boolean b2, final boolean b3, final String s, final FilenameURL filenameURL, final float n, final float n2, final float n3, final int n4, final float n5, final boolean b4) {
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
        if (!b2 && soundBuffer != null) {
            soundBuffer.trimData(1048576);
        }
        this.sourceMap.put(s, new SourceJavaSound(this.listener, b, b2, b3, s, filenameURL, soundBuffer, n, n2, n3, n4, n5, b4));
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
                if (!source.toStream && soundBuffer != null) {
                    soundBuffer.trimData(1048576);
                }
                if (!source.toStream && soundBuffer == null) {
                    continue;
                }
                this.sourceMap.put(s, new SourceJavaSound(this.listener, source, soundBuffer));
            }
        }
    }
    
    public void setListenerVelocity(final float n, final float n2, final float n3) {
        super.setListenerVelocity(n, n2, n3);
        this.listenerMoved();
    }
    
    public void dopplerChanged() {
        super.dopplerChanged();
        this.listenerMoved();
    }
    
    public static Mixer getMixer() {
        return mixer(false, null);
    }
    
    public static void setMixer(final Mixer mixer) {
        mixer(true, mixer);
        final SoundSystemException lastException = SoundSystem.getLastException();
        SoundSystem.setException(null);
        if (lastException != null) {
            throw lastException;
        }
    }
    
    private static synchronized Mixer mixer(final boolean b, final Mixer myMixer) {
        if (b) {
            if (myMixer == null) {
                return LibraryJavaSound.myMixer;
            }
            final LibraryJavaSound$MixerRanking libraryJavaSound$MixerRanking = new LibraryJavaSound$MixerRanking();
            try {
                libraryJavaSound$MixerRanking.rank(myMixer.getMixerInfo());
            }
            catch (LibraryJavaSound$Exception exception) {
                SoundSystemConfig.getLogger().printStackTrace(exception, 1);
                SoundSystem.setException(exception);
            }
            LibraryJavaSound.myMixer = myMixer;
            mixerRanking(true, libraryJavaSound$MixerRanking);
            if (LibraryJavaSound.instance != null) {
                final ListIterator<ChannelJavaSound> listIterator = LibraryJavaSound.instance.normalChannels.listIterator();
                SoundSystem.setException(null);
                while (listIterator.hasNext()) {
                    listIterator.next().newMixer(myMixer);
                }
                final ListIterator<ChannelJavaSound> listIterator2 = LibraryJavaSound.instance.streamingChannels.listIterator();
                while (listIterator2.hasNext()) {
                    listIterator2.next().newMixer(myMixer);
                }
            }
        }
        return LibraryJavaSound.myMixer;
    }
    
    public static LibraryJavaSound$MixerRanking getMixerRanking() {
        return mixerRanking(false, null);
    }
    
    private static synchronized LibraryJavaSound$MixerRanking mixerRanking(final boolean b, final LibraryJavaSound$MixerRanking myMixerRanking) {
        if (b) {
            LibraryJavaSound.myMixerRanking = myMixerRanking;
        }
        return LibraryJavaSound.myMixerRanking;
    }
    
    public static void setMinSampleRate(final int n) {
        minSampleRate(true, n);
    }
    
    private static synchronized int minSampleRate(final boolean b, final int minSampleRate) {
        if (b) {
            LibraryJavaSound.minSampleRate = minSampleRate;
        }
        return LibraryJavaSound.minSampleRate;
    }
    
    public static void setMaxSampleRate(final int n) {
        maxSampleRate(true, n);
    }
    
    private static synchronized int maxSampleRate(final boolean b, final int maxSampleRate) {
        if (b) {
            LibraryJavaSound.maxSampleRate = maxSampleRate;
        }
        return LibraryJavaSound.maxSampleRate;
    }
    
    public static void setLineCount(final int n) {
        lineCount(true, n);
    }
    
    private static synchronized int lineCount(final boolean b, final int lineCount) {
        if (b) {
            LibraryJavaSound.lineCount = lineCount;
        }
        return LibraryJavaSound.lineCount;
    }
    
    public static void useGainControl(final boolean b) {
        useGainControl(true, b);
    }
    
    private static synchronized boolean useGainControl(final boolean b, final boolean useGainControl) {
        if (b) {
            LibraryJavaSound.useGainControl = useGainControl;
        }
        return LibraryJavaSound.useGainControl;
    }
    
    public static void usePanControl(final boolean b) {
        usePanControl(true, b);
    }
    
    private static synchronized boolean usePanControl(final boolean b, final boolean usePanControl) {
        if (b) {
            LibraryJavaSound.usePanControl = usePanControl;
        }
        return LibraryJavaSound.usePanControl;
    }
    
    public static void useSampleRateControl(final boolean b) {
        useSampleRateControl(true, b);
    }
    
    private static synchronized boolean useSampleRateControl(final boolean b, final boolean useSampleRateControl) {
        if (b) {
            LibraryJavaSound.useSampleRateControl = useSampleRateControl;
        }
        return LibraryJavaSound.useSampleRateControl;
    }
    
    public static String getTitle() {
        return "Java Sound";
    }
    
    public static String getDescription() {
        return "The Java Sound API.  For more information, see http://java.sun.com/products/java-media/sound/";
    }
    
    public String getClassName() {
        return "LibraryJavaSound";
    }
    
    static {
        LibraryJavaSound.myMixer = null;
        LibraryJavaSound.myMixerRanking = null;
        LibraryJavaSound.instance = null;
        LibraryJavaSound.minSampleRate = 4000;
        LibraryJavaSound.maxSampleRate = 48000;
        LibraryJavaSound.lineCount = 32;
        LibraryJavaSound.useGainControl = true;
        LibraryJavaSound.usePanControl = true;
        LibraryJavaSound.useSampleRateControl = true;
    }
}
