package net;

import cc.novoline.utils.RenderUtils;
import java.awt.Color;
import java.util.Iterator;
import java.util.function.Predicate;
import net.aH9;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class uj {
   public float a;
   public float c;
   public boolean f;
   public Item e;
   final aH9 d;
   private static int[] b;

   public uj(aH9 var1, Item var2) {
      this.d = var1;
      b();
      super();
      this.e = var2;
      Iterator var4 = aH9.f(var1).iterator();
      if(var4.hasNext()) {
         Integer var5 = (Integer)var4.next();
         if(var5.intValue() == Item.b(var2)) {
            this.f = true;
         }
      }

   }

   public void a(float var1, float var2, float var3, float var4) {
      b();
      this.a = var3;
      this.c = var4;
      RenderUtils.drawBorderedRect(var3, var4, var3 + 20.0F, var4 + 20.0F, 2.0F, (new Color(29, 29, 29, 255)).getRGB(), this.f?(new Color(40, 40, 40, 255)).brighter().brighter().getRGB():(this.b(var1, var2, var3, var4)?(new Color(40, 40, 40, 255)).darker().getRGB():(new Color(40, 40, 40, 255)).getRGB()));
      if(this.e != null) {
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.enableDepth();
         aH9.a(this.d).getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(this.e), var3 + 2.0F, var4 + 2.0F);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableDepth();
      }

   }

   public void a(float var1, float var2, int var3) {
      int[] var4 = b();
      if(this.b(var1, var2, this.a, this.c) && var3 == 0) {
         this.f = !this.f;
         if(this.f) {
            aH9.f(this.d).add(Integer.valueOf(Item.b(this.e)));
         }

         aH9.f(this.d).removeIf(this::lambda$mouseClicked$0);
      }

   }

   public boolean b(float var1, float var2, float var3, float var4) {
      int[] var5 = b();
      return var1 > var3 && var1 < var3 + 20.0F && var2 > var4 && var2 < var4 + 20.0F;
   }

   private boolean lambda$mouseClicked$0(Integer var1) {
      int[] var2 = b();
      return var1.intValue() == Item.b(this.e);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[1]);
      }

   }
}
