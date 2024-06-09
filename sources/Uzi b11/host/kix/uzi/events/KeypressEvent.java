package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.Event;

/**
 * Created by myche on 2/4/2017.
 */
public class KeypressEvent implements Event {

    private int key;

    public KeypressEvent(final int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(final int key) {
        this.key = key;
    }

}
