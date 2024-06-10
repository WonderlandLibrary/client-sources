/*  1:   */ package net.minecraft.client.renderer.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelChest;
/*  4:   */ import net.minecraft.tileentity.TileEntity;
/*  5:   */ import net.minecraft.tileentity.TileEntityEnderChest;
/*  6:   */ import net.minecraft.util.ResourceLocation;
/*  7:   */ import org.lwjgl.opengl.GL11;
/*  8:   */ 
/*  9:   */ public class TileEntityEnderChestRenderer
/* 10:   */   extends TileEntitySpecialRenderer
/* 11:   */ {
/* 12:12 */   private static final ResourceLocation field_147520_b = new ResourceLocation("textures/entity/chest/ender.png");
/* 13:13 */   private ModelChest field_147521_c = new ModelChest();
/* 14:   */   private static final String __OBFID = "CL_00000967";
/* 15:   */   
/* 16:   */   public void renderTileEntityAt(TileEntityEnderChest p_147519_1_, double p_147519_2_, double p_147519_4_, double p_147519_6_, float p_147519_8_)
/* 17:   */   {
/* 18:18 */     int var9 = 0;
/* 19:20 */     if (p_147519_1_.hasWorldObj()) {
/* 20:22 */       var9 = p_147519_1_.getBlockMetadata();
/* 21:   */     }
/* 22:25 */     bindTexture(field_147520_b);
/* 23:26 */     GL11.glPushMatrix();
/* 24:27 */     GL11.glEnable(32826);
/* 25:28 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 26:29 */     GL11.glTranslatef((float)p_147519_2_, (float)p_147519_4_ + 1.0F, (float)p_147519_6_ + 1.0F);
/* 27:30 */     GL11.glScalef(1.0F, -1.0F, -1.0F);
/* 28:31 */     GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 29:32 */     short var10 = 0;
/* 30:34 */     if (var9 == 2) {
/* 31:36 */       var10 = 180;
/* 32:   */     }
/* 33:39 */     if (var9 == 3) {
/* 34:41 */       var10 = 0;
/* 35:   */     }
/* 36:44 */     if (var9 == 4) {
/* 37:46 */       var10 = 90;
/* 38:   */     }
/* 39:49 */     if (var9 == 5) {
/* 40:51 */       var10 = -90;
/* 41:   */     }
/* 42:54 */     GL11.glRotatef(var10, 0.0F, 1.0F, 0.0F);
/* 43:55 */     GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 44:56 */     float var11 = p_147519_1_.field_145975_i + (p_147519_1_.field_145972_a - p_147519_1_.field_145975_i) * p_147519_8_;
/* 45:57 */     var11 = 1.0F - var11;
/* 46:58 */     var11 = 1.0F - var11 * var11 * var11;
/* 47:59 */     this.field_147521_c.chestLid.rotateAngleX = (-(var11 * 3.141593F / 2.0F));
/* 48:60 */     this.field_147521_c.renderAll();
/* 49:61 */     GL11.glDisable(32826);
/* 50:62 */     GL11.glPopMatrix();
/* 51:63 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/* 55:   */   {
/* 56:68 */     renderTileEntityAt((TileEntityEnderChest)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer
 * JD-Core Version:    0.7.0.1
 */