/*
 * Decompiled with CFR 0.136.
 */
package gq.vapu.czfclient.Util;

import gq.vapu.czfclient.API.Events.World.EventMove;
import gq.vapu.czfclient.Manager.FriendManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.optifine.Reflector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wrapper {
    public static final byte HOTBAR_UP = 0;
    public static final byte HOTBAR_DOWN = 1;
    public static final Minecraft mc;
    private static final BlockUtils blockUtils;
    private static final RenderUtils.R3DUtils r3DUtils;

    static {
        mc = Minecraft.getMinecraft();
        blockUtils = new BlockUtils();
        r3DUtils = new RenderUtils.R3DUtils();
    }

    public static FontRenderer fr() {
        return Wrapper.mc.fontRendererObj;
    }

    public static int width() {
        return new ScaledResolution(mc).getScaledWidth();
    }

    public static int height() {
        return new ScaledResolution(mc).getScaledHeight();
    }

    public static Block block(BlockPos pos, double offset) {
        return mc.theWorld.getBlockState(pos.add(0.0, offset, 0.0)).getBlock();
    }

    public static void position(double x2, double y2, double z2, boolean grounded) {
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x2, y2, z2, grounded));
    }

    public static float pitch() {
        return mc.thePlayer.rotationPitch;
    }

    public static void pitch(float pitch) {
        mc.thePlayer.rotationPitch = pitch;
    }

    public static float yaw() {
        return mc.thePlayer.rotationYaw;
    }

    public static void yaw(float yaw) {
        mc.thePlayer.rotationYaw = yaw;
    }

    public static double x() {
        return mc.thePlayer.posX;
    }

    public static void x(double x2) {
        mc.thePlayer.posX = x2;
    }

    public static double y() {
        return mc.thePlayer.posY;
    }

    public static void y(double y2) {
        mc.thePlayer.posY = y2;
    }

    public static double z() {
        return mc.thePlayer.posZ;
    }

    public static void z(double z2) {
        mc.thePlayer.posZ = z2;
    }

    public static String withColors(String identifier, String input) {
        String output = input;
        int index = output.indexOf(identifier);
        while (output.indexOf(identifier) != -1) {
            output = output.replace(identifier, "\u00a7");
            index = output.indexOf(identifier);
        }
        return output;
    }

    public static void sendMessage(String message, boolean toServer) {
        if (toServer) {
            mc.thePlayer.sendChatMessage(message);
        } else {
            mc.thePlayer.addChatMessage(new ChatComponentText(String.format("%s%s", "Null: " + EnumChatFormatting.GRAY, message)));
        }
    }

    public static void msgPlayer(String msg) {
        mc.thePlayer.addChatMessage(new ChatComponentText(String.format("%s%s", "Null: " + EnumChatFormatting.GRAY, msg)));
    }

    public static int windowWidth() {
        return new ScaledResolution(mc).getScaledWidth();
    }

    public static int windowHeight() {
        return new ScaledResolution(mc).getScaledHeight();
    }

    public static void setMoveSpeed(EventMove eventMove, double d2) {
        throw new Error("Unresolved compilation problems: \n\tCannot make a static reference to the non-static method setX(double) from the type EventMove\n\tCannot make a static reference to the non-static method setZ(double) from the type EventMove\n\tCannot make a static reference to the non-static method setX(double) from the type EventMove\n\tCannot make a static reference to the non-static method setZ(double) from the type EventMove\n");
    }

    public static String capitalize(String line) {
        return String.valueOf(Character.toUpperCase(line.charAt(0))) + line.substring(1);
    }

    public static Entity getEntity(double distance) {
        if (Wrapper.getEntity(distance, 0.0, 0.0f) == null) {
            return null;
        }
        return (Entity) Wrapper.getEntity(distance, 0.0, 0.0f)[0];
    }

    public static Object[] getEntity(double distance, double expand, float partialTicks) {
        Entity var2 = mc.getRenderViewEntity();
        Entity entity = null;
        if (var2 == null || mc.theWorld == null) {
            return null;
        }
        Wrapper.mc.mcProfiler.startSection("pick");
        Vec3 var3 = var2.getPositionEyes(0.0f);
        Vec3 var4 = var2.getLook(0.0f);
        Vec3 var5 = var3.addVector(var4.xCoord * distance, var4.yCoord * distance, var4.zCoord * distance);
        Vec3 var6 = null;
        float var7 = 1.0f;
        List<Entity> var8 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var4.xCoord * distance, var4.yCoord * distance, var4.zCoord * distance).expand(1.0, 1.0, 1.0));
        double var9 = distance;
        int var10 = 0;
        while (var10 < var8.size()) {
            Entity var11 = var8.get(var10);
            if (var11.canBeCollidedWith()) {
                double var15;
                float var12 = var11.getCollisionBorderSize();
                AxisAlignedBB var13 = var11.getEntityBoundingBox().expand(var12, var12, var12);
                var13 = var13.expand(expand, expand, expand);
                MovingObjectPosition var14 = var13.calculateIntercept(var3, var5);
                if (var13.isVecInside(var3)) {
                    if (0.0 < var9 || var9 == 0.0) {
                        entity = var11;
                        var6 = var14 == null ? var3 : var14.hitVec;
                        var9 = 0.0;
                    }
                } else if (var14 != null && ((var15 = var3.distanceTo(var14.hitVec)) < var9 || var9 == 0.0)) {
                    boolean canRiderInteract = false;
                    if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                        canRiderInteract = Reflector.callBoolean(var11, Reflector.ForgeEntity_canRiderInteract);
                    }
                    if (var11 == var2.ridingEntity && !canRiderInteract) {
                        if (var9 == 0.0) {
                            entity = var11;
                            var6 = var14.hitVec;
                        }
                    } else {
                        entity = var11;
                        var6 = var14.hitVec;
                        var9 = var15;
                    }
                }
            }
            ++var10;
        }
        if (var9 < distance && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        Wrapper.mc.mcProfiler.endSection();
        if (entity == null || var6 == null) {
            return null;
        }
        return new Object[]{entity, var6};
    }

    public static BlockUtils blockUtils() {
        return blockUtils;
    }

    public static RenderUtils.R3DUtils get3DUtils() {
        return r3DUtils;
    }

    public static void sendPacketInQueue(Packet p2) {
        Minecraft.getMinecraft();
        mc.thePlayer.sendQueue.addToSendQueue(p2);
    }

    public static float getDistanceToEntity(Entity from, Entity to2) {
        return from.getDistanceToEntity(to2);
    }

    public static boolean isWithinFOV(Entity en2, double angle) {
        double angleDifference = Wrapper.angleDifference(mc.thePlayer.rotationYaw, Wrapper.getRotationToEntity(en2)[0]);
        return angleDifference > 0.0 && angleDifference < angle || -(angle *= 0.5) < angleDifference && angleDifference < 0.0;
    }

    public static double angleDifference(double a2, double b2) {
        return ((a2 - b2) % 360.0 + 540.0) % 360.0 - 180.0;
    }

    public static double[] getRotationToEntity(Entity entity) {
        double pX = mc.thePlayer.posX;
        double pY = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight();
        double pZ = mc.thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double) (entity.height / 2.0f);
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new double[]{yaw, 90.0 - pitch};
    }

    public static float[] faceTarget(Entity target, double p_70625_2_, double p_70625_3_, boolean miss) {
        double var6;
        double var4 = target.posX - mc.thePlayer.posX;
        double var8 = target.posZ - mc.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase) target;
            var6 = var10.posY + (double) var10.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        }
        Random rnd = new Random();
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float) ((-Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0.0), var14)) * 180.0 / 3.141592653589793);
        float pitch = Wrapper.changeRotation(mc.thePlayer.rotationPitch, var13, p_70625_3_);
        float yaw = Wrapper.changeRotation(mc.thePlayer.rotationYaw, var12, p_70625_2_);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(double p_70663_1_, double p_70663_2_, double p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float((float) (p_70663_2_ - p_70663_1_));
        if ((double) var4 > p_70663_3_) {
            var4 = (float) p_70663_3_;
        }
        if ((double) var4 < -p_70663_3_) {
            var4 = (float) (-p_70663_3_);
        }
        return (float) (p_70663_1_ + (double) var4);
    }

    public static Entity rayTrace(float f2, double d2) {
        throw new Error("Unresolved compilation problems: \n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe method func_174822_a(double, float) is undefined for the type Entity\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n");
    }

    public static Entity findClosestToCross(double range) {
        Entity e2 = null;
        double best = 360.0;
        Minecraft.getMinecraft();
        for (Object o2 : mc.theWorld.loadedEntityList) {
            Entity ent = (Entity) o2;
            if (!(ent instanceof EntityPlayer)) continue;
            Minecraft.getMinecraft();
            double diffX = ent.posX - mc.thePlayer.posX;
            Minecraft.getMinecraft();
            double diffZ = ent.posZ - mc.thePlayer.posZ;
            float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
            Minecraft.getMinecraft();
            double difference = Math.abs(Wrapper.angleDifference(newYaw, mc.thePlayer.rotationYaw));
            Minecraft.getMinecraft();
            if (ent == mc.thePlayer) continue;
            Minecraft.getMinecraft();
            if ((double) mc.thePlayer.getDistanceToEntity(ent) > range || !(ent instanceof EntityPlayer) || FriendManager.isFriend(ent.getName()) || difference >= best)
                continue;
            best = difference;
            e2 = ent;
        }
        return e2;
    }

    public static void updatePosition(double x2, double y2, double z2) {
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x2, y2, z2, mc.thePlayer.onGround));
    }

    public static int getStringWidth(String text, int increase) {
        return Wrapper.fr().getStringWidth(text);
    }

    public static int RGBtoHEX(int r2, int g2, int b2, int a2) {
        return (a2 << 24) + (r2 << 16) + (g2 << 8) + b2;
    }

    public static List<EntityLivingBase> loadedEntityList() {
        ArrayList<EntityLivingBase> loadedList = new ArrayList<EntityLivingBase>();
        loadedList.remove(mc.thePlayer);
        return loadedList;
    }

    public static void sendPacketNoEvents(Packet a2) {
        mc.getNetHandler().getNetworkManager().sendPacket(a2);
    }

    public static boolean isStorage(TileEntity entity) {
        return entity instanceof TileEntityChest || entity instanceof TileEntityBrewingStand || entity instanceof TileEntityDropper || entity instanceof TileEntityDispenser || entity instanceof TileEntityFurnace || entity instanceof TileEntityHopper || entity instanceof TileEntityEnderChest;
    }

    public static int toRGBAHex(float r2, float g2, float b2, float a2) {
        return ((int) (a2 * 255.0f) & 255) << 24 | ((int) (r2 * 255.0f) & 255) << 16 | ((int) (g2 * 255.0f) & 255) << 8 | (int) (b2 * 255.0f) & 255;
    }

    public static boolean isOnSameTeam(boolean teams, EntityLivingBase e2) {
        return teams && e2.isOnSameTeam(mc.thePlayer);
    }

    public static int changeAlpha(int color, int alpha) {
        return alpha << 24 | (color &= 16777215);
    }

    public static float[] getRotations(Entity target, Entity caster) {
        double x2 = target.posX - caster.posX;
        double y2 = target.posY + (double) target.getEyeHeight() / 1.3 - (caster.posY + (double) caster.getEyeHeight());
        double z2 = target.posZ - caster.posZ;
        double pos = Math.sqrt(x2 * x2 + z2 * z2);
        float yaw = (float) (Math.atan2(z2, x2) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) ((-Math.atan2(y2, pos)) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static void blink(double[] startPos, BlockPos endPos, double slack, double[] pOffset) {
        double curX = startPos[0];
        double curY = startPos[1];
        double curZ = startPos[2];
        try {
            double endX = (double) endPos.getX() + 0.5;
            double endY = (double) endPos.getY() + 1.0;
            double endZ = (double) endPos.getZ() + 0.5;
            double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            int count = 0;
            while (distance > slack) {
                distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
                if (count <= 120) {
                    boolean next = false;
                    double diffX = curX - endX;
                    double diffY = curY - endY;
                    double diffZ = curZ - endZ;
                    double offset = (count & 1) == 0 ? pOffset[0] : pOffset[1];
                    double d2 = offset;
                    if (diffX < 0.0) {
                        double d3 = curX = Math.abs(diffX) > offset ? (curX = curX + offset) : (curX = curX + Math.abs(diffX));
                    }
                    if (diffX > 0.0) {
                        double d4 = curX = Math.abs(diffX) > offset ? (curX = curX - offset) : (curX = curX - Math.abs(diffX));
                    }
                    if (diffY < 0.0) {
                        double d5 = curY = Math.abs(diffY) > 0.25 ? (curY = curY + 0.25) : (curY = curY + Math.abs(diffY));
                    }
                    if (diffY > 0.0) {
                        double d6 = curY = Math.abs(diffY) > 0.25 ? (curY = curY - 0.25) : (curY = curY - Math.abs(diffY));
                    }
                    if (diffZ < 0.0) {
                        double d7 = curZ = Math.abs(diffZ) > offset ? (curZ = curZ + offset) : (curZ = curZ + Math.abs(diffZ));
                    }
                    if (diffZ > 0.0) {
                        curZ = Math.abs(diffZ) > offset ? (curZ = curZ - offset) : (curZ = curZ - Math.abs(diffZ));
                    }
                    Minecraft.getMinecraft();
                    Minecraft.getMinecraft();
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(curX, curY, curZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
                    ++count;
                    continue;
                }
                break;
            }
        } catch (Exception endX) {
            // empty catch block
        }
    }
}

