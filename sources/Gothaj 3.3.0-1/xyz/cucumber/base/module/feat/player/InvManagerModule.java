package xyz.cucumber.base.module.feat.player;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
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
   description = "Automatically sorts things in your inv",
   name = "Inv Manager"
)
public class InvManagerModule extends Mod {
   public Timer startTimer = new Timer();
   public Timer timer = new Timer();
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Open Inv", "Spoof"});
   public BooleanSettings intave = new BooleanSettings("Intave", () -> this.mode.getMode().equalsIgnoreCase("Spoof"), true);
   public BooleanSettings throwGarbage = new BooleanSettings("Throw Garbage", true);
   public NumberSettings startDelay = new NumberSettings("Start Delay", 150.0, 0.0, 1000.0, 1.0);
   public NumberSettings speed = new NumberSettings("Speed", 150.0, 0.0, 1000.0, 1.0);
   public BooleanSettings sword = new BooleanSettings("Sword", true);
   public NumberSettings swordSlot = new NumberSettings("sword Slot", 1.0, 1.0, 9.0, 1.0);
   public BooleanSettings axe = new BooleanSettings("Axe", true);
   public NumberSettings axeSlot = new NumberSettings("Axe Slot", 2.0, 1.0, 9.0, 1.0);
   public BooleanSettings pickaxe = new BooleanSettings("Pickaxe", true);
   public NumberSettings pickaxeSlot = new NumberSettings("Pickaxe", 3.0, 1.0, 9.0, 1.0);
   public BooleanSettings shovel = new BooleanSettings("Shovel", false);
   public NumberSettings shovelSlot = new NumberSettings("Shovel Slot", 4.0, 1.0, 9.0, 1.0);
   public BooleanSettings bow = new BooleanSettings("Bow", false);
   public NumberSettings bowSlot = new NumberSettings("Bow Slot", 5.0, 1.0, 9.0, 1.0);
   public BooleanSettings blocks = new BooleanSettings("Blocks", true);
   public NumberSettings blockSlot = new NumberSettings("Block Slot", 6.0, 1.0, 9.0, 1.0);
   public BooleanSettings projectiles = new BooleanSettings("Projectiles", true);
   public NumberSettings projectileSlot = new NumberSettings("Projectile Slot", 7.0, 1.0, 9.0, 1.0);
   public BooleanSettings waterBucket = new BooleanSettings("Water Bucket", true);
   public NumberSettings waterBucketSlot = new NumberSettings("Water Bucket Slot", 8.0, 1.0, 9.0, 1.0);
   public KeyBinding[] moveKeys = new KeyBinding[]{
      this.mc.gameSettings.keyBindForward,
      this.mc.gameSettings.keyBindBack,
      this.mc.gameSettings.keyBindLeft,
      this.mc.gameSettings.keyBindRight,
      this.mc.gameSettings.keyBindJump,
      this.mc.gameSettings.keyBindSneak
   };

   public InvManagerModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.mode,
            this.intave,
            this.throwGarbage,
            this.startDelay,
            this.speed,
            this.sword,
            this.swordSlot,
            this.axe,
            this.axeSlot,
            this.pickaxe,
            this.pickaxeSlot,
            this.shovel,
            this.shovelSlot,
            this.bow,
            this.bowSlot,
            this.blocks,
            this.blockSlot,
            this.projectiles,
            this.projectileSlot,
            this.waterBucket,
            this.waterBucketSlot
         }
      );
   }

   @Override
   public void onDisable() {
      InventoryUtils.closeInv(this.mode.getMode());
   }

   @EventListener
   public void onMoveButton(EventMoveButton e) {
      this.setInfo(this.mode.getMode());
      if (this.mode.getMode().equalsIgnoreCase("Open Inv")) {
         if (this.mc.currentScreen == null) {
            this.startTimer.reset();
         }

         if (!this.startTimer.hasTimeElapsed(this.startDelay.getValue(), false)) {
            return;
         }
      }

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
         if (!this.mode.getMode().equalsIgnoreCase("Open Inv") || this.mc.currentScreen instanceof GuiInventory) {
            int i = 9;

            while (true) {
               label283: {
                  label188:
                  if (i < 45) {
                     if (!this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                        break label283;
                     }

                     ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                     if (!InventoryUtils.timer.hasTimeElapsed(this.speed.value, false)) {
                        break label283;
                     }

                     if (this.swordSlot.value != 0.0
                        && is.getItem() instanceof ItemSword
                        && is == InventoryUtils.bestSword()
                        && this.mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtils.bestSword())
                        && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.swordSlot.value)).getStack() != is
                        && this.sword.isEnabled()) {
                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc
                           .playerController
                           .windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, (int)(this.swordSlot.value - 1.0), 2, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     } else if (this.bowSlot.value != 0.0
                        && is.getItem() instanceof ItemBow
                        && is == InventoryUtils.bestBow()
                        && this.mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtils.bestBow())
                        && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.bowSlot.value)).getStack() != is
                        && this.bow.isEnabled()) {
                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc
                           .playerController
                           .windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, (int)(this.bowSlot.value - 1.0), 2, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     } else if (this.pickaxeSlot.value != 0.0
                        && is.getItem() instanceof ItemPickaxe
                        && is == InventoryUtils.bestPick()
                        && is != InventoryUtils.bestWeapon()
                        && this.mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtils.bestPick())
                        && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.pickaxeSlot.value)).getStack() != is
                        && this.pickaxe.isEnabled()) {
                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc
                           .playerController
                           .windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, (int)(this.pickaxeSlot.value - 1.0), 2, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     } else if (this.axeSlot.value != 0.0
                        && is.getItem() instanceof ItemAxe
                        && is == InventoryUtils.bestAxe()
                        && is != InventoryUtils.bestWeapon()
                        && this.mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtils.bestAxe())
                        && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.axeSlot.value)).getStack() != is
                        && this.axe.isEnabled()) {
                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc
                           .playerController
                           .windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, (int)(this.axeSlot.value - 1.0), 2, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     } else if (this.shovelSlot.value != 0.0
                        && is.getItem() instanceof ItemSpade
                        && is == InventoryUtils.bestShovel()
                        && is != InventoryUtils.bestWeapon()
                        && this.mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtils.bestShovel())
                        && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.shovelSlot.value)).getStack() != is
                        && this.shovel.isEnabled()) {
                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc
                           .playerController
                           .windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, (int)(this.shovelSlot.value - 1.0), 2, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     } else if (this.blockSlot.value != 0.0
                        && is.getItem() instanceof ItemBlock
                        && is == InventoryUtils.getBlockSlotInventory()
                        && this.mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtils.getBlockSlotInventory())
                        && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.blockSlot.value)).getStack() != is
                        && this.blocks.isEnabled()) {
                        if (this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.blockSlot.value)).getStack() != null
                           && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.blockSlot.value)).getStack().getItem() instanceof ItemBlock
                           && !InventoryUtils.invalidBlocks
                              .contains(
                                 ((ItemBlock)this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.blockSlot.value)).getStack().getItem()).getBlock()
                              )) {
                           return;
                        }

                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc
                           .playerController
                           .windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, (int)(this.blockSlot.value - 1.0), 2, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     } else if (this.projectileSlot.value != 0.0
                        && is == InventoryUtils.getProjectileSlotInventory()
                        && this.mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtils.getProjectileSlotInventory())
                        && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.projectileSlot.value)).getStack() != is
                        && this.projectiles.isEnabled()) {
                        if (this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.projectileSlot.value)).getStack() != null
                           && (
                              this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.projectileSlot.value)).getStack().getItem() instanceof ItemSnowball
                                 || this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.projectileSlot.value)).getStack().getItem() instanceof ItemEgg
                                 || this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.projectileSlot.value)).getStack().getItem() instanceof ItemFishingRod
                           )) {
                           return;
                        }

                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc
                           .playerController
                           .windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, (int)(this.projectileSlot.value - 1.0), 2, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     } else if (this.waterBucketSlot.value != 0.0
                        && is.getItem() == Items.water_bucket
                        && is == InventoryUtils.getBucketSlotInventory()
                        && this.mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtils.getBucketSlotInventory())
                        && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.shovelSlot.value)).getStack() != is
                        && this.waterBucket.isEnabled()) {
                        if (this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.waterBucketSlot.value)).getStack() != null
                           && this.mc.thePlayer.inventoryContainer.getSlot((int)(35.0 + this.waterBucketSlot.value)).getStack().getItem() == Items.water_bucket
                           )
                         {
                           return;
                        }

                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc
                           .playerController
                           .windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, (int)(this.waterBucketSlot.value - 1.0), 2, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     } else if (InventoryUtils.isBadStack(is, true, true) && this.throwGarbage.isEnabled()) {
                        InventoryUtils.openInv(this.mode.getMode());
                        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, i, 1, 4, this.mc.thePlayer);
                        if (!this.intave.isEnabled()) {
                           InventoryUtils.closeInv(this.mode.getMode());
                        }

                        InventoryUtils.timer.reset();
                        if (this.speed.value != 0.0) {
                           break label188;
                        }
                     }

                     if (InventoryUtils.timer.hasTimeElapsed(55.0, false)) {
                        InventoryUtils.closeInv(this.mode.getMode());
                     }
                     break label283;
                  }

                  if (InventoryUtils.isInventoryOpen && this.intave.isEnabled()) {
                     e.forward = false;
                     e.backward = false;
                     e.left = false;
                     e.right = false;
                     e.jump = false;
                     e.sneak = false;
                  }

                  return;
               }

               i++;
            }
         }
      } else {
         InventoryUtils.closeInv(this.mode.getMode());
      }
   }
}
