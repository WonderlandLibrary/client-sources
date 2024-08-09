/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class DownloadTerrainScreen
extends Screen {
    private static final ITextComponent field_243307_a = new TranslationTextComponent("multiplayer.downloadingTerrain");
    private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();

    public DownloadTerrainScreen() {
        super(NarratorChatListener.EMPTY);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.customLoadingScreen != null) {
            this.customLoadingScreen.drawBackground(this.width, this.height);
        } else {
            this.renderDirtBackground(0);
        }
        DownloadTerrainScreen.drawCenteredString(matrixStack, this.font, field_243307_a, this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}

