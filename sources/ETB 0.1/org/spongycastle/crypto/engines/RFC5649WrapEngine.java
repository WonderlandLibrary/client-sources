package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;











public class RFC5649WrapEngine
  implements Wrapper
{
  private BlockCipher engine;
  private KeyParameter param;
  private boolean forWrapping;
  private byte[] highOrderIV = { -90, 89, 89, -90 };
  private byte[] preIV = highOrderIV;
  
  private byte[] extractedAIV = null;
  
  public RFC5649WrapEngine(BlockCipher engine)
  {
    this.engine = engine;
  }
  
  public void init(boolean forWrapping, CipherParameters param)
  {
    this.forWrapping = forWrapping;
    
    if ((param instanceof ParametersWithRandom))
    {
      param = ((ParametersWithRandom)param).getParameters();
    }
    
    if ((param instanceof KeyParameter))
    {
      this.param = ((KeyParameter)param);
      preIV = highOrderIV;
    }
    else if ((param instanceof ParametersWithIV))
    {
      preIV = ((ParametersWithIV)param).getIV();
      this.param = ((KeyParameter)((ParametersWithIV)param).getParameters());
      if (preIV.length != 4)
      {
        throw new IllegalArgumentException("IV length not equal to 4");
      }
    }
  }
  
  public String getAlgorithmName()
  {
    return engine.getAlgorithmName();
  }
  







  private byte[] padPlaintext(byte[] plaintext)
  {
    int plaintextLength = plaintext.length;
    int numOfZerosToAppend = (8 - plaintextLength % 8) % 8;
    byte[] paddedPlaintext = new byte[plaintextLength + numOfZerosToAppend];
    System.arraycopy(plaintext, 0, paddedPlaintext, 0, plaintextLength);
    if (numOfZerosToAppend != 0)
    {


      byte[] zeros = new byte[numOfZerosToAppend];
      System.arraycopy(zeros, 0, paddedPlaintext, plaintextLength, numOfZerosToAppend);
    }
    return paddedPlaintext;
  }
  
  public byte[] wrap(byte[] in, int inOff, int inLen)
  {
    if (!forWrapping)
    {
      throw new IllegalStateException("not set for wrapping");
    }
    byte[] iv = new byte[8];
    

    byte[] mli = Pack.intToBigEndian(inLen);
    
    System.arraycopy(preIV, 0, iv, 0, preIV.length);
    
    System.arraycopy(mli, 0, iv, preIV.length, mli.length);
    

    byte[] relevantPlaintext = new byte[inLen];
    System.arraycopy(in, inOff, relevantPlaintext, 0, inLen);
    byte[] paddedPlaintext = padPlaintext(relevantPlaintext);
    
    if (paddedPlaintext.length == 8)
    {




      byte[] paddedPlainTextWithIV = new byte[paddedPlaintext.length + iv.length];
      System.arraycopy(iv, 0, paddedPlainTextWithIV, 0, iv.length);
      System.arraycopy(paddedPlaintext, 0, paddedPlainTextWithIV, iv.length, paddedPlaintext.length);
      
      engine.init(true, param);
      for (int i = 0; i < paddedPlainTextWithIV.length; i += engine.getBlockSize())
      {
        engine.processBlock(paddedPlainTextWithIV, i, paddedPlainTextWithIV, i);
      }
      
      return paddedPlainTextWithIV;
    }
    



    Wrapper wrapper = new RFC3394WrapEngine(engine);
    ParametersWithIV paramsWithIV = new ParametersWithIV(param, iv);
    wrapper.init(true, paramsWithIV);
    return wrapper.wrap(paddedPlaintext, 0, paddedPlaintext.length);
  }
  


  public byte[] unwrap(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (forWrapping)
    {
      throw new IllegalStateException("not set for unwrapping");
    }
    
    int n = inLen / 8;
    
    if (n * 8 != inLen)
    {
      throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
    }
    
    if (n == 1)
    {
      throw new InvalidCipherTextException("unwrap data must be at least 16 bytes");
    }
    
    byte[] relevantCiphertext = new byte[inLen];
    System.arraycopy(in, inOff, relevantCiphertext, 0, inLen);
    byte[] decrypted = new byte[inLen];
    
    byte[] paddedPlaintext;
    if (n == 2)
    {


      engine.init(false, param);
      for (int i = 0; i < relevantCiphertext.length; i += engine.getBlockSize())
      {
        engine.processBlock(relevantCiphertext, i, decrypted, i);
      }
      

      extractedAIV = new byte[8];
      System.arraycopy(decrypted, 0, extractedAIV, 0, extractedAIV.length);
      byte[] paddedPlaintext = new byte[decrypted.length - extractedAIV.length];
      System.arraycopy(decrypted, extractedAIV.length, paddedPlaintext, 0, paddedPlaintext.length);

    }
    else
    {
      decrypted = rfc3394UnwrapNoIvCheck(in, inOff, inLen);
      paddedPlaintext = decrypted;
    }
    

    byte[] extractedHighOrderAIV = new byte[4];
    byte[] mliBytes = new byte[4];
    System.arraycopy(extractedAIV, 0, extractedHighOrderAIV, 0, extractedHighOrderAIV.length);
    System.arraycopy(extractedAIV, extractedHighOrderAIV.length, mliBytes, 0, mliBytes.length);
    int mli = Pack.bigEndianToInt(mliBytes, 0);
    

    boolean isValid = true;
    

    if (!Arrays.constantTimeAreEqual(extractedHighOrderAIV, preIV))
    {
      isValid = false;
    }
    

    int upperBound = paddedPlaintext.length;
    int lowerBound = upperBound - 8;
    if (mli <= lowerBound)
    {
      isValid = false;
    }
    if (mli > upperBound)
    {
      isValid = false;
    }
    

    int expectedZeros = upperBound - mli;
    if (expectedZeros >= paddedPlaintext.length)
    {
      isValid = false;
      expectedZeros = paddedPlaintext.length;
    }
    
    byte[] zeros = new byte[expectedZeros];
    byte[] pad = new byte[expectedZeros];
    System.arraycopy(paddedPlaintext, paddedPlaintext.length - expectedZeros, pad, 0, expectedZeros);
    if (!Arrays.constantTimeAreEqual(pad, zeros))
    {
      isValid = false;
    }
    
    if (!isValid)
    {
      throw new InvalidCipherTextException("checksum failed");
    }
    

    byte[] plaintext = new byte[mli];
    System.arraycopy(paddedPlaintext, 0, plaintext, 0, plaintext.length);
    
    return plaintext;
  }
  










  private byte[] rfc3394UnwrapNoIvCheck(byte[] in, int inOff, int inLen)
  {
    byte[] iv = new byte[8];
    byte[] block = new byte[inLen - iv.length];
    byte[] a = new byte[iv.length];
    byte[] buf = new byte[8 + iv.length];
    
    System.arraycopy(in, inOff, a, 0, iv.length);
    System.arraycopy(in, inOff + iv.length, block, 0, inLen - iv.length);
    
    engine.init(false, param);
    
    int n = inLen / 8;
    n -= 1;
    
    for (int j = 5; j >= 0; j--)
    {
      for (int i = n; i >= 1; i--)
      {
        System.arraycopy(a, 0, buf, 0, iv.length);
        System.arraycopy(block, 8 * (i - 1), buf, iv.length, 8);
        
        int t = n * j + i;
        for (int k = 1; t != 0; k++)
        {
          byte v = (byte)t; int 
          
            tmp166_165 = (iv.length - k); byte[] tmp166_158 = buf;tmp166_158[tmp166_165] = ((byte)(tmp166_158[tmp166_165] ^ v));
          
          t >>>= 8;
        }
        
        engine.processBlock(buf, 0, buf, 0);
        System.arraycopy(buf, 0, a, 0, 8);
        System.arraycopy(buf, 8, block, 8 * (i - 1), 8);
      }
    }
    

    extractedAIV = a;
    
    return block;
  }
}
