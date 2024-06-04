package org.spongycastle.pqc.jcajce.provider.newhope;

import java.io.IOException;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.crypto.newhope.NHPrivateKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.NHPrivateKey;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;




public class BCNHPrivateKey
  implements NHPrivateKey
{
  private static final long serialVersionUID = 1L;
  private final NHPrivateKeyParameters params;
  
  public BCNHPrivateKey(NHPrivateKeyParameters params)
  {
    this.params = params;
  }
  
  public BCNHPrivateKey(PrivateKeyInfo keyInfo)
    throws IOException
  {
    params = new NHPrivateKeyParameters(convert(ASN1OctetString.getInstance(keyInfo.parsePrivateKey()).getOctets()));
  }
  






  public boolean equals(Object o)
  {
    if ((o == null) || (!(o instanceof BCNHPrivateKey)))
    {
      return false;
    }
    BCNHPrivateKey otherKey = (BCNHPrivateKey)o;
    
    return Arrays.areEqual(params.getSecData(), params.getSecData());
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(params.getSecData());
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
      
      short[] privateKeyData = params.getSecData();
      
      byte[] octets = new byte[privateKeyData.length * 2];
      for (int i = 0; i != privateKeyData.length; i++)
      {
        Pack.shortToLittleEndian(privateKeyData[i], octets, i * 2);
      }
      
      PrivateKeyInfo pki = new PrivateKeyInfo(algorithmIdentifier, new DEROctetString(octets));
      
      return pki.getEncoded();
    }
    catch (IOException e) {}
    
    return null;
  }
  

  public String getFormat()
  {
    return "PKCS#8";
  }
  
  public short[] getSecretData()
  {
    return params.getSecData();
  }
  
  CipherParameters getKeyParams()
  {
    return params;
  }
  
  private static short[] convert(byte[] octets)
  {
    short[] rv = new short[octets.length / 2];
    
    for (int i = 0; i != rv.length; i++)
    {
      rv[i] = Pack.littleEndianToShort(octets, i * 2);
    }
    
    return rv;
  }
}
