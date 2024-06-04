package org.spongycastle.crypto.engines;

import org.spongycastle.util.Pack;



public class XSalsa20Engine
  extends Salsa20Engine
{
  public XSalsa20Engine() {}
  
  public String getAlgorithmName()
  {
    return "XSalsa20";
  }
  
  protected int getNonceSize()
  {
    return 24;
  }
  





  protected void setKey(byte[] keyBytes, byte[] ivBytes)
  {
    if (keyBytes == null)
    {
      throw new IllegalArgumentException(getAlgorithmName() + " doesn't support re-init with null key");
    }
    
    if (keyBytes.length != 32)
    {
      throw new IllegalArgumentException(getAlgorithmName() + " requires a 256 bit key");
    }
    

    super.setKey(keyBytes, ivBytes);
    

    Pack.littleEndianToInt(ivBytes, 8, engineState, 8, 2);
    

    int[] hsalsa20Out = new int[engineState.length];
    salsaCore(20, engineState, hsalsa20Out);
    

    engineState[1] = (hsalsa20Out[0] - engineState[0]);
    engineState[2] = (hsalsa20Out[5] - engineState[5]);
    engineState[3] = (hsalsa20Out[10] - engineState[10]);
    engineState[4] = (hsalsa20Out[15] - engineState[15]);
    
    engineState[11] = (hsalsa20Out[6] - engineState[6]);
    engineState[12] = (hsalsa20Out[7] - engineState[7]);
    engineState[13] = (hsalsa20Out[8] - engineState[8]);
    engineState[14] = (hsalsa20Out[9] - engineState[9]);
    

    Pack.littleEndianToInt(ivBytes, 16, engineState, 6, 2);
  }
}
