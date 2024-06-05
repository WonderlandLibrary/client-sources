package cc.novoline.modules.player;

import cc.novoline.Novoline;
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
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.player.AutoArmor;
import cc.novoline.modules.player.ChestStealer;
import cc.novoline.modules.player.Freecam;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.aH9;
import net.aHD;
import net.aHM;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public final class InvManager extends AbstractModule {
   private final Timer timer = new Timer();
   private List x = new CopyOnWriteArrayList();
   @Property("inventory-cleaner")
   private final BooleanProperty inventoryCleaner;
   @Property("open-inventory")
   private final BooleanProperty open_inv;
   @Property("no-move")
   private final BooleanProperty H;
   @Property("delay")
   private final IntProperty delay;
   @Property("arrows")
   private final IntProperty arrows;
   @Property("blocks")
   private final IntProperty blocks;
   @Property("pickaxe-slot")
   private final IntProperty pickAxeSlot;
   @Property("axe-slot")
   private final IntProperty axeSlot;
   @Property("shovel-slot")
   private final IntProperty shovelSlot;
   @Property("weapon-slot")
   private final IntProperty swordSlot;
   @Property("bow-slot")
   private final IntProperty bowSlot;
   @Property("head-slot")
   private final IntProperty headSlot;
   @Property("gapple-slot")
   private final IntProperty gappleSlot;
   @Property("autodisable")
   private final ListProperty autoDisable;
   @Property("keep-items")
   private final ListProperty items;
   @Property("filters")
   private final ListProperty materials;
   @Property("sort-mode")
   private final StringProperty P;

   public InvManager(ModuleManager var1) {
      super(var1, "InventoryManager", "Inventory Manager", EnumModuleType.PLAYER, "Manages inventory");
      Freecam.a();
      this.inventoryCleaner = PropertyFactory.booleanTrue();
      this.open_inv = PropertyFactory.booleanFalse();
      this.H = PropertyFactory.booleanFalse();
      this.delay = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(1)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(10));
      this.arrows = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(128)).minimum(Integer.valueOf(64))).maximum(Integer.valueOf(512));
      this.blocks = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(128)).minimum(Integer.valueOf(0))).maximum(Integer.valueOf(512));
      this.pickAxeSlot = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(7)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(9));
      this.axeSlot = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(8)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(9));
      this.shovelSlot = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(9)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(9));
      this.swordSlot = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(1)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(9));
      this.bowSlot = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(2)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(9));
      this.headSlot = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(3)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(9));
      this.gappleSlot = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(4)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(9));
      this.autoDisable = PropertyFactory.createList((Object)"Death").acceptableValues((Object[])(new String[]{"World Change", "Game End", "Death"}));
      this.items = PropertyFactory.createList("Sword", "Bow").acceptableValues((Object[])(new String[]{"Sword", "Bow", "PickAxe", "Axe", "Shovel", "Golden Apple", "Head", "Buckets"}));
      this.materials = PropertyFactory.createList((Object)"Sticks").acceptableValues((Object[])(new String[]{"Ores", "Sticks"}));
      this.P = PropertyFactory.createString("Default").acceptableValues(new String[]{"Custom", "Default"});
      Manager.put(new Setting("IM_SORT_MODE", "Sort Mode", SettingType.COMBOBOX, this, this.P));
      Manager.put(new Setting("IM_CLEANER", "Cleaner", SettingType.CHECKBOX, this, this.inventoryCleaner));
      Manager.put(new Setting("IM_OPEN_INV", "Open Inventory", SettingType.CHECKBOX, this, this.open_inv));
      Manager.put(new Setting("IM_NO_MOVE", "Not Moving", SettingType.CHECKBOX, this, this.H));
      Manager.put(new Setting("IM_DELAY", "Delay", SettingType.SLIDER, this, this.delay, 1.0D));
      SettingType var10004 = SettingType.SLIDER;
      IntProperty var10006 = this.blocks;
      BooleanProperty var10008 = this.inventoryCleaner;
      this.inventoryCleaner.getClass();
      Manager.put(new Setting("IM_BLOCKS", "Blocks", var10004, this, var10006, 64.0D, var10008::get));
      Manager.put(new Setting("IM_ARROWS", "Arrows", SettingType.SLIDER, this, this.arrows, 64.0D, this::lambda$new$0));
      Manager.put(new Setting("IM_KEEP_ITEMS", "Keep Items", SettingType.SELECTBOX, this, this.items));
      Manager.put(new Setting("IM_MATERIALS", "Keep Materials", SettingType.SELECTBOX, this, this.materials));
      Manager.put(new Setting("IM_SLOT_CHANGE", "Preferred slots", SettingType.GUI, this, new aHD()));
      Manager.put(new Setting("IM_WHITELIST", "Item management", SettingType.GUI, this, new aH9(), this::lambda$new$1));
      Manager.put(new Setting("IM_AUTO_DISABLE", "Disable On", SettingType.SELECTBOX, this, this.autoDisable));
   }

   private boolean isBestSword(ItemStack var1) {
      float var3 = this.getDamage(var1);
      Freecam.a();
      int var4 = 9;
      if(var4 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var4).getHasStack()) {
            ItemStack var5 = this.mc.player.getSlotFromPlayerContainer(var4).getStack();
            if(this.getDamage(var5) > var3 && var5.getItem() instanceof ItemSword) {
               return false;
            }
         }

         ++var4;
      }

      return var1.getItem() instanceof ItemSword;
   }

   private boolean isHead(ItemStack var1) {
      int[] var2 = Freecam.a();
      return var1.getItem() instanceof ItemSkull && var1.getDisplayName().contains("Head") && !var1.getDisplayName().equalsIgnoreCase("Wither Skeleton Skull") && !var1.getDisplayName().equalsIgnoreCase("Zombie Head") && !var1.getDisplayName().equalsIgnoreCase("Creeper Head") && !var1.getDisplayName().equalsIgnoreCase("Skeleton Skull");
   }

   private boolean isGoldenApple(ItemStack var1) {
      return var1.getItem() instanceof ItemAppleGold;
   }

   @EventTarget
   public void onPreUpdate(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState().equals(EventState.PRE)) {
         if(!(this.mc.currentScreen instanceof GuiInventory) && ((Boolean)this.open_inv.get()).booleanValue() || ServerUtils.serverIs(Servers.PRE) || ServerUtils.serverIs(Servers.LOBBY) || this.isEnabled(AutoArmor.class) && ((AutoArmor)this.getModule(AutoArmor.class)).isWorking() || this.mc.player.getLastTickDistance() > 0.05D && ((Boolean)this.H.get()).booleanValue() || ((ChestStealer)this.getModule(ChestStealer.class)).isStealing()) {
            return;
         }

         if(this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen instanceof aHM) {
            int var3 = ((Integer)this.swordSlot.get()).intValue() - 1;
            int var4 = ((Integer)this.pickAxeSlot.get()).intValue() - 1;
            int var5 = ((Integer)this.bowSlot.get()).intValue() - 1;
            int var6 = ((Integer)this.shovelSlot.get()).intValue() - 1;
            int var7 = ((Integer)this.axeSlot.get()).intValue() - 1;
            int var8 = ((Integer)this.headSlot.get()).intValue() - 1;
            int var9 = ((Integer)this.gappleSlot.get()).intValue() - 1;
            boolean var10 = this.items.contains("PickAxe");
            boolean var11 = this.items.contains("Shovel");
            boolean var12 = this.items.contains("Axe");
            boolean var13 = this.items.contains("Sword");
            boolean var14 = this.items.contains("Bow");
            boolean var15 = this.items.contains("Head");
            boolean var16 = this.items.contains("Golden Apple");
            int var17 = ((Integer)this.delay.get()).intValue() * 50;
            int var18 = 9;
            if(var18 < 45) {
               ItemStack var19 = this.mc.player.getSlotFromPlayerContainer(var18).getStack();
               if(var19 != null && !var19.getDisplayName().contains("§7(Right Click)") && this.timer.check((float)var17)) {
                  if(this.isBestSword(var19) && var13 && this.shouldSwap(var3)[0]) {
                     this.mc.player.swap(var18, var3);
                     this.timer.reset();
                  }

                  if(this.isBestPickaxe(var19) && var10 && this.shouldSwap(var4)[2]) {
                     this.mc.player.swap(var18, var4);
                     this.timer.reset();
                  }

                  if(this.isBestAxe(var19) && var12 && this.shouldSwap(var7)[1]) {
                     this.mc.player.swap(var18, var7);
                     this.timer.reset();
                  }

                  if(this.isBestBow(var19) && var14 && this.shouldSwap(var5)[5]) {
                     this.mc.player.swap(var18, var5);
                     this.timer.reset();
                  }

                  if(this.isHead(var19) && var15 && this.shouldSwap(var8)[4]) {
                     this.mc.player.swap(var18, var8);
                     this.timer.reset();
                  }

                  if(this.isBestShovel(var19) && var11 && this.shouldSwap(var6)[3]) {
                     this.mc.player.swap(var18, var6);
                     this.timer.reset();
                  }

                  if(this.isGoldenApple(var19) && var16 && this.shouldSwap(var9)[6]) {
                     this.mc.player.swap(var18, var9);
                     this.timer.reset();
                  }
               }

               ++var18;
            }

            var18 = 9;
            if(var18 < 45) {
               if(!this.mc.player.getSlotFromPlayerContainer(var18).getHasStack()) {
                  ;
               }

               ItemStack var23 = this.mc.player.getSlotFromPlayerContainer(var18).getStack();
               if(var23 != null && this.shouldDrop(var23) && ((Boolean)this.inventoryCleaner.get()).booleanValue() && this.timer.delay((double)var17)) {
                  this.mc.player.drop(var18);
                  this.timer.reset();
               }

               ++var18;
            }
         }
      }

   }

   private boolean[] shouldSwap(int var1) {
      int[] var2 = Freecam.a();
      return new boolean[]{!this.mc.player.getSlotFromPlayerContainer(var1 + 36).getHasStack() || !this.isBestSword(this.mc.player.getSlotFromPlayerContainer(var1 + 36).getStack()), !this.mc.player.getSlotFromPlayerContainer(var1 + 36).getHasStack() || !this.isBestAxe(this.mc.player.getSlotFromPlayerContainer(var1 + 36).getStack()), !this.mc.player.getSlotFromPlayerContainer(var1 + 36).getHasStack() || !this.isBestPickaxe(this.mc.player.getSlotFromPlayerContainer(var1 + 36).getStack()), !this.mc.player.getSlotFromPlayerContainer(var1 + 36).getHasStack() || !this.isBestShovel(this.mc.player.getSlotFromPlayerContainer(var1 + 36).getStack()), !this.mc.player.getSlotFromPlayerContainer(var1 + 36).getHasStack() || !this.isHead(this.mc.player.getSlotFromPlayerContainer(var1 + 36).getStack()), !this.mc.player.getSlotFromPlayerContainer(var1 + 36).getHasStack() || !this.isBestBow(this.mc.player.getSlotFromPlayerContainer(var1 + 36).getStack()), !this.mc.player.getSlotFromPlayerContainer(var1 + 36).getHasStack() || !this.isGoldenApple(this.mc.player.getSlotFromPlayerContainer(var1 + 36).getStack())};
   }

   private float getDamage(ItemStack var1) {
      Freecam.a();
      float var3 = 0.0F;
      Item var4 = var1.getItem();
      if(var4 instanceof ItemTool) {
         var3 += ((ItemTool)var4).getDamage();
      }

      if(var4 instanceof ItemSword) {
         var3 += ((ItemSword)var4).getAttackDamage();
      }

      var3 = var3 + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, var1) * 1.25F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, var1) * 0.3F;
      return var3;
   }

   private int getBlocksCounter() {
      Freecam.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            Item var5 = var4.getItem();
            if(var4.getItem() instanceof ItemBlock && !((Scaffold)this.getModule(Scaffold.class)).getBlacklistedBlocks().contains(((ItemBlock)var5).getBlock())) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getArrowsCounter() {
      Freecam.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() == Items.arrow) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getIronIngotsCounter() {
      int var2 = 0;
      Freecam.a();
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() == Items.iron_ingot) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getCoalCounter() {
      Freecam.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() == Items.coal) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getSwordsCounter() {
      int var2 = 0;
      Freecam.a();
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() instanceof ItemSword && this.isBestSword(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getBowsCounter() {
      Freecam.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() instanceof ItemBow && this.isBestBow(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getPickaxexCounter() {
      int var2 = 0;
      Freecam.a();
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() instanceof ItemPickaxe && this.isBestPickaxe(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getAxesCounter() {
      Freecam.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() instanceof ItemAxe && this.isBestAxe(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getHeadsCounter() {
      Freecam.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() instanceof ItemSkull && this.isBestShovel(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int getShovelsCounter() {
      Freecam.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.mc.player.getSlotFromPlayerContainer(var3).getHasStack()) {
            ItemStack var4 = this.mc.player.getSlotFromPlayerContainer(var3).getStack();
            if(var4.getItem() instanceof ItemSpade && this.isBestShovel(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private boolean isBestPickaxe(ItemStack var1) {
      Freecam.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemPickaxe)) {
         return false;
      } else {
         float var4 = this.getToolEffect(var1);
         int var5 = 9;
         if(var5 < 45) {
            if(this.mc.player.getSlotFromPlayerContainer(var5).getHasStack()) {
               ItemStack var6 = this.mc.player.getSlotFromPlayerContainer(var5).getStack();
               if(this.getToolEffect(var6) > var4 && var6.getItem() instanceof ItemPickaxe) {
                  return false;
               }
            }

            ++var5;
         }

         return true;
      }
   }

   private boolean shouldDrop(ItemStack var1) {
      Item var3 = var1.getItem();
      Freecam.a();
      String var4 = var1.getDisplayName();
      int var5 = Item.b(var3);
      if(var5 != 58 && !var4.toLowerCase().contains(EnumChatFormatting.OBFUSCATED + "||") && !var4.contains(new Color(0, 255, 0, 255) + "Game Menu " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "Spectator Settings " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "Play Again " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "Teleporter " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "SkyWars Challenges " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Collectibles " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Kit Selector " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Kill Effect Selector " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.WHITE + "Players: " + EnumChatFormatting.RED + "Hidden " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Shop " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.WHITE + "Players: " + EnumChatFormatting.RED + "Visible " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GOLD + "Excalibur") && !var4.equalsIgnoreCase("aDragon Sword") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Cornucopia") && !var4.equalsIgnoreCase(EnumChatFormatting.RED + "Bloodlust") && !var4.equalsIgnoreCase(EnumChatFormatting.RED + "Artemis\' Bow") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Miner\'s Blessing") && !var4.equalsIgnoreCase(EnumChatFormatting.GOLD + "Axe of Perun") && !var4.equalsIgnoreCase(EnumChatFormatting.GOLD + "Cornucopia") && var5 != 116 && var5 != 145 && (var5 != 15 && var5 != 14 || !this.materials.contains("Ores")) && !var4.equalsIgnoreCase("§aAndúril") && var5 != 259 && var5 != 46) {
         boolean var6 = this.items.contains("PickAxe");
         boolean var7 = this.items.contains("Shovel");
         boolean var8 = this.items.contains("Axe");
         boolean var9 = this.items.contains("Sword");
         boolean var10 = this.items.contains("Bow");
         boolean var11 = this.items.contains("Head");
         int var12 = ((Integer)this.swordSlot.get()).intValue() - 1;
         int var13 = ((Integer)this.pickAxeSlot.get()).intValue() - 1;
         int var14 = ((Integer)this.bowSlot.get()).intValue() - 1;
         int var15 = ((Integer)this.shovelSlot.get()).intValue() - 1;
         int var16 = ((Integer)this.axeSlot.get()).intValue() - 1;
         int var17 = ((Integer)this.headSlot.get()).intValue() - 1;
         if(((!this.isBestShovel(var1) || this.getShovelsCounter() >= 2) && (!(var1.getItem() instanceof ItemSpade) || var1 != this.mc.player.inventory.getStackInSlot(var15)) || !var7) && ((!this.isBestBow(var1) || this.getBowsCounter() >= 2) && (!(var1.getItem() instanceof ItemBow) || var1 != this.mc.player.inventory.getStackInSlot(var14)) || !var10) && ((!this.isHead(var1) || this.getHeadsCounter() >= 2) && (!(var1.getItem() instanceof ItemSkull) || var1 != this.mc.player.inventory.getStackInSlot(var17)) || !var11) && ((!this.isBestAxe(var1) || this.getAxesCounter() >= 2) && (!(var1.getItem() instanceof ItemAxe) || var1 != this.mc.player.inventory.getStackInSlot(var16)) || !var8) && ((!this.isBestPickaxe(var1) || this.getPickaxexCounter() >= 2) && (!(var1.getItem() instanceof ItemPickaxe) || var1 != this.mc.player.inventory.getStackInSlot(var13)) || !var6) && ((!this.isBestSword(var1) || this.getSwordsCounter() >= 2) && (!(var1.getItem() instanceof ItemSword) || var1 != this.mc.player.inventory.getStackInSlot(var12)) || !var9)) {
            if(var3 instanceof ItemArmor) {
               AutoArmor var18 = (AutoArmor)this.getModule(AutoArmor.class);
               int var19 = 1;
               if(var19 < 5) {
                  label1491: {
                     if(this.mc.player.getSlotFromPlayerContainer(4 + var19).getHasStack()) {
                        ItemStack var20 = this.mc.player.getSlotFromPlayerContainer(4 + var19).getStack();
                        if(var18.isBestArmor(var20, var19)) {
                           break label1491;
                        }
                     }

                     if(var18.isBestArmor(var1, var19)) {
                        return false;
                     }
                  }

                  ++var19;
               }
            }

            if((!(var3 instanceof ItemBlock) || this.getBlocksCounter() <= ((Integer)this.blocks.get()).intValue() && !((Scaffold)this.getModule(Scaffold.class)).getBlacklistedBlocks().contains(((ItemBlock)var3).getBlock())) && (!(var3 instanceof ItemPotion) || !this.isBadPotion(var1)) && (!(var3 instanceof ItemFood) || var3 instanceof ItemAppleGold || var3 == Items.bread || var3 == Items.pumpkin_pie || var3 == Items.baked_potato || var3 == Items.cooked_chicken || var3 == Items.carrot || var3 == Items.apple || var3 == Items.beef || var3 == Items.cooked_beef || var3 == Items.porkchop || var3 == Items.cooked_porkchop || var3 == Items.mushroom_stew || var3 == Items.cooked_fish || var3 == Items.melon) && !(var3 instanceof ItemHoe) && !(var3 instanceof ItemTool) && !(var3 instanceof ItemSword) && !(var3 instanceof ItemArmor)) {
               String var21 = var3.getUnlocalizedName();
               return !this.P.equals("Default")?!this.x.contains(Integer.valueOf(var5)):!this.materials.contains("Sticks") && var21.contains("stick") || var21.contains("egg") || this.getIronIngotsCounter() > 64 && var3 == Items.iron_ingot || this.getCoalCounter() > 64 && var3 == Items.coal || var21.contains("string") || var21.contains("flint") || var21.contains("compass") || var21.contains("dyePowder") || var21.contains("feather") || var21.contains("chest") && !var4.toLowerCase().contains("collect") || var21.contains("snow") || var21.contains("torch") || var21.contains("seeds") || var21.contains("leather") || var21.contains("reeds") || var21.contains("record") || var21.contains("snowball") || var3 instanceof ItemGlassBottle || var3 instanceof ItemSlab || var5 == 113 || var5 == 106 || var5 == 325 || var5 == 326 && !this.items.contains("Buckets") || var5 == 327 || var5 == 111 || var5 == 85 || var5 == 188 || var5 == 189 || var5 == 190 || var5 == 191 || var5 == 401 || var5 == 192 || var5 == 81 || var5 == 32 || var21.contains("gravel") || var21.contains("flower") || var21.contains("tallgrass") || var3 instanceof ItemBow || var3 == Items.arrow && this.getArrowsCounter() > (this.items.contains("Bow")?((Integer)this.arrows.get()).intValue():0) || var5 == 175 || var5 == 340 || var5 == 339 || var5 == 160 || var5 == 101 || var5 == 102 || var5 == 321 || var5 == 323 || var5 == 389 || var5 == 416 || var5 == 171 || var5 == 139 || var5 == 23 || var5 == 25 || var5 == 69 || var5 == 70 || var5 == 72 || var5 == 77 || var5 == 96 || var5 == 107 || var5 == 123 || var5 == 131 || var5 == 143 || var5 == 147 || var5 == 148 || var5 == 151 || var5 == 152 || var5 == 154 || var5 == 158 || var5 == 167 || var5 == 403 || var5 == 183 || var5 == 184 || var5 == 185 || var5 == 186 || var5 == 187 || var5 == 331 || var5 == 356 || var5 == 404 || var5 == 27 || var5 == 28 || var5 == 66 || var5 == 76 || var5 == 157 || var5 == 328 || var5 == 342 || var5 == 343 || var5 == 398 || var5 == 407 || var5 == 408 || var5 == 138 || var5 == 352 || var5 == 385 || var5 == 386 || var5 == 395 || var5 == 402 || var5 == 418 || var5 == 419 || var5 == 281 || var5 == 289 || var5 == 337 || var5 == 336 || var5 == 348 || var5 == 353 || var5 == 369 || var5 == 372 || var5 == 405 || var5 == 406 || var5 == 409 || var5 == 410 || var5 == 415 || var5 == 370 || var5 == 376 || var5 == 377 || var5 == 378 || var5 == 379 || var5 == 380 || var5 == 382 || var5 == 414 || var5 == 346 || var5 == 347 || var5 == 420 || var5 == 397 || var5 == 421 || var5 == 341 || var21.contains("sapling") || var21.contains("stairs") || var21.contains("door") || var21.contains("monster_egg") || var21.contains("sand") || var21.contains("piston");
            } else {
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isBestShovel(ItemStack var1) {
      Freecam.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemSpade)) {
         return false;
      } else {
         float var4 = this.getToolEffect(var1);
         int var5 = 9;
         if(var5 < 45) {
            if(this.mc.player.getSlotFromPlayerContainer(var5).getHasStack()) {
               ItemStack var6 = this.mc.player.getSlotFromPlayerContainer(var5).getStack();
               if(this.getToolEffect(var6) > var4 && var6.getItem() instanceof ItemSpade) {
                  return false;
               }
            }

            ++var5;
         }

         return true;
      }
   }

   private boolean isBestAxe(ItemStack var1) {
      Freecam.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemAxe)) {
         return false;
      } else {
         float var4 = this.getToolEffect(var1);
         int var5 = 9;
         if(var5 < 45) {
            if(this.mc.player.getSlotFromPlayerContainer(var5).getHasStack()) {
               ItemStack var6 = this.mc.player.getSlotFromPlayerContainer(var5).getStack();
               if(this.getToolEffect(var6) > var4 && var6.getItem() instanceof ItemAxe && !this.isBestSword(var1)) {
                  return false;
               }
            }

            ++var5;
         }

         return true;
      }
   }

   private float getToolEffect(ItemStack var1) {
      Freecam.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemTool)) {
         return 0.0F;
      } else {
         float var6;
         label20: {
            String var4 = var3.getUnlocalizedName();
            ItemTool var5 = (ItemTool)var3;
            if(var3 instanceof ItemPickaxe) {
               var6 = var5.getStrVsBlock(var1, Blocks.stone);
               if(!var4.toLowerCase().contains("gold")) {
                  break label20;
               }

               var6 = var6 - 5.0F;
            }

            if(var3 instanceof ItemSpade) {
               var6 = var5.getStrVsBlock(var1, Blocks.dirt);
               if(!var4.toLowerCase().contains("gold")) {
                  break label20;
               }

               var6 = var6 - 5.0F;
            }

            if(!(var3 instanceof ItemAxe)) {
               return 1.0F;
            }

            var6 = var5.getStrVsBlock(var1, Blocks.log);
            if(var4.toLowerCase().contains("gold")) {
               var6 = var6 - 5.0F;
               return 1.0F;
            }
         }

         var6 = (float)((double)var6 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, var1) * 0.0075D);
         var6 = (float)((double)var6 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var1) / 100.0D);
         return var6;
      }
   }

   private float getBowEffect(ItemStack var1) {
      return (float)(1 + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, var1) + EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, var1) + EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1));
   }

   private boolean isBadPotion(ItemStack var1) {
      int[] var2 = Freecam.a();
      if(var1 != null && var1.getItem() instanceof ItemPotion) {
         ItemPotion var3 = (ItemPotion)var1.getItem();
         return var3.getEffects(var1) == null || this.isBadPotionEffect(var1, var3);
      } else {
         return false;
      }
   }

   public boolean isBadPotionEffect(ItemStack var1, ItemPotion var2) {
      Freecam.a();
      Iterator var4 = var2.getEffects(var1).iterator();
      if(var4.hasNext()) {
         PotionEffect var5 = (PotionEffect)var4.next();
         int var6 = var5.getPotionID();
         Potion var7 = Potion.potionTypes[var5.getPotionID()];
         if(var7.isBadEffect()) {
            return true;
         }
      }

      return false;
   }

   private boolean isBestBow(ItemStack var1) {
      Freecam.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemBow)) {
         return false;
      } else {
         float var4 = this.getBowEffect(var1);
         int var5 = 9;
         if(var5 < 45) {
            if(this.mc.player.getSlotFromPlayerContainer(var5).getHasStack()) {
               ItemStack var6 = this.mc.player.getSlotFromPlayerContainer(var5).getStack();
               if(this.getBowEffect(var6) > var4 && var6.getItem() instanceof ItemBow) {
                  return false;
               }
            }

            ++var5;
         }

         return true;
      }
   }

   public void onDisable() {
      this.timer.reset();
   }

   public void onEnable() {
      int[] var1 = Freecam.a();
      this.x.clear();
      if(!Files.exists(Paths.get(Novoline.getInstance().getPathString() + "item_preferences.txt", new String[0]), new LinkOption[0])) {
         try {
            Files.createFile(Paths.get(Novoline.getInstance().getPathString() + "item_preferences.txt", new String[0]), new FileAttribute[0]);
         } catch (IOException var4) {
            var4.printStackTrace();
         }
      }

      Iterator var2 = this.y().iterator();
      if(var2.hasNext()) {
         String var3 = (String)var2.next();
         this.x.add(Integer.valueOf(Integer.parseInt(var3)));
      }

   }

   public List y() {
      Freecam.a();
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();
      Scanner var3 = null;

      try {
         var3 = new Scanner(new File(Novoline.getInstance().getPathString() + "item_preferences.txt"));
      } catch (FileNotFoundException var5) {
         var5.printStackTrace();
      }

      if(var3 != null && var3.hasNext()) {
         var2.add(var3.nextLine());
      }

      return var2;
   }

   public BooleanProperty getInventoryCleaner() {
      return this.inventoryCleaner;
   }

   public BooleanProperty getOpen_inv() {
      return this.open_inv;
   }

   public BooleanProperty t() {
      return this.H;
   }

   public IntProperty getDelay() {
      return this.delay;
   }

   public IntProperty getBlocks() {
      return this.blocks;
   }

   public ListProperty getItems() {
      return this.items;
   }

   public IntProperty getPickAxeSlot() {
      return this.pickAxeSlot;
   }

   public IntProperty getAxeSlot() {
      return this.axeSlot;
   }

   public IntProperty getShovelSlot() {
      return this.shovelSlot;
   }

   public IntProperty getSwordSlot() {
      return this.swordSlot;
   }

   public IntProperty getBowSlot() {
      return this.bowSlot;
   }

   public IntProperty getHeadSlot() {
      return this.headSlot;
   }

   public IntProperty v() {
      return this.gappleSlot;
   }

   public ListProperty getAutoDisable() {
      return this.autoDisable;
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.P.equals("Custom"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.items.contains("Bow"));
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
