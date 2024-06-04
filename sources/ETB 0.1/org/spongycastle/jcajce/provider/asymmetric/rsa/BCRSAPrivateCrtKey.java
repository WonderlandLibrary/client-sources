package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.RSAPrivateKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.util.Strings;












public class BCRSAPrivateCrtKey
  extends BCRSAPrivateKey
  implements RSAPrivateCrtKey
{
  static final long serialVersionUID = 7834723820638524718L;
  private BigInteger publicExponent;
  private BigInteger primeP;
  private BigInteger primeQ;
  private BigInteger primeExponentP;
  private BigInteger primeExponentQ;
  private BigInteger crtCoefficient;
  
  BCRSAPrivateCrtKey(RSAPrivateCrtKeyParameters key)
  {
    super(key);
    
    publicExponent = key.getPublicExponent();
    primeP = key.getP();
    primeQ = key.getQ();
    primeExponentP = key.getDP();
    primeExponentQ = key.getDQ();
    crtCoefficient = key.getQInv();
  }
  






  BCRSAPrivateCrtKey(RSAPrivateCrtKeySpec spec)
  {
    modulus = spec.getModulus();
    publicExponent = spec.getPublicExponent();
    privateExponent = spec.getPrivateExponent();
    primeP = spec.getPrimeP();
    primeQ = spec.getPrimeQ();
    primeExponentP = spec.getPrimeExponentP();
    primeExponentQ = spec.getPrimeExponentQ();
    crtCoefficient = spec.getCrtCoefficient();
  }
  






  BCRSAPrivateCrtKey(RSAPrivateCrtKey key)
  {
    modulus = key.getModulus();
    publicExponent = key.getPublicExponent();
    privateExponent = key.getPrivateExponent();
    primeP = key.getPrimeP();
    primeQ = key.getPrimeQ();
    primeExponentP = key.getPrimeExponentP();
    primeExponentQ = key.getPrimeExponentQ();
    crtCoefficient = key.getCrtCoefficient();
  }
  




  BCRSAPrivateCrtKey(PrivateKeyInfo info)
    throws IOException
  {
    this(RSAPrivateKey.getInstance(info.parsePrivateKey()));
  }
  




  BCRSAPrivateCrtKey(RSAPrivateKey key)
  {
    modulus = key.getModulus();
    publicExponent = key.getPublicExponent();
    privateExponent = key.getPrivateExponent();
    primeP = key.getPrime1();
    primeQ = key.getPrime2();
    primeExponentP = key.getExponent1();
    primeExponentQ = key.getExponent2();
    crtCoefficient = key.getCoefficient();
  }
  





  public String getFormat()
  {
    return "PKCS#8";
  }
  






  public byte[] getEncoded()
  {
    return KeyUtil.getEncodedPrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new RSAPrivateKey(getModulus(), getPublicExponent(), getPrivateExponent(), getPrimeP(), getPrimeQ(), getPrimeExponentP(), getPrimeExponentQ(), getCrtCoefficient()));
  }
  





  public BigInteger getPublicExponent()
  {
    return publicExponent;
  }
  





  public BigInteger getPrimeP()
  {
    return primeP;
  }
  





  public BigInteger getPrimeQ()
  {
    return primeQ;
  }
  





  public BigInteger getPrimeExponentP()
  {
    return primeExponentP;
  }
  





  public BigInteger getPrimeExponentQ()
  {
    return primeExponentQ;
  }
  





  public BigInteger getCrtCoefficient()
  {
    return crtCoefficient;
  }
  
  public int hashCode()
  {
    return 
    
      getModulus().hashCode() ^ getPublicExponent().hashCode() ^ getPrivateExponent().hashCode();
  }
  
  public boolean equals(Object o)
  {
    if (o == this)
    {
      return true;
    }
    
    if (!(o instanceof RSAPrivateCrtKey))
    {
      return false;
    }
    
    RSAPrivateCrtKey key = (RSAPrivateCrtKey)o;
    
    return (getModulus().equals(key.getModulus())) && 
      (getPublicExponent().equals(key.getPublicExponent())) && 
      (getPrivateExponent().equals(key.getPrivateExponent())) && 
      (getPrimeP().equals(key.getPrimeP())) && 
      (getPrimeQ().equals(key.getPrimeQ())) && 
      (getPrimeExponentP().equals(key.getPrimeExponentP())) && 
      (getPrimeExponentQ().equals(key.getPrimeExponentQ())) && 
      (getCrtCoefficient().equals(key.getCrtCoefficient()));
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    String nl = Strings.lineSeparator();
    
    buf.append("RSA Private CRT Key [").append(
      RSAUtil.generateKeyFingerprint(getModulus(), getPublicExponent())).append("]").append(nl);
    buf.append("            modulus: ").append(getModulus().toString(16)).append(nl);
    buf.append("    public exponent: ").append(getPublicExponent().toString(16)).append(nl);
    
    return buf.toString();
  }
}
