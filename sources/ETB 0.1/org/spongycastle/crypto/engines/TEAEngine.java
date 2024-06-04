package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;









public class TEAEngine
  implements BlockCipher
{
  private static final int rounds = 32;
  private static final int block_size = 8;
  private static final int delta = -1640531527;
  private static final int d_sum = -957401312;
  private int _a;
  private int _b;
  private int _c;
  private int _d;
  private boolean _initialised;
  private boolean _forEncryption;
  
  public TEAEngine()
  {
    _initialised = false;
  }
  
  public String getAlgorithmName()
  {
    return "TEA";
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
    
    _a = bytesToInt(key, 0);
    _b = bytesToInt(key, 4);
    _c = bytesToInt(key, 8);
    _d = bytesToInt(key, 12);
  }
  





  private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    int v0 = bytesToInt(in, inOff);
    int v1 = bytesToInt(in, inOff + 4);
    
    int sum = 0;
    
    for (int i = 0; i != 32; i++)
    {
      sum -= 1640531527;
      v0 += ((v1 << 4) + _a ^ v1 + sum ^ (v1 >>> 5) + _b);
      v1 += ((v0 << 4) + _c ^ v0 + sum ^ (v0 >>> 5) + _d);
    }
    
    unpackInt(v0, out, outOff);
    unpackInt(v1, out, outOff + 4);
    
    return 8;
  }
  





  private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    int v0 = bytesToInt(in, inOff);
    int v1 = bytesToInt(in, inOff + 4);
    
    int sum = -957401312;
    
    for (int i = 0; i != 32; i++)
    {
      v1 -= ((v0 << 4) + _c ^ v0 + sum ^ (v0 >>> 5) + _d);
      v0 -= ((v1 << 4) + _a ^ v1 + sum ^ (v1 >>> 5) + _b);
      sum += 1640531527;
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
