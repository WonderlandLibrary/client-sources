package org.spongycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PBKDF2Params;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.PasswordConverter;
import org.spongycastle.jcajce.PBKDF2Key;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.symmetric.util.PBE.Util;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.spec.PBKDF2KeySpec;
import org.spongycastle.util.Integers;


public class PBEPBKDF2
{
  private static final Map prfCodes = new HashMap();
  
  private PBEPBKDF2() {}
  
  static { prfCodes.put(CryptoProObjectIdentifiers.gostR3411Hmac, Integers.valueOf(6));
    prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA1, Integers.valueOf(1));
    prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA256, Integers.valueOf(4));
    prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA224, Integers.valueOf(7));
    prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA384, Integers.valueOf(8));
    prfCodes.put(PKCSObjectIdentifiers.id_hmacWithSHA512, Integers.valueOf(9));
    prfCodes.put(NISTObjectIdentifiers.id_hmacWithSHA3_256, Integers.valueOf(11));
    prfCodes.put(NISTObjectIdentifiers.id_hmacWithSHA3_224, Integers.valueOf(10));
    prfCodes.put(NISTObjectIdentifiers.id_hmacWithSHA3_384, Integers.valueOf(12));
    prfCodes.put(NISTObjectIdentifiers.id_hmacWithSHA3_512, Integers.valueOf(13));
  }
  

  public static class AlgParams
    extends BaseAlgorithmParameters
  {
    PBKDF2Params params;
    

    public AlgParams() {}
    

    protected byte[] engineGetEncoded()
    {
      try
      {
        return params.getEncoded("DER");
      }
      catch (IOException e)
      {
        throw new RuntimeException("Oooops! " + e.toString());
      }
    }
    

    protected byte[] engineGetEncoded(String format)
    {
      if (isASN1FormatString(format))
      {
        return engineGetEncoded();
      }
      
      return null;
    }
    

    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec)
      throws InvalidParameterSpecException
    {
      if (paramSpec == PBEParameterSpec.class)
      {
        return new PBEParameterSpec(params.getSalt(), params
          .getIterationCount().intValue());
      }
      
      throw new InvalidParameterSpecException("unknown parameter spec passed to PBKDF2 PBE parameters object.");
    }
    

    protected void engineInit(AlgorithmParameterSpec paramSpec)
      throws InvalidParameterSpecException
    {
      if (!(paramSpec instanceof PBEParameterSpec))
      {
        throw new InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF2 PBE parameters algorithm parameters object");
      }
      
      PBEParameterSpec pbeSpec = (PBEParameterSpec)paramSpec;
      

      params = new PBKDF2Params(pbeSpec.getSalt(), pbeSpec.getIterationCount());
    }
    

    protected void engineInit(byte[] params)
      throws IOException
    {
      this.params = PBKDF2Params.getInstance(ASN1Primitive.fromByteArray(params));
    }
    


    protected void engineInit(byte[] params, String format)
      throws IOException
    {
      if (isASN1FormatString(format))
      {
        engineInit(params);
        return;
      }
      
      throw new IOException("Unknown parameters format in PBKDF2 parameters object");
    }
    
    protected String engineToString()
    {
      return "PBKDF2 Parameters";
    }
  }
  
  public static class BasePBKDF2
    extends BaseSecretKeyFactory
  {
    private int scheme;
    private int defaultDigest;
    
    public BasePBKDF2(String name, int scheme)
    {
      this(name, scheme, 1);
    }
    
    public BasePBKDF2(String name, int scheme, int defaultDigest)
    {
      super(PKCSObjectIdentifiers.id_PBKDF2);
      
      this.scheme = scheme;
      this.defaultDigest = defaultDigest;
    }
    

    protected SecretKey engineGenerateSecret(KeySpec keySpec)
      throws InvalidKeySpecException
    {
      if ((keySpec instanceof PBEKeySpec))
      {
        PBEKeySpec pbeSpec = (PBEKeySpec)keySpec;
        
        if (pbeSpec.getSalt() == null)
        {
          return new PBKDF2Key(((PBEKeySpec)keySpec).getPassword(), scheme == 1 ? PasswordConverter.ASCII : PasswordConverter.UTF8);
        }
        

        if (pbeSpec.getIterationCount() <= 0)
        {

          throw new InvalidKeySpecException("positive iteration count required: " + pbeSpec.getIterationCount());
        }
        
        if (pbeSpec.getKeyLength() <= 0)
        {

          throw new InvalidKeySpecException("positive key length required: " + pbeSpec.getKeyLength());
        }
        
        if (pbeSpec.getPassword().length == 0)
        {
          throw new IllegalArgumentException("password empty");
        }
        
        if ((pbeSpec instanceof PBKDF2KeySpec))
        {
          PBKDF2KeySpec spec = (PBKDF2KeySpec)pbeSpec;
          
          int digest = getDigestCode(spec.getPrf().getAlgorithm());
          int keySize = pbeSpec.getKeyLength();
          int ivSize = -1;
          CipherParameters param = PBE.Util.makePBEMacParameters(pbeSpec, scheme, digest, keySize);
          
          return new BCPBEKey(algName, algOid, scheme, digest, keySize, ivSize, pbeSpec, param);
        }
        

        int digest = defaultDigest;
        int keySize = pbeSpec.getKeyLength();
        int ivSize = -1;
        CipherParameters param = PBE.Util.makePBEMacParameters(pbeSpec, scheme, digest, keySize);
        
        return new BCPBEKey(algName, algOid, scheme, digest, keySize, ivSize, pbeSpec, param);
      }
      

      throw new InvalidKeySpecException("Invalid KeySpec");
    }
    

    private int getDigestCode(ASN1ObjectIdentifier algorithm)
      throws InvalidKeySpecException
    {
      Integer code = (Integer)PBEPBKDF2.prfCodes.get(algorithm);
      if (code != null)
      {
        return code.intValue();
      }
      
      throw new InvalidKeySpecException("Invalid KeySpec: unknown PRF algorithm " + algorithm);
    }
  }
  
  public static class PBKDF2withUTF8
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withUTF8()
    {
      super(5);
    }
  }
  
  public static class PBKDF2withSHA224
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withSHA224()
    {
      super(5, 7);
    }
  }
  
  public static class PBKDF2withSHA256
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withSHA256()
    {
      super(5, 4);
    }
  }
  
  public static class PBKDF2withSHA384
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withSHA384()
    {
      super(5, 8);
    }
  }
  
  public static class PBKDF2withSHA512
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withSHA512()
    {
      super(5, 9);
    }
  }
  
  public static class PBKDF2withGOST3411
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withGOST3411()
    {
      super(5, 6);
    }
  }
  
  public static class PBKDF2withSHA3_224
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withSHA3_224()
    {
      super(5, 10);
    }
  }
  
  public static class PBKDF2withSHA3_256
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withSHA3_256()
    {
      super(5, 11);
    }
  }
  
  public static class PBKDF2withSHA3_384
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withSHA3_384()
    {
      super(5, 12);
    }
  }
  
  public static class PBKDF2withSHA3_512
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2withSHA3_512()
    {
      super(5, 13);
    }
  }
  
  public static class PBKDF2with8BIT
    extends PBEPBKDF2.BasePBKDF2
  {
    public PBKDF2with8BIT()
    {
      super(1);
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = PBEPBKDF2.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("AlgorithmParameters.PBKDF2", PREFIX + "$AlgParams");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.id_PBKDF2, "PBKDF2");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2", PREFIX + "$PBKDF2withUTF8");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1", "PBKDF2");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1ANDUTF8", "PBKDF2");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.id_PBKDF2, "PBKDF2");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHASCII", PREFIX + "$PBKDF2with8BIT");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITH8BIT", "PBKDF2WITHASCII");
      provider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WITHHMACSHA1AND8BIT", "PBKDF2WITHASCII");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA224", PREFIX + "$PBKDF2withSHA224");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA256", PREFIX + "$PBKDF2withSHA256");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA384", PREFIX + "$PBKDF2withSHA384");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA512", PREFIX + "$PBKDF2withSHA512");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA3-224", PREFIX + "$PBKDF2withSHA3_224");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA3-256", PREFIX + "$PBKDF2withSHA3_256");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA3-384", PREFIX + "$PBKDF2withSHA3_384");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACSHA3-512", PREFIX + "$PBKDF2withSHA3_512");
      provider.addAlgorithm("SecretKeyFactory.PBKDF2WITHHMACGOST3411", PREFIX + "$PBKDF2withGOST3411");
    }
  }
}
