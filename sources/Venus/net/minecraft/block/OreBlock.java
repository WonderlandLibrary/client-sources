/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

public class OreBlock
extends Block {
    public OreBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected int getExperience(Random random2) {
        if (this == Blocks.COAL_ORE) {
            return MathHelper.nextInt(random2, 0, 2);
        }
        if (this == Blocks.DIAMOND_ORE) {
            return MathHelper.nextInt(random2, 3, 7);
        }
        if (this == Blocks.EMERALD_ORE) {
            return MathHelper.nextInt(random2, 3, 7);
        }
        if (this == Blocks.LAPIS_ORE) {
            return MathHelper.nextInt(random2, 2, 5);
        }
        if (this == Blocks.NETHER_QUARTZ_ORE) {
            return MathHelper.nextInt(random2, 2, 5);
        }
        return this == Blocks.NETHER_GOLD_ORE ? MathHelper.nextInt(random2, 0, 1) : 0;
    }

    @Override
    public void spawnAdditionalDrops(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
        int n;
        super.spawnAdditionalDrops(blockState, serverWorld, blockPos, itemStack);
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0 && (n = this.getExperience(serverWorld.rand)) > 0) {
            this.dropXpOnBlockBreak(serverWorld, blockPos, n);
        }
    }
}

