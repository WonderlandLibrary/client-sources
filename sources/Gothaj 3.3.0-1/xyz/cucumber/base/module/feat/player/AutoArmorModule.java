package xyz.cucumber.base.module.feat.player;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Automatically puts armor on you",
   name = "Auto Armor"
)
public class AutoArmorModule extends Mod {
   public Timer startTimer = new Timer();
   public Timer timer = new Timer();
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Open Inv", "Spoof"});
   public BooleanSettings intave = new BooleanSettings("Intave", () -> this.mode.getMode().equalsIgnoreCase("Spoof"), true);
   public NumberSettings startDelay = new NumberSettings("Start Delay", 150.0, 0.0, 1000.0, 1.0);
   public NumberSettings speed = new NumberSettings("Speed", 150.0, 0.0, 1000.0, 1.0);
   public KeyBinding[] moveKeys = new KeyBinding[]{
      this.mc.gameSettings.keyBindForward,
      this.mc.gameSettings.keyBindBack,
      this.mc.gameSettings.keyBindLeft,
      this.mc.gameSettings.keyBindRight,
      this.mc.gameSettings.keyBindJump,
      this.mc.gameSettings.keyBindSneak
   };

   public AutoArmorModule() {
      this.addSettings(new ModuleSettings[]{this.mode, this.intave, this.startDelay, this.speed});
   }

   @EventListener
   public void onMoveButton(EventMoveButton event) {
      this.setInfo(this.mode.getMode());
      KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      EntityLivingBase target = EntityUtils.getTarget(
         6.0,
         ka.Targets.getMode(),
         ka.switchMode.getMode(),
         400,
         Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
         ka.TroughWalls.isEnabled(),
         ka.attackInvisible.isEnabled(),
         ka.attackDead.isEnabled()
      );
      if (!this.mode.getMode().equalsIgnoreCase("Spoof") || this.mc.currentScreen == null && target == null) {
         if (this.mode.getMode().equalsIgnoreCase("Open Inv")) {
            if (this.mc.currentScreen == null) {
               this.startTimer.reset();
            }

            if (!this.startTimer.hasTimeElapsed(this.startDelay.getValue(), false)) {
               return;
            }
         }

         if (InventoryUtils.timer.hasTimeElapsed(this.speed.value, false)) {
            if (this.mode.getMode().equalsIgnoreCase("Open Inv") && !(this.mc.currentScreen instanceof GuiInventory)) {
               return;
            }

            for (int type = 1; type < 5; type++) {
               if (this.mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                  ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                  if (!InventoryUtils.isBestArmor(is, type)) {
                     InventoryUtils.openInv(this.mode.getMode());
                     InventoryUtils.drop(4 + type);
                     if (!this.intave.isEnabled()) {
                        InventoryUtils.closeInv(this.mode.getMode());
                     }

                     InventoryUtils.timer.reset();
                     if (this.speed.getValue() != 0.0) {
                        break;
                     }
                  }
               }
            }

            for (int typex = 1; typex < 5; typex++) {
               if ((double)InventoryUtils.timer.getTime() > this.speed.getValue()) {
                  for (int i = 9; i < 45; i++) {
                     if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                        ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                        if (InventoryUtils.getProtection(is) > 0.0F && InventoryUtils.isBestArmor(is, typex) && !InventoryUtils.isBadStack(is, true, true)) {
                           InventoryUtils.openInv(this.mode.getMode());
                           InventoryUtils.shiftClick(i);
                           if (!this.intave.isEnabled()) {
                              InventoryUtils.closeInv(this.mode.getMode());
                           }

                           InventoryUtils.timer.reset();
                           if (this.speed.getValue() != 0.0) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

         if (InventoryUtils.timer.hasTimeElapsed(55.0, false)) {
            InventoryUtils.closeInv(this.mode.getMode());
         }

         if (InventoryUtils.isInventoryOpen && this.intave.isEnabled()) {
            event.forward = false;
            event.backward = false;
            event.left = false;
            event.right = false;
            event.jump = false;
            event.sneak = false;
         }
      } else {
         InventoryUtils.closeInv(this.mode.getMode());
      }
   }

   @Override
   public void onDisable() {
      InventoryUtils.closeInv(this.mode.getMode());
   }
}
