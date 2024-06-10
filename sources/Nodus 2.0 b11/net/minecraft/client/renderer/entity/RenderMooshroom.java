/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelBase;
/*   4:    */ import net.minecraft.client.model.ModelQuadruped;
/*   5:    */ import net.minecraft.client.model.ModelRenderer;
/*   6:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   7:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityLiving;
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.entity.passive.EntityMooshroom;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.util.ResourceLocation;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ public class RenderMooshroom
/*  17:    */   extends RenderLiving
/*  18:    */ {
/*  19: 16 */   private static final ResourceLocation mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
/*  20:    */   private static final String __OBFID = "CL_00001016";
/*  21:    */   
/*  22:    */   public RenderMooshroom(ModelBase par1ModelBase, float par2)
/*  23:    */   {
/*  24: 21 */     super(par1ModelBase, par2);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void doRender(EntityMooshroom par1EntityMooshroom, double par2, double par4, double par6, float par8, float par9)
/*  28:    */   {
/*  29: 32 */     super.doRender(par1EntityMooshroom, par2, par4, par6, par8, par9);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected ResourceLocation getEntityTexture(EntityMooshroom par1EntityMooshroom)
/*  33:    */   {
/*  34: 40 */     return mooshroomTextures;
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void renderEquippedItems(EntityMooshroom par1EntityMooshroom, float par2)
/*  38:    */   {
/*  39: 45 */     super.renderEquippedItems(par1EntityMooshroom, par2);
/*  40: 47 */     if (!par1EntityMooshroom.isChild())
/*  41:    */     {
/*  42: 49 */       bindTexture(TextureMap.locationBlocksTexture);
/*  43: 50 */       GL11.glEnable(2884);
/*  44: 51 */       GL11.glPushMatrix();
/*  45: 52 */       GL11.glScalef(1.0F, -1.0F, 1.0F);
/*  46: 53 */       GL11.glTranslatef(0.2F, 0.4F, 0.5F);
/*  47: 54 */       GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
/*  48: 55 */       this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
/*  49: 56 */       GL11.glTranslatef(0.1F, 0.0F, -0.6F);
/*  50: 57 */       GL11.glRotatef(42.0F, 0.0F, 1.0F, 0.0F);
/*  51: 58 */       this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
/*  52: 59 */       GL11.glPopMatrix();
/*  53: 60 */       GL11.glPushMatrix();
/*  54: 61 */       ((ModelQuadruped)this.mainModel).head.postRender(0.0625F);
/*  55: 62 */       GL11.glScalef(1.0F, -1.0F, 1.0F);
/*  56: 63 */       GL11.glTranslatef(0.0F, 0.75F, -0.2F);
/*  57: 64 */       GL11.glRotatef(12.0F, 0.0F, 1.0F, 0.0F);
/*  58: 65 */       this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0F);
/*  59: 66 */       GL11.glPopMatrix();
/*  60: 67 */       GL11.glDisable(2884);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  65:    */   {
/*  66: 79 */     doRender((EntityMooshroom)par1EntityLiving, par2, par4, par6, par8, par9);
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/*  70:    */   {
/*  71: 84 */     renderEquippedItems((EntityMooshroom)par1EntityLivingBase, par2);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  75:    */   {
/*  76: 95 */     doRender((EntityMooshroom)par1Entity, par2, par4, par6, par8, par9);
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/*  80:    */   {
/*  81:103 */     return getEntityTexture((EntityMooshroom)par1Entity);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  85:    */   {
/*  86:114 */     doRender((EntityMooshroom)par1Entity, par2, par4, par6, par8, par9);
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderMooshroom
 * JD-Core Version:    0.7.0.1
 */