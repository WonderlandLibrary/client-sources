package mathax.client.utils.misc;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector3f;

public class TimeVec extends Vec3d {
    private final long time;

    public TimeVec(double x, double y, double z, long time) {
        super(x, y, z);
        this.time = time;
    }

    public TimeVec(Vec3i vector, long time) {
        super(new Vector3f(vector.getX(), vector.getY(), vector.getZ()));
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
