package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiListButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiPageButtonList$EditBoxEntry;
import net.minecraft.client.gui.GuiPageButtonList$GuiButtonEntry;
import net.minecraft.client.gui.GuiPageButtonList$GuiEntry;
import net.minecraft.client.gui.GuiPageButtonList$GuiLabelEntry;
import net.minecraft.client.gui.GuiPageButtonList$GuiListEntry;
import net.minecraft.client.gui.GuiPageButtonList$GuiResponder;
import net.minecraft.client.gui.GuiPageButtonList$GuiSlideEntry;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.IntHashMap;

public class GuiPageButtonList extends GuiListExtended {
   private final List field_178074_u = Lists.newArrayList();
   private final IntHashMap field_178073_v = new IntHashMap();
   private final List field_178072_w = Lists.newArrayList();
   private final GuiPageButtonList$GuiListEntry[][] field_178078_x;
   private int field_178077_y;
   private GuiPageButtonList$GuiResponder field_178076_z;
   private Gui field_178075_A;

   public GuiPageButtonList(Minecraft var1, int var2, int var3, int var4, int var5, int var6, GuiPageButtonList$GuiResponder var7, GuiPageButtonList$GuiListEntry[]... var8) {
      super(var1, var2, var3, var4, var5, var6);
      this.field_178076_z = var7;
      this.field_178078_x = var8;
      this.field_148163_i = false;
      this.func_178069_s();
      this.func_178055_t();
   }

   private void func_178069_s() {
      for(GuiPageButtonList$GuiListEntry[] var4 : this.field_178078_x) {
         for(int var5 = 0; var5 < var4.length; var5 += 2) {
            GuiPageButtonList$GuiListEntry var6 = var4[var5];
            GuiPageButtonList$GuiListEntry var7 = var5 < var4.length - 1?var4[var5 + 1]:null;
            Gui var8 = this.func_178058_a(var6, 0, true);
            Gui var9 = this.func_178058_a(var7, 160, true);
            GuiPageButtonList$GuiEntry var10 = new GuiPageButtonList$GuiEntry(var8, var9);
            this.field_178074_u.add(var10);
            this.field_178073_v.addKey(var6.func_178935_b(), var8);
            if(var8 instanceof GuiTextField) {
               this.field_178072_w.add((GuiTextField)var8);
            }

            this.field_178073_v.addKey(var7.func_178935_b(), var9);
            if(var9 instanceof GuiTextField) {
               this.field_178072_w.add((GuiTextField)var9);
            }
         }
      }

   }

   private void func_178055_t() {
      this.field_178074_u.clear();

      for(int var1 = 0; var1 < this.field_178078_x[this.field_178077_y].length; var1 += 2) {
         GuiPageButtonList$GuiListEntry var2 = this.field_178078_x[this.field_178077_y][var1];
         GuiPageButtonList$GuiListEntry var3 = var1 < this.field_178078_x[this.field_178077_y].length - 1?this.field_178078_x[this.field_178077_y][var1 + 1]:null;
         Gui var4 = (Gui)this.field_178073_v.lookup(var2.func_178935_b());
         Gui var5 = (Gui)this.field_178073_v.lookup(var3.func_178935_b());
         GuiPageButtonList$GuiEntry var6 = new GuiPageButtonList$GuiEntry(var4, var5);
         this.field_178074_u.add(var6);
      }

   }

   public void func_181156_c(int var1) {
      if(var1 != this.field_178077_y) {
         int var2 = this.field_178077_y;
         this.field_178077_y = var1;
         this.func_178055_t();
         this.func_178060_e(var2, var1);
         this.amountScrolled = 0.0F;
      }

   }

   public int func_178059_e() {
      return this.field_178077_y;
   }

   public int func_178057_f() {
      return this.field_178078_x.length;
   }

   public Gui func_178056_g() {
      return this.field_178075_A;
   }

   public void func_178071_h() {
      if(this.field_178077_y > 0) {
         this.func_181156_c(this.field_178077_y - 1);
      }

   }

   public void func_178064_i() {
      if(this.field_178077_y < this.field_178078_x.length - 1) {
         this.func_181156_c(this.field_178077_y + 1);
      }

   }

   public Gui func_178061_c(int var1) {
      return (Gui)this.field_178073_v.lookup(var1);
   }

   private void func_178060_e(int var1, int var2) {
      for(GuiPageButtonList$GuiListEntry var6 : this.field_178078_x[var1]) {
         this.func_178066_a((Gui)this.field_178073_v.lookup(var6.func_178935_b()), false);
      }

      for(GuiPageButtonList$GuiListEntry var10 : this.field_178078_x[var2]) {
         this.func_178066_a((Gui)this.field_178073_v.lookup(var10.func_178935_b()), true);
      }

   }

   private void func_178066_a(Gui var1, boolean var2) {
      if(var1 instanceof GuiButton) {
         ((GuiButton)var1).visible = var2;
      } else if(var1 instanceof GuiTextField) {
         ((GuiTextField)var1).setVisible(var2);
      } else if(var1 instanceof GuiLabel) {
         ((GuiLabel)var1).visible = var2;
      }

   }

   private Gui func_178058_a(GuiPageButtonList$GuiListEntry var1, int var2, boolean var3) {
      return (Gui)(var1 instanceof GuiPageButtonList$GuiSlideEntry?this.func_178067_a(this.width / 2 - 155 + var2, 0, (GuiPageButtonList$GuiSlideEntry)var1):(var1 instanceof GuiPageButtonList$GuiButtonEntry?this.func_178065_a(this.width / 2 - 155 + var2, 0, (GuiPageButtonList$GuiButtonEntry)var1):(var1 instanceof GuiPageButtonList$EditBoxEntry?this.func_178068_a(this.width / 2 - 155 + var2, 0, (GuiPageButtonList$EditBoxEntry)var1):(var1 instanceof GuiPageButtonList$GuiLabelEntry?this.func_178063_a(this.width / 2 - 155 + var2, 0, (GuiPageButtonList$GuiLabelEntry)var1, var3):null))));
   }

   public void func_181155_a(boolean var1) {
      for(GuiPageButtonList$GuiEntry var3 : this.field_178074_u) {
         if(GuiPageButtonList$GuiEntry.access$000(var3) instanceof GuiButton) {
            ((GuiButton)GuiPageButtonList$GuiEntry.access$000(var3)).enabled = var1;
         }

         if(GuiPageButtonList$GuiEntry.access$100(var3) instanceof GuiButton) {
            ((GuiButton)GuiPageButtonList$GuiEntry.access$100(var3)).enabled = var1;
         }
      }

   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      boolean var4 = super.mouseClicked(var1, var2, var3);
      int var5 = this.getSlotIndexFromScreenCoords(var1, var2);
      GuiPageButtonList$GuiEntry var6 = this.getListEntry(var5);
      if(this.field_178075_A != GuiPageButtonList$GuiEntry.access$200(var6) && this.field_178075_A != null && this.field_178075_A instanceof GuiTextField) {
         ((GuiTextField)this.field_178075_A).setFocused(false);
      }

      this.field_178075_A = GuiPageButtonList$GuiEntry.access$200(var6);
      return var4;
   }

   private GuiSlider func_178067_a(int var1, int var2, GuiPageButtonList$GuiSlideEntry var3) {
      GuiSlider var4 = new GuiSlider(this.field_178076_z, var3.func_178935_b(), var1, var2, var3.func_178936_c(), var3.func_178943_e(), var3.func_178944_f(), var3.func_178942_g(), var3.func_178945_a());
      var4.visible = var3.func_178934_d();
      return var4;
   }

   private GuiListButton func_178065_a(int var1, int var2, GuiPageButtonList$GuiButtonEntry var3) {
      GuiListButton var4 = new GuiListButton(this.field_178076_z, var3.func_178935_b(), var1, var2, var3.func_178936_c(), var3.func_178940_a());
      var4.visible = var3.func_178934_d();
      return var4;
   }

   private GuiTextField func_178068_a(int var1, int var2, GuiPageButtonList$EditBoxEntry var3) {
      GuiTextField var4 = new GuiTextField(var3.func_178935_b(), this.mc.fontRendererObj, var1, var2, 150, 20);
      var4.setText(var3.func_178936_c());
      var4.func_175207_a(this.field_178076_z);
      var4.setVisible(var3.func_178934_d());
      var4.func_175205_a(var3.func_178950_a());
      return var4;
   }

   private GuiLabel func_178063_a(int var1, int var2, GuiPageButtonList$GuiLabelEntry var3, boolean var4) {
      GuiLabel var5 = new GuiLabel(this.mc.fontRendererObj, var3.func_178935_b(), var1, var2, this.width - var1 * 2, 20, -1);
      var5.visible = var3.func_178934_d();
      var5.func_175202_a(var3.func_178936_c());
      var5.setCentered();
      return var5;
   }

   public void func_178062_a(char var1, int var2) {
      if(this.field_178075_A instanceof GuiTextField) {
         GuiTextField var3 = (GuiTextField)this.field_178075_A;
         if(!GuiScreen.isKeyComboCtrlV(var2)) {
            if(var2 == 15) {
               var3.setFocused(false);
               int var4 = this.field_178072_w.indexOf(this.field_178075_A);
               if(GuiScreen.isShiftKeyDown()) {
                  var4 = this.field_178072_w.size() - 1;
               } else if(var4 == this.field_178072_w.size() - 1) {
                  var4 = 0;
               } else {
                  ++var4;
               }

               this.field_178075_A = (Gui)this.field_178072_w.get(var4);
               var3 = (GuiTextField)this.field_178075_A;
               var3.setFocused(true);
               int var5 = (int)var3.yPosition + this.slotHeight;
               int var6 = (int)var3.yPosition;
               if(var5 > this.bottom) {
                  this.amountScrolled += (float)(var5 - this.bottom);
               } else if(var6 < this.top) {
                  this.amountScrolled = (float)var6;
               }
            } else {
               var3.textboxKeyTyped(var1, var2);
            }
         } else {
            String var14 = GuiScreen.getClipboardString();
            String[] var15 = var14.split(";");
            int var16 = this.field_178072_w.indexOf(this.field_178075_A);
            int var7 = var16;

            for(String var11 : var15) {
               ((GuiTextField)this.field_178072_w.get(var7)).setText(var11);
               if(var7 == this.field_178072_w.size() - 1) {
                  var7 = 0;
               } else {
                  ++var7;
               }

               if(var7 == var16) {
                  break;
               }
            }
         }
      }

   }

   public GuiPageButtonList$GuiEntry getListEntry(int var1) {
      return (GuiPageButtonList$GuiEntry)this.field_178074_u.get(var1);
   }

   public int getSize() {
      return this.field_178074_u.size();
   }

   public int getListWidth() {
      return 400;
   }

   protected int getScrollBarX() {
      return super.getScrollBarX() + 32;
   }
}
