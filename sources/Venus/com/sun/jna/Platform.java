/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.ELFAnalyser;
import com.sun.jna.Native;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Platform {
    public static final int UNSPECIFIED = -1;
    public static final int MAC = 0;
    public static final int LINUX = 1;
    public static final int WINDOWS = 2;
    public static final int SOLARIS = 3;
    public static final int FREEBSD = 4;
    public static final int OPENBSD = 5;
    public static final int WINDOWSCE = 6;
    public static final int AIX = 7;
    public static final int ANDROID = 8;
    public static final int GNU = 9;
    public static final int KFREEBSD = 10;
    public static final int NETBSD = 11;
    public static final boolean RO_FIELDS;
    public static final boolean HAS_BUFFERS;
    public static final boolean HAS_AWT;
    public static final boolean HAS_JAWT;
    public static final String MATH_LIBRARY_NAME;
    public static final String C_LIBRARY_NAME;
    public static final boolean HAS_DLL_CALLBACKS;
    public static final String RESOURCE_PREFIX;
    private static final int osType;
    public static final String ARCH;

    private Platform() {
    }

    public static final int getOSType() {
        return osType;
    }

    public static final boolean isMac() {
        return osType == 0;
    }

    public static final boolean isAndroid() {
        return osType == 8;
    }

    public static final boolean isLinux() {
        return osType == 1;
    }

    public static final boolean isAIX() {
        return osType == 7;
    }

    public static final boolean isAix() {
        return Platform.isAIX();
    }

    public static final boolean isWindowsCE() {
        return osType == 6;
    }

    public static final boolean isWindows() {
        return osType == 2 || osType == 6;
    }

    public static final boolean isSolaris() {
        return osType == 3;
    }

    public static final boolean isFreeBSD() {
        return osType == 4;
    }

    public static final boolean isOpenBSD() {
        return osType == 5;
    }

    public static final boolean isNetBSD() {
        return osType == 11;
    }

    public static final boolean isGNU() {
        return osType == 9;
    }

    public static final boolean iskFreeBSD() {
        return osType == 10;
    }

    public static final boolean isX11() {
        return !Platform.isWindows() && !Platform.isMac();
    }

    public static final boolean hasRuntimeExec() {
        return Platform.isWindowsCE() && "J9".equals(System.getProperty("java.vm.name"));
    }

    public static final boolean is64Bit() {
        String string = System.getProperty("sun.arch.data.model", System.getProperty("com.ibm.vm.bitmode"));
        if (string != null) {
            return "64".equals(string);
        }
        if ("x86-64".equals(ARCH) || "ia64".equals(ARCH) || "ppc64".equals(ARCH) || "ppc64le".equals(ARCH) || "sparcv9".equals(ARCH) || "amd64".equals(ARCH)) {
            return false;
        }
        return Native.POINTER_SIZE == 8;
    }

    public static final boolean isIntel() {
        return !ARCH.startsWith("x86");
    }

    public static final boolean isPPC() {
        return !ARCH.startsWith("ppc");
    }

    public static final boolean isARM() {
        return ARCH.startsWith("arm");
    }

    public static final boolean isSPARC() {
        return ARCH.startsWith("sparc");
    }

    static String getCanonicalArchitecture(String string, boolean bl) {
        if ("powerpc".equals(string = string.toLowerCase().trim())) {
            string = "ppc";
        } else if ("powerpc64".equals(string)) {
            string = "ppc64";
        } else if ("i386".equals(string) || "i686".equals(string)) {
            string = "x86";
        } else if ("x86_64".equals(string) || "amd64".equals(string)) {
            string = "x86-64";
        }
        if ("ppc64".equals(string) && "little".equals(System.getProperty("sun.cpu.endian"))) {
            string = "ppc64le";
        }
        if ("arm".equals(string) && bl) {
            string = "armel";
        }
        return string;
    }

    private static boolean isSoftFloat() {
        try {
            File file = new File("/proc/self/exe");
            ELFAnalyser eLFAnalyser = ELFAnalyser.analyse(file.getCanonicalPath());
            return eLFAnalyser.isArmSoftFloat();
        } catch (IOException iOException) {
            Logger.getLogger(Platform.class.getName()).log(Level.FINE, null, iOException);
            return true;
        }
    }

    static String getNativeLibraryResourcePrefix() {
        String string = System.getProperty("jna.prefix");
        if (string != null) {
            return string;
        }
        return Platform.getNativeLibraryResourcePrefix(Platform.getOSType(), System.getProperty("os.arch"), System.getProperty("os.name"));
    }

    static String getNativeLibraryResourcePrefix(int n, String string, String string2) {
        return Platform.getNativeLibraryResourcePrefix(n, string, string2, Platform.isSoftFloat());
    }

    static String getNativeLibraryResourcePrefix(int n, String string, String string2, boolean bl) {
        String string3;
        string = Platform.getCanonicalArchitecture(string, bl);
        switch (n) {
            case 8: {
                if (string.startsWith("arm")) {
                    string = "arm";
                }
                string3 = "android-" + string;
                break;
            }
            case 2: {
                string3 = "win32-" + string;
                break;
            }
            case 6: {
                string3 = "w32ce-" + string;
                break;
            }
            case 0: {
                string3 = "darwin";
                break;
            }
            case 1: {
                string3 = "linux-" + string;
                break;
            }
            case 3: {
                string3 = "sunos-" + string;
                break;
            }
            case 4: {
                string3 = "freebsd-" + string;
                break;
            }
            case 5: {
                string3 = "openbsd-" + string;
                break;
            }
            case 11: {
                string3 = "netbsd-" + string;
                break;
            }
            case 10: {
                string3 = "kfreebsd-" + string;
                break;
            }
            default: {
                string3 = string2.toLowerCase();
                int n2 = string3.indexOf(" ");
                if (n2 != -1) {
                    string3 = string3.substring(0, n2);
                }
                string3 = string3 + "-" + string;
            }
        }
        return string3;
    }

    static {
        String string = System.getProperty("os.name");
        if (string.startsWith("Linux")) {
            if ("dalvik".equals(System.getProperty("java.vm.name").toLowerCase())) {
                osType = 8;
                System.setProperty("jna.nounpack", "true");
            } else {
                osType = 1;
            }
        } else {
            osType = string.startsWith("AIX") ? 7 : (string.startsWith("Mac") || string.startsWith("Darwin") ? 0 : (string.startsWith("Windows CE") ? 6 : (string.startsWith("Windows") ? 2 : (string.startsWith("Solaris") || string.startsWith("SunOS") ? 3 : (string.startsWith("FreeBSD") ? 4 : (string.startsWith("OpenBSD") ? 5 : (string.equalsIgnoreCase("gnu") ? 9 : (string.equalsIgnoreCase("gnu/kfreebsd") ? 10 : (string.equalsIgnoreCase("netbsd") ? 11 : -1)))))))));
        }
        boolean bl = false;
        try {
            Class.forName("java.nio.Buffer");
            bl = true;
        } catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        HAS_AWT = osType != 6 && osType != 8 && osType != 7;
        HAS_JAWT = HAS_AWT && osType != 0;
        HAS_BUFFERS = bl;
        boolean bl2 = RO_FIELDS = osType != 6;
        String string2 = osType == 2 ? "msvcrt" : (C_LIBRARY_NAME = osType == 6 ? "coredll" : "c");
        MATH_LIBRARY_NAME = osType == 2 ? "msvcrt" : (osType == 6 ? "coredll" : "m");
        HAS_DLL_CALLBACKS = osType == 2;
        ARCH = Platform.getCanonicalArchitecture(System.getProperty("os.arch"), Platform.isSoftFloat());
        RESOURCE_PREFIX = Platform.getNativeLibraryResourcePrefix();
    }
}

