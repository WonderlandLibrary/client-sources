package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.player.ChestStealer;
import cc.novoline.modules.player.Freecam;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import net.aHM;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor extends AbstractModule {
   private final Timer timer = new Timer();
   @Property("equip-delay")
   private final IntProperty equipDelay = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(3)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(10));
   @Property("open-inventory")
   private final BooleanProperty open_inv = PropertyFactory.booleanFalse();
   @Property("no-move")
   private final BooleanProperty A = PropertyFactory.booleanFalse();

   public AutoArmor(ModuleManager var1) {
      super(var1, "AutoArmor", "Auto Armor", EnumModuleType.PLAYER, "Equips armor for you");
      Manager.put(new Setting("Equip_Delay", "Delay", SettingType.SLIDER, this, this.equipDelay, 1.0D));
      Manager.put(new Setting("AA_OPEN_INV", "Open Inventory", SettingType.CHECKBOX, this, this.open_inv));
      Manager.put(new Setting("AA_NO_MOVE", "Not Moving", SettingType.CHECKBOX, this, this.A));
   }

   private float getProtection(ItemStack var1) {
      Freecam.a();
      float var3 = 0.0F;
      if(var1.getItem() instanceof ItemArmor) {
         ItemArmor var4 = (ItemArmor)var1.getItem();
         var3 = (float)((double)var3 + (double)var4.damageReduceAmount + (double)((100 - var4.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, var1)) * 0.0075D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var1) / 50.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, var1) / 100.0D);
      }

      return var3;
   }

   @EventTarget
   public void onEvent(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState().equals(EventState.PRE)) {
         if(!(this.mc.currentScreen instanceof GuiInventory) && ((Boolean)this.open_inv.get()).booleanValue() || this.mc.player.getLastTickDistance() > 0.05D && ((Boolean)this.A.get()).booleanValue() || ServerUtils.serverIs(Servers.PRE) || ServerUtils.serverIs(Servers.LOBBY) || ((ChestStealer)this.getModule(ChestStealer.class)).isStealing()) {
            return;
         }

         if(this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen instanceof aHM) {
            int var3 = 1;
            if(var3 < 5) {
               if(this.mc.player.inventoryContainer.getSlot(4 + var3).getHasStack()) {
                  ItemStack var4 = this.mc.player.inventoryContainer.getSlot(4 + var3).getStack();
                  if(this.isBestArmor(var4, var3)) {
                     ;
                  }

                  this.mc.player.drop(4 + var3);
               }

               int var7 = 9;
               if(var7 < 45) {
                  if(this.mc.player.inventoryContainer.getSlot(var7).getHasStack()) {
                     ItemStack var5 = this.mc.player.inventoryContainer.getSlot(var7).getStack();
                     if(this.isBestArmor(var5, var3) && this.getProtection(var5) > 0.0F && this.timer.check((float)(((Integer)this.equipDelay.get()).intValue() * 50))) {
                        this.mc.player.shiftClick(var7);
                        this.timer.reset();
                     }
                  }

                  ++var7;
               }

               ++var3;
            }
         }
      }

   }

   public boolean isWorking() {
      int[] var1 = Freecam.a();
      return !this.timer.check((float)(((Integer)this.equipDelay.get()).intValue() * 100));
   }

   boolean isBestArmor(ItemStack var1, int var2) {
      Freecam.a();
      String var4 = "";
      switch(var2) {
      case 1:
         var4 = "helmet";
      case 2:
         var4 = "chestplate";
      case 3:
         var4 = "leggings";
      case 4:
         var4 = "boots";
      default:
         if(!var1.getUnlocalizedName().contains(var4)) {
            return false;
         } else {
            float var5 = this.getProtection(var1);
            int var6 = 5;
            if(var6 < 45) {
               if(this.mc.player.inventoryContainer.getSlot(var6).getHasStack()) {
                  ItemStack var7 = this.mc.player.inventoryContainer.getSlot(var6).getStack();
                  if(this.getProtection(var7) > var5 && var7.getUnlocalizedName().contains(var4)) {
                     return false;
                  }
               }

               ++var6;
            }

            return true;
         }
      }
   }

   public IntProperty getEquipDelay() {
      return this.equipDelay;
   }

   public BooleanProperty a() {
      return this.open_inv;
   }

   public BooleanProperty d() {
      return this.A;
   }
}
