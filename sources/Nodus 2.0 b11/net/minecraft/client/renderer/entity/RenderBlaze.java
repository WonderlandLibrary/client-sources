/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBlaze;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLiving;
/*  6:   */ import net.minecraft.entity.EntityLivingBase;
/*  7:   */ import net.minecraft.entity.monster.EntityBlaze;
/*  8:   */ import net.minecraft.util.ResourceLocation;
/*  9:   */ 
/* 10:   */ public class RenderBlaze
/* 11:   */   extends RenderLiving
/* 12:   */ {
/* 13:12 */   private static final ResourceLocation blazeTextures = new ResourceLocation("textures/entity/blaze.png");
/* 14:   */   private int field_77068_a;
/* 15:   */   private static final String __OBFID = "CL_00000980";
/* 16:   */   
/* 17:   */   public RenderBlaze()
/* 18:   */   {
/* 19:18 */     super(new ModelBlaze(), 0.5F);
/* 20:19 */     this.field_77068_a = ((ModelBlaze)this.mainModel).func_78104_a();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void doRender(EntityBlaze par1EntityBlaze, double par2, double par4, double par6, float par8, float par9)
/* 24:   */   {
/* 25:30 */     int var10 = ((ModelBlaze)this.mainModel).func_78104_a();
/* 26:32 */     if (var10 != this.field_77068_a)
/* 27:   */     {
/* 28:34 */       this.field_77068_a = var10;
/* 29:35 */       this.mainModel = new ModelBlaze();
/* 30:   */     }
/* 31:38 */     super.doRender(par1EntityBlaze, par2, par4, par6, par8, par9);
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected ResourceLocation getEntityTexture(EntityBlaze par1EntityBlaze)
/* 35:   */   {
/* 36:46 */     return blazeTextures;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/* 40:   */   {
/* 41:57 */     doRender((EntityBlaze)par1EntityLiving, par2, par4, par6, par8, par9);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 45:   */   {
/* 46:68 */     doRender((EntityBlaze)par1Entity, par2, par4, par6, par8, par9);
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 50:   */   {
/* 51:76 */     return getEntityTexture((EntityBlaze)par1Entity);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 55:   */   {
/* 56:87 */     doRender((EntityBlaze)par1Entity, par2, par4, par6, par8, par9);
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderBlaze
 * JD-Core Version:    0.7.0.1
 */