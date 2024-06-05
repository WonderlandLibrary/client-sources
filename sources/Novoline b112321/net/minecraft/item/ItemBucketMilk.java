package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {
   public ItemBucketMilk() {
      this.setMaxStackSize(1);
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   public ItemStack onItemUseFinish(ItemStack var1, World var2, EntityPlayer var3) {
      if(!var3.abilities.isCreative()) {
         --var1.stackSize;
      }

      if(!var2.isRemote) {
         var3.clearActivePotions();
      }

      var3.triggerAchievement(StatList.objectUseStats[Item.b(this)]);
      return var1.stackSize <= 0?new ItemStack(Items.bucket):var1;
   }

   public int getMaxItemUseDuration(ItemStack var1) {
      return 32;
   }

   public EnumAction getItemUseAction(ItemStack var1) {
      return EnumAction.DRINK;
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
      return var1;
   }
}
