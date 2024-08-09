/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.HTTPUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class ShareToLanScreen
extends Screen {
    private static final ITextComponent field_243310_a = new TranslationTextComponent("selectWorld.allowCommands");
    private static final ITextComponent field_243311_b = new TranslationTextComponent("selectWorld.gameMode");
    private static final ITextComponent field_243312_c = new TranslationTextComponent("lanServer.otherPlayers");
    private final Screen lastScreen;
    private Button allowCheatsButton;
    private Button gameModeButton;
    private String gameMode = "survival";
    private boolean allowCheats;

    public ShareToLanScreen(Screen screen) {
        super(new TranslationTextComponent("lanServer.title"));
        this.lastScreen = screen;
    }

    @Override
    protected void init() {
        this.addButton(new Button(this.width / 2 - 155, this.height - 28, 150, 20, new TranslationTextComponent("lanServer.start"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 + 5, this.height - 28, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
        this.gameModeButton = this.addButton(new Button(this.width / 2 - 155, 100, 150, 20, StringTextComponent.EMPTY, this::lambda$init$2));
        this.allowCheatsButton = this.addButton(new Button(this.width / 2 + 5, 100, 150, 20, field_243310_a, this::lambda$init$3));
        this.updateDisplayNames();
    }

    private void updateDisplayNames() {
        this.gameModeButton.setMessage(new TranslationTextComponent("options.generic_value", field_243311_b, new TranslationTextComponent("selectWorld.gameMode." + this.gameMode)));
        this.allowCheatsButton.setMessage(DialogTexts.getComposedOptionMessage(field_243310_a, this.allowCheats));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        ShareToLanScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 50, 0xFFFFFF);
        ShareToLanScreen.drawCenteredString(matrixStack, this.font, field_243312_c, this.width / 2, 82, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$3(Button button) {
        this.allowCheats = !this.allowCheats;
        this.updateDisplayNames();
    }

    private void lambda$init$2(Button button) {
        this.gameMode = "spectator".equals(this.gameMode) ? "creative" : ("creative".equals(this.gameMode) ? "adventure" : ("adventure".equals(this.gameMode) ? "survival" : "spectator"));
        this.updateDisplayNames();
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.lastScreen);
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(null);
        int n = HTTPUtil.getSuitableLanPort();
        TranslationTextComponent translationTextComponent = this.minecraft.getIntegratedServer().shareToLAN(GameType.getByName(this.gameMode), this.allowCheats, n) ? new TranslationTextComponent("commands.publish.started", n) : new TranslationTextComponent("commands.publish.failed");
        this.minecraft.ingameGUI.getChatGUI().printChatMessage(translationTextComponent);
        this.minecraft.setDefaultMinecraftTitle();
    }
}

