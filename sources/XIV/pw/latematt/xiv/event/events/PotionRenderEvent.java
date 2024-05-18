package pw.latematt.xiv.event.events;

import net.minecraft.potion.Potion;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Rederpz
 */
public class PotionRenderEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final Potion potion;

    public PotionRenderEvent(Potion potion) {
        this.potion = potion;
    }

    public Potion getEffect() {
        return potion;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
