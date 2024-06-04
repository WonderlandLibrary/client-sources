package org.spongycastle.math.ec;



public class FixedPointPreCompInfo
  implements PreCompInfo
{
  protected ECPoint offset = null;
  




  protected ECPoint[] preComp = null;
  





  protected int width = -1;
  
  public FixedPointPreCompInfo() {}
  
  public ECPoint getOffset() { return offset; }
  

  public void setOffset(ECPoint offset)
  {
    this.offset = offset;
  }
  
  public ECPoint[] getPreComp()
  {
    return preComp;
  }
  
  public void setPreComp(ECPoint[] preComp)
  {
    this.preComp = preComp;
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public void setWidth(int width)
  {
    this.width = width;
  }
}
