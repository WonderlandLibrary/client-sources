/*    */ package net.minecraft.client.gui.inventory;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiChest extends GuiContainer {
/* 12 */   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
/*    */ 
/*    */   
/*    */   private IInventory upperChestInventory;
/*    */   
/*    */   private IInventory lowerChestInventory;
/*    */   
/*    */   private int inventoryRows;
/*    */ 
/*    */   
/*    */   public GuiChest(IInventory upperInv, IInventory lowerInv) {
/* 23 */     super((Container)new ContainerChest(upperInv, lowerInv, (EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/* 24 */     this.upperChestInventory = upperInv;
/* 25 */     this.lowerChestInventory = lowerInv;
/* 26 */     this.allowUserInput = false;
/* 27 */     int i = 222;
/* 28 */     int j = i - 108;
/* 29 */     this.inventoryRows = lowerInv.getSizeInventory() / 9;
/* 30 */     this.ySize = j + this.inventoryRows * 18;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 38 */     this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
/* 39 */     this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 47 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 48 */     this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
/* 49 */     int i = (this.width - this.xSize) / 2;
/* 50 */     int j = (this.height - this.ySize) / 2;
/* 51 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
/* 52 */     drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\inventory\GuiChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */