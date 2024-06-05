package net.shoreline.client.impl.event.gui;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Cancelable
public class RenderTooltipEvent extends Event
{
    private final MatrixStack matrices;
    private final ItemStack stack;
    //
    private final int x, y;

    public RenderTooltipEvent(MatrixStack matrices, ItemStack stack, int x, int y)
    {
        this.matrices = matrices;
        this.stack = stack;
        this.x = x;
        this.y = y;
    }

    public MatrixStack getMatrices()
    {
        return matrices;
    }

    public ItemStack getStack()
    {
        return stack;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
