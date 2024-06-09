package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.Event;

/**
 * Created by myche on 2/27/2017.
 */
public class MotionEvent implements Event {

    public double x, y, z;

    public MotionEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
