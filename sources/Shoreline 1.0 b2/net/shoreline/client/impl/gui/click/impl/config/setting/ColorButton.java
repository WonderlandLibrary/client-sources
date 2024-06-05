package net.shoreline.client.impl.gui.click.impl.config.setting;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.gui.click.ClickGuiScreen;
import net.shoreline.client.impl.gui.click.impl.config.CategoryFrame;
import net.shoreline.client.init.Modules;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

/**
 *
 *
 * @author Shoreline
 * @since 1.0
 */
public class ColorButton extends ConfigButton<Color>
{
    private final float[] hsb;

    /**
     * @param frame
     * @param config
     * @param x
     * @param y
     */
    public ColorButton(CategoryFrame frame, Config<Color> config, float x, float y)
    {
        super(frame, config, x, y);
        Color color = config.getValue();
        hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
    }

    @Override
    public void render(MatrixStack matrices, float ix, float iy, float mouseX,
                       float mouseY, float delta)
    {
        x = ix;
        y = iy;
        //
        int min = 0;
        int max = 360;
        if (isWithin(mouseX, mouseY) && ClickGuiScreen.MOUSE_LEFT_HOLD)
        {
            float fillv = (mouseX - ix) / width;
            float val = (float) min + fillv * (max - min);
            int bval = (int) MathHelper.clamp(val, min, max);
            hsb[0] = bval;
            float lower = ix + 1.0f;
            float upper = ix + width - 1.0f;
            // out of bounds
            if (mouseX < lower)
            {
                hsb[0] = min;
            }
            else if (mouseX > upper)
            {
                hsb[0] = max;
            }
        }
        // slider fill
        float fill = (hsb[0] - (float) min) / (max - min);
        fill(matrices, ix, iy, (fill * width), height, Modules.COLORS.getRGB());
        RenderManager.renderText(matrices, config.getName() + Formatting.GRAY
                + " " + hsb[0], ix + 2.0f, iy + 4.0f, -1);
        config.setValue(new Color(Color.HSBtoRGB(hsb[0] / 360.0f, hsb[1], hsb[2])));
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button)
    {

    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button)
    {

    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers)
    {

    }
}
