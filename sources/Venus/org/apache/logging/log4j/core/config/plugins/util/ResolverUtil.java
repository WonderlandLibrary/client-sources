/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.osgi.framework.FrameworkUtil
 *  org.osgi.framework.wiring.BundleWiring
 */
package org.apache.logging.log4j.core.config.plugins.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.status.StatusLogger;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;

public class ResolverUtil {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String VFSZIP = "vfszip";
    private static final String VFS = "vfs";
    private static final String BUNDLE_RESOURCE = "bundleresource";
    private final Set<Class<?>> classMatches = new HashSet();
    private final Set<URI> resourceMatches = new HashSet<URI>();
    private ClassLoader classloader;

    public Set<Class<?>> getClasses() {
        return this.classMatches;
    }

    public Set<URI> getResources() {
        return this.resourceMatches;
    }

    public ClassLoader getClassLoader() {
        return this.classloader != null ? this.classloader : (this.classloader = Loader.getClassLoader(ResolverUtil.class, null));
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classloader = classLoader;
    }

    public void find(Test test, String ... stringArray) {
        if (stringArray == null) {
            return;
        }
        for (String string : stringArray) {
            this.findInPackage(test, string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void findInPackage(Test test, String string) {
        Enumeration<URL> enumeration;
        string = string.replace('.', '/');
        ClassLoader classLoader = this.getClassLoader();
        try {
            enumeration = classLoader.getResources(string);
        } catch (IOException iOException) {
            LOGGER.warn("Could not read package: " + string, (Throwable)iOException);
            return;
        }
        while (enumeration.hasMoreElements()) {
            try {
                Serializable serializable;
                Object object;
                URL uRL = enumeration.nextElement();
                String string2 = this.extractPath(uRL);
                LOGGER.info("Scanning for classes in [" + string2 + "] matching criteria: " + test);
                if (VFSZIP.equals(uRL.getProtocol())) {
                    object = string2.substring(0, string2.length() - string.length() - 2);
                    serializable = new URL(uRL.getProtocol(), uRL.getHost(), (String)object);
                    JarInputStream jarInputStream = new JarInputStream(((URL)serializable).openStream());
                    try {
                        this.loadImplementationsInJar(test, string, (String)object, jarInputStream);
                        continue;
                    } finally {
                        this.close(jarInputStream, serializable);
                        continue;
                    }
                }
                if (VFS.equals(uRL.getProtocol())) {
                    object = string2.substring(1, string2.length() - string.length() - 2);
                    serializable = new File((String)object);
                    if (((File)serializable).isDirectory()) {
                        this.loadImplementationsInDirectory(test, string, new File((File)serializable, string));
                        continue;
                    }
                    this.loadImplementationsInJar(test, string, (File)serializable);
                    continue;
                }
                if (BUNDLE_RESOURCE.equals(uRL.getProtocol())) {
                    this.loadImplementationsInBundle(test, string);
                    continue;
                }
                object = new File(string2);
                if (((File)object).isDirectory()) {
                    this.loadImplementationsInDirectory(test, string, (File)object);
                    continue;
                }
                this.loadImplementationsInJar(test, string, (File)object);
            } catch (IOException | URISyntaxException exception) {
                LOGGER.warn("Could not read entries", (Throwable)exception);
            }
        }
    }

    String extractPath(URL uRL) throws UnsupportedEncodingException, URISyntaxException {
        int n;
        String string = uRL.getPath();
        if (string.startsWith("jar:")) {
            string = string.substring(4);
        }
        if (string.startsWith("file:")) {
            string = string.substring(5);
        }
        if ((n = string.indexOf(33)) > 0) {
            string = string.substring(0, n);
        }
        String string2 = uRL.getProtocol();
        List<String> list = Arrays.asList(VFS, VFSZIP, BUNDLE_RESOURCE);
        if (list.contains(string2)) {
            return string;
        }
        String string3 = new URI(string).getPath();
        if (new File(string3).exists()) {
            return string3;
        }
        return URLDecoder.decode(string, StandardCharsets.UTF_8.name());
    }

    private void loadImplementationsInBundle(Test test, String string) {
        BundleWiring bundleWiring = (BundleWiring)FrameworkUtil.getBundle(ResolverUtil.class).adapt(BundleWiring.class);
        Collection collection = bundleWiring.listResources(string, "*.class", 1);
        for (String string2 : collection) {
            this.addIfMatching(test, string2);
        }
    }

    private void loadImplementationsInDirectory(Test test, String string, File file) {
        File[] fileArray = file.listFiles();
        if (fileArray == null) {
            return;
        }
        for (File file2 : fileArray) {
            String string2;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string).append('/').append(file2.getName());
            String string3 = string2 = string == null ? file2.getName() : stringBuilder.toString();
            if (file2.isDirectory()) {
                this.loadImplementationsInDirectory(test, string2, file2);
                continue;
            }
            if (!this.isTestApplicable(test, file2.getName())) continue;
            this.addIfMatching(test, string2);
        }
    }

    private boolean isTestApplicable(Test test, String string) {
        return test.doesMatchResource() || string.endsWith(".class") && test.doesMatchClass();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private void loadImplementationsInJar(Test test, String string, File file) {
        JarInputStream jarInputStream = null;
        try {
            jarInputStream = new JarInputStream(new FileInputStream(file));
            this.loadImplementationsInJar(test, string, file.getPath(), jarInputStream);
            this.close(jarInputStream, file);
        } catch (FileNotFoundException fileNotFoundException) {
            LOGGER.error("Could not search jar file '" + file + "' for classes matching criteria: " + test + " file not found", (Throwable)fileNotFoundException);
            this.close(jarInputStream, file);
        } catch (IOException iOException) {
            LOGGER.error("Could not search jar file '" + file + "' for classes matching criteria: " + test + " due to an IOException", (Throwable)iOException);
            this.close(jarInputStream, file);
            {
                catch (Throwable throwable) {
                    this.close(jarInputStream, file);
                    throw throwable;
                }
            }
        }
    }

    private void close(JarInputStream jarInputStream, Object object) {
        if (jarInputStream != null) {
            try {
                jarInputStream.close();
            } catch (IOException iOException) {
                LOGGER.error("Error closing JAR file stream for {}", object, (Object)iOException);
            }
        }
    }

    private void loadImplementationsInJar(Test test, String string, String string2, JarInputStream jarInputStream) {
        try {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                String string3 = jarEntry.getName();
                if (jarEntry.isDirectory() || !string3.startsWith(string) || !this.isTestApplicable(test, string3)) continue;
                this.addIfMatching(test, string3);
            }
        } catch (IOException iOException) {
            LOGGER.error("Could not search jar file '" + string2 + "' for classes matching criteria: " + test + " due to an IOException", (Throwable)iOException);
        }
    }

    protected void addIfMatching(Test test, String string) {
        try {
            Object object;
            ClassLoader classLoader = this.getClassLoader();
            if (test.doesMatchClass()) {
                Class<?> clazz;
                object = string.substring(0, string.indexOf(46)).replace('/', '.');
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Checking to see if class " + (String)object + " matches criteria [" + test + ']');
                }
                if (test.matches(clazz = classLoader.loadClass((String)object))) {
                    this.classMatches.add(clazz);
                }
            }
            if (test.doesMatchResource()) {
                object = classLoader.getResource(string);
                if (object == null) {
                    object = classLoader.getResource(string.substring(1));
                }
                if (object != null && test.matches(((URL)object).toURI())) {
                    this.resourceMatches.add(((URL)object).toURI());
                }
            }
        } catch (Throwable throwable) {
            LOGGER.warn("Could not examine class '" + string, throwable);
        }
    }

    public static interface Test {
        public boolean matches(Class<?> var1);

        public boolean matches(URI var1);

        public boolean doesMatchClass();

        public boolean doesMatchResource();
    }
}

