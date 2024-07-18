package com.alan.clients.module.impl.other.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Entity {
    private double x, y, z;
    private double yaw, pitch;
    @Getter
    private int id;
    @Getter
    private boolean click;

    public double gPX() {
        return x;
    }

    public double gPY() {
        return y;
    }

    public double gPZ() {
        return z;
    }

    public double gRY() {
        return yaw;
    }

    public double gRP() {
        return pitch;
    }
}
