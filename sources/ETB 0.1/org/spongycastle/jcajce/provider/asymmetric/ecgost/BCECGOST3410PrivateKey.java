package org.spongycastle.jcajce.provider.asymmetric.ecgost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.ECGOST3410NamedCurveTable;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;






public class BCECGOST3410PrivateKey
  implements java.security.interfaces.ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder
{
  static final long serialVersionUID = 7245981689601667138L;
  private String algorithm = "ECGOST3410";
  
  private boolean withCompression;
  private transient ASN1Encodable gostParams;
  private transient BigInteger d;
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient DERBitString publicKey;
  private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
  


  protected BCECGOST3410PrivateKey() {}
  

  public BCECGOST3410PrivateKey(java.security.interfaces.ECPrivateKey key)
  {
    d = key.getS();
    algorithm = key.getAlgorithm();
    ecSpec = key.getParams();
  }
  

  public BCECGOST3410PrivateKey(org.spongycastle.jce.spec.ECPrivateKeySpec spec)
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
  


  public BCECGOST3410PrivateKey(java.security.spec.ECPrivateKeySpec spec)
  {
    d = spec.getS();
    ecSpec = spec.getParams();
  }
  

  public BCECGOST3410PrivateKey(BCECGOST3410PrivateKey key)
  {
    d = d;
    ecSpec = ecSpec;
    withCompression = withCompression;
    attrCarrier = attrCarrier;
    publicKey = publicKey;
    gostParams = gostParams;
  }
  




  public BCECGOST3410PrivateKey(String algorithm, ECPrivateKeyParameters params, BCECGOST3410PublicKey pubKey, java.security.spec.ECParameterSpec spec)
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
    
    gostParams = pubKey.getGostParams();
    
    publicKey = getPublicKeyDetails(pubKey);
  }
  




  public BCECGOST3410PrivateKey(String algorithm, ECPrivateKeyParameters params, BCECGOST3410PublicKey pubKey, org.spongycastle.jce.spec.ECParameterSpec spec)
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
    
    gostParams = pubKey.getGostParams();
    
    publicKey = getPublicKeyDetails(pubKey);
  }
  


  public BCECGOST3410PrivateKey(String algorithm, ECPrivateKeyParameters params)
  {
    this.algorithm = algorithm;
    d = params.getD();
    ecSpec = null;
  }
  

  BCECGOST3410PrivateKey(PrivateKeyInfo info)
    throws IOException
  {
    populateFromPrivKeyInfo(info);
  }
  
  private void populateFromPrivKeyInfo(PrivateKeyInfo info)
    throws IOException
  {
    ASN1Primitive p = info.getPrivateKeyAlgorithm().getParameters().toASN1Primitive();
    
    if (((p instanceof ASN1Sequence)) && ((ASN1Sequence.getInstance(p).size() == 2) || (ASN1Sequence.getInstance(p).size() == 3)))
    {
      GOST3410PublicKeyAlgParameters gParams = GOST3410PublicKeyAlgParameters.getInstance(info.getPrivateKeyAlgorithm().getParameters());
      gostParams = gParams;
      
      ECNamedCurveParameterSpec spec = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(gParams.getPublicKeyParamSet()));
      
      ECCurve curve = spec.getCurve();
      EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, spec.getSeed());
      






      ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(gParams.getPublicKeyParamSet()), ellipticCurve, new java.security.spec.ECPoint(spec.getG().getAffineXCoord().toBigInteger(), spec.getG().getAffineYCoord().toBigInteger()), spec.getN(), spec.getH());
      
      ASN1Encodable privKey = info.parsePrivateKey();
      
      if ((privKey instanceof ASN1Integer))
      {
        d = ASN1Integer.getInstance(privKey).getPositiveValue();
      }
      else
      {
        byte[] encVal = ASN1OctetString.getInstance(privKey).getOctets();
        byte[] dVal = new byte[encVal.length];
        
        for (int i = 0; i != encVal.length; i++)
        {
          dVal[i] = encVal[(encVal.length - 1 - i)];
        }
        
        d = new BigInteger(1, dVal);
      }
      
    }
    else
    {
      X962Parameters params = X962Parameters.getInstance(info.getPrivateKeyAlgorithm().getParameters());
      
      if (params.isNamedCurve())
      {
        ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.getInstance(params.getParameters());
        X9ECParameters ecP = ECUtil.getNamedCurveByOid(oid);
        
        if (ecP == null)
        {
          ECDomainParameters gParam = ECGOST3410NamedCurves.getByOID(oid);
          EllipticCurve ellipticCurve = EC5Util.convertCurve(gParam.getCurve(), gParam.getSeed());
          







          ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(oid), ellipticCurve, new java.security.spec.ECPoint(gParam.getG().getAffineXCoord().toBigInteger(), gParam.getG().getAffineYCoord().toBigInteger()), gParam.getN(), gParam.getH());
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
    if (gostParams != null)
    {
      byte[] encKey = new byte[32];
      
      extractBytes(encKey, 0, getS());
      
      try
      {
        PrivateKeyInfo info = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, gostParams), new DEROctetString(encKey));
        
        return info.getEncoded("DER");
      }
      catch (IOException e)
      {
        return null;
      }
    }
    
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
    
    try
    {
      PrivateKeyInfo info = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, params.toASN1Primitive()), keyStructure.toASN1Primitive());
      
      return info.getEncoded("DER");
    }
    catch (IOException e) {}
    
    return null;
  }
  


  private void extractBytes(byte[] encKey, int offSet, BigInteger bI)
  {
    byte[] val = bI.toByteArray();
    if (val.length < 32)
    {
      byte[] tmp = new byte[32];
      System.arraycopy(val, 0, tmp, tmp.length - val.length, val.length);
      val = tmp;
    }
    
    for (int i = 0; i != 32; i++)
    {
      encKey[(offSet + i)] = val[(val.length - 1 - i)];
    }
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
    if (!(o instanceof BCECGOST3410PrivateKey))
    {
      return false;
    }
    
    BCECGOST3410PrivateKey other = (BCECGOST3410PrivateKey)o;
    
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
  
  private DERBitString getPublicKeyDetails(BCECGOST3410PublicKey pub)
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
