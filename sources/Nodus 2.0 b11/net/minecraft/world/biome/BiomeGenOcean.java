/*  1:   */ package net.minecraft.world.biome;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Random;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class BiomeGenOcean
/*  9:   */   extends BiomeGenBase
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000179";
/* 12:   */   
/* 13:   */   public BiomeGenOcean(int par1)
/* 14:   */   {
/* 15:13 */     super(par1);
/* 16:14 */     this.spawnableCreatureList.clear();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public BiomeGenBase.TempCategory func_150561_m()
/* 20:   */   {
/* 21:19 */     return BiomeGenBase.TempCategory.OCEAN;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void func_150573_a(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
/* 25:   */   {
/* 26:24 */     super.func_150573_a(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeGenOcean
 * JD-Core Version:    0.7.0.1
 */