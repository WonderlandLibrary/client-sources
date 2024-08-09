/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FurnaceScreen
extends AbstractFurnaceScreen<FurnaceContainer> {
    private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");

    public FurnaceScreen(FurnaceContainer furnaceContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(furnaceContainer, new FurnaceRecipeGui(), playerInventory, iTextComponent, FURNACE_GUI_TEXTURES);
    }
}

