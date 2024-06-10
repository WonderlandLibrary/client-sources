/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.model.ModelBiped;
/*   4:    */ import net.minecraft.client.model.ModelZombie;
/*   5:    */ import net.minecraft.client.model.ModelZombieVillager;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.EntityLiving;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.entity.monster.EntityPigZombie;
/*  10:    */ import net.minecraft.entity.monster.EntityZombie;
/*  11:    */ import net.minecraft.util.ResourceLocation;
/*  12:    */ 
/*  13:    */ public class RenderZombie
/*  14:    */   extends RenderBiped
/*  15:    */ {
/*  16: 15 */   private static final ResourceLocation zombiePigmanTextures = new ResourceLocation("textures/entity/zombie_pigman.png");
/*  17: 16 */   private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
/*  18: 17 */   private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
/*  19:    */   private ModelBiped field_82434_o;
/*  20:    */   private ModelZombieVillager zombieVillagerModel;
/*  21:    */   protected ModelBiped field_82437_k;
/*  22:    */   protected ModelBiped field_82435_l;
/*  23:    */   protected ModelBiped field_82436_m;
/*  24:    */   protected ModelBiped field_82433_n;
/*  25: 24 */   private int field_82431_q = 1;
/*  26:    */   private static final String __OBFID = "CL_00001037";
/*  27:    */   
/*  28:    */   public RenderZombie()
/*  29:    */   {
/*  30: 29 */     super(new ModelZombie(), 0.5F, 1.0F);
/*  31: 30 */     this.field_82434_o = this.modelBipedMain;
/*  32: 31 */     this.zombieVillagerModel = new ModelZombieVillager();
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected void func_82421_b()
/*  36:    */   {
/*  37: 36 */     this.field_82423_g = new ModelZombie(1.0F, true);
/*  38: 37 */     this.field_82425_h = new ModelZombie(0.5F, true);
/*  39: 38 */     this.field_82437_k = this.field_82423_g;
/*  40: 39 */     this.field_82435_l = this.field_82425_h;
/*  41: 40 */     this.field_82436_m = new ModelZombieVillager(1.0F, 0.0F, true);
/*  42: 41 */     this.field_82433_n = new ModelZombieVillager(0.5F, 0.0F, true);
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected int shouldRenderPass(EntityZombie par1EntityZombie, int par2, float par3)
/*  46:    */   {
/*  47: 49 */     func_82427_a(par1EntityZombie);
/*  48: 50 */     return super.shouldRenderPass(par1EntityZombie, par2, par3);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void doRender(EntityZombie par1EntityZombie, double par2, double par4, double par6, float par8, float par9)
/*  52:    */   {
/*  53: 61 */     func_82427_a(par1EntityZombie);
/*  54: 62 */     super.doRender(par1EntityZombie, par2, par4, par6, par8, par9);
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected ResourceLocation getEntityTexture(EntityZombie par1EntityZombie)
/*  58:    */   {
/*  59: 70 */     return par1EntityZombie.isVillager() ? zombieVillagerTextures : (par1EntityZombie instanceof EntityPigZombie) ? zombiePigmanTextures : zombieTextures;
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected void renderEquippedItems(EntityZombie par1EntityZombie, float par2)
/*  63:    */   {
/*  64: 75 */     func_82427_a(par1EntityZombie);
/*  65: 76 */     super.renderEquippedItems(par1EntityZombie, par2);
/*  66:    */   }
/*  67:    */   
/*  68:    */   private void func_82427_a(EntityZombie par1EntityZombie)
/*  69:    */   {
/*  70: 81 */     if (par1EntityZombie.isVillager())
/*  71:    */     {
/*  72: 83 */       if (this.field_82431_q != this.zombieVillagerModel.func_82897_a())
/*  73:    */       {
/*  74: 85 */         this.zombieVillagerModel = new ModelZombieVillager();
/*  75: 86 */         this.field_82431_q = this.zombieVillagerModel.func_82897_a();
/*  76: 87 */         this.field_82436_m = new ModelZombieVillager(1.0F, 0.0F, true);
/*  77: 88 */         this.field_82433_n = new ModelZombieVillager(0.5F, 0.0F, true);
/*  78:    */       }
/*  79: 91 */       this.mainModel = this.zombieVillagerModel;
/*  80: 92 */       this.field_82423_g = this.field_82436_m;
/*  81: 93 */       this.field_82425_h = this.field_82433_n;
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85: 97 */       this.mainModel = this.field_82434_o;
/*  86: 98 */       this.field_82423_g = this.field_82437_k;
/*  87: 99 */       this.field_82425_h = this.field_82435_l;
/*  88:    */     }
/*  89:102 */     this.modelBipedMain = ((ModelBiped)this.mainModel);
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected void rotateCorpse(EntityZombie par1EntityZombie, float par2, float par3, float par4)
/*  93:    */   {
/*  94:107 */     if (par1EntityZombie.isConverting()) {
/*  95:109 */       par3 += (float)(Math.cos(par1EntityZombie.ticksExisted * 3.25D) * 3.141592653589793D * 0.25D);
/*  96:    */     }
/*  97:112 */     super.rotateCorpse(par1EntityZombie, par2, par3, par4);
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
/* 101:    */   {
/* 102:117 */     renderEquippedItems((EntityZombie)par1EntityLiving, par2);
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
/* 106:    */   {
/* 107:125 */     return getEntityTexture((EntityZombie)par1EntityLiving);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/* 111:    */   {
/* 112:136 */     doRender((EntityZombie)par1EntityLiving, par2, par4, par6, par8, par9);
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
/* 116:    */   {
/* 117:144 */     return shouldRenderPass((EntityZombie)par1EntityLiving, par2, par3);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 121:    */   {
/* 122:152 */     return shouldRenderPass((EntityZombie)par1EntityLivingBase, par2, par3);
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/* 126:    */   {
/* 127:157 */     renderEquippedItems((EntityZombie)par1EntityLivingBase, par2);
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 131:    */   {
/* 132:162 */     rotateCorpse((EntityZombie)par1EntityLivingBase, par2, par3, par4);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 136:    */   {
/* 137:173 */     doRender((EntityZombie)par1Entity, par2, par4, par6, par8, par9);
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 141:    */   {
/* 142:181 */     return getEntityTexture((EntityZombie)par1Entity);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 146:    */   {
/* 147:192 */     doRender((EntityZombie)par1Entity, par2, par4, par6, par8, par9);
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderZombie
 * JD-Core Version:    0.7.0.1
 */