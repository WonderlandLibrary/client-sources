/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL20
 */
package drunkclient.beta.API.GUI.login;

import drunkclient.beta.API.GUI.MainMenu.MainMenu;
import drunkclient.beta.API.GUI.login.EmptyInputBox;
import drunkclient.beta.API.HWID.Auth;
import drunkclient.beta.API.HWID.Hwid;
import drunkclient.beta.IMPL.Shader.Shader;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.UTILS.render.RenderUtil;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class GuiAuth2
extends GuiScreen {
    EmptyInputBox username;
    public Shader shader;
    EmptyInputBox password;
    private boolean loginsuccessfully = false;
    int anim = 140;
    private long initTime = System.currentTimeMillis();
    String hwid = Hwid.getHWID();

    @Override
    public void initGui() {
        super.initGui();
        this.username = new EmptyInputBox(4, this.mc.fontRendererObj, 474, 105, 100, 20);
        this.password = new EmptyInputBox(4, this.mc.fontRendererObj, 480, 138, 100, 20);
        this.initTime = System.currentTimeMillis();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (this.loginsuccessfully) {
            this.mc.displayGuiScreen(new MainMenu());
        }
        this.mc.getTextureManager().bindTexture(new ResourceLocation("drunkclient/mainmenubg.png"));
        GuiAuth2.drawScaledCustomSizeModalRect(0, 0, 0.0f, 0.0f, this.width, this.height, this.width, this.height, this.width, this.height);
        GlStateManager.disableCull();
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)-1.0f, (float)-1.0f);
        GL11.glVertex2f((float)-1.0f, (float)1.0f);
        GL11.glVertex2f((float)1.0f, (float)1.0f);
        GL11.glVertex2f((float)1.0f, (float)-1.0f);
        GL11.glEnd();
        GL20.glUseProgram((int)0);
        this.username.yPosition = 100;
        this.password.yPosition = this.username.yPosition + 30;
        RenderUtil.drawRoundRect(565.0, (float)this.username.yPosition - 0.5f, 420.0, this.username.yPosition + 10, this.username.isSwidered() ? new Color(59, 59, 59).getRGB() : new Color(59, 59, 59).getRGB());
        RenderUtil.drawRoundRect(565.0, (float)this.username.yPosition - 0.5f, 420.0, (float)(this.username.yPosition + 20) - 0.5f, new Color(255, 255, 255).getRGB());
        if (!this.username.isSwidered() && this.username.getText().isEmpty()) {
            FontLoaders.F16.drawString("Username", 474.0f, 105.0f, new Color(180, 180, 180).getRGB());
        }
        RenderUtil.drawRoundRect(565.0, this.password.yPosition, 420.0, this.password.yPosition, this.password.isSwidered() ? new Color(150, 150, 150).getRGB() : new Color(200, 200, 200).getRGB());
        RenderUtil.drawRoundRect(565.0, (float)this.password.yPosition + 0.5f, 420.0, (float)(this.password.yPosition + 20) - 0.5f, new Color(255, 255, 255).getRGB());
        if (!this.password.isSwidered() && this.password.getText().isEmpty()) {
            FontLoaders.F16.drawString("Hwid", 480.0f, 138.0f, new Color(180, 180, 180).getRGB());
        }
        this.password.drawTextBox();
        this.username.drawTextBox();
        if (GuiAuth2.isHovered(this.password.xPosition, this.password.yPosition + 30, this.password.xPosition + this.password.getWidth(), this.password.yPosition + 50, mouseX, mouseY)) {
            if (Mouse.isButtonDown((int)0) && Auth.authManualHWID()) {
                this.loginsuccessfully = !this.loginsuccessfully;
            }
            RenderUtil.drawRoundRect(this.password.xPosition - 30, this.password.yPosition + 30, this.password.xPosition + this.password.getWidth() - 30, this.password.yPosition + 50, new Color(163, 33, 24).getRGB());
            FontLoaders.F16.drawCenteredString("LOGIN", this.password.xPosition + this.password.getWidth() / 2 - 30, this.password.yPosition + 38, new Color(255, 255, 255).getRGB());
        } else {
            RenderUtil.drawRoundRect(this.password.xPosition - 30, this.password.yPosition + 30, this.password.xPosition + this.password.getWidth() - 30, this.password.yPosition + 50, new Color(217, 61, 50).getRGB());
            FontLoaders.F16.drawCenteredString("LOGIN", this.password.xPosition + this.password.getWidth() / 2 - 30, this.password.yPosition + 38, new Color(255, 255, 255).getRGB());
        }
        if (GuiAuth2.isHovered(this.password.xPosition + this.password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid") - 60, this.password.yPosition + 60, this.password.xPosition + this.password.getWidth(), this.password.yPosition + 70, mouseX, mouseY)) {
            if (Mouse.isButtonDown((int)0)) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                GuiAuth2.setClipboardString(this.hwid);
            }
            FontLoaders.F14.drawString("Copy hwid", this.password.xPosition + this.password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid") - 60, this.password.yPosition + 60, new Color(77, 111, 175).getRGB());
        } else {
            FontLoaders.F14.drawString("Copy hwid", this.password.xPosition + this.password.getWidth() - FontLoaders.F14.getStringWidth("Copy hwid") - 60, this.password.yPosition + 60, new Color(150, 150, 150).getRGB());
        }
        FontLoaders.F14.drawString(Auth.getStatus(), 3.0f, this.password.yPosition + 100, new Color(150, 150, 150).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean contains_(String s, String t) {
        char[] array1 = s.toCharArray();
        char[] array2 = t.toCharArray();
        boolean status = false;
        if (array2.length >= array1.length) return status;
        int i = 0;
        while (i < array1.length) {
            if (array1[i] == array2[0] && i + array2.length - 1 < array1.length) {
                int j;
                for (j = 0; j < array2.length && array1[i + j] == array2[j]; ++j) {
                }
                if (j == array2.length) {
                    return true;
                }
            }
            ++i;
        }
        return status;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        switch (keyCode) {
            case 15: {
                if (!this.username.isSwidered()) return;
                if (keyCode != 15) return;
                this.password.setSwidered(true);
                this.username.setSwidered(false);
                return;
            }
            case 28: {
                if (!Auth.authManualHWID()) return;
                this.loginsuccessfully = !this.loginsuccessfully;
                return;
            }
        }
        if (this.username.isSwidered()) {
            this.username.textboxKeyTyped(typedChar, keyCode);
        }
        if (!this.password.isSwidered()) return;
        this.password.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        if (!((float)mouseX >= x)) return false;
        if (!((float)mouseX <= x2)) return false;
        if (!((float)mouseY >= y)) return false;
        if (!((float)mouseY <= y2)) return false;
        return true;
    }
}

