/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import net.minecraft.client.renderer.RenderBlocks;
/*  5:   */ import net.minecraft.world.ChunkCache;
/*  6:   */ 
/*  7:   */ public class WrUpdateState
/*  8:   */ {
/*  9: 9 */   public ChunkCache chunkcache = null;
/* 10:10 */   public RenderBlocks renderblocks = null;
/* 11:11 */   public HashSet setOldEntityRenders = null;
/* 12:12 */   int viewEntityPosX = 0;
/* 13:13 */   int viewEntityPosY = 0;
/* 14:14 */   int viewEntityPosZ = 0;
/* 15:15 */   public int renderPass = 0;
/* 16:16 */   public int y = 0;
/* 17:17 */   public boolean flag = false;
/* 18:18 */   public boolean hasRenderedBlocks = false;
/* 19:19 */   public boolean hasGlList = false;
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WrUpdateState
 * JD-Core Version:    0.7.0.1
 */