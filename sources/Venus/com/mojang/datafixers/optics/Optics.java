/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.FunctionType;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.Adapter;
import com.mojang.datafixers.optics.Affine;
import com.mojang.datafixers.optics.Forget;
import com.mojang.datafixers.optics.ForgetE;
import com.mojang.datafixers.optics.ForgetOpt;
import com.mojang.datafixers.optics.Getter;
import com.mojang.datafixers.optics.Grate;
import com.mojang.datafixers.optics.IdAdapter;
import com.mojang.datafixers.optics.Inj1;
import com.mojang.datafixers.optics.Inj2;
import com.mojang.datafixers.optics.Lens;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.PStore;
import com.mojang.datafixers.optics.Prism;
import com.mojang.datafixers.optics.Proj1;
import com.mojang.datafixers.optics.Proj2;
import com.mojang.datafixers.optics.ReForget;
import com.mojang.datafixers.optics.ReForgetC;
import com.mojang.datafixers.optics.ReForgetE;
import com.mojang.datafixers.optics.ReForgetEP;
import com.mojang.datafixers.optics.ReForgetP;
import com.mojang.datafixers.optics.Traversal;
import com.mojang.datafixers.optics.profunctors.AffineP;
import com.mojang.datafixers.optics.profunctors.Cartesian;
import com.mojang.datafixers.optics.profunctors.Cocartesian;
import com.mojang.datafixers.optics.profunctors.GetterP;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import com.mojang.datafixers.optics.profunctors.TraversalP;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Optics {
    public static <S, T, A, B> Adapter<S, T, A, B> toAdapter(Optic<? super Profunctor.Mu, S, T, A, B> optic) {
        Function function = optic.eval(new Adapter.Instance());
        return Adapter.unbox(function.apply(Optics.adapter(Function.identity(), Function.identity())));
    }

    public static <S, T, A, B> Lens<S, T, A, B> toLens(Optic<? super Cartesian.Mu, S, T, A, B> optic) {
        Function function = optic.eval(new Lens.Instance());
        return Lens.unbox(function.apply(Optics.lens(Function.identity(), Optics::lambda$toLens$0)));
    }

    public static <S, T, A, B> Prism<S, T, A, B> toPrism(Optic<? super Cocartesian.Mu, S, T, A, B> optic) {
        Function function = optic.eval(new Prism.Instance());
        return Prism.unbox(function.apply(Optics.prism(Either::right, Function.identity())));
    }

    public static <S, T, A, B> Affine<S, T, A, B> toAffine(Optic<? super AffineP.Mu, S, T, A, B> optic) {
        Function function = optic.eval(new Affine.Instance());
        return Affine.unbox(function.apply(Optics.affine(Either::right, Optics::lambda$toAffine$1)));
    }

    public static <S, T, A, B> Getter<S, T, A, B> toGetter(Optic<? super GetterP.Mu, S, T, A, B> optic) {
        Function function = optic.eval(new Getter.Instance());
        return Getter.unbox(function.apply(Optics.getter(Function.identity())));
    }

    public static <S, T, A, B> Traversal<S, T, A, B> toTraversal(Optic<? super TraversalP.Mu, S, T, A, B> optic) {
        Function function = optic.eval(new Traversal.Instance());
        return Traversal.unbox(function.apply(new Traversal<A, B, A, B>(){

            @Override
            public <F extends K1> FunctionType<A, App<F, B>> wander(Applicative<F, ?> applicative, FunctionType<A, App<F, B>> functionType) {
                return functionType;
            }
        }));
    }

    static <S, T, A, B, F> Lens<S, T, Pair<F, A>, B> merge(Lens<S, ?, F, ?> lens, Lens<S, T, A, B> lens2) {
        return Optics.lens(arg_0 -> Optics.lambda$merge$2(lens, lens2, arg_0), lens2::update);
    }

    public static <S, T> Adapter<S, T, S, T> id() {
        return new IdAdapter();
    }

    public static <S, T, A, B> Adapter<S, T, A, B> adapter(Function<S, A> function, Function<B, T> function2) {
        return new Adapter<S, T, A, B>(function, function2){
            final Function val$from;
            final Function val$to;
            {
                this.val$from = function;
                this.val$to = function2;
            }

            @Override
            public A from(S s) {
                return this.val$from.apply(s);
            }

            @Override
            public T to(B b) {
                return this.val$to.apply(b);
            }
        };
    }

    public static <S, T, A, B> Lens<S, T, A, B> lens(Function<S, A> function, BiFunction<B, S, T> biFunction) {
        return new Lens<S, T, A, B>(function, biFunction){
            final Function val$view;
            final BiFunction val$update;
            {
                this.val$view = function;
                this.val$update = biFunction;
            }

            @Override
            public A view(S s) {
                return this.val$view.apply(s);
            }

            @Override
            public T update(B b, S s) {
                return this.val$update.apply(b, s);
            }
        };
    }

    public static <S, T, A, B> Prism<S, T, A, B> prism(Function<S, Either<T, A>> function, Function<B, T> function2) {
        return new Prism<S, T, A, B>(function, function2){
            final Function val$match;
            final Function val$build;
            {
                this.val$match = function;
                this.val$build = function2;
            }

            @Override
            public Either<T, A> match(S s) {
                return (Either)this.val$match.apply(s);
            }

            @Override
            public T build(B b) {
                return this.val$build.apply(b);
            }
        };
    }

    public static <S, T, A, B> Affine<S, T, A, B> affine(Function<S, Either<T, A>> function, BiFunction<B, S, T> biFunction) {
        return new Affine<S, T, A, B>(function, biFunction){
            final Function val$preview;
            final BiFunction val$build;
            {
                this.val$preview = function;
                this.val$build = biFunction;
            }

            @Override
            public Either<T, A> preview(S s) {
                return (Either)this.val$preview.apply(s);
            }

            @Override
            public T set(B b, S s) {
                return this.val$build.apply(b, s);
            }
        };
    }

    public static <S, T, A, B> Getter<S, T, A, B> getter(Function<S, A> function) {
        return function::apply;
    }

    public static <R, A, B> Forget<R, A, B> forget(Function<A, R> function) {
        return function::apply;
    }

    public static <R, A, B> ForgetOpt<R, A, B> forgetOpt(Function<A, Optional<R>> function) {
        return function::apply;
    }

    public static <R, A, B> ForgetE<R, A, B> forgetE(Function<A, Either<B, R>> function) {
        return function::apply;
    }

    public static <R, A, B> ReForget<R, A, B> reForget(Function<R, B> function) {
        return function::apply;
    }

    public static <S, T, A, B> Grate<S, T, A, B> grate(FunctionType<FunctionType<FunctionType<S, A>, B>, T> functionType) {
        return functionType::apply;
    }

    public static <R, A, B> ReForgetEP<R, A, B> reForgetEP(String string, Function<Either<A, Pair<A, R>>, B> function) {
        return new ReForgetEP<R, A, B>(function, string){
            final Function val$function;
            final String val$name;
            {
                this.val$function = function;
                this.val$name = string;
            }

            @Override
            public B run(Either<A, Pair<A, R>> either) {
                return this.val$function.apply(either);
            }

            public String toString() {
                return "ReForgetEP_" + this.val$name;
            }
        };
    }

    public static <R, A, B> ReForgetE<R, A, B> reForgetE(String string, Function<Either<A, R>, B> function) {
        return new ReForgetE<R, A, B>(function, string){
            final Function val$function;
            final String val$name;
            {
                this.val$function = function;
                this.val$name = string;
            }

            @Override
            public B run(Either<A, R> either) {
                return this.val$function.apply(either);
            }

            public String toString() {
                return "ReForgetE_" + this.val$name;
            }
        };
    }

    public static <R, A, B> ReForgetP<R, A, B> reForgetP(String string, BiFunction<A, R, B> biFunction) {
        return new ReForgetP<R, A, B>(biFunction, string){
            final BiFunction val$function;
            final String val$name;
            {
                this.val$function = biFunction;
                this.val$name = string;
            }

            @Override
            public B run(A a, R r) {
                return this.val$function.apply(a, r);
            }

            public String toString() {
                return "ReForgetP_" + this.val$name;
            }
        };
    }

    public static <R, A, B> ReForgetC<R, A, B> reForgetC(String string, Either<Function<R, B>, BiFunction<A, R, B>> either) {
        return new ReForgetC<R, A, B>(either, string){
            final Either val$either;
            final String val$name;
            {
                this.val$either = either;
                this.val$name = string;
            }

            @Override
            public Either<Function<R, B>, BiFunction<A, R, B>> impl() {
                return this.val$either;
            }

            public String toString() {
                return "ReForgetC_" + this.val$name;
            }
        };
    }

    public static <I, J, X> PStore<I, J, X> pStore(Function<J, X> function, Supplier<I> supplier) {
        return new PStore<I, J, X>(function, supplier){
            final Function val$peek;
            final Supplier val$pos;
            {
                this.val$peek = function;
                this.val$pos = supplier;
            }

            @Override
            public X peek(J j) {
                return this.val$peek.apply(j);
            }

            @Override
            public I pos() {
                return this.val$pos.get();
            }
        };
    }

    public static <A, B> Function<A, B> getFunc(App2<FunctionType.Mu, A, B> app2) {
        return FunctionType.unbox(app2);
    }

    public static <F, G, F2> Proj1<F, G, F2> proj1() {
        return new Proj1();
    }

    public static <F, G, G2> Proj2<F, G, G2> proj2() {
        return new Proj2();
    }

    public static <F, G, F2> Inj1<F, G, F2> inj1() {
        return new Inj1();
    }

    public static <F, G, G2> Inj2<F, G, G2> inj2() {
        return new Inj2();
    }

    public static <F, G, F2, G2, A, B> Lens<Either<F, G>, Either<F2, G2>, A, B> eitherLens(Lens<F, F2, A, B> lens, Lens<G, G2, A, B> lens2) {
        return Optics.lens(arg_0 -> Optics.lambda$eitherLens$3(lens, lens2, arg_0), (arg_0, arg_1) -> Optics.lambda$eitherLens$6(lens, lens2, arg_0, arg_1));
    }

    public static <F, G, F2, G2, A, B> Affine<Either<F, G>, Either<F2, G2>, A, B> eitherAffine(Affine<F, F2, A, B> affine, Affine<G, G2, A, B> affine2) {
        return Optics.affine(arg_0 -> Optics.lambda$eitherAffine$9(affine, affine2, arg_0), (arg_0, arg_1) -> Optics.lambda$eitherAffine$12(affine, affine2, arg_0, arg_1));
    }

    public static <F, G, F2, G2, A, B> Traversal<Either<F, G>, Either<F2, G2>, A, B> eitherTraversal(Traversal<F, F2, A, B> traversal, Traversal<G, G2, A, B> traversal2) {
        return new Traversal<Either<F, G>, Either<F2, G2>, A, B>(traversal, traversal2){
            final Traversal val$fOptic;
            final Traversal val$gOptic;
            {
                this.val$fOptic = traversal;
                this.val$gOptic = traversal2;
            }

            @Override
            public <FT extends K1> FunctionType<Either<F, G>, App<FT, Either<F2, G2>>> wander(Applicative<FT, ?> applicative, FunctionType<A, App<FT, B>> functionType) {
                return arg_0 -> 11.lambda$wander$2(applicative, this.val$fOptic, functionType, this.val$gOptic, arg_0);
            }

            private static App lambda$wander$2(Applicative applicative, Traversal traversal, FunctionType functionType, Traversal traversal2, Either either) {
                return either.map(arg_0 -> 11.lambda$null$0(applicative, traversal, functionType, arg_0), arg_0 -> 11.lambda$null$1(applicative, traversal2, functionType, arg_0));
            }

            private static App lambda$null$1(Applicative applicative, Traversal traversal, FunctionType functionType, Object object) {
                return applicative.ap(Either::right, traversal.wander(applicative, functionType).apply(object));
            }

            private static App lambda$null$0(Applicative applicative, Traversal traversal, FunctionType functionType, Object object) {
                return applicative.ap(Either::left, traversal.wander(applicative, functionType).apply(object));
            }
        };
    }

    private static Either lambda$eitherAffine$12(Affine affine, Affine affine2, Object object, Either either) {
        return either.mapBoth(arg_0 -> Optics.lambda$null$10(affine, object, arg_0), arg_0 -> Optics.lambda$null$11(affine2, object, arg_0));
    }

    private static Object lambda$null$11(Affine affine, Object object, Object object2) {
        return affine.set(object, object2);
    }

    private static Object lambda$null$10(Affine affine, Object object, Object object2) {
        return affine.set(object, object2);
    }

    private static Either lambda$eitherAffine$9(Affine affine, Affine affine2, Either either) {
        return either.map(arg_0 -> Optics.lambda$null$7(affine, arg_0), arg_0 -> Optics.lambda$null$8(affine2, arg_0));
    }

    private static Either lambda$null$8(Affine affine, Object object) {
        return affine.preview(object).mapLeft(Either::right);
    }

    private static Either lambda$null$7(Affine affine, Object object) {
        return affine.preview(object).mapLeft(Either::left);
    }

    private static Either lambda$eitherLens$6(Lens lens, Lens lens2, Object object, Either either) {
        return either.mapBoth(arg_0 -> Optics.lambda$null$4(lens, object, arg_0), arg_0 -> Optics.lambda$null$5(lens2, object, arg_0));
    }

    private static Object lambda$null$5(Lens lens, Object object, Object object2) {
        return lens.update(object, object2);
    }

    private static Object lambda$null$4(Lens lens, Object object, Object object2) {
        return lens.update(object, object2);
    }

    private static Object lambda$eitherLens$3(Lens lens, Lens lens2, Either either) {
        return either.map(lens::view, lens2::view);
    }

    private static Pair lambda$merge$2(Lens lens, Lens lens2, Object object) {
        return Pair.of(lens.view(object), lens2.view(object));
    }

    private static Object lambda$toAffine$1(Object object, Object object2) {
        return object;
    }

    private static Object lambda$toLens$0(Object object, Object object2) {
        return object;
    }
}

