/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.ui.mainmenu.MainScreen;
import mpp.venusfr.venusfr;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MemoryErrorScreen
extends Screen {
    public MemoryErrorScreen() {
        super(new StringTextComponent("Out of memory!"));
    }

    @Override
    protected void init() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        this.addButton(new Button(this.width / 2 - 155, this.height / 4 + 120 + 12, 150, 20, new TranslationTextComponent("gui.toTitle"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 155 + 160, this.height / 4 + 120 + 12, 150, 20, new TranslationTextComponent("menu.quit"), this::lambda$init$1));
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        MemoryErrorScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
        MemoryErrorScreen.drawString(matrixStack, this.font, "Minecraft has run out of memory.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 0xA0A0A0);
        MemoryErrorScreen.drawString(matrixStack, this.font, "This could be caused by a bug in the game or by the", this.width / 2 - 140, this.height / 4 - 60 + 60 + 18, 0xA0A0A0);
        MemoryErrorScreen.drawString(matrixStack, this.font, "Java Virtual Machine not being allocated enough", this.width / 2 - 140, this.height / 4 - 60 + 60 + 27, 0xA0A0A0);
        MemoryErrorScreen.drawString(matrixStack, this.font, "memory.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 36, 0xA0A0A0);
        MemoryErrorScreen.drawString(matrixStack, this.font, "To prevent level corruption, the current game has quit.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 54, 0xA0A0A0);
        MemoryErrorScreen.drawString(matrixStack, this.font, "We've tried to free up enough memory to let you go back to", this.width / 2 - 140, this.height / 4 - 60 + 60 + 63, 0xA0A0A0);
        MemoryErrorScreen.drawString(matrixStack, this.font, "the main menu and back to playing, but this may not have worked.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 72, 0xA0A0A0);
        MemoryErrorScreen.drawString(matrixStack, this.font, "Please restart the game if you see this message again.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 81, 0xA0A0A0);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        this.minecraft.shutdown();
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(new MainScreen());
    }
}

