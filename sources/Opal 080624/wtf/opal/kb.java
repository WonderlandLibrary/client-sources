package wtf.opal;

import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class kb {
  private static final long a = on.a(4635895047195894657L, 4034920323154409132L, MethodHandles.lookup().lookupClass()).a(41643532339744L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public static p3 G(Object[] paramArrayOfObject) throws IOException {
    p3 p3 = (p3)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x178289716987L;
    (new Object[3])[2] = p3.v(new Object[0]);
    (new Object[3])[1] = p3.W(new Object[0]);
    new Object[3];
    return t(new Object[] { Long.valueOf(l2) });
  }
  
  public static p3 t(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore #4
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast wtf/opal/ks
    //   25: astore_1
    //   26: pop
    //   27: getstatic wtf/opal/kb.a : J
    //   30: lload_2
    //   31: lxor
    //   32: lstore_2
    //   33: lload_2
    //   34: dup2
    //   35: ldc2_w 47529270920072
    //   38: lxor
    //   39: lstore #5
    //   41: dup2
    //   42: ldc2_w 64072339570567
    //   45: lxor
    //   46: lstore #7
    //   48: pop2
    //   49: invokestatic A : ()[Ljava/lang/String;
    //   52: new java/util/HashMap
    //   55: dup
    //   56: invokespecial <init> : ()V
    //   59: astore #10
    //   61: aload #10
    //   63: sipush #30601
    //   66: ldc2_w 3477279257249570590
    //   69: lload_2
    //   70: lxor
    //   71: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   76: aload_1
    //   77: iconst_0
    //   78: anewarray java/lang/Object
    //   81: invokevirtual U : ([Ljava/lang/Object;)Ljava/lang/String;
    //   84: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   89: pop
    //   90: astore #9
    //   92: aload #10
    //   94: sipush #9859
    //   97: ldc2_w 772368953981603377
    //   100: lload_2
    //   101: lxor
    //   102: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   107: sipush #7748
    //   110: ldc2_w 3512741917974148820
    //   113: lload_2
    //   114: lxor
    //   115: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   120: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   125: pop
    //   126: aload_1
    //   127: iconst_0
    //   128: anewarray java/lang/Object
    //   131: invokevirtual u : ([Ljava/lang/Object;)Ljava/lang/String;
    //   134: aload #9
    //   136: ifnonnull -> 278
    //   139: invokestatic nonNull : (Ljava/lang/Object;)Z
    //   142: ifeq -> 188
    //   145: goto -> 152
    //   148: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   151: athrow
    //   152: aload #10
    //   154: sipush #1915
    //   157: ldc2_w 1751755080378396623
    //   160: lload_2
    //   161: lxor
    //   162: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   167: aload_1
    //   168: iconst_0
    //   169: anewarray java/lang/Object
    //   172: invokevirtual u : ([Ljava/lang/Object;)Ljava/lang/String;
    //   175: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   180: pop
    //   181: goto -> 188
    //   184: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   187: athrow
    //   188: aload #10
    //   190: sipush #13723
    //   193: ldc2_w 5260981641411534082
    //   196: lload_2
    //   197: lxor
    //   198: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   203: sipush #11505
    //   206: ldc2_w 3085723527248643136
    //   209: lload_2
    //   210: lxor
    //   211: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   216: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   221: pop
    //   222: aload #10
    //   224: sipush #23663
    //   227: ldc2_w 460160325913310448
    //   230: lload_2
    //   231: lxor
    //   232: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   237: aload #4
    //   239: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   244: pop
    //   245: aload #10
    //   247: sipush #7182
    //   250: ldc2_w 2692993092468782214
    //   253: lload_2
    //   254: lxor
    //   255: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   260: sipush #28802
    //   263: ldc2_w 7732905372138854420
    //   266: lload_2
    //   267: lxor
    //   268: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   273: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   278: pop
    //   279: new java/net/URL
    //   282: dup
    //   283: sipush #23150
    //   286: ldc2_w 3223092592829395708
    //   289: lload_2
    //   290: lxor
    //   291: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   296: invokespecial <init> : (Ljava/lang/String;)V
    //   299: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   302: checkcast javax/net/ssl/HttpsURLConnection
    //   305: astore #11
    //   307: aload #11
    //   309: iconst_1
    //   310: invokevirtual setDoOutput : (Z)V
    //   313: aload #11
    //   315: iconst_1
    //   316: invokevirtual setDoInput : (Z)V
    //   319: aload #11
    //   321: sipush #5155
    //   324: ldc2_w 1326321457958927547
    //   327: lload_2
    //   328: lxor
    //   329: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   334: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   337: aload #11
    //   339: sipush #24348
    //   342: ldc2_w 2492844838908393391
    //   345: lload_2
    //   346: lxor
    //   347: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   352: sipush #11401
    //   355: ldc2_w 3295504520378745916
    //   358: lload_2
    //   359: lxor
    //   360: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   365: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   368: aload #11
    //   370: sipush #28178
    //   373: ldc2_w 2025427828415841940
    //   376: lload_2
    //   377: lxor
    //   378: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   383: sipush #16635
    //   386: ldc2_w 3122352382518749305
    //   389: lload_2
    //   390: lxor
    //   391: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   396: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   399: aload #11
    //   401: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   404: aload #10
    //   406: iconst_1
    //   407: anewarray java/lang/Object
    //   410: dup_x1
    //   411: swap
    //   412: iconst_0
    //   413: swap
    //   414: aastore
    //   415: invokestatic G : ([Ljava/lang/Object;)Ljava/lang/String;
    //   418: invokevirtual getBytes : ()[B
    //   421: invokevirtual write : ([B)V
    //   424: new java/io/InputStreamReader
    //   427: dup
    //   428: aload #11
    //   430: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   433: invokespecial <init> : (Ljava/io/InputStream;)V
    //   436: lload #5
    //   438: iconst_2
    //   439: anewarray java/lang/Object
    //   442: dup_x2
    //   443: dup_x2
    //   444: pop
    //   445: invokestatic valueOf : (J)Ljava/lang/Long;
    //   448: iconst_1
    //   449: swap
    //   450: aastore
    //   451: dup_x1
    //   452: swap
    //   453: iconst_0
    //   454: swap
    //   455: aastore
    //   456: invokestatic i : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   459: invokevirtual w : ()Lwtf/opal/kq;
    //   462: lload #7
    //   464: aload_1
    //   465: iconst_3
    //   466: anewarray java/lang/Object
    //   469: dup_x1
    //   470: swap
    //   471: iconst_2
    //   472: swap
    //   473: aastore
    //   474: dup_x2
    //   475: dup_x2
    //   476: pop
    //   477: invokestatic valueOf : (J)Ljava/lang/Long;
    //   480: iconst_1
    //   481: swap
    //   482: aastore
    //   483: dup_x1
    //   484: swap
    //   485: iconst_0
    //   486: swap
    //   487: aastore
    //   488: invokestatic R : ([Ljava/lang/Object;)Lwtf/opal/p3;
    //   491: lload_2
    //   492: lconst_0
    //   493: lcmp
    //   494: iflt -> 509
    //   497: aload #9
    //   499: ifnull -> 516
    //   502: iconst_1
    //   503: anewarray wtf/opal/d
    //   506: invokestatic p : ([Lwtf/opal/d;)V
    //   509: goto -> 516
    //   512: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   515: athrow
    //   516: areturn
    // Exception table:
    //   from	to	target	type
    //   92	145	148	java/io/IOException
    //   139	181	184	java/io/IOException
    //   307	509	512	java/io/IOException
  }
  
  private static String G(Object[] paramArrayOfObject) {
    Map map = (Map)paramArrayOfObject[0];
    StringJoiner stringJoiner = new StringJoiner("&");
    map.forEach(stringJoiner::lambda$encodeToForm$0);
    return stringJoiner.toString();
  }
  
  public static bl e(Object[] paramArrayOfObject) throws IOException {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    p3 p3 = (p3)paramArrayOfObject[1];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x2766D57751E4L;
    (new Object[3])[2] = p3.v(new Object[0]).e(new Object[0]);
    (new Object[3])[1] = p3.k(new Object[0]);
    new Object[3];
    return n(new Object[] { Long.valueOf(l2) });
  }
  
  public static bl n(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore_1
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast java/lang/String
    //   24: astore #4
    //   26: pop
    //   27: getstatic wtf/opal/kb.a : J
    //   30: lload_2
    //   31: lxor
    //   32: lstore_2
    //   33: lload_2
    //   34: dup2
    //   35: ldc2_w 98694195595917
    //   38: lxor
    //   39: lstore #5
    //   41: dup2
    //   42: ldc2_w 103085935426038
    //   45: lxor
    //   46: lstore #7
    //   48: dup2
    //   49: ldc2_w 81605613830923
    //   52: lxor
    //   53: lstore #9
    //   55: dup2
    //   56: ldc2_w 7109305550059
    //   59: lxor
    //   60: lstore #11
    //   62: dup2
    //   63: ldc2_w 135691598850954
    //   66: lxor
    //   67: lstore #13
    //   69: pop2
    //   70: new java/net/URL
    //   73: dup
    //   74: sipush #22075
    //   77: ldc2_w 8734428696415323387
    //   80: lload_2
    //   81: lxor
    //   82: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   87: invokespecial <init> : (Ljava/lang/String;)V
    //   90: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   93: checkcast javax/net/ssl/HttpsURLConnection
    //   96: astore #16
    //   98: aload #16
    //   100: iconst_1
    //   101: invokevirtual setDoOutput : (Z)V
    //   104: invokestatic A : ()[Ljava/lang/String;
    //   107: aload #16
    //   109: iconst_1
    //   110: invokevirtual setDoInput : (Z)V
    //   113: aload #16
    //   115: sipush #23380
    //   118: ldc2_w 1335726955264788916
    //   121: lload_2
    //   122: lxor
    //   123: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   128: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   131: astore #15
    //   133: aload #16
    //   135: sipush #28178
    //   138: ldc2_w 2025411585591901418
    //   141: lload_2
    //   142: lxor
    //   143: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   148: sipush #11401
    //   151: ldc2_w 3295382709943025218
    //   154: lload_2
    //   155: lxor
    //   156: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   161: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   164: aload #16
    //   166: sipush #24348
    //   169: ldc2_w 2492790598491983313
    //   172: lload_2
    //   173: lxor
    //   174: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   179: sipush #11401
    //   182: ldc2_w 3295382709943025218
    //   185: lload_2
    //   186: lxor
    //   187: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   192: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   195: aload #16
    //   197: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   200: lload #11
    //   202: iconst_1
    //   203: anewarray java/lang/Object
    //   206: dup_x2
    //   207: dup_x2
    //   208: pop
    //   209: invokestatic valueOf : (J)Ljava/lang/Long;
    //   212: iconst_0
    //   213: swap
    //   214: aastore
    //   215: invokestatic C : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   218: sipush #19114
    //   221: ldc2_w 5139649535911024759
    //   224: lload_2
    //   225: lxor
    //   226: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   231: lload #11
    //   233: iconst_1
    //   234: anewarray java/lang/Object
    //   237: dup_x2
    //   238: dup_x2
    //   239: pop
    //   240: invokestatic valueOf : (J)Ljava/lang/Long;
    //   243: iconst_0
    //   244: swap
    //   245: aastore
    //   246: invokestatic C : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   249: lload #13
    //   251: sipush #10913
    //   254: ldc2_w 6484918658139895910
    //   257: lload_2
    //   258: lxor
    //   259: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   264: sipush #32315
    //   267: ldc2_w 5108160948404874479
    //   270: lload_2
    //   271: lxor
    //   272: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   277: iconst_3
    //   278: anewarray java/lang/Object
    //   281: dup_x1
    //   282: swap
    //   283: iconst_2
    //   284: swap
    //   285: aastore
    //   286: dup_x1
    //   287: swap
    //   288: iconst_1
    //   289: swap
    //   290: aastore
    //   291: dup_x2
    //   292: dup_x2
    //   293: pop
    //   294: invokestatic valueOf : (J)Ljava/lang/Long;
    //   297: iconst_0
    //   298: swap
    //   299: aastore
    //   300: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   303: lload #13
    //   305: sipush #28866
    //   308: ldc2_w 3108114845261357575
    //   311: lload_2
    //   312: lxor
    //   313: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   318: sipush #611
    //   321: ldc2_w 7325408847167262865
    //   324: lload_2
    //   325: lxor
    //   326: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   331: iconst_3
    //   332: anewarray java/lang/Object
    //   335: dup_x1
    //   336: swap
    //   337: iconst_2
    //   338: swap
    //   339: aastore
    //   340: dup_x1
    //   341: swap
    //   342: iconst_1
    //   343: swap
    //   344: aastore
    //   345: dup_x2
    //   346: dup_x2
    //   347: pop
    //   348: invokestatic valueOf : (J)Ljava/lang/Long;
    //   351: iconst_0
    //   352: swap
    //   353: aastore
    //   354: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   357: lload #13
    //   359: sipush #9666
    //   362: ldc2_w 2651220579352557359
    //   365: lload_2
    //   366: lxor
    //   367: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   372: sipush #7882
    //   375: ldc2_w 2994003119187201071
    //   378: lload_2
    //   379: lxor
    //   380: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   385: iconst_2
    //   386: anewarray java/lang/Object
    //   389: dup
    //   390: iconst_0
    //   391: aload #4
    //   393: aastore
    //   394: dup
    //   395: iconst_1
    //   396: aload_1
    //   397: aastore
    //   398: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   401: iconst_3
    //   402: anewarray java/lang/Object
    //   405: dup_x1
    //   406: swap
    //   407: iconst_2
    //   408: swap
    //   409: aastore
    //   410: dup_x1
    //   411: swap
    //   412: iconst_1
    //   413: swap
    //   414: aastore
    //   415: dup_x2
    //   416: dup_x2
    //   417: pop
    //   418: invokestatic valueOf : (J)Ljava/lang/Long;
    //   421: iconst_0
    //   422: swap
    //   423: aastore
    //   424: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   427: lload #9
    //   429: iconst_3
    //   430: anewarray java/lang/Object
    //   433: dup_x2
    //   434: dup_x2
    //   435: pop
    //   436: invokestatic valueOf : (J)Ljava/lang/Long;
    //   439: iconst_2
    //   440: swap
    //   441: aastore
    //   442: dup_x1
    //   443: swap
    //   444: iconst_1
    //   445: swap
    //   446: aastore
    //   447: dup_x1
    //   448: swap
    //   449: iconst_0
    //   450: swap
    //   451: aastore
    //   452: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   455: lload #13
    //   457: sipush #23027
    //   460: ldc2_w 4354508412900887332
    //   463: lload_2
    //   464: lxor
    //   465: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   470: sipush #20175
    //   473: ldc2_w 3038808067390987327
    //   476: lload_2
    //   477: lxor
    //   478: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   483: iconst_3
    //   484: anewarray java/lang/Object
    //   487: dup_x1
    //   488: swap
    //   489: iconst_2
    //   490: swap
    //   491: aastore
    //   492: dup_x1
    //   493: swap
    //   494: iconst_1
    //   495: swap
    //   496: aastore
    //   497: dup_x2
    //   498: dup_x2
    //   499: pop
    //   500: invokestatic valueOf : (J)Ljava/lang/Long;
    //   503: iconst_0
    //   504: swap
    //   505: aastore
    //   506: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   509: lload #13
    //   511: sipush #11019
    //   514: ldc2_w 5206231911057471960
    //   517: lload_2
    //   518: lxor
    //   519: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   524: sipush #29485
    //   527: ldc2_w 3761490931512271339
    //   530: lload_2
    //   531: lxor
    //   532: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   537: iconst_3
    //   538: anewarray java/lang/Object
    //   541: dup_x1
    //   542: swap
    //   543: iconst_2
    //   544: swap
    //   545: aastore
    //   546: dup_x1
    //   547: swap
    //   548: iconst_1
    //   549: swap
    //   550: aastore
    //   551: dup_x2
    //   552: dup_x2
    //   553: pop
    //   554: invokestatic valueOf : (J)Ljava/lang/Long;
    //   557: iconst_0
    //   558: swap
    //   559: aastore
    //   560: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   563: invokevirtual toString : ()Ljava/lang/String;
    //   566: invokevirtual getBytes : ()[B
    //   569: invokevirtual write : ([B)V
    //   572: new java/io/InputStreamReader
    //   575: dup
    //   576: aload #16
    //   578: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   581: invokespecial <init> : (Ljava/io/InputStream;)V
    //   584: lload #7
    //   586: iconst_2
    //   587: anewarray java/lang/Object
    //   590: dup_x2
    //   591: dup_x2
    //   592: pop
    //   593: invokestatic valueOf : (J)Ljava/lang/Long;
    //   596: iconst_1
    //   597: swap
    //   598: aastore
    //   599: dup_x1
    //   600: swap
    //   601: iconst_0
    //   602: swap
    //   603: aastore
    //   604: invokestatic i : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   607: invokevirtual w : ()Lwtf/opal/kq;
    //   610: lload #5
    //   612: dup2_x1
    //   613: pop2
    //   614: iconst_2
    //   615: anewarray java/lang/Object
    //   618: dup_x1
    //   619: swap
    //   620: iconst_1
    //   621: swap
    //   622: aastore
    //   623: dup_x2
    //   624: dup_x2
    //   625: pop
    //   626: invokestatic valueOf : (J)Ljava/lang/Long;
    //   629: iconst_0
    //   630: swap
    //   631: aastore
    //   632: invokestatic m : ([Ljava/lang/Object;)Lwtf/opal/bl;
    //   635: invokestatic D : ()[Lwtf/opal/d;
    //   638: ifnull -> 655
    //   641: iconst_1
    //   642: anewarray java/lang/String
    //   645: invokestatic t : ([Ljava/lang/String;)V
    //   648: goto -> 655
    //   651: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   654: athrow
    //   655: areturn
    // Exception table:
    //   from	to	target	type
    //   133	648	651	java/io/IOException
  }
  
  public static bt u(Object[] paramArrayOfObject) throws IOException {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    bl bl = (bl)paramArrayOfObject[1];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x536B9FB05DE6L;
    new Object[2];
    return o(new Object[] { null, Long.valueOf(l2), bl.i(new Object[0]) });
  }
  
  public static bt o(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore_1
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_2
    //   18: pop
    //   19: getstatic wtf/opal/kb.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: lload_2
    //   26: dup2
    //   27: ldc2_w 2339406091972
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 49314145833256
    //   37: lxor
    //   38: lstore #6
    //   40: dup2
    //   41: ldc2_w 65008645254101
    //   44: lxor
    //   45: lstore #8
    //   47: dup2
    //   48: ldc2_w 131318810356789
    //   51: lxor
    //   52: lstore #10
    //   54: dup2
    //   55: ldc2_w 11482077496148
    //   58: lxor
    //   59: lstore #12
    //   61: dup2
    //   62: ldc2_w 54061541864014
    //   65: lxor
    //   66: lstore #14
    //   68: pop2
    //   69: new java/net/URL
    //   72: dup
    //   73: sipush #13840
    //   76: ldc2_w 2916551604701776941
    //   79: lload_2
    //   80: lxor
    //   81: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   86: invokespecial <init> : (Ljava/lang/String;)V
    //   89: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   92: checkcast javax/net/ssl/HttpsURLConnection
    //   95: astore #16
    //   97: aload #16
    //   99: iconst_1
    //   100: invokevirtual setDoOutput : (Z)V
    //   103: aload #16
    //   105: iconst_1
    //   106: invokevirtual setDoInput : (Z)V
    //   109: aload #16
    //   111: sipush #23380
    //   114: ldc2_w 1335604876059746666
    //   117: lload_2
    //   118: lxor
    //   119: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   124: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   127: aload #16
    //   129: sipush #28178
    //   132: ldc2_w 2025427976400704564
    //   135: lload_2
    //   136: lxor
    //   137: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   142: sipush #11401
    //   145: ldc2_w 3295504651724838556
    //   148: lload_2
    //   149: lxor
    //   150: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   155: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   158: aload #16
    //   160: sipush #24348
    //   163: ldc2_w 2492842242111612175
    //   166: lload_2
    //   167: lxor
    //   168: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   173: sipush #11401
    //   176: ldc2_w 3295504651724838556
    //   179: lload_2
    //   180: lxor
    //   181: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   186: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   189: aload #16
    //   191: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   194: lload #10
    //   196: iconst_1
    //   197: anewarray java/lang/Object
    //   200: dup_x2
    //   201: dup_x2
    //   202: pop
    //   203: invokestatic valueOf : (J)Ljava/lang/Long;
    //   206: iconst_0
    //   207: swap
    //   208: aastore
    //   209: invokestatic C : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   212: sipush #19812
    //   215: ldc2_w 999574070617061228
    //   218: lload_2
    //   219: lxor
    //   220: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   225: lload #10
    //   227: iconst_1
    //   228: anewarray java/lang/Object
    //   231: dup_x2
    //   232: dup_x2
    //   233: pop
    //   234: invokestatic valueOf : (J)Ljava/lang/Long;
    //   237: iconst_0
    //   238: swap
    //   239: aastore
    //   240: invokestatic C : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   243: lload #12
    //   245: sipush #11126
    //   248: ldc2_w 2829655372614193494
    //   251: lload_2
    //   252: lxor
    //   253: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   258: sipush #18063
    //   261: ldc2_w 3168065431204936890
    //   264: lload_2
    //   265: lxor
    //   266: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   271: iconst_3
    //   272: anewarray java/lang/Object
    //   275: dup_x1
    //   276: swap
    //   277: iconst_2
    //   278: swap
    //   279: aastore
    //   280: dup_x1
    //   281: swap
    //   282: iconst_1
    //   283: swap
    //   284: aastore
    //   285: dup_x2
    //   286: dup_x2
    //   287: pop
    //   288: invokestatic valueOf : (J)Ljava/lang/Long;
    //   291: iconst_0
    //   292: swap
    //   293: aastore
    //   294: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   297: sipush #4342
    //   300: ldc2_w 5317769928954571485
    //   303: lload_2
    //   304: lxor
    //   305: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   310: iconst_1
    //   311: anewarray java/lang/String
    //   314: dup
    //   315: iconst_0
    //   316: aload_1
    //   317: aastore
    //   318: lload #4
    //   320: dup2_x1
    //   321: pop2
    //   322: iconst_2
    //   323: anewarray java/lang/Object
    //   326: dup_x1
    //   327: swap
    //   328: iconst_1
    //   329: swap
    //   330: aastore
    //   331: dup_x2
    //   332: dup_x2
    //   333: pop
    //   334: invokestatic valueOf : (J)Ljava/lang/Long;
    //   337: iconst_0
    //   338: swap
    //   339: aastore
    //   340: invokestatic u : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   343: lload #8
    //   345: iconst_3
    //   346: anewarray java/lang/Object
    //   349: dup_x2
    //   350: dup_x2
    //   351: pop
    //   352: invokestatic valueOf : (J)Ljava/lang/Long;
    //   355: iconst_2
    //   356: swap
    //   357: aastore
    //   358: dup_x1
    //   359: swap
    //   360: iconst_1
    //   361: swap
    //   362: aastore
    //   363: dup_x1
    //   364: swap
    //   365: iconst_0
    //   366: swap
    //   367: aastore
    //   368: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   371: lload #8
    //   373: iconst_3
    //   374: anewarray java/lang/Object
    //   377: dup_x2
    //   378: dup_x2
    //   379: pop
    //   380: invokestatic valueOf : (J)Ljava/lang/Long;
    //   383: iconst_2
    //   384: swap
    //   385: aastore
    //   386: dup_x1
    //   387: swap
    //   388: iconst_1
    //   389: swap
    //   390: aastore
    //   391: dup_x1
    //   392: swap
    //   393: iconst_0
    //   394: swap
    //   395: aastore
    //   396: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   399: lload #12
    //   401: sipush #11454
    //   404: ldc2_w 4102979286280853140
    //   407: lload_2
    //   408: lxor
    //   409: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   414: sipush #22294
    //   417: ldc2_w 1622915757136063744
    //   420: lload_2
    //   421: lxor
    //   422: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   427: iconst_3
    //   428: anewarray java/lang/Object
    //   431: dup_x1
    //   432: swap
    //   433: iconst_2
    //   434: swap
    //   435: aastore
    //   436: dup_x1
    //   437: swap
    //   438: iconst_1
    //   439: swap
    //   440: aastore
    //   441: dup_x2
    //   442: dup_x2
    //   443: pop
    //   444: invokestatic valueOf : (J)Ljava/lang/Long;
    //   447: iconst_0
    //   448: swap
    //   449: aastore
    //   450: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   453: lload #12
    //   455: sipush #19594
    //   458: ldc2_w 5933029512513107607
    //   461: lload_2
    //   462: lxor
    //   463: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   468: sipush #2378
    //   471: ldc2_w 5984168866607952747
    //   474: lload_2
    //   475: lxor
    //   476: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   481: iconst_3
    //   482: anewarray java/lang/Object
    //   485: dup_x1
    //   486: swap
    //   487: iconst_2
    //   488: swap
    //   489: aastore
    //   490: dup_x1
    //   491: swap
    //   492: iconst_1
    //   493: swap
    //   494: aastore
    //   495: dup_x2
    //   496: dup_x2
    //   497: pop
    //   498: invokestatic valueOf : (J)Ljava/lang/Long;
    //   501: iconst_0
    //   502: swap
    //   503: aastore
    //   504: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   507: invokevirtual toString : ()Ljava/lang/String;
    //   510: invokevirtual getBytes : ()[B
    //   513: invokevirtual write : ([B)V
    //   516: new java/io/InputStreamReader
    //   519: dup
    //   520: aload #16
    //   522: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   525: invokespecial <init> : (Ljava/io/InputStream;)V
    //   528: lload #6
    //   530: iconst_2
    //   531: anewarray java/lang/Object
    //   534: dup_x2
    //   535: dup_x2
    //   536: pop
    //   537: invokestatic valueOf : (J)Ljava/lang/Long;
    //   540: iconst_1
    //   541: swap
    //   542: aastore
    //   543: dup_x1
    //   544: swap
    //   545: iconst_0
    //   546: swap
    //   547: aastore
    //   548: invokestatic i : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   551: invokevirtual w : ()Lwtf/opal/kq;
    //   554: lload #14
    //   556: iconst_2
    //   557: anewarray java/lang/Object
    //   560: dup_x2
    //   561: dup_x2
    //   562: pop
    //   563: invokestatic valueOf : (J)Ljava/lang/Long;
    //   566: iconst_1
    //   567: swap
    //   568: aastore
    //   569: dup_x1
    //   570: swap
    //   571: iconst_0
    //   572: swap
    //   573: aastore
    //   574: invokestatic b : ([Ljava/lang/Object;)Lwtf/opal/bt;
    //   577: areturn
  }
  
  public static dg l(Object[] paramArrayOfObject) throws IOException {
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
    //   14: checkcast wtf/opal/bt
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/kb.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 25921091163229
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 83725727590720
    //   37: lxor
    //   38: lstore #6
    //   40: dup2
    //   41: ldc2_w 54131627509281
    //   44: lxor
    //   45: lstore #8
    //   47: dup2
    //   48: ldc2_w 104882002374829
    //   51: lxor
    //   52: lstore #10
    //   54: pop2
    //   55: new java/net/URL
    //   58: dup
    //   59: sipush #19049
    //   62: ldc2_w 8878264880176198935
    //   65: lload_1
    //   66: lxor
    //   67: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   72: invokespecial <init> : (Ljava/lang/String;)V
    //   75: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   78: checkcast javax/net/ssl/HttpsURLConnection
    //   81: astore #12
    //   83: aload #12
    //   85: iconst_1
    //   86: invokevirtual setDoOutput : (Z)V
    //   89: aload #12
    //   91: iconst_1
    //   92: invokevirtual setDoInput : (Z)V
    //   95: aload #12
    //   97: sipush #23380
    //   100: ldc2_w 1335667776372171807
    //   103: lload_1
    //   104: lxor
    //   105: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   110: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   113: aload #12
    //   115: sipush #28178
    //   118: ldc2_w 2025493148759478593
    //   121: lload_1
    //   122: lxor
    //   123: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   128: sipush #11401
    //   131: ldc2_w 3295446698108621801
    //   134: lload_1
    //   135: lxor
    //   136: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   141: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   144: aload #12
    //   146: sipush #24348
    //   149: ldc2_w 2492849618202517626
    //   152: lload_1
    //   153: lxor
    //   154: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   159: sipush #11401
    //   162: ldc2_w 3295446698108621801
    //   165: lload_1
    //   166: lxor
    //   167: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   172: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   175: aload #12
    //   177: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   180: lload #6
    //   182: iconst_1
    //   183: anewarray java/lang/Object
    //   186: dup_x2
    //   187: dup_x2
    //   188: pop
    //   189: invokestatic valueOf : (J)Ljava/lang/Long;
    //   192: iconst_0
    //   193: swap
    //   194: aastore
    //   195: invokestatic C : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   198: lload #8
    //   200: sipush #17986
    //   203: ldc2_w 10179865585285408
    //   206: lload_1
    //   207: lxor
    //   208: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   213: sipush #7477
    //   216: ldc2_w 1806487714749948525
    //   219: lload_1
    //   220: lxor
    //   221: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   226: iconst_2
    //   227: anewarray java/lang/Object
    //   230: dup
    //   231: iconst_0
    //   232: aload_3
    //   233: iconst_0
    //   234: anewarray java/lang/Object
    //   237: invokevirtual M : ([Ljava/lang/Object;)Ljava/lang/String;
    //   240: aastore
    //   241: dup
    //   242: iconst_1
    //   243: aload_3
    //   244: iconst_0
    //   245: anewarray java/lang/Object
    //   248: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   251: aastore
    //   252: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   255: iconst_3
    //   256: anewarray java/lang/Object
    //   259: dup_x1
    //   260: swap
    //   261: iconst_2
    //   262: swap
    //   263: aastore
    //   264: dup_x1
    //   265: swap
    //   266: iconst_1
    //   267: swap
    //   268: aastore
    //   269: dup_x2
    //   270: dup_x2
    //   271: pop
    //   272: invokestatic valueOf : (J)Ljava/lang/Long;
    //   275: iconst_0
    //   276: swap
    //   277: aastore
    //   278: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   281: invokevirtual toString : ()Ljava/lang/String;
    //   284: invokevirtual getBytes : ()[B
    //   287: invokevirtual write : ([B)V
    //   290: new java/io/InputStreamReader
    //   293: dup
    //   294: aload #12
    //   296: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   299: invokespecial <init> : (Ljava/io/InputStream;)V
    //   302: lload #4
    //   304: iconst_2
    //   305: anewarray java/lang/Object
    //   308: dup_x2
    //   309: dup_x2
    //   310: pop
    //   311: invokestatic valueOf : (J)Ljava/lang/Long;
    //   314: iconst_1
    //   315: swap
    //   316: aastore
    //   317: dup_x1
    //   318: swap
    //   319: iconst_0
    //   320: swap
    //   321: aastore
    //   322: invokestatic i : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   325: invokevirtual w : ()Lwtf/opal/kq;
    //   328: lload #10
    //   330: iconst_2
    //   331: anewarray java/lang/Object
    //   334: dup_x2
    //   335: dup_x2
    //   336: pop
    //   337: invokestatic valueOf : (J)Ljava/lang/Long;
    //   340: iconst_1
    //   341: swap
    //   342: aastore
    //   343: dup_x1
    //   344: swap
    //   345: iconst_0
    //   346: swap
    //   347: aastore
    //   348: invokestatic k : ([Ljava/lang/Object;)Lwtf/opal/dg;
    //   351: areturn
  }
  
  public static boolean W(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast wtf/opal/dg
    //   17: astore_1
    //   18: pop
    //   19: getstatic wtf/opal/kb.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: lload_2
    //   26: dup2
    //   27: ldc2_w 51183320181259
    //   30: lxor
    //   31: lstore #4
    //   33: pop2
    //   34: lload #4
    //   36: aload_1
    //   37: iconst_2
    //   38: anewarray java/lang/Object
    //   41: dup_x1
    //   42: swap
    //   43: iconst_1
    //   44: swap
    //   45: aastore
    //   46: dup_x2
    //   47: dup_x2
    //   48: pop
    //   49: invokestatic valueOf : (J)Ljava/lang/Long;
    //   52: iconst_0
    //   53: swap
    //   54: aastore
    //   55: invokestatic T : ([Ljava/lang/Object;)Ljava/util/List;
    //   58: sipush #25456
    //   61: ldc2_w 314084557721144482
    //   64: lload_2
    //   65: lxor
    //   66: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   71: invokeinterface contains : (Ljava/lang/Object;)Z
    //   76: ireturn
  }
  
  public static boolean E(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore_2
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast java/lang/Integer
    //   24: invokevirtual intValue : ()I
    //   27: istore #4
    //   29: dup
    //   30: iconst_3
    //   31: aaload
    //   32: checkcast java/lang/Integer
    //   35: invokevirtual intValue : ()I
    //   38: istore_1
    //   39: pop
    //   40: iload_3
    //   41: i2l
    //   42: bipush #32
    //   44: lshl
    //   45: iload #4
    //   47: i2l
    //   48: bipush #48
    //   50: lshl
    //   51: bipush #32
    //   53: lushr
    //   54: lor
    //   55: iload_1
    //   56: i2l
    //   57: bipush #48
    //   59: lshl
    //   60: bipush #48
    //   62: lushr
    //   63: lor
    //   64: getstatic wtf/opal/kb.a : J
    //   67: lxor
    //   68: lstore #5
    //   70: lload #5
    //   72: dup2
    //   73: ldc2_w 3722302743125
    //   76: lxor
    //   77: lstore #7
    //   79: pop2
    //   80: lload #7
    //   82: aload_2
    //   83: iconst_2
    //   84: anewarray java/lang/Object
    //   87: dup_x1
    //   88: swap
    //   89: iconst_1
    //   90: swap
    //   91: aastore
    //   92: dup_x2
    //   93: dup_x2
    //   94: pop
    //   95: invokestatic valueOf : (J)Ljava/lang/Long;
    //   98: iconst_0
    //   99: swap
    //   100: aastore
    //   101: invokestatic x : ([Ljava/lang/Object;)Ljava/util/List;
    //   104: sipush #28260
    //   107: ldc2_w 5654181432238247663
    //   110: lload #5
    //   112: lxor
    //   113: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   118: invokeinterface contains : (Ljava/lang/Object;)Z
    //   123: ireturn
  }
  
  public static List T(Object[] paramArrayOfObject) throws IOException {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    dg dg = (dg)paramArrayOfObject[1];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x5E4950148D16L;
    (new Object[2])[1] = dg.c(new Object[0]);
    new Object[2];
    return x(new Object[] { Long.valueOf(l2) });
  }
  
  public static List x(Object[] paramArrayOfObject) throws IOException {
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
    //   14: checkcast java/lang/String
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/kb.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 29729735386052
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 113790652129415
    //   37: lxor
    //   38: lstore #6
    //   40: dup2
    //   41: ldc2_w 42437028667430
    //   44: lxor
    //   45: lstore #8
    //   47: pop2
    //   48: new java/net/URL
    //   51: dup
    //   52: sipush #1942
    //   55: ldc2_w 4276154802857725814
    //   58: lload_1
    //   59: lxor
    //   60: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   65: invokespecial <init> : (Ljava/lang/String;)V
    //   68: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   71: checkcast javax/net/ssl/HttpsURLConnection
    //   74: astore #10
    //   76: aload #10
    //   78: iconst_1
    //   79: invokevirtual setDoInput : (Z)V
    //   82: aload #10
    //   84: sipush #19224
    //   87: ldc2_w 7397006356638729157
    //   90: lload_1
    //   91: lxor
    //   92: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   97: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   100: aload #10
    //   102: sipush #18879
    //   105: ldc2_w 1168510854485803375
    //   108: lload_1
    //   109: lxor
    //   110: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   115: sipush #11154
    //   118: ldc2_w 9214125665190175578
    //   121: lload_1
    //   122: lxor
    //   123: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   128: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   131: aload #10
    //   133: sipush #2737
    //   136: ldc2_w 9194991707560821319
    //   139: lload_1
    //   140: lxor
    //   141: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   146: sipush #11401
    //   149: ldc2_w 3295451668572857456
    //   152: lload_1
    //   153: lxor
    //   154: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   159: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   162: aload #10
    //   164: sipush #7035
    //   167: ldc2_w 7820437440009360264
    //   170: lload_1
    //   171: lxor
    //   172: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   177: sipush #13813
    //   180: ldc2_w 3372545810554944822
    //   183: lload_1
    //   184: lxor
    //   185: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   190: iconst_1
    //   191: anewarray java/lang/Object
    //   194: dup
    //   195: iconst_0
    //   196: aload_3
    //   197: aastore
    //   198: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   201: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   204: new java/io/InputStreamReader
    //   207: dup
    //   208: aload #10
    //   210: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   213: invokespecial <init> : (Ljava/io/InputStream;)V
    //   216: lload #4
    //   218: iconst_2
    //   219: anewarray java/lang/Object
    //   222: dup_x2
    //   223: dup_x2
    //   224: pop
    //   225: invokestatic valueOf : (J)Ljava/lang/Long;
    //   228: iconst_1
    //   229: swap
    //   230: aastore
    //   231: dup_x1
    //   232: swap
    //   233: iconst_0
    //   234: swap
    //   235: aastore
    //   236: invokestatic i : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   239: invokevirtual w : ()Lwtf/opal/kq;
    //   242: lload #6
    //   244: sipush #11599
    //   247: ldc2_w 8771844672423978393
    //   250: lload_1
    //   251: lxor
    //   252: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   257: iconst_2
    //   258: anewarray java/lang/Object
    //   261: dup_x1
    //   262: swap
    //   263: iconst_1
    //   264: swap
    //   265: aastore
    //   266: dup_x2
    //   267: dup_x2
    //   268: pop
    //   269: invokestatic valueOf : (J)Ljava/lang/Long;
    //   272: iconst_0
    //   273: swap
    //   274: aastore
    //   275: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   278: lload #8
    //   280: iconst_1
    //   281: anewarray java/lang/Object
    //   284: dup_x2
    //   285: dup_x2
    //   286: pop
    //   287: invokestatic valueOf : (J)Ljava/lang/Long;
    //   290: iconst_0
    //   291: swap
    //   292: aastore
    //   293: invokevirtual D : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   296: iconst_0
    //   297: anewarray java/lang/Object
    //   300: invokevirtual q : ([Ljava/lang/Object;)Ljava/util/List;
    //   303: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   308: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   313: invokeinterface map : (Ljava/util/function/Function;)Ljava/util/stream/Stream;
    //   318: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   323: invokeinterface map : (Ljava/util/function/Function;)Ljava/util/stream/Stream;
    //   328: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   333: invokeinterface map : (Ljava/util/function/Function;)Ljava/util/stream/Stream;
    //   338: invokestatic toList : ()Ljava/util/stream/Collector;
    //   341: invokeinterface collect : (Ljava/util/stream/Collector;)Ljava/lang/Object;
    //   346: checkcast java/util/List
    //   349: areturn
  }
  
  public static ka C(Object[] paramArrayOfObject) throws IOException {
    dg dg = (dg)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x13AC8FFE477AL;
    (new Object[2])[1] = dg.c(new Object[0]);
    new Object[2];
    return u(new Object[] { Long.valueOf(l2) });
  }
  
  public static ka u(Object[] paramArrayOfObject) throws IOException {
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
    //   14: checkcast java/lang/String
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/kb.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 15209778853221
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 133746645178857
    //   37: lxor
    //   38: lstore #6
    //   40: pop2
    //   41: new java/net/URL
    //   44: dup
    //   45: sipush #16275
    //   48: ldc2_w 7363488465282916859
    //   51: lload_1
    //   52: lxor
    //   53: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   58: invokespecial <init> : (Ljava/lang/String;)V
    //   61: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   64: checkcast javax/net/ssl/HttpsURLConnection
    //   67: astore #8
    //   69: aload #8
    //   71: iconst_1
    //   72: invokevirtual setDoInput : (Z)V
    //   75: aload #8
    //   77: sipush #13898
    //   80: ldc2_w 1834495004992530441
    //   83: lload_1
    //   84: lxor
    //   85: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   90: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   93: aload #8
    //   95: sipush #28178
    //   98: ldc2_w 2025464314295660665
    //   101: lload_1
    //   102: lxor
    //   103: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   108: sipush #11401
    //   111: ldc2_w 3295470585976910545
    //   114: lload_1
    //   115: lxor
    //   116: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   121: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   124: aload #8
    //   126: sipush #24348
    //   129: ldc2_w 2492878472665661762
    //   132: lload_1
    //   133: lxor
    //   134: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   139: sipush #11401
    //   142: ldc2_w 3295470585976910545
    //   145: lload_1
    //   146: lxor
    //   147: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   152: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   155: aload #8
    //   157: sipush #24641
    //   160: ldc2_w 4761633907524746795
    //   163: lload_1
    //   164: lxor
    //   165: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   170: sipush #18185
    //   173: ldc2_w 2377680249635117424
    //   176: lload_1
    //   177: lxor
    //   178: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   183: iconst_1
    //   184: anewarray java/lang/Object
    //   187: dup
    //   188: iconst_0
    //   189: aload_3
    //   190: aastore
    //   191: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   194: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   197: new java/io/InputStreamReader
    //   200: dup
    //   201: aload #8
    //   203: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   206: invokespecial <init> : (Ljava/io/InputStream;)V
    //   209: lload #4
    //   211: iconst_2
    //   212: anewarray java/lang/Object
    //   215: dup_x2
    //   216: dup_x2
    //   217: pop
    //   218: invokestatic valueOf : (J)Ljava/lang/Long;
    //   221: iconst_1
    //   222: swap
    //   223: aastore
    //   224: dup_x1
    //   225: swap
    //   226: iconst_0
    //   227: swap
    //   228: aastore
    //   229: invokestatic i : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   232: invokevirtual w : ()Lwtf/opal/kq;
    //   235: lload #6
    //   237: dup2_x1
    //   238: pop2
    //   239: iconst_2
    //   240: anewarray java/lang/Object
    //   243: dup_x1
    //   244: swap
    //   245: iconst_1
    //   246: swap
    //   247: aastore
    //   248: dup_x2
    //   249: dup_x2
    //   250: pop
    //   251: invokestatic valueOf : (J)Ljava/lang/Long;
    //   254: iconst_0
    //   255: swap
    //   256: aastore
    //   257: invokestatic O : ([Ljava/lang/Object;)Lwtf/opal/ka;
    //   260: areturn
  }
  
  private static kv lambda$retrieveMinecraftStore$1(kq paramkq) {
    // Byte code:
    //   0: getstatic wtf/opal/kb.a : J
    //   3: ldc2_w 103448539379104
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 8890427014953
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: aload_0
    //   17: lload_3
    //   18: sipush #20322
    //   21: ldc2_w 1064109356642654255
    //   24: lload_1
    //   25: lxor
    //   26: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   31: iconst_2
    //   32: anewarray java/lang/Object
    //   35: dup_x1
    //   36: swap
    //   37: iconst_1
    //   38: swap
    //   39: aastore
    //   40: dup_x2
    //   41: dup_x2
    //   42: pop
    //   43: invokestatic valueOf : (J)Ljava/lang/Long;
    //   46: iconst_0
    //   47: swap
    //   48: aastore
    //   49: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   52: areturn
  }
  
  private static void lambda$encodeToForm$0(StringJoiner paramStringJoiner, String paramString1, String paramString2) {
    // Byte code:
    //   0: getstatic wtf/opal/kb.a : J
    //   3: ldc2_w 125002895756860
    //   6: lxor
    //   7: lstore_3
    //   8: aload_0
    //   9: bipush #13
    //   11: ldc2_w 3965804338228014287
    //   14: lload_3
    //   15: lxor
    //   16: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   21: iconst_2
    //   22: anewarray java/lang/Object
    //   25: dup
    //   26: iconst_0
    //   27: aload_1
    //   28: aastore
    //   29: dup
    //   30: iconst_1
    //   31: aload_2
    //   32: sipush #6673
    //   35: ldc2_w 5671358115113547487
    //   38: lload_3
    //   39: lxor
    //   40: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   45: invokestatic encode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   48: aastore
    //   49: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   52: invokevirtual add : (Ljava/lang/CharSequence;)Ljava/util/StringJoiner;
    //   55: pop
    //   56: goto -> 61
    //   59: astore #5
    //   61: return
    // Exception table:
    //   from	to	target	type
    //   8	56	59	java/io/UnsupportedEncodingException
  }
  
  static {
    long l = a ^ 0xBA3DD2B07E6L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[57];
    boolean bool = false;
    String str;
    int i = (str = "´Ü¦È¼\013©çhÐK\0033k\020¢â¿ Aà¼±T\035Ö áÜ\036s;/Ý¶!OZà\030C¹ï}\002ÂCF¶oyQù\020ñ\020³0Qu­\005î}È¦\007YP\030\030Ã©ÄíFµ\004\021¼ã\026à¹9ÚÁ¥\026)\020ºRL[¦oÐæ\034%íå¸ÇÉHêÿkÖ§\024\033Ý¶º¡í\016ñeÒ>LÆýÔ×\034#\f\007z&$'¿ðýa}\022á \034\007J\023tU\025ôá 2#râ¤Ö)×+]ÛüXBN\030W.(ø\024ã\n\036S°ßf\036ä\033õ>¸o}ñÞÇe\030¯¸nK1ß'Âì{×°c6VJêÑ^\020QHcbiõKh¼\004\035ßÿÍnê¬ð©¼»k5\"ô³dÿÖ³(ª*´t\034j \b=ã\016á@äe¤¡XÂFKFþ¨Fó\005ç¾+ZeR\0207m3¤äÈ°³o¤\f\rNÜ#PI»\fu\nDýG(Ø>ô\r\017Í¶[\033ÐÛÑê±¤}6]Õbáµ1·Q-E]â7o Ye\032Ü\026¢H¥ ø\000Z\r¦Ñ`£|Ñ\031áÉ¾±p%½eÏÊ ¾Î¶çQT{,Ç?\032ñB*¼ö>(ÿçÍ«G*UPÑh L)KÔa5ä²àOå¢I¥ §^\tÒ9yªHóÄ\020ÁZ;áä*þú\f¨_ËI\0053\030ÆÊföü\r²LÝ§w¬8Õ:D²e\025°¶ £XrËÁtÊ¼öð(J5.Ld3I-\005údñ4J³à»\030\\ß³bÞ%Tó;g¹`·ñc\\k9\rjm\030:ÈG³5\fn¸$o\032$\033¶Ç÷>¢ÍSd \023bw[iãÃ\000\n¬qÜ\025Ä\024èÄ$)0!J]JÜ2cã ×l°ï\000¬÷å\007\023:Æ\004ÁhcèdÅ@®ÿ\026éÇR¿Å0úBîa\031µnþx¹¯#·g\002$d{\036â\tx=9iÂ_v!3gé{zÿqh*¸ñ\030\b%srmDíSIî¼h\007OoþÜ#è\034>%(ÄÖ¯£,\006Mfm6öòðBÑ9èT^ßuL.äeé'pÛ0ÜÐí+êR\030y¬;çMC:\000«VÊs$1MðÌ¤-@p\022ÍvÉÚ»ö¯ç*öúiÁ!O\020kÆè\016~\000ÏÅµ¿ìÐÙ}goW3\b¶m6¿@Uü/ÁÏXÐ\\üA×öÏX\006»\020~*Wýµ{E¥f\035ºè\025« û@íSéEÅK@Id×üs\fä³jt4î\túÕm\b»ý e¹·\022]\017ò<ºða&Ò©kêh\033\035î\002NµÆ?_\030õ\030å\022ø&¿Ès êu5U|q=m\034ô\020ø$:XDö6O6\030þ¢ýÝímÞ²»\024NosÛõ!N~wÐ\002æ\027D2c@\0350Ð4\013}4°¤A{EÏ§Â¼¸³1>kl\023Ìør½4gm\t;&C±áÜ( #%¹·çÞ*Ò(ßÆ®7´¸\025S<¯J´'\035\030\003»\027\t*V%Ù<r§Y\026_\007cµÐÿÚ¤Ü ç\nù±­½\024¶\t\027inÔG\030Ï\037ÿß÷¦´¿7ÌÌL±\002\020Víbu4\0271\004Â'H&¬ñ \025Í1ór6cí$Ñ>Z]ºedùÃE§\023m¶q\034ºê¾\020ÿk\004ö<ØB\004Ït¶W\030\033hM \027\024¡\bÌæaÍWW¦î\\ñX\021M\b$ãK\027\034XTíap\034|ÇÖ»\033ë\037$+V*Õ\013\024øÕ®ëaÔt}ÚMòUCe\"}ÞÄ±\032ûz»\026N3cyS>ÊòQiXV ×³b£°0í&Àåg\002\n\fµ åH½d\024IMÇ¹Kôù\016]ûFh¾i]z²Ì-Ú¶\f\020Å¦¸\006MRqáF\0174\020HDµ³P*âd+~Ûuék GV±[+^¸Z²\006g\030ÞÐðCiÁ\037<Ú±\005ïaõ«3\020É\020ççÎO\nÊ\005`\026Rã*\020 \007º)\001u\013\013.4öxO \022`ä6Fö~àÜ(e}ÅÛ9\nî7M4ë¶Ú<Ô¸8Z$Ã\n¼×\025èÌöã²ã¢ëÁr%µõ8²¼Ò±Ór%*Þh8ã³Zd¸Ï\020Ì¤{£\r6ð\022(¢Wã1\013à°\n\n\\x\005ýÃ¯Ù\000ætY·Ö·I2ÕWþ|Øf&¥\"R\020#ì³ðö\037\017EþÙm#ñpÈ¬\f\0134§7&\035¯xæêV\f<²ñ\034\nª¨ÈeÙ\021A+·Ã»\033<2­c\nM«z-\tìïiÝÇ\007Ü]áÐ±^\020¬¼+½ÃV=ñ.Ü\020xÓ`Qìe=¶ñ\024¹Õ\000\006äD%\035#ôê\031kf*5ÃÇ´øôÙ¬â\020¼g/¨Û:ÏJ.lÜµÏ1\037 Nn®\"âZÖ®*Õ¬:,r*#Hy'¢£áy\0210ÇDkV \000A\"d\025®\rý\brÄ\024°/(ý«{ÛGòÿX9é(ËËr\020Çªî:v\025:ý@\rì]»¬/\020Ù*ø7y$/fðÿÓ/,b¶ \035 ³P\031ÂÌ;\024±T\023ìÒ\016²q·\024RUx\026H{R\"IüN").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static IOException a(IOException paramIOException) {
    return paramIOException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x68C2;
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
        throw new RuntimeException("wtf/opal/kb", exception);
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
    //   65: ldc_w 'wtf/opal/kb'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */