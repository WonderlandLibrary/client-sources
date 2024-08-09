/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public interface DataFixer {
    public <T> Dynamic<T> update(DSL.TypeReference var1, Dynamic<T> var2, int var3, int var4);

    public Schema getSchema(int var1);
}

