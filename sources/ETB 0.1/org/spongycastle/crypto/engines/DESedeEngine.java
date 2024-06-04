package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;





public class DESedeEngine
  extends DESEngine
{
  protected static final int BLOCK_SIZE = 8;
  private int[] workingKey1 = null;
  private int[] workingKey2 = null;
  private int[] workingKey3 = null;
  





  private boolean forEncryption;
  





  public DESedeEngine() {}
  





  public void init(boolean encrypting, CipherParameters params)
  {
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("invalid parameter passed to DESede init - " + params.getClass().getName());
    }
    
    byte[] keyMaster = ((KeyParameter)params).getKey();
    
    if ((keyMaster.length != 24) && (keyMaster.length != 16))
    {
      throw new IllegalArgumentException("key size must be 16 or 24 bytes.");
    }
    
    forEncryption = encrypting;
    
    byte[] key1 = new byte[8];
    System.arraycopy(keyMaster, 0, key1, 0, key1.length);
    workingKey1 = generateWorkingKey(encrypting, key1);
    
    byte[] key2 = new byte[8];
    System.arraycopy(keyMaster, 8, key2, 0, key2.length);
    workingKey2 = generateWorkingKey(!encrypting, key2);
    
    if (keyMaster.length == 24)
    {
      byte[] key3 = new byte[8];
      System.arraycopy(keyMaster, 16, key3, 0, key3.length);
      workingKey3 = generateWorkingKey(encrypting, key3);
    }
    else
    {
      workingKey3 = workingKey1;
    }
  }
  
  public String getAlgorithmName()
  {
    return "DESede";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  




  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    if (workingKey1 == null)
    {
      throw new IllegalStateException("DESede engine not initialised");
    }
    
    if (inOff + 8 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + 8 > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    byte[] temp = new byte[8];
    
    if (forEncryption)
    {
      desFunc(workingKey1, in, inOff, temp, 0);
      desFunc(workingKey2, temp, 0, temp, 0);
      desFunc(workingKey3, temp, 0, out, outOff);
    }
    else
    {
      desFunc(workingKey3, in, inOff, temp, 0);
      desFunc(workingKey2, temp, 0, temp, 0);
      desFunc(workingKey1, temp, 0, out, outOff);
    }
    
    return 8;
  }
  
  public void reset() {}
}
