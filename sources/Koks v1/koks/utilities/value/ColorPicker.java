package koks.utilities.value;

import koks.gui.clickgui.commonvalue.CommonValue;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 22:40
 */
public class ColorPicker {
    private float currentHue;
    private boolean firstLoad;
    private float mouseSavedX, mouseSavedY;
    private boolean draggingMain;

    private final RenderUtils renderUtils = new RenderUtils();

    public void drawScreen(int mouseX, int mouseY, int x, int y, int width, CommonValue setting) {
        float[] color = Color.RGBtoHSB(setting.getColor().getRed(), setting.getColor().getBlue(), setting.getColor().getGreen(), null);

        if (!firstLoad) {
            setting.setHue(setting.getHue());
            this.currentHue = setting.getHue();
            this.mouseSavedX = setting.getX();
            this.mouseSavedY = setting.getY();
            firstLoad = true;
        }

        for (int i = 0; i < 360; i++) {
          Gui.drawRect(x + width + 5, y + (i * width) / 360F, x + width + 10, y + (i * width) / 360F + 1.0F, HSBtoRGB(i, 1, 1));
            if (mouseX > x + width + 5 && mouseX < x + width + 10 && mouseY > y + (i * width) / 360F && mouseY < y + (i * width) / 360F + 1F && Mouse.isButtonDown(0)) {
                setting.setHue(i);
                this.currentHue = i;
            }
        }

        for (int i = 0; i < 360; i++) {
            if (currentHue == i) {
                Gui.drawRect(x + width + 4, y + (i * width) / 360F - 1, x + width + 11, y + (i * width) / 360F + 1, HSBtoRGB(i, 1, 1));
                renderUtils.drawOutlineRect(x + width + 4, y + (i * width) / 360F - 1, x + width + 11, y + (i * width) / 360F + 1, 1, Color.WHITE);
            }
        }

        int pixel = 60;
        for (int i = x; i < x + width; i = (i + width / pixel)) {
            for (int j = y; j < y + width; j = (j + width / pixel)) {
                if (x + mouseSavedX == i && y + mouseSavedY == j) {
                    if (draggingMain || this.currentHue != color[0] * 360F)
                        setting.setColor(new Color(HSBtoRGB(this.currentHue, (float) (i - x) / width, 1.0F - (float) (j - y) / width)));
                }
             Gui.drawRect(i, j, i + width / pixel + 1.0F, j + width / pixel + 1.0F, HSBtoRGB(this.currentHue, (float) (i - x) / width, 1.0F - (float) (j - y) / width));
            }
        }

        /*
         * Informations
         */

        Gui.drawRect(x + width + 20, y, x + width + 56, y + 17, setting.getColor().getRGB());
        renderUtils.drawOutlineRect(x + width + 20, y, x + width + 56, y + 17, 1, Color.BLACK);

        String hex = String.format("%02x%02x%02x", setting.getColor().getRed(), setting.getColor().getGreen(), setting.getColor().getBlue());

        GL11.glPushMatrix();
        GL11.glTranslated(x + width + 20, y + 17 - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 1.75F), 0);
        GL11.glScaled(0.5, 0.5, 0);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(hex, 1, 1, -1);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(x + width + 20, y + 17, 0);
        GL11.glScaled(0.5, 0.5, 0);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("H: " + this.currentHue, 1, 3, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("S: 100%", 1, 3 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("B: 100%", 1, 3 + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1) * 2, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("R: " + setting.getColor().getRed(), 1, 3 + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1) * 4, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("G: " + setting.getColor().getGreen(), 1, 3 + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1) * 5, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("B: " + setting.getColor().getBlue(), 1, 3 + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1) * 6, -1);
        GL11.glPopMatrix();

        drawQuadrat(mouseX, mouseY, x, y, width, setting);
    }

    public void drawQuadrat(int mouseX, int mouseY, int x, int y, int width, CommonValue commonValue) {
        if (mouseX > x - 4 && mouseX < x + width && mouseY > y - 4 && mouseY < y + width) {
            if (Mouse.isButtonDown(0)) {
                draggingMain = true;
            } else {
                draggingMain = false;
            }
            if (draggingMain) {
                if (mouseX > x) {
                    commonValue.setX(mouseX - x);
                    mouseSavedX = commonValue.getX();
                }
                if (mouseY > y) {
                    commonValue.setY(mouseY - y);
                    mouseSavedY = commonValue.getY();
                }
            }
        }
        renderUtils.drawOutlineRect(x + mouseSavedX - 0.5F, y + mouseSavedY - 0.5F, x + mouseSavedX + 1.5F, y + mouseSavedY + 1.5F, 2, Color.BLACK);
        renderUtils.drawOutlineRect(x + mouseSavedX, y + mouseSavedY, x + mouseSavedX + 1, y + mouseSavedY + 1, 2, Color.WHITE);
    }

    public int HSBtoRGB(float hue, float saturation, float brightness) {
        float hue2 = 1.0F - hue / 360.0F;
        return Color.HSBtoRGB(hue2, saturation, brightness);
    }


}
