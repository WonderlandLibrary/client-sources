/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Function;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function11;
import kotlin.jvm.functions.Function12;
import kotlin.jvm.functions.Function13;
import kotlin.jvm.functions.Function14;
import kotlin.jvm.functions.Function15;
import kotlin.jvm.functions.Function16;
import kotlin.jvm.functions.Function17;
import kotlin.jvm.functions.Function18;
import kotlin.jvm.functions.Function19;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function20;
import kotlin.jvm.functions.Function21;
import kotlin.jvm.functions.Function22;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 O2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001OB\u0011\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010F\u001a\u00020\u00122\b\u0010G\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010H\u001a\u00020IH\u0002J\b\u0010J\u001a\u00020KH\u0016J\u0012\u0010L\u001a\u00020\u00122\b\u0010M\u001a\u0004\u0018\u00010\u0002H\u0017J\b\u0010N\u001a\u000201H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e0\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0015R\u001a\u0010\u0018\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u0018\u0010\u0015R\u001a\u0010\u001a\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u001b\u0010\u0014\u001a\u0004\b\u001a\u0010\u0015R\u001a\u0010\u001c\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u001d\u0010\u0014\u001a\u0004\b\u001c\u0010\u0015R\u001a\u0010\u001e\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\u001f\u0010\u0014\u001a\u0004\b\u001e\u0010\u0015R\u001a\u0010 \u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b!\u0010\u0014\u001a\u0004\b \u0010\u0015R\u001a\u0010\"\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b#\u0010\u0014\u001a\u0004\b\"\u0010\u0015R\u001a\u0010$\u001a\u00020\u00128VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b%\u0010\u0014\u001a\u0004\b$\u0010\u0015R\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u001e\u0010(\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030)0\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010\u0010R\u001e\u0010+\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00010\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010\u0010R\u0016\u0010-\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010/R\u0016\u00100\u001a\u0004\u0018\u0001018VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b2\u00103R(\u00104\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00010\b8VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b5\u0010\u0014\u001a\u0004\b6\u0010\u000bR\u0016\u00107\u001a\u0004\u0018\u0001018VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b8\u00103R \u00109\u001a\b\u0012\u0004\u0012\u00020:0\b8VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b;\u0010\u0014\u001a\u0004\b<\u0010\u000bR \u0010=\u001a\b\u0012\u0004\u0012\u00020>0\b8VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b?\u0010\u0014\u001a\u0004\b@\u0010\u000bR\u001c\u0010A\u001a\u0004\u0018\u00010B8VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\bC\u0010\u0014\u001a\u0004\bD\u0010E\u00a8\u0006P"}, d2={"Lkotlin/jvm/internal/ClassReference;", "Lkotlin/reflect/KClass;", "", "Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "jClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "isAbstract$annotations", "()V", "()Z", "isCompanion", "isCompanion$annotations", "isData", "isData$annotations", "isFinal", "isFinal$annotations", "isFun", "isFun$annotations", "isInner", "isInner$annotations", "isOpen", "isOpen$annotations", "isSealed", "isSealed$annotations", "isValue", "isValue$annotations", "getJClass", "()Ljava/lang/Class;", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Ljava/lang/Object;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "getSealedSubclasses$annotations", "getSealedSubclasses", "simpleName", "getSimpleName", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes$annotations", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters$annotations", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility$annotations", "getVisibility", "()Lkotlin/reflect/KVisibility;", "equals", "other", "error", "", "hashCode", "", "isInstance", "value", "toString", "Companion", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nClassReference.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClassReference.kt\nkotlin/jvm/internal/ClassReference\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,205:1\n1559#2:206\n1590#2,4:207\n1253#2,4:211\n1238#2,4:217\n453#3:215\n403#3:216\n*S KotlinDebug\n*F\n+ 1 ClassReference.kt\nkotlin/jvm/internal/ClassReference\n*L\n107#1:206\n107#1:207,4\n155#1:211,4\n163#1:217,4\n163#1:215\n163#1:216\n*E\n"})
public final class ClassReference
implements KClass<Object>,
ClassBasedDeclarationContainer {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final Class<?> jClass;
    @NotNull
    private static final Map<Class<? extends Function<?>>, Integer> FUNCTION_CLASSES;
    @NotNull
    private static final HashMap<String, String> primitiveFqNames;
    @NotNull
    private static final HashMap<String, String> primitiveWrapperFqNames;
    @NotNull
    private static final HashMap<String, String> classFqNames;
    @NotNull
    private static final Map<String, String> simpleNames;

    public ClassReference(@NotNull Class<?> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "jClass");
        this.jClass = clazz;
    }

    @Override
    @NotNull
    public Class<?> getJClass() {
        return this.jClass;
    }

    @Override
    @Nullable
    public String getSimpleName() {
        return Companion.getClassSimpleName(this.getJClass());
    }

    @Override
    @Nullable
    public String getQualifiedName() {
        return Companion.getClassQualifiedName(this.getJClass());
    }

    @Override
    @NotNull
    public Collection<KCallable<?>> getMembers() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @Override
    @NotNull
    public Collection<KFunction<Object>> getConstructors() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @Override
    @NotNull
    public Collection<KClass<?>> getNestedClasses() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @Override
    @NotNull
    public List<Annotation> getAnnotations() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @Override
    @Nullable
    public Object getObjectInstance() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isInstance(@Nullable Object object) {
        return Companion.isInstance(object, this.getJClass());
    }

    @Override
    @NotNull
    public List<KTypeParameter> getTypeParameters() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void getTypeParameters$annotations() {
    }

    @Override
    @NotNull
    public List<KType> getSupertypes() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void getSupertypes$annotations() {
    }

    @Override
    @NotNull
    public List<KClass<? extends Object>> getSealedSubclasses() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.3")
    public static void getSealedSubclasses$annotations() {
    }

    @Override
    @Nullable
    public KVisibility getVisibility() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void getVisibility$annotations() {
    }

    @Override
    public boolean isFinal() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void isFinal$annotations() {
    }

    @Override
    public boolean isOpen() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void isOpen$annotations() {
    }

    @Override
    public boolean isAbstract() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void isAbstract$annotations() {
    }

    @Override
    public boolean isSealed() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void isSealed$annotations() {
    }

    @Override
    public boolean isData() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void isData$annotations() {
    }

    @Override
    public boolean isInner() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void isInner$annotations() {
    }

    @Override
    public boolean isCompanion() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.1")
    public static void isCompanion$annotations() {
    }

    @Override
    public boolean isFun() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.4")
    public static void isFun$annotations() {
    }

    @Override
    public boolean isValue() {
        this.error();
        throw new KotlinNothingValueException();
    }

    @SinceKotlin(version="1.5")
    public static void isValue$annotations() {
    }

    private final Void error() {
        throw new KotlinReflectionNotSupportedError();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object instanceof ClassReference && Intrinsics.areEqual(JvmClassMappingKt.getJavaObjectType(this), JvmClassMappingKt.getJavaObjectType((KClass)object));
    }

    @Override
    public int hashCode() {
        return JvmClassMappingKt.getJavaObjectType(this).hashCode();
    }

    @NotNull
    public String toString() {
        return this.getJClass().toString() + " (Kotlin reflection is not available)";
    }

    public static final Map access$getSimpleNames$cp() {
        return simpleNames;
    }

    public static final HashMap access$getClassFqNames$cp() {
        return classFqNames;
    }

    public static final Map access$getFUNCTION_CLASSES$cp() {
        return FUNCTION_CLASSES;
    }

    static {
        boolean bl;
        Object object;
        Object object2;
        Companion = new Companion(null);
        Object object3 = new Class[]{Function0.class, Function1.class, Function2.class, Function3.class, Function4.class, Function5.class, Function6.class, Function7.class, Function8.class, Function9.class, Function10.class, Function11.class, Function12.class, Function13.class, Function14.class, Function15.class, Function16.class, Function17.class, Function18.class, Function19.class, Function20.class, Function21.class, Function22.class};
        object3 = CollectionsKt.listOf(object3);
        boolean bl2 = false;
        Object object4 = object3;
        Object object5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(object3, 10));
        boolean bl3 = false;
        int n = 0;
        Iterator<Object> bl7 = object4.iterator();
        while (bl7.hasNext()) {
            int e;
            object2 = bl7.next();
            if ((e = n++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Class entry = (Class)object2;
            int n2 = e;
            object = object5;
            bl = false;
            object.add(TuplesKt.to(entry, n2));
        }
        FUNCTION_CLASSES = MapsKt.toMap((List)object5);
        Object object6 = object3 = new HashMap();
        boolean bl4 = false;
        ((HashMap)object6).put("boolean", "kotlin.Boolean");
        ((HashMap)object6).put("char", "kotlin.Char");
        ((HashMap)object6).put("byte", "kotlin.Byte");
        ((HashMap)object6).put("short", "kotlin.Short");
        ((HashMap)object6).put("int", "kotlin.Int");
        ((HashMap)object6).put("float", "kotlin.Float");
        ((HashMap)object6).put("long", "kotlin.Long");
        ((HashMap)object6).put("double", "kotlin.Double");
        primitiveFqNames = object3;
        object6 = object3 = new HashMap();
        bl4 = false;
        ((HashMap)object6).put("java.lang.Boolean", "kotlin.Boolean");
        ((HashMap)object6).put("java.lang.Character", "kotlin.Char");
        ((HashMap)object6).put("java.lang.Byte", "kotlin.Byte");
        ((HashMap)object6).put("java.lang.Short", "kotlin.Short");
        ((HashMap)object6).put("java.lang.Integer", "kotlin.Int");
        ((HashMap)object6).put("java.lang.Float", "kotlin.Float");
        ((HashMap)object6).put("java.lang.Long", "kotlin.Long");
        ((HashMap)object6).put("java.lang.Double", "kotlin.Double");
        primitiveWrapperFqNames = object3;
        object6 = object3 = new HashMap();
        bl4 = false;
        ((HashMap)object6).put("java.lang.Object", "kotlin.Any");
        ((HashMap)object6).put("java.lang.String", "kotlin.String");
        ((HashMap)object6).put("java.lang.CharSequence", "kotlin.CharSequence");
        ((HashMap)object6).put("java.lang.Throwable", "kotlin.Throwable");
        ((HashMap)object6).put("java.lang.Cloneable", "kotlin.Cloneable");
        ((HashMap)object6).put("java.lang.Number", "kotlin.Number");
        ((HashMap)object6).put("java.lang.Comparable", "kotlin.Comparable");
        ((HashMap)object6).put("java.lang.Enum", "kotlin.Enum");
        ((HashMap)object6).put("java.lang.annotation.Annotation", "kotlin.Annotation");
        ((HashMap)object6).put("java.lang.Iterable", "kotlin.collections.Iterable");
        ((HashMap)object6).put("java.util.Iterator", "kotlin.collections.Iterator");
        ((HashMap)object6).put("java.util.Collection", "kotlin.collections.Collection");
        ((HashMap)object6).put("java.util.List", "kotlin.collections.List");
        ((HashMap)object6).put("java.util.Set", "kotlin.collections.Set");
        ((HashMap)object6).put("java.util.ListIterator", "kotlin.collections.ListIterator");
        ((HashMap)object6).put("java.util.Map", "kotlin.collections.Map");
        ((HashMap)object6).put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        ((HashMap)object6).put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        ((HashMap)object6).put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        ((HashMap)object6).putAll((Map)primitiveFqNames);
        ((HashMap)object6).putAll((Map)primitiveWrapperFqNames);
        Collection<String> collection = primitiveFqNames.values();
        Intrinsics.checkNotNullExpressionValue(collection, "primitiveFqNames.values");
        object5 = collection;
        boolean entry = false;
        Object object7 = object5.iterator();
        while (object7.hasNext()) {
            bl7 = object7.next();
            object2 = (Map)object6;
            Object object8 = (String)((Object)bl7);
            boolean bl5 = false;
            StringBuilder stringBuilder = new StringBuilder().append("kotlin.jvm.internal.");
            Intrinsics.checkNotNullExpressionValue(object8, "kotlinName");
            object8 = TuplesKt.to(stringBuilder.append(StringsKt.substringAfterLast$default((String)object8, '.', null, 2, null)).append("CompanionObject").toString(), (String)object8 + ".Companion");
            object2.put(((Pair)object8).getFirst(), ((Pair)object8).getSecond());
        }
        Map cfr_ignored_0 = (Map)object6;
        for (Map.Entry entry2 : FUNCTION_CLASSES.entrySet()) {
            object7 = (Class)entry2.getKey();
            int n3 = ((Number)entry2.getValue()).intValue();
            ((HashMap)object6).put(((Class)object7).getName(), "kotlin.Function" + n3);
        }
        classFqNames = object3;
        object3 = classFqNames;
        boolean bl6 = false;
        Object object9 = object3;
        object5 = new LinkedHashMap(MapsKt.mapCapacity(object3.size()));
        boolean bl8 = false;
        object7 = object9.entrySet();
        boolean bl9 = false;
        object2 = object7.iterator();
        while (object2.hasNext()) {
            Object e = object2.next();
            Map.Entry entry3 = (Map.Entry)e;
            Object object10 = object5;
            bl = false;
            Map.Entry entry4 = (Map.Entry)e;
            Object k = entry3.getKey();
            object = object10;
            boolean bl10 = false;
            String string = (String)entry4.getValue();
            String string2 = StringsKt.substringAfterLast$default(string, '.', null, 2, null);
            object.put(k, string2);
        }
        simpleNames = object5;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005J\u001c\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00012\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0005R&\u0010\u0003\u001a\u001a\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lkotlin/jvm/internal/ClassReference$Companion;", "", "()V", "FUNCTION_CLASSES", "", "Ljava/lang/Class;", "Lkotlin/Function;", "", "classFqNames", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "primitiveFqNames", "primitiveWrapperFqNames", "simpleNames", "getClassQualifiedName", "jClass", "getClassSimpleName", "isInstance", "", "value", "kotlin-stdlib"})
    @SourceDebugExtension(value={"SMAP\nClassReference.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClassReference.kt\nkotlin/jvm/internal/ClassReference$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,205:1\n1#2:206\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @Nullable
        public final String getClassSimpleName(@NotNull Class<?> clazz) {
            Object object;
            block9: {
                block10: {
                    Executable executable;
                    String string;
                    block11: {
                        block8: {
                            Intrinsics.checkNotNullParameter(clazz, "jClass");
                            if (!clazz.isAnonymousClass()) break block8;
                            object = null;
                            break block9;
                        }
                        if (!clazz.isLocalClass()) break block10;
                        string = clazz.getSimpleName();
                        object = clazz.getEnclosingMethod();
                        if (object == null) break block11;
                        Method method = object;
                        executable = method;
                        boolean bl = false;
                        Intrinsics.checkNotNullExpressionValue(string, "name");
                        String string2 = StringsKt.substringAfter$default(string, ((Method)executable).getName() + '$', null, 2, null);
                        object = string2;
                        if (string2 != null) break block9;
                    }
                    Constructor<?> constructor = clazz.getEnclosingConstructor();
                    if (constructor != null) {
                        Executable executable2 = executable = constructor;
                        boolean bl = false;
                        Intrinsics.checkNotNullExpressionValue(string, "name");
                        object = StringsKt.substringAfter$default(string, ((Constructor)executable2).getName() + '$', null, 2, null);
                    } else {
                        Intrinsics.checkNotNullExpressionValue(string, "name");
                        object = StringsKt.substringAfter$default(string, '$', null, 2, null);
                    }
                    break block9;
                }
                if (clazz.isArray()) {
                    String string;
                    Class<?> clazz2 = clazz.getComponentType();
                    if ((clazz2.isPrimitive() ? ((string = (String)ClassReference.access$getSimpleNames$cp().get(clazz2.getName())) != null ? string + "Array" : null) : (object = null)) == null) {
                        object = "Array";
                    }
                } else {
                    object = (String)ClassReference.access$getSimpleNames$cp().get(clazz.getName());
                    if (object == null) {
                        object = clazz.getSimpleName();
                    }
                }
            }
            return object;
        }

        @Nullable
        public final String getClassQualifiedName(@NotNull Class<?> clazz) {
            String string;
            Intrinsics.checkNotNullParameter(clazz, "jClass");
            if (clazz.isAnonymousClass()) {
                string = null;
            } else if (clazz.isLocalClass()) {
                string = null;
            } else if (clazz.isArray()) {
                String string2;
                Class<?> clazz2 = clazz.getComponentType();
                if ((clazz2.isPrimitive() ? ((string2 = (String)ClassReference.access$getClassFqNames$cp().get(clazz2.getName())) != null ? string2 + "Array" : null) : (string = null)) == null) {
                    string = "kotlin.Array";
                }
            } else {
                string = (String)ClassReference.access$getClassFqNames$cp().get(clazz.getName());
                if (string == null) {
                    string = clazz.getCanonicalName();
                }
            }
            return string;
        }

        public final boolean isInstance(@Nullable Object object, @NotNull Class<?> clazz) {
            Intrinsics.checkNotNullParameter(clazz, "jClass");
            Map map = ClassReference.access$getFUNCTION_CLASSES$cp();
            Intrinsics.checkNotNull(map, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.get, V of kotlin.collections.MapsKt__MapsKt.get>");
            Serializable serializable = (Integer)map.get(clazz);
            if (serializable != null) {
                Integer n = serializable;
                int n2 = ((Number)n).intValue();
                boolean bl = false;
                return TypeIntrinsics.isFunctionOfArity(object, n2);
            }
            serializable = clazz.isPrimitive() ? JvmClassMappingKt.getJavaObjectType(JvmClassMappingKt.getKotlinClass(clazz)) : clazz;
            return ((Class)serializable).isInstance(object);
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

