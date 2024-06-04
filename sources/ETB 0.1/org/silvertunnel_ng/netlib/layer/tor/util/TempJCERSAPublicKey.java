package org.silvertunnel_ng.netlib.layer.tor.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.RSAPublicKeyStructure;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;





public class TempJCERSAPublicKey
  implements RSAPublicKey
{
  static final long serialVersionUID = 2675817738516720772L;
  private BigInteger modulus;
  private BigInteger publicExponent;
  
  TempJCERSAPublicKey(RSAKeyParameters key)
  {
    modulus = key.getModulus();
    publicExponent = key.getExponent();
  }
  

  TempJCERSAPublicKey(RSAPublicKeySpec spec)
  {
    modulus = spec.getModulus();
    publicExponent = spec.getPublicExponent();
  }
  

  TempJCERSAPublicKey(RSAPublicKey key)
  {
    modulus = key.getModulus();
    publicExponent = key.getPublicExponent();
  }
  

  TempJCERSAPublicKey(SubjectPublicKeyInfo info)
  {
    try
    {
      RSAPublicKeyStructure pubKey = new RSAPublicKeyStructure((ASN1Sequence)info.parsePublicKey());
      
      modulus = pubKey.getModulus();
      publicExponent = pubKey.getPublicExponent();
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("invalid info structure in RSA public key");
    }
  }
  





  public BigInteger getModulus()
  {
    return modulus;
  }
  





  public BigInteger getPublicExponent()
  {
    return publicExponent;
  }
  
  public String getAlgorithm()
  {
    return "RSA";
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  public byte[] getEncoded()
  {
    return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new RSAPublicKeyStructure(getModulus(), getPublicExponent()));
  }
  
  public int hashCode()
  {
    return getModulus().hashCode() ^ getPublicExponent().hashCode();
  }
  
  public boolean equals(Object o)
  {
    if (o == this)
    {
      return true;
    }
    
    if (!(o instanceof RSAPublicKey))
    {
      return false;
    }
    
    RSAPublicKey key = (RSAPublicKey)o;
    

    return (getModulus().equals(key.getModulus())) && (getPublicExponent().equals(key.getPublicExponent()));
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    String nl = System.getProperty("line.separator");
    
    buf.append("RSA Public Key").append(nl);
    buf.append("            modulus: ").append(getModulus().toString(16)).append(nl);
    buf.append("    public exponent: ").append(getPublicExponent().toString(16)).append(nl);
    
    return buf.toString();
  }
}
