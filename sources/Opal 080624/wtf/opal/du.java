package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class du {
  private int O;
  
  private int o;
  
  private int E = 1;
  
  private long m;
  
  private long Z;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  private static final long e;
  
  public boolean l(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/du.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic B : ()[Ljava/lang/String;
    //   21: astore #4
    //   23: invokestatic currentTimeMillis : ()J
    //   26: aload_0
    //   27: getfield m : J
    //   30: lsub
    //   31: sipush #26688
    //   34: ldc2_w 5094386427107575918
    //   37: lload_2
    //   38: lxor
    //   39: <illegal opcode> v : (IJ)I
    //   44: aload_0
    //   45: getfield E : I
    //   48: idiv
    //   49: i2l
    //   50: lcmp
    //   51: aload #4
    //   53: ifnull -> 105
    //   56: ifle -> 104
    //   59: goto -> 66
    //   62: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   65: athrow
    //   66: aload_0
    //   67: invokestatic currentTimeMillis : ()J
    //   70: putfield m : J
    //   73: aload_0
    //   74: invokestatic currentTimeMillis : ()J
    //   77: putfield Z : J
    //   80: aload_0
    //   81: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   84: aload_0
    //   85: getfield O : I
    //   88: aload_0
    //   89: getfield o : I
    //   92: invokevirtual nextInt : (II)I
    //   95: putfield E : I
    //   98: iconst_1
    //   99: ireturn
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: iconst_0
    //   105: ireturn
    // Exception table:
    //   from	to	target	type
    //   23	59	62	wtf/opal/x5
    //   56	100	100	wtf/opal/x5
  }
  
  public boolean H(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/du.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic B : ()[Ljava/lang/String;
    //   21: aload_0
    //   22: getfield Z : J
    //   25: sipush #24086
    //   28: ldc2_w 6670531402445819954
    //   31: lload_2
    //   32: lxor
    //   33: <illegal opcode> v : (IJ)I
    //   38: aload_0
    //   39: getfield E : I
    //   42: idiv
    //   43: i2l
    //   44: ladd
    //   45: invokestatic currentTimeMillis : ()J
    //   48: lsub
    //   49: lstore #5
    //   51: astore #4
    //   53: lload #5
    //   55: getstatic wtf/opal/du.e : J
    //   58: lcmp
    //   59: aload #4
    //   61: ifnull -> 75
    //   64: ifgt -> 78
    //   67: goto -> 74
    //   70: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: iconst_1
    //   75: goto -> 79
    //   78: iconst_0
    //   79: ireturn
    // Exception table:
    //   from	to	target	type
    //   53	67	70	wtf/opal/x5
  }
  
  public void l(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.O = i;
  }
  
  public void G(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.o = i;
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -3263075869669279070
    //   3: ldc2_w 7103600048378650535
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 269053803936049
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/du.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/du.d : Ljava/util/Map;
    //   38: getstatic wtf/opal/du.a : J
    //   41: ldc2_w 79295273485951
    //   44: lxor
    //   45: lstore #5
    //   47: ldc 'DES/CBC/NoPadding'
    //   49: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   52: dup
    //   53: astore #7
    //   55: iconst_2
    //   56: ldc 'DES'
    //   58: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   61: bipush #8
    //   63: newarray byte
    //   65: dup
    //   66: iconst_0
    //   67: lload #5
    //   69: bipush #56
    //   71: lushr
    //   72: l2i
    //   73: i2b
    //   74: bastore
    //   75: iconst_1
    //   76: istore #8
    //   78: iload #8
    //   80: bipush #8
    //   82: if_icmpge -> 108
    //   85: dup
    //   86: iload #8
    //   88: lload #5
    //   90: iload #8
    //   92: bipush #8
    //   94: imul
    //   95: lshl
    //   96: bipush #56
    //   98: lushr
    //   99: l2i
    //   100: i2b
    //   101: bastore
    //   102: iinc #8, 1
    //   105: goto -> 78
    //   108: new javax/crypto/spec/DESKeySpec
    //   111: dup_x1
    //   112: swap
    //   113: invokespecial <init> : ([B)V
    //   116: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   119: new javax/crypto/spec/IvParameterSpec
    //   122: dup
    //   123: bipush #8
    //   125: newarray byte
    //   127: invokespecial <init> : ([B)V
    //   130: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   133: iconst_2
    //   134: newarray long
    //   136: astore #13
    //   138: iconst_0
    //   139: istore #10
    //   141: ldc '¯DE_Xéu¦§cÆú¬'
    //   143: dup
    //   144: astore #11
    //   146: invokevirtual length : ()I
    //   149: istore #12
    //   151: iconst_0
    //   152: istore #9
    //   154: aload #11
    //   156: iload #9
    //   158: iinc #9, 8
    //   161: iload #9
    //   163: invokevirtual substring : (II)Ljava/lang/String;
    //   166: ldc 'ISO-8859-1'
    //   168: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   171: astore #14
    //   173: aload #13
    //   175: iload #10
    //   177: iinc #10, 1
    //   180: aload #14
    //   182: iconst_0
    //   183: baload
    //   184: i2l
    //   185: ldc2_w 255
    //   188: land
    //   189: bipush #56
    //   191: lshl
    //   192: aload #14
    //   194: iconst_1
    //   195: baload
    //   196: i2l
    //   197: ldc2_w 255
    //   200: land
    //   201: bipush #48
    //   203: lshl
    //   204: lor
    //   205: aload #14
    //   207: iconst_2
    //   208: baload
    //   209: i2l
    //   210: ldc2_w 255
    //   213: land
    //   214: bipush #40
    //   216: lshl
    //   217: lor
    //   218: aload #14
    //   220: iconst_3
    //   221: baload
    //   222: i2l
    //   223: ldc2_w 255
    //   226: land
    //   227: bipush #32
    //   229: lshl
    //   230: lor
    //   231: aload #14
    //   233: iconst_4
    //   234: baload
    //   235: i2l
    //   236: ldc2_w 255
    //   239: land
    //   240: bipush #24
    //   242: lshl
    //   243: lor
    //   244: aload #14
    //   246: iconst_5
    //   247: baload
    //   248: i2l
    //   249: ldc2_w 255
    //   252: land
    //   253: bipush #16
    //   255: lshl
    //   256: lor
    //   257: aload #14
    //   259: bipush #6
    //   261: baload
    //   262: i2l
    //   263: ldc2_w 255
    //   266: land
    //   267: bipush #8
    //   269: lshl
    //   270: lor
    //   271: aload #14
    //   273: bipush #7
    //   275: baload
    //   276: i2l
    //   277: ldc2_w 255
    //   280: land
    //   281: lor
    //   282: iconst_m1
    //   283: goto -> 309
    //   286: lastore
    //   287: iload #9
    //   289: iload #12
    //   291: if_icmplt -> 154
    //   294: aload #13
    //   296: putstatic wtf/opal/du.b : [J
    //   299: iconst_2
    //   300: anewarray java/lang/Integer
    //   303: putstatic wtf/opal/du.c : [Ljava/lang/Integer;
    //   306: goto -> 512
    //   309: dup_x2
    //   310: pop
    //   311: lstore #15
    //   313: bipush #8
    //   315: newarray byte
    //   317: dup
    //   318: iconst_0
    //   319: lload #15
    //   321: bipush #56
    //   323: lushr
    //   324: l2i
    //   325: i2b
    //   326: bastore
    //   327: dup
    //   328: iconst_1
    //   329: lload #15
    //   331: bipush #48
    //   333: lushr
    //   334: l2i
    //   335: i2b
    //   336: bastore
    //   337: dup
    //   338: iconst_2
    //   339: lload #15
    //   341: bipush #40
    //   343: lushr
    //   344: l2i
    //   345: i2b
    //   346: bastore
    //   347: dup
    //   348: iconst_3
    //   349: lload #15
    //   351: bipush #32
    //   353: lushr
    //   354: l2i
    //   355: i2b
    //   356: bastore
    //   357: dup
    //   358: iconst_4
    //   359: lload #15
    //   361: bipush #24
    //   363: lushr
    //   364: l2i
    //   365: i2b
    //   366: bastore
    //   367: dup
    //   368: iconst_5
    //   369: lload #15
    //   371: bipush #16
    //   373: lushr
    //   374: l2i
    //   375: i2b
    //   376: bastore
    //   377: dup
    //   378: bipush #6
    //   380: lload #15
    //   382: bipush #8
    //   384: lushr
    //   385: l2i
    //   386: i2b
    //   387: bastore
    //   388: dup
    //   389: bipush #7
    //   391: lload #15
    //   393: l2i
    //   394: i2b
    //   395: bastore
    //   396: aload #7
    //   398: swap
    //   399: invokevirtual doFinal : ([B)[B
    //   402: astore #17
    //   404: aload #17
    //   406: iconst_0
    //   407: baload
    //   408: i2l
    //   409: ldc2_w 255
    //   412: land
    //   413: bipush #56
    //   415: lshl
    //   416: aload #17
    //   418: iconst_1
    //   419: baload
    //   420: i2l
    //   421: ldc2_w 255
    //   424: land
    //   425: bipush #48
    //   427: lshl
    //   428: lor
    //   429: aload #17
    //   431: iconst_2
    //   432: baload
    //   433: i2l
    //   434: ldc2_w 255
    //   437: land
    //   438: bipush #40
    //   440: lshl
    //   441: lor
    //   442: aload #17
    //   444: iconst_3
    //   445: baload
    //   446: i2l
    //   447: ldc2_w 255
    //   450: land
    //   451: bipush #32
    //   453: lshl
    //   454: lor
    //   455: aload #17
    //   457: iconst_4
    //   458: baload
    //   459: i2l
    //   460: ldc2_w 255
    //   463: land
    //   464: bipush #24
    //   466: lshl
    //   467: lor
    //   468: aload #17
    //   470: iconst_5
    //   471: baload
    //   472: i2l
    //   473: ldc2_w 255
    //   476: land
    //   477: bipush #16
    //   479: lshl
    //   480: lor
    //   481: aload #17
    //   483: bipush #6
    //   485: baload
    //   486: i2l
    //   487: ldc2_w 255
    //   490: land
    //   491: bipush #8
    //   493: lshl
    //   494: lor
    //   495: aload #17
    //   497: bipush #7
    //   499: baload
    //   500: i2l
    //   501: ldc2_w 255
    //   504: land
    //   505: lor
    //   506: dup2_x1
    //   507: pop2
    //   508: pop
    //   509: goto -> 286
    //   512: ldc 'DES/CBC/NoPadding'
    //   514: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   517: dup
    //   518: astore_0
    //   519: iconst_2
    //   520: ldc 'DES'
    //   522: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   525: bipush #8
    //   527: newarray byte
    //   529: dup
    //   530: iconst_0
    //   531: lload #5
    //   533: bipush #56
    //   535: lushr
    //   536: l2i
    //   537: i2b
    //   538: bastore
    //   539: iconst_1
    //   540: istore_1
    //   541: iload_1
    //   542: bipush #8
    //   544: if_icmpge -> 568
    //   547: dup
    //   548: iload_1
    //   549: lload #5
    //   551: iload_1
    //   552: bipush #8
    //   554: imul
    //   555: lshl
    //   556: bipush #56
    //   558: lushr
    //   559: l2i
    //   560: i2b
    //   561: bastore
    //   562: iinc #1, 1
    //   565: goto -> 541
    //   568: new javax/crypto/spec/DESKeySpec
    //   571: dup_x1
    //   572: swap
    //   573: invokespecial <init> : ([B)V
    //   576: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   579: new javax/crypto/spec/IvParameterSpec
    //   582: dup
    //   583: bipush #8
    //   585: newarray byte
    //   587: invokespecial <init> : ([B)V
    //   590: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   593: ldc2_w -7566493265227942102
    //   596: iconst_m1
    //   597: goto -> 606
    //   600: putstatic wtf/opal/du.e : J
    //   603: goto -> 799
    //   606: dup_x2
    //   607: pop
    //   608: lstore_2
    //   609: bipush #8
    //   611: newarray byte
    //   613: dup
    //   614: iconst_0
    //   615: lload_2
    //   616: bipush #56
    //   618: lushr
    //   619: l2i
    //   620: i2b
    //   621: bastore
    //   622: dup
    //   623: iconst_1
    //   624: lload_2
    //   625: bipush #48
    //   627: lushr
    //   628: l2i
    //   629: i2b
    //   630: bastore
    //   631: dup
    //   632: iconst_2
    //   633: lload_2
    //   634: bipush #40
    //   636: lushr
    //   637: l2i
    //   638: i2b
    //   639: bastore
    //   640: dup
    //   641: iconst_3
    //   642: lload_2
    //   643: bipush #32
    //   645: lushr
    //   646: l2i
    //   647: i2b
    //   648: bastore
    //   649: dup
    //   650: iconst_4
    //   651: lload_2
    //   652: bipush #24
    //   654: lushr
    //   655: l2i
    //   656: i2b
    //   657: bastore
    //   658: dup
    //   659: iconst_5
    //   660: lload_2
    //   661: bipush #16
    //   663: lushr
    //   664: l2i
    //   665: i2b
    //   666: bastore
    //   667: dup
    //   668: bipush #6
    //   670: lload_2
    //   671: bipush #8
    //   673: lushr
    //   674: l2i
    //   675: i2b
    //   676: bastore
    //   677: dup
    //   678: bipush #7
    //   680: lload_2
    //   681: l2i
    //   682: i2b
    //   683: bastore
    //   684: aload_0
    //   685: swap
    //   686: invokevirtual doFinal : ([B)[B
    //   689: astore #4
    //   691: aload #4
    //   693: iconst_0
    //   694: baload
    //   695: i2l
    //   696: ldc2_w 255
    //   699: land
    //   700: bipush #56
    //   702: lshl
    //   703: aload #4
    //   705: iconst_1
    //   706: baload
    //   707: i2l
    //   708: ldc2_w 255
    //   711: land
    //   712: bipush #48
    //   714: lshl
    //   715: lor
    //   716: aload #4
    //   718: iconst_2
    //   719: baload
    //   720: i2l
    //   721: ldc2_w 255
    //   724: land
    //   725: bipush #40
    //   727: lshl
    //   728: lor
    //   729: aload #4
    //   731: iconst_3
    //   732: baload
    //   733: i2l
    //   734: ldc2_w 255
    //   737: land
    //   738: bipush #32
    //   740: lshl
    //   741: lor
    //   742: aload #4
    //   744: iconst_4
    //   745: baload
    //   746: i2l
    //   747: ldc2_w 255
    //   750: land
    //   751: bipush #24
    //   753: lshl
    //   754: lor
    //   755: aload #4
    //   757: iconst_5
    //   758: baload
    //   759: i2l
    //   760: ldc2_w 255
    //   763: land
    //   764: bipush #16
    //   766: lshl
    //   767: lor
    //   768: aload #4
    //   770: bipush #6
    //   772: baload
    //   773: i2l
    //   774: ldc2_w 255
    //   777: land
    //   778: bipush #8
    //   780: lshl
    //   781: lor
    //   782: aload #4
    //   784: bipush #7
    //   786: baload
    //   787: i2l
    //   788: ldc2_w 255
    //   791: land
    //   792: lor
    //   793: dup2_x1
    //   794: pop2
    //   795: pop
    //   796: goto -> 600
    //   799: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x71D1;
    if (c[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = b[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])d.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/du", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      c[i] = Integer.valueOf(j);
    } 
    return c[i].intValue();
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
    //   65: ldc 'wtf/opal/du'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\du.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */