package org.spongycastle.jce;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECParameterSpec;












public class ECKeyUtil
{
  public ECKeyUtil() {}
  
  public static PublicKey publicToExplicitParameters(PublicKey key, String providerName)
    throws IllegalArgumentException, NoSuchAlgorithmException, NoSuchProviderException
  {
    Provider provider = Security.getProvider(providerName);
    
    if (provider == null)
    {
      throw new NoSuchProviderException("cannot find provider: " + providerName);
    }
    
    return publicToExplicitParameters(key, provider);
  }
  










  public static PublicKey publicToExplicitParameters(PublicKey key, Provider provider)
    throws IllegalArgumentException, NoSuchAlgorithmException
  {
    try
    {
      SubjectPublicKeyInfo info = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(key.getEncoded()));
      
      if (info.getAlgorithmId().getAlgorithm().equals(CryptoProObjectIdentifiers.gostR3410_2001))
      {
        throw new IllegalArgumentException("cannot convert GOST key to explicit parameters.");
      }
      

      X962Parameters params = X962Parameters.getInstance(info.getAlgorithmId().getParameters());
      

      if (params.isNamedCurve())
      {
        ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.getInstance(params.getParameters());
        
        X9ECParameters curveParams = ECUtil.getNamedCurveByOid(oid);
        
        curveParams = new X9ECParameters(curveParams.getCurve(), curveParams.getG(), curveParams.getN(), curveParams.getH());
      } else { X9ECParameters curveParams;
        if (params.isImplicitlyCA())
        {
          curveParams = new X9ECParameters(BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve(), BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getG(), BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getN(), BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getH());
        }
        else
        {
          return key; }
      }
      X9ECParameters curveParams;
      params = new X962Parameters(curveParams);
      
      info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, params), info.getPublicKeyData().getBytes());
      
      KeyFactory keyFact = KeyFactory.getInstance(key.getAlgorithm(), provider);
      
      return keyFact.generatePublic(new X509EncodedKeySpec(info.getEncoded()));

    }
    catch (IllegalArgumentException e)
    {
      throw e;
    }
    catch (NoSuchAlgorithmException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new UnexpectedException(e);
    }
  }
  











  public static PrivateKey privateToExplicitParameters(PrivateKey key, String providerName)
    throws IllegalArgumentException, NoSuchAlgorithmException, NoSuchProviderException
  {
    Provider provider = Security.getProvider(providerName);
    
    if (provider == null)
    {
      throw new NoSuchProviderException("cannot find provider: " + providerName);
    }
    
    return privateToExplicitParameters(key, provider);
  }
  










  public static PrivateKey privateToExplicitParameters(PrivateKey key, Provider provider)
    throws IllegalArgumentException, NoSuchAlgorithmException
  {
    try
    {
      PrivateKeyInfo info = PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(key.getEncoded()));
      
      if (info.getAlgorithmId().getAlgorithm().equals(CryptoProObjectIdentifiers.gostR3410_2001))
      {
        throw new UnsupportedEncodingException("cannot convert GOST key to explicit parameters.");
      }
      

      X962Parameters params = X962Parameters.getInstance(info.getAlgorithmId().getParameters());
      

      if (params.isNamedCurve())
      {
        ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.getInstance(params.getParameters());
        
        X9ECParameters curveParams = ECUtil.getNamedCurveByOid(oid);
        
        curveParams = new X9ECParameters(curveParams.getCurve(), curveParams.getG(), curveParams.getN(), curveParams.getH());
      } else { X9ECParameters curveParams;
        if (params.isImplicitlyCA())
        {
          curveParams = new X9ECParameters(BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve(), BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getG(), BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getN(), BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getH());
        }
        else
        {
          return key; }
      }
      X9ECParameters curveParams;
      params = new X962Parameters(curveParams);
      
      info = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, params), info.parsePrivateKey());
      
      KeyFactory keyFact = KeyFactory.getInstance(key.getAlgorithm(), provider);
      
      return keyFact.generatePrivate(new PKCS8EncodedKeySpec(info.getEncoded()));

    }
    catch (IllegalArgumentException e)
    {
      throw e;
    }
    catch (NoSuchAlgorithmException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new UnexpectedException(e);
    }
  }
  
  private static class UnexpectedException
    extends RuntimeException
  {
    private Throwable cause;
    
    UnexpectedException(Throwable cause)
    {
      super();
      
      this.cause = cause;
    }
    
    public Throwable getCause()
    {
      return cause;
    }
  }
}
