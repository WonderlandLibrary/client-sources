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

public class bt {
  private final String h;
  
  private final long g;
  
  private final String w;
  
  private static final long a = on.a(1295850738187035599L, 4833605939871222318L, MethodHandles.lookup().lookupClass()).a(124097258201601L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public bt(String paramString1, long paramLong, String paramString2) {
    this.h = paramString1;
    this.g = paramLong;
    this.w = paramString2;
  }
  
  public static bt b(Object[] paramArrayOfObject) {
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
    //   19: getstatic wtf/opal/bt.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 67899237442847
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 136495445796286
    //   37: lxor
    //   38: lstore #6
    //   40: pop2
    //   41: new wtf/opal/bt
    //   44: dup
    //   45: aload_3
    //   46: lload #4
    //   48: sipush #22163
    //   51: ldc2_w 9050442948262664274
    //   54: lload_1
    //   55: lxor
    //   56: <illegal opcode> i : (IJ)Ljava/lang/String;
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
    //   85: aload_3
    //   86: lload #4
    //   88: sipush #29978
    //   91: ldc2_w 7078537073684167642
    //   94: lload_1
    //   95: lxor
    //   96: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   101: iconst_2
    //   102: anewarray java/lang/Object
    //   105: dup_x1
    //   106: swap
    //   107: iconst_1
    //   108: swap
    //   109: aastore
    //   110: dup_x2
    //   111: dup_x2
    //   112: pop
    //   113: invokestatic valueOf : (J)Ljava/lang/Long;
    //   116: iconst_0
    //   117: swap
    //   118: aastore
    //   119: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   122: invokevirtual F : ()Ljava/lang/String;
    //   125: invokestatic parse : (Ljava/lang/CharSequence;)Ljava/time/OffsetDateTime;
    //   128: invokevirtual toInstant : ()Ljava/time/Instant;
    //   131: invokevirtual toEpochMilli : ()J
    //   134: aload_3
    //   135: lload #4
    //   137: sipush #19025
    //   140: ldc2_w 4063848983364535442
    //   143: lload_1
    //   144: lxor
    //   145: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   150: iconst_2
    //   151: anewarray java/lang/Object
    //   154: dup_x1
    //   155: swap
    //   156: iconst_1
    //   157: swap
    //   158: aastore
    //   159: dup_x2
    //   160: dup_x2
    //   161: pop
    //   162: invokestatic valueOf : (J)Ljava/lang/Long;
    //   165: iconst_0
    //   166: swap
    //   167: aastore
    //   168: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   171: invokevirtual w : ()Lwtf/opal/kq;
    //   174: lload #4
    //   176: sipush #26260
    //   179: ldc2_w 6933478294676711510
    //   182: lload_1
    //   183: lxor
    //   184: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   189: iconst_2
    //   190: anewarray java/lang/Object
    //   193: dup_x1
    //   194: swap
    //   195: iconst_1
    //   196: swap
    //   197: aastore
    //   198: dup_x2
    //   199: dup_x2
    //   200: pop
    //   201: invokestatic valueOf : (J)Ljava/lang/Long;
    //   204: iconst_0
    //   205: swap
    //   206: aastore
    //   207: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   210: lload #6
    //   212: iconst_1
    //   213: anewarray java/lang/Object
    //   216: dup_x2
    //   217: dup_x2
    //   218: pop
    //   219: invokestatic valueOf : (J)Ljava/lang/Long;
    //   222: iconst_0
    //   223: swap
    //   224: aastore
    //   225: invokevirtual D : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   228: iconst_0
    //   229: iconst_1
    //   230: anewarray java/lang/Object
    //   233: dup_x1
    //   234: swap
    //   235: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   238: iconst_0
    //   239: swap
    //   240: aastore
    //   241: invokevirtual P : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   244: invokevirtual w : ()Lwtf/opal/kq;
    //   247: lload #4
    //   249: sipush #2419
    //   252: ldc2_w 5608662509404473268
    //   255: lload_1
    //   256: lxor
    //   257: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   262: iconst_2
    //   263: anewarray java/lang/Object
    //   266: dup_x1
    //   267: swap
    //   268: iconst_1
    //   269: swap
    //   270: aastore
    //   271: dup_x2
    //   272: dup_x2
    //   273: pop
    //   274: invokestatic valueOf : (J)Ljava/lang/Long;
    //   277: iconst_0
    //   278: swap
    //   279: aastore
    //   280: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   283: invokevirtual F : ()Ljava/lang/String;
    //   286: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;)V
    //   289: areturn
  }
  
  public final String n(Object[] paramArrayOfObject) {
    return this.h;
  }
  
  public final long h(Object[] paramArrayOfObject) {
    return this.g;
  }
  
  public final String M(Object[] paramArrayOfObject) {
    return this.w;
  }
  
  static {
    long l = a ^ 0x29B7CC98C39L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "¶\003\n1MyZ@P¾èÃÊë\004\031÷çAc>\020v\007$þOÛÎÈåG®Ö\020¶+Ë/ùr\013¡ðûÛ\036Ê{").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6B4E;
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
        throw new RuntimeException("wtf/opal/bt", exception);
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
    //   65: ldc 'wtf/opal/bt'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */