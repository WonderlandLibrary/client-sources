package in.momin5.cookieclient.api.util.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CATestUtils {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static final DamageSource EXPLOSION_SOURCE = (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
    private static List<BlockPos> placements = new ArrayList<>();

    public static float calculateDamageThreaded(double posX, double posY, double posZ, EntityPlayer playerInfo) {
        float finalDamage = 1.0f;
        try {
            float doubleExplosionSize = 12.0F;
            double distancedSize = playerInfo.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
            double blockDensity = playerInfo.world.getBlockDensity(new Vec3d(posX, posY, posZ), playerInfo.getEntityBoundingBox());
            double v = (1.0D - distancedSize) * blockDensity;
            float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));

            finalDamage = getBlastReductionThreaded(playerInfo, getDamageMultiplied(damage));
        } catch (NullPointerException ignored){
        }

        return finalDamage;
    }
    public static float getBlastReductionThreaded(EntityPlayer playerInfo, float damage) {
        damage = CombatRules.getDamageAfterAbsorb(damage, playerInfo.getTotalArmorValue(), (float) playerInfo.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

        float f = MathHelper.clamp(EnchantmentHelper.getEnchantmentModifierDamage(playerInfo.getArmorInventoryList(), EXPLOSION_SOURCE), 0.0F, 20.0F);
        damage *= 1.0F - f / 25.0F;

        if (playerInfo.isPotionActive(Potion.getPotionById(11))) {
            damage = damage - (damage / 4);
        }
        damage = Math.max(damage, 0.0F);
        return damage;
    }

    private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }

    public static List<BlockPos> findCrystalBlocks(float placeRange, boolean mode) {
        return getSphere(PlayerUtil.getPlayerPos(), placeRange, (int) placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystal(pos, mode)).collect(Collectors.toList());
    }

    public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleBlocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }
        return circleBlocks;
    }

    private static boolean canPlaceCrystal(BlockPos blockPos, boolean ignoreCrystal) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(boost, boost2);

        if (!(mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK
                || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN)) {
            return false;
        }

        if (!(mc.world.getBlockState(boost).getBlock() == Blocks.AIR)) {
            return false;
        }

        if (!(mc.world.getBlockState(boost2).getBlock() == Blocks.AIR)) {
            return false;
        }

        if (!ignoreCrystal)
            return mc.world.getEntitiesWithinAABB(Entity.class, axisAlignedBB).isEmpty();
        else {
            List<Entity> entityList = mc.world.getEntitiesWithinAABB(Entity.class, axisAlignedBB);
            entityList.removeIf(entity -> entity instanceof EntityEnderCrystal);
            return entityList.isEmpty();
        }

    }

    public static List<BlockPos> allPossiblePlacements(float placeRange, boolean one16Place) {
        placements = findCrystalBlocks(placeRange,one16Place);

        return placements;
    }
}
