/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.model.ModelBase;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.passive.EntitySheep;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ 
/* 10:   */ public class RenderSheep
/* 11:   */   extends RenderLiving
/* 12:   */ {
/* 13:12 */   private static final ResourceLocation sheepTextures = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
/* 14:13 */   private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");
/* 15:   */   private static final String __OBFID = "CL_00001021";
/* 16:   */   
/* 17:   */   public RenderSheep(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
/* 18:   */   {
/* 19:18 */     super(par1ModelBase, par3);
/* 20:19 */     setRenderPassModel(par2ModelBase);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected int shouldRenderPass(EntitySheep par1EntitySheep, int par2, float par3)
/* 24:   */   {
/* 25:27 */     if ((par2 == 0) && (!par1EntitySheep.getSheared()))
/* 26:   */     {
/* 27:29 */       bindTexture(sheepTextures);
/* 28:30 */       int var4 = par1EntitySheep.getFleeceColor();
/* 29:31 */       GL11.glColor3f(EntitySheep.fleeceColorTable[var4][0], EntitySheep.fleeceColorTable[var4][1], EntitySheep.fleeceColorTable[var4][2]);
/* 30:32 */       return 1;
/* 31:   */     }
/* 32:36 */     return -1;
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected ResourceLocation getEntityTexture(EntitySheep par1EntitySheep)
/* 36:   */   {
/* 37:45 */     return shearedSheepTextures;
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 41:   */   {
/* 42:53 */     return shouldRenderPass((EntitySheep)par1EntityLivingBase, par2, par3);
/* 43:   */   }
/* 44:   */   
/* 45:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 46:   */   {
/* 47:61 */     return getEntityTexture((EntitySheep)par1Entity);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderSheep
 * JD-Core Version:    0.7.0.1
 */