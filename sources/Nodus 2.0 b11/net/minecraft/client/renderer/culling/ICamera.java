package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

public abstract interface ICamera
{
  public abstract boolean isBoundingBoxInFrustum(AxisAlignedBB paramAxisAlignedBB);
  
  public abstract boolean isBoundingBoxInFrustumFully(AxisAlignedBB paramAxisAlignedBB);
  
  public abstract void setPosition(double paramDouble1, double paramDouble2, double paramDouble3);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.culling.ICamera
 * JD-Core Version:    0.7.0.1
 */