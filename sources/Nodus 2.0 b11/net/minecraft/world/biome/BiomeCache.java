/*   1:    */ package net.minecraft.world.biome;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.server.MinecraftServer;
/*   6:    */ import net.minecraft.util.LongHashMap;
/*   7:    */ 
/*   8:    */ public class BiomeCache
/*   9:    */ {
/*  10:    */   private final WorldChunkManager chunkManager;
/*  11:    */   private long lastCleanupTime;
/*  12: 19 */   private LongHashMap cacheMap = new LongHashMap();
/*  13: 22 */   private List cache = new ArrayList();
/*  14:    */   private static final String __OBFID = "CL_00000162";
/*  15:    */   
/*  16:    */   public BiomeCache(WorldChunkManager par1WorldChunkManager)
/*  17:    */   {
/*  18: 27 */     this.chunkManager = par1WorldChunkManager;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Block getBiomeCacheBlock(int par1, int par2)
/*  22:    */   {
/*  23: 35 */     par1 >>= 4;
/*  24: 36 */     par2 >>= 4;
/*  25: 37 */     long var3 = par1 & 0xFFFFFFFF | (par2 & 0xFFFFFFFF) << 32;
/*  26: 38 */     Block var5 = (Block)this.cacheMap.getValueByKey(var3);
/*  27: 40 */     if (var5 == null)
/*  28:    */     {
/*  29: 42 */       var5 = new Block(par1, par2);
/*  30: 43 */       this.cacheMap.add(var3, var5);
/*  31: 44 */       this.cache.add(var5);
/*  32:    */     }
/*  33: 47 */     var5.lastAccessTime = MinecraftServer.getSystemTimeMillis();
/*  34: 48 */     return var5;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public BiomeGenBase getBiomeGenAt(int par1, int par2)
/*  38:    */   {
/*  39: 56 */     return getBiomeCacheBlock(par1, par2).getBiomeGenAt(par1, par2);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void cleanupCache()
/*  43:    */   {
/*  44: 64 */     long var1 = MinecraftServer.getSystemTimeMillis();
/*  45: 65 */     long var3 = var1 - this.lastCleanupTime;
/*  46: 67 */     if ((var3 > 7500L) || (var3 < 0L))
/*  47:    */     {
/*  48: 69 */       this.lastCleanupTime = var1;
/*  49: 71 */       for (int var5 = 0; var5 < this.cache.size(); var5++)
/*  50:    */       {
/*  51: 73 */         Block var6 = (Block)this.cache.get(var5);
/*  52: 74 */         long var7 = var1 - var6.lastAccessTime;
/*  53: 76 */         if ((var7 > 30000L) || (var7 < 0L))
/*  54:    */         {
/*  55: 78 */           this.cache.remove(var5--);
/*  56: 79 */           long var9 = var6.xPosition & 0xFFFFFFFF | (var6.zPosition & 0xFFFFFFFF) << 32;
/*  57: 80 */           this.cacheMap.remove(var9);
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public BiomeGenBase[] getCachedBiomes(int par1, int par2)
/*  64:    */   {
/*  65: 91 */     return getBiomeCacheBlock(par1, par2).biomes;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public class Block
/*  69:    */   {
/*  70: 96 */     public float[] rainfallValues = new float[256];
/*  71: 97 */     public BiomeGenBase[] biomes = new BiomeGenBase[256];
/*  72:    */     public int xPosition;
/*  73:    */     public int zPosition;
/*  74:    */     public long lastAccessTime;
/*  75:    */     private static final String __OBFID = "CL_00000163";
/*  76:    */     
/*  77:    */     public Block(int par2, int par3)
/*  78:    */     {
/*  79:105 */       this.xPosition = par2;
/*  80:106 */       this.zPosition = par3;
/*  81:107 */       BiomeCache.this.chunkManager.getRainfall(this.rainfallValues, par2 << 4, par3 << 4, 16, 16);
/*  82:108 */       BiomeCache.this.chunkManager.getBiomeGenAt(this.biomes, par2 << 4, par3 << 4, 16, 16, false);
/*  83:    */     }
/*  84:    */     
/*  85:    */     public BiomeGenBase getBiomeGenAt(int par1, int par2)
/*  86:    */     {
/*  87:113 */       return this.biomes[(par1 & 0xF | (par2 & 0xF) << 4)];
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.biome.BiomeCache
 * JD-Core Version:    0.7.0.1
 */