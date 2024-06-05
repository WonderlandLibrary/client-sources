package net;

import cc.novoline.events.events.EventState;
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
import net.UW;
import net.VN;
import net.WL;
import net.a2V;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aH9;
import net.aHD;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.avP;
import net.avU;
import net.avq;
import net.avu;
import net.axu;
import net.d3;
import net.gZ;
import net.lS;
import net.minecraft.client.gui.GuiChat;
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

public final class av6 extends as0 {
   private final d3 O = new d3();
   private List x = new CopyOnWriteArrayList();
   @VN("inventory-cleaner")
   private final aEu N;
   @VN("open-inventory")
   private final aEu L;
   @VN("no-move")
   private final aEu H;
   @VN("delay")
   private final aE8 C;
   @VN("arrows")
   private final aE8 A;
   @VN("blocks")
   private final aE8 J;
   @VN("pickaxe-slot")
   private final aE8 F;
   @VN("axe-slot")
   private final aE8 B;
   @VN("shovel-slot")
   private final aE8 y;
   @VN("weapon-slot")
   private final aE8 D;
   @VN("bow-slot")
   private final aE8 z;
   @VN("head-slot")
   private final aE8 G;
   @VN("gapple-slot")
   private final aE8 M;
   @VN("autodisable")
   private final aE3 E;
   @VN("keep-items")
   private final aE3 K;
   @VN("filters")
   private final aE3 I;
   @VN("sort-mode")
   private final aEs P;

   public av6(UW var1) {
      super(var1, "InventoryManager", "Inventory Manager", a2V.PLAYER, "Manages inventory");
      avq.a();
      this.N = axu.b();
      this.L = axu.g();
      this.H = axu.g();
      this.C = (aE8)((aE8)axu.b(Integer.valueOf(1)).d(Integer.valueOf(1))).c(Integer.valueOf(10));
      this.A = (aE8)((aE8)axu.b(Integer.valueOf(128)).d(Integer.valueOf(64))).c(Integer.valueOf(512));
      this.J = (aE8)((aE8)axu.b(Integer.valueOf(128)).d(Integer.valueOf(0))).c(Integer.valueOf(512));
      this.F = (aE8)((aE8)axu.b(Integer.valueOf(7)).d(Integer.valueOf(1))).c(Integer.valueOf(9));
      this.B = (aE8)((aE8)axu.b(Integer.valueOf(8)).d(Integer.valueOf(1))).c(Integer.valueOf(9));
      this.y = (aE8)((aE8)axu.b(Integer.valueOf(9)).d(Integer.valueOf(1))).c(Integer.valueOf(9));
      this.D = (aE8)((aE8)axu.b(Integer.valueOf(1)).d(Integer.valueOf(1))).c(Integer.valueOf(9));
      this.z = (aE8)((aE8)axu.b(Integer.valueOf(2)).d(Integer.valueOf(1))).c(Integer.valueOf(9));
      this.G = (aE8)((aE8)axu.b(Integer.valueOf(3)).d(Integer.valueOf(1))).c(Integer.valueOf(9));
      this.M = (aE8)((aE8)axu.b(Integer.valueOf(4)).d(Integer.valueOf(1))).c(Integer.valueOf(9));
      this.E = axu.a((Object)"Death").a((Object[])(new String[]{"World Change", "Game End", "Death"}));
      this.K = axu.a("Sword", "Bow").a((Object[])(new String[]{"Sword", "Bow", "PickAxe", "Axe", "Shovel", "Golden Apple", "Head", "Buckets"}));
      this.I = axu.a((Object)"Sticks").a((Object[])(new String[]{"Ores", "Sticks"}));
      this.P = axu.a("Default").a(new String[]{"Custom", "Default"});
      ae9.a(new adZ("IM_SORT_MODE", "Sort Mode", a6d.COMBOBOX, this, this.P));
      ae9.a(new adZ("IM_CLEANER", "Cleaner", a6d.CHECKBOX, this, this.N));
      ae9.a(new adZ("IM_OPEN_INV", "Open Inventory", a6d.CHECKBOX, this, this.L));
      ae9.a(new adZ("IM_NO_MOVE", "Not Moving", a6d.CHECKBOX, this, this.H));
      ae9.a(new adZ("IM_DELAY", "Delay", a6d.SLIDER, this, this.C, 1.0D));
      a6d var10004 = a6d.SLIDER;
      aE8 var10006 = this.J;
      aEu var10008 = this.N;
      this.N.getClass();
      ae9.a(new adZ("IM_BLOCKS", "Blocks", var10004, this, var10006, 64.0D, var10008::a));
      ae9.a(new adZ("IM_ARROWS", "Arrows", a6d.SLIDER, this, this.A, 64.0D, this::lambda$new$0));
      ae9.a(new adZ("IM_KEEP_ITEMS", "Keep Items", a6d.SELECTBOX, this, this.K));
      ae9.a(new adZ("IM_MATERIALS", "Keep Materials", a6d.SELECTBOX, this, this.I));
      ae9.a(new adZ("IM_SLOT_CHANGE", "Preferred slots", a6d.GUI, this, new aHD()));
      ae9.a(new adZ("IM_WHITELIST", "Item management", a6d.GUI, this, new aH9(), this::lambda$new$1));
      ae9.a(new adZ("IM_AUTO_DISABLE", "Disable On", a6d.SELECTBOX, this, this.E));
   }

   private boolean i(ItemStack var1) {
      float var3 = this.g(var1);
      avq.a();
      int var4 = 9;
      if(var4 < 45) {
         if(this.w.thePlayer.q(var4).getHasStack()) {
            ItemStack var5 = this.w.thePlayer.q(var4).getStack();
            if(this.g(var5) > var3 && var5.getItem() instanceof ItemSword) {
               return false;
            }
         }

         ++var4;
      }

      return var1.getItem() instanceof ItemSword;
   }

   private boolean a(ItemStack var1) {
      int[] var2 = avq.a();
      return var1.getItem() instanceof ItemSkull && var1.getDisplayName().contains("Head") && !var1.getDisplayName().equalsIgnoreCase("Wither Skeleton Skull") && !var1.getDisplayName().equalsIgnoreCase("Zombie Head") && !var1.getDisplayName().equalsIgnoreCase("Creeper Head") && !var1.getDisplayName().equalsIgnoreCase("Skeleton Skull");
   }

   private boolean h(ItemStack var1) {
      return var1.getItem() instanceof ItemAppleGold;
   }

   @agu
   public void a(aG1 var1) {
      int[] var2 = avq.a();
      if(var1.h().equals(EventState.PRE)) {
         if(!(this.w.currentScreen instanceof GuiInventory) && ((Boolean)this.L.a()).booleanValue() || lS.a(WL.PRE) || lS.a(WL.LOBBY) || this.a((Class)avP.class) && ((avP)this.b(avP.class)).b() || this.w.thePlayer.ay() > 0.05D && ((Boolean)this.H.a()).booleanValue() || ((avU)this.b(avU.class)).b()) {
            return;
         }

         if(this.w.currentScreen == null || this.w.currentScreen instanceof GuiInventory || this.w.currentScreen instanceof GuiChat) {
            int var3 = ((Integer)this.D.a()).intValue() - 1;
            int var4 = ((Integer)this.F.a()).intValue() - 1;
            int var5 = ((Integer)this.z.a()).intValue() - 1;
            int var6 = ((Integer)this.y.a()).intValue() - 1;
            int var7 = ((Integer)this.B.a()).intValue() - 1;
            int var8 = ((Integer)this.G.a()).intValue() - 1;
            int var9 = ((Integer)this.M.a()).intValue() - 1;
            boolean var10 = this.K.a((Object)"PickAxe");
            boolean var11 = this.K.a((Object)"Shovel");
            boolean var12 = this.K.a((Object)"Axe");
            boolean var13 = this.K.a((Object)"Sword");
            boolean var14 = this.K.a((Object)"Bow");
            boolean var15 = this.K.a((Object)"Head");
            boolean var16 = this.K.a((Object)"Golden Apple");
            int var17 = ((Integer)this.C.a()).intValue() * 50;
            int var18 = 9;
            if(var18 < 45) {
               ItemStack var19 = this.w.thePlayer.q(var18).getStack();
               if(var19 != null && !var19.getDisplayName().contains("§7(Right Click)") && this.O.a((float)var17)) {
                  if(this.i(var19) && var13 && this.a(var3)[0]) {
                     this.w.thePlayer.a(var18, var3);
                     this.O.b();
                  }

                  if(this.f(var19) && var10 && this.a(var4)[2]) {
                     this.w.thePlayer.a(var18, var4);
                     this.O.b();
                  }

                  if(this.l(var19) && var12 && this.a(var7)[1]) {
                     this.w.thePlayer.a(var18, var7);
                     this.O.b();
                  }

                  if(this.c(var19) && var14 && this.a(var5)[5]) {
                     this.w.thePlayer.a(var18, var5);
                     this.O.b();
                  }

                  if(this.a(var19) && var15 && this.a(var8)[4]) {
                     this.w.thePlayer.a(var18, var8);
                     this.O.b();
                  }

                  if(this.e(var19) && var11 && this.a(var6)[3]) {
                     this.w.thePlayer.a(var18, var6);
                     this.O.b();
                  }

                  if(this.h(var19) && var16 && this.a(var9)[6]) {
                     this.w.thePlayer.a(var18, var9);
                     this.O.b();
                  }
               }

               ++var18;
            }

            var18 = 9;
            if(var18 < 45) {
               if(!this.w.thePlayer.q(var18).getHasStack()) {
                  ;
               }

               ItemStack var23 = this.w.thePlayer.q(var18).getStack();
               if(var23 != null && this.d(var23) && ((Boolean)this.N.a()).booleanValue() && this.O.a((double)var17)) {
                  this.w.thePlayer.r(var18);
                  this.O.b();
               }

               ++var18;
            }
         }
      }

   }

   private boolean[] a(int var1) {
      int[] var2 = avq.a();
      return new boolean[]{!this.w.thePlayer.q(var1 + 36).getHasStack() || !this.i(this.w.thePlayer.q(var1 + 36).getStack()), !this.w.thePlayer.q(var1 + 36).getHasStack() || !this.l(this.w.thePlayer.q(var1 + 36).getStack()), !this.w.thePlayer.q(var1 + 36).getHasStack() || !this.f(this.w.thePlayer.q(var1 + 36).getStack()), !this.w.thePlayer.q(var1 + 36).getHasStack() || !this.e(this.w.thePlayer.q(var1 + 36).getStack()), !this.w.thePlayer.q(var1 + 36).getHasStack() || !this.a(this.w.thePlayer.q(var1 + 36).getStack()), !this.w.thePlayer.q(var1 + 36).getHasStack() || !this.c(this.w.thePlayer.q(var1 + 36).getStack()), !this.w.thePlayer.q(var1 + 36).getHasStack() || !this.h(this.w.thePlayer.q(var1 + 36).getStack())};
   }

   private float g(ItemStack var1) {
      avq.a();
      float var3 = 0.0F;
      Item var4 = var1.getItem();
      if(var4 instanceof ItemTool) {
         var3 += ((ItemTool)var4).b();
      }

      if(var4 instanceof ItemSword) {
         var3 += ((ItemSword)var4).a();
      }

      var3 = var3 + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, var1) * 1.25F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, var1) * 0.3F;
      return var3;
   }

   private int m() {
      avq.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            Item var5 = var4.getItem();
            if(var4.getItem() instanceof ItemBlock && !((avu)this.b(avu.class)).l().contains(((ItemBlock)var5).getBlock())) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int q() {
      avq.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() == Items.arrow) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int b() {
      int var2 = 0;
      avq.a();
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() == Items.iron_ingot) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int i() {
      avq.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() == Items.coal) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int l() {
      int var2 = 0;
      avq.a();
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() instanceof ItemSword && this.i(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int f() {
      avq.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() instanceof ItemBow && this.c(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int w() {
      int var2 = 0;
      avq.a();
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() instanceof ItemPickaxe && this.f(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int u() {
      avq.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() instanceof ItemAxe && this.l(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int n() {
      avq.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() instanceof ItemSkull && this.e(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private int e() {
      avq.a();
      int var2 = 0;
      int var3 = 0;
      if(var3 < 45) {
         if(this.w.thePlayer.q(var3).getHasStack()) {
            ItemStack var4 = this.w.thePlayer.q(var3).getStack();
            if(var4.getItem() instanceof ItemSpade && this.e(var4)) {
               var2 += var4.stackSize;
            }
         }

         ++var3;
      }

      return var2;
   }

   private boolean f(ItemStack var1) {
      avq.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemPickaxe)) {
         return false;
      } else {
         float var4 = this.j(var1);
         int var5 = 9;
         if(var5 < 45) {
            if(this.w.thePlayer.q(var5).getHasStack()) {
               ItemStack var6 = this.w.thePlayer.q(var5).getStack();
               if(this.j(var6) > var4 && var6.getItem() instanceof ItemPickaxe) {
                  return false;
               }
            }

            ++var5;
         }

         return true;
      }
   }

   private boolean d(ItemStack var1) {
      Item var3 = var1.getItem();
      avq.a();
      String var4 = var1.getDisplayName();
      int var5 = Item.b(var3);
      if(var5 != 58 && !var4.toLowerCase().contains(EnumChatFormatting.OBFUSCATED + "||") && !var4.contains(new Color(0, 255, 0, 255) + "Game Menu " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "Spectator Settings " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "Play Again " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "Teleporter " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "SkyWars Challenges " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Collectibles " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Kit Selector " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Kill Effect Selector " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.WHITE + "Players: " + EnumChatFormatting.RED + "Hidden " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Shop " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.WHITE + "Players: " + EnumChatFormatting.RED + "Visible " + EnumChatFormatting.GRAY + "(Right Click)") && !var4.equalsIgnoreCase(EnumChatFormatting.GOLD + "Excalibur") && !var4.equalsIgnoreCase("aDragon Sword") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Cornucopia") && !var4.equalsIgnoreCase(EnumChatFormatting.RED + "Bloodlust") && !var4.equalsIgnoreCase(EnumChatFormatting.RED + "Artemis\' Bow") && !var4.equalsIgnoreCase(EnumChatFormatting.GREEN + "Miner\'s Blessing") && !var4.equalsIgnoreCase(EnumChatFormatting.GOLD + "Axe of Perun") && !var4.equalsIgnoreCase(EnumChatFormatting.GOLD + "Cornucopia") && var5 != 116 && var5 != 145 && (var5 != 15 && var5 != 14 || !this.I.a((Object)"Ores")) && !var4.equalsIgnoreCase("§aAndúril") && var5 != 259 && var5 != 46) {
         boolean var6 = this.K.a((Object)"PickAxe");
         boolean var7 = this.K.a((Object)"Shovel");
         boolean var8 = this.K.a((Object)"Axe");
         boolean var9 = this.K.a((Object)"Sword");
         boolean var10 = this.K.a((Object)"Bow");
         boolean var11 = this.K.a((Object)"Head");
         int var12 = ((Integer)this.D.a()).intValue() - 1;
         int var13 = ((Integer)this.F.a()).intValue() - 1;
         int var14 = ((Integer)this.z.a()).intValue() - 1;
         int var15 = ((Integer)this.y.a()).intValue() - 1;
         int var16 = ((Integer)this.B.a()).intValue() - 1;
         int var17 = ((Integer)this.G.a()).intValue() - 1;
         if(((!this.e(var1) || this.e() >= 2) && (!(var1.getItem() instanceof ItemSpade) || var1 != this.w.thePlayer.bJ.getStackInSlot(var15)) || !var7) && ((!this.c(var1) || this.f() >= 2) && (!(var1.getItem() instanceof ItemBow) || var1 != this.w.thePlayer.bJ.getStackInSlot(var14)) || !var10) && ((!this.a(var1) || this.n() >= 2) && (!(var1.getItem() instanceof ItemSkull) || var1 != this.w.thePlayer.bJ.getStackInSlot(var17)) || !var11) && ((!this.l(var1) || this.u() >= 2) && (!(var1.getItem() instanceof ItemAxe) || var1 != this.w.thePlayer.bJ.getStackInSlot(var16)) || !var8) && ((!this.f(var1) || this.w() >= 2) && (!(var1.getItem() instanceof ItemPickaxe) || var1 != this.w.thePlayer.bJ.getStackInSlot(var13)) || !var6) && ((!this.i(var1) || this.l() >= 2) && (!(var1.getItem() instanceof ItemSword) || var1 != this.w.thePlayer.bJ.getStackInSlot(var12)) || !var9)) {
            if(var3 instanceof ItemArmor) {
               avP var18 = (avP)this.b(avP.class);
               int var19 = 1;
               if(var19 < 5) {
                  label1491: {
                     if(this.w.thePlayer.q(4 + var19).getHasStack()) {
                        ItemStack var20 = this.w.thePlayer.q(4 + var19).getStack();
                        if(var18.a(var20, var19)) {
                           break label1491;
                        }
                     }

                     if(var18.a(var1, var19)) {
                        return false;
                     }
                  }

                  ++var19;
               }
            }

            if((!(var3 instanceof ItemBlock) || this.m() <= ((Integer)this.J.a()).intValue() && !((avu)this.b(avu.class)).l().contains(((ItemBlock)var3).getBlock())) && (!(var3 instanceof ItemPotion) || !this.k(var1)) && (!(var3 instanceof ItemFood) || var3 instanceof ItemAppleGold || var3 == Items.bread || var3 == Items.pumpkin_pie || var3 == Items.baked_potato || var3 == Items.cooked_chicken || var3 == Items.carrot || var3 == Items.apple || var3 == Items.beef || var3 == Items.cooked_beef || var3 == Items.porkchop || var3 == Items.cooked_porkchop || var3 == Items.mushroom_stew || var3 == Items.cooked_fish || var3 == Items.melon) && !(var3 instanceof ItemHoe) && !(var3 instanceof ItemTool) && !(var3 instanceof ItemSword) && !(var3 instanceof ItemArmor)) {
               String var21 = var3.getUnlocalizedName();
               return !this.P.a("Default")?!this.x.contains(Integer.valueOf(var5)):!this.I.a((Object)"Sticks") && var21.contains("stick") || var21.contains("egg") || this.b() > 64 && var3 == Items.iron_ingot || this.i() > 64 && var3 == Items.coal || var21.contains("string") || var21.contains("flint") || var21.contains("compass") || var21.contains("dyePowder") || var21.contains("feather") || var21.contains("chest") && !var4.toLowerCase().contains("collect") || var21.contains("snow") || var21.contains("torch") || var21.contains("seeds") || var21.contains("leather") || var21.contains("reeds") || var21.contains("record") || var21.contains("snowball") || var3 instanceof ItemGlassBottle || var3 instanceof ItemSlab || var5 == 113 || var5 == 106 || var5 == 325 || var5 == 326 && !this.K.a((Object)"Buckets") || var5 == 327 || var5 == 111 || var5 == 85 || var5 == 188 || var5 == 189 || var5 == 190 || var5 == 191 || var5 == 401 || var5 == 192 || var5 == 81 || var5 == 32 || var21.contains("gravel") || var21.contains("flower") || var21.contains("tallgrass") || var3 instanceof ItemBow || var3 == Items.arrow && this.q() > (this.K.a((Object)"Bow")?((Integer)this.A.a()).intValue():0) || var5 == 175 || var5 == 340 || var5 == 339 || var5 == 160 || var5 == 101 || var5 == 102 || var5 == 321 || var5 == 323 || var5 == 389 || var5 == 416 || var5 == 171 || var5 == 139 || var5 == 23 || var5 == 25 || var5 == 69 || var5 == 70 || var5 == 72 || var5 == 77 || var5 == 96 || var5 == 107 || var5 == 123 || var5 == 131 || var5 == 143 || var5 == 147 || var5 == 148 || var5 == 151 || var5 == 152 || var5 == 154 || var5 == 158 || var5 == 167 || var5 == 403 || var5 == 183 || var5 == 184 || var5 == 185 || var5 == 186 || var5 == 187 || var5 == 331 || var5 == 356 || var5 == 404 || var5 == 27 || var5 == 28 || var5 == 66 || var5 == 76 || var5 == 157 || var5 == 328 || var5 == 342 || var5 == 343 || var5 == 398 || var5 == 407 || var5 == 408 || var5 == 138 || var5 == 352 || var5 == 385 || var5 == 386 || var5 == 395 || var5 == 402 || var5 == 418 || var5 == 419 || var5 == 281 || var5 == 289 || var5 == 337 || var5 == 336 || var5 == 348 || var5 == 353 || var5 == 369 || var5 == 372 || var5 == 405 || var5 == 406 || var5 == 409 || var5 == 410 || var5 == 415 || var5 == 370 || var5 == 376 || var5 == 377 || var5 == 378 || var5 == 379 || var5 == 380 || var5 == 382 || var5 == 414 || var5 == 346 || var5 == 347 || var5 == 420 || var5 == 397 || var5 == 421 || var5 == 341 || var21.contains("sapling") || var21.contains("stairs") || var21.contains("door") || var21.contains("monster_egg") || var21.contains("sand") || var21.contains("piston");
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

   private boolean e(ItemStack var1) {
      avq.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemSpade)) {
         return false;
      } else {
         float var4 = this.j(var1);
         int var5 = 9;
         if(var5 < 45) {
            if(this.w.thePlayer.q(var5).getHasStack()) {
               ItemStack var6 = this.w.thePlayer.q(var5).getStack();
               if(this.j(var6) > var4 && var6.getItem() instanceof ItemSpade) {
                  return false;
               }
            }

            ++var5;
         }

         return true;
      }
   }

   private boolean l(ItemStack var1) {
      avq.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemAxe)) {
         return false;
      } else {
         float var4 = this.j(var1);
         int var5 = 9;
         if(var5 < 45) {
            if(this.w.thePlayer.q(var5).getHasStack()) {
               ItemStack var6 = this.w.thePlayer.q(var5).getStack();
               if(this.j(var6) > var4 && var6.getItem() instanceof ItemAxe && !this.i(var1)) {
                  return false;
               }
            }

            ++var5;
         }

         return true;
      }
   }

   private float j(ItemStack var1) {
      avq.a();
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

   private float b(ItemStack var1) {
      return (float)(1 + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, var1) + EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, var1) + EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1));
   }

   private boolean k(ItemStack var1) {
      int[] var2 = avq.a();
      if(var1 != null && var1.getItem() instanceof ItemPotion) {
         ItemPotion var3 = (ItemPotion)var1.getItem();
         return var3.getEffects(var1) == null || this.a(var1, var3);
      } else {
         return false;
      }
   }

   public boolean a(ItemStack var1, ItemPotion var2) {
      avq.a();
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

   private boolean c(ItemStack var1) {
      avq.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemBow)) {
         return false;
      } else {
         float var4 = this.b(var1);
         int var5 = 9;
         if(var5 < 45) {
            if(this.w.thePlayer.q(var5).getHasStack()) {
               ItemStack var6 = this.w.thePlayer.q(var5).getStack();
               if(this.b(var6) > var4 && var6.getItem() instanceof ItemBow) {
                  return false;
               }
            }

            ++var5;
         }

         return true;
      }
   }

   public void c() {
      this.O.b();
   }

   public void n() {
      int[] var1 = avq.a();
      this.x.clear();
      if(!Files.exists(Paths.get(gZ.g().x() + "item_preferences.txt", new String[0]), new LinkOption[0])) {
         try {
            Files.createFile(Paths.get(gZ.g().x() + "item_preferences.txt", new String[0]), new FileAttribute[0]);
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
      avq.a();
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();
      Scanner var3 = null;

      try {
         var3 = new Scanner(new File(gZ.g().x() + "item_preferences.txt"));
      } catch (FileNotFoundException var5) {
         var5.printStackTrace();
      }

      if(var3 != null && var3.hasNext()) {
         var2.add(var3.nextLine());
      }

      return var2;
   }

   public aEu a() {
      return this.N;
   }

   public aEu j() {
      return this.L;
   }

   public aEu t() {
      return this.H;
   }

   public aE8 x() {
      return this.C;
   }

   public aE8 r() {
      return this.J;
   }

   public aE3 c() {
      return this.K;
   }

   public aE8 g() {
      return this.F;
   }

   public aE8 o() {
      return this.B;
   }

   public aE8 s() {
      return this.y;
   }

   public aE8 h() {
      return this.D;
   }

   public aE8 z() {
      return this.z;
   }

   public aE8 d() {
      return this.G;
   }

   public aE8 v() {
      return this.M;
   }

   public aE3 k() {
      return this.E;
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.P.a("Custom"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.K.a((Object)"Bow"));
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
