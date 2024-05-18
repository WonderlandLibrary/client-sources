package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

public class Frustrum implements ICamera
{
  private ClippingHelper clippingHelper;
  private double xPosition;
  private double yPosition;
  private double zPosition;
  private static final String __OBFID = "CL_00000976";
  
  public Frustrum()
  {
    this(ClippingHelperImpl.getInstance());
  }
  
  public Frustrum(ClippingHelper p_i46196_1_)
  {
    clippingHelper = p_i46196_1_;
  }
  
  public void setPosition(double p_78547_1_, double p_78547_3_, double p_78547_5_)
  {
    xPosition = p_78547_1_;
    yPosition = p_78547_3_;
    zPosition = p_78547_5_;
  }
  



  public boolean isBoxInFrustum(double p_78548_1_, double p_78548_3_, double p_78548_5_, double p_78548_7_, double p_78548_9_, double p_78548_11_)
  {
    return clippingHelper.isBoxInFrustum(p_78548_1_ - xPosition, p_78548_3_ - yPosition, p_78548_5_ - zPosition, p_78548_7_ - xPosition, p_78548_9_ - yPosition, p_78548_11_ - zPosition);
  }
  



  public boolean isBoundingBoxInFrustum(AxisAlignedBB p_78546_1_)
  {
    return isBoxInFrustum(minX, minY, minZ, maxX, maxY, maxZ);
  }
}
