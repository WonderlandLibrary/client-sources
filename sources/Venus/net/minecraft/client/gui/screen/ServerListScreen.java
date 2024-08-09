/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ServerListScreen
extends Screen {
    private static final ITextComponent field_243288_a = new TranslationTextComponent("addServer.enterIp");
    private Button field_195170_a;
    private final ServerData serverData;
    private TextFieldWidget ipEdit;
    private final BooleanConsumer field_213027_d;
    private final Screen previousScreen;

    public ServerListScreen(Screen screen, BooleanConsumer booleanConsumer, ServerData serverData) {
        super(new TranslationTextComponent("selectServer.direct"));
        this.previousScreen = screen;
        this.serverData = serverData;
        this.field_213027_d = booleanConsumer;
    }

    @Override
    public void tick() {
        this.ipEdit.tick();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (this.getListener() != this.ipEdit || n != 257 && n != 335) {
            return super.keyPressed(n, n2, n3);
        }
        this.func_195167_h();
        return false;
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_195170_a = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 20, new TranslationTextComponent("selectServer.select"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
        this.ipEdit = new TextFieldWidget(this.font, this.width / 2 - 100, 116, 200, 20, new TranslationTextComponent("addServer.enterIp"));
        this.ipEdit.setMaxStringLength(128);
        this.ipEdit.setFocused2(false);
        this.ipEdit.setText(this.minecraft.gameSettings.lastServer);
        this.ipEdit.setResponder(this::lambda$init$2);
        this.children.add(this.ipEdit);
        this.setFocusedDefault(this.ipEdit);
        this.func_195168_i();
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.ipEdit.getText();
        this.init(minecraft, n, n2);
        this.ipEdit.setText(string);
    }

    private void func_195167_h() {
        this.serverData.serverIP = this.ipEdit.getText();
        this.field_213027_d.accept(true);
    }

    @Override
    public void closeScreen() {
        this.minecraft.displayGuiScreen(this.previousScreen);
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.minecraft.gameSettings.lastServer = this.ipEdit.getText();
        this.minecraft.gameSettings.saveOptions();
    }

    private void func_195168_i() {
        String string = this.ipEdit.getText();
        this.field_195170_a.active = !string.isEmpty() && string.split(":").length > 0 && string.indexOf(32) == -1;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        ServerListScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        ServerListScreen.drawString(matrixStack, this.font, field_243288_a, this.width / 2 - 100, 100, 0xA0A0A0);
        this.ipEdit.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$2(String string) {
        this.func_195168_i();
    }

    private void lambda$init$1(Button button) {
        this.field_213027_d.accept(false);
    }

    private void lambda$init$0(Button button) {
        this.func_195167_h();
    }
}

