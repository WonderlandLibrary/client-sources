/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelBase;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.passive.EntitySquid;
/*   8:    */ import net.minecraft.util.ResourceLocation;
/*   9:    */ import org.lwjgl.opengl.GL11;
/*  10:    */ 
/*  11:    */ public class RenderSquid
/*  12:    */   extends RenderLiving
/*  13:    */ {
/*  14: 13 */   private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");
/*  15:    */   private static final String __OBFID = "CL_00001028";
/*  16:    */   
/*  17:    */   public RenderSquid(ModelBase par1ModelBase, float par2)
/*  18:    */   {
/*  19: 18 */     super(par1ModelBase, par2);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void doRender(EntitySquid par1EntitySquid, double par2, double par4, double par6, float par8, float par9)
/*  23:    */   {
/*  24: 29 */     super.doRender(par1EntitySquid, par2, par4, par6, par8, par9);
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected ResourceLocation getEntityTexture(EntitySquid par1EntitySquid)
/*  28:    */   {
/*  29: 37 */     return squidTextures;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void rotateCorpse(EntitySquid par1EntitySquid, float par2, float par3, float par4)
/*  33:    */   {
/*  34: 42 */     float var5 = par1EntitySquid.prevSquidPitch + (par1EntitySquid.squidPitch - par1EntitySquid.prevSquidPitch) * par4;
/*  35: 43 */     float var6 = par1EntitySquid.prevSquidYaw + (par1EntitySquid.squidYaw - par1EntitySquid.prevSquidYaw) * par4;
/*  36: 44 */     GL11.glTranslatef(0.0F, 0.5F, 0.0F);
/*  37: 45 */     GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);
/*  38: 46 */     GL11.glRotatef(var5, 1.0F, 0.0F, 0.0F);
/*  39: 47 */     GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
/*  40: 48 */     GL11.glTranslatef(0.0F, -1.2F, 0.0F);
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected float handleRotationFloat(EntitySquid par1EntitySquid, float par2)
/*  44:    */   {
/*  45: 56 */     return par1EntitySquid.lastTentacleAngle + (par1EntitySquid.tentacleAngle - par1EntitySquid.lastTentacleAngle) * par2;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  49:    */   {
/*  50: 67 */     doRender((EntitySquid)par1EntityLiving, par2, par4, par6, par8, par9);
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2)
/*  54:    */   {
/*  55: 75 */     return handleRotationFloat((EntitySquid)par1EntityLivingBase, par2);
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/*  59:    */   {
/*  60: 80 */     rotateCorpse((EntitySquid)par1EntityLivingBase, par2, par3, par4);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  64:    */   {
/*  65: 91 */     doRender((EntitySquid)par1Entity, par2, par4, par6, par8, par9);
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*  69:    */   {
/*  70: 99 */     return getEntityTexture((EntitySquid)par1Entity);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  74:    */   {
/*  75:110 */     doRender((EntitySquid)par1Entity, par2, par4, par6, par8, par9);
/*  76:    */   }
/*  77:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderSquid
 * JD-Core Version:    0.7.0.1
 */