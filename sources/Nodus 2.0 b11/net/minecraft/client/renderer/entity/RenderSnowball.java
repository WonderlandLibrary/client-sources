/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.client.renderer.texture.TextureMap;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.projectile.EntityPotion;
/*  7:   */ import net.minecraft.item.Item;
/*  8:   */ import net.minecraft.item.ItemPotion;
/*  9:   */ import net.minecraft.potion.PotionHelper;
/* 10:   */ import net.minecraft.util.IIcon;
/* 11:   */ import net.minecraft.util.ResourceLocation;
/* 12:   */ import org.lwjgl.opengl.GL11;
/* 13:   */ 
/* 14:   */ public class RenderSnowball
/* 15:   */   extends Render
/* 16:   */ {
/* 17:   */   private Item field_94151_a;
/* 18:   */   private int field_94150_f;
/* 19:   */   private static final String __OBFID = "CL_00001008";
/* 20:   */   
/* 21:   */   public RenderSnowball(Item par1Item, int par2)
/* 22:   */   {
/* 23:23 */     this.field_94151_a = par1Item;
/* 24:24 */     this.field_94150_f = par2;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public RenderSnowball(Item par1Item)
/* 28:   */   {
/* 29:29 */     this(par1Item, 0);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 33:   */   {
/* 34:40 */     IIcon var10 = this.field_94151_a.getIconFromDamage(this.field_94150_f);
/* 35:42 */     if (var10 != null)
/* 36:   */     {
/* 37:44 */       GL11.glPushMatrix();
/* 38:45 */       GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/* 39:46 */       GL11.glEnable(32826);
/* 40:47 */       GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 41:48 */       bindEntityTexture(par1Entity);
/* 42:49 */       Tessellator var11 = Tessellator.instance;
/* 43:51 */       if (var10 == ItemPotion.func_94589_d("bottle_splash"))
/* 44:   */       {
/* 45:53 */         int var12 = PotionHelper.func_77915_a(((EntityPotion)par1Entity).getPotionDamage(), false);
/* 46:54 */         float var13 = (var12 >> 16 & 0xFF) / 255.0F;
/* 47:55 */         float var14 = (var12 >> 8 & 0xFF) / 255.0F;
/* 48:56 */         float var15 = (var12 & 0xFF) / 255.0F;
/* 49:57 */         GL11.glColor3f(var13, var14, var15);
/* 50:58 */         GL11.glPushMatrix();
/* 51:59 */         func_77026_a(var11, ItemPotion.func_94589_d("overlay"));
/* 52:60 */         GL11.glPopMatrix();
/* 53:61 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 54:   */       }
/* 55:64 */       func_77026_a(var11, var10);
/* 56:65 */       GL11.glDisable(32826);
/* 57:66 */       GL11.glPopMatrix();
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 62:   */   {
/* 63:75 */     return TextureMap.locationItemsTexture;
/* 64:   */   }
/* 65:   */   
/* 66:   */   private void func_77026_a(Tessellator par1Tessellator, IIcon par2Icon)
/* 67:   */   {
/* 68:80 */     float var3 = par2Icon.getMinU();
/* 69:81 */     float var4 = par2Icon.getMaxU();
/* 70:82 */     float var5 = par2Icon.getMinV();
/* 71:83 */     float var6 = par2Icon.getMaxV();
/* 72:84 */     float var7 = 1.0F;
/* 73:85 */     float var8 = 0.5F;
/* 74:86 */     float var9 = 0.25F;
/* 75:87 */     GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 76:88 */     GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 77:89 */     par1Tessellator.startDrawingQuads();
/* 78:90 */     par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
/* 79:91 */     par1Tessellator.addVertexWithUV(0.0F - var8, 0.0F - var9, 0.0D, var3, var6);
/* 80:92 */     par1Tessellator.addVertexWithUV(var7 - var8, 0.0F - var9, 0.0D, var4, var6);
/* 81:93 */     par1Tessellator.addVertexWithUV(var7 - var8, var7 - var9, 0.0D, var4, var5);
/* 82:94 */     par1Tessellator.addVertexWithUV(0.0F - var8, var7 - var9, 0.0D, var3, var5);
/* 83:95 */     par1Tessellator.draw();
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderSnowball
 * JD-Core Version:    0.7.0.1
 */