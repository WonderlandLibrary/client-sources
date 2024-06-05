package lol.main.events;

import lol.base.addons.EventAddon;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MotionEvent extends EventAddon {
    public double x, y, z;
    public boolean onGround;
    public float yaw, pitch;
}
