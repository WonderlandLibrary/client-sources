package wwe;

import wwe.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;

public class WFux
{
    public WFux() {
        super();
    }
    
    public static void FayH(final BlockPos blockPos) {
        final float rotationYaw = Wrapper.mc.player.rotationYaw;
        final float rotationPitch = Wrapper.mc.player.rotationPitch;
        final double n = blockPos.getX() + 0.25;
        final double n2 = blockPos.getZ() + 0.25;
        final double n3 = n - Wrapper.mc.player.posX;
        final double n4 = n2 - Wrapper.mc.player.posZ;
        final double n5 = blockPos.getY() + 0.25 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n6 = MathHelper.sqrt(n3 * n3 + n4 * n4);
        final float n7 = (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        final float n8 = (float)(-(Math.atan2(n5, n6) * 180.0 / 3.141592653589793));
        final double posX = Wrapper.mc.player.posX;
        final double posY = Wrapper.mc.player.posY;
        final double posZ = Wrapper.mc.player.posZ;
        final float rotationYawHead = n7;
        final float n9 = n8;
        Wrapper.mc.player.rotationYawHead = rotationYawHead;
        final double posX2 = Wrapper.mc.player.posX;
        final double posY2 = Wrapper.mc.player.posY;
        final double posZ2 = Wrapper.mc.player.posZ;
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(posX, posY, posZ, rotationYawHead, n9, Wrapper.mc.player.onGround));
    }
    
    public static void oAdl(final BlockPos blockPos) {
        final double n = blockPos.getX() + 0.25;
        final double n2 = blockPos.getZ() + 0.25;
        final double n3 = n - Wrapper.mc.player.posX;
        final double n4 = n2 - Wrapper.mc.player.posZ;
        final double n5 = blockPos.getY() + 0.25 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n6 = MathHelper.sqrt(n3 * n3 + n4 * n4);
        final float n7 = (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        final float n8 = (float)(-(Math.atan2(n5, n6) * 180.0 / 3.141592653589793));
        final double posX = Wrapper.mc.player.posX;
        final double posY = Wrapper.mc.player.posY;
        final double posZ = Wrapper.mc.player.posZ;
        final float rotationYaw = n7;
        final float rotationPitch = n8;
        Wrapper.mc.player.rotationYaw = rotationYaw;
        Wrapper.mc.player.rotationPitch = rotationPitch;
    }
    
    public static void cfCu(final int n, final int n2, final int n3) {
        final float rotationYaw = Wrapper.mc.player.rotationYaw;
        final float rotationPitch = Wrapper.mc.player.rotationPitch;
        final double n4 = n + 0.25;
        final double n5 = n3 + 0.25;
        final double n6 = n4 - Wrapper.mc.player.posX;
        final double n7 = n5 - Wrapper.mc.player.posZ;
        final double n8 = n2 + 0.25 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n9 = MathHelper.sqrt(n6 * n6 + n7 * n7);
        final float rotationYawHead = (float)(Math.atan2(n7, n6) * 180.0 / 3.141592653589793) - 90.0f;
        final float n10 = (float)(-(Math.atan2(n8, n9) * 180.0 / 3.141592653589793));
        final double posX = Wrapper.mc.player.posX;
        final double posY = Wrapper.mc.player.posY;
        final double posZ = Wrapper.mc.player.posZ;
        Wrapper.mc.player.rotationYawHead = rotationYawHead;
        final double posX2 = Wrapper.mc.player.posX;
        final double posY2 = Wrapper.mc.player.posY;
        final double posZ2 = Wrapper.mc.player.posZ;
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotationYawHead, n10, Wrapper.mc.player.onGround));
        Wrapper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotationYaw, rotationPitch, Wrapper.mc.player.onGround));
    }
    
    public static float[] Goyn(final BlockPos blockPos) {
        final float rotationYaw = Wrapper.mc.player.rotationYaw;
        final float rotationPitch = Wrapper.mc.player.rotationPitch;
        final double n = blockPos.getX() + 0.25;
        final double n2 = blockPos.getZ() + 0.25;
        final double n3 = n - Wrapper.mc.player.posX;
        final double n4 = n2 - Wrapper.mc.player.posZ;
        final double n5 = blockPos.getY() + 0.25 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n6 = MathHelper.sqrt(n3 * n3 + n4 * n4);
        final float n7 = (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        final float n8 = (float)(-(Math.atan2(n5, n6) * 180.0 / 3.141592653589793));
        final double posX = Wrapper.mc.player.posX;
        final double posY = Wrapper.mc.player.posY;
        final double posZ = Wrapper.mc.player.posZ;
        final float rotationYawHead = n7;
        final float n9 = n8;
        Wrapper.mc.player.rotationYawHead = rotationYawHead;
        return new float[] { rotationYawHead, n9 };
    }
    
    public static float[] PMvk(final double n, final double n2, final double n3) {
        final double n4 = n - Wrapper.mc.player.posX;
        final double n5 = n3 - Wrapper.mc.player.posZ;
        final double n6 = n2 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n7 = MathHelper.sqrt(n4 * n4 + n5 * n5);
        final float n8 = (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793) - 90.0f;
        final float n9 = (float)(-(Math.atan2(n6, n7) * 180.0 / 3.141592653589793));
        final float rotationYaw = Wrapper.mc.player.rotationYaw;
        final float rotationPitch = Wrapper.mc.player.rotationPitch;
        final EntityPlayerSP player = Wrapper.mc.player;
        player.rotationYawHead += MathHelper.wrapDegrees(n8 - Wrapper.mc.player.rotationYaw);
        return new float[] { rotationYaw + MathHelper.wrapDegrees(n8 - Wrapper.mc.player.rotationYaw), rotationPitch + MathHelper.wrapDegrees(n9 - Wrapper.mc.player.rotationPitch) };
    }
}
