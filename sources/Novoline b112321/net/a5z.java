package net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class a5z extends Item {
   public a5z() {
      this.setMaxStackSize(1);
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      var3.displayGUIBook(var1);
      var3.triggerAchievement(StatList.objectUseStats[Item.b(this)]);
      return var1;
   }

   public static boolean a(NBTTagCompound var0) {
      return false;
   }
}
