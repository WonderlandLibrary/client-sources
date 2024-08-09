package mathax.client.events.entity.player;

import mathax.client.events.Event;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

public class MoveEvent extends Event {

    private MovementType type;
    private Vec3d vec;

    public MoveEvent(MovementType type, Vec3d vec) {
        this.type = type;
        this.vec = vec;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public Vec3d getVec() {
        return vec;
    }

    public void setVec(Vec3d vec) {
        this.vec = vec;
    }
}
