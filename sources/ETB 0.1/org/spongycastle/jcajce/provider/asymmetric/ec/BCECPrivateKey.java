package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;







public class BCECPrivateKey
  implements java.security.interfaces.ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder
{
  static final long serialVersionUID = 994553197664784084L;
  private String algorithm = "EC";
  
  private boolean withCompression;
  
  private transient BigInteger d;
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient ProviderConfiguration configuration;
  private transient DERBitString publicKey;
  private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
  


  protected BCECPrivateKey() {}
  


  public BCECPrivateKey(java.security.interfaces.ECPrivateKey key, ProviderConfiguration configuration)
  {
    d = key.getS();
    algorithm = key.getAlgorithm();
    ecSpec = key.getParams();
    this.configuration = configuration;
  }
  



  public BCECPrivateKey(String algorithm, org.spongycastle.jce.spec.ECPrivateKeySpec spec, ProviderConfiguration configuration)
  {
    this.algorithm = algorithm;
    d = spec.getD();
    
    if (spec.getParams() != null)
    {
      ECCurve curve = spec.getParams().getCurve();
      

      EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, spec.getParams().getSeed());
      
      ecSpec = EC5Util.convertSpec(ellipticCurve, spec.getParams());
    }
    else
    {
      ecSpec = null;
    }
    
    this.configuration = configuration;
  }
  




  public BCECPrivateKey(String algorithm, java.security.spec.ECPrivateKeySpec spec, ProviderConfiguration configuration)
  {
    this.algorithm = algorithm;
    d = spec.getS();
    ecSpec = spec.getParams();
    this.configuration = configuration;
  }
  


  public BCECPrivateKey(String algorithm, BCECPrivateKey key)
  {
    this.algorithm = algorithm;
    d = d;
    ecSpec = ecSpec;
    withCompression = withCompression;
    attrCarrier = attrCarrier;
    publicKey = publicKey;
    configuration = configuration;
  }
  





  public BCECPrivateKey(String algorithm, ECPrivateKeyParameters params, BCECPublicKey pubKey, java.security.spec.ECParameterSpec spec, ProviderConfiguration configuration)
  {
    ECDomainParameters dp = params.getParameters();
    
    this.algorithm = algorithm;
    d = params.getD();
    this.configuration = configuration;
    
    if (spec == null)
    {
      EllipticCurve ellipticCurve = EC5Util.convertCurve(dp.getCurve(), dp.getSeed());
      






      ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(dp.getG().getAffineXCoord().toBigInteger(), dp.getG().getAffineYCoord().toBigInteger()), dp.getN(), dp.getH().intValue());
    }
    else
    {
      ecSpec = spec;
    }
    
    publicKey = getPublicKeyDetails(pubKey);
  }
  





  public BCECPrivateKey(String algorithm, ECPrivateKeyParameters params, BCECPublicKey pubKey, org.spongycastle.jce.spec.ECParameterSpec spec, ProviderConfiguration configuration)
  {
    ECDomainParameters dp = params.getParameters();
    
    this.algorithm = algorithm;
    d = params.getD();
    this.configuration = configuration;
    
    if (spec == null)
    {
      EllipticCurve ellipticCurve = EC5Util.convertCurve(dp.getCurve(), dp.getSeed());
      






      ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(dp.getG().getAffineXCoord().toBigInteger(), dp.getG().getAffineYCoord().toBigInteger()), dp.getN(), dp.getH().intValue());
    }
    else
    {
      EllipticCurve ellipticCurve = EC5Util.convertCurve(spec.getCurve(), spec.getSeed());
      
      ecSpec = EC5Util.convertSpec(ellipticCurve, spec);
    }
    
    try
    {
      publicKey = getPublicKeyDetails(pubKey);
    }
    catch (Exception e)
    {
      publicKey = null;
    }
  }
  



  public BCECPrivateKey(String algorithm, ECPrivateKeyParameters params, ProviderConfiguration configuration)
  {
    this.algorithm = algorithm;
    d = params.getD();
    ecSpec = null;
    this.configuration = configuration;
  }
  



  BCECPrivateKey(String algorithm, PrivateKeyInfo info, ProviderConfiguration configuration)
    throws IOException
  {
    this.algorithm = algorithm;
    this.configuration = configuration;
    populateFromPrivKeyInfo(info);
  }
  
  private void populateFromPrivKeyInfo(PrivateKeyInfo info)
    throws IOException
  {
    X962Parameters params = X962Parameters.getInstance(info.getPrivateKeyAlgorithm().getParameters());
    
    ECCurve curve = EC5Util.getCurve(configuration, params);
    ecSpec = EC5Util.convertToSpec(params, curve);
    
    ASN1Encodable privKey = info.parsePrivateKey();
    if ((privKey instanceof ASN1Integer))
    {
      ASN1Integer derD = ASN1Integer.getInstance(privKey);
      
      d = derD.getValue();
    }
    else
    {
      org.spongycastle.asn1.sec.ECPrivateKey ec = org.spongycastle.asn1.sec.ECPrivateKey.getInstance(privKey);
      
      d = ec.getKey();
      publicKey = ec.getPublicKey();
    }
  }
  
  public String getAlgorithm()
  {
    return algorithm;
  }
  





  public String getFormat()
  {
    return "PKCS#8";
  }
  






  public byte[] getEncoded()
  {
    X962Parameters params = ECUtils.getDomainParametersFromName(ecSpec, withCompression);
    int orderBitLength;
    int orderBitLength;
    if (ecSpec == null)
    {
      orderBitLength = ECUtil.getOrderBitLength(configuration, null, getS());
    }
    else
    {
      orderBitLength = ECUtil.getOrderBitLength(configuration, ecSpec.getOrder(), getS());
    }
    
    org.spongycastle.asn1.sec.ECPrivateKey keyStructure;
    
    org.spongycastle.asn1.sec.ECPrivateKey keyStructure;
    if (publicKey != null)
    {
      keyStructure = new org.spongycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), publicKey, params);
    }
    else
    {
      keyStructure = new org.spongycastle.asn1.sec.ECPrivateKey(orderBitLength, getS(), params);
    }
    
    try
    {
      PrivateKeyInfo info = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, params), keyStructure);
      
      return info.getEncoded("DER");
    }
    catch (IOException e) {}
    
    return null;
  }
  

  public java.security.spec.ECParameterSpec getParams()
  {
    return ecSpec;
  }
  
  public org.spongycastle.jce.spec.ECParameterSpec getParameters()
  {
    if (ecSpec == null)
    {
      return null;
    }
    
    return EC5Util.convertSpec(ecSpec, withCompression);
  }
  
  org.spongycastle.jce.spec.ECParameterSpec engineGetSpec()
  {
    if (ecSpec != null)
    {
      return EC5Util.convertSpec(ecSpec, withCompression);
    }
    
    return configuration.getEcImplicitlyCa();
  }
  
  public BigInteger getS()
  {
    return d;
  }
  
  public BigInteger getD()
  {
    return d;
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
  
  public void setPointFormat(String style)
  {
    withCompression = (!"UNCOMPRESSED".equalsIgnoreCase(style));
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof BCECPrivateKey))
    {
      return false;
    }
    
    BCECPrivateKey other = (BCECPrivateKey)o;
    
    return (getD().equals(other.getD())) && (engineGetSpec().equals(other.engineGetSpec()));
  }
  
  public int hashCode()
  {
    return getD().hashCode() ^ engineGetSpec().hashCode();
  }
  
  public String toString()
  {
    return ECUtil.privateKeyToString("EC", d, engineGetSpec());
  }
  
  private org.spongycastle.math.ec.ECPoint calculateQ(org.spongycastle.jce.spec.ECParameterSpec spec)
  {
    return spec.getG().multiply(d).normalize();
  }
  
  private DERBitString getPublicKeyDetails(BCECPublicKey pub)
  {
    try
    {
      SubjectPublicKeyInfo info = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(pub.getEncoded()));
      
      return info.getPublicKeyData();
    }
    catch (IOException e) {}
    
    return null;
  }
  


  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    
    byte[] enc = (byte[])in.readObject();
    
    configuration = BouncyCastleProvider.CONFIGURATION;
    
    populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(enc)));
    
    attrCarrier = new PKCS12BagAttributeCarrierImpl();
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.defaultWriteObject();
    
    out.writeObject(getEncoded());
  }
}
