package net.minecraft.client.renderer.color;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;

public class ItemColors {
	private final ObjectIntIdentityMap<IItemColor> mapItemColors = new ObjectIntIdentityMap(32);

	public static ItemColors init(final BlockColors p_186729_0_) {
		ItemColors itemcolors = new ItemColors();
		itemcolors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return tintIndex > 0 ? -1 : ((ItemArmor) stack.getItem()).getColor(stack);
			}
		}, new Item[] { Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS });
		itemcolors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return tintIndex > 0 ? -1 : ItemBanner.getBaseColor(stack).getMapColor().colorValue;
			}
		}, new Item[] { Items.BANNER, Items.SHIELD });
		itemcolors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = BlockDoublePlant.EnumPlantType.byMetadata(stack.getMetadata());
				return (blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS) && (blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN) ? -1
						: ColorizerGrass.getGrassColor(0.5D, 1.0D);
			}
		}, new Block[] { Blocks.DOUBLE_PLANT });
		itemcolors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (tintIndex != 1) {
					return -1;
				} else {
					NBTBase nbtbase = ItemFireworkCharge.getExplosionTag(stack, "Colors");

					if (!(nbtbase instanceof NBTTagIntArray)) {
						return 9079434;
					} else {
						int[] aint = ((NBTTagIntArray) nbtbase).getIntArray();

						if (aint.length == 1) {
							return aint[0];
						} else {
							int i = 0;
							int j = 0;
							int k = 0;

							for (int l : aint) {
								i += (l & 16711680) >> 16;
								j += (l & 65280) >> 8;
								k += (l & 255) >> 0;
							}

							i = i / aint.length;
							j = j / aint.length;
							k = k / aint.length;
							return (i << 16) | (j << 8) | k;
						}
					}
				}
			}
		}, new Item[] { Items.FIREWORK_CHARGE });
		itemcolors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return tintIndex > 0 ? -1 : PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromStack(stack));
			}
		}, new Item[] { Items.POTIONITEM, Items.SPLASH_POTION, Items.LINGERING_POTION });
		itemcolors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				EntityList.EntityEggInfo entitylist$entityegginfo = EntityList.ENTITY_EGGS.get(ItemMonsterPlacer.getEntityIdFromItem(stack));
				return entitylist$entityegginfo == null ? -1 : (tintIndex == 0 ? entitylist$entityegginfo.primaryColor : entitylist$entityegginfo.secondaryColor);
			}
		}, new Item[] { Items.SPAWN_EGG });
		itemcolors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
				return p_186729_0_.colorMultiplier(iblockstate, (IBlockAccess) null, (BlockPos) null, tintIndex);
			}
		}, new Block[] { Blocks.GRASS, Blocks.TALLGRASS, Blocks.VINE, Blocks.LEAVES, Blocks.LEAVES2, Blocks.WATERLILY });
		itemcolors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				return tintIndex == 0 ? PotionUtils.getPotionColorFromEffectList(PotionUtils.getEffectsFromStack(stack)) : -1;
			}
		}, new Item[] { Items.TIPPED_ARROW });
		return itemcolors;
	}

	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		IItemColor iitemcolor = this.mapItemColors.getByValue(Item.REGISTRY.getIDForObject(stack.getItem()));
		return iitemcolor == null ? -1 : iitemcolor.getColorFromItemstack(stack, tintIndex);
	}

	public void registerItemColorHandler(IItemColor itemColor, Block... blocksIn) {
		for (Block block : blocksIn) {
			this.mapItemColors.put(itemColor, Item.getIdFromItem(Item.getItemFromBlock(block)));
		}
	}

	public void registerItemColorHandler(IItemColor itemColor, Item... itemsIn) {
		for (Item item : itemsIn) {
			this.mapItemColors.put(itemColor, Item.getIdFromItem(item));
		}
	}
}
