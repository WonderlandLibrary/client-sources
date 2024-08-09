/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class WorkingScreen
extends Screen
implements IProgressUpdate {
    @Nullable
    private ITextComponent field_238648_a_;
    @Nullable
    private ITextComponent stage;
    private int progress;
    private boolean doneWorking;
    private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();

    public WorkingScreen() {
        super(NarratorChatListener.EMPTY);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void displaySavingString(ITextComponent iTextComponent) {
        this.resetProgressAndMessage(iTextComponent);
    }

    @Override
    public void resetProgressAndMessage(ITextComponent iTextComponent) {
        this.field_238648_a_ = iTextComponent;
        this.displayLoadingString(new TranslationTextComponent("progress.working"));
    }

    @Override
    public void displayLoadingString(ITextComponent iTextComponent) {
        this.stage = iTextComponent;
        this.setLoadingProgress(0);
    }

    @Override
    public void setLoadingProgress(int n) {
        this.progress = n;
    }

    @Override
    public void setDoneWorking() {
        this.doneWorking = true;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.doneWorking) {
            if (!this.minecraft.isConnectedToRealms()) {
                this.minecraft.displayGuiScreen(null);
            }
        } else {
            if (this.customLoadingScreen != null && this.minecraft.world == null) {
                this.customLoadingScreen.drawBackground(this.width, this.height);
            } else {
                this.renderBackground(matrixStack);
            }
            if (this.progress > 0) {
                if (this.field_238648_a_ != null) {
                    WorkingScreen.drawCenteredString(matrixStack, this.font, this.field_238648_a_, this.width / 2, 70, 0xFFFFFF);
                }
                if (this.stage != null && this.progress != 0) {
                    WorkingScreen.drawCenteredString(matrixStack, this.font, new StringTextComponent("").append(this.stage).appendString(" " + this.progress + "%"), this.width / 2, 90, 0xFFFFFF);
                }
            }
            super.render(matrixStack, n, n2, f);
        }
    }
}

