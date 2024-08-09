/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.google.common.reflect.TypeToken;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.K2;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public interface Optic<Proof extends K1, S, T, A, B> {
    public <P extends K2> Function<App2<P, A, B>, App2<P, S, T>> eval(App<? extends Proof, P> var1);

    default public <Proof2 extends Proof, A1, B1> Optic<Proof2, S, T, A1, B1> compose(Optic<? super Proof2, A, B, A1, B1> optic) {
        return new CompositionOptic(this, optic);
    }

    default public <Proof2 extends K1, A1, B1> Optic<?, S, T, A1, B1> composeUnchecked(Optic<?, A, B, A1, B1> optic) {
        return new CompositionOptic(this, optic);
    }

    default public <Proof2 extends K1> Optional<Optic<? super Proof2, S, T, A, B>> upCast(Set<TypeToken<? extends K1>> set, TypeToken<Proof2> typeToken) {
        if (set.stream().allMatch(arg_0 -> Optic.lambda$upCast$0(typeToken, arg_0))) {
            return Optional.of(this);
        }
        return Optional.empty();
    }

    private static boolean lambda$upCast$0(TypeToken typeToken, TypeToken typeToken2) {
        return typeToken2.isSupertypeOf(typeToken);
    }

    public static final class CompositionOptic<Proof extends K1, S, T, A, B, A1, B1>
    implements Optic<Proof, S, T, A1, B1> {
        protected final Optic<? super Proof, S, T, A, B> outer;
        protected final Optic<? super Proof, A, B, A1, B1> inner;

        public CompositionOptic(Optic<? super Proof, S, T, A, B> optic, Optic<? super Proof, A, B, A1, B1> optic2) {
            this.outer = optic;
            this.inner = optic2;
        }

        @Override
        public <P extends K2> Function<App2<P, A1, B1>, App2<P, S, T>> eval(App<? extends Proof, P> app) {
            return this.outer.eval(app).compose(this.inner.eval(app));
        }

        public String toString() {
            return "(" + this.outer + " \u25e6 " + this.inner + ")";
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            CompositionOptic compositionOptic = (CompositionOptic)object;
            return Objects.equals(this.outer, compositionOptic.outer) && Objects.equals(this.inner, compositionOptic.inner);
        }

        public int hashCode() {
            return Objects.hash(this.outer, this.inner);
        }

        public Optic<? super Proof, S, T, A, B> outer() {
            return this.outer;
        }

        public Optic<? super Proof, A, B, A1, B1> inner() {
            return this.inner;
        }
    }
}

