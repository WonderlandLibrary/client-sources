/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.stream.Stream;

public final class KeyCompressor<T> {
    private final Int2ObjectMap<T> decompress = new Int2ObjectArrayMap<T>();
    private final Object2IntMap<T> compress = new Object2IntArrayMap<T>();
    private final Object2IntMap<String> compressString = new Object2IntArrayMap<String>();
    private final int size;
    private final DynamicOps<T> ops;

    public KeyCompressor(DynamicOps<T> dynamicOps, Stream<T> stream) {
        this.ops = dynamicOps;
        this.compressString.defaultReturnValue(-1);
        stream.forEach(arg_0 -> this.lambda$new$1(dynamicOps, arg_0));
        this.size = this.compress.size();
    }

    public T decompress(int n) {
        return (T)this.decompress.get(n);
    }

    public int compress(String string) {
        int n = this.compressString.getInt(string);
        return n == -1 ? this.compress(this.ops.createString(string)) : n;
    }

    public int compress(T t) {
        return (Integer)this.compress.get(t);
    }

    public int size() {
        return this.size;
    }

    private void lambda$new$1(DynamicOps dynamicOps, Object object) {
        if (this.compress.containsKey(object)) {
            return;
        }
        int n = this.compress.size();
        this.compress.put((T)object, n);
        dynamicOps.getStringValue(object).result().ifPresent(arg_0 -> this.lambda$null$0(n, arg_0));
        this.decompress.put(n, (T)object);
    }

    private void lambda$null$0(int n, String string) {
        this.compressString.put(string, n);
    }
}

