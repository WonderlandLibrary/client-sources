/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class RecipeRewriter<C extends ClientboundPacketType> {
    protected final Protocol<C, ?, ?, ?> protocol;
    protected final Map<String, RecipeConsumer> recipeHandlers = new HashMap<String, RecipeConsumer>();

    public RecipeRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
        this.recipeHandlers.put("crafting_shapeless", this::handleCraftingShapeless);
        this.recipeHandlers.put("crafting_shaped", this::handleCraftingShaped);
        this.recipeHandlers.put("smelting", this::handleSmelting);
        this.recipeHandlers.put("blasting", this::handleSmelting);
        this.recipeHandlers.put("smoking", this::handleSmelting);
        this.recipeHandlers.put("campfire_cooking", this::handleSmelting);
        this.recipeHandlers.put("stonecutting", this::handleStonecutting);
        this.recipeHandlers.put("smithing", this::handleSmithing);
        this.recipeHandlers.put("smithing_transform", this::handleSmithingTransform);
        this.recipeHandlers.put("smithing_trim", this::handleSmithingTrim);
        this.recipeHandlers.put("crafting_decorated_pot", this::handleSimpleRecipe);
    }

    public void handleRecipeType(PacketWrapper packetWrapper, String string) throws Exception {
        RecipeConsumer recipeConsumer = this.recipeHandlers.get(string);
        if (recipeConsumer != null) {
            recipeConsumer.accept(packetWrapper);
        }
    }

    public void register(C c) {
        this.protocol.registerClientbound(c, this::lambda$register$0);
    }

    public void handleCraftingShaped(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT) * packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.STRING);
        for (int i = 0; i < n; ++i) {
            this.handleIngredient(packetWrapper);
        }
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    public void handleCraftingShapeless(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        this.handleIngredients(packetWrapper);
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    public void handleSmelting(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        this.handleIngredient(packetWrapper);
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
        packetWrapper.passthrough(Type.FLOAT);
        packetWrapper.passthrough(Type.VAR_INT);
    }

    public void handleStonecutting(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        this.handleIngredient(packetWrapper);
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    public void handleSmithing(PacketWrapper packetWrapper) throws Exception {
        this.handleIngredient(packetWrapper);
        this.handleIngredient(packetWrapper);
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    public void handleSimpleRecipe(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
    }

    public void handleSmithingTransform(PacketWrapper packetWrapper) throws Exception {
        this.handleIngredient(packetWrapper);
        this.handleIngredient(packetWrapper);
        this.handleIngredient(packetWrapper);
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    public void handleSmithingTrim(PacketWrapper packetWrapper) throws Exception {
        this.handleIngredient(packetWrapper);
        this.handleIngredient(packetWrapper);
        this.handleIngredient(packetWrapper);
    }

    protected void rewrite(@Nullable Item item) {
        if (this.protocol.getItemRewriter() != null) {
            this.protocol.getItemRewriter().handleItemToClient(item);
        }
    }

    protected void handleIngredient(PacketWrapper packetWrapper) throws Exception {
        Item[] itemArray;
        for (Item item : itemArray = packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
            this.rewrite(item);
        }
    }

    protected void handleIngredients(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            this.handleIngredient(packetWrapper);
        }
    }

    private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.passthrough(Type.STRING);
            packetWrapper.passthrough(Type.STRING);
            this.handleRecipeType(packetWrapper, Key.stripMinecraftNamespace(string));
        }
    }

    @FunctionalInterface
    public static interface RecipeConsumer {
        public void accept(PacketWrapper var1) throws Exception;
    }
}

