/*    */ package net.minecraft.client.gui.inventory;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerBrewingStand;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiBrewingStand extends GuiContainer {
/* 11 */   private static final ResourceLocation brewingStandGuiTextures = new ResourceLocation("textures/gui/container/brewing_stand.png");
/*    */   
/*    */   private final InventoryPlayer playerInventory;
/*    */   
/*    */   private IInventory tileBrewingStand;
/*    */ 
/*    */   
/*    */   public GuiBrewingStand(InventoryPlayer playerInv, IInventory p_i45506_2_) {
/* 19 */     super((Container)new ContainerBrewingStand(playerInv, p_i45506_2_));
/* 20 */     this.playerInventory = playerInv;
/* 21 */     this.tileBrewingStand = p_i45506_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 29 */     String s = this.tileBrewingStand.getDisplayName().getUnformattedText();
/* 30 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/* 31 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 39 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 40 */     this.mc.getTextureManager().bindTexture(brewingStandGuiTextures);
/* 41 */     int i = (this.width - this.xSize) / 2;
/* 42 */     int j = (this.height - this.ySize) / 2;
/* 43 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 44 */     int k = this.tileBrewingStand.getField(0);
/*    */     
/* 46 */     if (k > 0) {
/*    */       
/* 48 */       int l = (int)(28.0F * (1.0F - k / 400.0F));
/*    */       
/* 50 */       if (l > 0)
/*    */       {
/* 52 */         drawTexturedModalRect(i + 97, j + 16, 176, 0, 9, l);
/*    */       }
/*    */       
/* 55 */       int i1 = k / 2 % 7;
/*    */       
/* 57 */       switch (i1) {
/*    */         
/*    */         case 0:
/* 60 */           l = 29;
/*    */           break;
/*    */         
/*    */         case 1:
/* 64 */           l = 24;
/*    */           break;
/*    */         
/*    */         case 2:
/* 68 */           l = 20;
/*    */           break;
/*    */         
/*    */         case 3:
/* 72 */           l = 16;
/*    */           break;
/*    */         
/*    */         case 4:
/* 76 */           l = 11;
/*    */           break;
/*    */         
/*    */         case 5:
/* 80 */           l = 6;
/*    */           break;
/*    */         
/*    */         case 6:
/* 84 */           l = 0;
/*    */           break;
/*    */       } 
/* 87 */       if (l > 0)
/*    */       {
/* 89 */         drawTexturedModalRect(i + 65, j + 14 + 29 - l, 185, 29 - l, 12, l);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\inventory\GuiBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */