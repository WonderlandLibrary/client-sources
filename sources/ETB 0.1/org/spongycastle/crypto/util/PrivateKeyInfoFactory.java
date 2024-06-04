package org.spongycastle.crypto.util;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.RSAPrivateKey;
import org.spongycastle.asn1.sec.ECPrivateKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECNamedDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;













public class PrivateKeyInfoFactory
{
  private PrivateKeyInfoFactory() {}
  
  public static PrivateKeyInfo createPrivateKeyInfo(AsymmetricKeyParameter privateKey)
    throws IOException
  {
    if ((privateKey instanceof RSAKeyParameters))
    {
      RSAPrivateCrtKeyParameters priv = (RSAPrivateCrtKeyParameters)privateKey;
      
      return new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new RSAPrivateKey(priv.getModulus(), priv.getPublicExponent(), priv.getExponent(), priv.getP(), priv.getQ(), priv.getDP(), priv.getDQ(), priv.getQInv()));
    }
    if ((privateKey instanceof DSAPrivateKeyParameters))
    {
      DSAPrivateKeyParameters priv = (DSAPrivateKeyParameters)privateKey;
      DSAParameters params = priv.getParameters();
      
      return new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(params.getP(), params.getQ(), params.getG())), new ASN1Integer(priv.getX()));
    }
    if ((privateKey instanceof ECPrivateKeyParameters))
    {
      ECPrivateKeyParameters priv = (ECPrivateKeyParameters)privateKey;
      ECDomainParameters domainParams = priv.getParameters();
      int orderBitLength;
      ASN1Encodable params;
      int orderBitLength;
      if (domainParams == null)
      {
        ASN1Encodable params = new X962Parameters(DERNull.INSTANCE);
        orderBitLength = priv.getD().bitLength();
      } else { int orderBitLength;
        if ((domainParams instanceof ECNamedDomainParameters))
        {
          ASN1Encodable params = new X962Parameters(((ECNamedDomainParameters)domainParams).getName());
          orderBitLength = domainParams.getN().bitLength();



        }
        else
        {


          X9ECParameters ecP = new X9ECParameters(domainParams.getCurve(), domainParams.getG(), domainParams.getN(), domainParams.getH(), domainParams.getSeed());
          
          params = new X962Parameters(ecP);
          orderBitLength = domainParams.getN().bitLength();
        }
      }
      return new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, params), new ECPrivateKey(orderBitLength, priv.getD(), params));
    }
    

    throw new IOException("key parameters not recognised.");
  }
}
