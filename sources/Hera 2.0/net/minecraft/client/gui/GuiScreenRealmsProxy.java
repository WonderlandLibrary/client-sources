/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.realms.RealmsButton;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ 
/*     */ public class GuiScreenRealmsProxy
/*     */   extends GuiScreen
/*     */ {
/*     */   private RealmsScreen field_154330_a;
/*     */   
/*     */   public GuiScreenRealmsProxy(RealmsScreen p_i1087_1_) {
/*  17 */     this.field_154330_a = p_i1087_1_;
/*  18 */     this.buttonList = Collections.synchronizedList(Lists.newArrayList());
/*     */   }
/*     */ 
/*     */   
/*     */   public RealmsScreen func_154321_a() {
/*  23 */     return this.field_154330_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  32 */     this.field_154330_a.init();
/*  33 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_154325_a(String p_154325_1_, int p_154325_2_, int p_154325_3_, int p_154325_4_) {
/*  38 */     drawCenteredString(this.fontRendererObj, p_154325_1_, p_154325_2_, p_154325_3_, p_154325_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_154322_b(String p_154322_1_, int p_154322_2_, int p_154322_3_, int p_154322_4_) {
/*  43 */     drawString(this.fontRendererObj, p_154322_1_, p_154322_2_, p_154322_3_, p_154322_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/*  51 */     this.field_154330_a.blit(x, y, textureX, textureY, width, height);
/*  52 */     super.drawTexturedModalRect(x, y, textureX, textureY, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
/*  61 */     super.drawGradientRect(left, top, right, bottom, startColor, endColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {
/*  69 */     super.drawDefaultBackground();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  77 */     return super.doesGuiPauseGame();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawWorldBackground(int tint) {
/*  82 */     super.drawWorldBackground(tint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  90 */     this.field_154330_a.render(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderToolTip(ItemStack stack, int x, int y) {
/*  95 */     super.renderToolTip(stack, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
/* 104 */     super.drawCreativeTabHoveringText(tabName, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawHoveringText(List<String> textLines, int x, int y) {
/* 112 */     super.drawHoveringText(textLines, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 120 */     this.field_154330_a.tick();
/* 121 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_154329_h() {
/* 126 */     return this.fontRendererObj.FONT_HEIGHT;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_154326_c(String p_154326_1_) {
/* 131 */     return this.fontRendererObj.getStringWidth(p_154326_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_154319_c(String p_154319_1_, int p_154319_2_, int p_154319_3_, int p_154319_4_) {
/* 136 */     this.fontRendererObj.drawStringWithShadow(p_154319_1_, p_154319_2_, p_154319_3_, p_154319_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> func_154323_a(String p_154323_1_, int p_154323_2_) {
/* 141 */     return this.fontRendererObj.listFormattedStringToWidth(p_154323_1_, p_154323_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void actionPerformed(GuiButton button) throws IOException {
/* 149 */     this.field_154330_a.buttonClicked(((GuiButtonRealmsProxy)button).getRealmsButton());
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_154324_i() {
/* 154 */     this.buttonList.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_154327_a(RealmsButton p_154327_1_) {
/* 159 */     this.buttonList.add(p_154327_1_.getProxy());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<RealmsButton> func_154320_j() {
/* 164 */     List<RealmsButton> list = Lists.newArrayListWithExpectedSize(this.buttonList.size());
/*     */     
/* 166 */     for (GuiButton guibutton : this.buttonList)
/*     */     {
/* 168 */       list.add(((GuiButtonRealmsProxy)guibutton).getRealmsButton());
/*     */     }
/*     */     
/* 171 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_154328_b(RealmsButton p_154328_1_) {
/* 176 */     this.buttonList.remove(p_154328_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 184 */     this.field_154330_a.mouseClicked(mouseX, mouseY, mouseButton);
/* 185 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 193 */     this.field_154330_a.mouseEvent();
/* 194 */     super.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyboardInput() throws IOException {
/* 202 */     this.field_154330_a.keyboardEvent();
/* 203 */     super.handleKeyboardInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 211 */     this.field_154330_a.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 220 */     this.field_154330_a.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) throws IOException {
/* 229 */     this.field_154330_a.keyPressed(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 234 */     this.field_154330_a.confirmResult(result, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 242 */     this.field_154330_a.removed();
/* 243 */     super.onGuiClosed();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreenRealmsProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */