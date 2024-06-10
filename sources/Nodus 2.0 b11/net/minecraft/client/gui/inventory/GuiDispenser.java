/*  1:   */ package net.minecraft.client.gui.inventory;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.client.gui.FontRenderer;
/*  5:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  6:   */ import net.minecraft.client.resources.I18n;
/*  7:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  8:   */ import net.minecraft.inventory.ContainerDispenser;
/*  9:   */ import net.minecraft.tileentity.TileEntityDispenser;
/* 10:   */ import net.minecraft.util.ResourceLocation;
/* 11:   */ import org.lwjgl.opengl.GL11;
/* 12:   */ 
/* 13:   */ public class GuiDispenser
/* 14:   */   extends GuiContainer
/* 15:   */ {
/* 16:12 */   private static final ResourceLocation field_147088_v = new ResourceLocation("textures/gui/container/dispenser.png");
/* 17:   */   public TileEntityDispenser field_147089_u;
/* 18:   */   private static final String __OBFID = "CL_00000765";
/* 19:   */   
/* 20:   */   public GuiDispenser(InventoryPlayer par1InventoryPlayer, TileEntityDispenser par2TileEntityDispenser)
/* 21:   */   {
/* 22:18 */     super(new ContainerDispenser(par1InventoryPlayer, par2TileEntityDispenser));
/* 23:19 */     this.field_147089_u = par2TileEntityDispenser;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/* 27:   */   {
/* 28:24 */     String var3 = this.field_147089_u.isInventoryNameLocalized() ? this.field_147089_u.getInventoryName() : I18n.format(this.field_147089_u.getInventoryName(), new Object[0]);
/* 29:25 */     this.fontRendererObj.drawString(var3, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
/* 30:26 */     this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/* 34:   */   {
/* 35:31 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 36:32 */     this.mc.getTextureManager().bindTexture(field_147088_v);
/* 37:33 */     int var4 = (width - this.field_146999_f) / 2;
/* 38:34 */     int var5 = (height - this.field_147000_g) / 2;
/* 39:35 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiDispenser
 * JD-Core Version:    0.7.0.1
 */