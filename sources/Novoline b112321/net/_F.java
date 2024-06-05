package net;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

abstract class _F extends GuiSlot {
   protected int A;
   protected List z;
   protected Comparator B;
   protected int C;
   protected int y;
   final GuiStats x;

   protected _F(GuiStats var1, Minecraft var2) {
      super(var2, var1.width, var1.height, 32, var1.height - 64, 20);
      this.x = var1;
      this.A = -1;
      this.C = -1;
      this.setShowSelectionBox(false);
      this.setHasListHeader(true, 20);
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
   }

   protected boolean isSelected(int var1) {
      return false;
   }

   protected void drawBackground() {
      this.x.drawDefaultBackground();
   }

   protected void drawListHeader(int var1, int var2, Tessellator var3) {
      if(!Mouse.isButtonDown(0)) {
         this.A = -1;
      }

      if(this.A == 0) {
         GuiStats.access$000(this.x, var1 + 115 - 18, var2 + 1, 0, 0);
      } else {
         GuiStats.access$000(this.x, var1 + 115 - 18, var2 + 1, 0, 18);
      }

      if(this.A == 1) {
         GuiStats.access$000(this.x, var1 + 165 - 18, var2 + 1, 0, 0);
      } else {
         GuiStats.access$000(this.x, var1 + 165 - 18, var2 + 1, 0, 18);
      }

      if(this.A == 2) {
         GuiStats.access$000(this.x, var1 + 215 - 18, var2 + 1, 0, 0);
      } else {
         GuiStats.access$000(this.x, var1 + 215 - 18, var2 + 1, 0, 18);
      }

      if(this.C != -1) {
         short var4 = 79;
         byte var5 = 18;
         if(this.C == 1) {
            var4 = 129;
         } else if(this.C == 2) {
            var4 = 179;
         }

         if(this.y == 1) {
            var5 = 36;
         }

         GuiStats.access$000(this.x, var1 + var4, var2 + 1, var5, 0);
      }

   }

   protected void func_148132_a(int var1, int var2) {
      this.A = -1;
      if(var1 >= 79 && var1 < 115) {
         this.A = 0;
      } else if(var1 >= 129 && var1 < 165) {
         this.A = 1;
      } else if(var1 >= 179 && var1 < 215) {
         this.A = 2;
      }

      if(this.A >= 0) {
         this.a(this.A);
         this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      }

   }

   protected final int getSize() {
      return this.z.size();
   }

   protected final StatCrafting c(int var1) {
      return (StatCrafting)this.z.get(var1);
   }

   protected abstract String b(int var1);

   protected void a(StatBase var1, int var2, int var3, boolean var4) {
      String var5 = var1.format(GuiStats.access$100(this.x).readStat(var1));
      this.x.drawString(GuiStats.p(this.x), var5, var2 - GuiStats.g(this.x).d(var5), var3 + 5, 16777215);
   }

   protected void func_148142_b(int var1, int var2) {
      if(var2 >= this.top && var2 <= this.bottom) {
         int var3 = this.getSlotIndexFromScreenCoords(var1, var2);
         int var4 = this.width / 2 - 92 - 16;
         if(var1 < var4 + 40 || var1 > var4 + 40 + 20) {
            return;
         }

         StatCrafting var5 = this.c(var3);
         this.a(var5, var1, var2);
      }

   }

   protected void a(StatCrafting var1, int var2, int var3) {
      Item var4 = var1.func_150959_a();
      ItemStack var5 = new ItemStack(var4);
      String var6 = var5.getUnlocalizedName();
      String var7 = ("" + I18n.format(var6 + ".name", new Object[0])).trim();
      if(!var7.isEmpty()) {
         int var8 = var2 + 12;
         int var9 = var3 - 12;
         int var10 = GuiStats.q(this.x).d(var7);
         GuiStats.access$1000(this.x, (float)(var8 - 3), (float)(var9 - 3), (float)(var8 + var10 + 3), (float)(var9 + 8 + 3), -1073741824, -1073741824);
         GuiStats.r(this.x).drawStringWithShadow(var7, (float)var8, (float)var9, -1);
      }

   }

   protected void a(int var1) {
      if(var1 != this.C) {
         this.C = var1;
         this.y = -1;
      } else if(this.y == -1) {
         this.y = 1;
      } else {
         this.C = -1;
         this.y = 0;
      }

      Collections.sort(this.z, this.B);
   }
}
