package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class dh extends dn {
  private static int c;
  
  private static final long b = on.a(-8715573745851207567L, 8205515663141621515L, MethodHandles.lookup().lookupClass()).a(177275455985513L);
  
  private static final String d;
  
  public dh(short paramShort, int paramInt1, int paramInt2) {
    super(d, l2, dt.MOVEMENT);
  }
  
  public boolean b(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_1657
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_3
    //   18: pop
    //   19: invokestatic a : ()I
    //   22: iconst_0
    //   23: anewarray java/lang/Object
    //   26: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   29: iconst_0
    //   30: anewarray java/lang/Object
    //   33: invokevirtual q : ([Ljava/lang/Object;)Lwtf/opal/ko;
    //   36: iconst_0
    //   37: anewarray java/lang/Object
    //   40: invokevirtual W : ([Ljava/lang/Object;)Ljava/util/Map;
    //   43: aload_2
    //   44: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   49: checkcast wtf/opal/lv
    //   52: astore #6
    //   54: istore #5
    //   56: new net/minecraft/class_5611
    //   59: dup
    //   60: aload #6
    //   62: iconst_0
    //   63: anewarray java/lang/Object
    //   66: invokevirtual F : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   69: invokevirtual method_10216 : ()D
    //   72: d2f
    //   73: aload #6
    //   75: iconst_0
    //   76: anewarray java/lang/Object
    //   79: invokevirtual F : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   82: invokevirtual method_10215 : ()D
    //   85: d2f
    //   86: invokespecial <init> : (FF)V
    //   89: astore #7
    //   91: new net/minecraft/class_5611
    //   94: dup
    //   95: aload_2
    //   96: invokevirtual method_23317 : ()D
    //   99: d2f
    //   100: aload_2
    //   101: invokevirtual method_23321 : ()D
    //   104: d2f
    //   105: invokespecial <init> : (FF)V
    //   108: astore #8
    //   110: new net/minecraft/class_5611
    //   113: dup
    //   114: aload #8
    //   116: invokevirtual method_32118 : ()F
    //   119: aload #7
    //   121: invokevirtual method_32118 : ()F
    //   124: fsub
    //   125: aload #8
    //   127: invokevirtual method_32119 : ()F
    //   130: aload #7
    //   132: invokevirtual method_32119 : ()F
    //   135: fsub
    //   136: invokespecial <init> : (FF)V
    //   139: astore #9
    //   141: aload #9
    //   143: invokevirtual method_32118 : ()F
    //   146: fneg
    //   147: f2d
    //   148: aload #9
    //   150: invokevirtual method_32119 : ()F
    //   153: f2d
    //   154: invokestatic atan2 : (DD)D
    //   157: ldc2_w 6.2831854820251465
    //   160: dadd
    //   161: ldc2_w 6.2831854820251465
    //   164: drem
    //   165: invokestatic toDegrees : (D)D
    //   168: d2f
    //   169: fstore #10
    //   171: aload_2
    //   172: invokevirtual method_23317 : ()D
    //   175: aload #6
    //   177: iconst_0
    //   178: anewarray java/lang/Object
    //   181: invokevirtual F : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   184: invokevirtual method_10216 : ()D
    //   187: dcmpl
    //   188: iload #5
    //   190: ifeq -> 282
    //   193: ifne -> 281
    //   196: goto -> 203
    //   199: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   202: athrow
    //   203: aload_2
    //   204: invokevirtual method_23318 : ()D
    //   207: aload #6
    //   209: iconst_0
    //   210: anewarray java/lang/Object
    //   213: invokevirtual F : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   216: invokevirtual method_10214 : ()D
    //   219: dcmpl
    //   220: iload #5
    //   222: ifeq -> 282
    //   225: goto -> 232
    //   228: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   231: athrow
    //   232: ifne -> 281
    //   235: goto -> 242
    //   238: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   241: athrow
    //   242: aload_2
    //   243: invokevirtual method_23321 : ()D
    //   246: aload #6
    //   248: iconst_0
    //   249: anewarray java/lang/Object
    //   252: invokevirtual F : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   255: invokevirtual method_10215 : ()D
    //   258: dcmpl
    //   259: iload #5
    //   261: ifeq -> 282
    //   264: goto -> 271
    //   267: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   270: athrow
    //   271: ifeq -> 285
    //   274: goto -> 281
    //   277: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   280: athrow
    //   281: iconst_1
    //   282: goto -> 286
    //   285: iconst_0
    //   286: istore #11
    //   288: fload #10
    //   290: aload_2
    //   291: invokevirtual method_36454 : ()F
    //   294: invokestatic method_15381 : (FF)F
    //   297: invokestatic abs : (F)F
    //   300: ldc 90.0
    //   302: fcmpl
    //   303: iload #5
    //   305: ifeq -> 481
    //   308: ifle -> 472
    //   311: goto -> 318
    //   314: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   317: athrow
    //   318: aload #6
    //   320: iconst_0
    //   321: anewarray java/lang/Object
    //   324: invokevirtual a : ([Ljava/lang/Object;)I
    //   327: iconst_1
    //   328: iload #5
    //   330: ifeq -> 482
    //   333: goto -> 340
    //   336: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   339: athrow
    //   340: if_icmple -> 472
    //   343: goto -> 350
    //   346: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   349: athrow
    //   350: aload #6
    //   352: iconst_0
    //   353: anewarray java/lang/Object
    //   356: invokevirtual B : ([Ljava/lang/Object;)I
    //   359: iconst_1
    //   360: iload #5
    //   362: lload_3
    //   363: lconst_0
    //   364: lcmp
    //   365: iflt -> 484
    //   368: ifeq -> 482
    //   371: goto -> 378
    //   374: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   377: athrow
    //   378: if_icmple -> 472
    //   381: goto -> 388
    //   384: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   387: athrow
    //   388: iload #11
    //   390: iload #5
    //   392: ifeq -> 481
    //   395: goto -> 402
    //   398: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   401: athrow
    //   402: ifeq -> 472
    //   405: goto -> 412
    //   408: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   411: athrow
    //   412: aload #6
    //   414: aload #6
    //   416: iconst_0
    //   417: anewarray java/lang/Object
    //   420: invokevirtual b : ([Ljava/lang/Object;)I
    //   423: iconst_1
    //   424: iadd
    //   425: iconst_1
    //   426: anewarray java/lang/Object
    //   429: dup_x1
    //   430: swap
    //   431: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   434: iconst_0
    //   435: swap
    //   436: aastore
    //   437: invokevirtual S : ([Ljava/lang/Object;)V
    //   440: iload #5
    //   442: lload_3
    //   443: lconst_0
    //   444: lcmp
    //   445: ifle -> 541
    //   448: ifne -> 532
    //   451: goto -> 458
    //   454: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   457: athrow
    //   458: iconst_3
    //   459: anewarray wtf/opal/d
    //   462: invokestatic p : ([Lwtf/opal/d;)V
    //   465: goto -> 472
    //   468: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   471: athrow
    //   472: aload #6
    //   474: iconst_0
    //   475: anewarray java/lang/Object
    //   478: invokevirtual b : ([Ljava/lang/Object;)I
    //   481: iconst_1
    //   482: iload #5
    //   484: ifeq -> 560
    //   487: if_icmplt -> 532
    //   490: goto -> 497
    //   493: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   496: athrow
    //   497: aload #6
    //   499: aload #6
    //   501: iconst_0
    //   502: anewarray java/lang/Object
    //   505: invokevirtual b : ([Ljava/lang/Object;)I
    //   508: iconst_1
    //   509: isub
    //   510: iconst_1
    //   511: anewarray java/lang/Object
    //   514: dup_x1
    //   515: swap
    //   516: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   519: iconst_0
    //   520: swap
    //   521: aastore
    //   522: invokevirtual S : ([Ljava/lang/Object;)V
    //   525: goto -> 532
    //   528: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   531: athrow
    //   532: aload #6
    //   534: iconst_0
    //   535: anewarray java/lang/Object
    //   538: invokevirtual b : ([Ljava/lang/Object;)I
    //   541: iload #5
    //   543: lload_3
    //   544: lconst_0
    //   545: lcmp
    //   546: ifle -> 553
    //   549: ifeq -> 564
    //   552: iconst_5
    //   553: goto -> 560
    //   556: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   559: athrow
    //   560: if_icmple -> 567
    //   563: iconst_1
    //   564: goto -> 568
    //   567: iconst_0
    //   568: ireturn
    // Exception table:
    //   from	to	target	type
    //   171	196	199	wtf/opal/x5
    //   193	225	228	wtf/opal/x5
    //   203	235	238	wtf/opal/x5
    //   232	264	267	wtf/opal/x5
    //   242	274	277	wtf/opal/x5
    //   288	311	314	wtf/opal/x5
    //   308	333	336	wtf/opal/x5
    //   318	343	346	wtf/opal/x5
    //   340	371	374	wtf/opal/x5
    //   350	381	384	wtf/opal/x5
    //   378	395	398	wtf/opal/x5
    //   388	405	408	wtf/opal/x5
    //   402	451	454	wtf/opal/x5
    //   412	465	468	wtf/opal/x5
    //   482	490	493	wtf/opal/x5
    //   487	525	528	wtf/opal/x5
    //   532	553	556	wtf/opal/x5
  }
  
  public static void H(int paramInt) {
    c = paramInt;
  }
  
  public static int d() {
    return c;
  }
  
  public static int a() {
    int i = d();
    try {
      if (i == 0)
        return 64; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  static {
    long l = b ^ 0x2779315E0F35L;
    H(0);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
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
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dh.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */