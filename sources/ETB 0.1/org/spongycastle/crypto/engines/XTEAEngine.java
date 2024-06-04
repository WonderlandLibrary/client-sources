package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;










public class XTEAEngine
  implements BlockCipher
{
  private boolean _forEncryption;
  private boolean _initialised;
  private int[] _sum1 = new int[32]; private int[] _sum0 = new int[32]; private int[] _S = new int[4];
  

  private static final int delta = -1640531527;
  
  private static final int block_size = 8;
  
  private static final int rounds = 32;
  

  public XTEAEngine()
  {
    _initialised = false;
  }
  
  public String getAlgorithmName()
  {
    return "XTEA";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  










  public void init(boolean forEncryption, CipherParameters params)
  {
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("invalid parameter passed to TEA init - " + params.getClass().getName());
    }
    
    _forEncryption = forEncryption;
    _initialised = true;
    
    KeyParameter p = (KeyParameter)params;
    
    setKey(p.getKey());
  }
  




  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    if (!_initialised)
    {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    
    if (inOff + 8 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + 8 > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    return _forEncryption ? encryptBlock(in, inOff, out, outOff) : 
      decryptBlock(in, inOff, out, outOff);
  }
  




  public void reset() {}
  




  private void setKey(byte[] key)
  {
    if (key.length != 16)
    {
      throw new IllegalArgumentException("Key size must be 128 bits.");
    }
    
    int j;
    for (int i = j = 0; i < 4; j += 4)
    {
      _S[i] = bytesToInt(key, j);i++;
    }
    
    for (i = j = 0; i < 32; i++)
    {
      _sum0[i] = (j + _S[(j & 0x3)]);
      j -= 1640531527;
      _sum1[i] = (j + _S[(j >>> 11 & 0x3)]);
    }
  }
  





  private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    int v0 = bytesToInt(in, inOff);
    int v1 = bytesToInt(in, inOff + 4);
    
    for (int i = 0; i < 32; i++)
    {
      v0 += ((v1 << 4 ^ v1 >>> 5) + v1 ^ _sum0[i]);
      v1 += ((v0 << 4 ^ v0 >>> 5) + v0 ^ _sum1[i]);
    }
    
    unpackInt(v0, out, outOff);
    unpackInt(v1, out, outOff + 4);
    
    return 8;
  }
  





  private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    int v0 = bytesToInt(in, inOff);
    int v1 = bytesToInt(in, inOff + 4);
    
    for (int i = 31; i >= 0; i--)
    {
      v1 -= ((v0 << 4 ^ v0 >>> 5) + v0 ^ _sum1[i]);
      v0 -= ((v1 << 4 ^ v1 >>> 5) + v1 ^ _sum0[i]);
    }
    
    unpackInt(v0, out, outOff);
    unpackInt(v1, out, outOff + 4);
    
    return 8;
  }
  
  private int bytesToInt(byte[] in, int inOff)
  {
    return in[(inOff++)] << 24 | (in[(inOff++)] & 0xFF) << 16 | (in[(inOff++)] & 0xFF) << 8 | in[inOff] & 0xFF;
  }
  



  private void unpackInt(int v, byte[] out, int outOff)
  {
    out[(outOff++)] = ((byte)(v >>> 24));
    out[(outOff++)] = ((byte)(v >>> 16));
    out[(outOff++)] = ((byte)(v >>> 8));
    out[outOff] = ((byte)v);
  }
}
