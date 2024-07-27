package dev.nexus.utils.game;

import dev.nexus.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class CombatUtils implements Utils {
    public static boolean isSameTeam(Entity e) {
        try {
            EntityPlayer entityPlayer = (EntityPlayer) e;
            if (mc.thePlayer.isOnSameTeam((EntityLivingBase) e) || mc.thePlayer.getDisplayName().getUnformattedText().startsWith(entityPlayer.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        } catch (Exception ignored) {

        }
        return false;

    }

    public static double getSqrtDistanceToEntityBox(Entity e) {
        Vec3 eyes = mc.thePlayer.getPositionEyes(1f);
        Vec3 pos = getHitVec3(e);
        double xDist = Math.abs(pos.xCoord - eyes.xCoord);
        double yDist = Math.abs(pos.yCoord - eyes.yCoord);
        double zDist = Math.abs(pos.zCoord - eyes.zCoord);
        return Math.pow(xDist, 2) + Math.pow(yDist, 2) + Math.pow(zDist, 2);
    }

    public static Vec3 getHitVec3(Entity entity) {
        Vec3 eyesPosition = mc.thePlayer.getPositionEyes(1.0f);

        float size = entity.getCollisionBorderSize();

        AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(size, size, size);

        double x = MathHelper.clamp_double(eyesPosition.xCoord, entityBoundingBox.minX, entityBoundingBox.maxX);
        double y = MathHelper.clamp_double(eyesPosition.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);
        double z = MathHelper.clamp_double(eyesPosition.zCoord, entityBoundingBox.minZ, entityBoundingBox.maxZ);

        return new Vec3(x, y - 0.4, z);
    }

    public static boolean isHoldingSword() {
        return mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }

    public static EntityPlayer getClosestPlayerWithin(double distance, boolean attackteam) {
        EntityPlayer target = null;
        for (EntityPlayer entity : mc.theWorld.playerEntities) {
            float tempDistance = mc.thePlayer.getDistanceToEntity(entity);
            if (entity != mc.thePlayer && tempDistance <= distance) {
                if (entity.isInvisible()) {
                    continue;
                }
                if (attackteam) {
                    target = entity;
                    distance = tempDistance;
                } else if (!isSameTeam(entity)) {
                    target = entity;
                    distance = tempDistance;
                }
            }
        }
        return target;
    }

    public static List<EntityPlayer> getPlayersWithin(double distance, boolean attackteam) {
        List<EntityPlayer> targets = new ArrayList<>();
        for (EntityPlayer entity : mc.theWorld.playerEntities) {
            if (!attackteam) {
                if (isSameTeam(entity)) {
                    continue;
                }
            }
            double tempDistance = mc.thePlayer.getDistanceToEntity(entity);
            if (entity != mc.thePlayer && tempDistance <= distance) {
                targets.add(entity);
            }
        }
        return targets;
    }
}
