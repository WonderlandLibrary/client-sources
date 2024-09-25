/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.rewriters.ItemRewriter;
import us.myles.ViaVersion.api.rewriters.RecipeRewriter;
import us.myles.ViaVersion.api.type.Type;

public class RecipeRewriter1_13_2
extends RecipeRewriter {
    public RecipeRewriter1_13_2(Protocol protocol, ItemRewriter.RewriteFunction rewriter) {
        super(protocol, rewriter);
        this.recipeHandlers.put("crafting_shapeless", this::handleCraftingShapeless);
        this.recipeHandlers.put("crafting_shaped", this::handleCraftingShaped);
        this.recipeHandlers.put("smelting", this::handleSmelting);
    }

    public void handleSmelting(PacketWrapper wrapper) throws Exception {
        Item[] items;
        wrapper.passthrough(Type.STRING);
        for (Item item : items = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
            this.rewriter.rewrite(item);
        }
        this.rewriter.rewrite(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
        wrapper.passthrough(Type.FLOAT);
        wrapper.passthrough(Type.VAR_INT);
    }

    public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
        int ingredientsNo = wrapper.passthrough(Type.VAR_INT) * wrapper.passthrough(Type.VAR_INT);
        wrapper.passthrough(Type.STRING);
        for (int j = 0; j < ingredientsNo; ++j) {
            Item[] items;
            for (Item item : items = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
                this.rewriter.rewrite(item);
            }
        }
        this.rewriter.rewrite(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    public void handleCraftingShapeless(PacketWrapper wrapper) throws Exception {
        wrapper.passthrough(Type.STRING);
        int ingredientsNo = wrapper.passthrough(Type.VAR_INT);
        for (int j = 0; j < ingredientsNo; ++j) {
            Item[] items;
            for (Item item : items = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
                this.rewriter.rewrite(item);
            }
        }
        this.rewriter.rewrite(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
}

