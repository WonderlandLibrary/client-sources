/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerHopper;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiHopper extends GuiContainer {
/* 14 */   private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
/*    */ 
/*    */   
/*    */   private IInventory playerInventory;
/*    */ 
/*    */   
/*    */   private IInventory hopperInventory;
/*    */ 
/*    */   
/*    */   public GuiHopper(InventoryPlayer playerInv, IInventory hopperInv) {
/* 24 */     super((Container)new ContainerHopper(playerInv, hopperInv, (EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/* 25 */     this.playerInventory = (IInventory)playerInv;
/* 26 */     this.hopperInventory = hopperInv;
/* 27 */     this.allowUserInput = false;
/* 28 */     this.ySize = 133;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 36 */     this.fontRendererObj.drawString(this.hopperInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
/* 37 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 45 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 46 */     this.mc.getTextureManager().bindTexture(HOPPER_GUI_TEXTURE);
/* 47 */     int i = (this.width - this.xSize) / 2;
/* 48 */     int j = (this.height - this.ySize) / 2;
/* 49 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */