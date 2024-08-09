/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

public class FrostWalkerEnchantment
extends Enchantment {
    public FrostWalkerEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.ARMOR_FEET, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return n * 10;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + 15;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public static void freezeNearby(LivingEntity livingEntity, World world, BlockPos blockPos, int n) {
        if (livingEntity.isOnGround()) {
            BlockState blockState = Blocks.FROSTED_ICE.getDefaultState();
            float f = Math.min(16, 2 + n);
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-f, -1.0, -f), blockPos.add(f, -1.0, f))) {
                BlockState blockState2;
                if (!blockPos2.withinDistance(livingEntity.getPositionVec(), (double)f)) continue;
                mutable.setPos(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
                BlockState blockState3 = world.getBlockState(mutable);
                if (!blockState3.isAir() || (blockState2 = world.getBlockState(blockPos2)).getMaterial() != Material.WATER || blockState2.get(FlowingFluidBlock.LEVEL) != 0 || !blockState.isValidPosition(world, blockPos2) || !world.placedBlockCollides(blockState, blockPos2, ISelectionContext.dummy())) continue;
                world.setBlockState(blockPos2, blockState);
                world.getPendingBlockTicks().scheduleTick(blockPos2, Blocks.FROSTED_ICE, MathHelper.nextInt(livingEntity.getRNG(), 60, 120));
            }
        }
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return super.canApplyTogether(enchantment) && enchantment != Enchantments.DEPTH_STRIDER;
    }
}

