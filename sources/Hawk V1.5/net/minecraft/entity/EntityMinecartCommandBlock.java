package net.minecraft.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMinecartCommandBlock extends EntityMinecart {
   private int field_145823_b = 0;
   private final CommandBlockLogic field_145824_a = new CommandBlockLogic(this) {
      private static final String __OBFID = "CL_00001673";
      final EntityMinecartCommandBlock this$0;

      public Entity getCommandSenderEntity() {
         return this.this$0;
      }

      {
         this.this$0 = var1;
      }

      public void func_145756_e() {
         this.this$0.getDataWatcher().updateObject(23, this.getCustomName());
         this.this$0.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
      }

      public void func_145757_a(ByteBuf var1) {
         var1.writeInt(this.this$0.getEntityId());
      }

      public int func_145751_f() {
         return 1;
      }

      public Vec3 getPositionVector() {
         return new Vec3(this.this$0.posX, this.this$0.posY, this.this$0.posZ);
      }

      public World getEntityWorld() {
         return this.this$0.worldObj;
      }

      public BlockPos getPosition() {
         return new BlockPos(this.this$0.posX, this.this$0.posY + 0.5D, this.this$0.posZ);
      }
   };
   private static final String __OBFID = "CL_00001672";

   public EntityMinecartCommandBlock(World var1) {
      super(var1);
   }

   public IBlockState func_180457_u() {
      return Blocks.command_block.getDefaultState();
   }

   public EntityMinecart.EnumMinecartType func_180456_s() {
      return EntityMinecart.EnumMinecartType.COMMAND_BLOCK;
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      this.field_145824_a.writeDataToNBT(var1);
   }

   public void onActivatorRailPass(int var1, int var2, int var3, boolean var4) {
      if (var4 && this.ticksExisted - this.field_145823_b >= 4) {
         this.func_145822_e().trigger(this.worldObj);
         this.field_145823_b = this.ticksExisted;
      }

   }

   public EntityMinecartCommandBlock(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   public boolean interactFirst(EntityPlayer var1) {
      this.field_145824_a.func_175574_a(var1);
      return false;
   }

   public void func_145781_i(int var1) {
      super.func_145781_i(var1);
      if (var1 == 24) {
         try {
            this.field_145824_a.func_145750_b(IChatComponent.Serializer.jsonToComponent(this.getDataWatcher().getWatchableObjectString(24)));
         } catch (Throwable var3) {
         }
      } else if (var1 == 23) {
         this.field_145824_a.setCommand(this.getDataWatcher().getWatchableObjectString(23));
      }

   }

   protected void entityInit() {
      super.entityInit();
      this.getDataWatcher().addObject(23, "");
      this.getDataWatcher().addObject(24, "");
   }

   public CommandBlockLogic func_145822_e() {
      return this.field_145824_a;
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.field_145824_a.readDataFromNBT(var1);
      this.getDataWatcher().updateObject(23, this.func_145822_e().getCustomName());
      this.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.func_145822_e().getLastOutput()));
   }
}
