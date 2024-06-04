package org.spongycastle.math.ec;








public class WNafPreCompInfo
  implements PreCompInfo
{
  protected ECPoint[] preComp = null;
  




  protected ECPoint[] preCompNeg = null;
  




  protected ECPoint twice = null;
  
  public WNafPreCompInfo() {}
  
  public ECPoint[] getPreComp() { return preComp; }
  

  public void setPreComp(ECPoint[] preComp)
  {
    this.preComp = preComp;
  }
  
  public ECPoint[] getPreCompNeg()
  {
    return preCompNeg;
  }
  
  public void setPreCompNeg(ECPoint[] preCompNeg)
  {
    this.preCompNeg = preCompNeg;
  }
  
  public ECPoint getTwice()
  {
    return twice;
  }
  
  public void setTwice(ECPoint twice)
  {
    this.twice = twice;
  }
}
