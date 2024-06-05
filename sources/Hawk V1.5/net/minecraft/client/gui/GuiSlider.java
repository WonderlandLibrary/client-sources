package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider extends GuiButton {
   private GuiSlider.FormatHelper field_175222_u;
   private float field_175227_p = 1.0F;
   private String field_175226_q;
   private final float field_175224_s;
   private static final String __OBFID = "CL_00001954";
   public boolean field_175228_o;
   private final float field_175225_r;
   private final GuiPageButtonList.GuiResponder field_175223_t;

   public float func_175217_d() {
      return this.field_175227_p;
   }

   public boolean mousePressed(Minecraft var1, int var2, int var3) {
      if (super.mousePressed(var1, var2, var3)) {
         this.field_175227_p = (float)(var2 - (this.xPosition + 4)) / (float)(this.width - 8);
         if (this.field_175227_p < 0.0F) {
            this.field_175227_p = 0.0F;
         }

         if (this.field_175227_p > 1.0F) {
            this.field_175227_p = 1.0F;
         }

         this.displayString = this.func_175221_e();
         this.field_175223_t.func_175320_a(this.id, this.func_175220_c());
         this.field_175228_o = true;
         return true;
      } else {
         return false;
      }
   }

   protected void mouseDragged(Minecraft var1, int var2, int var3) {
      if (this.visible) {
         if (this.field_175228_o) {
            this.field_175227_p = (float)(var2 - (this.xPosition + 4)) / (float)(this.width - 8);
            if (this.field_175227_p < 0.0F) {
               this.field_175227_p = 0.0F;
            }

            if (this.field_175227_p > 1.0F) {
               this.field_175227_p = 1.0F;
            }

            this.displayString = this.func_175221_e();
            this.field_175223_t.func_175320_a(this.id, this.func_175220_c());
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(this.xPosition + (int)(this.field_175227_p * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(this.xPosition + (int)(this.field_175227_p * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
      }

   }

   public GuiSlider(GuiPageButtonList.GuiResponder var1, int var2, int var3, int var4, String var5, float var6, float var7, float var8, GuiSlider.FormatHelper var9) {
      super(var2, var3, var4, 150, 20, "");
      this.field_175226_q = var5;
      this.field_175225_r = var6;
      this.field_175224_s = var7;
      this.field_175227_p = (var8 - var6) / (var7 - var6);
      this.field_175222_u = var9;
      this.field_175223_t = var1;
      this.displayString = this.func_175221_e();
   }

   private String func_175221_e() {
      return this.field_175222_u == null ? String.valueOf((new StringBuilder(String.valueOf(I18n.format(this.field_175226_q)))).append(": ").append(this.func_175220_c())) : this.field_175222_u.func_175318_a(this.id, I18n.format(this.field_175226_q), this.func_175220_c());
   }

   public void func_175218_a(float var1, boolean var2) {
      this.field_175227_p = (var1 - this.field_175225_r) / (this.field_175224_s - this.field_175225_r);
      this.displayString = this.func_175221_e();
      if (var2) {
         this.field_175223_t.func_175320_a(this.id, this.func_175220_c());
      }

   }

   public void func_175219_a(float var1) {
      this.field_175227_p = var1;
      this.displayString = this.func_175221_e();
      this.field_175223_t.func_175320_a(this.id, this.func_175220_c());
   }

   public void mouseReleased(int var1, int var2) {
      this.field_175228_o = false;
   }

   protected int getHoverState(boolean var1) {
      return 0;
   }

   public float func_175220_c() {
      return this.field_175225_r + (this.field_175224_s - this.field_175225_r) * this.field_175227_p;
   }

   public interface FormatHelper {
      String func_175318_a(int var1, String var2, float var3);
   }
}
