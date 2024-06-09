package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.Event;

/**
 * Created by myche on 2/4/2017.
 */
public class RenderWorldEvent implements Event {

    private float partialTicks;

    public RenderWorldEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
