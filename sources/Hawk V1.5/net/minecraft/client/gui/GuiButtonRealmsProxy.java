package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsButton;

public class GuiButtonRealmsProxy extends GuiButton {
   private static final String __OBFID = "CL_00001848";
   private RealmsButton field_154318_o;

   public int getPositionY() {
      return super.yPosition;
   }

   public boolean mousePressed(Minecraft var1, int var2, int var3) {
      if (super.mousePressed(var1, var2, var3)) {
         this.field_154318_o.clicked(var2, var3);
      }

      return super.mousePressed(var1, var2, var3);
   }

   public int getId() {
      return super.id;
   }

   public void setEnabled(boolean var1) {
      super.enabled = var1;
   }

   public GuiButtonRealmsProxy(RealmsButton var1, int var2, int var3, int var4, String var5, int var6, int var7) {
      super(var2, var3, var4, var6, var7, var5);
      this.field_154318_o = var1;
   }

   public void setText(String var1) {
      super.displayString = var1;
   }

   public int func_175232_g() {
      return this.height;
   }

   public void mouseDragged(Minecraft var1, int var2, int var3) {
      this.field_154318_o.renderBg(var2, var3);
   }

   public GuiButtonRealmsProxy(RealmsButton var1, int var2, int var3, int var4, String var5) {
      super(var2, var3, var4, var5);
      this.field_154318_o = var1;
   }

   public boolean getEnabled() {
      return super.enabled;
   }

   public void mouseReleased(int var1, int var2) {
      this.field_154318_o.released(var1, var2);
   }

   public int getButtonWidth() {
      return super.getButtonWidth();
   }

   public int getHoverState(boolean var1) {
      return this.field_154318_o.getYImage(var1);
   }

   public RealmsButton getRealmsButton() {
      return this.field_154318_o;
   }

   public int func_154312_c(boolean var1) {
      return super.getHoverState(var1);
   }
}
