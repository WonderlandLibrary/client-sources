package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Event;

/**
 * @author Rederpz
 */
public class PlaceBlockEvent extends Event {
    private int placeDelay;

    public PlaceBlockEvent(int placeDelay) {
        this.placeDelay = placeDelay;
    }

    public int getPlaceDelay() {
        return placeDelay;
    }

    public void setPlaceDelay(int placeDelay) {
        this.placeDelay = placeDelay;
    }
}
