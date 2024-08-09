/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class RecipeRewriter1_19_3<C extends ClientboundPacketType>
extends RecipeRewriter<C> {
    public RecipeRewriter1_19_3(Protocol<C, ?, ?, ?> protocol) {
        super(protocol);
        this.recipeHandlers.put("crafting_special_armordye", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_bookcloning", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_mapcloning", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_mapextending", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_firework_rocket", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_firework_star", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_firework_star_fade", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_tippedarrow", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_bannerduplicate", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_shielddecoration", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_shulkerboxcoloring", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_suspiciousstew", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_repairitem", this::handleSimpleRecipe);
    }

    @Override
    public void handleCraftingShapeless(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        packetWrapper.passthrough(Type.VAR_INT);
        this.handleIngredients(packetWrapper);
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    @Override
    public void handleCraftingShaped(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT) * packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.STRING);
        packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            this.handleIngredient(packetWrapper);
        }
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    @Override
    public void handleSmelting(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        packetWrapper.passthrough(Type.VAR_INT);
        this.handleIngredient(packetWrapper);
        this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
        packetWrapper.passthrough(Type.FLOAT);
        packetWrapper.passthrough(Type.VAR_INT);
    }
}

