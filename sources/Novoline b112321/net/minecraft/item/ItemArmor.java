package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor$1;
import net.minecraft.item.ItemArmor$ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemArmor extends Item {
   private static final int[] maxDamageArray = new int[]{11, 16, 15, 13};
   public static final String[] EMPTY_SLOT_NAMES = new String[]{"minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots"};
   private static final IBehaviorDispenseItem dispenserBehavior = new ItemArmor$1();
   public final int armorType;
   public final int damageReduceAmount;
   public final int renderIndex;
   private final ItemArmor$ArmorMaterial material;

   public ItemArmor(ItemArmor$ArmorMaterial var1, int var2, int var3) {
      this.material = var1;
      this.armorType = var3;
      this.renderIndex = var2;
      this.damageReduceAmount = var1.getDamageReductionAmount(var3);
      this.setMaxDamage(var1.getDurability(var3));
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabCombat);
      BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
   }

   public int getColorFromItemStack(ItemStack var1, int var2) {
      return 16777215;
   }

   public int getItemEnchantability() {
      return this.material.getEnchantability();
   }

   public ItemArmor$ArmorMaterial getArmorMaterial() {
      return this.material;
   }

   public boolean hasColor(ItemStack var1) {
      return this.material == ItemArmor$ArmorMaterial.LEATHER && var1.hasTagCompound() && var1.getTagCompound().hasKey("display", 10) && var1.getTagCompound().getCompoundTag("display").hasKey("color", 3);
   }

   public int getColor(ItemStack var1) {
      if(this.material != ItemArmor$ArmorMaterial.LEATHER) {
         return -1;
      } else {
         NBTTagCompound var2 = var1.getTagCompound();
         NBTTagCompound var3 = var2.getCompoundTag("display");
         return var3.hasKey("color", 3)?var3.getInteger("color"):10511680;
      }
   }

   public void removeColor(ItemStack var1) {
      if(this.material == ItemArmor$ArmorMaterial.LEATHER) {
         NBTTagCompound var2 = var1.getTagCompound();
         NBTTagCompound var3 = var2.getCompoundTag("display");
         if(var3.hasKey("color")) {
            var3.removeTag("color");
         }
      }

   }

   public void setColor(ItemStack var1, int var2) {
      if(this.material != ItemArmor$ArmorMaterial.LEATHER) {
         throw new UnsupportedOperationException("Can\'t dye non-leather!");
      } else {
         NBTTagCompound var3 = var1.getTagCompound();
         var3 = new NBTTagCompound();
         var1.setTagCompound(var3);
         NBTTagCompound var4 = var3.getCompoundTag("display");
         if(!var3.hasKey("display", 10)) {
            var3.setTag("display", var4);
         }

         var4.setInteger("color", var2);
      }
   }

   public boolean getIsRepairable(ItemStack var1, ItemStack var2) {
      return this.material.getRepairItem() == var2.getItem() || super.getIsRepairable(var1, var2);
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      int var4 = EntityLiving.getArmorPosition(var1) - 1;
      ItemStack var5 = var3.getCurrentArmor(var4);
      var3.setCurrentItemOrArmor(var4, var1.copy());
      var1.stackSize = 0;
      return var1;
   }

   static int[] access$000() {
      return maxDamageArray;
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
