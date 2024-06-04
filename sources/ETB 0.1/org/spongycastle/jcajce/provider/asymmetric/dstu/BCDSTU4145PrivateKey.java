package org.spongycastle.jcajce.provider.asymmetric.dstu;

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
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.ua.DSTU4145NamedCurves;
import org.spongycastle.asn1.ua.UAObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
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
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;






public class BCDSTU4145PrivateKey
  implements java.security.interfaces.ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder
{
  static final long serialVersionUID = 7245981689601667138L;
  private String algorithm = "DSTU4145";
  
  private boolean withCompression;
  private transient BigInteger d;
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient DERBitString publicKey;
  private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
  


  protected BCDSTU4145PrivateKey() {}
  

  public BCDSTU4145PrivateKey(java.security.interfaces.ECPrivateKey key)
  {
    d = key.getS();
    algorithm = key.getAlgorithm();
    ecSpec = key.getParams();
  }
  

  public BCDSTU4145PrivateKey(org.spongycastle.jce.spec.ECPrivateKeySpec spec)
  {
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
  }
  


  public BCDSTU4145PrivateKey(java.security.spec.ECPrivateKeySpec spec)
  {
    d = spec.getS();
    ecSpec = spec.getParams();
  }
  

  public BCDSTU4145PrivateKey(BCDSTU4145PrivateKey key)
  {
    d = d;
    ecSpec = ecSpec;
    withCompression = withCompression;
    attrCarrier = attrCarrier;
    publicKey = publicKey;
  }
  




  public BCDSTU4145PrivateKey(String algorithm, ECPrivateKeyParameters params, BCDSTU4145PublicKey pubKey, java.security.spec.ECParameterSpec spec)
  {
    ECDomainParameters dp = params.getParameters();
    
    this.algorithm = algorithm;
    d = params.getD();
    
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
  




  public BCDSTU4145PrivateKey(String algorithm, ECPrivateKeyParameters params, BCDSTU4145PublicKey pubKey, org.spongycastle.jce.spec.ECParameterSpec spec)
  {
    ECDomainParameters dp = params.getParameters();
    
    this.algorithm = algorithm;
    d = params.getD();
    
    if (spec == null)
    {
      EllipticCurve ellipticCurve = EC5Util.convertCurve(dp.getCurve(), dp.getSeed());
      






      ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(dp.getG().getAffineXCoord().toBigInteger(), dp.getG().getAffineYCoord().toBigInteger()), dp.getN(), dp.getH().intValue());
    }
    else
    {
      EllipticCurve ellipticCurve = EC5Util.convertCurve(spec.getCurve(), spec.getSeed());
      






      ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(spec.getG().getAffineXCoord().toBigInteger(), spec.getG().getAffineYCoord().toBigInteger()), spec.getN(), spec.getH().intValue());
    }
    
    publicKey = getPublicKeyDetails(pubKey);
  }
  


  public BCDSTU4145PrivateKey(String algorithm, ECPrivateKeyParameters params)
  {
    this.algorithm = algorithm;
    d = params.getD();
    ecSpec = null;
  }
  

  BCDSTU4145PrivateKey(PrivateKeyInfo info)
    throws IOException
  {
    populateFromPrivKeyInfo(info);
  }
  
  private void populateFromPrivKeyInfo(PrivateKeyInfo info)
    throws IOException
  {
    X962Parameters params = new X962Parameters((ASN1Primitive)info.getPrivateKeyAlgorithm().getParameters());
    
    if (params.isNamedCurve())
    {
      ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.getInstance(params.getParameters());
      X9ECParameters ecP = ECUtil.getNamedCurveByOid(oid);
      
      if (ecP == null)
      {
        ECDomainParameters gParam = DSTU4145NamedCurves.getByOID(oid);
        EllipticCurve ellipticCurve = EC5Util.convertCurve(gParam.getCurve(), gParam.getSeed());
        







        ecSpec = new ECNamedCurveSpec(oid.getId(), ellipticCurve, new java.security.spec.ECPoint(gParam.getG().getAffineXCoord().toBigInteger(), gParam.getG().getAffineYCoord().toBigInteger()), gParam.getN(), gParam.getH());
      }
      else
      {
        EllipticCurve ellipticCurve = EC5Util.convertCurve(ecP.getCurve(), ecP.getSeed());
        







        ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(oid), ellipticCurve, new java.security.spec.ECPoint(ecP.getG().getAffineXCoord().toBigInteger(), ecP.getG().getAffineYCoord().toBigInteger()), ecP.getN(), ecP.getH());
      }
    }
    else if (params.isImplicitlyCA())
    {
      ecSpec = null;
    }
    else
    {
      X9ECParameters ecP = X9ECParameters.getInstance(params.getParameters());
      EllipticCurve ellipticCurve = EC5Util.convertCurve(ecP.getCurve(), ecP.getSeed());
      






      ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(ecP.getG().getAffineXCoord().toBigInteger(), ecP.getG().getAffineYCoord().toBigInteger()), ecP.getN(), ecP.getH().intValue());
    }
    
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
    int orderBitLength;
    

    X962Parameters params;
    
    int orderBitLength;
    
    if ((ecSpec instanceof ECNamedCurveSpec))
    {
      ASN1ObjectIdentifier curveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)ecSpec).getName());
      if (curveOid == null)
      {
        curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec)ecSpec).getName());
      }
      X962Parameters params = new X962Parameters(curveOid);
      orderBitLength = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, ecSpec.getOrder(), getS());
    } else { int orderBitLength;
      if (ecSpec == null)
      {
        X962Parameters params = new X962Parameters(DERNull.INSTANCE);
        orderBitLength = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, null, getS());
      }
      else
      {
        ECCurve curve = EC5Util.convertCurve(ecSpec.getCurve());
        





        X9ECParameters ecP = new X9ECParameters(curve, EC5Util.convertPoint(curve, ecSpec.getGenerator(), withCompression), ecSpec.getOrder(), BigInteger.valueOf(ecSpec.getCofactor()), ecSpec.getCurve().getSeed());
        
        params = new X962Parameters(ecP);
        orderBitLength = ECUtil.getOrderBitLength(BouncyCastleProvider.CONFIGURATION, ecSpec.getOrder(), getS());
      }
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
    try {
      PrivateKeyInfo info;
      PrivateKeyInfo info;
      if (algorithm.equals("DSTU4145"))
      {
        info = new PrivateKeyInfo(new AlgorithmIdentifier(UAObjectIdentifiers.dstu4145be, params.toASN1Primitive()), keyStructure.toASN1Primitive());

      }
      else
      {
        info = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, params.toASN1Primitive()), keyStructure.toASN1Primitive());
      }
      
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
    
    return BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
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
    if (!(o instanceof BCDSTU4145PrivateKey))
    {
      return false;
    }
    
    BCDSTU4145PrivateKey other = (BCDSTU4145PrivateKey)o;
    
    return (getD().equals(other.getD())) && (engineGetSpec().equals(other.engineGetSpec()));
  }
  
  public int hashCode()
  {
    return getD().hashCode() ^ engineGetSpec().hashCode();
  }
  
  public String toString()
  {
    return ECUtil.privateKeyToString(algorithm, d, engineGetSpec());
  }
  
  private DERBitString getPublicKeyDetails(BCDSTU4145PublicKey pub)
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
