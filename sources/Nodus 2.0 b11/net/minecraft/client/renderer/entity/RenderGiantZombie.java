/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.monster.EntityGiantZombie;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderGiantZombie
/* 11:   */   extends RenderLiving
/* 12:   */ {
/* 13:12 */   private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
/* 14:   */   private float scale;
/* 15:   */   private static final String __OBFID = "CL_00000998";
/* 16:   */   
/* 17:   */   public RenderGiantZombie(ModelBase par1ModelBase, float par2, float par3)
/* 18:   */   {
/* 19:20 */     super(par1ModelBase, par2 * par3);
/* 20:21 */     this.scale = par3;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected void preRenderCallback(EntityGiantZombie par1EntityGiantZombie, float par2)
/* 24:   */   {
/* 25:30 */     GL11.glScalef(this.scale, this.scale, this.scale);
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected ResourceLocation getEntityTexture(EntityGiantZombie par1EntityGiantZombie)
/* 29:   */   {
/* 30:38 */     return zombieTextures;
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 34:   */   {
/* 35:47 */     preRenderCallback((EntityGiantZombie)par1EntityLivingBase, par2);
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 39:   */   {
/* 40:55 */     return getEntityTexture((EntityGiantZombie)par1Entity);
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderGiantZombie
 * JD-Core Version:    0.7.0.1
 */