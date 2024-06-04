package org.spongycastle.crypto.modes;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSBox;




public class GCFBBlockCipher
  extends StreamBlockCipher
{
  private static final byte[] C = { 105, 0, 114, 34, 100, -55, 4, 35, -115, 58, -37, -106, 70, -23, 42, -60, 24, -2, -84, -108, 0, -19, 7, 18, -64, -122, -36, -62, -17, 76, -87, 43 };
  


  private final CFBBlockCipher cfbEngine;
  


  private KeyParameter key;
  

  private long counter = 0L;
  private boolean forEncryption;
  
  public GCFBBlockCipher(BlockCipher engine)
  {
    super(engine);
    
    cfbEngine = new CFBBlockCipher(engine, engine.getBlockSize() * 8);
  }
  
  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    counter = 0L;
    cfbEngine.init(forEncryption, params);
    
    this.forEncryption = forEncryption;
    
    if ((params instanceof ParametersWithIV))
    {
      params = ((ParametersWithIV)params).getParameters();
    }
    
    if ((params instanceof ParametersWithRandom))
    {
      params = ((ParametersWithRandom)params).getParameters();
    }
    
    if ((params instanceof ParametersWithSBox))
    {
      params = ((ParametersWithSBox)params).getParameters();
    }
    
    key = ((KeyParameter)params);
  }
  
  public String getAlgorithmName()
  {
    String name = cfbEngine.getAlgorithmName();
    return name.substring(0, name.indexOf('/')) + "/G" + name.substring(name.indexOf('/') + 1);
  }
  
  public int getBlockSize()
  {
    return cfbEngine.getBlockSize();
  }
  
  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    processBytes(in, inOff, cfbEngine.getBlockSize(), out, outOff);
    
    return cfbEngine.getBlockSize();
  }
  
  protected byte calculateByte(byte b)
  {
    if ((counter > 0L) && (counter % 1024L == 0L))
    {
      BlockCipher base = cfbEngine.getUnderlyingCipher();
      
      base.init(false, key);
      
      byte[] nextKey = new byte[32];
      
      base.processBlock(C, 0, nextKey, 0);
      base.processBlock(C, 8, nextKey, 8);
      base.processBlock(C, 16, nextKey, 16);
      base.processBlock(C, 24, nextKey, 24);
      
      key = new KeyParameter(nextKey);
      
      base.init(true, key);
      
      byte[] iv = cfbEngine.getCurrentIV();
      
      base.processBlock(iv, 0, iv, 0);
      
      cfbEngine.init(forEncryption, new ParametersWithIV(key, iv));
    }
    
    counter += 1L;
    
    return cfbEngine.calculateByte(b);
  }
  
  public void reset()
  {
    counter = 0L;
    cfbEngine.reset();
  }
}
