package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PublicKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.pqc.asn1.McElieceCCA2PublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;













public class BCMcElieceCCA2PublicKey
  implements CipherParameters, PublicKey
{
  private static final long serialVersionUID = 1L;
  private McElieceCCA2PublicKeyParameters params;
  
  public BCMcElieceCCA2PublicKey(McElieceCCA2PublicKeyParameters params)
  {
    this.params = params;
  }
  





  public String getAlgorithm()
  {
    return "McEliece-CCA2";
  }
  



  public int getN()
  {
    return params.getN();
  }
  



  public int getK()
  {
    return params.getK();
  }
  



  public int getT()
  {
    return params.getT();
  }
  



  public GF2Matrix getG()
  {
    return params.getG();
  }
  



  public String toString()
  {
    String result = "McEliecePublicKey:\n";
    result = result + " length of the code         : " + params.getN() + "\n";
    result = result + " error correction capability: " + params.getT() + "\n";
    result = result + " generator matrix           : " + params.getG().toString();
    return result;
  }
  






  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof BCMcElieceCCA2PublicKey)))
    {
      return false;
    }
    
    BCMcElieceCCA2PublicKey otherKey = (BCMcElieceCCA2PublicKey)other;
    
    return (params.getN() == otherKey.getN()) && (params.getT() == otherKey.getT()) && (params.getG().equals(otherKey.getG()));
  }
  



  public int hashCode()
  {
    return 37 * (params.getN() + 37 * params.getT()) + params.getG().hashCode();
  }
  














  public byte[] getEncoded()
  {
    McElieceCCA2PublicKey key = new McElieceCCA2PublicKey(params.getN(), params.getT(), params.getG(), Utils.getDigAlgId(params.getDigest()));
    AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.mcElieceCca2);
    
    try
    {
      SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo(algorithmIdentifier, key);
      
      return subjectPublicKeyInfo.getEncoded();
    }
    catch (IOException e) {}
    
    return null;
  }
  


  public String getFormat()
  {
    return "X.509";
  }
  
  AsymmetricKeyParameter getKeyParams()
  {
    return params;
  }
}
