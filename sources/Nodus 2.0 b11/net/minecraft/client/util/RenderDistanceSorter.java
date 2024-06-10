/*  1:   */ package net.minecraft.client.util;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.ComparisonChain;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import net.minecraft.client.renderer.RenderList;
/*  6:   */ 
/*  7:   */ public class RenderDistanceSorter
/*  8:   */   implements Comparator
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000945";
/* 11:   */   
/* 12:   */   public int compare(RenderList p_147714_1_, RenderList p_147714_2_)
/* 13:   */   {
/* 14:13 */     int var3 = p_147714_2_.renderChunkX * p_147714_2_.renderChunkX + p_147714_2_.renderChunkY * p_147714_2_.renderChunkY + p_147714_2_.renderChunkZ * p_147714_2_.renderChunkZ;
/* 15:14 */     int var4 = p_147714_1_.renderChunkX * p_147714_1_.renderChunkX + p_147714_1_.renderChunkY * p_147714_1_.renderChunkY + p_147714_1_.renderChunkZ * p_147714_1_.renderChunkZ;
/* 16:15 */     return ComparisonChain.start().compare(var3, var4).result();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int compare(Object par1Obj, Object par2Obj)
/* 20:   */   {
/* 21:20 */     return compare((RenderList)par1Obj, (RenderList)par2Obj);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.util.RenderDistanceSorter
 * JD-Core Version:    0.7.0.1
 */