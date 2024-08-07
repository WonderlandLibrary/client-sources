package net.minecraft.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemFirework extends Item {
	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(worldIn, pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ, stack);
			worldIn.spawnEntityInWorld(entityfireworkrocket);

			if (!playerIn.capabilities.isCreativeMode) {
				--stack.stackSize;
			}
		}

		return EnumActionResult.SUCCESS;
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (stack.hasTagCompound()) {
			NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("Fireworks");

			if (nbttagcompound != null) {
				if (nbttagcompound.hasKey("Flight", 99)) {
					tooltip.add(I18n.translateToLocal("item.fireworks.flight") + " " + nbttagcompound.getByte("Flight"));
				}

				NBTTagList nbttaglist = nbttagcompound.getTagList("Explosions", 10);

				if ((nbttaglist != null) && !nbttaglist.hasNoTags()) {
					for (int i = 0; i < nbttaglist.tagCount(); ++i) {
						NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
						List<String> list = Lists.<String> newArrayList();
						ItemFireworkCharge.addExplosionInfo(nbttagcompound1, list);

						if (!list.isEmpty()) {
							for (int j = 1; j < ((List) list).size(); ++j) {
								list.set(j, "  " + list.get(j));
							}

							tooltip.addAll(list);
						}
					}
				}
			}
		}
	}
}
