package net.shadersmod.client;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiSlot;
import net.optifine.Lang;
import net.shadersmod.client.GuiShaders;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.Shaders;

class GuiSlotShaders extends GuiSlot {
   private ArrayList shaderslist;
   private int selectedIndex;
   private long lastClickedCached = 0L;
   final GuiShaders shadersGui;

   public GuiSlotShaders(GuiShaders var1, int var2, int var3, int var4, int var5, int var6) {
      super(var1.getMc(), var2, var3, var4, var5, var6);
      this.shadersGui = var1;
      this.updateList();
      this.amountScrolled = 0.0F;
      int var7 = this.selectedIndex * var6;
      int var8 = (var5 - var4) / 2;
      if(var7 > var8) {
         this.scrollBy(var7 - var8);
      }

   }

   public int getListWidth() {
      return this.width - 20;
   }

   public void updateList() {
      this.shaderslist = Shaders.listOfShaders();
      ShaderOption.p();
      this.selectedIndex = 0;
      int var2 = 0;
      int var3 = this.shaderslist.size();
      if(var2 < var3) {
         if(((String)this.shaderslist.get(var2)).equals(Shaders.currentshadername)) {
            this.selectedIndex = var2;
         }

         ++var2;
      }

   }

   protected int getSize() {
      return this.shaderslist.size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      String[] var5 = ShaderOption.p();
      if(var1 != this.selectedIndex || this.lastClicked != this.lastClickedCached) {
         this.selectedIndex = var1;
         this.lastClickedCached = this.lastClicked;
         Shaders.setShaderPack((String)this.shaderslist.get(var1));
         Shaders.uninit();
         this.shadersGui.updateButtons();
      }

   }

   protected boolean isSelected(int var1) {
      String[] var2 = ShaderOption.p();
      return var1 == this.selectedIndex;
   }

   protected int getScrollBarX() {
      return this.width - 6;
   }

   protected int getContentHeight() {
      return this.getSize() * 18;
   }

   protected void drawBackground() {
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      ShaderOption.p();
      String var8 = (String)this.shaderslist.get(var1);
      if(var8.equals(Shaders.packNameNone)) {
         var8 = Lang.get("of.options.shaders.packNone");
      }

      if(var8.equals(Shaders.packNameDefault)) {
         var8 = Lang.get("of.options.shaders.packDefault");
      }

      this.shadersGui.drawCenteredString(var8, this.width / 2, var3 + 1, 16777215);
   }

   public int getSelectedIndex() {
      return this.selectedIndex;
   }
}
