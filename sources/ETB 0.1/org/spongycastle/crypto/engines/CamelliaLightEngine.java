package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;






public class CamelliaLightEngine
  implements BlockCipher
{
  private static final int BLOCK_SIZE = 16;
  private static final int MASK8 = 255;
  private boolean initialized;
  private boolean _keyis128;
  private int[] subkey = new int[96];
  private int[] kw = new int[8];
  private int[] ke = new int[12];
  private int[] state = new int[4];
  
  private static final int[] SIGMA = { -1600231809, 1003262091, -1233459112, 1286239154, -957401297, -380665154, 1426019237, -237801700, 283453434, -563598051, -1336506174, -1276722691 };
  












  private static final byte[] SBOX1 = { 112, -126, 44, -20, -77, 39, -64, -27, -28, -123, 87, 53, -22, 12, -82, 65, 35, -17, 107, -109, 69, 25, -91, 33, -19, 14, 79, 78, 29, 101, -110, -67, -122, -72, -81, -113, 124, -21, 31, -50, 62, 48, -36, 95, 94, -59, 11, 26, -90, -31, 57, -54, -43, 71, 93, 61, -39, 1, 90, -42, 81, 86, 108, 77, -117, 13, -102, 102, -5, -52, -80, 45, 116, 18, 43, 32, -16, -79, -124, -103, -33, 76, -53, -62, 52, 126, 118, 5, 109, -73, -87, 49, -47, 23, 4, -41, 20, 88, 58, 97, -34, 27, 17, 28, 50, 15, -100, 22, 83, 24, -14, 34, -2, 68, -49, -78, -61, -75, 122, -111, 36, 8, -24, -88, 96, -4, 105, 80, -86, -48, -96, 125, -95, -119, 98, -105, 84, 91, 30, -107, -32, -1, 100, -46, 16, -60, 0, 72, -93, -9, 117, -37, -118, 3, -26, -38, 9, 63, -35, -108, -121, 92, -125, 2, -51, 74, -112, 51, 115, 103, -10, -13, -99, Byte.MAX_VALUE, -65, -30, 82, -101, -40, 38, -56, 55, -58, 59, -127, -106, 111, 75, 19, -66, 99, 46, -23, 121, -89, -116, -97, 110, -68, -114, 41, -11, -7, -74, 47, -3, -76, 89, 120, -104, 6, 106, -25, 70, 113, -70, -44, 37, -85, 66, -120, -94, -115, -6, 114, 7, -71, 85, -8, -18, -84, 10, 54, 73, 42, 104, 60, 56, -15, -92, 64, 40, -45, 123, -69, -55, 67, -63, 21, -29, -83, -12, 119, -57, Byte.MIN_VALUE, -98 };
  

































































  private static int rightRotate(int x, int s)
  {
    return (x >>> s) + (x << 32 - s);
  }
  
  private static int leftRotate(int x, int s)
  {
    return (x << s) + (x >>> 32 - s);
  }
  

  private static void roldq(int rot, int[] ki, int ioff, int[] ko, int ooff)
  {
    ko[(0 + ooff)] = (ki[(0 + ioff)] << rot | ki[(1 + ioff)] >>> 32 - rot);
    ko[(1 + ooff)] = (ki[(1 + ioff)] << rot | ki[(2 + ioff)] >>> 32 - rot);
    ko[(2 + ooff)] = (ki[(2 + ioff)] << rot | ki[(3 + ioff)] >>> 32 - rot);
    ko[(3 + ooff)] = (ki[(3 + ioff)] << rot | ki[(0 + ioff)] >>> 32 - rot);
    ki[(0 + ioff)] = ko[(0 + ooff)];
    ki[(1 + ioff)] = ko[(1 + ooff)];
    ki[(2 + ioff)] = ko[(2 + ooff)];
    ki[(3 + ioff)] = ko[(3 + ooff)];
  }
  

  private static void decroldq(int rot, int[] ki, int ioff, int[] ko, int ooff)
  {
    ko[(2 + ooff)] = (ki[(0 + ioff)] << rot | ki[(1 + ioff)] >>> 32 - rot);
    ko[(3 + ooff)] = (ki[(1 + ioff)] << rot | ki[(2 + ioff)] >>> 32 - rot);
    ko[(0 + ooff)] = (ki[(2 + ioff)] << rot | ki[(3 + ioff)] >>> 32 - rot);
    ko[(1 + ooff)] = (ki[(3 + ioff)] << rot | ki[(0 + ioff)] >>> 32 - rot);
    ki[(0 + ioff)] = ko[(2 + ooff)];
    ki[(1 + ioff)] = ko[(3 + ooff)];
    ki[(2 + ioff)] = ko[(0 + ooff)];
    ki[(3 + ioff)] = ko[(1 + ooff)];
  }
  

  private static void roldqo32(int rot, int[] ki, int ioff, int[] ko, int ooff)
  {
    ko[(0 + ooff)] = (ki[(1 + ioff)] << rot - 32 | ki[(2 + ioff)] >>> 64 - rot);
    ko[(1 + ooff)] = (ki[(2 + ioff)] << rot - 32 | ki[(3 + ioff)] >>> 64 - rot);
    ko[(2 + ooff)] = (ki[(3 + ioff)] << rot - 32 | ki[(0 + ioff)] >>> 64 - rot);
    ko[(3 + ooff)] = (ki[(0 + ioff)] << rot - 32 | ki[(1 + ioff)] >>> 64 - rot);
    ki[(0 + ioff)] = ko[(0 + ooff)];
    ki[(1 + ioff)] = ko[(1 + ooff)];
    ki[(2 + ioff)] = ko[(2 + ooff)];
    ki[(3 + ioff)] = ko[(3 + ooff)];
  }
  

  private static void decroldqo32(int rot, int[] ki, int ioff, int[] ko, int ooff)
  {
    ko[(2 + ooff)] = (ki[(1 + ioff)] << rot - 32 | ki[(2 + ioff)] >>> 64 - rot);
    ko[(3 + ooff)] = (ki[(2 + ioff)] << rot - 32 | ki[(3 + ioff)] >>> 64 - rot);
    ko[(0 + ooff)] = (ki[(3 + ioff)] << rot - 32 | ki[(0 + ioff)] >>> 64 - rot);
    ko[(1 + ooff)] = (ki[(0 + ioff)] << rot - 32 | ki[(1 + ioff)] >>> 64 - rot);
    ki[(0 + ioff)] = ko[(2 + ooff)];
    ki[(1 + ioff)] = ko[(3 + ooff)];
    ki[(2 + ioff)] = ko[(0 + ooff)];
    ki[(3 + ioff)] = ko[(1 + ooff)];
  }
  
  private int bytes2int(byte[] src, int offset)
  {
    int word = 0;
    
    for (int i = 0; i < 4; i++)
    {
      word = (word << 8) + (src[(i + offset)] & 0xFF);
    }
    return word;
  }
  
  private void int2bytes(int word, byte[] dst, int offset)
  {
    for (int i = 0; i < 4; i++)
    {
      dst[(3 - i + offset)] = ((byte)word);
      word >>>= 8;
    }
  }
  
  private byte lRot8(byte v, int rot)
  {
    return (byte)(v << rot | (v & 0xFF) >>> 8 - rot);
  }
  
  private int sbox2(int x)
  {
    return lRot8(SBOX1[x], 1) & 0xFF;
  }
  
  private int sbox3(int x)
  {
    return lRot8(SBOX1[x], 7) & 0xFF;
  }
  
  private int sbox4(int x)
  {
    return SBOX1[(lRot8((byte)x, 1) & 0xFF)] & 0xFF;
  }
  


  private void camelliaF2(int[] s, int[] skey, int keyoff)
  {
    int t1 = s[0] ^ skey[(0 + keyoff)];
    int u = sbox4(t1 & 0xFF);
    u |= sbox3(t1 >>> 8 & 0xFF) << 8;
    u |= sbox2(t1 >>> 16 & 0xFF) << 16;
    u |= (SBOX1[(t1 >>> 24 & 0xFF)] & 0xFF) << 24;
    
    int t2 = s[1] ^ skey[(1 + keyoff)];
    int v = SBOX1[(t2 & 0xFF)] & 0xFF;
    v |= sbox4(t2 >>> 8 & 0xFF) << 8;
    v |= sbox3(t2 >>> 16 & 0xFF) << 16;
    v |= sbox2(t2 >>> 24 & 0xFF) << 24;
    
    v = leftRotate(v, 8);
    u ^= v;
    v = leftRotate(v, 8) ^ u;
    u = rightRotate(u, 8) ^ v;
    s[2] ^= leftRotate(v, 16) ^ u;
    s[3] ^= leftRotate(u, 8);
    
    t1 = s[2] ^ skey[(2 + keyoff)];
    u = sbox4(t1 & 0xFF);
    u |= sbox3(t1 >>> 8 & 0xFF) << 8;
    u |= sbox2(t1 >>> 16 & 0xFF) << 16;
    u |= (SBOX1[(t1 >>> 24 & 0xFF)] & 0xFF) << 24;
    
    t2 = s[3] ^ skey[(3 + keyoff)];
    v = SBOX1[(t2 & 0xFF)] & 0xFF;
    v |= sbox4(t2 >>> 8 & 0xFF) << 8;
    v |= sbox3(t2 >>> 16 & 0xFF) << 16;
    v |= sbox2(t2 >>> 24 & 0xFF) << 24;
    
    v = leftRotate(v, 8);
    u ^= v;
    v = leftRotate(v, 8) ^ u;
    u = rightRotate(u, 8) ^ v;
    s[0] ^= leftRotate(v, 16) ^ u;
    s[1] ^= leftRotate(u, 8);
  }
  

  private void camelliaFLs(int[] s, int[] fkey, int keyoff)
  {
    s[1] ^= leftRotate(s[0] & fkey[(0 + keyoff)], 1);
    s[0] ^= (fkey[(1 + keyoff)] | s[1]);
    
    s[2] ^= (fkey[(3 + keyoff)] | s[3]);
    s[3] ^= leftRotate(fkey[(2 + keyoff)] & s[2], 1);
  }
  
  private void setKey(boolean forEncryption, byte[] key)
  {
    int[] k = new int[8];
    int[] ka = new int[4];
    int[] kb = new int[4];
    int[] t = new int[4];
    
    switch (key.length)
    {
    case 16: 
      _keyis128 = true;
      k[0] = bytes2int(key, 0);
      k[1] = bytes2int(key, 4);
      k[2] = bytes2int(key, 8);
      k[3] = bytes2int(key, 12); int 
        tmp114_113 = (k[6] = k[7] = 0);k[5] = tmp114_113;k[4] = tmp114_113;
      break;
    case 24: 
      k[0] = bytes2int(key, 0);
      k[1] = bytes2int(key, 4);
      k[2] = bytes2int(key, 8);
      k[3] = bytes2int(key, 12);
      k[4] = bytes2int(key, 16);
      k[5] = bytes2int(key, 20);
      k[6] = (k[4] ^ 0xFFFFFFFF);
      k[7] = (k[5] ^ 0xFFFFFFFF);
      _keyis128 = false;
      break;
    case 32: 
      k[0] = bytes2int(key, 0);
      k[1] = bytes2int(key, 4);
      k[2] = bytes2int(key, 8);
      k[3] = bytes2int(key, 12);
      k[4] = bytes2int(key, 16);
      k[5] = bytes2int(key, 20);
      k[6] = bytes2int(key, 24);
      k[7] = bytes2int(key, 28);
      _keyis128 = false;
      break;
    default: 
      throw new IllegalArgumentException("key sizes are only 16/24/32 bytes.");
    }
    
    
    for (int i = 0; i < 4; i++)
    {
      k[i] ^= k[(i + 4)];
    }
    
    camelliaF2(ka, SIGMA, 0);
    for (int i = 0; i < 4; i++)
    {
      ka[i] ^= k[i];
    }
    camelliaF2(ka, SIGMA, 4);
    
    if (_keyis128)
    {
      if (forEncryption)
      {

        kw[0] = k[0];
        kw[1] = k[1];
        kw[2] = k[2];
        kw[3] = k[3];
        roldq(15, k, 0, subkey, 4);
        roldq(30, k, 0, subkey, 12);
        roldq(15, k, 0, t, 0);
        subkey[18] = t[2];
        subkey[19] = t[3];
        roldq(17, k, 0, ke, 4);
        roldq(17, k, 0, subkey, 24);
        roldq(17, k, 0, subkey, 32);
        
        subkey[0] = ka[0];
        subkey[1] = ka[1];
        subkey[2] = ka[2];
        subkey[3] = ka[3];
        roldq(15, ka, 0, subkey, 8);
        roldq(15, ka, 0, ke, 0);
        roldq(15, ka, 0, t, 0);
        subkey[16] = t[0];
        subkey[17] = t[1];
        roldq(15, ka, 0, subkey, 20);
        roldqo32(34, ka, 0, subkey, 28);
        roldq(17, ka, 0, kw, 4);

      }
      else
      {

        kw[4] = k[0];
        kw[5] = k[1];
        kw[6] = k[2];
        kw[7] = k[3];
        decroldq(15, k, 0, subkey, 28);
        decroldq(30, k, 0, subkey, 20);
        decroldq(15, k, 0, t, 0);
        subkey[16] = t[0];
        subkey[17] = t[1];
        decroldq(17, k, 0, ke, 0);
        decroldq(17, k, 0, subkey, 8);
        decroldq(17, k, 0, subkey, 0);
        
        subkey[34] = ka[0];
        subkey[35] = ka[1];
        subkey[32] = ka[2];
        subkey[33] = ka[3];
        decroldq(15, ka, 0, subkey, 24);
        decroldq(15, ka, 0, ke, 4);
        decroldq(15, ka, 0, t, 0);
        subkey[18] = t[2];
        subkey[19] = t[3];
        decroldq(15, ka, 0, subkey, 12);
        decroldqo32(34, ka, 0, subkey, 4);
        roldq(17, ka, 0, kw, 0);
      }
      
    }
    else
    {
      for (int i = 0; i < 4; i++)
      {
        ka[i] ^= k[(i + 4)];
      }
      camelliaF2(kb, SIGMA, 8);
      
      if (forEncryption)
      {

        kw[0] = k[0];
        kw[1] = k[1];
        kw[2] = k[2];
        kw[3] = k[3];
        roldqo32(45, k, 0, subkey, 16);
        roldq(15, k, 0, ke, 4);
        roldq(17, k, 0, subkey, 32);
        roldqo32(34, k, 0, subkey, 44);
        
        roldq(15, k, 4, subkey, 4);
        roldq(15, k, 4, ke, 0);
        roldq(30, k, 4, subkey, 24);
        roldqo32(34, k, 4, subkey, 36);
        
        roldq(15, ka, 0, subkey, 8);
        roldq(30, ka, 0, subkey, 20);
        
        ke[8] = ka[1];
        ke[9] = ka[2];
        ke[10] = ka[3];
        ke[11] = ka[0];
        roldqo32(49, ka, 0, subkey, 40);
        

        subkey[0] = kb[0];
        subkey[1] = kb[1];
        subkey[2] = kb[2];
        subkey[3] = kb[3];
        roldq(30, kb, 0, subkey, 12);
        roldq(30, kb, 0, subkey, 28);
        roldqo32(51, kb, 0, kw, 4);

      }
      else
      {

        kw[4] = k[0];
        kw[5] = k[1];
        kw[6] = k[2];
        kw[7] = k[3];
        decroldqo32(45, k, 0, subkey, 28);
        decroldq(15, k, 0, ke, 4);
        decroldq(17, k, 0, subkey, 12);
        decroldqo32(34, k, 0, subkey, 0);
        
        decroldq(15, k, 4, subkey, 40);
        decroldq(15, k, 4, ke, 8);
        decroldq(30, k, 4, subkey, 20);
        decroldqo32(34, k, 4, subkey, 8);
        
        decroldq(15, ka, 0, subkey, 36);
        decroldq(30, ka, 0, subkey, 24);
        
        ke[2] = ka[1];
        ke[3] = ka[2];
        ke[0] = ka[3];
        ke[1] = ka[0];
        decroldqo32(49, ka, 0, subkey, 4);
        

        subkey[46] = kb[0];
        subkey[47] = kb[1];
        subkey[44] = kb[2];
        subkey[45] = kb[3];
        decroldq(30, kb, 0, subkey, 32);
        decroldq(30, kb, 0, subkey, 16);
        roldqo32(51, kb, 0, kw, 0);
      }
    }
  }
  

  private int processBlock128(byte[] in, int inOff, byte[] out, int outOff)
  {
    for (int i = 0; i < 4; i++)
    {
      state[i] = bytes2int(in, inOff + i * 4);
      state[i] ^= kw[i];
    }
    
    camelliaF2(state, subkey, 0);
    camelliaF2(state, subkey, 4);
    camelliaF2(state, subkey, 8);
    camelliaFLs(state, ke, 0);
    camelliaF2(state, subkey, 12);
    camelliaF2(state, subkey, 16);
    camelliaF2(state, subkey, 20);
    camelliaFLs(state, ke, 4);
    camelliaF2(state, subkey, 24);
    camelliaF2(state, subkey, 28);
    camelliaF2(state, subkey, 32);
    
    state[2] ^= kw[4];
    state[3] ^= kw[5];
    state[0] ^= kw[6];
    state[1] ^= kw[7];
    
    int2bytes(state[2], out, outOff);
    int2bytes(state[3], out, outOff + 4);
    int2bytes(state[0], out, outOff + 8);
    int2bytes(state[1], out, outOff + 12);
    
    return 16;
  }
  

  private int processBlock192or256(byte[] in, int inOff, byte[] out, int outOff)
  {
    for (int i = 0; i < 4; i++)
    {
      state[i] = bytes2int(in, inOff + i * 4);
      state[i] ^= kw[i];
    }
    
    camelliaF2(state, subkey, 0);
    camelliaF2(state, subkey, 4);
    camelliaF2(state, subkey, 8);
    camelliaFLs(state, ke, 0);
    camelliaF2(state, subkey, 12);
    camelliaF2(state, subkey, 16);
    camelliaF2(state, subkey, 20);
    camelliaFLs(state, ke, 4);
    camelliaF2(state, subkey, 24);
    camelliaF2(state, subkey, 28);
    camelliaF2(state, subkey, 32);
    camelliaFLs(state, ke, 8);
    camelliaF2(state, subkey, 36);
    camelliaF2(state, subkey, 40);
    camelliaF2(state, subkey, 44);
    
    state[2] ^= kw[4];
    state[3] ^= kw[5];
    state[0] ^= kw[6];
    state[1] ^= kw[7];
    
    int2bytes(state[2], out, outOff);
    int2bytes(state[3], out, outOff + 4);
    int2bytes(state[0], out, outOff + 8);
    int2bytes(state[1], out, outOff + 12);
    return 16;
  }
  

  public CamelliaLightEngine() {}
  

  public String getAlgorithmName()
  {
    return "Camellia";
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  
  public void init(boolean forEncryption, CipherParameters params)
  {
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("only simple KeyParameter expected.");
    }
    
    setKey(forEncryption, ((KeyParameter)params).getKey());
    initialized = true;
  }
  


  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws IllegalStateException
  {
    if (!initialized)
    {
      throw new IllegalStateException("Camellia is not initialized");
    }
    
    if (inOff + 16 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + 16 > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    if (_keyis128)
    {
      return processBlock128(in, inOff, out, outOff);
    }
    

    return processBlock192or256(in, inOff, out, outOff);
  }
  
  public void reset() {}
}
