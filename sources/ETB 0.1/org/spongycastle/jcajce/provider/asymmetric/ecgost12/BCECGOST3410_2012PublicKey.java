package org.spongycastle.jcajce.provider.asymmetric.ecgost12;

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
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
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









public class BCECGOST3410_2012PublicKey
  implements java.security.interfaces.ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder
{
  static final long serialVersionUID = 7026240464295649314L;
  private String algorithm = "ECGOST3410-2012";
  
  private boolean withCompression;
  
  private transient ECPublicKeyParameters ecPublicKey;
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient GOST3410PublicKeyAlgParameters gostParams;
  
  public BCECGOST3410_2012PublicKey(BCECGOST3410_2012PublicKey key)
  {
    ecPublicKey = ecPublicKey;
    ecSpec = ecSpec;
    withCompression = withCompression;
    gostParams = gostParams;
  }
  

  public BCECGOST3410_2012PublicKey(java.security.spec.ECPublicKeySpec spec)
  {
    ecSpec = spec.getParams();
    ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(ecSpec, spec.getW(), false), EC5Util.getDomainParameters(null, spec.getParams()));
  }
  


  public BCECGOST3410_2012PublicKey(org.spongycastle.jce.spec.ECPublicKeySpec spec, ProviderConfiguration configuration)
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
  



  public BCECGOST3410_2012PublicKey(String algorithm, ECPublicKeyParameters params, java.security.spec.ECParameterSpec spec)
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
  



  public BCECGOST3410_2012PublicKey(String algorithm, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec)
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
  





  public BCECGOST3410_2012PublicKey(String algorithm, ECPublicKeyParameters params)
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
  

  public BCECGOST3410_2012PublicKey(java.security.interfaces.ECPublicKey key)
  {
    algorithm = key.getAlgorithm();
    ecSpec = key.getParams();
    ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(ecSpec, key.getW(), false), EC5Util.getDomainParameters(null, key.getParams()));
  }
  

  BCECGOST3410_2012PublicKey(SubjectPublicKeyInfo info)
  {
    populateFromPubKeyInfo(info);
  }
  
  private void populateFromPubKeyInfo(SubjectPublicKeyInfo info)
  {
    ASN1ObjectIdentifier algOid = info.getAlgorithm().getAlgorithm();
    DERBitString bits = info.getPublicKeyData();
    
    algorithm = "ECGOST3410-2012";
    
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
    int keySize = 64;
    if (algOid.equals(RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512))
    {
      keySize = 128;
    }
    int arraySize = keySize / 2;
    
    byte[] x = new byte[arraySize];
    byte[] y = new byte[arraySize];
    
    for (int i = 0; i != x.length; i++)
    {
      x[i] = keyEnc[(arraySize - 1 - i)];
    }
    
    for (int i = 0; i != y.length; i++)
    {
      y[i] = keyEnc[(keySize - 1 - i)];
    }
    
    gostParams = GOST3410PublicKeyAlgParameters.getInstance(info.getAlgorithm().getParameters());
    
    ECNamedCurveParameterSpec spec = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(gostParams.getPublicKeyParamSet()));
    
    ECCurve curve = spec.getCurve();
    EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, spec.getSeed());
    
    ecPublicKey = new ECPublicKeyParameters(curve.createPoint(new BigInteger(1, x), new BigInteger(1, y)), ECUtil.getDomainParameters(null, spec));
    






    ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(gostParams.getPublicKeyParamSet()), ellipticCurve, new java.security.spec.ECPoint(spec.getG().getAffineXCoord().toBigInteger(), spec.getG().getAffineYCoord().toBigInteger()), spec.getN(), spec.getH());
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
    BigInteger bX = ecPublicKey.getQ().getAffineXCoord().toBigInteger();
    BigInteger bY = ecPublicKey.getQ().getAffineYCoord().toBigInteger();
    

    boolean is512 = bX.bitLength() > 256;
    ASN1Encodable params;
    ASN1Encodable params; if (gostParams != null)
    {
      params = gostParams;
    }
    else {
      ASN1Encodable params;
      if ((ecSpec instanceof ECNamedCurveSpec)) {
        ASN1Encodable params;
        if (is512)
        {

          params = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.getOID(((ECNamedCurveSpec)ecSpec).getName()), RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512);

        }
        else
        {

          params = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.getOID(((ECNamedCurveSpec)ecSpec).getName()), RosstandartObjectIdentifiers.id_tc26_gost_3410_12_256);
        }
        
      }
      else
      {
        ECCurve curve = EC5Util.convertCurve(ecSpec.getCurve());
        





        X9ECParameters ecP = new X9ECParameters(curve, EC5Util.convertPoint(curve, ecSpec.getGenerator(), withCompression), ecSpec.getOrder(), BigInteger.valueOf(ecSpec.getCofactor()), ecSpec.getCurve().getSeed());
        
        params = new X962Parameters(ecP);
      }
    }
    ASN1ObjectIdentifier algIdentifier;
    int encKeySize;
    int offset;
    ASN1ObjectIdentifier algIdentifier;
    if (is512)
    {
      int encKeySize = 128;
      int offset = 64;
      algIdentifier = RosstandartObjectIdentifiers.id_tc26_gost_3410_12_512;
    }
    else
    {
      encKeySize = 64;
      offset = 32;
      algIdentifier = RosstandartObjectIdentifiers.id_tc26_gost_3410_12_256;
    }
    
    byte[] encKey = new byte[encKeySize];
    
    extractBytes(encKey, encKeySize / 2, 0, bX);
    extractBytes(encKey, encKeySize / 2, offset, bY);
    
    try
    {
      info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(algIdentifier, params), new DEROctetString(encKey));
    }
    catch (IOException e)
    {
      SubjectPublicKeyInfo info;
      return null;
    }
    SubjectPublicKeyInfo info;
    return KeyUtil.getEncodedSubjectPublicKeyInfo(info);
  }
  
  private void extractBytes(byte[] encKey, int size, int offSet, BigInteger bI)
  {
    byte[] val = bI.toByteArray();
    if (val.length < size)
    {
      byte[] tmp = new byte[size];
      System.arraycopy(val, 0, tmp, tmp.length - val.length, val.length);
      val = tmp;
    }
    
    for (int i = 0; i != size; i++)
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
    if (!(o instanceof BCECGOST3410_2012PublicKey))
    {
      return false;
    }
    
    BCECGOST3410_2012PublicKey other = (BCECGOST3410_2012PublicKey)o;
    
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
  
  public GOST3410PublicKeyAlgParameters getGostParams()
  {
    return gostParams;
  }
}
