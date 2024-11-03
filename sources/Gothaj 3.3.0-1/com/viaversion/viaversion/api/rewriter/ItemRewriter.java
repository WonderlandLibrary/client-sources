package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ItemRewriter<T extends Protocol> extends Rewriter<T> {
   @Nullable
   Item handleItemToClient(@Nullable Item var1);

   @Nullable
   Item handleItemToServer(@Nullable Item var1);
}
