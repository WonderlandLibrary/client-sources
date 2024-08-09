package wtf.shiyeno.ui.clickgui.theme;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.ui.midnight.Style;
import wtf.shiyeno.util.math.MathUtil;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.NoiseShader;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.animation.AnimationMath;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeDrawing {

    private List<ThemeObject> objects = new ArrayList<>();

    float animation;
    public ThemeDrawing() {
        Style custom = Managment.STYLE_MANAGER.styles.get(Managment.STYLE_MANAGER.styles.size() - 1);
        for (Style style : Managment.STYLE_MANAGER.styles) {
            if (style.name.equalsIgnoreCase("Свой цвет")) continue;
            objects.add(new ThemeObject(style));
        }

        float[] rgb = RenderUtil.IntColor.rgb(custom.colors[edit]);
        float[] hsb = Color.RGBtoHSB((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255), null);
        this.hsb = hsb[0];
        this.satur = hsb[1];
        this.brithe = hsb[2];
    }

    boolean colorOpen;
    public float openAnimation;

    public int edit;

    float x,y,width,height;

    float hsb;
    float satur;
    float brithe;

    public void draw(MatrixStack stack, int mouseX, int mouseY, float x,float y,float width ,float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        float offsetX = 10;
        float offsetY = 6;

        openAnimation = AnimationMath.lerp(openAnimation, colorOpen ? 1 : 0, 15);

        for (ThemeObject object : objects) {
            object.x = x + offsetX;
            object.y = y + offsetY;
            object.width = 154 / 2f;
            object.height = 50;
            RenderUtil.Render2D.drawRect(object.x, object.y, object.width, object.height, ColorUtil.rgba(25, 26, 33, 255));
            offsetX += object.width + 13 / 2f;
            if (offsetX > 450 - 100 - object.width + 13 / 2f) {
                offsetX = 10;
                offsetY += 55;
            }
        }

        for (ThemeObject object : objects) {
            object.draw(stack,mouseX,mouseY);
        }
        Style custom = Managment.STYLE_MANAGER.styles.get(Managment.STYLE_MANAGER.styles.size() - 1);

        animation = (float) AnimationMath.lerp(animation, Managment.STYLE_MANAGER.getCurrentStyle() == custom ? 1 : RenderUtil.isInRegion(mouseX,mouseY,x + 10, y + height - 65, width - 20, 50) ? 0.5f : 0, 5);

        RenderUtil.Render2D.drawRect(x + 10, y + height - 65, width - 20, 50, ColorUtil.rgba(24, 24, 24, 255));

        Fonts.msBold[18].drawString(stack, "Свой цвет", x + 17, y + height - 65 + 7, -1);

        int colors = custom.colors[0];

        String hexFirst = "#" + Integer.toHexString(colors).substring(2).toUpperCase();
        String hexTwo = "#" + Integer.toHexString(colors).substring(2).toUpperCase();
        Fonts.msBold[10].drawString(stack, hexFirst, x + 15, y + height - 14 - 16, -1);
        Fonts.msBold[10].drawString(stack, hexTwo, x + 15, y + height - 7 - 16, -1);

        RenderUtil.Render2D.drawImage(new ResourceLocation("shiyeno/images/rect2.png"), x - 10.5F + width - 345 / 2f, y + height - 49.5F, 345 / 2f, 69 / 2f, colors);

        RenderUtil.Render2D.drawRect(x - 10.5F + width - 15, y + height - 14 - 16, 7,7, ColorUtil.rgba(0, 0, 0, 0));

        if (openAnimation > 0.01) {
            GlStateManager.pushMatrix();
            float colorX = x + width + 20;
            float colorY = y + height - 315 / 2f;
            GlStateManager.translated(x - 10.5F + width - 15 + 3.5F, y + height - 14 - 16 + 3.5F, 0);
            GlStateManager.scalef(openAnimation, openAnimation, 1);
            GlStateManager.translated(-(x - 10.5F + width - 15  + 3.5F), -(y + height - 14 - 16 + 3.5F), 0);

            RenderUtil.Render2D.drawTexture(x + width + 20, y + height - 315 / 2f, 130, 315 / 2f, 15, 1);
            RenderUtil.Render2D.drawImage(new ResourceLocation("shiyeno/images/corner.png"), colorX + 8 + 25, colorY + 7 + (edit == 1 ? -2 : 0), 34, 23, custom.colors[1]);
            RenderUtil.Render2D.drawImage(new ResourceLocation("shiyeno/images/corner.png"), colorX + 8, colorY + 7 + (edit == 0 ? -2 : 0), 34, 23, custom.colors[0]);
            RenderUtil.Render2D.drawGradientRound(colorX + 9, colorY + 15, 222 / 2f + 2, 210 / 2f + 2, 5, Color.BLACK.getRGB(), Color.BLACK.getRGB(), Color.BLACK.getRGB(), Color.BLACK.getRGB());

            RenderUtil.Render2D.drawGradientRound(colorX + 10, colorY + 16, 222 / 2f, 210 / 2f, 4, -1, Color.BLACK.getRGB(), Color.HSBtoRGB(this.hsb, 1, 1), Color.BLACK.getRGB());
            if (colorOpen && openAnimation >= 0.999) {
                NoiseShader.start();
                RenderUtil.Render2D.drawGradientRound(colorX + 10, colorY + 16, 222 / 2f, 210 / 2f, 4, -1, Color.BLACK.getRGB(), Color.HSBtoRGB(this.hsb, 1, 1), Color.BLACK.getRGB());
                NoiseShader.end(60);
            }

            for (float i = 0; i < 222 / 2f;i+=1) {
                float hue = (i / (222 / 2f));
                RenderUtil.Render2D.drawRoundCircle(colorX + 10 + i, colorY + 16+ 210 / 2f + 13, 7, Color.HSBtoRGB(hue,1,1));
            }

            if (GLFW.glfwGetMouseButton(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
                if (RenderUtil.isInRegion(mouseX,mouseY, colorX + 10, colorY + 16+ 210 / 2f + 8, 222 / 2f, 9)) {
                    hsb = (mouseX - (colorX + 10)) / 110f;
                }
            }

            if (RenderUtil.isInRegion(mouseX,mouseY,colorX + 10, colorY + 16, 222 / 2f, 210 / 2f) && GLFW.glfwGetMouseButton(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
                float br = MathHelper.clamp(1 - (mouseY - colorY - 16) / (210 / 2f), 0,1);
                this.brithe = br;
                float satur = MathHelper.clamp((mouseX - colorX - 10) / (222 / 2f), 0,1);
                this.satur = satur;
            }

            RenderUtil.Render2D.drawRoundCircle(colorX + 10 + (this.satur * (222 / 2f)), colorY + 16 + ((1 - this.brithe) * (210 / 2f)), 7, -1);
            RenderUtil.Render2D.drawRoundCircle(colorX + 10 + ((hsb * openAnimation) * (222 / 2f)), colorY + 16+ 210 / 2f + 13, 10, -1);
            int col = Color.HSBtoRGB(this.hsb, this.satur,brithe);
            custom.colors[edit] = col;
            GlStateManager.popMatrix();
        }
        RenderUtil.Render2D.drawLine(x - 10.5F + width - 15 + 3.5f, y + height - 14 - 16 + 3.5f, MathUtil.interpolate(x + width + 30 + 3.5f,x - 10.5F + width - 15 + 3.5f , openAnimation), MathUtil.interpolate(y + height - 16 + 3.5f, y + height - 14 - 16 + 3.5f, openAnimation), RenderUtil.reAlphaInt(-1, (int) (255)));
        RenderUtil.Render2D.drawRect((float) MathUtil.interpolate(x - 10.5F + width - 15, x + width + 30, 1 - openAnimation), (float) MathUtil.interpolate(y + height - 16,y + height - 14 - 16, openAnimation), 7, 7, ColorUtil.rgba(0, 0, 0, 0));
    }

    boolean drag;

    public void click(int mouseX, int mouseY, int button) {
        Style custom = Managment.STYLE_MANAGER.styles.get(Managment.STYLE_MANAGER.styles.size() - 1);

        float colorX = x + width + 20;
        float colorY = y + height - 315 / 2f;

        if (RenderUtil.isInRegion(mouseX,mouseY,colorX + 8, colorY + 6, 34, 8)) {
            edit = 0;
            float[] rgb = RenderUtil.IntColor.rgb(custom.colors[edit]);
            float[] hsb = Color.RGBtoHSB((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255), null);
            this.hsb = hsb[0];
            this.satur = hsb[1];
            this.brithe = hsb[2];
        }

        if (RenderUtil.isInRegion(mouseX,mouseY,colorX + 8 + 25, colorY + 6, 34, 8)) {
            edit = 1;
            float[] rgb = RenderUtil.IntColor.rgb(custom.colors[edit]);
            float[] hsb = Color.RGBtoHSB((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255), null);
            this.hsb = hsb[0];
            this.satur = hsb[1];
            this.brithe = hsb[2];
        }

        if (RenderUtil.isInRegion(mouseX,mouseY,x + 10, y + height - 65, width - 20, 50) && button == 1) {
            colorOpen = !colorOpen;
        }

        if (RenderUtil.isInRegion(mouseX,mouseY,x + 10, y + height - 65, width - 20, 50) && button == 0) {
            Style c = Managment.STYLE_MANAGER.styles.get(Managment.STYLE_MANAGER.styles.size() - 1);
            Managment.STYLE_MANAGER.setCurrentStyle(c);
        }
        for (ThemeObject object : objects) {
            if (RenderUtil.isInRegion(mouseX,mouseY, object.x,object.y, object.width, object.height)) {
                Managment.STYLE_MANAGER.setCurrentStyle(object.style);
            }
        }
    }
}