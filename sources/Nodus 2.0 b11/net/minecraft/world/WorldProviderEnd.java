/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.util.ChunkCoordinates;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import net.minecraft.util.Vec3;
/*   8:    */ import net.minecraft.util.Vec3Pool;
/*   9:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  10:    */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*  11:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  12:    */ import net.minecraft.world.gen.ChunkProviderEnd;
/*  13:    */ 
/*  14:    */ public class WorldProviderEnd
/*  15:    */   extends WorldProvider
/*  16:    */ {
/*  17:    */   private static final String __OBFID = "CL_00000389";
/*  18:    */   
/*  19:    */   public void registerWorldChunkManager()
/*  20:    */   {
/*  21: 20 */     this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
/*  22: 21 */     this.dimensionId = 1;
/*  23: 22 */     this.hasNoSky = true;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public IChunkProvider createChunkGenerator()
/*  27:    */   {
/*  28: 30 */     return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public float calculateCelestialAngle(long par1, float par3)
/*  32:    */   {
/*  33: 38 */     return 0.0F;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public float[] calcSunriseSunsetColors(float par1, float par2)
/*  37:    */   {
/*  38: 46 */     return null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Vec3 getFogColor(float par1, float par2)
/*  42:    */   {
/*  43: 54 */     int var3 = 10518688;
/*  44: 55 */     float var4 = MathHelper.cos(par1 * 3.141593F * 2.0F) * 2.0F + 0.5F;
/*  45: 57 */     if (var4 < 0.0F) {
/*  46: 59 */       var4 = 0.0F;
/*  47:    */     }
/*  48: 62 */     if (var4 > 1.0F) {
/*  49: 64 */       var4 = 1.0F;
/*  50:    */     }
/*  51: 67 */     float var5 = (var3 >> 16 & 0xFF) / 255.0F;
/*  52: 68 */     float var6 = (var3 >> 8 & 0xFF) / 255.0F;
/*  53: 69 */     float var7 = (var3 & 0xFF) / 255.0F;
/*  54: 70 */     var5 *= (var4 * 0.0F + 0.15F);
/*  55: 71 */     var6 *= (var4 * 0.0F + 0.15F);
/*  56: 72 */     var7 *= (var4 * 0.0F + 0.15F);
/*  57: 73 */     return this.worldObj.getWorldVec3Pool().getVecFromPool(var5, var6, var7);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isSkyColored()
/*  61:    */   {
/*  62: 78 */     return false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean canRespawnHere()
/*  66:    */   {
/*  67: 86 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean isSurfaceWorld()
/*  71:    */   {
/*  72: 94 */     return false;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public float getCloudHeight()
/*  76:    */   {
/*  77:102 */     return 8.0F;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean canCoordinateBeSpawn(int par1, int par2)
/*  81:    */   {
/*  82:110 */     return this.worldObj.getTopBlock(par1, par2).getMaterial().blocksMovement();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public ChunkCoordinates getEntrancePortalLocation()
/*  86:    */   {
/*  87:118 */     return new ChunkCoordinates(100, 50, 0);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getAverageGroundLevel()
/*  91:    */   {
/*  92:123 */     return 50;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean doesXZShowFog(int par1, int par2)
/*  96:    */   {
/*  97:131 */     return true;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getDimensionName()
/* 101:    */   {
/* 102:139 */     return "The End";
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldProviderEnd
 * JD-Core Version:    0.7.0.1
 */