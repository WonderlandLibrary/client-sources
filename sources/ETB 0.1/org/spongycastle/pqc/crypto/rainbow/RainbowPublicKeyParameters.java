package org.spongycastle.pqc.crypto.rainbow;




public class RainbowPublicKeyParameters
  extends RainbowKeyParameters
{
  private short[][] coeffquadratic;
  


  private short[][] coeffsingular;
  

  private short[] coeffscalar;
  


  public RainbowPublicKeyParameters(int docLength, short[][] coeffQuadratic, short[][] coeffSingular, short[] coeffScalar)
  {
    super(false, docLength);
    
    coeffquadratic = coeffQuadratic;
    coeffsingular = coeffSingular;
    coeffscalar = coeffScalar;
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
