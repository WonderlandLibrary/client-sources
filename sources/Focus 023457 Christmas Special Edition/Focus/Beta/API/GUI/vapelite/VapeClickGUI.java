package Focus.Beta.API.GUI.vapelite;

import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.UTILS.render.RenderUtil2;
import Focus.Beta.UTILS.world.Timer;
import com.sun.javafx.tk.FontLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class VapeClickGUI extends GuiScreen {
    private boolean close = false;
    private boolean closed;

    private float dragX, dragY;
    private boolean drag = false;
    private int valuemodx = 0;

    private static float modsRole, modsRoleNow;
    private static float valueRoleNow, valueRole;
    static float windowX = 200, windowY = 200;
    static float width = 500, height = 310;

    static Type category = Type.COMBAT;
    static Module selectMod;

    float[] typeXAnim = new float[]{windowX + 10, windowX + 10, windowX + 10, windowX + 10};

    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public float outro;
    public float lastOutro;

    float hy = windowY + 40;

    Timer valuetimer = new Timer();

    @Override
    public void initGui() {
        super.initGui();
        percent = 1.33f;
        lastPercent = 1f;
        percent2 = 1.33f;
        lastPercent2 = 1f;
        outro = 1;
        lastOutro = 1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution sResolution = new ScaledResolution(mc);
        ScaledResolution sr = new ScaledResolution(mc);

        float outro = smoothTrans(this.outro, lastOutro);
        if (mc.currentScreen == null) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(outro, outro, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        }
        percent = smoothTrans(this.percent, lastPercent);
        percent2 = smoothTrans(this.percent2, lastPercent2);
        if (percent > 0.98) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(percent, percent, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        } else {
            if (percent2 <= 1) {
                GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
                GlStateManager.scale(percent2, percent2, 0);
                GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
            }
        }
        if(percent <= 1.5 && close) {
            percent = smoothTrans(this.percent, 2);
            percent2 = smoothTrans(this.percent2, 2);
        }
        if(percent >= 1.4  &&  close){
            percent = 1.5f;
            closed = true;
            mc.currentScreen = null;
        }
        if (isHovered(windowX, windowY, windowX + width, windowY + 20, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (dragX == 0 && dragY == 0) {
                dragX = mouseX - windowX;
                dragY = mouseY - windowY;
            } else {
                windowX = mouseX - dragX;
                windowY = mouseY - dragY;
            }
            drag = true;
        } else if (dragX != 0 || dragY != 0) {
            dragX = 0;
            dragY = 0;
        }


        RenderUtil2.drawRoundedRect(windowX, windowY, windowX + width, windowY + height, 0, new Color(21, 22, 25).getRGB());

        float typeX = windowX + 20;
        int i = 0;
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(0, 2 * ((int) (sr.getScaledHeight_double() - (windowY + height))) + 40, (int) (sr.getScaledWidth_double() * 2), (int) ((height) * 2) - 160);
        if (selectMod == null) {
            float cateY = windowY + 65;
            for (Type m : Type.values()) {
                if (m == category) {

                    FontLoaders.arial22.drawString(m.name(), windowX + 20, cateY, -1);
                    if (isHovered(windowX, windowY, windowX + width, windowY + 20, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                        hy = cateY;
                    } else {
                        if (hy != cateY) {
                            hy += (cateY - hy) / 10;
                        }
                    }
                } else {
                    FontLoaders.arial22.drawString(m.name(), windowX + 20, cateY, new Color(108, 109, 113).getRGB());
                }


                cateY += 25;
            }
        }
        if (selectMod != null) {
            if (valuemodx > -80) {
                valuemodx -= 5;
            }
        } else {
            if (valuemodx < 0) {
                valuemodx += 5;
            }
        }


        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }


    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }


    public float smoothTrans(double current, double last){
        return (float) (current + (last - current) / (Minecraft.debugFPS / 10));
    }
}
