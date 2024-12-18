package net.minecraft.block;

import java.util.Random;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockMagma extends Block {
	public BlockMagma() {
		super(Material.ROCK);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setLightLevel(0.2F);
		this.setTickRandomly(true);
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state) {
		return MapColor.NETHERRACK;
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block)
	 */
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.isImmuneToFire() && (entityIn instanceof EntityLivingBase) && !EnchantmentHelper.func_189869_j((EntityLivingBase) entityIn)) {
			entityIn.attackEntityFrom(DamageSource.field_190095_e, 1.0F);
		}

		super.onEntityWalk(worldIn, pos, entityIn);
	}

	@Override
	public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
		return 15728880;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		BlockPos blockpos = pos.up();
		IBlockState iblockstate = worldIn.getBlockState(blockpos);

		if ((iblockstate.getBlock() == Blocks.WATER) || (iblockstate.getBlock() == Blocks.FLOWING_WATER)) {
			worldIn.setBlockToAir(blockpos);
			worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F));

			if (worldIn instanceof WorldServer) {
				((WorldServer) worldIn).spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockpos.getX() + 0.5D, blockpos.getY() + 0.25D, blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D, new int[0]);
			}
		}
	}

	@Override
	public boolean func_189872_a(IBlockState p_189872_1_, Entity p_189872_2_) {
		return p_189872_2_.isImmuneToFire();
	}
}
