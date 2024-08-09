/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

public class DemoScreen
extends Screen {
    private static final ResourceLocation DEMO_BACKGROUND_LOCATION = new ResourceLocation("textures/gui/demo_background.png");
    private IBidiRenderer field_243286_b = IBidiRenderer.field_243257_a;
    private IBidiRenderer field_243287_c = IBidiRenderer.field_243257_a;

    public DemoScreen() {
        super(new TranslationTextComponent("demo.help.title"));
    }

    @Override
    protected void init() {
        int n = -16;
        this.addButton(new Button(this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20, new TranslationTextComponent("demo.help.buy"), DemoScreen::lambda$init$0));
        this.addButton(new Button(this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20, new TranslationTextComponent("demo.help.later"), this::lambda$init$1));
        GameSettings gameSettings = this.minecraft.gameSettings;
        this.field_243286_b = IBidiRenderer.func_243260_a(this.font, new TranslationTextComponent("demo.help.movementShort", gameSettings.keyBindForward.func_238171_j_(), gameSettings.keyBindLeft.func_238171_j_(), gameSettings.keyBindBack.func_238171_j_(), gameSettings.keyBindRight.func_238171_j_()), new TranslationTextComponent("demo.help.movementMouse"), new TranslationTextComponent("demo.help.jump", gameSettings.keyBindJump.func_238171_j_()), new TranslationTextComponent("demo.help.inventory", gameSettings.keyBindInventory.func_238171_j_()));
        this.field_243287_c = IBidiRenderer.func_243258_a(this.font, new TranslationTextComponent("demo.help.fullWrapped"), 218);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(DEMO_BACKGROUND_LOCATION);
        int n = (this.width - 248) / 2;
        int n2 = (this.height - 166) / 2;
        this.blit(matrixStack, n, n2, 0, 0, 248, 166);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        int n3 = (this.width - 248) / 2 + 10;
        int n4 = (this.height - 166) / 2 + 8;
        this.font.func_243248_b(matrixStack, this.title, n3, n4, 0x1F1F1F);
        n4 = this.field_243286_b.func_241866_c(matrixStack, n3, n4 + 12, 12, 0x4F4F4F);
        this.field_243287_c.func_241866_c(matrixStack, n3, n4 + 20, 9, 0x1F1F1F);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(null);
        this.minecraft.mouseHelper.grabMouse();
    }

    private static void lambda$init$0(Button button) {
        button.active = false;
        Util.getOSType().openURI("http://www.minecraft.net/store?source=demo");
    }
}

