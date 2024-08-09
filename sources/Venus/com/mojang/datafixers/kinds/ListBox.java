/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.kinds.Traversable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ListBox<T>
implements App<Mu, T> {
    private final List<T> value;

    public static <T> List<T> unbox(App<Mu, T> app) {
        return ((ListBox)app).value;
    }

    public static <T> ListBox<T> create(List<T> list) {
        return new ListBox<T>(list);
    }

    private ListBox(List<T> list) {
        this.value = list;
    }

    public static <F extends K1, A, B> App<F, List<B>> traverse(Applicative<F, ?> applicative, Function<A, App<F, B>> function, List<A> list) {
        return applicative.map(ListBox::unbox, Instance.INSTANCE.traverse(applicative, function, ListBox.create(list)));
    }

    public static <F extends K1, A> App<F, List<A>> flip(Applicative<F, ?> applicative, List<App<F, A>> list) {
        return applicative.map(ListBox::unbox, Instance.INSTANCE.flip(applicative, ListBox.create(list)));
    }

    public static enum Instance implements Traversable<com.mojang.datafixers.kinds.ListBox$Mu, Mu>
    {
        INSTANCE;


        @Override
        public <T, R> App<com.mojang.datafixers.kinds.ListBox$Mu, R> map(Function<? super T, ? extends R> function, App<com.mojang.datafixers.kinds.ListBox$Mu, T> app) {
            return ListBox.create(ListBox.unbox(app).stream().map(function).collect(Collectors.toList()));
        }

        @Override
        public <F extends K1, A, B> App<F, App<com.mojang.datafixers.kinds.ListBox$Mu, B>> traverse(Applicative<F, ?> applicative, Function<A, App<F, B>> function, App<com.mojang.datafixers.kinds.ListBox$Mu, A> app) {
            List<A> list = ListBox.unbox(app);
            App app2 = applicative.point(ImmutableList.builder());
            for (A a : list) {
                App<F, B> app3 = function.apply(a);
                app2 = applicative.ap2(applicative.point(ImmutableList.Builder::add), app2, app3);
            }
            return applicative.map(Instance::lambda$traverse$0, app2);
        }

        private static App lambda$traverse$0(ImmutableList.Builder builder) {
            return ListBox.create(builder.build());
        }

        public static final class Mu
        implements Traversable.Mu {
        }
    }

    public static final class Mu
    implements K1 {
    }
}

