package net;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ax2 {
   public static void a(World var0, BlockPos var1, EnumFacing var2, Block var3) {
      ItemDoor.placeDoor(var0, var1, var2, var3);
   }
}
