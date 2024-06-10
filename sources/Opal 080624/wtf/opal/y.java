package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_304;

public final class y extends d {
  private static final List<class_304> d;
  
  private static final ke A;
  
  private final ke Z;
  
  private final gm<b6> r;
  
  private static final long a = on.a(-148464280288874200L, -2951307358945127934L, MethodHandles.lookup().lookupClass()).a(223646050182255L);
  
  private static final String[] b;
  
  private static final String[] f;
  
  private static final Map g = new HashMap<>(13);
  
  public y(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/y.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 18884839165324
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 69623204353241
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #697
    //   25: ldc2_w 626063541152270630
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #22410
    //   40: ldc2_w 8016521647635667990
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/ke
    //   60: dup
    //   61: sipush #15214
    //   64: ldc2_w 5673293731075715312
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   74: iconst_1
    //   75: invokespecial <init> : (Ljava/lang/String;Z)V
    //   78: putfield Z : Lwtf/opal/ke;
    //   81: aload_0
    //   82: aload_0
    //   83: <illegal opcode> H : (Lwtf/opal/y;)Lwtf/opal/gm;
    //   88: putfield r : Lwtf/opal/gm;
    //   91: aload_0
    //   92: iconst_2
    //   93: anewarray wtf/opal/k3
    //   96: dup
    //   97: iconst_0
    //   98: getstatic wtf/opal/y.A : Lwtf/opal/ke;
    //   101: aastore
    //   102: dup
    //   103: iconst_1
    //   104: aload_0
    //   105: getfield Z : Lwtf/opal/ke;
    //   108: aastore
    //   109: lload_3
    //   110: dup2_x1
    //   111: pop2
    //   112: iconst_2
    //   113: anewarray java/lang/Object
    //   116: dup_x1
    //   117: swap
    //   118: iconst_1
    //   119: swap
    //   120: aastore
    //   121: dup_x2
    //   122: dup_x2
    //   123: pop
    //   124: invokestatic valueOf : (J)Ljava/lang/Long;
    //   127: iconst_0
    //   128: swap
    //   129: aastore
    //   130: invokevirtual o : ([Ljava/lang/Object;)V
    //   133: return
  }
  
  public static void m(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    try {
      d.forEach(y::lambda$updateKeyStates$1);
      if (A.z().booleanValue())
        class_304.method_1416(b9.c.field_1690.field_1832.method_1429(), dj.m(new Object[] { b9.c.field_1690.field_1832 })); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static void lambda$updateKeyStates$1(class_304 paramclass_304) {
    class_304.method_1416(paramclass_304.method_1429(), dj.m(new Object[] { paramclass_304 }));
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/y.a : J
    //   3: ldc2_w 7059251102413
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 69132376748925
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic P : ()Ljava/lang/String;
    //   20: astore #6
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: getfield field_1755 : Lnet/minecraft/class_437;
    //   28: aload #6
    //   30: ifnonnull -> 56
    //   33: ifnull -> 178
    //   36: goto -> 43
    //   39: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   46: getfield field_1755 : Lnet/minecraft/class_437;
    //   49: goto -> 56
    //   52: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   55: athrow
    //   56: instanceof net/minecraft/class_408
    //   59: aload #6
    //   61: ifnonnull -> 94
    //   64: ifne -> 178
    //   67: goto -> 74
    //   70: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: aload_0
    //   75: getfield Z : Lwtf/opal/ke;
    //   78: invokevirtual z : ()Ljava/lang/Object;
    //   81: checkcast java/lang/Boolean
    //   84: invokevirtual booleanValue : ()Z
    //   87: goto -> 94
    //   90: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: aload #6
    //   96: ifnonnull -> 156
    //   99: ifeq -> 140
    //   102: goto -> 109
    //   105: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   108: athrow
    //   109: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   112: getfield field_1755 : Lnet/minecraft/class_437;
    //   115: instanceof net/minecraft/class_490
    //   118: aload #6
    //   120: ifnonnull -> 156
    //   123: goto -> 130
    //   126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   129: athrow
    //   130: ifeq -> 178
    //   133: goto -> 140
    //   136: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   139: athrow
    //   140: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   143: getfield field_1755 : Lnet/minecraft/class_437;
    //   146: instanceof wtf/opal/x9
    //   149: goto -> 156
    //   152: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   155: athrow
    //   156: ifeq -> 160
    //   159: return
    //   160: lload #4
    //   162: iconst_1
    //   163: anewarray java/lang/Object
    //   166: dup_x2
    //   167: dup_x2
    //   168: pop
    //   169: invokestatic valueOf : (J)Ljava/lang/Long;
    //   172: iconst_0
    //   173: swap
    //   174: aastore
    //   175: invokestatic m : ([Ljava/lang/Object;)V
    //   178: return
    // Exception table:
    //   from	to	target	type
    //   22	36	39	wtf/opal/x5
    //   33	49	52	wtf/opal/x5
    //   56	67	70	wtf/opal/x5
    //   64	87	90	wtf/opal/x5
    //   94	102	105	wtf/opal/x5
    //   99	123	126	wtf/opal/x5
    //   109	133	136	wtf/opal/x5
    //   130	149	152	wtf/opal/x5
  }
  
  static {
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[4];
    boolean bool = false;
    String str;
    int i = (str = "q\005_PtùÑg9\fÈïhíru@ÁªÒ¢@ó@Ê^xÝ4­\001£íÆ\027jsO¶vé5+\025E\004\b.OpOñ\tÁHD÷Ry¼.Ð\035è¶\n\025ÞÛø\0357ìh").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x52D;
    if (f[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])g.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/y", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      f[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return f[i];
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
    //   65: ldc_w 'wtf/opal/y'
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
  
  static {
    long l = a ^ 0x3488A6BE33DDL;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\y.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */