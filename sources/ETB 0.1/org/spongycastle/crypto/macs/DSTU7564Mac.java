package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.digests.DSTU7564Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;









public class DSTU7564Mac
  implements Mac
{
  private static final int BITS_IN_BYTE = 8;
  private DSTU7564Digest engine;
  private int macSize;
  private byte[] paddedKey;
  private byte[] invertedKey;
  private long inputLength;
  
  public DSTU7564Mac(int macBitSize)
  {
    engine = new DSTU7564Digest(macBitSize);
    macSize = (macBitSize / 8);
    
    paddedKey = null;
    invertedKey = null;
  }
  
  public void init(CipherParameters params)
    throws IllegalArgumentException
  {
    if ((params instanceof KeyParameter))
    {
      byte[] key = ((KeyParameter)params).getKey();
      
      invertedKey = new byte[key.length];
      
      paddedKey = padKey(key);
      
      for (int byteIndex = 0; byteIndex < invertedKey.length; byteIndex++)
      {
        invertedKey[byteIndex] = ((byte)(key[byteIndex] ^ 0xFFFFFFFF));
      }
    }
    else
    {
      throw new IllegalArgumentException("Bad parameter passed");
    }
    
    engine.update(paddedKey, 0, paddedKey.length);
  }
  
  public String getAlgorithmName()
  {
    return "DSTU7564Mac";
  }
  
  public int getMacSize()
  {
    return macSize;
  }
  
  public void update(byte in)
    throws IllegalStateException
  {
    engine.update(in);
    inputLength += 1L;
  }
  
  public void update(byte[] in, int inOff, int len)
    throws DataLengthException, IllegalStateException
  {
    if (in.length - inOff < len)
    {
      throw new DataLengthException("Input buffer too short");
    }
    
    if (paddedKey == null)
    {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    
    engine.update(in, inOff, len);
    inputLength += len;
  }
  
  public int doFinal(byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (paddedKey == null)
    {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    if (out.length - outOff < macSize)
    {
      throw new OutputLengthException("Output buffer too short");
    }
    
    pad();
    
    engine.update(invertedKey, 0, invertedKey.length);
    
    inputLength = 0L;
    
    return engine.doFinal(out, outOff);
  }
  
  public void reset()
  {
    inputLength = 0L;
    engine.reset();
    if (paddedKey != null)
    {
      engine.update(paddedKey, 0, paddedKey.length);
    }
  }
  
  private void pad()
  {
    int extra = engine.getByteLength() - (int)(inputLength % engine.getByteLength());
    if (extra < 13)
    {
      extra += engine.getByteLength();
    }
    
    byte[] padded = new byte[extra];
    
    padded[0] = Byte.MIN_VALUE;
    

    Pack.longToLittleEndian(inputLength * 8L, padded, padded.length - 12);
    
    engine.update(padded, 0, padded.length);
  }
  
  private byte[] padKey(byte[] in)
  {
    int paddedLen = (in.length + engine.getByteLength() - 1) / engine.getByteLength() * engine.getByteLength();
    
    int extra = engine.getByteLength() - in.length % engine.getByteLength();
    if (extra < 13)
    {
      paddedLen += engine.getByteLength();
    }
    
    byte[] padded = new byte[paddedLen];
    
    System.arraycopy(in, 0, padded, 0, in.length);
    
    padded[in.length] = Byte.MIN_VALUE;
    Pack.intToLittleEndian(in.length * 8, padded, padded.length - 12);
    
    return padded;
  }
}
