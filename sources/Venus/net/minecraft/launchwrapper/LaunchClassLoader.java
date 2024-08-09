/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.launchwrapper;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipFile;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LogWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class LaunchClassLoader
extends URLClassLoader {
    public static final int BUFFER_SIZE = 4096;
    private List<URL> sources;
    private ClassLoader parent = this.getClass().getClassLoader();
    private List<IClassTransformer> transformers = new ArrayList<IClassTransformer>(2);
    private Map<String, Class<?>> cachedClasses = new ConcurrentHashMap();
    private Set<String> invalidClasses = new HashSet<String>(1000);
    private Set<String> classLoaderExceptions = new HashSet<String>();
    private Set<String> transformerExceptions = new HashSet<String>();
    private Map<String, byte[]> resourceCache = new ConcurrentHashMap<String, byte[]>(1000);
    private Set<String> negativeResourceCache = Collections.newSetFromMap(new ConcurrentHashMap());
    private IClassNameTransformer renameTransformer;
    private final ThreadLocal<byte[]> loadBuffer = new ThreadLocal();
    private static final String[] RESERVED_NAMES = new String[]{"CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
    private static final boolean DEBUG = Boolean.parseBoolean(System.getProperty("legacy.debugClassLoading", "false"));
    private static final boolean DEBUG_FINER = DEBUG && Boolean.parseBoolean(System.getProperty("legacy.debugClassLoadingFiner", "false"));
    private static final boolean DEBUG_SAVE = DEBUG && Boolean.parseBoolean(System.getProperty("legacy.debugClassLoadingSave", "false"));
    private static File tempFolder = null;

    public LaunchClassLoader(URL[] uRLArray) {
        super(uRLArray, LaunchClassLoader.getParentClassLoader());
        this.sources = new ArrayList<URL>(Arrays.asList(uRLArray));
        this.addClassLoaderExclusion("java.");
        this.addClassLoaderExclusion("sun.");
        this.addClassLoaderExclusion("com.sun.");
        this.addClassLoaderExclusion("org.lwjgl.");
        this.addClassLoaderExclusion("org.apache.logging.");
        this.addClassLoaderExclusion("net.minecraft.launchwrapper.");
        this.addTransformerExclusion("javax.");
        this.addTransformerExclusion("argo.");
        this.addTransformerExclusion("org.objectweb.asm.");
        this.addTransformerExclusion("com.google.common.");
        this.addTransformerExclusion("org.bouncycastle.");
        this.addTransformerExclusion("net.minecraft.launchwrapper.injector.");
        if (DEBUG_SAVE) {
            int n = 1;
            tempFolder = new File(Launch.minecraftHome, "CLASSLOADER_TEMP");
            while (tempFolder.exists() && n <= 10) {
                tempFolder = new File(Launch.minecraftHome, "CLASSLOADER_TEMP" + n++);
            }
            if (tempFolder.exists()) {
                LogWrapper.info("DEBUG_SAVE enabled, but 10 temp directories already exist, clean them and try again.", new Object[0]);
                tempFolder = null;
            } else {
                LogWrapper.info("DEBUG_SAVE Enabled, saving all classes to \"%s\"", tempFolder.getAbsolutePath().replace('\\', '/'));
                tempFolder.mkdirs();
            }
        }
    }

    public void registerTransformer(String string) {
        try {
            IClassTransformer iClassTransformer = (IClassTransformer)this.loadClass(string).newInstance();
            this.transformers.add(iClassTransformer);
            if (iClassTransformer instanceof IClassNameTransformer && this.renameTransformer == null) {
                this.renameTransformer = (IClassNameTransformer)((Object)iClassTransformer);
            }
        } catch (Exception exception) {
            LogWrapper.log(Level.ERROR, exception, "A critical problem occurred registering the ASM transformer class %s", string);
        }
    }

    @Override
    public Class<?> findClass(String string) throws ClassNotFoundException {
        if (this.invalidClasses.contains(string)) {
            throw new ClassNotFoundException(string);
        }
        for (String string2 : this.classLoaderExceptions) {
            if (!string.startsWith(string2)) continue;
            return this.parent.loadClass(string);
        }
        if (this.cachedClasses.containsKey(string)) {
            return this.cachedClasses.get(string);
        }
        for (String string2 : this.transformerExceptions) {
            if (!string.startsWith(string2)) continue;
            try {
                Class<?> clazz = super.findClass(string);
                this.cachedClasses.put(string, clazz);
                return clazz;
            } catch (ClassNotFoundException classNotFoundException) {
                this.invalidClasses.add(string);
                throw classNotFoundException;
            }
        }
        try {
            Object object;
            Object object2;
            Object object3;
            String string2;
            String string3 = this.transformName(string);
            if (this.cachedClasses.containsKey(string3)) {
                return this.cachedClasses.get(string3);
            }
            string2 = this.untransformName(string);
            int n = string2.lastIndexOf(46);
            String string4 = n == -1 ? "" : string2.substring(0, n);
            String string5 = string2.replace('.', '/').concat(".class");
            URLConnection uRLConnection = this.findCodeSourceConnectionFor(string5);
            CodeSigner[] codeSignerArray = null;
            if (n > -1 && !string2.startsWith("net.minecraft.") && !string2.startsWith("com.mojang.blaze3d.")) {
                if (uRLConnection instanceof JarURLConnection) {
                    object3 = (JarURLConnection)uRLConnection;
                    object2 = ((JarURLConnection)object3).getJarFile();
                    if (object2 != null && ((JarFile)object2).getManifest() != null) {
                        object = ((JarFile)object2).getManifest();
                        JarEntry jarEntry = ((JarFile)object2).getJarEntry(string5);
                        Package package_ = this.getPackage(string4);
                        this.getClassBytes(string2);
                        codeSignerArray = jarEntry.getCodeSigners();
                        if (package_ == null) {
                            package_ = this.definePackage(string4, (Manifest)object, ((JarURLConnection)object3).getJarFileURL());
                        } else if (package_.isSealed() && !package_.isSealed(((JarURLConnection)object3).getJarFileURL())) {
                            LogWrapper.severe("The jar file %s is trying to seal already secured path %s", ((ZipFile)object2).getName(), string4);
                        } else if (this.isSealed(string4, (Manifest)object)) {
                            LogWrapper.severe("The jar file %s has a security seal for path %s, but that path is defined and not secure", ((ZipFile)object2).getName(), string4);
                        }
                    }
                } else {
                    object3 = this.getPackage(string4);
                    if (object3 == null) {
                        object3 = this.definePackage(string4, null, null, null, null, null, null, null);
                    } else if (((Package)object3).isSealed()) {
                        LogWrapper.severe("The URL %s is defining elements for sealed path %s", uRLConnection.getURL(), string4);
                    }
                }
            }
            object3 = this.runTransformers(string2, string3, this.getClassBytes(string2));
            if (DEBUG_SAVE) {
                this.saveTransformedClass((byte[])object3, string3);
            }
            object2 = uRLConnection == null ? null : new CodeSource(uRLConnection.getURL(), codeSignerArray);
            object = this.defineClass(string3, (byte[])object3, 0, ((Object)object3).length, (CodeSource)object2);
            this.cachedClasses.put(string3, (Class<?>)object);
            return object;
        } catch (Throwable throwable) {
            this.invalidClasses.add(string);
            if (DEBUG) {
                LogWrapper.log(Level.TRACE, throwable, "Exception encountered attempting classloading of %s", string);
                LogManager.getLogger("LaunchWrapper").log(Level.ERROR, "Exception encountered attempting classloading of {}", throwable);
            }
            throw new ClassNotFoundException(string, throwable);
        }
    }

    private void saveTransformedClass(byte[] byArray, String string) {
        if (tempFolder == null) {
            return;
        }
        File file = new File(tempFolder, string.replace('.', File.separatorChar) + ".class");
        File file2 = file.getParentFile();
        if (!file2.exists()) {
            file2.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            LogWrapper.fine("Saving transformed class \"%s\" to \"%s\"", string, file.getAbsolutePath().replace('\\', '/'));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ((OutputStream)fileOutputStream).write(byArray);
            ((OutputStream)fileOutputStream).close();
        } catch (IOException iOException) {
            LogWrapper.log(Level.WARN, iOException, "Could not save transformed class \"%s\"", string);
        }
    }

    private String untransformName(String string) {
        if (this.renameTransformer != null) {
            return this.renameTransformer.unmapClassName(string);
        }
        return string;
    }

    private String transformName(String string) {
        if (this.renameTransformer != null) {
            return this.renameTransformer.remapClassName(string);
        }
        return string;
    }

    private boolean isSealed(String string, Manifest manifest) {
        Attributes attributes = manifest.getAttributes(string);
        String string2 = null;
        if (attributes != null) {
            string2 = attributes.getValue(Attributes.Name.SEALED);
        }
        if (string2 == null && (attributes = manifest.getMainAttributes()) != null) {
            string2 = attributes.getValue(Attributes.Name.SEALED);
        }
        return "true".equalsIgnoreCase(string2);
    }

    private URLConnection findCodeSourceConnectionFor(String string) {
        URL uRL = this.findResource(string);
        if (uRL != null) {
            try {
                return uRL.openConnection();
            } catch (IOException iOException) {
                throw new RuntimeException(iOException);
            }
        }
        return null;
    }

    private byte[] runTransformers(String string, String string2, byte[] byArray) {
        if (DEBUG_FINER) {
            LogWrapper.finest("Beginning transform of {%s (%s)} Start Length: %d", string, string2, byArray == null ? 0 : byArray.length);
            for (IClassTransformer iClassTransformer : this.transformers) {
                String string3 = iClassTransformer.getClass().getName();
                LogWrapper.finest("Before Transformer {%s (%s)} %s: %d", string, string2, string3, byArray == null ? 0 : byArray.length);
                byArray = iClassTransformer.transform(string, string2, byArray);
                LogWrapper.finest("After  Transformer {%s (%s)} %s: %d", string, string2, string3, byArray == null ? 0 : byArray.length);
            }
            LogWrapper.finest("Ending transform of {%s (%s)} Start Length: %d", string, string2, byArray == null ? 0 : byArray.length);
        } else {
            for (IClassTransformer iClassTransformer : this.transformers) {
                byArray = iClassTransformer.transform(string, string2, byArray);
            }
        }
        return byArray;
    }

    @Override
    public void addURL(URL uRL) {
        super.addURL(uRL);
        this.sources.add(uRL);
    }

    public List<URL> getSources() {
        return this.sources;
    }

    private byte[] readFully(InputStream inputStream) {
        try {
            byte[] byArray;
            int n;
            byte[] byArray2 = this.getOrCreateBuffer();
            int n2 = 0;
            while ((n = inputStream.read(byArray2, n2, byArray2.length - n2)) != -1) {
                if ((n2 += n) < byArray2.length - 1) continue;
                byArray = new byte[byArray2.length + 4096];
                System.arraycopy(byArray2, 0, byArray, 0, byArray2.length);
                byArray2 = byArray;
            }
            byArray = new byte[n2];
            System.arraycopy(byArray2, 0, byArray, 0, n2);
            return byArray;
        } catch (Throwable throwable) {
            LogWrapper.log(Level.WARN, throwable, "Problem loading class", new Object[0]);
            return new byte[0];
        }
    }

    private byte[] getOrCreateBuffer() {
        byte[] byArray = this.loadBuffer.get();
        if (byArray == null) {
            this.loadBuffer.set(new byte[4096]);
            byArray = this.loadBuffer.get();
        }
        return byArray;
    }

    public List<IClassTransformer> getTransformers() {
        return Collections.unmodifiableList(this.transformers);
    }

    public void addClassLoaderExclusion(String string) {
        this.classLoaderExceptions.add(string);
    }

    public void addTransformerExclusion(String string) {
        this.transformerExceptions.add(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] getClassBytes(String string) throws IOException {
        URL uRL;
        byte[] byArray;
        block9: {
            byte[] byArray2;
            if (this.negativeResourceCache.contains(string)) {
                return null;
            }
            if (this.resourceCache.containsKey(string)) {
                return this.resourceCache.get(string);
            }
            if (string.indexOf(46) == -1) {
                for (String string2 : RESERVED_NAMES) {
                    if (!string.toUpperCase(Locale.ENGLISH).startsWith(string2) || (byArray = this.getClassBytes("_" + string)) == null) continue;
                    this.resourceCache.put(string, byArray);
                    return byArray;
                }
            }
            Closeable closeable = null;
            try {
                String string3 = string.replace('.', '/').concat(".class");
                uRL = this.findResource(string3);
                if (uRL != null) break block9;
                if (DEBUG) {
                    LogWrapper.finest("Failed to find class resource %s", string3);
                }
                this.negativeResourceCache.add(string);
                byArray2 = null;
            } catch (Throwable throwable) {
                LaunchClassLoader.closeSilently(closeable);
                throw throwable;
            }
            LaunchClassLoader.closeSilently(closeable);
            return byArray2;
        }
        InputStream inputStream = uRL.openStream();
        if (DEBUG) {
            LogWrapper.finest("Loading class %s from resource %s", string, uRL.toString());
        }
        byte[] byArray3 = this.readFully(inputStream);
        this.resourceCache.put(string, byArray3);
        byArray = byArray3;
        LaunchClassLoader.closeSilently(inputStream);
        return byArray;
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public void clearNegativeEntries(Set<String> set) {
        this.negativeResourceCache.removeAll(set);
    }

    private static ClassLoader getParentClassLoader() {
        if (!System.getProperty("java.version").startsWith("1.")) {
            try {
                return (ClassLoader)ClassLoader.class.getDeclaredMethod("getPlatformClassLoader", new Class[0]).invoke(null, new Object[0]);
            } catch (Throwable throwable) {
                LogWrapper.warning("No platform classloader: " + System.getProperty("java.version"), new Object[0]);
            }
        }
        return null;
    }
}

