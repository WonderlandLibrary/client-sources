/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.optics;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Functor;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.optics.Optics;
import java.util.function.Function;

interface PStore<I, J, X>
extends App<Mu<I, J>, X> {
    public static <I, J, X> PStore<I, J, X> unbox(App<Mu<I, J>, X> app) {
        return (PStore)app;
    }

    public X peek(J var1);

    public I pos();

    public static final class Instance<I, J>
    implements Functor<com.mojang.datafixers.optics.PStore$Mu<I, J>, Mu<I, J>> {
        @Override
        public <T, R> App<com.mojang.datafixers.optics.PStore$Mu<I, J>, R> map(Function<? super T, ? extends R> function, App<com.mojang.datafixers.optics.PStore$Mu<I, J>, T> app) {
            PStore<I, J, T> pStore = PStore.unbox(app);
            return Optics.pStore(function.compose(pStore::peek)::apply, pStore::pos);
        }

        public static final class Mu<I, J>
        implements Functor.Mu {
        }
    }

    public static final class Mu<I, J>
    implements K1 {
    }
}

