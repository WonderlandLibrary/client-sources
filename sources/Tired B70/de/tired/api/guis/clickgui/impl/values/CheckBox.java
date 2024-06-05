package de.tired.api.guis.clickgui.impl.values;

import de.tired.api.extension.Extension;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.misc.FileUtil;
import de.tired.api.util.render.Translate;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CheckBox {

    public final BooleanSetting value;

    public int WIDTH = 120;
    public final int HEIGHT = 15;

    private int x, y;

    public Translate translate;
    public double animation;
    public float float2;

    public int colRed = 95, colGreen = 99, colBlue = 104;

    public float animationToggle;

    public CheckBox(BooleanSetting value) {
        this.value = value;
        this.translate = new Translate(0, 0);
    }


    public void drawCheckBox(int x, int y, int mouseX, int mouseY, boolean rect) {
        this.x = x;
        this.y = y;
        boolean state = value.getValue();


        animation = AnimationUtil.getAnimationState(animation, WIDTH, Math.max(3.6D, Math.abs((double) animation - WIDTH)) * 2);
        WIDTH = (int) animation;
        this.animationToggle = (float) AnimationUtil.getAnimationState((double) this.animationToggle, this.x + WIDTH, Math.max(4.6D, Math.abs((double) this.animationToggle - animationToggle)) * 282);

        boolean isMouseOver = (mouseX > this.x + 2) && (mouseX < this.x - 2 + 15) && (mouseY > this.y + 2) && (mouseY < this.y - 2 + 15); //cant use the void from abstract class because the values are diffrent

        colRed = (int) AnimationUtil.getAnimationState(colRed, state ? 135 : 95, 222);

        colGreen = (int) AnimationUtil.getAnimationState(colGreen, state ? 211 : 99, 222);

        colBlue = (int) AnimationUtil.getAnimationState(colBlue, state ? 181 : 104, 222);

        final Color color = state ? new Color(colRed, colGreen, colBlue).darker() : new Color(colRed, colGreen, colBlue);

        final Color colorRect = state ? new Color(colRed, colGreen, colBlue) : new Color(30, 33, 39);


        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        GL11.glHint(GL11.GL_POINT_SMOOTH_HINT,GL11.GL_FASTEST);

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRect2(this.x + 2, this.y + 5, 18, HEIGHT - 8, 3, colorRect.darker().darker());

        float amount = state ? x + 16 : x + 5;

        float2 = (float) AnimationUtil.getAnimationState(float2, amount, Math.max(22.6D, Math.abs((double) this.float2 - amount) * 7));

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(float2, this.y + 8.5, 4, color.getRGB());


        if (isMouseOver) {
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRect(this.x + 2, this.y + 5, this.x + WIDTH - WIDTH + 9, this.y + HEIGHT - 3, Integer.MIN_VALUE);
        }

        String name = value.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        FontManager.light.drawString(name, x + 27, y + 8, -1);

        WIDTH = 120;

    }


    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {


        boolean isMouseOver = (mouseX > this.x + 2) && (mouseX < this.x - 2 + 25) && (mouseY > this.y + 2) && (mouseY < this.y - 2 + 25); //cant use the void from abstract class because the values are diffrent
        if (isMouseOver) {
            value.setValue(!(boolean) value.getValue());
            FileUtil.FILE_UTIL.saveSettings();
        }

    }
}