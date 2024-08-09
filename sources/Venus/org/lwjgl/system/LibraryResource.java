/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.Library;
import org.lwjgl.system.SharedLibraryLoader;

public final class LibraryResource {
    private LibraryResource() {
    }

    public static Path load(String string) {
        return LibraryResource.load(LibraryResource.class, string);
    }

    public static Path load(Class<?> clazz, String string) {
        return LibraryResource.load(clazz, string, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Path load(Class<?> clazz, String string, boolean bl) {
        String string2;
        Path path;
        block24: {
            APIUtil.apiLog("Loading library resource: " + string);
            path = Paths.get(string, new String[0]);
            if (path.isAbsolute()) {
                if (!Files.exists(path, new LinkOption[0])) {
                    if (!bl) throw new IllegalStateException("Failed to locate library resource: " + string);
                    LibraryResource.printError();
                    throw new IllegalStateException("Failed to locate library resource: " + string);
                }
                APIUtil.apiLog("\tSuccess");
                return path;
            }
            URL uRL = clazz.getClassLoader().getResource(string);
            if (uRL == null) {
                path = LibraryResource.loadFromLibraryPath(string);
                if (path != null) {
                    return path;
                }
            } else {
                boolean bl2 = Configuration.DEBUG_LOADER.get(false);
                try {
                    if (bl2) {
                        APIUtil.apiLog("\tUsing SharedLibraryLoader...");
                    }
                    try (FileChannel fileChannel = SharedLibraryLoader.load(string, string, uRL);){
                        path = LibraryResource.loadFromLibraryPath(string);
                        if (path != null) {
                            Path path2 = path;
                            return path2;
                        }
                    }
                } catch (Exception exception) {
                    if (!bl2) break block24;
                    exception.printStackTrace(APIUtil.DEBUG_STREAM);
                }
            }
        }
        if ((string2 = System.getProperty("java.library.path")) != null && (path = LibraryResource.load(string, "java.library.path", string2)) != null) {
            return path;
        }
        if (!bl) throw new IllegalStateException("Failed to locate library resource: " + string);
        LibraryResource.printError();
        throw new IllegalStateException("Failed to locate library resource: " + string);
    }

    @Nullable
    private static Path loadFromLibraryPath(String string) {
        String string2 = Configuration.LIBRARY_PATH.get();
        if (string2 == null) {
            return null;
        }
        return LibraryResource.load(string, Configuration.LIBRARY_PATH.getProperty(), string2);
    }

    @Nullable
    private static Path load(String string, String string2, String string3) {
        Path path = Library.findFile(string3, string);
        if (path == null) {
            APIUtil.apiLog(String.format("\t%s not found in %s=%s", string, string2, string3));
            return null;
        }
        APIUtil.apiLog(String.format("\tLoaded from %s: %s", string2, path));
        return path;
    }

    public static Path load(Class<?> clazz, Configuration<String> configuration, String ... stringArray) {
        return LibraryResource.load(clazz, configuration, null, stringArray);
    }

    public static Path load(Class<?> clazz, Configuration<String> configuration, @Nullable Supplier<Path> supplier, String ... stringArray) {
        if (stringArray.length == 0) {
            throw new IllegalArgumentException("No default names specified.");
        }
        String string = configuration.get();
        if (string != null) {
            return LibraryResource.load(clazz, string);
        }
        if (supplier == null && stringArray.length <= 1) {
            return LibraryResource.load(clazz, stringArray[0]);
        }
        try {
            return LibraryResource.load(clazz, stringArray[0], false);
        } catch (Throwable throwable) {
            for (int i = 1; i < stringArray.length; ++i) {
                try {
                    return LibraryResource.load(clazz, stringArray[i], supplier == null && i == stringArray.length - 1);
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

    private static void printError() {
        Library.printError("[LWJGL] Failed to load a library resource. Possible solutions:\n\ta) Add the directory that contains the resource to -Djava.library.path or -Dorg.lwjgl.librarypath.\n\tb) Add the JAR that contains the resource to the classpath.");
    }

    static {
        Library.initialize();
    }
}

