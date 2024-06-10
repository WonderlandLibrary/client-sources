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

class bv {
  private final byte[] e;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  bv(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/bv.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: aload_0
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: sipush #9655
    //   14: ldc2_w 7518146311728400779
    //   17: lload_1
    //   18: lxor
    //   19: <illegal opcode> o : (IJ)I
    //   24: newarray byte
    //   26: putfield e : [B
    //   29: return
  }
  
  bv(long paramLong, bv parambv) {
    // Byte code:
    //   0: getstatic wtf/opal/bv.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: aload_0
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: sipush #32152
    //   14: ldc2_w 172854930930892887
    //   17: lload_1
    //   18: lxor
    //   19: <illegal opcode> o : (IJ)I
    //   24: newarray byte
    //   26: putfield e : [B
    //   29: aload_3
    //   30: getfield e : [B
    //   33: iconst_0
    //   34: aload_0
    //   35: getfield e : [B
    //   38: iconst_0
    //   39: aload_0
    //   40: getfield e : [B
    //   43: arraylength
    //   44: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   47: return
  }
  
  void I(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Integer
    //   15: invokevirtual intValue : ()I
    //   18: istore #5
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/Long
    //   26: invokevirtual longValue : ()J
    //   29: lstore_2
    //   30: pop
    //   31: getstatic wtf/opal/bv.a : J
    //   34: lload_2
    //   35: lxor
    //   36: lstore_2
    //   37: invokestatic O : ()I
    //   40: aload_0
    //   41: aload #4
    //   43: iconst_1
    //   44: anewarray java/lang/Object
    //   47: dup_x1
    //   48: swap
    //   49: iconst_0
    //   50: swap
    //   51: aastore
    //   52: invokevirtual l : ([Ljava/lang/Object;)I
    //   55: istore #7
    //   57: istore #6
    //   59: iload #6
    //   61: ifeq -> 108
    //   64: iload #5
    //   66: sipush #8270
    //   69: ldc2_w 2976619132502979866
    //   72: lload_2
    //   73: lxor
    //   74: <illegal opcode> o : (IJ)I
    //   79: if_icmpge -> 119
    //   82: goto -> 89
    //   85: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   88: athrow
    //   89: aload_0
    //   90: getfield e : [B
    //   93: iload #7
    //   95: iload #5
    //   97: iconst_1
    //   98: iadd
    //   99: i2b
    //   100: bastore
    //   101: goto -> 108
    //   104: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   107: athrow
    //   108: lload_2
    //   109: lconst_0
    //   110: lcmp
    //   111: iflt -> 127
    //   114: iload #6
    //   116: ifne -> 134
    //   119: aload_0
    //   120: getfield e : [B
    //   123: iload #7
    //   125: iconst_0
    //   126: bastore
    //   127: goto -> 134
    //   130: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   133: athrow
    //   134: return
    // Exception table:
    //   from	to	target	type
    //   59	82	85	wtf/opal/x5
    //   64	101	104	wtf/opal/x5
    //   108	127	130	wtf/opal/x5
  }
  
  void T(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore #4
    //   12: dup
    //   13: iconst_1
    //   14: aaload
    //   15: checkcast java/lang/Long
    //   18: invokevirtual longValue : ()J
    //   21: lstore_2
    //   22: pop
    //   23: getstatic wtf/opal/bv.a : J
    //   26: lload_2
    //   27: lxor
    //   28: lstore_2
    //   29: invokestatic T : ()I
    //   32: iconst_0
    //   33: istore #6
    //   35: istore #5
    //   37: iload #6
    //   39: aload_0
    //   40: getfield e : [B
    //   43: arraylength
    //   44: if_icmpge -> 199
    //   47: aload_0
    //   48: getfield e : [B
    //   51: iload #6
    //   53: baload
    //   54: sipush #10604
    //   57: ldc2_w 3446048517947094787
    //   60: lload_2
    //   61: lxor
    //   62: <illegal opcode> o : (IJ)I
    //   67: iand
    //   68: iload #4
    //   70: iconst_1
    //   71: iadd
    //   72: lload_2
    //   73: lconst_0
    //   74: lcmp
    //   75: iflt -> 169
    //   78: iload #5
    //   80: ifne -> 169
    //   83: if_icmpne -> 119
    //   86: goto -> 93
    //   89: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   92: athrow
    //   93: aload_0
    //   94: getfield e : [B
    //   97: iload #6
    //   99: iconst_0
    //   100: bastore
    //   101: iload #5
    //   103: lload_2
    //   104: lconst_0
    //   105: lcmp
    //   106: iflt -> 196
    //   109: ifeq -> 191
    //   112: goto -> 119
    //   115: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: aload_0
    //   120: getfield e : [B
    //   123: iload #6
    //   125: iload #5
    //   127: lload_2
    //   128: lconst_0
    //   129: lcmp
    //   130: ifle -> 190
    //   133: ifne -> 185
    //   136: goto -> 143
    //   139: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   142: athrow
    //   143: baload
    //   144: sipush #8270
    //   147: ldc2_w 2976670523454455331
    //   150: lload_2
    //   151: lxor
    //   152: <illegal opcode> o : (IJ)I
    //   157: iand
    //   158: iload #4
    //   160: iconst_1
    //   161: iadd
    //   162: goto -> 169
    //   165: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   168: athrow
    //   169: if_icmple -> 191
    //   172: aload_0
    //   173: getfield e : [B
    //   176: iload #6
    //   178: goto -> 185
    //   181: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: dup2
    //   186: baload
    //   187: iconst_1
    //   188: isub
    //   189: i2b
    //   190: bastore
    //   191: iinc #6, 1
    //   194: iload #5
    //   196: ifeq -> 37
    //   199: lload_2
    //   200: lconst_0
    //   201: lcmp
    //   202: ifle -> 47
    //   205: return
    // Exception table:
    //   from	to	target	type
    //   47	86	89	wtf/opal/x5
    //   83	112	115	wtf/opal/x5
    //   93	136	139	wtf/opal/x5
    //   119	162	165	wtf/opal/x5
    //   169	178	181	wtf/opal/x5
  }
  
  int J(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Object
    //   17: astore #4
    //   19: pop
    //   20: getstatic wtf/opal/bv.a : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: aload_0
    //   27: aload #4
    //   29: iconst_1
    //   30: anewarray java/lang/Object
    //   33: dup_x1
    //   34: swap
    //   35: iconst_0
    //   36: swap
    //   37: aastore
    //   38: invokevirtual l : ([Ljava/lang/Object;)I
    //   41: istore #5
    //   43: aload_0
    //   44: getfield e : [B
    //   47: iload #5
    //   49: baload
    //   50: sipush #8270
    //   53: ldc2_w 2976657557405457399
    //   56: lload_2
    //   57: lxor
    //   58: <illegal opcode> o : (IJ)I
    //   63: iand
    //   64: iconst_1
    //   65: isub
    //   66: ireturn
  }
  
  private int l(Object[] paramArrayOfObject) {
    Object object = paramArrayOfObject[0];
    return object.hashCode() & this.e.length - 1;
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 7089465730194218209
    //   3: ldc2_w 131955810104059745
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 270546417798707
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/bv.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/bv.d : Ljava/util/Map;
    //   38: getstatic wtf/opal/bv.a : J
    //   41: ldc2_w 38290408346332
    //   44: lxor
    //   45: lstore_0
    //   46: ldc 'DES/CBC/NoPadding'
    //   48: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   51: dup
    //   52: astore_2
    //   53: iconst_2
    //   54: ldc 'DES'
    //   56: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   59: bipush #8
    //   61: newarray byte
    //   63: dup
    //   64: iconst_0
    //   65: lload_0
    //   66: bipush #56
    //   68: lushr
    //   69: l2i
    //   70: i2b
    //   71: bastore
    //   72: iconst_1
    //   73: istore_3
    //   74: iload_3
    //   75: bipush #8
    //   77: if_icmpge -> 100
    //   80: dup
    //   81: iload_3
    //   82: lload_0
    //   83: iload_3
    //   84: bipush #8
    //   86: imul
    //   87: lshl
    //   88: bipush #56
    //   90: lushr
    //   91: l2i
    //   92: i2b
    //   93: bastore
    //   94: iinc #3, 1
    //   97: goto -> 74
    //   100: new javax/crypto/spec/DESKeySpec
    //   103: dup_x1
    //   104: swap
    //   105: invokespecial <init> : ([B)V
    //   108: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   111: new javax/crypto/spec/IvParameterSpec
    //   114: dup
    //   115: bipush #8
    //   117: newarray byte
    //   119: invokespecial <init> : ([B)V
    //   122: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   125: iconst_4
    //   126: newarray long
    //   128: astore #8
    //   130: iconst_0
    //   131: istore #5
    //   133: ldc 'ùytÂNygVÛãÓÍT'
    //   135: dup
    //   136: astore #6
    //   138: invokevirtual length : ()I
    //   141: istore #7
    //   143: iconst_0
    //   144: istore #4
    //   146: aload #6
    //   148: iload #4
    //   150: iinc #4, 8
    //   153: iload #4
    //   155: invokevirtual substring : (II)Ljava/lang/String;
    //   158: ldc 'ISO-8859-1'
    //   160: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   163: astore #9
    //   165: aload #8
    //   167: iload #5
    //   169: iinc #5, 1
    //   172: aload #9
    //   174: iconst_0
    //   175: baload
    //   176: i2l
    //   177: ldc2_w 255
    //   180: land
    //   181: bipush #56
    //   183: lshl
    //   184: aload #9
    //   186: iconst_1
    //   187: baload
    //   188: i2l
    //   189: ldc2_w 255
    //   192: land
    //   193: bipush #48
    //   195: lshl
    //   196: lor
    //   197: aload #9
    //   199: iconst_2
    //   200: baload
    //   201: i2l
    //   202: ldc2_w 255
    //   205: land
    //   206: bipush #40
    //   208: lshl
    //   209: lor
    //   210: aload #9
    //   212: iconst_3
    //   213: baload
    //   214: i2l
    //   215: ldc2_w 255
    //   218: land
    //   219: bipush #32
    //   221: lshl
    //   222: lor
    //   223: aload #9
    //   225: iconst_4
    //   226: baload
    //   227: i2l
    //   228: ldc2_w 255
    //   231: land
    //   232: bipush #24
    //   234: lshl
    //   235: lor
    //   236: aload #9
    //   238: iconst_5
    //   239: baload
    //   240: i2l
    //   241: ldc2_w 255
    //   244: land
    //   245: bipush #16
    //   247: lshl
    //   248: lor
    //   249: aload #9
    //   251: bipush #6
    //   253: baload
    //   254: i2l
    //   255: ldc2_w 255
    //   258: land
    //   259: bipush #8
    //   261: lshl
    //   262: lor
    //   263: aload #9
    //   265: bipush #7
    //   267: baload
    //   268: i2l
    //   269: ldc2_w 255
    //   272: land
    //   273: lor
    //   274: iconst_m1
    //   275: goto -> 454
    //   278: lastore
    //   279: iload #4
    //   281: iload #7
    //   283: if_icmplt -> 146
    //   286: ldc 'â½0»çÂºü(Àþé'
    //   288: dup
    //   289: astore #6
    //   291: invokevirtual length : ()I
    //   294: istore #7
    //   296: iconst_0
    //   297: istore #4
    //   299: aload #6
    //   301: iload #4
    //   303: iinc #4, 8
    //   306: iload #4
    //   308: invokevirtual substring : (II)Ljava/lang/String;
    //   311: ldc 'ISO-8859-1'
    //   313: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   316: astore #9
    //   318: aload #8
    //   320: iload #5
    //   322: iinc #5, 1
    //   325: aload #9
    //   327: iconst_0
    //   328: baload
    //   329: i2l
    //   330: ldc2_w 255
    //   333: land
    //   334: bipush #56
    //   336: lshl
    //   337: aload #9
    //   339: iconst_1
    //   340: baload
    //   341: i2l
    //   342: ldc2_w 255
    //   345: land
    //   346: bipush #48
    //   348: lshl
    //   349: lor
    //   350: aload #9
    //   352: iconst_2
    //   353: baload
    //   354: i2l
    //   355: ldc2_w 255
    //   358: land
    //   359: bipush #40
    //   361: lshl
    //   362: lor
    //   363: aload #9
    //   365: iconst_3
    //   366: baload
    //   367: i2l
    //   368: ldc2_w 255
    //   371: land
    //   372: bipush #32
    //   374: lshl
    //   375: lor
    //   376: aload #9
    //   378: iconst_4
    //   379: baload
    //   380: i2l
    //   381: ldc2_w 255
    //   384: land
    //   385: bipush #24
    //   387: lshl
    //   388: lor
    //   389: aload #9
    //   391: iconst_5
    //   392: baload
    //   393: i2l
    //   394: ldc2_w 255
    //   397: land
    //   398: bipush #16
    //   400: lshl
    //   401: lor
    //   402: aload #9
    //   404: bipush #6
    //   406: baload
    //   407: i2l
    //   408: ldc2_w 255
    //   411: land
    //   412: bipush #8
    //   414: lshl
    //   415: lor
    //   416: aload #9
    //   418: bipush #7
    //   420: baload
    //   421: i2l
    //   422: ldc2_w 255
    //   425: land
    //   426: lor
    //   427: iconst_0
    //   428: goto -> 454
    //   431: lastore
    //   432: iload #4
    //   434: iload #7
    //   436: if_icmplt -> 299
    //   439: aload #8
    //   441: putstatic wtf/opal/bv.b : [J
    //   444: iconst_4
    //   445: anewarray java/lang/Integer
    //   448: putstatic wtf/opal/bv.c : [Ljava/lang/Integer;
    //   451: goto -> 672
    //   454: dup_x2
    //   455: pop
    //   456: lstore #10
    //   458: bipush #8
    //   460: newarray byte
    //   462: dup
    //   463: iconst_0
    //   464: lload #10
    //   466: bipush #56
    //   468: lushr
    //   469: l2i
    //   470: i2b
    //   471: bastore
    //   472: dup
    //   473: iconst_1
    //   474: lload #10
    //   476: bipush #48
    //   478: lushr
    //   479: l2i
    //   480: i2b
    //   481: bastore
    //   482: dup
    //   483: iconst_2
    //   484: lload #10
    //   486: bipush #40
    //   488: lushr
    //   489: l2i
    //   490: i2b
    //   491: bastore
    //   492: dup
    //   493: iconst_3
    //   494: lload #10
    //   496: bipush #32
    //   498: lushr
    //   499: l2i
    //   500: i2b
    //   501: bastore
    //   502: dup
    //   503: iconst_4
    //   504: lload #10
    //   506: bipush #24
    //   508: lushr
    //   509: l2i
    //   510: i2b
    //   511: bastore
    //   512: dup
    //   513: iconst_5
    //   514: lload #10
    //   516: bipush #16
    //   518: lushr
    //   519: l2i
    //   520: i2b
    //   521: bastore
    //   522: dup
    //   523: bipush #6
    //   525: lload #10
    //   527: bipush #8
    //   529: lushr
    //   530: l2i
    //   531: i2b
    //   532: bastore
    //   533: dup
    //   534: bipush #7
    //   536: lload #10
    //   538: l2i
    //   539: i2b
    //   540: bastore
    //   541: aload_2
    //   542: swap
    //   543: invokevirtual doFinal : ([B)[B
    //   546: astore #12
    //   548: aload #12
    //   550: iconst_0
    //   551: baload
    //   552: i2l
    //   553: ldc2_w 255
    //   556: land
    //   557: bipush #56
    //   559: lshl
    //   560: aload #12
    //   562: iconst_1
    //   563: baload
    //   564: i2l
    //   565: ldc2_w 255
    //   568: land
    //   569: bipush #48
    //   571: lshl
    //   572: lor
    //   573: aload #12
    //   575: iconst_2
    //   576: baload
    //   577: i2l
    //   578: ldc2_w 255
    //   581: land
    //   582: bipush #40
    //   584: lshl
    //   585: lor
    //   586: aload #12
    //   588: iconst_3
    //   589: baload
    //   590: i2l
    //   591: ldc2_w 255
    //   594: land
    //   595: bipush #32
    //   597: lshl
    //   598: lor
    //   599: aload #12
    //   601: iconst_4
    //   602: baload
    //   603: i2l
    //   604: ldc2_w 255
    //   607: land
    //   608: bipush #24
    //   610: lshl
    //   611: lor
    //   612: aload #12
    //   614: iconst_5
    //   615: baload
    //   616: i2l
    //   617: ldc2_w 255
    //   620: land
    //   621: bipush #16
    //   623: lshl
    //   624: lor
    //   625: aload #12
    //   627: bipush #6
    //   629: baload
    //   630: i2l
    //   631: ldc2_w 255
    //   634: land
    //   635: bipush #8
    //   637: lshl
    //   638: lor
    //   639: aload #12
    //   641: bipush #7
    //   643: baload
    //   644: i2l
    //   645: ldc2_w 255
    //   648: land
    //   649: lor
    //   650: dup2_x1
    //   651: pop2
    //   652: tableswitch default -> 278, 0 -> 431
    //   672: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5630;
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
        throw new RuntimeException("wtf/opal/bv", exception);
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
    //   65: ldc 'wtf/opal/bv'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bv.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */