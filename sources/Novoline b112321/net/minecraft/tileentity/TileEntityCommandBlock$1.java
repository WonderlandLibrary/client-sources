package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

class TileEntityCommandBlock$1 extends CommandBlockLogic {
   final TileEntityCommandBlock this$0;

   TileEntityCommandBlock$1(TileEntityCommandBlock var1) {
      this.this$0 = var1;
   }

   public BlockPos getPosition() {
      return this.this$0.pos;
   }

   public Vec3 getPositionVector() {
      return new Vec3((double)this.this$0.pos.getX() + 0.5D, (double)this.this$0.pos.getY() + 0.5D, (double)this.this$0.pos.getZ() + 0.5D);
   }

   public World getEntityWorld() {
      return this.this$0.getWorld();
   }

   public void setCommand(String var1) {
      super.setCommand(var1);
      this.this$0.markDirty();
   }

   public void updateCommand() {
      this.this$0.getWorld().markBlockForUpdate(this.this$0.pos);
   }

   public int func_145751_f() {
      return 0;
   }

   public void func_145757_a(ByteBuf var1) {
      var1.writeInt(this.this$0.pos.getX());
      var1.writeInt(this.this$0.pos.getY());
      var1.writeInt(this.this$0.pos.getZ());
   }

   public Entity getCommandSenderEntity() {
      return null;
   }
}
