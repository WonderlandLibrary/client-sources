package net.shoreline.client.impl.gui.click.impl.config.setting;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.gui.click.impl.config.CategoryFrame;
import net.shoreline.client.init.Modules;
import net.minecraft.client.util.math.MatrixStack;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Config
 */
public class CheckboxButton extends ConfigButton<Boolean>
{
    /**
     *
     *
     * @param frame
     * @param config
     */
    public CheckboxButton(CategoryFrame frame, Config<Boolean> config,
                          float x, float y)
    {
        super(frame, config, x, y);
    }

    /**
     *
     *
     * @param matrices
     * @param ix
     * @param iy
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    @Override
    public void render(MatrixStack matrices, float ix, float iy, float mouseX,
                       float mouseY, float delta)
    {
        x = ix;
        y = iy;
        boolean val = config.getValue();
        rect(matrices, val ? Modules.COLORS.getRGB() : 0x00000000);
        RenderManager.renderText(matrices, config.getName(), ix + 2.0f,
                iy + 4.0f, -1);
    }

    /**
     *
     *
     * @param mouseX
     * @param mouseY
     * @param button
     */
    @Override
    public void mouseClicked(double mouseX, double mouseY, int button)
    {
        if (isWithin(mouseX, mouseY))
        {
            if (button == 0)
            {
                boolean val = config.getValue();
                config.setValue(!val);
            }
        }
    }

    /**
     *
     *
     * @param mouseX
     * @param mouseY
     * @param button
     */
    @Override
    public void mouseReleased(double mouseX, double mouseY, int button)
    {

    }

    /**
     *
     *
     * @param keyCode
     * @param scanCode
     * @param modifiers
     */
    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers)
    {

    }

}
