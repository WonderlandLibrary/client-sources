package org.spongycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.DESedeWrapEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.generators.DESedeKeyGenerator;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;




public final class DESede
{
  private DESede() {}
  
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
  



  public static class DESedeCFB8
    extends BaseMac
  {
    public DESedeCFB8()
    {
      super();
    }
  }
  



  public static class DESede64
    extends BaseMac
  {
    public DESede64()
    {
      super();
    }
  }
  



  public static class DESede64with7816d4
    extends BaseMac
  {
    public DESede64with7816d4()
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
  
  public static class Wrap
    extends BaseWrapCipher
  {
    public Wrap()
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
  







  public static class KeyGenerator
    extends BaseKeyGenerator
  {
    private boolean keySizeSet = false;
    
    public KeyGenerator()
    {
      super(192, new DESedeKeyGenerator());
    }
    


    protected void engineInit(int keySize, SecureRandom random)
    {
      super.engineInit(keySize, random);
      keySizeSet = true;
    }
    
    protected SecretKey engineGenerateKey()
    {
      if (uninitialised)
      {
        engine.init(new KeyGenerationParameters(new SecureRandom(), defaultKeySize));
        uninitialised = false;
      }
      




      if (!keySizeSet)
      {
        byte[] k = engine.generateKey();
        
        System.arraycopy(k, 0, k, 16, 8);
        
        return new SecretKeySpec(k, algName);
      }
      

      return new SecretKeySpec(engine.generateKey(), algName);
    }
  }
  




  public static class KeyGenerator3
    extends BaseKeyGenerator
  {
    public KeyGenerator3()
    {
      super(192, new DESedeKeyGenerator());
    }
  }
  



  public static class PBEWithSHAAndDES3Key
    extends BaseBlockCipher
  {
    public PBEWithSHAAndDES3Key()
    {
      super(2, 1, 192, 8);
    }
  }
  



  public static class PBEWithSHAAndDES2Key
    extends BaseBlockCipher
  {
    public PBEWithSHAAndDES2Key()
    {
      super(2, 1, 128, 8);
    }
  }
  



  public static class PBEWithSHAAndDES3KeyFactory
    extends DES.DESPBEKeyFactory
  {
    public PBEWithSHAAndDES3KeyFactory()
    {
      super(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, true, 2, 1, 192, 64);
    }
  }
  



  public static class PBEWithSHAAndDES2KeyFactory
    extends DES.DESPBEKeyFactory
  {
    public PBEWithSHAAndDES2KeyFactory()
    {
      super(PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, true, 2, 1, 128, 64);
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
      if (DESedeKeySpec.class.isAssignableFrom(keySpec))
      {
        byte[] bytes = key.getEncoded();
        
        try
        {
          if (bytes.length == 16)
          {
            byte[] longKey = new byte[24];
            
            System.arraycopy(bytes, 0, longKey, 0, 16);
            System.arraycopy(bytes, 0, longKey, 16, 8);
            
            return new DESedeKeySpec(longKey);
          }
          

          return new DESedeKeySpec(bytes);

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
      if ((keySpec instanceof DESedeKeySpec))
      {
        DESedeKeySpec desKeySpec = (DESedeKeySpec)keySpec;
        return new SecretKeySpec(desKeySpec.getKey(), "DESede");
      }
      
      return super.engineGenerateSecret(keySpec);
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = DESede.class.getName();
    
    private static final String PACKAGE = "org.spongycastle.jcajce.provider.symmetric";
    

    public Mappings() {}
    
    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("Cipher.DESEDE", PREFIX + "$ECB");
      provider.addAlgorithm("Cipher", PKCSObjectIdentifiers.des_EDE3_CBC, PREFIX + "$CBC");
      provider.addAlgorithm("Cipher.DESEDEWRAP", PREFIX + "$Wrap");
      provider.addAlgorithm("Cipher", PKCSObjectIdentifiers.id_alg_CMS3DESwrap, PREFIX + "$Wrap");
      provider.addAlgorithm("Cipher.DESEDERFC3211WRAP", PREFIX + "$RFC3211");
      provider.addAlgorithm("Alg.Alias.Cipher.DESEDERFC3217WRAP", "DESEDEWRAP");
      
      provider.addAlgorithm("Alg.Alias.Cipher.TDEA", "DESEDE");
      provider.addAlgorithm("Alg.Alias.Cipher.TDEAWRAP", "DESEDEWRAP");
      provider.addAlgorithm("Alg.Alias.KeyGenerator.TDEA", "DESEDE");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.TDEA", "DESEDE");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator.TDEA", "DESEDE");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.TDEA", "DESEDE");
      
      if (provider.hasAlgorithm("MessageDigest", "SHA-1"))
      {
        provider.addAlgorithm("Cipher.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES3Key");
        provider.addAlgorithm("Cipher.BROKENPBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$BrokePBEWithSHAAndDES3Key");
        provider.addAlgorithm("Cipher.OLDPBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$OldPBEWithSHAAndDES3Key");
        provider.addAlgorithm("Cipher.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES2Key");
        provider.addAlgorithm("Cipher.BROKENPBEWITHSHAAND2-KEYTRIPLEDES-CBC", PREFIX + "$BrokePBEWithSHAAndDES2Key");
        provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYTRIPLEDES-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYTRIPLEDES-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND3-KEYDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND2-KEYDESEDE-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYDESEDE-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        provider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
      }
      
      provider.addAlgorithm("KeyGenerator.DESEDE", PREFIX + "$KeyGenerator");
      provider.addAlgorithm("KeyGenerator." + PKCSObjectIdentifiers.des_EDE3_CBC, PREFIX + "$KeyGenerator3");
      provider.addAlgorithm("KeyGenerator.DESEDEWRAP", PREFIX + "$KeyGenerator");
      
      provider.addAlgorithm("SecretKeyFactory.DESEDE", PREFIX + "$KeyFactory");
      
      provider.addAlgorithm("SecretKeyFactory", OIWObjectIdentifiers.desEDE, PREFIX + "$KeyFactory");
      
      provider.addAlgorithm("Mac.DESEDECMAC", PREFIX + "$CMAC");
      provider.addAlgorithm("Mac.DESEDEMAC", PREFIX + "$CBCMAC");
      provider.addAlgorithm("Alg.Alias.Mac.DESEDE", "DESEDEMAC");
      
      provider.addAlgorithm("Mac.DESEDEMAC/CFB8", PREFIX + "$DESedeCFB8");
      provider.addAlgorithm("Alg.Alias.Mac.DESEDE/CFB8", "DESEDEMAC/CFB8");
      
      provider.addAlgorithm("Mac.DESEDEMAC64", PREFIX + "$DESede64");
      provider.addAlgorithm("Alg.Alias.Mac.DESEDE64", "DESEDEMAC64");
      
      provider.addAlgorithm("Mac.DESEDEMAC64WITHISO7816-4PADDING", PREFIX + "$DESede64with7816d4");
      provider.addAlgorithm("Alg.Alias.Mac.DESEDE64WITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
      provider.addAlgorithm("Alg.Alias.Mac.DESEDEISO9797ALG1MACWITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
      provider.addAlgorithm("Alg.Alias.Mac.DESEDEISO9797ALG1WITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
      
      provider.addAlgorithm("AlgorithmParameters.DESEDE", "org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.des_EDE3_CBC, "DESEDE");
      
      provider.addAlgorithm("AlgorithmParameterGenerator.DESEDE", PREFIX + "$AlgParamGen");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + PKCSObjectIdentifiers.des_EDE3_CBC, "DESEDE");
      
      provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES3KeyFactory");
      provider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES2KeyFactory");
      
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES", "PKCS12PBE");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES", "PKCS12PBE");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PKCS12PBE");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PKCS12PBE");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES3KEY-CBC", "PKCS12PBE");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES2KEY-CBC", "PKCS12PBE");
      
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.3", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.4", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.3", "PKCS12PBE");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.4", "PKCS12PBE");
      provider.addAlgorithm("Alg.Alias.Cipher.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
    }
  }
}
