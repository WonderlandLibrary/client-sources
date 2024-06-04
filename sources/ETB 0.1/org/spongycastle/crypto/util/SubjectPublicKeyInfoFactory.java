package org.spongycastle.crypto.util;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSAPublicKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECNamedDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;














public class SubjectPublicKeyInfoFactory
{
  private SubjectPublicKeyInfoFactory() {}
  
  public static SubjectPublicKeyInfo createSubjectPublicKeyInfo(AsymmetricKeyParameter publicKey)
    throws IOException
  {
    if ((publicKey instanceof RSAKeyParameters))
    {
      RSAKeyParameters pub = (RSAKeyParameters)publicKey;
      
      return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), new RSAPublicKey(pub.getModulus(), pub.getExponent()));
    }
    if ((publicKey instanceof DSAPublicKeyParameters))
    {
      DSAPublicKeyParameters pub = (DSAPublicKeyParameters)publicKey;
      
      DSAParameter params = null;
      DSAParameters dsaParams = pub.getParameters();
      if (dsaParams != null)
      {
        params = new DSAParameter(dsaParams.getP(), dsaParams.getQ(), dsaParams.getG());
      }
      
      return new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, params), new ASN1Integer(pub.getY()));
    }
    if ((publicKey instanceof ECPublicKeyParameters))
    {
      ECPublicKeyParameters pub = (ECPublicKeyParameters)publicKey;
      ECDomainParameters domainParams = pub.getParameters();
      ASN1Encodable params;
      ASN1Encodable params;
      if (domainParams == null)
      {
        params = new X962Parameters(DERNull.INSTANCE);
      } else { ASN1Encodable params;
        if ((domainParams instanceof ECNamedDomainParameters))
        {
          params = new X962Parameters(((ECNamedDomainParameters)domainParams).getName());



        }
        else
        {


          X9ECParameters ecP = new X9ECParameters(domainParams.getCurve(), domainParams.getG(), domainParams.getN(), domainParams.getH(), domainParams.getSeed());
          
          params = new X962Parameters(ecP);
        }
      }
      ASN1OctetString p = (ASN1OctetString)new X9ECPoint(pub.getQ()).toASN1Primitive();
      
      return new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, params), p.getOctets());
    }
    

    throw new IOException("key parameters not recognised.");
  }
}
