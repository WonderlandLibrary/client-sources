package in.momin5.cookieclient.api.util.utils.rotation;

import in.momin5.cookieclient.api.util.utils.client.Wrapper;
import in.momin5.cookieclient.api.util.utils.misc.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtils {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static void faceVectorPacketInstant(Vec3d vec, Boolean roundAngles) {
        float[] rotations = getNeededRotations2(vec);

        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], roundAngles ? MathHelper.normalizeAngle((int) rotations[1], 360) : rotations[1], mc.player.onGround));
    }

    private static float[] getNeededRotations2(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[] {
                mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw),
                mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch)
        };
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    }

    public static void faceBlockPacket(BlockPos pos) {

        float oldYaw = Wrapper.mc.player.rotationYaw;
        float oldPitch = Wrapper.mc.player.rotationPitch;

        final double n = pos.getX() + 0.25;
        final double n2 = pos.getZ() + 0.25;
        final double var4 = n - Wrapper.mc.player.posX;
        final double var5 = n2 - Wrapper.mc.player.posZ;
        final double n3 = pos.getY() + 0.25;
        final double posY = Wrapper.mc.player.posY;
        final double var6 = n3 - (posY + Wrapper.mc.player.getEyeHeight());
        final double var7 = MathHelper.sqrt(var4 * var4 + var5 * var5);
        final float yaw = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793));
        final double posX = Wrapper.mc.player.posX;
        final double posY2 = Wrapper.mc.player.posY;
        final double posZ = Wrapper.mc.player.posZ;
        final float p_i45941_7_ = yaw;
        final float p_i45941_8_ = pitch;
        Wrapper.mc.player.rotationYawHead = p_i45941_7_;
        double oldPosX = Wrapper.mc.player.posX;
        double oldPosY = Wrapper.mc.player.posY;
        double oldPosZ = Wrapper.mc.player.posZ;

        Wrapper.mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(posX, posY2, posZ, p_i45941_7_, p_i45941_8_, Wrapper.mc.player.onGround));
    }

    public static Vec3d getClientLookVec()
    {
        float f = MathUtil.cos(-Wrapper.mc.player.rotationYaw * 0.017453292F
                - (float)Math.PI);
        float f1 = MathUtil.sin(-Wrapper.mc.player.rotationYaw * 0.017453292F
                - (float)Math.PI);
        float f2 =
                - MathUtil.cos(-Wrapper.mc.player.rotationPitch * 0.017453292F);
        float f3 =
                MathUtil.sin(-Wrapper.mc.player.rotationPitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }

}
