package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockOre extends Block {
	public BlockOre() {
		this(Material.ROCK.getMaterialMapColor());
	}

	public BlockOre(MapColor color) {
		super(Material.ROCK, color);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this == Blocks.COAL_ORE ? Items.COAL
				: (this == Blocks.DIAMOND_ORE ? Items.DIAMOND
						: (this == Blocks.LAPIS_ORE ? Items.DYE : (this == Blocks.EMERALD_ORE ? Items.EMERALD : (this == Blocks.QUARTZ_ORE ? Items.QUARTZ : Item.getItemFromBlock(this)))));
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random) {
		return this == Blocks.LAPIS_ORE ? 4 + random.nextInt(5) : 1;
	}

	/**
	 * Get the quantity dropped based on the given fortune level
	 */
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		if ((fortune > 0) && (Item.getItemFromBlock(this) != this.getItemDropped(this.getBlockState().getValidStates().iterator().next(), random, fortune))) {
			int i = random.nextInt(fortune + 2) - 1;

			if (i < 0) {
				i = 0;
			}

			return this.quantityDropped(random) * (i + 1);
		} else {
			return this.quantityDropped(random);
		}
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);

		if (this.getItemDropped(state, worldIn.rand, fortune) != Item.getItemFromBlock(this)) {
			int i = 0;

			if (this == Blocks.COAL_ORE) {
				i = MathHelper.getRandomIntegerInRange(worldIn.rand, 0, 2);
			} else if (this == Blocks.DIAMOND_ORE) {
				i = MathHelper.getRandomIntegerInRange(worldIn.rand, 3, 7);
			} else if (this == Blocks.EMERALD_ORE) {
				i = MathHelper.getRandomIntegerInRange(worldIn.rand, 3, 7);
			} else if (this == Blocks.LAPIS_ORE) {
				i = MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
			} else if (this == Blocks.QUARTZ_ORE) {
				i = MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
			}

			this.dropXpOnBlockBreak(worldIn, pos, i);
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return this == Blocks.LAPIS_ORE ? EnumDyeColor.BLUE.getDyeDamage() : 0;
	}
}
