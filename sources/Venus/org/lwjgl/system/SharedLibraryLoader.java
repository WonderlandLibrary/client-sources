/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import org.lwjgl.Version;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.Platform;

final class SharedLibraryLoader {
    private static final Lock EXTRACT_PATH_LOCK = new ReentrantLock();
    @Nullable
    @GuardedBy(value="EXTRACT_PATH_LOCK")
    private static Path extractPath;

    private SharedLibraryLoader() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static FileChannel load(String string, String string2, URL uRL) {
        try {
            Path path;
            EXTRACT_PATH_LOCK.lock();
            try {
                if (extractPath != null) {
                    path = extractPath.resolve(string2);
                } else {
                    path = SharedLibraryLoader.getExtractPath(string2, uRL);
                    if (Platform.get() != Platform.WINDOWS || SharedLibraryLoader.workaroundJDK8195129(path)) {
                        extractPath = path.getParent();
                        SharedLibraryLoader.initExtractPath(extractPath);
                    }
                }
            } finally {
                EXTRACT_PATH_LOCK.unlock();
            }
            return SharedLibraryLoader.extract(path, uRL);
        } catch (Exception exception) {
            throw new RuntimeException("\tFailed to extract " + string + " library", exception);
        }
    }

    private static void initExtractPath(Path path) {
        String string = path.toAbsolutePath().toString();
        String string2 = Configuration.LIBRARY_PATH.get();
        if (string2 != null && !string2.isEmpty()) {
            string = string + File.pathSeparator + string2;
        }
        System.setProperty(Configuration.LIBRARY_PATH.getProperty(), string);
        Configuration.LIBRARY_PATH.set(string);
    }

    private static Path getExtractPath(String string, URL uRL) {
        Path path;
        String string2 = Configuration.SHARED_LIBRARY_EXTRACT_PATH.get();
        if (string2 != null) {
            return Paths.get(string2, string);
        }
        String string3 = Version.getVersion().replace(' ', '-');
        Path path2 = Paths.get(Configuration.SHARED_LIBRARY_EXTRACT_DIRECTORY.get("lwjgl" + System.getProperty("user.name")), string3, string);
        Path path3 = Paths.get(System.getProperty("java.io.tmpdir"), new String[0]);
        if (SharedLibraryLoader.canWrite(path3, path = path3.resolve(path2), uRL)) {
            return path;
        }
        path2 = Paths.get(Configuration.SHARED_LIBRARY_EXTRACT_DIRECTORY.get("lwjgl"), string3, string);
        path3 = Paths.get(System.getProperty("user.home"), new String[0]);
        if (SharedLibraryLoader.canWrite(path3, path = path3.resolve(path2), uRL)) {
            return path;
        }
        path3 = Paths.get("", new String[0]).toAbsolutePath();
        if (SharedLibraryLoader.canWrite(path3, path = path3.resolve(path2), uRL)) {
            return path;
        }
        if (Platform.get() == Platform.WINDOWS) {
            String string4 = System.getenv("SystemRoot");
            if (string4 != null && SharedLibraryLoader.canWrite(path3 = Paths.get(string4, "Temp"), path = path3.resolve(path2), uRL)) {
                return path;
            }
            string4 = System.getenv("SystemDrive");
            if (string4 != null && SharedLibraryLoader.canWrite(path3 = Paths.get(string4 + "/", new String[0]), path = path3.resolve(Paths.get("Temp", new String[0]).resolve(path2)), uRL)) {
                return path;
            }
        }
        try {
            path = Files.createTempDirectory("lwjgl", new FileAttribute[0]);
            path3 = path.getParent();
            path = path.resolve(string);
            if (SharedLibraryLoader.canWrite(path3, path, uRL)) {
                return path;
            }
        } catch (IOException iOException) {
            // empty catch block
        }
        throw new RuntimeException("Failed to find an appropriate directory to extract the native library");
    }

    private static FileChannel extract(Path path, URL uRL) throws IOException {
        Throwable throwable;
        InputStream inputStream;
        if (Files.exists(path, new LinkOption[0])) {
            inputStream = uRL.openStream();
            throwable = null;
            try {
                InputStream inputStream2 = Files.newInputStream(path, new OpenOption[0]);
                Throwable throwable2 = null;
                try {
                    if (SharedLibraryLoader.crc(inputStream) == SharedLibraryLoader.crc(inputStream2)) {
                        if (Configuration.DEBUG_LOADER.get(false).booleanValue()) {
                            APIUtil.apiLog(String.format("\tFound at: %s", path));
                        }
                        FileChannel fileChannel = SharedLibraryLoader.lock(path);
                        return fileChannel;
                    }
                } catch (Throwable throwable3) {
                    throwable2 = throwable3;
                    throw throwable3;
                } finally {
                    if (inputStream2 != null) {
                        SharedLibraryLoader.$closeResource(throwable2, inputStream2);
                    }
                }
            } catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            } finally {
                if (inputStream != null) {
                    SharedLibraryLoader.$closeResource(throwable, inputStream);
                }
            }
        }
        APIUtil.apiLog(String.format("    Extracting: %s\n", uRL.getPath()));
        if (extractPath == null) {
            APIUtil.apiLog(String.format("            to: %s\n", path));
        }
        Files.createDirectories(path.getParent(), new FileAttribute[0]);
        inputStream = uRL.openStream();
        throwable = null;
        try {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Throwable throwable5) {
            throwable = throwable5;
            throw throwable5;
        } finally {
            if (inputStream != null) {
                SharedLibraryLoader.$closeResource(throwable, inputStream);
            }
        }
        return SharedLibraryLoader.lock(path);
    }

    private static FileChannel lock(Path path) {
        try {
            FileChannel fileChannel = FileChannel.open(path, new OpenOption[0]);
            if (fileChannel.tryLock(0L, Long.MAX_VALUE, false) == null) {
                if (Configuration.DEBUG_LOADER.get(false).booleanValue()) {
                    APIUtil.apiLog("\tFile is locked by another process, waiting...");
                }
                fileChannel.lock(0L, Long.MAX_VALUE, false);
            }
            return fileChannel;
        } catch (Exception exception) {
            throw new RuntimeException("Failed to lock file.", exception);
        }
    }

    private static long crc(InputStream inputStream) throws IOException {
        int n;
        CRC32 cRC32 = new CRC32();
        byte[] byArray = new byte[8192];
        while ((n = inputStream.read(byArray)) != -1) {
            cRC32.update(byArray, 0, n);
        }
        return cRC32.getValue();
    }

    private static boolean canWrite(Path path, Path path2, URL uRL) {
        Path path3;
        if (Files.exists(path2, new LinkOption[0])) {
            if (!Files.isWritable(path2)) {
                return true;
            }
            path3 = path2.getParent().resolve(".lwjgl.test");
        } else {
            try {
                Files.createDirectories(path2.getParent(), new FileAttribute[0]);
            } catch (IOException iOException) {
                return true;
            }
            path3 = path2;
        }
        try {
            Files.write(path3, new byte[0], new OpenOption[0]);
            Files.delete(path3);
            if (SharedLibraryLoader.workaroundJDK8195129(path2)) {
                FileChannel fileChannel = SharedLibraryLoader.extract(path2, uRL);
                Throwable throwable = null;
                try {
                    System.load(path2.toAbsolutePath().toString());
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    if (fileChannel != null) {
                        SharedLibraryLoader.$closeResource(throwable, fileChannel);
                    }
                }
            }
            return true;
        } catch (Throwable throwable) {
            if (path2 == path3) {
                SharedLibraryLoader.canWriteCleanup(path, path2);
            }
            return true;
        }
    }

    private static void canWriteCleanup(Path path, Path path2) {
        try {
            Files.deleteIfExists(path2);
            Path path3 = path2.getParent();
            while (!Files.isSameFile(path3, path)) {
                block9: {
                    Stream<Path> stream = Files.list(path3);
                    Throwable throwable = null;
                    try {
                        if (!stream.findAny().isPresent()) break block9;
                        break;
                    } catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    } finally {
                        if (stream != null) {
                            SharedLibraryLoader.$closeResource(throwable, stream);
                        }
                    }
                }
                Files.delete(path3);
                path3 = path3.getParent();
            }
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    private static boolean workaroundJDK8195129(Path path) {
        return Platform.get() == Platform.WINDOWS && path.toString().endsWith(".dll");
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
}

