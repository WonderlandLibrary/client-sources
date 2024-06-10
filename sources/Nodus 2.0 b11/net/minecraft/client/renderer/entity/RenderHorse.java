/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.util.Map;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.model.ModelBase;
/*   7:    */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*   8:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.entity.passive.EntityHorse;
/*  12:    */ import net.minecraft.util.ResourceLocation;
/*  13:    */ import org.lwjgl.opengl.GL11;
/*  14:    */ 
/*  15:    */ public class RenderHorse
/*  16:    */   extends RenderLiving
/*  17:    */ {
/*  18: 16 */   private static final Map field_110852_a = ;
/*  19: 17 */   private static final ResourceLocation whiteHorseTextures = new ResourceLocation("textures/entity/horse/horse_white.png");
/*  20: 18 */   private static final ResourceLocation muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
/*  21: 19 */   private static final ResourceLocation donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
/*  22: 20 */   private static final ResourceLocation zombieHorseTextures = new ResourceLocation("textures/entity/horse/horse_zombie.png");
/*  23: 21 */   private static final ResourceLocation skeletonHorseTextures = new ResourceLocation("textures/entity/horse/horse_skeleton.png");
/*  24:    */   private static final String __OBFID = "CL_00001000";
/*  25:    */   
/*  26:    */   public RenderHorse(ModelBase par1ModelBase, float par2)
/*  27:    */   {
/*  28: 26 */     super(par1ModelBase, par2);
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void preRenderCallback(EntityHorse par1EntityHorse, float par2)
/*  32:    */   {
/*  33: 35 */     float var3 = 1.0F;
/*  34: 36 */     int var4 = par1EntityHorse.getHorseType();
/*  35: 38 */     if (var4 == 1) {
/*  36: 40 */       var3 *= 0.87F;
/*  37: 42 */     } else if (var4 == 2) {
/*  38: 44 */       var3 *= 0.92F;
/*  39:    */     }
/*  40: 47 */     GL11.glScalef(var3, var3, var3);
/*  41: 48 */     super.preRenderCallback(par1EntityHorse, par2);
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void renderModel(EntityHorse par1EntityHorse, float par2, float par3, float par4, float par5, float par6, float par7)
/*  45:    */   {
/*  46: 56 */     if (par1EntityHorse.isInvisible())
/*  47:    */     {
/*  48: 58 */       this.mainModel.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityHorse);
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52: 62 */       bindEntityTexture(par1EntityHorse);
/*  53: 63 */       this.mainModel.render(par1EntityHorse, par2, par3, par4, par5, par6, par7);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected ResourceLocation getEntityTexture(EntityHorse par1EntityHorse)
/*  58:    */   {
/*  59: 72 */     if (!par1EntityHorse.func_110239_cn())
/*  60:    */     {
/*  61: 74 */       switch (par1EntityHorse.getHorseType())
/*  62:    */       {
/*  63:    */       case 0: 
/*  64:    */       default: 
/*  65: 78 */         return whiteHorseTextures;
/*  66:    */       case 1: 
/*  67: 81 */         return donkeyTextures;
/*  68:    */       case 2: 
/*  69: 84 */         return muleTextures;
/*  70:    */       case 3: 
/*  71: 87 */         return zombieHorseTextures;
/*  72:    */       }
/*  73: 90 */       return skeletonHorseTextures;
/*  74:    */     }
/*  75: 95 */     return func_110848_b(par1EntityHorse);
/*  76:    */   }
/*  77:    */   
/*  78:    */   private ResourceLocation func_110848_b(EntityHorse par1EntityHorse)
/*  79:    */   {
/*  80:101 */     String var2 = par1EntityHorse.getHorseTexture();
/*  81:102 */     ResourceLocation var3 = (ResourceLocation)field_110852_a.get(var2);
/*  82:104 */     if (var3 == null)
/*  83:    */     {
/*  84:106 */       var3 = new ResourceLocation(var2);
/*  85:107 */       Minecraft.getMinecraft().getTextureManager().loadTexture(var3, new LayeredTexture(par1EntityHorse.getVariantTexturePaths()));
/*  86:108 */       field_110852_a.put(var2, var3);
/*  87:    */     }
/*  88:111 */     return var3;
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/*  92:    */   {
/*  93:120 */     preRenderCallback((EntityHorse)par1EntityLivingBase, par2);
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7)
/*  97:    */   {
/*  98:128 */     renderModel((EntityHorse)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 102:    */   {
/* 103:136 */     return getEntityTexture((EntityHorse)par1Entity);
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderHorse
 * JD-Core Version:    0.7.0.1
 */