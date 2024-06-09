/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.realms.RealmsScrolledSelectionList;
/*    */ 
/*    */ public class GuiSlotRealmsProxy
/*    */   extends GuiSlot
/*    */ {
/*    */   private final RealmsScrolledSelectionList selectionList;
/*    */   
/*    */   public GuiSlotRealmsProxy(RealmsScrolledSelectionList selectionListIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/* 12 */     super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/* 13 */     this.selectionList = selectionListIn;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSize() {
/* 18 */     return this.selectionList.getItemCount();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 26 */     this.selectionList.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isSelected(int slotIndex) {
/* 34 */     return this.selectionList.isSelectedItem(slotIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawBackground() {
/* 39 */     this.selectionList.renderBackground();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 44 */     this.selectionList.renderItem(entryID, p_180791_2_, p_180791_3_, p_180791_4_, mouseXIn, mouseYIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_154338_k() {
/* 49 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_154339_l() {
/* 54 */     return this.mouseY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_154337_m() {
/* 59 */     return this.mouseX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getContentHeight() {
/* 67 */     return this.selectionList.getMaxPosition();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getScrollBarX() {
/* 72 */     return this.selectionList.getScrollbarPosition();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMouseInput() {
/* 77 */     super.handleMouseInput();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiSlotRealmsProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */