package org.spongycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;







public class ECNamedCurveGenParameterSpec
  implements AlgorithmParameterSpec
{
  private String name;
  
  public ECNamedCurveGenParameterSpec(String name)
  {
    this.name = name;
  }
  



  public String getName()
  {
    return name;
  }
}
