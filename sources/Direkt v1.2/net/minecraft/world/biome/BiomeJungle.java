package net.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class BiomeJungle extends Biome {
	private final boolean isEdge;
	private static final IBlockState JUNGLE_LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
	private static final IBlockState JUNGLE_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY,
			Boolean.valueOf(false));
	private static final IBlockState OAK_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY,
			Boolean.valueOf(false));

	public BiomeJungle(boolean isEdgeIn, Biome.BiomeProperties properties) {
		super(properties);
		this.isEdge = isEdgeIn;

		if (isEdgeIn) {
			this.theBiomeDecorator.treesPerChunk = 2;
		} else {
			this.theBiomeDecorator.treesPerChunk = 50;
		}

		this.theBiomeDecorator.grassPerChunk = 25;
		this.theBiomeDecorator.flowersPerChunk = 4;

		if (!isEdgeIn) {
			this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
		}

		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random rand) {
		return rand.nextInt(10) == 0 ? BIG_TREE_FEATURE
				: (rand.nextInt(2) == 0 ? new WorldGenShrub(JUNGLE_LOG, OAK_LEAF)
						: (!this.isEdge && (rand.nextInt(3) == 0) ? new WorldGenMegaJungle(false, 10, 20, JUNGLE_LOG, JUNGLE_LEAF)
								: new WorldGenTrees(false, 4 + rand.nextInt(7), JUNGLE_LOG, JUNGLE_LEAF, true)));
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand) {
		return rand.nextInt(4) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		super.decorate(worldIn, rand, pos);
		int i = rand.nextInt(16) + 8;
		int j = rand.nextInt(16) + 8;
		int k = rand.nextInt(worldIn.getHeight(pos.add(i, 0, j)).getY() * 2);
		(new WorldGenMelon()).generate(worldIn, rand, pos.add(i, k, j));
		WorldGenVines worldgenvines = new WorldGenVines();

		for (j = 0; j < 50; ++j) {
			k = rand.nextInt(16) + 8;
			int l = 128;
			int i1 = rand.nextInt(16) + 8;
			worldgenvines.generate(worldIn, rand, pos.add(k, 128, i1));
		}
	}
}
