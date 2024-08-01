package wtf.diablo.client.util.mc.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.impl.combat.killaura.KillAuraModule;

public final class EntityUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private EntityUtil() {}

    public static float getPitchChangeToEntityHitPoint(Entity entity, KillAuraModule.EnumHitPoint hitpoint) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double offset = 1.6;
        switch (hitpoint) { //leaked diablo feature (crazy)
            case HEAD:
                break;
            case CHEST:
                offset = 2;
                break;
            case LOWER_CHEST:
                offset = 2.275;
                break;
            case STOMACH:
                offset = 2.475;
                break;
            case COCK:
                offset = 2.655;
                break;
            case LEGS:
                offset = 2.825;
                break;
            case FEET:
                offset = 3.1;
                break;
            case TOES:
                offset = 3.225;
                break;

        }
        double deltaY = entity.posY - offset + entity.getEyeHeight() - mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return Double.isNaN(mc.thePlayer.rotationPitch - pitchToEntity) ? 0.0f : -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) pitchToEntity);
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity;
        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + v;
        } else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + v;
        } else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return Double.isNaN(mc.thePlayer.rotationYaw - yawToEntity) ? 0.0f : MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) yawToEntity));
    }

    public static float[] getAnglesFromHitPoint(Entity e, KillAuraModule.EnumHitPoint hitpoint) {
        return new float[]{getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntityHitPoint(e, hitpoint) + mc.thePlayer.rotationPitch};
    }

    public static void rotate(final MotionEvent update, final float yaw, final float pitch) {
        update.setYaw(yaw);
        update.setPitch(pitch);
        mc.thePlayer.rotationYawHead = yaw;
        mc.thePlayer.rotationPitchHead = pitch;
        mc.thePlayer.renderYawOffset = yaw;
    }

    public static float getYaw(Vec3 to) {
        float x = (float) (to.xCoord - mc.thePlayer.posX);
        float z = (float) (to.zCoord - mc.thePlayer.posZ);
        float var1 = (float) (StrictMath.atan2(z, x) * 180.0D / StrictMath.PI) - 90.0F;
        float rotationYaw =mc.thePlayer.rotationYaw;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }

    /** gets best slot for best sword from weed client */
    public static int getSwordSlot() {
        if (mc.thePlayer == null) {
            return -1;
        }

        int bestSword = -1;
        float bestDamage = 1F;

        for (int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (item != null) {
                    if (item.getItem() instanceof ItemSword) {
                        ItemSword is = (ItemSword) item.getItem();
                        float damage = is.getDamageVsEntity();
                        damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, item) * 1.26F +
                                EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, item) * 0.01f;
                        if (damage > bestDamage) {
                            bestDamage = damage;
                            bestSword = i;
                        }
                    }
                }
            }
        }
        return bestSword;
    }

    /** Compares sword X with sword already in player inventory */
    public static boolean isBestSword(ItemStack comparingItem) {
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack is = mc.thePlayer.inventory.mainInventory[k];
            if (is != null && comparingItem != is && is.getItem() instanceof ItemSword) {
                ItemSword item = (ItemSword) is.getItem();
                ItemSword compare = (ItemSword) comparingItem.getItem();

                float damage = item.getDamageVsEntity();
                damage += (EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, is)
                        + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, is));

                float damage2 = compare.getDamageVsEntity();
                damage2 += (EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, comparingItem)
                        + EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, comparingItem));

                if (item == compare) {
                    if (damage2 <= damage) return false;
                }
            }
        }
        return true;
    }

    public static boolean getBestProtection(final ItemStack comparingItem, final ItemStack itemStack) {
        double regularItemProtection = 0, comparingItemProtection = 0;
        if (itemStack != null) {
            if (itemStack.getItem() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor) itemStack.getItem();
                regularItemProtection = armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, comparingItem)) * 0.0075;
            }
        }

        if (comparingItem != null) {
            if (comparingItem.getItem() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor) comparingItem.getItem();
                comparingItemProtection = armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, comparingItem)) * 0.0075;
            }
        }

        if (comparingItem != null && itemStack != null) {
            return comparingItemProtection >= regularItemProtection;
        }

        return false;
    }

    /** Compares armor X with armor already in player inventory */
    public static boolean isBestArmor(final ItemStack comparingItem) {
        for (int i = 0; i < mc.thePlayer.inventory.mainInventory.length; i++) {
            final ItemStack is = mc.thePlayer.inventory.mainInventory[i];

            if (is == null) {
                return false;
            }

            if (comparingItem != is && is.getItem() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor) is.getItem();
                final ItemArmor comparedItem = (ItemArmor) comparingItem.getItem();

                final double regularItemProtection = armor.damageReduceAmount + ((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, is)) * 0.0075;
                final double comparingItemProtection = comparedItem.damageReduceAmount + ((100 - comparedItem.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, comparingItem)) * 0.0075;

                if (armor.armorType == comparedItem.armorType) {
                    if (comparingItemProtection <= regularItemProtection) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
