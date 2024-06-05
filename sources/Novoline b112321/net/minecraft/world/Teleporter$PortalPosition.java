package net.minecraft.world;

import net.minecraft.util.BlockPos;
import net.minecraft.world.Teleporter;

public class Teleporter$PortalPosition extends BlockPos {
   public long lastUpdateTime;
   final Teleporter this$0;

   public Teleporter$PortalPosition(Teleporter var1, BlockPos var2, long var3) {
      super(var2.getX(), var2.getY(), var2.getZ());
      this.this$0 = var1;
      this.lastUpdateTime = var3;
   }
}
