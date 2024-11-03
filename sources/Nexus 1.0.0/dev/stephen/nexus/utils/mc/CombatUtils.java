package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.modules.combat.InfiniteAura;
import dev.stephen.nexus.module.modules.combat.KillAura;
import dev.stephen.nexus.utils.Utils;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.regex.Pattern;

public class CombatUtils implements Utils {

    public static boolean isInCombat() {
        return (Client.INSTANCE.getModuleManager().getModule(KillAura.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(KillAura.class).target != null) || (Client.INSTANCE.getModuleManager().getModule(InfiniteAura.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(InfiniteAura.class).target != null);
    }

    public static double getDistanceToEntity(Entity entity) {
        return getClosestVec(entity, 1, 1, 0, 0, 0).distanceTo(getEyeVec());
    }

    public static Vec3d getEyeVec() {
        return mc.player.getPos().add(new Vec3d(0, mc.player.getEyeHeight(mc.player.getPose()), 0));
    }

    public static boolean isSameTeam(LivingEntity entity) {
        if (mc.player.isTeammate(entity)) {
            return true;
        }

        Text clientDisplayName = mc.player.getDisplayName();
        Text targetDisplayName = entity.getDisplayName();

        if (clientDisplayName == null || targetDisplayName == null) {
            return false;
        }

        return checkName(clientDisplayName, targetDisplayName) || checkPrefix(targetDisplayName, clientDisplayName) || checkArmor(entity);
    }

    private static boolean checkName(Text clientDisplayName, Text targetDisplayName) {
        Style targetStyle = targetDisplayName.getStyle();
        Style clientStyle = clientDisplayName.getStyle();

        return targetStyle.getColor() != null &&
                clientStyle.getColor() != null &&
                targetStyle.getColor().equals(clientStyle.getColor());
    }

    private static boolean checkPrefix(Text targetDisplayName, Text clientDisplayName) {
        String targetName = stripMinecraftColorCodes(targetDisplayName.getString());
        String clientName = stripMinecraftColorCodes(clientDisplayName.getString());
        String[] targetSplit = targetName.split(" ");
        String[] clientSplit = clientName.split(" ");

        return targetSplit.length > 1 && clientSplit.length > 1 && targetSplit[0].equals(clientSplit[0]);
    }

    private static boolean checkArmor(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }

        PlayerEntity playerEntity = (PlayerEntity) entity;
        return (matchesArmorColor(playerEntity, 3)) || (matchesArmorColor(playerEntity, 2));
    }

    private static boolean matchesArmorColor(PlayerEntity player, int armorSlot) {
        var ownStack = mc.player.getInventory().getArmorStack(armorSlot);
        var otherStack = player.getInventory().getArmorStack(armorSlot);

        Integer ownColor = getArmorColor(ownStack);
        Integer otherColor = getArmorColor(otherStack);

        return ownColor != null && ownColor.equals(otherColor);
    }

    private static Integer getArmorColor(ItemStack otherStack) {
        if (otherStack.isIn(ItemTags.DYEABLE)) {
            return DyedColorComponent.getColor(otherStack, -6265536);
        } else {
            return null;
        }
    }

    private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");

    public static String stripMinecraftColorCodes(String input) {
        return COLOR_PATTERN.matcher(input).replaceAll("");
    }

    private static Vec3d getEntityHitBoxSize(Entity ent) {
        Box box = ent.getBoundingBox();
        double hbX = box.maxX - box.minX;
        double hbY = box.maxY - box.minY;
        double hbZ = box.maxZ - box.minZ;
        return new Vec3d(hbX, hbY, hbZ);
    }

    public static Box getScaledEntityHitBox(Entity ent, double horizontalMulti, double verticalMulti) {
        Vec3d hb = getEntityHitBoxSize(ent);
        double xPart = ((1 - horizontalMulti) / 2) * hb.x;
        double yPart = ((1 - verticalMulti) / 2) * hb.y;
        double zPart = ((1 - horizontalMulti) / 2) * hb.z;

        Box box = ent.getBoundingBox();
        Vec3d s = new Vec3d(box.minX, box.minY, box.minZ);
        return new Box(s.x + xPart, s.y + yPart, s.z + zPart, s.x + hb.x - xPart, s.y + hb.y + yPart, s.z + hb.z + zPart);
    }

    public static Vec3d getClosestVec(Entity target, double hitBoxMultiHorizontal, double hitBoxMultiVertical, double xOffset, double yOffset, double zOffset) {
        Box hb = getScaledEntityHitBox(target, hitBoxMultiHorizontal, hitBoxMultiVertical);
        Vec3d start = new Vec3d(hb.minX, hb.minY, hb.minZ);
        double hbX = hb.maxX - hb.minX;
        double hbY = hb.maxY - hb.minY;
        double hbZ = hb.maxZ - hb.minZ;
        Vec3d s = new Vec3d(hbX, hbY, hbZ);

        Vec3d eP = getEyeVec();

        Vec3d closest = null;
        double closestDist = Double.MAX_VALUE;
        double stepSize = 0.1d;
        for (double dX = 0; dX <= s.x; dX += stepSize) {
            for (double dY = 0; dY <= s.y; dY += stepSize) {
                for (double dZ = 0; dZ <= s.z; dZ += stepSize) {
                    Vec3d point = start.add(dX, dY, dZ);
                    Vec3d offsetPoint = point.add(xOffset, yOffset, zOffset);
                    if (!hb.contains(offsetPoint)) continue;
                    double d = point.squaredDistanceTo(eP);
                    if (d < closestDist) {
                        closestDist = d;
                        closest = point;
                    }
                }
            }
        }

        return closest != null ? closest.add(xOffset, yOffset, zOffset) : null;
    }

    public static double getDistanceToPos(Vec3d pos) {
        return pos.distanceTo(getEyeVec());
    }

    public static double fovFromEntity(Entity en) {
        return ((((double) (mc.player.getYaw() - fovToEntity(en)) % 360.0D) + 540.0D) % 360.0D) - 180.0D;
    }

    public static float fovToEntity(Entity ent) {
        double x = ent.getX() - mc.player.getX();
        double z = ent.getZ() - mc.player.getZ();
        double yaw = Math.atan2(x, z) * 57.2957795D;
        return (float) (yaw * -1.0D);
    }
}