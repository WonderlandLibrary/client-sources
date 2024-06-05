/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.rewriter.Rewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ItemRewriter<T extends Protocol>
extends Rewriter<T> {
    public @Nullable Item handleItemToClient(@Nullable Item var1);

    public @Nullable Item handleItemToServer(@Nullable Item var1);
}

