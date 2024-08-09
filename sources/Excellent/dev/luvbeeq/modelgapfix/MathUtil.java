package dev.luvbeeq.modelgapfix;

import dev.excellent.api.interfaces.game.IMinecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

public class MathUtil implements IMinecraft {
    public static float unlerp(float delta, float start, float end) {
        return (start - delta * end) / (1 - delta);
    }

    public static float[] unlerpUVs(float[] uvs, float delta) {
        float centerU = (uvs[0] + uvs[2]) / 2.0F;
        float centerV = (uvs[1] + uvs[3]) / 2.0F;
        uvs[0] = unlerp(delta, uvs[0], centerU);
        uvs[2] = unlerp(delta, uvs[2], centerU);
        uvs[1] = unlerp(delta, uvs[1], centerV);
        uvs[3] = unlerp(delta, uvs[3], centerV);
        return uvs;
    }

    public static Vector2f rotationToVec(Vector3d vec) {
        Vector3d eyesPos = mc.player.getEyePosition(1.0f);
        double diffX = vec != null ? vec.x - eyesPos.x : 0;
        double diffY = vec != null ? vec.y - (mc.player.getPosY() + (double) mc.player.getEyeHeight() + 0.5) : 0;
        double diffZ = vec != null ? vec.z - eyesPos.z : 0;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        yaw = mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw);
        pitch = mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch);
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);

        return new Vector2f(yaw, pitch);
    }
}