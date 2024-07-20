/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package paulscode.sound;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import paulscode.sound.ICodec;
import paulscode.sound.IStreamListener;
import paulscode.sound.Library;
import paulscode.sound.SoundSystemException;
import paulscode.sound.SoundSystemLogger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SoundSystemConfig {
    public static final Object THREAD_SYNC = new Object();
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_STREAMING = 1;
    public static final int ATTENUATION_NONE = 0;
    public static final int ATTENUATION_ROLLOFF = 1;
    public static final int ATTENUATION_LINEAR = 2;
    public static String EXTENSION_MIDI = ".*[mM][iI][dD][iI]?$";
    public static String PREFIX_URL = "^[hH][tT][tT][pP]://.*";
    private static SoundSystemLogger logger = null;
    private static LinkedList<Class> libraries;
    private static LinkedList<Codec> codecs;
    private static LinkedList<IStreamListener> streamListeners;
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

    public static void addLibrary(Class libraryClass) throws SoundSystemException {
        if (libraryClass == null) {
            throw new SoundSystemException("Parameter null in method 'addLibrary'", 2);
        }
        if (!Library.class.isAssignableFrom(libraryClass)) {
            throw new SoundSystemException("The specified class does not extend class 'Library' in method 'addLibrary'");
        }
        if (libraries == null) {
            libraries = new LinkedList();
        }
        if (!libraries.contains(libraryClass)) {
            libraries.add(libraryClass);
        }
    }

    public static void removeLibrary(Class libraryClass) throws SoundSystemException {
        if (libraries == null || libraryClass == null) {
            return;
        }
        libraries.remove(libraryClass);
    }

    public static LinkedList<Class> getLibraries() {
        return libraries;
    }

    public static boolean libraryCompatible(Class libraryClass) {
        if (libraryClass == null) {
            SoundSystemConfig.errorMessage("Parameter 'libraryClass' null in method'librayCompatible'");
            return false;
        }
        if (!Library.class.isAssignableFrom(libraryClass)) {
            SoundSystemConfig.errorMessage("The specified class does not extend class 'Library' in method 'libraryCompatible'");
            return false;
        }
        Object o = SoundSystemConfig.runMethod(libraryClass, "libraryCompatible", new Class[0], new Object[0]);
        if (o == null) {
            SoundSystemConfig.errorMessage("Method 'Library.libraryCompatible' returned 'null' in method 'libraryCompatible'");
            return false;
        }
        return (Boolean)o;
    }

    public static String getLibraryTitle(Class libraryClass) {
        if (libraryClass == null) {
            SoundSystemConfig.errorMessage("Parameter 'libraryClass' null in method'getLibrayTitle'");
            return null;
        }
        if (!Library.class.isAssignableFrom(libraryClass)) {
            SoundSystemConfig.errorMessage("The specified class does not extend class 'Library' in method 'getLibraryTitle'");
            return null;
        }
        Object o = SoundSystemConfig.runMethod(libraryClass, "getTitle", new Class[0], new Object[0]);
        if (o == null) {
            SoundSystemConfig.errorMessage("Method 'Library.getTitle' returned 'null' in method 'getLibraryTitle'");
            return null;
        }
        return (String)o;
    }

    public static String getLibraryDescription(Class libraryClass) {
        if (libraryClass == null) {
            SoundSystemConfig.errorMessage("Parameter 'libraryClass' null in method'getLibrayDescription'");
            return null;
        }
        if (!Library.class.isAssignableFrom(libraryClass)) {
            SoundSystemConfig.errorMessage("The specified class does not extend class 'Library' in method 'getLibraryDescription'");
            return null;
        }
        Object o = SoundSystemConfig.runMethod(libraryClass, "getDescription", new Class[0], new Object[0]);
        if (o == null) {
            SoundSystemConfig.errorMessage("Method 'Library.getDescription' returned 'null' in method 'getLibraryDescription'");
            return null;
        }
        return (String)o;
    }

    public static boolean reverseByteOrder(Class libraryClass) {
        if (libraryClass == null) {
            SoundSystemConfig.errorMessage("Parameter 'libraryClass' null in method'reverseByteOrder'");
            return false;
        }
        if (!Library.class.isAssignableFrom(libraryClass)) {
            SoundSystemConfig.errorMessage("The specified class does not extend class 'Library' in method 'reverseByteOrder'");
            return false;
        }
        Object o = SoundSystemConfig.runMethod(libraryClass, "reversByteOrder", new Class[0], new Object[0]);
        if (o == null) {
            SoundSystemConfig.errorMessage("Method 'Library.reverseByteOrder' returned 'null' in method 'getLibraryDescription'");
            return false;
        }
        return (Boolean)o;
    }

    public static void setLogger(SoundSystemLogger l) {
        logger = l;
    }

    public static SoundSystemLogger getLogger() {
        return logger;
    }

    public static synchronized void setNumberNormalChannels(int number) {
        numberNormalChannels = number;
    }

    public static synchronized int getNumberNormalChannels() {
        return numberNormalChannels;
    }

    public static synchronized void setNumberStreamingChannels(int number) {
        numberStreamingChannels = number;
    }

    public static synchronized int getNumberStreamingChannels() {
        return numberStreamingChannels;
    }

    public static synchronized void setMasterGain(float value) {
        masterGain = value;
    }

    public static synchronized float getMasterGain() {
        return masterGain;
    }

    public static synchronized void setDefaultAttenuation(int model) {
        defaultAttenuationModel = model;
    }

    public static synchronized int getDefaultAttenuation() {
        return defaultAttenuationModel;
    }

    public static synchronized void setDefaultRolloff(float rolloff) {
        defaultRolloffFactor = rolloff;
    }

    public static synchronized float getDopplerFactor() {
        return dopplerFactor;
    }

    public static synchronized void setDopplerFactor(float factor) {
        dopplerFactor = factor;
    }

    public static synchronized float getDopplerVelocity() {
        return dopplerVelocity;
    }

    public static synchronized void setDopplerVelocity(float velocity) {
        dopplerVelocity = velocity;
    }

    public static synchronized float getDefaultRolloff() {
        return defaultRolloffFactor;
    }

    public static synchronized void setDefaultFadeDistance(float distance) {
        defaultFadeDistance = distance;
    }

    public static synchronized float getDefaultFadeDistance() {
        return defaultFadeDistance;
    }

    public static synchronized void setSoundFilesPackage(String location) {
        soundFilesPackage = location;
    }

    public static synchronized String getSoundFilesPackage() {
        return soundFilesPackage;
    }

    public static synchronized void setStreamingBufferSize(int size) {
        streamingBufferSize = size;
    }

    public static synchronized int getStreamingBufferSize() {
        return streamingBufferSize;
    }

    public static synchronized void setNumberStreamingBuffers(int num) {
        numberStreamingBuffers = num;
    }

    public static synchronized int getNumberStreamingBuffers() {
        return numberStreamingBuffers;
    }

    public static synchronized void setStreamQueueFormatsMatch(boolean val) {
        streamQueueFormatsMatch = val;
    }

    public static synchronized boolean getStreamQueueFormatsMatch() {
        return streamQueueFormatsMatch;
    }

    public static synchronized void setMaxFileSize(int size) {
        maxFileSize = size;
    }

    public static synchronized int getMaxFileSize() {
        return maxFileSize;
    }

    public static synchronized void setFileChunkSize(int size) {
        fileChunkSize = size;
    }

    public static synchronized int getFileChunkSize() {
        return fileChunkSize;
    }

    public static synchronized String getOverrideMIDISynthesizer() {
        return overrideMIDISynthesizer;
    }

    public static synchronized void setOverrideMIDISynthesizer(String name) {
        overrideMIDISynthesizer = name;
    }

    public static synchronized void setCodec(String extension, Class iCodecClass) throws SoundSystemException {
        if (extension == null) {
            throw new SoundSystemException("Parameter 'extension' null in method 'setCodec'.", 2);
        }
        if (iCodecClass == null) {
            throw new SoundSystemException("Parameter 'iCodecClass' null in method 'setCodec'.", 2);
        }
        if (!ICodec.class.isAssignableFrom(iCodecClass)) {
            throw new SoundSystemException("The specified class does not implement interface 'ICodec' in method 'setCodec'", 3);
        }
        if (codecs == null) {
            codecs = new LinkedList();
        }
        ListIterator i = codecs.listIterator();
        while (i.hasNext()) {
            Codec codec = (Codec)i.next();
            if (!extension.matches(codec.extensionRegX)) continue;
            i.remove();
        }
        codecs.add(new Codec(extension, iCodecClass));
        if (extension.matches(EXTENSION_MIDI)) {
            midiCodec = true;
        }
    }

    public static synchronized ICodec getCodec(String filename) {
        if (codecs == null) {
            return null;
        }
        ListIterator i = codecs.listIterator();
        while (i.hasNext()) {
            Codec codec = (Codec)i.next();
            if (!filename.matches(codec.extensionRegX)) continue;
            return codec.getInstance();
        }
        return null;
    }

    public static boolean midiCodec() {
        return midiCodec;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addStreamListener(IStreamListener streamListener) {
        Object object = streamListenersLock;
        synchronized (object) {
            if (streamListeners == null) {
                streamListeners = new LinkedList();
            }
            if (!streamListeners.contains(streamListener)) {
                streamListeners.add(streamListener);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeStreamListener(IStreamListener streamListener) {
        Object object = streamListenersLock;
        synchronized (object) {
            if (streamListeners == null) {
                streamListeners = new LinkedList();
            }
            if (streamListeners.contains(streamListener)) {
                streamListeners.remove(streamListener);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void notifyEOS(String sourcename, int queueSize) {
        Object object = streamListenersLock;
        synchronized (object) {
            if (streamListeners == null) {
                return;
            }
        }
        final String srcName = sourcename;
        final int qSize = queueSize;
        new Thread(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                Object object = streamListenersLock;
                synchronized (object) {
                    if (streamListeners == null) {
                        return;
                    }
                    ListIterator i = streamListeners.listIterator();
                    while (i.hasNext()) {
                        IStreamListener streamListener = (IStreamListener)i.next();
                        if (streamListener == null) {
                            i.remove();
                            continue;
                        }
                        streamListener.endOfStream(srcName, qSize);
                    }
                }
            }
        }.start();
    }

    private static void errorMessage(String message) {
        if (logger != null) {
            logger.errorMessage("SoundSystemConfig", message, 0);
        }
    }

    private static Object runMethod(Class c, String method, Class[] paramTypes, Object[] params) {
        Method m = null;
        try {
            m = c.getMethod(method, paramTypes);
        } catch (NoSuchMethodException nsme) {
            SoundSystemConfig.errorMessage("NoSuchMethodException thrown when attempting to call method '" + method + "' in " + "method 'runMethod'");
            return null;
        } catch (SecurityException se) {
            SoundSystemConfig.errorMessage("Access denied when attempting to call method '" + method + "' in method 'runMethod'");
            return null;
        } catch (NullPointerException npe) {
            SoundSystemConfig.errorMessage("NullPointerException thrown when attempting to call method '" + method + "' in " + "method 'runMethod'");
            return null;
        }
        if (m == null) {
            SoundSystemConfig.errorMessage("Method '" + method + "' not found for the class " + "specified in method 'runMethod'");
            return null;
        }
        Object o = null;
        try {
            o = m.invoke(null, params);
        } catch (IllegalAccessException iae) {
            SoundSystemConfig.errorMessage("IllegalAccessException thrown when attempting to invoke method '" + method + "' in " + "method 'runMethod'");
            return null;
        } catch (IllegalArgumentException iae) {
            SoundSystemConfig.errorMessage("IllegalArgumentException thrown when attempting to invoke method '" + method + "' in " + "method 'runMethod'");
            return null;
        } catch (InvocationTargetException ite) {
            SoundSystemConfig.errorMessage("InvocationTargetException thrown while attempting to invoke method 'Library.getTitle' in method 'getLibraryTitle'");
            return null;
        } catch (NullPointerException npe) {
            SoundSystemConfig.errorMessage("NullPointerException thrown when attempting to invoke method '" + method + "' in " + "method 'runMethod'");
            return null;
        } catch (ExceptionInInitializerError eiie) {
            SoundSystemConfig.errorMessage("ExceptionInInitializerError thrown when attempting to invoke method '" + method + "' in " + "method 'runMethod'");
            return null;
        }
        return o;
    }

    static {
        codecs = null;
        streamListeners = null;
        streamListenersLock = new Object();
        numberNormalChannels = 28;
        numberStreamingChannels = 4;
        masterGain = 1.0f;
        defaultAttenuationModel = 1;
        defaultRolloffFactor = 0.03f;
        dopplerFactor = 0.0f;
        dopplerVelocity = 1.0f;
        defaultFadeDistance = 1000.0f;
        soundFilesPackage = "Sounds/";
        streamingBufferSize = 131072;
        numberStreamingBuffers = 3;
        streamQueueFormatsMatch = false;
        maxFileSize = 0x10000000;
        fileChunkSize = 0x100000;
        midiCodec = false;
        overrideMIDISynthesizer = "";
    }

    private static class Codec {
        public String extensionRegX = "";
        public Class iCodecClass;

        public Codec(String extension, Class iCodecClass) {
            if (extension != null && extension.length() > 0) {
                this.extensionRegX = ".*";
                for (int x = 0; x < extension.length(); ++x) {
                    String c = extension.substring(x, x + 1);
                    this.extensionRegX = this.extensionRegX + "[" + c.toLowerCase(Locale.ENGLISH) + c.toUpperCase(Locale.ENGLISH) + "]";
                }
                this.extensionRegX = this.extensionRegX + "$";
            }
            this.iCodecClass = iCodecClass;
        }

        public ICodec getInstance() {
            if (this.iCodecClass == null) {
                return null;
            }
            Object o = null;
            try {
                o = this.iCodecClass.newInstance();
            } catch (InstantiationException ie) {
                this.instantiationErrorMessage();
                return null;
            } catch (IllegalAccessException iae) {
                this.instantiationErrorMessage();
                return null;
            } catch (ExceptionInInitializerError eiie) {
                this.instantiationErrorMessage();
                return null;
            } catch (SecurityException se) {
                this.instantiationErrorMessage();
                return null;
            }
            if (o == null) {
                this.instantiationErrorMessage();
                return null;
            }
            return o;
        }

        private void instantiationErrorMessage() {
            SoundSystemConfig.errorMessage("Unrecognized ICodec implementation in method 'getInstance'.  Ensure that the implementing class has one public, parameterless constructor.");
        }
    }
}

