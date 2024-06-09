/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public abstract class GuiListExtended
/*    */   extends GuiSlot
/*    */ {
/*    */   public GuiListExtended(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/*  9 */     super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isSelected(int slotIndex) {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawBackground() {}
/*    */ 
/*    */   
/*    */   protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 33 */     getListEntry(entryID).drawEntry(entryID, p_180791_2_, p_180791_3_, getListWidth(), p_180791_4_, mouseXIn, mouseYIn, (getSlotIndexFromScreenCoords(mouseXIn, mouseYIn) == entryID));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_) {
/* 38 */     getListEntry(p_178040_1_).setSelected(p_178040_1_, p_178040_2_, p_178040_3_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
/* 43 */     if (isMouseYWithinSlotBounds(mouseY)) {
/*    */       
/* 45 */       int i = getSlotIndexFromScreenCoords(mouseX, mouseY);
/*    */       
/* 47 */       if (i >= 0) {
/*    */         
/* 49 */         int j = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 50 */         int k = this.top + 4 - getAmountScrolled() + i * this.slotHeight + this.headerPadding;
/* 51 */         int l = mouseX - j;
/* 52 */         int i1 = mouseY - k;
/*    */         
/* 54 */         if (getListEntry(i).mousePressed(i, mouseX, mouseY, mouseEvent, l, i1)) {
/*    */           
/* 56 */           setEnabled(false);
/* 57 */           return true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseReleased(int p_148181_1_, int p_148181_2_, int p_148181_3_) {
/* 67 */     for (int i = 0; i < getSize(); i++) {
/*    */       
/* 69 */       int j = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 70 */       int k = this.top + 4 - getAmountScrolled() + i * this.slotHeight + this.headerPadding;
/* 71 */       int l = p_148181_1_ - j;
/* 72 */       int i1 = p_148181_2_ - k;
/* 73 */       getListEntry(i).mouseReleased(i, p_148181_1_, p_148181_2_, p_148181_3_, l, i1);
/*    */     } 
/*    */     
/* 76 */     setEnabled(true);
/* 77 */     return false;
/*    */   }
/*    */   
/*    */   public abstract IGuiListEntry getListEntry(int paramInt);
/*    */   
/*    */   public static interface IGuiListEntry {
/*    */     void setSelected(int param1Int1, int param1Int2, int param1Int3);
/*    */     
/*    */     void drawEntry(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, boolean param1Boolean);
/*    */     
/*    */     boolean mousePressed(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6);
/*    */     
/*    */     void mouseReleased(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiListExtended.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */