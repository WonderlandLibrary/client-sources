package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class jr extends d {
  private final Map<py, dx> t = new ConcurrentHashMap<>();
  
  private final xt I = d1.q(new Object[0]).p(new Object[0]);
  
  private final pa G = d1.q(new Object[0]).z(new Object[0]);
  
  private final bu q = d1.q(new Object[0]).h(new Object[0]);
  
  private final gm<uj> m = this::lambda$new$1;
  
  private static final long a = on.a(-7220543640879728665L, 6083788127296229846L, MethodHandles.lookup().lookupClass()).a(9822123734368L);
  
  private static final String b;
  
  private static final long[] d;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public jr(int paramInt1, byte paramByte, int paramInt2) {
    super(b, l2, "", kn.VISUAL);
  }
  
  private void lambda$new$1(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/jr.a : J
    //   3: ldc2_w 32057039211694
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 73468221397843
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 14358467507246
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 50852641039913
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 114156094406248
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 39138983804620
    //   41: lxor
    //   42: lstore #12
    //   44: dup2
    //   45: ldc2_w 96580611264092
    //   48: lxor
    //   49: lstore #14
    //   51: dup2
    //   52: ldc2_w 136730066451139
    //   55: lxor
    //   56: lstore #16
    //   58: dup2
    //   59: ldc2_w 114808185224034
    //   62: lxor
    //   63: lstore #18
    //   65: pop2
    //   66: invokestatic S : ()Ljava/lang/String;
    //   69: astore #20
    //   71: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   74: invokevirtual method_53526 : ()Lnet/minecraft/class_340;
    //   77: invokevirtual method_53536 : ()Z
    //   80: ifeq -> 88
    //   83: return
    //   84: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: iconst_0
    //   89: anewarray java/lang/Object
    //   92: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   95: iconst_0
    //   96: anewarray java/lang/Object
    //   99: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   102: ldc wtf/opal/jt
    //   104: iconst_1
    //   105: anewarray java/lang/Object
    //   108: dup_x1
    //   109: swap
    //   110: iconst_0
    //   111: swap
    //   112: aastore
    //   113: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   116: checkcast wtf/opal/jt
    //   119: astore #21
    //   121: aload_1
    //   122: iconst_0
    //   123: anewarray java/lang/Object
    //   126: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   129: astore #22
    //   131: fconst_0
    //   132: fstore #23
    //   134: aload_0
    //   135: getfield I : Lwtf/opal/xt;
    //   138: invokevirtual iterator : ()Ljava/util/Iterator;
    //   141: astore #24
    //   143: aload #24
    //   145: invokeinterface hasNext : ()Z
    //   150: ifeq -> 843
    //   153: aload #24
    //   155: invokeinterface next : ()Ljava/lang/Object;
    //   160: checkcast wtf/opal/py
    //   163: astore #25
    //   165: aload_0
    //   166: getfield t : Ljava/util/Map;
    //   169: aload #25
    //   171: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   176: invokeinterface computeIfAbsent : (Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;
    //   181: checkcast wtf/opal/dx
    //   184: astore #26
    //   186: aload #25
    //   188: lload #14
    //   190: iconst_1
    //   191: anewarray java/lang/Object
    //   194: dup_x2
    //   195: dup_x2
    //   196: pop
    //   197: invokestatic valueOf : (J)Ljava/lang/Long;
    //   200: iconst_0
    //   201: swap
    //   202: aastore
    //   203: invokevirtual r : ([Ljava/lang/Object;)Z
    //   206: ifeq -> 245
    //   209: aload #26
    //   211: lload #10
    //   213: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   216: iconst_2
    //   217: anewarray java/lang/Object
    //   220: dup_x1
    //   221: swap
    //   222: iconst_1
    //   223: swap
    //   224: aastore
    //   225: dup_x2
    //   226: dup_x2
    //   227: pop
    //   228: invokestatic valueOf : (J)Ljava/lang/Long;
    //   231: iconst_0
    //   232: swap
    //   233: aastore
    //   234: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   237: pop
    //   238: goto -> 245
    //   241: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   244: athrow
    //   245: aload #26
    //   247: lload #12
    //   249: iconst_1
    //   250: anewarray java/lang/Object
    //   253: dup_x2
    //   254: dup_x2
    //   255: pop
    //   256: invokestatic valueOf : (J)Ljava/lang/Long;
    //   259: iconst_0
    //   260: swap
    //   261: aastore
    //   262: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   265: invokevirtual doubleValue : ()D
    //   268: dstore #27
    //   270: ldc 135.0
    //   272: fstore #29
    //   274: ldc 30.0
    //   276: fstore #30
    //   278: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   281: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   284: invokevirtual method_4486 : ()I
    //   287: i2d
    //   288: dload #27
    //   290: ldc2_w 145.0
    //   293: dmul
    //   294: dsub
    //   295: d2f
    //   296: fstore #31
    //   298: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   301: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   304: invokevirtual method_4502 : ()I
    //   307: i2f
    //   308: ldc 40.0
    //   310: fsub
    //   311: fload #23
    //   313: fsub
    //   314: fstore #32
    //   316: aload_0
    //   317: getfield G : Lwtf/opal/pa;
    //   320: fload #31
    //   322: fload #32
    //   324: ldc 135.0
    //   326: ldc 30.0
    //   328: lload #16
    //   330: ldc 6.0
    //   332: fconst_2
    //   333: sipush #32191
    //   336: ldc2_w 8308053665967387165
    //   339: lload_2
    //   340: lxor
    //   341: <illegal opcode> v : (IJ)I
    //   346: bipush #8
    //   348: anewarray java/lang/Object
    //   351: dup_x1
    //   352: swap
    //   353: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   356: bipush #7
    //   358: swap
    //   359: aastore
    //   360: dup_x1
    //   361: swap
    //   362: invokestatic valueOf : (F)Ljava/lang/Float;
    //   365: bipush #6
    //   367: swap
    //   368: aastore
    //   369: dup_x1
    //   370: swap
    //   371: invokestatic valueOf : (F)Ljava/lang/Float;
    //   374: iconst_5
    //   375: swap
    //   376: aastore
    //   377: dup_x2
    //   378: dup_x2
    //   379: pop
    //   380: invokestatic valueOf : (J)Ljava/lang/Long;
    //   383: iconst_4
    //   384: swap
    //   385: aastore
    //   386: dup_x1
    //   387: swap
    //   388: invokestatic valueOf : (F)Ljava/lang/Float;
    //   391: iconst_3
    //   392: swap
    //   393: aastore
    //   394: dup_x1
    //   395: swap
    //   396: invokestatic valueOf : (F)Ljava/lang/Float;
    //   399: iconst_2
    //   400: swap
    //   401: aastore
    //   402: dup_x1
    //   403: swap
    //   404: invokestatic valueOf : (F)Ljava/lang/Float;
    //   407: iconst_1
    //   408: swap
    //   409: aastore
    //   410: dup_x1
    //   411: swap
    //   412: invokestatic valueOf : (F)Ljava/lang/Float;
    //   415: iconst_0
    //   416: swap
    //   417: aastore
    //   418: invokevirtual G : ([Ljava/lang/Object;)V
    //   421: aload_0
    //   422: getfield G : Lwtf/opal/pa;
    //   425: fload #31
    //   427: fload #32
    //   429: ldc 135.0
    //   431: ldc 30.0
    //   433: ldc 6.0
    //   435: sipush #942
    //   438: ldc2_w 8391827207909821453
    //   441: lload_2
    //   442: lxor
    //   443: <illegal opcode> v : (IJ)I
    //   448: lload #6
    //   450: bipush #7
    //   452: anewarray java/lang/Object
    //   455: dup_x2
    //   456: dup_x2
    //   457: pop
    //   458: invokestatic valueOf : (J)Ljava/lang/Long;
    //   461: bipush #6
    //   463: swap
    //   464: aastore
    //   465: dup_x1
    //   466: swap
    //   467: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   470: iconst_5
    //   471: swap
    //   472: aastore
    //   473: dup_x1
    //   474: swap
    //   475: invokestatic valueOf : (F)Ljava/lang/Float;
    //   478: iconst_4
    //   479: swap
    //   480: aastore
    //   481: dup_x1
    //   482: swap
    //   483: invokestatic valueOf : (F)Ljava/lang/Float;
    //   486: iconst_3
    //   487: swap
    //   488: aastore
    //   489: dup_x1
    //   490: swap
    //   491: invokestatic valueOf : (F)Ljava/lang/Float;
    //   494: iconst_2
    //   495: swap
    //   496: aastore
    //   497: dup_x1
    //   498: swap
    //   499: invokestatic valueOf : (F)Ljava/lang/Float;
    //   502: iconst_1
    //   503: swap
    //   504: aastore
    //   505: dup_x1
    //   506: swap
    //   507: invokestatic valueOf : (F)Ljava/lang/Float;
    //   510: iconst_0
    //   511: swap
    //   512: aastore
    //   513: invokevirtual M : ([Ljava/lang/Object;)V
    //   516: aload_0
    //   517: getfield q : Lwtf/opal/bu;
    //   520: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   523: aload #22
    //   525: aload #25
    //   527: iconst_0
    //   528: anewarray java/lang/Object
    //   531: invokevirtual u : ([Ljava/lang/Object;)Ljava/lang/String;
    //   534: fload #31
    //   536: ldc 30.0
    //   538: fadd
    //   539: fload #32
    //   541: ldc_w 3.0
    //   544: fadd
    //   545: lload #8
    //   547: dup2_x1
    //   548: pop2
    //   549: ldc_w 10.0
    //   552: iconst_m1
    //   553: bipush #8
    //   555: anewarray java/lang/Object
    //   558: dup_x1
    //   559: swap
    //   560: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   563: bipush #7
    //   565: swap
    //   566: aastore
    //   567: dup_x1
    //   568: swap
    //   569: invokestatic valueOf : (F)Ljava/lang/Float;
    //   572: bipush #6
    //   574: swap
    //   575: aastore
    //   576: dup_x1
    //   577: swap
    //   578: invokestatic valueOf : (F)Ljava/lang/Float;
    //   581: iconst_5
    //   582: swap
    //   583: aastore
    //   584: dup_x2
    //   585: dup_x2
    //   586: pop
    //   587: invokestatic valueOf : (J)Ljava/lang/Long;
    //   590: iconst_4
    //   591: swap
    //   592: aastore
    //   593: dup_x1
    //   594: swap
    //   595: invokestatic valueOf : (F)Ljava/lang/Float;
    //   598: iconst_3
    //   599: swap
    //   600: aastore
    //   601: dup_x1
    //   602: swap
    //   603: iconst_2
    //   604: swap
    //   605: aastore
    //   606: dup_x1
    //   607: swap
    //   608: iconst_1
    //   609: swap
    //   610: aastore
    //   611: dup_x1
    //   612: swap
    //   613: iconst_0
    //   614: swap
    //   615: aastore
    //   616: invokevirtual z : ([Ljava/lang/Object;)V
    //   619: aload_0
    //   620: getfield q : Lwtf/opal/bu;
    //   623: aload #22
    //   625: aload #25
    //   627: iconst_0
    //   628: anewarray java/lang/Object
    //   631: invokevirtual Y : ([Ljava/lang/Object;)Ljava/lang/String;
    //   634: fload #31
    //   636: ldc 30.0
    //   638: fadd
    //   639: lload #4
    //   641: dup2_x1
    //   642: pop2
    //   643: fload #32
    //   645: ldc_w 14.0
    //   648: fadd
    //   649: ldc_w 8.0
    //   652: sipush #21793
    //   655: ldc2_w 7674951932056115841
    //   658: lload_2
    //   659: lxor
    //   660: <illegal opcode> v : (IJ)I
    //   665: bipush #7
    //   667: anewarray java/lang/Object
    //   670: dup_x1
    //   671: swap
    //   672: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   675: bipush #6
    //   677: swap
    //   678: aastore
    //   679: dup_x1
    //   680: swap
    //   681: invokestatic valueOf : (F)Ljava/lang/Float;
    //   684: iconst_5
    //   685: swap
    //   686: aastore
    //   687: dup_x1
    //   688: swap
    //   689: invokestatic valueOf : (F)Ljava/lang/Float;
    //   692: iconst_4
    //   693: swap
    //   694: aastore
    //   695: dup_x1
    //   696: swap
    //   697: invokestatic valueOf : (F)Ljava/lang/Float;
    //   700: iconst_3
    //   701: swap
    //   702: aastore
    //   703: dup_x2
    //   704: dup_x2
    //   705: pop
    //   706: invokestatic valueOf : (J)Ljava/lang/Long;
    //   709: iconst_2
    //   710: swap
    //   711: aastore
    //   712: dup_x1
    //   713: swap
    //   714: iconst_1
    //   715: swap
    //   716: aastore
    //   717: dup_x1
    //   718: swap
    //   719: iconst_0
    //   720: swap
    //   721: aastore
    //   722: invokevirtual R : ([Ljava/lang/Object;)V
    //   725: aload #20
    //   727: ifnonnull -> 838
    //   730: aload #26
    //   732: iconst_0
    //   733: anewarray java/lang/Object
    //   736: invokevirtual H : ([Ljava/lang/Object;)Lwtf/opal/lp;
    //   739: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   742: if_acmpne -> 807
    //   745: goto -> 752
    //   748: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   751: athrow
    //   752: aload #26
    //   754: lload #18
    //   756: iconst_1
    //   757: anewarray java/lang/Object
    //   760: dup_x2
    //   761: dup_x2
    //   762: pop
    //   763: invokestatic valueOf : (J)Ljava/lang/Long;
    //   766: iconst_0
    //   767: swap
    //   768: aastore
    //   769: invokevirtual H : ([Ljava/lang/Object;)Z
    //   772: ifeq -> 807
    //   775: goto -> 782
    //   778: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   781: athrow
    //   782: aload_0
    //   783: getfield I : Lwtf/opal/xt;
    //   786: aload #25
    //   788: iconst_1
    //   789: anewarray java/lang/Object
    //   792: dup_x1
    //   793: swap
    //   794: iconst_0
    //   795: swap
    //   796: aastore
    //   797: invokevirtual T : ([Ljava/lang/Object;)V
    //   800: goto -> 807
    //   803: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   806: athrow
    //   807: fload #23
    //   809: ldc 40.0
    //   811: aload #26
    //   813: lload #12
    //   815: iconst_1
    //   816: anewarray java/lang/Object
    //   819: dup_x2
    //   820: dup_x2
    //   821: pop
    //   822: invokestatic valueOf : (J)Ljava/lang/Long;
    //   825: iconst_0
    //   826: swap
    //   827: aastore
    //   828: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   831: invokevirtual floatValue : ()F
    //   834: fmul
    //   835: fadd
    //   836: fstore #23
    //   838: aload #20
    //   840: ifnull -> 143
    //   843: return
    // Exception table:
    //   from	to	target	type
    //   71	84	84	wtf/opal/x5
    //   186	238	241	wtf/opal/x5
    //   316	745	748	wtf/opal/x5
    //   730	775	778	wtf/opal/x5
    //   752	800	803	wtf/opal/x5
  }
  
  private static dx lambda$new$0(py parampy) {
    // Byte code:
    //   0: getstatic wtf/opal/jr.a : J
    //   3: ldc2_w 78383115135888
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 80075392320259
    //   13: lxor
    //   14: dup2
    //   15: bipush #32
    //   17: lushr
    //   18: l2i
    //   19: istore_3
    //   20: dup2
    //   21: bipush #32
    //   23: lshl
    //   24: bipush #48
    //   26: lushr
    //   27: l2i
    //   28: istore #4
    //   30: dup2
    //   31: bipush #48
    //   33: lshl
    //   34: bipush #48
    //   36: lushr
    //   37: l2i
    //   38: istore #5
    //   40: pop2
    //   41: pop2
    //   42: new wtf/opal/dk
    //   45: dup
    //   46: sipush #8645
    //   49: ldc2_w 3567800927650629978
    //   52: lload_1
    //   53: lxor
    //   54: <illegal opcode> v : (IJ)I
    //   59: iload_3
    //   60: dconst_1
    //   61: iload #4
    //   63: i2c
    //   64: iload #5
    //   66: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   69: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   72: areturn
  }
  
  static {
    long l = a ^ 0x54047CC05AD5L;
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
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5241;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = d[i];
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
        throw new RuntimeException("wtf/opal/jr", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
  }
  
  private static int a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
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
    //   66: ldc_w 'wtf/opal/jr'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jr.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */