package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.asn1.McEliecePrivateKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;












public class BCMcEliecePrivateKey
  implements CipherParameters, PrivateKey
{
  private static final long serialVersionUID = 1L;
  private McEliecePrivateKeyParameters params;
  
  public BCMcEliecePrivateKey(McEliecePrivateKeyParameters params)
  {
    this.params = params;
  }
  





  public String getAlgorithm()
  {
    return "McEliece";
  }
  



  public int getN()
  {
    return params.getN();
  }
  



  public int getK()
  {
    return params.getK();
  }
  



  public GF2mField getField()
  {
    return params.getField();
  }
  



  public PolynomialGF2mSmallM getGoppaPoly()
  {
    return params.getGoppaPoly();
  }
  



  public GF2Matrix getSInv()
  {
    return params.getSInv();
  }
  



  public Permutation getP1()
  {
    return params.getP1();
  }
  



  public Permutation getP2()
  {
    return params.getP2();
  }
  



  public GF2Matrix getH()
  {
    return params.getH();
  }
  



  public PolynomialGF2mSmallM[] getQInv()
  {
    return params.getQInv();
  }
  





















  public boolean equals(Object other)
  {
    if (!(other instanceof BCMcEliecePrivateKey))
    {
      return false;
    }
    BCMcEliecePrivateKey otherKey = (BCMcEliecePrivateKey)other;
    
    return (getN() == otherKey.getN()) && (getK() == otherKey.getK()) && 
      (getField().equals(otherKey.getField())) && 
      (getGoppaPoly().equals(otherKey.getGoppaPoly())) && 
      (getSInv().equals(otherKey.getSInv())) && (getP1().equals(otherKey.getP1())) && 
      (getP2().equals(otherKey.getP2()));
  }
  



  public int hashCode()
  {
    int code = params.getK();
    
    code = code * 37 + params.getN();
    code = code * 37 + params.getField().hashCode();
    code = code * 37 + params.getGoppaPoly().hashCode();
    code = code * 37 + params.getP1().hashCode();
    code = code * 37 + params.getP2().hashCode();
    
    return code * 37 + params.getSInv().hashCode();
  }
  






















  public byte[] getEncoded()
  {
    McEliecePrivateKey privateKey = new McEliecePrivateKey(params.getN(), params.getK(), params.getField(), params.getGoppaPoly(), params.getP1(), params.getP2(), params.getSInv());
    
    try
    {
      AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.mcEliece);
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
  
  AsymmetricKeyParameter getKeyParams()
  {
    return params;
  }
}
