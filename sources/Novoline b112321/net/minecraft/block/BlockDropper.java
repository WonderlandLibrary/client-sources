package net.minecraft.block;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockDropper extends BlockDispenser {
   private final IBehaviorDispenseItem dropBehavior = new BehaviorDefaultDispenseItem();

   protected IBehaviorDispenseItem getBehavior(ItemStack var1) {
      return this.dropBehavior;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityDropper();
   }

   protected void a(World var1, BlockPos var2) {
      BlockSourceImpl var3 = new BlockSourceImpl(var1, var2);
      TileEntityDispenser var4 = (TileEntityDispenser)var3.getBlockTileEntity();
      int var5 = var4.getDispenseSlot();
      var1.playAuxSFX(1001, var2, 0);
   }
}
