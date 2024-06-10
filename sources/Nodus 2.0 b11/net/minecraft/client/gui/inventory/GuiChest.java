/*  1:   */ package net.minecraft.client.gui.inventory;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.FontRenderer;
/*  5:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ import net.minecraft.inventory.ContainerChest;
/*  8:   */ import net.minecraft.inventory.IInventory;
/*  9:   */ import net.minecraft.util.ResourceLocation;
/* 10:   */ import org.lwjgl.opengl.GL11;
/* 11:   */ 
/* 12:   */ public class GuiChest
/* 13:   */   extends GuiContainer
/* 14:   */ {
/* 15:11 */   private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
/* 16:   */   private IInventory field_147016_v;
/* 17:   */   private IInventory field_147015_w;
/* 18:   */   private int field_147018_x;
/* 19:   */   private static final String __OBFID = "CL_00000749";
/* 20:   */   
/* 21:   */   public GuiChest(IInventory par1IInventory, IInventory par2IInventory)
/* 22:   */   {
/* 23:19 */     super(new ContainerChest(par1IInventory, par2IInventory));
/* 24:20 */     this.field_147016_v = par1IInventory;
/* 25:21 */     this.field_147015_w = par2IInventory;
/* 26:22 */     this.field_146291_p = false;
/* 27:23 */     short var3 = 222;
/* 28:24 */     int var4 = var3 - 108;
/* 29:25 */     this.field_147018_x = (par2IInventory.getSizeInventory() / 9);
/* 30:26 */     this.field_147000_g = (var4 + this.field_147018_x * 18);
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/* 34:   */   {
/* 35:31 */     this.fontRendererObj.drawString(this.field_147015_w.isInventoryNameLocalized() ? this.field_147015_w.getInventoryName() : I18n.format(this.field_147015_w.getInventoryName(), new Object[0]), 8, 6, 4210752);
/* 36:32 */     this.fontRendererObj.drawString(this.field_147016_v.isInventoryNameLocalized() ? this.field_147016_v.getInventoryName() : I18n.format(this.field_147016_v.getInventoryName(), new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 40:   */   {
/* 41:37 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 42:38 */     this.mc.getTextureManager().bindTexture(field_147017_u);
/* 43:39 */     int var4 = (width - this.field_146999_f) / 2;
/* 44:40 */     int var5 = (height - this.field_147000_g) / 2;
/* 45:41 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147018_x * 18 + 17);
/* 46:42 */     drawTexturedModalRect(var4, var5 + this.field_147018_x * 18 + 17, 0, 126, this.field_146999_f, 96);
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiChest
 * JD-Core Version:    0.7.0.1
 */