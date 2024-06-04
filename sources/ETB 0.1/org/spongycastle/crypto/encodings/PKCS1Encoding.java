package org.spongycastle.crypto.encodings;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;

















public class PKCS1Encoding
  implements AsymmetricBlockCipher
{
  /**
   * @deprecated
   */
  public static final String STRICT_LENGTH_ENABLED_PROPERTY = "org.spongycastle.pkcs1.strict";
  public static final String NOT_STRICT_LENGTH_ENABLED_PROPERTY = "org.spongycastle.pkcs1.not_strict";
  private static final int HEADER_LENGTH = 10;
  private SecureRandom random;
  private AsymmetricBlockCipher engine;
  private boolean forEncryption;
  private boolean forPrivateKey;
  private boolean useStrictLength;
  private int pLen = -1;
  private byte[] fallback = null;
  


  private byte[] blockBuffer;
  



  public PKCS1Encoding(AsymmetricBlockCipher cipher)
  {
    engine = cipher;
    useStrictLength = useStrict();
  }
  








  public PKCS1Encoding(AsymmetricBlockCipher cipher, int pLen)
  {
    engine = cipher;
    useStrictLength = useStrict();
    this.pLen = pLen;
  }
  









  public PKCS1Encoding(AsymmetricBlockCipher cipher, byte[] fallback)
  {
    engine = cipher;
    useStrictLength = useStrict();
    this.fallback = fallback;
    pLen = fallback.length;
  }
  





  private boolean useStrict()
  {
    String strict = (String)AccessController.doPrivileged(new PrivilegedAction()
    {
      public Object run()
      {
        return System.getProperty("org.spongycastle.pkcs1.strict");
      }
    });
    String notStrict = (String)AccessController.doPrivileged(new PrivilegedAction()
    {
      public Object run()
      {
        return System.getProperty("org.spongycastle.pkcs1.not_strict");
      }
    });
    
    if (notStrict != null)
    {
      return !notStrict.equals("true");
    }
    
    return (strict == null) || (strict.equals("true"));
  }
  
  public AsymmetricBlockCipher getUnderlyingCipher()
  {
    return engine;
  }
  

  public void init(boolean forEncryption, CipherParameters param)
  {
    AsymmetricKeyParameter kParam;
    
    AsymmetricKeyParameter kParam;
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      
      random = rParam.getRandom();
      kParam = (AsymmetricKeyParameter)rParam.getParameters();
    }
    else
    {
      kParam = (AsymmetricKeyParameter)param;
      if ((!kParam.isPrivate()) && (forEncryption))
      {
        random = new SecureRandom();
      }
    }
    
    engine.init(forEncryption, param);
    
    forPrivateKey = kParam.isPrivate();
    this.forEncryption = forEncryption;
    blockBuffer = new byte[engine.getOutputBlockSize()];
    
    if ((pLen > 0) && (fallback == null) && (random == null))
    {
      throw new IllegalArgumentException("encoder requires random");
    }
  }
  
  public int getInputBlockSize()
  {
    int baseBlockSize = engine.getInputBlockSize();
    
    if (forEncryption)
    {
      return baseBlockSize - 10;
    }
    

    return baseBlockSize;
  }
  

  public int getOutputBlockSize()
  {
    int baseBlockSize = engine.getOutputBlockSize();
    
    if (forEncryption)
    {
      return baseBlockSize;
    }
    

    return baseBlockSize - 10;
  }
  




  public byte[] processBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (forEncryption)
    {
      return encodeBlock(in, inOff, inLen);
    }
    

    return decodeBlock(in, inOff, inLen);
  }
  




  private byte[] encodeBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (inLen > getInputBlockSize())
    {
      throw new IllegalArgumentException("input data too large");
    }
    
    byte[] block = new byte[engine.getInputBlockSize()];
    
    if (forPrivateKey)
    {
      block[0] = 1;
      
      for (int i = 1; i != block.length - inLen - 1; i++)
      {
        block[i] = -1;
      }
    }
    else
    {
      random.nextBytes(block);
      
      block[0] = 2;
      




      for (int i = 1; i != block.length - inLen - 1; i++)
      {
        while (block[i] == 0)
        {
          block[i] = ((byte)random.nextInt());
        }
      }
    }
    
    block[(block.length - inLen - 1)] = 0;
    System.arraycopy(in, inOff, block, block.length - inLen, inLen);
    
    return engine.processBlock(block, 0, block.length);
  }
  








  private static int checkPkcs1Encoding(byte[] encoded, int pLen)
  {
    int correct = 0;
    


    correct |= encoded[0] ^ 0x2;
    



    int plen = encoded.length - (pLen + 1);
    



    for (int i = 1; i < plen; i++)
    {
      int tmp = encoded[i];
      tmp |= tmp >> 1;
      tmp |= tmp >> 2;
      tmp |= tmp >> 4;
      correct |= (tmp & 0x1) - 1;
    }
    



    correct |= encoded[(encoded.length - (pLen + 1))];
    



    correct |= correct >> 1;
    correct |= correct >> 2;
    correct |= correct >> 4;
    return (correct & 0x1) - 1 ^ 0xFFFFFFFF;
  }
  











  private byte[] decodeBlockOrRandom(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (!forPrivateKey)
    {
      throw new InvalidCipherTextException("sorry, this method is only for decryption, not for signing");
    }
    
    byte[] block = engine.processBlock(in, inOff, inLen);
    byte[] random;
    if (fallback == null)
    {
      byte[] random = new byte[pLen];
      this.random.nextBytes(random);
    }
    else
    {
      random = fallback;
    }
    
    byte[] data = (useStrictLength & block.length != engine.getOutputBlockSize()) ? blockBuffer : block;
    



    int correct = checkPkcs1Encoding(data, pLen);
    




    byte[] result = new byte[pLen];
    for (int i = 0; i < pLen; i++)
    {
      result[i] = ((byte)(data[(i + (data.length - pLen))] & (correct ^ 0xFFFFFFFF) | random[i] & correct));
    }
    
    Arrays.fill(data, (byte)0);
    
    return result;
  }
  










  private byte[] decodeBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (pLen != -1)
    {
      return decodeBlockOrRandom(in, inOff, inLen);
    }
    
    byte[] block = engine.processBlock(in, inOff, inLen);
    boolean incorrectLength = useStrictLength & block.length != engine.getOutputBlockSize();
    byte[] data;
    byte[] data;
    if (block.length < getOutputBlockSize())
    {
      data = blockBuffer;
    }
    else
    {
      data = block;
    }
    
    byte type = data[0];
    boolean badType;
    boolean badType;
    if (forPrivateKey)
    {
      badType = type != 2;
    }
    else
    {
      badType = type != 1;
    }
    



    int start = findStart(type, data);
    
    start++;
    
    if ((badType | start < 10))
    {
      Arrays.fill(data, (byte)0);
      throw new InvalidCipherTextException("block incorrect");
    }
    

    if (incorrectLength)
    {
      Arrays.fill(data, (byte)0);
      throw new InvalidCipherTextException("block incorrect size");
    }
    
    byte[] result = new byte[data.length - start];
    
    System.arraycopy(data, start, result, 0, result.length);
    
    return result;
  }
  
  private int findStart(byte type, byte[] block)
    throws InvalidCipherTextException
  {
    int start = -1;
    boolean padErr = false;
    
    for (int i = 1; i != block.length; i++)
    {
      byte pad = block[i];
      
      if (((pad == 0 ? 1 : 0) & (start < 0 ? 1 : 0)) != 0)
      {
        start = i;
      }
      padErr |= (type == 1 ? 1 : 0) & (start < 0 ? 1 : 0) & (pad != -1 ? 1 : 0);
    }
    
    if (padErr)
    {
      return -1;
    }
    
    return start;
  }
}
