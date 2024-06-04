package org.spongycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.util.Random;



















public class GF2Polynomial
{
  private int len;
  private int blocks;
  private int[] value;
  private static Random rand = new Random();
  

  private static final boolean[] parity = { false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false };
  



























  private static final short[] squaringTable = { 0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85, 256, 257, 260, 261, 272, 273, 276, 277, 320, 321, 324, 325, 336, 337, 340, 341, 1024, 1025, 1028, 1029, 1040, 1041, 1044, 1045, 1088, 1089, 1092, 1093, 1104, 1105, 1108, 1109, 1280, 1281, 1284, 1285, 1296, 1297, 1300, 1301, 1344, 1345, 1348, 1349, 1360, 1361, 1364, 1365, 4096, 4097, 4100, 4101, 4112, 4113, 4116, 4117, 4160, 4161, 4164, 4165, 4176, 4177, 4180, 4181, 4352, 4353, 4356, 4357, 4368, 4369, 4372, 4373, 4416, 4417, 4420, 4421, 4432, 4433, 4436, 4437, 5120, 5121, 5124, 5125, 5136, 5137, 5140, 5141, 5184, 5185, 5188, 5189, 5200, 5201, 5204, 5205, 5376, 5377, 5380, 5381, 5392, 5393, 5396, 5397, 5440, 5441, 5444, 5445, 5456, 5457, 5460, 5461, 16384, 16385, 16388, 16389, 16400, 16401, 16404, 16405, 16448, 16449, 16452, 16453, 16464, 16465, 16468, 16469, 16640, 16641, 16644, 16645, 16656, 16657, 16660, 16661, 16704, 16705, 16708, 16709, 16720, 16721, 16724, 16725, 17408, 17409, 17412, 17413, 17424, 17425, 17428, 17429, 17472, 17473, 17476, 17477, 17488, 17489, 17492, 17493, 17664, 17665, 17668, 17669, 17680, 17681, 17684, 17685, 17728, 17729, 17732, 17733, 17744, 17745, 17748, 17749, 20480, 20481, 20484, 20485, 20496, 20497, 20500, 20501, 20544, 20545, 20548, 20549, 20560, 20561, 20564, 20565, 20736, 20737, 20740, 20741, 20752, 20753, 20756, 20757, 20800, 20801, 20804, 20805, 20816, 20817, 20820, 20821, 21504, 21505, 21508, 21509, 21520, 21521, 21524, 21525, 21568, 21569, 21572, 21573, 21584, 21585, 21588, 21589, 21760, 21761, 21764, 21765, 21776, 21777, 21780, 21781, 21824, 21825, 21828, 21829, 21840, 21841, 21844, 21845 };
  

































  private static final int[] bitMask = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824, Integer.MIN_VALUE, 0 };
  







  private static final int[] reverseRightMask = { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, Integer.MAX_VALUE, -1 };
  












  public GF2Polynomial(int length)
  {
    int l = length;
    if (l < 1)
    {
      l = 1;
    }
    blocks = ((l - 1 >> 5) + 1);
    value = new int[blocks];
    len = l;
  }
  






  public GF2Polynomial(int length, Random rand)
  {
    int l = length;
    if (l < 1)
    {
      l = 1;
    }
    blocks = ((l - 1 >> 5) + 1);
    value = new int[blocks];
    len = l;
    randomize(rand);
  }
  














  public GF2Polynomial(int length, String value)
  {
    int l = length;
    if (l < 1)
    {
      l = 1;
    }
    blocks = ((l - 1 >> 5) + 1);
    this.value = new int[blocks];
    len = l;
    if (value.equalsIgnoreCase("ZERO"))
    {
      assignZero();
    }
    else if (value.equalsIgnoreCase("ONE"))
    {
      assignOne();
    }
    else if (value.equalsIgnoreCase("RANDOM"))
    {
      randomize();
    }
    else if (value.equalsIgnoreCase("X"))
    {
      assignX();
    }
    else if (value.equalsIgnoreCase("ALL"))
    {
      assignAll();
    }
    else
    {
      throw new IllegalArgumentException("Error: GF2Polynomial was called using " + value + " as value!");
    }
  }
  










  public GF2Polynomial(int length, int[] bs)
  {
    int leng = length;
    if (leng < 1)
    {
      leng = 1;
    }
    blocks = ((leng - 1 >> 5) + 1);
    value = new int[blocks];
    len = leng;
    int l = Math.min(blocks, bs.length);
    System.arraycopy(bs, 0, value, 0, l);
    zeroUnusedBits();
  }
  








  public GF2Polynomial(int length, byte[] os)
  {
    int l = length;
    if (l < 1)
    {
      l = 1;
    }
    blocks = ((l - 1 >> 5) + 1);
    value = new int[blocks];
    len = l;
    
    int k = Math.min((os.length - 1 >> 2) + 1, blocks);
    for (int i = 0; i < k - 1; i++)
    {
      int m = os.length - (i << 2) - 1;
      value[i] = (os[m] & 0xFF);
      value[i] |= os[(m - 1)] << 8 & 0xFF00;
      value[i] |= os[(m - 2)] << 16 & 0xFF0000;
      value[i] |= os[(m - 3)] << 24 & 0xFF000000;
    }
    i = k - 1;
    int m = os.length - (i << 2) - 1;
    value[i] = (os[m] & 0xFF);
    if (m > 0)
    {
      value[i] |= os[(m - 1)] << 8 & 0xFF00;
    }
    if (m > 1)
    {
      value[i] |= os[(m - 2)] << 16 & 0xFF0000;
    }
    if (m > 2)
    {
      value[i] |= os[(m - 3)] << 24 & 0xFF000000;
    }
    zeroUnusedBits();
    reduceN();
  }
  








  public GF2Polynomial(int length, BigInteger bi)
  {
    int l = length;
    if (l < 1)
    {
      l = 1;
    }
    blocks = ((l - 1 >> 5) + 1);
    value = new int[blocks];
    len = l;
    
    byte[] val = bi.toByteArray();
    if (val[0] == 0)
    {
      byte[] dummy = new byte[val.length - 1];
      System.arraycopy(val, 1, dummy, 0, dummy.length);
      val = dummy;
    }
    int ov = val.length & 0x3;
    int k = (val.length - 1 >> 2) + 1;
    for (int i = 0; i < ov; i++)
    {
      value[(k - 1)] |= (val[i] & 0xFF) << (ov - 1 - i << 3);
    }
    int m = 0;
    for (i = 0; i <= val.length - 4 >> 2; i++)
    {
      m = val.length - 1 - (i << 2);
      value[i] = (val[m] & 0xFF);
      value[i] |= val[(m - 1)] << 8 & 0xFF00;
      value[i] |= val[(m - 2)] << 16 & 0xFF0000;
      value[i] |= val[(m - 3)] << 24 & 0xFF000000;
    }
    if ((len & 0x1F) != 0)
    {
      value[(blocks - 1)] &= reverseRightMask[(len & 0x1F)];
    }
    reduceN();
  }
  





  public GF2Polynomial(GF2Polynomial b)
  {
    len = len;
    blocks = blocks;
    value = IntUtils.clone(value);
  }
  



  public Object clone()
  {
    return new GF2Polynomial(this);
  }
  






  public int getLength()
  {
    return len;
  }
  






  public int[] toIntegerArray()
  {
    int[] result = new int[blocks];
    System.arraycopy(value, 0, result, 0, blocks);
    return result;
  }
  







  public String toString(int radix)
  {
    char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    String[] BIN_CHARS = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111" };
    



    String res = new String();
    if (radix == 16)
    {
      for (int i = blocks - 1; i >= 0; i--)
      {
        res = res + HEX_CHARS[(value[i] >>> 28 & 0xF)];
        res = res + HEX_CHARS[(value[i] >>> 24 & 0xF)];
        res = res + HEX_CHARS[(value[i] >>> 20 & 0xF)];
        res = res + HEX_CHARS[(value[i] >>> 16 & 0xF)];
        res = res + HEX_CHARS[(value[i] >>> 12 & 0xF)];
        res = res + HEX_CHARS[(value[i] >>> 8 & 0xF)];
        res = res + HEX_CHARS[(value[i] >>> 4 & 0xF)];
        res = res + HEX_CHARS[(value[i] & 0xF)];
        res = res + " ";
      }
    }
    

    for (int i = blocks - 1; i >= 0; i--)
    {
      res = res + BIN_CHARS[(value[i] >>> 28 & 0xF)];
      res = res + BIN_CHARS[(value[i] >>> 24 & 0xF)];
      res = res + BIN_CHARS[(value[i] >>> 20 & 0xF)];
      res = res + BIN_CHARS[(value[i] >>> 16 & 0xF)];
      res = res + BIN_CHARS[(value[i] >>> 12 & 0xF)];
      res = res + BIN_CHARS[(value[i] >>> 8 & 0xF)];
      res = res + BIN_CHARS[(value[i] >>> 4 & 0xF)];
      res = res + BIN_CHARS[(value[i] & 0xF)];
      res = res + " ";
    }
    
    return res;
  }
  






  public byte[] toByteArray()
  {
    int k = (len - 1 >> 3) + 1;
    int ov = k & 0x3;
    
    byte[] res = new byte[k];
    
    for (int i = 0; i < k >> 2; i++)
    {
      int m = k - (i << 2) - 1;
      res[m] = ((byte)(value[i] & 0xFF));
      res[(m - 1)] = ((byte)((value[i] & 0xFF00) >>> 8));
      res[(m - 2)] = ((byte)((value[i] & 0xFF0000) >>> 16));
      res[(m - 3)] = ((byte)((value[i] & 0xFF000000) >>> 24));
    }
    for (i = 0; i < ov; i++)
    {
      int m = ov - i - 1 << 3;
      res[i] = ((byte)((value[(blocks - 1)] & 255 << m) >>> m));
    }
    return res;
  }
  






  public BigInteger toFlexiBigInt()
  {
    if ((len == 0) || (isZero()))
    {
      return new BigInteger(0, new byte[0]);
    }
    return new BigInteger(1, toByteArray());
  }
  





  public void assignOne()
  {
    for (int i = 1; i < blocks; i++)
    {
      value[i] = 0;
    }
    value[0] = 1;
  }
  




  public void assignX()
  {
    for (int i = 1; i < blocks; i++)
    {
      value[i] = 0;
    }
    value[0] = 2;
  }
  




  public void assignAll()
  {
    for (int i = 0; i < blocks; i++)
    {
      value[i] = -1;
    }
    zeroUnusedBits();
  }
  




  public void assignZero()
  {
    for (int i = 0; i < blocks; i++)
    {
      value[i] = 0;
    }
  }
  




  public void randomize()
  {
    for (int i = 0; i < blocks; i++)
    {
      value[i] = rand.nextInt();
    }
    zeroUnusedBits();
  }
  







  public void randomize(Random rand)
  {
    for (int i = 0; i < blocks; i++)
    {
      value[i] = rand.nextInt();
    }
    zeroUnusedBits();
  }
  








  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof GF2Polynomial)))
    {
      return false;
    }
    
    GF2Polynomial otherPol = (GF2Polynomial)other;
    
    if (len != len)
    {
      return false;
    }
    for (int i = 0; i < blocks; i++)
    {
      if (value[i] != value[i])
      {
        return false;
      }
    }
    return true;
  }
  



  public int hashCode()
  {
    return len + value.hashCode();
  }
  






  public boolean isZero()
  {
    if (len == 0)
    {
      return true;
    }
    for (int i = 0; i < blocks; i++)
    {
      if (value[i] != 0)
      {
        return false;
      }
    }
    return true;
  }
  






  public boolean isOne()
  {
    for (int i = 1; i < blocks; i++)
    {
      if (value[i] != 0)
      {
        return false;
      }
    }
    if (value[0] != 1)
    {
      return false;
    }
    return true;
  }
  






  public void addToThis(GF2Polynomial b)
  {
    expandN(len);
    xorThisBy(b);
  }
  







  public GF2Polynomial add(GF2Polynomial b)
  {
    return xor(b);
  }
  






  public void subtractFromThis(GF2Polynomial b)
  {
    expandN(len);
    xorThisBy(b);
  }
  








  public GF2Polynomial subtract(GF2Polynomial b)
  {
    return xor(b);
  }
  



  public void increaseThis()
  {
    xorBit(0);
  }
  






  public GF2Polynomial increase()
  {
    GF2Polynomial result = new GF2Polynomial(this);
    result.increaseThis();
    return result;
  }
  








  public GF2Polynomial multiplyClassic(GF2Polynomial b)
  {
    GF2Polynomial result = new GF2Polynomial(Math.max(len, len) << 1);
    GF2Polynomial[] m = new GF2Polynomial[32];
    
    m[0] = new GF2Polynomial(this);
    for (int i = 1; i <= 31; i++)
    {
      m[i] = m[(i - 1)].shiftLeft();
    }
    for (i = 0; i < blocks; i++)
    {
      for (int j = 0; j <= 31; j++)
      {
        if ((value[i] & bitMask[j]) != 0)
        {
          result.xorThisBy(m[j]);
        }
      }
      for (j = 0; j <= 31; j++)
      {
        m[j].shiftBlocksLeft();
      }
    }
    return result;
  }
  








  public GF2Polynomial multiply(GF2Polynomial b)
  {
    int n = Math.max(len, len);
    expandN(n);
    b.expandN(n);
    return karaMult(b);
  }
  



  private GF2Polynomial karaMult(GF2Polynomial b)
  {
    GF2Polynomial result = new GF2Polynomial(len << 1);
    if (len <= 32)
    {
      value = mult32(value[0], value[0]);
      return result;
    }
    if (len <= 64)
    {
      value = mult64(value, value);
      return result;
    }
    if (len <= 128)
    {
      value = mult128(value, value);
      return result;
    }
    if (len <= 256)
    {
      value = mult256(value, value);
      return result;
    }
    if (len <= 512)
    {
      value = mult512(value, value);
      return result;
    }
    
    int n = IntegerFunctions.floorLog(len - 1);
    n = bitMask[n];
    
    GF2Polynomial a0 = lower((n - 1 >> 5) + 1);
    GF2Polynomial a1 = upper((n - 1 >> 5) + 1);
    GF2Polynomial b0 = b.lower((n - 1 >> 5) + 1);
    GF2Polynomial b1 = b.upper((n - 1 >> 5) + 1);
    
    GF2Polynomial c = a1.karaMult(b1);
    GF2Polynomial e = a0.karaMult(b0);
    a0.addToThis(a1);
    b0.addToThis(b1);
    GF2Polynomial d = a0.karaMult(b0);
    
    result.shiftLeftAddThis(c, n << 1);
    result.shiftLeftAddThis(c, n);
    result.shiftLeftAddThis(d, n);
    result.shiftLeftAddThis(e, n);
    result.addToThis(e);
    return result;
  }
  



  private static int[] mult512(int[] a, int[] b)
  {
    int[] result = new int[32];
    int[] a0 = new int[8];
    System.arraycopy(a, 0, a0, 0, Math.min(8, a.length));
    int[] a1 = new int[8];
    if (a.length > 8)
    {
      System.arraycopy(a, 8, a1, 0, Math.min(8, a.length - 8));
    }
    int[] b0 = new int[8];
    System.arraycopy(b, 0, b0, 0, Math.min(8, b.length));
    int[] b1 = new int[8];
    if (b.length > 8)
    {
      System.arraycopy(b, 8, b1, 0, Math.min(8, b.length - 8));
    }
    int[] c = mult256(a1, b1);
    result[31] ^= c[15];
    result[30] ^= c[14];
    result[29] ^= c[13];
    result[28] ^= c[12];
    result[27] ^= c[11];
    result[26] ^= c[10];
    result[25] ^= c[9];
    result[24] ^= c[8];
    result[23] ^= c[7] ^ c[15];
    result[22] ^= c[6] ^ c[14];
    result[21] ^= c[5] ^ c[13];
    result[20] ^= c[4] ^ c[12];
    result[19] ^= c[3] ^ c[11];
    result[18] ^= c[2] ^ c[10];
    result[17] ^= c[1] ^ c[9];
    result[16] ^= c[0] ^ c[8];
    result[15] ^= c[7];
    result[14] ^= c[6];
    result[13] ^= c[5];
    result[12] ^= c[4];
    result[11] ^= c[3];
    result[10] ^= c[2];
    result[9] ^= c[1];
    result[8] ^= c[0];
    a1[0] ^= a0[0];
    a1[1] ^= a0[1];
    a1[2] ^= a0[2];
    a1[3] ^= a0[3];
    a1[4] ^= a0[4];
    a1[5] ^= a0[5];
    a1[6] ^= a0[6];
    a1[7] ^= a0[7];
    b1[0] ^= b0[0];
    b1[1] ^= b0[1];
    b1[2] ^= b0[2];
    b1[3] ^= b0[3];
    b1[4] ^= b0[4];
    b1[5] ^= b0[5];
    b1[6] ^= b0[6];
    b1[7] ^= b0[7];
    int[] d = mult256(a1, b1);
    result[23] ^= d[15];
    result[22] ^= d[14];
    result[21] ^= d[13];
    result[20] ^= d[12];
    result[19] ^= d[11];
    result[18] ^= d[10];
    result[17] ^= d[9];
    result[16] ^= d[8];
    result[15] ^= d[7];
    result[14] ^= d[6];
    result[13] ^= d[5];
    result[12] ^= d[4];
    result[11] ^= d[3];
    result[10] ^= d[2];
    result[9] ^= d[1];
    result[8] ^= d[0];
    int[] e = mult256(a0, b0);
    result[23] ^= e[15];
    result[22] ^= e[14];
    result[21] ^= e[13];
    result[20] ^= e[12];
    result[19] ^= e[11];
    result[18] ^= e[10];
    result[17] ^= e[9];
    result[16] ^= e[8];
    result[15] ^= e[7] ^ e[15];
    result[14] ^= e[6] ^ e[14];
    result[13] ^= e[5] ^ e[13];
    result[12] ^= e[4] ^ e[12];
    result[11] ^= e[3] ^ e[11];
    result[10] ^= e[2] ^ e[10];
    result[9] ^= e[1] ^ e[9];
    result[8] ^= e[0] ^ e[8];
    result[7] ^= e[7];
    result[6] ^= e[6];
    result[5] ^= e[5];
    result[4] ^= e[4];
    result[3] ^= e[3];
    result[2] ^= e[2];
    result[1] ^= e[1];
    result[0] ^= e[0];
    return result;
  }
  



  private static int[] mult256(int[] a, int[] b)
  {
    int[] result = new int[16];
    int[] a0 = new int[4];
    System.arraycopy(a, 0, a0, 0, Math.min(4, a.length));
    int[] a1 = new int[4];
    if (a.length > 4)
    {
      System.arraycopy(a, 4, a1, 0, Math.min(4, a.length - 4));
    }
    int[] b0 = new int[4];
    System.arraycopy(b, 0, b0, 0, Math.min(4, b.length));
    int[] b1 = new int[4];
    if (b.length > 4)
    {
      System.arraycopy(b, 4, b1, 0, Math.min(4, b.length - 4));
    }
    if ((a1[3] == 0) && (a1[2] == 0) && (b1[3] == 0) && (b1[2] == 0))
    {
      if ((a1[1] == 0) && (b1[1] == 0))
      {
        if ((a1[0] != 0) || (b1[0] != 0))
        {
          int[] c = mult32(a1[0], b1[0]);
          result[9] ^= c[1];
          result[8] ^= c[0];
          result[5] ^= c[1];
          result[4] ^= c[0];
        }
      }
      else
      {
        int[] c = mult64(a1, b1);
        result[11] ^= c[3];
        result[10] ^= c[2];
        result[9] ^= c[1];
        result[8] ^= c[0];
        result[7] ^= c[3];
        result[6] ^= c[2];
        result[5] ^= c[1];
        result[4] ^= c[0];
      }
    }
    else
    {
      int[] c = mult128(a1, b1);
      result[15] ^= c[7];
      result[14] ^= c[6];
      result[13] ^= c[5];
      result[12] ^= c[4];
      result[11] ^= c[3] ^ c[7];
      result[10] ^= c[2] ^ c[6];
      result[9] ^= c[1] ^ c[5];
      result[8] ^= c[0] ^ c[4];
      result[7] ^= c[3];
      result[6] ^= c[2];
      result[5] ^= c[1];
      result[4] ^= c[0];
    }
    a1[0] ^= a0[0];
    a1[1] ^= a0[1];
    a1[2] ^= a0[2];
    a1[3] ^= a0[3];
    b1[0] ^= b0[0];
    b1[1] ^= b0[1];
    b1[2] ^= b0[2];
    b1[3] ^= b0[3];
    int[] d = mult128(a1, b1);
    result[11] ^= d[7];
    result[10] ^= d[6];
    result[9] ^= d[5];
    result[8] ^= d[4];
    result[7] ^= d[3];
    result[6] ^= d[2];
    result[5] ^= d[1];
    result[4] ^= d[0];
    int[] e = mult128(a0, b0);
    result[11] ^= e[7];
    result[10] ^= e[6];
    result[9] ^= e[5];
    result[8] ^= e[4];
    result[7] ^= e[3] ^ e[7];
    result[6] ^= e[2] ^ e[6];
    result[5] ^= e[1] ^ e[5];
    result[4] ^= e[0] ^ e[4];
    result[3] ^= e[3];
    result[2] ^= e[2];
    result[1] ^= e[1];
    result[0] ^= e[0];
    return result;
  }
  



  private static int[] mult128(int[] a, int[] b)
  {
    int[] result = new int[8];
    int[] a0 = new int[2];
    System.arraycopy(a, 0, a0, 0, Math.min(2, a.length));
    int[] a1 = new int[2];
    if (a.length > 2)
    {
      System.arraycopy(a, 2, a1, 0, Math.min(2, a.length - 2));
    }
    int[] b0 = new int[2];
    System.arraycopy(b, 0, b0, 0, Math.min(2, b.length));
    int[] b1 = new int[2];
    if (b.length > 2)
    {
      System.arraycopy(b, 2, b1, 0, Math.min(2, b.length - 2));
    }
    if ((a1[1] == 0) && (b1[1] == 0))
    {
      if ((a1[0] != 0) || (b1[0] != 0))
      {
        int[] c = mult32(a1[0], b1[0]);
        result[5] ^= c[1];
        result[4] ^= c[0];
        result[3] ^= c[1];
        result[2] ^= c[0];
      }
    }
    else
    {
      int[] c = mult64(a1, b1);
      result[7] ^= c[3];
      result[6] ^= c[2];
      result[5] ^= c[1] ^ c[3];
      result[4] ^= c[0] ^ c[2];
      result[3] ^= c[1];
      result[2] ^= c[0];
    }
    a1[0] ^= a0[0];
    a1[1] ^= a0[1];
    b1[0] ^= b0[0];
    b1[1] ^= b0[1];
    if ((a1[1] == 0) && (b1[1] == 0))
    {
      int[] d = mult32(a1[0], b1[0]);
      result[3] ^= d[1];
      result[2] ^= d[0];
    }
    else
    {
      int[] d = mult64(a1, b1);
      result[5] ^= d[3];
      result[4] ^= d[2];
      result[3] ^= d[1];
      result[2] ^= d[0];
    }
    if ((a0[1] == 0) && (b0[1] == 0))
    {
      int[] e = mult32(a0[0], b0[0]);
      result[3] ^= e[1];
      result[2] ^= e[0];
      result[1] ^= e[1];
      result[0] ^= e[0];
    }
    else
    {
      int[] e = mult64(a0, b0);
      result[5] ^= e[3];
      result[4] ^= e[2];
      result[3] ^= e[1] ^ e[3];
      result[2] ^= e[0] ^ e[2];
      result[1] ^= e[1];
      result[0] ^= e[0];
    }
    return result;
  }
  



  private static int[] mult64(int[] a, int[] b)
  {
    int[] result = new int[4];
    int a0 = a[0];
    int a1 = 0;
    if (a.length > 1)
    {
      a1 = a[1];
    }
    int b0 = b[0];
    int b1 = 0;
    if (b.length > 1)
    {
      b1 = b[1];
    }
    if ((a1 != 0) || (b1 != 0))
    {
      int[] c = mult32(a1, b1);
      result[3] ^= c[1];
      result[2] ^= c[0] ^ c[1];
      result[1] ^= c[0];
    }
    int[] d = mult32(a0 ^ a1, b0 ^ b1);
    result[2] ^= d[1];
    result[1] ^= d[0];
    int[] e = mult32(a0, b0);
    result[2] ^= e[1];
    result[1] ^= e[0] ^ e[1];
    result[0] ^= e[0];
    return result;
  }
  



  private static int[] mult32(int a, int b)
  {
    int[] result = new int[2];
    if ((a == 0) || (b == 0))
    {
      return result;
    }
    long b2 = b;
    b2 &= 0xFFFFFFFF;
    
    long h = 0L;
    for (int i = 1; i <= 32; i++)
    {
      if ((a & bitMask[(i - 1)]) != 0)
      {
        h ^= b2;
      }
      b2 <<= 1;
    }
    result[1] = ((int)(h >>> 32));
    result[0] = ((int)(h & 0xFFFFFFFF));
    return result;
  }
  









  private GF2Polynomial upper(int k)
  {
    int j = Math.min(k, blocks - k);
    GF2Polynomial result = new GF2Polynomial(j << 5);
    if (blocks >= k)
    {
      System.arraycopy(value, k, value, 0, j);
    }
    return result;
  }
  









  private GF2Polynomial lower(int k)
  {
    GF2Polynomial result = new GF2Polynomial(k << 5);
    System.arraycopy(value, 0, value, 0, Math.min(k, blocks));
    return result;
  }
  








  public GF2Polynomial remainder(GF2Polynomial g)
    throws RuntimeException
  {
    GF2Polynomial a = new GF2Polynomial(this);
    GF2Polynomial b = new GF2Polynomial(g);
    

    if (b.isZero())
    {
      throw new RuntimeException();
    }
    a.reduceN();
    b.reduceN();
    if (len < len)
    {
      return a;
    }
    int i = len - len;
    while (i >= 0)
    {
      GF2Polynomial j = b.shiftLeft(i);
      a.subtractFromThis(j);
      a.reduceN();
      i = len - len;
    }
    return a;
  }
  








  public GF2Polynomial quotient(GF2Polynomial g)
    throws RuntimeException
  {
    GF2Polynomial q = new GF2Polynomial(len);
    GF2Polynomial a = new GF2Polynomial(this);
    GF2Polynomial b = new GF2Polynomial(g);
    

    if (b.isZero())
    {
      throw new RuntimeException();
    }
    a.reduceN();
    b.reduceN();
    if (len < len)
    {
      return new GF2Polynomial(0);
    }
    int i = len - len;
    q.expandN(i + 1);
    
    while (i >= 0)
    {
      GF2Polynomial j = b.shiftLeft(i);
      a.subtractFromThis(j);
      a.reduceN();
      q.xorBit(i);
      i = len - len;
    }
    
    return q;
  }
  








  public GF2Polynomial[] divide(GF2Polynomial g)
    throws RuntimeException
  {
    GF2Polynomial[] result = new GF2Polynomial[2];
    GF2Polynomial q = new GF2Polynomial(len);
    GF2Polynomial a = new GF2Polynomial(this);
    GF2Polynomial b = new GF2Polynomial(g);
    

    if (b.isZero())
    {
      throw new RuntimeException();
    }
    a.reduceN();
    b.reduceN();
    if (len < len)
    {
      result[0] = new GF2Polynomial(0);
      result[1] = a;
      return result;
    }
    int i = len - len;
    q.expandN(i + 1);
    
    while (i >= 0)
    {
      GF2Polynomial j = b.shiftLeft(i);
      a.subtractFromThis(j);
      a.reduceN();
      q.xorBit(i);
      i = len - len;
    }
    
    result[0] = q;
    result[1] = a;
    return result;
  }
  








  public GF2Polynomial gcd(GF2Polynomial g)
    throws RuntimeException
  {
    if ((isZero()) && (g.isZero()))
    {
      throw new ArithmeticException("Both operands of gcd equal zero.");
    }
    if (isZero())
    {
      return new GF2Polynomial(g);
    }
    if (g.isZero())
    {
      return new GF2Polynomial(this);
    }
    GF2Polynomial a = new GF2Polynomial(this);
    GF2Polynomial b = new GF2Polynomial(g);
    

    while (!b.isZero())
    {
      GF2Polynomial c = a.remainder(b);
      a = b;
      b = c;
    }
    
    return a;
  }
  











  public boolean isIrreducible()
  {
    if (isZero())
    {
      return false;
    }
    GF2Polynomial f = new GF2Polynomial(this);
    


    f.reduceN();
    int d = len - 1;
    GF2Polynomial u = new GF2Polynomial(len, "X");
    GF2Polynomial g;
    for (int i = 1; i <= d >> 1; i++)
    {
      u.squareThisPreCalc();
      u = u.remainder(f);
      GF2Polynomial dummy = u.add(new GF2Polynomial(32, "X"));
      if (!dummy.isZero())
      {
        GF2Polynomial g = f.gcd(dummy);
        if (!g.isOne())
        {
          return false;
        }
      }
      else
      {
        return false;
      }
    }
    
    return true;
  }
  











  void reduceTrinomial(int m, int tc)
  {
    int p0 = m >>> 5;
    int q0 = 32 - (m & 0x1F);
    int p1 = m - tc >>> 5;
    int q1 = 32 - (m - tc & 0x1F);
    int max = (m << 1) - 2 >>> 5;
    int min = p0;
    for (int i = max; i > min; i--)
    {


      long t = value[i] & 0xFFFFFFFF;
      
      value[(i - p0 - 1)] ^= (int)(t << q0); int 
      
        tmp95_94 = (i - p0); int[] tmp95_88 = value;tmp95_88[tmp95_94] = ((int)(tmp95_88[tmp95_94] ^ t >>> 32 - q0));
      
      value[(i - p1 - 1)] ^= (int)(t << q1); int 
      
        tmp137_136 = (i - p1); int[] tmp137_130 = value;tmp137_130[tmp137_136] = ((int)(tmp137_130[tmp137_136] ^ t >>> 32 - q1));
      value[i] = 0;
    }
    
    long t = value[min] & 0xFFFFFFFF & 4294967295L << (m & 0x1F); int 
    
      tmp192_191 = 0; int[] tmp192_188 = value;tmp192_188[tmp192_191] = ((int)(tmp192_188[tmp192_191] ^ t >>> 32 - q0));
    if (min - p1 - 1 >= 0)
    {
      value[(min - p1 - 1)] ^= (int)(t << q1);
    }
    int tmp246_245 = (min - p1); int[] tmp246_238 = value;tmp246_238[tmp246_245] = ((int)(tmp246_238[tmp246_245] ^ t >>> 32 - q1));
    
    value[min] &= reverseRightMask[(m & 0x1F)];
    blocks = ((m - 1 >>> 5) + 1);
    len = m;
  }
  











  void reducePentanomial(int m, int[] pc)
  {
    int p0 = m >>> 5;
    int q0 = 32 - (m & 0x1F);
    int p1 = m - pc[0] >>> 5;
    int q1 = 32 - (m - pc[0] & 0x1F);
    int p2 = m - pc[1] >>> 5;
    int q2 = 32 - (m - pc[1] & 0x1F);
    int p3 = m - pc[2] >>> 5;
    int q3 = 32 - (m - pc[2] & 0x1F);
    int max = (m << 1) - 2 >>> 5;
    int min = p0;
    for (int i = max; i > min; i--)
    {
      long t = value[i] & 0xFFFFFFFF;
      value[(i - p0 - 1)] ^= (int)(t << q0); int 
        tmp143_142 = (i - p0); int[] tmp143_136 = value;tmp143_136[tmp143_142] = ((int)(tmp143_136[tmp143_142] ^ t >>> 32 - q0));
      value[(i - p1 - 1)] ^= (int)(t << q1); int 
        tmp185_184 = (i - p1); int[] tmp185_178 = value;tmp185_178[tmp185_184] = ((int)(tmp185_178[tmp185_184] ^ t >>> 32 - q1));
      value[(i - p2 - 1)] ^= (int)(t << q2); int 
        tmp227_226 = (i - p2); int[] tmp227_220 = value;tmp227_220[tmp227_226] = ((int)(tmp227_220[tmp227_226] ^ t >>> 32 - q2));
      value[(i - p3 - 1)] ^= (int)(t << q3); int 
        tmp269_268 = (i - p3); int[] tmp269_262 = value;tmp269_262[tmp269_268] = ((int)(tmp269_262[tmp269_268] ^ t >>> 32 - q3));
      value[i] = 0;
    }
    long t = value[min] & 0xFFFFFFFF & 4294967295L << (m & 0x1F); int 
      tmp324_323 = 0; int[] tmp324_320 = value;tmp324_320[tmp324_323] = ((int)(tmp324_320[tmp324_323] ^ t >>> 32 - q0));
    if (min - p1 - 1 >= 0)
    {
      value[(min - p1 - 1)] ^= (int)(t << q1);
    }
    int tmp378_377 = (min - p1); int[] tmp378_370 = value;tmp378_370[tmp378_377] = ((int)(tmp378_370[tmp378_377] ^ t >>> 32 - q1));
    if (min - p2 - 1 >= 0)
    {
      value[(min - p2 - 1)] ^= (int)(t << q2);
    }
    int tmp432_431 = (min - p2); int[] tmp432_424 = value;tmp432_424[tmp432_431] = ((int)(tmp432_424[tmp432_431] ^ t >>> 32 - q2));
    if (min - p3 - 1 >= 0)
    {
      value[(min - p3 - 1)] ^= (int)(t << q3);
    }
    int tmp486_485 = (min - p3); int[] tmp486_478 = value;tmp486_478[tmp486_485] = ((int)(tmp486_478[tmp486_485] ^ t >>> 32 - q3));
    value[min] &= reverseRightMask[(m & 0x1F)];
    
    blocks = ((m - 1 >>> 5) + 1);
    len = m;
  }
  





  public void reduceN()
  {
    int i = blocks - 1;
    while ((value[i] == 0) && (i > 0))
    {
      i--;
    }
    int h = value[i];
    int j = 0;
    while (h != 0)
    {
      h >>>= 1;
      j++;
    }
    len = ((i << 5) + j);
    blocks = (i + 1);
  }
  








  public void expandN(int i)
  {
    if (len >= i)
    {
      return;
    }
    len = i;
    int k = (i - 1 >>> 5) + 1;
    if (blocks >= k)
    {
      return;
    }
    if (value.length >= k)
    {

      for (int j = blocks; j < k; j++)
      {
        value[j] = 0;
      }
      blocks = k;
      return;
    }
    int[] bs = new int[k];
    System.arraycopy(value, 0, bs, 0, blocks);
    blocks = k;
    value = null;
    value = bs;
  }
  








  public void squareThisBitwise()
  {
    if (isZero())
    {
      return;
    }
    int[] result = new int[blocks << 1];
    for (int i = blocks - 1; i >= 0; i--)
    {
      int h = value[i];
      int j = 1;
      for (int k = 0; k < 16; k++)
      {
        if ((h & 0x1) != 0)
        {
          result[(i << 1)] |= j;
        }
        if ((h & 0x10000) != 0)
        {
          result[((i << 1) + 1)] |= j;
        }
        j <<= 2;
        h >>>= 1;
      }
    }
    value = null;
    value = result;
    blocks = result.length;
    len = ((len << 1) - 1);
  }
  





  public void squareThisPreCalc()
  {
    if (isZero())
    {
      return;
    }
    if (value.length >= blocks << 1)
    {
      for (int i = blocks - 1; i >= 0; i--)
      {
        value[((i << 1) + 1)] = (squaringTable[((value[i] & 0xFF0000) >>> 16)] | squaringTable[((value[i] & 0xFF000000) >>> 24)] << 16);
        
        value[(i << 1)] = (squaringTable[(value[i] & 0xFF)] | squaringTable[((value[i] & 0xFF00) >>> 8)] << 16);
      }
      
      blocks <<= 1;
      len = ((len << 1) - 1);
    }
    else
    {
      int[] result = new int[blocks << 1];
      for (int i = 0; i < blocks; i++)
      {
        result[(i << 1)] = (squaringTable[(value[i] & 0xFF)] | squaringTable[((value[i] & 0xFF00) >>> 8)] << 16);
        
        result[((i << 1) + 1)] = (squaringTable[((value[i] & 0xFF0000) >>> 16)] | squaringTable[((value[i] & 0xFF000000) >>> 24)] << 16);
      }
      
      value = null;
      value = result;
      blocks <<= 1;
      len = ((len << 1) - 1);
    }
  }
  








  public boolean vectorMult(GF2Polynomial b)
    throws RuntimeException
  {
    boolean result = false;
    if (len != len)
    {
      throw new RuntimeException();
    }
    for (int i = 0; i < blocks; i++)
    {
      int h = value[i] & value[i];
      result ^= parity[(h & 0xFF)];
      result ^= parity[(h >>> 8 & 0xFF)];
      result ^= parity[(h >>> 16 & 0xFF)];
      result ^= parity[(h >>> 24 & 0xFF)];
    }
    return result;
  }
  









  public GF2Polynomial xor(GF2Polynomial b)
  {
    int k = Math.min(blocks, blocks);
    if (len >= len)
    {
      GF2Polynomial result = new GF2Polynomial(this);
      for (int i = 0; i < k; i++)
      {
        value[i] ^= value[i];
      }
    }
    

    GF2Polynomial result = new GF2Polynomial(b);
    for (int i = 0; i < k; i++)
    {
      value[i] ^= value[i];
    }
    


    result.zeroUnusedBits();
    return result;
  }
  








  public void xorThisBy(GF2Polynomial b)
  {
    for (int i = 0; i < Math.min(blocks, blocks); i++)
    {
      value[i] ^= value[i];
    }
    

    zeroUnusedBits();
  }
  





  private void zeroUnusedBits()
  {
    if ((len & 0x1F) != 0)
    {
      value[(blocks - 1)] &= reverseRightMask[(len & 0x1F)];
    }
  }
  






  public void setBit(int i)
    throws RuntimeException
  {
    if ((i < 0) || (i > len - 1))
    {
      throw new RuntimeException();
    }
    value[(i >>> 5)] |= bitMask[(i & 0x1F)];
  }
  








  public int getBit(int i)
  {
    if (i < 0)
    {
      throw new RuntimeException();
    }
    if (i > len - 1)
    {
      return 0;
    }
    return (value[(i >>> 5)] & bitMask[(i & 0x1F)]) != 0 ? 1 : 0;
  }
  






  public void resetBit(int i)
    throws RuntimeException
  {
    if (i < 0)
    {
      throw new RuntimeException();
    }
    if (i > len - 1)
    {
      return;
    }
    value[(i >>> 5)] &= (bitMask[(i & 0x1F)] ^ 0xFFFFFFFF);
  }
  






  public void xorBit(int i)
    throws RuntimeException
  {
    if ((i < 0) || (i > len - 1))
    {
      throw new RuntimeException();
    }
    value[(i >>> 5)] ^= bitMask[(i & 0x1F)];
  }
  







  public boolean testBit(int i)
  {
    if (i < 0)
    {
      throw new RuntimeException();
    }
    if (i > len - 1)
    {
      return false;
    }
    return (value[(i >>> 5)] & bitMask[(i & 0x1F)]) != 0;
  }
  





  public GF2Polynomial shiftLeft()
  {
    GF2Polynomial result = new GF2Polynomial(len + 1, value);
    
    for (int i = blocks - 1; i >= 1; i--)
    {
      value[i] <<= 1;
      value[i] |= value[(i - 1)] >>> 31;
    }
    value[0] <<= 1;
    return result;
  }
  





  public void shiftLeftThis()
  {
    if ((len & 0x1F) == 0)
    {
      len += 1;
      blocks += 1;
      if (blocks > value.length)
      {
        int[] bs = new int[blocks];
        System.arraycopy(value, 0, bs, 0, value.length);
        value = null;
        value = bs;
      }
      for (int i = blocks - 1; i >= 1; i--)
      {
        value[i] |= value[(i - 1)] >>> 31;
        value[(i - 1)] <<= 1;
      }
    }
    

    len += 1;
    for (int i = blocks - 1; i >= 1; i--)
    {
      value[i] <<= 1;
      value[i] |= value[(i - 1)] >>> 31;
    }
    value[0] <<= 1;
  }
  













  public GF2Polynomial shiftLeft(int k)
  {
    GF2Polynomial result = new GF2Polynomial(len + k, value);
    
    if (k >= 32)
    {
      result.doShiftBlocksLeft(k >>> 5);
    }
    
    int remaining = k & 0x1F;
    if (remaining != 0)
    {
      for (int i = blocks - 1; i >= 1; i--)
      {
        value[i] <<= remaining;
        value[i] |= value[(i - 1)] >>> 32 - remaining;
      }
      value[0] <<= remaining;
    }
    return result;
  }
  








  public void shiftLeftAddThis(GF2Polynomial b, int k)
  {
    if (k == 0)
    {
      addToThis(b);
      return;
    }
    
    expandN(len + k);
    int d = k >>> 5;
    for (int i = blocks - 1; i >= 0; i--)
    {
      if ((i + d + 1 < blocks) && ((k & 0x1F) != 0))
      {
        value[(i + d + 1)] ^= value[i] >>> 32 - (k & 0x1F);
      }
      value[(i + d)] ^= value[i] << (k & 0x1F);
    }
  }
  






  void shiftBlocksLeft()
  {
    blocks += 1;
    len += 32;
    if (blocks <= value.length)
    {

      for (int i = blocks - 1; i >= 1; i--)
      {
        value[i] = value[(i - 1)];
      }
      value[0] = 0;
    }
    else
    {
      int[] result = new int[blocks];
      System.arraycopy(value, 0, result, 1, blocks - 1);
      value = null;
      value = result;
    }
  }
  







  private void doShiftBlocksLeft(int b)
  {
    if (blocks <= value.length)
    {

      for (int i = blocks - 1; i >= b; i--)
      {
        value[i] = value[(i - b)];
      }
      for (i = 0; i < b; i++)
      {
        value[i] = 0;
      }
    }
    else
    {
      int[] result = new int[blocks];
      System.arraycopy(value, 0, result, b, blocks - b);
      value = null;
      value = result;
    }
  }
  





  public GF2Polynomial shiftRight()
  {
    GF2Polynomial result = new GF2Polynomial(len - 1);
    
    System.arraycopy(value, 0, value, 0, blocks);
    for (int i = 0; i <= blocks - 2; i++)
    {
      value[i] >>>= 1;
      value[i] |= value[(i + 1)] << 31;
    }
    value[(blocks - 1)] >>>= 1;
    if (blocks < blocks)
    {
      value[(blocks - 1)] |= value[blocks] << 31;
    }
    return result;
  }
  




  public void shiftRightThis()
  {
    len -= 1;
    blocks = ((len - 1 >>> 5) + 1);
    for (int i = 0; i <= blocks - 2; i++)
    {
      value[i] >>>= 1;
      value[i] |= value[(i + 1)] << 31;
    }
    value[(blocks - 1)] >>>= 1;
    if ((len & 0x1F) == 0)
    {
      value[(blocks - 1)] |= value[blocks] << 31;
    }
  }
}
