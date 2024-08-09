package wtf.shiyeno.ui.clickgui.theme;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import org.joml.Vector4i;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.ui.midnight.Style;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.animation.AnimationMath;

public class ThemeObject {

    public float x, y, width, height;
    public Style style;
    public float anim;

    public ThemeObject(Style style) {
        this.style = style;
    }

    public void draw(MatrixStack stack, int mouseX, int mouseY) {

        RenderUtil.Render2D.drawRect(x, y, width, height, ColorUtil.rgba(24, 24, 24, 255));

        Vector4i colors = new Vector4i(
                style.colors[0],
                style.colors[0],
                style.colors[1],
                style.colors[1]
        );

        if (style.name.equalsIgnoreCase("Цветной")) {
            colors = new Vector4i(
                    style.getColor(0),
                    style.getColor(0),
                    style.getColor(90),
                    style.getColor(90)
            );
        }

        if (style.name.equalsIgnoreCase("Радужный")) {
            colors = new Vector4i(
                    style.getColor(0),
                    style.getColor(90),
                    style.getColor(180),
                    style.getColor(270)
            );
        }

        float off = 0;
        for (String ss : style.name.split(" ")) {
            Fonts.msBold[16].drawString(stack, ss, x + 7, y + 7 + off, -1);
            off += 10;
        }

        String hexFirst = "#" + Integer.toHexString(colors.x).substring(2).toUpperCase();
        String hexTwo = "#" + Integer.toHexString(colors.w).substring(2).toUpperCase();
        Fonts.msBold[10].drawString(stack, hexFirst, x + 5, y + height - 14, -1);
        Fonts.msBold[10].drawString(stack, hexTwo, x + 5, y + height - 7, -1);

        RenderUtil.Render2D.drawImage(new ResourceLocation("shiyeno/images/rect.png"), x + width - 42 - 0.5f, y + height - 16 - 0.5f, 42, 16, colors);
    }
}