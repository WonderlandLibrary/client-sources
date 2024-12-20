package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;









public class RFC3394WrapEngine
  implements Wrapper
{
  private BlockCipher engine;
  private boolean wrapCipherMode;
  private KeyParameter param;
  private boolean forWrapping;
  private byte[] iv = { -90, -90, -90, -90, -90, -90, -90, -90 };
  







  public RFC3394WrapEngine(BlockCipher engine)
  {
    this(engine, false);
  }
  






  public RFC3394WrapEngine(BlockCipher engine, boolean useReverseDirection)
  {
    this.engine = engine;
    wrapCipherMode = (!useReverseDirection);
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
    }
    else if ((param instanceof ParametersWithIV))
    {
      iv = ((ParametersWithIV)param).getIV();
      this.param = ((KeyParameter)((ParametersWithIV)param).getParameters());
      if (iv.length != 8)
      {
        throw new IllegalArgumentException("IV not equal to 8");
      }
    }
  }
  
  public String getAlgorithmName()
  {
    return engine.getAlgorithmName();
  }
  



  public byte[] wrap(byte[] in, int inOff, int inLen)
  {
    if (!forWrapping)
    {
      throw new IllegalStateException("not set for wrapping");
    }
    
    int n = inLen / 8;
    
    if (n * 8 != inLen)
    {
      throw new DataLengthException("wrap data must be a multiple of 8 bytes");
    }
    
    byte[] block = new byte[inLen + iv.length];
    byte[] buf = new byte[8 + iv.length];
    
    System.arraycopy(iv, 0, block, 0, iv.length);
    System.arraycopy(in, inOff, block, iv.length, inLen);
    
    engine.init(wrapCipherMode, param);
    
    for (int j = 0; j != 6; j++)
    {
      for (int i = 1; i <= n; i++)
      {
        System.arraycopy(block, 0, buf, 0, iv.length);
        System.arraycopy(block, 8 * i, buf, iv.length, 8);
        engine.processBlock(buf, 0, buf, 0);
        
        int t = n * j + i;
        for (int k = 1; t != 0; k++)
        {
          byte v = (byte)t; int 
          
            tmp213_212 = (iv.length - k); byte[] tmp213_203 = buf;tmp213_203[tmp213_212] = ((byte)(tmp213_203[tmp213_212] ^ v));
          
          t >>>= 8;
        }
        
        System.arraycopy(buf, 0, block, 0, 8);
        System.arraycopy(buf, 8, block, 8 * i, 8);
      }
    }
    
    return block;
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
    
    byte[] block = new byte[inLen - iv.length];
    byte[] a = new byte[iv.length];
    byte[] buf = new byte[8 + iv.length];
    
    System.arraycopy(in, inOff, a, 0, iv.length);
    System.arraycopy(in, inOff + iv.length, block, 0, inLen - iv.length);
    
    engine.init(!wrapCipherMode, param);
    
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
          
            tmp225_224 = (iv.length - k); byte[] tmp225_215 = buf;tmp225_215[tmp225_224] = ((byte)(tmp225_215[tmp225_224] ^ v));
          
          t >>>= 8;
        }
        
        engine.processBlock(buf, 0, buf, 0);
        System.arraycopy(buf, 0, a, 0, 8);
        System.arraycopy(buf, 8, block, 8 * (i - 1), 8);
      }
    }
    
    if (!Arrays.constantTimeAreEqual(a, iv))
    {
      throw new InvalidCipherTextException("checksum failed");
    }
    
    return block;
  }
}
