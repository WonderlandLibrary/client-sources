package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti$1;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;

public class WorldServerMulti extends WorldServer {
   private WorldServer delegate;

   public WorldServerMulti(MinecraftServer var1, ISaveHandler var2, int var3, WorldServer var4, Profiler var5) {
      super(var1, var2, new DerivedWorldInfo(var4.getWorldInfo()), var3, var5);
      this.delegate = var4;
      var4.getWorldBorder().addListener(new WorldServerMulti$1(this));
   }

   protected void saveLevel() throws MinecraftException {
   }

   public World init() {
      this.mapStorage = this.delegate.getMapStorage();
      this.worldScoreboard = this.delegate.getScoreboard();
      String var1 = VillageCollection.fileNameForProvider(this.provider);
      VillageCollection var2 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, var1);
      this.villageCollectionObj = new VillageCollection(this);
      this.mapStorage.setData(var1, this.villageCollectionObj);
      return this;
   }
}
