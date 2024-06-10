/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.passive.EntityCow;
/*  6:   */ import net.minecraft.util.ResourceLocation;
/*  7:   */ 
/*  8:   */ public class RenderCow
/*  9:   */   extends RenderLiving
/* 10:   */ {
/* 11:10 */   private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
/* 12:   */   private static final String __OBFID = "CL_00000984";
/* 13:   */   
/* 14:   */   public RenderCow(ModelBase par1ModelBase, float par2)
/* 15:   */   {
/* 16:15 */     super(par1ModelBase, par2);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected ResourceLocation getEntityTexture(EntityCow par1EntityCow)
/* 20:   */   {
/* 21:23 */     return cowTextures;
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 25:   */   {
/* 26:31 */     return getEntityTexture((EntityCow)par1Entity);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderCow
 * JD-Core Version:    0.7.0.1
 */