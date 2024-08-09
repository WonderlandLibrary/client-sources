/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.util.tinyfd;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Platform;

public class TinyFileDialogs {
    public static final String tinyfd_version;
    public static final String tinyfd_needs;
    public static final IntBuffer tinyfd_verbose;
    public static final IntBuffer tinyfd_silent;
    public static final IntBuffer tinyfd_forceConsole;

    protected TinyFileDialogs() {
        throw new UnsupportedOperationException();
    }

    private static native long ntinyfd_version();

    @NativeType(value="char *")
    private static String tinyfd_version() {
        long l = TinyFileDialogs.ntinyfd_version();
        return MemoryUtil.memASCII(l);
    }

    private static native long ntinyfd_needs();

    @NativeType(value="char *")
    private static String tinyfd_needs() {
        long l = TinyFileDialogs.ntinyfd_needs();
        return MemoryUtil.memASCII(l);
    }

    private static native long ntinyfd_verbose();

    @NativeType(value="int *")
    private static IntBuffer tinyfd_verbose() {
        long l = TinyFileDialogs.ntinyfd_verbose();
        return MemoryUtil.memIntBuffer(l, 1);
    }

    private static native long ntinyfd_silent();

    @NativeType(value="int *")
    private static IntBuffer tinyfd_silent() {
        long l = TinyFileDialogs.ntinyfd_silent();
        return MemoryUtil.memIntBuffer(l, 1);
    }

    private static native long ntinyfd_winUtf8();

    @NativeType(value="int *")
    private static IntBuffer tinyfd_winUtf8() {
        long l = TinyFileDialogs.ntinyfd_winUtf8();
        return MemoryUtil.memIntBuffer(l, 1);
    }

    private static native long ntinyfd_forceConsole();

    @NativeType(value="int *")
    private static IntBuffer tinyfd_forceConsole() {
        long l = TinyFileDialogs.ntinyfd_forceConsole();
        return MemoryUtil.memIntBuffer(l, 1);
    }

    public static native long ntinyfd_response();

    @NativeType(value="char *")
    public static String tinyfd_response() {
        long l = TinyFileDialogs.ntinyfd_response();
        return MemoryUtil.memUTF8(l);
    }

    public static native void tinyfd_beep();

    public static native int ntinyfd_notifyPopup(long var0, long var2, long var4);

    public static int tinyfd_notifyPopup(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer2, @NativeType(value="char const *") ByteBuffer byteBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
            Checks.checkNT1Safe(byteBuffer2);
            Checks.checkNT1(byteBuffer3);
        }
        return TinyFileDialogs.ntinyfd_notifyPopup(MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer2), MemoryUtil.memAddress(byteBuffer3));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int tinyfd_notifyPopup(@Nullable @NativeType(value="char const *") CharSequence charSequence, @Nullable @NativeType(value="char const *") CharSequence charSequence2, @NativeType(value="char const *") CharSequence charSequence3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8Safe(charSequence2, false);
            long l2 = charSequence2 == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nASCII(charSequence3, false);
            long l3 = memoryStack.getPointerAddress();
            int n2 = TinyFileDialogs.ntinyfd_notifyPopup(l, l2, l3);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int ntinyfd_messageBox(long var0, long var2, long var4, long var6, int var8);

    @NativeType(value="int")
    public static boolean tinyfd_messageBox(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer2, @NativeType(value="char const *") ByteBuffer byteBuffer3, @NativeType(value="char const *") ByteBuffer byteBuffer4, @NativeType(value="int") boolean bl) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
            Checks.checkNT1Safe(byteBuffer2);
            Checks.checkNT1(byteBuffer3);
            Checks.checkNT1(byteBuffer4);
        }
        return TinyFileDialogs.ntinyfd_messageBox(MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer2), MemoryUtil.memAddress(byteBuffer3), MemoryUtil.memAddress(byteBuffer4), bl ? 1 : 0) != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="int")
    public static boolean tinyfd_messageBox(@Nullable @NativeType(value="char const *") CharSequence charSequence, @Nullable @NativeType(value="char const *") CharSequence charSequence2, @NativeType(value="char const *") CharSequence charSequence3, @NativeType(value="char const *") CharSequence charSequence4, @NativeType(value="int") boolean bl) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8Safe(charSequence2, false);
            long l2 = charSequence2 == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nASCII(charSequence3, false);
            long l3 = memoryStack.getPointerAddress();
            memoryStack.nASCII(charSequence4, false);
            long l4 = memoryStack.getPointerAddress();
            boolean bl2 = TinyFileDialogs.ntinyfd_messageBox(l, l2, l3, l4, bl ? 1 : 0) != 0;
            return bl2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native long ntinyfd_inputBox(long var0, long var2, long var4);

    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_inputBox(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer2, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
            Checks.checkNT1Safe(byteBuffer2);
            Checks.checkNT1Safe(byteBuffer3);
        }
        long l = TinyFileDialogs.ntinyfd_inputBox(MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer2), MemoryUtil.memAddressSafe(byteBuffer3));
        return MemoryUtil.memUTF8Safe(l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_inputBox(@Nullable @NativeType(value="char const *") CharSequence charSequence, @Nullable @NativeType(value="char const *") CharSequence charSequence2, @Nullable @NativeType(value="char const *") CharSequence charSequence3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8Safe(charSequence2, false);
            long l2 = charSequence2 == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8Safe(charSequence3, false);
            long l3 = charSequence3 == null ? 0L : memoryStack.getPointerAddress();
            long l4 = TinyFileDialogs.ntinyfd_inputBox(l, l2, l3);
            String string = MemoryUtil.memUTF8Safe(l4);
            return string;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native long ntinyfd_saveFileDialog(long var0, long var2, int var4, long var5, long var7);

    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_saveFileDialog(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer2, @Nullable @NativeType(value="char const * const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
            Checks.checkNT1Safe(byteBuffer2);
            Checks.checkNT1Safe(byteBuffer3);
        }
        long l = TinyFileDialogs.ntinyfd_saveFileDialog(MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer2), Checks.remainingSafe(pointerBuffer), MemoryUtil.memAddressSafe(pointerBuffer), MemoryUtil.memAddressSafe(byteBuffer3));
        return MemoryUtil.memUTF8Safe(l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_saveFileDialog(@Nullable @NativeType(value="char const *") CharSequence charSequence, @Nullable @NativeType(value="char const *") CharSequence charSequence2, @Nullable @NativeType(value="char const * const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="char const *") CharSequence charSequence3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8Safe(charSequence2, false);
            long l2 = charSequence2 == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8Safe(charSequence3, false);
            long l3 = charSequence3 == null ? 0L : memoryStack.getPointerAddress();
            long l4 = TinyFileDialogs.ntinyfd_saveFileDialog(l, l2, Checks.remainingSafe(pointerBuffer), MemoryUtil.memAddressSafe(pointerBuffer), l3);
            String string = MemoryUtil.memUTF8Safe(l4);
            return string;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native long ntinyfd_openFileDialog(long var0, long var2, int var4, long var5, long var7, int var9);

    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_openFileDialog(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer2, @Nullable @NativeType(value="char const * const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer3, @NativeType(value="int") boolean bl) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
            Checks.checkNT1Safe(byteBuffer2);
            Checks.checkNT1Safe(byteBuffer3);
        }
        long l = TinyFileDialogs.ntinyfd_openFileDialog(MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer2), Checks.remainingSafe(pointerBuffer), MemoryUtil.memAddressSafe(pointerBuffer), MemoryUtil.memAddressSafe(byteBuffer3), bl ? 1 : 0);
        return MemoryUtil.memUTF8Safe(l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_openFileDialog(@Nullable @NativeType(value="char const *") CharSequence charSequence, @Nullable @NativeType(value="char const *") CharSequence charSequence2, @Nullable @NativeType(value="char const * const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="char const *") CharSequence charSequence3, @NativeType(value="int") boolean bl) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8Safe(charSequence2, false);
            long l2 = charSequence2 == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8Safe(charSequence3, false);
            long l3 = charSequence3 == null ? 0L : memoryStack.getPointerAddress();
            long l4 = TinyFileDialogs.ntinyfd_openFileDialog(l, l2, Checks.remainingSafe(pointerBuffer), MemoryUtil.memAddressSafe(pointerBuffer), l3, bl ? 1 : 0);
            String string = MemoryUtil.memUTF8Safe(l4);
            return string;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native long ntinyfd_selectFolderDialog(long var0, long var2);

    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_selectFolderDialog(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="char const *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
            Checks.checkNT1(byteBuffer2);
        }
        long l = TinyFileDialogs.ntinyfd_selectFolderDialog(MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddress(byteBuffer2));
        return MemoryUtil.memUTF8Safe(l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_selectFolderDialog(@Nullable @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="char const *") CharSequence charSequence2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nUTF8(charSequence2, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = TinyFileDialogs.ntinyfd_selectFolderDialog(l, l2);
            String string = MemoryUtil.memUTF8Safe(l3);
            return string;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native long ntinyfd_colorChooser(long var0, long var2, long var4, long var6);

    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_colorChooser(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer2, @Nullable @NativeType(value="unsigned char *") ByteBuffer byteBuffer3, @NativeType(value="unsigned char *") ByteBuffer byteBuffer4) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
            Checks.checkNT1Safe(byteBuffer2);
            Checks.checkSafe((Buffer)byteBuffer3, 3);
            Checks.check((Buffer)byteBuffer4, 3);
        }
        long l = TinyFileDialogs.ntinyfd_colorChooser(MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer2), MemoryUtil.memAddressSafe(byteBuffer3), MemoryUtil.memAddress(byteBuffer4));
        return MemoryUtil.memUTF8Safe(l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const *")
    public static String tinyfd_colorChooser(@Nullable @NativeType(value="char const *") CharSequence charSequence, @Nullable @NativeType(value="char const *") CharSequence charSequence2, @Nullable @NativeType(value="unsigned char *") ByteBuffer byteBuffer, @NativeType(value="unsigned char *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)byteBuffer, 3);
            Checks.check((Buffer)byteBuffer2, 3);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            memoryStack.nASCIISafe(charSequence2, false);
            long l2 = charSequence2 == null ? 0L : memoryStack.getPointerAddress();
            long l3 = TinyFileDialogs.ntinyfd_colorChooser(l, l2, MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddress(byteBuffer2));
            String string = MemoryUtil.memUTF8Safe(l3);
            return string;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    static {
        Library.loadSystem(System::load, System::loadLibrary, TinyFileDialogs.class, Platform.mapLibraryNameBundled("lwjgl_tinyfd"));
        TinyFileDialogs.tinyfd_winUtf8().put(0, 1);
        tinyfd_version = TinyFileDialogs.tinyfd_version();
        tinyfd_needs = TinyFileDialogs.tinyfd_needs();
        tinyfd_verbose = TinyFileDialogs.tinyfd_verbose();
        tinyfd_silent = TinyFileDialogs.tinyfd_silent();
        tinyfd_forceConsole = TinyFileDialogs.tinyfd_forceConsole();
    }
}

