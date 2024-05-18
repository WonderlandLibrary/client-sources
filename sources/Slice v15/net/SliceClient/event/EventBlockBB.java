package net.SliceClient.event;

import net.minecraft.util.AxisAlignedBB;

public class EventBlockBB implements com.darkmagician6.eventapi.events.Event
{
  public net.minecraft.block.Block block;
  public AxisAlignedBB boundingBox;
  public int x;
  public int y;
  public int z;
  
  public EventBlockBB() {}
  
  public net.minecraft.block.Block getBlock() {
    return block;
  }
  
  public AxisAlignedBB getBoundingBox() {
    return boundingBox;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getZ() {
    return z;
  }
  
  public void setBlock(net.minecraft.block.Block block, AxisAlignedBB boundingBox, int x, int y, int z) {
    this.block = block;
    this.boundingBox = boundingBox;
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public void setBoundingBox(AxisAlignedBB boundingBox) {
    this.boundingBox = boundingBox;
  }
}
