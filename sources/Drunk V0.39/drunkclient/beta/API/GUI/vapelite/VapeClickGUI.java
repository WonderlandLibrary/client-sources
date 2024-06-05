/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.API.GUI.vapelite;

import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.UTILS.render.RenderUtil2;
import drunkclient.beta.UTILS.world.Timer;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class VapeClickGUI
extends GuiScreen {
    private boolean close = false;
    private boolean closed;
    private float dragX;
    private float dragY;
    private boolean drag = false;
    private int valuemodx = 0;
    private static float modsRole;
    private static float modsRoleNow;
    private static float valueRoleNow;
    private static float valueRole;
    static float windowX;
    static float windowY;
    static float width;
    static float height;
    static Type category;
    static Module selectMod;
    float[] typeXAnim = new float[]{windowX + 10.0f, windowX + 10.0f, windowX + 10.0f, windowX + 10.0f};
    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public float outro;
    public float lastOutro;
    float hy = windowY + 40.0f;
    Timer valuetimer = new Timer();

    @Override
    public void initGui() {
        super.initGui();
        this.percent = 1.33f;
        this.lastPercent = 1.0f;
        this.percent2 = 1.33f;
        this.lastPercent2 = 1.0f;
        this.outro = 1.0f;
        this.lastOutro = 1.0f;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution sResolution = new ScaledResolution(this.mc);
        ScaledResolution sr = new ScaledResolution(this.mc);
        float outro = this.smoothTrans(this.outro, this.lastOutro);
        if (this.mc.currentScreen == null) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0.0f);
            GlStateManager.scale(outro, outro, 0.0f);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0.0f);
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
        if ((double)this.percent <= 1.5 && this.close) {
            this.percent = this.smoothTrans(this.percent, 2.0);
            this.percent2 = this.smoothTrans(this.percent2, 2.0);
        }
        if ((double)this.percent >= 1.4 && this.close) {
            this.percent = 1.5f;
            this.closed = true;
            this.mc.currentScreen = null;
        }
        if (VapeClickGUI.isHovered(windowX, windowY, windowX + width, windowY + 20.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
            if (this.dragX == 0.0f && this.dragY == 0.0f) {
                this.dragX = (float)mouseX - windowX;
                this.dragY = (float)mouseY - windowY;
            } else {
                windowX = (float)mouseX - this.dragX;
                windowY = (float)mouseY - this.dragY;
            }
            this.drag = true;
        } else if (this.dragX != 0.0f || this.dragY != 0.0f) {
            this.dragX = 0.0f;
            this.dragY = 0.0f;
        }
        RenderUtil2.drawRoundedRect(windowX, windowY, windowX + width, windowY + height, 0, new Color(21, 22, 25).getRGB());
        float typeX = windowX + 20.0f;
        boolean i = false;
        GL11.glEnable((int)3089);
        GL11.glScissor((int)0, (int)(2 * (int)(sr.getScaledHeight_double() - (double)(windowY + height)) + 40), (int)((int)(sr.getScaledWidth_double() * 2.0)), (int)((int)(height * 2.0f) - 160));
        if (selectMod == null) {
            float cateY = windowY + 65.0f;
            Type[] typeArray = Type.values();
            int n = typeArray.length;
            for (int j = 0; j < n; cateY += 25.0f, ++j) {
                Type m = typeArray[j];
                if (m == category) {
                    FontLoaders.arial22.drawString(m.name(), windowX + 20.0f, cateY, -1);
                    if (VapeClickGUI.isHovered(windowX, windowY, windowX + width, windowY + 20.0f, mouseX, mouseY) && Mouse.isButtonDown((int)0)) {
                        this.hy = cateY;
                        continue;
                    }
                    if (this.hy == cateY) continue;
                    this.hy += (cateY - this.hy) / 10.0f;
                    continue;
                }
                FontLoaders.arial22.drawString(m.name(), windowX + 20.0f, cateY, new Color(108, 109, 113).getRGB());
            }
        }
        if (selectMod != null) {
            if (this.valuemodx > -80) {
                this.valuemodx -= 5;
            }
        } else if (this.valuemodx < 0) {
            this.valuemodx += 5;
        }
        GL11.glDisable((int)3089);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        if (!((float)mouseX >= x)) return false;
        if (!((float)mouseX <= x2)) return false;
        if (!((float)mouseY >= y)) return false;
        if (!((float)mouseY <= y2)) return false;
        return true;
    }

    public float smoothTrans(double current, double last) {
        return (float)(current + (last - current) / (double)(Minecraft.debugFPS / 10));
    }

    static {
        windowX = 200.0f;
        windowY = 200.0f;
        width = 500.0f;
        height = 310.0f;
        category = Type.COMBAT;
    }
}

