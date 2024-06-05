package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.*;

public class EventMove implements Event
{
    public double x;
    public double y;
    public double z;
    
    public EventMove(final double a, final double b, final double c) {
        this.x = a;
        this.y = b;
        this.z = c;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
}
