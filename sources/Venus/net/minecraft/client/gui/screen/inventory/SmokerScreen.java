/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import net.minecraft.client.gui.recipebook.SmokerRecipeGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.SmokerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SmokerScreen
extends AbstractFurnaceScreen<SmokerContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/smoker.png");

    public SmokerScreen(SmokerContainer smokerContainer, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(smokerContainer, new SmokerRecipeGui(), playerInventory, iTextComponent, GUI_TEXTURE);
    }
}

