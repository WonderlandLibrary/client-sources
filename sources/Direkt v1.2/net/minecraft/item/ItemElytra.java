package net.minecraft.item;

import javax.annotation.Nullable;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemElytra extends Item {
	public ItemElytra() {
		this.maxStackSize = 1;
		this.setMaxDamage(432);
		this.setCreativeTab(CreativeTabs.TRANSPORTATION);
		this.addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter() {
			@Override
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return ItemElytra.isBroken(stack) ? 0.0F : 1.0F;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
	}

	public static boolean isBroken(ItemStack stack) {
		return stack.getItemDamage() < (stack.getMaxDamage() - 1);
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == Items.LEATHER;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemStackIn);
		ItemStack itemstack = playerIn.getItemStackFromSlot(entityequipmentslot);

		if (itemstack == null) {
			playerIn.setItemStackToSlot(entityequipmentslot, itemStackIn.copy());
			itemStackIn.stackSize = 0;
			return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
		} else {
			return new ActionResult(EnumActionResult.FAIL, itemStackIn);
		}
	}
}
