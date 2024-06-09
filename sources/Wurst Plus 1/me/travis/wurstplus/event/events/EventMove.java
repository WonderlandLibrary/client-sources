package me.travis.wurstplus.event.events;

import net.minecraft.entity.MoverType;

public class EventMove extends EventCancellable
{
    private MoverType moverType;
    private double x;
    private double y;
    private double z;
    
    public EventMove(final MoverType moverType, final double x, final double y, final double z) {
        this.moverType = moverType;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public MoverType getMoverType() {
        return this.moverType;
    }
    
    public void setMoverType(final MoverType moverType) {
        this.moverType = moverType;
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
