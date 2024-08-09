/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LeadItem
extends Item {
    public LeadItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        BlockPos blockPos;
        World world = itemUseContext.getWorld();
        Block block = world.getBlockState(blockPos = itemUseContext.getPos()).getBlock();
        if (block.isIn(BlockTags.FENCES)) {
            PlayerEntity playerEntity = itemUseContext.getPlayer();
            if (!world.isRemote && playerEntity != null) {
                LeadItem.bindPlayerMobs(playerEntity, world, blockPos);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }

    public static ActionResultType bindPlayerMobs(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        LeashKnotEntity leashKnotEntity = null;
        boolean bl = false;
        double d = 7.0;
        int n = blockPos.getX();
        int n2 = blockPos.getY();
        int n3 = blockPos.getZ();
        for (MobEntity mobEntity : world.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB((double)n - 7.0, (double)n2 - 7.0, (double)n3 - 7.0, (double)n + 7.0, (double)n2 + 7.0, (double)n3 + 7.0))) {
            if (mobEntity.getLeashHolder() != playerEntity) continue;
            if (leashKnotEntity == null) {
                leashKnotEntity = LeashKnotEntity.create(world, blockPos);
            }
            mobEntity.setLeashHolder(leashKnotEntity, false);
            bl = true;
        }
        return bl ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }
}

