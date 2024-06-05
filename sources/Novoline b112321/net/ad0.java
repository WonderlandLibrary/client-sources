package net;

import cc.novoline.Novoline;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.player.InvManager;
import cc.novoline.utils.RenderUtils;
import java.awt.Color;
import net.aHD;
import net.uj;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ad0 {
   private int h;
   private boolean d;
   private boolean a;
   private float g;
   private float c;
   private Item b;
   private IntProperty e;
   final aHD f;

   public ad0(aHD var1, int var2, float var3, float var4) {
      this.f = var1;
      uj.b();
      this.h = var2;
      this.g = var3;
      this.c = var4;
      InvManager var6 = (InvManager)Novoline.getInstance().getModuleManager().getModule(InvManager.class);
      if(((Integer)var6.v().get()).intValue() == var2) {
         this.a(true);
         this.b = Items.golden_apple;
      }

      if(((Integer)var6.getAxeSlot().get()).intValue() == var2) {
         this.a(true);
         this.b = Items.diamond_axe;
      }

      if(((Integer)var6.getBowSlot().get()).intValue() == var2) {
         this.a(true);
         this.b = Items.bow;
      }

      if(((Integer)var6.getHeadSlot().get()).intValue() == var2) {
         this.a(true);
         this.b = Items.skull;
      }

      if(((Integer)var6.getShovelSlot().get()).intValue() == var2) {
         this.a(true);
         this.b = Items.diamond_shovel;
      }

      if(((Integer)var6.getPickAxeSlot().get()).intValue() == var2) {
         this.a(true);
         this.b = Items.diamond_pickaxe;
      }

      if(((Integer)var6.getSwordSlot().get()).intValue() == var2) {
         this.a(true);
         this.b = Items.diamond_sword;
      }

      this.c();
   }

   public Item a() {
      return this.b;
   }

   public void a(boolean var1) {
      this.d = var1;
   }

   public boolean b() {
      return this.d;
   }

   public int e() {
      return this.h;
   }

   public void a(Item var1) {
      this.b = var1;
   }

   public IntProperty d() {
      return this.e;
   }

   public void a(float var1, float var2) {
      int[] var3 = uj.b();
      RenderUtils.drawBorderedRect(this.g, this.c, this.g + 20.0F, this.c + 20.0F, 1.0F, (new Color(29, 29, 29, 255)).getRGB(), !this.b(var1, var2) && !this.a?(new Color(40, 40, 40, 255)).getRGB():(new Color(40, 40, 40, 255)).brighter().getRGB());
      if(this.b() && this.b != null) {
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.enableDepth();
         aHD.a(this.f).getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(this.b), this.g + 2.0F, this.c + 2.0F);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableDepth();
      }

   }

   public void c() {
      uj.b();
      InvManager var2 = (InvManager)Novoline.getInstance().getModuleManager().getModule(InvManager.class);
      if(((Integer)var2.v().get()).intValue() == this.h) {
         this.e = var2.v();
      }

      if(((Integer)var2.getAxeSlot().get()).intValue() == this.h) {
         this.e = var2.getAxeSlot();
      }

      if(((Integer)var2.getBowSlot().get()).intValue() == this.h) {
         this.e = var2.getBowSlot();
      }

      if(((Integer)var2.getHeadSlot().get()).intValue() == this.h) {
         this.e = var2.getHeadSlot();
      }

      if(((Integer)var2.getShovelSlot().get()).intValue() == this.h) {
         this.e = var2.getShovelSlot();
      }

      if(((Integer)var2.getPickAxeSlot().get()).intValue() == this.h) {
         this.e = var2.getPickAxeSlot();
      }

      if(((Integer)var2.getSwordSlot().get()).intValue() == this.h) {
         this.e = var2.getSwordSlot();
      }

   }

   public boolean b(float var1, float var2) {
      int[] var3 = uj.b();
      return var1 >= this.g && var1 <= this.g + 20.0F && var2 >= this.c && var2 <= this.c + 20.0F;
   }

   static boolean b(ad0 var0) {
      return var0.a;
   }

   static IntProperty c(ad0 var0) {
      return var0.e;
   }

   static IntProperty a(ad0 var0, IntProperty var1) {
      return var0.e = var1;
   }

   static int a(ad0 var0) {
      return var0.h;
   }

   static boolean a(ad0 var0, boolean var1) {
      return var0.a = var1;
   }
}
