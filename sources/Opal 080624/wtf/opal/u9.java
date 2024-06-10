package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class u9 {
  private static final int Z;
  
  private static final int h;
  
  private static final int w;
  
  private static String[] E;
  
  private static final long a = on.a(6279496149606510632L, -6447412359875839052L, MethodHandles.lookup().lookupClass()).a(222443607548385L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public static byte[] c(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast [B
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast [B
    //   15: astore #5
    //   17: dup
    //   18: iconst_2
    //   19: aaload
    //   20: checkcast [B
    //   23: astore_3
    //   24: dup
    //   25: iconst_3
    //   26: aaload
    //   27: checkcast java/lang/Long
    //   30: invokevirtual longValue : ()J
    //   33: lstore_1
    //   34: pop
    //   35: getstatic wtf/opal/u9.a : J
    //   38: lload_1
    //   39: lxor
    //   40: lstore_1
    //   41: invokestatic I : ()[Ljava/lang/String;
    //   44: astore #6
    //   46: aload #4
    //   48: aload #6
    //   50: ifnonnull -> 77
    //   53: ifnonnull -> 75
    //   56: goto -> 63
    //   59: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   62: athrow
    //   63: new java/lang/IllegalArgumentException
    //   66: dup
    //   67: invokespecial <init> : ()V
    //   70: athrow
    //   71: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   74: athrow
    //   75: aload #5
    //   77: aload #6
    //   79: lload_1
    //   80: lconst_0
    //   81: lcmp
    //   82: iflt -> 114
    //   85: ifnonnull -> 112
    //   88: ifnonnull -> 110
    //   91: goto -> 98
    //   94: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   97: athrow
    //   98: new java/lang/IllegalArgumentException
    //   101: dup
    //   102: invokespecial <init> : ()V
    //   105: athrow
    //   106: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   109: athrow
    //   110: aload #5
    //   112: aload #6
    //   114: lload_1
    //   115: lconst_0
    //   116: lcmp
    //   117: ifle -> 168
    //   120: ifnonnull -> 160
    //   123: arraylength
    //   124: sipush #17776
    //   127: ldc2_w 4969978377336900337
    //   130: lload_1
    //   131: lxor
    //   132: <illegal opcode> u : (IJ)I
    //   137: if_icmpeq -> 159
    //   140: goto -> 147
    //   143: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   146: athrow
    //   147: new java/lang/IllegalArgumentException
    //   150: dup
    //   151: invokespecial <init> : ()V
    //   154: athrow
    //   155: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   158: athrow
    //   159: aload_3
    //   160: lload_1
    //   161: lconst_0
    //   162: lcmp
    //   163: ifle -> 194
    //   166: aload #6
    //   168: ifnonnull -> 194
    //   171: ifnonnull -> 193
    //   174: goto -> 181
    //   177: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   180: athrow
    //   181: new java/lang/IllegalArgumentException
    //   184: dup
    //   185: invokespecial <init> : ()V
    //   188: athrow
    //   189: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   192: athrow
    //   193: aload_3
    //   194: arraylength
    //   195: lload_1
    //   196: lconst_0
    //   197: lcmp
    //   198: iflt -> 232
    //   201: sipush #26593
    //   204: ldc2_w 7517531667680094308
    //   207: lload_1
    //   208: lxor
    //   209: <illegal opcode> u : (IJ)I
    //   214: if_icmpeq -> 229
    //   217: new java/lang/IllegalArgumentException
    //   220: dup
    //   221: invokespecial <init> : ()V
    //   224: athrow
    //   225: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   228: athrow
    //   229: sipush #2927
    //   232: ldc2_w 6136080052355610920
    //   235: lload_1
    //   236: lxor
    //   237: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   242: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   245: astore #7
    //   247: new javax/crypto/spec/SecretKeySpec
    //   250: dup
    //   251: aload #5
    //   253: sipush #10409
    //   256: ldc2_w 3280526355315268335
    //   259: lload_1
    //   260: lxor
    //   261: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   266: invokespecial <init> : ([BLjava/lang/String;)V
    //   269: astore #8
    //   271: new javax/crypto/spec/GCMParameterSpec
    //   274: dup
    //   275: sipush #13623
    //   278: ldc2_w 7864966408201157303
    //   281: lload_1
    //   282: lxor
    //   283: <illegal opcode> u : (IJ)I
    //   288: aload_3
    //   289: invokespecial <init> : (I[B)V
    //   292: astore #9
    //   294: aload #7
    //   296: iconst_2
    //   297: aload #8
    //   299: aload #9
    //   301: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   304: aload #7
    //   306: aload #4
    //   308: invokevirtual doFinal : ([B)[B
    //   311: areturn
    //   312: astore #7
    //   314: aconst_null
    //   315: areturn
    // Exception table:
    //   from	to	target	type
    //   46	56	59	java/lang/Exception
    //   46	311	312	java/lang/Exception
    //   53	71	71	java/lang/Exception
    //   77	91	94	java/lang/Exception
    //   88	106	106	java/lang/Exception
    //   112	140	143	java/lang/Exception
    //   123	155	155	java/lang/Exception
    //   160	174	177	java/lang/Exception
    //   171	189	189	java/lang/Exception
    //   194	225	225	java/lang/Exception
  }
  
  public static void j(String[] paramArrayOfString) {
    E = paramArrayOfString;
  }
  
  public static String[] I() {
    return E;
  }
  
  static {
    j(null);
    long l = a ^ 0x76715790685FL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "M\000ý9f.1\n\r\016{e;\016\005¥\"\0037/Ó;?CðÝ×X&Æc«Y&\020¢u®øv\0028Öì;é").length();
    byte b2 = 40;
    byte b = -1;
    while (true);
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    byte b1 = 0;
    int i;
    char[] arrayOfChar = new char[i = paramArrayOfbyte.length];
    for (byte b2 = 0; b2 < i; b2++) {
      int j;
      if ((j = 0xFF & paramArrayOfbyte[b2]) < 192) {
        arrayOfChar[b1++] = (char)j;
      } else if (j < 224) {
        char c = (char)((char)(j & 0x1F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b2 < i - 2) {
        char c = (char)((char)(j & 0xF) << 12);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5B9E;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])d.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/u9", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      c[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return c[i];
  }
  
  private static Object a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    String str = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(String.class, str);
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return str;
  }
  
  private static CallSite a(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
    // Byte code:
    //   0: new java/lang/invoke/MutableCallSite
    //   3: dup
    //   4: aload_2
    //   5: invokespecial <init> : (Ljava/lang/invoke/MethodType;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ldc_w
    //   13: ldc [Ljava/lang/Object;
    //   15: aload_2
    //   16: invokevirtual parameterCount : ()I
    //   19: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   22: iconst_0
    //   23: iconst_3
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_3
    //   34: aastore
    //   35: dup
    //   36: iconst_2
    //   37: aload_1
    //   38: aastore
    //   39: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   42: aload_2
    //   43: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   46: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/u9'
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: ldc_w ' : '
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload_1
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc_w ' : '
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: aload #4
    //   99: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: athrow
    //   103: aload_3
    //   104: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6659;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])g.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/u9", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
  }
  
  private static int b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = b(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite b(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
    // Byte code:
    //   0: new java/lang/invoke/MutableCallSite
    //   3: dup
    //   4: aload_2
    //   5: invokespecial <init> : (Ljava/lang/invoke/MethodType;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ldc_w
    //   13: ldc [Ljava/lang/Object;
    //   15: aload_2
    //   16: invokevirtual parameterCount : ()I
    //   19: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   22: iconst_0
    //   23: iconst_3
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_3
    //   34: aastore
    //   35: dup
    //   36: iconst_2
    //   37: aload_1
    //   38: aastore
    //   39: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   42: aload_2
    //   43: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   46: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/u9'
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: ldc_w ' : '
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload_1
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc_w ' : '
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: aload #4
    //   99: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: athrow
    //   103: aload_3
    //   104: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u9.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */