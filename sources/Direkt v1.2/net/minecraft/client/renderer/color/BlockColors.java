package net.minecraft.client.renderer.color;

import javax.annotation.Nullable;

import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockColors {
	private final ObjectIntIdentityMap<IBlockColor> mapBlockColors = new ObjectIntIdentityMap(32);

	public static BlockColors init() {
		final BlockColors blockcolors = new BlockColors();
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = state.getValue(BlockDoublePlant.VARIANT);
				return (worldIn == null) || (pos == null)
						|| ((blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS) && (blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN)) ? -1
								: BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
			}
		}, new Block[] { Blocks.DOUBLE_PLANT });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				if ((worldIn != null) && (pos != null)) {
					TileEntity tileentity = worldIn.getTileEntity(pos);

					if (tileentity instanceof TileEntityFlowerPot) {
						Item item = ((TileEntityFlowerPot) tileentity).getFlowerPotItem();

						if (item instanceof ItemBlock) {
							IBlockState iblockstate = Block.getBlockFromItem(item).getDefaultState();
							return blockcolors.colorMultiplier(iblockstate, worldIn, pos, tintIndex);
						}
					}

					return -1;
				} else {
					return -1;
				}
			}
		}, new Block[] { Blocks.FLOWER_POT });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return (worldIn != null) && (pos != null) ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : ColorizerGrass.getGrassColor(0.5D, 1.0D);
			}
		}, new Block[] { Blocks.GRASS });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				BlockPlanks.EnumType blockplanks$enumtype = state.getValue(BlockOldLeaf.VARIANT);
				return blockplanks$enumtype == BlockPlanks.EnumType.SPRUCE ? ColorizerFoliage.getFoliageColorPine()
						: (blockplanks$enumtype == BlockPlanks.EnumType.BIRCH ? ColorizerFoliage.getFoliageColorBirch()
								: ((worldIn != null) && (pos != null) ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic()));
			}
		}, new Block[] { Blocks.LEAVES });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return (worldIn != null) && (pos != null) ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
			}
		}, new Block[] { Blocks.LEAVES2 });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return (worldIn != null) && (pos != null) ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : -1;
			}
		}, new Block[] { Blocks.WATER, Blocks.FLOWING_WATER });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return BlockRedstoneWire.colorMultiplier(state.getValue(BlockRedstoneWire.POWER).intValue());
			}
		}, new Block[] { Blocks.REDSTONE_WIRE });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return (worldIn != null) && (pos != null) ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : -1;
			}
		}, new Block[] { Blocks.REEDS });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				int i = state.getValue(BlockStem.AGE).intValue();
				int j = i * 32;
				int k = 255 - (i * 8);
				int l = i * 4;
				return (j << 16) | (k << 8) | l;
			}
		}, new Block[] { Blocks.MELON_STEM, Blocks.PUMPKIN_STEM });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return (worldIn != null) && (pos != null) ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos)
						: (state.getValue(BlockTallGrass.TYPE) == BlockTallGrass.EnumType.DEAD_BUSH ? 16777215 : ColorizerGrass.getGrassColor(0.5D, 1.0D));
			}
		}, new Block[] { Blocks.TALLGRASS });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return (worldIn != null) && (pos != null) ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
			}
		}, new Block[] { Blocks.VINE });
		blockcolors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return (worldIn != null) && (pos != null) ? 2129968 : 7455580;
			}
		}, new Block[] { Blocks.WATERLILY });
		return blockcolors;
	}

	public int func_189991_a(IBlockState p_189991_1_) {
		IBlockColor iblockcolor = this.mapBlockColors.getByValue(Block.getIdFromBlock(p_189991_1_.getBlock()));

		if (iblockcolor != null) {
			return iblockcolor.colorMultiplier(p_189991_1_, (IBlockAccess) null, (BlockPos) null, 0);
		} else {
			MapColor mapcolor = p_189991_1_.getMapColor();
			return mapcolor != null ? mapcolor.colorValue : -1;
		}
	}

	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess blockAccess, @Nullable BlockPos pos, int renderPass) {
		IBlockColor iblockcolor = this.mapBlockColors.getByValue(Block.getIdFromBlock(state.getBlock()));
		return iblockcolor == null ? -1 : iblockcolor.colorMultiplier(state, blockAccess, pos, renderPass);
	}

	public void registerBlockColorHandler(IBlockColor blockColor, Block... blocksIn) {
		for (Block block : blocksIn) {
			this.mapBlockColors.put(blockColor, Block.getIdFromBlock(block));
		}
	}
}
