package net.shoreline.client.impl.gui.click.impl.config.setting;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.MacroConfig;
import net.shoreline.client.api.macro.Macro;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.gui.click.impl.config.CategoryFrame;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class BindButton extends ConfigButton<Macro>
{
    // Check for whether we are listening for an input
    private boolean listen;

    /**
     *
     * @param frame
     * @param config
     * @param x
     * @param y
     */
    public BindButton(CategoryFrame frame, Config<Macro> config, float x, float y)
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
        final Macro macro = config.getValue();
        String val = listen ? "..." : macro.getKeyName();
        rect(matrices, 0x00000000);
        RenderManager.renderText(matrices, config.getName() + Formatting.GRAY
                        + " " + val, ix + 2.0f, iy + 4.0f, -1);
    }

    /**
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
                listen = !listen;
            }
        }
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
        if (listen)
        {
            // unbind
            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_BACKSPACE)
            {
                ((MacroConfig) config).setValue(GLFW.GLFW_KEY_UNKNOWN);
            }
            else
            {
                ((MacroConfig) config).setValue(keyCode);
            }
            listen = false;
        }
    }
}
