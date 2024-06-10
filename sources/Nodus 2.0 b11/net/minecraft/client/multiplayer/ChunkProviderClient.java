/*   1:    */ package net.minecraft.client.multiplayer;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.entity.EnumCreatureType;
/*   7:    */ import net.minecraft.util.IProgressUpdate;
/*   8:    */ import net.minecraft.util.LongHashMap;
/*   9:    */ import net.minecraft.world.ChunkCoordIntPair;
/*  10:    */ import net.minecraft.world.ChunkPosition;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ import net.minecraft.world.chunk.Chunk;
/*  13:    */ import net.minecraft.world.chunk.EmptyChunk;
/*  14:    */ import net.minecraft.world.chunk.IChunkProvider;
/*  15:    */ import org.apache.logging.log4j.LogManager;
/*  16:    */ import org.apache.logging.log4j.Logger;
/*  17:    */ 
/*  18:    */ public class ChunkProviderClient
/*  19:    */   implements IChunkProvider
/*  20:    */ {
/*  21: 20 */   private static final Logger logger = ;
/*  22:    */   private Chunk blankChunk;
/*  23: 31 */   private LongHashMap chunkMapping = new LongHashMap();
/*  24: 37 */   private List chunkListing = new ArrayList();
/*  25:    */   private World worldObj;
/*  26:    */   private static final String __OBFID = "CL_00000880";
/*  27:    */   
/*  28:    */   public ChunkProviderClient(World par1World)
/*  29:    */   {
/*  30: 45 */     this.blankChunk = new EmptyChunk(par1World, 0, 0);
/*  31: 46 */     this.worldObj = par1World;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean chunkExists(int par1, int par2)
/*  35:    */   {
/*  36: 54 */     return true;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void unloadChunk(int par1, int par2)
/*  40:    */   {
/*  41: 63 */     Chunk var3 = provideChunk(par1, par2);
/*  42: 65 */     if (!var3.isEmpty()) {
/*  43: 67 */       var3.onChunkUnload();
/*  44:    */     }
/*  45: 70 */     this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
/*  46: 71 */     this.chunkListing.remove(var3);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Chunk loadChunk(int par1, int par2)
/*  50:    */   {
/*  51: 79 */     Chunk var3 = new Chunk(this.worldObj, par1, par2);
/*  52: 80 */     this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2), var3);
/*  53: 81 */     this.chunkListing.add(var3);
/*  54: 82 */     var3.isChunkLoaded = true;
/*  55: 83 */     return var3;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Chunk provideChunk(int par1, int par2)
/*  59:    */   {
/*  60: 92 */     Chunk var3 = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
/*  61: 93 */     return var3 == null ? this.blankChunk : var3;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
/*  65:    */   {
/*  66:102 */     return true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void saveExtraData() {}
/*  70:    */   
/*  71:    */   public boolean unloadQueuedChunks()
/*  72:    */   {
/*  73:116 */     long var1 = System.currentTimeMillis();
/*  74:117 */     Iterator var3 = this.chunkListing.iterator();
/*  75:119 */     while (var3.hasNext())
/*  76:    */     {
/*  77:121 */       Chunk var4 = (Chunk)var3.next();
/*  78:122 */       var4.func_150804_b(System.currentTimeMillis() - var1 > 5L);
/*  79:    */     }
/*  80:125 */     if (System.currentTimeMillis() - var1 > 100L) {
/*  81:127 */       logger.info("Warning: Clientside chunk ticking took {} ms", new Object[] { Long.valueOf(System.currentTimeMillis() - var1) });
/*  82:    */     }
/*  83:130 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean canSave()
/*  87:    */   {
/*  88:138 */     return false;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {}
/*  92:    */   
/*  93:    */   public String makeString()
/*  94:    */   {
/*  95:151 */     return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
/*  99:    */   {
/* 100:159 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
/* 104:    */   {
/* 105:164 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getLoadedChunkCount()
/* 109:    */   {
/* 110:169 */     return this.chunkListing.size();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void recreateStructures(int par1, int par2) {}
/* 114:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.multiplayer.ChunkProviderClient
 * JD-Core Version:    0.7.0.1
 */