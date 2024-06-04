package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Pack;






public class KXTSBlockCipher
  extends BufferedBlockCipher
{
  private static final long RED_POLY_128 = 135L;
  private static final long RED_POLY_256 = 1061L;
  private static final long RED_POLY_512 = 293L;
  private final int blockSize;
  private final long reductionPolynomial;
  private final long[] tw_init;
  private final long[] tw_current;
  private int counter;
  
  protected static long getReductionPolynomial(int blockSize)
  {
    switch (blockSize)
    {
    case 16: 
      return 135L;
    case 32: 
      return 1061L;
    case 64: 
      return 293L;
    }
    throw new IllegalArgumentException("Only 128, 256, and 512 -bit block sizes supported");
  }
  







  public KXTSBlockCipher(BlockCipher cipher)
  {
    this.cipher = cipher;
    
    blockSize = cipher.getBlockSize();
    reductionPolynomial = getReductionPolynomial(blockSize);
    tw_init = new long[blockSize >>> 3];
    tw_current = new long[blockSize >>> 3];
    counter = -1;
  }
  
  public int getOutputSize(int length)
  {
    return length;
  }
  
  public int getUpdateOutputSize(int len)
  {
    return len;
  }
  
  public void init(boolean forEncryption, CipherParameters parameters)
  {
    if (!(parameters instanceof ParametersWithIV))
    {
      throw new IllegalArgumentException("Invalid parameters passed");
    }
    
    ParametersWithIV ivParam = (ParametersWithIV)parameters;
    parameters = ivParam.getParameters();
    
    byte[] iv = ivParam.getIV();
    





    if (iv.length != blockSize)
    {
      throw new IllegalArgumentException("Currently only support IVs of exactly one block");
    }
    
    byte[] tweak = new byte[blockSize];
    System.arraycopy(iv, 0, tweak, 0, blockSize);
    
    cipher.init(true, parameters);
    cipher.processBlock(tweak, 0, tweak, 0);
    
    cipher.init(forEncryption, parameters);
    Pack.littleEndianToLong(tweak, 0, tw_init);
    System.arraycopy(tw_init, 0, tw_current, 0, tw_init.length);
    counter = 0;
  }
  



  public int processByte(byte in, byte[] out, int outOff)
  {
    throw new IllegalStateException("unsupported operation");
  }
  
  public int processBytes(byte[] input, int inOff, int len, byte[] output, int outOff)
  {
    if (input.length - inOff < len)
    {
      throw new DataLengthException("Input buffer too short");
    }
    if (output.length - inOff < len)
    {
      throw new OutputLengthException("Output buffer too short");
    }
    if (len % blockSize != 0)
    {
      throw new IllegalArgumentException("Partial blocks not supported");
    }
    
    for (int pos = 0; pos < len; pos += blockSize)
    {
      processBlock(input, inOff + pos, output, outOff + pos);
    }
    
    return len;
  }
  



  private void processBlock(byte[] input, int inOff, byte[] output, int outOff)
  {
    if (counter == -1)
    {
      throw new IllegalStateException("Attempt to process too many blocks");
    }
    
    counter += 1;
    



    GF_double(reductionPolynomial, tw_current);
    
    byte[] tweak = new byte[blockSize];
    Pack.longToLittleEndian(tw_current, tweak, 0);
    
    byte[] buffer = new byte[blockSize];
    System.arraycopy(tweak, 0, buffer, 0, blockSize);
    
    for (int i = 0; i < blockSize; i++)
    {
      int tmp94_92 = i; byte[] tmp94_90 = buffer;tmp94_90[tmp94_92] = ((byte)(tmp94_90[tmp94_92] ^ input[(inOff + i)]));
    }
    
    cipher.processBlock(buffer, 0, buffer, 0);
    
    for (int i = 0; i < blockSize; i++)
    {
      output[(outOff + i)] = ((byte)(buffer[i] ^ tweak[i]));
    }
  }
  
  public int doFinal(byte[] output, int outOff)
  {
    reset();
    
    return 0;
  }
  

  public void reset()
  {
    cipher.reset();
    
    System.arraycopy(tw_init, 0, tw_current, 0, tw_init.length);
    counter = 0;
  }
  
  private static void GF_double(long redPoly, long[] z)
  {
    long c = 0L;
    for (int i = 0; i < z.length; i++)
    {
      long zVal = z[i];
      long bit = zVal >>> 63;
      z[i] = (zVal << 1 ^ c);
      c = bit;
    }
    
    z[0] ^= redPoly & -c;
  }
}
