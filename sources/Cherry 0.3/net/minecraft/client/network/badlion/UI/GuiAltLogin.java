// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.UI;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.ScaledResolution;
import java.io.IOException;
import net.minecraft.client.network.badlion.Utils.LoginUtils;
import net.minecraft.util.StringUtils;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class GuiAltLogin extends GuiScreen
{
    private GuiScreen prevMenu;
    private GuiTextField theName;
    private GuiPasswordField thePassword;
    
    public GuiAltLogin(final GuiScreen parent) {
        this.prevMenu = parent;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        final boolean var2 = true;
        (this.theName = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, this.width / 2 - 100, this.height / 4 + 20, 200, 20)).setMaxStringLength(250);
        (this.thePassword = new GuiPasswordField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 4 + 60, 200, 20)).setMaxStringLength(250);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120, I18n.format("menu.returnToMenu", new Object[0])));
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                if (!StringUtils.isNullOrEmpty(this.theName.getText()) && !StringUtils.isNullOrEmpty(this.thePassword.getText())) {
                    LoginUtils.login(this.theName.getText(), this.thePassword.getText());
                    break;
                }
                if (!StringUtils.isNullOrEmpty(this.theName.getText())) {
                    LoginUtils.changeCrackedName(this.theName.getText());
                    break;
                }
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.prevMenu);
                break;
            }
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        this.theName.textboxKeyTyped(typedChar, keyCode);
        this.thePassword.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.prevMenu);
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.theName.mouseClicked(mouseX, mouseY, mouseButton);
        this.thePassword.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void updateScreen() {
        this.theName.updateCursorCounter();
        this.thePassword.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        final int displayWidth = Minecraft.displayWidth;
        Minecraft.getMinecraft();
        final ScaledResolution sr = new ScaledResolution(minecraft, displayWidth, Minecraft.displayHeight);
        final Tessellator var4 = Tessellator.getInstance();
        final WorldRenderer var5 = var4.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/Background.jpg"));
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Alt Manager", this.width / 2, 48, -1);
        this.theName.drawTextBox();
        this.thePassword.drawTextBox();
        Gui.drawString(Minecraft.getMinecraft().fontRendererObj, "Username", this.width / 2 - 100, this.height / 4 + 8, -1);
        Gui.drawString(Minecraft.getMinecraft().fontRendererObj, "Password", this.width / 2 - 100, this.height / 4 + 48, -1);
        this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Your current name: §a" + Minecraft.getMinecraft().getSession().getUsername(), this.width / 2, 12, -1);
        if (LoginUtils.getMessage() != "") {
            this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, LoginUtils.getMessage(), this.width / 2, 24, 16711680);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
