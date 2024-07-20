/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package paulscode.sound.libraries;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import paulscode.sound.Channel;
import paulscode.sound.FilenameURL;
import paulscode.sound.ICodec;
import paulscode.sound.Library;
import paulscode.sound.SoundBuffer;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.Source;
import paulscode.sound.libraries.ChannelJavaSound;
import paulscode.sound.libraries.SourceJavaSound;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LibraryJavaSound
extends Library {
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final int XXX = 0;
    private final int maxClipSize = 0x100000;
    private static Mixer myMixer = null;
    private static MixerRanking myMixerRanking = null;
    private static LibraryJavaSound instance = null;
    private static int minSampleRate = 4000;
    private static int maxSampleRate = 48000;
    private static int lineCount = 32;
    private static boolean useGainControl = true;
    private static boolean usePanControl = true;
    private static boolean useSampleRateControl = true;

    public LibraryJavaSound() throws SoundSystemException {
        instance = this;
    }

    @Override
    public void init() throws SoundSystemException {
        MixerRanking mixerRanker = null;
        if (myMixer == null) {
            for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
                if (!mixerInfo.getName().equals("Java Sound Audio Engine")) continue;
                mixerRanker = new MixerRanking();
                try {
                    mixerRanker.rank(mixerInfo);
                } catch (Exception ljse) {
                    break;
                }
                if (mixerRanker.rank < 14) break;
                myMixer = AudioSystem.getMixer(mixerInfo);
                LibraryJavaSound.mixerRanking(true, mixerRanker);
                break;
            }
            if (myMixer == null) {
                MixerRanking bestRankedMixer = mixerRanker;
                for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
                    mixerRanker = new MixerRanking();
                    try {
                        mixerRanker.rank(mixerInfo);
                    } catch (Exception ljse) {
                        // empty catch block
                    }
                    if (bestRankedMixer != null && mixerRanker.rank <= bestRankedMixer.rank) continue;
                    bestRankedMixer = mixerRanker;
                }
                if (bestRankedMixer == null) {
                    throw new Exception("No useable mixers found!", new MixerRanking());
                }
                try {
                    myMixer = AudioSystem.getMixer(bestRankedMixer.mixerInfo);
                    LibraryJavaSound.mixerRanking(true, bestRankedMixer);
                } catch (java.lang.Exception e) {
                    throw new Exception("No useable mixers available!", new MixerRanking());
                }
            }
        }
        this.setMasterVolume(1.0f);
        this.message("JavaSound initialized.");
        super.init();
    }

    public static boolean libraryCompatible() {
        for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
            if (!mixerInfo.getName().equals("Java Sound Audio Engine")) continue;
            return true;
        }
        return false;
    }

    @Override
    protected Channel createChannel(int type2) {
        return new ChannelJavaSound(type2, myMixer);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        instance = null;
        myMixer = null;
        myMixerRanking = null;
    }

    @Override
    public boolean loadSound(FilenameURL filenameURL) {
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
        ICodec codec = SoundSystemConfig.getCodec(filenameURL.getFilename());
        if (this.errorCheck(codec == null, "No codec found for file '" + filenameURL.getFilename() + "' in method 'loadSound'")) {
            return false;
        }
        URL url = filenameURL.getURL();
        if (this.errorCheck(url == null, "Unable to open file '" + filenameURL.getFilename() + "' in method 'loadSound'")) {
            return false;
        }
        codec.initialize(url);
        SoundBuffer buffer = codec.readAll();
        codec.cleanup();
        codec = null;
        if (buffer != null) {
            this.bufferMap.put(filenameURL.getFilename(), buffer);
        } else {
            this.errorMessage("Sound buffer null in method 'loadSound'");
        }
        return true;
    }

    @Override
    public boolean loadSound(SoundBuffer buffer, String identifier) {
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'loadSound'");
        }
        if (this.errorCheck(identifier == null, "Identifier not specified in method 'loadSound'")) {
            return false;
        }
        if (this.bufferMap.get(identifier) != null) {
            return true;
        }
        if (buffer != null) {
            this.bufferMap.put(identifier, buffer);
        } else {
            this.errorMessage("Sound buffer null in method 'loadSound'");
        }
        return true;
    }

    @Override
    public void setMasterVolume(float value) {
        super.setMasterVolume(value);
        Set keys = this.sourceMap.keySet();
        for (String sourcename : keys) {
            Source source = (Source)this.sourceMap.get(sourcename);
            if (source == null) continue;
            source.positionChanged();
        }
    }

    @Override
    public void newSource(boolean priority, boolean toStream, boolean toLoop, String sourcename, FilenameURL filenameURL, float x, float y, float z, int attModel, float distOrRoll) {
        SoundBuffer buffer = null;
        if (!toStream) {
            buffer = (SoundBuffer)this.bufferMap.get(filenameURL.getFilename());
            if (buffer == null && !this.loadSound(filenameURL)) {
                this.errorMessage("Source '" + sourcename + "' was not created " + "because an error occurred while loading " + filenameURL.getFilename());
                return;
            }
            buffer = (SoundBuffer)this.bufferMap.get(filenameURL.getFilename());
            if (buffer == null) {
                this.errorMessage("Source '" + sourcename + "' was not created " + "because audio data was not found for " + filenameURL.getFilename());
                return;
            }
        }
        if (!toStream && buffer != null) {
            buffer.trimData(0x100000);
        }
        this.sourceMap.put(sourcename, new SourceJavaSound(this.listener, priority, toStream, toLoop, sourcename, filenameURL, buffer, x, y, z, attModel, distOrRoll, false));
    }

    @Override
    public void rawDataStream(AudioFormat audioFormat, boolean priority, String sourcename, float x, float y, float z, int attModel, float distOrRoll) {
        this.sourceMap.put(sourcename, new SourceJavaSound(this.listener, audioFormat, priority, sourcename, x, y, z, attModel, distOrRoll));
    }

    @Override
    public void quickPlay(boolean priority, boolean toStream, boolean toLoop, String sourcename, FilenameURL filenameURL, float x, float y, float z, int attModel, float distOrRoll, boolean temporary) {
        SoundBuffer buffer = null;
        if (!toStream) {
            buffer = (SoundBuffer)this.bufferMap.get(filenameURL.getFilename());
            if (buffer == null && !this.loadSound(filenameURL)) {
                this.errorMessage("Source '" + sourcename + "' was not created " + "because an error occurred while loading " + filenameURL.getFilename());
                return;
            }
            buffer = (SoundBuffer)this.bufferMap.get(filenameURL.getFilename());
            if (buffer == null) {
                this.errorMessage("Source '" + sourcename + "' was not created " + "because audio data was not found for " + filenameURL.getFilename());
                return;
            }
        }
        if (!toStream && buffer != null) {
            buffer.trimData(0x100000);
        }
        this.sourceMap.put(sourcename, new SourceJavaSound(this.listener, priority, toStream, toLoop, sourcename, filenameURL, buffer, x, y, z, attModel, distOrRoll, temporary));
    }

    @Override
    public void copySources(HashMap<String, Source> srcMap) {
        if (srcMap == null) {
            return;
        }
        Set<String> keys = srcMap.keySet();
        Iterator<String> iter = keys.iterator();
        if (this.bufferMap == null) {
            this.bufferMap = new HashMap();
            this.importantMessage("Buffer Map was null in method 'copySources'");
        }
        this.sourceMap.clear();
        while (iter.hasNext()) {
            String sourcename = iter.next();
            Source source = srcMap.get(sourcename);
            if (source == null) continue;
            SoundBuffer buffer = null;
            if (!source.toStream) {
                this.loadSound(source.filenameURL);
                buffer = (SoundBuffer)this.bufferMap.get(source.filenameURL.getFilename());
            }
            if (!source.toStream && buffer != null) {
                buffer.trimData(0x100000);
            }
            if (!source.toStream && buffer == null) continue;
            this.sourceMap.put(sourcename, new SourceJavaSound(this.listener, source, buffer));
        }
    }

    @Override
    public void setListenerVelocity(float x, float y, float z) {
        super.setListenerVelocity(x, y, z);
        this.listenerMoved();
    }

    @Override
    public void dopplerChanged() {
        super.dopplerChanged();
        this.listenerMoved();
    }

    public static Mixer getMixer() {
        return LibraryJavaSound.mixer(false, null);
    }

    public static void setMixer(Mixer m) throws SoundSystemException {
        LibraryJavaSound.mixer(true, m);
        SoundSystemException e = SoundSystem.getLastException();
        SoundSystem.setException(null);
        if (e != null) {
            throw e;
        }
    }

    private static synchronized Mixer mixer(boolean action, Mixer m) {
        if (action) {
            if (m == null) {
                return myMixer;
            }
            MixerRanking mixerRanker = new MixerRanking();
            try {
                mixerRanker.rank(m.getMixerInfo());
            } catch (Exception ljse) {
                SoundSystemConfig.getLogger().printStackTrace(ljse, 1);
                SoundSystem.setException(ljse);
            }
            myMixer = m;
            LibraryJavaSound.mixerRanking(true, mixerRanker);
            if (instance != null) {
                ChannelJavaSound c;
                ListIterator itr = LibraryJavaSound.instance.normalChannels.listIterator();
                SoundSystem.setException(null);
                while (itr.hasNext()) {
                    c = (ChannelJavaSound)itr.next();
                    c.newMixer(m);
                }
                itr = LibraryJavaSound.instance.streamingChannels.listIterator();
                while (itr.hasNext()) {
                    c = (ChannelJavaSound)itr.next();
                    c.newMixer(m);
                }
            }
        }
        return myMixer;
    }

    public static MixerRanking getMixerRanking() {
        return LibraryJavaSound.mixerRanking(false, null);
    }

    private static synchronized MixerRanking mixerRanking(boolean action, MixerRanking value) {
        if (action) {
            myMixerRanking = value;
        }
        return myMixerRanking;
    }

    public static void setMinSampleRate(int value) {
        LibraryJavaSound.minSampleRate(true, value);
    }

    private static synchronized int minSampleRate(boolean action, int value) {
        if (action) {
            minSampleRate = value;
        }
        return minSampleRate;
    }

    public static void setMaxSampleRate(int value) {
        LibraryJavaSound.maxSampleRate(true, value);
    }

    private static synchronized int maxSampleRate(boolean action, int value) {
        if (action) {
            maxSampleRate = value;
        }
        return maxSampleRate;
    }

    public static void setLineCount(int value) {
        LibraryJavaSound.lineCount(true, value);
    }

    private static synchronized int lineCount(boolean action, int value) {
        if (action) {
            lineCount = value;
        }
        return lineCount;
    }

    public static void useGainControl(boolean value) {
        LibraryJavaSound.useGainControl(true, value);
    }

    private static synchronized boolean useGainControl(boolean action, boolean value) {
        if (action) {
            useGainControl = value;
        }
        return useGainControl;
    }

    public static void usePanControl(boolean value) {
        LibraryJavaSound.usePanControl(true, value);
    }

    private static synchronized boolean usePanControl(boolean action, boolean value) {
        if (action) {
            usePanControl = value;
        }
        return usePanControl;
    }

    public static void useSampleRateControl(boolean value) {
        LibraryJavaSound.useSampleRateControl(true, value);
    }

    private static synchronized boolean useSampleRateControl(boolean action, boolean value) {
        if (action) {
            useSampleRateControl = value;
        }
        return useSampleRateControl;
    }

    public static String getTitle() {
        return "Java Sound";
    }

    public static String getDescription() {
        return "The Java Sound API.  For more information, see http://java.sun.com/products/java-media/sound/";
    }

    @Override
    public String getClassName() {
        return "LibraryJavaSound";
    }

    public static class Exception
    extends SoundSystemException {
        public static final int MIXER_PROBLEM = 101;
        public static MixerRanking mixerRanking = null;

        public Exception(String message) {
            super(message);
        }

        public Exception(String message, int type2) {
            super(message, type2);
        }

        public Exception(String message, MixerRanking rank) {
            super(message, 101);
            mixerRanking = rank;
        }
    }

    public static class MixerRanking {
        public static final int HIGH = 1;
        public static final int MEDIUM = 2;
        public static final int LOW = 3;
        public static final int NONE = 4;
        public static int MIXER_EXISTS_PRIORITY = 1;
        public static int MIN_SAMPLE_RATE_PRIORITY = 1;
        public static int MAX_SAMPLE_RATE_PRIORITY = 1;
        public static int LINE_COUNT_PRIORITY = 1;
        public static int GAIN_CONTROL_PRIORITY = 2;
        public static int PAN_CONTROL_PRIORITY = 2;
        public static int SAMPLE_RATE_CONTROL_PRIORITY = 3;
        public Mixer.Info mixerInfo = null;
        public int rank = 0;
        public boolean mixerExists = false;
        public boolean minSampleRateOK = false;
        public boolean maxSampleRateOK = false;
        public boolean lineCountOK = false;
        public boolean gainControlOK = false;
        public boolean panControlOK = false;
        public boolean sampleRateControlOK = false;
        public int minSampleRatePossible = -1;
        public int maxSampleRatePossible = -1;
        public int maxLinesPossible = 0;

        public MixerRanking() {
        }

        public MixerRanking(Mixer.Info i, int r, boolean e, boolean mnsr, boolean mxsr, boolean lc, boolean gc, boolean pc, boolean src) {
            this.mixerInfo = i;
            this.rank = r;
            this.mixerExists = e;
            this.minSampleRateOK = mnsr;
            this.maxSampleRateOK = mxsr;
            this.lineCountOK = lc;
            this.gainControlOK = gc;
            this.panControlOK = pc;
            this.sampleRateControlOK = src;
        }

        public void rank(Mixer.Info i) throws Exception {
            int testSampleRate;
            int uL;
            int lL;
            DataLine.Info lineInfo;
            AudioFormat format;
            Mixer m;
            if (i == null) {
                throw new Exception("No Mixer info specified in method 'MixerRanking.rank'", this);
            }
            this.mixerInfo = i;
            try {
                m = AudioSystem.getMixer(this.mixerInfo);
            } catch (java.lang.Exception e) {
                throw new Exception("Unable to acquire the specified Mixer in method 'MixerRanking.rank'", this);
            }
            if (m == null) {
                throw new Exception("Unable to acquire the specified Mixer in method 'MixerRanking.rank'", this);
            }
            this.mixerExists = true;
            try {
                format = new AudioFormat(LibraryJavaSound.minSampleRate(false, 0), 16, 2, true, false);
                lineInfo = new DataLine.Info(SourceDataLine.class, format);
            } catch (java.lang.Exception e) {
                throw new Exception("Invalid minimum sample-rate specified in method 'MixerRanking.rank'", this);
            }
            if (!AudioSystem.isLineSupported(lineInfo)) {
                if (MIN_SAMPLE_RATE_PRIORITY == 1) {
                    throw new Exception("Specified minimum sample-rate not possible for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            } else {
                this.minSampleRateOK = true;
            }
            try {
                format = new AudioFormat(LibraryJavaSound.maxSampleRate(false, 0), 16, 2, true, false);
                lineInfo = new DataLine.Info(SourceDataLine.class, format);
            } catch (java.lang.Exception e) {
                throw new Exception("Invalid maximum sample-rate specified in method 'MixerRanking.rank'", this);
            }
            if (!AudioSystem.isLineSupported(lineInfo)) {
                if (MAX_SAMPLE_RATE_PRIORITY == 1) {
                    throw new Exception("Specified maximum sample-rate not possible for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            } else {
                this.maxSampleRateOK = true;
            }
            if (this.minSampleRateOK) {
                this.minSampleRatePossible = LibraryJavaSound.minSampleRate(false, 0);
            } else {
                lL = LibraryJavaSound.minSampleRate(false, 0);
                uL = LibraryJavaSound.maxSampleRate(false, 0);
                while (uL - lL > 1) {
                    testSampleRate = lL + (uL - lL) / 2;
                    format = new AudioFormat(testSampleRate, 16, 2, true, false);
                    lineInfo = new DataLine.Info(SourceDataLine.class, format);
                    if (AudioSystem.isLineSupported(lineInfo)) {
                        this.minSampleRatePossible = testSampleRate;
                        uL = testSampleRate;
                        continue;
                    }
                    lL = testSampleRate;
                }
            }
            if (this.maxSampleRateOK) {
                this.maxSampleRatePossible = LibraryJavaSound.maxSampleRate(false, 0);
            } else if (this.minSampleRatePossible != -1) {
                uL = LibraryJavaSound.maxSampleRate(false, 0);
                lL = this.minSampleRatePossible;
                while (uL - lL > 1) {
                    testSampleRate = lL + (uL - lL) / 2;
                    format = new AudioFormat(testSampleRate, 16, 2, true, false);
                    lineInfo = new DataLine.Info(SourceDataLine.class, format);
                    if (AudioSystem.isLineSupported(lineInfo)) {
                        this.maxSampleRatePossible = testSampleRate;
                        lL = testSampleRate;
                        continue;
                    }
                    uL = testSampleRate;
                }
            }
            if (this.minSampleRatePossible == -1 || this.maxSampleRatePossible == -1) {
                throw new Exception("No possible sample-rate found for Mixer '" + this.mixerInfo.getName() + "'", this);
            }
            format = new AudioFormat(this.minSampleRatePossible, 16, 2, true, false);
            Clip clip = null;
            try {
                DataLine.Info clipLineInfo = new DataLine.Info(Clip.class, format);
                clip = (Clip)m.getLine(clipLineInfo);
                byte[] buffer = new byte[10];
                clip.open(format, buffer, 0, buffer.length);
            } catch (java.lang.Exception e) {
                throw new Exception("Unable to attach an actual audio buffer to an actual Clip... Mixer '" + this.mixerInfo.getName() + "' is unuseable.", this);
            }
            this.maxLinesPossible = 1;
            lineInfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine[] lines = new SourceDataLine[LibraryJavaSound.lineCount(false, 0) - 1];
            boolean c = false;
            for (int x = 1; x < lines.length + 1; ++x) {
                try {
                    lines[x - 1] = (SourceDataLine)m.getLine(lineInfo);
                } catch (java.lang.Exception e) {
                    if (x == 0) {
                        throw new Exception("No output lines possible for Mixer '" + this.mixerInfo.getName() + "'", this);
                    }
                    if (LINE_COUNT_PRIORITY != 1) break;
                    throw new Exception("Specified maximum number of lines not possible for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
                this.maxLinesPossible = x + 1;
            }
            try {
                clip.close();
            } catch (java.lang.Exception e) {
                // empty catch block
            }
            clip = null;
            if (this.maxLinesPossible == LibraryJavaSound.lineCount(false, 0)) {
                this.lineCountOK = true;
            }
            if (!LibraryJavaSound.useGainControl(false, false)) {
                GAIN_CONTROL_PRIORITY = 4;
            } else if (!lines[0].isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                if (GAIN_CONTROL_PRIORITY == 1) {
                    throw new Exception("Gain control not available for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            } else {
                this.gainControlOK = true;
            }
            if (!LibraryJavaSound.usePanControl(false, false)) {
                PAN_CONTROL_PRIORITY = 4;
            } else if (!lines[0].isControlSupported(FloatControl.Type.PAN)) {
                if (PAN_CONTROL_PRIORITY == 1) {
                    throw new Exception("Pan control not available for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            } else {
                this.panControlOK = true;
            }
            if (!LibraryJavaSound.useSampleRateControl(false, false)) {
                SAMPLE_RATE_CONTROL_PRIORITY = 4;
            } else if (!lines[0].isControlSupported(FloatControl.Type.SAMPLE_RATE)) {
                if (SAMPLE_RATE_CONTROL_PRIORITY == 1) {
                    throw new Exception("Sample-rate control not available for Mixer '" + this.mixerInfo.getName() + "'", this);
                }
            } else {
                this.sampleRateControlOK = true;
            }
            this.rank += this.getRankValue(this.mixerExists, MIXER_EXISTS_PRIORITY);
            this.rank += this.getRankValue(this.minSampleRateOK, MIN_SAMPLE_RATE_PRIORITY);
            this.rank += this.getRankValue(this.maxSampleRateOK, MAX_SAMPLE_RATE_PRIORITY);
            this.rank += this.getRankValue(this.lineCountOK, LINE_COUNT_PRIORITY);
            this.rank += this.getRankValue(this.gainControlOK, GAIN_CONTROL_PRIORITY);
            this.rank += this.getRankValue(this.panControlOK, PAN_CONTROL_PRIORITY);
            this.rank += this.getRankValue(this.sampleRateControlOK, SAMPLE_RATE_CONTROL_PRIORITY);
            m = null;
            format = null;
            lineInfo = null;
            lines = null;
        }

        private int getRankValue(boolean property, int priority) {
            if (property) {
                return 2;
            }
            if (priority == 4) {
                return 2;
            }
            if (priority == 3) {
                return 1;
            }
            return 0;
        }
    }
}

