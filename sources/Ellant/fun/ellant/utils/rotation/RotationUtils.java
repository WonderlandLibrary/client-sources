package fun.ellant.utils.rotation;

import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.math.VectorUtils;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;

@UtilityClass
public class RotationUtils implements IMinecraft {
    public Vector3d getClosestVec(Entity entity) {
        Vector3d eyePosVec = mc.player.getEyePosition(1.0F);

        return VectorUtils.getClosestVec(eyePosVec, entity).subtract(eyePosVec);
    }

    public double getStrictDistance(Entity entity) {
        return getClosestVec(entity).length();
    }

}
