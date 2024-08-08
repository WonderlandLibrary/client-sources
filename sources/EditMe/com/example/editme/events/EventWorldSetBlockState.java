package com.example.editme.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class EventWorldSetBlockState extends EditmeEvent {
   public IBlockState NewState;
   public int Flags;
   public BlockPos Pos;

   public EventWorldSetBlockState(BlockPos var1, IBlockState var2, int var3) {
      this.Pos = var1;
      this.NewState = var2;
      this.Flags = var3;
   }
}
