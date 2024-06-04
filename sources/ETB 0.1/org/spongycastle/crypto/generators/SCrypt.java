package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.engines.Salsa20Engine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;




















public class SCrypt
{
  public SCrypt() {}
  
  public static byte[] generate(byte[] P, byte[] S, int N, int r, int p, int dkLen)
  {
    if (P == null)
    {
      throw new IllegalArgumentException("Passphrase P must be provided.");
    }
    if (S == null)
    {
      throw new IllegalArgumentException("Salt S must be provided.");
    }
    if (N <= 1)
    {
      throw new IllegalArgumentException("Cost parameter N must be > 1.");
    }
    
    if ((r == 1) && (N > 65536))
    {
      throw new IllegalArgumentException("Cost parameter N must be > 1 and < 65536.");
    }
    if (r < 1)
    {
      throw new IllegalArgumentException("Block size r must be >= 1.");
    }
    int maxParallel = Integer.MAX_VALUE / (128 * r * 8);
    if ((p < 1) || (p > maxParallel))
    {
      throw new IllegalArgumentException("Parallelisation parameter p must be >= 1 and <= " + maxParallel + " (based on block size r of " + r + ")");
    }
    
    if (dkLen < 1)
    {
      throw new IllegalArgumentException("Generated key length dkLen must be >= 1.");
    }
    return MFcrypt(P, S, N, r, p, dkLen);
  }
  
  private static byte[] MFcrypt(byte[] P, byte[] S, int N, int r, int p, int dkLen)
  {
    int MFLenBytes = r * 128;
    byte[] bytes = SingleIterationPBKDF2(P, S, p * MFLenBytes);
    
    int[] B = null;
    
    try
    {
      int BLen = bytes.length >>> 2;
      B = new int[BLen];
      
      Pack.littleEndianToInt(bytes, 0, B);
      
      int MFLenWords = MFLenBytes >>> 2;
      for (int BOff = 0; BOff < BLen; BOff += MFLenWords)
      {

        SMix(B, BOff, N, r);
      }
      
      Pack.intToLittleEndian(B, bytes, 0);
      
      return SingleIterationPBKDF2(P, bytes, dkLen);
    }
    finally
    {
      Clear(bytes);
      Clear(B);
    }
  }
  
  private static byte[] SingleIterationPBKDF2(byte[] P, byte[] S, int dkLen)
  {
    PBEParametersGenerator pGen = new PKCS5S2ParametersGenerator(new SHA256Digest());
    pGen.init(P, S, 1);
    KeyParameter key = (KeyParameter)pGen.generateDerivedMacParameters(dkLen * 8);
    return key.getKey();
  }
  
  /* Error */
  private static void SMix(int[] B, int BOff, int N, int r)
  {
    // Byte code:
    //   0: iload_3
    //   1: bipush 32
    //   3: imul
    //   4: istore 4
    //   6: bipush 16
    //   8: newarray int
    //   10: astore 5
    //   12: bipush 16
    //   14: newarray int
    //   16: astore 6
    //   18: iload 4
    //   20: newarray int
    //   22: astore 7
    //   24: iload 4
    //   26: newarray int
    //   28: astore 8
    //   30: iload_2
    //   31: anewarray 125	[I
    //   34: astore 9
    //   36: aload_0
    //   37: iload_1
    //   38: aload 8
    //   40: iconst_0
    //   41: iload 4
    //   43: invokestatic 131	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   46: iconst_0
    //   47: istore 10
    //   49: iload 10
    //   51: iload_2
    //   52: if_icmpge +31 -> 83
    //   55: aload 9
    //   57: iload 10
    //   59: aload 8
    //   61: invokestatic 137	org/spongycastle/util/Arrays:clone	([I)[I
    //   64: aastore
    //   65: aload 8
    //   67: aload 5
    //   69: aload 6
    //   71: aload 7
    //   73: iload_3
    //   74: invokestatic 141	org/spongycastle/crypto/generators/SCrypt:BlockMix	([I[I[I[II)V
    //   77: iinc 10 1
    //   80: goto -31 -> 49
    //   83: iload_2
    //   84: iconst_1
    //   85: isub
    //   86: istore 10
    //   88: iconst_0
    //   89: istore 11
    //   91: iload 11
    //   93: iload_2
    //   94: if_icmpge +47 -> 141
    //   97: aload 8
    //   99: iload 4
    //   101: bipush 16
    //   103: isub
    //   104: iaload
    //   105: iload 10
    //   107: iand
    //   108: istore 12
    //   110: aload 8
    //   112: aload 9
    //   114: iload 12
    //   116: aaload
    //   117: iconst_0
    //   118: aload 8
    //   120: invokestatic 145	org/spongycastle/crypto/generators/SCrypt:Xor	([I[II[I)V
    //   123: aload 8
    //   125: aload 5
    //   127: aload 6
    //   129: aload 7
    //   131: iload_3
    //   132: invokestatic 141	org/spongycastle/crypto/generators/SCrypt:BlockMix	([I[I[I[II)V
    //   135: iinc 11 1
    //   138: goto -47 -> 91
    //   141: aload 8
    //   143: iconst_0
    //   144: aload_0
    //   145: iload_1
    //   146: iload 4
    //   148: invokestatic 131	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   151: aload 9
    //   153: invokestatic 149	org/spongycastle/crypto/generators/SCrypt:ClearAll	([[I)V
    //   156: iconst_4
    //   157: anewarray 125	[I
    //   160: dup
    //   161: iconst_0
    //   162: aload 8
    //   164: aastore
    //   165: dup
    //   166: iconst_1
    //   167: aload 5
    //   169: aastore
    //   170: dup
    //   171: iconst_2
    //   172: aload 6
    //   174: aastore
    //   175: dup
    //   176: iconst_3
    //   177: aload 7
    //   179: aastore
    //   180: invokestatic 149	org/spongycastle/crypto/generators/SCrypt:ClearAll	([[I)V
    //   183: goto +40 -> 223
    //   186: astore 13
    //   188: aload 9
    //   190: invokestatic 149	org/spongycastle/crypto/generators/SCrypt:ClearAll	([[I)V
    //   193: iconst_4
    //   194: anewarray 125	[I
    //   197: dup
    //   198: iconst_0
    //   199: aload 8
    //   201: aastore
    //   202: dup
    //   203: iconst_1
    //   204: aload 5
    //   206: aastore
    //   207: dup
    //   208: iconst_2
    //   209: aload 6
    //   211: aastore
    //   212: dup
    //   213: iconst_3
    //   214: aload 7
    //   216: aastore
    //   217: invokestatic 149	org/spongycastle/crypto/generators/SCrypt:ClearAll	([[I)V
    //   220: aload 13
    //   222: athrow
    //   223: return
    // Line number table:
    //   Java source line #111	-> byte code offset #0
    //   Java source line #113	-> byte code offset #6
    //   Java source line #114	-> byte code offset #12
    //   Java source line #115	-> byte code offset #18
    //   Java source line #117	-> byte code offset #24
    //   Java source line #118	-> byte code offset #30
    //   Java source line #122	-> byte code offset #36
    //   Java source line #124	-> byte code offset #46
    //   Java source line #126	-> byte code offset #55
    //   Java source line #127	-> byte code offset #65
    //   Java source line #124	-> byte code offset #77
    //   Java source line #130	-> byte code offset #83
    //   Java source line #131	-> byte code offset #88
    //   Java source line #133	-> byte code offset #97
    //   Java source line #134	-> byte code offset #110
    //   Java source line #135	-> byte code offset #123
    //   Java source line #131	-> byte code offset #135
    //   Java source line #138	-> byte code offset #141
    //   Java source line #142	-> byte code offset #151
    //   Java source line #143	-> byte code offset #156
    //   Java source line #144	-> byte code offset #183
    //   Java source line #142	-> byte code offset #186
    //   Java source line #143	-> byte code offset #193
    //   Java source line #145	-> byte code offset #223
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	224	0	B	int[]
    //   0	224	1	BOff	int
    //   0	224	2	N	int
    //   0	224	3	r	int
    //   4	143	4	BCount	int
    //   10	195	5	blockX1	int[]
    //   16	194	6	blockX2	int[]
    //   22	193	7	blockY	int[]
    //   28	172	8	X	int[]
    //   34	155	9	V	int[][]
    //   47	31	10	i	int
    //   86	20	10	mask	int
    //   89	47	11	i	int
    //   108	7	12	j	int
    //   186	35	13	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   36	151	186	finally
    //   186	188	186	finally
  }
  
  private static void BlockMix(int[] B, int[] X1, int[] X2, int[] Y, int r)
  {
    System.arraycopy(B, B.length - 16, X1, 0, 16);
    
    int BOff = 0;int YOff = 0;int halfLen = B.length >>> 1;
    
    for (int i = 2 * r; i > 0; i--)
    {
      Xor(X1, B, BOff, X2);
      
      Salsa20Engine.salsaCore(8, X2, X1);
      System.arraycopy(X1, 0, Y, YOff, 16);
      
      YOff = halfLen + BOff - YOff;
      BOff += 16;
    }
    
    System.arraycopy(Y, 0, B, 0, Y.length);
  }
  
  private static void Xor(int[] a, int[] b, int bOff, int[] output)
  {
    for (int i = output.length - 1; i >= 0; i--)
    {
      a[i] ^= b[(bOff + i)];
    }
  }
  
  private static void Clear(byte[] array)
  {
    if (array != null)
    {
      Arrays.fill(array, (byte)0);
    }
  }
  
  private static void Clear(int[] array)
  {
    if (array != null)
    {
      Arrays.fill(array, 0);
    }
  }
  
  private static void ClearAll(int[][] arrays)
  {
    for (int i = 0; i < arrays.length; i++)
    {
      Clear(arrays[i]);
    }
  }
}
