package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class Autoarmor extends Module {
   private int[] boots;
   private int[] helmet;
   private int[] leggings;
   hawk.util.Timer timer = new hawk.util.Timer();
   private int delay;
   private boolean best;
   private int[] chestplate;
   public NumberSetting DelayArmor = new NumberSetting("Delay", 100.0D, 0.0D, 1000.0D, 50.0D, this);

   public void onEnable() {
      if (AutoSetting.enabled) {
         if (AutoSetting.isHypixel) {
            this.DelayArmor.setValue(200.0D);
         }

         if (AutoSetting.isMineplex) {
            this.DelayArmor.setValue(50.0D);
         }

         if (AutoSetting.isOldVerus) {
            this.DelayArmor.setValue(200.0D);
         }

         if (AutoSetting.isRedesky) {
            this.DelayArmor.setValue(50.0D);
         }
      }

   }

   public Autoarmor() {
      super("Autoarmor", 0, Module.Category.PLAYER);
      this.addSettings(new Setting[]{this.DelayArmor});
   }

   public boolean isChestInventory() {
      return this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest;
   }

   public static int checkProtection(ItemStack var0) {
      return EnchantmentHelper.getEnchantmentLevel(0, var0);
   }

   public int getFreeSlot() {
      for(int var1 = 35; var1 > 0; --var1) {
         ItemStack var2 = this.mc.thePlayer.inventory.getStackInSlot(var1);
         if (var2 == null) {
            return var1;
         }
      }

      return -1;
   }

   public void onEvent(Event var1) {
      if (AutoSetting.enabled) {
         if (AutoSetting.isHypixel) {
            this.DelayArmor.setValue(200.0D);
         }

         if (AutoSetting.isMineplex) {
            this.DelayArmor.setValue(50.0D);
         }

         if (AutoSetting.isOldVerus) {
            this.DelayArmor.setValue(200.0D);
         }

         if (AutoSetting.isRedesky) {
            this.DelayArmor.setValue(50.0D);
         }
      }

      if (var1 instanceof EventUpdate && !this.isChestInventory()) {
         for(int var2 = 0; var2 < 36; ++var2) {
            ItemStack var3 = this.mc.thePlayer.inventory.getStackInSlot(var2);
            if (var3 != null && var3.getItem() instanceof ItemArmor) {
               ItemArmor var4 = (ItemArmor)this.mc.thePlayer.inventory.getStackInSlot(var2).getItem();
               int var5 = 0;
               int var6 = 0;
               int var7 = 0;
               if (this.mc.thePlayer.inventory.getStackInSlot(39 - var4.armorType) != null) {
                  ItemArmor var8 = (ItemArmor)this.mc.thePlayer.inventory.getStackInSlot(39 - var4.armorType).getItem();
                  ItemStack var9 = this.mc.thePlayer.inventory.getStackInSlot(39 - var4.armorType);
                  var5 = var8.getArmorMaterial().getDamageReductionAmount(var4.armorType);
                  var5 += checkProtection(this.mc.thePlayer.inventory.getStackInSlot(39 - var4.armorType));
                  var6 = var9.getItemDamage();
                  var7 = var4.getArmorMaterial().getDamageReductionAmount(var4.armorType);
                  var7 += checkProtection(this.mc.thePlayer.inventory.getStackInSlot(var2));
               }

               if (this.getFreeSlot() != -1 && this.mc.thePlayer.inventory.getStackInSlot(39 - var4.armorType) != null && (var7 > var5 || var7 == var5 && var3.getItemDamage() < var6)) {
                  if (var2 < 9) {
                     var2 += 36;
                  }

                  this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, 5 + var4.armorType, 0, 4, this.mc.thePlayer);
                  this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, var2, 0, 1, this.mc.thePlayer);
               }

               if (this.mc.thePlayer.inventory.getStackInSlot(39 - var4.armorType) == null && this.timer.hasTimeElapsed((long)this.DelayArmor.getValue(), true)) {
                  if (var2 < 9) {
                     var2 += 36;
                  }

                  this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, var2, 0, 1, this.mc.thePlayer);
               }
            }
         }
      }

   }
}
