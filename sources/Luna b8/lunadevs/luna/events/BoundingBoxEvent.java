package lunadevs.luna.events;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class BoundingBoxEvent
  extends Event
{
  private Block block;
  private BlockPos blockPos;
  public AxisAlignedBB boundingBox;
  private final double x;
  private final double y;
  private final double z;
  
  public BoundingBoxEvent(Block block, BlockPos pos, AxisAlignedBB boundingBox, double x, double y, double z)
  {
    this.block = block;
    this.blockPos = pos;
    this.boundingBox = boundingBox;
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public Block getBlock()
  {
    return this.block;
  }
  
  public BlockPos getBlockPos()
  {
    return this.blockPos;
  }
  
  public AxisAlignedBB getBoundingBox()
  {
    return this.boundingBox;
  }
  
  public void setBlock(Block block)
  {
    this.block = block;
  }
  
  public void setBlockPos(BlockPos blockPos)
  {
    this.blockPos = blockPos;
  }
  
  public void setBoundingBox(AxisAlignedBB boundingBox)
  {
    this.boundingBox = boundingBox;
  }
  
  public double getX()
  {
    return this.x;
  }
  
  public double getY()
  {
    return this.y;
  }
  
  public double getZ()
  {
    return this.z;
  }
  
}
