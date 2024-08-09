/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.templates;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.Affine;
import com.mojang.datafixers.optics.Lens;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Traversal;
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.RecursiveTypeFamily;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.codecs.KeyDispatchCodec;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class TaggedChoice<K>
implements TypeTemplate {
    private final String name;
    private final Type<K> keyType;
    private final Map<K, TypeTemplate> templates;
    private final Map<Pair<TypeFamily, Integer>, Type<?>> types = Maps.newConcurrentMap();
    private final int size;

    public TaggedChoice(String string, Type<K> type, Map<K, TypeTemplate> map) {
        this.name = string;
        this.keyType = type;
        this.templates = map;
        this.size = map.values().stream().mapToInt(TypeTemplate::size).max().orElse(0);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public TypeFamily apply(TypeFamily typeFamily) {
        return arg_0 -> this.lambda$apply$2(typeFamily, arg_0);
    }

    @Override
    public <A, B> FamilyOptic<A, B> applyO(FamilyOptic<A, B> familyOptic, Type<A> type, Type<B> type2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A, B> Either<TypeTemplate, Type.FieldNotFoundException> findFieldOrType(int n, @Nullable String string, Type<A> type, Type<B> type2) {
        return Either.right(new Type.FieldNotFoundException("Not implemented"));
    }

    @Override
    public IntFunction<RewriteResult<?, ?>> hmap(TypeFamily typeFamily, IntFunction<RewriteResult<?, ?>> intFunction) {
        return arg_0 -> this.lambda$hmap$3(typeFamily, intFunction, arg_0);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof TaggedChoice)) {
            return true;
        }
        TaggedChoice taggedChoice = (TaggedChoice)object;
        return Objects.equals(this.name, taggedChoice.name) && Objects.equals(this.keyType, taggedChoice.keyType) && Objects.equals(this.templates, taggedChoice.templates);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.keyType, this.templates);
    }

    public String toString() {
        return "TaggedChoice[" + this.name + ", " + Joiner.on(", ").withKeyValueSeparator(" -> ").join(this.templates) + "]";
    }

    private RewriteResult lambda$hmap$3(TypeFamily typeFamily, IntFunction intFunction, int n) {
        RewriteResult rewriteResult = RewriteResult.nop((TaggedChoiceType)this.apply(typeFamily).apply(n));
        for (Map.Entry<K, TypeTemplate> entry : this.templates.entrySet()) {
            RewriteResult<?, ?> rewriteResult2 = entry.getValue().hmap(typeFamily, intFunction).apply(n);
            rewriteResult = TaggedChoiceType.elementResult(entry.getKey(), (TaggedChoiceType)rewriteResult.view().newType(), rewriteResult2).compose(rewriteResult);
        }
        return rewriteResult;
    }

    private Type lambda$apply$2(TypeFamily typeFamily, int n) {
        return this.types.computeIfAbsent(Pair.of(typeFamily, n), this::lambda$null$1);
    }

    private Type lambda$null$1(Pair pair) {
        return DSL.taggedChoiceType(this.name, this.keyType, this.templates.entrySet().stream().map(arg_0 -> TaggedChoice.lambda$null$0(pair, arg_0)).collect(Pair.toMap()));
    }

    private static Pair lambda$null$0(Pair pair, Map.Entry entry) {
        return Pair.of(entry.getKey(), ((TypeTemplate)entry.getValue()).apply((TypeFamily)pair.getFirst()).apply((Integer)pair.getSecond()));
    }

    public static final class TaggedChoiceType<K>
    extends Type<Pair<K, ?>> {
        private final String name;
        private final Type<K> keyType;
        protected final Map<K, Type<?>> types;
        private final int hashCode;

        public TaggedChoiceType(String string, Type<K> type, Map<K, Type<?>> map) {
            this.name = string;
            this.keyType = type;
            this.types = map;
            this.hashCode = Objects.hash(string, type, map);
        }

        @Override
        public RewriteResult<Pair<K, ?>, ?> all(TypeRewriteRule typeRewriteRule, boolean bl, boolean bl2) {
            Map map = this.types.entrySet().stream().map(arg_0 -> TaggedChoiceType.lambda$all$1(typeRewriteRule, arg_0)).filter(TaggedChoiceType::lambda$all$2).map(Optional::get).collect(Pair.toMap());
            if (map.isEmpty()) {
                return RewriteResult.nop(this);
            }
            if (map.size() == 1) {
                Map.Entry entry = map.entrySet().iterator().next();
                return TaggedChoiceType.elementResult(entry.getKey(), this, (RewriteResult)entry.getValue());
            }
            HashMap hashMap = Maps.newHashMap(this.types);
            BitSet bitSet = new BitSet();
            for (Map.Entry entry : map.entrySet()) {
                hashMap.put(entry.getKey(), ((RewriteResult)entry.getValue()).view().newType());
                bitSet.or(((RewriteResult)entry.getValue()).recData());
            }
            return RewriteResult.create(View.create(this, DSL.taggedChoiceType(this.name, this.keyType, hashMap), Functions.fun("TaggedChoiceTypeRewriteResult " + map.size(), new RewriteFunc(map))), bitSet);
        }

        public static <K, FT, FR> RewriteResult<Pair<K, ?>, Pair<K, ?>> elementResult(K k, TaggedChoiceType<K> taggedChoiceType, RewriteResult<FT, FR> rewriteResult) {
            return TaggedChoiceType.opticView(taggedChoiceType, rewriteResult, TypedOptic.tagged(taggedChoiceType, k, rewriteResult.view().type(), rewriteResult.view().newType()));
        }

        @Override
        public Optional<RewriteResult<Pair<K, ?>, ?>> one(TypeRewriteRule typeRewriteRule) {
            for (Map.Entry<K, Type<?>> entry : this.types.entrySet()) {
                Optional<RewriteResult<?, ?>> optional = typeRewriteRule.rewrite(entry.getValue());
                if (!optional.isPresent()) continue;
                return Optional.of(TaggedChoiceType.elementResult(entry.getKey(), this, optional.get()));
            }
            return Optional.empty();
        }

        @Override
        public Type<?> updateMu(RecursiveTypeFamily recursiveTypeFamily) {
            return DSL.taggedChoiceType(this.name, this.keyType, this.types.entrySet().stream().map(arg_0 -> TaggedChoiceType.lambda$updateMu$3(recursiveTypeFamily, arg_0)).collect(Pair.toMap()));
        }

        @Override
        public TypeTemplate buildTemplate() {
            return DSL.taggedChoice(this.name, this.keyType, this.types.entrySet().stream().map(TaggedChoiceType::lambda$buildTemplate$4).collect(Pair.toMap()));
        }

        private <V> DataResult<? extends Encoder<Pair<K, ?>>> encoder(Pair<K, V> pair) {
            return this.getCodec(pair.getFirst()).map(TaggedChoiceType::lambda$encoder$6);
        }

        @Override
        protected Codec<Pair<K, ?>> buildCodec() {
            return KeyDispatchCodec.unsafe(this.name, this.keyType.codec(), TaggedChoiceType::lambda$buildCodec$7, this::lambda$buildCodec$10, this::encoder).codec();
        }

        private DataResult<? extends Codec<?>> getCodec(K k) {
            return Optional.ofNullable(this.types.get(k)).map(TaggedChoiceType::lambda$getCodec$11).orElseGet(() -> TaggedChoiceType.lambda$getCodec$12(k));
        }

        @Override
        public Optional<Type<?>> findFieldTypeOpt(String string) {
            return this.types.values().stream().map(arg_0 -> TaggedChoiceType.lambda$findFieldTypeOpt$13(string, arg_0)).filter(Optional::isPresent).findFirst().flatMap(Function.identity());
        }

        @Override
        public Optional<Pair<K, ?>> point(DynamicOps<?> dynamicOps) {
            return this.types.entrySet().stream().map(arg_0 -> TaggedChoiceType.lambda$point$15(dynamicOps, arg_0)).filter(Optional::isPresent).findFirst().flatMap(Function.identity()).map(TaggedChoiceType::lambda$point$16);
        }

        public Optional<Typed<Pair<K, ?>>> point(DynamicOps<?> dynamicOps, K k, Object object) {
            if (!this.types.containsKey(k)) {
                return Optional.empty();
            }
            return Optional.of(new Typed<Pair<K, Object>>(this, dynamicOps, Pair.of(k, object)));
        }

        @Override
        public <FT, FR> Either<TypedOptic<Pair<K, ?>, ?, FT, FR>, Type.FieldNotFoundException> findTypeInChildren(Type<FT> type, Type<FR> type2, Type.TypeMatcher<FT, FR> typeMatcher, boolean bl) {
            Optic optic;
            TypeToken<Cartesian.Mu> typeToken;
            Map map = this.types.entrySet().stream().map(arg_0 -> TaggedChoiceType.lambda$findTypeInChildren$17(type, type2, typeMatcher, bl, arg_0)).filter(TaggedChoiceType::lambda$findTypeInChildren$18).map(TaggedChoiceType::lambda$findTypeInChildren$20).collect(Pair.toMap());
            if (map.isEmpty()) {
                return Either.right(new Type.FieldNotFoundException("Not found in any choices"));
            }
            if (map.size() == 1) {
                Map.Entry entry = map.entrySet().iterator().next();
                return Either.left(this.cap(this, entry.getKey(), (TypedOptic)entry.getValue()));
            }
            HashSet<TypeToken<? extends K1>> hashSet = Sets.newHashSet();
            map.values().forEach(arg_0 -> TaggedChoiceType.lambda$findTypeInChildren$21(hashSet, arg_0));
            if (TypedOptic.instanceOf(hashSet, Cartesian.Mu.TYPE_TOKEN) && map.size() == this.types.size()) {
                typeToken = Cartesian.Mu.TYPE_TOKEN;
                optic = new Lens<Pair<K, ?>, Pair<K, ?>, FT, FR>(this, map){
                    final Map val$optics;
                    final TaggedChoiceType this$0;
                    {
                        this.this$0 = taggedChoiceType;
                        this.val$optics = map;
                    }

                    @Override
                    public FT view(Pair<K, ?> pair) {
                        TypedOptic typedOptic = (TypedOptic)this.val$optics.get(pair.getFirst());
                        return this.capView(pair, typedOptic);
                    }

                    private <S, T> FT capView(Pair<K, ?> pair, TypedOptic<S, T, FT, FR> typedOptic) {
                        return Optics.toLens(typedOptic.upCast(Cartesian.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)).view(pair.getSecond());
                    }

                    @Override
                    public Pair<K, ?> update(FR FR, Pair<K, ?> pair) {
                        TypedOptic typedOptic = (TypedOptic)this.val$optics.get(pair.getFirst());
                        return this.capUpdate(FR, pair, typedOptic);
                    }

                    private <S, T> Pair<K, ?> capUpdate(FR FR, Pair<K, ?> pair, TypedOptic<S, T, FT, FR> typedOptic) {
                        return Pair.of(pair.getFirst(), Optics.toLens(typedOptic.upCast(Cartesian.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)).update(FR, pair.getSecond()));
                    }

                    @Override
                    public Object update(Object object, Object object2) {
                        return this.update(object, (Pair)object2);
                    }

                    @Override
                    public Object view(Object object) {
                        return this.view((Pair)object);
                    }
                };
            } else if (TypedOptic.instanceOf(hashSet, AffineP.Mu.TYPE_TOKEN)) {
                typeToken = AffineP.Mu.TYPE_TOKEN;
                optic = new Affine<Pair<K, ?>, Pair<K, ?>, FT, FR>(this, map){
                    final Map val$optics;
                    final TaggedChoiceType this$0;
                    {
                        this.this$0 = taggedChoiceType;
                        this.val$optics = map;
                    }

                    @Override
                    public Either<Pair<K, ?>, FT> preview(Pair<K, ?> pair) {
                        if (!this.val$optics.containsKey(pair.getFirst())) {
                            return Either.left(pair);
                        }
                        TypedOptic typedOptic = (TypedOptic)this.val$optics.get(pair.getFirst());
                        return this.capPreview(pair, typedOptic);
                    }

                    private <S, T> Either<Pair<K, ?>, FT> capPreview(Pair<K, ?> pair, TypedOptic<S, T, FT, FR> typedOptic) {
                        return Optics.toAffine(typedOptic.upCast(AffineP.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)).preview(pair.getSecond()).mapLeft(arg_0 -> 2.lambda$capPreview$0(pair, arg_0));
                    }

                    @Override
                    public Pair<K, ?> set(FR FR, Pair<K, ?> pair) {
                        if (!this.val$optics.containsKey(pair.getFirst())) {
                            return pair;
                        }
                        TypedOptic typedOptic = (TypedOptic)this.val$optics.get(pair.getFirst());
                        return this.capSet(FR, pair, typedOptic);
                    }

                    private <S, T> Pair<K, ?> capSet(FR FR, Pair<K, ?> pair, TypedOptic<S, T, FT, FR> typedOptic) {
                        return Pair.of(pair.getFirst(), Optics.toAffine(typedOptic.upCast(AffineP.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new)).set(FR, pair.getSecond()));
                    }

                    @Override
                    public Object set(Object object, Object object2) {
                        return this.set(object, (Pair)object2);
                    }

                    @Override
                    public Either preview(Object object) {
                        return this.preview((Pair)object);
                    }

                    private static Pair lambda$capPreview$0(Pair pair, Object object) {
                        return Pair.of(pair.getFirst(), object);
                    }
                };
            } else if (TypedOptic.instanceOf(hashSet, TraversalP.Mu.TYPE_TOKEN)) {
                typeToken = TraversalP.Mu.TYPE_TOKEN;
                optic = new Traversal<Pair<K, ?>, Pair<K, ?>, FT, FR>(this, map){
                    final Map val$optics;
                    final TaggedChoiceType this$0;
                    {
                        this.this$0 = taggedChoiceType;
                        this.val$optics = map;
                    }

                    @Override
                    public <F extends K1> FunctionType<Pair<K, ?>, App<F, Pair<K, ?>>> wander(Applicative<F, ?> applicative, FunctionType<FT, App<F, FR>> functionType) {
                        return arg_0 -> this.lambda$wander$0(this.val$optics, applicative, functionType, arg_0);
                    }

                    private <S, T, F extends K1> App<F, Pair<K, ?>> capTraversal(Applicative<F, ?> applicative, FunctionType<FT, App<F, FR>> functionType, Pair<K, ?> pair, TypedOptic<S, T, FT, FR> typedOptic) {
                        Traversal traversal = Optics.toTraversal(typedOptic.upCast(TraversalP.Mu.TYPE_TOKEN).orElseThrow(IllegalArgumentException::new));
                        return applicative.ap(arg_0 -> 3.lambda$capTraversal$1(pair, arg_0), traversal.wander(applicative, functionType).apply(pair.getSecond()));
                    }

                    private static Pair lambda$capTraversal$1(Pair pair, Object object) {
                        return Pair.of(pair.getFirst(), object);
                    }

                    private App lambda$wander$0(Map map, Applicative applicative, FunctionType functionType, Pair pair) {
                        if (!map.containsKey(pair.getFirst())) {
                            return applicative.point(pair);
                        }
                        TypedOptic typedOptic = (TypedOptic)map.get(pair.getFirst());
                        return this.capTraversal(applicative, functionType, pair, typedOptic);
                    }
                };
            } else {
                throw new IllegalStateException("Could not merge TaggedChoiceType optics, unknown bound: " + Arrays.toString(hashSet.toArray()));
            }
            Map map2 = this.types.entrySet().stream().map(arg_0 -> TaggedChoiceType.lambda$findTypeInChildren$22(map, arg_0)).collect(Pair.toMap());
            return Either.left(new TypedOptic(typeToken, this, DSL.taggedChoiceType(this.name, this.keyType, map2), type, type2, optic));
        }

        private <S, T, FT, FR> TypedOptic<Pair<K, ?>, Pair<K, ?>, FT, FR> cap(TaggedChoiceType<K> taggedChoiceType, K k, TypedOptic<S, T, FT, FR> typedOptic) {
            return TypedOptic.tagged(taggedChoiceType, k, typedOptic.sType(), typedOptic.tType()).compose(typedOptic);
        }

        @Override
        public Optional<TaggedChoiceType<?>> findChoiceType(String string, int n) {
            if (Objects.equals(string, this.name)) {
                return Optional.of(this);
            }
            return Optional.empty();
        }

        @Override
        public Optional<Type<?>> findCheckedType(int n) {
            return this.types.values().stream().map(arg_0 -> TaggedChoiceType.lambda$findCheckedType$23(n, arg_0)).filter(Optional::isPresent).findFirst().flatMap(Function.identity());
        }

        @Override
        public boolean equals(Object object, boolean bl, boolean bl2) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof TaggedChoiceType)) {
                return true;
            }
            TaggedChoiceType taggedChoiceType = (TaggedChoiceType)object;
            if (!Objects.equals(this.name, taggedChoiceType.name)) {
                return true;
            }
            if (!this.keyType.equals(taggedChoiceType.keyType, bl, bl2)) {
                return true;
            }
            if (this.types.size() != taggedChoiceType.types.size()) {
                return true;
            }
            for (Map.Entry<K, Type<?>> entry : this.types.entrySet()) {
                if (entry.getValue().equals(taggedChoiceType.types.get(entry.getKey()), bl, bl2)) continue;
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return "TaggedChoiceType[" + this.name + ", " + Joiner.on(", \n").withKeyValueSeparator(" -> ").join(this.types) + "]\n";
        }

        public String getName() {
            return this.name;
        }

        public Type<K> getKeyType() {
            return this.keyType;
        }

        public boolean hasType(K k) {
            return this.types.containsKey(k);
        }

        public Map<K, Type<?>> types() {
            return this.types;
        }

        private static Optional lambda$findCheckedType$23(int n, Type type) {
            return type.findCheckedType(n);
        }

        private static Pair lambda$findTypeInChildren$22(Map map, Map.Entry entry) {
            return Pair.of(entry.getKey(), map.containsKey(entry.getKey()) ? ((TypedOptic)map.get(entry.getKey())).tType() : (Type)entry.getValue());
        }

        private static void lambda$findTypeInChildren$21(Set set, TypedOptic typedOptic) {
            set.addAll(typedOptic.bounds());
        }

        private static Pair lambda$findTypeInChildren$20(Pair pair) {
            return pair.mapSecond(TaggedChoiceType::lambda$null$19);
        }

        private static TypedOptic lambda$null$19(Either either) {
            return (TypedOptic)either.left().get();
        }

        private static boolean lambda$findTypeInChildren$18(Pair pair) {
            return ((Either)pair.getSecond()).left().isPresent();
        }

        private static Pair lambda$findTypeInChildren$17(Type type, Type type2, Type.TypeMatcher typeMatcher, boolean bl, Map.Entry entry) {
            return Pair.of(entry.getKey(), ((Type)entry.getValue()).findType(type, type2, typeMatcher, bl));
        }

        private static Pair lambda$point$16(Pair pair) {
            return pair;
        }

        private static Optional lambda$point$15(DynamicOps dynamicOps, Map.Entry entry) {
            return ((Type)entry.getValue()).point(dynamicOps).map(arg_0 -> TaggedChoiceType.lambda$null$14(entry, arg_0));
        }

        private static Pair lambda$null$14(Map.Entry entry, Object object) {
            return Pair.of(entry.getKey(), object);
        }

        private static Optional lambda$findFieldTypeOpt$13(String string, Type type) {
            return type.findFieldTypeOpt(string);
        }

        private static DataResult lambda$getCodec$12(Object object) {
            return DataResult.error("Unsupported key: " + object);
        }

        private static DataResult lambda$getCodec$11(Type type) {
            return DataResult.success(type.codec());
        }

        private DataResult lambda$buildCodec$10(Object object) {
            return this.getCodec(object).map(arg_0 -> TaggedChoiceType.lambda$null$9(object, arg_0));
        }

        private static Decoder lambda$null$9(Object object, Codec codec) {
            return codec.map(arg_0 -> TaggedChoiceType.lambda$null$8(object, arg_0));
        }

        private static Pair lambda$null$8(Object object, Object object2) {
            return Pair.of(object, object2);
        }

        private static DataResult lambda$buildCodec$7(Pair pair) {
            return DataResult.success(pair.getFirst());
        }

        private static Encoder lambda$encoder$6(Codec codec) {
            return codec.comap(TaggedChoiceType::lambda$null$5);
        }

        private static Object lambda$null$5(Pair pair) {
            return pair.getSecond();
        }

        private static Pair lambda$buildTemplate$4(Map.Entry entry) {
            return Pair.of(entry.getKey(), ((Type)entry.getValue()).template());
        }

        private static Pair lambda$updateMu$3(RecursiveTypeFamily recursiveTypeFamily, Map.Entry entry) {
            return Pair.of(entry.getKey(), ((Type)entry.getValue()).updateMu(recursiveTypeFamily));
        }

        private static boolean lambda$all$2(Optional optional) {
            return optional.isPresent() && !Objects.equals(((RewriteResult)((Pair)optional.get()).getSecond()).view().function(), Functions.id());
        }

        private static Optional lambda$all$1(TypeRewriteRule typeRewriteRule, Map.Entry entry) {
            return typeRewriteRule.rewrite((Type)entry.getValue()).map(arg_0 -> TaggedChoiceType.lambda$null$0(entry, arg_0));
        }

        private static Pair lambda$null$0(Map.Entry entry, RewriteResult rewriteResult) {
            return Pair.of(entry.getKey(), rewriteResult);
        }

        private static final class RewriteFunc<K>
        implements Function<DynamicOps<?>, Function<Pair<K, ?>, Pair<K, ?>>> {
            private final Map<K, ? extends RewriteResult<?, ?>> results;

            public RewriteFunc(Map<K, ? extends RewriteResult<?, ?>> map) {
                this.results = map;
            }

            @Override
            public FunctionType<Pair<K, ?>, Pair<K, ?>> apply(DynamicOps<?> dynamicOps) {
                return arg_0 -> this.lambda$apply$0(dynamicOps, arg_0);
            }

            private <A, B> Pair<K, B> capRuleApply(DynamicOps<?> dynamicOps, Pair<K, ?> pair, RewriteResult<A, B> rewriteResult) {
                return pair.mapSecond(arg_0 -> RewriteFunc.lambda$capRuleApply$1(rewriteResult, dynamicOps, arg_0));
            }

            public boolean equals(Object object) {
                if (this == object) {
                    return false;
                }
                if (object == null || this.getClass() != object.getClass()) {
                    return true;
                }
                RewriteFunc rewriteFunc = (RewriteFunc)object;
                return Objects.equals(this.results, rewriteFunc.results);
            }

            public int hashCode() {
                return Objects.hash(this.results);
            }

            @Override
            public Object apply(Object object) {
                return this.apply((DynamicOps)object);
            }

            private static Object lambda$capRuleApply$1(RewriteResult rewriteResult, DynamicOps dynamicOps, Object object) {
                return rewriteResult.view().function().evalCached().apply(dynamicOps).apply(object);
            }

            private Pair lambda$apply$0(DynamicOps dynamicOps, Pair pair) {
                RewriteResult<?, ?> rewriteResult = this.results.get(pair.getFirst());
                if (rewriteResult == null) {
                    return pair;
                }
                return this.capRuleApply(dynamicOps, pair, rewriteResult);
            }
        }
    }
}

