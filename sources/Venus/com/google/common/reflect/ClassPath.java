/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.google.common.reflect.Reflection;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@Beta
public final class ClassPath {
    private static final Logger logger = Logger.getLogger(ClassPath.class.getName());
    private static final Predicate<ClassInfo> IS_TOP_LEVEL = new Predicate<ClassInfo>(){

        @Override
        public boolean apply(ClassInfo classInfo) {
            return ClassInfo.access$000(classInfo).indexOf(36) == -1;
        }

        @Override
        public boolean apply(Object object) {
            return this.apply((ClassInfo)object);
        }
    };
    private static final Splitter CLASS_PATH_ATTRIBUTE_SEPARATOR = Splitter.on(" ").omitEmptyStrings();
    private static final String CLASS_FILE_NAME_EXTENSION = ".class";
    private final ImmutableSet<ResourceInfo> resources;

    private ClassPath(ImmutableSet<ResourceInfo> immutableSet) {
        this.resources = immutableSet;
    }

    public static ClassPath from(ClassLoader classLoader) throws IOException {
        DefaultScanner defaultScanner = new DefaultScanner();
        defaultScanner.scan(classLoader);
        return new ClassPath(defaultScanner.getResources());
    }

    public ImmutableSet<ResourceInfo> getResources() {
        return this.resources;
    }

    public ImmutableSet<ClassInfo> getAllClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses() {
        return FluentIterable.from(this.resources).filter(ClassInfo.class).filter(IS_TOP_LEVEL).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses(String string) {
        Preconditions.checkNotNull(string);
        ImmutableSet.Builder builder = ImmutableSet.builder();
        for (ClassInfo classInfo : this.getTopLevelClasses()) {
            if (!classInfo.getPackageName().equals(string)) continue;
            builder.add(classInfo);
        }
        return builder.build();
    }

    public ImmutableSet<ClassInfo> getTopLevelClassesRecursive(String string) {
        Preconditions.checkNotNull(string);
        String string2 = string + '.';
        ImmutableSet.Builder builder = ImmutableSet.builder();
        for (ClassInfo classInfo : this.getTopLevelClasses()) {
            if (!classInfo.getName().startsWith(string2)) continue;
            builder.add(classInfo);
        }
        return builder.build();
    }

    @VisibleForTesting
    static String getClassName(String string) {
        int n = string.length() - 6;
        return string.substring(0, n).replace('/', '.');
    }

    static Logger access$100() {
        return logger;
    }

    static Splitter access$200() {
        return CLASS_PATH_ATTRIBUTE_SEPARATOR;
    }

    @VisibleForTesting
    static final class DefaultScanner
    extends Scanner {
        private final SetMultimap<ClassLoader, String> resources = MultimapBuilder.hashKeys().linkedHashSetValues().build();

        DefaultScanner() {
        }

        ImmutableSet<ResourceInfo> getResources() {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            for (Map.Entry entry : this.resources.entries()) {
                builder.add(ResourceInfo.of((String)entry.getValue(), (ClassLoader)entry.getKey()));
            }
            return builder.build();
        }

        @Override
        protected void scanJarFile(ClassLoader classLoader, JarFile jarFile) {
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                if (jarEntry.isDirectory() || jarEntry.getName().equals("META-INF/MANIFEST.MF")) continue;
                this.resources.get((Object)classLoader).add(jarEntry.getName());
            }
        }

        @Override
        protected void scanDirectory(ClassLoader classLoader, File file) throws IOException {
            this.scanDirectory(file, classLoader, "");
        }

        private void scanDirectory(File file, ClassLoader classLoader, String string) throws IOException {
            File[] fileArray = file.listFiles();
            if (fileArray == null) {
                ClassPath.access$100().warning("Cannot read directory " + file);
                return;
            }
            for (File file2 : fileArray) {
                String string2 = file2.getName();
                if (file2.isDirectory()) {
                    this.scanDirectory(file2, classLoader, string + string2 + "/");
                    continue;
                }
                String string3 = string + string2;
                if (string3.equals("META-INF/MANIFEST.MF")) continue;
                this.resources.get((Object)classLoader).add(string3);
            }
        }
    }

    static abstract class Scanner {
        private final Set<File> scannedUris = Sets.newHashSet();

        Scanner() {
        }

        public final void scan(ClassLoader classLoader) throws IOException {
            for (Map.Entry entry : Scanner.getClassPathEntries(classLoader).entrySet()) {
                this.scan((File)entry.getKey(), (ClassLoader)entry.getValue());
            }
        }

        protected abstract void scanDirectory(ClassLoader var1, File var2) throws IOException;

        protected abstract void scanJarFile(ClassLoader var1, JarFile var2) throws IOException;

        @VisibleForTesting
        final void scan(File file, ClassLoader classLoader) throws IOException {
            if (this.scannedUris.add(file.getCanonicalFile())) {
                this.scanFrom(file, classLoader);
            }
        }

        private void scanFrom(File file, ClassLoader classLoader) throws IOException {
            try {
                if (!file.exists()) {
                    return;
                }
            } catch (SecurityException securityException) {
                ClassPath.access$100().warning("Cannot access " + file + ": " + securityException);
                return;
            }
            if (file.isDirectory()) {
                this.scanDirectory(classLoader, file);
            } else {
                this.scanJar(file, classLoader);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void scanJar(File file, ClassLoader classLoader) throws IOException {
            JarFile jarFile;
            try {
                jarFile = new JarFile(file);
            } catch (IOException iOException) {
                return;
            }
            try {
                for (File file2 : Scanner.getClassPathFromManifest(file, jarFile.getManifest())) {
                    this.scan(file2, classLoader);
                }
                this.scanJarFile(classLoader, jarFile);
            } finally {
                try {
                    jarFile.close();
                } catch (IOException iOException) {}
            }
        }

        @VisibleForTesting
        static ImmutableSet<File> getClassPathFromManifest(File file, @Nullable Manifest manifest) {
            if (manifest == null) {
                return ImmutableSet.of();
            }
            ImmutableSet.Builder builder = ImmutableSet.builder();
            String string = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString());
            if (string != null) {
                for (String string2 : ClassPath.access$200().split(string)) {
                    URL uRL;
                    try {
                        uRL = Scanner.getClassPathEntry(file, string2);
                    } catch (MalformedURLException malformedURLException) {
                        ClassPath.access$100().warning("Invalid Class-Path entry: " + string2);
                        continue;
                    }
                    if (!uRL.getProtocol().equals("file")) continue;
                    builder.add(new File(uRL.getFile()));
                }
            }
            return builder.build();
        }

        @VisibleForTesting
        static ImmutableMap<File, ClassLoader> getClassPathEntries(ClassLoader classLoader) {
            LinkedHashMap<File, ClassLoader> linkedHashMap = Maps.newLinkedHashMap();
            ClassLoader classLoader2 = classLoader.getParent();
            if (classLoader2 != null) {
                linkedHashMap.putAll(Scanner.getClassPathEntries(classLoader2));
            }
            if (classLoader instanceof URLClassLoader) {
                URLClassLoader uRLClassLoader = (URLClassLoader)classLoader;
                for (URL uRL : uRLClassLoader.getURLs()) {
                    File file;
                    if (!uRL.getProtocol().equals("file") || linkedHashMap.containsKey(file = new File(uRL.getFile()))) continue;
                    linkedHashMap.put(file, classLoader);
                }
            }
            return ImmutableMap.copyOf(linkedHashMap);
        }

        @VisibleForTesting
        static URL getClassPathEntry(File file, String string) throws MalformedURLException {
            return new URL(file.toURI().toURL(), string);
        }
    }

    @Beta
    public static final class ClassInfo
    extends ResourceInfo {
        private final String className;

        ClassInfo(String string, ClassLoader classLoader) {
            super(string, classLoader);
            this.className = ClassPath.getClassName(string);
        }

        public String getPackageName() {
            return Reflection.getPackageName(this.className);
        }

        public String getSimpleName() {
            int n = this.className.lastIndexOf(36);
            if (n != -1) {
                String string = this.className.substring(n + 1);
                return CharMatcher.digit().trimLeadingFrom(string);
            }
            String string = this.getPackageName();
            if (string.isEmpty()) {
                return this.className;
            }
            return this.className.substring(string.length() + 1);
        }

        public String getName() {
            return this.className;
        }

        public Class<?> load() {
            try {
                return this.loader.loadClass(this.className);
            } catch (ClassNotFoundException classNotFoundException) {
                throw new IllegalStateException(classNotFoundException);
            }
        }

        @Override
        public String toString() {
            return this.className;
        }

        static String access$000(ClassInfo classInfo) {
            return classInfo.className;
        }
    }

    @Beta
    public static class ResourceInfo {
        private final String resourceName;
        final ClassLoader loader;

        static ResourceInfo of(String string, ClassLoader classLoader) {
            if (string.endsWith(ClassPath.CLASS_FILE_NAME_EXTENSION)) {
                return new ClassInfo(string, classLoader);
            }
            return new ResourceInfo(string, classLoader);
        }

        ResourceInfo(String string, ClassLoader classLoader) {
            this.resourceName = Preconditions.checkNotNull(string);
            this.loader = Preconditions.checkNotNull(classLoader);
        }

        public final URL url() {
            URL uRL = this.loader.getResource(this.resourceName);
            if (uRL == null) {
                throw new NoSuchElementException(this.resourceName);
            }
            return uRL;
        }

        public final ByteSource asByteSource() {
            return Resources.asByteSource(this.url());
        }

        public final CharSource asCharSource(Charset charset) {
            return Resources.asCharSource(this.url(), charset);
        }

        public final String getResourceName() {
            return this.resourceName;
        }

        public int hashCode() {
            return this.resourceName.hashCode();
        }

        public boolean equals(Object object) {
            if (object instanceof ResourceInfo) {
                ResourceInfo resourceInfo = (ResourceInfo)object;
                return this.resourceName.equals(resourceInfo.resourceName) && this.loader == resourceInfo.loader;
            }
            return true;
        }

        public String toString() {
            return this.resourceName;
        }
    }
}

