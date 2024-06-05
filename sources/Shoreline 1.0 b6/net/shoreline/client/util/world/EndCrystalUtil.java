package net.shoreline.client.util.world;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import net.shoreline.client.util.Globals;

/**
 * @author linus
 * @since 1.0
 */
public class EndCrystalUtil implements Globals {
    /**
     * @param entity
     * @param crystal
     * @return
     */
    public static double getDamageTo(final Entity entity,
                                     final Vec3d crystal) {
        return getDamageTo(entity, crystal, false);
    }

    /**
     * @param entity
     * @param crystal
     * @param ignoreTerrain
     * @return
     */
    public static double getDamageTo(final Entity entity,
                                     final Vec3d crystal,
                                     final boolean ignoreTerrain) {
        double ab = getExposure(crystal, entity, ignoreTerrain);
        double w = Math.sqrt(entity.squaredDistanceTo(crystal)) / 12.0;
        double ac = (1.0 - w) * ab;
        double dmg = (float) ((int) ((ac * ac + ac) / 2.0 * 7.0 * 12.0 + 1.0));
        dmg = getReduction(entity, mc.world.getDamageSources().explosion(null), dmg);
        return Math.max(0.0, dmg);
    }

    /**
     * @param pos
     * @param entity
     * @param crystal
     * @return
     */
    public static double getDamageToPos(final Vec3d pos,
                                        final Entity entity,
                                        final Vec3d crystal) {
        return getDamageToPos(pos, entity, crystal, false);
    }

    /**
     * @param pos           The actual position of the damage
     * @param entity
     * @param crystal
     * @param ignoreTerrain
     * @return
     */
    public static double getDamageToPos(final Vec3d pos,
                                        final Entity entity,
                                        final Vec3d crystal,
                                        final boolean ignoreTerrain) {
        final Box bb = entity.getBoundingBox();
        double dx = pos.getX() - bb.minX;
        double dy = pos.getY() - bb.minY;
        double dz = pos.getZ() - bb.minZ;
        final Box box = bb.offset(dx, dy, dz);
        //
        double ab = getExposure(crystal, entity, box, ignoreTerrain);
        double w = Math.sqrt(pos.squaredDistanceTo(crystal)) / 12.0;
        double ac = (1.0 - w) * ab;
        double dmg = (float) ((int) ((ac * ac + ac) / 2.0 * 7.0 * 12.0 + 1.0));
        dmg = getReduction(entity, mc.world.getDamageSources().explosion(null), dmg);
        return Math.max(0.0, dmg);
    }

    /**
     * @param entity
     * @param damage
     * @return
     */
    private static double getReduction(Entity entity, DamageSource damageSource, double damage) {
        if (damageSource.isScaledWithDifficulty()) {
            switch (mc.world.getDifficulty()) {
                // case PEACEFUL -> return 0;
                case EASY -> damage = Math.min(damage / 2 + 1, damage);
                case HARD -> damage *= 1.5f;
            }
        }
        if (entity instanceof LivingEntity livingEntity) {
            damage = DamageUtil.getDamageLeft((float) damage, getArmor(livingEntity), (float) getAttributeValue(livingEntity, EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
            damage = getProtectionReduction(entity, damage, damageSource);
        }
        return Math.max(damage, 0);
    }

    private static float getArmor(LivingEntity entity) {
        return (float) Math.floor(getAttributeValue(entity, EntityAttributes.GENERIC_ARMOR));
    }

    private static float getProtectionReduction(Entity player, double damage, DamageSource source) {
        int protLevel = EnchantmentHelper.getProtectionAmount(player.getArmorItems(), source);
        return DamageUtil.getInflictedDamage((float) damage, protLevel);
    }

    public static double getAttributeValue(LivingEntity entity, EntityAttribute attribute) {
        return getAttributeInstance(entity, attribute).getValue();
    }

    public static EntityAttributeInstance getAttributeInstance(LivingEntity entity, EntityAttribute attribute) {
        double baseValue = getDefaultForEntity(entity).getBaseValue(attribute);
        EntityAttributeInstance attributeInstance = new EntityAttributeInstance(attribute, o1 -> {
        });
        attributeInstance.setBaseValue(baseValue);
        for (var equipmentSlot : EquipmentSlot.values()) {
            ItemStack stack = entity.getEquippedStack(equipmentSlot);
            Multimap<EntityAttribute, EntityAttributeModifier> modifiers = stack.getAttributeModifiers(equipmentSlot);
            for (var modifier : modifiers.get(attribute)) attributeInstance.addTemporaryModifier(modifier);
        }
        return attributeInstance;
    }

    private static <T extends LivingEntity> DefaultAttributeContainer getDefaultForEntity(T entity) {
        return DefaultAttributeRegistry.get((EntityType<? extends LivingEntity>) entity.getType());
    }

    /**
     * @param source
     * @param entity
     * @param ignoreTerrain
     * @return
     */
    private static float getExposure(final Vec3d source,
                                     final Entity entity,
                                     final boolean ignoreTerrain) {
        final Box box = entity.getBoundingBox();
        return getExposure(source, entity, box, ignoreTerrain);
    }

    /**
     * @param source
     * @param entity
     * @param box
     * @param ignoreTerrain
     * @return
     */
    private static float getExposure(final Vec3d source,
                                     final Entity entity,
                                     final Box box,
                                     final boolean ignoreTerrain) {
        double d = 1.0 / ((box.maxX - box.minX) * 2.0 + 1.0);
        double e = 1.0 / ((box.maxY - box.minY) * 2.0 + 1.0);
        double f = 1.0 / ((box.maxZ - box.minZ) * 2.0 + 1.0);
        double g = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
        double h = (1.0 - Math.floor(1.0 / f) * f) / 2.0;
        if (d >= 0.0 && e >= 0.0 && f >= 0.0) {
            int i = 0;
            int j = 0;
            for (double k = 0.0; k <= 1.0; k += d) {
                for (double l = 0.0; l <= 1.0; l += e) {
                    for (double m = 0.0; m <= 1.0; m += f) {
                        double n = MathHelper.lerp(k, box.minX, box.maxX);
                        double o = MathHelper.lerp(l, box.minY, box.maxY);
                        double p = MathHelper.lerp(m, box.minZ, box.maxZ);
                        final Vec3d vec3d = new Vec3d(n + g, o, p + h);
                        RaycastContext ctx = new RaycastContext(vec3d, source,
                                RaycastContext.ShapeType.COLLIDER,
                                RaycastContext.FluidHandling.NONE, entity);
                        BlockHitResult result = ignoreTerrain ?
                                raycastIgnoreTerrain(ctx) : raycast(ctx);
                        if (result.getType() == HitResult.Type.MISS) {
                            ++i;
                        }
                        ++j;
                    }
                }
            }
            return (float) i / (float) j;
        } else {
            return 0.0f;
        }
    }

    /**
     * @param context
     * @return
     */
    private static BlockHitResult raycast(final RaycastContext context) {
        return BlockView.raycast(context.getStart(), context.getEnd(),
                context, (contextx, pos) ->
                {
                    BlockState blockState = mc.world.getBlockState(pos);
                    FluidState fluidState = mc.world.getFluidState(pos);
                    Vec3d vec3d = contextx.getStart();
                    Vec3d vec3d2 = contextx.getEnd();
                    VoxelShape voxelShape = contextx.getBlockShape(blockState,
                            mc.world, pos);
                    BlockHitResult blockHitResult = mc.world.raycastBlock(vec3d,
                            vec3d2, pos, voxelShape, blockState);
                    VoxelShape voxelShape2 = contextx.getFluidShape(fluidState,
                            mc.world, pos);
                    BlockHitResult blockHitResult2 = voxelShape2.raycast(vec3d,
                            vec3d2, pos);
                    double d = blockHitResult == null ? Double.MAX_VALUE :
                            contextx.getStart().squaredDistanceTo(blockHitResult.getPos());
                    double e = blockHitResult2 == null ? Double.MAX_VALUE :
                            contextx.getStart().squaredDistanceTo(blockHitResult2.getPos());
                    return d <= e ? blockHitResult : blockHitResult2;
                },
                (contextx) ->
                {
                    Vec3d vec3d = contextx.getStart().subtract(contextx.getEnd());
                    return BlockHitResult.createMissed(contextx.getEnd(),
                            Direction.getFacing(vec3d.x, vec3d.y, vec3d.z),
                            BlockPos.ofFloored(contextx.getEnd()));
                });
    }

    /**
     * @param context
     * @return
     */
    private static BlockHitResult raycastIgnoreTerrain(final RaycastContext context) {
        return BlockView.raycast(context.getStart(), context.getEnd(),
                context, (contextx, pos) ->
                {
                    BlockState blockState = mc.world.getBlockState(pos);
                    if (blockState.getBlock().getBlastResistance() < 600) {
                        blockState = Blocks.AIR.getDefaultState();
                    }
                    FluidState fluidState = mc.world.getFluidState(pos);
                    Vec3d vec3d = contextx.getStart();
                    Vec3d vec3d2 = contextx.getEnd();
                    VoxelShape voxelShape = contextx.getBlockShape(blockState,
                            mc.world, pos);
                    BlockHitResult blockHitResult = mc.world.raycastBlock(vec3d,
                            vec3d2, pos, voxelShape, blockState);
                    VoxelShape voxelShape2 = contextx.getFluidShape(fluidState,
                            mc.world, pos);
                    BlockHitResult blockHitResult2 = voxelShape2.raycast(vec3d,
                            vec3d2, pos);
                    double d = blockHitResult == null ? Double.MAX_VALUE :
                            contextx.getStart().squaredDistanceTo(blockHitResult.getPos());
                    double e = blockHitResult2 == null ? Double.MAX_VALUE :
                            contextx.getStart().squaredDistanceTo(blockHitResult2.getPos());
                    return d <= e ? blockHitResult : blockHitResult2;
                },
                (contextx) ->
                {
                    Vec3d vec3d = contextx.getStart().subtract(contextx.getEnd());
                    return BlockHitResult.createMissed(contextx.getEnd(),
                            Direction.getFacing(vec3d.x, vec3d.y, vec3d.z),
                            BlockPos.ofFloored(contextx.getEnd()));
                });
    }
}
