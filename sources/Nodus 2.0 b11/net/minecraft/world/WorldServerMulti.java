/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ import net.minecraft.profiler.Profiler;
/*  4:   */ import net.minecraft.server.MinecraftServer;
/*  5:   */ import net.minecraft.world.storage.DerivedWorldInfo;
/*  6:   */ import net.minecraft.world.storage.ISaveHandler;
/*  7:   */ 
/*  8:   */ public class WorldServerMulti
/*  9:   */   extends WorldServer
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001430";
/* 12:   */   
/* 13:   */   public WorldServerMulti(MinecraftServer p_i45283_1_, ISaveHandler p_i45283_2_, String p_i45283_3_, int p_i45283_4_, WorldSettings p_i45283_5_, WorldServer p_i45283_6_, Profiler p_i45283_7_)
/* 14:   */   {
/* 15:14 */     super(p_i45283_1_, p_i45283_2_, p_i45283_3_, p_i45283_4_, p_i45283_5_, p_i45283_7_);
/* 16:15 */     this.mapStorage = p_i45283_6_.mapStorage;
/* 17:16 */     this.worldScoreboard = p_i45283_6_.getScoreboard();
/* 18:17 */     this.worldInfo = new DerivedWorldInfo(p_i45283_6_.getWorldInfo());
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void saveLevel()
/* 22:   */     throws MinecraftException
/* 23:   */   {}
/* 24:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldServerMulti
 * JD-Core Version:    0.7.0.1
 */