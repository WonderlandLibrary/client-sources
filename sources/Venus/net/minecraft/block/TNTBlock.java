/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TNTBlock
extends Block {
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

    public TNTBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)this.getDefaultState().with(UNSTABLE, false));
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock()) && world.isBlockPowered(blockPos)) {
            TNTBlock.explode(world, blockPos);
            world.removeBlock(blockPos, true);
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (world.isBlockPowered(blockPos)) {
            TNTBlock.explode(world, blockPos);
            world.removeBlock(blockPos, true);
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (!world.isRemote() && !playerEntity.isCreative() && blockState.get(UNSTABLE).booleanValue()) {
            TNTBlock.explode(world, blockPos);
        }
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    @Override
    public void onExplosionDestroy(World world, BlockPos blockPos, Explosion explosion) {
        if (!world.isRemote) {
            TNTEntity tNTEntity = new TNTEntity(world, (double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, explosion.getExplosivePlacedBy());
            tNTEntity.setFuse((short)(world.rand.nextInt(tNTEntity.getFuse() / 4) + tNTEntity.getFuse() / 8));
            world.addEntity(tNTEntity);
        }
    }

    public static void explode(World world, BlockPos blockPos) {
        TNTBlock.explode(world, blockPos, null);
    }

    private static void explode(World world, BlockPos blockPos, @Nullable LivingEntity livingEntity) {
        if (!world.isRemote) {
            TNTEntity tNTEntity = new TNTEntity(world, (double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, livingEntity);
            world.addEntity(tNTEntity);
            world.playSound(null, tNTEntity.getPosX(), tNTEntity.getPosY(), tNTEntity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.onBlockActivated(blockState, world, blockPos, playerEntity, hand, blockRayTraceResult);
        }
        TNTBlock.explode(world, blockPos, playerEntity);
        world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 0);
        if (!playerEntity.isCreative()) {
            if (item == Items.FLINT_AND_STEEL) {
                itemStack.damageItem(1, playerEntity, arg_0 -> TNTBlock.lambda$onBlockActivated$0(hand, arg_0));
            } else {
                itemStack.shrink(1);
            }
        }
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public void onProjectileCollision(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
        if (!world.isRemote) {
            Entity entity2 = projectileEntity.func_234616_v_();
            if (projectileEntity.isBurning()) {
                BlockPos blockPos = blockRayTraceResult.getPos();
                TNTBlock.explode(world, blockPos, entity2 instanceof LivingEntity ? (LivingEntity)entity2 : null);
                world.removeBlock(blockPos, true);
            }
        }
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosion) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UNSTABLE);
    }

    private static void lambda$onBlockActivated$0(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }
}

