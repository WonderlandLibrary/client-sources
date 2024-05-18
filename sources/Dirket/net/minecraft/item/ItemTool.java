package net.minecraft.item;

import java.util.Set;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTool extends Item {
	private final Set<Block> effectiveBlocks;
	protected float efficiencyOnProperMaterial;

	/** Damage versus entities. */
	protected float damageVsEntity;
	protected float attackSpeed;

	/** The material this tool is made from. */
	protected Item.ToolMaterial toolMaterial;

	protected ItemTool(float attackDamageIn, float attackSpeedIn, Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
		this.efficiencyOnProperMaterial = 4.0F;
		this.toolMaterial = materialIn;
		this.effectiveBlocks = effectiveBlocksIn;
		this.maxStackSize = 1;
		this.setMaxDamage(materialIn.getMaxUses());
		this.efficiencyOnProperMaterial = materialIn.getEfficiencyOnProperMaterial();
		this.damageVsEntity = attackDamageIn + materialIn.getDamageVsEntity();
		this.attackSpeed = attackSpeedIn;
		this.setCreativeTab(CreativeTabs.TOOLS);
	}

	protected ItemTool(Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
		this(0.0F, 0.0F, materialIn, effectiveBlocksIn);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		return this.effectiveBlocks.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : 1.0F;
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise the damage on the stack.
	 */
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(2, attacker);
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (state.getBlockHardness(worldIn, pos) != 0.0D) {
			stack.damageItem(1, entityLiving);
		}

		return true;
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@Override
	public boolean isFull3D() {
		return true;
	}

	public Item.ToolMaterial getToolMaterial() {
		return this.toolMaterial;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getItemEnchantability() {
		return this.toolMaterial.getEnchantability();
	}

	/**
	 * Return the name for this tool's material.
	 */
	public String getToolMaterialName() {
		return this.toolMaterial.toString();
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.toolMaterial.getRepairItem() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.damageVsEntity, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, 0));
		}

		return multimap;
	}
}
