/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.util.regex.Pattern;
import org.lwjgl.system.Pointer;

public enum Platform {
    LINUX("Linux"){
        private final Pattern SO = Pattern.compile("(?:^|/)lib\\w+[.]so(?:[.]\\d+)*$");

        @Override
        String mapLibraryName(String string) {
            if (this.SO.matcher(string).find()) {
                return string;
            }
            return System.mapLibraryName(string);
        }
    }
    ,
    MACOSX("macOS"){
        private final Pattern DYLIB = Pattern.compile("(?:^|/)lib\\w+(?:[.]\\d+)*[.]dylib$");

        @Override
        String mapLibraryName(String string) {
            if (this.DYLIB.matcher(string).find()) {
                return string;
            }
            return System.mapLibraryName(string);
        }
    }
    ,
    WINDOWS("Windows"){

        @Override
        String mapLibraryName(String string) {
            if (string.endsWith(".dll")) {
                return string;
            }
            return System.mapLibraryName(string);
        }
    };

    private static final Platform current;
    private final String name;

    private Platform(String string2) {
        this.name = string2;
    }

    public String getName() {
        return this.name;
    }

    abstract String mapLibraryName(String var1);

    public static Platform get() {
        return current;
    }

    public static String mapLibraryNameBundled(String string) {
        return Pointer.BITS64 ? string : string + "32";
    }

    Platform(String string2, 1 var4_4) {
        this(string2);
    }

    static {
        String string = System.getProperty("os.name");
        if (string.startsWith("Windows")) {
            current = WINDOWS;
        } else if (string.startsWith("Linux") || string.startsWith("FreeBSD") || string.startsWith("SunOS") || string.startsWith("Unix")) {
            current = LINUX;
        } else if (string.startsWith("Mac OS X") || string.startsWith("Darwin")) {
            current = MACOSX;
        } else {
            throw new LinkageError("Unknown platform: " + string);
        }
    }
}

