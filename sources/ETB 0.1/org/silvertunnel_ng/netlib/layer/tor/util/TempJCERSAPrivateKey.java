package org.silvertunnel_ng.netlib.layer.tor.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;




public class TempJCERSAPrivateKey
  implements java.security.interfaces.RSAPrivateKey, PKCS12BagAttributeCarrier
{
  static final long serialVersionUID = 5110188922551353628L;
  private static BigInteger ZERO = BigInteger.valueOf(0L);
  
  protected BigInteger modulus;
  
  protected BigInteger privateExponent;
  private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
  


  protected TempJCERSAPrivateKey() {}
  

  TempJCERSAPrivateKey(RSAKeyParameters key)
  {
    modulus = key.getModulus();
    privateExponent = key.getExponent();
  }
  

  TempJCERSAPrivateKey(RSAPrivateKeySpec spec)
  {
    modulus = spec.getModulus();
    privateExponent = spec.getPrivateExponent();
  }
  

  TempJCERSAPrivateKey(java.security.interfaces.RSAPrivateKey key)
  {
    modulus = key.getModulus();
    privateExponent = key.getPrivateExponent();
  }
  
  public BigInteger getModulus()
  {
    return modulus;
  }
  
  public BigInteger getPrivateExponent()
  {
    return privateExponent;
  }
  
  public String getAlgorithm()
  {
    return "RSA";
  }
  
  public String getFormat()
  {
    return "PKCS#8";
  }
  
  public byte[] getEncoded()
  {
    return KeyUtil.getEncodedPrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new org.spongycastle.asn1.pkcs.RSAPrivateKey(getModulus(), ZERO, getPrivateExponent(), ZERO, ZERO, ZERO, ZERO, ZERO));
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof java.security.interfaces.RSAPrivateKey))
    {
      return false;
    }
    
    if (o == this)
    {
      return true;
    }
    
    java.security.interfaces.RSAPrivateKey key = (java.security.interfaces.RSAPrivateKey)o;
    

    return (getModulus().equals(key.getModulus())) && (getPrivateExponent().equals(key.getPrivateExponent()));
  }
  
  public int hashCode()
  {
    return getModulus().hashCode() ^ getPrivateExponent().hashCode();
  }
  


  public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute)
  {
    attrCarrier.setBagAttribute(oid, attribute);
  }
  

  public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid)
  {
    return attrCarrier.getBagAttribute(oid);
  }
  
  public Enumeration getBagAttributeKeys()
  {
    return attrCarrier.getBagAttributeKeys();
  }
  

  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    modulus = ((BigInteger)in.readObject());
    attrCarrier = new PKCS12BagAttributeCarrierImpl();
    
    attrCarrier.readObject(in);
    
    privateExponent = ((BigInteger)in.readObject());
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.writeObject(modulus);
    
    attrCarrier.writeObject(out);
    
    out.writeObject(privateExponent);
  }
}
