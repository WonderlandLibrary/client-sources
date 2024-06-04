package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;






public class ISAACEngine
  implements StreamCipher
{
  private final int sizeL = 8; private final int stateArraySize = 256;
  


  private int[] engineState = null; private int[] results = null;
  
  private int a = 0; private int b = 0; private int c = 0;
  

  private int index = 0;
  private byte[] keyStream = new byte['Ѐ']; private byte[] workingKey = null;
  
  private boolean initialised = false;
  




  public ISAACEngine() {}
  




  public void init(boolean forEncryption, CipherParameters params)
  {
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("invalid parameter passed to ISAAC init - " + params.getClass().getName());
    }
    




    KeyParameter p = (KeyParameter)params;
    setKey(p.getKey());
  }
  


  public byte returnByte(byte in)
  {
    if (index == 0)
    {
      isaac();
      keyStream = Pack.intToBigEndian(results);
    }
    byte out = (byte)(keyStream[index] ^ in);
    index = (index + 1 & 0x3FF);
    
    return out;
  }
  





  public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff)
  {
    if (!initialised)
    {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    
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
      if (index == 0)
      {
        isaac();
        keyStream = Pack.intToBigEndian(results);
      }
      out[(i + outOff)] = ((byte)(keyStream[index] ^ in[(i + inOff)]));
      index = (index + 1 & 0x3FF);
    }
    
    return len;
  }
  
  public String getAlgorithmName()
  {
    return "ISAAC";
  }
  
  public void reset()
  {
    setKey(workingKey);
  }
  

  private void setKey(byte[] keyBytes)
  {
    workingKey = keyBytes;
    
    if (engineState == null)
    {
      engineState = new int['Ā'];
    }
    
    if (results == null)
    {
      results = new int['Ā'];
    }
    



    for (int i = 0; i < 256; i++)
    {
      int tmp57_56 = 0;results[i] = tmp57_56;engineState[i] = tmp57_56;
    }
    a = (this.b = this.c = 0);
    

    index = 0;
    

    byte[] t = new byte[keyBytes.length + (keyBytes.length & 0x3)];
    System.arraycopy(keyBytes, 0, t, 0, keyBytes.length);
    for (i = 0; i < t.length; i += 4)
    {
      results[(i >>> 2)] = Pack.littleEndianToInt(t, i);
    }
    

    int[] abcdefgh = new int[8];
    
    for (i = 0; i < 8; i++)
    {
      abcdefgh[i] = -1640531527;
    }
    
    for (i = 0; i < 4; i++)
    {
      mix(abcdefgh);
    }
    
    for (i = 0; i < 2; i++)
    {
      for (int j = 0; j < 256; j += 8)
      {
        for (int k = 0; k < 8; k++)
        {
          abcdefgh[k] += (i < 1 ? results[(j + k)] : engineState[(j + k)]);
        }
        
        mix(abcdefgh);
        
        for (k = 0; k < 8; k++)
        {
          engineState[(j + k)] = abcdefgh[k];
        }
      }
    }
    
    isaac();
    
    initialised = true;
  }
  


  private void isaac()
  {
    b += ++c;
    for (int i = 0; i < 256; i++)
    {
      int x = engineState[i];
      switch (i & 0x3) {
      case 0: 
        a ^= a << 13; break;
      case 1:  a ^= a >>> 6; break;
      case 2:  a ^= a << 2; break;
      case 3:  a ^= a >>> 16;
      }
      a += engineState[(i + 128 & 0xFF)]; int 
        tmp190_189 = (engineState[(x >>> 2 & 0xFF)] + a + b);int y = tmp190_189;engineState[i] = tmp190_189; int 
        tmp214_213 = (engineState[(y >>> 10 & 0xFF)] + x);b = tmp214_213;results[i] = tmp214_213;
    }
  }
  
  private void mix(int[] x)
  {
    x[0] ^= x[1] << 11;x[3] += x[0];x[1] += x[2];
    x[1] ^= x[2] >>> 2;x[4] += x[1];x[2] += x[3];
    x[2] ^= x[3] << 8;x[5] += x[2];x[3] += x[4];
    x[3] ^= x[4] >>> 16;x[6] += x[3];x[4] += x[5];
    x[4] ^= x[5] << 10;x[7] += x[4];x[5] += x[6];
    x[5] ^= x[6] >>> 4;x[0] += x[5];x[6] += x[7];
    x[6] ^= x[7] << 8;x[1] += x[6];x[7] += x[0];
    x[7] ^= x[0] >>> 9;x[2] += x[7];x[0] += x[1];
  }
}
