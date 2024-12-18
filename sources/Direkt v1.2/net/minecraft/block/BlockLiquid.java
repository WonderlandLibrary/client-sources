package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockLiquid extends Block {
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);

	protected BlockLiquid(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(0)));
		this.setTickRandomly(true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return this.blockMaterial != Material.LAVA;
	}

	/**
	 * Returns the percentage of the liquid block that is air, based on the given flow decay of the liquid
	 */
	public static float getLiquidHeightPercent(int meta) {
		if (meta >= 8) {
			meta = 0;
		}

		return (meta + 1) / 9.0F;
	}

	protected int func_189542_i(IBlockState p_189542_1_) {
		return p_189542_1_.getMaterial() == this.blockMaterial ? p_189542_1_.getValue(LEVEL).intValue() : -1;
	}

	protected int func_189545_x(IBlockState p_189545_1_) {
		int i = this.func_189542_i(p_189545_1_);
		return i >= 8 ? 0 : i;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
		return hitIfLiquid && (state.getValue(LEVEL).intValue() == 0);
	}

	/**
	 * Whether this Block is solid on the given Side
	 */
	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		Material material = worldIn.getBlockState(pos).getMaterial();
		return material == this.blockMaterial ? false : (side == EnumFacing.UP ? true : (material == Material.ICE ? false : super.isBlockSolid(worldIn, pos, side)));
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockAccess.getBlockState(pos.offset(side)).getMaterial() == this.blockMaterial ? false
				: (side == EnumFacing.UP ? true : super.shouldSideBeRendered(blockState, blockAccess, pos, side));
	}

	public boolean shouldRenderSides(IBlockAccess blockAccess, BlockPos pos) {
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				IBlockState iblockstate = blockAccess.getBlockState(pos.add(i, 0, j));

				if ((iblockstate.getMaterial() != this.blockMaterial) && !iblockstate.isFullBlock()) { return true; }
			}
		}

		return false;
	}

	/**
	 * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.LIQUID;
	}

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	protected Vec3d func_189543_a(IBlockAccess p_189543_1_, BlockPos p_189543_2_, IBlockState p_189543_3_) {
		double d0 = 0.0D;
		double d1 = 0.0D;
		double d2 = 0.0D;
		int i = this.func_189545_x(p_189543_3_);
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			blockpos$pooledmutableblockpos.func_189533_g(p_189543_2_).func_189536_c(enumfacing);
			int j = this.func_189545_x(p_189543_1_.getBlockState(blockpos$pooledmutableblockpos));

			if (j < 0) {
				if (!p_189543_1_.getBlockState(blockpos$pooledmutableblockpos).getMaterial().blocksMovement()) {
					j = this.func_189545_x(p_189543_1_.getBlockState(blockpos$pooledmutableblockpos.down()));

					if (j >= 0) {
						int k = j - (i - 8);
						d0 += enumfacing.getFrontOffsetX() * k;
						d1 += enumfacing.getFrontOffsetY() * k;
						d2 += enumfacing.getFrontOffsetZ() * k;
					}
				}
			} else if (j >= 0) {
				int l = j - i;
				d0 += enumfacing.getFrontOffsetX() * l;
				d1 += enumfacing.getFrontOffsetY() * l;
				d2 += enumfacing.getFrontOffsetZ() * l;
			}
		}

		Vec3d vec3d = new Vec3d(d0, d1, d2);

		if (p_189543_3_.getValue(LEVEL).intValue() >= 8) {
			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
				blockpos$pooledmutableblockpos.func_189533_g(p_189543_2_).func_189536_c(enumfacing1);

				if (this.isBlockSolid(p_189543_1_, blockpos$pooledmutableblockpos, enumfacing1) || this.isBlockSolid(p_189543_1_, blockpos$pooledmutableblockpos.up(), enumfacing1)) {
					vec3d = vec3d.normalize().addVector(0.0D, -6.0D, 0.0D);
					break;
				}
			}
		}

		blockpos$pooledmutableblockpos.release();
		return vec3d.normalize();
	}

	@Override
	public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
		return motion.add(this.func_189543_a(worldIn, pos, worldIn.getBlockState(pos)));
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World worldIn) {
		return this.blockMaterial == Material.WATER ? 5 : (this.blockMaterial == Material.LAVA ? (worldIn.provider.getHasNoSky() ? 10 : 30) : 0);
	}

	@Override
	public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
		int i = source.getCombinedLight(pos, 0);
		int j = source.getCombinedLight(pos.up(), 0);
		int k = i & 255;
		int l = j & 255;
		int i1 = (i >> 16) & 255;
		int j1 = (j >> 16) & 255;
		return (k > l ? k : l) | ((i1 > j1 ? i1 : j1) << 16);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return this.blockMaterial == Material.WATER ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID;
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double d0 = pos.getX();
		double d1 = pos.getY();
		double d2 = pos.getZ();

		if (this.blockMaterial == Material.WATER) {
			int i = stateIn.getValue(LEVEL).intValue();

			if ((i > 0) && (i < 8)) {
				if (rand.nextInt(64) == 0) {
					worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, (rand.nextFloat() * 0.25F) + 0.75F, rand.nextFloat() + 0.5F, false);
				}
			} else if (rand.nextInt(10) == 0) {
				worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + rand.nextFloat(), d1 + rand.nextFloat(), d2 + rand.nextFloat(), 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}

		if ((this.blockMaterial == Material.LAVA) && (worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR) && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
			if (rand.nextInt(100) == 0) {
				double d8 = d0 + rand.nextFloat();
				double d4 = d1 + stateIn.getBoundingBox(worldIn, pos).maxY;
				double d6 = d2 + rand.nextFloat();
				worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.playSound(d8, d4, d6, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + (rand.nextFloat() * 0.2F), 0.9F + (rand.nextFloat() * 0.15F), false);
			}

			if (rand.nextInt(200) == 0) {
				worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2F + (rand.nextFloat() * 0.2F), 0.9F + (rand.nextFloat() * 0.15F), false);
			}
		}

		if ((rand.nextInt(10) == 0) && worldIn.getBlockState(pos.down()).isFullyOpaque()) {
			Material material = worldIn.getBlockState(pos.down(2)).getMaterial();

			if (!material.blocksMovement() && !material.isLiquid()) {
				double d3 = d0 + rand.nextFloat();
				double d5 = d1 - 1.05D;
				double d7 = d2 + rand.nextFloat();

				if (this.blockMaterial == Material.WATER) {
					worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
				} else {
					worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
				}
			}
		}
	}

	public static float func_189544_a(IBlockAccess p_189544_0_, BlockPos p_189544_1_, Material p_189544_2_, IBlockState p_189544_3_) {
		Vec3d vec3d = getFlowingBlock(p_189544_2_).func_189543_a(p_189544_0_, p_189544_1_, p_189544_3_);
		return (vec3d.xCoord == 0.0D) && (vec3d.zCoord == 0.0D) ? -1000.0F : (float) MathHelper.atan2(vec3d.zCoord, vec3d.xCoord) - ((float) Math.PI / 2F);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.checkForMixing(worldIn, pos, state);
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		this.checkForMixing(p_189540_2_, p_189540_3_, p_189540_1_);
	}

	public boolean checkForMixing(World worldIn, BlockPos pos, IBlockState state) {
		if (this.blockMaterial == Material.LAVA) {
			boolean flag = false;

			for (EnumFacing enumfacing : EnumFacing.values()) {
				if ((enumfacing != EnumFacing.DOWN) && (worldIn.getBlockState(pos.offset(enumfacing)).getMaterial() == Material.WATER)) {
					flag = true;
					break;
				}
			}

			if (flag) {
				Integer integer = state.getValue(LEVEL);

				if (integer.intValue() == 0) {
					worldIn.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
					this.triggerMixEffects(worldIn, pos);
					return true;
				}

				if (integer.intValue() <= 4) {
					worldIn.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
					this.triggerMixEffects(worldIn, pos);
					return true;
				}
			}
		}

		return false;
	}

	protected void triggerMixEffects(World worldIn, BlockPos pos) {
		double d0 = pos.getX();
		double d1 = pos.getY();
		double d2 = pos.getZ();
		worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F));

		for (int i = 0; i < 8; ++i) {
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LEVEL).intValue();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { LEVEL });
	}

	public static BlockDynamicLiquid getFlowingBlock(Material materialIn) {
		if (materialIn == Material.WATER) {
			return Blocks.FLOWING_WATER;
		} else if (materialIn == Material.LAVA) {
			return Blocks.FLOWING_LAVA;
		} else {
			throw new IllegalArgumentException("Invalid material");
		}
	}

	public static BlockStaticLiquid getStaticBlock(Material materialIn) {
		if (materialIn == Material.WATER) {
			return Blocks.WATER;
		} else if (materialIn == Material.LAVA) {
			return Blocks.LAVA;
		} else {
			throw new IllegalArgumentException("Invalid material");
		}
	}
}
