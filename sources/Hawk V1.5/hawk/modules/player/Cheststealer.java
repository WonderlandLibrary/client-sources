package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventOpenChest;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.settings.BooleanSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import net.minecraft.inventory.ContainerChest;

public class Cheststealer extends Module {
   public static boolean silent = true;
   public BooleanSetting silentSteal = new BooleanSetting("Silent", false, this);
   long a;
   public static boolean enabled;
   public BooleanSetting autoClose = new BooleanSetting("Auto close", true, this);
   public NumberSetting delay = new NumberSetting("Delay", 20.0D, 0.0D, 1000.0D, 50.0D, this);
   hawk.util.Timer timer = new hawk.util.Timer();

   public Cheststealer() {
      super("ChestStealer", 0, Module.Category.PLAYER);
      this.addSettings(new Setting[]{this.delay, this.silentSteal});
   }

   public void onEnable() {
      enabled = true;
      this.a = 0L;
      if (AutoSetting.enabled) {
         if (AutoSetting.isHypixel) {
            this.delay.setValue(200.0D);
         }

         if (AutoSetting.isMineplex) {
            this.delay.setValue(50.0D);
         }

         if (AutoSetting.isOldVerus) {
            this.delay.setValue(200.0D);
         }

         if (AutoSetting.isRedesky) {
            this.delay.setValue(50.0D);
         }
      }

   }

   public void OnDisable() {
      enabled = false;
   }

   public void onEvent(Event var1) {
      if (this.mc.thePlayer == null) {
         this.toggled = false;
      }

      if (var1 instanceof EventUpdate) {
         if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel) {
               this.delay.setValue(200.0D);
            }

            if (AutoSetting.isMineplex) {
               this.delay.setValue(50.0D);
            }

            if (AutoSetting.isOldVerus) {
               this.delay.setValue(200.0D);
            }

            if (AutoSetting.isRedesky) {
               this.delay.setValue(50.0D);
            }
         }

         if (var1.isPre() && this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest var2 = (ContainerChest)this.mc.thePlayer.openContainer;

            for(int var3 = 0; var3 < var2.getLowerChestInventory().getSizeInventory(); ++var3) {
               if (var2.getLowerChestInventory().getStackInSlot(var3) != null && this.timer.hasTimeElapsed((long)this.delay.getValue(), true)) {
                  this.mc.playerController.windowClick(var2.windowId, var3, 0, 1, this.mc.thePlayer);
               }
            }

            var2.getInventory().isEmpty();
         }
      }

      if (var1 instanceof EventOpenChest && this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest && this.silentSteal.isEnabled()) {
         var1.setCancelled(true);
         this.mc.inGameHasFocus = true;
         this.mc.mouseHelper.grabMouseCursor();
      }

   }
}
