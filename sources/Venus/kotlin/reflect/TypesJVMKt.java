/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.reflect;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.KTypeBase;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.GenericArrayTypeImpl;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;
import kotlin.reflect.ParameterizedTypeImpl;
import kotlin.reflect.TypeVariableImpl;
import kotlin.reflect.TypesJVMKt;
import kotlin.reflect.WildcardTypeImpl;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a\"\u0010\n\u001a\u00020\u00012\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0003\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0001H\u0002\u001a\u0016\u0010\u0012\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0003\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00078BX\u0083\u0004\u00a2\u0006\f\u0012\u0004\b\u0003\u0010\b\u001a\u0004\b\u0005\u0010\t\u00a8\u0006\u0015"}, d2={"javaType", "Ljava/lang/reflect/Type;", "Lkotlin/reflect/KType;", "getJavaType$annotations", "(Lkotlin/reflect/KType;)V", "getJavaType", "(Lkotlin/reflect/KType;)Ljava/lang/reflect/Type;", "Lkotlin/reflect/KTypeProjection;", "(Lkotlin/reflect/KTypeProjection;)V", "(Lkotlin/reflect/KTypeProjection;)Ljava/lang/reflect/Type;", "createPossiblyInnerType", "jClass", "Ljava/lang/Class;", "arguments", "", "typeToString", "", "type", "computeJavaType", "forceWrapper", "", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nTypesJVM.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TypesJVM.kt\nkotlin/reflect/TypesJVMKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,230:1\n1#2:231\n1549#3:232\n1620#3,3:233\n1549#3:236\n1620#3,3:237\n1549#3:240\n1620#3,3:241\n*S KotlinDebug\n*F\n+ 1 TypesJVM.kt\nkotlin/reflect/TypesJVMKt\n*L\n69#1:232\n69#1:233,3\n71#1:236\n71#1:237,3\n77#1:240\n77#1:241,3\n*E\n"})
public final class TypesJVMKt {
    @NotNull
    public static final Type getJavaType(@NotNull KType kType) {
        Type type;
        Intrinsics.checkNotNullParameter(kType, "<this>");
        if (kType instanceof KTypeBase && (type = ((KTypeBase)kType).getJavaType()) != null) {
            Type type2;
            Type type3 = type2 = type;
            boolean bl = false;
            return type3;
        }
        return TypesJVMKt.computeJavaType$default(kType, false, 1, null);
    }

    @SinceKotlin(version="1.4")
    @ExperimentalStdlibApi
    @LowPriorityInOverloadResolution
    public static void getJavaType$annotations(KType kType) {
    }

    @ExperimentalStdlibApi
    private static final Type computeJavaType(KType kType, boolean bl) {
        KClassifier kClassifier = kType.getClassifier();
        if (kClassifier instanceof KTypeParameter) {
            return new TypeVariableImpl((KTypeParameter)kClassifier);
        }
        if (kClassifier instanceof KClass) {
            Class<Object> clazz = bl ? JvmClassMappingKt.getJavaObjectType((KClass)kClassifier) : JvmClassMappingKt.getJavaClass((KClass)kClassifier);
            List<KTypeProjection> list = kType.getArguments();
            if (list.isEmpty()) {
                return clazz;
            }
            if (clazz.isArray()) {
                Type type;
                if (clazz.getComponentType().isPrimitive()) {
                    return clazz;
                }
                KTypeProjection kTypeProjection = CollectionsKt.singleOrNull(list);
                if (kTypeProjection == null) {
                    throw new IllegalArgumentException("kotlin.Array must have exactly one type argument: " + kType);
                }
                KTypeProjection kTypeProjection2 = kTypeProjection;
                KVariance kVariance = kTypeProjection2.component1();
                KType kType2 = kTypeProjection2.component2();
                KVariance kVariance2 = kVariance;
                switch (kVariance2 == null ? -1 : WhenMappings.$EnumSwitchMapping$0[kVariance2.ordinal()]) {
                    case -1: 
                    case 1: {
                        type = clazz;
                        break;
                    }
                    case 2: 
                    case 3: {
                        KType kType3 = kType2;
                        Intrinsics.checkNotNull(kType3);
                        Type type2 = TypesJVMKt.computeJavaType$default(kType3, false, 1, null);
                        if (type2 instanceof Class) {
                            type = clazz;
                            break;
                        }
                        type = new GenericArrayTypeImpl(type2);
                        break;
                    }
                    default: {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return type;
            }
            return TypesJVMKt.createPossiblyInnerType(clazz, list);
        }
        throw new UnsupportedOperationException("Unsupported type classifier: " + kType);
    }

    static Type computeJavaType$default(KType kType, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return TypesJVMKt.computeJavaType(kType, bl);
    }

    @ExperimentalStdlibApi
    private static final Type createPossiblyInnerType(Class<?> clazz, List<KTypeProjection> list) {
        Collection<Type> collection;
        Class<?> clazz2 = clazz.getDeclaringClass();
        if (clazz2 == null) {
            Collection<Type> collection2;
            Iterable iterable = list;
            Type type = null;
            Class<?> clazz3 = clazz;
            boolean bl = false;
            Iterable iterable2 = iterable;
            Collection collection3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
            boolean bl2 = false;
            for (Object t : iterable2) {
                KTypeProjection kTypeProjection = (KTypeProjection)t;
                collection2 = collection3;
                boolean bl3 = false;
                collection2.add(TypesJVMKt.getJavaType(kTypeProjection));
            }
            collection2 = (List)collection3;
            List list2 = collection2;
            Type type2 = type;
            Class<?> clazz4 = clazz3;
            return new ParameterizedTypeImpl(clazz4, type2, list2);
        }
        Class<?> clazz5 = clazz2;
        if (Modifier.isStatic(clazz.getModifiers())) {
            Collection<Type> collection4;
            clazz2 = list;
            Type type = clazz5;
            Class<?> clazz6 = clazz;
            boolean bl = false;
            Class<?> clazz7 = clazz2;
            Collection collection5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(clazz2, 10));
            boolean bl4 = false;
            Iterator iterator2 = clazz7.iterator();
            while (iterator2.hasNext()) {
                Object t = iterator2.next();
                KTypeProjection kTypeProjection = (KTypeProjection)t;
                collection4 = collection5;
                boolean bl5 = false;
                collection4.add(TypesJVMKt.getJavaType(kTypeProjection));
            }
            collection4 = (List)collection5;
            List list3 = collection4;
            Type type3 = type;
            Class<?> clazz8 = clazz6;
            return new ParameterizedTypeImpl(clazz8, type3, list3);
        }
        int n = clazz.getTypeParameters().length;
        Iterable iterable = list.subList(0, n);
        Type type = TypesJVMKt.createPossiblyInnerType(clazz5, list.subList(n, list.size()));
        Class<?> clazz9 = clazz;
        boolean bl = false;
        Iterable iterable3 = iterable;
        Collection collection6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        boolean bl6 = false;
        for (Object t : iterable3) {
            KTypeProjection kTypeProjection = (KTypeProjection)t;
            collection = collection6;
            boolean bl7 = false;
            collection.add(TypesJVMKt.getJavaType(kTypeProjection));
        }
        collection = (List)collection6;
        List list4 = collection;
        Type type4 = type;
        Class<?> clazz10 = clazz9;
        return new ParameterizedTypeImpl(clazz10, type4, list4);
    }

    private static final Type getJavaType(KTypeProjection kTypeProjection) {
        Type type;
        KVariance kVariance = kTypeProjection.getVariance();
        if (kVariance == null) {
            return WildcardTypeImpl.Companion.getSTAR();
        }
        KVariance kVariance2 = kVariance;
        KType kType = kTypeProjection.getType();
        Intrinsics.checkNotNull(kType);
        KType kType2 = kType;
        switch (WhenMappings.$EnumSwitchMapping$0[kVariance2.ordinal()]) {
            case 2: {
                type = TypesJVMKt.computeJavaType(kType2, true);
                break;
            }
            case 1: {
                type = new WildcardTypeImpl(null, TypesJVMKt.computeJavaType(kType2, true));
                break;
            }
            case 3: {
                type = new WildcardTypeImpl(TypesJVMKt.computeJavaType(kType2, true), null);
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return type;
    }

    @ExperimentalStdlibApi
    private static void getJavaType$annotations(KTypeProjection kTypeProjection) {
    }

    private static final String typeToString(Type type) {
        String string;
        if (type instanceof Class) {
            String string2;
            if (((Class)type).isArray()) {
                Sequence<Type> sequence = SequencesKt.generateSequence(type, (Function1)typeToString.unwrap.1.INSTANCE);
                string2 = ((Class)SequencesKt.last(sequence)).getName() + StringsKt.repeat("[]", SequencesKt.count(sequence));
            } else {
                string2 = ((Class)type).getName();
            }
            String string3 = string2;
            Intrinsics.checkNotNullExpressionValue(string3, "{\n        if (type.isArr\u2026   } else type.name\n    }");
            string = string3;
        } else {
            string = type.toString();
        }
        return string;
    }

    public static final Type access$computeJavaType(KType kType, boolean bl) {
        return TypesJVMKt.computeJavaType(kType, bl);
    }

    public static final String access$typeToString(Type type) {
        return TypesJVMKt.typeToString(type);
    }

    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[KVariance.values().length];
            try {
                nArray[KVariance.IN.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[KVariance.INVARIANT.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[KVariance.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

