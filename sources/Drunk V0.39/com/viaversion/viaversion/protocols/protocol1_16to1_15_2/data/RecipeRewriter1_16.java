/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;

public class RecipeRewriter1_16
extends RecipeRewriter1_14 {
    public RecipeRewriter1_16(Protocol protocol) {
        super(protocol);
        this.recipeHandlers.put("smithing", this::handleSmithing);
    }

    public void handleSmithing(PacketWrapper wrapper) throws Exception {
        Item[] ingredients;
        Item[] baseIngredients;
        for (Item item : baseIngredients = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
            this.rewrite(item);
        }
        Item[] itemArray = ingredients = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
        int n = itemArray.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                this.rewrite(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                return;
            }
            Item item = itemArray[n2];
            this.rewrite(item);
            ++n2;
        }
    }
}

