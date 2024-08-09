/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.families;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FamilyOptic;
import com.mojang.datafixers.OpticParts;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.TypedOptic;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.Functions;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.families.Algebra;
import com.mojang.datafixers.types.families.ListAlgebra;
import com.mojang.datafixers.types.families.TypeFamily;
import com.mojang.datafixers.types.templates.RecursivePoint;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import javax.annotation.Nullable;

public final class RecursiveTypeFamily
implements TypeFamily {
    private final String name;
    private final TypeTemplate template;
    private final int size;
    private final Int2ObjectMap<RecursivePoint.RecursivePointType<?>> types = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap());
    private final int hashCode;

    public RecursiveTypeFamily(String string, TypeTemplate typeTemplate) {
        this.name = string;
        this.template = typeTemplate;
        this.size = typeTemplate.size();
        this.hashCode = Objects.hashCode(typeTemplate);
    }

    public static <A, B> View<A, B> viewUnchecked(Type<?> type, Type<?> type2, PointFree<Function<A, B>> pointFree) {
        return View.create(type, type2, pointFree);
    }

    public <A> RecursivePoint.RecursivePointType<A> buildMuType(Type<A> type, @Nullable RecursiveTypeFamily recursiveTypeFamily) {
        Object object;
        if (recursiveTypeFamily == null) {
            object = type.template();
            recursiveTypeFamily = Objects.equals(this.template, object) ? this : new RecursiveTypeFamily("ruled " + this.name, (TypeTemplate)object);
        }
        object = null;
        for (int i = 0; i < recursiveTypeFamily.size; ++i) {
            Type type2 = recursiveTypeFamily.apply(i);
            Type type3 = ((RecursivePoint.RecursivePointType)type2).unfold();
            if (!type.equals(type3, true, true)) continue;
            object = type2;
            break;
        }
        if (object == null) {
            throw new IllegalStateException("Couldn't determine the new type properly");
        }
        return object;
    }

    public String name() {
        return this.name;
    }

    public TypeTemplate template() {
        return this.template;
    }

    public int size() {
        return this.size;
    }

    public IntFunction<RewriteResult<?, ?>> fold(Algebra algebra) {
        return arg_0 -> this.lambda$fold$0(algebra, arg_0);
    }

    public RecursivePoint.RecursivePointType<?> apply(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException();
        }
        return (RecursivePoint.RecursivePointType)this.types.computeIfAbsent((Object)n, this::lambda$apply$2);
    }

    public <A, B> Either<TypedOptic<?, ?, A, B>, Type.FieldNotFoundException> findType(int n, Type<A> type, Type<B> type2, Type.TypeMatcher<A, B> typeMatcher, boolean bl) {
        return ((RecursivePoint.RecursivePointType)this.apply(n)).unfold().findType(type, type2, typeMatcher, true).flatMap(arg_0 -> this.lambda$findType$4(n, bl, type, type2, typeMatcher, arg_0));
    }

    private <S, T, A, B> TypedOptic<S, T, A, B> mkOptic(Type<S> type, Type<T> type2, Type<A> type3, Type<B> type4, OpticParts<A, B> opticParts) {
        return new TypedOptic<S, T, A, B>(opticParts.bounds(), type, type2, type3, type4, opticParts.optic());
    }

    private <S, T, A, B> Either<TypedOptic<?, ?, A, B>, Type.FieldNotFoundException> mkSimpleOptic(RecursivePoint.RecursivePointType<S> recursivePointType, RecursivePoint.RecursivePointType<T> recursivePointType2, Type<A> type, Type<B> type2, Type.TypeMatcher<A, B> typeMatcher) {
        return recursivePointType.unfold().findType(type, type2, typeMatcher, true).mapLeft(arg_0 -> this.lambda$mkSimpleOptic$5(recursivePointType, recursivePointType2, arg_0));
    }

    public Optional<RewriteResult<?, ?>> everywhere(int n, TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule) {
        Object object;
        Type type = ((RecursivePoint.RecursivePointType)this.apply(n)).unfold();
        RewriteResult rewriteResult = DataFixUtils.orElse(type.everywhere(typeRewriteRule, pointFreeRule, false, true), RewriteResult.nop(type));
        RecursivePoint.RecursivePointType recursivePointType = this.buildMuType(rewriteResult.view().newType(), null);
        RecursiveTypeFamily recursiveTypeFamily = recursivePointType.family();
        ArrayList<RewriteResult<?, ?>> arrayList = Lists.newArrayList();
        boolean bl = false;
        for (int i = 0; i < this.size; ++i) {
            object = this.apply(i);
            Type type2 = ((RecursivePoint.RecursivePointType)object).unfold();
            boolean bl2 = true;
            RewriteResult rewriteResult2 = DataFixUtils.orElse(type2.everywhere(typeRewriteRule, pointFreeRule, false, false), RewriteResult.nop(type2));
            if (!Objects.equals(rewriteResult2.view().function(), Functions.id())) {
                bl2 = false;
            }
            RecursivePoint.RecursivePointType recursivePointType2 = this.buildMuType(rewriteResult2.view().newType(), recursiveTypeFamily);
            boolean bl3 = this.cap2((List<RewriteResult<?, ?>>)arrayList, (RecursivePoint.RecursivePointType)object, typeRewriteRule, pointFreeRule, bl2, rewriteResult2, recursivePointType2);
            bl = bl || !bl3;
        }
        if (!bl) {
            return Optional.empty();
        }
        ListAlgebra listAlgebra = new ListAlgebra("everywhere", arrayList);
        object = this.fold(listAlgebra).apply(n);
        return Optional.of(RewriteResult.create(RecursiveTypeFamily.viewUnchecked(this.apply(n), recursivePointType, ((RewriteResult)object).view().function()), ((RewriteResult)object).recData()));
    }

    private <A, B> boolean cap2(List<RewriteResult<?, ?>> list, RecursivePoint.RecursivePointType<A> recursivePointType, TypeRewriteRule typeRewriteRule, PointFreeRule pointFreeRule, boolean bl, RewriteResult<?, ?> rewriteResult, RecursivePoint.RecursivePointType<B> recursivePointType2) {
        RewriteResult<?, B> rewriteResult2 = RewriteResult.create(recursivePointType2.in(), new BitSet()).compose(rewriteResult);
        Optional<RewriteResult<B, ?>> optional = typeRewriteRule.rewrite(rewriteResult2.view().newType());
        if (optional.isPresent() && !Objects.equals(optional.get().view().function(), Functions.id())) {
            bl = false;
            rewriteResult = optional.get().compose(rewriteResult2);
        }
        rewriteResult = RewriteResult.create(rewriteResult.view().rewriteOrNop(pointFreeRule), rewriteResult.recData());
        list.add(rewriteResult);
        return bl;
    }

    public String toString() {
        return "Mu[" + this.name + ", " + this.size + ", " + this.template + "]";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof RecursiveTypeFamily)) {
            return true;
        }
        RecursiveTypeFamily recursiveTypeFamily = (RecursiveTypeFamily)object;
        return Objects.equals(this.template, recursiveTypeFamily.template);
    }

    public int hashCode() {
        return this.hashCode;
    }

    public Type apply(int n) {
        return this.apply(n);
    }

    private TypedOptic lambda$mkSimpleOptic$5(RecursivePoint.RecursivePointType recursivePointType, RecursivePoint.RecursivePointType recursivePointType2, TypedOptic typedOptic) {
        return this.mkOptic(recursivePointType, recursivePointType2, typedOptic.aType(), typedOptic.bType(), new OpticParts(typedOptic.bounds(), typedOptic.optic()));
    }

    private Either lambda$findType$4(int n, boolean bl, Type type, Type type2, Type.TypeMatcher typeMatcher, TypedOptic typedOptic) {
        TypeTemplate typeTemplate = typedOptic.tType().template();
        ArrayList arrayList = Lists.newArrayList();
        RecursiveTypeFamily recursiveTypeFamily = new RecursiveTypeFamily(this.name, typeTemplate);
        Type type3 = this.apply(n);
        Type type4 = recursiveTypeFamily.apply(n);
        if (bl) {
            FamilyOptic familyOptic = arg_0 -> RecursiveTypeFamily.lambda$null$3(arrayList, arg_0);
            arrayList.add(this.template.applyO(familyOptic, type, type2));
            OpticParts opticParts = ((FamilyOptic)arrayList.get(0)).apply(n);
            return Either.left(this.mkOptic(type3, type4, type, type2, opticParts));
        }
        return this.mkSimpleOptic((RecursivePoint.RecursivePointType)type3, (RecursivePoint.RecursivePointType)type4, type, type2, typeMatcher);
    }

    private static OpticParts lambda$null$3(List list, int n) {
        return ((FamilyOptic)list.get(0)).apply(n);
    }

    private RecursivePoint.RecursivePointType lambda$apply$2(Integer n) {
        return new RecursivePoint.RecursivePointType(this, n, () -> this.lambda$null$1(n));
    }

    private Type lambda$null$1(Integer n) {
        return this.template.apply(this).apply(n);
    }

    private RewriteResult lambda$fold$0(Algebra algebra, int n) {
        RewriteResult<?, ?> rewriteResult = algebra.apply(n);
        return RewriteResult.create(RecursiveTypeFamily.viewUnchecked(rewriteResult.view().type(), rewriteResult.view().newType(), Functions.fold(this.apply(n), rewriteResult, algebra, n)), rewriteResult.recData());
    }
}

