/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.BlockAnvil;
/*   5:    */ import net.minecraft.block.BlockDragonEgg;
/*   6:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   7:    */ import net.minecraft.client.renderer.Tessellator;
/*   8:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.item.EntityFallingBlock;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ import net.minecraft.util.ResourceLocation;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ public class RenderFallingBlock
/*  17:    */   extends Render
/*  18:    */ {
/*  19: 18 */   private final RenderBlocks field_147920_a = new RenderBlocks();
/*  20:    */   private static final String __OBFID = "CL_00000994";
/*  21:    */   
/*  22:    */   public RenderFallingBlock()
/*  23:    */   {
/*  24: 23 */     this.shadowSize = 0.5F;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void doRender(EntityFallingBlock p_147918_1_, double p_147918_2_, double p_147918_4_, double p_147918_6_, float p_147918_8_, float p_147918_9_)
/*  28:    */   {
/*  29: 34 */     World var10 = p_147918_1_.func_145807_e();
/*  30: 35 */     Block var11 = p_147918_1_.func_145805_f();
/*  31: 36 */     int var12 = MathHelper.floor_double(p_147918_1_.posX);
/*  32: 37 */     int var13 = MathHelper.floor_double(p_147918_1_.posY);
/*  33: 38 */     int var14 = MathHelper.floor_double(p_147918_1_.posZ);
/*  34: 40 */     if ((var11 != null) && (var11 != var10.getBlock(var12, var13, var14)))
/*  35:    */     {
/*  36: 42 */       GL11.glPushMatrix();
/*  37: 43 */       GL11.glTranslatef((float)p_147918_2_, (float)p_147918_4_, (float)p_147918_6_);
/*  38: 44 */       bindEntityTexture(p_147918_1_);
/*  39: 45 */       GL11.glDisable(2896);
/*  40: 48 */       if ((var11 instanceof BlockAnvil))
/*  41:    */       {
/*  42: 50 */         this.field_147920_a.blockAccess = var10;
/*  43: 51 */         Tessellator var15 = Tessellator.instance;
/*  44: 52 */         var15.startDrawingQuads();
/*  45: 53 */         var15.setTranslation(-var12 - 0.5F, -var13 - 0.5F, -var14 - 0.5F);
/*  46: 54 */         this.field_147920_a.renderBlockAnvilMetadata((BlockAnvil)var11, var12, var13, var14, p_147918_1_.field_145814_a);
/*  47: 55 */         var15.setTranslation(0.0D, 0.0D, 0.0D);
/*  48: 56 */         var15.draw();
/*  49:    */       }
/*  50: 58 */       else if ((var11 instanceof BlockDragonEgg))
/*  51:    */       {
/*  52: 60 */         this.field_147920_a.blockAccess = var10;
/*  53: 61 */         Tessellator var15 = Tessellator.instance;
/*  54: 62 */         var15.startDrawingQuads();
/*  55: 63 */         var15.setTranslation(-var12 - 0.5F, -var13 - 0.5F, -var14 - 0.5F);
/*  56: 64 */         this.field_147920_a.renderBlockDragonEgg((BlockDragonEgg)var11, var12, var13, var14);
/*  57: 65 */         var15.setTranslation(0.0D, 0.0D, 0.0D);
/*  58: 66 */         var15.draw();
/*  59:    */       }
/*  60:    */       else
/*  61:    */       {
/*  62: 70 */         this.field_147920_a.setRenderBoundsFromBlock(var11);
/*  63: 71 */         this.field_147920_a.renderBlockSandFalling(var11, var10, var12, var13, var14, p_147918_1_.field_145814_a);
/*  64:    */       }
/*  65: 74 */       GL11.glEnable(2896);
/*  66: 75 */       GL11.glPopMatrix();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected ResourceLocation getEntityTexture(EntityFallingBlock p_147919_1_)
/*  71:    */   {
/*  72: 84 */     return TextureMap.locationBlocksTexture;
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*  76:    */   {
/*  77: 92 */     return getEntityTexture((EntityFallingBlock)par1Entity);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  81:    */   {
/*  82:103 */     doRender((EntityFallingBlock)par1Entity, par2, par4, par6, par8, par9);
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderFallingBlock
 * JD-Core Version:    0.7.0.1
 */