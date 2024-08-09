/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.server.ServerWorld;

public class EnderCrystalItem
extends Item {
    public EnderCrystalItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        double d;
        double d2;
        BlockPos blockPos;
        World world = itemUseContext.getWorld();
        BlockState blockState = world.getBlockState(blockPos = itemUseContext.getPos());
        if (!blockState.isIn(Blocks.OBSIDIAN) && !blockState.isIn(Blocks.BEDROCK)) {
            return ActionResultType.FAIL;
        }
        BlockPos blockPos2 = blockPos.up();
        if (!world.isAirBlock(blockPos2)) {
            return ActionResultType.FAIL;
        }
        double d3 = blockPos2.getX();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(d3, d2 = (double)blockPos2.getY(), d = (double)blockPos2.getZ(), d3 + 1.0, d2 + 2.0, d + 1.0));
        if (!list.isEmpty()) {
            return ActionResultType.FAIL;
        }
        if (world instanceof ServerWorld) {
            EnderCrystalEntity enderCrystalEntity = new EnderCrystalEntity(world, d3 + 0.5, d2, d + 0.5);
            enderCrystalEntity.setShowBottom(true);
            world.addEntity(enderCrystalEntity);
            DragonFightManager dragonFightManager = ((ServerWorld)world).func_241110_C_();
            if (dragonFightManager != null) {
                dragonFightManager.tryRespawnDragon();
            }
        }
        itemUseContext.getItem().shrink(1);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return false;
    }
}

