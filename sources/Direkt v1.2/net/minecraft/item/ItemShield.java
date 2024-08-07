package net.minecraft.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class ItemShield extends Item {
	public ItemShield() {
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.COMBAT);
		this.setMaxDamage(336);
		this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter() {
			@Override
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return (entityIn != null) && entityIn.isHandActive() && (entityIn.getActiveItemStack() == stack) ? 1.0F : 0.0F;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.getSubCompound("BlockEntityTag", false) != null) {
			String s = "item.shield.";
			EnumDyeColor enumdyecolor = ItemBanner.getBaseColor(stack);
			s = s + enumdyecolor.getUnlocalizedName() + ".name";
			return I18n.translateToLocal(s);
		} else {
			return I18n.translateToLocal("item.shield.name");
		}
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		ItemBanner.appendHoverTextFromTileEntityTag(stack, tooltip);
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		ItemStack itemstack = new ItemStack(itemIn, 1, 0);
		subItems.add(itemstack);
	}

	/**
	 * gets the CreativeTab this item is displayed on
	 */
	@Override
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.COMBAT;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		playerIn.setActiveHand(hand);
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == Item.getItemFromBlock(Blocks.PLANKS) ? true : super.getIsRepairable(toRepair, repair);
	}
}
