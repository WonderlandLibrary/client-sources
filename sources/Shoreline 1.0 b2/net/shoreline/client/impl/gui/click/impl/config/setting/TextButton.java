package net.shoreline.client.impl.gui.click.impl.config.setting;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.impl.gui.click.impl.config.CategoryFrame;
import net.minecraft.client.util.math.MatrixStack;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 *
 */
public class TextButton extends ConfigButton<String>
{
    /**
     *
     *
     * @param frame
     * @param config
     */
    public TextButton(CategoryFrame frame, Config<String> config, float x, float y)
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

    }

    /**
     * @param mouseX
     * @param mouseY
     * @param button
     */
    @Override
    public void mouseClicked(double mouseX, double mouseY, int button)
    {

    }

    /**
     * @param mouseX
     * @param mouseY
     * @param button
     */
    @Override
    public void mouseReleased(double mouseX, double mouseY, int button)
    {

    }

    /**
     * @param keyCode
     * @param scanCode
     * @param modifiers
     */
    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers)
    {

    }
}
