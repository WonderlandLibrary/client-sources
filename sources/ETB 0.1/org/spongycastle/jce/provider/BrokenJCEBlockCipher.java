package org.spongycastle.jce.provider;

import java.io.PrintStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.TwofishEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.CFBBlockCipher;
import org.spongycastle.crypto.modes.CTSBlockCipher;
import org.spongycastle.crypto.modes.OFBBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.RC2Parameters;
import org.spongycastle.crypto.params.RC5Parameters;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.util.Strings;






public class BrokenJCEBlockCipher
  implements BrokenPBE
{
  private Class[] availableSpecs = { IvParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class };
  


  private BufferedBlockCipher cipher;
  


  private ParametersWithIV ivParam;
  

  private int pbeType = 2;
  private int pbeHash = 1;
  
  private int pbeKeySize;
  private int pbeIvSize;
  private int ivLength = 0;
  
  private AlgorithmParameters engineParams = null;
  

  protected BrokenJCEBlockCipher(BlockCipher engine)
  {
    cipher = new PaddedBufferedBlockCipher(engine);
  }
  





  protected BrokenJCEBlockCipher(BlockCipher engine, int pbeType, int pbeHash, int pbeKeySize, int pbeIvSize)
  {
    cipher = new PaddedBufferedBlockCipher(engine);
    
    this.pbeType = pbeType;
    this.pbeHash = pbeHash;
    this.pbeKeySize = pbeKeySize;
    this.pbeIvSize = pbeIvSize;
  }
  
  protected int engineGetBlockSize()
  {
    return cipher.getBlockSize();
  }
  
  protected byte[] engineGetIV()
  {
    return ivParam != null ? ivParam.getIV() : null;
  }
  

  protected int engineGetKeySize(Key key)
  {
    return key.getEncoded().length;
  }
  

  protected int engineGetOutputSize(int inputLen)
  {
    return cipher.getOutputSize(inputLen);
  }
  
  protected AlgorithmParameters engineGetParameters()
  {
    if (engineParams == null)
    {
      if (ivParam != null)
      {
        String name = cipher.getUnderlyingCipher().getAlgorithmName();
        
        if (name.indexOf('/') >= 0)
        {
          name = name.substring(0, name.indexOf('/'));
        }
        
        try
        {
          engineParams = AlgorithmParameters.getInstance(name, "SC");
          engineParams.init(ivParam.getIV());
        }
        catch (Exception e)
        {
          throw new RuntimeException(e.toString());
        }
      }
    }
    
    return engineParams;
  }
  

  protected void engineSetMode(String mode)
  {
    String modeName = Strings.toUpperCase(mode);
    
    if (modeName.equals("ECB"))
    {
      ivLength = 0;
      cipher = new PaddedBufferedBlockCipher(cipher.getUnderlyingCipher());
    }
    else if (modeName.equals("CBC"))
    {
      ivLength = cipher.getUnderlyingCipher().getBlockSize();
      
      cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(cipher.getUnderlyingCipher()));
    }
    else if (modeName.startsWith("OFB"))
    {
      ivLength = cipher.getUnderlyingCipher().getBlockSize();
      if (modeName.length() != 3)
      {
        int wordSize = Integer.parseInt(modeName.substring(3));
        

        cipher = new PaddedBufferedBlockCipher(new OFBBlockCipher(cipher.getUnderlyingCipher(), wordSize));

      }
      else
      {
        cipher = new PaddedBufferedBlockCipher(new OFBBlockCipher(cipher.getUnderlyingCipher(), 8 * cipher.getBlockSize()));
      }
    }
    else if (modeName.startsWith("CFB"))
    {
      ivLength = cipher.getUnderlyingCipher().getBlockSize();
      if (modeName.length() != 3)
      {
        int wordSize = Integer.parseInt(modeName.substring(3));
        

        cipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(cipher.getUnderlyingCipher(), wordSize));

      }
      else
      {
        cipher = new PaddedBufferedBlockCipher(new CFBBlockCipher(cipher.getUnderlyingCipher(), 8 * cipher.getBlockSize()));
      }
    }
    else
    {
      throw new IllegalArgumentException("can't support mode " + mode);
    }
  }
  

  protected void engineSetPadding(String padding)
    throws NoSuchPaddingException
  {
    String paddingName = Strings.toUpperCase(padding);
    
    if (paddingName.equals("NOPADDING"))
    {
      cipher = new BufferedBlockCipher(cipher.getUnderlyingCipher());
    }
    else if ((paddingName.equals("PKCS5PADDING")) || (paddingName.equals("PKCS7PADDING")) || (paddingName.equals("ISO10126PADDING")))
    {
      cipher = new PaddedBufferedBlockCipher(cipher.getUnderlyingCipher());
    }
    else if (paddingName.equals("WITHCTS"))
    {
      cipher = new CTSBlockCipher(cipher.getUnderlyingCipher());
    }
    else
    {
      throw new NoSuchPaddingException("Padding " + padding + " unknown.");
    }
  }
  









  protected void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    if ((key instanceof BCPBEKey))
    {
      CipherParameters param = BrokenPBE.Util.makePBEParameters((BCPBEKey)key, params, pbeType, pbeHash, cipher
        .getUnderlyingCipher().getAlgorithmName(), pbeKeySize, pbeIvSize);
      
      if (pbeIvSize != 0)
      {
        ivParam = ((ParametersWithIV)param); }
    } else {
      CipherParameters param;
      if (params == null)
      {
        param = new KeyParameter(key.getEncoded());
      } else { CipherParameters param;
        if ((params instanceof IvParameterSpec))
        {
          if (ivLength != 0)
          {
            CipherParameters param = new ParametersWithIV(new KeyParameter(key.getEncoded()), ((IvParameterSpec)params).getIV());
            ivParam = ((ParametersWithIV)param);
          }
          else
          {
            param = new KeyParameter(key.getEncoded());
          }
        }
        else if ((params instanceof RC2ParameterSpec))
        {
          RC2ParameterSpec rc2Param = (RC2ParameterSpec)params;
          
          CipherParameters param = new RC2Parameters(key.getEncoded(), ((RC2ParameterSpec)params).getEffectiveKeyBits());
          
          if ((rc2Param.getIV() != null) && (ivLength != 0))
          {
            param = new ParametersWithIV(param, rc2Param.getIV());
            ivParam = ((ParametersWithIV)param);
          }
        }
        else if ((params instanceof RC5ParameterSpec))
        {
          RC5ParameterSpec rc5Param = (RC5ParameterSpec)params;
          
          CipherParameters param = new RC5Parameters(key.getEncoded(), ((RC5ParameterSpec)params).getRounds());
          if (rc5Param.getWordSize() != 32)
          {
            throw new IllegalArgumentException("can only accept RC5 word size 32 (at the moment...)");
          }
          if ((rc5Param.getIV() != null) && (ivLength != 0))
          {
            param = new ParametersWithIV(param, rc5Param.getIV());
            ivParam = ((ParametersWithIV)param);
          }
        }
        else
        {
          throw new InvalidAlgorithmParameterException("unknown parameter type.");
        } } }
    CipherParameters param;
    if ((ivLength != 0) && (!(param instanceof ParametersWithIV)))
    {
      if (random == null)
      {
        random = new SecureRandom();
      }
      
      if ((opmode == 1) || (opmode == 3))
      {
        byte[] iv = new byte[ivLength];
        
        random.nextBytes(iv);
        param = new ParametersWithIV(param, iv);
        ivParam = ((ParametersWithIV)param);
      }
      else
      {
        throw new InvalidAlgorithmParameterException("no IV set when one expected");
      }
    }
    
    switch (opmode)
    {
    case 1: 
    case 3: 
      cipher.init(true, param);
      break;
    case 2: 
    case 4: 
      cipher.init(false, param);
      break;
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
        catch (Exception e) {}
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
    int length = cipher.getUpdateOutputSize(inputLen);
    
    if (length > 0)
    {
      byte[] out = new byte[length];
      
      cipher.processBytes(input, inputOffset, inputLen, out, 0);
      return out;
    }
    
    cipher.processBytes(input, inputOffset, inputLen, null, 0);
    
    return null;
  }
  





  protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
  {
    return cipher.processBytes(input, inputOffset, inputLen, output, outputOffset);
  }
  



  protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen)
    throws IllegalBlockSizeException, BadPaddingException
  {
    int len = 0;
    byte[] tmp = new byte[engineGetOutputSize(inputLen)];
    
    if (inputLen != 0)
    {
      len = cipher.processBytes(input, inputOffset, inputLen, tmp, 0);
    }
    
    try
    {
      len += cipher.doFinal(tmp, len);
    }
    catch (DataLengthException e)
    {
      throw new IllegalBlockSizeException(e.getMessage());
    }
    catch (InvalidCipherTextException e)
    {
      throw new BadPaddingException(e.getMessage());
    }
    
    byte[] out = new byte[len];
    
    System.arraycopy(tmp, 0, out, 0, len);
    
    return out;
  }
  





  protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
    throws IllegalBlockSizeException, BadPaddingException
  {
    int len = 0;
    
    if (inputLen != 0)
    {
      len = cipher.processBytes(input, inputOffset, inputLen, output, outputOffset);
    }
    
    try
    {
      return len + cipher.doFinal(output, outputOffset + len);
    }
    catch (DataLengthException e)
    {
      throw new IllegalBlockSizeException(e.getMessage());
    }
    catch (InvalidCipherTextException e)
    {
      throw new BadPaddingException(e.getMessage());
    }
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
      return engineDoFinal(encoded, 0, encoded.length);
    }
    catch (BadPaddingException e)
    {
      throw new IllegalBlockSizeException(e.getMessage());
    }
  }
  



  protected Key engineUnwrap(byte[] wrappedKey, String wrappedKeyAlgorithm, int wrappedKeyType)
    throws InvalidKeyException
  {
    byte[] encoded = null;
    try
    {
      encoded = engineDoFinal(wrappedKey, 0, wrappedKey.length);
    }
    catch (BadPaddingException e)
    {
      throw new InvalidKeyException(e.getMessage());
    }
    catch (IllegalBlockSizeException e2)
    {
      throw new InvalidKeyException(e2.getMessage());
    }
    
    if (wrappedKeyType == 3)
    {
      return new SecretKeySpec(encoded, wrappedKeyAlgorithm);
    }
    

    try
    {
      KeyFactory kf = KeyFactory.getInstance(wrappedKeyAlgorithm, "SC");
      
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
    catch (NoSuchAlgorithmException e)
    {
      throw new InvalidKeyException("Unknown key type " + e.getMessage());
    }
    catch (InvalidKeySpecException e2)
    {
      throw new InvalidKeyException("Unknown key type " + e2.getMessage());
    }
    
    throw new InvalidKeyException("Unknown key type " + wrappedKeyType);
  }
  








  public static class BrokePBEWithMD5AndDES
    extends BrokenJCEBlockCipher
  {
    public BrokePBEWithMD5AndDES()
    {
      super(0, 0, 64, 64);
    }
  }
  



  public static class BrokePBEWithSHA1AndDES
    extends BrokenJCEBlockCipher
  {
    public BrokePBEWithSHA1AndDES()
    {
      super(0, 1, 64, 64);
    }
  }
  



  public static class BrokePBEWithSHAAndDES3Key
    extends BrokenJCEBlockCipher
  {
    public BrokePBEWithSHAAndDES3Key()
    {
      super(2, 1, 192, 64);
    }
  }
  



  public static class OldPBEWithSHAAndDES3Key
    extends BrokenJCEBlockCipher
  {
    public OldPBEWithSHAAndDES3Key()
    {
      super(3, 1, 192, 64);
    }
  }
  



  public static class BrokePBEWithSHAAndDES2Key
    extends BrokenJCEBlockCipher
  {
    public BrokePBEWithSHAAndDES2Key()
    {
      super(2, 1, 128, 64);
    }
  }
  



  public static class OldPBEWithSHAAndTwofish
    extends BrokenJCEBlockCipher
  {
    public OldPBEWithSHAAndTwofish()
    {
      super(3, 1, 256, 128);
    }
  }
}
