/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelSilverfish;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.monster.EntitySilverfish;
/*   8:    */ import net.minecraft.util.ResourceLocation;
/*   9:    */ 
/*  10:    */ public class RenderSilverfish
/*  11:    */   extends RenderLiving
/*  12:    */ {
/*  13: 12 */   private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
/*  14:    */   private static final String __OBFID = "CL_00001022";
/*  15:    */   
/*  16:    */   public RenderSilverfish()
/*  17:    */   {
/*  18: 17 */     super(new ModelSilverfish(), 0.3F);
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected float getDeathMaxRotation(EntitySilverfish par1EntitySilverfish)
/*  22:    */   {
/*  23: 22 */     return 180.0F;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void doRender(EntitySilverfish par1EntitySilverfish, double par2, double par4, double par6, float par8, float par9)
/*  27:    */   {
/*  28: 33 */     super.doRender(par1EntitySilverfish, par2, par4, par6, par8, par9);
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected ResourceLocation getEntityTexture(EntitySilverfish par1EntitySilverfish)
/*  32:    */   {
/*  33: 41 */     return silverfishTextures;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int shouldRenderPass(EntitySilverfish par1EntitySilverfish, int par2, float par3)
/*  37:    */   {
/*  38: 49 */     return -1;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  42:    */   {
/*  43: 60 */     doRender((EntitySilverfish)par1EntityLiving, par2, par4, par6, par8, par9);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
/*  47:    */   {
/*  48: 65 */     return getDeathMaxRotation((EntitySilverfish)par1EntityLivingBase);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/*  52:    */   {
/*  53: 73 */     return shouldRenderPass((EntitySilverfish)par1EntityLivingBase, par2, par3);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  57:    */   {
/*  58: 84 */     doRender((EntitySilverfish)par1Entity, par2, par4, par6, par8, par9);
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*  62:    */   {
/*  63: 92 */     return getEntityTexture((EntitySilverfish)par1Entity);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  67:    */   {
/*  68:103 */     doRender((EntitySilverfish)par1Entity, par2, par4, par6, par8, par9);
/*  69:    */   }
/*  70:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderSilverfish
 * JD-Core Version:    0.7.0.1
 */