package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;






public class RC4Engine
  implements StreamCipher
{
  private static final int STATE_LENGTH = 256;
  private byte[] engineState = null;
  private int x = 0;
  private int y = 0;
  private byte[] workingKey = null;
  





  public RC4Engine() {}
  




  public void init(boolean forEncryption, CipherParameters params)
  {
    if ((params instanceof KeyParameter))
    {





      workingKey = ((KeyParameter)params).getKey();
      setKey(workingKey);
      
      return;
    }
    
    throw new IllegalArgumentException("invalid parameter passed to RC4 init - " + params.getClass().getName());
  }
  
  public String getAlgorithmName()
  {
    return "RC4";
  }
  
  public byte returnByte(byte in)
  {
    x = (x + 1 & 0xFF);
    y = (engineState[x] + y & 0xFF);
    

    byte tmp = engineState[x];
    engineState[x] = engineState[y];
    engineState[y] = tmp;
    

    return (byte)(in ^ engineState[(engineState[x] + engineState[y] & 0xFF)]);
  }
  





  public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff)
  {
    if (inOff + len > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + len > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    for (int i = 0; i < len; i++)
    {
      x = (x + 1 & 0xFF);
      y = (engineState[x] + y & 0xFF);
      

      byte tmp = engineState[x];
      engineState[x] = engineState[y];
      engineState[y] = tmp;
      

      out[(i + outOff)] = ((byte)(in[(i + inOff)] ^ engineState[(engineState[x] + engineState[y] & 0xFF)]));
    }
    

    return len;
  }
  
  public void reset()
  {
    setKey(workingKey);
  }
  


  private void setKey(byte[] keyBytes)
  {
    workingKey = keyBytes;
    


    x = 0;
    y = 0;
    
    if (engineState == null)
    {
      engineState = new byte['Ā'];
    }
    

    for (int i = 0; i < 256; i++)
    {
      engineState[i] = ((byte)i);
    }
    
    int i1 = 0;
    int i2 = 0;
    
    for (int i = 0; i < 256; i++)
    {
      i2 = (keyBytes[i1] & 0xFF) + engineState[i] + i2 & 0xFF;
      
      byte tmp = engineState[i];
      engineState[i] = engineState[i2];
      engineState[i2] = tmp;
      i1 = (i1 + 1) % keyBytes.length;
    }
  }
}
