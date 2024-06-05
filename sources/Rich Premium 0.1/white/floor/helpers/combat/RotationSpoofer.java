package white.floor.helpers.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationSpoofer {
    public static boolean isLookingAtEntity1(Entity e) {
        return isLookingAt2(e.getPositionEyes((Minecraft.getMinecraft()).timer.renderPartialTicks));
    }
    public static boolean isLookingAt2(Vec3d vec) {
        Float[] targetangles = RotationSpoofer.getLookAngles(vec);
        targetangles = RotationSpoofer.getLookAngles(vec);
        float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0].floatValue() - Minecraft.player.rotationYaw)) / 0.6f;
        return change < 20.0f;
    }
    public static Float[] getLookAngles(Vec3d vec) {
        Float[] angles = new Float[2];
        Minecraft mc = Minecraft.getMinecraft();
        angles[0] = Float.valueOf((float)(Math.atan2(Minecraft.player.posZ - vec.zCoord, Minecraft.player.posX - vec.xCoord) / Math.PI * 180.0) + 90.0f);
        float heightdiff = (float)(Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() - vec.yCoord);
        float distance = (float)Math.sqrt((Minecraft.player.posZ - vec.zCoord) * (Minecraft.player.posZ - vec.zCoord) + (Minecraft.player.posX - vec.xCoord) * (Minecraft.player.posX - vec.xCoord));
        angles[1] = Float.valueOf((float)(Math.atan2(heightdiff, distance) / Math.PI * 180.0));
        return angles;
    }

}
