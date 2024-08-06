package club.strifeclient.event.implementations.player;

import club.strifeclient.event.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MotionEvent extends Event {
    public double prevX, prevY, prevZ;
    public double x, y, z;
    public float prevYaw, prevPitch, yaw, pitch;
    public boolean ground;
}
