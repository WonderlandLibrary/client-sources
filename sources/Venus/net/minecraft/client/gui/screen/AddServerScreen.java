/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.net.IDN;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AddServerScreen
extends Screen {
    private static final ITextComponent field_243290_a = new TranslationTextComponent("addServer.enterName");
    private static final ITextComponent field_243291_b = new TranslationTextComponent("addServer.enterIp");
    private Button buttonAddServer;
    private final BooleanConsumer field_213032_b;
    private final ServerData serverData;
    private TextFieldWidget textFieldServerAddress;
    private TextFieldWidget textFieldServerName;
    private Button buttonResourcePack;
    private final Screen field_228179_g_;
    private final Predicate<String> addressFilter = AddServerScreen::lambda$new$0;

    public AddServerScreen(Screen screen, BooleanConsumer booleanConsumer, ServerData serverData) {
        super(new TranslationTextComponent("addServer.title"));
        this.field_228179_g_ = screen;
        this.field_213032_b = booleanConsumer;
        this.serverData = serverData;
    }

    @Override
    public void tick() {
        this.textFieldServerName.tick();
        this.textFieldServerAddress.tick();
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.textFieldServerName = new TextFieldWidget(this.font, this.width / 2 - 100, 66, 200, 20, new TranslationTextComponent("addServer.enterName"));
        this.textFieldServerName.setFocused2(false);
        this.textFieldServerName.setText(this.serverData.serverName);
        this.textFieldServerName.setResponder(this::func_213028_a);
        this.children.add(this.textFieldServerName);
        this.textFieldServerAddress = new TextFieldWidget(this.font, this.width / 2 - 100, 106, 200, 20, new TranslationTextComponent("addServer.enterIp"));
        this.textFieldServerAddress.setMaxStringLength(128);
        this.textFieldServerAddress.setText(this.serverData.serverIP);
        this.textFieldServerAddress.setValidator(this.addressFilter);
        this.textFieldServerAddress.setResponder(this::func_213028_a);
        this.children.add(this.textFieldServerAddress);
        this.buttonResourcePack = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 72, 200, 20, AddServerScreen.func_238624_a_(this.serverData.getResourceMode()), this::lambda$init$1));
        this.buttonAddServer = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, new TranslationTextComponent("addServer.add"), this::lambda$init$2));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20, DialogTexts.GUI_CANCEL, this::lambda$init$3));
        this.func_228180_b_();
    }

    private static ITextComponent func_238624_a_(ServerData.ServerResourceMode serverResourceMode) {
        return new TranslationTextComponent("addServer.resourcePack").appendString(": ").append(serverResourceMode.getMotd());
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.textFieldServerAddress.getText();
        String string2 = this.textFieldServerName.getText();
        this.init(minecraft, n, n2);
        this.textFieldServerAddress.setText(string);
        this.textFieldServerName.setText(string2);
    }

    private void func_213028_a(String string) {
        this.func_228180_b_();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    private void onButtonServerAddPressed() {
        this.serverData.serverName = this.textFieldServerName.getText();
        this.serverData.serverIP = this.textFieldServerAddress.getText();
        this.field_213032_b.accept(true);
    }

    @Override
    public void closeScreen() {
        this.func_228180_b_();
        this.minecraft.displayGuiScreen(this.field_228179_g_);
    }

    private void func_228180_b_() {
        String string = this.textFieldServerAddress.getText();
        boolean bl = !string.isEmpty() && string.split(":").length > 0 && string.indexOf(32) == -1;
        this.buttonAddServer.active = bl && !this.textFieldServerName.getText().isEmpty();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        AddServerScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 17, 0xFFFFFF);
        AddServerScreen.drawString(matrixStack, this.font, field_243290_a, this.width / 2 - 100, 53, 0xA0A0A0);
        AddServerScreen.drawString(matrixStack, this.font, field_243291_b, this.width / 2 - 100, 94, 0xA0A0A0);
        this.textFieldServerName.render(matrixStack, n, n2, f);
        this.textFieldServerAddress.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$3(Button button) {
        this.field_213032_b.accept(false);
    }

    private void lambda$init$2(Button button) {
        this.onButtonServerAddPressed();
    }

    private void lambda$init$1(Button button) {
        this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % ServerData.ServerResourceMode.values().length]);
        this.buttonResourcePack.setMessage(AddServerScreen.func_238624_a_(this.serverData.getResourceMode()));
    }

    private static boolean lambda$new$0(String string) {
        if (StringUtils.isNullOrEmpty(string)) {
            return false;
        }
        String[] stringArray = string.split(":");
        if (stringArray.length == 0) {
            return false;
        }
        try {
            String string2 = IDN.toASCII(stringArray[0]);
            return true;
        } catch (IllegalArgumentException illegalArgumentException) {
            return true;
        }
    }
}

