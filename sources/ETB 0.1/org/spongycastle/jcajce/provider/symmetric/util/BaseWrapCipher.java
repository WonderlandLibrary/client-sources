package org.spongycastle.jcajce.provider.symmetric.util;

import java.io.PrintStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.crypto.params.ParametersWithUKM;
import org.spongycastle.jcajce.spec.GOST28147WrapParameterSpec;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.Arrays;





public abstract class BaseWrapCipher
  extends CipherSpi
  implements PBE
{
  private Class[] availableSpecs = { GOST28147WrapParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class };
  







  protected int pbeType = 2;
  protected int pbeHash = 1;
  
  protected int pbeKeySize;
  protected int pbeIvSize;
  protected AlgorithmParameters engineParams = null;
  
  protected Wrapper wrapEngine = null;
  
  private int ivSize;
  
  private byte[] iv;
  private final JcaJceHelper helper = new BCJcaJceHelper();
  


  protected BaseWrapCipher() {}
  

  protected BaseWrapCipher(Wrapper wrapEngine)
  {
    this(wrapEngine, 0);
  }
  


  protected BaseWrapCipher(Wrapper wrapEngine, int ivSize)
  {
    this.wrapEngine = wrapEngine;
    this.ivSize = ivSize;
  }
  
  protected int engineGetBlockSize()
  {
    return 0;
  }
  
  protected byte[] engineGetIV()
  {
    return Arrays.clone(iv);
  }
  

  protected int engineGetKeySize(Key key)
  {
    return key.getEncoded().length * 8;
  }
  

  protected int engineGetOutputSize(int inputLen)
  {
    return -1;
  }
  
  protected AlgorithmParameters engineGetParameters()
  {
    return null;
  }
  
  protected final AlgorithmParameters createParametersInstance(String algorithm)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return helper.createAlgorithmParameters(algorithm);
  }
  

  protected void engineSetMode(String mode)
    throws NoSuchAlgorithmException
  {
    throw new NoSuchAlgorithmException("can't support mode " + mode);
  }
  

  protected void engineSetPadding(String padding)
    throws NoSuchPaddingException
  {
    throw new NoSuchPaddingException("Padding " + padding + " unknown.");
  }
  


  protected void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    CipherParameters param;
    
    CipherParameters param;
    
    if ((key instanceof BCPBEKey))
    {
      BCPBEKey k = (BCPBEKey)key;
      CipherParameters param;
      if ((params instanceof PBEParameterSpec))
      {
        param = PBE.Util.makePBEParameters(k, params, wrapEngine.getAlgorithmName());
      } else { CipherParameters param;
        if (k.getParam() != null)
        {
          param = k.getParam();
        }
        else
        {
          throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
        }
      }
    }
    else {
      param = new KeyParameter(key.getEncoded());
    }
    
    if ((params instanceof IvParameterSpec))
    {
      IvParameterSpec iv = (IvParameterSpec)params;
      param = new ParametersWithIV(param, iv.getIV());
    }
    
    if ((params instanceof GOST28147WrapParameterSpec))
    {
      GOST28147WrapParameterSpec spec = (GOST28147WrapParameterSpec)params;
      
      byte[] sBox = spec.getSBox();
      if (sBox != null)
      {
        param = new ParametersWithSBox(param, sBox);
      }
      param = new ParametersWithUKM(param, spec.getUKM());
    }
    
    if (((param instanceof KeyParameter)) && (ivSize != 0))
    {
      this.iv = new byte[ivSize];
      random.nextBytes(this.iv);
      param = new ParametersWithIV(param, this.iv);
    }
    
    if (random != null)
    {
      param = new ParametersWithRandom(param, random);
    }
    
    switch (opmode)
    {
    case 3: 
      wrapEngine.init(true, param);
      break;
    case 4: 
      wrapEngine.init(false, param);
      break;
    case 1: 
    case 2: 
      throw new IllegalArgumentException("engine only valid for wrapping");
    default: 
      System.out.println("eeek!");
    }
    
  }
  



  protected void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    AlgorithmParameterSpec paramSpec = null;
    
    if (params != null)
    {
      for (int i = 0; i != availableSpecs.length; i++)
      {
        try
        {
          paramSpec = params.getParameterSpec(availableSpecs[i]);
        }
        catch (Exception localException) {}
      }
      




      if (paramSpec == null)
      {
        throw new InvalidAlgorithmParameterException("can't handle parameter " + params.toString());
      }
    }
    
    engineParams = params;
    engineInit(opmode, key, paramSpec, random);
  }
  



  protected void engineInit(int opmode, Key key, SecureRandom random)
    throws InvalidKeyException
  {
    try
    {
      engineInit(opmode, key, (AlgorithmParameterSpec)null, random);
    }
    catch (InvalidAlgorithmParameterException e)
    {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
  



  protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen)
  {
    throw new RuntimeException("not supported for wrapping");
  }
  





  protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
    throws ShortBufferException
  {
    throw new RuntimeException("not supported for wrapping");
  }
  



  protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen)
    throws IllegalBlockSizeException, BadPaddingException
  {
    return null;
  }
  





  protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
    throws IllegalBlockSizeException, BadPaddingException, ShortBufferException
  {
    return 0;
  }
  

  protected byte[] engineWrap(Key key)
    throws IllegalBlockSizeException, InvalidKeyException
  {
    byte[] encoded = key.getEncoded();
    if (encoded == null)
    {
      throw new InvalidKeyException("Cannot wrap key, null encoding.");
    }
    
    try
    {
      if (wrapEngine == null)
      {
        return engineDoFinal(encoded, 0, encoded.length);
      }
      

      return wrapEngine.wrap(encoded, 0, encoded.length);

    }
    catch (BadPaddingException e)
    {
      throw new IllegalBlockSizeException(e.getMessage());
    }
  }
  


  protected Key engineUnwrap(byte[] wrappedKey, String wrappedKeyAlgorithm, int wrappedKeyType)
    throws InvalidKeyException, NoSuchAlgorithmException
  {
    try
    {
      byte[] encoded;
      
      if (wrapEngine == null)
      {
        encoded = engineDoFinal(wrappedKey, 0, wrappedKey.length);
      }
      else
      {
        encoded = wrapEngine.unwrap(wrappedKey, 0, wrappedKey.length);
      }
    }
    catch (InvalidCipherTextException e) {
      byte[] encoded;
      throw new InvalidKeyException(e.getMessage());
    }
    catch (BadPaddingException e)
    {
      throw new InvalidKeyException(e.getMessage());
    }
    catch (IllegalBlockSizeException e2)
    {
      throw new InvalidKeyException(e2.getMessage());
    }
    byte[] encoded;
    if (wrappedKeyType == 3)
    {
      return new SecretKeySpec(encoded, wrappedKeyAlgorithm);
    }
    if ((wrappedKeyAlgorithm.equals("")) && (wrappedKeyType == 2))
    {


      try
      {


        PrivateKeyInfo in = PrivateKeyInfo.getInstance(encoded);
        
        PrivateKey privKey = BouncyCastleProvider.getPrivateKey(in);
        
        if (privKey != null)
        {
          return privKey;
        }
        

        throw new InvalidKeyException("algorithm " + in.getPrivateKeyAlgorithm().getAlgorithm() + " not supported");

      }
      catch (Exception e)
      {
        throw new InvalidKeyException("Invalid key encoding.");
      }
    }
    

    try
    {
      KeyFactory kf = helper.createKeyFactory(wrappedKeyAlgorithm);
      
      if (wrappedKeyType == 1)
      {
        return kf.generatePublic(new X509EncodedKeySpec(encoded));
      }
      if (wrappedKeyType == 2)
      {
        return kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
      }
    }
    catch (NoSuchProviderException e)
    {
      throw new InvalidKeyException("Unknown key type " + e.getMessage());
    }
    catch (InvalidKeySpecException e2)
    {
      throw new InvalidKeyException("Unknown key type " + e2.getMessage());
    }
    
    throw new InvalidKeyException("Unknown key type " + wrappedKeyType);
  }
}
