package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;






public class NoekeonEngine
  implements BlockCipher
{
  private static final int genericSize = 16;
  private static final int[] nullVector = { 0, 0, 0, 0 };
  



  private static final int[] roundConstants = { 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212 };
  







  private int[] state = new int[4]; private int[] subKeys = new int[4]; private int[] decryptKeys = new int[4];
  


  private boolean _initialised;
  

  private boolean _forEncryption;
  


  public NoekeonEngine()
  {
    _initialised = false;
  }
  
  public String getAlgorithmName()
  {
    return "Noekeon";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  










  public void init(boolean forEncryption, CipherParameters params)
  {
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("invalid parameter passed to Noekeon init - " + params.getClass().getName());
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
    
    if (inOff + 16 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + 16 > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    return _forEncryption ? encryptBlock(in, inOff, out, outOff) : 
      decryptBlock(in, inOff, out, outOff);
  }
  




  public void reset() {}
  




  private void setKey(byte[] key)
  {
    subKeys[0] = bytesToIntBig(key, 0);
    subKeys[1] = bytesToIntBig(key, 4);
    subKeys[2] = bytesToIntBig(key, 8);
    subKeys[3] = bytesToIntBig(key, 12);
  }
  




  private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    state[0] = bytesToIntBig(in, inOff);
    state[1] = bytesToIntBig(in, inOff + 4);
    state[2] = bytesToIntBig(in, inOff + 8);
    state[3] = bytesToIntBig(in, inOff + 12);
    

    for (int i = 0; i < 16; i++)
    {
      state[0] ^= roundConstants[i];
      theta(state, subKeys);
      pi1(state);
      gamma(state);
      pi2(state);
    }
    
    state[0] ^= roundConstants[i];
    theta(state, subKeys);
    
    intToBytesBig(state[0], out, outOff);
    intToBytesBig(state[1], out, outOff + 4);
    intToBytesBig(state[2], out, outOff + 8);
    intToBytesBig(state[3], out, outOff + 12);
    
    return 16;
  }
  




  private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    state[0] = bytesToIntBig(in, inOff);
    state[1] = bytesToIntBig(in, inOff + 4);
    state[2] = bytesToIntBig(in, inOff + 8);
    state[3] = bytesToIntBig(in, inOff + 12);
    
    System.arraycopy(subKeys, 0, decryptKeys, 0, subKeys.length);
    theta(decryptKeys, nullVector);
    

    for (int i = 16; i > 0; i--)
    {
      theta(state, decryptKeys);
      state[0] ^= roundConstants[i];
      pi1(state);
      gamma(state);
      pi2(state);
    }
    
    theta(state, decryptKeys);
    state[0] ^= roundConstants[i];
    
    intToBytesBig(state[0], out, outOff);
    intToBytesBig(state[1], out, outOff + 4);
    intToBytesBig(state[2], out, outOff + 8);
    intToBytesBig(state[3], out, outOff + 12);
    
    return 16;
  }
  
  private void gamma(int[] a)
  {
    a[1] ^= (a[3] ^ 0xFFFFFFFF) & (a[2] ^ 0xFFFFFFFF);
    a[0] ^= a[2] & a[1];
    
    int tmp = a[3];
    a[3] = a[0];
    a[0] = tmp;
    a[2] ^= a[0] ^ a[1] ^ a[3];
    
    a[1] ^= (a[3] ^ 0xFFFFFFFF) & (a[2] ^ 0xFFFFFFFF);
    a[0] ^= a[2] & a[1];
  }
  


  private void theta(int[] a, int[] k)
  {
    int tmp = a[0] ^ a[2];
    tmp ^= rotl(tmp, 8) ^ rotl(tmp, 24);
    a[1] ^= tmp;
    a[3] ^= tmp;
    
    for (int i = 0; i < 4; i++)
    {
      a[i] ^= k[i];
    }
    
    tmp = a[1] ^ a[3];
    tmp ^= rotl(tmp, 8) ^ rotl(tmp, 24);
    a[0] ^= tmp;
    a[2] ^= tmp;
  }
  
  private void pi1(int[] a)
  {
    a[1] = rotl(a[1], 1);
    a[2] = rotl(a[2], 5);
    a[3] = rotl(a[3], 2);
  }
  
  private void pi2(int[] a)
  {
    a[1] = rotl(a[1], 31);
    a[2] = rotl(a[2], 27);
    a[3] = rotl(a[3], 30);
  }
  


  private int bytesToIntBig(byte[] in, int off)
  {
    return in[(off++)] << 24 | (in[(off++)] & 0xFF) << 16 | (in[(off++)] & 0xFF) << 8 | in[off] & 0xFF;
  }
  



  private void intToBytesBig(int x, byte[] out, int off)
  {
    out[(off++)] = ((byte)(x >>> 24));
    out[(off++)] = ((byte)(x >>> 16));
    out[(off++)] = ((byte)(x >>> 8));
    out[off] = ((byte)x);
  }
  
  private int rotl(int x, int y)
  {
    return x << y | x >>> 32 - y;
  }
}
