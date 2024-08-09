/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;

public class ChangePageButton
extends Button {
    private final boolean isForward;
    private final boolean playTurnSound;

    public ChangePageButton(int n, int n2, boolean bl, Button.IPressable iPressable, boolean bl2) {
        super(n, n2, 23, 13, StringTextComponent.EMPTY, iPressable);
        this.isForward = bl;
        this.playTurnSound = bl2;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getInstance().getTextureManager().bindTexture(ReadBookScreen.BOOK_TEXTURES);
        int n3 = 0;
        int n4 = 192;
        if (this.isHovered()) {
            n3 += 23;
        }
        if (!this.isForward) {
            n4 += 13;
        }
        this.blit(matrixStack, this.x, this.y, n3, n4, 23, 13);
    }

    @Override
    public void playDownSound(SoundHandler soundHandler) {
        if (this.playTurnSound) {
            soundHandler.play(SimpleSound.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0f));
        }
    }
}

