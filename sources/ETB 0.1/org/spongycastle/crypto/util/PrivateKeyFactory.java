package org.spongycastle.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.RSAPrivateKey;
import org.spongycastle.asn1.sec.ECPrivateKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECNamedDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;









public class PrivateKeyFactory
{
  public PrivateKeyFactory() {}
  
  public static AsymmetricKeyParameter createKey(byte[] privateKeyInfoData)
    throws IOException
  {
    return createKey(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(privateKeyInfoData)));
  }
  







  public static AsymmetricKeyParameter createKey(InputStream inStr)
    throws IOException
  {
    return createKey(PrivateKeyInfo.getInstance(new ASN1InputStream(inStr).readObject()));
  }
  






  public static AsymmetricKeyParameter createKey(PrivateKeyInfo keyInfo)
    throws IOException
  {
    AlgorithmIdentifier algId = keyInfo.getPrivateKeyAlgorithm();
    
    if (algId.getAlgorithm().equals(PKCSObjectIdentifiers.rsaEncryption))
    {
      RSAPrivateKey keyStructure = RSAPrivateKey.getInstance(keyInfo.parsePrivateKey());
      
      return new RSAPrivateCrtKeyParameters(keyStructure.getModulus(), keyStructure
        .getPublicExponent(), keyStructure.getPrivateExponent(), keyStructure
        .getPrime1(), keyStructure.getPrime2(), keyStructure.getExponent1(), keyStructure
        .getExponent2(), keyStructure.getCoefficient());
    }
    

    if (algId.getAlgorithm().equals(PKCSObjectIdentifiers.dhKeyAgreement))
    {
      DHParameter params = DHParameter.getInstance(algId.getParameters());
      ASN1Integer derX = (ASN1Integer)keyInfo.parsePrivateKey();
      
      BigInteger lVal = params.getL();
      int l = lVal == null ? 0 : lVal.intValue();
      DHParameters dhParams = new DHParameters(params.getP(), params.getG(), null, l);
      
      return new DHPrivateKeyParameters(derX.getValue(), dhParams);
    }
    if (algId.getAlgorithm().equals(OIWObjectIdentifiers.elGamalAlgorithm))
    {
      ElGamalParameter params = ElGamalParameter.getInstance(algId.getParameters());
      ASN1Integer derX = (ASN1Integer)keyInfo.parsePrivateKey();
      
      return new ElGamalPrivateKeyParameters(derX.getValue(), new ElGamalParameters(params
        .getP(), params.getG()));
    }
    if (algId.getAlgorithm().equals(X9ObjectIdentifiers.id_dsa))
    {
      ASN1Integer derX = (ASN1Integer)keyInfo.parsePrivateKey();
      ASN1Encodable de = algId.getParameters();
      
      DSAParameters parameters = null;
      if (de != null)
      {
        DSAParameter params = DSAParameter.getInstance(de.toASN1Primitive());
        parameters = new DSAParameters(params.getP(), params.getQ(), params.getG());
      }
      
      return new DSAPrivateKeyParameters(derX.getValue(), parameters);
    }
    if (algId.getAlgorithm().equals(X9ObjectIdentifiers.id_ecPublicKey))
    {
      X962Parameters params = new X962Parameters((ASN1Primitive)algId.getParameters());
      
      ECDomainParameters dParams;
      
      ECDomainParameters dParams;
      if (params.isNamedCurve())
      {
        ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)params.getParameters();
        
        X9ECParameters x9 = CustomNamedCurves.getByOID(oid);
        if (x9 == null)
        {
          x9 = ECNamedCurveTable.getByOID(oid);
        }
        
        dParams = new ECNamedDomainParameters(oid, x9.getCurve(), x9.getG(), x9.getN(), x9.getH(), x9.getSeed());
      }
      else
      {
        X9ECParameters x9 = X9ECParameters.getInstance(params.getParameters());
        
        dParams = new ECDomainParameters(x9.getCurve(), x9.getG(), x9.getN(), x9.getH(), x9.getSeed());
      }
      
      ECPrivateKey ec = ECPrivateKey.getInstance(keyInfo.parsePrivateKey());
      BigInteger d = ec.getKey();
      
      return new ECPrivateKeyParameters(d, dParams);
    }
    

    throw new RuntimeException("algorithm identifier in key not recognised");
  }
}
