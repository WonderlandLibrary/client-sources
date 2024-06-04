package org.spongycastle.jce.provider;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S1ParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;

















































































































































































































































public abstract interface BrokenPBE
{
  public static final int MD5 = 0;
  public static final int SHA1 = 1;
  public static final int RIPEMD160 = 2;
  public static final int PKCS5S1 = 0;
  public static final int PKCS5S2 = 1;
  public static final int PKCS12 = 2;
  public static final int OLD_PKCS12 = 3;
  
  public static class Util
  {
    public Util() {}
    
    private static void setOddParity(byte[] bytes)
    {
      for (int i = 0; i < bytes.length; i++)
      {
        int b = bytes[i];
        bytes[i] = ((byte)(b & 0xFE | b >> 1 ^ b >> 2 ^ b >> 3 ^ b >> 4 ^ b >> 5 ^ b >> 6 ^ b >> 7 ^ 0x1));
      }
    }
    











    private static PBEParametersGenerator makePBEGenerator(int type, int hash)
    {
      if (type == 0) { PBEParametersGenerator generator;
        PBEParametersGenerator generator;
        switch (hash)
        {
        case 0: 
          generator = new PKCS5S1ParametersGenerator(new MD5Digest());
          break;
        case 1: 
          generator = new PKCS5S1ParametersGenerator(new SHA1Digest());
          break;
        default: 
          throw new IllegalStateException("PKCS5 scheme 1 only supports only MD5 and SHA1."); }
      } else {
        PBEParametersGenerator generator;
        if (type == 1)
        {
          generator = new PKCS5S2ParametersGenerator();
        }
        else if (type == 3) { PBEParametersGenerator generator;
          PBEParametersGenerator generator;
          PBEParametersGenerator generator; switch (hash)
          {
          case 0: 
            generator = new OldPKCS12ParametersGenerator(new MD5Digest());
            break;
          case 1: 
            generator = new OldPKCS12ParametersGenerator(new SHA1Digest());
            break;
          case 2: 
            generator = new OldPKCS12ParametersGenerator(new RIPEMD160Digest());
            break;
          default: 
            throw new IllegalStateException("unknown digest scheme for PBE encryption."); }
        } else {
          PBEParametersGenerator generator;
          PBEParametersGenerator generator;
          PBEParametersGenerator generator;
          switch (hash)
          {
          case 0: 
            generator = new PKCS12ParametersGenerator(new MD5Digest());
            break;
          case 1: 
            generator = new PKCS12ParametersGenerator(new SHA1Digest());
            break;
          case 2: 
            generator = new PKCS12ParametersGenerator(new RIPEMD160Digest());
            break;
          default: 
            throw new IllegalStateException("unknown digest scheme for PBE encryption."); }
        }
      }
      PBEParametersGenerator generator;
      return generator;
    }
    











    static CipherParameters makePBEParameters(BCPBEKey pbeKey, AlgorithmParameterSpec spec, int type, int hash, String targetAlgorithm, int keySize, int ivSize)
    {
      if ((spec == null) || (!(spec instanceof PBEParameterSpec)))
      {
        throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
      }
      
      PBEParameterSpec pbeParam = (PBEParameterSpec)spec;
      PBEParametersGenerator generator = makePBEGenerator(type, hash);
      byte[] key = pbeKey.getEncoded();
      

      generator.init(key, pbeParam.getSalt(), pbeParam.getIterationCount());
      CipherParameters param;
      CipherParameters param; if (ivSize != 0)
      {
        param = generator.generateDerivedParameters(keySize, ivSize);
      }
      else
      {
        param = generator.generateDerivedParameters(keySize);
      }
      
      if (targetAlgorithm.startsWith("DES"))
      {
        if ((param instanceof ParametersWithIV))
        {
          KeyParameter kParam = (KeyParameter)((ParametersWithIV)param).getParameters();
          
          setOddParity(kParam.getKey());
        }
        else
        {
          KeyParameter kParam = (KeyParameter)param;
          
          setOddParity(kParam.getKey());
        }
      }
      
      for (int i = 0; i != key.length; i++)
      {
        key[i] = 0;
      }
      
      return param;
    }
    










    static CipherParameters makePBEMacParameters(BCPBEKey pbeKey, AlgorithmParameterSpec spec, int type, int hash, int keySize)
    {
      if ((spec == null) || (!(spec instanceof PBEParameterSpec)))
      {
        throw new IllegalArgumentException("Need a PBEParameter spec with a PBE key.");
      }
      
      PBEParameterSpec pbeParam = (PBEParameterSpec)spec;
      PBEParametersGenerator generator = makePBEGenerator(type, hash);
      byte[] key = pbeKey.getEncoded();
      

      generator.init(key, pbeParam.getSalt(), pbeParam.getIterationCount());
      
      CipherParameters param = generator.generateDerivedMacParameters(keySize);
      
      for (int i = 0; i != key.length; i++)
      {
        key[i] = 0;
      }
      
      return param;
    }
  }
}
