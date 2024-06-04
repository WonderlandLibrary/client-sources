package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Hashtable;
import java.util.Map;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Integers;


public abstract class KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  public KeyPairGeneratorSpi(String algorithmName)
  {
    super(algorithmName);
  }
  
  public static class EC
    extends KeyPairGeneratorSpi
  {
    ECKeyGenerationParameters param;
    ECKeyPairGenerator engine = new ECKeyPairGenerator();
    Object ecParams = null;
    int strength = 239;
    int certainty = 50;
    SecureRandom random = new SecureRandom();
    boolean initialised = false;
    

    String algorithm;
    
    ProviderConfiguration configuration;
    
    private static Hashtable ecParameters = new Hashtable();
    
    static { ecParameters.put(Integers.valueOf(192), new ECGenParameterSpec("prime192v1"));
      ecParameters.put(Integers.valueOf(239), new ECGenParameterSpec("prime239v1"));
      ecParameters.put(Integers.valueOf(256), new ECGenParameterSpec("prime256v1"));
      
      ecParameters.put(Integers.valueOf(224), new ECGenParameterSpec("P-224"));
      ecParameters.put(Integers.valueOf(384), new ECGenParameterSpec("P-384"));
      ecParameters.put(Integers.valueOf(521), new ECGenParameterSpec("P-521"));
    }
    
    public EC()
    {
      super();
      algorithm = "EC";
      configuration = BouncyCastleProvider.CONFIGURATION;
    }
    


    public EC(String algorithm, ProviderConfiguration configuration)
    {
      super();
      this.algorithm = algorithm;
      this.configuration = configuration;
    }
    


    public void initialize(int strength, SecureRandom random)
    {
      this.strength = strength;
      this.random = random;
      
      ECGenParameterSpec ecParams = (ECGenParameterSpec)ecParameters.get(Integers.valueOf(strength));
      if (ecParams == null)
      {
        throw new InvalidParameterException("unknown key size.");
      }
      
      try
      {
        initialize(ecParams, random);
      }
      catch (InvalidAlgorithmParameterException e)
      {
        throw new InvalidParameterException("key size not configurable.");
      }
    }
    


    public void initialize(AlgorithmParameterSpec params, SecureRandom random)
      throws InvalidAlgorithmParameterException
    {
      if (params == null)
      {
        org.spongycastle.jce.spec.ECParameterSpec implicitCA = configuration.getEcImplicitlyCa();
        if (implicitCA == null)
        {
          throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
        }
        
        ecParams = null;
        param = createKeyGenParamsBC(implicitCA, random);
      }
      else if ((params instanceof org.spongycastle.jce.spec.ECParameterSpec))
      {
        ecParams = params;
        param = createKeyGenParamsBC((org.spongycastle.jce.spec.ECParameterSpec)params, random);
      }
      else if ((params instanceof java.security.spec.ECParameterSpec))
      {
        ecParams = params;
        param = createKeyGenParamsJCE((java.security.spec.ECParameterSpec)params, random);
      }
      else if ((params instanceof ECGenParameterSpec))
      {
        initializeNamedCurve(((ECGenParameterSpec)params).getName(), random);
      }
      else if ((params instanceof ECNamedCurveGenParameterSpec))
      {
        initializeNamedCurve(((ECNamedCurveGenParameterSpec)params).getName(), random);
      }
      else
      {
        throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec");
      }
      
      engine.init(param);
      initialised = true;
    }
    
    public KeyPair generateKeyPair()
    {
      if (!initialised)
      {
        initialize(strength, new SecureRandom());
      }
      
      AsymmetricCipherKeyPair pair = engine.generateKeyPair();
      ECPublicKeyParameters pub = (ECPublicKeyParameters)pair.getPublic();
      ECPrivateKeyParameters priv = (ECPrivateKeyParameters)pair.getPrivate();
      
      if ((ecParams instanceof org.spongycastle.jce.spec.ECParameterSpec))
      {
        org.spongycastle.jce.spec.ECParameterSpec p = (org.spongycastle.jce.spec.ECParameterSpec)ecParams;
        
        BCECPublicKey pubKey = new BCECPublicKey(algorithm, pub, p, configuration);
        return new KeyPair(pubKey, new BCECPrivateKey(algorithm, priv, pubKey, p, configuration));
      }
      
      if (ecParams == null)
      {
        return new KeyPair(new BCECPublicKey(algorithm, pub, configuration), new BCECPrivateKey(algorithm, priv, configuration));
      }
      


      java.security.spec.ECParameterSpec p = (java.security.spec.ECParameterSpec)ecParams;
      
      BCECPublicKey pubKey = new BCECPublicKey(algorithm, pub, p, configuration);
      
      return new KeyPair(pubKey, new BCECPrivateKey(algorithm, priv, pubKey, p, configuration));
    }
    

    protected ECKeyGenerationParameters createKeyGenParamsBC(org.spongycastle.jce.spec.ECParameterSpec p, SecureRandom r)
    {
      return new ECKeyGenerationParameters(new ECDomainParameters(p.getCurve(), p.getG(), p.getN(), p.getH()), r);
    }
    
    protected ECKeyGenerationParameters createKeyGenParamsJCE(java.security.spec.ECParameterSpec p, SecureRandom r)
    {
      ECCurve curve = EC5Util.convertCurve(p.getCurve());
      ECPoint g = EC5Util.convertPoint(curve, p.getGenerator(), false);
      BigInteger n = p.getOrder();
      BigInteger h = BigInteger.valueOf(p.getCofactor());
      ECDomainParameters dp = new ECDomainParameters(curve, g, n, h);
      return new ECKeyGenerationParameters(dp, r);
    }
    


    protected ECNamedCurveSpec createNamedCurveSpec(String curveName)
      throws InvalidAlgorithmParameterException
    {
      X9ECParameters p = ECUtils.getDomainParametersFromName(curveName);
      if (p == null)
      {
        try
        {

          p = ECNamedCurveTable.getByOID(new ASN1ObjectIdentifier(curveName));
          if (p == null)
          {
            Map extraCurves = configuration.getAdditionalECParameters();
            
            p = (X9ECParameters)extraCurves.get(new ASN1ObjectIdentifier(curveName));
            
            if (p == null)
            {
              throw new InvalidAlgorithmParameterException("unknown curve OID: " + curveName);
            }
          }
        }
        catch (IllegalArgumentException ex)
        {
          throw new InvalidAlgorithmParameterException("unknown curve name: " + curveName);
        }
      }
      

      byte[] seed = null;
      
      return new ECNamedCurveSpec(curveName, p.getCurve(), p.getG(), p.getN(), p.getH(), seed);
    }
    
    protected void initializeNamedCurve(String curveName, SecureRandom random)
      throws InvalidAlgorithmParameterException
    {
      ECNamedCurveSpec namedCurve = createNamedCurveSpec(curveName);
      ecParams = namedCurve;
      param = createKeyGenParamsJCE(namedCurve, random);
    }
  }
  
  public static class ECDSA
    extends KeyPairGeneratorSpi.EC
  {
    public ECDSA()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECDH
    extends KeyPairGeneratorSpi.EC
  {
    public ECDH()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECDHC
    extends KeyPairGeneratorSpi.EC
  {
    public ECDHC()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
  
  public static class ECMQV
    extends KeyPairGeneratorSpi.EC
  {
    public ECMQV()
    {
      super(BouncyCastleProvider.CONFIGURATION);
    }
  }
}
