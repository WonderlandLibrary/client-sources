/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Random;
/*  6:   */ import net.minecraft.world.ChunkPosition;
/*  7:   */ 
/*  8:   */ public class WorldChunkManagerHell
/*  9:   */   extends WorldChunkManager
/* 10:   */ {
/* 11:   */   private BiomeGenBase biomeGenerator;
/* 12:   */   private float rainfall;
/* 13:   */   private static final String __OBFID = "CL_00000169";
/* 14:   */   
/* 15:   */   public WorldChunkManagerHell(BiomeGenBase p_i45374_1_, float p_i45374_2_)
/* 16:   */   {
/* 17:19 */     this.biomeGenerator = p_i45374_1_;
/* 18:20 */     this.rainfall = p_i45374_2_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public BiomeGenBase getBiomeGenAt(int par1, int par2)
/* 22:   */   {
/* 23:28 */     return this.biomeGenerator;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
/* 27:   */   {
/* 28:36 */     if ((par1ArrayOfBiomeGenBase == null) || (par1ArrayOfBiomeGenBase.length < par4 * par5)) {
/* 29:38 */       par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
/* 30:   */     }
/* 31:41 */     Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeGenerator);
/* 32:42 */     return par1ArrayOfBiomeGenBase;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
/* 36:   */   {
/* 37:50 */     if ((par1ArrayOfFloat == null) || (par1ArrayOfFloat.length < par4 * par5)) {
/* 38:52 */       par1ArrayOfFloat = new float[par4 * par5];
/* 39:   */     }
/* 40:55 */     Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.rainfall);
/* 41:56 */     return par1ArrayOfFloat;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
/* 45:   */   {
/* 46:65 */     if ((par1ArrayOfBiomeGenBase == null) || (par1ArrayOfBiomeGenBase.length < par4 * par5)) {
/* 47:67 */       par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
/* 48:   */     }
/* 49:70 */     Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeGenerator);
/* 50:71 */     return par1ArrayOfBiomeGenBase;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
/* 54:   */   {
/* 55:80 */     return loadBlockGeneratorData(par1ArrayOfBiomeGenBase, par2, par3, par4, par5);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public ChunkPosition func_150795_a(int p_150795_1_, int p_150795_2_, int p_150795_3_, List p_150795_4_, Random p_150795_5_)
/* 59:   */   {
/* 60:85 */     return p_150795_4_.contains(this.biomeGenerator) ? new ChunkPosition(p_150795_1_ - p_150795_3_ + p_150795_5_.nextInt(p_150795_3_ * 2 + 1), 0, p_150795_2_ - p_150795_3_ + p_150795_5_.nextInt(p_150795_3_ * 2 + 1)) : null;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
/* 64:   */   {
/* 65:93 */     return par4List.contains(this.biomeGenerator);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.WorldChunkManagerHell
 * JD-Core Version:    0.7.0.1
 */