/*    */ package net.minecraft.client.gui.inventory;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerHorseInventory;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiScreenHorseInventory extends GuiContainer {
/* 12 */   private static final ResourceLocation horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
/*    */ 
/*    */   
/*    */   private IInventory playerInventory;
/*    */ 
/*    */   
/*    */   private IInventory horseInventory;
/*    */ 
/*    */   
/*    */   private EntityHorse horseEntity;
/*    */ 
/*    */   
/*    */   private float mousePosx;
/*    */ 
/*    */   
/*    */   private float mousePosY;
/*    */ 
/*    */   
/*    */   public GuiScreenHorseInventory(IInventory playerInv, IInventory horseInv, EntityHorse horse) {
/* 31 */     super((Container)new ContainerHorseInventory(playerInv, horseInv, horse, (EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/* 32 */     this.playerInventory = playerInv;
/* 33 */     this.horseInventory = horseInv;
/* 34 */     this.horseEntity = horse;
/* 35 */     this.allowUserInput = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 43 */     this.fontRendererObj.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
/* 44 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 52 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 53 */     this.mc.getTextureManager().bindTexture(horseGuiTextures);
/* 54 */     int i = (this.width - this.xSize) / 2;
/* 55 */     int j = (this.height - this.ySize) / 2;
/* 56 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */     
/* 58 */     if (this.horseEntity.isChested())
/*    */     {
/* 60 */       drawTexturedModalRect(i + 79, j + 17, 0, this.ySize, 90, 54);
/*    */     }
/*    */     
/* 63 */     if (this.horseEntity.canWearArmor())
/*    */     {
/* 65 */       drawTexturedModalRect(i + 7, j + 35, 0, this.ySize + 54, 18, 18);
/*    */     }
/*    */     
/* 68 */     GuiInventory.drawEntityOnScreen(i + 51, j + 60, 17, (i + 51) - this.mousePosx, (j + 75 - 50) - this.mousePosY, (EntityLivingBase)this.horseEntity);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 76 */     this.mousePosx = mouseX;
/* 77 */     this.mousePosY = mouseY;
/* 78 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\inventory\GuiScreenHorseInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */