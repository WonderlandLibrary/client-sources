package de.tired.api.util.rotation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Rotations {

    public boolean noRotate;

    public float scaffoldYaw, scaffoldPitch;

    public float yawDifference, pitchDifference;

    public static float yaw, pitch, beforePitch, beforeYaw;

}
