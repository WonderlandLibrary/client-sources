/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import me.thekirkayt.utils.MathUtils;
import me.thekirkayt.utils.SallosRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.Packet;
import net.minecraft.src.BlockUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import org.apache.http.util.EntityUtils;

public class Helper {
    private static EntityUtils entityUtils;
    private static BlockUtils blockUtils;
    private static SallosRender.R2DUtils r2DUtils;
    private static SallosRender.R3DUtils r3DUtils;
    private static SallosRender.ColorUtils colorUtils;
    private static MathUtils mathUtils;

    static {
        r2DUtils = new SallosRender.R2DUtils();
        r3DUtils = new SallosRender.R3DUtils();
        colorUtils = new SallosRender.ColorUtils();
    }

    public static float[] aimAtLocation(double x, double y, double z, EnumFacing facing) {
        Helper.mc();
        EntitySnowball temp = new EntitySnowball(Minecraft.theWorld);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        EntitySnowball entitySnowball = temp;
        entitySnowball.posX += (double)facing.getDirectionVec().getX() * 0.25;
        EntitySnowball entitySnowball2 = temp;
        entitySnowball2.posY += (double)facing.getDirectionVec().getY() * 0.25;
        EntitySnowball entitySnowball3 = temp;
        entitySnowball3.posZ += (double)facing.getDirectionVec().getZ() * 0.25;
        return Helper.aimAtLocation(temp.posX, temp.posY, temp.posZ);
    }

    public static float[] aimAtLocation(double positionX, double positionY, double positionZ) {
        Helper.mc();
        double x = positionX - Minecraft.thePlayer.posX;
        Helper.mc();
        double y = positionY - Minecraft.thePlayer.posY;
        Helper.mc();
        double z = positionZ - Minecraft.thePlayer.posZ;
        double distance = MathHelper.sqrt_double(x * x + z * z);
        return new float[]{(float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(Math.atan2(y, distance) * 180.0 / 3.141592653589793))};
    }

    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP player() {
        Helper.mc();
        return Minecraft.thePlayer;
    }

    public static WorldClient world() {
        Helper.mc();
        return Minecraft.theWorld;
    }

    public static EntityUtils entityUtils() {
        return entityUtils;
    }

    public static BlockUtils blockUtils() {
        return blockUtils;
    }

    public static SallosRender.R2DUtils get2DUtils() {
        return r2DUtils;
    }

    public static SallosRender.R3DUtils get3DUtils() {
        return r3DUtils;
    }

    public static SallosRender.ColorUtils colorUtils() {
        return colorUtils;
    }

    public static MathUtils mathUtils() {
        return mathUtils;
    }

    public static void sendPacket(Packet p) {
        Helper.mc();
        Minecraft.getNetHandler().addToSendQueue(p);
    }
}

