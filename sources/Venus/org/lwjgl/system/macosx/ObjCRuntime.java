/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.macosx.EnumerationMutationHandlerI;
import org.lwjgl.system.macosx.ObjCMethodDescription;
import org.lwjgl.system.macosx.ObjCPropertyAttribute;

public class ObjCRuntime {
    public static final long nil = 0L;
    public static final byte YES = 1;
    public static final byte NO = 0;
    public static final char _C_ID = '@';
    public static final char _C_CLASS = '#';
    public static final char _C_SEL = ':';
    public static final char _C_CHR = 'c';
    public static final char _C_UCHR = 'C';
    public static final char _C_SHT = 's';
    public static final char _C_USHT = 'S';
    public static final char _C_INT = 'i';
    public static final char _C_UINT = 'I';
    public static final char _C_LNG = 'l';
    public static final char _C_ULNG = 'L';
    public static final char _C_LNG_LNG = 'q';
    public static final char _C_ULNG_LNG = 'Q';
    public static final char _C_FLT = 'f';
    public static final char _C_DBL = 'd';
    public static final char _C_BFLD = 'b';
    public static final char _C_BOOL = 'B';
    public static final char _C_VOID = 'v';
    public static final char _C_UNDEF = '?';
    public static final char _C_PTR = '^';
    public static final char _C_CHARPTR = '*';
    public static final char _C_ATOM = '%';
    public static final char _C_ARY_B = '[';
    public static final char _C_ARY_E = ']';
    public static final char _C_UNION_B = '(';
    public static final char _C_UNION_E = ')';
    public static final char _C_STRUCT_B = '{';
    public static final char _C_STRUCT_E = '}';
    public static final char _C_VECTOR = '!';
    public static final char _C_CONST = 'r';
    public static final int OBJC_ASSOCIATION_ASSIGN = 0;
    public static final int OBJC_ASSOCIATION_RETAIN_NONATOMIC = 1;
    public static final int OBJC_ASSOCIATION_COPY_NONATOMIC = 3;
    public static final int OBJC_ASSOCIATION_RETAIN = 1401;
    public static final int OBJC_ASSOCIATION_COPY = 1403;
    private static final SharedLibrary OBJC = Library.loadNative(ObjCRuntime.class, "objc");

    protected ObjCRuntime() {
        throw new UnsupportedOperationException();
    }

    public static SharedLibrary getLibrary() {
        return OBJC;
    }

    @NativeType(value="id")
    public static long object_copy(@NativeType(value="id") long l, @NativeType(value="size_t") long l2) {
        long l3 = Functions.object_copy;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="id")
    public static long object_dispose(@NativeType(value="id") long l) {
        long l2 = Functions.object_dispose;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Class")
    public static long object_getClass(@NativeType(value="id") long l) {
        long l2 = Functions.object_getClass;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Class")
    public static long object_setClass(@NativeType(value="id") long l, @NativeType(value="Class") long l2) {
        long l3 = Functions.object_setClass;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    public static long nobject_getClassName(long l) {
        long l2 = Functions.object_getClassName;
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String object_getClassName(@NativeType(value="id") long l) {
        long l2 = ObjCRuntime.nobject_getClassName(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    @NativeType(value="void *")
    public static long object_getIndexedIvars(@NativeType(value="id") long l) {
        long l2 = Functions.object_getIndexedIvars;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="id")
    public static long object_getIvar(@NativeType(value="id") long l, @NativeType(value="Ivar") long l2) {
        long l3 = Functions.object_getIvar;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    public static void object_setIvar(@NativeType(value="id") long l, @NativeType(value="Ivar") long l2, @NativeType(value="id") long l3) {
        long l4 = Functions.object_setIvar;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        JNI.invokePPPV(l, l2, l3, l4);
    }

    public static long nobject_setInstanceVariable(long l, long l2, long l3) {
        long l4 = Functions.object_setInstanceVariable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPPP(l, l2, l3, l4);
    }

    @NativeType(value="Ivar")
    public static long object_setInstanceVariable(@NativeType(value="id") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="void *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nobject_setInstanceVariable(l, MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Ivar")
    public static long object_setInstanceVariable(@NativeType(value="id") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="void *") ByteBuffer byteBuffer) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = ObjCRuntime.nobject_setInstanceVariable(l, l2, MemoryUtil.memAddress(byteBuffer));
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobject_getInstanceVariable(long l, long l2, long l3) {
        long l4 = Functions.object_getInstanceVariable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPPP(l, l2, l3, l4);
    }

    @NativeType(value="Ivar")
    public static long object_getInstanceVariable(@NativeType(value="id") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(pointerBuffer, 1);
        }
        return ObjCRuntime.nobject_getInstanceVariable(l, MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Ivar")
    public static long object_getInstanceVariable(@NativeType(value="id") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = ObjCRuntime.nobject_getInstanceVariable(l, l2, MemoryUtil.memAddress(pointerBuffer));
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobjc_getClass(long l) {
        long l2 = Functions.objc_getClass;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Class")
    public static long objc_getClass(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nobjc_getClass(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Class")
    public static long objc_getClass(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nobjc_getClass(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobjc_getMetaClass(long l) {
        long l2 = Functions.objc_getMetaClass;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Class")
    public static long objc_getMetaClass(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nobjc_getMetaClass(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Class")
    public static long objc_getMetaClass(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nobjc_getMetaClass(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobjc_lookUpClass(long l) {
        long l2 = Functions.objc_lookUpClass;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Class")
    public static long objc_lookUpClass(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nobjc_lookUpClass(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Class")
    public static long objc_lookUpClass(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nobjc_lookUpClass(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobjc_getRequiredClass(long l) {
        long l2 = Functions.objc_getRequiredClass;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Class")
    public static long objc_getRequiredClass(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nobjc_getRequiredClass(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Class")
    public static long objc_getRequiredClass(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nobjc_getRequiredClass(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static int nobjc_getClassList(long l, int n) {
        long l2 = Functions.objc_getClassList;
        return JNI.invokePI(l, n, l2);
    }

    public static int objc_getClassList(@Nullable @NativeType(value="Class *") PointerBuffer pointerBuffer) {
        return ObjCRuntime.nobjc_getClassList(MemoryUtil.memAddressSafe(pointerBuffer), Checks.remainingSafe(pointerBuffer));
    }

    public static long nobjc_copyClassList(long l) {
        long l2 = Functions.objc_copyClassList;
        return JNI.invokePP(l, l2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="Class *")
    public static PointerBuffer objc_copyClassList() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l = ObjCRuntime.nobjc_copyClassList(MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nclass_getName(long l) {
        long l2 = Functions.class_getName;
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String class_getName(@NativeType(value="Class") long l) {
        long l2 = ObjCRuntime.nclass_getName(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    @NativeType(value="BOOL")
    public static boolean class_isMetaClass(@NativeType(value="Class") long l) {
        long l2 = Functions.class_isMetaClass;
        return JNI.invokePZ(l, l2);
    }

    @NativeType(value="Class")
    public static long class_getSuperclass(@NativeType(value="Class") long l) {
        long l2 = Functions.class_getSuperclass;
        return JNI.invokePP(l, l2);
    }

    public static int class_getVersion(@NativeType(value="Class") long l) {
        long l2 = Functions.class_getVersion;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePI(l, l2);
    }

    public static void class_setVersion(@NativeType(value="Class") long l, int n) {
        long l2 = Functions.class_setVersion;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, n, l2);
    }

    @NativeType(value="size_t")
    public static long class_getInstanceSize(@NativeType(value="Class") long l) {
        long l2 = Functions.class_getInstanceSize;
        return JNI.invokePP(l, l2);
    }

    public static long nclass_getInstanceVariable(long l, long l2) {
        long l3 = Functions.class_getInstanceVariable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="Ivar")
    public static long class_getInstanceVariable(@NativeType(value="Class") long l, @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nclass_getInstanceVariable(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Ivar")
    public static long class_getInstanceVariable(@NativeType(value="Class") long l, @NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = ObjCRuntime.nclass_getInstanceVariable(l, l2);
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nclass_getClassVariable(long l, long l2) {
        long l3 = Functions.class_getClassVariable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="Ivar")
    public static long class_getClassVariable(@NativeType(value="Class") long l, @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nclass_getClassVariable(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Ivar")
    public static long class_getClassVariable(@NativeType(value="Class") long l, @NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = ObjCRuntime.nclass_getClassVariable(l, l2);
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nclass_copyIvarList(long l, long l2) {
        long l3 = Functions.class_copyIvarList;
        return JNI.invokePPP(l, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="Ivar *")
    public static PointerBuffer class_copyIvarList(@NativeType(value="Class") long l) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = ObjCRuntime.nclass_copyIvarList(l, MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="Method")
    public static long class_getInstanceMethod(@NativeType(value="Class") long l, @NativeType(value="SEL") long l2) {
        long l3 = Functions.class_getInstanceMethod;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="Method")
    public static long class_getClassMethod(@NativeType(value="Class") long l, @NativeType(value="SEL") long l2) {
        long l3 = Functions.class_getClassMethod;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="IMP")
    public static long class_getMethodImplementation(@NativeType(value="Class") long l, @NativeType(value="SEL") long l2) {
        long l3 = Functions.class_getMethodImplementation;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean class_respondsToSelector(@NativeType(value="Class") long l, @NativeType(value="SEL") long l2) {
        long l3 = Functions.class_respondsToSelector;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPZ(l, l2, l3);
    }

    public static long nclass_copyMethodList(long l, long l2) {
        long l3 = Functions.class_copyMethodList;
        return JNI.invokePPP(l, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="Method *")
    public static PointerBuffer class_copyMethodList(@NativeType(value="Class") long l) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = ObjCRuntime.nclass_copyMethodList(l, MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="BOOL")
    public static boolean class_conformsToProtocol(@NativeType(value="Class") long l, @NativeType(value="Protocol *") long l2) {
        long l3 = Functions.class_conformsToProtocol;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPZ(l, l2, l3);
    }

    public static long nclass_copyProtocolList(long l, long l2) {
        long l3 = Functions.class_copyProtocolList;
        return JNI.invokePPP(l, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="Protocol **")
    public static PointerBuffer class_copyProtocolList(@NativeType(value="Class") long l) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = ObjCRuntime.nclass_copyProtocolList(l, MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nclass_getProperty(long l, long l2) {
        long l3 = Functions.class_getProperty;
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="objc_property_t")
    public static long class_getProperty(@NativeType(value="Class") long l, @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nclass_getProperty(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="objc_property_t")
    public static long class_getProperty(@NativeType(value="Class") long l, @NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = ObjCRuntime.nclass_getProperty(l, l2);
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nclass_copyPropertyList(long l, long l2) {
        long l3 = Functions.class_copyPropertyList;
        return JNI.invokePPP(l, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="objc_property_t *")
    public static PointerBuffer class_copyPropertyList(@NativeType(value="Class") long l) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = ObjCRuntime.nclass_copyPropertyList(l, MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nclass_getIvarLayout(long l) {
        long l2 = Functions.class_getIvarLayout;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="uint8_t const *")
    public static String class_getIvarLayout(@NativeType(value="Class") long l) {
        long l2 = ObjCRuntime.nclass_getIvarLayout(l);
        return MemoryUtil.memASCIISafe(l2);
    }

    public static long nclass_getWeakIvarLayout(long l) {
        long l2 = Functions.class_getWeakIvarLayout;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="uint8_t const *")
    public static String class_getWeakIvarLayout(@NativeType(value="Class") long l) {
        long l2 = ObjCRuntime.nclass_getWeakIvarLayout(l);
        return MemoryUtil.memASCIISafe(l2);
    }

    public static boolean nclass_addMethod(long l, long l2, long l3, long l4) {
        long l5 = Functions.class_addMethod;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        return JNI.invokePPPPZ(l, l2, l3, l4, l5);
    }

    @NativeType(value="BOOL")
    public static boolean class_addMethod(@NativeType(value="Class") long l, @NativeType(value="SEL") long l2, @NativeType(value="IMP") long l3, @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nclass_addMethod(l, l2, l3, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="BOOL")
    public static boolean class_addMethod(@NativeType(value="Class") long l, @NativeType(value="SEL") long l2, @NativeType(value="IMP") long l3, @NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l4 = memoryStack.getPointerAddress();
            boolean bl = ObjCRuntime.nclass_addMethod(l, l2, l3, l4);
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nclass_replaceMethod(long l, long l2, long l3, long l4) {
        long l5 = Functions.class_replaceMethod;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        return JNI.invokePPPPP(l, l2, l3, l4, l5);
    }

    @NativeType(value="IMP")
    public static long class_replaceMethod(@NativeType(value="Class") long l, @NativeType(value="SEL") long l2, @NativeType(value="IMP") long l3, @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nclass_replaceMethod(l, l2, l3, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="IMP")
    public static long class_replaceMethod(@NativeType(value="Class") long l, @NativeType(value="SEL") long l2, @NativeType(value="IMP") long l3, @NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l4 = memoryStack.getPointerAddress();
            long l5 = ObjCRuntime.nclass_replaceMethod(l, l2, l3, l4);
            return l5;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static boolean nclass_addIvar(long l, long l2, long l3, byte by, long l4) {
        long l5 = Functions.class_addIvar;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPPPZ(l, l2, l3, by, l4, l5);
    }

    @NativeType(value="BOOL")
    public static boolean class_addIvar(@NativeType(value="Class") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="size_t") long l2, @NativeType(value="uint8_t") byte by, @NativeType(value="char const *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.checkNT1(byteBuffer2);
        }
        return ObjCRuntime.nclass_addIvar(l, MemoryUtil.memAddress(byteBuffer), l2, by, MemoryUtil.memAddress(byteBuffer2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="BOOL")
    public static boolean class_addIvar(@NativeType(value="Class") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="size_t") long l2, @NativeType(value="uint8_t") byte by, @NativeType(value="char const *") CharSequence charSequence2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l3 = memoryStack.getPointerAddress();
            memoryStack.nUTF8(charSequence2, false);
            long l4 = memoryStack.getPointerAddress();
            boolean bl = ObjCRuntime.nclass_addIvar(l, l3, l2, by, l4);
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="BOOL")
    public static boolean class_addProtocol(@NativeType(value="Class") long l, @NativeType(value="Protocol *") long l2) {
        long l3 = Functions.class_addProtocol;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPZ(l, l2, l3);
    }

    public static boolean nclass_addProperty(long l, long l2, long l3, int n) {
        long l4 = Functions.class_addProperty;
        if (Checks.CHECKS) {
            Checks.check(l);
            ObjCPropertyAttribute.validate(l3, n);
        }
        return JNI.invokePPPZ(l, l2, l3, n, l4);
    }

    @NativeType(value="BOOL")
    public static boolean class_addProperty(@NativeType(value="Class") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="objc_property_attribute_t const *") ObjCPropertyAttribute.Buffer buffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nclass_addProperty(l, MemoryUtil.memAddress(byteBuffer), buffer.address(), buffer.remaining());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="BOOL")
    public static boolean class_addProperty(@NativeType(value="Class") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="objc_property_attribute_t const *") ObjCPropertyAttribute.Buffer buffer) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            boolean bl = ObjCRuntime.nclass_addProperty(l, l2, buffer.address(), buffer.remaining());
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nclass_replaceProperty(long l, long l2, long l3, int n) {
        long l4 = Functions.class_replaceProperty;
        if (Checks.CHECKS) {
            Checks.check(l);
            ObjCPropertyAttribute.validate(l3, n);
        }
        JNI.invokePPPV(l, l2, l3, n, l4);
    }

    public static void class_replaceProperty(@NativeType(value="Class") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="objc_property_attribute_t const *") ObjCPropertyAttribute.Buffer buffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        ObjCRuntime.nclass_replaceProperty(l, MemoryUtil.memAddress(byteBuffer), buffer.address(), buffer.remaining());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void class_replaceProperty(@NativeType(value="Class") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="objc_property_attribute_t const *") ObjCPropertyAttribute.Buffer buffer) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            ObjCRuntime.nclass_replaceProperty(l, l2, buffer.address(), buffer.remaining());
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nclass_setIvarLayout(long l, long l2) {
        long l3 = Functions.class_setIvarLayout;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePPV(l, l2, l3);
    }

    public static void class_setIvarLayout(@NativeType(value="Class") long l, @NativeType(value="uint8_t const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        ObjCRuntime.nclass_setIvarLayout(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void class_setIvarLayout(@NativeType(value="Class") long l, @NativeType(value="uint8_t const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            ObjCRuntime.nclass_setIvarLayout(l, l2);
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nclass_setWeakIvarLayout(long l, long l2) {
        long l3 = Functions.class_setWeakIvarLayout;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePPV(l, l2, l3);
    }

    public static void class_setWeakIvarLayout(@NativeType(value="Class") long l, @NativeType(value="uint8_t const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        ObjCRuntime.nclass_setWeakIvarLayout(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void class_setWeakIvarLayout(@NativeType(value="Class") long l, @NativeType(value="uint8_t const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            ObjCRuntime.nclass_setWeakIvarLayout(l, l2);
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="id")
    public static long class_createInstance(@NativeType(value="Class") long l, @NativeType(value="size_t") long l2) {
        long l3 = Functions.class_createInstance;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    public static long nobjc_constructInstance(long l, long l2) {
        long l3 = Functions.objc_constructInstance;
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="id")
    public static long objc_constructInstance(@NativeType(value="Class") long l, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS && Checks.DEBUG) {
            Checks.checkSafe((Buffer)byteBuffer, ObjCRuntime.class_getInstanceSize(l));
        }
        return ObjCRuntime.nobjc_constructInstance(l, MemoryUtil.memAddressSafe(byteBuffer));
    }

    @NativeType(value="void *")
    public static long objc_destructInstance(@NativeType(value="id") long l) {
        long l2 = Functions.objc_destructInstance;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static long nobjc_allocateClassPair(long l, long l2, long l3) {
        long l4 = Functions.objc_allocateClassPair;
        return JNI.invokePPPP(l, l2, l3, l4);
    }

    @NativeType(value="Class")
    public static long objc_allocateClassPair(@NativeType(value="Class") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="size_t") long l2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nobjc_allocateClassPair(l, MemoryUtil.memAddress(byteBuffer), l2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Class")
    public static long objc_allocateClassPair(@NativeType(value="Class") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="size_t") long l2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l3 = memoryStack.getPointerAddress();
            long l4 = ObjCRuntime.nobjc_allocateClassPair(l, l3, l2);
            return l4;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void objc_registerClassPair(@NativeType(value="Class") long l) {
        long l2 = Functions.objc_registerClassPair;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    public static void objc_disposeClassPair(@NativeType(value="Class") long l) {
        long l2 = Functions.objc_disposeClassPair;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    @NativeType(value="SEL")
    public static long method_getName(@NativeType(value="Method") long l) {
        long l2 = Functions.method_getName;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="IMP")
    public static long method_getImplementation(@NativeType(value="Method") long l) {
        long l2 = Functions.method_getImplementation;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static long nmethod_getTypeEncoding(long l) {
        long l2 = Functions.method_getTypeEncoding;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String method_getTypeEncoding(@NativeType(value="Method") long l) {
        long l2 = ObjCRuntime.nmethod_getTypeEncoding(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    @NativeType(value="unsigned int")
    public static int method_getNumberOfArguments(@NativeType(value="Method") long l) {
        long l2 = Functions.method_getNumberOfArguments;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePI(l, l2);
    }

    public static long nmethod_copyReturnType(long l) {
        long l2 = Functions.method_copyReturnType;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char *")
    public static String method_copyReturnType(@NativeType(value="Method") long l) {
        long l2 = ObjCRuntime.nmethod_copyReturnType(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static long nmethod_copyArgumentType(long l, int n) {
        long l2 = Functions.method_copyArgumentType;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, n, l2);
    }

    @Nullable
    @NativeType(value="char *")
    public static String method_copyArgumentType(@NativeType(value="Method") long l, @NativeType(value="unsigned int") int n) {
        long l2 = ObjCRuntime.nmethod_copyArgumentType(l, n);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static void nmethod_getReturnType(long l, long l2, long l3) {
        long l4 = Functions.method_getReturnType;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePPPV(l, l2, l3, l4);
    }

    public static void method_getReturnType(@NativeType(value="Method") long l, @NativeType(value="char *") ByteBuffer byteBuffer) {
        ObjCRuntime.nmethod_getReturnType(l, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String method_getReturnType(@NativeType(value="Method") long l, @NativeType(value="size_t") long l2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            ByteBuffer byteBuffer = memoryStack.malloc((int)l2);
            ObjCRuntime.nmethod_getReturnType(l, MemoryUtil.memAddress(byteBuffer), l2);
            String string = MemoryUtil.memUTF8(MemoryUtil.memByteBufferNT1(MemoryUtil.memAddress(byteBuffer), (int)l2));
            return string;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nmethod_getArgumentType(long l, int n, long l2, long l3) {
        long l4 = Functions.method_getArgumentType;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePPPV(l, n, l2, l3, l4);
    }

    public static void method_getArgumentType(@NativeType(value="Method") long l, @NativeType(value="unsigned int") int n, @NativeType(value="char *") ByteBuffer byteBuffer) {
        ObjCRuntime.nmethod_getArgumentType(l, n, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String method_getArgumentType(@NativeType(value="Method") long l, @NativeType(value="unsigned int") int n, @NativeType(value="size_t") long l2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            ByteBuffer byteBuffer = memoryStack.malloc((int)l2);
            ObjCRuntime.nmethod_getArgumentType(l, n, MemoryUtil.memAddress(byteBuffer), l2);
            String string = MemoryUtil.memUTF8(MemoryUtil.memByteBufferNT1(MemoryUtil.memAddress(byteBuffer), (int)l2));
            return string;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="IMP")
    public static long method_setImplementation(@NativeType(value="Method") long l, @NativeType(value="IMP") long l2) {
        long l3 = Functions.method_setImplementation;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    public static void method_exchangeImplementations(@NativeType(value="Method") long l, @NativeType(value="Method") long l2) {
        long l3 = Functions.method_exchangeImplementations;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.invokePPV(l, l2, l3);
    }

    public static long nivar_getName(long l) {
        long l2 = Functions.ivar_getName;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String ivar_getName(@NativeType(value="Ivar") long l) {
        long l2 = ObjCRuntime.nivar_getName(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static long nivar_getTypeEncoding(long l) {
        long l2 = Functions.ivar_getTypeEncoding;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String ivar_getTypeEncoding(@NativeType(value="Ivar") long l) {
        long l2 = ObjCRuntime.nivar_getTypeEncoding(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    @NativeType(value="ptrdiff_t")
    public static long ivar_getOffset(@NativeType(value="Ivar") long l) {
        long l2 = Functions.ivar_getOffset;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static long nproperty_getName(long l) {
        long l2 = Functions.property_getName;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String property_getName(@NativeType(value="objc_property_t") long l) {
        long l2 = ObjCRuntime.nproperty_getName(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static long nproperty_getAttributes(long l) {
        long l2 = Functions.property_getAttributes;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String property_getAttributes(@NativeType(value="objc_property_t") long l) {
        long l2 = ObjCRuntime.nproperty_getAttributes(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static long nproperty_copyAttributeList(long l, long l2) {
        long l3 = Functions.property_copyAttributeList;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="objc_property_attribute_t *")
    public static ObjCPropertyAttribute.Buffer property_copyAttributeList(@NativeType(value="objc_property_t") long l) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = ObjCRuntime.nproperty_copyAttributeList(l, MemoryUtil.memAddress(intBuffer));
            ObjCPropertyAttribute.Buffer buffer = ObjCPropertyAttribute.createSafe(l2, intBuffer.get(0));
            return buffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nproperty_copyAttributeValue(long l, long l2) {
        long l3 = Functions.property_copyAttributeValue;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @Nullable
    @NativeType(value="char *")
    public static String property_copyAttributeValue(@NativeType(value="objc_property_t") long l, @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        long l2 = ObjCRuntime.nproperty_copyAttributeValue(l, MemoryUtil.memAddress(byteBuffer));
        return MemoryUtil.memUTF8Safe(l2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char *")
    public static String property_copyAttributeValue(@NativeType(value="objc_property_t") long l, @NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = ObjCRuntime.nproperty_copyAttributeValue(l, l2);
            String string = MemoryUtil.memUTF8Safe(l3);
            return string;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobjc_getProtocol(long l) {
        long l2 = Functions.objc_getProtocol;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Protocol *")
    public static long objc_getProtocol(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nobjc_getProtocol(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Protocol *")
    public static long objc_getProtocol(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nobjc_getProtocol(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobjc_copyProtocolList(long l) {
        long l2 = Functions.objc_copyProtocolList;
        return JNI.invokePP(l, l2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="Protocol **")
    public static PointerBuffer objc_copyProtocolList() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l = ObjCRuntime.nobjc_copyProtocolList(MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="BOOL")
    public static boolean protocol_conformsToProtocol(@NativeType(value="Protocol *") long l, @NativeType(value="Protocol *") long l2) {
        long l3 = Functions.protocol_conformsToProtocol;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPZ(l, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean protocol_isEqual(@NativeType(value="Protocol *") long l, @NativeType(value="Protocol *") long l2) {
        long l3 = Functions.protocol_isEqual;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPZ(l, l2, l3);
    }

    public static long nprotocol_getName(long l) {
        long l2 = Functions.protocol_getName;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String protocol_getName(@NativeType(value="Protocol *") long l) {
        long l2 = ObjCRuntime.nprotocol_getName(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static native void nprotocol_getMethodDescription(long var0, long var2, boolean var4, boolean var5, long var6, long var8);

    public static void nprotocol_getMethodDescription(long l, long l2, boolean bl, boolean bl2, long l3) {
        long l4 = Functions.protocol_getMethodDescription;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        ObjCRuntime.nprotocol_getMethodDescription(l, l2, bl, bl2, l4, l3);
    }

    @NativeType(value="struct objc_method_description")
    public static ObjCMethodDescription protocol_getMethodDescription(@NativeType(value="Protocol *") long l, @NativeType(value="SEL") long l2, @NativeType(value="BOOL") boolean bl, @NativeType(value="BOOL") boolean bl2, @NativeType(value="struct objc_method_description") ObjCMethodDescription objCMethodDescription) {
        ObjCRuntime.nprotocol_getMethodDescription(l, l2, bl, bl2, objCMethodDescription.address());
        return objCMethodDescription;
    }

    public static long nprotocol_copyMethodDescriptionList(long l, boolean bl, boolean bl2, long l2) {
        long l3 = Functions.protocol_copyMethodDescriptionList;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, bl, bl2, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="struct objc_method_description *")
    public static ObjCMethodDescription.Buffer protocol_copyMethodDescriptionList(@NativeType(value="Protocol *") long l, @NativeType(value="BOOL") boolean bl, @NativeType(value="BOOL") boolean bl2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = ObjCRuntime.nprotocol_copyMethodDescriptionList(l, bl, bl2, MemoryUtil.memAddress(intBuffer));
            ObjCMethodDescription.Buffer buffer = ObjCMethodDescription.createSafe(l2, intBuffer.get(0));
            return buffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nprotocol_getProperty(long l, long l2, boolean bl, boolean bl2) {
        long l3 = Functions.protocol_getProperty;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, bl, bl2, l3);
    }

    @NativeType(value="objc_property_t")
    public static long protocol_getProperty(@NativeType(value="Protocol *") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="BOOL") boolean bl, @NativeType(value="BOOL") boolean bl2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nprotocol_getProperty(l, MemoryUtil.memAddress(byteBuffer), bl, bl2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="objc_property_t")
    public static long protocol_getProperty(@NativeType(value="Protocol *") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="BOOL") boolean bl, @NativeType(value="BOOL") boolean bl2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = ObjCRuntime.nprotocol_getProperty(l, l2, bl, bl2);
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nprotocol_copyPropertyList(long l, long l2) {
        long l3 = Functions.protocol_copyPropertyList;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="objc_property_t *")
    public static PointerBuffer protocol_copyPropertyList(@NativeType(value="Protocol *") long l) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = ObjCRuntime.nprotocol_copyPropertyList(l, MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nprotocol_copyProtocolList(long l, long l2) {
        long l3 = Functions.protocol_copyProtocolList;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="Protocol **")
    public static PointerBuffer protocol_copyProtocolList(@NativeType(value="Protocol *") long l) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = ObjCRuntime.nprotocol_copyProtocolList(l, MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobjc_allocateProtocol(long l) {
        long l2 = Functions.objc_allocateProtocol;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Protocol *")
    public static long objc_allocateProtocol(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nobjc_allocateProtocol(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="Protocol *")
    public static long objc_allocateProtocol(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nobjc_allocateProtocol(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void objc_registerProtocol(@NativeType(value="Protocol *") long l) {
        long l2 = Functions.objc_registerProtocol;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    public static void nprotocol_addMethodDescription(long l, long l2, long l3, boolean bl, boolean bl2) {
        long l4 = Functions.protocol_addMethodDescription;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.invokePPPV(l, l2, l3, bl, bl2, l4);
    }

    public static void protocol_addMethodDescription(@NativeType(value="Protocol *") long l, @NativeType(value="SEL") long l2, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="BOOL") boolean bl, @NativeType(value="BOOL") boolean bl2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        ObjCRuntime.nprotocol_addMethodDescription(l, l2, MemoryUtil.memAddress(byteBuffer), bl, bl2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void protocol_addMethodDescription(@NativeType(value="Protocol *") long l, @NativeType(value="SEL") long l2, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="BOOL") boolean bl, @NativeType(value="BOOL") boolean bl2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l3 = memoryStack.getPointerAddress();
            ObjCRuntime.nprotocol_addMethodDescription(l, l2, l3, bl, bl2);
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void protocol_addProtocol(@NativeType(value="Protocol *") long l, @NativeType(value="Protocol *") long l2) {
        long l3 = Functions.protocol_addProtocol;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.invokePPV(l, l2, l3);
    }

    public static void nprotocol_addProperty(long l, long l2, long l3, int n, boolean bl, boolean bl2) {
        long l4 = Functions.protocol_addProperty;
        if (Checks.CHECKS) {
            Checks.check(l);
            ObjCPropertyAttribute.validate(l3, n);
        }
        JNI.invokePPPV(l, l2, l3, n, bl, bl2, l4);
    }

    public static void protocol_addProperty(@NativeType(value="Protocol *") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="objc_property_attribute_t const *") ObjCPropertyAttribute.Buffer buffer, @NativeType(value="BOOL") boolean bl, @NativeType(value="BOOL") boolean bl2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        ObjCRuntime.nprotocol_addProperty(l, MemoryUtil.memAddress(byteBuffer), buffer.address(), buffer.remaining(), bl, bl2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void protocol_addProperty(@NativeType(value="Protocol *") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="objc_property_attribute_t const *") ObjCPropertyAttribute.Buffer buffer, @NativeType(value="BOOL") boolean bl, @NativeType(value="BOOL") boolean bl2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            ObjCRuntime.nprotocol_addProperty(l, l2, buffer.address(), buffer.remaining(), bl, bl2);
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nobjc_copyImageNames(long l) {
        long l2 = Functions.objc_copyImageNames;
        return JNI.invokePP(l, l2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const **")
    public static PointerBuffer objc_copyImageNames() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l = ObjCRuntime.nobjc_copyImageNames(MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nclass_getImageName(long l) {
        long l2 = Functions.class_getImageName;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String class_getImageName(@NativeType(value="Class") long l) {
        long l2 = ObjCRuntime.nclass_getImageName(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static long nobjc_copyClassNamesForImage(long l, long l2) {
        long l3 = Functions.objc_copyClassNamesForImage;
        return JNI.invokePPP(l, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const **")
    public static PointerBuffer objc_copyClassNamesForImage(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l = ObjCRuntime.nobjc_copyClassNamesForImage(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const **")
    public static PointerBuffer objc_copyClassNamesForImage(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nobjc_copyClassNamesForImage(l, MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nsel_getName(long l) {
        long l2 = Functions.sel_getName;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String sel_getName(@NativeType(value="SEL") long l) {
        long l2 = ObjCRuntime.nsel_getName(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static long nsel_getUid(long l) {
        long l2 = Functions.sel_getUid;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="SEL")
    public static long sel_getUid(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nsel_getUid(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="SEL")
    public static long sel_getUid(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nsel_getUid(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nsel_registerName(long l) {
        long l2 = Functions.sel_registerName;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="SEL")
    public static long sel_registerName(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ObjCRuntime.nsel_registerName(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="SEL")
    public static long sel_registerName(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = ObjCRuntime.nsel_registerName(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="BOOL")
    public static boolean sel_isEqual(@NativeType(value="SEL") long l, @NativeType(value="SEL") long l2) {
        long l3 = Functions.sel_isEqual;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPZ(l, l2, l3);
    }

    public static void objc_enumerationMutation(@NativeType(value="id") long l) {
        long l2 = Functions.objc_enumerationMutation;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    public static void nobjc_setEnumerationMutationHandler(long l) {
        long l2 = Functions.objc_setEnumerationMutationHandler;
        JNI.invokePV(l, l2);
    }

    public static void objc_setEnumerationMutationHandler(@NativeType(value="EnumerationMutationHandler") EnumerationMutationHandlerI enumerationMutationHandlerI) {
        ObjCRuntime.nobjc_setEnumerationMutationHandler(enumerationMutationHandlerI.address());
    }

    @NativeType(value="IMP")
    public static long imp_implementationWithBlock(@NativeType(value="id") long l) {
        long l2 = Functions.imp_implementationWithBlock;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="id")
    public static long imp_getBlock(@NativeType(value="IMP") long l) {
        long l2 = Functions.imp_getBlock;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="BOOL")
    public static boolean imp_removeBlock(@NativeType(value="IMP") long l) {
        long l2 = Functions.imp_removeBlock;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePZ(l, l2);
    }

    public static long nobjc_loadWeak(long l) {
        long l2 = Functions.objc_loadWeak;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="id")
    public static long objc_loadWeak(@Nullable @NativeType(value="id *") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe(pointerBuffer, 1);
        }
        return ObjCRuntime.nobjc_loadWeak(MemoryUtil.memAddressSafe(pointerBuffer));
    }

    public static long nobjc_storeWeak(long l, long l2) {
        long l3 = Functions.objc_storeWeak;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="id")
    public static long objc_storeWeak(@NativeType(value="id *") PointerBuffer pointerBuffer, @NativeType(value="id") long l) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        return ObjCRuntime.nobjc_storeWeak(MemoryUtil.memAddress(pointerBuffer), l);
    }

    public static void objc_setAssociatedObject(@NativeType(value="id") long l, @NativeType(value="void const *") long l2, @NativeType(value="id") long l3, @NativeType(value="objc_AssociationPolicy") long l4) {
        long l5 = Functions.objc_setAssociatedObject;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        JNI.invokePPPPV(l, l2, l3, l4, l5);
    }

    @NativeType(value="id")
    public static long objc_getAssociatedObject(@NativeType(value="id") long l, @NativeType(value="void const *") long l2) {
        long l3 = Functions.objc_getAssociatedObject;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    public static void objc_removeAssociatedObjects(@NativeType(value="id") long l) {
        long l2 = Functions.objc_removeAssociatedObjects;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    static SharedLibrary access$000() {
        return OBJC;
    }

    public static final class Functions {
        public static final long object_copy = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_copy");
        public static final long object_dispose = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_dispose");
        public static final long object_getClass = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_getClass");
        public static final long object_setClass = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_setClass");
        public static final long object_getClassName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_getClassName");
        public static final long object_getIndexedIvars = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_getIndexedIvars");
        public static final long object_getIvar = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_getIvar");
        public static final long object_setIvar = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_setIvar");
        public static final long object_setInstanceVariable = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_setInstanceVariable");
        public static final long object_getInstanceVariable = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "object_getInstanceVariable");
        public static final long objc_getClass = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_getClass");
        public static final long objc_getMetaClass = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_getMetaClass");
        public static final long objc_lookUpClass = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_lookUpClass");
        public static final long objc_getRequiredClass = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_getRequiredClass");
        public static final long objc_getClassList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_getClassList");
        public static final long objc_copyClassList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_copyClassList");
        public static final long class_getName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getName");
        public static final long class_isMetaClass = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_isMetaClass");
        public static final long class_getSuperclass = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getSuperclass");
        public static final long class_getVersion = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getVersion");
        public static final long class_setVersion = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_setVersion");
        public static final long class_getInstanceSize = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getInstanceSize");
        public static final long class_getInstanceVariable = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getInstanceVariable");
        public static final long class_getClassVariable = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getClassVariable");
        public static final long class_copyIvarList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_copyIvarList");
        public static final long class_getInstanceMethod = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getInstanceMethod");
        public static final long class_getClassMethod = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getClassMethod");
        public static final long class_getMethodImplementation = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getMethodImplementation");
        public static final long class_respondsToSelector = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_respondsToSelector");
        public static final long class_copyMethodList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_copyMethodList");
        public static final long class_conformsToProtocol = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_conformsToProtocol");
        public static final long class_copyProtocolList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_copyProtocolList");
        public static final long class_getProperty = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getProperty");
        public static final long class_copyPropertyList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_copyPropertyList");
        public static final long class_getIvarLayout = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getIvarLayout");
        public static final long class_getWeakIvarLayout = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getWeakIvarLayout");
        public static final long class_addMethod = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_addMethod");
        public static final long class_replaceMethod = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_replaceMethod");
        public static final long class_addIvar = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_addIvar");
        public static final long class_addProtocol = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_addProtocol");
        public static final long class_addProperty = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_addProperty");
        public static final long class_replaceProperty = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_replaceProperty");
        public static final long class_setIvarLayout = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_setIvarLayout");
        public static final long class_setWeakIvarLayout = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_setWeakIvarLayout");
        public static final long class_createInstance = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_createInstance");
        public static final long objc_constructInstance = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_constructInstance");
        public static final long objc_destructInstance = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_destructInstance");
        public static final long objc_allocateClassPair = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_allocateClassPair");
        public static final long objc_registerClassPair = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_registerClassPair");
        public static final long objc_disposeClassPair = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_disposeClassPair");
        public static final long method_getName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_getName");
        public static final long method_getImplementation = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_getImplementation");
        public static final long method_getTypeEncoding = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_getTypeEncoding");
        public static final long method_getNumberOfArguments = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_getNumberOfArguments");
        public static final long method_copyReturnType = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_copyReturnType");
        public static final long method_copyArgumentType = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_copyArgumentType");
        public static final long method_getReturnType = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_getReturnType");
        public static final long method_getArgumentType = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_getArgumentType");
        public static final long method_setImplementation = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_setImplementation");
        public static final long method_exchangeImplementations = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "method_exchangeImplementations");
        public static final long ivar_getName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "ivar_getName");
        public static final long ivar_getTypeEncoding = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "ivar_getTypeEncoding");
        public static final long ivar_getOffset = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "ivar_getOffset");
        public static final long property_getName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "property_getName");
        public static final long property_getAttributes = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "property_getAttributes");
        public static final long property_copyAttributeList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "property_copyAttributeList");
        public static final long property_copyAttributeValue = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "property_copyAttributeValue");
        public static final long objc_getProtocol = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_getProtocol");
        public static final long objc_copyProtocolList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_copyProtocolList");
        public static final long protocol_conformsToProtocol = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_conformsToProtocol");
        public static final long protocol_isEqual = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_isEqual");
        public static final long protocol_getName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_getName");
        public static final long protocol_getMethodDescription = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_getMethodDescription");
        public static final long protocol_copyMethodDescriptionList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_copyMethodDescriptionList");
        public static final long protocol_getProperty = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_getProperty");
        public static final long protocol_copyPropertyList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_copyPropertyList");
        public static final long protocol_copyProtocolList = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_copyProtocolList");
        public static final long objc_allocateProtocol = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_allocateProtocol");
        public static final long objc_registerProtocol = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_registerProtocol");
        public static final long protocol_addMethodDescription = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_addMethodDescription");
        public static final long protocol_addProtocol = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_addProtocol");
        public static final long protocol_addProperty = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "protocol_addProperty");
        public static final long objc_copyImageNames = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_copyImageNames");
        public static final long class_getImageName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "class_getImageName");
        public static final long objc_copyClassNamesForImage = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_copyClassNamesForImage");
        public static final long sel_getName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "sel_getName");
        public static final long sel_getUid = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "sel_getUid");
        public static final long sel_registerName = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "sel_registerName");
        public static final long sel_isEqual = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "sel_isEqual");
        public static final long objc_enumerationMutation = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_enumerationMutation");
        public static final long objc_setEnumerationMutationHandler = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_setEnumerationMutationHandler");
        public static final long imp_implementationWithBlock = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "imp_implementationWithBlock");
        public static final long imp_getBlock = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "imp_getBlock");
        public static final long imp_removeBlock = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "imp_removeBlock");
        public static final long objc_loadWeak = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_loadWeak");
        public static final long objc_storeWeak = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_storeWeak");
        public static final long objc_setAssociatedObject = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_setAssociatedObject");
        public static final long objc_getAssociatedObject = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_getAssociatedObject");
        public static final long objc_removeAssociatedObjects = APIUtil.apiGetFunctionAddress(ObjCRuntime.access$000(), "objc_removeAssociatedObjects");

        private Functions() {
        }
    }
}

