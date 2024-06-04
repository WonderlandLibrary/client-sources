package org.spongycastle.jcajce.provider.asymmetric.ecgost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
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
import org.spongycastle.jce.ECGOST3410NamedCurveTable;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;






public class BCECGOST3410PublicKey
  implements java.security.interfaces.ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder
{
  static final long serialVersionUID = 7026240464295649314L;
  private String algorithm = "ECGOST3410";
  
  private boolean withCompression;
  
  private transient ECPublicKeyParameters ecPublicKey;
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient ASN1Encodable gostParams;
  
  public BCECGOST3410PublicKey(BCECGOST3410PublicKey key)
  {
    ecPublicKey = ecPublicKey;
    ecSpec = ecSpec;
    withCompression = withCompression;
    gostParams = gostParams;
  }
  

  public BCECGOST3410PublicKey(java.security.spec.ECPublicKeySpec spec)
  {
    ecSpec = spec.getParams();
    ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(ecSpec, spec.getW(), false), EC5Util.getDomainParameters(null, spec.getParams()));
  }
  


  public BCECGOST3410PublicKey(org.spongycastle.jce.spec.ECPublicKeySpec spec, ProviderConfiguration configuration)
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
  



  public BCECGOST3410PublicKey(String algorithm, ECPublicKeyParameters params, java.security.spec.ECParameterSpec spec)
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
  



  public BCECGOST3410PublicKey(String algorithm, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec)
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
      EllipticCurve ellipticCurve = EC5Util.convertCurve(spec.getCurve(), spec.getSeed());
      
      ecSpec = EC5Util.convertSpec(ellipticCurve, spec);
    }
  }
  





  public BCECGOST3410PublicKey(String algorithm, ECPublicKeyParameters params)
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
  

  public BCECGOST3410PublicKey(java.security.interfaces.ECPublicKey key)
  {
    algorithm = key.getAlgorithm();
    ecSpec = key.getParams();
    ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(ecSpec, key.getW(), false), EC5Util.getDomainParameters(null, key.getParams()));
  }
  

  BCECGOST3410PublicKey(SubjectPublicKeyInfo info)
  {
    populateFromPubKeyInfo(info);
  }
  
  private void populateFromPubKeyInfo(SubjectPublicKeyInfo info)
  {
    DERBitString bits = info.getPublicKeyData();
    
    algorithm = "ECGOST3410";
    
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
    byte[] x = new byte[32];
    byte[] y = new byte[32];
    
    for (int i = 0; i != x.length; i++)
    {
      x[i] = keyEnc[(31 - i)];
    }
    
    for (int i = 0; i != y.length; i++)
    {
      y[i] = keyEnc[(63 - i)];
    }
    
    ASN1ObjectIdentifier paramOID;
    
    if ((info.getAlgorithm().getParameters() instanceof ASN1ObjectIdentifier))
    {
      ASN1ObjectIdentifier paramOID = ASN1ObjectIdentifier.getInstance(info.getAlgorithm().getParameters());
      gostParams = paramOID;
    }
    else
    {
      GOST3410PublicKeyAlgParameters params = GOST3410PublicKeyAlgParameters.getInstance(info.getAlgorithm().getParameters());
      gostParams = params;
      paramOID = params.getPublicKeyParamSet();
    }
    

    ECNamedCurveParameterSpec spec = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(paramOID));
    
    ECCurve curve = spec.getCurve();
    EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, spec.getSeed());
    
    ecPublicKey = new ECPublicKeyParameters(curve.createPoint(new BigInteger(1, x), new BigInteger(1, y)), ECUtil.getDomainParameters(null, spec));
    






    ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(paramOID), ellipticCurve, new java.security.spec.ECPoint(spec.getG().getAffineXCoord().toBigInteger(), spec.getG().getAffineYCoord().toBigInteger()), spec.getN(), spec.getH());
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
    if (gostParams != null)
    {
      params = gostParams;
    }
    else {
      ASN1Encodable params;
      if ((ecSpec instanceof ECNamedCurveSpec))
      {

        params = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.getOID(((ECNamedCurveSpec)ecSpec).getName()), CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet);

      }
      else
      {
        ECCurve curve = EC5Util.convertCurve(ecSpec.getCurve());
        





        X9ECParameters ecP = new X9ECParameters(curve, EC5Util.convertPoint(curve, ecSpec.getGenerator(), withCompression), ecSpec.getOrder(), BigInteger.valueOf(ecSpec.getCofactor()), ecSpec.getCurve().getSeed());
        
        params = new X962Parameters(ecP);
      }
    }
    
    BigInteger bX = ecPublicKey.getQ().getAffineXCoord().toBigInteger();
    BigInteger bY = ecPublicKey.getQ().getAffineYCoord().toBigInteger();
    byte[] encKey = new byte[64];
    
    extractBytes(encKey, 0, bX);
    extractBytes(encKey, 32, bY);
    
    try
    {
      info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, params), new DEROctetString(encKey));
    }
    catch (IOException e) {
      SubjectPublicKeyInfo info;
      return null;
    }
    SubjectPublicKeyInfo info;
    return KeyUtil.getEncodedSubjectPublicKeyInfo(info);
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
  
  public java.security.spec.ECPoint getW()
  {
    return new java.security.spec.ECPoint(ecPublicKey.getQ().getAffineXCoord().toBigInteger(), ecPublicKey.getQ().getAffineYCoord().toBigInteger());
  }
  
  public org.spongycastle.math.ec.ECPoint getQ()
  {
    if (ecSpec == null)
    {
      return ecPublicKey.getQ().getDetachedPoint();
    }
    
    return ecPublicKey.getQ();
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
    if (!(o instanceof BCECGOST3410PublicKey))
    {
      return false;
    }
    
    BCECGOST3410PublicKey other = (BCECGOST3410PublicKey)o;
    
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
  
  ASN1Encodable getGostParams()
  {
    return gostParams;
  }
}
