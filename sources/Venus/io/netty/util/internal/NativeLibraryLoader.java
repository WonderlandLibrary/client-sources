/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.NativeLibraryUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.PosixFilePermission;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public final class NativeLibraryLoader {
    private static final InternalLogger logger;
    private static final String NATIVE_RESOURCE_HOME = "META-INF/native/";
    private static final File WORKDIR;
    private static final boolean DELETE_NATIVE_LIB_AFTER_LOADING;
    static final boolean $assertionsDisabled;

    public static void loadFirstAvailable(ClassLoader classLoader, String ... stringArray) {
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>();
        for (String string : stringArray) {
            try {
                NativeLibraryLoader.load(string, classLoader);
                return;
            } catch (Throwable throwable) {
                arrayList.add(throwable);
                logger.debug("Unable to load the library '{}', trying next name...", (Object)string, (Object)throwable);
            }
        }
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Failed to load any of the given libraries: " + Arrays.toString(stringArray));
        ThrowableUtil.addSuppressedAndClear(illegalArgumentException, arrayList);
        throw illegalArgumentException;
    }

    private static String calculatePackagePrefix() {
        String string;
        String string2 = NativeLibraryLoader.class.getName();
        if (!string2.endsWith(string = "io!netty!util!internal!NativeLibraryLoader".replace('!', '.'))) {
            throw new UnsatisfiedLinkError(String.format("Could not find prefix added to %s to get %s. When shading, only adding a package prefix is supported", string, string2));
        }
        return string2.substring(0, string2.length() - string.length());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void load(String string, ClassLoader classLoader) {
        String string2 = NativeLibraryLoader.calculatePackagePrefix().replace('.', '_') + string;
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>();
        try {
            NativeLibraryLoader.loadLibrary(classLoader, string2, false);
            return;
        } catch (Throwable throwable) {
            arrayList.add(throwable);
            logger.debug("{} cannot be loaded from java.libary.path, now trying export to -Dio.netty.native.workdir: {}", string2, WORKDIR, throwable);
            String string3 = System.mapLibraryName(string2);
            String string4 = NATIVE_RESOURCE_HOME + string3;
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            File file = null;
            URL uRL = classLoader == null ? ClassLoader.getSystemResource(string4) : classLoader.getResource(string4);
            try {
                int n;
                if (uRL == null) {
                    if (!PlatformDependent.isOsx()) {
                        FileNotFoundException fileNotFoundException = new FileNotFoundException(string4);
                        ThrowableUtil.addSuppressedAndClear(fileNotFoundException, arrayList);
                        throw fileNotFoundException;
                    }
                    String string5 = string4.endsWith(".jnilib") ? "META-INF/native/lib" + string2 + ".dynlib" : "META-INF/native/lib" + string2 + ".jnilib";
                    uRL = classLoader == null ? ClassLoader.getSystemResource(string5) : classLoader.getResource(string5);
                    if (uRL == null) {
                        FileNotFoundException fileNotFoundException = new FileNotFoundException(string5);
                        ThrowableUtil.addSuppressedAndClear(fileNotFoundException, arrayList);
                        throw fileNotFoundException;
                    }
                }
                int n2 = string3.lastIndexOf(46);
                String string6 = string3.substring(0, n2);
                String string7 = string3.substring(n2, string3.length());
                file = File.createTempFile(string6, string7, WORKDIR);
                inputStream = uRL.openStream();
                fileOutputStream = new FileOutputStream(file);
                byte[] byArray = new byte[8192];
                while ((n = inputStream.read(byArray)) > 0) {
                    ((OutputStream)fileOutputStream).write(byArray, 0, n);
                }
                fileOutputStream.flush();
                NativeLibraryLoader.closeQuietly(fileOutputStream);
                fileOutputStream = null;
                NativeLibraryLoader.loadLibrary(classLoader, file.getPath(), true);
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                try {
                    try {
                        if (file != null && file.isFile() && file.canRead() && !NoexecVolumeDetector.access$000(file)) {
                            logger.info("{} exists but cannot be executed even when execute permissions set; check volume for \"noexec\" flag; use -Dio.netty.native.workdir=[path] to set native working directory separately.", (Object)file.getPath());
                        }
                    } catch (Throwable throwable2) {
                        arrayList.add(throwable2);
                        logger.debug("Error checking if {} is on a file store mounted with noexec", (Object)file, (Object)throwable2);
                    }
                    ThrowableUtil.addSuppressedAndClear(unsatisfiedLinkError, arrayList);
                    throw unsatisfiedLinkError;
                    catch (Exception exception) {
                        UnsatisfiedLinkError unsatisfiedLinkError2 = new UnsatisfiedLinkError("could not load a native library: " + string2);
                        unsatisfiedLinkError2.initCause(exception);
                        ThrowableUtil.addSuppressedAndClear(unsatisfiedLinkError2, arrayList);
                        throw unsatisfiedLinkError2;
                    }
                } catch (Throwable throwable3) {
                    NativeLibraryLoader.closeQuietly(inputStream);
                    NativeLibraryLoader.closeQuietly(fileOutputStream);
                    if (file == null) throw throwable3;
                    if (DELETE_NATIVE_LIB_AFTER_LOADING) {
                        if (file.delete()) throw throwable3;
                    }
                    file.deleteOnExit();
                    throw throwable3;
                }
            }
            NativeLibraryLoader.closeQuietly(inputStream);
            NativeLibraryLoader.closeQuietly(fileOutputStream);
            if (file == null) return;
            if (DELETE_NATIVE_LIB_AFTER_LOADING) {
                if (file.delete()) return;
            }
            file.deleteOnExit();
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void loadLibrary(ClassLoader classLoader, String string, boolean bl) {
        Throwable throwable = null;
        try {
            Class<?> clazz = NativeLibraryLoader.tryToLoadClass(classLoader, NativeLibraryUtil.class);
            NativeLibraryLoader.loadLibraryByHelper(clazz, string, bl);
            logger.debug("Successfully loaded the library {}", (Object)string);
            return;
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            try {
                block5: {
                    throwable = unsatisfiedLinkError;
                    logger.debug("Unable to load the library '{}', trying other loading mechanism.", (Object)string, (Object)unsatisfiedLinkError);
                    break block5;
                    catch (Exception exception) {
                        throwable = exception;
                        logger.debug("Unable to load the library '{}', trying other loading mechanism.", (Object)string, (Object)exception);
                    }
                }
                NativeLibraryUtil.loadLibrary(string, bl);
                logger.debug("Successfully loaded the library {}", (Object)string);
                return;
            } catch (UnsatisfiedLinkError unsatisfiedLinkError2) {
                if (throwable == null) throw unsatisfiedLinkError2;
                ThrowableUtil.addSuppressed((Throwable)unsatisfiedLinkError2, throwable);
                throw unsatisfiedLinkError2;
            }
        }
    }

    private static void loadLibraryByHelper(Class<?> clazz, String string, boolean bl) throws UnsatisfiedLinkError {
        Object object = AccessController.doPrivileged(new PrivilegedAction<Object>(clazz, string, bl){
            final Class val$helper;
            final String val$name;
            final boolean val$absolute;
            {
                this.val$helper = clazz;
                this.val$name = string;
                this.val$absolute = bl;
            }

            @Override
            public Object run() {
                try {
                    Method method = this.val$helper.getMethod("loadLibrary", String.class, Boolean.TYPE);
                    method.setAccessible(false);
                    return method.invoke(null, this.val$name, this.val$absolute);
                } catch (Exception exception) {
                    return exception;
                }
            }
        });
        if (object instanceof Throwable) {
            Throwable throwable = (Throwable)object;
            if (!$assertionsDisabled && throwable instanceof UnsatisfiedLinkError) {
                throw new AssertionError((Object)(throwable + " should be a wrapper throwable"));
            }
            Throwable throwable2 = throwable.getCause();
            if (throwable2 instanceof UnsatisfiedLinkError) {
                throw (UnsatisfiedLinkError)throwable2;
            }
            UnsatisfiedLinkError unsatisfiedLinkError = new UnsatisfiedLinkError(throwable.getMessage());
            unsatisfiedLinkError.initCause(throwable);
            throw unsatisfiedLinkError;
        }
    }

    private static Class<?> tryToLoadClass(ClassLoader classLoader, Class<?> clazz) throws ClassNotFoundException {
        try {
            return Class.forName(clazz.getName(), false, classLoader);
        } catch (ClassNotFoundException classNotFoundException) {
            if (classLoader == null) {
                throw classNotFoundException;
            }
            try {
                byte[] byArray = NativeLibraryLoader.classToByteArray(clazz);
                return (Class)AccessController.doPrivileged(new PrivilegedAction<Class<?>>(classLoader, clazz, byArray){
                    final ClassLoader val$loader;
                    final Class val$helper;
                    final byte[] val$classBinary;
                    {
                        this.val$loader = classLoader;
                        this.val$helper = clazz;
                        this.val$classBinary = byArray;
                    }

                    @Override
                    public Class<?> run() {
                        try {
                            Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                            method.setAccessible(false);
                            return (Class)method.invoke(this.val$loader, this.val$helper.getName(), this.val$classBinary, 0, this.val$classBinary.length);
                        } catch (Exception exception) {
                            throw new IllegalStateException("Define class failed!", exception);
                        }
                    }

                    @Override
                    public Object run() {
                        return this.run();
                    }
                });
            } catch (ClassNotFoundException classNotFoundException2) {
                ThrowableUtil.addSuppressed((Throwable)classNotFoundException2, classNotFoundException);
                throw classNotFoundException2;
            } catch (RuntimeException runtimeException) {
                ThrowableUtil.addSuppressed((Throwable)runtimeException, classNotFoundException);
                throw runtimeException;
            } catch (Error error2) {
                ThrowableUtil.addSuppressed((Throwable)error2, classNotFoundException);
                throw error2;
            }
        }
    }

    private static byte[] classToByteArray(Class<?> clazz) throws ClassNotFoundException {
        URL uRL;
        String string = clazz.getName();
        int n = string.lastIndexOf(46);
        if (n > 0) {
            string = string.substring(n + 1);
        }
        if ((uRL = clazz.getResource(string + ".class")) == null) {
            throw new ClassNotFoundException(clazz.getName());
        }
        byte[] byArray = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        InputStream inputStream = null;
        try {
            int n2;
            inputStream = uRL.openStream();
            while ((n2 = inputStream.read(byArray)) != -1) {
                byteArrayOutputStream.write(byArray, 0, n2);
            }
            byte[] byArray2 = byteArrayOutputStream.toByteArray();
            return byArray2;
        } catch (IOException iOException) {
            throw new ClassNotFoundException(clazz.getName(), iOException);
        } finally {
            NativeLibraryLoader.closeQuietly(inputStream);
            NativeLibraryLoader.closeQuietly(byteArrayOutputStream);
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private NativeLibraryLoader() {
    }

    static {
        $assertionsDisabled = !NativeLibraryLoader.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);
        String string = SystemPropertyUtil.get("io.netty.native.workdir");
        if (string != null) {
            File file = new File(string);
            file.mkdirs();
            try {
                file = file.getAbsoluteFile();
            } catch (Exception exception) {
                // empty catch block
            }
            WORKDIR = file;
            logger.debug("-Dio.netty.native.workdir: " + WORKDIR);
        } else {
            WORKDIR = PlatformDependent.tmpdir();
            logger.debug("-Dio.netty.native.workdir: " + WORKDIR + " (io.netty.tmpdir)");
        }
        DELETE_NATIVE_LIB_AFTER_LOADING = SystemPropertyUtil.getBoolean("io.netty.native.deleteLibAfterLoading", true);
    }

    private static final class NoexecVolumeDetector {
        private static boolean canExecuteExecutable(File file) throws IOException {
            EnumSet<PosixFilePermission> enumSet;
            if (PlatformDependent.javaVersion() < 7) {
                return false;
            }
            if (file.canExecute()) {
                return false;
            }
            Set<PosixFilePermission> set = Files.getPosixFilePermissions(file.toPath(), new LinkOption[0]);
            if (set.containsAll(enumSet = EnumSet.of(PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_EXECUTE))) {
                return true;
            }
            EnumSet<PosixFilePermission> enumSet2 = EnumSet.copyOf(set);
            enumSet2.addAll(enumSet);
            Files.setPosixFilePermissions(file.toPath(), enumSet2);
            return file.canExecute();
        }

        private NoexecVolumeDetector() {
        }

        static boolean access$000(File file) throws IOException {
            return NoexecVolumeDetector.canExecuteExecutable(file);
        }
    }
}

