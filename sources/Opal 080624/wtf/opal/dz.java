package wtf.opal;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.lwjgl.BufferUtils;

public final class dz {
  private static int N;
  
  private static final long a = on.a(-2299404021795246125L, 5784101013372536037L, MethodHandles.lookup().lookupClass()).a(160190697406494L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long e;
  
  private static ByteBuffer C(Object[] paramArrayOfObject) {
    ByteBuffer byteBuffer1 = (ByteBuffer)paramArrayOfObject[0];
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    ByteBuffer byteBuffer2 = BufferUtils.createByteBuffer(i);
    byteBuffer1.flip();
    byteBuffer2.put(byteBuffer1);
    return byteBuffer2;
  }
  
  public static String o(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/io/InputStream
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/dz.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: invokestatic k : ()I
    //   28: new java/lang/StringBuilder
    //   31: dup
    //   32: invokespecial <init> : ()V
    //   35: astore #5
    //   37: istore #4
    //   39: new java/io/BufferedReader
    //   42: dup
    //   43: new java/io/InputStreamReader
    //   46: dup
    //   47: aload_3
    //   48: invokespecial <init> : (Ljava/io/InputStream;)V
    //   51: invokespecial <init> : (Ljava/io/Reader;)V
    //   54: astore #6
    //   56: aload #6
    //   58: invokevirtual readLine : ()Ljava/lang/String;
    //   61: dup
    //   62: astore #7
    //   64: ifnull -> 111
    //   67: aload #5
    //   69: aload #7
    //   71: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: getstatic wtf/opal/dz.e : J
    //   77: l2i
    //   78: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   81: lload_1
    //   82: lconst_0
    //   83: lcmp
    //   84: iflt -> 123
    //   87: pop
    //   88: iload #4
    //   90: ifne -> 121
    //   93: iload #4
    //   95: ifeq -> 56
    //   98: lload_1
    //   99: lconst_0
    //   100: lcmp
    //   101: iflt -> 88
    //   104: goto -> 111
    //   107: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   110: athrow
    //   111: goto -> 121
    //   114: astore #6
    //   116: aload #6
    //   118: invokevirtual printStackTrace : ()V
    //   121: aload #5
    //   123: invokevirtual toString : ()Ljava/lang/String;
    //   126: areturn
    // Exception table:
    //   from	to	target	type
    //   39	111	114	java/lang/Exception
    //   67	98	107	java/lang/Exception
  }
  
  public static ByteBuffer m(Object[] paramArrayOfObject) {
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
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast java/lang/Integer
    //   24: invokevirtual intValue : ()I
    //   27: istore #4
    //   29: pop
    //   30: getstatic wtf/opal/dz.a : J
    //   33: lload_1
    //   34: lxor
    //   35: lstore_1
    //   36: invokestatic k : ()I
    //   39: istore #5
    //   41: aload_3
    //   42: iload #5
    //   44: ifne -> 82
    //   47: sipush #13574
    //   50: ldc2_w 3669268056302136436
    //   53: lload_1
    //   54: lxor
    //   55: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   60: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   63: ifeq -> 81
    //   66: goto -> 73
    //   69: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   72: athrow
    //   73: aconst_null
    //   74: goto -> 89
    //   77: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   80: athrow
    //   81: aload_3
    //   82: iconst_0
    //   83: anewarray java/lang/String
    //   86: invokestatic get : (Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;
    //   89: astore #7
    //   91: aload #7
    //   93: lload_1
    //   94: lconst_0
    //   95: lcmp
    //   96: iflt -> 116
    //   99: iload #5
    //   101: ifne -> 116
    //   104: ifnull -> 277
    //   107: goto -> 114
    //   110: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   113: athrow
    //   114: aload #7
    //   116: invokestatic isReadable : (Ljava/nio/file/Path;)Z
    //   119: lload_1
    //   120: lconst_0
    //   121: lcmp
    //   122: iflt -> 301
    //   125: iload #5
    //   127: ifne -> 301
    //   130: ifeq -> 277
    //   133: goto -> 140
    //   136: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   139: athrow
    //   140: aload #7
    //   142: iconst_0
    //   143: anewarray java/nio/file/OpenOption
    //   146: invokestatic newByteChannel : (Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/nio/channels/SeekableByteChannel;
    //   149: astore #8
    //   151: aload #8
    //   153: invokeinterface size : ()J
    //   158: l2i
    //   159: iconst_1
    //   160: iadd
    //   161: invokestatic createByteBuffer : (I)Ljava/nio/ByteBuffer;
    //   164: astore #6
    //   166: aload #8
    //   168: aload #6
    //   170: invokeinterface read : (Ljava/nio/ByteBuffer;)I
    //   175: iconst_m1
    //   176: if_icmpeq -> 186
    //   179: goto -> 166
    //   182: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   185: athrow
    //   186: lload_1
    //   187: lconst_0
    //   188: lcmp
    //   189: iflt -> 216
    //   192: aload #8
    //   194: iload #5
    //   196: ifne -> 211
    //   199: ifnull -> 266
    //   202: goto -> 209
    //   205: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   208: athrow
    //   209: aload #8
    //   211: invokeinterface close : ()V
    //   216: goto -> 266
    //   219: astore #9
    //   221: lload_1
    //   222: lconst_0
    //   223: lcmp
    //   224: ifle -> 251
    //   227: aload #8
    //   229: iload #5
    //   231: ifne -> 246
    //   234: ifnull -> 263
    //   237: goto -> 244
    //   240: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   243: athrow
    //   244: aload #8
    //   246: invokeinterface close : ()V
    //   251: goto -> 263
    //   254: astore #10
    //   256: aload #9
    //   258: aload #10
    //   260: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   263: aload #9
    //   265: athrow
    //   266: lload_1
    //   267: lconst_0
    //   268: lcmp
    //   269: ifle -> 627
    //   272: iload #5
    //   274: ifeq -> 621
    //   277: aload_3
    //   278: sipush #17406
    //   281: ldc2_w 1686521455807486605
    //   284: lload_1
    //   285: lxor
    //   286: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   291: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   294: goto -> 301
    //   297: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   300: athrow
    //   301: ifeq -> 322
    //   304: new java/net/URL
    //   307: dup
    //   308: aload_3
    //   309: invokespecial <init> : (Ljava/lang/String;)V
    //   312: invokevirtual openStream : ()Ljava/io/InputStream;
    //   315: goto -> 331
    //   318: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   321: athrow
    //   322: ldc wtf/opal/dz
    //   324: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   327: aload_3
    //   328: invokevirtual getResourceAsStream : (Ljava/lang/String;)Ljava/io/InputStream;
    //   331: astore #8
    //   333: aload #8
    //   335: invokestatic newChannel : (Ljava/io/InputStream;)Ljava/nio/channels/ReadableByteChannel;
    //   338: astore #9
    //   340: iload #4
    //   342: invokestatic createByteBuffer : (I)Ljava/nio/ByteBuffer;
    //   345: astore #6
    //   347: aload #9
    //   349: aload #6
    //   351: invokeinterface read : (Ljava/nio/ByteBuffer;)I
    //   356: istore #10
    //   358: iload #10
    //   360: iconst_m1
    //   361: if_icmpne -> 387
    //   364: iload #5
    //   366: lload_1
    //   367: lconst_0
    //   368: lcmp
    //   369: iflt -> 456
    //   372: ifne -> 454
    //   375: iload #5
    //   377: ifeq -> 459
    //   380: goto -> 387
    //   383: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   386: athrow
    //   387: aload #6
    //   389: iload #5
    //   391: ifne -> 452
    //   394: goto -> 401
    //   397: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   400: athrow
    //   401: invokevirtual remaining : ()I
    //   404: ifne -> 454
    //   407: goto -> 414
    //   410: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   413: athrow
    //   414: aload #6
    //   416: aload #6
    //   418: invokevirtual capacity : ()I
    //   421: iconst_3
    //   422: imul
    //   423: iconst_2
    //   424: idiv
    //   425: iconst_2
    //   426: anewarray java/lang/Object
    //   429: dup_x1
    //   430: swap
    //   431: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   434: iconst_1
    //   435: swap
    //   436: aastore
    //   437: dup_x1
    //   438: swap
    //   439: iconst_0
    //   440: swap
    //   441: aastore
    //   442: invokestatic C : ([Ljava/lang/Object;)Ljava/nio/ByteBuffer;
    //   445: goto -> 452
    //   448: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   451: athrow
    //   452: astore #6
    //   454: iload #5
    //   456: ifeq -> 347
    //   459: lload_1
    //   460: lconst_0
    //   461: lcmp
    //   462: iflt -> 364
    //   465: lload_1
    //   466: lconst_0
    //   467: lcmp
    //   468: iflt -> 495
    //   471: aload #9
    //   473: iload #5
    //   475: ifne -> 490
    //   478: ifnull -> 545
    //   481: goto -> 488
    //   484: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   487: athrow
    //   488: aload #9
    //   490: invokeinterface close : ()V
    //   495: goto -> 545
    //   498: astore #10
    //   500: lload_1
    //   501: lconst_0
    //   502: lcmp
    //   503: iflt -> 530
    //   506: aload #9
    //   508: iload #5
    //   510: ifne -> 525
    //   513: ifnull -> 542
    //   516: goto -> 523
    //   519: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   522: athrow
    //   523: aload #9
    //   525: invokeinterface close : ()V
    //   530: goto -> 542
    //   533: astore #11
    //   535: aload #10
    //   537: aload #11
    //   539: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   542: aload #10
    //   544: athrow
    //   545: lload_1
    //   546: lconst_0
    //   547: lcmp
    //   548: ifle -> 573
    //   551: aload #8
    //   553: iload #5
    //   555: ifne -> 570
    //   558: ifnull -> 621
    //   561: goto -> 568
    //   564: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   567: athrow
    //   568: aload #8
    //   570: invokevirtual close : ()V
    //   573: goto -> 621
    //   576: astore #9
    //   578: lload_1
    //   579: lconst_0
    //   580: lcmp
    //   581: ifle -> 606
    //   584: aload #8
    //   586: iload #5
    //   588: ifne -> 603
    //   591: ifnull -> 618
    //   594: goto -> 601
    //   597: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   600: athrow
    //   601: aload #8
    //   603: invokevirtual close : ()V
    //   606: goto -> 618
    //   609: astore #10
    //   611: aload #9
    //   613: aload #10
    //   615: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   618: aload #9
    //   620: athrow
    //   621: aload #6
    //   623: invokevirtual flip : ()Ljava/nio/ByteBuffer;
    //   626: pop
    //   627: aload #6
    //   629: invokestatic memSlice : (Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
    //   632: areturn
    //   633: astore #6
    //   635: aload #6
    //   637: invokevirtual printStackTrace : ()V
    //   640: aconst_null
    //   641: areturn
    // Exception table:
    //   from	to	target	type
    //   41	66	69	java/lang/Throwable
    //   41	632	633	java/io/IOException
    //   47	77	77	java/lang/Throwable
    //   91	107	110	java/lang/Throwable
    //   116	133	136	java/lang/Throwable
    //   151	186	219	java/lang/Throwable
    //   166	182	182	java/lang/Throwable
    //   186	202	205	java/lang/Throwable
    //   221	237	240	java/lang/Throwable
    //   244	251	254	java/lang/Throwable
    //   266	294	297	java/lang/Throwable
    //   301	318	318	java/lang/Throwable
    //   333	545	576	java/lang/Throwable
    //   340	459	498	java/lang/Throwable
    //   364	380	383	java/lang/Throwable
    //   375	394	397	java/lang/Throwable
    //   387	407	410	java/lang/Throwable
    //   401	445	448	java/lang/Throwable
    //   459	481	484	java/lang/Throwable
    //   500	516	519	java/lang/Throwable
    //   523	530	533	java/lang/Throwable
    //   545	561	564	java/lang/Throwable
    //   578	594	597	java/lang/Throwable
    //   601	606	609	java/lang/Throwable
  }
  
  public static ByteBuffer v(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/io/InputStream
    //   7: astore_1
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_3
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast java/lang/Integer
    //   24: invokevirtual intValue : ()I
    //   27: istore_2
    //   28: pop
    //   29: getstatic wtf/opal/dz.a : J
    //   32: lload_3
    //   33: lxor
    //   34: lstore_3
    //   35: invokestatic d : ()I
    //   38: istore #5
    //   40: aload_1
    //   41: iload #5
    //   43: ifeq -> 57
    //   46: ifnull -> 277
    //   49: goto -> 56
    //   52: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   55: athrow
    //   56: aload_1
    //   57: invokestatic newChannel : (Ljava/io/InputStream;)Ljava/nio/channels/ReadableByteChannel;
    //   60: astore #7
    //   62: iload_2
    //   63: invokestatic createByteBuffer : (I)Ljava/nio/ByteBuffer;
    //   66: astore #6
    //   68: aload #7
    //   70: aload #6
    //   72: invokeinterface read : (Ljava/nio/ByteBuffer;)I
    //   77: istore #8
    //   79: iload #8
    //   81: iconst_m1
    //   82: if_icmpne -> 108
    //   85: iload #5
    //   87: lload_3
    //   88: lconst_0
    //   89: lcmp
    //   90: iflt -> 177
    //   93: ifeq -> 175
    //   96: iload #5
    //   98: ifne -> 180
    //   101: goto -> 108
    //   104: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   107: athrow
    //   108: aload #6
    //   110: iload #5
    //   112: ifeq -> 173
    //   115: goto -> 122
    //   118: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   121: athrow
    //   122: invokevirtual remaining : ()I
    //   125: ifne -> 175
    //   128: goto -> 135
    //   131: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   134: athrow
    //   135: aload #6
    //   137: aload #6
    //   139: invokevirtual capacity : ()I
    //   142: iconst_3
    //   143: imul
    //   144: iconst_2
    //   145: idiv
    //   146: iconst_2
    //   147: anewarray java/lang/Object
    //   150: dup_x1
    //   151: swap
    //   152: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   155: iconst_1
    //   156: swap
    //   157: aastore
    //   158: dup_x1
    //   159: swap
    //   160: iconst_0
    //   161: swap
    //   162: aastore
    //   163: invokestatic C : ([Ljava/lang/Object;)Ljava/nio/ByteBuffer;
    //   166: goto -> 173
    //   169: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   172: athrow
    //   173: astore #6
    //   175: iload #5
    //   177: ifne -> 68
    //   180: lload_3
    //   181: lconst_0
    //   182: lcmp
    //   183: ifle -> 85
    //   186: lload_3
    //   187: lconst_0
    //   188: lcmp
    //   189: iflt -> 216
    //   192: aload #7
    //   194: iload #5
    //   196: ifeq -> 211
    //   199: ifnull -> 266
    //   202: goto -> 209
    //   205: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   208: athrow
    //   209: aload #7
    //   211: invokeinterface close : ()V
    //   216: goto -> 266
    //   219: astore #8
    //   221: lload_3
    //   222: lconst_0
    //   223: lcmp
    //   224: ifle -> 251
    //   227: aload #7
    //   229: iload #5
    //   231: ifeq -> 246
    //   234: ifnull -> 263
    //   237: goto -> 244
    //   240: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   243: athrow
    //   244: aload #7
    //   246: invokeinterface close : ()V
    //   251: goto -> 263
    //   254: astore #9
    //   256: aload #8
    //   258: aload #9
    //   260: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   263: aload #8
    //   265: athrow
    //   266: lload_3
    //   267: lconst_0
    //   268: lcmp
    //   269: iflt -> 308
    //   272: iload #5
    //   274: ifne -> 302
    //   277: new java/lang/IllegalArgumentException
    //   280: dup
    //   281: sipush #24651
    //   284: ldc2_w 2329512364003832183
    //   287: lload_3
    //   288: lxor
    //   289: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   294: invokespecial <init> : (Ljava/lang/String;)V
    //   297: athrow
    //   298: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   301: athrow
    //   302: aload #6
    //   304: invokevirtual flip : ()Ljava/nio/ByteBuffer;
    //   307: pop
    //   308: aload #6
    //   310: invokestatic memSlice : (Ljava/nio/ByteBuffer;)Ljava/nio/ByteBuffer;
    //   313: areturn
    //   314: astore #6
    //   316: aload #6
    //   318: invokevirtual printStackTrace : ()V
    //   321: aconst_null
    //   322: areturn
    // Exception table:
    //   from	to	target	type
    //   40	49	52	java/lang/Throwable
    //   40	313	314	java/io/IOException
    //   62	180	219	java/lang/Throwable
    //   85	101	104	java/lang/Throwable
    //   96	115	118	java/lang/Throwable
    //   108	128	131	java/lang/Throwable
    //   122	166	169	java/lang/Throwable
    //   180	202	205	java/lang/Throwable
    //   221	237	240	java/lang/Throwable
    //   244	251	254	java/lang/Throwable
    //   266	298	298	java/lang/Throwable
  }
  
  public static byte[] D(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    long l = ((Long)paramArrayOfObject[1]).longValue();
    l = a ^ l;
    InputStream inputStream = dz.class.getResourceAsStream(str);
    int i = k();
    try {
      try {
        if (i != 0)
          d.p(new d[5]); 
        return inputStream.readAllBytes();
      } catch (IOException iOException) {
        throw new RuntimeException(iOException);
      } 
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
  }
  
  public static void n(int paramInt) {
    N = paramInt;
  }
  
  public static int k() {
    return N;
  }
  
  public static int d() {
    int i = k();
    try {
      if (i == 0)
        return 19; 
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    return 0;
  }
  
  static {
    n(0);
    long l = a ^ 0x5D0CBD4F889DL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "&Ànü\rHøo5\035I<>\020vTÅ´â\\>\027ISgVC\n8¨Å³n\025_,zÅ\026©R}µt²·ï÷¼6r\026\021±\016'ÅTN\024Ýz>6ëSIñÒ:J¨ÿæeC_").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static Throwable a(Throwable paramThrowable) {
    return paramThrowable;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1682;
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
        throw new RuntimeException("wtf/opal/dz", exception);
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
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/dz'
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
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dz.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */