/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.ui.guis;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.ui.GuiFlatNormalField;
import pw.vertexcode.nemphis.ui.GuiFlatPasswordField;
import pw.vertexcode.nemphis.utils.AltLoginThread;
import pw.vertexcode.util.font.FontManager;
import pw.vertexcode.util.lwjgl.LWJGLUtil;

public class GuiDirectLogin
extends GuiScreen {
    private GuiFlatNormalField normalField;
    private GuiFlatPasswordField passwordField;

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        int x = sr.getScaledWidth() / 2 - 108;
        int y = sr.getScaledHeight() / 2;
        this.normalField = new GuiFlatNormalField(0, this.mc.fontRendererObj, x, y - 20, 224, 20);
        this.passwordField = new GuiFlatPasswordField(0, this.mc.fontRendererObj, x, y + 10, 224, 20);
        int x2 = sr.getScaledWidth() / 2 - 104;
        this.buttonList.add(new GuiButton(0, x2, y + 60, "Login"));
        this.buttonList.add(new GuiButton(1, x2, y + 81, "Back"));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        int id = button.id;
        if (!(id != 0 || this.normalField.getText().isEmpty() && this.passwordField.getText().isEmpty())) {
            AltLoginThread altlogin = new AltLoginThread(this.normalField.getText(), this.passwordField.getText());
            altlogin.start();
        }
        if (id == 1) {
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        LWJGLUtil.drawFullscreenImage(Nemphis.instance.background);
        LWJGLUtil.drawRect(0.0f, 0.0f, this.width, this.height, Integer.MIN_VALUE);
        FontRenderer fr = this.mc.fontRendererObj;
        fr.drawStringWithShadow("Username: \u00a74" + this.mc.session.getUsername(), 2.0f, 2.0f, -1);
        ScaledResolution sr = new ScaledResolution(this.mc);
        int x = sr.getScaledWidth() / 2;
        int y = sr.getScaledHeight() / 2;
        this.normalField.drawTextBox();
        this.passwordField.drawTextBox();
        FontManager.getFont("nm", 120).drawString("Login", x - FontManager.getFont("nm", 120).getStringWidth("Login") / 2, y - 100, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void updateScreen() {
        this.normalField.updateCursorCounter();
        this.passwordField.updateCursorCounter();
        super.updateScreen();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.normalField.textboxKeyTyped(typedChar, keyCode);
        this.passwordField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.normalField.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}

