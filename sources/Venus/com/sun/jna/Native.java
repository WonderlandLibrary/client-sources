/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Callback;
import com.sun.jna.CallbackReference;
import com.sun.jna.CallbackThreadInitializer;
import com.sun.jna.FromNativeContext;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.Function;
import com.sun.jna.IntegerType;
import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.MethodResultContext;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.ToNativeContext;
import com.sun.jna.ToNativeConverter;
import com.sun.jna.TypeMapper;
import com.sun.jna.Version;
import com.sun.jna.WString;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;

public final class Native
implements Version {
    public static final String DEFAULT_ENCODING = Charset.defaultCharset().name();
    public static boolean DEBUG_LOAD = Boolean.getBoolean("jna.debug_load");
    public static boolean DEBUG_JNA_LOAD = Boolean.getBoolean("jna.debug_load.jna");
    static String jnidispatchPath = null;
    private static final Map<Class<?>, Map<String, Object>> typeOptions = new WeakHashMap();
    private static final Map<Class<?>, Reference<?>> libraries = new WeakHashMap();
    private static final String _OPTION_ENCLOSING_LIBRARY = "enclosing-library";
    private static final Callback.UncaughtExceptionHandler DEFAULT_HANDLER;
    private static Callback.UncaughtExceptionHandler callbackExceptionHandler;
    public static final int POINTER_SIZE;
    public static final int LONG_SIZE;
    public static final int WCHAR_SIZE;
    public static final int SIZE_T_SIZE;
    public static final int BOOL_SIZE;
    private static final int TYPE_VOIDP = 0;
    private static final int TYPE_LONG = 1;
    private static final int TYPE_WCHAR_T = 2;
    private static final int TYPE_SIZE_T = 3;
    private static final int TYPE_BOOL = 4;
    static final int MAX_ALIGNMENT;
    static final int MAX_PADDING;
    private static final Object finalizer;
    static final String JNA_TMPLIB_PREFIX = "jna";
    private static Map<Class<?>, long[]> registeredClasses;
    private static Map<Class<?>, NativeLibrary> registeredLibraries;
    static final int CB_HAS_INITIALIZER = 1;
    private static final int CVT_UNSUPPORTED = -1;
    private static final int CVT_DEFAULT = 0;
    private static final int CVT_POINTER = 1;
    private static final int CVT_STRING = 2;
    private static final int CVT_STRUCTURE = 3;
    private static final int CVT_STRUCTURE_BYVAL = 4;
    private static final int CVT_BUFFER = 5;
    private static final int CVT_ARRAY_BYTE = 6;
    private static final int CVT_ARRAY_SHORT = 7;
    private static final int CVT_ARRAY_CHAR = 8;
    private static final int CVT_ARRAY_INT = 9;
    private static final int CVT_ARRAY_LONG = 10;
    private static final int CVT_ARRAY_FLOAT = 11;
    private static final int CVT_ARRAY_DOUBLE = 12;
    private static final int CVT_ARRAY_BOOLEAN = 13;
    private static final int CVT_BOOLEAN = 14;
    private static final int CVT_CALLBACK = 15;
    private static final int CVT_FLOAT = 16;
    private static final int CVT_NATIVE_MAPPED = 17;
    private static final int CVT_NATIVE_MAPPED_STRING = 18;
    private static final int CVT_NATIVE_MAPPED_WSTRING = 19;
    private static final int CVT_WSTRING = 20;
    private static final int CVT_INTEGER_TYPE = 21;
    private static final int CVT_POINTER_TYPE = 22;
    private static final int CVT_TYPE_MAPPER = 23;
    private static final int CVT_TYPE_MAPPER_STRING = 24;
    private static final int CVT_TYPE_MAPPER_WSTRING = 25;
    static final int CB_OPTION_DIRECT = 1;
    static final int CB_OPTION_IN_DLL = 2;
    private static final ThreadLocal<Memory> nativeThreadTerminationFlag;
    private static final Map<Thread, Pointer> nativeThreads;

    @Deprecated
    public static float parseVersion(String string) {
        return Float.parseFloat(string.substring(0, string.lastIndexOf(".")));
    }

    static boolean isCompatibleVersion(String string, String string2) {
        String[] stringArray = string.split("\\.");
        String[] stringArray2 = string2.split("\\.");
        if (stringArray.length < 3 || stringArray2.length < 3) {
            return true;
        }
        int n = Integer.parseInt(stringArray[0]);
        int n2 = Integer.parseInt(stringArray2[0]);
        int n3 = Integer.parseInt(stringArray[5]);
        int n4 = Integer.parseInt(stringArray2[5]);
        if (n != n2) {
            return true;
        }
        return n3 > n4;
    }

    private static void dispose() {
        CallbackReference.disposeAll();
        Memory.disposeAll();
        NativeLibrary.disposeAll();
        Native.unregisterAll();
        jnidispatchPath = null;
        System.setProperty("jna.loaded", "false");
    }

    static boolean deleteLibrary(File file) {
        if (file.delete()) {
            return false;
        }
        Native.markTemporaryFile(file);
        return true;
    }

    private Native() {
    }

    private static native void initIDs();

    public static synchronized native void setProtected(boolean var0);

    public static synchronized native boolean isProtected();

    @Deprecated
    public static void setPreserveLastError(boolean bl) {
    }

    @Deprecated
    public static boolean getPreserveLastError() {
        return false;
    }

    public static long getWindowID(Window window) throws HeadlessException {
        return AWT.getWindowID(window);
    }

    public static long getComponentID(Component component) throws HeadlessException {
        return AWT.getComponentID(component);
    }

    public static Pointer getWindowPointer(Window window) throws HeadlessException {
        return new Pointer(AWT.getWindowID(window));
    }

    public static Pointer getComponentPointer(Component component) throws HeadlessException {
        return new Pointer(AWT.getComponentID(component));
    }

    static native long getWindowHandle0(Component var0);

    public static Pointer getDirectBufferPointer(Buffer buffer) {
        long l = Native._getDirectBufferPointer(buffer);
        return l == 0L ? null : new Pointer(l);
    }

    private static native long _getDirectBufferPointer(Buffer var0);

    public static String toString(byte[] byArray) {
        return Native.toString(byArray, Native.getDefaultStringEncoding());
    }

    public static String toString(byte[] byArray, String string) {
        int n = byArray.length;
        for (int i = 0; i < n; ++i) {
            if (byArray[i] != 0) continue;
            n = i;
            break;
        }
        if (n == 0) {
            return "";
        }
        if (string != null) {
            try {
                return new String(byArray, 0, n, string);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                System.err.println("JNA Warning: Encoding '" + string + "' is unsupported");
            }
        }
        System.err.println("JNA Warning: Decoding with fallback " + System.getProperty("file.encoding"));
        return new String(byArray, 0, n);
    }

    public static String toString(char[] cArray) {
        int n = cArray.length;
        for (int i = 0; i < n; ++i) {
            if (cArray[i] != '\u0000') continue;
            n = i;
            break;
        }
        if (n == 0) {
            return "";
        }
        return new String(cArray, 0, n);
    }

    public static List<String> toStringList(char[] cArray) {
        return Native.toStringList(cArray, 0, cArray.length);
    }

    public static List<String> toStringList(char[] cArray, int n, int n2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n3 = n;
        int n4 = n + n2;
        for (int i = n; i < n4; ++i) {
            if (cArray[i] != '\u0000') continue;
            if (n3 == i) {
                return arrayList;
            }
            String string = new String(cArray, n3, i - n3);
            arrayList.add(string);
            n3 = i + 1;
        }
        if (n3 < n4) {
            String string = new String(cArray, n3, n4 - n3);
            arrayList.add(string);
        }
        return arrayList;
    }

    public static <T> T loadLibrary(Class<T> clazz) {
        return Native.loadLibrary(null, clazz);
    }

    public static <T> T loadLibrary(Class<T> clazz, Map<String, ?> map) {
        return Native.loadLibrary(null, clazz, map);
    }

    public static <T> T loadLibrary(String string, Class<T> clazz) {
        return Native.loadLibrary(string, clazz, Collections.emptyMap());
    }

    public static <T> T loadLibrary(String string, Class<T> clazz, Map<String, ?> map) {
        if (!Library.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Interface (" + clazz.getSimpleName() + ") of library=" + string + " does not extend " + Library.class.getSimpleName());
        }
        Library.Handler handler = new Library.Handler(string, clazz, map);
        ClassLoader classLoader = clazz.getClassLoader();
        Object object = Proxy.newProxyInstance(classLoader, new Class[]{clazz}, handler);
        Native.cacheOptions(clazz, map, object);
        return clazz.cast(object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void loadLibraryInstance(Class<?> clazz) {
        Map<Class<?>, Reference<?>> map = libraries;
        synchronized (map) {
            if (clazz != null && !libraries.containsKey(clazz)) {
                try {
                    Field[] fieldArray = clazz.getFields();
                    for (int i = 0; i < fieldArray.length; ++i) {
                        Field field = fieldArray[i];
                        if (field.getType() != clazz || !Modifier.isStatic(field.getModifiers())) continue;
                        libraries.put(clazz, new WeakReference<Object>(field.get(null)));
                        break;
                    }
                } catch (Exception exception) {
                    throw new IllegalArgumentException("Could not access instance of " + clazz + " (" + exception + ")");
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Class<?> findEnclosingLibraryClass(Class<?> clazz) {
        Class<?> clazz2;
        if (clazz == null) {
            return null;
        }
        Object object = libraries;
        synchronized (object) {
            if (typeOptions.containsKey(clazz)) {
                Map<String, Object> map = typeOptions.get(clazz);
                Class clazz3 = (Class)map.get(_OPTION_ENCLOSING_LIBRARY);
                if (clazz3 != null) {
                    return clazz3;
                }
                return clazz;
            }
        }
        if (Library.class.isAssignableFrom(clazz)) {
            return clazz;
        }
        if (Callback.class.isAssignableFrom(clazz)) {
            clazz = CallbackReference.findCallbackClass(clazz);
        }
        if ((clazz2 = Native.findEnclosingLibraryClass(object = clazz.getDeclaringClass())) != null) {
            return clazz2;
        }
        return Native.findEnclosingLibraryClass(clazz.getSuperclass());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Map<String, Object> getLibraryOptions(Class<?> clazz) {
        Map<String, Object> map;
        Class<?> clazz2 = libraries;
        synchronized (clazz2) {
            map = typeOptions.get(clazz);
            if (map != null) {
                return map;
            }
        }
        clazz2 = Native.findEnclosingLibraryClass(clazz);
        if (clazz2 != null) {
            Native.loadLibraryInstance(clazz2);
        } else {
            clazz2 = clazz;
        }
        Map<Class<?>, Reference<?>> map2 = libraries;
        synchronized (map2) {
            map = typeOptions.get(clazz2);
            if (map != null) {
                typeOptions.put(clazz, map);
                return map;
            }
            try {
                Field field = clazz2.getField("OPTIONS");
                field.setAccessible(false);
                map = (Map<String, Object>)field.get(null);
                if (map == null) {
                    throw new IllegalStateException("Null options field");
                }
            } catch (NoSuchFieldException noSuchFieldException) {
                map = Collections.emptyMap();
            } catch (Exception exception) {
                throw new IllegalArgumentException("OPTIONS must be a public field of type java.util.Map (" + exception + "): " + clazz2);
            }
            map = new HashMap<String, Object>(map);
            if (!map.containsKey("type-mapper")) {
                map.put("type-mapper", Native.lookupField(clazz2, "TYPE_MAPPER", TypeMapper.class));
            }
            if (!map.containsKey("structure-alignment")) {
                map.put("structure-alignment", Native.lookupField(clazz2, "STRUCTURE_ALIGNMENT", Integer.class));
            }
            if (!map.containsKey("string-encoding")) {
                map.put("string-encoding", Native.lookupField(clazz2, "STRING_ENCODING", String.class));
            }
            map = Native.cacheOptions(clazz2, map, null);
            if (clazz != clazz2) {
                typeOptions.put(clazz, map);
            }
            return map;
        }
    }

    private static Object lookupField(Class<?> clazz, String string, Class<?> clazz2) {
        try {
            Field field = clazz.getField(string);
            field.setAccessible(false);
            return field.get(null);
        } catch (NoSuchFieldException noSuchFieldException) {
            return null;
        } catch (Exception exception) {
            throw new IllegalArgumentException(string + " must be a public field of type " + clazz2.getName() + " (" + exception + "): " + clazz);
        }
    }

    public static TypeMapper getTypeMapper(Class<?> clazz) {
        Map<String, Object> map = Native.getLibraryOptions(clazz);
        return (TypeMapper)map.get("type-mapper");
    }

    public static String getStringEncoding(Class<?> clazz) {
        Map<String, Object> map = Native.getLibraryOptions(clazz);
        String string = (String)map.get("string-encoding");
        return string != null ? string : Native.getDefaultStringEncoding();
    }

    public static String getDefaultStringEncoding() {
        return System.getProperty("jna.encoding", DEFAULT_ENCODING);
    }

    public static int getStructureAlignment(Class<?> clazz) {
        Integer n = (Integer)Native.getLibraryOptions(clazz).get("structure-alignment");
        return n == null ? 0 : n;
    }

    static byte[] getBytes(String string) {
        return Native.getBytes(string, Native.getDefaultStringEncoding());
    }

    static byte[] getBytes(String string, String string2) {
        if (string2 != null) {
            try {
                return string.getBytes(string2);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                System.err.println("JNA Warning: Encoding '" + string2 + "' is unsupported");
            }
        }
        System.err.println("JNA Warning: Encoding with fallback " + System.getProperty("file.encoding"));
        return string.getBytes();
    }

    public static byte[] toByteArray(String string) {
        return Native.toByteArray(string, Native.getDefaultStringEncoding());
    }

    public static byte[] toByteArray(String string, String string2) {
        byte[] byArray = Native.getBytes(string, string2);
        byte[] byArray2 = new byte[byArray.length + 1];
        System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        return byArray2;
    }

    public static char[] toCharArray(String string) {
        char[] cArray = string.toCharArray();
        char[] cArray2 = new char[cArray.length + 1];
        System.arraycopy(cArray, 0, cArray2, 0, cArray.length);
        return cArray2;
    }

    private static void loadNativeDispatchLibrary() {
        if (!Boolean.getBoolean("jna.nounpack")) {
            try {
                Native.removeTemporaryFiles();
            } catch (IOException iOException) {
                System.err.println("JNA Warning: IOException removing temporary files: " + iOException.getMessage());
            }
        }
        String string = System.getProperty("jna.boot.library.name", "jnidispatch");
        String string2 = System.getProperty("jna.boot.library.path");
        if (string2 != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string2, File.pathSeparator);
            while (stringTokenizer.hasMoreTokens()) {
                String string3;
                String string4;
                String string5 = stringTokenizer.nextToken();
                File file = new File(new File(string5), System.mapLibraryName(string).replace(".dylib", ".jnilib"));
                String string6 = file.getAbsolutePath();
                if (DEBUG_JNA_LOAD) {
                    System.out.println("Looking in " + string6);
                }
                if (file.exists()) {
                    try {
                        if (DEBUG_JNA_LOAD) {
                            System.out.println("Trying " + string6);
                        }
                        System.setProperty("jnidispatch.path", string6);
                        System.load(string6);
                        jnidispatchPath = string6;
                        if (DEBUG_JNA_LOAD) {
                            System.out.println("Found jnidispatch at " + string6);
                        }
                        return;
                    } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                        // empty catch block
                    }
                }
                if (!Platform.isMac()) continue;
                if (string6.endsWith("dylib")) {
                    string4 = "dylib";
                    string3 = "jnilib";
                } else {
                    string4 = "jnilib";
                    string3 = "dylib";
                }
                string6 = string6.substring(0, string6.lastIndexOf(string4)) + string3;
                if (DEBUG_JNA_LOAD) {
                    System.out.println("Looking in " + string6);
                }
                if (!new File(string6).exists()) continue;
                try {
                    if (DEBUG_JNA_LOAD) {
                        System.out.println("Trying " + string6);
                    }
                    System.setProperty("jnidispatch.path", string6);
                    System.load(string6);
                    jnidispatchPath = string6;
                    if (DEBUG_JNA_LOAD) {
                        System.out.println("Found jnidispatch at " + string6);
                    }
                    return;
                } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                    System.err.println("File found at " + string6 + " but not loadable: " + unsatisfiedLinkError.getMessage());
                }
            }
        }
        if (!Boolean.getBoolean("jna.nosys")) {
            try {
                if (DEBUG_JNA_LOAD) {
                    System.out.println("Trying (via loadLibrary) " + string);
                }
                System.loadLibrary(string);
                if (DEBUG_JNA_LOAD) {
                    System.out.println("Found jnidispatch on system path");
                }
                return;
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                // empty catch block
            }
        }
        if (Boolean.getBoolean("jna.noclasspath")) {
            throw new UnsatisfiedLinkError("Unable to locate JNA native support library");
        }
        Native.loadNativeDispatchLibraryFromClasspath();
    }

    private static void loadNativeDispatchLibraryFromClasspath() {
        try {
            String string = "/com/sun/jna/" + Platform.RESOURCE_PREFIX + "/" + System.mapLibraryName("jnidispatch").replace(".dylib", ".jnilib");
            File file = Native.extractFromResourcePath(string, Native.class.getClassLoader());
            if (file == null && file == null) {
                throw new UnsatisfiedLinkError("Could not find JNA native support");
            }
            if (DEBUG_JNA_LOAD) {
                System.out.println("Trying " + file.getAbsolutePath());
            }
            System.setProperty("jnidispatch.path", file.getAbsolutePath());
            System.load(file.getAbsolutePath());
            jnidispatchPath = file.getAbsolutePath();
            if (DEBUG_JNA_LOAD) {
                System.out.println("Found jnidispatch at " + jnidispatchPath);
            }
            if (Native.isUnpacked(file) && !Boolean.getBoolean("jnidispatch.preserve")) {
                Native.deleteLibrary(file);
            }
        } catch (IOException iOException) {
            throw new UnsatisfiedLinkError(iOException.getMessage());
        }
    }

    static boolean isUnpacked(File file) {
        return file.getName().startsWith(JNA_TMPLIB_PREFIX);
    }

    public static File extractFromResourcePath(String string) throws IOException {
        return Native.extractFromResourcePath(string, null);
    }

    public static File extractFromResourcePath(String string, ClassLoader classLoader) throws IOException {
        URL uRL;
        String string2;
        boolean bl;
        boolean bl2 = bl = DEBUG_LOAD || DEBUG_JNA_LOAD && string.indexOf("jnidispatch") != -1;
        if (classLoader == null && (classLoader = Thread.currentThread().getContextClassLoader()) == null) {
            classLoader = Native.class.getClassLoader();
        }
        if (bl) {
            System.out.println("Looking in classpath from " + classLoader + " for " + string);
        }
        String string3 = string.startsWith("/") ? string : NativeLibrary.mapSharedLibraryName(string);
        String string4 = string2 = string.startsWith("/") ? string : Platform.RESOURCE_PREFIX + "/" + string3;
        if (string2.startsWith("/")) {
            string2 = string2.substring(1);
        }
        if ((uRL = classLoader.getResource(string2)) == null && string2.startsWith(Platform.RESOURCE_PREFIX)) {
            uRL = classLoader.getResource(string3);
        }
        if (uRL == null) {
            String string5 = System.getProperty("java.class.path");
            if (classLoader instanceof URLClassLoader) {
                string5 = Arrays.asList(((URLClassLoader)classLoader).getURLs()).toString();
            }
            throw new IOException("Native library (" + string2 + ") not found in resource path (" + string5 + ")");
        }
        if (bl) {
            System.out.println("Found library resource at " + uRL);
        }
        File file = null;
        if (uRL.getProtocol().toLowerCase().equals("file")) {
            try {
                file = new File(new URI(uRL.toString()));
            } catch (URISyntaxException uRISyntaxException) {
                file = new File(uRL.getPath());
            }
            if (bl) {
                System.out.println("Looking in " + file.getAbsolutePath());
            }
            if (!file.exists()) {
                throw new IOException("File URL " + uRL + " could not be properly decoded");
            }
        } else if (!Boolean.getBoolean("jna.nounpack")) {
            InputStream inputStream = classLoader.getResourceAsStream(string2);
            if (inputStream == null) {
                throw new IOException("Can't obtain InputStream for " + string2);
            }
            FileOutputStream fileOutputStream = null;
            try {
                int n;
                File file2 = Native.getTempDir();
                file = File.createTempFile(JNA_TMPLIB_PREFIX, Platform.isWindows() ? ".dll" : null, file2);
                if (!Boolean.getBoolean("jnidispatch.preserve")) {
                    file.deleteOnExit();
                }
                fileOutputStream = new FileOutputStream(file);
                byte[] byArray = new byte[1024];
                while ((n = inputStream.read(byArray, 0, byArray.length)) > 0) {
                    fileOutputStream.write(byArray, 0, n);
                }
            } catch (IOException iOException) {
                throw new IOException("Failed to create temporary file for " + string + " library: " + iOException.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException iOException) {}
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException iOException) {}
                }
            }
        }
        return file;
    }

    private static native int sizeof(int var0);

    private static native String getNativeVersion();

    private static native String getAPIChecksum();

    public static native int getLastError();

    public static native void setLastError(int var0);

    public static Library synchronizedLibrary(Library library) {
        Class<?> clazz = library.getClass();
        if (!Proxy.isProxyClass(clazz)) {
            throw new IllegalArgumentException("Library must be a proxy class");
        }
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(library);
        if (!(invocationHandler instanceof Library.Handler)) {
            throw new IllegalArgumentException("Unrecognized proxy handler: " + invocationHandler);
        }
        Library.Handler handler = (Library.Handler)invocationHandler;
        InvocationHandler invocationHandler2 = new InvocationHandler(handler, library){
            final Library.Handler val$handler;
            final Library val$library;
            {
                this.val$handler = handler;
                this.val$library = library;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public Object invoke(Object object, Method method, Object[] objectArray) throws Throwable {
                NativeLibrary nativeLibrary = this.val$handler.getNativeLibrary();
                synchronized (nativeLibrary) {
                    return this.val$handler.invoke(this.val$library, method, objectArray);
                }
            }
        };
        return (Library)Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), invocationHandler2);
    }

    public static String getWebStartLibraryPath(String string) {
        if (System.getProperty("javawebstart.version") == null) {
            return null;
        }
        try {
            ClassLoader classLoader = Native.class.getClassLoader();
            Method method = AccessController.doPrivileged(new PrivilegedAction<Method>(){

                @Override
                public Method run() {
                    try {
                        Method method = ClassLoader.class.getDeclaredMethod("findLibrary", String.class);
                        method.setAccessible(false);
                        return method;
                    } catch (Exception exception) {
                        return null;
                    }
                }

                @Override
                public Object run() {
                    return this.run();
                }
            });
            String string2 = (String)method.invoke(classLoader, string);
            if (string2 != null) {
                return new File(string2).getParent();
            }
            return null;
        } catch (Exception exception) {
            return null;
        }
    }

    static void markTemporaryFile(File file) {
        try {
            File file2 = new File(file.getParentFile(), file.getName() + ".x");
            file2.createNewFile();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    static File getTempDir() throws IOException {
        File file;
        String string = System.getProperty("jna.tmpdir");
        if (string != null) {
            file = new File(string);
            file.mkdirs();
        } else {
            File file2 = new File(System.getProperty("java.io.tmpdir"));
            file = new File(file2, "jna-" + System.getProperty("user.name").hashCode());
            file.mkdirs();
            if (!file.exists() || !file.canWrite()) {
                file = file2;
            }
        }
        if (!file.exists()) {
            throw new IOException("JNA temporary directory '" + file + "' does not exist");
        }
        if (!file.canWrite()) {
            throw new IOException("JNA temporary directory '" + file + "' is not writable");
        }
        return file;
    }

    static void removeTemporaryFiles() throws IOException {
        File file = Native.getTempDir();
        FilenameFilter filenameFilter = new FilenameFilter(){

            @Override
            public boolean accept(File file, String string) {
                return string.endsWith(".x") && string.startsWith(Native.JNA_TMPLIB_PREFIX);
            }
        };
        File[] fileArray = file.listFiles(filenameFilter);
        for (int i = 0; fileArray != null && i < fileArray.length; ++i) {
            File file2 = fileArray[i];
            String string = file2.getName();
            string = string.substring(0, string.length() - 2);
            File file3 = new File(file2.getParentFile(), string);
            if (file3.exists() && !file3.delete()) continue;
            file2.delete();
        }
    }

    public static int getNativeSize(Class<?> clazz, Object object) {
        if (clazz.isArray()) {
            int n = Array.getLength(object);
            if (n > 0) {
                Object object2 = Array.get(object, 0);
                return n * Native.getNativeSize(clazz.getComponentType(), object2);
            }
            throw new IllegalArgumentException("Arrays of length zero not allowed: " + clazz);
        }
        if (Structure.class.isAssignableFrom(clazz) && !Structure.ByReference.class.isAssignableFrom(clazz)) {
            return Structure.size(clazz, (Structure)object);
        }
        try {
            return Native.getNativeSize(clazz);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("The type \"" + clazz.getName() + "\" is not supported: " + illegalArgumentException.getMessage());
        }
    }

    public static int getNativeSize(Class<?> clazz) {
        if (NativeMapped.class.isAssignableFrom(clazz)) {
            clazz = NativeMappedConverter.getInstance(clazz).nativeType();
        }
        if (clazz == Boolean.TYPE || clazz == Boolean.class) {
            return 1;
        }
        if (clazz == Byte.TYPE || clazz == Byte.class) {
            return 0;
        }
        if (clazz == Short.TYPE || clazz == Short.class) {
            return 1;
        }
        if (clazz == Character.TYPE || clazz == Character.class) {
            return WCHAR_SIZE;
        }
        if (clazz == Integer.TYPE || clazz == Integer.class) {
            return 1;
        }
        if (clazz == Long.TYPE || clazz == Long.class) {
            return 1;
        }
        if (clazz == Float.TYPE || clazz == Float.class) {
            return 1;
        }
        if (clazz == Double.TYPE || clazz == Double.class) {
            return 1;
        }
        if (Structure.class.isAssignableFrom(clazz)) {
            if (Structure.ByValue.class.isAssignableFrom(clazz)) {
                return Structure.size(clazz);
            }
            return POINTER_SIZE;
        }
        if (Pointer.class.isAssignableFrom(clazz) || Platform.HAS_BUFFERS && Buffers.isBuffer(clazz) || Callback.class.isAssignableFrom(clazz) || String.class == clazz || WString.class == clazz) {
            return POINTER_SIZE;
        }
        throw new IllegalArgumentException("Native size for type \"" + clazz.getName() + "\" is unknown");
    }

    public static boolean isSupportedNativeType(Class<?> clazz) {
        if (Structure.class.isAssignableFrom(clazz)) {
            return false;
        }
        try {
            return Native.getNativeSize(clazz) != 0;
        } catch (IllegalArgumentException illegalArgumentException) {
            return true;
        }
    }

    public static void setCallbackExceptionHandler(Callback.UncaughtExceptionHandler uncaughtExceptionHandler) {
        callbackExceptionHandler = uncaughtExceptionHandler == null ? DEFAULT_HANDLER : uncaughtExceptionHandler;
    }

    public static Callback.UncaughtExceptionHandler getCallbackExceptionHandler() {
        return callbackExceptionHandler;
    }

    public static void register(String string) {
        Native.register(Native.findDirectMappedClass(Native.getCallingClass()), string);
    }

    public static void register(NativeLibrary nativeLibrary) {
        Native.register(Native.findDirectMappedClass(Native.getCallingClass()), nativeLibrary);
    }

    static Class<?> findDirectMappedClass(Class<?> clazz) {
        Method[] methodArray;
        for (Method method : methodArray = clazz.getDeclaredMethods()) {
            if ((method.getModifiers() & 0x100) == 0) continue;
            return clazz;
        }
        int n = clazz.getName().lastIndexOf("$");
        if (n != -1) {
            String string = clazz.getName().substring(0, n);
            try {
                return Native.findDirectMappedClass(Class.forName(string, true, clazz.getClassLoader()));
            } catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        throw new IllegalArgumentException("Can't determine class with native methods from the current context (" + clazz + ")");
    }

    static Class<?> getCallingClass() {
        Class<?>[] classArray = new SecurityManager(){

            public Class<?>[] getClassContext() {
                return super.getClassContext();
            }
        }.getClassContext();
        if (classArray == null) {
            throw new IllegalStateException("The SecurityManager implementation on this platform is broken; you must explicitly provide the class to register");
        }
        if (classArray.length < 4) {
            throw new IllegalStateException("This method must be called from the static initializer of a class");
        }
        return classArray[0];
    }

    public static void setCallbackThreadInitializer(Callback callback, CallbackThreadInitializer callbackThreadInitializer) {
        CallbackReference.setCallbackThreadInitializer(callback, callbackThreadInitializer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void unregisterAll() {
        Map<Class<?>, long[]> map = registeredClasses;
        synchronized (map) {
            for (Map.Entry<Class<?>, long[]> entry : registeredClasses.entrySet()) {
                Native.unregister(entry.getKey(), entry.getValue());
            }
            registeredClasses.clear();
        }
    }

    public static void unregister() {
        Native.unregister(Native.findDirectMappedClass(Native.getCallingClass()));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void unregister(Class<?> clazz) {
        Map<Class<?>, long[]> map = registeredClasses;
        synchronized (map) {
            long[] lArray = registeredClasses.get(clazz);
            if (lArray != null) {
                Native.unregister(clazz, lArray);
                registeredClasses.remove(clazz);
                registeredLibraries.remove(clazz);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean registered(Class<?> clazz) {
        Map<Class<?>, long[]> map = registeredClasses;
        synchronized (map) {
            return registeredClasses.containsKey(clazz);
        }
    }

    private static native void unregister(Class<?> var0, long[] var1);

    static String getSignature(Class<?> clazz) {
        if (clazz.isArray()) {
            return "[" + Native.getSignature(clazz.getComponentType());
        }
        if (clazz.isPrimitive()) {
            if (clazz == Void.TYPE) {
                return "V";
            }
            if (clazz == Boolean.TYPE) {
                return "Z";
            }
            if (clazz == Byte.TYPE) {
                return "B";
            }
            if (clazz == Short.TYPE) {
                return "S";
            }
            if (clazz == Character.TYPE) {
                return "C";
            }
            if (clazz == Integer.TYPE) {
                return "I";
            }
            if (clazz == Long.TYPE) {
                return "J";
            }
            if (clazz == Float.TYPE) {
                return "F";
            }
            if (clazz == Double.TYPE) {
                return "D";
            }
        }
        return "L" + Native.replace(".", "/", clazz.getName()) + ";";
    }

    static String replace(String string, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int n;
            if ((n = string3.indexOf(string)) == -1) break;
            stringBuilder.append(string3.substring(0, n));
            stringBuilder.append(string2);
            string3 = string3.substring(n + string.length());
        }
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    private static int getConversion(Class<?> clazz, TypeMapper typeMapper) {
        Class<?> clazz2;
        if (clazz == Boolean.class) {
            clazz = Boolean.TYPE;
        } else if (clazz == Byte.class) {
            clazz = Byte.TYPE;
        } else if (clazz == Short.class) {
            clazz = Short.TYPE;
        } else if (clazz == Character.class) {
            clazz = Character.TYPE;
        } else if (clazz == Integer.class) {
            clazz = Integer.TYPE;
        } else if (clazz == Long.class) {
            clazz = Long.TYPE;
        } else if (clazz == Float.class) {
            clazz = Float.TYPE;
        } else if (clazz == Double.class) {
            clazz = Double.TYPE;
        } else if (clazz == Void.class) {
            clazz = Void.TYPE;
        }
        if (typeMapper != null) {
            clazz2 = typeMapper.getFromNativeConverter(clazz);
            ToNativeConverter toNativeConverter = typeMapper.getToNativeConverter(clazz);
            if (clazz2 != null) {
                Class<?> clazz3 = clazz2.nativeType();
                if (clazz3 == String.class) {
                    return 1;
                }
                if (clazz3 == WString.class) {
                    return 0;
                }
                return 0;
            }
            if (toNativeConverter != null) {
                Class<?> clazz4 = toNativeConverter.nativeType();
                if (clazz4 == String.class) {
                    return 1;
                }
                if (clazz4 == WString.class) {
                    return 0;
                }
                return 0;
            }
        }
        if (Pointer.class.isAssignableFrom(clazz)) {
            return 0;
        }
        if (String.class == clazz) {
            return 1;
        }
        if (WString.class.isAssignableFrom(clazz)) {
            return 1;
        }
        if (Platform.HAS_BUFFERS && Buffers.isBuffer(clazz)) {
            return 0;
        }
        if (Structure.class.isAssignableFrom(clazz)) {
            if (Structure.ByValue.class.isAssignableFrom(clazz)) {
                return 1;
            }
            return 0;
        }
        if (clazz.isArray()) {
            switch (clazz.getName().charAt(1)) {
                case 'Z': {
                    return 0;
                }
                case 'B': {
                    return 1;
                }
                case 'S': {
                    return 0;
                }
                case 'C': {
                    return 1;
                }
                case 'I': {
                    return 0;
                }
                case 'J': {
                    return 1;
                }
                case 'F': {
                    return 0;
                }
                case 'D': {
                    return 1;
                }
            }
        }
        if (clazz.isPrimitive()) {
            return clazz == Boolean.TYPE ? 14 : 0;
        }
        if (Callback.class.isAssignableFrom(clazz)) {
            return 0;
        }
        if (IntegerType.class.isAssignableFrom(clazz)) {
            return 0;
        }
        if (PointerType.class.isAssignableFrom(clazz)) {
            return 1;
        }
        if (NativeMapped.class.isAssignableFrom(clazz)) {
            clazz2 = NativeMappedConverter.getInstance(clazz).nativeType();
            if (clazz2 == String.class) {
                return 1;
            }
            if (clazz2 == WString.class) {
                return 0;
            }
            return 0;
        }
        return 1;
    }

    public static void register(Class<?> clazz, String string) {
        NativeLibrary nativeLibrary = NativeLibrary.getInstance(string, Collections.singletonMap("classloader", clazz.getClassLoader()));
        Native.register(clazz, nativeLibrary);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public static void register(Class<?> clazz, NativeLibrary nativeLibrary) {
        Method[] methodArray = clazz.getDeclaredMethods();
        ArrayList<Method> arrayList = new ArrayList<Method>();
        Map<String, Object> map = nativeLibrary.getOptions();
        TypeMapper typeMapper = (TypeMapper)map.get("type-mapper");
        map = Native.cacheOptions(clazz, map, null);
        Method[] objectArray = methodArray;
        int map2 = objectArray.length;
        for (int method = 0; method < map2; ++method) {
            Method method2 = objectArray[method];
            if ((method2.getModifiers() & 0x100) == 0) continue;
            arrayList.add(method2);
        }
        long[] lArray = new long[arrayList.size()];
        for (map2 = 0; map2 < lArray.length; ++map2) {
            void var9_15;
            Class<Pointer> clazz2;
            long l;
            long l2;
            Method method = (Method)arrayList.get(map2);
            String string = "(";
            Class<?> clazz3 = method.getReturnType();
            Class<?>[] classArray = method.getParameterTypes();
            long[] lArray2 = new long[classArray.length];
            long[] lArray3 = new long[classArray.length];
            int[] nArray = new int[classArray.length];
            ToNativeConverter[] toNativeConverterArray = new ToNativeConverter[classArray.length];
            FromNativeConverter fromNativeConverter = null;
            int n = Native.getConversion(clazz3, typeMapper);
            boolean bl = false;
            switch (n) {
                case -1: {
                    throw new IllegalArgumentException(clazz3 + " is not a supported return type (in method " + method.getName() + " in " + clazz + ")");
                }
                case 23: 
                case 24: 
                case 25: {
                    fromNativeConverter = typeMapper.getFromNativeConverter(clazz3);
                    l2 = Structure.FFIType.get(clazz3.isPrimitive() ? clazz3 : Pointer.class).peer;
                    l = Structure.FFIType.get(fromNativeConverter.nativeType()).peer;
                    break;
                }
                case 17: 
                case 18: 
                case 19: 
                case 21: 
                case 22: {
                    l2 = Structure.FFIType.get(Pointer.class).peer;
                    l = Structure.FFIType.get(NativeMappedConverter.getInstance(clazz3).nativeType()).peer;
                    break;
                }
                case 3: {
                    l2 = l = Structure.FFIType.get(Pointer.class).peer;
                    break;
                }
                case 4: {
                    l2 = Structure.FFIType.get(Pointer.class).peer;
                    l = Structure.FFIType.get(clazz3).peer;
                    break;
                }
                default: {
                    l2 = l = Structure.FFIType.get(clazz3).peer;
                }
            }
            block19: for (int i = 0; i < classArray.length; ++i) {
                int n2;
                clazz2 = classArray[i];
                String string2 = (String)var9_15 + Native.getSignature(clazz2);
                nArray[i] = n2 = Native.getConversion(clazz2, typeMapper);
                if (n2 == -1) {
                    throw new IllegalArgumentException(clazz2 + " is not a supported argument type (in method " + method.getName() + " in " + clazz + ")");
                }
                if (n2 == 17 || n2 == 18 || n2 == 19 || n2 == 21) {
                    clazz2 = NativeMappedConverter.getInstance(clazz2).nativeType();
                } else if (n2 == 23 || n2 == 24 || n2 == 25) {
                    toNativeConverterArray[i] = typeMapper.getToNativeConverter(clazz2);
                }
                switch (n2) {
                    case 4: 
                    case 17: 
                    case 18: 
                    case 19: 
                    case 21: 
                    case 22: {
                        lArray2[i] = Structure.FFIType.get((Object)clazz2).peer;
                        lArray3[i] = Structure.FFIType.get(Pointer.class).peer;
                        continue block19;
                    }
                    case 23: 
                    case 24: 
                    case 25: {
                        lArray3[i] = Structure.FFIType.get(clazz2.isPrimitive() ? clazz2 : Pointer.class).peer;
                        lArray2[i] = Structure.FFIType.get(toNativeConverterArray[i].nativeType()).peer;
                        continue block19;
                    }
                    case 0: {
                        lArray3[i] = lArray2[i] = Structure.FFIType.get(clazz2).peer;
                        continue block19;
                    }
                    default: {
                        lArray3[i] = lArray2[i] = Structure.FFIType.get(Pointer.class).peer;
                    }
                }
            }
            String string4 = (String)var9_15 + ")";
            string4 = string4 + Native.getSignature(clazz3);
            Class<?>[] classArray2 = method.getExceptionTypes();
            for (int i = 0; i < classArray2.length; ++i) {
                if (!LastErrorException.class.isAssignableFrom(classArray2[i])) continue;
                bl = true;
                break;
            }
            clazz2 = nativeLibrary.getFunction(method.getName(), method);
            try {
                lArray[map2] = Native.registerMethod(clazz, method.getName(), string4, nArray, lArray3, lArray2, n, l2, l, method, ((Function)((Object)clazz2)).peer, ((Function)((Object)clazz2)).getCallingConvention(), bl, toNativeConverterArray, fromNativeConverter, ((Function)((Object)clazz2)).encoding);
                continue;
            } catch (NoSuchMethodError noSuchMethodError) {
                throw new UnsatisfiedLinkError("No method " + method.getName() + " with signature " + string4 + " in " + clazz);
            }
        }
        Map<Class<?>, long[]> map3 = registeredClasses;
        synchronized (map3) {
            registeredClasses.put(clazz, lArray);
            registeredLibraries.put(clazz, nativeLibrary);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Map<String, Object> cacheOptions(Class<?> clazz, Map<String, ?> map, Object object) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>(map);
        hashMap.put(_OPTION_ENCLOSING_LIBRARY, clazz);
        Map<Class<?>, Reference<?>> map2 = libraries;
        synchronized (map2) {
            typeOptions.put(clazz, hashMap);
            if (object != null) {
                libraries.put(clazz, new WeakReference<Object>(object));
            }
            if (!clazz.isInterface() && Library.class.isAssignableFrom(clazz)) {
                Class<?>[] classArray;
                for (Class<?> clazz2 : classArray = clazz.getInterfaces()) {
                    if (!Library.class.isAssignableFrom(clazz2)) continue;
                    Native.cacheOptions(clazz2, hashMap, object);
                    break;
                }
            }
        }
        return hashMap;
    }

    private static native long registerMethod(Class<?> var0, String var1, String var2, int[] var3, long[] var4, long[] var5, int var6, long var7, long var9, Method var11, long var12, int var14, boolean var15, ToNativeConverter[] var16, FromNativeConverter var17, String var18);

    private static NativeMapped fromNative(Class<?> clazz, Object object) {
        return (NativeMapped)NativeMappedConverter.getInstance(clazz).fromNative(object, new FromNativeContext(clazz));
    }

    private static NativeMapped fromNative(Method method, Object object) {
        Class<?> clazz = method.getReturnType();
        return (NativeMapped)NativeMappedConverter.getInstance(clazz).fromNative(object, new MethodResultContext(clazz, null, null, method));
    }

    private static Class<?> nativeType(Class<?> clazz) {
        return NativeMappedConverter.getInstance(clazz).nativeType();
    }

    private static Object toNative(ToNativeConverter toNativeConverter, Object object) {
        return toNativeConverter.toNative(object, new ToNativeContext());
    }

    private static Object fromNative(FromNativeConverter fromNativeConverter, Object object, Method method) {
        return fromNativeConverter.fromNative(object, new MethodResultContext(method.getReturnType(), null, null, method));
    }

    public static native long ffi_prep_cif(int var0, int var1, long var2, long var4);

    public static native void ffi_call(long var0, long var2, long var4, long var6);

    public static native long ffi_prep_closure(long var0, ffi_callback var2);

    public static native void ffi_free_closure(long var0);

    static native int initialize_ffi_type(long var0);

    public static void main(String[] stringArray) {
        String string;
        String string2;
        String string3 = "Java Native Access (JNA)";
        String string4 = "4.4.0";
        String string5 = "4.4.0 (package information missing)";
        Package package_ = Native.class.getPackage();
        String string6 = string2 = package_ != null ? package_.getSpecificationTitle() : "Java Native Access (JNA)";
        if (string2 == null) {
            string2 = "Java Native Access (JNA)";
        }
        String string7 = string = package_ != null ? package_.getSpecificationVersion() : "4.4.0";
        if (string == null) {
            string = "4.4.0";
        }
        string2 = string2 + " API Version " + string;
        System.out.println(string2);
        String string8 = string = package_ != null ? package_.getImplementationVersion() : "4.4.0 (package information missing)";
        if (string == null) {
            string = "4.4.0 (package information missing)";
        }
        System.out.println("Version: " + string);
        System.out.println(" Native: " + Native.getNativeVersion() + " (" + Native.getAPIChecksum() + ")");
        System.out.println(" Prefix: " + Platform.RESOURCE_PREFIX);
    }

    static synchronized native void freeNativeCallback(long var0);

    static synchronized native long createNativeCallback(Callback var0, Method var1, Class<?>[] var2, Class<?> var3, int var4, int var5, String var6);

    static native int invokeInt(Function var0, long var1, int var3, Object[] var4);

    static native long invokeLong(Function var0, long var1, int var3, Object[] var4);

    static native void invokeVoid(Function var0, long var1, int var3, Object[] var4);

    static native float invokeFloat(Function var0, long var1, int var3, Object[] var4);

    static native double invokeDouble(Function var0, long var1, int var3, Object[] var4);

    static native long invokePointer(Function var0, long var1, int var3, Object[] var4);

    private static native void invokeStructure(Function var0, long var1, int var3, Object[] var4, long var5, long var7);

    static Structure invokeStructure(Function function, long l, int n, Object[] objectArray, Structure structure) {
        Native.invokeStructure(function, l, n, objectArray, structure.getPointer().peer, structure.getTypeInfo().peer);
        return structure;
    }

    static native Object invokeObject(Function var0, long var1, int var3, Object[] var4);

    static long open(String string) {
        return Native.open(string, -1);
    }

    static native long open(String var0, int var1);

    static native void close(long var0);

    static native long findSymbol(long var0, String var2);

    static native long indexOf(Pointer var0, long var1, long var3, byte var5);

    static native void read(Pointer var0, long var1, long var3, byte[] var5, int var6, int var7);

    static native void read(Pointer var0, long var1, long var3, short[] var5, int var6, int var7);

    static native void read(Pointer var0, long var1, long var3, char[] var5, int var6, int var7);

    static native void read(Pointer var0, long var1, long var3, int[] var5, int var6, int var7);

    static native void read(Pointer var0, long var1, long var3, long[] var5, int var6, int var7);

    static native void read(Pointer var0, long var1, long var3, float[] var5, int var6, int var7);

    static native void read(Pointer var0, long var1, long var3, double[] var5, int var6, int var7);

    static native void write(Pointer var0, long var1, long var3, byte[] var5, int var6, int var7);

    static native void write(Pointer var0, long var1, long var3, short[] var5, int var6, int var7);

    static native void write(Pointer var0, long var1, long var3, char[] var5, int var6, int var7);

    static native void write(Pointer var0, long var1, long var3, int[] var5, int var6, int var7);

    static native void write(Pointer var0, long var1, long var3, long[] var5, int var6, int var7);

    static native void write(Pointer var0, long var1, long var3, float[] var5, int var6, int var7);

    static native void write(Pointer var0, long var1, long var3, double[] var5, int var6, int var7);

    static native byte getByte(Pointer var0, long var1, long var3);

    static native char getChar(Pointer var0, long var1, long var3);

    static native short getShort(Pointer var0, long var1, long var3);

    static native int getInt(Pointer var0, long var1, long var3);

    static native long getLong(Pointer var0, long var1, long var3);

    static native float getFloat(Pointer var0, long var1, long var3);

    static native double getDouble(Pointer var0, long var1, long var3);

    static Pointer getPointer(long l) {
        long l2 = Native._getPointer(l);
        return l2 == 0L ? null : new Pointer(l2);
    }

    private static native long _getPointer(long var0);

    static native String getWideString(Pointer var0, long var1, long var3);

    static String getString(Pointer pointer, long l) {
        return Native.getString(pointer, l, Native.getDefaultStringEncoding());
    }

    static String getString(Pointer pointer, long l, String string) {
        byte[] byArray = Native.getStringBytes(pointer, pointer.peer, l);
        if (string != null) {
            try {
                return new String(byArray, string);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
        return new String(byArray);
    }

    static native byte[] getStringBytes(Pointer var0, long var1, long var3);

    static native void setMemory(Pointer var0, long var1, long var3, long var5, byte var7);

    static native void setByte(Pointer var0, long var1, long var3, byte var5);

    static native void setShort(Pointer var0, long var1, long var3, short var5);

    static native void setChar(Pointer var0, long var1, long var3, char var5);

    static native void setInt(Pointer var0, long var1, long var3, int var5);

    static native void setLong(Pointer var0, long var1, long var3, long var5);

    static native void setFloat(Pointer var0, long var1, long var3, float var5);

    static native void setDouble(Pointer var0, long var1, long var3, double var5);

    static native void setPointer(Pointer var0, long var1, long var3, long var5);

    static native void setWideString(Pointer var0, long var1, long var3, String var5);

    static native ByteBuffer getDirectByteBuffer(Pointer var0, long var1, long var3, long var5);

    public static native long malloc(long var0);

    public static native void free(long var0);

    @Deprecated
    public static native ByteBuffer getDirectByteBuffer(long var0, long var2);

    public static void detach(boolean bl) {
        Thread thread2 = Thread.currentThread();
        if (bl) {
            nativeThreads.remove(thread2);
            Pointer pointer = nativeThreadTerminationFlag.get();
            Native.setDetachState(true, 0L);
        } else if (!nativeThreads.containsKey(thread2)) {
            Pointer pointer = nativeThreadTerminationFlag.get();
            nativeThreads.put(thread2, pointer);
            Native.setDetachState(false, pointer.peer);
        }
    }

    static Pointer getTerminationFlag(Thread thread2) {
        return nativeThreads.get(thread2);
    }

    private static native void setDetachState(boolean var0, long var1);

    static void access$000() {
        Native.dispose();
    }

    static {
        callbackExceptionHandler = DEFAULT_HANDLER = new Callback.UncaughtExceptionHandler(){

            @Override
            public void uncaughtException(Callback callback, Throwable throwable) {
                System.err.println("JNA: Callback " + callback + " threw the following exception:");
                throwable.printStackTrace();
            }
        };
        Native.loadNativeDispatchLibrary();
        if (!Native.isCompatibleVersion("5.1.0", Native.getNativeVersion())) {
            String string = System.getProperty("line.separator");
            throw new Error(string + string + "There is an incompatible JNA native library installed on this system" + string + "Expected: " + "5.1.0" + string + "Found:    " + Native.getNativeVersion() + string + (jnidispatchPath != null ? "(at " + jnidispatchPath + ")" : System.getProperty("java.library.path")) + "." + string + "To resolve this issue you may do one of the following:" + string + " - remove or uninstall the offending library" + string + " - set the system property jna.nosys=true" + string + " - set jna.boot.library.path to include the path to the version of the " + string + "   jnidispatch library included with the JNA jar file you are using" + string);
        }
        POINTER_SIZE = Native.sizeof(0);
        LONG_SIZE = Native.sizeof(1);
        WCHAR_SIZE = Native.sizeof(2);
        SIZE_T_SIZE = Native.sizeof(3);
        BOOL_SIZE = Native.sizeof(4);
        Native.initIDs();
        if (Boolean.getBoolean("jna.protected")) {
            Native.setProtected(true);
        }
        MAX_ALIGNMENT = Platform.isSPARC() || Platform.isWindows() || Platform.isLinux() && (Platform.isARM() || Platform.isPPC()) || Platform.isAIX() || Platform.isAndroid() ? 8 : LONG_SIZE;
        MAX_PADDING = Platform.isMac() && Platform.isPPC() ? 8 : MAX_ALIGNMENT;
        System.setProperty("jna.loaded", "true");
        finalizer = new Object(){

            protected void finalize() {
                Native.access$000();
            }
        };
        registeredClasses = new WeakHashMap();
        registeredLibraries = new WeakHashMap();
        nativeThreadTerminationFlag = new ThreadLocal<Memory>(){

            @Override
            protected Memory initialValue() {
                Memory memory = new Memory(4L);
                memory.clear();
                return memory;
            }

            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        nativeThreads = Collections.synchronizedMap(new WeakHashMap());
    }

    private static class AWT {
        private AWT() {
        }

        static long getWindowID(Window window) throws HeadlessException {
            return AWT.getComponentID(window);
        }

        static long getComponentID(Object object) throws HeadlessException {
            if (GraphicsEnvironment.isHeadless()) {
                throw new HeadlessException("No native windows when headless");
            }
            Component component = (Component)object;
            if (component.isLightweight()) {
                throw new IllegalArgumentException("Component must be heavyweight");
            }
            if (!component.isDisplayable()) {
                throw new IllegalStateException("Component must be displayable");
            }
            if (Platform.isX11() && System.getProperty("java.version").startsWith("1.4") && !component.isVisible()) {
                throw new IllegalStateException("Component must be visible");
            }
            return Native.getWindowHandle0(component);
        }
    }

    private static class Buffers {
        private Buffers() {
        }

        static boolean isBuffer(Class<?> clazz) {
            return Buffer.class.isAssignableFrom(clazz);
        }
    }

    public static interface ffi_callback {
        public void invoke(long var1, long var3, long var5);
    }
}

