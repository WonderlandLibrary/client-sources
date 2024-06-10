/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelVillager;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.passive.EntityVillager;
/*   8:    */ import net.minecraft.util.ResourceLocation;
/*   9:    */ import org.lwjgl.opengl.GL11;
/*  10:    */ 
/*  11:    */ public class RenderVillager
/*  12:    */   extends RenderLiving
/*  13:    */ {
/*  14: 13 */   private static final ResourceLocation villagerTextures = new ResourceLocation("textures/entity/villager/villager.png");
/*  15: 14 */   private static final ResourceLocation farmerVillagerTextures = new ResourceLocation("textures/entity/villager/farmer.png");
/*  16: 15 */   private static final ResourceLocation librarianVillagerTextures = new ResourceLocation("textures/entity/villager/librarian.png");
/*  17: 16 */   private static final ResourceLocation priestVillagerTextures = new ResourceLocation("textures/entity/villager/priest.png");
/*  18: 17 */   private static final ResourceLocation smithVillagerTextures = new ResourceLocation("textures/entity/villager/smith.png");
/*  19: 18 */   private static final ResourceLocation butcherVillagerTextures = new ResourceLocation("textures/entity/villager/butcher.png");
/*  20:    */   protected ModelVillager villagerModel;
/*  21:    */   private static final String __OBFID = "CL_00001032";
/*  22:    */   
/*  23:    */   public RenderVillager()
/*  24:    */   {
/*  25: 26 */     super(new ModelVillager(0.0F), 0.5F);
/*  26: 27 */     this.villagerModel = ((ModelVillager)this.mainModel);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected int shouldRenderPass(EntityVillager par1EntityVillager, int par2, float par3)
/*  30:    */   {
/*  31: 35 */     return -1;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void doRender(EntityVillager par1EntityVillager, double par2, double par4, double par6, float par8, float par9)
/*  35:    */   {
/*  36: 46 */     super.doRender(par1EntityVillager, par2, par4, par6, par8, par9);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected ResourceLocation getEntityTexture(EntityVillager par1EntityVillager)
/*  40:    */   {
/*  41: 54 */     switch (par1EntityVillager.getProfession())
/*  42:    */     {
/*  43:    */     case 0: 
/*  44: 57 */       return farmerVillagerTextures;
/*  45:    */     case 1: 
/*  46: 60 */       return librarianVillagerTextures;
/*  47:    */     case 2: 
/*  48: 63 */       return priestVillagerTextures;
/*  49:    */     case 3: 
/*  50: 66 */       return smithVillagerTextures;
/*  51:    */     case 4: 
/*  52: 69 */       return butcherVillagerTextures;
/*  53:    */     }
/*  54: 72 */     return villagerTextures;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected void renderEquippedItems(EntityVillager par1EntityVillager, float par2)
/*  58:    */   {
/*  59: 78 */     super.renderEquippedItems(par1EntityVillager, par2);
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected void preRenderCallback(EntityVillager par1EntityVillager, float par2)
/*  63:    */   {
/*  64: 87 */     float var3 = 0.9375F;
/*  65: 89 */     if (par1EntityVillager.getGrowingAge() < 0)
/*  66:    */     {
/*  67: 91 */       var3 = (float)(var3 * 0.5D);
/*  68: 92 */       this.shadowSize = 0.25F;
/*  69:    */     }
/*  70:    */     else
/*  71:    */     {
/*  72: 96 */       this.shadowSize = 0.5F;
/*  73:    */     }
/*  74: 99 */     GL11.glScalef(var3, var3, var3);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/*  78:    */   {
/*  79:110 */     doRender((EntityVillager)par1EntityLiving, par2, par4, par6, par8, par9);
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/*  83:    */   {
/*  84:119 */     preRenderCallback((EntityVillager)par1EntityLivingBase, par2);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/*  88:    */   {
/*  89:127 */     return shouldRenderPass((EntityVillager)par1EntityLivingBase, par2, par3);
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/*  93:    */   {
/*  94:132 */     renderEquippedItems((EntityVillager)par1EntityLivingBase, par2);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/*  98:    */   {
/*  99:143 */     doRender((EntityVillager)par1Entity, par2, par4, par6, par8, par9);
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 103:    */   {
/* 104:151 */     return getEntityTexture((EntityVillager)par1Entity);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 108:    */   {
/* 109:162 */     doRender((EntityVillager)par1Entity, par2, par4, par6, par8, par9);
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderVillager
 * JD-Core Version:    0.7.0.1
 */