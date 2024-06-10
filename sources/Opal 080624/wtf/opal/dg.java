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

public class dg {
  private final String i;
  
  private final long S;
  
  private static int[] t;
  
  private static final long a = on.a(1190692956352538078L, -2159114523567324457L, MethodHandles.lookup().lookupClass()).a(33485507468735L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long e;
  
  public dg(String paramString, long paramLong) {
    this.i = paramString;
    this.S = paramLong;
  }
  
  public static dg k(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/kq
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_1
    //   18: pop
    //   19: getstatic wtf/opal/dg.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 94718941031526
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 106021772796652
    //   37: lxor
    //   38: lstore #6
    //   40: pop2
    //   41: new wtf/opal/dg
    //   44: dup
    //   45: aload_3
    //   46: lload #4
    //   48: sipush #24224
    //   51: ldc2_w 485170367714985630
    //   54: lload_1
    //   55: lxor
    //   56: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   61: iconst_2
    //   62: anewarray java/lang/Object
    //   65: dup_x1
    //   66: swap
    //   67: iconst_1
    //   68: swap
    //   69: aastore
    //   70: dup_x2
    //   71: dup_x2
    //   72: pop
    //   73: invokestatic valueOf : (J)Ljava/lang/Long;
    //   76: iconst_0
    //   77: swap
    //   78: aastore
    //   79: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   82: invokevirtual F : ()Ljava/lang/String;
    //   85: invokestatic currentTimeMillis : ()J
    //   88: aload_3
    //   89: lload #4
    //   91: sipush #10325
    //   94: ldc2_w 5835858818362732650
    //   97: lload_1
    //   98: lxor
    //   99: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   104: iconst_2
    //   105: anewarray java/lang/Object
    //   108: dup_x1
    //   109: swap
    //   110: iconst_1
    //   111: swap
    //   112: aastore
    //   113: dup_x2
    //   114: dup_x2
    //   115: pop
    //   116: invokestatic valueOf : (J)Ljava/lang/Long;
    //   119: iconst_0
    //   120: swap
    //   121: aastore
    //   122: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   125: lload #6
    //   127: iconst_1
    //   128: anewarray java/lang/Object
    //   131: dup_x2
    //   132: dup_x2
    //   133: pop
    //   134: invokestatic valueOf : (J)Ljava/lang/Long;
    //   137: iconst_0
    //   138: swap
    //   139: aastore
    //   140: invokevirtual J : ([Ljava/lang/Object;)J
    //   143: getstatic wtf/opal/dg.e : J
    //   146: lmul
    //   147: ladd
    //   148: invokespecial <init> : (Ljava/lang/String;J)V
    //   151: areturn
  }
  
  public final String c(Object[] paramArrayOfObject) {
    return this.i;
  }
  
  public final long j(Object[] paramArrayOfObject) {
    return this.S;
  }
  
  public static void S(int[] paramArrayOfint) {
    t = paramArrayOfint;
  }
  
  public static int[] i() {
    return t;
  }
  
  static {
    long l = a ^ 0x602F9ABBD409L;
    S(null);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "­ã¬è\034¥§\002çLÇ}\"ùMÓÀ6É^þ\036*îç­¹ã /ÿMn.¸X±#\016\006\001b\024 RÐ\020ËÛ¨¢ÀpÿO").length();
    byte b2 = 32;
    byte b = -1;
    while (true);
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x34CB;
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
        throw new RuntimeException("wtf/opal/dg", exception);
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
    //   49: goto -> 102
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc 'wtf/opal/dg'
    //   67: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: ldc_w ' : '
    //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: aload_1
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: ldc_w ' : '
    //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: aload_2
    //   87: invokevirtual toString : ()Ljava/lang/String;
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: invokevirtual toString : ()Ljava/lang/String;
    //   96: aload #4
    //   98: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   101: athrow
    //   102: aload_3
    //   103: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */