/*   1:    */ package net.minecraft.client.renderer.tileentity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*   4:    */ import net.minecraft.client.model.ModelSkeletonHead;
/*   5:    */ import net.minecraft.tileentity.TileEntity;
/*   6:    */ import net.minecraft.tileentity.TileEntitySkull;
/*   7:    */ import net.minecraft.util.ResourceLocation;
/*   8:    */ import org.lwjgl.opengl.GL11;
/*   9:    */ 
/*  10:    */ public class TileEntitySkullRenderer
/*  11:    */   extends TileEntitySpecialRenderer
/*  12:    */ {
/*  13: 14 */   private static final ResourceLocation field_147537_c = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/*  14: 15 */   private static final ResourceLocation field_147534_d = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
/*  15: 16 */   private static final ResourceLocation field_147535_e = new ResourceLocation("textures/entity/zombie/zombie.png");
/*  16: 17 */   private static final ResourceLocation field_147532_f = new ResourceLocation("textures/entity/creeper/creeper.png");
/*  17:    */   public static TileEntitySkullRenderer field_147536_b;
/*  18: 19 */   private ModelSkeletonHead field_147533_g = new ModelSkeletonHead(0, 0, 64, 32);
/*  19: 20 */   private ModelSkeletonHead field_147538_h = new ModelSkeletonHead(0, 0, 64, 64);
/*  20:    */   private static final String __OBFID = "CL_00000971";
/*  21:    */   
/*  22:    */   public void renderTileEntityAt(TileEntitySkull p_147531_1_, double p_147531_2_, double p_147531_4_, double p_147531_6_, float p_147531_8_)
/*  23:    */   {
/*  24: 25 */     func_147530_a((float)p_147531_2_, (float)p_147531_4_, (float)p_147531_6_, p_147531_1_.getBlockMetadata() & 0x7, p_147531_1_.func_145906_b() * 360 / 16.0F, p_147531_1_.func_145904_a(), p_147531_1_.func_145907_c());
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void func_147497_a(TileEntityRendererDispatcher p_147497_1_)
/*  28:    */   {
/*  29: 30 */     super.func_147497_a(p_147497_1_);
/*  30: 31 */     field_147536_b = this;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void func_147530_a(float p_147530_1_, float p_147530_2_, float p_147530_3_, int p_147530_4_, float p_147530_5_, int p_147530_6_, String p_147530_7_)
/*  34:    */   {
/*  35: 36 */     ModelSkeletonHead var8 = this.field_147533_g;
/*  36: 38 */     switch (p_147530_6_)
/*  37:    */     {
/*  38:    */     case 0: 
/*  39:    */     default: 
/*  40: 42 */       bindTexture(field_147537_c);
/*  41: 43 */       break;
/*  42:    */     case 1: 
/*  43: 46 */       bindTexture(field_147534_d);
/*  44: 47 */       break;
/*  45:    */     case 2: 
/*  46: 50 */       bindTexture(field_147535_e);
/*  47: 51 */       var8 = this.field_147538_h;
/*  48: 52 */       break;
/*  49:    */     case 3: 
/*  50: 55 */       ResourceLocation var9 = AbstractClientPlayer.locationStevePng;
/*  51: 57 */       if ((p_147530_7_ != null) && (p_147530_7_.length() > 0))
/*  52:    */       {
/*  53: 59 */         var9 = AbstractClientPlayer.getLocationSkull(p_147530_7_);
/*  54: 60 */         AbstractClientPlayer.getDownloadImageSkin(var9, p_147530_7_);
/*  55:    */       }
/*  56: 63 */       bindTexture(var9);
/*  57: 64 */       break;
/*  58:    */     case 4: 
/*  59: 67 */       bindTexture(field_147532_f);
/*  60:    */     }
/*  61: 70 */     GL11.glPushMatrix();
/*  62: 71 */     GL11.glDisable(2884);
/*  63: 73 */     if (p_147530_4_ != 1) {
/*  64: 75 */       switch (p_147530_4_)
/*  65:    */       {
/*  66:    */       case 2: 
/*  67: 78 */         GL11.glTranslatef(p_147530_1_ + 0.5F, p_147530_2_ + 0.25F, p_147530_3_ + 0.74F);
/*  68: 79 */         break;
/*  69:    */       case 3: 
/*  70: 82 */         GL11.glTranslatef(p_147530_1_ + 0.5F, p_147530_2_ + 0.25F, p_147530_3_ + 0.26F);
/*  71: 83 */         p_147530_5_ = 180.0F;
/*  72: 84 */         break;
/*  73:    */       case 4: 
/*  74: 87 */         GL11.glTranslatef(p_147530_1_ + 0.74F, p_147530_2_ + 0.25F, p_147530_3_ + 0.5F);
/*  75: 88 */         p_147530_5_ = 270.0F;
/*  76: 89 */         break;
/*  77:    */       case 5: 
/*  78:    */       default: 
/*  79: 93 */         GL11.glTranslatef(p_147530_1_ + 0.26F, p_147530_2_ + 0.25F, p_147530_3_ + 0.5F);
/*  80: 94 */         p_147530_5_ = 90.0F;
/*  81:    */         
/*  82: 96 */         break;
/*  83:    */       }
/*  84:    */     } else {
/*  85: 99 */       GL11.glTranslatef(p_147530_1_ + 0.5F, p_147530_2_, p_147530_3_ + 0.5F);
/*  86:    */     }
/*  87:102 */     float var10 = 0.0625F;
/*  88:103 */     GL11.glEnable(32826);
/*  89:104 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/*  90:105 */     GL11.glEnable(3008);
/*  91:106 */     var8.render(null, 0.0F, 0.0F, 0.0F, p_147530_5_, 0.0F, var10);
/*  92:107 */     GL11.glPopMatrix();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
/*  96:    */   {
/*  97:112 */     renderTileEntityAt((TileEntitySkull)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer
 * JD-Core Version:    0.7.0.1
 */