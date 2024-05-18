package pw.latematt.xiv.event.events;

import net.minecraft.entity.player.EntityPlayer;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class InventoryClosedEvent extends Event implements Cancellable {
    private final EntityPlayer player;
    private boolean cancelled;

    public InventoryClosedEvent(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
