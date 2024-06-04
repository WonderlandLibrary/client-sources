package org.spongycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.RSAPublicKeySpec;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.util.Strings;





public class JCERSAPublicKey
  implements java.security.interfaces.RSAPublicKey
{
  static final long serialVersionUID = 2675817738516720772L;
  private BigInteger modulus;
  private BigInteger publicExponent;
  
  JCERSAPublicKey(RSAKeyParameters key)
  {
    modulus = key.getModulus();
    publicExponent = key.getExponent();
  }
  

  JCERSAPublicKey(RSAPublicKeySpec spec)
  {
    modulus = spec.getModulus();
    publicExponent = spec.getPublicExponent();
  }
  

  JCERSAPublicKey(java.security.interfaces.RSAPublicKey key)
  {
    modulus = key.getModulus();
    publicExponent = key.getPublicExponent();
  }
  

  JCERSAPublicKey(SubjectPublicKeyInfo info)
  {
    try
    {
      org.spongycastle.asn1.pkcs.RSAPublicKey pubKey = org.spongycastle.asn1.pkcs.RSAPublicKey.getInstance(info.parsePublicKey());
      
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
    return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new org.spongycastle.asn1.pkcs.RSAPublicKey(getModulus(), getPublicExponent()));
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
    
    if (!(o instanceof java.security.interfaces.RSAPublicKey))
    {
      return false;
    }
    
    java.security.interfaces.RSAPublicKey key = (java.security.interfaces.RSAPublicKey)o;
    
    return (getModulus().equals(key.getModulus())) && 
      (getPublicExponent().equals(key.getPublicExponent()));
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    String nl = Strings.lineSeparator();
    
    buf.append("RSA Public Key").append(nl);
    buf.append("            modulus: ").append(getModulus().toString(16)).append(nl);
    buf.append("    public exponent: ").append(getPublicExponent().toString(16)).append(nl);
    
    return buf.toString();
  }
}
