package com.example.editme.events;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AddCollisionBoxToListEvent extends EditmeEvent {
   private final List collidingBoxes;
   private final IBlockState state;
   private final Entity entity;
   private final boolean bool;
   private final Block block;
   private final World world;
   private AxisAlignedBB entityBox;
   private final BlockPos pos;

   public void setEntityBox(AxisAlignedBB var1) {
      this.entityBox = var1;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public AddCollisionBoxToListEvent(Block var1, IBlockState var2, World var3, BlockPos var4, AxisAlignedBB var5, List var6, Entity var7, boolean var8) {
      this.block = var1;
      this.state = var2;
      this.world = var3;
      this.pos = var4;
      this.entityBox = var5;
      this.collidingBoxes = var6;
      this.entity = var7;
      this.bool = var8;
   }

   public IBlockState getState() {
      return this.state;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public boolean isBool() {
      return this.bool;
   }

   public Block getBlock() {
      return this.block;
   }

   public AxisAlignedBB getEntityBox() {
      return this.entityBox;
   }

   public List getCollidingBoxes() {
      return this.collidingBoxes;
   }

   public World getWorld() {
      return this.world;
   }
}
