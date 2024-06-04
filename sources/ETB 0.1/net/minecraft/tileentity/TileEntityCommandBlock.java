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

public class TileEntityCommandBlock extends TileEntity
{
  private final CommandBlockLogic field_145994_a = new CommandBlockLogic()
  {
    private static final String __OBFID = "CL_00000348";
    
    public BlockPos getPosition() {
      return pos;
    }
    
    public Vec3 getPositionVector() {
      return new Vec3(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
    }
    
    public World getEntityWorld() {
      return getWorld();
    }
    
    public void setCommand(String p_145752_1_) {
      super.setCommand(p_145752_1_);
      markDirty();
    }
    
    public void func_145756_e() {
      getWorld().markBlockForUpdate(pos);
    }
    
    public int func_145751_f() {
      return 0;
    }
    
    public void func_145757_a(ByteBuf p_145757_1_) {
      p_145757_1_.writeInt(pos.getX());
      p_145757_1_.writeInt(pos.getY());
      p_145757_1_.writeInt(pos.getZ());
    }
    

    public Entity getCommandSenderEntity() { return null; }
  };
  private static final String __OBFID = "CL_00000347";
  
  public TileEntityCommandBlock() {}
  
  public void writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    field_145994_a.writeDataToNBT(compound);
  }
  
  public void readFromNBT(NBTTagCompound compound)
  {
    super.readFromNBT(compound);
    field_145994_a.readDataFromNBT(compound);
  }
  



  public Packet getDescriptionPacket()
  {
    NBTTagCompound var1 = new NBTTagCompound();
    writeToNBT(var1);
    return new S35PacketUpdateTileEntity(pos, 2, var1);
  }
  
  public CommandBlockLogic getCommandBlockLogic()
  {
    return field_145994_a;
  }
  
  public CommandResultStats func_175124_c()
  {
    return field_145994_a.func_175572_n();
  }
}
