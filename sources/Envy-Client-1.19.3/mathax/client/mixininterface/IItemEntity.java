package mathax.client.mixininterface;

import net.minecraft.util.math.Vec3d;

public interface IItemEntity {
    Vec3d getRotation();
    void setRotation(Vec3d rotation);
}
