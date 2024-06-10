/*   1:    */ package net.minecraft.client.particle;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.BlockLiquid;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class EntityDropParticleFX
/*  10:    */   extends EntityFX
/*  11:    */ {
/*  12:    */   private Material materialType;
/*  13:    */   private int bobTimer;
/*  14:    */   private static final String __OBFID = "CL_00000901";
/*  15:    */   
/*  16:    */   public EntityDropParticleFX(World par1World, double par2, double par4, double par6, Material par8Material)
/*  17:    */   {
/*  18: 19 */     super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
/*  19: 20 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  20: 22 */     if (par8Material == Material.water)
/*  21:    */     {
/*  22: 24 */       this.particleRed = 0.0F;
/*  23: 25 */       this.particleGreen = 0.0F;
/*  24: 26 */       this.particleBlue = 1.0F;
/*  25:    */     }
/*  26:    */     else
/*  27:    */     {
/*  28: 30 */       this.particleRed = 1.0F;
/*  29: 31 */       this.particleGreen = 0.0F;
/*  30: 32 */       this.particleBlue = 0.0F;
/*  31:    */     }
/*  32: 35 */     setParticleTextureIndex(113);
/*  33: 36 */     setSize(0.01F, 0.01F);
/*  34: 37 */     this.particleGravity = 0.06F;
/*  35: 38 */     this.materialType = par8Material;
/*  36: 39 */     this.bobTimer = 40;
/*  37: 40 */     this.particleMaxAge = ((int)(64.0D / (Math.random() * 0.8D + 0.2D)));
/*  38: 41 */     this.motionX = (this.motionY = this.motionZ = 0.0D);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getBrightnessForRender(float par1)
/*  42:    */   {
/*  43: 46 */     return this.materialType == Material.water ? super.getBrightnessForRender(par1) : 257;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public float getBrightness(float par1)
/*  47:    */   {
/*  48: 54 */     return this.materialType == Material.water ? super.getBrightness(par1) : 1.0F;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void onUpdate()
/*  52:    */   {
/*  53: 62 */     this.prevPosX = this.posX;
/*  54: 63 */     this.prevPosY = this.posY;
/*  55: 64 */     this.prevPosZ = this.posZ;
/*  56: 66 */     if (this.materialType == Material.water)
/*  57:    */     {
/*  58: 68 */       this.particleRed = 0.2F;
/*  59: 69 */       this.particleGreen = 0.3F;
/*  60: 70 */       this.particleBlue = 1.0F;
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 74 */       this.particleRed = 1.0F;
/*  65: 75 */       this.particleGreen = (16.0F / (40 - this.bobTimer + 16));
/*  66: 76 */       this.particleBlue = (4.0F / (40 - this.bobTimer + 8));
/*  67:    */     }
/*  68: 79 */     this.motionY -= this.particleGravity;
/*  69: 81 */     if (this.bobTimer-- > 0)
/*  70:    */     {
/*  71: 83 */       this.motionX *= 0.02D;
/*  72: 84 */       this.motionY *= 0.02D;
/*  73: 85 */       this.motionZ *= 0.02D;
/*  74: 86 */       setParticleTextureIndex(113);
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78: 90 */       setParticleTextureIndex(112);
/*  79:    */     }
/*  80: 93 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  81: 94 */     this.motionX *= 0.9800000190734863D;
/*  82: 95 */     this.motionY *= 0.9800000190734863D;
/*  83: 96 */     this.motionZ *= 0.9800000190734863D;
/*  84: 98 */     if (this.particleMaxAge-- <= 0) {
/*  85:100 */       setDead();
/*  86:    */     }
/*  87:103 */     if (this.onGround)
/*  88:    */     {
/*  89:105 */       if (this.materialType == Material.water)
/*  90:    */       {
/*  91:107 */         setDead();
/*  92:108 */         this.worldObj.spawnParticle("splash", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
/*  93:    */       }
/*  94:    */       else
/*  95:    */       {
/*  96:112 */         setParticleTextureIndex(114);
/*  97:    */       }
/*  98:115 */       this.motionX *= 0.699999988079071D;
/*  99:116 */       this.motionZ *= 0.699999988079071D;
/* 100:    */     }
/* 101:119 */     Material var1 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial();
/* 102:121 */     if ((var1.isLiquid()) || (var1.isSolid()))
/* 103:    */     {
/* 104:123 */       double var2 = MathHelper.floor_double(this.posY) + 1 - BlockLiquid.func_149801_b(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
/* 105:125 */       if (this.posY < var2) {
/* 106:127 */         setDead();
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EntityDropParticleFX
 * JD-Core Version:    0.7.0.1
 */