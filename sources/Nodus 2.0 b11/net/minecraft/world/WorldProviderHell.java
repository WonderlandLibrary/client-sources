/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.Vec3;
/*  4:   */ import net.minecraft.util.Vec3Pool;
/*  5:   */ import net.minecraft.world.biome.BiomeGenBase;
/*  6:   */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*  7:   */ import net.minecraft.world.chunk.IChunkProvider;
/*  8:   */ import net.minecraft.world.gen.ChunkProviderHell;
/*  9:   */ 
/* 10:   */ public class WorldProviderHell
/* 11:   */   extends WorldProvider
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000387";
/* 14:   */   
/* 15:   */   public void registerWorldChunkManager()
/* 16:   */   {
/* 17:18 */     this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F);
/* 18:19 */     this.isHellWorld = true;
/* 19:20 */     this.hasNoSky = true;
/* 20:21 */     this.dimensionId = -1;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Vec3 getFogColor(float par1, float par2)
/* 24:   */   {
/* 25:29 */     return this.worldObj.getWorldVec3Pool().getVecFromPool(0.2000000029802322D, 0.02999999932944775D, 0.02999999932944775D);
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected void generateLightBrightnessTable()
/* 29:   */   {
/* 30:37 */     float var1 = 0.1F;
/* 31:39 */     for (int var2 = 0; var2 <= 15; var2++)
/* 32:   */     {
/* 33:41 */       float var3 = 1.0F - var2 / 15.0F;
/* 34:42 */       this.lightBrightnessTable[var2] = ((1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public IChunkProvider createChunkGenerator()
/* 39:   */   {
/* 40:51 */     return new ChunkProviderHell(this.worldObj, this.worldObj.getSeed());
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean isSurfaceWorld()
/* 44:   */   {
/* 45:59 */     return false;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean canCoordinateBeSpawn(int par1, int par2)
/* 49:   */   {
/* 50:67 */     return false;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public float calculateCelestialAngle(long par1, float par3)
/* 54:   */   {
/* 55:75 */     return 0.5F;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean canRespawnHere()
/* 59:   */   {
/* 60:83 */     return false;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public boolean doesXZShowFog(int par1, int par2)
/* 64:   */   {
/* 65:91 */     return true;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String getDimensionName()
/* 69:   */   {
/* 70:99 */     return "Nether";
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldProviderHell
 * JD-Core Version:    0.7.0.1
 */