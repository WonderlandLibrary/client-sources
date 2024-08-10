package cc.slack.events.impl.player;

import cc.slack.events.Event;
import cc.slack.events.State;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.entity.EntityPlayerSP;

@Getter
@Setter
public class MotionEvent extends Event {
    private double x, y, z;
    private float yaw, pitch;
    private float lastTickYaw, lastTickPitch;
    private boolean ground, forcedC06;
    private State state;
    private final EntityPlayerSP player;

    public MotionEvent(double x, double y, double z, float yaw, float pitch, float lastTickYaw, float lastTickPitch, boolean ground, EntityPlayerSP player) {
        this.state = State.PRE;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.lastTickYaw = lastTickYaw;
        this.lastTickPitch = lastTickPitch;
        this.ground = ground;
        this.player = player;
    }
}