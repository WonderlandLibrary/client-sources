package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DamageUtil implements MCUtil {
    public static boolean isArmorLow(final EntityPlayer player, final int durability) {
        for (final ItemStack piece : player.inventory.armorInventory) {
            if (piece == null) {
                return true;
            }
            if (getItemDamage(piece) < durability) {
                return true;
            }
        }
        return false;
    }

    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float) stack.getMaxDamage() * 100.0f;
    }

    public static int getRoundedDamage(final ItemStack stack) {
        return (int) getDamageInPercent(stack);
    }

    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }

    public static boolean canBreakWeakness(final EntityPlayer player) {
        int strengthAmp = 0;
        final PotionEffect effect = Minecraft.player.getActivePotionEffect(MobEffects.STRENGTH);
        if (effect != null) {
            strengthAmp = effect.getAmplifier();
        }
        return !Minecraft.player.isPotionActive(MobEffects.WEAKNESS) || strengthAmp >= 1 || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSword || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemAxe || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSpade;
    }

    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        } catch (Exception ex) {
        }
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float) (int) ((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(DamageUtil.mc.world, null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float) finald;
    }

    public static float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer) entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            } catch (Exception ex) {
            }
            final float f = MathHelper.clamp((float) k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    public static float getDamageMultiplied(final float damage) {
        final int diff = DamageUtil.mc.world.getDifficulty().getDifficultyId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }

    public static float calculateDamageAlt(final Entity crystal, final Entity entity) {
        final BlockPos cPos = new BlockPos(crystal.posX, crystal.posY, crystal.posZ);
        return calculateDamage(cPos.getX(), cPos.getY(), cPos.getZ(), entity);
    }

    public static float calculateDamage(final Entity crystal, final Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    public static float calculateDamage(final BlockPos pos, final Entity entity) {
        return calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, entity);
    }

    public static boolean canTakeDamage(final boolean suicide) {
        return !Minecraft.player.capabilities.isCreativeMode && !suicide;
    }

    public static double[] teleportToPosition(double[] startPosition, double[] endPosition, double setOffset, double slack, boolean extendOffset, boolean onGround) {
        boolean wasSneaking = false;

        if (Minecraft.player.isSneaking())
            wasSneaking = true;

        double startX = startPosition[0];
        double startY = startPosition[1];
        double startZ = startPosition[2];

        double endX = endPosition[0];
        double endY = endPosition[1];
        double endZ = endPosition[2];

        double distance = Math.abs(startX - startY) + Math.abs(startY - endY) + Math.abs(startZ - endZ);

        int count = 0;
        while (distance > slack) {
            distance = Math.abs(startX - endX) + Math.abs(startY - endY) + Math.abs(startZ - endZ);

            if (count > 120) {
                break;
            }

            double offset = extendOffset && (count & 0x1) == 0 ? setOffset + 0.15D : setOffset;

            double diffX = startX - endX;
            double diffY = startY - endY;
            double diffZ = startZ - endZ;

            if (diffX < 0.0D) {
                if (Math.abs(diffX) > offset) {
                    startX += offset;
                } else {
                    startX += Math.abs(diffX);
                }
            }
            if (diffX > 0.0D) {
                if (Math.abs(diffX) > offset) {
                    startX -= offset;
                } else {
                    startX -= Math.abs(diffX);
                }
            }
            if (diffY < 0.0D) {
                if (Math.abs(diffY) > offset) {
                    startY += offset;
                } else {
                    startY += Math.abs(diffY);
                }
            }
            if (diffY > 0.0D) {
                if (Math.abs(diffY) > offset) {
                    startY -= offset;
                } else {
                    startY -= Math.abs(diffY);
                }
            }
            if (diffZ < 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    startZ += offset;
                } else {
                    startZ += Math.abs(diffZ);
                }
            }
            if (diffZ > 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    startZ -= offset;
                } else {
                    startZ -= Math.abs(diffZ);
                }
            }

            if (wasSneaking) {
                Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            Minecraft.getConnection().getNetworkManager().sendPacket(new CPacketPlayer.Position(startX, startY, startZ, onGround));
            count++;
        }

        if (wasSneaking) {
            Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
        }

        return new double[]{startX, startY, startZ};
    }

    public static int getCooldownByWeapon(final EntityPlayer player) {
        final Item item = player.getHeldItemMainhand().getItem();
        if (item instanceof ItemSword) {
            return 600;
        }
        if (item instanceof ItemPickaxe) {
            return 850;
        }
        if (item == Items.IRON_AXE) {
            return 1100;
        }
        if (item == Items.STONE_HOE) {
            return 500;
        }
        if (item == Items.IRON_HOE) {
            return 350;
        }
        if (item == Items.WOODEN_AXE || item == Items.STONE_AXE) {
            return 1250;
        }
        if (item instanceof ItemSpade || item == Items.GOLDEN_AXE || item == Items.DIAMOND_AXE || item == Items.WOODEN_HOE || item == Items.GOLDEN_HOE) {
            return 1000;
        }
        return 250;
    }
}