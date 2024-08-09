/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.util.Arrays;
import java.util.Collections;
import kotlin.SinceKotlin;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference2;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference2;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty0;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KProperty2;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;

public class Reflection {
    private static final ReflectionFactory factory;
    static final String REFLECTION_NOT_AVAILABLE = " (Kotlin reflection is not available)";
    private static final KClass[] EMPTY_K_CLASS_ARRAY;

    public static KClass createKotlinClass(Class clazz) {
        return factory.createKotlinClass(clazz);
    }

    public static KClass createKotlinClass(Class clazz, String string) {
        return factory.createKotlinClass(clazz, string);
    }

    @SinceKotlin(version="1.4")
    public static KDeclarationContainer getOrCreateKotlinPackage(Class clazz) {
        return factory.getOrCreateKotlinPackage(clazz, "");
    }

    public static KDeclarationContainer getOrCreateKotlinPackage(Class clazz, String string) {
        return factory.getOrCreateKotlinPackage(clazz, string);
    }

    public static KClass getOrCreateKotlinClass(Class clazz) {
        return factory.getOrCreateKotlinClass(clazz);
    }

    public static KClass getOrCreateKotlinClass(Class clazz, String string) {
        return factory.getOrCreateKotlinClass(clazz, string);
    }

    public static KClass[] getOrCreateKotlinClasses(Class[] classArray) {
        int n = classArray.length;
        if (n == 0) {
            return EMPTY_K_CLASS_ARRAY;
        }
        KClass[] kClassArray = new KClass[n];
        for (int i = 0; i < n; ++i) {
            kClassArray[i] = Reflection.getOrCreateKotlinClass(classArray[i]);
        }
        return kClassArray;
    }

    @SinceKotlin(version="1.1")
    public static String renderLambdaToString(Lambda lambda) {
        return factory.renderLambdaToString(lambda);
    }

    @SinceKotlin(version="1.3")
    public static String renderLambdaToString(FunctionBase functionBase) {
        return factory.renderLambdaToString(functionBase);
    }

    public static KFunction function(FunctionReference functionReference) {
        return factory.function(functionReference);
    }

    public static KProperty0 property0(PropertyReference0 propertyReference0) {
        return factory.property0(propertyReference0);
    }

    public static KMutableProperty0 mutableProperty0(MutablePropertyReference0 mutablePropertyReference0) {
        return factory.mutableProperty0(mutablePropertyReference0);
    }

    public static KProperty1 property1(PropertyReference1 propertyReference1) {
        return factory.property1(propertyReference1);
    }

    public static KMutableProperty1 mutableProperty1(MutablePropertyReference1 mutablePropertyReference1) {
        return factory.mutableProperty1(mutablePropertyReference1);
    }

    public static KProperty2 property2(PropertyReference2 propertyReference2) {
        return factory.property2(propertyReference2);
    }

    public static KMutableProperty2 mutableProperty2(MutablePropertyReference2 mutablePropertyReference2) {
        return factory.mutableProperty2(mutablePropertyReference2);
    }

    @SinceKotlin(version="1.4")
    public static KType typeOf(KClassifier kClassifier) {
        return factory.typeOf(kClassifier, Collections.emptyList(), true);
    }

    @SinceKotlin(version="1.4")
    public static KType typeOf(Class clazz) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(clazz), Collections.emptyList(), true);
    }

    @SinceKotlin(version="1.4")
    public static KType typeOf(Class clazz, KTypeProjection kTypeProjection) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(clazz), Collections.singletonList(kTypeProjection), true);
    }

    @SinceKotlin(version="1.4")
    public static KType typeOf(Class clazz, KTypeProjection kTypeProjection, KTypeProjection kTypeProjection2) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(clazz), Arrays.asList(kTypeProjection, kTypeProjection2), true);
    }

    @SinceKotlin(version="1.4")
    public static KType typeOf(Class clazz, KTypeProjection ... kTypeProjectionArray) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(clazz), ArraysKt.toList(kTypeProjectionArray), true);
    }

    @SinceKotlin(version="1.4")
    public static KType nullableTypeOf(KClassifier kClassifier) {
        return factory.typeOf(kClassifier, Collections.emptyList(), false);
    }

    @SinceKotlin(version="1.4")
    public static KType nullableTypeOf(Class clazz) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(clazz), Collections.emptyList(), false);
    }

    @SinceKotlin(version="1.4")
    public static KType nullableTypeOf(Class clazz, KTypeProjection kTypeProjection) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(clazz), Collections.singletonList(kTypeProjection), false);
    }

    @SinceKotlin(version="1.4")
    public static KType nullableTypeOf(Class clazz, KTypeProjection kTypeProjection, KTypeProjection kTypeProjection2) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(clazz), Arrays.asList(kTypeProjection, kTypeProjection2), false);
    }

    @SinceKotlin(version="1.4")
    public static KType nullableTypeOf(Class clazz, KTypeProjection ... kTypeProjectionArray) {
        return factory.typeOf(Reflection.getOrCreateKotlinClass(clazz), ArraysKt.toList(kTypeProjectionArray), false);
    }

    @SinceKotlin(version="1.4")
    public static KTypeParameter typeParameter(Object object, String string, KVariance kVariance, boolean bl) {
        return factory.typeParameter(object, string, kVariance, bl);
    }

    @SinceKotlin(version="1.4")
    public static void setUpperBounds(KTypeParameter kTypeParameter, KType kType) {
        factory.setUpperBounds(kTypeParameter, Collections.singletonList(kType));
    }

    @SinceKotlin(version="1.4")
    public static void setUpperBounds(KTypeParameter kTypeParameter, KType ... kTypeArray) {
        factory.setUpperBounds(kTypeParameter, ArraysKt.toList(kTypeArray));
    }

    @SinceKotlin(version="1.6")
    public static KType platformType(KType kType, KType kType2) {
        return factory.platformType(kType, kType2);
    }

    @SinceKotlin(version="1.6")
    public static KType mutableCollectionType(KType kType) {
        return factory.mutableCollectionType(kType);
    }

    @SinceKotlin(version="1.6")
    public static KType nothingType(KType kType) {
        return factory.nothingType(kType);
    }

    static {
        ReflectionFactory reflectionFactory;
        try {
            Class<?> clazz = Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl");
            reflectionFactory = (ReflectionFactory)clazz.newInstance();
        } catch (ClassCastException classCastException) {
            reflectionFactory = null;
        } catch (ClassNotFoundException classNotFoundException) {
            reflectionFactory = null;
        } catch (InstantiationException instantiationException) {
            reflectionFactory = null;
        } catch (IllegalAccessException illegalAccessException) {
            reflectionFactory = null;
        }
        factory = reflectionFactory != null ? reflectionFactory : new ReflectionFactory();
        EMPTY_K_CLASS_ARRAY = new KClass[0];
    }
}

