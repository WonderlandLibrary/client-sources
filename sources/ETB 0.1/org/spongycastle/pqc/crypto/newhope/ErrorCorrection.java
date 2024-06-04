package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.util.Arrays;

class ErrorCorrection {
  ErrorCorrection() {}
  
  static int abs(int v) {
    int mask = v >> 31;
    return (v ^ mask) - mask;
  }
  



  static int f(int[] v, int off0, int off1, int x)
  {
    int b = x * 2730;
    int t = b >> 25;
    b = x - t * 12289;
    b = 12288 - b;
    b >>= 31;
    t -= b;
    
    int r = t & 0x1;
    int xit = t >> 1;
    v[off0] = (xit + r);
    
    t--;
    r = t & 0x1;
    v[off1] = ((t >> 1) + r);
    
    return abs(x - v[off0] * 2 * 12289);
  }
  



  static int g(int x)
  {
    int b = x * 2730;
    int t = b >> 27;
    b = x - t * 49156;
    b = 49155 - b;
    b >>= 31;
    t -= b;
    
    int c = t & 0x1;
    t = (t >> 1) + c;
    
    t *= 98312;
    
    return abs(t - x);
  }
  
  static void helpRec(short[] c, short[] v, byte[] seed, byte nonce)
  {
    byte[] iv = new byte[8];
    
    iv[0] = nonce;
    
    byte[] rand = new byte[32];
    ChaCha20.process(seed, iv, rand, 0, rand.length);
    

    int[] vs = new int[8];int[] vTmp = new int[4];
    

    for (int i = 0; i < 256; i++)
    {
      int rBit = rand[(i >>> 3)] >>> (i & 0x7) & 0x1;
      
      int k = f(vs, 0, 4, 8 * v[(0 + i)] + 4 * rBit);
      k += f(vs, 1, 5, 8 * v[(256 + i)] + 4 * rBit);
      k += f(vs, 2, 6, 8 * v[(512 + i)] + 4 * rBit);
      k += f(vs, 3, 7, 8 * v[(768 + i)] + 4 * rBit);
      
      k = 24577 - k >> 31;
      
      vTmp[0] = ((k ^ 0xFFFFFFFF) & vs[0] ^ k & vs[4]);
      vTmp[1] = ((k ^ 0xFFFFFFFF) & vs[1] ^ k & vs[5]);
      vTmp[2] = ((k ^ 0xFFFFFFFF) & vs[2] ^ k & vs[6]);
      vTmp[3] = ((k ^ 0xFFFFFFFF) & vs[3] ^ k & vs[7]);
      
      c[(0 + i)] = ((short)(vTmp[0] - vTmp[3] & 0x3));
      c[(256 + i)] = ((short)(vTmp[1] - vTmp[3] & 0x3));
      c[(512 + i)] = ((short)(vTmp[2] - vTmp[3] & 0x3));
      c[(768 + i)] = ((short)(-k + 2 * vTmp[3] & 0x3));
    }
  }
  


  static short LDDecode(int xi0, int xi1, int xi2, int xi3)
  {
    int t = g(xi0);
    t += g(xi1);
    t += g(xi2);
    t += g(xi3);
    
    t -= 98312;
    
    return (short)(t >>> 31);
  }
  
  static void rec(byte[] key, short[] v, short[] c)
  {
    Arrays.fill(key, (byte)0);
    
    int[] tmp = new int[4];
    for (int i = 0; i < 256; i++)
    {
      tmp[0] = (196624 + 8 * v[(0 + i)] - 12289 * (2 * c[(0 + i)] + c[(768 + i)]));
      tmp[1] = (196624 + 8 * v[(256 + i)] - 12289 * (2 * c[(256 + i)] + c[(768 + i)]));
      tmp[2] = (196624 + 8 * v[(512 + i)] - 12289 * (2 * c[(512 + i)] + c[(768 + i)]));
      tmp[3] = (196624 + 8 * v[(768 + i)] - 12289 * c[(768 + i)]); int 
      
        tmp174_173 = (i >>> 3);key[tmp174_173] = ((byte)(key[tmp174_173] | LDDecode(tmp[0], tmp[1], tmp[2], tmp[3]) << (i & 0x7)));
    }
  }
}
