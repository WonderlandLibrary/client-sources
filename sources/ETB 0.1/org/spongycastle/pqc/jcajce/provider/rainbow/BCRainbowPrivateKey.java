package org.spongycastle.pqc.jcajce.provider.rainbow;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.RainbowPrivateKey;
import org.spongycastle.pqc.crypto.rainbow.Layer;
import org.spongycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.spongycastle.pqc.jcajce.spec.RainbowPrivateKeySpec;










































public class BCRainbowPrivateKey
  implements PrivateKey
{
  private static final long serialVersionUID = 1L;
  private short[][] A1inv;
  private short[] b1;
  private short[][] A2inv;
  private short[] b2;
  private Layer[] layers;
  private int[] vi;
  
  public BCRainbowPrivateKey(short[][] A1inv, short[] b1, short[][] A2inv, short[] b2, int[] vi, Layer[] layers)
  {
    this.A1inv = A1inv;
    this.b1 = b1;
    this.A2inv = A2inv;
    this.b2 = b2;
    this.vi = vi;
    this.layers = layers;
  }
  





  public BCRainbowPrivateKey(RainbowPrivateKeySpec keySpec)
  {
    this(keySpec.getInvA1(), keySpec.getB1(), keySpec.getInvA2(), keySpec
      .getB2(), keySpec.getVi(), keySpec.getLayers());
  }
  

  public BCRainbowPrivateKey(RainbowPrivateKeyParameters params)
  {
    this(params.getInvA1(), params.getB1(), params.getInvA2(), params.getB2(), params.getVi(), params.getLayers());
  }
  





  public short[][] getInvA1()
  {
    return A1inv;
  }
  





  public short[] getB1()
  {
    return b1;
  }
  





  public short[] getB2()
  {
    return b2;
  }
  





  public short[][] getInvA2()
  {
    return A2inv;
  }
  





  public Layer[] getLayers()
  {
    return layers;
  }
  





  public int[] getVi()
  {
    return vi;
  }
  






  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof BCRainbowPrivateKey)))
    {
      return false;
    }
    BCRainbowPrivateKey otherKey = (BCRainbowPrivateKey)other;
    
    boolean eq = true;
    
    eq = (eq) && (RainbowUtil.equals(A1inv, otherKey.getInvA1()));
    eq = (eq) && (RainbowUtil.equals(A2inv, otherKey.getInvA2()));
    eq = (eq) && (RainbowUtil.equals(b1, otherKey.getB1()));
    eq = (eq) && (RainbowUtil.equals(b2, otherKey.getB2()));
    eq = (eq) && (java.util.Arrays.equals(vi, otherKey.getVi()));
    if (layers.length != otherKey.getLayers().length)
    {
      return false;
    }
    for (int i = layers.length - 1; i >= 0; i--)
    {
      eq &= layers[i].equals(otherKey.getLayers()[i]);
    }
    return eq;
  }
  
  public int hashCode()
  {
    int hash = layers.length;
    
    hash = hash * 37 + org.spongycastle.util.Arrays.hashCode(A1inv);
    hash = hash * 37 + org.spongycastle.util.Arrays.hashCode(b1);
    hash = hash * 37 + org.spongycastle.util.Arrays.hashCode(A2inv);
    hash = hash * 37 + org.spongycastle.util.Arrays.hashCode(b2);
    hash = hash * 37 + org.spongycastle.util.Arrays.hashCode(vi);
    
    for (int i = layers.length - 1; i >= 0; i--)
    {
      hash = hash * 37 + layers[i].hashCode();
    }
    

    return hash;
  }
  



  public final String getAlgorithm()
  {
    return "Rainbow";
  }
  
  public byte[] getEncoded()
  {
    RainbowPrivateKey privateKey = new RainbowPrivateKey(A1inv, b1, A2inv, b2, vi, layers);
    

    try
    {
      AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.rainbow, DERNull.INSTANCE);
      pki = new PrivateKeyInfo(algorithmIdentifier, privateKey);
    }
    catch (IOException e) {
      PrivateKeyInfo pki;
      return null;
    }
    try {
      PrivateKeyInfo pki;
      return pki.getEncoded();
    }
    catch (IOException e) {}
    

    return null;
  }
  

  public String getFormat()
  {
    return "PKCS#8";
  }
}
