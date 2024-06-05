package zamorozka.event.events;

import net.minecraft.item.ItemStack;
import zamorozka.event.Event;

public class RenderToolTipEvent extends Event {
    private ItemStack stack;
    private int x, y;

    public RenderToolTipEvent(ItemStack stack, int x, int y) {
        this.stack = stack;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ItemStack getStack() {
        return stack;
    }
}