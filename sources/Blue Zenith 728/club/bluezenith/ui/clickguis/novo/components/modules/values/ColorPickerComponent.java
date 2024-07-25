package club.bluezenith.ui.clickguis.novo.components.modules.values;

import club.bluezenith.module.value.types.ColorValue;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;

import java.awt.*;

import static club.bluezenith.util.render.RenderUtil.rect;
import static java.awt.Color.getHSBColor;
import static org.lwjgl.input.Mouse.isButtonDown;

public class ColorPickerComponent extends Component {

    private final ColorValue parent;

    private boolean dragging;

    public ColorPickerComponent(ColorValue parent, float x, float y) {
        super(x, y);
        this.parent = parent;
    }

    @Override
    public void draw(int mouseX, int mouseY, ScaledResolution scaledResolution) {
        y -= 2.5F;

        if(!isButtonDown(0))
            dragging = false;

        int mode = parent.sliderState;

        if(mode > 3 || mode < 1) {
            parent.sliderState = mode = 1;
        }

        for (int i = 0; i < width; i++) {
            final float colorPoint = i / width;

            final Color colour = getHSBColor(
                    mode == 1 ? colorPoint : parent.h,
                    mode == 2 ? colorPoint : parent.s,
                    mode == 3 ? colorPoint : parent.b
            );

            float[] hsb = new float[3];
            hsb = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), hsb);

            final float coordinate = x + i;

            if (mode == 1 && hsb[0] == parent.h)
                parent.xH = coordinate;
            else if(mode == 2 && hsb[1] == parent.s)
                parent.xS = coordinate;
            else if(mode == 3 && hsb[2] == parent.b)
                parent.xB = coordinate;

            rect(x + i, y, x + i + 1, y + height, colour.getRGB());

            if(dragging) switch(mode) {
                    case 1:
                        if (x + i == parent.xH)
                            parent.setHue(hsb[0]);
                        parent.xH = mouseX;
                        parent.xH = MathHelper.clamp(parent.xH, x, x + width);
                    break;

                    case 2:
                        if (x + i == parent.xS)
                            parent.setSaturation(hsb[1]);
                        parent.xS = mouseX;
                        parent.xS = MathHelper.clamp(parent.xS, x, x + width);
                    break;

                    case 3:
                        if (x + i == parent.xB)
                            parent.setBrightness(hsb[2]);
                        parent.xB = mouseX;
                        parent.xB = MathHelper.clamp(parent.xB, x, x + width);
                   break;
            }
        }

        if(mode == 1) //todo instead of hardcoding x hardcode the x / width or something
            rect(parent.xH, y, parent.xH + 0.5F, y + height, -1);
        else if(mode == 2)
            rect(parent.xS, y, parent.xS + 0.5F, y + height, -1);
        else
            rect(parent.xB, y, parent.xB + 0.5F, y + height, -1);

        final TFontRenderer font = FontUtil.createFont("helvetica", 36);

        font.drawString(parent.name, x + 1, y + (height/2F - font.getHeight(parent.name)/2F) + 1, -1, true);
        y += 2.5F;
    }

    @Override
    public boolean shouldUpdateWidth() {
        return true;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height + 3;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        dragging = mouseButton == 0;

        if(mouseButton == 1)
            parent.sliderState++;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, x, y - 2.5F, x + width, y + height - 2F);
    }
}
