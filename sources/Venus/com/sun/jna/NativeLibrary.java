/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.FunctionMapper;
import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class NativeLibrary {
    private long handle;
    private final String libraryName;
    private final String libraryPath;
    private final Map<String, Function> functions = new HashMap<String, Function>();
    final int callFlags;
    private String encoding;
    final Map<String, ?> options;
    private static final Map<String, Reference<NativeLibrary>> libraries = new HashMap<String, Reference<NativeLibrary>>();
    private static final Map<String, List<String>> searchPaths = Collections.synchronizedMap(new HashMap());
    private static final List<String> librarySearchPath = new ArrayList<String>();
    private static final int DEFAULT_OPEN_OPTIONS = -1;

    private static String functionKey(String string, int n, String string2) {
        return string + "|" + n + "|" + string2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private NativeLibrary(String string, String string2, long l, Map<String, ?> map) {
        int n;
        this.libraryName = this.getLibraryName(string);
        this.libraryPath = string2;
        this.handle = l;
        Object obj = map.get("calling-convention");
        this.callFlags = n = obj instanceof Number ? ((Number)obj).intValue() : 0;
        this.options = map;
        this.encoding = (String)map.get("string-encoding");
        if (this.encoding == null) {
            this.encoding = Native.getDefaultStringEncoding();
        }
        if (Platform.isWindows() && "kernel32".equals(this.libraryName.toLowerCase())) {
            Map<String, Function> map2 = this.functions;
            synchronized (map2) {
                Function function = new Function(this, this, "GetLastError", 63, this.encoding){
                    final NativeLibrary this$0;
                    {
                        this.this$0 = nativeLibrary;
                        super(nativeLibrary2, string, n, string2);
                    }

                    @Override
                    Object invoke(Object[] objectArray, Class<?> clazz, boolean bl, int n) {
                        return Native.getLastError();
                    }

                    @Override
                    Object invoke(Method method, Class<?>[] classArray, Class<?> clazz, Object[] objectArray, Map<String, ?> map) {
                        return Native.getLastError();
                    }
                };
                this.functions.put(NativeLibrary.functionKey("GetLastError", this.callFlags, this.encoding), function);
            }
        }
    }

    private static int openFlags(Map<String, ?> map) {
        Object obj = map.get("open-flags");
        if (obj instanceof Number) {
            return ((Number)obj).intValue();
        }
        return 1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static NativeLibrary loadLibrary(String string, Map<String, ?> map) {
        long l;
        Object object;
        block51: {
            List<String> list;
            if (Native.DEBUG_LOAD) {
                System.out.println("Looking for library '" + string + "'");
            }
            boolean bl = new File(string).isAbsolute();
            ArrayList<String> arrayList = new ArrayList<String>();
            int n = NativeLibrary.openFlags(map);
            String string2 = Native.getWebStartLibraryPath(string);
            if (string2 != null) {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Adding web start path " + string2);
                }
                arrayList.add(string2);
            }
            if ((list = searchPaths.get(string)) != null) {
                object = list;
                synchronized (object) {
                    arrayList.addAll(0, list);
                }
            }
            if (Native.DEBUG_LOAD) {
                System.out.println("Adding paths from jna.library.path: " + System.getProperty("jna.library.path"));
            }
            arrayList.addAll(NativeLibrary.initPaths("jna.library.path"));
            object = NativeLibrary.findLibraryPath(string, arrayList);
            l = 0L;
            try {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Trying " + (String)object);
                }
                l = Native.open((String)object, n);
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Adding system paths: " + librarySearchPath);
                }
                arrayList.addAll(librarySearchPath);
            }
            try {
                if (l == 0L) {
                    object = NativeLibrary.findLibraryPath(string, arrayList);
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Trying " + (String)object);
                    }
                    if ((l = Native.open((String)object, n)) == 0L) {
                        throw new UnsatisfiedLinkError("Failed to load library '" + string + "'");
                    }
                }
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                UnsatisfiedLinkError unsatisfiedLinkError2;
                if (Platform.isAndroid()) {
                    try {
                        if (Native.DEBUG_LOAD) {
                            System.out.println("Preload (via System.loadLibrary) " + string);
                        }
                        System.loadLibrary(string);
                        l = Native.open((String)object, n);
                    } catch (UnsatisfiedLinkError unsatisfiedLinkError3) {
                        unsatisfiedLinkError2 = unsatisfiedLinkError3;
                    }
                } else if (Platform.isLinux() || Platform.isFreeBSD()) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Looking for version variants");
                    }
                    if ((object = NativeLibrary.matchLibrary(string, arrayList)) != null) {
                        if (Native.DEBUG_LOAD) {
                            System.out.println("Trying " + (String)object);
                        }
                        try {
                            l = Native.open((String)object, n);
                        } catch (UnsatisfiedLinkError unsatisfiedLinkError4) {
                            unsatisfiedLinkError2 = unsatisfiedLinkError4;
                        }
                    }
                } else if (Platform.isMac() && !string.endsWith(".dylib")) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Looking for matching frameworks");
                    }
                    if ((object = NativeLibrary.matchFramework(string)) != null) {
                        try {
                            if (Native.DEBUG_LOAD) {
                                System.out.println("Trying " + (String)object);
                            }
                            l = Native.open((String)object, n);
                        } catch (UnsatisfiedLinkError unsatisfiedLinkError5) {
                            unsatisfiedLinkError2 = unsatisfiedLinkError5;
                        }
                    }
                } else if (Platform.isWindows() && !bl) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Looking for lib- prefix");
                    }
                    if ((object = NativeLibrary.findLibraryPath("lib" + string, arrayList)) != null) {
                        if (Native.DEBUG_LOAD) {
                            System.out.println("Trying " + (String)object);
                        }
                        try {
                            l = Native.open((String)object, n);
                        } catch (UnsatisfiedLinkError unsatisfiedLinkError6) {
                            unsatisfiedLinkError2 = unsatisfiedLinkError6;
                        }
                    }
                }
                if (l == 0L) {
                    try {
                        File file = Native.extractFromResourcePath(string, (ClassLoader)map.get("classloader"));
                        try {
                            l = Native.open(file.getAbsolutePath(), n);
                            object = file.getAbsolutePath();
                        } finally {
                            if (Native.isUnpacked(file)) {
                                Native.deleteLibrary(file);
                            }
                        }
                    } catch (IOException iOException) {
                        unsatisfiedLinkError2 = new UnsatisfiedLinkError(iOException.getMessage());
                    }
                }
                if (l != 0L) break block51;
                throw new UnsatisfiedLinkError("Unable to load library '" + string + "': " + unsatisfiedLinkError2.getMessage());
            }
        }
        if (Native.DEBUG_LOAD) {
            System.out.println("Found library '" + string + "' at " + (String)object);
        }
        return new NativeLibrary(string, (String)object, l, map);
    }

    static String matchFramework(String string) {
        File file = new File(string);
        if (file.isAbsolute()) {
            if (string.indexOf(".framework") != -1 && file.exists()) {
                return file.getAbsolutePath();
            }
            if ((file = new File(new File(file.getParentFile(), file.getName() + ".framework"), file.getName())).exists()) {
                return file.getAbsolutePath();
            }
        } else {
            String[] stringArray = new String[]{System.getProperty("user.home"), "", "/System"};
            String string2 = string.indexOf(".framework") == -1 ? string + ".framework/" + string : string;
            for (int i = 0; i < stringArray.length; ++i) {
                String string3 = stringArray[i] + "/Library/Frameworks/" + string2;
                if (!new File(string3).exists()) continue;
                return string3;
            }
        }
        return null;
    }

    private String getLibraryName(String string) {
        String string2;
        int n;
        String string3 = string;
        String string4 = "---";
        String string5 = NativeLibrary.mapSharedLibraryName("---");
        int n2 = string5.indexOf("---");
        if (n2 > 0 && string3.startsWith(string5.substring(0, n2))) {
            string3 = string3.substring(n2);
        }
        if ((n = string3.indexOf(string2 = string5.substring(n2 + 3))) != -1) {
            string3 = string3.substring(0, n);
        }
        return string3;
    }

    public static final NativeLibrary getInstance(String string) {
        return NativeLibrary.getInstance(string, Collections.emptyMap());
    }

    public static final NativeLibrary getInstance(String string, ClassLoader classLoader) {
        return NativeLibrary.getInstance(string, Collections.singletonMap("classloader", classLoader));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final NativeLibrary getInstance(String string, Map<String, ?> map) {
        HashMap hashMap = new HashMap(map);
        if (hashMap.get("calling-convention") == null) {
            hashMap.put("calling-convention", 0);
        }
        if ((Platform.isLinux() || Platform.isFreeBSD() || Platform.isAIX()) && Platform.C_LIBRARY_NAME.equals(string)) {
            string = null;
        }
        Map<String, Reference<NativeLibrary>> map2 = libraries;
        synchronized (map2) {
            NativeLibrary nativeLibrary;
            Reference<NativeLibrary> reference = libraries.get(string + hashMap);
            NativeLibrary nativeLibrary2 = nativeLibrary = reference != null ? reference.get() : null;
            if (nativeLibrary == null) {
                nativeLibrary = string == null ? new NativeLibrary("<process>", null, Native.open(null, NativeLibrary.openFlags(hashMap)), hashMap) : NativeLibrary.loadLibrary(string, hashMap);
                reference = new WeakReference<NativeLibrary>(nativeLibrary);
                libraries.put(nativeLibrary.getName() + hashMap, reference);
                File file = nativeLibrary.getFile();
                if (file != null) {
                    libraries.put(file.getAbsolutePath() + hashMap, reference);
                    libraries.put(file.getName() + hashMap, reference);
                }
            }
            return nativeLibrary;
        }
    }

    public static final synchronized NativeLibrary getProcess() {
        return NativeLibrary.getInstance(null);
    }

    public static final synchronized NativeLibrary getProcess(Map<String, ?> map) {
        return NativeLibrary.getInstance(null, map);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final void addSearchPath(String string, String string2) {
        Map<String, List<String>> map = searchPaths;
        synchronized (map) {
            List<String> list = searchPaths.get(string);
            if (list == null) {
                list = Collections.synchronizedList(new ArrayList());
                searchPaths.put(string, list);
            }
            list.add(string2);
        }
    }

    public Function getFunction(String string) {
        return this.getFunction(string, this.callFlags);
    }

    Function getFunction(String string, Method method) {
        String string2;
        FunctionMapper functionMapper = (FunctionMapper)this.options.get("function-mapper");
        if (functionMapper != null) {
            string = functionMapper.getFunctionName(this, method);
        }
        if (string.startsWith(string2 = System.getProperty("jna.profiler.prefix", "$$YJP$$"))) {
            string = string.substring(string2.length());
        }
        int n = this.callFlags;
        Class<?>[] classArray = method.getExceptionTypes();
        for (int i = 0; i < classArray.length; ++i) {
            if (!LastErrorException.class.isAssignableFrom(classArray[i])) continue;
            n |= 0x40;
        }
        return this.getFunction(string, n);
    }

    public Function getFunction(String string, int n) {
        return this.getFunction(string, n, this.encoding);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Function getFunction(String string, int n, String string2) {
        if (string == null) {
            throw new NullPointerException("Function name may not be null");
        }
        Map<String, Function> map = this.functions;
        synchronized (map) {
            String string3 = NativeLibrary.functionKey(string, n, string2);
            Function function = this.functions.get(string3);
            if (function == null) {
                function = new Function(this, string, n, string2);
                this.functions.put(string3, function);
            }
            return function;
        }
    }

    public Map<String, ?> getOptions() {
        return this.options;
    }

    public Pointer getGlobalVariableAddress(String string) {
        try {
            return new Pointer(this.getSymbolAddress(string));
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            throw new UnsatisfiedLinkError("Error looking up '" + string + "': " + unsatisfiedLinkError.getMessage());
        }
    }

    long getSymbolAddress(String string) {
        if (this.handle == 0L) {
            throw new UnsatisfiedLinkError("Library has been unloaded");
        }
        return Native.findSymbol(this.handle, string);
    }

    public String toString() {
        return "Native Library <" + this.libraryPath + "@" + this.handle + ">";
    }

    public String getName() {
        return this.libraryName;
    }

    public File getFile() {
        if (this.libraryPath == null) {
            return null;
        }
        return new File(this.libraryPath);
    }

    protected void finalize() {
        this.dispose();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void disposeAll() {
        LinkedHashSet<Reference<NativeLibrary>> linkedHashSet;
        Map<String, Reference<NativeLibrary>> map = libraries;
        synchronized (map) {
            linkedHashSet = new LinkedHashSet<Reference<NativeLibrary>>(libraries.values());
        }
        for (Reference reference : linkedHashSet) {
            NativeLibrary nativeLibrary = (NativeLibrary)reference.get();
            if (nativeLibrary == null) continue;
            nativeLibrary.dispose();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dispose() {
        HashSet hashSet = new HashSet();
        Object object = libraries;
        synchronized (object) {
            for (Map.Entry<String, Reference<NativeLibrary>> entry : libraries.entrySet()) {
                Reference<NativeLibrary> reference = entry.getValue();
                if (reference.get() != this) continue;
                hashSet.add(entry.getKey());
            }
            for (Map.Entry<String, Reference<NativeLibrary>> entry : hashSet) {
                libraries.remove(entry);
            }
        }
        object = this;
        synchronized (object) {
            if (this.handle != 0L) {
                Native.close(this.handle);
                this.handle = 0L;
            }
        }
    }

    private static List<String> initPaths(String string) {
        String string2 = System.getProperty(string, "");
        if ("".equals(string2)) {
            return Collections.emptyList();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string2, File.pathSeparator);
        ArrayList<String> arrayList = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            String string3 = stringTokenizer.nextToken();
            if ("".equals(string3)) continue;
            arrayList.add(string3);
        }
        return arrayList;
    }

    private static String findLibraryPath(String string, List<String> list) {
        if (new File(string).isAbsolute()) {
            return string;
        }
        String string2 = NativeLibrary.mapSharedLibraryName(string);
        for (String string3 : list) {
            File file = new File(string3, string2);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            if (!Platform.isMac() || !string2.endsWith(".dylib") || !(file = new File(string3, string2.substring(0, string2.lastIndexOf(".dylib")) + ".jnilib")).exists()) continue;
            return file.getAbsolutePath();
        }
        return string2;
    }

    static String mapSharedLibraryName(String string) {
        if (Platform.isMac()) {
            if (string.startsWith("lib") && (string.endsWith(".dylib") || string.endsWith(".jnilib"))) {
                return string;
            }
            String string2 = System.mapLibraryName(string);
            if (string2.endsWith(".jnilib")) {
                return string2.substring(0, string2.lastIndexOf(".jnilib")) + ".dylib";
            }
            return string2;
        }
        if (Platform.isLinux() || Platform.isFreeBSD() ? NativeLibrary.isVersionedName(string) || string.endsWith(".so") : (Platform.isAIX() ? string.startsWith("lib") : Platform.isWindows() && (string.endsWith(".drv") || string.endsWith(".dll")))) {
            return string;
        }
        return System.mapLibraryName(string);
    }

    private static boolean isVersionedName(String string) {
        int n;
        if (string.startsWith("lib") && (n = string.lastIndexOf(".so.")) != -1 && n + 4 < string.length()) {
            for (int i = n + 4; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (Character.isDigit(c) || c == '.') continue;
                return true;
            }
            return false;
        }
        return true;
    }

    static String matchLibrary(String string, List<String> list) {
        Object object;
        File file = new File(string);
        if (file.isAbsolute()) {
            list = Arrays.asList(file.getParent());
        }
        FilenameFilter filenameFilter = new FilenameFilter(string){
            final String val$libName;
            {
                this.val$libName = string;
            }

            @Override
            public boolean accept(File file, String string) {
                return (string.startsWith("lib" + this.val$libName + ".so") || string.startsWith(this.val$libName + ".so") && this.val$libName.startsWith("lib")) && NativeLibrary.access$000(string);
            }
        };
        LinkedList<File> linkedList = new LinkedList<File>();
        for (String string2 : list) {
            object = new File(string2).listFiles(filenameFilter);
            if (object == null || ((File[])object).length <= 0) continue;
            linkedList.addAll(Arrays.asList(object));
        }
        double d = -1.0;
        object = null;
        for (File file2 : linkedList) {
            String string3 = file2.getAbsolutePath();
            String string4 = string3.substring(string3.lastIndexOf(".so.") + 4);
            double d2 = NativeLibrary.parseVersion(string4);
            if (!(d2 > d)) continue;
            d = d2;
            object = string3;
        }
        return object;
    }

    static double parseVersion(String string) {
        double d = 0.0;
        double d2 = 1.0;
        int n = string.indexOf(".");
        while (string != null) {
            String string2;
            if (n != -1) {
                string2 = string.substring(0, n);
                string = string.substring(n + 1);
                n = string.indexOf(".");
            } else {
                string2 = string;
                string = null;
            }
            try {
                d += (double)Integer.parseInt(string2) / d2;
            } catch (NumberFormatException numberFormatException) {
                return 0.0;
            }
            d2 *= 100.0;
        }
        return d;
    }

    private static String getMultiArchPath() {
        String string = Platform.ARCH;
        String string2 = Platform.iskFreeBSD() ? "-kfreebsd" : (Platform.isGNU() ? "" : "-linux");
        String string3 = "-gnu";
        if (Platform.isIntel()) {
            string = Platform.is64Bit() ? "x86_64" : "i386";
        } else if (Platform.isPPC()) {
            string = Platform.is64Bit() ? "powerpc64" : "powerpc";
        } else if (Platform.isARM()) {
            string = "arm";
            string3 = "-gnueabi";
        }
        return string + string2 + string3;
    }

    private static ArrayList<String> getLinuxLdPaths() {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec("/sbin/ldconfig -p");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String string = "";
            while ((string = bufferedReader.readLine()) != null) {
                String string2;
                int n = string.indexOf(" => ");
                int n2 = string.lastIndexOf(47);
                if (n == -1 || n2 == -1 || n >= n2 || arrayList.contains(string2 = string.substring(n + 4, n2))) continue;
                arrayList.add(string2);
            }
            bufferedReader.close();
        } catch (Exception exception) {
            // empty catch block
        }
        return arrayList;
    }

    static boolean access$000(String string) {
        return NativeLibrary.isVersionedName(string);
    }

    static {
        if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        String string = Native.getWebStartLibraryPath("jnidispatch");
        if (string != null) {
            librarySearchPath.add(string);
        }
        if (System.getProperty("jna.platform.library.path") == null && !Platform.isWindows()) {
            Object object;
            String string2 = "";
            String string3 = "";
            String string4 = "";
            if (Platform.isLinux() || Platform.isSolaris() || Platform.isFreeBSD() || Platform.iskFreeBSD()) {
                string4 = (Platform.isSolaris() ? "/" : "") + Pointer.SIZE * 8;
            }
            String[] stringArray = new String[]{"/usr/lib" + string4, "/lib" + string4, "/usr/lib", "/lib"};
            if (Platform.isLinux() || Platform.iskFreeBSD() || Platform.isGNU()) {
                object = NativeLibrary.getMultiArchPath();
                stringArray = new String[]{"/usr/lib/" + (String)object, "/lib/" + (String)object, "/usr/lib" + string4, "/lib" + string4, "/usr/lib", "/lib"};
            }
            if (Platform.isLinux()) {
                object = NativeLibrary.getLinuxLdPaths();
                for (int i = stringArray.length - 1; 0 <= i; --i) {
                    int n = ((ArrayList)object).indexOf(stringArray[i]);
                    if (n != -1) {
                        ((ArrayList)object).remove(n);
                    }
                    ((ArrayList)object).add(0, stringArray[i]);
                }
                stringArray = ((ArrayList)object).toArray(new String[((ArrayList)object).size()]);
            }
            for (int i = 0; i < stringArray.length; ++i) {
                File file = new File(stringArray[i]);
                if (!file.exists() || !file.isDirectory()) continue;
                string2 = string2 + string3 + stringArray[i];
                string3 = File.pathSeparator;
            }
            if (!"".equals(string2)) {
                System.setProperty("jna.platform.library.path", string2);
            }
        }
        librarySearchPath.addAll(NativeLibrary.initPaths("jna.platform.library.path"));
    }
}

