package org.spongycastle.pqc.jcajce.provider.newhope;

import java.io.IOException;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.newhope.NHPublicKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.NHPublicKey;
import org.spongycastle.util.Arrays;



public class BCNHPublicKey
  implements NHPublicKey
{
  private static final long serialVersionUID = 1L;
  private final NHPublicKeyParameters params;
  
  public BCNHPublicKey(NHPublicKeyParameters params)
  {
    this.params = params;
  }
  
  public BCNHPublicKey(SubjectPublicKeyInfo keyInfo)
  {
    params = new NHPublicKeyParameters(keyInfo.getPublicKeyData().getBytes());
  }
  






  public boolean equals(Object o)
  {
    if ((o == null) || (!(o instanceof BCNHPublicKey)))
    {
      return false;
    }
    BCNHPublicKey otherKey = (BCNHPublicKey)o;
    
    return Arrays.areEqual(params.getPubData(), params.getPubData());
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(params.getPubData());
  }
  



  public final String getAlgorithm()
  {
    return "NH";
  }
  

  public byte[] getEncoded()
  {
    try
    {
      AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.newHope);
      SubjectPublicKeyInfo pki = new SubjectPublicKeyInfo(algorithmIdentifier, params.getPubData());
      
      return pki.getEncoded();
    }
    catch (IOException e) {}
    
    return null;
  }
  

  public String getFormat()
  {
    return "X.509";
  }
  
  public byte[] getPublicData()
  {
    return params.getPubData();
  }
  
  CipherParameters getKeyParams()
  {
    return params;
  }
}
