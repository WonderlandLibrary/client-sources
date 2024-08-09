/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.serialization.DynamicOps;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Keyable {
    public <T> Stream<T> keys(DynamicOps<T> var1);

    public static Keyable forStrings(Supplier<Stream<String>> supplier) {
        return new Keyable(supplier){
            final Supplier val$keys;
            {
                this.val$keys = supplier;
            }

            @Override
            public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                return ((Stream)this.val$keys.get()).map(dynamicOps::createString);
            }
        };
    }
}

