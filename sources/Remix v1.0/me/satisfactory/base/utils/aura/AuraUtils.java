package me.satisfactory.base.utils.aura;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;

public class AuraUtils
{
    public static ArrayList<EntityLivingBase> targets;
    public static ArrayList<EntityLivingBase> blackList;
    public static Minecraft mc;
    private static boolean disableAura;
    private static boolean reachExploit;
    private static int timercap;
    private static double range;
    private static boolean headsnap;
    private static double chargerange;
    private static double packetTPRange;
    
    public static boolean hasEntity(final Entity en) {
        if (en == null) {
            return false;
        }
        if (!AuraUtils.targets.isEmpty()) {
            for (final EntityLivingBase en2 : AuraUtils.targets) {
                if (en2 == null) {
                    continue;
                }
                if (en2.isEntityEqual(en)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static Entity getMouseOver(final float yaw, final float pitch, final Entity target) {
        Entity pointedEntity = null;
        final float p_78473_1_ = 1.0f;
        final Entity var2 = AuraUtils.mc.getRenderViewEntity();
        if (var2 != null && AuraUtils.mc.theWorld != null) {
            AuraUtils.mc.mcProfiler.startSection("pick");
            AuraUtils.mc.pointedEntity = null;
            double var3 = AuraUtils.mc.playerController.getBlockReachDistance();
            AuraUtils.mc.objectMouseOver = var2.rayTrace(var3, p_78473_1_);
            double var4 = var3;
            final Vec3 var5 = var2.getPositionEyes(var2.getEyeHeight());
            var3 = target.getDistanceToEntity(AuraUtils.mc.thePlayer);
            var4 = target.getDistanceToEntity(AuraUtils.mc.thePlayer);
            if (AuraUtils.mc.objectMouseOver != null) {
                var4 = AuraUtils.mc.objectMouseOver.hitVec.distanceTo(var5);
            }
            final Vec3 var6 = var2.getRotationVec(pitch, yaw);
            final Vec3 var7 = var5.addVector(var6.xCoord * var3, var6.yCoord * var3, var6.zCoord * var3);
            pointedEntity = null;
            Vec3 var8 = null;
            final float var9 = 0.3f;
            final List var10 = AuraUtils.mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var6.xCoord * var3, var6.yCoord * var3, var6.zCoord * var3).expand(var9, var9, var9));
            double var11 = var4;
            for (int var12 = 0; var12 < var10.size(); ++var12) {
                final Entity var13 = var10.get(var12);
                if (var13.canBeCollidedWith()) {
                    final float var14 = var13.getCollisionBorderSize();
                    final AxisAlignedBB var15 = var13.getEntityBoundingBox().expand(var14, var14, var14);
                    final MovingObjectPosition var16 = var15.calculateIntercept(var5, var7);
                    if (var15.isVecInside(var5)) {
                        if (0.0 < var11 || var11 == 0.0) {
                            pointedEntity = var13;
                            var8 = ((var16 == null) ? var5 : var16.hitVec);
                            var11 = 0.0;
                        }
                    }
                    else {
                        final double var17;
                        if (var16 != null && ((var17 = var5.distanceTo(var16.hitVec)) < var11 || var11 == 0.0)) {
                            if (var13 == var2.ridingEntity) {
                                if (var11 == 0.0) {
                                    pointedEntity = var13;
                                    var8 = var16.hitVec;
                                }
                            }
                            else {
                                pointedEntity = var13;
                                var8 = var16.hitVec;
                                var11 = var17;
                            }
                        }
                    }
                }
            }
            if (pointedEntity != null && (var11 < var4 || AuraUtils.mc.objectMouseOver == null)) {
                AuraUtils.mc.objectMouseOver = new MovingObjectPosition(pointedEntity, var8);
                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
                    AuraUtils.mc.pointedEntity = pointedEntity;
                }
            }
            AuraUtils.mc.mcProfiler.endSection();
        }
        return pointedEntity;
    }
    
    public static boolean blackEntity(final Entity en) {
        if (en == null) {
            return false;
        }
        if (!AuraUtils.blackList.isEmpty()) {
            for (final EntityLivingBase en2 : AuraUtils.blackList) {
                if (en2 == null) {
                    continue;
                }
                if (en2.isEntityEqual(en)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean getDisableAura() {
        return AuraUtils.disableAura;
    }
    
    public static void setDisableAura(final boolean disableAura) {
        AuraUtils.disableAura = disableAura;
    }
    
    public static boolean isReachExploit() {
        return AuraUtils.reachExploit;
    }
    
    public static void setReachExploit(final boolean reachExploit) {
        AuraUtils.reachExploit = reachExploit;
    }
    
    public static double getPacketTPRange() {
        return AuraUtils.packetTPRange;
    }
    
    public static void setPacketTPRange(final double packetTPRange) {
        AuraUtils.packetTPRange = packetTPRange;
    }
    
    public static double getRange() {
        return AuraUtils.range;
    }
    
    public static void setRange(final double value) {
        AuraUtils.range = value;
    }
    
    public static boolean getHeadsnap() {
        return AuraUtils.headsnap;
    }
    
    public static int getAPS() {
        return AuraUtils.timercap;
    }
    
    public static void setTimer(final int set) {
        AuraUtils.timercap = set;
    }
    
    public static void setHeadSnap(final boolean selected) {
        AuraUtils.headsnap = selected;
    }
    
    public static double getChargeRange() {
        return AuraUtils.chargerange;
    }
    
    public static void setChargeRange(final double chargerange) {
        AuraUtils.chargerange = chargerange;
    }
    
    public static double[] getRotationToEntity(final Entity entity) {
        final double pX = AuraUtils.mc.thePlayer.posX;
        final double pY = AuraUtils.mc.thePlayer.posY + AuraUtils.mc.thePlayer.getEyeHeight();
        final double pZ = AuraUtils.mc.thePlayer.posZ;
        final double eX = entity.posX;
        final double eY = entity.posY + entity.height / 2.0f;
        final double eZ = entity.posZ;
        final double dX = pX - eX;
        final double dY = pY - eY;
        final double dZ = pZ - eZ;
        final double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        final double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        final double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new double[] { yaw, 90.0 - pitch };
    }
    
    public static boolean isOnTeam(final EntityLivingBase en) {
        if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("§")) {
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2 || en.getDisplayName().getUnformattedText().length() <= 2) {
                return false;
            }
            if (Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(en.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        }
        return false;
    }
    
    public static double angleDifference(final double a, final double b) {
        return ((a - b) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    static {
        AuraUtils.targets = new ArrayList<EntityLivingBase>();
        AuraUtils.blackList = new ArrayList<EntityLivingBase>();
        AuraUtils.mc = Minecraft.getMinecraft();
        AuraUtils.timercap = 15;
        AuraUtils.range = 7.0;
        AuraUtils.chargerange = 8.0;
        AuraUtils.packetTPRange = 10.0;
    }
}
