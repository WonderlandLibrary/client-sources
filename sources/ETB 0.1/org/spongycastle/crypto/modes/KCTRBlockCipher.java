package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;






public class KCTRBlockCipher
  extends StreamBlockCipher
{
  private byte[] iv;
  private byte[] ofbV;
  private byte[] ofbOutV;
  private int byteCount;
  private boolean initialised;
  private BlockCipher engine;
  
  public KCTRBlockCipher(BlockCipher engine)
  {
    super(engine);
    
    this.engine = engine;
    iv = new byte[engine.getBlockSize()];
    ofbV = new byte[engine.getBlockSize()];
    ofbOutV = new byte[engine.getBlockSize()];
  }
  
  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    initialised = true;
    
    if ((params instanceof ParametersWithIV))
    {
      ParametersWithIV ivParam = (ParametersWithIV)params;
      byte[] iv = ivParam.getIV();
      int diff = this.iv.length - iv.length;
      
      Arrays.fill(this.iv, (byte)0);
      System.arraycopy(iv, 0, this.iv, diff, iv.length);
      params = ivParam.getParameters();
    }
    else
    {
      throw new IllegalArgumentException("invalid parameter passed");
    }
    
    if (params != null)
    {
      engine.init(true, params);
    }
    
    reset();
  }
  
  public String getAlgorithmName()
  {
    return engine.getAlgorithmName() + "/KCTR";
  }
  
  public int getBlockSize()
  {
    return engine.getBlockSize();
  }
  
  protected byte calculateByte(byte b)
  {
    if (byteCount == 0)
    {
      incrementCounterAt(0);
      
      checkCounter();
      
      engine.processBlock(ofbV, 0, ofbOutV, 0);
      
      return (byte)(ofbOutV[(byteCount++)] ^ b);
    }
    
    byte rv = (byte)(ofbOutV[(byteCount++)] ^ b);
    
    if (byteCount == ofbV.length)
    {
      byteCount = 0;
    }
    
    return rv;
  }
  
  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (in.length - inOff < getBlockSize())
    {
      throw new DataLengthException("input buffer too short");
    }
    if (out.length - outOff < getBlockSize())
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    processBytes(in, inOff, getBlockSize(), out, outOff);
    
    return getBlockSize();
  }
  
  public void reset()
  {
    if (initialised)
    {
      engine.processBlock(iv, 0, ofbV, 0);
    }
    engine.reset();
    byteCount = 0;
  }
  
  private void incrementCounterAt(int pos)
  {
    int i = pos;
    while (i < ofbV.length)
    {
      int tmp19_16 = (i++);ofbV;
      































































































































      if ((tmp19_12[tmp19_16] = (byte)(tmp19_12[tmp19_16] + 1)) != 0) {
        break;
      }
    }
  }
  
  private void checkCounter() {}
}
