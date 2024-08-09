/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.linux.LinuxLibrary;
import org.lwjgl.system.macosx.MacOSXLibrary;
import org.lwjgl.system.windows.WindowsLibrary;

public final class APIUtil {
    public static final PrintStream DEBUG_STREAM = APIUtil.getDebugStream();

    private static PrintStream getDebugStream() {
        PrintStream printStream = System.err;
        Object object = Configuration.DEBUG_STREAM.get();
        if (object instanceof String) {
            try {
                Supplier supplier = (Supplier)Class.forName((String)object).getConstructor(new Class[0]).newInstance(new Object[0]);
                printStream = (PrintStream)supplier.get();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (object instanceof Supplier) {
            printStream = (PrintStream)((Supplier)object).get();
        } else if (object instanceof PrintStream) {
            printStream = (PrintStream)object;
        }
        return printStream;
    }

    private APIUtil() {
    }

    public static void apiLog(@Nullable CharSequence charSequence) {
        if (Checks.DEBUG) {
            DEBUG_STREAM.print("[LWJGL] ");
            DEBUG_STREAM.println(charSequence);
        }
    }

    public static Optional<String> apiGetManifestValue(String string) {
        String string2;
        URL uRL = APIUtil.class.getClassLoader().getResource("org/lwjgl/system/APIUtil.class");
        if (uRL != null && (string2 = uRL.toString()).startsWith("jar:")) {
            Optional<String> optional;
            block9: {
                InputStream inputStream = new URL(string2.substring(0, string2.lastIndexOf(33) + 1) + '/' + "META-INF/MANIFEST.MF").openStream();
                Throwable throwable = null;
                try {
                    optional = Optional.ofNullable(new Manifest(inputStream).getMainAttributes().getValue(string));
                    if (inputStream == null) break block9;
                } catch (Throwable throwable2) {
                    try {
                        try {
                            throwable = throwable2;
                            throw throwable2;
                        } catch (Throwable throwable3) {
                            if (inputStream != null) {
                                APIUtil.$closeResource(throwable, inputStream);
                            }
                            throw throwable3;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace(DEBUG_STREAM);
                    }
                }
                APIUtil.$closeResource(throwable, inputStream);
            }
            return optional;
        }
        return Optional.empty();
    }

    public static String apiFindLibrary(String string, String string2) {
        String string3;
        block8: {
            String string4 = Platform.get().mapLibraryName(string2);
            Stream<Path> stream = Files.find(Paths.get(string, new String[0]).toAbsolutePath(), Integer.MAX_VALUE, (arg_0, arg_1) -> APIUtil.lambda$apiFindLibrary$0(string4, arg_0, arg_1), new FileVisitOption[0]);
            Throwable throwable = null;
            try {
                string3 = stream.findFirst().map(Path::toString).orElse(string2);
                if (stream == null) break block8;
            } catch (Throwable throwable2) {
                try {
                    try {
                        throwable = throwable2;
                        throw throwable2;
                    } catch (Throwable throwable3) {
                        if (stream != null) {
                            APIUtil.$closeResource(throwable, stream);
                        }
                        throw throwable3;
                    }
                } catch (IOException iOException) {
                    return string2;
                }
            }
            APIUtil.$closeResource(throwable, stream);
        }
        return string3;
    }

    public static SharedLibrary apiCreateLibrary(String string) {
        switch (1.$SwitchMap$org$lwjgl$system$Platform[Platform.get().ordinal()]) {
            case 1: {
                return new WindowsLibrary(string);
            }
            case 2: {
                return new LinuxLibrary(string);
            }
            case 3: {
                return MacOSXLibrary.create(string);
            }
        }
        throw new IllegalStateException();
    }

    public static long apiGetFunctionAddress(FunctionProvider functionProvider, String string) {
        long l = functionProvider.getFunctionAddress(string);
        if (l == 0L) {
            APIUtil.requiredFunctionMissing(string);
        }
        return l;
    }

    private static void requiredFunctionMissing(String string) {
        if (!Configuration.DISABLE_FUNCTION_CHECKS.get(false).booleanValue()) {
            throw new NullPointerException("A required function is missing: " + string);
        }
    }

    @Nullable
    public static ByteBuffer apiGetMappedBuffer(@Nullable ByteBuffer byteBuffer, long l, int n) {
        if (byteBuffer != null && MemoryUtil.memAddress(byteBuffer) == l && byteBuffer.capacity() == n) {
            return byteBuffer;
        }
        return l == 0L ? null : MemoryUtil.wrap(MemoryUtil.BUFFER_BYTE, l, n).order(MemoryUtil.NATIVE_ORDER);
    }

    public static long apiGetBytes(int n, int n2) {
        return ((long)n & 0xFFFFFFFFL) << n2;
    }

    public static long apiCheckAllocation(int n, long l, long l2) {
        if (Checks.DEBUG) {
            if (n < 0) {
                throw new IllegalArgumentException("Invalid number of elements");
            }
            if (l2 + Long.MIN_VALUE < l + Long.MIN_VALUE) {
                throw new IllegalArgumentException("The request allocation is too large");
            }
        }
        return l;
    }

    @Nullable
    public static APIVersion apiParseVersion(Configuration<?> configuration) {
        Object obj = configuration.get();
        APIVersion aPIVersion = obj instanceof String ? APIUtil.apiParseVersion((String)obj, null) : (obj instanceof APIVersion ? (APIVersion)obj : null);
        return aPIVersion;
    }

    public static APIVersion apiParseVersion(String string) {
        return APIUtil.apiParseVersion(string, null);
    }

    public static APIVersion apiParseVersion(String string, @Nullable String string2) {
        Matcher matcher;
        String string3 = "([0-9]+)[.]([0-9]+)([.]\\S+)?\\s*(.+)?";
        if (string2 != null) {
            string3 = "(?:" + string2 + "\\s+)?" + string3;
        }
        if (!(matcher = Pattern.compile(string3).matcher(string)).matches()) {
            throw new IllegalArgumentException(String.format("Malformed API version string [%s]", string));
        }
        return new APIVersion(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3), matcher.group(4));
    }

    public static String apiUnknownToken(int n) {
        return APIUtil.apiUnknownToken("Unknown", n);
    }

    public static String apiUnknownToken(String string, int n) {
        return String.format("%s [0x%X]", string, n);
    }

    public static Map<Integer, String> apiClassTokens(@Nullable BiPredicate<Field, Integer> biPredicate, @Nullable Map<Integer, String> map, Class<?> ... classArray) {
        if (map == null) {
            map = new HashMap<Integer, String>(64);
        }
        int n = 25;
        for (Class<?> clazz : classArray) {
            if (clazz == null) continue;
            for (Field field : clazz.getDeclaredFields()) {
                if ((field.getModifiers() & n) != n || field.getType() != Integer.TYPE) continue;
                try {
                    Integer n2 = field.getInt(null);
                    if (biPredicate != null && !biPredicate.test(field, n2)) continue;
                    String string = map.get(n2);
                    map.put(n2, string == null ? field.getName() : string + "|" + field.getName());
                } catch (IllegalAccessException illegalAccessException) {
                    // empty catch block
                }
            }
        }
        return map;
    }

    public static long apiArray(MemoryStack memoryStack, long ... lArray) {
        PointerBuffer pointerBuffer = MemoryUtil.memPointerBuffer(memoryStack.nmalloc(Pointer.POINTER_SIZE, lArray.length << Pointer.POINTER_SHIFT), lArray.length);
        for (long l : lArray) {
            pointerBuffer.put(l);
        }
        return pointerBuffer.address;
    }

    public static long apiArray(MemoryStack memoryStack, ByteBuffer ... byteBufferArray) {
        PointerBuffer pointerBuffer = MemoryUtil.memPointerBuffer(memoryStack.nmalloc(Pointer.POINTER_SIZE, byteBufferArray.length << Pointer.POINTER_SHIFT), byteBufferArray.length);
        for (ByteBuffer byteBuffer : byteBufferArray) {
            pointerBuffer.put(byteBuffer);
        }
        return pointerBuffer.address;
    }

    public static long apiArrayp(MemoryStack memoryStack, ByteBuffer ... byteBufferArray) {
        long l = APIUtil.apiArray(memoryStack, byteBufferArray);
        PointerBuffer pointerBuffer = memoryStack.mallocPointer(byteBufferArray.length);
        for (ByteBuffer byteBuffer : byteBufferArray) {
            pointerBuffer.put(byteBuffer.remaining());
        }
        return l;
    }

    public static long apiArray(MemoryStack memoryStack, Encoder encoder, CharSequence ... charSequenceArray) {
        PointerBuffer pointerBuffer = memoryStack.mallocPointer(charSequenceArray.length);
        for (CharSequence charSequence : charSequenceArray) {
            pointerBuffer.put(encoder.encode(charSequence, true));
        }
        return pointerBuffer.address;
    }

    public static long apiArrayi(MemoryStack memoryStack, Encoder encoder, CharSequence ... charSequenceArray) {
        PointerBuffer pointerBuffer = memoryStack.mallocPointer(charSequenceArray.length);
        IntBuffer intBuffer = memoryStack.mallocInt(charSequenceArray.length);
        for (CharSequence charSequence : charSequenceArray) {
            ByteBuffer byteBuffer = encoder.encode(charSequence, false);
            pointerBuffer.put(byteBuffer);
            intBuffer.put(byteBuffer.capacity());
        }
        return pointerBuffer.address;
    }

    public static long apiArrayp(MemoryStack memoryStack, Encoder encoder, CharSequence ... charSequenceArray) {
        PointerBuffer pointerBuffer = memoryStack.mallocPointer(charSequenceArray.length);
        PointerBuffer pointerBuffer2 = memoryStack.mallocPointer(charSequenceArray.length);
        for (CharSequence charSequence : charSequenceArray) {
            ByteBuffer byteBuffer = encoder.encode(charSequence, false);
            pointerBuffer.put(byteBuffer);
            pointerBuffer2.put(byteBuffer.capacity());
        }
        return pointerBuffer.address;
    }

    public static void apiArrayFree(long l, int n) {
        int n2 = n;
        while (--n2 >= 0) {
            MemoryUtil.nmemFree(MemoryUtil.memGetAddress(l + Integer.toUnsignedLong(n2) * (long)Pointer.POINTER_SIZE));
        }
    }

    private static boolean lambda$apiFindLibrary$0(String string, Path path, BasicFileAttributes basicFileAttributes) {
        return basicFileAttributes.isRegularFile() && path.getFileName().toString().equals(string);
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

    public static interface Encoder {
        public ByteBuffer encode(CharSequence var1, boolean var2);
    }

    public static class APIVersion
    implements Comparable<APIVersion> {
        public final int major;
        public final int minor;
        @Nullable
        public final String revision;
        @Nullable
        public final String implementation;

        public APIVersion(int n, int n2) {
            this(n, n2, null, null);
        }

        public APIVersion(int n, int n2, @Nullable String string, @Nullable String string2) {
            this.major = n;
            this.minor = n2;
            this.revision = string;
            this.implementation = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(16);
            stringBuilder.append(this.major).append('.').append(this.minor);
            if (this.revision != null) {
                stringBuilder.append('.').append(this.revision);
            }
            if (this.implementation != null) {
                stringBuilder.append(" (").append(this.implementation).append(')');
            }
            return stringBuilder.toString();
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof APIVersion)) {
                return true;
            }
            APIVersion aPIVersion = (APIVersion)object;
            return this.major == aPIVersion.major && this.minor == aPIVersion.major && Objects.equals(this.revision, aPIVersion.revision) && Objects.equals(this.implementation, aPIVersion.implementation);
        }

        public int hashCode() {
            int n = this.major;
            n = 31 * n + this.minor;
            n = 31 * n + (this.revision != null ? this.revision.hashCode() : 0);
            n = 31 * n + (this.implementation != null ? this.implementation.hashCode() : 0);
            return n;
        }

        @Override
        public int compareTo(APIVersion aPIVersion) {
            if (this.major != aPIVersion.major) {
                return Integer.compare(this.major, aPIVersion.major);
            }
            if (this.minor != aPIVersion.minor) {
                return Integer.compare(this.minor, aPIVersion.minor);
            }
            return 1;
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((APIVersion)object);
        }
    }
}

