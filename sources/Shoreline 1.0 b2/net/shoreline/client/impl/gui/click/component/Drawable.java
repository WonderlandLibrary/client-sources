package net.shoreline.client.impl.gui.click.component;

import net.minecraft.client.util.math.MatrixStack;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public interface Drawable
{
    /**
     *
     *
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    void render(MatrixStack matrixStack, float mouseX, float mouseY,
                float delta);
}
