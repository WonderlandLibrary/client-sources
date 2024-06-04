package org.spongycastle.jcajce.provider.symmetric;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST28147Parameters;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.CryptoProWrapEngine;
import org.spongycastle.crypto.engines.GOST28147Engine;
import org.spongycastle.crypto.engines.GOST28147WrapEngine;
import org.spongycastle.crypto.macs.GOST28147Mac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.GCFBBlockCipher;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.spec.GOST28147ParameterSpec;



public final class GOST28147
{
  private static Map<ASN1ObjectIdentifier, String> oidMappings = new HashMap();
  private static Map<String, ASN1ObjectIdentifier> nameMappings = new HashMap();
  
  private GOST28147() {}
  
  static { oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_TestParamSet, "E-TEST");
    oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_A_ParamSet, "E-A");
    oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_B_ParamSet, "E-B");
    oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_C_ParamSet, "E-C");
    oidMappings.put(CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_D_ParamSet, "E-D");
    
    nameMappings.put("E-A", CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_A_ParamSet);
    nameMappings.put("E-B", CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_B_ParamSet);
    nameMappings.put("E-C", CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_C_ParamSet);
    nameMappings.put("E-D", CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_D_ParamSet);
  }
  




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
  
  public static class GCFB
    extends BaseBlockCipher
  {
    public GCFB()
    {
      super(64);
    }
  }
  
  public static class GostWrap
    extends BaseWrapCipher
  {
    public GostWrap()
    {
      super();
    }
  }
  
  public static class CryptoProWrap
    extends BaseWrapCipher
  {
    public CryptoProWrap()
    {
      super();
    }
  }
  



  public static class Mac
    extends BaseMac
  {
    public Mac()
    {
      super();
    }
  }
  
  public static class KeyGen
    extends BaseKeyGenerator
  {
    public KeyGen()
    {
      this(256);
    }
    
    public KeyGen(int keySize)
    {
      super(keySize, new CipherKeyGenerator());
    }
  }
  
  public static class AlgParamGen
    extends BaseAlgorithmParameterGenerator
  {
    byte[] iv = new byte[8];
    byte[] sBox = GOST28147Engine.getSBox("E-A");
    
    public AlgParamGen() {}
    
    protected void engineInit(AlgorithmParameterSpec genParamSpec, SecureRandom random)
      throws InvalidAlgorithmParameterException
    {
      if ((genParamSpec instanceof GOST28147ParameterSpec))
      {
        sBox = ((GOST28147ParameterSpec)genParamSpec).getSBox();
      }
      else
      {
        throw new InvalidAlgorithmParameterException("parameter spec not supported");
      }
    }
    
    protected AlgorithmParameters engineGenerateParameters()
    {
      if (random == null)
      {
        random = new SecureRandom();
      }
      
      random.nextBytes(iv);
      


      try
      {
        AlgorithmParameters params = createParametersInstance("GOST28147");
        params.init(new GOST28147ParameterSpec(sBox, iv));
      }
      catch (Exception e)
      {
        throw new RuntimeException(e.getMessage());
      }
      AlgorithmParameters params;
      return params;
    }
  }
  
  public static abstract class BaseAlgParams
    extends BaseAlgorithmParameters
  {
    private ASN1ObjectIdentifier sBox = CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_A_ParamSet;
    private byte[] iv;
    
    public BaseAlgParams() {}
    
    protected final void engineInit(byte[] encoding) throws IOException {
      engineInit(encoding, "ASN.1");
    }
    
    protected final byte[] engineGetEncoded()
      throws IOException
    {
      return engineGetEncoded("ASN.1");
    }
    

    protected final byte[] engineGetEncoded(String format)
      throws IOException
    {
      if (isASN1FormatString(format))
      {
        return localGetEncoded();
      }
      
      throw new IOException("Unknown parameter format: " + format);
    }
    


    protected final void engineInit(byte[] params, String format)
      throws IOException
    {
      if (params == null)
      {
        throw new NullPointerException("Encoded parameters cannot be null");
      }
      
      if (isASN1FormatString(format))
      {
        try
        {
          localInit(params);
        }
        catch (IOException e)
        {
          throw e;
        }
        catch (Exception e)
        {
          throw new IOException("Parameter parsing failed: " + e.getMessage());
        }
        
      }
      else {
        throw new IOException("Unknown parameter format: " + format);
      }
    }
    
    protected byte[] localGetEncoded()
      throws IOException
    {
      return new GOST28147Parameters(iv, sBox).getEncoded();
    }
    

    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec)
      throws InvalidParameterSpecException
    {
      if (paramSpec == IvParameterSpec.class)
      {
        return new IvParameterSpec(iv);
      }
      
      if ((paramSpec == GOST28147ParameterSpec.class) || (paramSpec == AlgorithmParameterSpec.class))
      {
        return new GOST28147ParameterSpec(sBox, iv);
      }
      
      throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: " + paramSpec.getName());
    }
    

    protected void engineInit(AlgorithmParameterSpec paramSpec)
      throws InvalidParameterSpecException
    {
      if ((paramSpec instanceof IvParameterSpec))
      {
        iv = ((IvParameterSpec)paramSpec).getIV();
      }
      else if ((paramSpec instanceof GOST28147ParameterSpec))
      {
        iv = ((GOST28147ParameterSpec)paramSpec).getIV();
        try
        {
          sBox = getSBoxOID(((GOST28147ParameterSpec)paramSpec).getSBox());
        }
        catch (IllegalArgumentException e)
        {
          throw new InvalidParameterSpecException(e.getMessage());
        }
      }
      else
      {
        throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
      }
    }
    
    protected static ASN1ObjectIdentifier getSBoxOID(String name)
    {
      ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)GOST28147.nameMappings.get(name);
      
      if (oid == null)
      {
        throw new IllegalArgumentException("Unknown SBOX name: " + name);
      }
      
      return oid;
    }
    
    protected static ASN1ObjectIdentifier getSBoxOID(byte[] sBox)
    {
      return getSBoxOID(GOST28147Engine.getSBoxName(sBox));
    }
    
    abstract void localInit(byte[] paramArrayOfByte) throws IOException;
  }
  
  public static class AlgParams
    extends GOST28147.BaseAlgParams
  {
    private ASN1ObjectIdentifier sBox = CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_A_ParamSet;
    private byte[] iv;
    
    public AlgParams() {}
    
    protected byte[] localGetEncoded() throws IOException {
      return new GOST28147Parameters(iv, sBox).getEncoded();
    }
    

    protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec)
      throws InvalidParameterSpecException
    {
      if (paramSpec == IvParameterSpec.class)
      {
        return new IvParameterSpec(iv);
      }
      
      if ((paramSpec == GOST28147ParameterSpec.class) || (paramSpec == AlgorithmParameterSpec.class))
      {
        return new GOST28147ParameterSpec(sBox, iv);
      }
      
      throw new InvalidParameterSpecException("AlgorithmParameterSpec not recognized: " + paramSpec.getName());
    }
    

    protected void engineInit(AlgorithmParameterSpec paramSpec)
      throws InvalidParameterSpecException
    {
      if ((paramSpec instanceof IvParameterSpec))
      {
        iv = ((IvParameterSpec)paramSpec).getIV();
      }
      else if ((paramSpec instanceof GOST28147ParameterSpec))
      {
        iv = ((GOST28147ParameterSpec)paramSpec).getIV();
        try
        {
          sBox = getSBoxOID(((GOST28147ParameterSpec)paramSpec).getSBox());
        }
        catch (IllegalArgumentException e)
        {
          throw new InvalidParameterSpecException(e.getMessage());
        }
      }
      else
      {
        throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
      }
    }
    

    protected void localInit(byte[] params)
      throws IOException
    {
      ASN1Primitive asn1Params = ASN1Primitive.fromByteArray(params);
      
      if ((asn1Params instanceof ASN1OctetString))
      {
        iv = ASN1OctetString.getInstance(asn1Params).getOctets();
      }
      else if ((asn1Params instanceof ASN1Sequence))
      {
        GOST28147Parameters gParams = GOST28147Parameters.getInstance(asn1Params);
        
        sBox = gParams.getEncryptionParamSet();
        iv = gParams.getIV();
      }
      else
      {
        throw new IOException("Unable to recognize parameters");
      }
    }
    
    protected String engineToString()
    {
      return "GOST 28147 IV Parameters";
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = GOST28147.class.getName();
    

    public Mappings() {}
    

    public void configure(ConfigurableProvider provider)
    {
      provider.addAlgorithm("Cipher.GOST28147", PREFIX + "$ECB");
      provider.addAlgorithm("Alg.Alias.Cipher.GOST", "GOST28147");
      provider.addAlgorithm("Alg.Alias.Cipher.GOST-28147", "GOST28147");
      provider.addAlgorithm("Cipher." + CryptoProObjectIdentifiers.gostR28147_gcfb, PREFIX + "$GCFB");
      
      provider.addAlgorithm("KeyGenerator.GOST28147", PREFIX + "$KeyGen");
      provider.addAlgorithm("Alg.Alias.KeyGenerator.GOST", "GOST28147");
      provider.addAlgorithm("Alg.Alias.KeyGenerator.GOST-28147", "GOST28147");
      provider.addAlgorithm("Alg.Alias.KeyGenerator." + CryptoProObjectIdentifiers.gostR28147_gcfb, "GOST28147");
      
      provider.addAlgorithm("AlgorithmParameters.GOST28147", PREFIX + "$AlgParams");
      provider.addAlgorithm("AlgorithmParameterGenerator.GOST28147", PREFIX + "$AlgParamGen");
      
      provider.addAlgorithm("Alg.Alias.AlgorithmParameters." + CryptoProObjectIdentifiers.gostR28147_gcfb, "GOST28147");
      provider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + CryptoProObjectIdentifiers.gostR28147_gcfb, "GOST28147");
      
      provider.addAlgorithm("Cipher." + CryptoProObjectIdentifiers.id_Gost28147_89_CryptoPro_KeyWrap, PREFIX + "$CryptoProWrap");
      provider.addAlgorithm("Cipher." + CryptoProObjectIdentifiers.id_Gost28147_89_None_KeyWrap, PREFIX + "$GostWrap");
      
      provider.addAlgorithm("Mac.GOST28147MAC", PREFIX + "$Mac");
      provider.addAlgorithm("Alg.Alias.Mac.GOST28147", "GOST28147MAC");
    }
  }
}
