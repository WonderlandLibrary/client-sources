/*  1:   */ package net.minecraft.client.renderer.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.client.gui.FontRenderer;
/*  5:   */ import net.minecraft.client.model.ModelSign;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.tileentity.TileEntity;
/*  8:   */ import net.minecraft.tileentity.TileEntitySign;
/*  9:   */ import net.minecraft.util.ResourceLocation;
/* 10:   */ import org.lwjgl.opengl.GL11;
/* 11:   */ 
/* 12:   */ public class TileEntitySignRenderer
/* 13:   */   extends TileEntitySpecialRenderer
/* 14:   */ {
/* 15:14 */   private static final ResourceLocation field_147513_b = new ResourceLocation("textures/entity/sign.png");
/* 16:15 */   private final ModelSign field_147514_c = new ModelSign();
/* 17:   */   private static final String __OBFID = "CL_00000970";
/* 18:   */   
/* 19:   */   public void renderTileEntityAt(TileEntitySign p_147512_1_, double p_147512_2_, double p_147512_4_, double p_147512_6_, float p_147512_8_)
/* 20:   */   {
/* 21:20 */     Block var9 = p_147512_1_.getBlockType();
/* 22:21 */     GL11.glPushMatrix();
/* 23:22 */     float var10 = 0.6666667F;
/* 24:25 */     if (var9 == Blocks.standing_sign)
/* 25:   */     {
/* 26:27 */       GL11.glTranslatef((float)p_147512_2_ + 0.5F, (float)p_147512_4_ + 0.75F * var10, (float)p_147512_6_ + 0.5F);
/* 27:28 */       float var11 = p_147512_1_.getBlockMetadata() * 360 / 16.0F;
/* 28:29 */       GL11.glRotatef(-var11, 0.0F, 1.0F, 0.0F);
/* 29:30 */       this.field_147514_c.signStick.showModel = true;
/* 30:   */     }
/* 31:   */     else
/* 32:   */     {
/* 33:34 */       int var16 = p_147512_1_.getBlockMetadata();
/* 34:35 */       float var12 = 0.0F;
/* 35:37 */       if (var16 == 2) {
/* 36:39 */         var12 = 180.0F;
/* 37:   */       }
/* 38:42 */       if (var16 == 4) {
/* 39:44 */         var12 = 90.0F;
/* 40:   */       }
/* 41:47 */       if (var16 == 5) {
/* 42:49 */         var12 = -90.0F;
/* 43:   */       }
/* 44:52 */       GL11.glTranslatef((float)p_147512_2_ + 0.5F, (float)p_147512_4_ + 0.75F * var10, (float)p_147512_6_ + 0.5F);
/* 45:53 */       GL11.glRotatef(-var12, 0.0F, 1.0F, 0.0F);
/* 46:54 */       GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
/* 47:55 */       this.field_147514_c.signStick.showModel = false;
/* 48:   */     }
/* 49:58 */     bindTexture(field_147513_b);
/* 50:59 */     GL11.glPushMatrix();
/* 51:60 */     GL11.glScalef(var10, -var10, -var10);
/* 52:61 */     this.field_147514_c.renderSign();
/* 53:62 */     GL11.glPopMatrix();
/* 54:63 */     FontRenderer var17 = func_147498_b();
/* 55:64 */     float var12 = 0.01666667F * var10;
/* 56:65 */     GL11.glTranslatef(0.0F, 0.5F * var10, 0.07F * var10);
/* 57:66 */     GL11.glScalef(var12, -var12, var12);
/* 58:67 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F * var12);
/* 59:68 */     GL11.glDepthMask(false);
/* 60:69 */     byte var13 = 0;
/* 61:71 */     for (int var14 = 0; var14 < p_147512_1_.field_145915_a.length; var14++)
/* 62:   */     {
/* 63:73 */       String var15 = p_147512_1_.field_145915_a[var14];
/* 64:75 */       if (var14 == p_147512_1_.field_145918_i)
/* 65:   */       {
/* 66:77 */         var15 = "> " + var15 + " <";
/* 67:78 */         var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - p_147512_1_.field_145915_a.length * 5, var13);
/* 68:   */       }
/* 69:   */       else
/* 70:   */       {
/* 71:82 */         var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - p_147512_1_.field_145915_a.length * 5, var13);
/* 72:   */       }
/* 73:   */     }
/* 74:86 */     GL11.glDepthMask(true);
/* 75:87 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 76:88 */     GL11.glPopMatrix();
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/* 80:   */   {
/* 81:93 */     renderTileEntityAt((TileEntitySign)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntitySignRenderer
 * JD-Core Version:    0.7.0.1
 */