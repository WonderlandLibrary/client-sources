/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\b\n\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u001f\u001a\u00020 \"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0014*\u0006\u0012\u0002\b\u00030!\u00a2\u0006\u0002\u0010\"\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\";\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t*\b\u0012\u0004\u0012\u0002H\b0\t8\u00c6\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"-\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G\u00a2\u0006\f\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"&\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\u0002H\u00028\u00c6\u0002\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0015\";\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018\u00c7\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0016\u0010\u0010\u001a\u0004\b\u0017\u0010\u0012\"+\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0012\"-\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0012\"+\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00078G\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006#"}, d2={"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "declaringJavaClass", "Ljava/lang/Class;", "E", "", "getDeclaringJavaClass$annotations", "(Ljava/lang/Enum;)V", "getDeclaringJavaClass", "(Ljava/lang/Enum;)Ljava/lang/Class;", "java", "getJavaClass$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "getRuntimeClassOfKClassInstance$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"})
@JvmName(name="JvmClassMappingKt")
public final class JvmClassMappingKt {
    @JvmName(name="getJavaClass")
    @NotNull
    public static final <T> Class<T> getJavaClass(@NotNull KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Class<?> clazz = ((ClassBasedDeclarationContainer)((Object)kClass)).getJClass();
        Intrinsics.checkNotNull(clazz, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-java>>");
        return clazz;
    }

    public static void getJavaClass$annotations(KClass kClass) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static final <T> Class<T> getJavaPrimitiveType(@NotNull KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Class<?> clazz = ((ClassBasedDeclarationContainer)((Object)kClass)).getJClass();
        if (clazz.isPrimitive()) {
            Intrinsics.checkNotNull(clazz, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaPrimitiveType>>");
            return clazz;
        }
        String string = clazz.getName();
        if (string == null) return null;
        int n = -1;
        switch (string.hashCode()) {
            case -527879800: {
                if (string.equals("java.lang.Float")) {
                    n = 1;
                }
                break;
            }
            case 399092968: {
                if (string.equals("java.lang.Void")) {
                    n = 2;
                }
                break;
            }
            case 155276373: {
                if (string.equals("java.lang.Character")) {
                    n = 3;
                }
                break;
            }
            case 398795216: {
                if (string.equals("java.lang.Long")) {
                    n = 4;
                }
                break;
            }
            case 761287205: {
                if (string.equals("java.lang.Double")) {
                    n = 5;
                }
                break;
            }
            case -515992664: {
                if (string.equals("java.lang.Short")) {
                    n = 6;
                }
                break;
            }
            case 344809556: {
                if (string.equals("java.lang.Boolean")) {
                    n = 7;
                }
                break;
            }
            case 398507100: {
                if (string.equals("java.lang.Byte")) {
                    n = 8;
                }
                break;
            }
            case -2056817302: {
                if (string.equals("java.lang.Integer")) {
                    n = 9;
                }
                break;
            }
        }
        switch (n) {
            case 7: {
                Class<Object> clazz2 = Boolean.TYPE;
                return clazz2;
            }
            case 3: {
                Class<Object> clazz2 = Character.TYPE;
                return clazz2;
            }
            case 8: {
                Class<Object> clazz2 = Byte.TYPE;
                return clazz2;
            }
            case 6: {
                Class<Object> clazz2 = Short.TYPE;
                return clazz2;
            }
            case 9: {
                Class<Object> clazz2 = Integer.TYPE;
                return clazz2;
            }
            case 1: {
                Class<Object> clazz2 = Float.TYPE;
                return clazz2;
            }
            case 4: {
                Class<Object> clazz2 = Long.TYPE;
                return clazz2;
            }
            case 5: {
                Class<Object> clazz2 = Double.TYPE;
                return clazz2;
            }
            case 2: {
                Class<Object> clazz2 = Void.TYPE;
                return clazz2;
            }
            default: {
                return null;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @NotNull
    public static final <T> Class<T> getJavaObjectType(@NotNull KClass<T> var0) {
        Intrinsics.checkNotNullParameter(var0, "<this>");
        var1_1 = ((ClassBasedDeclarationContainer)var0).getJClass();
        if (!var1_1.isPrimitive()) {
            Intrinsics.checkNotNull(var1_1, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>");
            return var1_1;
        }
        var2_2 = var1_1.getName();
        if (var2_2 == null) ** GOTO lbl-1000
        tmp = -1;
        switch (var2_2.hashCode()) {
            case 64711720: {
                if (var2_2.equals("boolean")) {
                    tmp = 1;
                }
                break;
            }
            case 3625364: {
                if (var2_2.equals("void")) {
                    tmp = 2;
                }
                break;
            }
            case 3039496: {
                if (var2_2.equals("byte")) {
                    tmp = 3;
                }
                break;
            }
            case -1325958191: {
                if (var2_2.equals("double")) {
                    tmp = 4;
                }
                break;
            }
            case 3052374: {
                if (var2_2.equals("char")) {
                    tmp = 5;
                }
                break;
            }
            case 109413500: {
                if (var2_2.equals("short")) {
                    tmp = 6;
                }
                break;
            }
            case 97526364: {
                if (var2_2.equals("float")) {
                    tmp = 7;
                }
                break;
            }
            case 104431: {
                if (var2_2.equals("int")) {
                    tmp = 8;
                }
                break;
            }
            case 3327612: {
                if (var2_2.equals("long")) {
                    tmp = 9;
                }
                break;
            }
        }
        switch (tmp) {
            case 1: {
                v0 /* !! */  = Boolean.class;
                break;
            }
            case 5: {
                v0 /* !! */  = Character.class;
                break;
            }
            case 3: {
                v0 /* !! */  = Byte.class;
                break;
            }
            case 6: {
                v0 /* !! */  = Short.class;
                break;
            }
            case 8: {
                v0 /* !! */  = Integer.class;
                break;
            }
            case 7: {
                v0 /* !! */  = Float.class;
                break;
            }
            case 9: {
                v0 /* !! */  = Long.class;
                break;
            }
            case 4: {
                v0 /* !! */  = Double.class;
                break;
            }
            case 2: {
                v0 /* !! */  = Void.class;
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v0 /* !! */  = var1_1;
            }
        }
        Intrinsics.checkNotNull(v0 /* !! */ , "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>");
        return v0 /* !! */ ;
    }

    @JvmName(name="getKotlinClass")
    @NotNull
    public static final <T> KClass<T> getKotlinClass(@NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "<this>");
        return Reflection.getOrCreateKotlinClass(clazz);
    }

    @NotNull
    public static final <T> Class<T> getJavaClass(@NotNull T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        boolean bl = false;
        Class<?> clazz = t.getClass();
        Intrinsics.checkNotNull(clazz, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaClass>>");
        return clazz;
    }

    @JvmName(name="getRuntimeClassOfKClassInstance")
    @NotNull
    public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(@NotNull KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        boolean bl = false;
        Class<KClass<T>> clazz = ((Object)kClass).getClass();
        Intrinsics.checkNotNull(clazz, "null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T of kotlin.jvm.JvmClassMappingKt.<get-javaClass>>>");
        return clazz;
    }

    @Deprecated(message="Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.", replaceWith=@ReplaceWith(expression="(this as Any).javaClass", imports={}), level=DeprecationLevel.ERROR)
    public static void getRuntimeClassOfKClassInstance$annotations(KClass kClass) {
    }

    public static final boolean isArrayOf(Object[] objectArray) {
        Intrinsics.checkNotNullParameter(objectArray, "<this>");
        Intrinsics.reifiedOperationMarker(4, "T");
        return Object.class.isAssignableFrom(objectArray.getClass().getComponentType());
    }

    @NotNull
    public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(@NotNull T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Class<? extends Annotation> clazz = t.annotationType();
        Intrinsics.checkNotNullExpressionValue(clazz, "this as java.lang.annota\u2026otation).annotationType()");
        KClass<? extends Annotation> kClass = JvmClassMappingKt.getKotlinClass(clazz);
        Intrinsics.checkNotNull(kClass, "null cannot be cast to non-null type kotlin.reflect.KClass<out T of kotlin.jvm.JvmClassMappingKt.<get-annotationClass>>");
        return kClass;
    }

    private static final <E extends Enum<E>> Class<E> getDeclaringJavaClass(Enum<E> enum_) {
        Intrinsics.checkNotNullParameter(enum_, "<this>");
        Class<E> clazz = enum_.getDeclaringClass();
        Intrinsics.checkNotNullExpressionValue(clazz, "this as java.lang.Enum<E>).declaringClass");
        return clazz;
    }

    @SinceKotlin(version="1.7")
    @InlineOnly
    public static void getDeclaringJavaClass$annotations(Enum enum_) {
    }
}

