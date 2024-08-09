/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Inj1;
import com.mojang.datafixers.optics.Inj2;
import com.mojang.datafixers.optics.InjTagged;
import com.mojang.datafixers.optics.ListTraversal;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Proj1;
import com.mojang.datafixers.optics.Proj2;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.optics.profunctors.Cocartesian;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class TypedOptic<S, T, A, B> {
    protected final Set<TypeToken<? extends K1>> proofBounds;
    protected final Type<S> sType;
    protected final Type<T> tType;
    protected final Type<A> aType;
    protected final Type<B> bType;
    private final Optic<?, S, T, A, B> optic;

    public TypedOptic(TypeToken<? extends K1> typeToken, Type<S> type, Type<T> type2, Type<A> type3, Type<B> type4, Optic<?, S, T, A, B> optic) {
        this(ImmutableSet.of(typeToken), type, type2, type3, type4, optic);
    }

    public TypedOptic(Set<TypeToken<? extends K1>> set, Type<S> type, Type<T> type2, Type<A> type3, Type<B> type4, Optic<?, S, T, A, B> optic) {
        this.proofBounds = set;
        this.sType = type;
        this.tType = type2;
        this.aType = type3;
        this.bType = type4;
        this.optic = optic;
    }

    public <P extends K2, Proof2 extends K1> App2<P, S, T> apply(TypeToken<Proof2> typeToken, App<Proof2, P> app, App2<P, A, B> app2) {
        return this.upCast(typeToken).orElseThrow(TypedOptic::lambda$apply$0).eval(app).apply(app2);
    }

    public Optic<?, S, T, A, B> optic() {
        return this.optic;
    }

    public Set<TypeToken<? extends K1>> bounds() {
        return this.proofBounds;
    }

    public Type<S> sType() {
        return this.sType;
    }

    public Type<T> tType() {
        return this.tType;
    }

    public Type<A> aType() {
        return this.aType;
    }

    public Type<B> bType() {
        return this.bType;
    }

    public <A1, B1> TypedOptic<S, T, A1, B1> compose(TypedOptic<A, B, A1, B1> typedOptic) {
        ImmutableSet.Builder builder = ImmutableSet.builder();
        builder.addAll(this.proofBounds);
        builder.addAll(typedOptic.proofBounds);
        return new TypedOptic<S, T, A, B>((Set<TypeToken<? extends K1>>)((Object)builder.build()), this.sType, this.tType, typedOptic.aType, typedOptic.bType, this.optic().composeUnchecked(typedOptic.optic()));
    }

    public <Proof2 extends K1> Optional<Optic<? super Proof2, S, T, A, B>> upCast(TypeToken<Proof2> typeToken) {
        if (TypedOptic.instanceOf(this.proofBounds, typeToken)) {
            return Optional.of(this.optic);
        }
        return Optional.empty();
    }

    public static <Proof2 extends K1> boolean instanceOf(Collection<TypeToken<? extends K1>> collection, TypeToken<Proof2> typeToken) {
        return collection.stream().allMatch(arg_0 -> TypedOptic.lambda$instanceOf$1(typeToken, arg_0));
    }

    public static <S, T> TypedOptic<S, T, S, T> adapter(Type<S> type, Type<T> type2) {
        return new TypedOptic<S, T, S, T>(Profunctor.Mu.TYPE_TOKEN, type, type2, type, type2, Optics.id());
    }

    public static <F, G, F2> TypedOptic<Pair<F, G>, Pair<F2, G>, F, F2> proj1(Type<F> type, Type<G> type2, Type<F2> type3) {
        return new TypedOptic<Pair<F, G>, Pair<F2, G>, F, F2>(Cartesian.Mu.TYPE_TOKEN, DSL.and(type, type2), DSL.and(type3, type2), type, type3, new Proj1());
    }

    public static <F, G, G2> TypedOptic<Pair<F, G>, Pair<F, G2>, G, G2> proj2(Type<F> type, Type<G> type2, Type<G2> type3) {
        return new TypedOptic<Pair<F, G>, Pair<F, G2>, G, G2>(Cartesian.Mu.TYPE_TOKEN, DSL.and(type, type2), DSL.and(type, type3), type2, type3, new Proj2());
    }

    public static <F, G, F2> TypedOptic<Either<F, G>, Either<F2, G>, F, F2> inj1(Type<F> type, Type<G> type2, Type<F2> type3) {
        return new TypedOptic<Either<F, G>, Either<F2, G>, F, F2>(Cocartesian.Mu.TYPE_TOKEN, DSL.or(type, type2), DSL.or(type3, type2), type, type3, new Inj1());
    }

    public static <F, G, G2> TypedOptic<Either<F, G>, Either<F, G2>, G, G2> inj2(Type<F> type, Type<G> type2, Type<G2> type3) {
        return new TypedOptic<Either<F, G>, Either<F, G2>, G, G2>(Cocartesian.Mu.TYPE_TOKEN, DSL.or(type, type2), DSL.or(type, type3), type2, type3, new Inj2());
    }

    public static <K, V, K2> TypedOptic<List<Pair<K, V>>, List<Pair<K2, V>>, K, K2> compoundListKeys(Type<K> type, Type<K2> type2, Type<V> type3) {
        return new TypedOptic(TraversalP.Mu.TYPE_TOKEN, DSL.compoundList(type, type3), DSL.compoundList(type2, type3), type, type2, new ListTraversal().compose(Optics.proj1()));
    }

    public static <K, V, V2> TypedOptic<List<Pair<K, V>>, List<Pair<K, V2>>, V, V2> compoundListElements(Type<K> type, Type<V> type2, Type<V2> type3) {
        return new TypedOptic(TraversalP.Mu.TYPE_TOKEN, DSL.compoundList(type, type2), DSL.compoundList(type, type3), type2, type3, new ListTraversal().compose(Optics.proj2()));
    }

    public static <A, B> TypedOptic<List<A>, List<B>, A, B> list(Type<A> type, Type<B> type2) {
        return new TypedOptic<A, B, A, B>(TraversalP.Mu.TYPE_TOKEN, DSL.list(type), DSL.list(type2), type, type2, new ListTraversal());
    }

    public static <K, A, B> TypedOptic<Pair<K, ?>, Pair<K, ?>, A, B> tagged(TaggedChoice.TaggedChoiceType<K> taggedChoiceType, K k, Type<A> type, Type<B> type2) {
        if (!Objects.equals(taggedChoiceType.types().get(k), type)) {
            throw new IllegalArgumentException("Focused type doesn't match.");
        }
        HashMap<K, Type<?>> hashMap = Maps.newHashMap(taggedChoiceType.types());
        hashMap.put(k, type2);
        Type<Pair<K, ?>> type3 = DSL.taggedChoiceType(taggedChoiceType.getName(), taggedChoiceType.getKeyType(), hashMap);
        return new TypedOptic(Cocartesian.Mu.TYPE_TOKEN, taggedChoiceType, type3, type, type2, new InjTagged(k));
    }

    private static boolean lambda$instanceOf$1(TypeToken typeToken, TypeToken typeToken2) {
        return typeToken2.isSupertypeOf(typeToken);
    }

    private static IllegalArgumentException lambda$apply$0() {
        return new IllegalArgumentException("Couldn't upcast");
    }
}

