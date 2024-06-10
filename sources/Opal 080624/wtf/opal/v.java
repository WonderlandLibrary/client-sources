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

public final class v extends d {
  private int f;
  
  private int x;
  
  private final gm<lz> t;
  
  private static final long a = on.a(4458622546022280383L, 7282475507957876932L, MethodHandles.lookup().lookupClass()).a(54194312273912L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map g = new HashMap<>(13);
  
  private static final long k;
  
  public v(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/v.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 23417558656764
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: sipush #27318
    //   18: ldc2_w 1363671561616924499
    //   21: lload_1
    //   22: lxor
    //   23: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   28: lload_3
    //   29: sipush #19360
    //   32: ldc2_w 3247395229863657028
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   42: getstatic wtf/opal/kn.COMBAT : Lwtf/opal/kn;
    //   45: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   48: aload_0
    //   49: aload_0
    //   50: <illegal opcode> H : (Lwtf/opal/v;)Lwtf/opal/gm;
    //   55: putfield t : Lwtf/opal/gm;
    //   58: return
  }
  
  private void lambda$new$0(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/v.a : J
    //   3: ldc2_w 65683538444648
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 13613836720645
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic y : ()I
    //   20: istore #6
    //   22: iload #6
    //   24: ifeq -> 63
    //   27: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   30: getfield field_1724 : Lnet/minecraft/class_746;
    //   33: invokevirtual method_24828 : ()Z
    //   36: ifeq -> 68
    //   39: goto -> 46
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: aload_0
    //   47: dup
    //   48: getfield f : I
    //   51: iconst_1
    //   52: iadd
    //   53: putfield f : I
    //   56: goto -> 63
    //   59: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   62: athrow
    //   63: iload #6
    //   65: ifne -> 80
    //   68: aload_0
    //   69: iconst_0
    //   70: putfield f : I
    //   73: goto -> 80
    //   76: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   79: athrow
    //   80: iconst_0
    //   81: anewarray java/lang/Object
    //   84: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   87: iconst_0
    //   88: anewarray java/lang/Object
    //   91: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   94: ldc wtf/opal/q
    //   96: iconst_1
    //   97: anewarray java/lang/Object
    //   100: dup_x1
    //   101: swap
    //   102: iconst_0
    //   103: swap
    //   104: aastore
    //   105: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   108: checkcast wtf/opal/q
    //   111: astore #7
    //   113: iconst_0
    //   114: anewarray java/lang/Object
    //   117: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   120: iconst_0
    //   121: anewarray java/lang/Object
    //   124: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   127: ldc wtf/opal/xc
    //   129: iconst_1
    //   130: anewarray java/lang/Object
    //   133: dup_x1
    //   134: swap
    //   135: iconst_0
    //   136: swap
    //   137: aastore
    //   138: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   141: checkcast wtf/opal/xc
    //   144: astore #8
    //   146: iload #6
    //   148: ifeq -> 263
    //   151: aload #7
    //   153: iconst_0
    //   154: anewarray java/lang/Object
    //   157: invokevirtual D : ([Ljava/lang/Object;)Z
    //   160: ifeq -> 251
    //   163: goto -> 170
    //   166: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   169: athrow
    //   170: aload #7
    //   172: iconst_0
    //   173: anewarray java/lang/Object
    //   176: invokevirtual U : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   179: ifnull -> 251
    //   182: goto -> 189
    //   185: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   188: athrow
    //   189: aload #8
    //   191: iconst_0
    //   192: anewarray java/lang/Object
    //   195: invokevirtual D : ([Ljava/lang/Object;)Z
    //   198: iload #6
    //   200: ifeq -> 273
    //   203: goto -> 210
    //   206: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   209: athrow
    //   210: ifeq -> 264
    //   213: goto -> 220
    //   216: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   219: athrow
    //   220: aload #8
    //   222: iconst_0
    //   223: anewarray java/lang/Object
    //   226: invokevirtual Z : ([Ljava/lang/Object;)Z
    //   229: iload #6
    //   231: ifeq -> 273
    //   234: goto -> 241
    //   237: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   240: athrow
    //   241: ifeq -> 264
    //   244: goto -> 251
    //   247: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   250: athrow
    //   251: aload_0
    //   252: iconst_0
    //   253: putfield x : I
    //   256: goto -> 263
    //   259: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   262: athrow
    //   263: return
    //   264: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   267: getfield field_1724 : Lnet/minecraft/class_746;
    //   270: invokevirtual method_24828 : ()Z
    //   273: iload #6
    //   275: ifeq -> 299
    //   278: ifeq -> 482
    //   281: goto -> 288
    //   284: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   287: athrow
    //   288: aload_0
    //   289: getfield x : I
    //   292: goto -> 299
    //   295: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   298: athrow
    //   299: iload #6
    //   301: ifeq -> 369
    //   304: ifne -> 348
    //   307: goto -> 314
    //   310: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   313: athrow
    //   314: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   317: getfield field_1724 : Lnet/minecraft/class_746;
    //   320: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   323: getfield field_1724 : Lnet/minecraft/class_746;
    //   326: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   329: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   332: ldc2_w 0.41999998688697815
    //   335: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   338: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   341: goto -> 348
    //   344: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   347: athrow
    //   348: aload_0
    //   349: iload #6
    //   351: ifeq -> 456
    //   354: getfield x : I
    //   357: getstatic wtf/opal/v.k : J
    //   360: l2i
    //   361: irem
    //   362: goto -> 369
    //   365: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   368: athrow
    //   369: ifne -> 455
    //   372: aload_0
    //   373: iload #6
    //   375: ifeq -> 456
    //   378: goto -> 385
    //   381: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   384: athrow
    //   385: getfield x : I
    //   388: ifle -> 455
    //   391: goto -> 398
    //   394: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   397: athrow
    //   398: aload_1
    //   399: aload_1
    //   400: iconst_0
    //   401: anewarray java/lang/Object
    //   404: invokevirtual P : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   407: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   410: aload_1
    //   411: lload #4
    //   413: iconst_1
    //   414: anewarray java/lang/Object
    //   417: dup_x2
    //   418: dup_x2
    //   419: pop
    //   420: invokestatic valueOf : (J)Ljava/lang/Long;
    //   423: iconst_0
    //   424: swap
    //   425: aastore
    //   426: invokevirtual J : ([Ljava/lang/Object;)D
    //   429: ldc2_w 9.9E-4
    //   432: dadd
    //   433: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   436: iconst_1
    //   437: anewarray java/lang/Object
    //   440: dup_x1
    //   441: swap
    //   442: iconst_0
    //   443: swap
    //   444: aastore
    //   445: invokevirtual d : ([Ljava/lang/Object;)V
    //   448: goto -> 455
    //   451: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   454: athrow
    //   455: aload_0
    //   456: dup
    //   457: getfield x : I
    //   460: iconst_1
    //   461: iadd
    //   462: putfield x : I
    //   465: aload_1
    //   466: iconst_0
    //   467: iconst_1
    //   468: anewarray java/lang/Object
    //   471: dup_x1
    //   472: swap
    //   473: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   476: iconst_0
    //   477: swap
    //   478: aastore
    //   479: invokevirtual w : ([Ljava/lang/Object;)V
    //   482: return
    // Exception table:
    //   from	to	target	type
    //   22	39	42	wtf/opal/x5
    //   27	56	59	wtf/opal/x5
    //   63	73	76	wtf/opal/x5
    //   146	163	166	wtf/opal/x5
    //   151	182	185	wtf/opal/x5
    //   170	203	206	wtf/opal/x5
    //   189	213	216	wtf/opal/x5
    //   210	234	237	wtf/opal/x5
    //   220	244	247	wtf/opal/x5
    //   241	256	259	wtf/opal/x5
    //   273	281	284	wtf/opal/x5
    //   278	292	295	wtf/opal/x5
    //   299	307	310	wtf/opal/x5
    //   304	341	344	wtf/opal/x5
    //   348	362	365	wtf/opal/x5
    //   369	378	381	wtf/opal/x5
    //   372	391	394	wtf/opal/x5
    //   385	448	451	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x4D17714E2F64L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "Gêæ$Íi÷.²Sa³^\025RÊº\f0À\n\032gýÌ¹XÚ\"²\037Z¹põ®\002¦^G?*ý©H\ntt³-¤ölÖCñ×µ<\\¯À?¥éI\016 Ì¥q«êÍÆØÎÕøºGG¸¡\001#U\022Q\032êý'BÚúó\fäl[").length();
    byte b2 = 32;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4970;
    if (d[i] == null) {
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
        throw new RuntimeException("wtf/opal/v", exception);
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
    //   66: ldc_w 'wtf/opal/v'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\v.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */