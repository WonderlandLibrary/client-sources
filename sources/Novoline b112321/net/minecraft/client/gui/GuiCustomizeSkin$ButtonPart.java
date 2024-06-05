package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCustomizeSkin;
import net.minecraft.client.gui.GuiCustomizeSkin$1;
import net.minecraft.entity.player.EnumPlayerModelParts;

class GuiCustomizeSkin$ButtonPart extends GuiButton {
   private final EnumPlayerModelParts playerModelParts;
   final GuiCustomizeSkin this$0;

   private GuiCustomizeSkin$ButtonPart(GuiCustomizeSkin var1, int var2, int var3, int var4, int var5, int var6, EnumPlayerModelParts var7) {
      super(var2, var3, var4, var5, var6, GuiCustomizeSkin.access$200(var1, var7));
      this.this$0 = var1;
      this.playerModelParts = var7;
   }

   GuiCustomizeSkin$ButtonPart(GuiCustomizeSkin var1, int var2, int var3, int var4, int var5, int var6, EnumPlayerModelParts var7, GuiCustomizeSkin$1 var8) {
      this(var1, var2, var3, var4, var5, var6, var7);
   }

   static EnumPlayerModelParts access$100(GuiCustomizeSkin$ButtonPart var0) {
      return var0.playerModelParts;
   }
}
