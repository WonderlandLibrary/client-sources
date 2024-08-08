package in.momin5.cookieclient.api.event.events;

import in.momin5.cookieclient.api.event.Event;
import net.minecraft.item.ItemStack;

public class TooltipRenderEvent extends Event {
    private ItemStack Item;
    private int X;
    private int Y;

    public TooltipRenderEvent(ItemStack p_Stack, int p_X, int p_Y)
    {
        Item = p_Stack;
        X = p_X;
        Y = p_Y;
    }

    public ItemStack getItemStack()
    {
        return Item;
    }

    public int getX()
    {
        return X;
    }

    public int getY()
    {
        return Y;
    }

}
