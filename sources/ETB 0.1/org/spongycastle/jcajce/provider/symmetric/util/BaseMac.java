package org.spongycastle.jcajce.provider.symmetric.util;

import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.crypto.MacSpi;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.RC2Parameters;
import org.spongycastle.crypto.params.SkeinParameters.Builder;
import org.spongycastle.jcajce.PKCS12Key;
import org.spongycastle.jcajce.spec.AEADParameterSpec;
import org.spongycastle.jcajce.spec.SkeinParameterSpec;

public class BaseMac
  extends MacSpi
  implements PBE
{
  private static final Class gcmSpecClass = ClassUtil.loadClass(BaseMac.class, "javax.crypto.spec.GCMParameterSpec");
  
  private Mac macEngine;
  
  private int scheme = 2;
  private int pbeHash = 1;
  private int keySize = 160;
  

  protected BaseMac(Mac macEngine)
  {
    this.macEngine = macEngine;
  }
  




  protected BaseMac(Mac macEngine, int scheme, int pbeHash, int keySize)
  {
    this.macEngine = macEngine;
    this.scheme = scheme;
    this.pbeHash = pbeHash;
    this.keySize = keySize;
  }
  




  protected void engineInit(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    if (key == null)
    {
      throw new InvalidKeyException("key is null"); }
    CipherParameters param;
    CipherParameters param;
    if ((key instanceof PKCS12Key))
    {


      try
      {

        k = (SecretKey)key;
      }
      catch (Exception e) {
        SecretKey k;
        throw new InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
      }
      SecretKey k;
      try
      {
        pbeSpec = (PBEParameterSpec)params;
      }
      catch (Exception e) {
        PBEParameterSpec pbeSpec;
        throw new InvalidAlgorithmParameterException("PKCS12 requires a PBEParameterSpec");
      }
      PBEParameterSpec pbeSpec;
      if (((k instanceof PBEKey)) && (pbeSpec == null))
      {
        pbeSpec = new PBEParameterSpec(((PBEKey)k).getSalt(), ((PBEKey)k).getIterationCount());
      }
      
      int digest = 1;
      int keySize = 160;
      if (macEngine.getAlgorithmName().startsWith("GOST"))
      {
        digest = 6;
        keySize = 256;
      }
      else if ((macEngine instanceof HMac))
      {
        if (!macEngine.getAlgorithmName().startsWith("SHA-1"))
        {
          if (macEngine.getAlgorithmName().startsWith("SHA-224"))
          {
            digest = 7;
            keySize = 224;
          }
          else if (macEngine.getAlgorithmName().startsWith("SHA-256"))
          {
            digest = 4;
            keySize = 256;
          }
          else if (macEngine.getAlgorithmName().startsWith("SHA-384"))
          {
            digest = 8;
            keySize = 384;
          }
          else if (macEngine.getAlgorithmName().startsWith("SHA-512"))
          {
            digest = 9;
            keySize = 512;
          }
          else if (macEngine.getAlgorithmName().startsWith("RIPEMD160"))
          {
            digest = 2;
            keySize = 160;
          }
          else
          {
            throw new InvalidAlgorithmParameterException("no PKCS12 mapping for HMAC: " + macEngine.getAlgorithmName());
          }
        }
      }
      
      param = PBE.Util.makePBEMacParameters(k, 2, digest, keySize, pbeSpec);
    } else { CipherParameters param;
      if ((key instanceof BCPBEKey))
      {
        BCPBEKey k = (BCPBEKey)key;
        CipherParameters param;
        if (k.getParam() != null)
        {
          param = k.getParam();
        } else { CipherParameters param;
          if ((params instanceof PBEParameterSpec))
          {
            param = PBE.Util.makePBEMacParameters(k, params);
          }
          else
          {
            throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
          }
        }
      }
      else {
        if ((params instanceof PBEParameterSpec))
        {
          throw new InvalidAlgorithmParameterException("inappropriate parameter type: " + params.getClass().getName());
        }
        param = new KeyParameter(key.getEncoded());
      } }
    KeyParameter keyParam;
    KeyParameter keyParam;
    if ((param instanceof ParametersWithIV))
    {
      keyParam = (KeyParameter)((ParametersWithIV)param).getParameters();
    }
    else
    {
      keyParam = (KeyParameter)param;
    }
    
    if ((params instanceof AEADParameterSpec))
    {
      AEADParameterSpec aeadSpec = (AEADParameterSpec)params;
      
      param = new AEADParameters(keyParam, aeadSpec.getMacSizeInBits(), aeadSpec.getNonce(), aeadSpec.getAssociatedData());
    }
    else if ((params instanceof IvParameterSpec))
    {
      param = new ParametersWithIV(keyParam, ((IvParameterSpec)params).getIV());
    }
    else if ((params instanceof RC2ParameterSpec))
    {
      param = new ParametersWithIV(new RC2Parameters(keyParam.getKey(), ((RC2ParameterSpec)params).getEffectiveKeyBits()), ((RC2ParameterSpec)params).getIV());
    }
    else if ((params instanceof SkeinParameterSpec))
    {
      param = new SkeinParameters.Builder(copyMap(((SkeinParameterSpec)params).getParameters())).setKey(keyParam.getKey()).build();
    }
    else if (params == null)
    {
      param = new KeyParameter(key.getEncoded());
    }
    else if ((gcmSpecClass != null) && (gcmSpecClass.isAssignableFrom(params.getClass())))
    {
      try
      {
        Method tLen = gcmSpecClass.getDeclaredMethod("getTLen", new Class[0]);
        Method iv = gcmSpecClass.getDeclaredMethod("getIV", new Class[0]);
        
        param = new AEADParameters(keyParam, ((Integer)tLen.invoke(params, new Object[0])).intValue(), (byte[])iv.invoke(params, new Object[0]));
      }
      catch (Exception e)
      {
        throw new InvalidAlgorithmParameterException("Cannot process GCMParameterSpec.");
      }
    }
    else if (!(params instanceof PBEParameterSpec))
    {
      throw new InvalidAlgorithmParameterException("unknown parameter type: " + params.getClass().getName());
    }
    
    try
    {
      macEngine.init(param);
    }
    catch (Exception e)
    {
      throw new InvalidAlgorithmParameterException("cannot initialize MAC: " + e.getMessage());
    }
  }
  
  protected int engineGetMacLength()
  {
    return macEngine.getMacSize();
  }
  
  protected void engineReset()
  {
    macEngine.reset();
  }
  

  protected void engineUpdate(byte input)
  {
    macEngine.update(input);
  }
  



  protected void engineUpdate(byte[] input, int offset, int len)
  {
    macEngine.update(input, offset, len);
  }
  
  protected byte[] engineDoFinal()
  {
    byte[] out = new byte[engineGetMacLength()];
    
    macEngine.doFinal(out, 0);
    
    return out;
  }
  
  private static Hashtable copyMap(Map paramsMap)
  {
    Hashtable newTable = new Hashtable();
    
    Iterator keys = paramsMap.keySet().iterator();
    while (keys.hasNext())
    {
      Object key = keys.next();
      newTable.put(key, paramsMap.get(key));
    }
    
    return newTable;
  }
}
