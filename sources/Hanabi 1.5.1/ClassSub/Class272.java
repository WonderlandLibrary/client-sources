package ClassSub;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import cn.Hanabi.injection.interfaces.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.events.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;

public final class Class272
{
    private static Minecraft mc;
    public static boolean l;
    public static float ll;
    public static float l1;
    private static int l11;
    public static boolean lll;
    public static float[] ll1;
    private static double llll;
    private static double lll1;
    private static double lmc;
    
    
    public static double ll(final Entity entity) {
        final float[] array = l(entity);
        if (array == null) {
            return 0.0;
        }
        return l(array[0], array[1]);
    }
    
    public static double l(final float n, final float n2) {
        return Math.sqrt(Math.pow(Math.abs(l(Class272.ll1[0] % 360.0f, (double)n)), 2.0) + Math.pow(Math.abs(l(Class272.ll1[1], (double)n2)), 2.0));
    }
    
    public static void l1() {
        Class272.l = false;
        Class272.l11 = 0;
        Class272.ll = 0.0f;
        Class272.l1 = 0.0f;
    }
    
    @EventTarget
    public static void l(final EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof C03PacketPlayer) {
            final C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)eventPacket.getPacket();
            if (Class272.l && !Class272.lll && (Class272.ll != Class272.ll1[0] || Class272.l1 != Class272.ll1[1])) {
                ((IC03PacketPlayer)c03PacketPlayer).setYaw(Class272.ll);
                ((IC03PacketPlayer)c03PacketPlayer).setPitch(Class272.l1);
                ((IC03PacketPlayer)c03PacketPlayer).setRotate(true);
            }
            if (((IC03PacketPlayer)c03PacketPlayer).getRotate()) {
                Class272.ll1 = new float[] { ((IC03PacketPlayer)c03PacketPlayer).getYaw(), ((IC03PacketPlayer)c03PacketPlayer).getPitch() };
            }
        }
    }
    
    @EventTarget
    public static void update(final EventUpdate eventUpdate) {
        if (Class272.l && ++Class272.l11 > 15) {
            l1();
        }
        final Random random = new Random();
        if (random.nextGaussian() * 100.0 > 80.0) {
            Class272.llll = Math.random() / 3.0;
        }
        if (random.nextGaussian() * 100.0 > 80.0) {
            Class272.lll1 = Math.random() / 3.0;
        }
        if (random.nextGaussian() * 100.0 > 80.0) {
            Class272.lmc = Math.random() / 3.0;
        }
    }
    
    public static Vec3 l(final AxisAlignedBB axisAlignedBB) {
        return new Vec3(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * 0.5, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * 0.5, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * 0.5);
    }
    
    public static float[] l(final float[] array, final float[] array2, final float n) {
        final double l = l(array2[0], (double)array[0]);
        final double i = l(array2[1], (double)array[1]);
        array[0] += (float)((l > n) ? n : ((l < -n) ? (-n) : l));
        array[1] += (float)((i > n) ? n : ((i < -n) ? (-n) : i));
        return array;
    }
    
    public static Vec3 ll() {
        return new Vec3(Class272.mc.thePlayer.posX, Class272.mc.thePlayer.getEntityBoundingBox().minY + Class272.mc.thePlayer.getEyeHeight(), Class272.mc.thePlayer.posZ);
    }
    
    private static double l(final double n, final double n2) {
        return ((n - n2) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    public static Vec3 l(final AxisAlignedBB axisAlignedBB, final boolean b) {
        if (b) {
            return new Vec3(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * (Class272.llll * 0.3 + 1.0), axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * (Class272.lll1 * 0.3 + 1.0), axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * (Class272.lmc * 0.3 + 1.0));
        }
        return new Vec3(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * Class272.llll * 0.8, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * Class272.lll1 * 0.8, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * Class272.lmc * 0.8);
    }
    
    public boolean l() {
        return true;
    }
    
    public static Vec3 ll(final float n, final float n2) {
        final float cos = MathHelper.cos(-n2 * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-n2 * 0.017453292f - 3.1415927f);
        final float n3 = -MathHelper.cos(-n * 0.017453292f);
        return new Vec3((double)(sin * n3), (double)MathHelper.sin(-n * 0.017453292f), (double)(cos * n3));
    }
    
    public static void l(final Entity entity, final boolean b, final boolean b2, final float n) {
        final EntityPlayerSP thePlayer = Class272.mc.thePlayer;
        final double n2 = entity.posX + (b2 ? ((entity.posX - entity.prevPosX) * n) : 0.0) - (thePlayer.posX + (b2 ? (thePlayer.posX - thePlayer.prevPosX) : 0.0));
        final double n3 = entity.getEntityBoundingBox().minY + (b2 ? ((entity.getEntityBoundingBox().minY - entity.prevPosY) * n) : 0.0) + entity.getEyeHeight() - 0.15 - (thePlayer.getEntityBoundingBox().minY + (b2 ? (thePlayer.posY - thePlayer.prevPosY) : 0.0)) - thePlayer.getEyeHeight();
        final double n4 = entity.posZ + (b2 ? ((entity.posZ - entity.prevPosZ) * n) : 0.0) - (thePlayer.posZ + (b2 ? (thePlayer.posZ - thePlayer.prevPosZ) : 0.0));
        final double n5 = n2;
        final double n6 = n4;
        final double sqrt = Math.sqrt(n5 * n5 + n6 * n6);
        final float n7 = thePlayer.getItemInUseCount() / 20.0f;
        float n8;
        if ((n8 = (n7 * n7 + n7 * 2.0f) / 3.0f) > 1.0f) {
            n8 = 1.0f;
        }
        float n9 = (float)(Math.atan2(n4, n2) * 180.0 / 3.141592653589793) - 90.0f;
        final float n10 = n8;
        final float n11 = n8;
        final double n12 = sqrt;
        final float n13 = n8;
        float n14 = (float)(-Math.toDegrees(Math.atan((n10 * n10 - Math.sqrt(n11 * n11 * n8 * n8 - 0.006000000052154064 * (0.006000000052154064 * (n12 * n12) + n3 * 2.0 * (n13 * n13)))) / (sqrt * 0.006000000052154064))));
        if (n8 < 0.1f) {
            final float[] array = l(l(entity.getEntityBoundingBox()), true);
            n9 = array[0];
            n14 = array[1];
        }
        if (b) {
            l1(n9, n14);
            return;
        }
        final float[] array2 = l(new float[] { thePlayer.rotationYaw, thePlayer.rotationPitch }, new float[] { n9, n14 }, 10 + new Random().nextInt(6));
        if (array2 == null) {
            return;
        }
        thePlayer.rotationYaw = array2[0];
        thePlayer.rotationPitch = array2[1];
    }
    
    public static void l1(final float ll, final float l1) {
        if (Double.isNaN(ll) || Double.isNaN(l1)) {
            return;
        }
        Class272.ll = ll;
        Class272.l1 = l1;
        Class272.l = true;
        Class272.l11 = 0;
    }
    
    public static float[] l(final Entity entity) {
        if (entity == null || Class272.mc.thePlayer == null) {
            return null;
        }
        return l(l(entity.getEntityBoundingBox(), false), true);
    }
    
    public static float[] l(final Vec3 vec3, final boolean b) {
        final Vec3 ll = ll();
        if (b) {
            ll.addVector(Class272.mc.thePlayer.motionX, Class272.mc.thePlayer.motionY, Class272.mc.thePlayer.motionZ);
        }
        final double n = vec3.xCoord - ll.xCoord;
        final double n2 = vec3.yCoord - ll.yCoord;
        final double n3 = vec3.zCoord - ll.zCoord;
        final double n4 = n;
        final double n5 = n3;
        return new float[] { MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(n3, n)) - 90.0f), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(n2, Math.sqrt(n4 * n4 + n5 * n5))))) };
    }
    
    public static void l(final BlockPos blockPos) {
        if (blockPos == null) {
            return;
        }
        final double n = blockPos.getX() + 0.5 - Class272.mc.thePlayer.posX;
        final double n2 = blockPos.getY() + 0.5 - (Class272.mc.thePlayer.getEntityBoundingBox().minY + Class272.mc.thePlayer.getEyeHeight());
        final double n3 = blockPos.getZ() + 0.5 - Class272.mc.thePlayer.posZ;
        final double n4 = n;
        final double n5 = n3;
        l1(Class272.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f - Class272.mc.thePlayer.rotationYaw), Class272.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-Math.atan2(n2, Math.sqrt(n4 * n4 + n5 * n5)) * 180.0 / 3.141592653589793) - Class272.mc.thePlayer.rotationPitch));
    }
    
    static {
        Class272.mc = Minecraft.getMinecraft();
        Class272.lll = false;
        Class272.ll1 = new float[] { 0.0f, 0.0f };
        Class272.llll = Math.random() / 3.0;
        Class272.lll1 = Math.random() / 3.0;
        Class272.lmc = Math.random() / 3.0;
    }
}
