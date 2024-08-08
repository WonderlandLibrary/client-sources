package in.momin5.cookieclient.api.event.events;

import in.momin5.cookieclient.api.event.Event;
import in.momin5.cookieclient.api.event.util.MultiPhase;
import in.momin5.cookieclient.api.event.util.Phase;
import in.momin5.cookieclient.api.util.utils.misc.EnumUtil;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class OnUpdateWalkingPlayerEvent extends Event implements MultiPhase<OnUpdateWalkingPlayerEvent> {
    private final Phase phase;

    private boolean moving = false;
    private boolean rotating = false;
    private Vec3d position;
    private Vec2f rotation;

    public OnUpdateWalkingPlayerEvent(Vec3d position, Vec2f rotation) {
        this(position, rotation, Phase.PRE);
    }

    private OnUpdateWalkingPlayerEvent(Vec3d position, Vec2f rotation, Phase phase) {
        this.position = position;
        this.rotation = rotation;
        this.phase = phase;
    }

    @Override
    public OnUpdateWalkingPlayerEvent nextPhase() {
        return new OnUpdateWalkingPlayerEvent(position, rotation, EnumUtil.next(phase));
    }


    public boolean isMoving() {
        return moving;
    }

    public boolean isRotating() {
        return rotating;
    }

    public Vec3d getPosition() {
        return position;
    }

    public Vec2f getRotation() {
        return rotation;
    }

    @Override
    public Phase getPhase() {
        return phase;
    }



}
