package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;








public final class TwofishEngine
  implements BlockCipher
{
  private static final byte[][] P = { { -87, 103, -77, -24, 4, -3, -93, 118, -102, -110, Byte.MIN_VALUE, 120, -28, -35, -47, 56, 13, -58, 53, -104, 24, -9, -20, 108, 67, 117, 55, 38, -6, 19, -108, 72, -14, -48, -117, 48, -124, 84, -33, 35, 25, 91, 61, 89, -13, -82, -94, -126, 99, 1, -125, 46, -39, 81, -101, 124, -90, -21, -91, -66, 22, 12, -29, 97, -64, -116, 58, -11, 115, 44, 37, 11, -69, 78, -119, 107, 83, 106, -76, -15, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, 28, 30, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, 51, -55, 98, 113, -127, 121, 9, -83, 36, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, 29, -86, -19, 6, 112, -78, -46, 65, 123, -96, 17, 49, -62, 39, -112, 32, -10, 96, -1, -106, 92, -79, -85, -98, -100, 82, 27, 95, -109, 10, -17, -111, -123, 73, -18, 45, 79, -113, 59, 71, -121, 109, 70, -42, 62, 105, 100, 42, -50, -53, 47, -4, -105, 5, 122, -84, Byte.MAX_VALUE, -43, 26, 75, 14, -89, 90, 40, 20, 63, 41, -120, 60, 76, 2, -72, -38, -80, 23, 85, 31, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, 21, 34, 18, 88, 7, -103, 52, 110, 80, -34, 104, 101, -68, -37, -8, -56, -88, 43, 64, -36, -2, 50, -92, -54, 16, 33, -16, -45, 93, 15, 0, 111, -99, 54, 66, 74, 94, -63, -32 }, { 117, -13, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, -15, -31, 48, 15, -8, 27, -121, -6, 6, 63, 94, -70, -82, 91, -118, 0, -68, -99, 109, -63, -79, 14, Byte.MIN_VALUE, 93, -46, -43, -96, -124, 7, 20, -75, -112, 44, -93, -78, 115, 76, 84, -110, 116, 54, 81, 56, -80, -67, 90, -4, 96, 98, -106, 108, 66, -9, 16, 124, 40, 39, -116, 19, -107, -100, -57, 36, 70, 59, 112, -54, -29, -123, -53, 17, -48, -109, -72, -90, -125, 32, -1, -97, 119, -61, -52, 3, 111, 8, -65, 64, -25, 43, -30, 121, 12, -86, -126, 65, 58, -22, -71, -28, -102, -92, -105, 126, -38, 122, 23, 102, -108, -95, 29, 61, -16, -34, -77, 11, 114, -89, 28, -17, -47, 83, 62, -113, 51, 38, 95, -20, 118, 42, 73, -127, -120, -18, 33, -60, 26, -21, -39, -59, 57, -103, -51, -83, 49, -117, 1, 24, 35, -35, 31, 78, 45, -7, 72, 79, -14, 101, -114, 120, 92, 88, 25, -115, -27, -104, 87, 103, Byte.MAX_VALUE, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, -32, 77, 67, 105, 41, 46, -84, 21, 89, -88, 10, -98, 110, 71, -33, 52, 53, 106, -49, -36, 34, -55, -64, -101, -119, -44, -19, -85, 18, -94, 13, 82, -69, 2, 47, -87, -41, 97, 30, -76, 80, 4, -10, -62, 22, 37, -122, 86, 85, 9, -66, -111 } };
  




  private static final int P_00 = 1;
  




  private static final int P_01 = 0;
  




  private static final int P_02 = 0;
  




  private static final int P_03 = 1;
  




  private static final int P_04 = 1;
  




  private static final int P_10 = 0;
  




  private static final int P_11 = 0;
  




  private static final int P_12 = 1;
  



  private static final int P_13 = 1;
  



  private static final int P_14 = 0;
  



  private static final int P_20 = 1;
  



  private static final int P_21 = 1;
  



  private static final int P_22 = 0;
  



  private static final int P_23 = 0;
  



  private static final int P_24 = 0;
  



  private static final int P_30 = 0;
  



  private static final int P_31 = 1;
  



  private static final int P_32 = 1;
  



  private static final int P_33 = 0;
  



  private static final int P_34 = 1;
  



  private static final int GF256_FDBK = 361;
  



  private static final int GF256_FDBK_2 = 180;
  



  private static final int GF256_FDBK_4 = 90;
  



  private static final int RS_GF_FDBK = 333;
  



  private static final int ROUNDS = 16;
  



  private static final int MAX_ROUNDS = 16;
  



  private static final int BLOCK_SIZE = 16;
  



  private static final int MAX_KEY_BITS = 256;
  



  private static final int INPUT_WHITEN = 0;
  



  private static final int OUTPUT_WHITEN = 4;
  



  private static final int ROUND_SUBKEYS = 8;
  



  private static final int TOTAL_SUBKEYS = 40;
  



  private static final int SK_STEP = 33686018;
  



  private static final int SK_BUMP = 16843009;
  



  private static final int SK_ROTL = 9;
  



  private boolean encrypting = false;
  
  private int[] gMDS0 = new int['Ā'];
  private int[] gMDS1 = new int['Ā'];
  private int[] gMDS2 = new int['Ā'];
  private int[] gMDS3 = new int['Ā'];
  

  private int[] gSubKeys;
  

  private int[] gSBox;
  

  private int k64Cnt = 0;
  
  private byte[] workingKey = null;
  

  public TwofishEngine()
  {
    int[] m1 = new int[2];
    int[] mX = new int[2];
    int[] mY = new int[2];
    

    for (int i = 0; i < 256; i++)
    {
      int j = P[0][i] & 0xFF;
      m1[0] = j;
      mX[0] = (Mx_X(j) & 0xFF);
      mY[0] = (Mx_Y(j) & 0xFF);
      
      j = P[1][i] & 0xFF;
      m1[1] = j;
      mX[1] = (Mx_X(j) & 0xFF);
      mY[1] = (Mx_Y(j) & 0xFF);
      
      gMDS0[i] = (m1[1] | mX[1] << 8 | mY[1] << 16 | mY[1] << 24);
      

      gMDS1[i] = (mY[0] | mY[0] << 8 | mX[0] << 16 | m1[0] << 24);
      

      gMDS2[i] = (mX[1] | mY[1] << 8 | m1[1] << 16 | mY[1] << 24);
      

      gMDS3[i] = (mX[0] | m1[0] << 8 | mY[0] << 16 | mX[0] << 24);
    }
  }
  











  public void init(boolean encrypting, CipherParameters params)
  {
    if ((params instanceof KeyParameter))
    {
      this.encrypting = encrypting;
      workingKey = ((KeyParameter)params).getKey();
      k64Cnt = (workingKey.length / 8);
      setKey(workingKey);
      
      return;
    }
    
    throw new IllegalArgumentException("invalid parameter passed to Twofish init - " + params.getClass().getName());
  }
  
  public String getAlgorithmName()
  {
    return "Twofish";
  }
  




  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    if (workingKey == null)
    {
      throw new IllegalStateException("Twofish not initialised");
    }
    
    if (inOff + 16 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + 16 > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    if (encrypting)
    {
      encryptBlock(in, inOff, out, outOff);
    }
    else
    {
      decryptBlock(in, inOff, out, outOff);
    }
    
    return 16;
  }
  
  public void reset()
  {
    if (workingKey != null)
    {
      setKey(workingKey);
    }
  }
  
  public int getBlockSize()
  {
    return 16;
  }
  




  private void setKey(byte[] key)
  {
    int[] k32e = new int[4];
    int[] k32o = new int[4];
    
    int[] sBoxKeys = new int[4];
    gSubKeys = new int[40];
    
    if (k64Cnt < 1)
    {
      throw new IllegalArgumentException("Key size less than 64 bits");
    }
    
    if (k64Cnt > 4)
    {
      throw new IllegalArgumentException("Key size larger than 256 bits");
    }
    






    for (int i = 0; i < k64Cnt; i++)
    {
      int p = i * 8;
      
      k32e[i] = BytesTo32Bits(key, p);
      k32o[i] = BytesTo32Bits(key, p + 4);
      
      sBoxKeys[(k64Cnt - 1 - i)] = RS_MDS_Encode(k32e[i], k32o[i]);
    }
    

    for (int i = 0; i < 20; i++)
    {
      int q = i * 33686018;
      int A = F32(q, k32e);
      int B = F32(q + 16843009, k32o);
      B = B << 8 | B >>> 24;
      A += B;
      gSubKeys[(i * 2)] = A;
      A += B;
      gSubKeys[(i * 2 + 1)] = (A << 9 | A >>> 23);
    }
    



    int k0 = sBoxKeys[0];
    int k1 = sBoxKeys[1];
    int k2 = sBoxKeys[2];
    int k3 = sBoxKeys[3];
    
    gSBox = new int['Ѐ'];
    for (int i = 0; i < 256; i++) { int b3;
      int b2;
      int b1; int b0 = b1 = b2 = b3 = i;
      switch (k64Cnt & 0x3)
      {
      case 1: 
        gSBox[(i * 2)] = gMDS0[(P[0][b0] & 0xFF ^ b0(k0))];
        gSBox[(i * 2 + 1)] = gMDS1[(P[0][b1] & 0xFF ^ b1(k0))];
        gSBox[(i * 2 + 512)] = gMDS2[(P[1][b2] & 0xFF ^ b2(k0))];
        gSBox[(i * 2 + 513)] = gMDS3[(P[1][b3] & 0xFF ^ b3(k0))];
        break;
      case 0: 
        b0 = P[1][b0] & 0xFF ^ b0(k3);
        b1 = P[0][b1] & 0xFF ^ b1(k3);
        b2 = P[0][b2] & 0xFF ^ b2(k3);
        b3 = P[1][b3] & 0xFF ^ b3(k3);
      
      case 3: 
        b0 = P[1][b0] & 0xFF ^ b0(k2);
        b1 = P[1][b1] & 0xFF ^ b1(k2);
        b2 = P[0][b2] & 0xFF ^ b2(k2);
        b3 = P[0][b3] & 0xFF ^ b3(k2);
      

      case 2: 
        gSBox[(i * 2)] = gMDS0[(P[0][(P[0][b0] & 0xFF ^ b0(k1))] & 0xFF ^ b0(k0))];
        gSBox[(i * 2 + 1)] = gMDS1[
          (P[0][(P[1][b1] & 0xFF ^ b1(k1))] & 0xFF ^ b1(k0))];
        gSBox[(i * 2 + 512)] = gMDS2[
          (P[1][(P[0][b2] & 0xFF ^ b2(k1))] & 0xFF ^ b2(k0))];
        gSBox[(i * 2 + 513)] = gMDS3[
          (P[1][(P[1][b3] & 0xFF ^ b3(k1))] & 0xFF ^ b3(k0))];
      }
      
    }
  }
  

















  private void encryptBlock(byte[] src, int srcIndex, byte[] dst, int dstIndex)
  {
    int x0 = BytesTo32Bits(src, srcIndex) ^ gSubKeys[0];
    int x1 = BytesTo32Bits(src, srcIndex + 4) ^ gSubKeys[1];
    int x2 = BytesTo32Bits(src, srcIndex + 8) ^ gSubKeys[2];
    int x3 = BytesTo32Bits(src, srcIndex + 12) ^ gSubKeys[3];
    
    int k = 8;
    
    for (int r = 0; r < 16; r += 2)
    {
      int t0 = Fe32_0(x0);
      int t1 = Fe32_3(x1);
      x2 ^= t0 + t1 + gSubKeys[(k++)];
      x2 = x2 >>> 1 | x2 << 31;
      x3 = (x3 << 1 | x3 >>> 31) ^ t0 + 2 * t1 + gSubKeys[(k++)];
      
      t0 = Fe32_0(x2);
      t1 = Fe32_3(x3);
      x0 ^= t0 + t1 + gSubKeys[(k++)];
      x0 = x0 >>> 1 | x0 << 31;
      x1 = (x1 << 1 | x1 >>> 31) ^ t0 + 2 * t1 + gSubKeys[(k++)];
    }
    
    Bits32ToBytes(x2 ^ gSubKeys[4], dst, dstIndex);
    Bits32ToBytes(x3 ^ gSubKeys[5], dst, dstIndex + 4);
    Bits32ToBytes(x0 ^ gSubKeys[6], dst, dstIndex + 8);
    Bits32ToBytes(x1 ^ gSubKeys[7], dst, dstIndex + 12);
  }
  









  private void decryptBlock(byte[] src, int srcIndex, byte[] dst, int dstIndex)
  {
    int x2 = BytesTo32Bits(src, srcIndex) ^ gSubKeys[4];
    int x3 = BytesTo32Bits(src, srcIndex + 4) ^ gSubKeys[5];
    int x0 = BytesTo32Bits(src, srcIndex + 8) ^ gSubKeys[6];
    int x1 = BytesTo32Bits(src, srcIndex + 12) ^ gSubKeys[7];
    
    int k = 39;
    
    for (int r = 0; r < 16; r += 2)
    {
      int t0 = Fe32_0(x2);
      int t1 = Fe32_3(x3);
      x1 ^= t0 + 2 * t1 + gSubKeys[(k--)];
      x0 = (x0 << 1 | x0 >>> 31) ^ t0 + t1 + gSubKeys[(k--)];
      x1 = x1 >>> 1 | x1 << 31;
      
      t0 = Fe32_0(x0);
      t1 = Fe32_3(x1);
      x3 ^= t0 + 2 * t1 + gSubKeys[(k--)];
      x2 = (x2 << 1 | x2 >>> 31) ^ t0 + t1 + gSubKeys[(k--)];
      x3 = x3 >>> 1 | x3 << 31;
    }
    
    Bits32ToBytes(x0 ^ gSubKeys[0], dst, dstIndex);
    Bits32ToBytes(x1 ^ gSubKeys[1], dst, dstIndex + 4);
    Bits32ToBytes(x2 ^ gSubKeys[2], dst, dstIndex + 8);
    Bits32ToBytes(x3 ^ gSubKeys[3], dst, dstIndex + 12);
  }
  





  private int F32(int x, int[] k32)
  {
    int b0 = b0(x);
    int b1 = b1(x);
    int b2 = b2(x);
    int b3 = b3(x);
    int k0 = k32[0];
    int k1 = k32[1];
    int k2 = k32[2];
    int k3 = k32[3];
    
    int result = 0;
    switch (k64Cnt & 0x3)
    {



    case 1: 
      result = gMDS0[(P[0][b0] & 0xFF ^ b0(k0))] ^ gMDS1[(P[0][b1] & 0xFF ^ b1(k0))] ^ gMDS2[(P[1][b2] & 0xFF ^ b2(k0))] ^ gMDS3[(P[1][b3] & 0xFF ^ b3(k0))];
      break;
    case 0: 
      b0 = P[1][b0] & 0xFF ^ b0(k3);
      b1 = P[0][b1] & 0xFF ^ b1(k3);
      b2 = P[0][b2] & 0xFF ^ b2(k3);
      b3 = P[1][b3] & 0xFF ^ b3(k3);
    case 3: 
      b0 = P[1][b0] & 0xFF ^ b0(k2);
      b1 = P[1][b1] & 0xFF ^ b1(k2);
      b2 = P[0][b2] & 0xFF ^ b2(k2);
      b3 = P[0][b3] & 0xFF ^ b3(k2);
    



    case 2: 
      result = gMDS0[(P[0][(P[0][b0] & 0xFF ^ b0(k1))] & 0xFF ^ b0(k0))] ^ gMDS1[(P[0][(P[1][b1] & 0xFF ^ b1(k1))] & 0xFF ^ b1(k0))] ^ gMDS2[(P[1][(P[0][b2] & 0xFF ^ b2(k1))] & 0xFF ^ b2(k0))] ^ gMDS3[(P[1][(P[1][b3] & 0xFF ^ b3(k1))] & 0xFF ^ b3(k0))];
    }
    
    return result;
  }
  









  private int RS_MDS_Encode(int k0, int k1)
  {
    int r = k1;
    for (int i = 0; i < 4; i++)
    {
      r = RS_rem(r);
    }
    r ^= k0;
    for (int i = 0; i < 4; i++)
    {
      r = RS_rem(r);
    }
    
    return r;
  }
  







  private int RS_rem(int x)
  {
    int b = x >>> 24 & 0xFF;
    int g2 = (b << 1 ^ ((b & 0x80) != 0 ? 333 : 0)) & 0xFF;
    
    int g3 = b >>> 1 ^ ((b & 0x1) != 0 ? 166 : 0) ^ g2;
    
    return x << 8 ^ g3 << 24 ^ g2 << 16 ^ g3 << 8 ^ b;
  }
  
  private int LFSR1(int x)
  {
    return x >> 1 ^ ((x & 0x1) != 0 ? 180 : 0);
  }
  

  private int LFSR2(int x)
  {
    return x >> 2 ^ ((x & 0x2) != 0 ? 180 : 0) ^ ((x & 0x1) != 0 ? 90 : 0);
  }
  


  private int Mx_X(int x)
  {
    return x ^ LFSR2(x);
  }
  
  private int Mx_Y(int x)
  {
    return x ^ LFSR1(x) ^ LFSR2(x);
  }
  
  private int b0(int x)
  {
    return x & 0xFF;
  }
  
  private int b1(int x)
  {
    return x >>> 8 & 0xFF;
  }
  
  private int b2(int x)
  {
    return x >>> 16 & 0xFF;
  }
  
  private int b3(int x)
  {
    return x >>> 24 & 0xFF;
  }
  
  private int Fe32_0(int x)
  {
    return gSBox[(0 + 2 * (x & 0xFF))] ^ gSBox[(1 + 2 * (x >>> 8 & 0xFF))] ^ gSBox[(512 + 2 * (x >>> 16 & 0xFF))] ^ gSBox[(513 + 2 * (x >>> 24 & 0xFF))];
  }
  



  private int Fe32_3(int x)
  {
    return gSBox[(0 + 2 * (x >>> 24 & 0xFF))] ^ gSBox[(1 + 2 * (x & 0xFF))] ^ gSBox[(512 + 2 * (x >>> 8 & 0xFF))] ^ gSBox[(513 + 2 * (x >>> 16 & 0xFF))];
  }
  



  private int BytesTo32Bits(byte[] b, int p)
  {
    return b[p] & 0xFF | (b[(p + 1)] & 0xFF) << 8 | (b[(p + 2)] & 0xFF) << 16 | (b[(p + 3)] & 0xFF) << 24;
  }
  



  private void Bits32ToBytes(int in, byte[] b, int offset)
  {
    b[offset] = ((byte)in);
    b[(offset + 1)] = ((byte)(in >> 8));
    b[(offset + 2)] = ((byte)(in >> 16));
    b[(offset + 3)] = ((byte)(in >> 24));
  }
}
