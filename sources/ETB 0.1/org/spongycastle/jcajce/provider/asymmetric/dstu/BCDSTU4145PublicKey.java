package org.spongycastle.jcajce.provider.asymmetric.dstu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.ua.DSTU4145BinaryField;
import org.spongycastle.asn1.ua.DSTU4145ECBinary;
import org.spongycastle.asn1.ua.DSTU4145NamedCurves;
import org.spongycastle.asn1.ua.DSTU4145Params;
import org.spongycastle.asn1.ua.DSTU4145PointEncoder;
import org.spongycastle.asn1.ua.UAObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.F2m;
import org.spongycastle.math.ec.ECFieldElement;





public class BCDSTU4145PublicKey
  implements java.security.interfaces.ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder
{
  static final long serialVersionUID = 7026240464295649314L;
  private String algorithm = "DSTU4145";
  
  private boolean withCompression;
  
  private transient ECPublicKeyParameters ecPublicKey;
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient DSTU4145Params dstuParams;
  
  public BCDSTU4145PublicKey(BCDSTU4145PublicKey key)
  {
    ecPublicKey = ecPublicKey;
    ecSpec = ecSpec;
    withCompression = withCompression;
    dstuParams = dstuParams;
  }
  

  public BCDSTU4145PublicKey(java.security.spec.ECPublicKeySpec spec)
  {
    ecSpec = spec.getParams();
    ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(ecSpec, spec.getW(), false), EC5Util.getDomainParameters(null, ecSpec));
  }
  


  public BCDSTU4145PublicKey(org.spongycastle.jce.spec.ECPublicKeySpec spec, ProviderConfiguration configuration)
  {
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
  }
  



  public BCDSTU4145PublicKey(String algorithm, ECPublicKeyParameters params, java.security.spec.ECParameterSpec spec)
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
  }
  



  public BCDSTU4145PublicKey(String algorithm, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec)
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
  }
  





  public BCDSTU4145PublicKey(String algorithm, ECPublicKeyParameters params)
  {
    this.algorithm = algorithm;
    ecPublicKey = params;
    ecSpec = null;
  }
  
  private java.security.spec.ECParameterSpec createSpec(EllipticCurve ellipticCurve, ECDomainParameters dp)
  {
    return new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(dp
    

      .getG().getAffineXCoord().toBigInteger(), dp
      .getG().getAffineYCoord().toBigInteger()), dp
      .getN(), dp
      .getH().intValue());
  }
  

  BCDSTU4145PublicKey(SubjectPublicKeyInfo info)
  {
    populateFromPubKeyInfo(info);
  }
  


  private void reverseBytes(byte[] bytes)
  {
    for (int i = 0; i < bytes.length / 2; i++)
    {
      byte tmp = bytes[i];
      bytes[i] = bytes[(bytes.length - 1 - i)];
      bytes[(bytes.length - 1 - i)] = tmp;
    }
  }
  
  private void populateFromPubKeyInfo(SubjectPublicKeyInfo info)
  {
    DERBitString bits = info.getPublicKeyData();
    
    algorithm = "DSTU4145";
    
    try
    {
      key = (ASN1OctetString)ASN1Primitive.fromByteArray(bits.getBytes());
    }
    catch (IOException ex) {
      ASN1OctetString key;
      throw new IllegalArgumentException("error recovering public key");
    }
    ASN1OctetString key;
    byte[] keyEnc = key.getOctets();
    
    if (info.getAlgorithm().getAlgorithm().equals(UAObjectIdentifiers.dstu4145le))
    {
      reverseBytes(keyEnc);
    }
    
    dstuParams = DSTU4145Params.getInstance((ASN1Sequence)info.getAlgorithm().getParameters());
    

    org.spongycastle.jce.spec.ECParameterSpec spec = null;
    if (dstuParams.isNamedCurve())
    {
      ASN1ObjectIdentifier curveOid = dstuParams.getNamedCurve();
      ECDomainParameters ecP = DSTU4145NamedCurves.getByOID(curveOid);
      
      spec = new ECNamedCurveParameterSpec(curveOid.getId(), ecP.getCurve(), ecP.getG(), ecP.getN(), ecP.getH(), ecP.getSeed());
    }
    else
    {
      DSTU4145ECBinary binary = dstuParams.getECBinary();
      byte[] b_bytes = binary.getB();
      if (info.getAlgorithm().getAlgorithm().equals(UAObjectIdentifiers.dstu4145le))
      {
        reverseBytes(b_bytes);
      }
      DSTU4145BinaryField field = binary.getField();
      ECCurve curve = new ECCurve.F2m(field.getM(), field.getK1(), field.getK2(), field.getK3(), binary.getA(), new BigInteger(1, b_bytes));
      byte[] g_bytes = binary.getG();
      if (info.getAlgorithm().getAlgorithm().equals(UAObjectIdentifiers.dstu4145le))
      {
        reverseBytes(g_bytes);
      }
      spec = new org.spongycastle.jce.spec.ECParameterSpec(curve, DSTU4145PointEncoder.decodePoint(curve, g_bytes), binary.getN());
    }
    
    ECCurve curve = spec.getCurve();
    EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, spec.getSeed());
    
    if (dstuParams.isNamedCurve())
    {






      ecSpec = new ECNamedCurveSpec(dstuParams.getNamedCurve().getId(), ellipticCurve, new java.security.spec.ECPoint(spec.getG().getAffineXCoord().toBigInteger(), spec.getG().getAffineYCoord().toBigInteger()), spec.getN(), spec.getH());



    }
    else
    {


      ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(spec.getG().getAffineXCoord().toBigInteger(), spec.getG().getAffineYCoord().toBigInteger()), spec.getN(), spec.getH().intValue());
    }
    

    ecPublicKey = new ECPublicKeyParameters(DSTU4145PointEncoder.decodePoint(curve, keyEnc), EC5Util.getDomainParameters(null, ecSpec));
  }
  
  public byte[] getSbox()
  {
    if (null != dstuParams)
    {
      return dstuParams.getDKE();
    }
    

    return DSTU4145Params.getDefaultDKE();
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
    ASN1Encodable params;
    ASN1Encodable params;
    if (dstuParams != null)
    {
      params = dstuParams;
    }
    else {
      ASN1Encodable params;
      if ((ecSpec instanceof ECNamedCurveSpec))
      {
        params = new DSTU4145Params(new ASN1ObjectIdentifier(((ECNamedCurveSpec)ecSpec).getName()));
      }
      else
      {
        ECCurve curve = EC5Util.convertCurve(ecSpec.getCurve());
        





        X9ECParameters ecP = new X9ECParameters(curve, EC5Util.convertPoint(curve, ecSpec.getGenerator(), withCompression), ecSpec.getOrder(), BigInteger.valueOf(ecSpec.getCofactor()), ecSpec.getCurve().getSeed());
        
        params = new X962Parameters(ecP);
      }
    }
    
    byte[] encKey = DSTU4145PointEncoder.encodePoint(ecPublicKey.getQ());
    
    try
    {
      info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(UAObjectIdentifiers.dstu4145be, params), new DEROctetString(encKey));
    }
    catch (IOException e) {
      SubjectPublicKeyInfo info;
      return null;
    }
    SubjectPublicKeyInfo info;
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
    
    return BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
  }
  
  public String toString()
  {
    return ECUtil.publicKeyToString(algorithm, ecPublicKey.getQ(), engineGetSpec());
  }
  
  public void setPointFormat(String style)
  {
    withCompression = (!"UNCOMPRESSED".equalsIgnoreCase(style));
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof BCDSTU4145PublicKey))
    {
      return false;
    }
    
    BCDSTU4145PublicKey other = (BCDSTU4145PublicKey)o;
    
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
    
    populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(enc)));
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.defaultWriteObject();
    
    out.writeObject(getEncoded());
  }
}
