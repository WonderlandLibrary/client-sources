/*  1:   */ package net.minecraft.client.model;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.Random;
/*  8:   */ import net.minecraft.entity.Entity;
/*  9:   */ import net.minecraft.entity.EntityLivingBase;
/* 10:   */ 
/* 11:   */ public abstract class ModelBase
/* 12:   */ {
/* 13:   */   public float onGround;
/* 14:   */   public boolean isRiding;
/* 15:19 */   public List boxList = new ArrayList();
/* 16:20 */   public boolean isChild = true;
/* 17:23 */   private Map modelTextureMap = new HashMap();
/* 18:24 */   public int textureWidth = 64;
/* 19:25 */   public int textureHeight = 32;
/* 20:   */   private static final String __OBFID = "CL_00000845";
/* 21:   */   
/* 22:   */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {}
/* 23:   */   
/* 24:   */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {}
/* 25:   */   
/* 26:   */   public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {}
/* 27:   */   
/* 28:   */   public ModelRenderer getRandomModelBox(Random par1Random)
/* 29:   */   {
/* 30:48 */     return (ModelRenderer)this.boxList.get(par1Random.nextInt(this.boxList.size()));
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void setTextureOffset(String par1Str, int par2, int par3)
/* 34:   */   {
/* 35:53 */     this.modelTextureMap.put(par1Str, new TextureOffset(par2, par3));
/* 36:   */   }
/* 37:   */   
/* 38:   */   public TextureOffset getTextureOffset(String par1Str)
/* 39:   */   {
/* 40:58 */     return (TextureOffset)this.modelTextureMap.get(par1Str);
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelBase
 * JD-Core Version:    0.7.0.1
 */