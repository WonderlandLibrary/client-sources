/*  1:   */ package net.minecraft.client.renderer.tileentity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.block.BlockPistonBase;
/*  5:   */ import net.minecraft.block.BlockPistonExtension;
/*  6:   */ import net.minecraft.block.material.Material;
/*  7:   */ import net.minecraft.client.Minecraft;
/*  8:   */ import net.minecraft.client.renderer.RenderBlocks;
/*  9:   */ import net.minecraft.client.renderer.RenderHelper;
/* 10:   */ import net.minecraft.client.renderer.Tessellator;
/* 11:   */ import net.minecraft.client.renderer.texture.TextureMap;
/* 12:   */ import net.minecraft.init.Blocks;
/* 13:   */ import net.minecraft.tileentity.TileEntity;
/* 14:   */ import net.minecraft.tileentity.TileEntityPiston;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ import org.lwjgl.opengl.GL11;
/* 17:   */ 
/* 18:   */ public class TileEntityRendererPiston
/* 19:   */   extends TileEntitySpecialRenderer
/* 20:   */ {
/* 21:   */   private RenderBlocks field_147516_b;
/* 22:   */   private static final String __OBFID = "CL_00000969";
/* 23:   */   
/* 24:   */   public void renderTileEntityAt(TileEntityPiston p_147515_1_, double p_147515_2_, double p_147515_4_, double p_147515_6_, float p_147515_8_)
/* 25:   */   {
/* 26:24 */     Block var9 = p_147515_1_.func_145861_a();
/* 27:26 */     if ((var9.getMaterial() != Material.air) && (p_147515_1_.func_145860_a(p_147515_8_) < 1.0F))
/* 28:   */     {
/* 29:28 */       Tessellator var10 = Tessellator.instance;
/* 30:29 */       bindTexture(TextureMap.locationBlocksTexture);
/* 31:30 */       RenderHelper.disableStandardItemLighting();
/* 32:31 */       GL11.glBlendFunc(770, 771);
/* 33:32 */       GL11.glEnable(3042);
/* 34:33 */       GL11.glDisable(2884);
/* 35:35 */       if (Minecraft.isAmbientOcclusionEnabled()) {
/* 36:37 */         GL11.glShadeModel(7425);
/* 37:   */       } else {
/* 38:41 */         GL11.glShadeModel(7424);
/* 39:   */       }
/* 40:44 */       var10.startDrawingQuads();
/* 41:45 */       var10.setTranslation((float)p_147515_2_ - p_147515_1_.field_145851_c + p_147515_1_.func_145865_b(p_147515_8_), (float)p_147515_4_ - p_147515_1_.field_145848_d + p_147515_1_.func_145862_c(p_147515_8_), (float)p_147515_6_ - p_147515_1_.field_145849_e + p_147515_1_.func_145859_d(p_147515_8_));
/* 42:46 */       var10.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 43:48 */       if ((var9 == Blocks.piston_head) && (p_147515_1_.func_145860_a(p_147515_8_) < 0.5F))
/* 44:   */       {
/* 45:50 */         this.field_147516_b.renderPistonExtensionAllFaces(var9, p_147515_1_.field_145851_c, p_147515_1_.field_145848_d, p_147515_1_.field_145849_e, false);
/* 46:   */       }
/* 47:52 */       else if ((p_147515_1_.func_145867_d()) && (!p_147515_1_.func_145868_b()))
/* 48:   */       {
/* 49:54 */         Blocks.piston_head.func_150086_a(((BlockPistonBase)var9).func_150073_e());
/* 50:55 */         this.field_147516_b.renderPistonExtensionAllFaces(Blocks.piston_head, p_147515_1_.field_145851_c, p_147515_1_.field_145848_d, p_147515_1_.field_145849_e, p_147515_1_.func_145860_a(p_147515_8_) < 0.5F);
/* 51:56 */         Blocks.piston_head.func_150087_e();
/* 52:57 */         var10.setTranslation((float)p_147515_2_ - p_147515_1_.field_145851_c, (float)p_147515_4_ - p_147515_1_.field_145848_d, (float)p_147515_6_ - p_147515_1_.field_145849_e);
/* 53:58 */         this.field_147516_b.renderPistonBaseAllFaces(var9, p_147515_1_.field_145851_c, p_147515_1_.field_145848_d, p_147515_1_.field_145849_e);
/* 54:   */       }
/* 55:   */       else
/* 56:   */       {
/* 57:62 */         this.field_147516_b.renderBlockAllFaces(var9, p_147515_1_.field_145851_c, p_147515_1_.field_145848_d, p_147515_1_.field_145849_e);
/* 58:   */       }
/* 59:65 */       var10.setTranslation(0.0D, 0.0D, 0.0D);
/* 60:66 */       var10.draw();
/* 61:67 */       RenderHelper.enableStandardItemLighting();
/* 62:   */     }
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void func_147496_a(World p_147496_1_)
/* 66:   */   {
/* 67:73 */     this.field_147516_b = new RenderBlocks(p_147496_1_);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/* 71:   */   {
/* 72:78 */     renderTileEntityAt((TileEntityPiston)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntityRendererPiston
 * JD-Core Version:    0.7.0.1
 */