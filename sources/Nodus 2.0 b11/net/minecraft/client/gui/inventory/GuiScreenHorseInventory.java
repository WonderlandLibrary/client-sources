/*  1:   */ package net.minecraft.client.gui.inventory;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.FontRenderer;
/*  5:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ import net.minecraft.entity.passive.EntityHorse;
/*  8:   */ import net.minecraft.inventory.ContainerHorseInventory;
/*  9:   */ import net.minecraft.inventory.IInventory;
/* 10:   */ import net.minecraft.util.ResourceLocation;
/* 11:   */ import org.lwjgl.opengl.GL11;
/* 12:   */ 
/* 13:   */ public class GuiScreenHorseInventory
/* 14:   */   extends GuiContainer
/* 15:   */ {
/* 16:12 */   private static final ResourceLocation field_147031_u = new ResourceLocation("textures/gui/container/horse.png");
/* 17:   */   private IInventory field_147030_v;
/* 18:   */   private IInventory field_147029_w;
/* 19:   */   private EntityHorse field_147034_x;
/* 20:   */   private float field_147033_y;
/* 21:   */   private float field_147032_z;
/* 22:   */   private static final String __OBFID = "CL_00000760";
/* 23:   */   
/* 24:   */   public GuiScreenHorseInventory(IInventory par1IInventory, IInventory par2IInventory, EntityHorse par3EntityHorse)
/* 25:   */   {
/* 26:22 */     super(new ContainerHorseInventory(par1IInventory, par2IInventory, par3EntityHorse));
/* 27:23 */     this.field_147030_v = par1IInventory;
/* 28:24 */     this.field_147029_w = par2IInventory;
/* 29:25 */     this.field_147034_x = par3EntityHorse;
/* 30:26 */     this.field_146291_p = false;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/* 34:   */   {
/* 35:31 */     this.fontRendererObj.drawString(this.field_147029_w.isInventoryNameLocalized() ? this.field_147029_w.getInventoryName() : I18n.format(this.field_147029_w.getInventoryName(), new Object[0]), 8, 6, 4210752);
/* 36:32 */     this.fontRendererObj.drawString(this.field_147030_v.isInventoryNameLocalized() ? this.field_147030_v.getInventoryName() : I18n.format(this.field_147030_v.getInventoryName(), new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 40:   */   {
/* 41:37 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 42:38 */     this.mc.getTextureManager().bindTexture(field_147031_u);
/* 43:39 */     int var4 = (width - this.field_146999_f) / 2;
/* 44:40 */     int var5 = (height - this.field_147000_g) / 2;
/* 45:41 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/* 46:43 */     if (this.field_147034_x.isChested()) {
/* 47:45 */       drawTexturedModalRect(var4 + 79, var5 + 17, 0, this.field_147000_g, 90, 54);
/* 48:   */     }
/* 49:48 */     if (this.field_147034_x.func_110259_cr()) {
/* 50:50 */       drawTexturedModalRect(var4 + 7, var5 + 35, 0, this.field_147000_g + 54, 18, 18);
/* 51:   */     }
/* 52:53 */     GuiInventory.func_147046_a(var4 + 51, var5 + 60, 17, var4 + 51 - this.field_147033_y, var5 + 75 - 50 - this.field_147032_z, this.field_147034_x);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void drawScreen(int par1, int par2, float par3)
/* 56:   */   {
/* 57:61 */     this.field_147033_y = par1;
/* 58:62 */     this.field_147032_z = par2;
/* 59:63 */     super.drawScreen(par1, par2, par3);
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiScreenHorseInventory
 * JD-Core Version:    0.7.0.1
 */