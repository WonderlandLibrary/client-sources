package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;














public class Grain128Engine
  implements StreamCipher
{
  private static final int STATE_SIZE = 4;
  private byte[] workingKey;
  private byte[] workingIV;
  private byte[] out;
  private int[] lfsr;
  private int[] nfsr;
  private int output;
  private int index = 4;
  
  private boolean initialised = false;
  
  public Grain128Engine() {}
  
  public String getAlgorithmName() { return "Grain-128"; }
  












  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    if (!(params instanceof ParametersWithIV))
    {
      throw new IllegalArgumentException("Grain-128 Init parameters must include an IV");
    }
    

    ParametersWithIV ivParams = (ParametersWithIV)params;
    
    byte[] iv = ivParams.getIV();
    
    if ((iv == null) || (iv.length != 12))
    {
      throw new IllegalArgumentException("Grain-128  requires exactly 12 bytes of IV");
    }
    

    if (!(ivParams.getParameters() instanceof KeyParameter))
    {
      throw new IllegalArgumentException("Grain-128 Init parameters must include a key");
    }
    

    KeyParameter key = (KeyParameter)ivParams.getParameters();
    



    workingIV = new byte[key.getKey().length];
    workingKey = new byte[key.getKey().length];
    lfsr = new int[4];
    nfsr = new int[4];
    out = new byte[4];
    
    System.arraycopy(iv, 0, workingIV, 0, iv.length);
    System.arraycopy(key.getKey(), 0, workingKey, 0, key.getKey().length);
    
    reset();
  }
  



  private void initGrain()
  {
    for (int i = 0; i < 8; i++)
    {
      output = getOutput();
      nfsr = shift(nfsr, getOutputNFSR() ^ lfsr[0] ^ output);
      lfsr = shift(lfsr, getOutputLFSR() ^ output);
    }
    initialised = true;
  }
  





  private int getOutputNFSR()
  {
    int b0 = nfsr[0];
    int b3 = nfsr[0] >>> 3 | nfsr[1] << 29;
    int b11 = nfsr[0] >>> 11 | nfsr[1] << 21;
    int b13 = nfsr[0] >>> 13 | nfsr[1] << 19;
    int b17 = nfsr[0] >>> 17 | nfsr[1] << 15;
    int b18 = nfsr[0] >>> 18 | nfsr[1] << 14;
    int b26 = nfsr[0] >>> 26 | nfsr[1] << 6;
    int b27 = nfsr[0] >>> 27 | nfsr[1] << 5;
    int b40 = nfsr[1] >>> 8 | nfsr[2] << 24;
    int b48 = nfsr[1] >>> 16 | nfsr[2] << 16;
    int b56 = nfsr[1] >>> 24 | nfsr[2] << 8;
    int b59 = nfsr[1] >>> 27 | nfsr[2] << 5;
    int b61 = nfsr[1] >>> 29 | nfsr[2] << 3;
    int b65 = nfsr[2] >>> 1 | nfsr[3] << 31;
    int b67 = nfsr[2] >>> 3 | nfsr[3] << 29;
    int b68 = nfsr[2] >>> 4 | nfsr[3] << 28;
    int b84 = nfsr[2] >>> 20 | nfsr[3] << 12;
    int b91 = nfsr[2] >>> 27 | nfsr[3] << 5;
    int b96 = nfsr[3];
    
    return b0 ^ b26 ^ b56 ^ b91 ^ b96 ^ b3 & b67 ^ b11 & b13 ^ b17 & b18 ^ b27 & b59 ^ b40 & b48 ^ b61 & b65 ^ b68 & b84;
  }
  






  private int getOutputLFSR()
  {
    int s0 = lfsr[0];
    int s7 = lfsr[0] >>> 7 | lfsr[1] << 25;
    int s38 = lfsr[1] >>> 6 | lfsr[2] << 26;
    int s70 = lfsr[2] >>> 6 | lfsr[3] << 26;
    int s81 = lfsr[2] >>> 17 | lfsr[3] << 15;
    int s96 = lfsr[3];
    
    return s0 ^ s7 ^ s38 ^ s70 ^ s81 ^ s96;
  }
  





  private int getOutput()
  {
    int b2 = nfsr[0] >>> 2 | nfsr[1] << 30;
    int b12 = nfsr[0] >>> 12 | nfsr[1] << 20;
    int b15 = nfsr[0] >>> 15 | nfsr[1] << 17;
    int b36 = nfsr[1] >>> 4 | nfsr[2] << 28;
    int b45 = nfsr[1] >>> 13 | nfsr[2] << 19;
    int b64 = nfsr[2];
    int b73 = nfsr[2] >>> 9 | nfsr[3] << 23;
    int b89 = nfsr[2] >>> 25 | nfsr[3] << 7;
    int b95 = nfsr[2] >>> 31 | nfsr[3] << 1;
    int s8 = lfsr[0] >>> 8 | lfsr[1] << 24;
    int s13 = lfsr[0] >>> 13 | lfsr[1] << 19;
    int s20 = lfsr[0] >>> 20 | lfsr[1] << 12;
    int s42 = lfsr[1] >>> 10 | lfsr[2] << 22;
    int s60 = lfsr[1] >>> 28 | lfsr[2] << 4;
    int s79 = lfsr[2] >>> 15 | lfsr[3] << 17;
    int s93 = lfsr[2] >>> 29 | lfsr[3] << 3;
    int s95 = lfsr[2] >>> 31 | lfsr[3] << 1;
    
    return b12 & s8 ^ s13 & s20 ^ b95 & s42 ^ s60 & s79 ^ b12 & b95 & s95 ^ s93 ^ b2 ^ b15 ^ b36 ^ b45 ^ b64 ^ b73 ^ b89;
  }
  








  private int[] shift(int[] array, int val)
  {
    array[0] = array[1];
    array[1] = array[2];
    array[2] = array[3];
    array[3] = val;
    
    return array;
  }
  






  private void setKey(byte[] keyBytes, byte[] ivBytes)
  {
    ivBytes[12] = -1;
    ivBytes[13] = -1;
    ivBytes[14] = -1;
    ivBytes[15] = -1;
    workingKey = keyBytes;
    workingIV = ivBytes;
    



    int j = 0;
    for (int i = 0; i < nfsr.length; i++)
    {
      nfsr[i] = (workingKey[(j + 3)] << 24 | workingKey[(j + 2)] << 16 & 0xFF0000 | workingKey[(j + 1)] << 8 & 0xFF00 | workingKey[j] & 0xFF);
      


      lfsr[i] = (workingIV[(j + 3)] << 24 | workingIV[(j + 2)] << 16 & 0xFF0000 | workingIV[(j + 1)] << 8 & 0xFF00 | workingIV[j] & 0xFF);
      

      j += 4;
    }
  }
  

  public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff)
    throws DataLengthException
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
      out[(outOff + i)] = ((byte)(in[(inOff + i)] ^ getKeyStream()));
    }
    
    return len;
  }
  
  public void reset()
  {
    index = 4;
    setKey(workingKey, workingIV);
    initGrain();
  }
  



  private void oneRound()
  {
    output = getOutput();
    out[0] = ((byte)output);
    out[1] = ((byte)(output >> 8));
    out[2] = ((byte)(output >> 16));
    out[3] = ((byte)(output >> 24));
    
    nfsr = shift(nfsr, getOutputNFSR() ^ lfsr[0]);
    lfsr = shift(lfsr, getOutputLFSR());
  }
  
  public byte returnByte(byte in)
  {
    if (!initialised)
    {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    
    return (byte)(in ^ getKeyStream());
  }
  
  private byte getKeyStream()
  {
    if (index > 3)
    {
      oneRound();
      index = 0;
    }
    return out[(index++)];
  }
}
