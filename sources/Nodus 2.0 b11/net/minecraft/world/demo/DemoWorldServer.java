/*  1:   */ package net.minecraft.world.demo;
/*  2:   */ 
/*  3:   */ import net.minecraft.profiler.Profiler;
/*  4:   */ import net.minecraft.server.MinecraftServer;
/*  5:   */ import net.minecraft.world.WorldServer;
/*  6:   */ import net.minecraft.world.WorldSettings;
/*  7:   */ import net.minecraft.world.WorldSettings.GameType;
/*  8:   */ import net.minecraft.world.WorldType;
/*  9:   */ import net.minecraft.world.storage.ISaveHandler;
/* 10:   */ 
/* 11:   */ public class DemoWorldServer
/* 12:   */   extends WorldServer
/* 13:   */ {
/* 14:12 */   private static final long demoWorldSeed = "North Carolina".hashCode();
/* 15:13 */   public static final WorldSettings demoWorldSettings = new WorldSettings(demoWorldSeed, WorldSettings.GameType.SURVIVAL, true, false, WorldType.DEFAULT).enableBonusChest();
/* 16:   */   private static final String __OBFID = "CL_00001428";
/* 17:   */   
/* 18:   */   public DemoWorldServer(MinecraftServer p_i45282_1_, ISaveHandler p_i45282_2_, String p_i45282_3_, int p_i45282_4_, Profiler p_i45282_5_)
/* 19:   */   {
/* 20:18 */     super(p_i45282_1_, p_i45282_2_, p_i45282_3_, p_i45282_4_, demoWorldSettings, p_i45282_5_);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.demo.DemoWorldServer
 * JD-Core Version:    0.7.0.1
 */