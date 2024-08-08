package com.example.editme.util.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class EmulatorWorld extends WorldServer {
   public EmulatorWorld(MinecraftServer var1, ISaveHandler var2, WorldInfo var3, int var4, Profiler var5, long var6) {
      super(var1, var2, var3, var4, var5);
      WorldSettings var8 = new WorldSettings(var6, GameType.SURVIVAL, true, false, WorldType.field_77137_b);
      this.field_72986_A.func_176127_a(var8);
   }
}
