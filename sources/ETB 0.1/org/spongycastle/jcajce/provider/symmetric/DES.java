package org.spongycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.PasswordConverter;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.generators.DESKeyGenerator;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.macs.ISO9797Alg3Mac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.crypto.params.DESParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.jcajce.PBKDF1Key;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.symmetric.util.PBE.Util;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;




public final class DES
{
  private DES() {}
  
  public static class ECB
    extends BaseBlockCipher
  {
    public ECB()
    {
      super();
    }
  }
  
  public static class CBC
    extends BaseBlockCipher
  {
    public CBC()
    {
      super(64);
    }
  }
  



  public static class DESCFB8
    extends BaseMac
  {
    public DESCFB8()
    {
      super();
    }
  }
  



  public static class DES64
    extends BaseMac
  {
    public DES64()
    {
      super();
    }
  }
  



  public static class DES64with7816d4
    extends BaseMac
  {
    public DES64with7816d4()
    {
      super();
    }
  }
  
  public static class CBCMAC
    extends BaseMac
  {
    public CBCMAC()
    {
      super();
    }
  }
  
  public static class CMAC
    extends BaseMac
  {
    public CMAC()
    {
      super();
    }
  }
  



  public static class DES9797Alg3with7816d4
    extends BaseMac
  {
    public DES9797Alg3with7816d4()
    {
      super();
    }
  }
  



  public static class DES9797Alg3
    extends BaseMac
  {
    public DES9797Alg3()
    {
      super();
    }
  }
  
  public static class RFC3211
    extends BaseWrapCipher
  {
    public RFC3211()
    {
      super(8);
    }
  }
  
  public static class AlgParamGen
    extends BaseAlgorithmParameterGenerator
  {
    public AlgParamGen() {}
    
    protected void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random)
      throws InvalidAlgorithmParameterException
    {
      throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DES parameter generation.");
    }
    
    protected AlgorithmParameters engineGenerateParameters()
    {
      byte[] iv = new byte[8];
      
      if (random == null)
      {
        random = new SecureRandom();
      }
      
      random.nextBytes(iv);
      


      try
      {
        AlgorithmParameters params = createParametersInstance("DES");
        params.init(new IvParameterSpec(iv));
      }
      catch (Exception e)
      {
        throw new RuntimeException(e.getMessage());
      }
      AlgorithmParameters params;
      return params;
    }
  }
  







  public static class KeyGenerator
    extends BaseKeyGenerator
  {
    public KeyGenerator()
    {
      super(64, new DESKeyGenerator());
    }
    


    protected void engineInit(int keySize, SecureRandom random)
    {
      super.engineInit(keySize, random);
    }
    
    protected SecretKey engineGenerateKey()
    {
      if (uninitialised)
      {
        engine.init(new KeyGenerationParameters(new SecureRandom(), defaultKeySize));
        uninitialised = false;
      }
      
      return new SecretKeySpec(engine.generateKey(), algName);
    }
  }
  
  public static class KeyFactory
    extends BaseSecretKeyFactory
  {
    public KeyFactory()
    {
      super(null);
    }
    


    protected KeySpec engineGetKeySpec(SecretKey key, Class keySpec)
      throws InvalidKeySpecException
    {
      if (keySpec == null)
      {
        throw new InvalidKeySpecException("keySpec parameter is null");
      }
      if (key == null)
      {
        throw new InvalidKeySpecException("key parameter is null");
      }
      
      if (SecretKeySpec.class.isAssignableFrom(keySpec))
      {
        return new SecretKeySpec(key.getEncoded(), algName);
      }
      if (DESKeySpec.class.isAssignableFrom(keySpec))
      {
        byte[] bytes = key.getEncoded();
        
        try
        {
          return new DESKeySpec(bytes);
        }
        catch (Exception e)
        {
          throw new InvalidKeySpecException(e.toString());
        }
      }
      
      throw new InvalidKeySpecException("Invalid KeySpec");
    }
    

    protected SecretKey engineGenerateSecret(KeySpec keySpec)
      throws InvalidKeySpecException
    {
      if ((keySpec instanceof DESKeySpec))
      {
        DESKeySpec desKeySpec = (DESKeySpec)keySpec;
        return new SecretKeySpec(desKeySpec.getKey(), "DES");
      }
      
      return super.engineGenerateSecret(keySpec);
    }
  }
  


  public static class DESPBEKeyFactory
    extends BaseSecretKeyFactory
  {
    private boolean forCipher;
    
    private int scheme;
    
    private int digest;
    
    private int keySize;
    
    private int ivSize;
    

    public DESPBEKeyFactory(String algorithm, ASN1ObjectIdentifier oid, boolean forCipher, int scheme, int digest, int keySize, int ivSize)
    {
      super(oid);
      
      this.forCipher = forCipher;
      this.scheme = scheme;
      this.digest = digest;
      this.keySize = keySize;
      this.ivSize = ivSize;
    }
    

    protected SecretKey engineGenerateSecret(KeySpec keySpec)
      throws InvalidKeySpecException
    {
      if ((keySpec instanceof PBEKeySpec))
      {
        PBEKeySpec pbeSpec = (PBEKeySpec)keySpec;
        

        if (pbeSpec.getSalt() == null)
        {
          if ((scheme == 0) || (scheme == 4))
          {
            return new PBKDF1Key(pbeSpec.getPassword(), scheme == 0 ? PasswordConverter.ASCII : PasswordConverter.UTF8);
          }
          


          return new BCPBEKey(algName, algOid, scheme, digest, keySize, ivSize, pbeSpec, null);
        }
        CipherParameters param;
        CipherParameters param;
        if (forCipher)
        {
          param = PBE.Util.makePBEParameters(pbeSpec, scheme, digest, keySize, ivSize);
        }
        else
        {
          param = PBE.Util.makePBEMacParameters(pbeSpec, scheme, digest, keySize);
        }
        KeyParameter kParam;
        KeyParameter kParam;
        if ((param instanceof ParametersWithIV))
        {
          kParam = (KeyParameter)((ParametersWithIV)param).getParameters();
        }
        else
        {
          kParam = (KeyParameter)param;
        }
        
        DESParameters.setOddParity(kParam.getKey());
        
        return new BCPBEKey(algName, algOid, scheme, digest, keySize, ivSize, pbeSpec, param);
      }
      
      throw new InvalidKeySpecException("Invalid KeySpec");
    }
  }
  



  public static class PBEWithMD2KeyFactory
    extends DES.DESPBEKeyFactory
  {
    public PBEWithMD2KeyFactory()
    {
      super(PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC, true, 0, 5, 64, 64);
    }
  }
  



  public static class PBEWithMD5KeyFactory
    extends DES.DESPBEKeyFactory
  {
    public PBEWithMD5KeyFactory()
    {
      super(PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC, true, 0, 0, 64, 64);
    }
  }
  



  public static class PBEWithSHA1KeyFactory
    extends DES.DESPBEKeyFactory
  {
    public PBEWithSHA1KeyFactory()
    {
      super(PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC, true, 0, 1, 64, 64);
    }
  }
  



  public static class PBEWithMD2
    extends BaseBlockCipher
  {
    public PBEWithMD2()
    {
      super(0, 5, 64, 8);
    }
  }
  



  public static class PBEWithMD5
    extends BaseBlockCipher
  {
    public PBEWithMD5()
    {
      super(0, 0, 64, 8);
    }
  }
  



  public static class PBEWithSHA1
    extends BaseBlockCipher
  {
    public PBEWithSHA1()
    {
      super(0, 1, 64, 8);
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = DES.class.getName();
    
    private static final String PACKAGE = "org.spongycastle.jcajce.provider.symmetric";
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("Cipher.DES", PREFIX + "$ECB");
      provider.addAlgorithm("Cipher", OIWObjectIdentifiers.desCBC, PREFIX + "$CBC");
      
      addAlias(provider, OIWObjectIdentifiers.desCBC, "DES");
      
      provider.addAlgorithm("Cipher.DESRFC3211WRAP", PREFIX + "$RFC3211");
      
      provider.addAlgorithm("KeyGenerator.DES", PREFIX + "$KeyGenerator");
      
      provider.addAlgorithm("SecretKeyFactory.DES", PREFIX + "$KeyFactory");
      
      provider.addAlgorithm("Mac.DESCMAC", PREFIX + "$CMAC");
      provider.addAlgorithm("Mac.DESMAC", PREFIX + "$CBCMAC");
      provider.addAlgorithm("Alg.Alias.Mac.DES", "DESMAC");
      
      provider.addAlgorithm("Mac.DESMAC/CFB8", PREFIX + "$DESCFB8");
      provider.addAlgorithm("Alg.Alias.Mac.DES/CFB8", "DESMAC/CFB8");
      
      provider.addAlgorithm("Mac.DESMAC64", PREFIX + "$DES64");
      provider.addAlgorithm("Alg.Alias.Mac.DES64", "DESMAC64");
      
      provider.addAlgorithm("Mac.DESMAC64WITHISO7816-4PADDING", PREFIX + "$DES64with7816d4");
      provider.addAlgorithm("Alg.Alias.Mac.DES64WITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
      provider.addAlgorithm("Alg.Alias.Mac.DESISO9797ALG1MACWITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
      provider.addAlgorithm("Alg.Alias.Mac.DESISO9797ALG1WITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
      
      provider.addAlgorithm("Mac.DESWITHISO9797", PREFIX + "$DES9797Alg3");
      provider.addAlgorithm("Alg.Alias.Mac.DESISO9797MAC", "DESWITHISO9797");
      
      provider.addAlgorithm("Mac.ISO9797ALG3MAC", PREFIX + "$DES9797Alg3");
      provider.addAlgorithm("Alg.Alias.Mac.ISO9797ALG3", "ISO9797ALG3MAC");
      provider.addAlgorithm("Mac.ISO9797ALG3WITHISO7816-4PADDING", PREFIX + "$DES9797Alg3with7816d4");
      provider.addAlgorithm("Alg.Alias.Mac.ISO9797ALG3MACWITHISO7816-4PADDING", "ISO9797ALG3WITHISO7816-4PADDING");
      
      provider.addAlgorithm("AlgorithmParameters.DES", "org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters", OIWObjectIdentifiers.desCBC, "DES");
      
      provider.addAlgorithm("AlgorithmParameterGenerator.DES", PREFIX + "$AlgParamGen");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + OIWObjectIdentifiers.desCBC, "DES");
      
      provider.addAlgorithm("Cipher.PBEWITHMD2ANDDES", PREFIX + "$PBEWithMD2");
      provider.addAlgorithm("Cipher.PBEWITHMD5ANDDES", PREFIX + "$PBEWithMD5");
      provider.addAlgorithm("Cipher.PBEWITHSHA1ANDDES", PREFIX + "$PBEWithSHA1");
      
      provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC, "PBEWITHMD2ANDDES");
      provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC, "PBEWITHMD5ANDDES");
      provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC, "PBEWITHSHA1ANDDES");
      
      provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHMD2ANDDES-CBC", "PBEWITHMD2ANDDES");
      provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHMD5ANDDES-CBC", "PBEWITHMD5ANDDES");
      provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDES-CBC", "PBEWITHSHA1ANDDES");
      
      provider.addAlgorithm("SecretKeyFactory.PBEWITHMD2ANDDES", PREFIX + "$PBEWithMD2KeyFactory");
      provider.addAlgorithm("SecretKeyFactory.PBEWITHMD5ANDDES", PREFIX + "$PBEWithMD5KeyFactory");
      provider.addAlgorithm("SecretKeyFactory.PBEWITHSHA1ANDDES", PREFIX + "$PBEWithSHA1KeyFactory");
      
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHMD2ANDDES-CBC", "PBEWITHMD2ANDDES");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHMD5ANDDES-CBC", "PBEWITHMD5ANDDES");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1ANDDES-CBC", "PBEWITHSHA1ANDDES");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC, "PBEWITHMD2ANDDES");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC, "PBEWITHMD5ANDDES");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC, "PBEWITHSHA1ANDDES");
    }
    
    private void addAlias(ConfigurableProvider provider, ASN1ObjectIdentifier oid, String name)
    {
      provider.addAlgorithm("Alg.Alias.KeyGenerator." + oid.getId(), name);
      provider.addAlgorithm("Alg.Alias.KeyFactory." + oid.getId(), name);
    }
  }
}
