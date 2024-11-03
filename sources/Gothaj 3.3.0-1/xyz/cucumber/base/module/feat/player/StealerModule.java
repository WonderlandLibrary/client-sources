package xyz.cucumber.base.module.feat.player;

import java.util.ArrayList;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.S30PacketWindowItems;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.Display;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventUpdate;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.InventoryUtils;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Automatically steals items from chest",
   name = "Stealer"
)
public class StealerModule extends Mod {
   private final Timer timer = new Timer();
   private final Timer startTimer = new Timer();
   private final NumberSettings startDelay = new NumberSettings("Start Delay", 50.0, 0.0, 1000.0, 25.0);
   private final NumberSettings minDelay = new NumberSettings("Min Delay", 5.0, 0.0, 1000.0, 25.0);
   private final NumberSettings maxDelay = new NumberSettings("Max Delay", 5.0, 0.0, 1000.0, 25.0);
   private final BooleanSettings stealTrashItems = new BooleanSettings("Steal trash items", false);
   private final BooleanSettings autoClose = new BooleanSettings("Auto Close", true);
   private final BooleanSettings chestName = new BooleanSettings("Check chest name", false);
   private final BooleanSettings disableOnWorldChange = new BooleanSettings("Disable on World Change", false);
   private int decidedTimer = 0;
   public static boolean closeAfterContainer;
   private boolean gotItems;
   private int ticksInChest;
   private boolean lastInChest;

   public StealerModule() {
      this.addSettings(
         new ModuleSettings[]{this.startDelay, this.minDelay, this.maxDelay, this.stealTrashItems, this.autoClose, this.chestName, this.disableOnWorldChange}
      );
   }

   @Override
   public void onDisable() {
      closeAfterContainer = false;
      this.gotItems = false;
   }

   @EventListener
   public void onReceivePacket(EventReceivePacket e) {
      if (this.mc.thePlayer.ticksExisted > 60) {
         if (e.getPacket() instanceof S30PacketWindowItems) {
            this.gotItems = true;
         }
      }
   }

   @EventListener
   public void onMotion(EventMotion e) {
      if (e.getType() == EventType.PRE) {
         if (this.mc.thePlayer.ticksExisted > 60) {
            if (this.mc.currentScreen instanceof GuiChest
               && Display.isVisible()
               && (!this.chestName.isEnabled() || ((GuiChest)this.mc.currentScreen).lowerChestInventory.getDisplayName().getUnformattedText().contains("chest"))
               )
             {
               this.mc.mouseHelper.mouseXYChange();
               this.mc.mouseHelper.ungrabMouseCursor();
               this.mc.mouseHelper.grabMouseCursor();
            }

            if (this.mc.currentScreen instanceof GuiChest) {
               this.ticksInChest++;
               if (this.ticksInChest * 50 > 255) {
                  this.ticksInChest = 10;
               }
            } else {
               this.ticksInChest--;
               this.gotItems = false;
               if (this.ticksInChest < 0) {
                  this.ticksInChest = 0;
               }
            }
         }
      }
   }

   @EventListener
   public void onRenderGui(EventUpdate e) {
      if (this.mc.thePlayer.ticksExisted > 60) {
         if (!this.lastInChest) {
            this.startTimer.reset();
         }

         this.lastInChest = this.mc.currentScreen instanceof GuiChest;
         if (this.mc.currentScreen instanceof GuiChest) {
            if (this.chestName.isEnabled()) {
               String name = ((GuiChest)this.mc.currentScreen).lowerChestInventory.getDisplayName().getUnformattedText();
               if (!name.toLowerCase().contains("chest")) {
                  return;
               }
            }

            if (!this.startTimer.hasTimeElapsed(this.startDelay.getValue(), false)) {
               return;
            }

            if (this.decidedTimer == 0) {
               int delayFirst = (int)Math.floor(Math.min(this.minDelay.value, this.maxDelay.value));
               int delaySecond = (int)Math.ceil(Math.max(this.minDelay.value, this.maxDelay.value));
               this.decidedTimer = RandomUtils.nextInt(delayFirst, delaySecond);
            }

            if (this.timer.hasTimeElapsed((double)this.decidedTimer, false)) {
               ContainerChest chest = (ContainerChest)this.mc.thePlayer.openContainer;

               for (int i = 0; i < chest.inventorySlots.size(); i++) {
                  ItemStack stack = chest.getLowerChestInventory().getStackInSlot(i);
                  if (stack != null && this.itemWhitelisted(stack) && !this.stealTrashItems.isEnabled()) {
                     this.mc.playerController.windowClick(chest.windowId, i, 0, 1, this.mc.thePlayer);
                     this.timer.reset();
                     int delayFirst = (int)Math.floor(Math.min(this.minDelay.value, this.maxDelay.value));
                     int delaySecond = (int)Math.ceil(Math.max(this.minDelay.value, this.maxDelay.value));
                     this.decidedTimer = RandomUtils.nextInt(delayFirst, delaySecond);
                     this.gotItems = true;
                     return;
                  }
               }

               if (this.gotItems && this.autoClose.isEnabled() && this.ticksInChest > 3) {
                  this.mc.thePlayer.closeScreen();
               }
            }
         }
      }
   }

   @EventListener
   public void onWorld(EventWorldChange e) {
      if (this.disableOnWorldChange.isEnabled()) {
         this.toggle();
      }
   }

   private boolean itemWhitelisted(ItemStack itemStack) {
      if (InventoryUtils.isBadStackStealer(itemStack, true, true)) {
         return false;
      } else {
         ArrayList<Item> whitelistedItems = new ArrayList<Item>() {
            {
               this.add(Items.ender_pearl);
               this.add(Items.iron_ingot);
               this.add(Items.snowball);
               this.add(Items.gold_ingot);
               this.add(Items.redstone);
               this.add(Items.diamond);
               this.add(Items.emerald);
               this.add(Items.quartz);
               this.add(Items.bow);
               this.add(Items.arrow);
               this.add(Items.fishing_rod);
               this.add(Items.egg);
               this.add(Items.water_bucket);
               this.add(Items.lava_bucket);
            }
         };
         Item item = itemStack.getItem();
         String itemName = itemStack.getDisplayName();
         if (!itemName.contains("Right Click") && !itemName.contains("Click to Use") && !itemName.contains("Players Finder")) {
            ArrayList<Integer> whitelistedPotions = new ArrayList<Integer>() {
               {
                  this.add(Integer.valueOf(6));
                  this.add(Integer.valueOf(1));
                  this.add(Integer.valueOf(5));
                  this.add(Integer.valueOf(8));
                  this.add(Integer.valueOf(14));
                  this.add(Integer.valueOf(12));
                  this.add(Integer.valueOf(10));
                  this.add(Integer.valueOf(16));
               }
            };
            if (item instanceof ItemPotion) {
               int potionID = this.getPotionId(itemStack);
               return whitelistedPotions.contains(potionID);
            } else {
               return item instanceof ItemBlock
                     && !(((ItemBlock)item).getBlock() instanceof BlockTNT)
                     && !(((ItemBlock)item).getBlock() instanceof BlockSlime)
                     && !(((ItemBlock)item).getBlock() instanceof BlockFalling)
                  || item instanceof ItemAnvilBlock
                  || item instanceof ItemSword
                  || item instanceof ItemArmor
                  || item instanceof ItemTool
                  || item instanceof ItemFood
                  || item instanceof ItemSkull
                  || itemName.contains("§")
                  || whitelistedItems.contains(item) && !item.equals(Items.spider_eye);
            }
         } else {
            return true;
         }
      }
   }

   private int getPotionId(ItemStack potion) {
      Item item = potion.getItem();

      try {
         if (item instanceof ItemPotion) {
            ItemPotion p = (ItemPotion)item;
            return p.getEffects(potion.getMetadata()).get(0).getPotionID();
         }
      } catch (NullPointerException var4) {
      }

      return 0;
   }
}
