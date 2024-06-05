package hawk.util;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ArmorUtils {
   public static boolean isBetterArmor(int var0, int[] var1) {
      if (Minecraft.getMinecraft().thePlayer.inventory.armorInventory[var0] != null) {
         int var2 = 0;
         int var3 = 0;
         int var4 = -1;
         int var5 = -1;
         int[] var6 = var1;
         int var7 = var1.length;

         int var8;
         int var9;
         for(var8 = 0; var8 < var7; ++var8) {
            var9 = var6[var8];
            if (Item.getIdFromItem(Minecraft.getMinecraft().thePlayer.inventory.armorInventory[var0].getItem()) == var9) {
               var4 = var2;
               break;
            }

            ++var2;
         }

         var6 = var1;
         var7 = var1.length;

         for(var8 = 0; var8 < var7; ++var8) {
            var9 = var6[var8];
            if (getItem(var9) != -1) {
               var5 = var3;
               break;
            }

            ++var3;
         }

         if (var5 > -1) {
            if (var5 < var4) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   public static int getItem(int var0) {
      for(int var1 = 9; var1 < 45; ++var1) {
         ItemStack var2 = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(var1).getStack();
         if (var2 != null && Item.getIdFromItem(var2.getItem()) == var0) {
            return var1;
         }
      }

      return -1;
   }
}
