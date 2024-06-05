package net.minecraft.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item$ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemSword extends Item {
   private float attackDamage;
   private final Item$ToolMaterial material;

   public ItemSword(Item$ToolMaterial var1) {
      this.material = var1;
      this.maxStackSize = 1;
      this.setMaxDamage(var1.getMaxUses());
      this.setCreativeTab(CreativeTabs.tabCombat);
      this.attackDamage = 4.0F + var1.getDamageVsEntity();
   }

   public float getAttackDamage() {
      return this.attackDamage;
   }

   public float getDamageVsEntity() {
      return this.material.getDamageVsEntity();
   }

   public float getStrVsBlock(ItemStack var1, Block var2) {
      if(var2 == Blocks.web) {
         return 15.0F;
      } else {
         Material var3 = var2.getMaterial();
         return var3 != Material.plants && var3 != Material.vine && var3 != Material.coral && var3 != Material.leaves && var3 != Material.gourd?1.0F:1.5F;
      }
   }

   public boolean hitEntity(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      var1.damageItem(1, var3);
      return true;
   }

   public boolean onBlockDestroyed(ItemStack var1, World var2, Block var3, BlockPos var4, EntityLivingBase var5) {
      if((double)var3.getBlockHardness(var2, var4) != 0.0D) {
         var1.damageItem(2, var5);
      }

      return true;
   }

   public boolean isFull3D() {
      return true;
   }

   public EnumAction getItemUseAction(ItemStack var1) {
      return EnumAction.BLOCK;
   }

   public int getMaxItemUseDuration(ItemStack var1) {
      return 72000;
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
      return var1;
   }

   public boolean canHarvestBlock(Block var1) {
      return var1 == Blocks.web;
   }

   public int getItemEnchantability() {
      return this.material.getEnchantability();
   }

   public String getToolMaterialName() {
      return this.material.toString();
   }

   public boolean getIsRepairable(ItemStack var1, ItemStack var2) {
      return this.material.getRepairItem() == var2.getItem() || super.getIsRepairable(var1, var2);
   }

   public Multimap getItemAttributeModifiers() {
      Multimap var1 = super.getItemAttributeModifiers();
      var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", (double)this.attackDamage, 0));
      return var1;
   }
}
