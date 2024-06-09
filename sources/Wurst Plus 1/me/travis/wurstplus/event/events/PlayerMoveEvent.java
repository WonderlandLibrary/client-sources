/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.MoverType
 */
package me.travis.wurstplus.event.events;

import me.travis.wurstplus.event.wurstplusEvent;
import net.minecraft.entity.MoverType;

public class PlayerMoveEvent
extends wurstplusEvent {
    private MoverType type;
    private double x;
    private double y;
    private double z;

    public PlayerMoveEvent(MoverType type, double x, double y, double z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MoverType getType() {
        return this.type;
    }

    public void setType(MoverType type) {
        this.type = type;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}

