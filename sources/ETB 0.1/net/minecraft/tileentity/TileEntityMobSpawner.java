package net.minecraft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity implements IUpdatePlayerListBox
{
  private final MobSpawnerBaseLogic field_145882_a = new MobSpawnerBaseLogic()
  {
    private static final String __OBFID = "CL_00000361";
    
    public void func_98267_a(int p_98267_1_) {
      worldObj.addBlockEvent(pos, Blocks.mob_spawner, p_98267_1_, 0);
    }
    
    public World getSpawnerWorld() {
      return worldObj;
    }
    
    public BlockPos func_177221_b() {
      return pos;
    }
    
    public void setRandomEntity(MobSpawnerBaseLogic.WeightedRandomMinecart p_98277_1_) {
      super.setRandomEntity(p_98277_1_);
      
      if (getSpawnerWorld() != null)
      {
        getSpawnerWorld().markBlockForUpdate(pos); }
    }
  };
  private static final String __OBFID = "CL_00000360";
  
  public TileEntityMobSpawner() {}
  
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    field_145882_a.readFromNBT(compound);
  }
  
  public void writeToNBT(NBTTagCompound compound)
  {
    super.writeToNBT(compound);
    field_145882_a.writeToNBT(compound);
  }
  



  public void update()
  {
    field_145882_a.updateSpawner();
  }
  



  public Packet getDescriptionPacket()
  {
    NBTTagCompound var1 = new NBTTagCompound();
    writeToNBT(var1);
    var1.removeTag("SpawnPotentials");
    return new S35PacketUpdateTileEntity(pos, 1, var1);
  }
  
  public boolean receiveClientEvent(int id, int type)
  {
    return field_145882_a.setDelayToMin(id) ? true : super.receiveClientEvent(id, type);
  }
  
  public MobSpawnerBaseLogic getSpawnerBaseLogic()
  {
    return field_145882_a;
  }
}
