package dev.darkmoon.client.event.player;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.entity.MoverType;

public class EventThunder extends EventCancellable implements Event {

    private boolean toGround;
    public double x, y, z;
    private MoverType move_type;

    public EventThunder(MoverType type, double x, double y, double z, boolean toGround) {
        this.move_type = type;
        this.x = x;
        this.y = y;
        this.toGround = toGround;
        this.z = z;
    }
    public boolean toGround() {
        return this.toGround;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public MoverType get_move_type() {
        return this.move_type;
    }

    public void set_move_type(MoverType type) {
        this.move_type = type;
    }

    public double get_x() {
        return this.x;
    }

    public void set_x(double x) {
        this.x = x;
    }

    public double get_y() {
        return this.y;
    }

    public void set_y(double y) {
        this.y = y;
    }

    public double get_z() {
        return this.z;
    }

    public void set_z(double z) {
        this.z = z;
    }
}
