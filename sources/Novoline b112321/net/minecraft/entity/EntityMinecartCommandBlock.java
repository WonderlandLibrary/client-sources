package net.minecraft.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.EntityMinecartCommandBlock$1;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent$Serializer;
import net.minecraft.world.World;

public class EntityMinecartCommandBlock extends EntityMinecart {
   private final CommandBlockLogic commandBlockLogic = new EntityMinecartCommandBlock$1(this);
   private int activatorRailCooldown = 0;

   public EntityMinecartCommandBlock(World var1) {
      super(var1);
   }

   public EntityMinecartCommandBlock(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   protected void entityInit() {
      super.entityInit();
      this.k().b(23, "");
      this.k().b(24, "");
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.commandBlockLogic.readDataFromNBT(var1);
      this.k().a(23, this.getCommandBlockLogic().getCommand());
      this.k().a(24, IChatComponent$Serializer.componentToJson(this.getCommandBlockLogic().getLastOutput()));
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      this.commandBlockLogic.writeDataToNBT(var1);
   }

   public EntityMinecart$EnumMinecartType getMinecartType() {
      return EntityMinecart$EnumMinecartType.COMMAND_BLOCK;
   }

   public IBlockState getDefaultDisplayTile() {
      return Blocks.command_block.getDefaultState();
   }

   public CommandBlockLogic getCommandBlockLogic() {
      return this.commandBlockLogic;
   }

   public void onActivatorRailPass(int var1, int var2, int var3, boolean var4) {
      if(this.ticksExisted - this.activatorRailCooldown >= 4) {
         this.getCommandBlockLogic().trigger(this.worldObj);
         this.activatorRailCooldown = this.ticksExisted;
      }

   }

   public boolean interactFirst(EntityPlayer var1) {
      this.commandBlockLogic.tryOpenEditCommandBlock(var1);
      return false;
   }

   public void onDataWatcherUpdate(int var1) {
      super.onDataWatcherUpdate(var1);
      if(var1 == 24) {
         EntityMinecartCommandBlock var10000 = this;

         try {
            var10000.commandBlockLogic.setLastOutput(IChatComponent$Serializer.jsonToComponent(this.k().a(24)));
         } catch (Throwable var3) {
            ;
         }
      } else if(var1 == 23) {
         this.commandBlockLogic.setCommand(this.k().a(23));
      }

   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
