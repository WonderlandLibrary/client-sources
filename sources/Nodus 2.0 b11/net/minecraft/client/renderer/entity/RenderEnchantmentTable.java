/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBook;
/*  4:   */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*  5:   */ import net.minecraft.tileentity.TileEntity;
/*  6:   */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*  7:   */ import net.minecraft.util.MathHelper;
/*  8:   */ import net.minecraft.util.ResourceLocation;
/*  9:   */ import org.lwjgl.opengl.GL11;
/* 10:   */ 
/* 11:   */ public class RenderEnchantmentTable
/* 12:   */   extends TileEntitySpecialRenderer
/* 13:   */ {
/* 14:14 */   private static final ResourceLocation field_147540_b = new ResourceLocation("textures/entity/enchanting_table_book.png");
/* 15:15 */   private ModelBook field_147541_c = new ModelBook();
/* 16:   */   private static final String __OBFID = "CL_00000966";
/* 17:   */   
/* 18:   */   public void renderTileEntityAt(TileEntityEnchantmentTable p_147539_1_, double p_147539_2_, double p_147539_4_, double p_147539_6_, float p_147539_8_)
/* 19:   */   {
/* 20:20 */     GL11.glPushMatrix();
/* 21:21 */     GL11.glTranslatef((float)p_147539_2_ + 0.5F, (float)p_147539_4_ + 0.75F, (float)p_147539_6_ + 0.5F);
/* 22:22 */     float var9 = p_147539_1_.field_145926_a + p_147539_8_;
/* 23:23 */     GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(var9 * 0.1F) * 0.01F, 0.0F);
/* 24:26 */     for (float var10 = p_147539_1_.field_145928_o - p_147539_1_.field_145925_p; var10 >= 3.141593F; var10 -= 6.283186F) {}
/* 25:31 */     while (var10 < -3.141593F) {
/* 26:33 */       var10 += 6.283186F;
/* 27:   */     }
/* 28:36 */     float var11 = p_147539_1_.field_145925_p + var10 * p_147539_8_;
/* 29:37 */     GL11.glRotatef(-var11 * 180.0F / 3.141593F, 0.0F, 1.0F, 0.0F);
/* 30:38 */     GL11.glRotatef(80.0F, 0.0F, 0.0F, 1.0F);
/* 31:39 */     bindTexture(field_147540_b);
/* 32:40 */     float var12 = p_147539_1_.field_145931_j + (p_147539_1_.field_145933_i - p_147539_1_.field_145931_j) * p_147539_8_ + 0.25F;
/* 33:41 */     float var13 = p_147539_1_.field_145931_j + (p_147539_1_.field_145933_i - p_147539_1_.field_145931_j) * p_147539_8_ + 0.75F;
/* 34:42 */     var12 = (var12 - MathHelper.truncateDoubleToInt(var12)) * 1.6F - 0.3F;
/* 35:43 */     var13 = (var13 - MathHelper.truncateDoubleToInt(var13)) * 1.6F - 0.3F;
/* 36:45 */     if (var12 < 0.0F) {
/* 37:47 */       var12 = 0.0F;
/* 38:   */     }
/* 39:50 */     if (var13 < 0.0F) {
/* 40:52 */       var13 = 0.0F;
/* 41:   */     }
/* 42:55 */     if (var12 > 1.0F) {
/* 43:57 */       var12 = 1.0F;
/* 44:   */     }
/* 45:60 */     if (var13 > 1.0F) {
/* 46:62 */       var13 = 1.0F;
/* 47:   */     }
/* 48:65 */     float var14 = p_147539_1_.field_145927_n + (p_147539_1_.field_145930_m - p_147539_1_.field_145927_n) * p_147539_8_;
/* 49:66 */     GL11.glEnable(2884);
/* 50:67 */     this.field_147541_c.render(null, var9, var12, var13, var14, 0.0F, 0.0625F);
/* 51:68 */     GL11.glPopMatrix();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/* 55:   */   {
/* 56:73 */     renderTileEntityAt((TileEntityEnchantmentTable)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderEnchantmentTable
 * JD-Core Version:    0.7.0.1
 */