package org.spongycastle.jce.provider;

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
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.ECGOST3410NamedCurveTable;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.util.Strings;






public class JCEECPublicKey
  implements java.security.interfaces.ECPublicKey, org.spongycastle.jce.interfaces.ECPublicKey, ECPointEncoder
{
  private String algorithm = "EC";
  
  private org.spongycastle.math.ec.ECPoint q;
  
  private java.security.spec.ECParameterSpec ecSpec;
  private boolean withCompression;
  private GOST3410PublicKeyAlgParameters gostParams;
  
  public JCEECPublicKey(String algorithm, JCEECPublicKey key)
  {
    this.algorithm = algorithm;
    q = q;
    ecSpec = ecSpec;
    withCompression = withCompression;
    gostParams = gostParams;
  }
  


  public JCEECPublicKey(String algorithm, java.security.spec.ECPublicKeySpec spec)
  {
    this.algorithm = algorithm;
    ecSpec = spec.getParams();
    q = EC5Util.convertPoint(ecSpec, spec.getW(), false);
  }
  


  public JCEECPublicKey(String algorithm, org.spongycastle.jce.spec.ECPublicKeySpec spec)
  {
    this.algorithm = algorithm;
    q = spec.getQ();
    
    if (spec.getParams() != null)
    {
      ECCurve curve = spec.getParams().getCurve();
      EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, spec.getParams().getSeed());
      
      ecSpec = EC5Util.convertSpec(ellipticCurve, spec.getParams());
    }
    else
    {
      if (q.getCurve() == null)
      {
        org.spongycastle.jce.spec.ECParameterSpec s = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
        
        q = s.getCurve().createPoint(q.getAffineXCoord().toBigInteger(), q.getAffineYCoord().toBigInteger(), false);
      }
      ecSpec = null;
    }
  }
  



  public JCEECPublicKey(String algorithm, ECPublicKeyParameters params, java.security.spec.ECParameterSpec spec)
  {
    ECDomainParameters dp = params.getParameters();
    
    this.algorithm = algorithm;
    q = params.getQ();
    
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
  



  public JCEECPublicKey(String algorithm, ECPublicKeyParameters params, org.spongycastle.jce.spec.ECParameterSpec spec)
  {
    ECDomainParameters dp = params.getParameters();
    
    this.algorithm = algorithm;
    q = params.getQ();
    
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
  





  public JCEECPublicKey(String algorithm, ECPublicKeyParameters params)
  {
    this.algorithm = algorithm;
    q = params.getQ();
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
  

  public JCEECPublicKey(java.security.interfaces.ECPublicKey key)
  {
    algorithm = key.getAlgorithm();
    ecSpec = key.getParams();
    q = EC5Util.convertPoint(ecSpec, key.getW(), false);
  }
  

  JCEECPublicKey(SubjectPublicKeyInfo info)
  {
    populateFromPubKeyInfo(info);
  }
  
  private void populateFromPubKeyInfo(SubjectPublicKeyInfo info)
  {
    if (info.getAlgorithmId().getAlgorithm().equals(CryptoProObjectIdentifiers.gostR3410_2001))
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
      
      gostParams = new GOST3410PublicKeyAlgParameters((ASN1Sequence)info.getAlgorithmId().getParameters());
      
      ECNamedCurveParameterSpec spec = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(gostParams.getPublicKeyParamSet()));
      
      ECCurve curve = spec.getCurve();
      EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, spec.getSeed());
      
      q = curve.createPoint(new BigInteger(1, x), new BigInteger(1, y), false);
      






      ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(gostParams.getPublicKeyParamSet()), ellipticCurve, new java.security.spec.ECPoint(spec.getG().getAffineXCoord().toBigInteger(), spec.getG().getAffineYCoord().toBigInteger()), spec.getN(), spec.getH());

    }
    else
    {
      X962Parameters params = new X962Parameters((ASN1Primitive)info.getAlgorithmId().getParameters());
      
      ECCurve curve;
      
      if (params.isNamedCurve())
      {
        ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)params.getParameters();
        X9ECParameters ecP = ECUtil.getNamedCurveByOid(oid);
        
        ECCurve curve = ecP.getCurve();
        EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, ecP.getSeed());
        







        ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(oid), ellipticCurve, new java.security.spec.ECPoint(ecP.getG().getAffineXCoord().toBigInteger(), ecP.getG().getAffineYCoord().toBigInteger()), ecP.getN(), ecP.getH());
      } else { ECCurve curve;
        if (params.isImplicitlyCA())
        {
          ecSpec = null;
          curve = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve();
        }
        else
        {
          X9ECParameters ecP = X9ECParameters.getInstance(params.getParameters());
          
          curve = ecP.getCurve();
          EllipticCurve ellipticCurve = EC5Util.convertCurve(curve, ecP.getSeed());
          






          ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(ecP.getG().getAffineXCoord().toBigInteger(), ecP.getG().getAffineYCoord().toBigInteger()), ecP.getN(), ecP.getH().intValue());
        }
      }
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
      
      q = derQ.getPoint();
    }
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
    SubjectPublicKeyInfo info;
    SubjectPublicKeyInfo info;
    if (algorithm.equals("ECGOST3410")) { ASN1Encodable params;
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
      
      BigInteger bX = q.getAffineXCoord().toBigInteger();
      BigInteger bY = q.getAffineYCoord().toBigInteger();
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
    } else {
      ASN1Encodable params;
      ASN1Encodable params;
      if ((ecSpec instanceof ECNamedCurveSpec))
      {
        ASN1ObjectIdentifier curveOid = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)ecSpec).getName());
        if (curveOid == null)
        {
          curveOid = new ASN1ObjectIdentifier(((ECNamedCurveSpec)ecSpec).getName());
        }
        params = new X962Parameters(curveOid);
      } else { ASN1Encodable params;
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
      ECCurve curve = engineGetQ().getCurve();
      
      ASN1OctetString p = (ASN1OctetString)new X9ECPoint(curve.createPoint(getQ().getAffineXCoord().toBigInteger(), getQ().getAffineYCoord().toBigInteger(), withCompression)).toASN1Primitive();
      
      info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, params), p.getOctets());
    }
    
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
    return new java.security.spec.ECPoint(q.getAffineXCoord().toBigInteger(), q.getAffineYCoord().toBigInteger());
  }
  
  public org.spongycastle.math.ec.ECPoint getQ()
  {
    if (ecSpec == null)
    {
      return q.getDetachedPoint();
    }
    
    return q;
  }
  
  public org.spongycastle.math.ec.ECPoint engineGetQ()
  {
    return q;
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
    StringBuffer buf = new StringBuffer();
    String nl = Strings.lineSeparator();
    
    buf.append("EC Public Key").append(nl);
    buf.append("            X: ").append(q.getAffineXCoord().toBigInteger().toString(16)).append(nl);
    buf.append("            Y: ").append(q.getAffineYCoord().toBigInteger().toString(16)).append(nl);
    
    return buf.toString();
  }
  

  public void setPointFormat(String style)
  {
    withCompression = (!"UNCOMPRESSED".equalsIgnoreCase(style));
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof JCEECPublicKey))
    {
      return false;
    }
    
    JCEECPublicKey other = (JCEECPublicKey)o;
    
    return (engineGetQ().equals(other.engineGetQ())) && (engineGetSpec().equals(other.engineGetSpec()));
  }
  
  public int hashCode()
  {
    return engineGetQ().hashCode() ^ engineGetSpec().hashCode();
  }
  

  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    byte[] enc = (byte[])in.readObject();
    
    populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(enc)));
    
    algorithm = ((String)in.readObject());
    withCompression = in.readBoolean();
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.writeObject(getEncoded());
    out.writeObject(algorithm);
    out.writeBoolean(withCompression);
  }
}
