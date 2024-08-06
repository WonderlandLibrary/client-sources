package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import net.minecraft.util.math.Vec2f;

public class FirstPersonCameraEvent extends Event {

    Vec2f camera;

    public FirstPersonCameraEvent(Vec2f camera) {
        this.camera = camera;

        RenderUtil.lastCameraY = camera.x;
        RenderUtil.cameraY = camera.y;
    }
}
