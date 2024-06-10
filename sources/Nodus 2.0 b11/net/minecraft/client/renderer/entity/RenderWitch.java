/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.client.model.ModelBase;
/*   5:    */ import net.minecraft.client.model.ModelRenderer;
/*   6:    */ import net.minecraft.client.model.ModelWitch;
/*   7:    */ import net.minecraft.client.renderer.ItemRenderer;
/*   8:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.EntityLiving;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.entity.monster.EntityWitch;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemBlock;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.util.ResourceLocation;
/*  18:    */ import org.lwjgl.opengl.GL11;
/*  19:    */ 
/*  20:    */ public class RenderWitch
/*  21:    */   extends RenderLiving
/*  22:    */ {
/*  23: 18 */   private static final ResourceLocation witchTextures = new ResourceLocation("textures/entity/witch.png");
/*  24:    */   private final ModelWitch witchModel;
/*  25:    */   private static final String __OBFID = "CL_00001033";
/*  26:    */   
/*  27:    */   public RenderWitch()
/*  28:    */   {
/*  29: 24 */     super(new ModelWitch(0.0F), 0.5F);
/*  30: 25 */     this.witchModel = ((ModelWitch)this.mainModel);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void doRender(EntityWitch par1EntityWitch, double par2, double par4, double par6, float par8, float par9)
/*  34:    */   {
/*  35: 36 */     ItemStack var10 = par1EntityWitch.getHeldItem();
/*  36: 37 */     this.witchModel.field_82900_g = (var10 != null);
/*  37: 38 */     super.doRender(par1EntityWitch, par2, par4, par6, par8, par9);
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected ResourceLocation getEntityTexture(EntityWitch par1EntityWitch)
/*  41:    */   {
/*  42: 46 */     return witchTextures;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void renderEquippedItems(EntityWitch par1EntityWitch, float par2)
/*  46:    */   {
/*  47: 51 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*  48: 52 */     super.renderEquippedItems(par1EntityWitch, par2);
/*  49: 53 */     ItemStack var3 = par1EntityWitch.getHeldItem();
/*  50: 55 */     if (var3 != null)
/*  51:    */     {
/*  52: 57 */       GL11.glPushMatrix();
/*  53: 60 */       if (this.mainModel.isChild)
/*  54:    */       {
/*  55: 62 */         float var4 = 0.5F;
/*  56: 63 */         GL11.glTranslatef(0.0F, 0.625F, 0.0F);
/*  57: 64 */         GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
/*  58: 65 */         GL11.glScalef(var4, var4, var4);
/*  59:    */       }
/*  60: 68 */       this.witchModel.villagerNose.postRender(0.0625F);
/*  61: 69 */       GL11.glTranslatef(-0.0625F, 0.53125F, 0.21875F);
/*  62: 71 */       if (((var3.getItem() instanceof ItemBlock)) && (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType())))
/*  63:    */       {
/*  64: 73 */         float var4 = 0.5F;
/*  65: 74 */         GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
/*  66: 75 */         var4 *= 0.75F;
/*  67: 76 */         GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
/*  68: 77 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*  69: 78 */         GL11.glScalef(var4, -var4, var4);
/*  70:    */       }
/*  71: 80 */       else if (var3.getItem() == Items.bow)
/*  72:    */       {
/*  73: 82 */         float var4 = 0.625F;
/*  74: 83 */         GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
/*  75: 84 */         GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
/*  76: 85 */         GL11.glScalef(var4, -var4, var4);
/*  77: 86 */         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/*  78: 87 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*  79:    */       }
/*  80: 89 */       else if (var3.getItem().isFull3D())
/*  81:    */       {
/*  82: 91 */         float var4 = 0.625F;
/*  83: 93 */         if (var3.getItem().shouldRotateAroundWhenRendering())
/*  84:    */         {
/*  85: 95 */           GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  86: 96 */           GL11.glTranslatef(0.0F, -0.125F, 0.0F);
/*  87:    */         }
/*  88: 99 */         func_82410_b();
/*  89:100 */         GL11.glScalef(var4, -var4, var4);
/*  90:101 */         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/*  91:102 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:106 */         float var4 = 0.375F;
/*  96:107 */         GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
/*  97:108 */         GL11.glScalef(var4, var4, var4);
/*  98:109 */         GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
/*  99:110 */         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 100:111 */         GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
/* 101:    */       }
/* 102:114 */       GL11.glRotatef(-15.0F, 1.0F, 0.0F, 0.0F);
/* 103:115 */       GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
/* 104:116 */       this.renderManager.itemRenderer.renderItem(par1EntityWitch, var3, 0);
/* 105:118 */       if (var3.getItem().requiresMultipleRenderPasses()) {
/* 106:120 */         this.renderManager.itemRenderer.renderItem(par1EntityWitch, var3, 1);
/* 107:    */       }
/* 108:123 */       GL11.glPopMatrix();
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected void func_82410_b()
/* 113:    */   {
/* 114:129 */     GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected void preRenderCallback(EntityWitch par1EntityWitch, float par2)
/* 118:    */   {
/* 119:138 */     float var3 = 0.9375F;
/* 120:139 */     GL11.glScalef(var3, var3, var3);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/* 124:    */   {
/* 125:150 */     doRender((EntityWitch)par1EntityLiving, par2, par4, par6, par8, par9);
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 129:    */   {
/* 130:159 */     preRenderCallback((EntityWitch)par1EntityLivingBase, par2);
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/* 134:    */   {
/* 135:164 */     renderEquippedItems((EntityWitch)par1EntityLivingBase, par2);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 139:    */   {
/* 140:175 */     doRender((EntityWitch)par1Entity, par2, par4, par6, par8, par9);
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 144:    */   {
/* 145:183 */     return getEntityTexture((EntityWitch)par1Entity);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 149:    */   {
/* 150:194 */     doRender((EntityWitch)par1Entity, par2, par4, par6, par8, par9);
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderWitch
 * JD-Core Version:    0.7.0.1
 */