package org.spongycastle.pqc.crypto.xmss;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.encoders.Hex;









public class XMSSUtil
{
  public XMSSUtil() {}
  
  public static int log2(int n)
  {
    int log = 0;
    while (n >>= 1 != 0)
    {
      log++;
    }
    return log;
  }
  







  public static byte[] toBytesBigEndian(long value, int sizeInByte)
  {
    byte[] out = new byte[sizeInByte];
    for (int i = sizeInByte - 1; i >= 0; i--)
    {
      out[i] = ((byte)(int)value);
      value >>>= 8;
    }
    return out;
  }
  



  public static void longToBigEndian(long value, byte[] in, int offset)
  {
    if (in == null)
    {
      throw new NullPointerException("in == null");
    }
    if (in.length - offset < 8)
    {
      throw new IllegalArgumentException("not enough space in array");
    }
    in[offset] = ((byte)(int)(value >> 56 & 0xFF));
    in[(offset + 1)] = ((byte)(int)(value >> 48 & 0xFF));
    in[(offset + 2)] = ((byte)(int)(value >> 40 & 0xFF));
    in[(offset + 3)] = ((byte)(int)(value >> 32 & 0xFF));
    in[(offset + 4)] = ((byte)(int)(value >> 24 & 0xFF));
    in[(offset + 5)] = ((byte)(int)(value >> 16 & 0xFF));
    in[(offset + 6)] = ((byte)(int)(value >> 8 & 0xFF));
    in[(offset + 7)] = ((byte)(int)(value & 0xFF));
  }
  



  public static long bytesToXBigEndian(byte[] in, int offset, int size)
  {
    if (in == null)
    {
      throw new NullPointerException("in == null");
    }
    long res = 0L;
    for (int i = offset; i < offset + size; i++)
    {
      res = res << 8 | in[i] & 0xFF;
    }
    return res;
  }
  






  public static byte[] cloneArray(byte[] in)
  {
    if (in == null)
    {
      throw new NullPointerException("in == null");
    }
    byte[] out = new byte[in.length];
    for (int i = 0; i < in.length; i++)
    {
      out[i] = in[i];
    }
    return out;
  }
  






  public static byte[][] cloneArray(byte[][] in)
  {
    if (hasNullPointer(in))
    {
      throw new NullPointerException("in has null pointers");
    }
    byte[][] out = new byte[in.length][];
    for (int i = 0; i < in.length; i++)
    {
      out[i] = new byte[in[i].length];
      for (int j = 0; j < in[i].length; j++)
      {
        out[i][j] = in[i][j];
      }
    }
    return out;
  }
  







  public static boolean areEqual(byte[][] a, byte[][] b)
  {
    if ((hasNullPointer(a)) || (hasNullPointer(b)))
    {
      throw new NullPointerException("a or b == null");
    }
    for (int i = 0; i < a.length; i++)
    {
      if (!Arrays.areEqual(a[i], b[i]))
      {
        return false;
      }
    }
    return true;
  }
  





  public static void dumpByteArray(byte[][] x)
  {
    if (hasNullPointer(x))
    {
      throw new NullPointerException("x has null pointers");
    }
    for (int i = 0; i < x.length; i++)
    {
      System.out.println(Hex.toHexString(x[i]));
    }
  }
  






  public static boolean hasNullPointer(byte[][] in)
  {
    if (in == null)
    {
      return true;
    }
    for (int i = 0; i < in.length; i++)
    {
      if (in[i] == null)
      {
        return true;
      }
    }
    return false;
  }
  







  public static void copyBytesAtOffset(byte[] dst, byte[] src, int offset)
  {
    if (dst == null)
    {
      throw new NullPointerException("dst == null");
    }
    if (src == null)
    {
      throw new NullPointerException("src == null");
    }
    if (offset < 0)
    {
      throw new IllegalArgumentException("offset hast to be >= 0");
    }
    if (src.length + offset > dst.length)
    {
      throw new IllegalArgumentException("src length + offset must not be greater than size of destination");
    }
    for (int i = 0; i < src.length; i++)
    {
      dst[(offset + i)] = src[i];
    }
  }
  








  public static byte[] extractBytesAtOffset(byte[] src, int offset, int length)
  {
    if (src == null)
    {
      throw new NullPointerException("src == null");
    }
    if (offset < 0)
    {
      throw new IllegalArgumentException("offset hast to be >= 0");
    }
    if (length < 0)
    {
      throw new IllegalArgumentException("length hast to be >= 0");
    }
    if (offset + length > src.length)
    {
      throw new IllegalArgumentException("offset + length must not be greater then size of source array");
    }
    byte[] out = new byte[length];
    for (int i = 0; i < out.length; i++)
    {
      out[i] = src[(offset + i)];
    }
    return out;
  }
  







  public static boolean isIndexValid(int height, long index)
  {
    if (index < 0L)
    {
      throw new IllegalStateException("index must not be negative");
    }
    return index < 1L << height;
  }
  






  public static int getDigestSize(Digest digest)
  {
    if (digest == null)
    {
      throw new NullPointerException("digest == null");
    }
    String algorithmName = digest.getAlgorithmName();
    if (algorithmName.equals("SHAKE128"))
    {
      return 32;
    }
    if (algorithmName.equals("SHAKE256"))
    {
      return 64;
    }
    return digest.getDigestSize();
  }
  
  public static long getTreeIndex(long index, int xmssTreeHeight)
  {
    return index >> xmssTreeHeight;
  }
  
  public static int getLeafIndex(long index, int xmssTreeHeight)
  {
    return (int)(index & (1L << xmssTreeHeight) - 1L);
  }
  
  public static byte[] serialize(Object obj)
    throws IOException
  {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(out);
    oos.writeObject(obj);
    oos.flush();
    return out.toByteArray();
  }
  
  public static Object deserialize(byte[] data)
    throws IOException, ClassNotFoundException
  {
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ObjectInputStream is = new ObjectInputStream(in);
    return is.readObject();
  }
  
  public static int calculateTau(int index, int height)
  {
    int tau = 0;
    for (int i = 0; i < height; i++)
    {
      if ((index >> i & 0x1) == 0)
      {
        tau = i;
        break;
      }
    }
    return tau;
  }
  
  public static boolean isNewBDSInitNeeded(long globalIndex, int xmssHeight, int layer)
  {
    if (globalIndex == 0L)
    {
      return false;
    }
    return globalIndex % Math.pow(1 << xmssHeight, layer + 1) == 0L;
  }
  
  public static boolean isNewAuthenticationPathNeeded(long globalIndex, int xmssHeight, int layer)
  {
    if (globalIndex == 0L)
    {
      return false;
    }
    return (globalIndex + 1L) % Math.pow(1 << xmssHeight, layer) == 0L;
  }
}
