package net.shoreline.client.impl.gui.click.impl.config.setting;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.impl.gui.click.component.Button;
import net.shoreline.client.impl.gui.click.impl.config.CategoryFrame;
import net.minecraft.client.util.math.MatrixStack;

/**
 *
 * @author linus
 * @since 1.0
 *
 * @param <T>
 */
public abstract class ConfigButton<T> extends Button
{
    //
    protected final Config<T> config;

    /**
     *
     *
     * @param frame
     * @param config
     */
    public ConfigButton(CategoryFrame frame, Config<T> config, float x, float y)
    {
        super(frame, x, y, 85.0f, 15.0f);
        this.config = config;
    }

    /**
     *
     *
     * @param matrices
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    @Override
    public void render(MatrixStack matrices, float mouseX, float mouseY,
                       float delta)
    {
        render(matrices, x, y, mouseX, mouseY, delta);
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
    public abstract void render(MatrixStack matrices, float ix, float iy,
                                float mouseX, float mouseY, float delta);

    /**
     *
     *
     * @return
     */
    public Config<T> getConfig()
    {
        return config;
    }
}
