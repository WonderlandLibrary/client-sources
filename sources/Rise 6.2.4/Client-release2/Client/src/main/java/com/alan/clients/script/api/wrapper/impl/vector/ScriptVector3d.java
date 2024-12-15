package com.alan.clients.script.api.wrapper.impl.vector;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScriptVector3d {
    private double x, y, z;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void add(ScriptVector3d vector3) {
        this.x += vector3.getX();
        this.y += vector3.getY();
        this.z += vector3.getZ();
    }
}
