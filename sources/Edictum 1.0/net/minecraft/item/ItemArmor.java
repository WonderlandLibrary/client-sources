package net.minecraft.item;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemArmor extends Item
{
    /** Holds the 'base' maxDamage that each armorType have. */
    private static final int[] MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};
    private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    public static final String[] EMPTY_SLOT_NAMES = new String[] {"minecraft:items/empty_armor_slot_boots", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_helmet"};
    public static final IBehaviorDispenseItem DISPENSER_BEHAVIOR = new BehaviorDefaultDispenseItem()
    {
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
            ItemStack itemstack = ItemArmor.dispenseArmor(source, stack);
            return itemstack != null ? itemstack : super.dispenseStack(source, stack);
        }
    };

    /**
     * Stores the armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots
     */
    public final EntityEquipmentSlot armorType;

    /** Holds the amount of damage that the armor reduces at full durability. */
    public final int damageReduceAmount;
    public final float field_189415_e;

    /**
     * Used on RenderPlayer to select the correspondent armor to be rendered on the player: 0 is cloth, 1 is chain, 2 is
     * iron, 3 is diamond and 4 is gold.
     */
    public final int renderIndex;

    /** The EnumArmorMaterial used for this ItemArmor */
    private final ItemArmor.ArmorMaterial material;

    public static ItemStack dispenseArmor(IBlockSource blockSource, ItemStack stack)
    {
        BlockPos blockpos = blockSource.getBlockPos().offset((EnumFacing)blockSource.func_189992_e().getValue(BlockDispenser.FACING));
        List<EntityLivingBase> list = blockSource.getWorld().<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(blockpos), Predicates.<EntityLivingBase> and (EntitySelectors.NOT_SPECTATING, new EntitySelectors.ArmoredMob(stack)));

        if (list.isEmpty())
        {
            return null;
        }
        else
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)list.get(0);
            EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(stack);
            ItemStack itemstack = stack.copy();
            itemstack.stackSize = 1;
            entitylivingbase.setItemStackToSlot(entityequipmentslot, itemstack);

            if (entitylivingbase instanceof EntityLiving)
            {
                ((EntityLiving)entitylivingbase).setDropChance(entityequipmentslot, 2.0F);
            }

            --stack.stackSize;
            return stack;
        }
    }

    public ItemArmor(ItemArmor.ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
    {
        this.material = materialIn;
        this.armorType = equipmentSlotIn;
        this.renderIndex = renderIndexIn;
        this.damageReduceAmount = materialIn.getDamageReductionAmount(equipmentSlotIn);
        this.setMaxDamage(materialIn.getDurability(equipmentSlotIn));
        this.field_189415_e = materialIn.func_189416_e();
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.COMBAT);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DISPENSER_BEHAVIOR);
    }

    /**
     * Gets the equipment slot of this armor piece (formerly known as armor type)
     */
    public EntityEquipmentSlot getEquipmentSlot()
    {
        return this.armorType;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    /**
     * Return the armor material for this armor item.
     */
    public ItemArmor.ArmorMaterial getArmorMaterial()
    {
        return this.material;
    }

    /**
     * Return whether the specified armor ItemStack has a color.
     */
    public boolean hasColor(ItemStack stack)
    {
        if (this.material != ItemArmor.ArmorMaterial.LEATHER)
        {
            return false;
        }
        else
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            return nbttagcompound != null && nbttagcompound.hasKey("display", 10) ? nbttagcompound.getCompoundTag("display").hasKey("color", 3) : false;
        }
    }

    /**
     * Return the color for the specified armor ItemStack.
     */
    public int getColor(ItemStack stack)
    {
        if (this.material != ItemArmor.ArmorMaterial.LEATHER)
        {
            return 16777215;
        }
        else
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
                {
                    return nbttagcompound1.getInteger("color");
                }
            }

            return 10511680;
        }
    }

    /**
     * Remove the color from the specified armor ItemStack.
     */
    public void removeColor(ItemStack stack)
    {
        if (this.material == ItemArmor.ArmorMaterial.LEATHER)
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound != null)
            {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

                if (nbttagcompound1.hasKey("color"))
                {
                    nbttagcompound1.removeTag("color");
                }
            }
        }
    }

    /**
     * Sets the color of the specified armor ItemStack
     */
    public void setColor(ItemStack stack, int color)
    {
        if (this.material != ItemArmor.ArmorMaterial.LEATHER)
        {
            throw new UnsupportedOperationException("Can\'t dye non-leather!");
        }
        else
        {
            NBTTagCompound nbttagcompound = stack.getTagCompound();

            if (nbttagcompound == null)
            {
                nbttagcompound = new NBTTagCompound();
                stack.setTagCompound(nbttagcompound);
            }

            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (!nbttagcompound.hasKey("display", 10))
            {
                nbttagcompound.setTag("display", nbttagcompound1);
            }

            nbttagcompound1.setInteger("color", color);
        }
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return this.material.getRepairItem() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemStackIn);
        ItemStack itemstack = playerIn.getItemStackFromSlot(entityequipmentslot);

        if (itemstack == null)
        {
            playerIn.setItemStackToSlot(entityequipmentslot, itemStackIn.copy());
            itemStackIn.stackSize = 0;
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        }
        else
        {
            return new ActionResult(EnumActionResult.FAIL, itemStackIn);
        }
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", (double)this.damageReduceAmount, 0));
            multimap.put(SharedMonsterAttributes.field_189429_h.getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", (double)this.field_189415_e, 0));
        }

        return multimap;
    }

    public static enum ArmorMaterial
    {
        LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F),
        CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F),
        IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F),
        GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F),
        DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);

        private final String name;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float field_189417_k;

        private ArmorMaterial(String p_i47117_3_, int p_i47117_4_, int[] p_i47117_5_, int p_i47117_6_, SoundEvent p_i47117_7_, float p_i47117_8_)
        {
            this.name = p_i47117_3_;
            this.maxDamageFactor = p_i47117_4_;
            this.damageReductionAmountArray = p_i47117_5_;
            this.enchantability = p_i47117_6_;
            this.soundEvent = p_i47117_7_;
            this.field_189417_k = p_i47117_8_;
        }

        public int getDurability(EntityEquipmentSlot armorType)
        {
            return ItemArmor.MAX_DAMAGE_ARRAY[armorType.getIndex()] * this.maxDamageFactor;
        }

        public int getDamageReductionAmount(EntityEquipmentSlot armorType)
        {
            return this.damageReductionAmountArray[armorType.getIndex()];
        }

        public int getEnchantability()
        {
            return this.enchantability;
        }

        public SoundEvent getSoundEvent()
        {
            return this.soundEvent;
        }

        public Item getRepairItem()
        {
            return this == LEATHER ? Items.LEATHER : (this == CHAIN ? Items.IRON_INGOT : (this == GOLD ? Items.GOLD_INGOT : (this == IRON ? Items.IRON_INGOT : (this == DIAMOND ? Items.DIAMOND : null))));
        }

        public String getName()
        {
            return this.name;
        }

        public float func_189416_e()
        {
            return this.field_189417_k;
        }
    }
}
