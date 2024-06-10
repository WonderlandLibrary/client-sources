package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class ka {
  private final UUID a;
  
  private final String D;
  
  private static final long b = on.a(-2122792144833147454L, -3001207383853185795L, MethodHandles.lookup().lookupClass()).a(146982985684899L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  private static final long[] f;
  
  private static final Integer[] g;
  
  private static final Map h;
  
  public ka(UUID paramUUID, String paramString) {
    this.a = paramUUID;
    this.D = paramString;
  }
  
  private static UUID y(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_1
    //   18: pop
    //   19: getstatic wtf/opal/ka.b : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: new java/lang/StringBuilder
    //   28: dup
    //   29: aload_3
    //   30: invokespecial <init> : (Ljava/lang/String;)V
    //   33: sipush #5948
    //   36: ldc2_w 2884860695779989897
    //   39: lload_1
    //   40: lxor
    //   41: <illegal opcode> v : (IJ)I
    //   46: ldc '-'
    //   48: invokevirtual insert : (ILjava/lang/String;)Ljava/lang/StringBuilder;
    //   51: sipush #26459
    //   54: ldc2_w 2538267938925586927
    //   57: lload_1
    //   58: lxor
    //   59: <illegal opcode> v : (IJ)I
    //   64: ldc '-'
    //   66: invokevirtual insert : (ILjava/lang/String;)Ljava/lang/StringBuilder;
    //   69: sipush #22819
    //   72: ldc2_w 6627192200122472341
    //   75: lload_1
    //   76: lxor
    //   77: <illegal opcode> v : (IJ)I
    //   82: ldc '-'
    //   84: invokevirtual insert : (ILjava/lang/String;)Ljava/lang/StringBuilder;
    //   87: sipush #10902
    //   90: ldc2_w 7565271840520201249
    //   93: lload_1
    //   94: lxor
    //   95: <illegal opcode> v : (IJ)I
    //   100: ldc '-'
    //   102: invokevirtual insert : (ILjava/lang/String;)Ljava/lang/StringBuilder;
    //   105: invokevirtual toString : ()Ljava/lang/String;
    //   108: invokestatic fromString : (Ljava/lang/String;)Ljava/util/UUID;
    //   111: areturn
  }
  
  public static ka O(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast wtf/opal/kq
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/ka.b : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 79011435554199
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 35596994536840
    //   37: lxor
    //   38: lstore #6
    //   40: pop2
    //   41: new wtf/opal/ka
    //   44: dup
    //   45: aload_3
    //   46: lload #6
    //   48: sipush #24480
    //   51: ldc2_w 1550781742598056038
    //   54: lload_1
    //   55: lxor
    //   56: <illegal opcode> k : (IJ)Ljava/lang/String;
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
    //   85: lload #4
    //   87: iconst_2
    //   88: anewarray java/lang/Object
    //   91: dup_x2
    //   92: dup_x2
    //   93: pop
    //   94: invokestatic valueOf : (J)Ljava/lang/Long;
    //   97: iconst_1
    //   98: swap
    //   99: aastore
    //   100: dup_x1
    //   101: swap
    //   102: iconst_0
    //   103: swap
    //   104: aastore
    //   105: invokestatic y : ([Ljava/lang/Object;)Ljava/util/UUID;
    //   108: aload_3
    //   109: lload #6
    //   111: sipush #32699
    //   114: ldc2_w 8811650722047412348
    //   117: lload_1
    //   118: lxor
    //   119: <illegal opcode> k : (IJ)Ljava/lang/String;
    //   124: iconst_2
    //   125: anewarray java/lang/Object
    //   128: dup_x1
    //   129: swap
    //   130: iconst_1
    //   131: swap
    //   132: aastore
    //   133: dup_x2
    //   134: dup_x2
    //   135: pop
    //   136: invokestatic valueOf : (J)Ljava/lang/Long;
    //   139: iconst_0
    //   140: swap
    //   141: aastore
    //   142: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   145: invokevirtual F : ()Ljava/lang/String;
    //   148: invokespecial <init> : (Ljava/util/UUID;Ljava/lang/String;)V
    //   151: areturn
  }
  
  public final String j(Object[] paramArrayOfObject) {
    return this.D;
  }
  
  public final UUID Z(Object[] paramArrayOfObject) {
    return this.a;
  }
  
  static {
    long l = b ^ 0x3A330C317E9BL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "vaø\037%¹\023oe¯aLu\020£\007Tu?Þ÷;^\\$åHVI").length();
    byte b2 = 16;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x52DD;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])e.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/ka", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = c[i].getBytes("ISO-8859-1");
      d[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return d[i];
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
    //   13: ldc_w [Ljava/lang/Object;
    //   16: aload_2
    //   17: invokevirtual parameterCount : ()I
    //   20: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   23: iconst_0
    //   24: iconst_3
    //   25: anewarray java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: aload_0
    //   31: aastore
    //   32: dup
    //   33: iconst_1
    //   34: aload_3
    //   35: aastore
    //   36: dup
    //   37: iconst_2
    //   38: aload_1
    //   39: aastore
    //   40: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   43: aload_2
    //   44: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   47: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   50: goto -> 104
    //   53: astore #4
    //   55: new java/lang/RuntimeException
    //   58: dup
    //   59: new java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: ldc_w 'wtf/opal/ka'
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc_w ' : '
    //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: aload_1
    //   79: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc_w ' : '
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_2
    //   89: invokevirtual toString : ()Ljava/lang/String;
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: aload #4
    //   100: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   103: athrow
    //   104: aload_3
    //   105: areturn
    // Exception table:
    //   from	to	target	type
    //   9	50	53	java/lang/Exception
  }
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x447C;
    if (g[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = f[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])h.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          h.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/ka", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      g[i] = Integer.valueOf(j);
    } 
    return g[i].intValue();
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
    //   13: ldc_w [Ljava/lang/Object;
    //   16: aload_2
    //   17: invokevirtual parameterCount : ()I
    //   20: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   23: iconst_0
    //   24: iconst_3
    //   25: anewarray java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: aload_0
    //   31: aastore
    //   32: dup
    //   33: iconst_1
    //   34: aload_3
    //   35: aastore
    //   36: dup
    //   37: iconst_2
    //   38: aload_1
    //   39: aastore
    //   40: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   43: aload_2
    //   44: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   47: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   50: goto -> 104
    //   53: astore #4
    //   55: new java/lang/RuntimeException
    //   58: dup
    //   59: new java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: ldc_w 'wtf/opal/ka'
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc_w ' : '
    //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: aload_1
    //   79: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc_w ' : '
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_2
    //   89: invokevirtual toString : ()Ljava/lang/String;
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: aload #4
    //   100: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   103: athrow
    //   104: aload_3
    //   105: areturn
    // Exception table:
    //   from	to	target	type
    //   9	50	53	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ka.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */