/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.network.LanServerDetector;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class ServerListEntryLanDetected
/*    */   implements GuiListExtended.IGuiListEntry {
/*    */   private final GuiMultiplayer field_148292_c;
/*    */   protected final Minecraft mc;
/*    */   protected final LanServerDetector.LanServer field_148291_b;
/* 12 */   private long field_148290_d = 0L;
/*    */ 
/*    */   
/*    */   protected ServerListEntryLanDetected(GuiMultiplayer p_i45046_1_, LanServerDetector.LanServer p_i45046_2_) {
/* 16 */     this.field_148292_c = p_i45046_1_;
/* 17 */     this.field_148291_b = p_i45046_2_;
/* 18 */     this.mc = Minecraft.getMinecraft();
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/* 23 */     this.mc.fontRendererObj.drawString(I18n.format("lanServer.title", new Object[0]), x + 32 + 3, y + 1, 16777215);
/* 24 */     this.mc.fontRendererObj.drawString(this.field_148291_b.getServerMotd(), x + 32 + 3, y + 12, 8421504);
/*    */     
/* 26 */     if (this.mc.gameSettings.hideServerAddress) {
/*    */       
/* 28 */       this.mc.fontRendererObj.drawString(I18n.format("selectServer.hiddenAddress", new Object[0]), x + 32 + 3, y + 12 + 11, 3158064);
/*    */     }
/*    */     else {
/*    */       
/* 32 */       this.mc.fontRendererObj.drawString(this.field_148291_b.getServerIpPort(), x + 32 + 3, y + 12 + 11, 3158064);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 41 */     this.field_148292_c.selectServer(slotIndex);
/*    */     
/* 43 */     if (Minecraft.getSystemTime() - this.field_148290_d < 250L)
/*    */     {
/* 45 */       this.field_148292_c.connectToSelected();
/*    */     }
/*    */     
/* 48 */     this.field_148290_d = Minecraft.getSystemTime();
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public LanServerDetector.LanServer getLanServer() {
/* 65 */     return this.field_148291_b;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\ServerListEntryLanDetected.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */