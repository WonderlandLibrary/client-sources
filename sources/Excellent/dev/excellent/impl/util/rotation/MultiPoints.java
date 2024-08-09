package dev.excellent.impl.util.rotation;

import dev.excellent.api.interfaces.game.IMinecraft;
import net.minecraft.entity.Entity;
import org.joml.Vector3d;

public class MultiPoints implements IMinecraft {

    public static Vector3d getBestPoint(Entity entity) {
        float x = (float) limit(entity.getBoundingBox().minX, entity.getBoundingBox().maxX - entity.getBoundingBox().minX, mc.player.getPosX());
        float y = (float) limit(entity.getBoundingBox().minY, entity.getBoundingBox().maxY - entity.getBoundingBox().minY, mc.player.getPosY() + mc.player.getEyeHeight());
        float z = (float) limit(entity.getBoundingBox().minZ, entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ, mc.player.getPosZ());

        return new Vector3d(
                entity.getBoundingBox().minX + (entity.getBoundingBox().maxX - entity.getBoundingBox().minX) * x,
                entity.getBoundingBox().minY + (entity.getBoundingBox().maxY - entity.getBoundingBox().minY) * y,
                entity.getBoundingBox().minZ + (entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ) * z
        );
    }

    private static double limit(double min, double max, double current) {
        return Math.min(1, Math.max(0, (current - min) / max));
    }
}
