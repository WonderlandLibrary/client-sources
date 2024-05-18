/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*    */ 
/*    */ public class GuiDownloadTerrain
/*    */   extends GuiScreen {
/*    */   private NetHandlerPlayClient netHandlerPlayClient;
/*    */   private int progress;
/*    */   
/*    */   public GuiDownloadTerrain(NetHandlerPlayClient netHandler) {
/* 15 */     this.netHandlerPlayClient = netHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 32 */     this.buttonList.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 40 */     this.progress++;
/*    */     
/* 42 */     if (this.progress % 20 == 0)
/*    */     {
/* 44 */       this.netHandlerPlayClient.addToSendQueue((Packet)new C00PacketKeepAlive());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 53 */     drawBackground(0);
/* 54 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
/* 55 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 63 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiDownloadTerrain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */