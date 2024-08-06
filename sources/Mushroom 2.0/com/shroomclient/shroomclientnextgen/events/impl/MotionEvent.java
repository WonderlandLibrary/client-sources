package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Cancellable;
import com.shroomclient.shroomclientnextgen.events.Event;
import com.shroomclient.shroomclientnextgen.util.RotationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

public class MotionEvent extends Event {

    @Cancellable
    @Data
    @AllArgsConstructor
    public static class Pre extends Event {

        private boolean sprinting;
        private boolean sneaking;

        private double x;
        private double y;
        private double z;

        private float yaw;
        private float pitch;

        private boolean onGround;

        private RotationUtil.Rotation rotation;
    }

    @Value
    @AllArgsConstructor
    public static class Post extends Event {

        private boolean sprinting;
        private boolean sneaking;

        private double x;
        private double y;
        private double z;

        private float yaw;
        private float pitch;

        private boolean onGround;
    }
}
