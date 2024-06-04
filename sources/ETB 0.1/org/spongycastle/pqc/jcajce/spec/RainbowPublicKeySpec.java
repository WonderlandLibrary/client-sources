package org.spongycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;

















public class RainbowPublicKeySpec
  implements KeySpec
{
  private short[][] coeffquadratic;
  private short[][] coeffsingular;
  private short[] coeffscalar;
  private int docLength;
  
  public RainbowPublicKeySpec(int docLength, short[][] coeffquadratic, short[][] coeffSingular, short[] coeffScalar)
  {
    this.docLength = docLength;
    this.coeffquadratic = coeffquadratic;
    coeffsingular = coeffSingular;
    coeffscalar = coeffScalar;
  }
  



  public int getDocLength()
  {
    return docLength;
  }
  



  public short[][] getCoeffQuadratic()
  {
    return coeffquadratic;
  }
  



  public short[][] getCoeffSingular()
  {
    return coeffsingular;
  }
  



  public short[] getCoeffScalar()
  {
    return coeffscalar;
  }
}
