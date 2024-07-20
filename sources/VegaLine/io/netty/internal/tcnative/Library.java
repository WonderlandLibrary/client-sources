/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.internal.tcnative;

import io.netty.internal.tcnative.SSL;
import java.io.File;

public final class Library {
    private static final String[] NAMES = new String[]{"netty-tcnative", "libnetty-tcnative", "netty-tcnative-1", "libnetty-tcnative-1"};
    private static Library _instance = null;

    private Library() throws Exception {
        boolean loaded = false;
        String path = System.getProperty("java.library.path");
        String[] paths = path.split(File.pathSeparator);
        StringBuilder err = new StringBuilder();
        for (int i = 0; i < NAMES.length; ++i) {
            try {
                System.loadLibrary(NAMES[i]);
                loaded = true;
            } catch (ThreadDeath t) {
                throw t;
            } catch (VirtualMachineError t) {
                throw t;
            } catch (Throwable t) {
                String name = System.mapLibraryName(NAMES[i]);
                for (int j = 0; j < paths.length; ++j) {
                    File fd = new File(paths[j], name);
                    if (!fd.exists()) continue;
                    throw new RuntimeException(t);
                }
                if (i > 0) {
                    err.append(", ");
                }
                err.append(t.getMessage());
            }
            if (loaded) break;
        }
        if (!loaded) {
            throw new UnsatisfiedLinkError(err.toString());
        }
    }

    private Library(String libraryName) {
        if (!"provided".equals(libraryName)) {
            System.loadLibrary(libraryName);
        }
    }

    private static native boolean initialize0();

    private static native boolean has(int var0);

    private static native int version(int var0);

    private static native String aprVersionString();

    public static boolean initialize() throws Exception {
        return Library.initialize("provided", null);
    }

    public static boolean initialize(String libraryName, String engine) throws Exception {
        if (_instance == null) {
            _instance = libraryName == null ? new Library() : new Library(libraryName);
            int aprMajor = Library.version(17);
            if (aprMajor < 1) {
                throw new UnsatisfiedLinkError("Unsupported APR Version (" + Library.aprVersionString() + ")");
            }
            boolean aprHasThreads = Library.has(2);
            if (!aprHasThreads) {
                throw new UnsatisfiedLinkError("Missing APR_HAS_THREADS");
            }
        }
        return Library.initialize0() && SSL.initialize(engine) == 0;
    }
}

