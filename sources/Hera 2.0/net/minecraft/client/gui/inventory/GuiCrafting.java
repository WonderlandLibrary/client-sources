/*    */ package net.minecraft.client.gui.inventory;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerWorkbench;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class GuiCrafting extends GuiContainer {
/* 13 */   private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");
/*    */ 
/*    */   
/*    */   public GuiCrafting(InventoryPlayer playerInv, World worldIn) {
/* 17 */     this(playerInv, worldIn, BlockPos.ORIGIN);
/*    */   }
/*    */ 
/*    */   
/*    */   public GuiCrafting(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition) {
/* 22 */     super((Container)new ContainerWorkbench(playerInv, worldIn, blockPosition));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 30 */     this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
/* 31 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 39 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 40 */     this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
/* 41 */     int i = (this.width - this.xSize) / 2;
/* 42 */     int j = (this.height - this.ySize) / 2;
/* 43 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\inventory\GuiCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */