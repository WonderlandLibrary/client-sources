package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;







public class NullEngine
  implements BlockCipher
{
  private boolean initialised;
  protected static final int DEFAULT_BLOCK_SIZE = 1;
  private final int blockSize;
  
  public NullEngine()
  {
    this(1);
  }
  





  public NullEngine(int blockSize)
  {
    this.blockSize = blockSize;
  }
  



  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    initialised = true;
  }
  



  public String getAlgorithmName()
  {
    return "Null";
  }
  



  public int getBlockSize()
  {
    return blockSize;
  }
  



  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (!initialised)
    {
      throw new IllegalStateException("Null engine not initialised");
    }
    if (inOff + blockSize > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + blockSize > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    for (int i = 0; i < blockSize; i++)
    {
      out[(outOff + i)] = in[(inOff + i)];
    }
    
    return blockSize;
  }
  
  public void reset() {}
}
