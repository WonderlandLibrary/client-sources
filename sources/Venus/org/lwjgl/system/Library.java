/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.lwjgl.Version;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.Platform;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.SharedLibraryLoader;

public final class Library {
    public static final String JNI_LIBRARY_NAME = Configuration.LIBRARY_NAME.get(System.getProperty("os.arch").contains("64") ? "lwjgl" : "lwjgl32");
    static final String JAVA_LIBRARY_PATH = "java.library.path";
    private static final Pattern PATH_SEPARATOR = Pattern.compile(File.pathSeparator);
    private static final Pattern NATIVES_JAR = Pattern.compile("/[\\w-]+?-natives-\\w+.jar!/");

    private Library() {
    }

    public static void initialize() {
    }

    public static void loadSystem(String string) throws UnsatisfiedLinkError {
        Library.loadSystem(System::load, System::loadLibrary, Library.class, string);
    }

    public static void loadSystem(Consumer<String> consumer, Consumer<String> consumer2, Class<?> clazz, String string) throws UnsatisfiedLinkError {
        FileChannel fileChannel;
        String string2;
        block18: {
            APIUtil.apiLog("Loading library (system): " + string);
            if (Paths.get(string, new String[0]).isAbsolute()) {
                consumer.accept(string);
                APIUtil.apiLog("\tSuccess");
                return;
            }
            string2 = Platform.get().mapLibraryName(string);
            URL uRL = clazz.getClassLoader().getResource(string2);
            if (uRL == null) {
                if (Library.loadSystemFromLibraryPath(consumer, clazz, string2)) {
                    return;
                }
            } else {
                boolean bl = Configuration.DEBUG_LOADER.get(false);
                try {
                    if (bl) {
                        APIUtil.apiLog("\tUsing SharedLibraryLoader...");
                    }
                    fileChannel = SharedLibraryLoader.load(string, string2, uRL);
                    Throwable throwable = null;
                    try {
                        if (Library.loadSystemFromLibraryPath(consumer, clazz, string2)) {
                            return;
                        }
                    } catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    } finally {
                        if (fileChannel != null) {
                            Library.$closeResource(throwable, fileChannel);
                        }
                    }
                } catch (Exception exception) {
                    if (!bl) break block18;
                    exception.printStackTrace(APIUtil.DEBUG_STREAM);
                }
            }
        }
        try {
            consumer2.accept(string);
            String string3 = System.getProperty(JAVA_LIBRARY_PATH);
            FileChannel fileChannel2 = fileChannel = string3 == null ? null : Library.findFile(string3, string2);
            if (fileChannel != null) {
                APIUtil.apiLog(String.format("\tLoaded from %s: %s", JAVA_LIBRARY_PATH, fileChannel));
                Library.checkHash(clazz, (Path)((Object)fileChannel));
            } else {
                APIUtil.apiLog("\tLoaded from a ClassLoader provided path.");
            }
            return;
        } catch (Throwable throwable) {
            APIUtil.apiLog(String.format("\t%s not found in %s", string2, JAVA_LIBRARY_PATH));
            Library.printError(true);
            throw new UnsatisfiedLinkError("Failed to locate library: " + string2);
        }
    }

    private static boolean loadSystemFromLibraryPath(Consumer<String> consumer, Class<?> clazz, String string) {
        String string2 = Configuration.LIBRARY_PATH.get();
        return string2 != null && Library.loadSystem(consumer, clazz, string, Configuration.LIBRARY_PATH.getProperty(), string2);
    }

    private static boolean loadSystem(Consumer<String> consumer, Class<?> clazz, String string, String string2, String string3) {
        Path path = Library.findFile(string3, string);
        if (path == null) {
            APIUtil.apiLog(String.format("\t%s not found in %s=%s", string, string2, string3));
            return true;
        }
        consumer.accept(path.toAbsolutePath().toString());
        APIUtil.apiLog(String.format("\tLoaded from %s: %s", string2, path));
        Library.checkHash(clazz, path);
        return false;
    }

    public static SharedLibrary loadNative(String string) {
        return Library.loadNative(Library.class, string);
    }

    public static SharedLibrary loadNative(Class<?> clazz, String string) {
        return Library.loadNative(clazz, string, false);
    }

    public static SharedLibrary loadNative(Class<?> clazz, String string, boolean bl) {
        return Library.loadNative(clazz, string, bl, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static SharedLibrary loadNative(Class<?> clazz, String string, boolean bl, boolean bl2) {
        String string2;
        Object object;
        SharedLibrary sharedLibrary;
        String string3;
        block21: {
            APIUtil.apiLog("Loading library: " + string);
            if (Paths.get(string, new String[0]).isAbsolute()) {
                SharedLibrary sharedLibrary2 = APIUtil.apiCreateLibrary(string);
                APIUtil.apiLog("\tSuccess");
                return sharedLibrary2;
            }
            string3 = Platform.get().mapLibraryName(string);
            URL uRL = clazz.getClassLoader().getResource(string3);
            if (uRL == null) {
                sharedLibrary = Library.loadNativeFromLibraryPath(clazz, string3);
                if (sharedLibrary != null) {
                    return sharedLibrary;
                }
            } else {
                boolean bl3 = Configuration.DEBUG_LOADER.get(false);
                try {
                    if (bl3) {
                        APIUtil.apiLog("\tUsing SharedLibraryLoader...");
                    }
                    object = SharedLibraryLoader.load(string, string3, uRL);
                    Throwable throwable = null;
                    try {
                        sharedLibrary = Library.loadNativeFromLibraryPath(clazz, string3);
                        if (sharedLibrary != null) {
                            SharedLibrary sharedLibrary3 = sharedLibrary;
                            return sharedLibrary3;
                        }
                    } catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    } finally {
                        if (object != null) {
                            Library.$closeResource(throwable, (AutoCloseable)object);
                        }
                    }
                } catch (Exception exception) {
                    if (!bl3) break block21;
                    exception.printStackTrace(APIUtil.DEBUG_STREAM);
                }
            }
        }
        if (!bl && (sharedLibrary = Library.loadNativeFromSystem(string3)) != null) {
            return sharedLibrary;
        }
        if (Configuration.EMULATE_SYSTEM_LOADLIBRARY.get(false).booleanValue()) {
            try {
                Method method = ClassLoader.class.getDeclaredMethod("findLibrary", String.class);
                method.setAccessible(false);
                object = (String)method.invoke(clazz.getClassLoader(), string);
                if (object != null) {
                    sharedLibrary = APIUtil.apiCreateLibrary((String)object);
                    APIUtil.apiLog(String.format("\tLoaded from ClassLoader provided path: %s", object));
                    return sharedLibrary;
                }
            } catch (Exception exception) {
                // empty catch block
            }
        }
        if ((string2 = System.getProperty(JAVA_LIBRARY_PATH)) != null && (sharedLibrary = Library.loadNative(clazz, string3, JAVA_LIBRARY_PATH, string2)) != null) {
            return sharedLibrary;
        }
        if (bl && (sharedLibrary = Library.loadNativeFromSystem(string3)) != null) {
            return sharedLibrary;
        }
        if (!bl2) throw new UnsatisfiedLinkError("Failed to locate library: " + string3);
        Library.printError(bl);
        throw new UnsatisfiedLinkError("Failed to locate library: " + string3);
    }

    @Nullable
    private static SharedLibrary loadNativeFromSystem(String string) {
        SharedLibrary sharedLibrary;
        try {
            sharedLibrary = APIUtil.apiCreateLibrary(string);
            String string2 = sharedLibrary.getPath();
            APIUtil.apiLog(string2 == null ? "\tLoaded from system paths" : "\tLoaded from system paths: " + string2);
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            sharedLibrary = null;
            APIUtil.apiLog(String.format("\t%s not found in system paths", string));
        }
        return sharedLibrary;
    }

    @Nullable
    private static SharedLibrary loadNativeFromLibraryPath(Class<?> clazz, String string) {
        String string2 = Configuration.LIBRARY_PATH.get();
        if (string2 == null) {
            return null;
        }
        return Library.loadNative(clazz, string, Configuration.LIBRARY_PATH.getProperty(), string2);
    }

    @Nullable
    private static SharedLibrary loadNative(Class<?> clazz, String string, String string2, String string3) {
        Path path = Library.findFile(string3, string);
        if (path == null) {
            APIUtil.apiLog(String.format("\t%s not found in %s=%s", string, string2, string3));
            return null;
        }
        SharedLibrary sharedLibrary = APIUtil.apiCreateLibrary(path.toAbsolutePath().toString());
        APIUtil.apiLog(String.format("\tLoaded from %s: %s", string2, path));
        Library.checkHash(clazz, path);
        return sharedLibrary;
    }

    public static SharedLibrary loadNative(Class<?> clazz, @Nullable Configuration<String> configuration, String ... stringArray) {
        return Library.loadNative(clazz, configuration, null, stringArray);
    }

    public static SharedLibrary loadNative(Class<?> clazz, @Nullable Configuration<String> configuration, @Nullable Supplier<SharedLibrary> supplier, String ... stringArray) {
        String string;
        if (stringArray.length == 0) {
            throw new IllegalArgumentException("No default names specified.");
        }
        if (configuration != null && (string = configuration.get()) != null) {
            return Library.loadNative(clazz, string);
        }
        if (supplier == null && stringArray.length <= 1) {
            return Library.loadNative(clazz, stringArray[0]);
        }
        try {
            return Library.loadNative(clazz, stringArray[0], false, false);
        } catch (Throwable throwable) {
            for (int i = 1; i < stringArray.length; ++i) {
                try {
                    return Library.loadNative(clazz, stringArray[i], false, supplier == null && i == stringArray.length - 1);
                } catch (Throwable throwable2) {
                    continue;
                }
            }
            if (supplier != null) {
                return supplier.get();
            }
            throw throwable;
        }
    }

    @Nullable
    static Path findFile(String string, String string2) {
        for (String string3 : PATH_SEPARATOR.split(string)) {
            Path path = Paths.get(string3, string2);
            if (!Files.isReadable(path)) continue;
            return path;
        }
        return null;
    }

    private static void printError(boolean bl) {
        Library.printError("[LWJGL] Failed to load a library. Possible solutions:\n" + (bl ? "\ta) Add the directory that contains the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.\n\tb) Add the JAR that contains the shared library to the classpath." : "\ta) Install the library or the driver that provides the library.\n\tb) Ensure that the library is accessible from the system library paths."));
    }

    static void printError(String string) {
        APIUtil.DEBUG_STREAM.println(string);
        if (!Checks.DEBUG) {
            APIUtil.DEBUG_STREAM.println("[LWJGL] Enable debug mode with -Dorg.lwjgl.util.Debug=true for better diagnostics.");
            if (!Configuration.DEBUG_LOADER.get(false).booleanValue()) {
                APIUtil.DEBUG_STREAM.println("[LWJGL] Enable the SharedLibraryLoader debug mode with -Dorg.lwjgl.util.DebugLoader=true for better diagnostics.");
            }
        }
    }

    private static void checkHash(Class<?> clazz, Path path) {
        block7: {
            if (!Checks.CHECKS) {
                return;
            }
            try {
                byte[] byArray;
                Object object;
                Object object2 = null;
                Object object3 = null;
                Enumeration<URL> enumeration = clazz.getClassLoader().getResources(path.getFileName() + ".sha1");
                while (enumeration.hasMoreElements()) {
                    object = enumeration.nextElement();
                    if (NATIVES_JAR.matcher(((URL)object).toExternalForm()).find()) {
                        object3 = object;
                        continue;
                    }
                    object2 = object;
                }
                if (object2 == null) {
                    return;
                }
                object = Library.getSHA1(object2);
                byte[] byArray2 = byArray = Checks.DEBUG || object3 == null ? Library.getSHA1(path) : Library.getSHA1(object3);
                if (!Arrays.equals((byte[])object, byArray)) {
                    APIUtil.DEBUG_STREAM.println("[LWJGL] [ERROR] Incompatible Java and native library versions detected.\nPossible reasons:\n\ta) -Djava.library.path is set to a folder containing shared libraries of an older LWJGL version.\n\tb) The classpath contains jar files of an older LWJGL version.\nPossible solutions:\n\ta) Make sure to not set -Djava.library.path (it is not needed for developing with LWJGL 3) or make\n\t   sure the folder it points to contains the shared libraries of the correct LWJGL version.\n\tb) Check the classpath and make sure to only have jar files of the same LWJGL version in it.");
                }
            } catch (Throwable throwable) {
                if (!Checks.DEBUG) break block7;
                APIUtil.apiLog("Failed to verify native library.");
                throwable.printStackTrace();
            }
        }
    }

    private static byte[] getSHA1(URL uRL) throws IOException {
        byte[] byArray = new byte[20];
        InputStream inputStream = uRL.openStream();
        Throwable throwable = null;
        try {
            for (int i = 0; i < 20; ++i) {
                byArray[i] = (byte)(Character.digit(inputStream.read(), 16) << 4 | Character.digit(inputStream.read(), 16));
            }
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (inputStream != null) {
                Library.$closeResource(throwable, inputStream);
            }
        }
        return byArray;
    }

    private static byte[] getSHA1(Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        Throwable throwable = null;
        try {
            int n;
            byte[] byArray = new byte[8192];
            while ((n = inputStream.read(byArray)) != -1) {
                messageDigest.update(byArray, 0, n);
            }
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (inputStream != null) {
                Library.$closeResource(throwable, inputStream);
            }
        }
        return messageDigest.digest();
    }

    private static void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        if (Checks.DEBUG) {
            APIUtil.apiLog("Version: " + Version.getVersion());
            APIUtil.apiLog("\t OS: " + System.getProperty("os.name") + " v" + System.getProperty("os.version"));
            APIUtil.apiLog("\tJRE: " + System.getProperty("java.version") + " " + System.getProperty("os.arch"));
            APIUtil.apiLog("\tJVM: " + System.getProperty("java.vm.name") + " v" + System.getProperty("java.vm.version") + " by " + System.getProperty("java.vm.vendor"));
        }
        Library.loadSystem(JNI_LIBRARY_NAME);
    }
}

