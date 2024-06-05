/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.GUI.MainMenu;

import drunkclient.beta.API.GUI.alt.GuiAltLogin;
import drunkclient.beta.IMPL.Shader.Shader;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.UTILS.world.Timer;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class MainMenu
extends GuiScreen {
    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public float upx;
    public float outro;
    public float lastOutro;
    boolean cangobackUP;
    Timer timer = new Timer();
    private float hHeight;
    private float hWidth;
    public Shader shader;
    public long initTime = System.currentTimeMillis();
    private float animatedMouseX;
    private float animatedMouseY;

    public float smoothTrans(double current, double last) {
        return (float)(current + (last - current) / (double)(Minecraft.debugFPS / 10));
    }

    @Override
    public void initGui() {
        this.initTime = System.currentTimeMillis();
        this.percent = 1.33f;
        this.lastPercent = 1.0f;
        this.hHeight = 540.0f;
        this.hWidth = 960.0f;
        this.percent2 = 1.33f;
        this.lastPercent2 = 1.0f;
        this.outro = 1.0f;
        this.lastOutro = 1.0f;
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("drunkclient/JP.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(-21.0f + this.animatedMouseX / 90.0f, this.animatedMouseY * -1.0f / 90.0f, 0.0f, 0.0f, this.width + 20, this.height + 20, (float)(this.width + 21), (float)(this.height + 20));
        float outro = this.smoothTrans(this.outro, this.lastOutro);
        if (this.mc.currentScreen == null) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0.0f);
            GlStateManager.scale(outro, outro, 0.0f);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0.0f);
        }
        if (this.timer.hasElapsed(1L, true)) {
            if (this.upx < 40.0f && !this.cangobackUP) {
                this.upx += 0.6f;
            } else {
                this.cangobackUP = true;
            }
            if (this.cangobackUP) {
                if (this.upx > 40.0f) {
                    this.upx -= 0.6f;
                } else if (this.upx == 0.0f) {
                    this.cangobackUP = false;
                }
            }
        }
        this.percent = this.smoothTrans(this.percent, this.lastPercent);
        this.percent2 = this.smoothTrans(this.percent2, this.lastPercent2);
        if ((double)this.percent > 0.98) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0.0f);
            GlStateManager.scale(this.percent, this.percent, 0.0f);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0.0f);
        } else if (this.percent2 <= 1.0f) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0.0f);
            GlStateManager.scale(this.percent2, this.percent2, 0.0f);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0.0f);
        }
        FontLoaders.arial22.drawString("Options", 534.0f, 194.0f, -1);
        FontLoaders.arial16.drawString("Allows you to change options", 501.0f, 205.0f, -1);
        FontLoaders.arial22.drawString("Exit", 544.0f, 220.0f, -1);
        FontLoaders.arial16.drawString("Close Minecraft", 524.0f, 232.0f, -1);
        FontLoaders.arial22.drawString("SinglePlayer", 374.0f, 194.0f, -1);
        FontLoaders.arial16.drawString("Allows you to play on offline worlds", 342.0f, 205.0f, -1);
        FontLoaders.arial22.drawString("MultiPlayer", 376.0f, 220.0f, -1);
        FontLoaders.arial16.drawString("Allows you to play on online servers", 342.0f, 232.0f, -1);
        FontLoaders.arial22.drawString("AltManager", 376.0f, 246.0f, -1);
        FontLoaders.arial16.drawString("Allows you to change your account", 342.0f, 258.0f, -1);
        this.animatedMouseX = (float)((double)this.animatedMouseX + ((double)((float)mouseX - this.animatedMouseX) / 1.8 + 0.1));
        this.animatedMouseY = (float)((double)this.animatedMouseY + ((double)((float)mouseY - this.animatedMouseY) / 1.8 + 0.1));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseX > 338 && mouseX < 471 && mouseY > 191 && mouseY < 215 && mouseButton == 0) {
            System.out.println("Single");
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (mouseX > 338 && mouseX < 471 && mouseY > 218 && mouseY < 239 && mouseButton == 0) {
            System.out.println("Multi");
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (mouseX > 338 && mouseX < 471 && mouseY > 245 && mouseY < 266 && mouseButton == 0) {
            System.out.println("Alt");
            this.mc.displayGuiScreen(new GuiAltLogin(this));
        }
        if (mouseX > 492 && mouseX < 614 && mouseY > 191 && mouseY < 214 && mouseButton == 0) {
            System.out.println("Options");
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (mouseX > 492 && mouseX < 614 && mouseY > 219 && mouseY < 240 && mouseButton == 0) {
            System.out.println("Exit");
            this.mc.shutdown();
        }
        if (mouseButton == 0) {
            System.out.println(mouseX + " " + mouseY);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}

