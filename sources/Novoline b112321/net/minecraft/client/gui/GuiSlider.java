package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList$GuiResponder;
import net.minecraft.client.gui.GuiSlider$FormatHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider extends GuiButton {
   private float sliderPosition = 1.0F;
   public boolean isMouseDown;
   private String name;
   private final float min;
   private final float max;
   private final GuiPageButtonList$GuiResponder responder;
   private GuiSlider$FormatHelper formatHelper;

   public GuiSlider(GuiPageButtonList$GuiResponder var1, int var2, int var3, int var4, String var5, float var6, float var7, float var8, GuiSlider$FormatHelper var9) {
      super(var2, var3, var4, 150, 20, "");
      this.name = var5;
      this.min = var6;
      this.max = var7;
      this.sliderPosition = (var8 - var6) / (var7 - var6);
      this.formatHelper = var9;
      this.responder = var1;
      this.displayString = this.getDisplayString();
   }

   public float func_175220_c() {
      return this.min + (this.max - this.min) * this.sliderPosition;
   }

   public void func_175218_a(float var1, boolean var2) {
      this.sliderPosition = (var1 - this.min) / (this.max - this.min);
      this.displayString = this.getDisplayString();
      this.responder.onTick(this.id, this.func_175220_c());
   }

   public float func_175217_d() {
      return this.sliderPosition;
   }

   public String getDisplayString() {
      return this.formatHelper == null?I18n.format(this.name, new Object[0]) + ": " + this.func_175220_c():this.formatHelper.getText(this.id, I18n.format(this.name, new Object[0]), this.func_175220_c());
   }

   protected int getHoverState(boolean var1) {
      return 0;
   }

   protected void mouseDragged(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         if(this.isMouseDown) {
            this.sliderPosition = (float)((double)var2 - (this.xPosition + 4.0D)) / (float)(this.width - 8);
            if(this.sliderPosition < 0.0F) {
               this.sliderPosition = 0.0F;
            }

            if(this.sliderPosition > 1.0F) {
               this.sliderPosition = 1.0F;
            }

            this.displayString = this.getDisplayString();
            this.responder.onTick(this.id, this.func_175220_c());
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect((int)this.xPosition + (int)(this.sliderPosition * (float)(this.width - 8)), (int)this.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect((int)this.xPosition + (int)(this.sliderPosition * (float)(this.width - 8)) + 4, (int)this.yPosition, 196, 66, 4, 20);
      }

   }

   public void func_175219_a(float var1) {
      this.sliderPosition = var1;
      this.displayString = this.getDisplayString();
      this.responder.onTick(this.id, this.func_175220_c());
   }

   public boolean mousePressed(Minecraft var1, int var2, int var3) {
      if(super.mousePressed(var1, var2, var3)) {
         this.sliderPosition = (float)((double)var2 - (this.xPosition + 4.0D)) / (float)(this.width - 8);
         if(this.sliderPosition < 0.0F) {
            this.sliderPosition = 0.0F;
         }

         if(this.sliderPosition > 1.0F) {
            this.sliderPosition = 1.0F;
         }

         this.displayString = this.getDisplayString();
         this.responder.onTick(this.id, this.func_175220_c());
         this.isMouseDown = true;
         return true;
      } else {
         return false;
      }
   }

   public void mouseReleased(int var1, int var2) {
      this.isMouseDown = false;
   }
}
