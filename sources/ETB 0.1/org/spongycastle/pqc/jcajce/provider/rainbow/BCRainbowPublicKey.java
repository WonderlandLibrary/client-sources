package org.spongycastle.pqc.jcajce.provider.rainbow;

import java.security.PublicKey;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.RainbowPublicKey;
import org.spongycastle.pqc.crypto.rainbow.RainbowParameters;
import org.spongycastle.pqc.crypto.rainbow.RainbowPublicKeyParameters;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.spongycastle.pqc.jcajce.provider.util.KeyUtil;
import org.spongycastle.pqc.jcajce.spec.RainbowPublicKeySpec;
import org.spongycastle.util.Arrays;































public class BCRainbowPublicKey
  implements PublicKey
{
  private static final long serialVersionUID = 1L;
  private short[][] coeffquadratic;
  private short[][] coeffsingular;
  private short[] coeffscalar;
  private int docLength;
  private RainbowParameters rainbowParams;
  
  public BCRainbowPublicKey(int docLength, short[][] coeffQuadratic, short[][] coeffSingular, short[] coeffScalar)
  {
    this.docLength = docLength;
    coeffquadratic = coeffQuadratic;
    coeffsingular = coeffSingular;
    coeffscalar = coeffScalar;
  }
  





  public BCRainbowPublicKey(RainbowPublicKeySpec keySpec)
  {
    this(keySpec.getDocLength(), keySpec.getCoeffQuadratic(), keySpec
      .getCoeffSingular(), keySpec.getCoeffScalar());
  }
  

  public BCRainbowPublicKey(RainbowPublicKeyParameters params)
  {
    this(params.getDocLength(), params.getCoeffQuadratic(), params.getCoeffSingular(), params.getCoeffScalar());
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
    short[][] copy = new short[coeffsingular.length][];
    
    for (int i = 0; i != coeffsingular.length; i++)
    {
      copy[i] = Arrays.clone(coeffsingular[i]);
    }
    
    return copy;
  }
  




  public short[] getCoeffScalar()
  {
    return Arrays.clone(coeffscalar);
  }
  






  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof BCRainbowPublicKey)))
    {
      return false;
    }
    BCRainbowPublicKey otherKey = (BCRainbowPublicKey)other;
    
    return (docLength == otherKey.getDocLength()) && 
      (RainbowUtil.equals(coeffquadratic, otherKey.getCoeffQuadratic())) && 
      (RainbowUtil.equals(coeffsingular, otherKey.getCoeffSingular())) && 
      (RainbowUtil.equals(coeffscalar, otherKey.getCoeffScalar()));
  }
  
  public int hashCode()
  {
    int hash = docLength;
    
    hash = hash * 37 + Arrays.hashCode(coeffquadratic);
    hash = hash * 37 + Arrays.hashCode(coeffsingular);
    hash = hash * 37 + Arrays.hashCode(coeffscalar);
    
    return hash;
  }
  



  public final String getAlgorithm()
  {
    return "Rainbow";
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  public byte[] getEncoded()
  {
    RainbowPublicKey key = new RainbowPublicKey(docLength, coeffquadratic, coeffsingular, coeffscalar);
    AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.rainbow, DERNull.INSTANCE);
    
    return KeyUtil.getEncodedSubjectPublicKeyInfo(algorithmIdentifier, key);
  }
}
