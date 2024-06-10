/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  5:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  8:   */ import net.minecraft.inventory.ContainerHopper;
/*  9:   */ import net.minecraft.inventory.IInventory;
/* 10:   */ import net.minecraft.util.ResourceLocation;
/* 11:   */ import org.lwjgl.opengl.GL11;
/* 12:   */ 
/* 13:   */ public class GuiHopper
/* 14:   */   extends GuiContainer
/* 15:   */ {
/* 16:13 */   private static final ResourceLocation field_147085_u = new ResourceLocation("textures/gui/container/hopper.png");
/* 17:   */   private IInventory field_147084_v;
/* 18:   */   private IInventory field_147083_w;
/* 19:   */   private static final String __OBFID = "CL_00000759";
/* 20:   */   
/* 21:   */   public GuiHopper(InventoryPlayer par1InventoryPlayer, IInventory par2IInventory)
/* 22:   */   {
/* 23:20 */     super(new ContainerHopper(par1InventoryPlayer, par2IInventory));
/* 24:21 */     this.field_147084_v = par1InventoryPlayer;
/* 25:22 */     this.field_147083_w = par2IInventory;
/* 26:23 */     this.field_146291_p = false;
/* 27:24 */     this.field_147000_g = 133;
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/* 31:   */   {
/* 32:29 */     this.fontRendererObj.drawString(this.field_147083_w.isInventoryNameLocalized() ? this.field_147083_w.getInventoryName() : I18n.format(this.field_147083_w.getInventoryName(), new Object[0]), 8, 6, 4210752);
/* 33:30 */     this.fontRendererObj.drawString(this.field_147084_v.isInventoryNameLocalized() ? this.field_147084_v.getInventoryName() : I18n.format(this.field_147084_v.getInventoryName(), new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 37:   */   {
/* 38:35 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 39:36 */     this.mc.getTextureManager().bindTexture(field_147085_u);
/* 40:37 */     int var4 = (width - this.field_146999_f) / 2;
/* 41:38 */     int var5 = (height - this.field_147000_g) / 2;
/* 42:39 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiHopper
 * JD-Core Version:    0.7.0.1
 */