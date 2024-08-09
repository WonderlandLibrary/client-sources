/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.util.List;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference2;
import kotlin.jvm.internal.PackageReference;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference2;
import kotlin.jvm.internal.TypeParameterReference;
import kotlin.jvm.internal.TypeReference;
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

public class ReflectionFactory {
    private static final String KOTLIN_JVM_FUNCTIONS = "kotlin.jvm.functions.";

    public KClass createKotlinClass(Class clazz) {
        return new ClassReference(clazz);
    }

    public KClass createKotlinClass(Class clazz, String string) {
        return new ClassReference(clazz);
    }

    public KDeclarationContainer getOrCreateKotlinPackage(Class clazz, String string) {
        return new PackageReference(clazz, string);
    }

    public KClass getOrCreateKotlinClass(Class clazz) {
        return new ClassReference(clazz);
    }

    public KClass getOrCreateKotlinClass(Class clazz, String string) {
        return new ClassReference(clazz);
    }

    @SinceKotlin(version="1.1")
    public String renderLambdaToString(Lambda lambda) {
        return this.renderLambdaToString((FunctionBase)lambda);
    }

    @SinceKotlin(version="1.3")
    public String renderLambdaToString(FunctionBase functionBase) {
        String string = functionBase.getClass().getGenericInterfaces()[0].toString();
        return string.startsWith(KOTLIN_JVM_FUNCTIONS) ? string.substring(21) : string;
    }

    public KFunction function(FunctionReference functionReference) {
        return functionReference;
    }

    public KProperty0 property0(PropertyReference0 propertyReference0) {
        return propertyReference0;
    }

    public KMutableProperty0 mutableProperty0(MutablePropertyReference0 mutablePropertyReference0) {
        return mutablePropertyReference0;
    }

    public KProperty1 property1(PropertyReference1 propertyReference1) {
        return propertyReference1;
    }

    public KMutableProperty1 mutableProperty1(MutablePropertyReference1 mutablePropertyReference1) {
        return mutablePropertyReference1;
    }

    public KProperty2 property2(PropertyReference2 propertyReference2) {
        return propertyReference2;
    }

    public KMutableProperty2 mutableProperty2(MutablePropertyReference2 mutablePropertyReference2) {
        return mutablePropertyReference2;
    }

    @SinceKotlin(version="1.4")
    public KType typeOf(KClassifier kClassifier, List<KTypeProjection> list, boolean bl) {
        return new TypeReference(kClassifier, list, bl);
    }

    @SinceKotlin(version="1.4")
    public KTypeParameter typeParameter(Object object, String string, KVariance kVariance, boolean bl) {
        return new TypeParameterReference(object, string, kVariance, bl);
    }

    @SinceKotlin(version="1.4")
    public void setUpperBounds(KTypeParameter kTypeParameter, List<KType> list) {
        ((TypeParameterReference)kTypeParameter).setUpperBounds(list);
    }

    @SinceKotlin(version="1.6")
    public KType platformType(KType kType, KType kType2) {
        return new TypeReference(kType.getClassifier(), kType.getArguments(), kType2, ((TypeReference)kType).getFlags$kotlin_stdlib());
    }

    @SinceKotlin(version="1.6")
    public KType mutableCollectionType(KType kType) {
        TypeReference typeReference = (TypeReference)kType;
        return new TypeReference(kType.getClassifier(), kType.getArguments(), typeReference.getPlatformTypeUpperBound$kotlin_stdlib(), typeReference.getFlags$kotlin_stdlib() | 2);
    }

    @SinceKotlin(version="1.6")
    public KType nothingType(KType kType) {
        TypeReference typeReference = (TypeReference)kType;
        return new TypeReference(kType.getClassifier(), kType.getArguments(), typeReference.getPlatformTypeUpperBound$kotlin_stdlib(), typeReference.getFlags$kotlin_stdlib() | 4);
    }
}

