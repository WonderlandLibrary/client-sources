package net.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomePlains extends Biome {
	protected boolean sunflowers;

	protected BiomePlains(boolean p_i46699_1_, Biome.BiomeProperties properties) {
		super(properties);
		this.sunflowers = p_i46699_1_;
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityHorse.class, 5, 2, 6));
		this.theBiomeDecorator.treesPerChunk = 0;
		this.theBiomeDecorator.field_189870_A = 0.05F;
		this.theBiomeDecorator.flowersPerChunk = 4;
		this.theBiomeDecorator.grassPerChunk = 10;
	}

	@Override
	public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
		double d0 = GRASS_COLOR_NOISE.getValue(pos.getX() / 200.0D, pos.getZ() / 200.0D);

		if (d0 < -0.8D) {
			int j = rand.nextInt(4);

			switch (j) {
			case 0:
				return BlockFlower.EnumFlowerType.ORANGE_TULIP;

			case 1:
				return BlockFlower.EnumFlowerType.RED_TULIP;

			case 2:
				return BlockFlower.EnumFlowerType.PINK_TULIP;

			case 3:
			default:
				return BlockFlower.EnumFlowerType.WHITE_TULIP;
			}
		} else if (rand.nextInt(3) > 0) {
			int i = rand.nextInt(3);
			return i == 0 ? BlockFlower.EnumFlowerType.POPPY : (i == 1 ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY);
		} else {
			return BlockFlower.EnumFlowerType.DANDELION;
		}
	}

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		double d0 = GRASS_COLOR_NOISE.getValue((pos.getX() + 8) / 200.0D, (pos.getZ() + 8) / 200.0D);

		if (d0 < -0.8D) {
			this.theBiomeDecorator.flowersPerChunk = 15;
			this.theBiomeDecorator.grassPerChunk = 5;
		} else {
			this.theBiomeDecorator.flowersPerChunk = 4;
			this.theBiomeDecorator.grassPerChunk = 10;
			DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

			for (int i = 0; i < 7; ++i) {
				int j = rand.nextInt(16) + 8;
				int k = rand.nextInt(16) + 8;
				int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
				DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
			}
		}

		if (this.sunflowers) {
			DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);

			for (int i1 = 0; i1 < 10; ++i1) {
				int j1 = rand.nextInt(16) + 8;
				int k1 = rand.nextInt(16) + 8;
				int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
				DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
			}
		}

		super.decorate(worldIn, rand, pos);
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random rand) {
		return rand.nextInt(3) == 0 ? BIG_TREE_FEATURE : TREE_FEATURE;
	}
}
