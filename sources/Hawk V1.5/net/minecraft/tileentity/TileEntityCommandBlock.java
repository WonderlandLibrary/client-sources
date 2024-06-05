package net.minecraft.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityCommandBlock extends TileEntity {
   private final CommandBlockLogic field_145994_a = new CommandBlockLogic(this) {
      private static final String __OBFID = "CL_00000348";
      final TileEntityCommandBlock this$0;

      {
         this.this$0 = var1;
      }

      public BlockPos getPosition() {
         return this.this$0.pos;
      }

      public Vec3 getPositionVector() {
         return new Vec3((double)this.this$0.pos.getX() + 0.5D, (double)this.this$0.pos.getY() + 0.5D, (double)this.this$0.pos.getZ() + 0.5D);
      }

      public void setCommand(String var1) {
         super.setCommand(var1);
         this.this$0.markDirty();
      }

      public void func_145756_e() {
         this.this$0.getWorld().markBlockForUpdate(this.this$0.pos);
      }

      public Entity getCommandSenderEntity() {
         return null;
      }

      public int func_145751_f() {
         return 0;
      }

      public World getEntityWorld() {
         return this.this$0.getWorld();
      }

      public void func_145757_a(ByteBuf var1) {
         var1.writeInt(this.this$0.pos.getX());
         var1.writeInt(this.this$0.pos.getY());
         var1.writeInt(this.this$0.pos.getZ());
      }
   };
   private static final String __OBFID = "CL_00000347";

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.field_145994_a.readDataFromNBT(var1);
   }

   public CommandBlockLogic getCommandBlockLogic() {
      return this.field_145994_a;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      this.field_145994_a.writeDataToNBT(var1);
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new S35PacketUpdateTileEntity(this.pos, 2, var1);
   }

   public CommandResultStats func_175124_c() {
      return this.field_145994_a.func_175572_n();
   }
}
