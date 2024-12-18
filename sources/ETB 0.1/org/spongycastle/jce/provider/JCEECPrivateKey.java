package org.spongycastle.jce.provider;

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
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.sec.ECPrivateKeyStructure;
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
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.util.Strings;





public class JCEECPrivateKey
  implements java.security.interfaces.ECPrivateKey, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier, ECPointEncoder
{
  private String algorithm = "EC";
  
  private BigInteger d;
  
  private java.security.spec.ECParameterSpec ecSpec;
  private boolean withCompression;
  private DERBitString publicKey;
  private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
  


  protected JCEECPrivateKey() {}
  

  public JCEECPrivateKey(java.security.interfaces.ECPrivateKey key)
  {
    d = key.getS();
    algorithm = key.getAlgorithm();
    ecSpec = key.getParams();
  }
  


  public JCEECPrivateKey(String algorithm, org.spongycastle.jce.spec.ECPrivateKeySpec spec)
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
  }
  



  public JCEECPrivateKey(String algorithm, java.security.spec.ECPrivateKeySpec spec)
  {
    this.algorithm = algorithm;
    d = spec.getS();
    ecSpec = spec.getParams();
  }
  


  public JCEECPrivateKey(String algorithm, JCEECPrivateKey key)
  {
    this.algorithm = algorithm;
    d = d;
    ecSpec = ecSpec;
    withCompression = withCompression;
    attrCarrier = attrCarrier;
    publicKey = publicKey;
  }
  




  public JCEECPrivateKey(String algorithm, ECPrivateKeyParameters params, JCEECPublicKey pubKey, java.security.spec.ECParameterSpec spec)
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
  




  public JCEECPrivateKey(String algorithm, ECPrivateKeyParameters params, JCEECPublicKey pubKey, org.spongycastle.jce.spec.ECParameterSpec spec)
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
  


  public JCEECPrivateKey(String algorithm, ECPrivateKeyParameters params)
  {
    this.algorithm = algorithm;
    d = params.getD();
    ecSpec = null;
  }
  

  JCEECPrivateKey(PrivateKeyInfo info)
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
      ECPrivateKeyStructure ec = new ECPrivateKeyStructure((ASN1Sequence)privKey);
      
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
    X962Parameters params;
    

    X962Parameters params;
    

    if ((ecSpec instanceof ECNamedCurveSpec))
    {
      ASN1ObjectIdentifier curveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)ecSpec).getName());
      if (curveOid == null)
      {
        curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec)ecSpec).getName());
      }
      params = new X962Parameters(curveOid);
    } else { X962Parameters params;
      if (ecSpec == null)
      {
        params = new X962Parameters(DERNull.INSTANCE);
      }
      else
      {
        ECCurve curve = EC5Util.convertCurve(ecSpec.getCurve());
        





        X9ECParameters ecP = new X9ECParameters(curve, EC5Util.convertPoint(curve, ecSpec.getGenerator(), withCompression), ecSpec.getOrder(), BigInteger.valueOf(ecSpec.getCofactor()), ecSpec.getCurve().getSeed());
        
        params = new X962Parameters(ecP);
      }
    }
    
    ECPrivateKeyStructure keyStructure;
    ECPrivateKeyStructure keyStructure;
    if (publicKey != null)
    {
      keyStructure = new ECPrivateKeyStructure(getS(), publicKey, params);
    }
    else
    {
      keyStructure = new ECPrivateKeyStructure(getS(), params);
    }
    try {
      PrivateKeyInfo info;
      PrivateKeyInfo info;
      if (algorithm.equals("ECGOST3410"))
      {
        info = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, params.toASN1Primitive()), keyStructure.toASN1Primitive());

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
    if (!(o instanceof JCEECPrivateKey))
    {
      return false;
    }
    
    JCEECPrivateKey other = (JCEECPrivateKey)o;
    
    return (getD().equals(other.getD())) && (engineGetSpec().equals(other.engineGetSpec()));
  }
  
  public int hashCode()
  {
    return getD().hashCode() ^ engineGetSpec().hashCode();
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    String nl = Strings.lineSeparator();
    
    buf.append("EC Private Key").append(nl);
    buf.append("             S: ").append(d.toString(16)).append(nl);
    
    return buf.toString();
  }
  

  private DERBitString getPublicKeyDetails(JCEECPublicKey pub)
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
    byte[] enc = (byte[])in.readObject();
    
    populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(enc)));
    
    algorithm = ((String)in.readObject());
    withCompression = in.readBoolean();
    attrCarrier = new PKCS12BagAttributeCarrierImpl();
    
    attrCarrier.readObject(in);
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.writeObject(getEncoded());
    out.writeObject(algorithm);
    out.writeBoolean(withCompression);
    
    attrCarrier.writeObject(out);
  }
}
