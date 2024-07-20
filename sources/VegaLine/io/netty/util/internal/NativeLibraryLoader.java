/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.NativeLibraryUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Locale;

public final class NativeLibraryLoader {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);
    private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
    private static final String OSNAME = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    private static final File WORKDIR;
    private static final boolean DELETE_NATIVE_LIB_AFTER_LOADING;

    private static File tmpdir() {
        File f;
        try {
            f = NativeLibraryLoader.toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
            if (f != null) {
                logger.debug("-Dio.netty.tmpdir: " + f);
                return f;
            }
            f = NativeLibraryLoader.toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
            if (f != null) {
                logger.debug("-Dio.netty.tmpdir: " + f + " (java.io.tmpdir)");
                return f;
            }
            if (NativeLibraryLoader.isWindows()) {
                f = NativeLibraryLoader.toDirectory(System.getenv("TEMP"));
                if (f != null) {
                    logger.debug("-Dio.netty.tmpdir: " + f + " (%TEMP%)");
                    return f;
                }
                String userprofile = System.getenv("USERPROFILE");
                if (userprofile != null) {
                    f = NativeLibraryLoader.toDirectory(userprofile + "\\AppData\\Local\\Temp");
                    if (f != null) {
                        logger.debug("-Dio.netty.tmpdir: " + f + " (%USERPROFILE%\\AppData\\Local\\Temp)");
                        return f;
                    }
                    f = NativeLibraryLoader.toDirectory(userprofile + "\\Local Settings\\Temp");
                    if (f != null) {
                        logger.debug("-Dio.netty.tmpdir: " + f + " (%USERPROFILE%\\Local Settings\\Temp)");
                        return f;
                    }
                }
            } else {
                f = NativeLibraryLoader.toDirectory(System.getenv("TMPDIR"));
                if (f != null) {
                    logger.debug("-Dio.netty.tmpdir: " + f + " ($TMPDIR)");
                    return f;
                }
            }
        } catch (Exception exception) {
            // empty catch block
        }
        f = NativeLibraryLoader.isWindows() ? new File("C:\\Windows\\Temp") : new File("/tmp");
        logger.warn("Failed to get the temporary directory; falling back to: " + f);
        return f;
    }

    private static File toDirectory(String path) {
        if (path == null) {
            return null;
        }
        File f = new File(path);
        f.mkdirs();
        if (!f.isDirectory()) {
            return null;
        }
        try {
            return f.getAbsoluteFile();
        } catch (Exception ignored) {
            return f;
        }
    }

    private static boolean isWindows() {
        return OSNAME.startsWith("windows");
    }

    private static boolean isOSX() {
        return OSNAME.startsWith("macosx") || OSNAME.startsWith("osx");
    }

    public static void loadFirstAvailable(ClassLoader loader, String ... names) {
        for (String name : names) {
            try {
                NativeLibraryLoader.load(name, loader);
                logger.debug("Successfully loaded the library: {}", (Object)name);
                return;
            } catch (Throwable t) {
                logger.debug("Unable to load the library '{}', trying next name...", (Object)name, (Object)t);
            }
        }
        throw new IllegalArgumentException("Failed to load any of the given libraries: " + Arrays.toString(names));
    }

    public static void load(String name, ClassLoader loader) {
        String libname = System.mapLibraryName(name);
        String path = NATIVE_RESOURCE_HOME + libname;
        URL url = loader.getResource(path);
        if (url == null && NativeLibraryLoader.isOSX()) {
            url = path.endsWith(".jnilib") ? loader.getResource("META-INF/native/lib" + name + ".dynlib") : loader.getResource("META-INF/native/lib" + name + ".jnilib");
        }
        if (url == null) {
            NativeLibraryLoader.loadLibrary(loader, name, false);
            return;
        }
        int index = libname.lastIndexOf(46);
        String prefix = libname.substring(0, index);
        String suffix = libname.substring(index, libname.length());
        InputStream in = null;
        FileOutputStream out = null;
        File tmpFile = null;
        try {
            int length;
            tmpFile = File.createTempFile(prefix, suffix, WORKDIR);
            in = url.openStream();
            out = new FileOutputStream(tmpFile);
            byte[] buffer = new byte[8192];
            while ((length = in.read(buffer)) > 0) {
                ((OutputStream)out).write(buffer, 0, length);
            }
            out.flush();
            NativeLibraryLoader.closeQuietly(out);
            out = null;
            NativeLibraryLoader.loadLibrary(loader, tmpFile.getPath(), true);
        } catch (Exception e) {
            try {
                throw (UnsatisfiedLinkError)new UnsatisfiedLinkError("could not load a native library: " + name).initCause(e);
            } catch (Throwable throwable) {
                NativeLibraryLoader.closeQuietly(in);
                NativeLibraryLoader.closeQuietly(out);
                if (!(tmpFile == null || DELETE_NATIVE_LIB_AFTER_LOADING && tmpFile.delete())) {
                    tmpFile.deleteOnExit();
                }
                throw throwable;
            }
        }
        NativeLibraryLoader.closeQuietly(in);
        NativeLibraryLoader.closeQuietly(out);
        if (!(tmpFile == null || DELETE_NATIVE_LIB_AFTER_LOADING && tmpFile.delete())) {
            tmpFile.deleteOnExit();
        }
    }

    private static void loadLibrary(ClassLoader loader, String name, boolean absolute) {
        try {
            Class<?> newHelper = NativeLibraryLoader.tryToLoadClass(loader, NativeLibraryUtil.class);
            NativeLibraryLoader.loadLibraryByHelper(newHelper, name, absolute);
            return;
        } catch (UnsatisfiedLinkError e) {
            logger.debug("Unable to load the library '{}', trying other loading mechanism.", (Object)name, (Object)e);
        } catch (Exception e) {
            logger.debug("Unable to load the library '{}', trying other loading mechanism.", (Object)name, (Object)e);
        }
        NativeLibraryUtil.loadLibrary(name, absolute);
    }

    private static void loadLibraryByHelper(final Class<?> helper, final String name, final boolean absolute) throws UnsatisfiedLinkError {
        Object ret = AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                try {
                    Method method = helper.getMethod("loadLibrary", String.class, Boolean.TYPE);
                    method.setAccessible(true);
                    return method.invoke(null, name, absolute);
                } catch (Exception e) {
                    return e;
                }
            }
        });
        if (ret instanceof Throwable) {
            Throwable error = (Throwable)ret;
            Throwable cause = error.getCause();
            if (cause != null) {
                if (cause instanceof UnsatisfiedLinkError) {
                    throw (UnsatisfiedLinkError)cause;
                }
                throw new UnsatisfiedLinkError(cause.getMessage());
            }
            throw new UnsatisfiedLinkError(error.getMessage());
        }
    }

    private static Class<?> tryToLoadClass(final ClassLoader loader, final Class<?> helper) throws ClassNotFoundException {
        try {
            return loader.loadClass(helper.getName());
        } catch (ClassNotFoundException e) {
            final byte[] classBinary = NativeLibraryLoader.classToByteArray(helper);
            return (Class)AccessController.doPrivileged(new PrivilegedAction<Class<?>>(){

                @Override
                public Class<?> run() {
                    try {
                        Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                        defineClass.setAccessible(true);
                        return (Class)defineClass.invoke(loader, helper.getName(), classBinary, 0, classBinary.length);
                    } catch (Exception e) {
                        throw new IllegalStateException("Define class failed!", e);
                    }
                }
            });
        }
    }

    private static byte[] classToByteArray(Class<?> clazz) throws ClassNotFoundException {
        URL classUrl;
        String fileName = clazz.getName();
        int lastDot = fileName.lastIndexOf(46);
        if (lastDot > 0) {
            fileName = fileName.substring(lastDot + 1);
        }
        if ((classUrl = clazz.getResource(fileName + ".class")) == null) {
            throw new ClassNotFoundException(clazz.getName());
        }
        byte[] buf = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        InputStream in = null;
        try {
            int r22;
            in = classUrl.openStream();
            while ((r22 = in.read(buf)) != -1) {
                out.write(buf, 0, r22);
            }
            byte[] r22 = out.toByteArray();
            return r22;
        } catch (IOException ex) {
            throw new ClassNotFoundException(clazz.getName(), ex);
        } finally {
            NativeLibraryLoader.closeQuietly(in);
            NativeLibraryLoader.closeQuietly(out);
        }
    }

    private static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private NativeLibraryLoader() {
    }

    static {
        String workdir = SystemPropertyUtil.get("io.netty.native.workdir");
        if (workdir != null) {
            File f = new File(workdir);
            f.mkdirs();
            try {
                f = f.getAbsoluteFile();
            } catch (Exception exception) {
                // empty catch block
            }
            WORKDIR = f;
            logger.debug("-Dio.netty.native.workdir: " + WORKDIR);
        } else {
            WORKDIR = NativeLibraryLoader.tmpdir();
            logger.debug("-Dio.netty.native.workdir: " + WORKDIR + " (io.netty.tmpdir)");
        }
        DELETE_NATIVE_LIB_AFTER_LOADING = SystemPropertyUtil.getBoolean("io.netty.native.deleteLibAfterLoading", true);
    }
}

