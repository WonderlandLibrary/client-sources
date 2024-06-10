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

public final class jv extends d {
  private int Q;
  
  private final gm<u0> W;
  
  private static final long a = on.a(-8108162787135852433L, 1552091728610584278L, MethodHandles.lookup().lookupClass()).a(188369313615353L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public jv(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jv.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 8693212758197
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: sipush #589
    //   18: ldc2_w 2088675029376515807
    //   21: lload_1
    //   22: lxor
    //   23: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   28: lload_3
    //   29: sipush #4146
    //   32: ldc2_w 6637855825017515169
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   42: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   45: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   48: aload_0
    //   49: iconst_m1
    //   50: putfield Q : I
    //   53: aload_0
    //   54: aload_0
    //   55: <illegal opcode> H : (Lwtf/opal/jv;)Lwtf/opal/gm;
    //   60: putfield W : Lwtf/opal/gm;
    //   63: return
  }
  
  private void lambda$new$0(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/jv.a : J
    //   3: ldc2_w 4544955481724
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 114684225375456
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic k : ()Z
    //   20: istore #6
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: getfield field_1765 : Lnet/minecraft/class_239;
    //   28: iload #6
    //   30: ifeq -> 56
    //   33: ifnull -> 277
    //   36: goto -> 43
    //   39: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   46: getfield field_1765 : Lnet/minecraft/class_239;
    //   49: goto -> 56
    //   52: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   55: athrow
    //   56: invokevirtual method_17783 : ()Lnet/minecraft/class_239$class_240;
    //   59: getstatic net/minecraft/class_239$class_240.field_1332 : Lnet/minecraft/class_239$class_240;
    //   62: invokevirtual equals : (Ljava/lang/Object;)Z
    //   65: iload #6
    //   67: ifeq -> 300
    //   70: ifeq -> 277
    //   73: goto -> 80
    //   76: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   79: athrow
    //   80: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   83: getfield field_1690 : Lnet/minecraft/class_315;
    //   86: getfield field_1886 : Lnet/minecraft/class_304;
    //   89: invokevirtual method_1434 : ()Z
    //   92: iload #6
    //   94: ifeq -> 300
    //   97: goto -> 104
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: ifeq -> 277
    //   107: goto -> 114
    //   110: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   117: getfield field_1690 : Lnet/minecraft/class_315;
    //   120: getfield field_1904 : Lnet/minecraft/class_304;
    //   123: invokevirtual method_1434 : ()Z
    //   126: iload #6
    //   128: ifeq -> 300
    //   131: goto -> 138
    //   134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: ifne -> 277
    //   141: goto -> 148
    //   144: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   147: athrow
    //   148: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   151: getfield field_1765 : Lnet/minecraft/class_239;
    //   154: checkcast net/minecraft/class_3965
    //   157: astore #7
    //   159: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   162: getfield field_1687 : Lnet/minecraft/class_638;
    //   165: aload #7
    //   167: invokevirtual method_17777 : ()Lnet/minecraft/class_2338;
    //   170: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   173: astore #8
    //   175: aload_0
    //   176: getfield Q : I
    //   179: iload #6
    //   181: ifeq -> 243
    //   184: iconst_m1
    //   185: if_icmpne -> 218
    //   188: goto -> 195
    //   191: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   194: athrow
    //   195: aload_0
    //   196: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   199: getfield field_1724 : Lnet/minecraft/class_746;
    //   202: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   205: getfield field_7545 : I
    //   208: putfield Q : I
    //   211: goto -> 218
    //   214: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   217: athrow
    //   218: aload #8
    //   220: lload #4
    //   222: iconst_2
    //   223: anewarray java/lang/Object
    //   226: dup_x2
    //   227: dup_x2
    //   228: pop
    //   229: invokestatic valueOf : (J)Ljava/lang/Long;
    //   232: iconst_1
    //   233: swap
    //   234: aastore
    //   235: dup_x1
    //   236: swap
    //   237: iconst_0
    //   238: swap
    //   239: aastore
    //   240: invokestatic E : ([Ljava/lang/Object;)I
    //   243: istore #9
    //   245: iload #9
    //   247: iconst_m1
    //   248: if_icmpeq -> 272
    //   251: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   254: getfield field_1724 : Lnet/minecraft/class_746;
    //   257: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   260: iload #9
    //   262: putfield field_7545 : I
    //   265: goto -> 272
    //   268: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   271: athrow
    //   272: iload #6
    //   274: ifne -> 332
    //   277: aload_0
    //   278: iload #6
    //   280: ifeq -> 328
    //   283: goto -> 290
    //   286: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   289: athrow
    //   290: getfield Q : I
    //   293: goto -> 300
    //   296: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   299: athrow
    //   300: iconst_m1
    //   301: if_icmpeq -> 332
    //   304: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   307: getfield field_1724 : Lnet/minecraft/class_746;
    //   310: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   313: aload_0
    //   314: getfield Q : I
    //   317: putfield field_7545 : I
    //   320: aload_0
    //   321: goto -> 328
    //   324: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   327: athrow
    //   328: iconst_m1
    //   329: putfield Q : I
    //   332: return
    // Exception table:
    //   from	to	target	type
    //   22	36	39	wtf/opal/x5
    //   33	49	52	wtf/opal/x5
    //   56	73	76	wtf/opal/x5
    //   70	97	100	wtf/opal/x5
    //   80	107	110	wtf/opal/x5
    //   104	131	134	wtf/opal/x5
    //   114	141	144	wtf/opal/x5
    //   175	188	191	wtf/opal/x5
    //   184	211	214	wtf/opal/x5
    //   245	265	268	wtf/opal/x5
    //   272	283	286	wtf/opal/x5
    //   277	293	296	wtf/opal/x5
    //   300	321	324	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x3BD839129CAEL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "auý%GÃ\021\034|zÚoL\030xÄì4D®~<X.¬TÍ\001Î\013ºÌjEi»pXþÎpk½P7\013÷«©wÉ6C{ªZÌ!ÜU\013W;\tR±dìØ@\fXÏÏäst\r5 A\020Iò)\n`;ÜfüB=:gÙÖ¼@Ib'M").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x264E;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])f.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          f.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jv", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
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
    //   66: ldc_w 'wtf/opal/jv'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jv.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */