package org.spongycastle.jce.spec;

import java.security.spec.KeySpec;





public class ECKeySpec
  implements KeySpec
{
  private ECParameterSpec spec;
  
  protected ECKeySpec(ECParameterSpec spec)
  {
    this.spec = spec;
  }
  



  public ECParameterSpec getParams()
  {
    return spec;
  }
}
