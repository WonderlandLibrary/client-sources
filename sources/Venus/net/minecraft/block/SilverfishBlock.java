/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SilverfishBlock
extends Block {
    private final Block mimickedBlock;
    private static final Map<Block, Block> normalToInfectedMap = Maps.newIdentityHashMap();

    public SilverfishBlock(Block block, AbstractBlock.Properties properties) {
        super(properties);
        this.mimickedBlock = block;
        normalToInfectedMap.put(block, this);
    }

    public Block getMimickedBlock() {
        return this.mimickedBlock;
    }

    public static boolean canContainSilverfish(BlockState blockState) {
        return normalToInfectedMap.containsKey(blockState.getBlock());
    }

    private void spawnSilverFish(ServerWorld serverWorld, BlockPos blockPos) {
        SilverfishEntity silverfishEntity = EntityType.SILVERFISH.create(serverWorld);
        silverfishEntity.setLocationAndAngles((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, 0.0f, 0.0f);
        serverWorld.addEntity(silverfishEntity);
        silverfishEntity.spawnExplosionParticle();
    }

    @Override
    public void spawnAdditionalDrops(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
        super.spawnAdditionalDrops(blockState, serverWorld, blockPos, itemStack);
        if (serverWorld.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
            this.spawnSilverFish(serverWorld, blockPos);
        }
    }

    @Override
    public void onExplosionDestroy(World world, BlockPos blockPos, Explosion explosion) {
        if (world instanceof ServerWorld) {
            this.spawnSilverFish((ServerWorld)world, blockPos);
        }
    }

    public static BlockState infest(Block block) {
        return normalToInfectedMap.get(block).getDefaultState();
    }
}

