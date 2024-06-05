package me.tojatta.api.utilities.utilities.angle;

import me.tojatta.api.utilities.utilities.vector.impl.*;
import me.tojatta.api.utilities.utilities.vector.*;

public class Angle extends Vector2<Float>
{
    public Angle(final Float x, final Float y) {
        super(x, y);
    }
    
    public Float getYaw() {
        return this.getX().floatValue();
    }
    
    public Angle setYaw(final Float yaw) {
        ((Vector<Float>)this).setX(yaw);
        return this;
    }
    
    public Float getPitch() {
        return this.getY().floatValue();
    }
    
    public Angle setPitch(final Float pitch) {
        ((Vector<Float>)this).setY(pitch);
        return this;
    }
    
    public Angle constrantAngle() {
        this.setYaw(this.getYaw() % 360.0f);
        this.setPitch(this.getPitch() % 360.0f);
        while (this.getYaw() <= -180.0f) {
            this.setYaw(this.getYaw() + 360.0f);
        }
        while (this.getPitch() <= -180.0f) {
            this.setPitch(this.getPitch() + 360.0f);
        }
        while (this.getYaw() > 180.0f) {
            this.setYaw(this.getYaw() - 360.0f);
        }
        while (this.getPitch() > 180.0f) {
            this.setPitch(this.getPitch() - 360.0f);
        }
        return this;
    }
}
