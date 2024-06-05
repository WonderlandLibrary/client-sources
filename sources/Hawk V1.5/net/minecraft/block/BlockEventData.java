package net.minecraft.block;

import net.minecraft.util.BlockPos;

public class BlockEventData {
   private static final String __OBFID = "CL_00000131";
   private int eventParameter;
   private int eventID;
   private BlockPos field_180329_a;
   private Block field_151344_d;

   public boolean equals(Object var1) {
      if (!(var1 instanceof BlockEventData)) {
         return false;
      } else {
         BlockEventData var2 = (BlockEventData)var1;
         return this.field_180329_a.equals(var2.field_180329_a) && this.eventID == var2.eventID && this.eventParameter == var2.eventParameter && this.field_151344_d == var2.field_151344_d;
      }
   }

   public int getEventID() {
      return this.eventID;
   }

   public BlockEventData(BlockPos var1, Block var2, int var3, int var4) {
      this.field_180329_a = var1;
      this.eventID = var3;
      this.eventParameter = var4;
      this.field_151344_d = var2;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("TE(")).append(this.field_180329_a).append("),").append(this.eventID).append(",").append(this.eventParameter).append(",").append(this.field_151344_d));
   }

   public BlockPos func_180328_a() {
      return this.field_180329_a;
   }

   public int getEventParameter() {
      return this.eventParameter;
   }

   public Block getBlock() {
      return this.field_151344_d;
   }
}
