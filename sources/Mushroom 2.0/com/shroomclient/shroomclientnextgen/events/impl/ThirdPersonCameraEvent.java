package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import net.minecraft.util.math.Vec3d;

public class ThirdPersonCameraEvent extends Event {

    Vec3d camera;

    public ThirdPersonCameraEvent(Vec3d camera) {
        this.camera = camera;

        RenderUtil.cameraVector = camera;
    }
}
