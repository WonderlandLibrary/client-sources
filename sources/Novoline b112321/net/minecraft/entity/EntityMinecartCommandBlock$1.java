package net.minecraft.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent$Serializer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

class EntityMinecartCommandBlock$1 extends CommandBlockLogic {
   final EntityMinecartCommandBlock this$0;

   EntityMinecartCommandBlock$1(EntityMinecartCommandBlock var1) {
      this.this$0 = var1;
   }

   public void updateCommand() {
      this.this$0.k().a(23, this.getCommand());
      this.this$0.k().a(24, IChatComponent$Serializer.componentToJson(this.getLastOutput()));
   }

   public int func_145751_f() {
      return 1;
   }

   public void func_145757_a(ByteBuf var1) {
      var1.writeInt(this.this$0.getEntityID());
   }

   public BlockPos getPosition() {
      return new BlockPos(this.this$0.posX, this.this$0.posY + 0.5D, this.this$0.posZ);
   }

   public Vec3 getPositionVector() {
      return new Vec3(this.this$0.posX, this.this$0.posY, this.this$0.posZ);
   }

   public World getEntityWorld() {
      return this.this$0.worldObj;
   }

   public Entity getCommandSenderEntity() {
      return this.this$0;
   }
}
