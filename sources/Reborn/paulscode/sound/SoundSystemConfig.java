package paulscode.sound;

import java.util.*;
import java.lang.reflect.*;

public class SoundSystemConfig
{
    public static final Object THREAD_SYNC;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_STREAMING = 1;
    public static final int ATTENUATION_NONE = 0;
    public static final int ATTENUATION_ROLLOFF = 1;
    public static final int ATTENUATION_LINEAR = 2;
    public static String EXTENSION_MIDI;
    public static String PREFIX_URL;
    private static SoundSystemLogger logger;
    private static LinkedList libraries;
    private static LinkedList codecs;
    private static LinkedList streamListeners;
    private static final Object streamListenersLock;
    private static int numberNormalChannels;
    private static int numberStreamingChannels;
    private static float masterGain;
    private static int defaultAttenuationModel;
    private static float defaultRolloffFactor;
    private static float dopplerFactor;
    private static float dopplerVelocity;
    private static float defaultFadeDistance;
    private static String soundFilesPackage;
    private static int streamingBufferSize;
    private static int numberStreamingBuffers;
    private static boolean streamQueueFormatsMatch;
    private static int maxFileSize;
    private static int fileChunkSize;
    private static boolean midiCodec;
    private static String overrideMIDISynthesizer;
    
    public static void addLibrary(final Class clazz) {
        if (clazz == null) {
            throw new SoundSystemException("Parameter null in method 'addLibrary'", 2);
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            throw new SoundSystemException("The specified class does not extend class 'Library' in method 'addLibrary'");
        }
        if (SoundSystemConfig.libraries == null) {
            SoundSystemConfig.libraries = new LinkedList();
        }
        if (!SoundSystemConfig.libraries.contains(clazz)) {
            SoundSystemConfig.libraries.add(clazz);
        }
    }
    
    public static void removeLibrary(final Class clazz) {
        if (SoundSystemConfig.libraries == null || clazz == null) {
            return;
        }
        SoundSystemConfig.libraries.remove(clazz);
    }
    
    public static LinkedList getLibraries() {
        return SoundSystemConfig.libraries;
    }
    
    public static boolean libraryCompatible(final Class clazz) {
        if (clazz == null) {
            errorMessage("Parameter 'libraryClass' null in method'librayCompatible'");
            return false;
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            errorMessage("The specified class does not extend class 'Library' in method 'libraryCompatible'");
            return false;
        }
        final Object runMethod = runMethod(clazz, "libraryCompatible", new Class[0], new Object[0]);
        if (runMethod == null) {
            errorMessage("Method 'Library.libraryCompatible' returned 'null' in method 'libraryCompatible'");
            return false;
        }
        return (boolean)runMethod;
    }
    
    public static String getLibraryTitle(final Class clazz) {
        if (clazz == null) {
            errorMessage("Parameter 'libraryClass' null in method'getLibrayTitle'");
            return null;
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            errorMessage("The specified class does not extend class 'Library' in method 'getLibraryTitle'");
            return null;
        }
        final Object runMethod = runMethod(clazz, "getTitle", new Class[0], new Object[0]);
        if (runMethod == null) {
            errorMessage("Method 'Library.getTitle' returned 'null' in method 'getLibraryTitle'");
            return null;
        }
        return (String)runMethod;
    }
    
    public static String getLibraryDescription(final Class clazz) {
        if (clazz == null) {
            errorMessage("Parameter 'libraryClass' null in method'getLibrayDescription'");
            return null;
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            errorMessage("The specified class does not extend class 'Library' in method 'getLibraryDescription'");
            return null;
        }
        final Object runMethod = runMethod(clazz, "getDescription", new Class[0], new Object[0]);
        if (runMethod == null) {
            errorMessage("Method 'Library.getDescription' returned 'null' in method 'getLibraryDescription'");
            return null;
        }
        return (String)runMethod;
    }
    
    public static boolean reverseByteOrder(final Class clazz) {
        if (clazz == null) {
            errorMessage("Parameter 'libraryClass' null in method'reverseByteOrder'");
            return false;
        }
        if (!Library.class.isAssignableFrom(clazz)) {
            errorMessage("The specified class does not extend class 'Library' in method 'reverseByteOrder'");
            return false;
        }
        final Object runMethod = runMethod(clazz, "reversByteOrder", new Class[0], new Object[0]);
        if (runMethod == null) {
            errorMessage("Method 'Library.reverseByteOrder' returned 'null' in method 'getLibraryDescription'");
            return false;
        }
        return (boolean)runMethod;
    }
    
    public static void setLogger(final SoundSystemLogger logger) {
        SoundSystemConfig.logger = logger;
    }
    
    public static SoundSystemLogger getLogger() {
        return SoundSystemConfig.logger;
    }
    
    public static synchronized void setNumberNormalChannels(final int numberNormalChannels) {
        SoundSystemConfig.numberNormalChannels = numberNormalChannels;
    }
    
    public static synchronized int getNumberNormalChannels() {
        return SoundSystemConfig.numberNormalChannels;
    }
    
    public static synchronized void setNumberStreamingChannels(final int numberStreamingChannels) {
        SoundSystemConfig.numberStreamingChannels = numberStreamingChannels;
    }
    
    public static synchronized int getNumberStreamingChannels() {
        return SoundSystemConfig.numberStreamingChannels;
    }
    
    public static synchronized void setMasterGain(final float masterGain) {
        SoundSystemConfig.masterGain = masterGain;
    }
    
    public static synchronized float getMasterGain() {
        return SoundSystemConfig.masterGain;
    }
    
    public static synchronized void setDefaultAttenuation(final int defaultAttenuationModel) {
        SoundSystemConfig.defaultAttenuationModel = defaultAttenuationModel;
    }
    
    public static synchronized int getDefaultAttenuation() {
        return SoundSystemConfig.defaultAttenuationModel;
    }
    
    public static synchronized void setDefaultRolloff(final float defaultRolloffFactor) {
        SoundSystemConfig.defaultRolloffFactor = defaultRolloffFactor;
    }
    
    public static synchronized float getDopplerFactor() {
        return SoundSystemConfig.dopplerFactor;
    }
    
    public static synchronized void setDopplerFactor(final float dopplerFactor) {
        SoundSystemConfig.dopplerFactor = dopplerFactor;
    }
    
    public static synchronized float getDopplerVelocity() {
        return SoundSystemConfig.dopplerVelocity;
    }
    
    public static synchronized void setDopplerVelocity(final float dopplerVelocity) {
        SoundSystemConfig.dopplerVelocity = dopplerVelocity;
    }
    
    public static synchronized float getDefaultRolloff() {
        return SoundSystemConfig.defaultRolloffFactor;
    }
    
    public static synchronized void setDefaultFadeDistance(final float defaultFadeDistance) {
        SoundSystemConfig.defaultFadeDistance = defaultFadeDistance;
    }
    
    public static synchronized float getDefaultFadeDistance() {
        return SoundSystemConfig.defaultFadeDistance;
    }
    
    public static synchronized void setSoundFilesPackage(final String soundFilesPackage) {
        SoundSystemConfig.soundFilesPackage = soundFilesPackage;
    }
    
    public static synchronized String getSoundFilesPackage() {
        return SoundSystemConfig.soundFilesPackage;
    }
    
    public static synchronized void setStreamingBufferSize(final int streamingBufferSize) {
        SoundSystemConfig.streamingBufferSize = streamingBufferSize;
    }
    
    public static synchronized int getStreamingBufferSize() {
        return SoundSystemConfig.streamingBufferSize;
    }
    
    public static synchronized void setNumberStreamingBuffers(final int numberStreamingBuffers) {
        SoundSystemConfig.numberStreamingBuffers = numberStreamingBuffers;
    }
    
    public static synchronized int getNumberStreamingBuffers() {
        return SoundSystemConfig.numberStreamingBuffers;
    }
    
    public static synchronized void setStreamQueueFormatsMatch(final boolean streamQueueFormatsMatch) {
        SoundSystemConfig.streamQueueFormatsMatch = streamQueueFormatsMatch;
    }
    
    public static synchronized boolean getStreamQueueFormatsMatch() {
        return SoundSystemConfig.streamQueueFormatsMatch;
    }
    
    public static synchronized void setMaxFileSize(final int maxFileSize) {
        SoundSystemConfig.maxFileSize = maxFileSize;
    }
    
    public static synchronized int getMaxFileSize() {
        return SoundSystemConfig.maxFileSize;
    }
    
    public static synchronized void setFileChunkSize(final int fileChunkSize) {
        SoundSystemConfig.fileChunkSize = fileChunkSize;
    }
    
    public static synchronized int getFileChunkSize() {
        return SoundSystemConfig.fileChunkSize;
    }
    
    public static synchronized String getOverrideMIDISynthesizer() {
        return SoundSystemConfig.overrideMIDISynthesizer;
    }
    
    public static synchronized void setOverrideMIDISynthesizer(final String overrideMIDISynthesizer) {
        SoundSystemConfig.overrideMIDISynthesizer = overrideMIDISynthesizer;
    }
    
    public static synchronized void setCodec(final String s, final Class clazz) {
        if (s == null) {
            throw new SoundSystemException("Parameter 'extension' null in method 'setCodec'.", 2);
        }
        if (clazz == null) {
            throw new SoundSystemException("Parameter 'iCodecClass' null in method 'setCodec'.", 2);
        }
        if (!ICodec.class.isAssignableFrom(clazz)) {
            throw new SoundSystemException("The specified class does not implement interface 'ICodec' in method 'setCodec'", 3);
        }
        if (SoundSystemConfig.codecs == null) {
            SoundSystemConfig.codecs = new LinkedList();
        }
        final ListIterator listIterator = SoundSystemConfig.codecs.listIterator();
        while (listIterator.hasNext()) {
            if (s.matches(listIterator.next().extensionRegX)) {
                listIterator.remove();
            }
        }
        SoundSystemConfig.codecs.add(new SoundSystemConfig$Codec(s, clazz));
        if (s.matches(SoundSystemConfig.EXTENSION_MIDI)) {
            SoundSystemConfig.midiCodec = true;
        }
    }
    
    public static synchronized ICodec getCodec(final String s) {
        if (SoundSystemConfig.codecs == null) {
            return null;
        }
        final ListIterator listIterator = SoundSystemConfig.codecs.listIterator();
        while (listIterator.hasNext()) {
            final SoundSystemConfig$Codec soundSystemConfig$Codec = listIterator.next();
            if (s.matches(soundSystemConfig$Codec.extensionRegX)) {
                return soundSystemConfig$Codec.getInstance();
            }
        }
        return null;
    }
    
    public static boolean midiCodec() {
        return SoundSystemConfig.midiCodec;
    }
    
    public static void addStreamListener(final IStreamListener streamListener) {
        synchronized (SoundSystemConfig.streamListenersLock) {
            if (SoundSystemConfig.streamListeners == null) {
                SoundSystemConfig.streamListeners = new LinkedList();
            }
            if (!SoundSystemConfig.streamListeners.contains(streamListener)) {
                SoundSystemConfig.streamListeners.add(streamListener);
            }
        }
    }
    
    public static void removeStreamListener(final IStreamListener streamListener) {
        synchronized (SoundSystemConfig.streamListenersLock) {
            if (SoundSystemConfig.streamListeners == null) {
                SoundSystemConfig.streamListeners = new LinkedList();
            }
            if (SoundSystemConfig.streamListeners.contains(streamListener)) {
                SoundSystemConfig.streamListeners.remove(streamListener);
            }
        }
    }
    
    public static void notifyEOS(final String s, final int n) {
        synchronized (SoundSystemConfig.streamListenersLock) {
            if (SoundSystemConfig.streamListeners == null) {
                return;
            }
        }
        new SoundSystemConfig$1(s, n).start();
    }
    
    private static void errorMessage(final String s) {
        if (SoundSystemConfig.logger != null) {
            SoundSystemConfig.logger.errorMessage("SoundSystemConfig", s, 0);
        }
    }
    
    private static Object runMethod(final Class clazz, final String s, final Class[] array, final Object[] array2) {
        Method method;
        try {
            method = clazz.getMethod(s, (Class[])array);
        }
        catch (NoSuchMethodException ex) {
            errorMessage("NoSuchMethodException thrown when attempting to call method '" + s + "' in " + "method 'runMethod'");
            return null;
        }
        catch (SecurityException ex2) {
            errorMessage("Access denied when attempting to call method '" + s + "' in method 'runMethod'");
            return null;
        }
        catch (NullPointerException ex3) {
            errorMessage("NullPointerException thrown when attempting to call method '" + s + "' in " + "method 'runMethod'");
            return null;
        }
        if (method == null) {
            errorMessage("Method '" + s + "' not found for the class " + "specified in method 'runMethod'");
            return null;
        }
        Object invoke;
        try {
            invoke = method.invoke(null, array2);
        }
        catch (IllegalAccessException ex4) {
            errorMessage("IllegalAccessException thrown when attempting to invoke method '" + s + "' in " + "method 'runMethod'");
            return null;
        }
        catch (IllegalArgumentException ex5) {
            errorMessage("IllegalArgumentException thrown when attempting to invoke method '" + s + "' in " + "method 'runMethod'");
            return null;
        }
        catch (InvocationTargetException ex6) {
            errorMessage("InvocationTargetException thrown while attempting to invoke method 'Library.getTitle' in method 'getLibraryTitle'");
            return null;
        }
        catch (NullPointerException ex7) {
            errorMessage("NullPointerException thrown when attempting to invoke method '" + s + "' in " + "method 'runMethod'");
            return null;
        }
        catch (ExceptionInInitializerError exceptionInInitializerError) {
            errorMessage("ExceptionInInitializerError thrown when attempting to invoke method '" + s + "' in " + "method 'runMethod'");
            return null;
        }
        return invoke;
    }
    
    static {
        THREAD_SYNC = new Object();
        SoundSystemConfig.EXTENSION_MIDI = ".*[mM][iI][dD][iI]?$";
        SoundSystemConfig.PREFIX_URL = "^[hH][tT][tT][pP]://.*";
        SoundSystemConfig.logger = null;
        SoundSystemConfig.codecs = null;
        SoundSystemConfig.streamListeners = null;
        streamListenersLock = new Object();
        SoundSystemConfig.numberNormalChannels = 28;
        SoundSystemConfig.numberStreamingChannels = 4;
        SoundSystemConfig.masterGain = 1.0f;
        SoundSystemConfig.defaultAttenuationModel = 1;
        SoundSystemConfig.defaultRolloffFactor = 0.03f;
        SoundSystemConfig.dopplerFactor = 0.0f;
        SoundSystemConfig.dopplerVelocity = 1.0f;
        SoundSystemConfig.defaultFadeDistance = 1000.0f;
        SoundSystemConfig.soundFilesPackage = "Sounds/";
        SoundSystemConfig.streamingBufferSize = 131072;
        SoundSystemConfig.numberStreamingBuffers = 3;
        SoundSystemConfig.streamQueueFormatsMatch = false;
        SoundSystemConfig.maxFileSize = 268435456;
        SoundSystemConfig.fileChunkSize = 1048576;
        SoundSystemConfig.midiCodec = false;
        SoundSystemConfig.overrideMIDISynthesizer = "";
    }
}
