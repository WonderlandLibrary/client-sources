package com.example.editme.events;

import net.minecraft.util.math.BlockPos;

public class EventPlayerDestroyBlock extends EditmeEvent {
   public BlockPos Location;

   public EventPlayerDestroyBlock(BlockPos var1) {
      this.Location = var1;
   }
}
