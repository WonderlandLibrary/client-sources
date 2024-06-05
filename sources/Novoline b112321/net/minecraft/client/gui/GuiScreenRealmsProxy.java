package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.aYC;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonRealmsProxy;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsScreen;

public class GuiScreenRealmsProxy extends GuiScreen {
   private RealmsScreen field_154330_a;

   public GuiScreenRealmsProxy(RealmsScreen var1) {
      this.field_154330_a = var1;
      super.buttonList = Collections.synchronizedList(Lists.newArrayList());
   }

   public RealmsScreen func_154321_a() {
      return this.field_154330_a;
   }

   public void initGui() {
      this.field_154330_a.init();
      super.initGui();
   }

   public void func_154325_a(String var1, int var2, int var3, int var4) {
      super.drawCenteredString(this.fontRendererObj, var1, var2, var3, var4);
   }

   public void func_154322_b(String var1, int var2, int var3, int var4) {
      super.drawString(this.fontRendererObj, var1, var2, var3, var4);
   }

   public void drawTexturedModalRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      aYC.a(this.field_154330_a, var1, var2, var3, var4, var5, var6);
      super.drawTexturedModalRect(var1, var2, var3, var4, var5, var6);
   }

   public void drawGradientRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      super.drawGradientRect((float)var1, (float)var2, (float)var3, (float)var4, var5, var6);
   }

   public void drawDefaultBackground() {
      super.drawDefaultBackground();
   }

   public boolean doesGuiPauseGame() {
      return super.doesGuiPauseGame();
   }

   public void drawWorldBackground(int var1) {
      super.drawWorldBackground(var1);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.field_154330_a.render(var1, var2, var3);
   }

   public void renderToolTip(ItemStack var1, int var2, int var3) {
      super.renderToolTip(var1, var2, var3);
   }

   public void drawCreativeTabHoveringText(String var1, int var2, int var3) {
      super.drawCreativeTabHoveringText(var1, var2, var3);
   }

   public void drawHoveringText(List var1, int var2, int var3) {
      super.drawHoveringText(var1, var2, var3);
   }

   public void updateScreen() {
      this.field_154330_a.tick();
      super.updateScreen();
   }

   public int func_154329_h() {
      return this.fontRendererObj.getHeight();
   }

   public int func_154326_c(String var1) {
      return this.fontRendererObj.d(var1);
   }

   public void func_154319_c(String var1, int var2, int var3, int var4) {
      this.fontRendererObj.drawStringWithShadow(var1, (float)var2, (float)var3, var4);
   }

   public List func_154323_a(String var1, int var2) {
      return this.fontRendererObj.listFormattedStringToWidth(var1, var2);
   }

   public final void actionPerformed(GuiButton var1) throws IOException {
      this.field_154330_a.buttonClicked(((GuiButtonRealmsProxy)var1).getRealmsButton());
   }

   public void func_154324_i() {
      super.buttonList.clear();
   }

   public void func_154327_a(RealmsButton var1) {
      super.buttonList.add(var1.getProxy());
   }

   public List func_154320_j() {
      ArrayList var1 = Lists.newArrayListWithExpectedSize(super.buttonList.size());

      for(GuiButton var3 : super.buttonList) {
         var1.add(((GuiButtonRealmsProxy)var3).getRealmsButton());
      }

      return var1;
   }

   public void func_154328_b(RealmsButton var1) {
      super.buttonList.remove(var1);
   }

   public void mouseClicked(int var1, int var2, int var3) throws IOException {
      this.field_154330_a.mouseClicked(var1, var2, var3);
      super.mouseClicked(var1, var2, var3);
   }

   public void handleMouseInput() throws IOException {
      this.field_154330_a.mouseEvent();
      super.handleMouseInput();
   }

   public void handleKeyboardInput() throws IOException {
      this.field_154330_a.keyboardEvent();
      super.handleKeyboardInput();
   }

   public void mouseReleased(int var1, int var2, int var3) {
      this.field_154330_a.mouseReleased(var1, var2, var3);
   }

   public void mouseClickMove(int var1, int var2, int var3, long var4) {
      this.field_154330_a.mouseDragged(var1, var2, var3, var4);
   }

   public void keyTyped(char var1, int var2) throws IOException {
      this.field_154330_a.keyPressed(var1, var2);
   }

   public void confirmClicked(boolean var1, int var2) {
      this.field_154330_a.confirmResult(var1, var2);
   }

   public void onGuiClosed() {
      this.field_154330_a.removed();
      super.onGuiClosed();
   }
}
