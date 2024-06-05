package zamorozka.ui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Random;


public class Utils {

    private static final Random RANDOM = new Random();
    public static boolean lookChanged;
    public static float[] rotationsToBlock = null;
    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean screenCheck() {
        return !(mc.currentScreen instanceof GuiContainer)
                && !(mc.currentScreen instanceof GuiChat)
                && !(mc.currentScreen instanceof GuiScreen);
    }

    public static void assistFaceEntity(Entity entity, float yaw, float pitch) {
        if (entity == null) {
            return;
        }

        double diffX = entity.posX - Minecraft.player.posX;
        double diffZ = entity.posZ - Minecraft.player.posZ;
        double yDifference;

        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            yDifference = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (
                    Minecraft.player).posY + Minecraft.player.getEyeHeight();
        } else {
            yDifference = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D - (
                    Minecraft.player.posY + Minecraft.player.getEyeHeight());
        }

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float rotationYaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float rotationPitch = (float) -(Math.atan2(yDifference, dist) * 180.0D / Math.PI);

        if (yaw > 0) {
            Minecraft.player.rotationYaw = updateRotation(Minecraft.player.rotationYaw, rotationYaw, yaw / 4);
        }
        if (pitch > 0) {
            Minecraft.player.rotationPitch = updateRotation(Minecraft.player.rotationPitch, rotationPitch, pitch / 4);
        }
    }

    public static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }

    public static List<Entity> getEntityList() {
        return mc.world.getLoadedEntityList();
    }

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
        if (entity.width == width && entity.height == height) return;
        entity.width = width;
        entity.height = height;
        double d0 = (double) width / 2.0D;
        entity.setEntityBoundingBox(
                new AxisAlignedBB(
                        entity.posX - d0,
                        entity.posY,
                        entity.posZ - d0,
                        entity.posX + d0,
                        entity.posY + (double) entity.height,
                        entity.posZ + d0));

    }

}