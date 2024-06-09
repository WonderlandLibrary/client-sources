package com.client.glowclient;

import com.client.glowclient.utils.*;
import net.minecraft.entity.*;
import com.client.glowclient.modules.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

public class y
{
    public static float A;
    private static boolean B;
    public static float b;
    
    public static void D(final Vec3d vec3d) {
        Wrapper.mc.player.rotationYaw = M(vec3d)[0];
    }
    
    public static void M(final Entity entity) {
        M(entity.getEntityBoundingBox().getCenter());
    }
    
    public static void M(final Module module) {
        if (module.k) {
            y.b = Wrapper.mc.player.rotationYaw;
            y.A = Wrapper.mc.player.rotationPitch;
            module.k = false;
        }
    }
    
    static {
        y.A = Wrapper.mc.player.rotationPitch;
        y.b = Wrapper.mc.player.rotationYaw;
        y.B = false;
    }
    
    public static void M(final Entity entity, final Module module) {
        M(entity.getEntityBoundingBox().getCenter(), module);
    }
    
    private static float[] D(final Vec3d vec3d) {
        final Vec3d vec3d2 = new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ);
        final double n = vec3d.x - vec3d2.x;
        final double n2 = vec3d.y - vec3d2.y;
        final double n3 = vec3d.z - vec3d2.z;
        final double n4 = n;
        final double n5 = n4 * n4;
        final double n6 = n3;
        return new float[] { Wrapper.mc.player.rotationYaw + MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(n3, n)) - 90.0f - Wrapper.mc.player.rotationYaw), Wrapper.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(n2, Math.sqrt(n5 + n6 * n6)))) - Wrapper.mc.player.rotationPitch) };
    }
    
    public y() {
        super();
    }
    
    public static void M(final Vec3d vec3d) {
        final float[] m = M(vec3d);
        Wrapper.mc.player.rotationYaw = m[0];
        Wrapper.mc.player.rotationPitch = m[1];
    }
    
    public static void M(final float rotationYaw, final float rotationPitch) {
        Wrapper.mc.player.rotationYaw = rotationYaw;
        Wrapper.mc.player.rotationPitch = rotationPitch;
    }
    
    private static float[] M(final Vec3d vec3d) {
        final Vec3d vec3d2 = new Vec3d(Wrapper.mc.player.posX, Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(), Wrapper.mc.player.posZ);
        final double n = vec3d.x - vec3d2.x;
        final double n2 = vec3d.y - vec3d2.y;
        final double n3 = vec3d.z - vec3d2.z;
        final double n4 = n;
        final double n5 = n4 * n4;
        final double n6 = n3;
        return new float[] { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(n3, n)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(n2, Math.sqrt(n5 + n6 * n6))))) };
    }
    
    public static void M(final float b, final float a, final Module module) {
        y.b = b;
        y.A = a;
        module.k = true;
    }
    
    public static void M() {
        if (y.B) {
            final EntityPlayerSP player = Wrapper.mc.player;
            player.rotationPitch += (float)4.0E-4;
            y.B = false;
            return;
        }
        final EntityPlayerSP player2 = Wrapper.mc.player;
        player2.rotationPitch -= (float)4.0E-4;
        y.B = true;
    }
    
    public static void M(final Vec3d vec3d, final Module module) {
        final float[] d = D(vec3d);
        final int k = 1;
        final float[] array = d;
        y.b = array[0];
        y.A = array[k];
        module.k = (k != 0);
    }
}
