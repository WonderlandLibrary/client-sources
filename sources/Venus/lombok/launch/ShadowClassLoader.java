/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package lombok.launch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.launch.PackageShader;

class ShadowClassLoader
extends ClassLoader {
    private static final String SELF_NAME = "lombok/launch/ShadowClassLoader.class";
    private static final ConcurrentMap<String, Class<?>> highlanderMap = new ConcurrentHashMap();
    private final String SELF_BASE;
    private final File SELF_BASE_FILE;
    private final int SELF_BASE_LENGTH;
    private final List<File> override = new ArrayList<File>();
    private final String sclSuffix;
    private final List<String> parentExclusion = new ArrayList<String>();
    private final List<String> highlanders = new ArrayList<String>();
    private final Set<ClassLoader> prependedParentLoaders = Collections.newSetFromMap(new IdentityHashMap());
    private final PackageShader shader = new PackageShader("org/objectweb/asm/", "org/lombokweb/asm/");
    private final Map<String, Object> mapJarPathToTracker = new HashMap<String, Object>();
    private static final Map<Object, String> mapTrackerToJarPath = new WeakHashMap<Object, String>();
    private static final Map<Object, Set<String>> mapTrackerToJarContents = new WeakHashMap<Object, Set<String>>();
    private Map<String, Boolean> fileRootCache = new HashMap<String, Boolean>();
    private Map<String, Boolean> jarLocCache = new HashMap<String, Boolean>();

    public void prependParent(ClassLoader classLoader) {
        if (classLoader == null) {
            return;
        }
        if (classLoader == this.getParent()) {
            return;
        }
        this.prependedParentLoaders.add(classLoader);
    }

    ShadowClassLoader(ClassLoader classLoader, String string, String string2, List<String> list, List<String> list2) {
        super(classLoader);
        Object object;
        Object object2;
        this.sclSuffix = string;
        if (list != null) {
            object2 = list.iterator();
            while (object2.hasNext()) {
                object = object2.next();
                if (!((String)(object = ((String)object).replace(".", "/"))).endsWith("/")) {
                    object = String.valueOf(object) + "/";
                }
                this.parentExclusion.add((String)object);
            }
        }
        if (list2 != null) {
            object2 = list2.iterator();
            while (object2.hasNext()) {
                object = object2.next();
                this.highlanders.add((String)object);
            }
        }
        if (string2 != null) {
            this.SELF_BASE = string2;
            this.SELF_BASE_LENGTH = string2.length();
        } else {
            String string3;
            object = ShadowClassLoader.class.getResource("ShadowClassLoader.class");
            Object object3 = object2 = object == null ? null : ((URL)object).toString();
            if (object2 == null || !((String)object2).endsWith(SELF_NAME)) {
                ClassLoader classLoader2 = ShadowClassLoader.class.getClassLoader();
                throw new RuntimeException("ShadowLoader can't find itself. SCL loader type: " + (classLoader2 == null ? "*NULL*" : classLoader2.getClass().toString()));
            }
            this.SELF_BASE_LENGTH = ((String)object2).length() - 37;
            this.SELF_BASE = string3 = ShadowClassLoader.urlDecode(((String)object2).substring(0, this.SELF_BASE_LENGTH));
        }
        this.SELF_BASE_FILE = this.SELF_BASE.startsWith("jar:file:") && this.SELF_BASE.endsWith("!/") ? new File(this.SELF_BASE.substring(9, this.SELF_BASE.length() - 2)) : (this.SELF_BASE.startsWith("file:") ? new File(this.SELF_BASE.substring(5)) : new File(this.SELF_BASE));
        object = System.getProperty("shadow.override." + string);
        if (object != null && !((String)object).isEmpty()) {
            String[] stringArray = ((String)object).split("\\s*" + (File.pathSeparatorChar == ';' ? ";" : ":") + "\\s*");
            int n = stringArray.length;
            int n2 = 0;
            while (n2 < n) {
                object2 = stringArray[n2];
                if (((String)object2).endsWith("/*") || ((String)object2).endsWith(String.valueOf(File.separator) + "*")) {
                    this.addOverrideJarDir(((String)object2).substring(0, ((String)object2).length() - 2));
                } else {
                    this.addOverrideClasspathEntry((String)object2);
                }
                ++n2;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Set<String> getOrMakeJarListing(String string) {
        Map<Object, String> map = mapTrackerToJarPath;
        synchronized (map) {
            Map.Entry<Object, String> entry2;
            Object object = this.mapJarPathToTracker.get(string);
            if (object != null) {
                return mapTrackerToJarContents.get(object);
            }
            for (Map.Entry<Object, String> entry2 : mapTrackerToJarPath.entrySet()) {
                if (!entry2.getValue().equals(string)) continue;
                Object object2 = entry2.getKey();
                this.mapJarPathToTracker.put(string, object2);
                return mapTrackerToJarContents.get(object2);
            }
            entry2 = new Map.Entry<Object, String>();
            Set<String> set = this.getJarMemberSet(string);
            mapTrackerToJarContents.put(entry2, set);
            mapTrackerToJarPath.put(entry2, string);
            this.mapJarPathToTracker.put(string, entry2);
            return set;
        }
    }

    private Set<String> getJarMemberSet(String string) {
        try {
            int n = 1;
            JarFile jarFile = new JarFile(string);
            int n2 = Integer.highestOneBit(jarFile.size());
            if (n2 != jarFile.size()) {
                n2 <<= 1;
            }
            if (n2 == 0) {
                n2 = 1;
            }
            HashSet<String> hashSet = new HashSet<String>(n2 >> n, 1 << n);
            try {
                try {
                    Enumeration<JarEntry> enumeration = jarFile.entries();
                    while (enumeration.hasMoreElements()) {
                        JarEntry jarEntry = enumeration.nextElement();
                        if (jarEntry.isDirectory()) continue;
                        hashSet.add(jarEntry.getName());
                    }
                } catch (Exception exception) {
                    jarFile.close();
                }
            } finally {
                jarFile.close();
            }
            return hashSet;
        } catch (Exception exception) {
            return Collections.emptySet();
        }
    }

    private URL getResourceFromLocation(String string, String string2, File file) {
        File file2;
        if (file.isDirectory()) {
            try {
                File file3;
                if (string2 != null && (file3 = new File(file, string2)).isFile() && file3.canRead()) {
                    return file3.toURI().toURL();
                }
                file3 = new File(file, string);
                if (file3.isFile() && file3.canRead()) {
                    return file3.toURI().toURL();
                }
                return null;
            } catch (MalformedURLException malformedURLException) {
                return null;
            }
        }
        if (!file.isFile() || !file.canRead()) {
            return null;
        }
        try {
            file2 = file.getCanonicalFile();
        } catch (Exception exception) {
            file2 = file.getAbsoluteFile();
        }
        Set<String> set = this.getOrMakeJarListing(file2.getAbsolutePath());
        String string3 = file2.toURI().toString();
        try {
            if (set.contains(string2)) {
                return new URI("jar:" + string3 + "!/" + string2).toURL();
            }
        } catch (Exception exception) {}
        try {
            if (set.contains(string)) {
                return new URI("jar:" + string3 + "!/" + string).toURL();
            }
        } catch (Exception exception) {}
        return null;
    }

    private boolean partOfShadow(String string, String string2) {
        return string2.startsWith("java/") || string2.startsWith("sun/") || !this.inOwnBase(string, string2) && !this.isPartOfShadowSuffix(string, string2, this.sclSuffix);
    }

    private boolean inOwnBase(String string, String string2) {
        if (string == null) {
            return true;
        }
        return string.length() != this.SELF_BASE_LENGTH + string2.length() || !this.SELF_BASE.regionMatches(0, string, 0, this.SELF_BASE_LENGTH);
    }

    private static boolean sclFileContainsSuffix(InputStream inputStream, String string) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String string2 = bufferedReader.readLine();
        while (string2 != null) {
            if (!(string2 = string2.trim()).isEmpty() && string2.charAt(0) != '#' && string2.equals(string)) {
                return false;
            }
            string2 = bufferedReader.readLine();
        }
        return true;
    }

    private static String urlDecode(String string) {
        String string2 = string.replaceAll("\\+", "%2B");
        try {
            return URLDecoder.decode(string2, "UTF-8");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new InternalError("UTF-8 not supported");
        }
    }

    private boolean isPartOfShadowSuffixFileBased(String string, String string2) {
        boolean bl;
        String string3 = String.valueOf(string) + "::" + string2;
        Boolean bl2 = this.fileRootCache.get(string3);
        if (bl2 != null) {
            return bl2;
        }
        File file = new File(String.valueOf(string) + "/META-INF/ShadowClassLoader");
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            boolean bl3 = ShadowClassLoader.sclFileContainsSuffix(fileInputStream, string2);
            this.fileRootCache.put(string3, bl3);
            bl = bl3;
        } catch (Throwable throwable) {
            try {
                fileInputStream.close();
                throw throwable;
            } catch (FileNotFoundException fileNotFoundException) {
                this.fileRootCache.put(string3, false);
                return true;
            } catch (IOException iOException) {
                this.fileRootCache.put(string3, false);
                return true;
            }
        }
        fileInputStream.close();
        return bl;
    }

    /*
     * Exception decompiling
     */
    private boolean isPartOfShadowSuffixJarBased(String var1_1, String var2_2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 12[DOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:538)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at async.DecompilerRunnable.cfrDecompilation(DecompilerRunnable.java:348)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:309)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:31)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean isPartOfShadowSuffix(String string, String string2, String string3) {
        if (string == null) {
            return true;
        }
        if (string.startsWith("file:/")) {
            if ((string = ShadowClassLoader.urlDecode(string.substring(5))).length() <= string2.length() || !string.endsWith(string2) || string.charAt(string.length() - string2.length() - 1) != '/') {
                return true;
            }
            String string4 = string.substring(0, string.length() - string2.length() - 1);
            return this.isPartOfShadowSuffixFileBased(string4, string3);
        }
        if (string.startsWith("jar:")) {
            int n = string.indexOf(33);
            if (n == -1) {
                return true;
            }
            String string5 = string.substring(4, n);
            return this.isPartOfShadowSuffixJarBased(string5, string3);
        }
        return true;
    }

    private String toSclResourceName(String string) {
        return "SCL." + this.sclSuffix + "/" + string.substring(0, string.length() - 6) + ".SCL." + this.sclSuffix;
    }

    @Override
    public Enumeration<URL> getResources(String string) throws IOException {
        Object object;
        URL uRL;
        URL uRL2;
        String string2 = null;
        if (string.endsWith(".class")) {
            string2 = this.toSclResourceName(string);
        }
        Vector<Object> vector = new Vector<Object>();
        for (File object22 : this.override) {
            uRL2 = this.getResourceFromLocation(string, string2, object22);
            if (uRL2 == null) continue;
            vector.add(uRL2);
        }
        if (this.override.isEmpty() && (uRL = this.getResourceFromLocation(string, string2, this.SELF_BASE_FILE)) != null) {
            vector.add(uRL);
        }
        Enumeration<URL> enumeration = super.getResources(string);
        while (enumeration.hasMoreElements()) {
            object = enumeration.nextElement();
            if (!this.isPartOfShadowSuffix(((URL)object).toString(), string, this.sclSuffix)) continue;
            vector.add(object);
        }
        if (string2 != null) {
            object = super.getResources(string2);
            while (object.hasMoreElements()) {
                uRL2 = object.nextElement();
                if (!this.isPartOfShadowSuffix(uRL2.toString(), string2, this.sclSuffix)) continue;
                vector.add(uRL2);
            }
        }
        return vector.elements();
    }

    @Override
    public URL getResource(String string) {
        return this.getResource_(string, false);
    }

    private URL getResource_(String string, boolean bl) {
        Serializable serializable;
        String string2 = null;
        String string3 = string = this.shader == null ? string : this.shader.reverseResourceName(string);
        if (string.endsWith(".class")) {
            string2 = this.toSclResourceName(string);
        }
        Object object = this.override.iterator();
        while (object.hasNext()) {
            serializable = object.next();
            URL uRL = this.getResourceFromLocation(string, string2, (File)serializable);
            if (uRL == null) continue;
            return uRL;
        }
        if (!this.override.isEmpty()) {
            if (bl) {
                return null;
            }
            if (string2 != null) {
                try {
                    serializable = this.getResourceSkippingSelf(string2);
                    if (serializable != null) {
                        return serializable;
                    }
                } catch (IOException iOException) {}
            }
            try {
                return this.getResourceSkippingSelf(string);
            } catch (IOException iOException) {
                return null;
            }
        }
        serializable = this.getResourceFromLocation(string, string2, this.SELF_BASE_FILE);
        if (serializable != null) {
            return serializable;
        }
        if (string2 != null && (object = super.getResource(string2)) != null && (!bl || this.partOfShadow(((URL)object).toString(), string2))) {
            return object;
        }
        object = super.getResource(string);
        if (object != null && (!bl || this.partOfShadow(((URL)object).toString(), string))) {
            return object;
        }
        return null;
    }

    private boolean exclusionListMatch(String string) {
        for (String string2 : this.parentExclusion) {
            if (!string.startsWith(string2)) continue;
            return false;
        }
        return true;
    }

    private URL getResourceSkippingSelf(String string) throws IOException {
        URL uRL = super.getResource(string);
        if (uRL == null) {
            return null;
        }
        if (!this.partOfShadow(uRL.toString(), string)) {
            return uRL;
        }
        Enumeration<URL> enumeration = super.getResources(string);
        while (enumeration.hasMoreElements()) {
            uRL = enumeration.nextElement();
            if (this.partOfShadow(uRL.toString(), string)) continue;
            return uRL;
        }
        return null;
    }

    @Override
    public Class<?> loadClass(String string, boolean bl) throws ClassNotFoundException {
        URL uRL;
        block9: {
            Object object = this.findLoadedClass(string);
            if (object != null) {
                return object;
            }
            if (this.highlanders.contains(string) && (object = (Class)highlanderMap.get(string)) != null) {
                return object;
            }
            object = String.valueOf(string.replace(".", "/")) + ".class";
            uRL = this.getResource_((String)object, true);
            if (uRL == null && !this.exclusionListMatch((String)object)) {
                try {
                    for (ClassLoader classLoader : this.prependedParentLoaders) {
                        try {
                            Class<?> clazz = classLoader.loadClass(string);
                            if (clazz == null) continue;
                            return clazz;
                        } catch (Throwable throwable) {}
                    }
                    return super.loadClass(string, bl);
                } catch (ClassNotFoundException classNotFoundException) {
                    uRL = this.getResource_("secondaryLoading.SCL." + this.sclSuffix + "/" + string.replace(".", "/") + ".SCL." + this.sclSuffix, true);
                    if (uRL != null) break block9;
                    throw classNotFoundException;
                }
            }
        }
        if (uRL == null) {
            throw new ClassNotFoundException(string);
        }
        return this.urlToDefineClass(string, uRL, bl);
    }

    private Class<?> urlToDefineClass(String string, URL uRL, boolean bl) throws ClassNotFoundException {
        Class<?> clazz;
        Class<?> clazz2;
        block16: {
            Object object;
            byte[] byArray;
            int n = 0;
            try {
                clazz2 = uRL.openStream();
                try {
                    int n2;
                    byArray = new byte[65536];
                    while ((n2 = ((InputStream)((Object)clazz2)).read(byArray, n, byArray.length - n)) != -1) {
                        if ((n += n2) != byArray.length) continue;
                        object = new byte[byArray.length * 2];
                        System.arraycopy(byArray, 0, object, 0, n);
                        byArray = object;
                    }
                } finally {
                    ((InputStream)((Object)clazz2)).close();
                }
            } catch (IOException iOException) {
                throw new ClassNotFoundException("I/O exception reading class " + string, iOException);
            }
            if (this.shader != null) {
                this.shader.apply(byArray);
            }
            try {
                clazz2 = this.defineClass(string, byArray, 0, n);
            } catch (LinkageError linkageError) {
                if (this.highlanders.contains(string) && (object = (Object)((Class)highlanderMap.get(string))) != null) {
                    return object;
                }
                try {
                    clazz2 = this.findLoadedClass(string);
                } catch (LinkageError linkageError2) {
                    throw linkageError;
                }
                if (clazz2 != null) break block16;
                throw linkageError;
            }
        }
        if (this.highlanders.contains(string) && (clazz = highlanderMap.putIfAbsent(string, clazz2)) != null) {
            clazz2 = clazz;
        }
        if (bl) {
            this.resolveClass(clazz2);
        }
        return clazz2;
    }

    public void addOverrideJarDir(String string) {
        File file = new File(string);
        File[] fileArray = file.listFiles();
        int n = fileArray.length;
        int n2 = 0;
        while (n2 < n) {
            File file2 = fileArray[n2];
            if (file2.getName().toLowerCase().endsWith(".jar") && file2.canRead() && file2.isFile()) {
                this.override.add(file2);
            }
            ++n2;
        }
    }

    public void addOverrideClasspathEntry(String string) {
        this.override.add(new File(string));
    }
}

