package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;





public class BCECPublicKey
  implements java.security.interfaces.ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder
{
  static final long serialVersionUID = 2422789860422731812L;
  private String algorithm = "EC";
  
  private boolean withCompression;
  
  private transient ECPublicKeyParameters ecPublicKey;
  
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient ProviderConfiguration configuration;
  
  public BCECPublicKey(String algorithm, BCECPublicKey key)
  {
    this.algorithm = algorithm;
    ecPublicKey = ecPublicKey;
    ecSpec = ecSpec;
    withCompression = withCompression;
    configuration = configuration;
  }
  



  public BCECPublicKey(String algorithm, java.security.spec.ECPublicKeySpec spec, ProviderConfiguration configuration)
  {
    this.algorithm = algorithm;
    ecSpec = spec.getParams();
    ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(ecSpec, spec.getW(), false), EC5Util.getDomainParameters(configuration, spec.getParams()));
    this.configuration = configuration;
  }
  



  public BCECPublicKey(String algorithm, org.spongycastle.jce.spec.ECPublicKeySpec spec, ProviderConfiguration configuration)
  {
    this.algorithm = algorithm;
    
    if (spec.getParams() != null)
    {
      ECCurve curve = spec.getParams().getCurve();
      EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, spec.getParams().getSeed());
      


      ecPublicKey = new ECPublicKeyParameters(spec.getQ(), ECUtil.getDomainParameters(configuration, spec.getParams()));
      ecSpec = EC5Util.convertSpec(ellipticCurve, spec.getParams());
    }
    else
    {
      org.spongycastle.jce.spec.ECParameterSpec s = configuration.getEcImplicitlyCa();
      
      ecPublicKey = new ECPublicKeyParameters(s.getCurve().createPoint(spec.getQ().getAffineXCoord().toBigInteger(), spec.getQ().getAffineYCoord().toBigInteger()), EC5Util.getDomainParameters(configuration, (java.security.spec.ECParameterSpec)null));
      ecSpec = null;
    }
    
    this.configuration = configuration;
  }
  




  public BCECPublicKey(String algorithm, ECPublicKeyParameters params, java.security.spec.ECParameterSpec spec, ProviderConfiguration configuration)
  {
    ECDomainParameters dp = params.getParameters();
    
    this.algorithm = algorithm;
    ecPublicKey = params;
    
    if (spec == null)
    {
      EllipticCurve ellipticCurve = EC5Util.convertCurve(dp.getCurve(), dp.getSeed());
      
      ecSpec = createSpec(ellipticCurve, dp);
    }
    else
    {
      ecSpec = spec;
    }
    
    this.configuration = configuration;
  }
  




  public BCECPublicKey(String algorithm, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec, ProviderConfiguration configuration)
  {
    ECDomainParameters dp = params.getParameters();
    
    this.algorithm = algorithm;
    
    if (spec == null)
    {
      EllipticCurve ellipticCurve = EC5Util.convertCurve(dp.getCurve(), dp.getSeed());
      
      ecSpec = createSpec(ellipticCurve, dp);
    }
    else
    {
      EllipticCurve ellipticCurve = EC5Util.convertCurve(spec.getCurve(), spec.getSeed());
      
      ecSpec = EC5Util.convertSpec(ellipticCurve, spec);
    }
    
    ecPublicKey = params;
    this.configuration = configuration;
  }
  






  public BCECPublicKey(String algorithm, ECPublicKeyParameters params, ProviderConfiguration configuration)
  {
    this.algorithm = algorithm;
    ecPublicKey = params;
    ecSpec = null;
    this.configuration = configuration;
  }
  


  public BCECPublicKey(java.security.interfaces.ECPublicKey key, ProviderConfiguration configuration)
  {
    algorithm = key.getAlgorithm();
    ecSpec = key.getParams();
    ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(ecSpec, key.getW(), false), EC5Util.getDomainParameters(configuration, key.getParams()));
  }
  



  BCECPublicKey(String algorithm, SubjectPublicKeyInfo info, ProviderConfiguration configuration)
  {
    this.algorithm = algorithm;
    this.configuration = configuration;
    populateFromPubKeyInfo(info);
  }
  
  private java.security.spec.ECParameterSpec createSpec(EllipticCurve ellipticCurve, ECDomainParameters dp)
  {
    return new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(dp
    

      .getG().getAffineXCoord().toBigInteger(), dp
      .getG().getAffineYCoord().toBigInteger()), dp
      .getN(), dp
      .getH().intValue());
  }
  
  private void populateFromPubKeyInfo(SubjectPublicKeyInfo info)
  {
    X962Parameters params = X962Parameters.getInstance(info.getAlgorithm().getParameters());
    ECCurve curve = EC5Util.getCurve(configuration, params);
    ecSpec = EC5Util.convertToSpec(params, curve);
    
    DERBitString bits = info.getPublicKeyData();
    byte[] data = bits.getBytes();
    ASN1OctetString key = new DEROctetString(data);
    



    if ((data[0] == 4) && (data[1] == data.length - 2) && ((data[2] == 2) || (data[2] == 3)))
    {

      int qLength = new X9IntegerConverter().getByteLength(curve);
      
      if (qLength >= data.length - 3)
      {
        try
        {
          key = (ASN1OctetString)ASN1Primitive.fromByteArray(data);
        }
        catch (IOException ex)
        {
          throw new IllegalArgumentException("error recovering public key");
        }
      }
    }
    
    X9ECPoint derQ = new X9ECPoint(curve, key);
    
    ecPublicKey = new ECPublicKeyParameters(derQ.getPoint(), ECUtil.getDomainParameters(configuration, params));
  }
  
  public String getAlgorithm()
  {
    return algorithm;
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  public byte[] getEncoded()
  {
    ASN1Encodable params = ECUtils.getDomainParametersFromName(ecSpec, withCompression);
    ASN1OctetString p = ASN1OctetString.getInstance(new X9ECPoint(ecPublicKey.getQ(), withCompression).toASN1Primitive());
    

    SubjectPublicKeyInfo info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, params), p.getOctets());
    
    return KeyUtil.getEncodedSubjectPublicKeyInfo(info);
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
  
  public java.security.spec.ECPoint getW()
  {
    org.spongycastle.math.ec.ECPoint q = ecPublicKey.getQ();
    
    return new java.security.spec.ECPoint(q.getAffineXCoord().toBigInteger(), q.getAffineYCoord().toBigInteger());
  }
  
  public org.spongycastle.math.ec.ECPoint getQ()
  {
    org.spongycastle.math.ec.ECPoint q = ecPublicKey.getQ();
    
    if (ecSpec == null)
    {
      return q.getDetachedPoint();
    }
    
    return q;
  }
  
  ECPublicKeyParameters engineGetKeyParameters()
  {
    return ecPublicKey;
  }
  
  org.spongycastle.jce.spec.ECParameterSpec engineGetSpec()
  {
    if (ecSpec != null)
    {
      return EC5Util.convertSpec(ecSpec, withCompression);
    }
    
    return configuration.getEcImplicitlyCa();
  }
  
  public String toString()
  {
    return ECUtil.publicKeyToString("EC", ecPublicKey.getQ(), engineGetSpec());
  }
  
  public void setPointFormat(String style)
  {
    withCompression = (!"UNCOMPRESSED".equalsIgnoreCase(style));
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof BCECPublicKey))
    {
      return false;
    }
    
    BCECPublicKey other = (BCECPublicKey)o;
    
    return (ecPublicKey.getQ().equals(ecPublicKey.getQ())) && (engineGetSpec().equals(other.engineGetSpec()));
  }
  
  public int hashCode()
  {
    return ecPublicKey.getQ().hashCode() ^ engineGetSpec().hashCode();
  }
  

  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    
    byte[] enc = (byte[])in.readObject();
    
    configuration = BouncyCastleProvider.CONFIGURATION;
    
    populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(enc)));
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.defaultWriteObject();
    
    out.writeObject(getEncoded());
  }
}
