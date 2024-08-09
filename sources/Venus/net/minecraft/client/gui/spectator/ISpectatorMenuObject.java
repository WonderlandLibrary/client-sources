/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.spectator;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.util.text.ITextComponent;

public interface ISpectatorMenuObject {
    public void selectItem(SpectatorMenu var1);

    public ITextComponent getSpectatorName();

    public void func_230485_a_(MatrixStack var1, float var2, int var3);

    public boolean isEnabled();
}

