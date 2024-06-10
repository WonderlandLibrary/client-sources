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
import org.lwjgl.opengl.GL32;

public final class kc {
  private static final int[] x;
  
  private static final int[] s;
  
  private static int h;
  
  private static int J;
  
  private static int a;
  
  private static int K;
  
  private static int u;
  
  private static int R;
  
  private static final Map<Integer, Boolean> W;
  
  private static final Map<Integer, Integer> I;
  
  private static int S;
  
  private static int N;
  
  private static int d;
  
  private static int Q;
  
  private static int X;
  
  private static int e;
  
  private static final int[] t;
  
  private static final int[] Z;
  
  private static final long b;
  
  private static final long[] c;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public static void S(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/kc.b : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: sipush #12115
    //   21: ldc2_w 3973345813896380051
    //   24: lload_1
    //   25: lxor
    //   26: <illegal opcode> m : (IJ)I
    //   31: invokestatic glGetInteger : (I)I
    //   34: putstatic wtf/opal/kc.h : I
    //   37: sipush #5763
    //   40: ldc2_w 2900493997183469330
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> m : (IJ)I
    //   50: invokestatic glGetInteger : (I)I
    //   53: putstatic wtf/opal/kc.J : I
    //   56: sipush #28679
    //   59: ldc2_w 1474061330139210213
    //   62: lload_1
    //   63: lxor
    //   64: <illegal opcode> m : (IJ)I
    //   69: invokestatic glGetInteger : (I)I
    //   72: putstatic wtf/opal/kc.a : I
    //   75: sipush #32351
    //   78: ldc2_w 6262685404331667401
    //   81: lload_1
    //   82: lxor
    //   83: <illegal opcode> m : (IJ)I
    //   88: invokestatic glGetInteger : (I)I
    //   91: putstatic wtf/opal/kc.K : I
    //   94: sipush #4896
    //   97: ldc2_w 3466167043514729213
    //   100: lload_1
    //   101: lxor
    //   102: <illegal opcode> m : (IJ)I
    //   107: invokestatic glGetInteger : (I)I
    //   110: putstatic wtf/opal/kc.R : I
    //   113: sipush #25422
    //   116: ldc2_w 6338740301966045839
    //   119: lload_1
    //   120: lxor
    //   121: <illegal opcode> m : (IJ)I
    //   126: invokestatic glGetInteger : (I)I
    //   129: putstatic wtf/opal/kc.u : I
    //   132: getstatic wtf/opal/kc.x : [I
    //   135: invokestatic stream : ([I)Ljava/util/stream/IntStream;
    //   138: <illegal opcode> accept : ()Ljava/util/function/IntConsumer;
    //   143: invokeinterface forEach : (Ljava/util/function/IntConsumer;)V
    //   148: getstatic wtf/opal/kc.s : [I
    //   151: invokestatic stream : ([I)Ljava/util/stream/IntStream;
    //   154: <illegal opcode> accept : ()Ljava/util/function/IntConsumer;
    //   159: invokeinterface forEach : (Ljava/util/function/IntConsumer;)V
    //   164: sipush #260
    //   167: ldc2_w 9143086890690711702
    //   170: lload_1
    //   171: lxor
    //   172: <illegal opcode> m : (IJ)I
    //   177: invokestatic glGetInteger : (I)I
    //   180: putstatic wtf/opal/kc.S : I
    //   183: sipush #6942
    //   186: ldc2_w 6170388039929039581
    //   189: lload_1
    //   190: lxor
    //   191: <illegal opcode> m : (IJ)I
    //   196: invokestatic glGetInteger : (I)I
    //   199: putstatic wtf/opal/kc.N : I
    //   202: sipush #10398
    //   205: ldc2_w 1269736876030769514
    //   208: lload_1
    //   209: lxor
    //   210: <illegal opcode> m : (IJ)I
    //   215: invokestatic glGetInteger : (I)I
    //   218: putstatic wtf/opal/kc.d : I
    //   221: sipush #22376
    //   224: ldc2_w 2540128168363476644
    //   227: lload_1
    //   228: lxor
    //   229: <illegal opcode> m : (IJ)I
    //   234: invokestatic glGetInteger : (I)I
    //   237: putstatic wtf/opal/kc.Q : I
    //   240: sipush #27287
    //   243: ldc2_w 4003520882639240062
    //   246: lload_1
    //   247: lxor
    //   248: <illegal opcode> m : (IJ)I
    //   253: invokestatic glGetInteger : (I)I
    //   256: putstatic wtf/opal/kc.X : I
    //   259: sipush #28659
    //   262: ldc2_w 8263600338448907821
    //   265: lload_1
    //   266: lxor
    //   267: <illegal opcode> m : (IJ)I
    //   272: invokestatic glGetInteger : (I)I
    //   275: putstatic wtf/opal/kc.e : I
    //   278: sipush #26710
    //   281: ldc2_w 1711050327853070780
    //   284: lload_1
    //   285: lxor
    //   286: <illegal opcode> m : (IJ)I
    //   291: getstatic wtf/opal/kc.t : [I
    //   294: invokestatic glGetIntegerv : (I[I)V
    //   297: sipush #7388
    //   300: ldc2_w 2319934339884256545
    //   303: lload_1
    //   304: lxor
    //   305: <illegal opcode> m : (IJ)I
    //   310: getstatic wtf/opal/kc.Z : [I
    //   313: invokestatic glGetIntegerv : (I[I)V
    //   316: return
  }
  
  public static void I(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/kc.b : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: invokestatic P : ()Z
    //   21: getstatic wtf/opal/kc.J : I
    //   24: invokestatic glUseProgram : (I)V
    //   27: istore_3
    //   28: sipush #7004
    //   31: ldc2_w 5568591727471325796
    //   34: lload_1
    //   35: lxor
    //   36: <illegal opcode> m : (IJ)I
    //   41: getstatic wtf/opal/kc.a : I
    //   44: invokestatic glBindTexture : (II)V
    //   47: iconst_0
    //   48: getstatic wtf/opal/kc.K : I
    //   51: invokestatic glBindSampler : (II)V
    //   54: getstatic wtf/opal/kc.h : I
    //   57: invokestatic glActiveTexture : (I)V
    //   60: getstatic wtf/opal/kc.u : I
    //   63: invokestatic glBindVertexArray : (I)V
    //   66: sipush #12512
    //   69: ldc2_w 8567014497749228028
    //   72: lload_1
    //   73: lxor
    //   74: <illegal opcode> m : (IJ)I
    //   79: getstatic wtf/opal/kc.R : I
    //   82: invokestatic glBindBuffer : (II)V
    //   85: getstatic wtf/opal/kc.X : I
    //   88: getstatic wtf/opal/kc.e : I
    //   91: invokestatic glBlendEquationSeparate : (II)V
    //   94: getstatic wtf/opal/kc.S : I
    //   97: getstatic wtf/opal/kc.N : I
    //   100: getstatic wtf/opal/kc.d : I
    //   103: getstatic wtf/opal/kc.Q : I
    //   106: invokestatic glBlendFuncSeparate : (IIII)V
    //   109: getstatic wtf/opal/kc.x : [I
    //   112: invokestatic stream : ([I)Ljava/util/stream/IntStream;
    //   115: <illegal opcode> accept : ()Ljava/util/function/IntConsumer;
    //   120: invokeinterface forEach : (Ljava/util/function/IntConsumer;)V
    //   125: getstatic wtf/opal/kc.s : [I
    //   128: invokestatic stream : ([I)Ljava/util/stream/IntStream;
    //   131: <illegal opcode> accept : ()Ljava/util/function/IntConsumer;
    //   136: invokeinterface forEach : (Ljava/util/function/IntConsumer;)V
    //   141: getstatic wtf/opal/kc.t : [I
    //   144: iconst_0
    //   145: iaload
    //   146: getstatic wtf/opal/kc.t : [I
    //   149: iconst_1
    //   150: iaload
    //   151: getstatic wtf/opal/kc.t : [I
    //   154: iconst_2
    //   155: iaload
    //   156: getstatic wtf/opal/kc.t : [I
    //   159: iconst_3
    //   160: iaload
    //   161: invokestatic glViewport : (IIII)V
    //   164: getstatic wtf/opal/kc.Z : [I
    //   167: iconst_0
    //   168: iaload
    //   169: getstatic wtf/opal/kc.Z : [I
    //   172: iconst_1
    //   173: iaload
    //   174: getstatic wtf/opal/kc.Z : [I
    //   177: iconst_2
    //   178: iaload
    //   179: getstatic wtf/opal/kc.Z : [I
    //   182: iconst_3
    //   183: iaload
    //   184: invokestatic glScissor : (IIII)V
    //   187: invokestatic D : ()[Lwtf/opal/d;
    //   190: ifnull -> 216
    //   193: iload_3
    //   194: ifeq -> 212
    //   197: goto -> 204
    //   200: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   203: athrow
    //   204: iconst_0
    //   205: goto -> 213
    //   208: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: iconst_1
    //   213: invokestatic v : (Z)V
    //   216: return
    // Exception table:
    //   from	to	target	type
    //   28	197	200	wtf/opal/x5
    //   193	208	208	wtf/opal/x5
  }
  
  private static void lambda$restore$3(int paramInt) {
    GL32.glPixelStorei(paramInt, ((Integer)I.get(Integer.valueOf(paramInt))).intValue());
  }
  
  private static void lambda$restore$2(int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/kc.b : J
    //   3: ldc2_w 4238228946674
    //   6: lxor
    //   7: lstore_1
    //   8: invokestatic P : ()Z
    //   11: istore_3
    //   12: getstatic wtf/opal/kc.W : Ljava/util/Map;
    //   15: iload_0
    //   16: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   19: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   24: checkcast java/lang/Boolean
    //   27: invokevirtual booleanValue : ()Z
    //   30: iload_3
    //   31: ifeq -> 67
    //   34: ifeq -> 59
    //   37: goto -> 44
    //   40: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   43: athrow
    //   44: iload_0
    //   45: invokestatic glEnable : (I)V
    //   48: iload_3
    //   49: ifne -> 70
    //   52: goto -> 59
    //   55: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: iload_0
    //   60: goto -> 67
    //   63: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   66: athrow
    //   67: invokestatic glDisable : (I)V
    //   70: return
    // Exception table:
    //   from	to	target	type
    //   12	37	40	wtf/opal/x5
    //   34	52	55	wtf/opal/x5
    //   44	60	63	wtf/opal/x5
  }
  
  private static void lambda$backup$1(int paramInt) {
    I.put(Integer.valueOf(paramInt), Integer.valueOf(GL32.glGetInteger(paramInt)));
  }
  
  private static void lambda$backup$0(int paramInt) {
    W.put(Integer.valueOf(paramInt), Boolean.valueOf(GL32.glIsEnabled(paramInt)));
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -5215607868817832707
    //   3: ldc2_w -6558640673982792089
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 44031842003189
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/kc.b : J
    //   26: getstatic wtf/opal/kc.b : J
    //   29: ldc2_w 83126580716765
    //   32: lxor
    //   33: lstore #11
    //   35: new java/util/HashMap
    //   38: dup
    //   39: bipush #13
    //   41: invokespecial <init> : (I)V
    //   44: putstatic wtf/opal/kc.g : Ljava/util/Map;
    //   47: ldc_w 'DES/CBC/NoPadding'
    //   50: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   53: dup
    //   54: astore_0
    //   55: iconst_2
    //   56: ldc_w 'DES'
    //   59: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   62: bipush #8
    //   64: newarray byte
    //   66: dup
    //   67: iconst_0
    //   68: lload #11
    //   70: bipush #56
    //   72: lushr
    //   73: l2i
    //   74: i2b
    //   75: bastore
    //   76: iconst_1
    //   77: istore_1
    //   78: iload_1
    //   79: bipush #8
    //   81: if_icmpge -> 105
    //   84: dup
    //   85: iload_1
    //   86: lload #11
    //   88: iload_1
    //   89: bipush #8
    //   91: imul
    //   92: lshl
    //   93: bipush #56
    //   95: lushr
    //   96: l2i
    //   97: i2b
    //   98: bastore
    //   99: iinc #1, 1
    //   102: goto -> 78
    //   105: new javax/crypto/spec/DESKeySpec
    //   108: dup_x1
    //   109: swap
    //   110: invokespecial <init> : ([B)V
    //   113: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   116: new javax/crypto/spec/IvParameterSpec
    //   119: dup
    //   120: bipush #8
    //   122: newarray byte
    //   124: invokespecial <init> : ([B)V
    //   127: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   130: bipush #80
    //   132: newarray long
    //   134: astore #6
    //   136: iconst_0
    //   137: istore_3
    //   138: ldc_w '>~Ð.²-ÊjM#ùYoÐ÷=ôK=î4îÀcüFñ9Ö@qòîBY¤z~Æí¡i|yÐÌ;ËÔ\\bÿjçÚgÎQ9Íÿnà\\nðxæ)*.×E>Å+èJ»ZÇþñ·kõÝNÜ'Ë®º4¬,ð?óù¬AÌÉHW8úmÑ¹¾ò0TÆøx3qÐèáÏùÒ<½¡ï\\nþGN\\tëñ¨¹·  ­Y+:?Õ«MqF£ÐÉd^_3Wü?PXûð!¼\\rìÍÆ1Q§;\XÙÍàwÕj@ÿ ò*a5Ñ&GZj86&rÀÑû÷Y5âJÈ-zuQõ¾³I;\\f\ó$#ÍR>Ô¯ø\\rà¾{¹Qy\\tTøeÈSºç[v¸·ÔÁþÊ ²ÛPÏuÊô8Éd¼ê¬ß-KtYÄiÉ¢Ûm[x×¿á]m_´§½eyv\g®eÈ\\rì½ :2ÐqÝçUÇ;m]7nsOÁóL;·]ö}QV²·JI%/ÓpkNÂ@Xb°Ø8ë3ï`$ØàF!ü=ërc³Æ£ç&{<ë.`>Õ,VÃÉOU7ý¤·ø(ùÉ×:0ÿ~á/op;L¶x¨¹B¸?¡¶näësµM´Ðe¨BÃS§`¹XN¶,jÂ|©¿enè¤õö÷\è'<xIûËRç^'ù£èýÊÍú\\bðÚÞh«ÀdLAoC(8·1bß³îR"ºägÏ¤}¨¾tEÁßå+\\n\\n'
    //   141: dup
    //   142: astore #4
    //   144: invokevirtual length : ()I
    //   147: istore #5
    //   149: iconst_0
    //   150: istore_2
    //   151: aload #4
    //   153: iload_2
    //   154: iinc #2, 8
    //   157: iload_2
    //   158: invokevirtual substring : (II)Ljava/lang/String;
    //   161: ldc_w 'ISO-8859-1'
    //   164: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   167: astore #7
    //   169: aload #6
    //   171: iload_3
    //   172: iinc #3, 1
    //   175: aload #7
    //   177: iconst_0
    //   178: baload
    //   179: i2l
    //   180: ldc2_w 255
    //   183: land
    //   184: bipush #56
    //   186: lshl
    //   187: aload #7
    //   189: iconst_1
    //   190: baload
    //   191: i2l
    //   192: ldc2_w 255
    //   195: land
    //   196: bipush #48
    //   198: lshl
    //   199: lor
    //   200: aload #7
    //   202: iconst_2
    //   203: baload
    //   204: i2l
    //   205: ldc2_w 255
    //   208: land
    //   209: bipush #40
    //   211: lshl
    //   212: lor
    //   213: aload #7
    //   215: iconst_3
    //   216: baload
    //   217: i2l
    //   218: ldc2_w 255
    //   221: land
    //   222: bipush #32
    //   224: lshl
    //   225: lor
    //   226: aload #7
    //   228: iconst_4
    //   229: baload
    //   230: i2l
    //   231: ldc2_w 255
    //   234: land
    //   235: bipush #24
    //   237: lshl
    //   238: lor
    //   239: aload #7
    //   241: iconst_5
    //   242: baload
    //   243: i2l
    //   244: ldc2_w 255
    //   247: land
    //   248: bipush #16
    //   250: lshl
    //   251: lor
    //   252: aload #7
    //   254: bipush #6
    //   256: baload
    //   257: i2l
    //   258: ldc2_w 255
    //   261: land
    //   262: bipush #8
    //   264: lshl
    //   265: lor
    //   266: aload #7
    //   268: bipush #7
    //   270: baload
    //   271: i2l
    //   272: ldc2_w 255
    //   275: land
    //   276: lor
    //   277: iconst_m1
    //   278: goto -> 454
    //   281: lastore
    //   282: iload_2
    //   283: iload #5
    //   285: if_icmplt -> 151
    //   288: ldc_w '³E,\\nFü\\rÏýø·'
    //   291: dup
    //   292: astore #4
    //   294: invokevirtual length : ()I
    //   297: istore #5
    //   299: iconst_0
    //   300: istore_2
    //   301: aload #4
    //   303: iload_2
    //   304: iinc #2, 8
    //   307: iload_2
    //   308: invokevirtual substring : (II)Ljava/lang/String;
    //   311: ldc_w 'ISO-8859-1'
    //   314: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   317: astore #7
    //   319: aload #6
    //   321: iload_3
    //   322: iinc #3, 1
    //   325: aload #7
    //   327: iconst_0
    //   328: baload
    //   329: i2l
    //   330: ldc2_w 255
    //   333: land
    //   334: bipush #56
    //   336: lshl
    //   337: aload #7
    //   339: iconst_1
    //   340: baload
    //   341: i2l
    //   342: ldc2_w 255
    //   345: land
    //   346: bipush #48
    //   348: lshl
    //   349: lor
    //   350: aload #7
    //   352: iconst_2
    //   353: baload
    //   354: i2l
    //   355: ldc2_w 255
    //   358: land
    //   359: bipush #40
    //   361: lshl
    //   362: lor
    //   363: aload #7
    //   365: iconst_3
    //   366: baload
    //   367: i2l
    //   368: ldc2_w 255
    //   371: land
    //   372: bipush #32
    //   374: lshl
    //   375: lor
    //   376: aload #7
    //   378: iconst_4
    //   379: baload
    //   380: i2l
    //   381: ldc2_w 255
    //   384: land
    //   385: bipush #24
    //   387: lshl
    //   388: lor
    //   389: aload #7
    //   391: iconst_5
    //   392: baload
    //   393: i2l
    //   394: ldc2_w 255
    //   397: land
    //   398: bipush #16
    //   400: lshl
    //   401: lor
    //   402: aload #7
    //   404: bipush #6
    //   406: baload
    //   407: i2l
    //   408: ldc2_w 255
    //   411: land
    //   412: bipush #8
    //   414: lshl
    //   415: lor
    //   416: aload #7
    //   418: bipush #7
    //   420: baload
    //   421: i2l
    //   422: ldc2_w 255
    //   425: land
    //   426: lor
    //   427: iconst_0
    //   428: goto -> 454
    //   431: lastore
    //   432: iload_2
    //   433: iload #5
    //   435: if_icmplt -> 301
    //   438: aload #6
    //   440: putstatic wtf/opal/kc.c : [J
    //   443: bipush #80
    //   445: anewarray java/lang/Integer
    //   448: putstatic wtf/opal/kc.f : [Ljava/lang/Integer;
    //   451: goto -> 672
    //   454: dup_x2
    //   455: pop
    //   456: lstore #8
    //   458: bipush #8
    //   460: newarray byte
    //   462: dup
    //   463: iconst_0
    //   464: lload #8
    //   466: bipush #56
    //   468: lushr
    //   469: l2i
    //   470: i2b
    //   471: bastore
    //   472: dup
    //   473: iconst_1
    //   474: lload #8
    //   476: bipush #48
    //   478: lushr
    //   479: l2i
    //   480: i2b
    //   481: bastore
    //   482: dup
    //   483: iconst_2
    //   484: lload #8
    //   486: bipush #40
    //   488: lushr
    //   489: l2i
    //   490: i2b
    //   491: bastore
    //   492: dup
    //   493: iconst_3
    //   494: lload #8
    //   496: bipush #32
    //   498: lushr
    //   499: l2i
    //   500: i2b
    //   501: bastore
    //   502: dup
    //   503: iconst_4
    //   504: lload #8
    //   506: bipush #24
    //   508: lushr
    //   509: l2i
    //   510: i2b
    //   511: bastore
    //   512: dup
    //   513: iconst_5
    //   514: lload #8
    //   516: bipush #16
    //   518: lushr
    //   519: l2i
    //   520: i2b
    //   521: bastore
    //   522: dup
    //   523: bipush #6
    //   525: lload #8
    //   527: bipush #8
    //   529: lushr
    //   530: l2i
    //   531: i2b
    //   532: bastore
    //   533: dup
    //   534: bipush #7
    //   536: lload #8
    //   538: l2i
    //   539: i2b
    //   540: bastore
    //   541: aload_0
    //   542: swap
    //   543: invokevirtual doFinal : ([B)[B
    //   546: astore #10
    //   548: aload #10
    //   550: iconst_0
    //   551: baload
    //   552: i2l
    //   553: ldc2_w 255
    //   556: land
    //   557: bipush #56
    //   559: lshl
    //   560: aload #10
    //   562: iconst_1
    //   563: baload
    //   564: i2l
    //   565: ldc2_w 255
    //   568: land
    //   569: bipush #48
    //   571: lshl
    //   572: lor
    //   573: aload #10
    //   575: iconst_2
    //   576: baload
    //   577: i2l
    //   578: ldc2_w 255
    //   581: land
    //   582: bipush #40
    //   584: lshl
    //   585: lor
    //   586: aload #10
    //   588: iconst_3
    //   589: baload
    //   590: i2l
    //   591: ldc2_w 255
    //   594: land
    //   595: bipush #32
    //   597: lshl
    //   598: lor
    //   599: aload #10
    //   601: iconst_4
    //   602: baload
    //   603: i2l
    //   604: ldc2_w 255
    //   607: land
    //   608: bipush #24
    //   610: lshl
    //   611: lor
    //   612: aload #10
    //   614: iconst_5
    //   615: baload
    //   616: i2l
    //   617: ldc2_w 255
    //   620: land
    //   621: bipush #16
    //   623: lshl
    //   624: lor
    //   625: aload #10
    //   627: bipush #6
    //   629: baload
    //   630: i2l
    //   631: ldc2_w 255
    //   634: land
    //   635: bipush #8
    //   637: lshl
    //   638: lor
    //   639: aload #10
    //   641: bipush #7
    //   643: baload
    //   644: i2l
    //   645: ldc2_w 255
    //   648: land
    //   649: lor
    //   650: dup2_x1
    //   651: pop2
    //   652: tableswitch default -> 281, 0 -> 431
    //   672: sipush #9338
    //   675: ldc2_w 8414047009597712759
    //   678: lload #11
    //   680: lxor
    //   681: <illegal opcode> m : (IJ)I
    //   686: newarray int
    //   688: dup
    //   689: iconst_0
    //   690: sipush #6034
    //   693: ldc2_w 8646676726524697228
    //   696: lload #11
    //   698: lxor
    //   699: <illegal opcode> m : (IJ)I
    //   704: iastore
    //   705: dup
    //   706: iconst_1
    //   707: sipush #13114
    //   710: ldc2_w 4902315067320467004
    //   713: lload #11
    //   715: lxor
    //   716: <illegal opcode> m : (IJ)I
    //   721: iastore
    //   722: dup
    //   723: iconst_2
    //   724: sipush #29130
    //   727: ldc2_w 4830284180790295719
    //   730: lload #11
    //   732: lxor
    //   733: <illegal opcode> m : (IJ)I
    //   738: iastore
    //   739: dup
    //   740: iconst_3
    //   741: sipush #30920
    //   744: ldc2_w 6558820907097077234
    //   747: lload #11
    //   749: lxor
    //   750: <illegal opcode> m : (IJ)I
    //   755: iastore
    //   756: dup
    //   757: iconst_4
    //   758: sipush #12426
    //   761: ldc2_w 3746874800558079396
    //   764: lload #11
    //   766: lxor
    //   767: <illegal opcode> m : (IJ)I
    //   772: iastore
    //   773: dup
    //   774: iconst_5
    //   775: sipush #182
    //   778: ldc2_w 3752490697604124119
    //   781: lload #11
    //   783: lxor
    //   784: <illegal opcode> m : (IJ)I
    //   789: iastore
    //   790: dup
    //   791: sipush #6989
    //   794: ldc2_w 2131401820488586791
    //   797: lload #11
    //   799: lxor
    //   800: <illegal opcode> m : (IJ)I
    //   805: sipush #5253
    //   808: ldc2_w 3503486218880211364
    //   811: lload #11
    //   813: lxor
    //   814: <illegal opcode> m : (IJ)I
    //   819: iastore
    //   820: dup
    //   821: sipush #22822
    //   824: ldc2_w 2331317361878012989
    //   827: lload #11
    //   829: lxor
    //   830: <illegal opcode> m : (IJ)I
    //   835: sipush #23150
    //   838: ldc2_w 8846011957284869973
    //   841: lload #11
    //   843: lxor
    //   844: <illegal opcode> m : (IJ)I
    //   849: iastore
    //   850: dup
    //   851: sipush #14367
    //   854: ldc2_w 994901875103593789
    //   857: lload #11
    //   859: lxor
    //   860: <illegal opcode> m : (IJ)I
    //   865: sipush #11988
    //   868: ldc2_w 4064131014966371281
    //   871: lload #11
    //   873: lxor
    //   874: <illegal opcode> m : (IJ)I
    //   879: iastore
    //   880: dup
    //   881: sipush #9011
    //   884: ldc2_w 3208679488062942813
    //   887: lload #11
    //   889: lxor
    //   890: <illegal opcode> m : (IJ)I
    //   895: sipush #31515
    //   898: ldc2_w 7639158890063771152
    //   901: lload #11
    //   903: lxor
    //   904: <illegal opcode> m : (IJ)I
    //   909: iastore
    //   910: dup
    //   911: sipush #6070
    //   914: ldc2_w 3228135962095213246
    //   917: lload #11
    //   919: lxor
    //   920: <illegal opcode> m : (IJ)I
    //   925: sipush #27890
    //   928: ldc2_w 4050492953426797028
    //   931: lload #11
    //   933: lxor
    //   934: <illegal opcode> m : (IJ)I
    //   939: iastore
    //   940: dup
    //   941: sipush #17852
    //   944: ldc2_w 6210068460257511613
    //   947: lload #11
    //   949: lxor
    //   950: <illegal opcode> m : (IJ)I
    //   955: sipush #13590
    //   958: ldc2_w 7942411902243076100
    //   961: lload #11
    //   963: lxor
    //   964: <illegal opcode> m : (IJ)I
    //   969: iastore
    //   970: dup
    //   971: sipush #7218
    //   974: ldc2_w 4908232135434752283
    //   977: lload #11
    //   979: lxor
    //   980: <illegal opcode> m : (IJ)I
    //   985: sipush #11588
    //   988: ldc2_w 4797804839052730462
    //   991: lload #11
    //   993: lxor
    //   994: <illegal opcode> m : (IJ)I
    //   999: iastore
    //   1000: dup
    //   1001: sipush #11081
    //   1004: ldc2_w 3653607623319184985
    //   1007: lload #11
    //   1009: lxor
    //   1010: <illegal opcode> m : (IJ)I
    //   1015: sipush #18508
    //   1018: ldc2_w 1678400384935003434
    //   1021: lload #11
    //   1023: lxor
    //   1024: <illegal opcode> m : (IJ)I
    //   1029: iastore
    //   1030: dup
    //   1031: sipush #30699
    //   1034: ldc2_w 1751845448903441036
    //   1037: lload #11
    //   1039: lxor
    //   1040: <illegal opcode> m : (IJ)I
    //   1045: sipush #12632
    //   1048: ldc2_w 1100630271855295553
    //   1051: lload #11
    //   1053: lxor
    //   1054: <illegal opcode> m : (IJ)I
    //   1059: iastore
    //   1060: dup
    //   1061: sipush #25702
    //   1064: ldc2_w 1600573751616776523
    //   1067: lload #11
    //   1069: lxor
    //   1070: <illegal opcode> m : (IJ)I
    //   1075: sipush #2739
    //   1078: ldc2_w 8789078093593855915
    //   1081: lload #11
    //   1083: lxor
    //   1084: <illegal opcode> m : (IJ)I
    //   1089: iastore
    //   1090: dup
    //   1091: sipush #12763
    //   1094: ldc2_w 8137331017837870320
    //   1097: lload #11
    //   1099: lxor
    //   1100: <illegal opcode> m : (IJ)I
    //   1105: sipush #7933
    //   1108: ldc2_w 5174863132394968046
    //   1111: lload #11
    //   1113: lxor
    //   1114: <illegal opcode> m : (IJ)I
    //   1119: iastore
    //   1120: dup
    //   1121: sipush #24287
    //   1124: ldc2_w 7663196455064376307
    //   1127: lload #11
    //   1129: lxor
    //   1130: <illegal opcode> m : (IJ)I
    //   1135: sipush #12456
    //   1138: ldc2_w 4009528400110779812
    //   1141: lload #11
    //   1143: lxor
    //   1144: <illegal opcode> m : (IJ)I
    //   1149: iastore
    //   1150: dup
    //   1151: sipush #21393
    //   1154: ldc2_w 5881917509193113249
    //   1157: lload #11
    //   1159: lxor
    //   1160: <illegal opcode> m : (IJ)I
    //   1165: sipush #9466
    //   1168: ldc2_w 1624968342901909983
    //   1171: lload #11
    //   1173: lxor
    //   1174: <illegal opcode> m : (IJ)I
    //   1179: iastore
    //   1180: dup
    //   1181: sipush #19564
    //   1184: ldc2_w 4887144150686925147
    //   1187: lload #11
    //   1189: lxor
    //   1190: <illegal opcode> m : (IJ)I
    //   1195: sipush #26661
    //   1198: ldc2_w 6537131728119637274
    //   1201: lload #11
    //   1203: lxor
    //   1204: <illegal opcode> m : (IJ)I
    //   1209: iastore
    //   1210: dup
    //   1211: sipush #25087
    //   1214: ldc2_w 7711635693205236981
    //   1217: lload #11
    //   1219: lxor
    //   1220: <illegal opcode> m : (IJ)I
    //   1225: sipush #1279
    //   1228: ldc2_w 5930838064650373592
    //   1231: lload #11
    //   1233: lxor
    //   1234: <illegal opcode> m : (IJ)I
    //   1239: iastore
    //   1240: putstatic wtf/opal/kc.x : [I
    //   1243: sipush #27790
    //   1246: ldc2_w 7329201880831074747
    //   1249: lload #11
    //   1251: lxor
    //   1252: <illegal opcode> m : (IJ)I
    //   1257: newarray int
    //   1259: dup
    //   1260: iconst_0
    //   1261: sipush #6271
    //   1264: ldc2_w 2630855276128556317
    //   1267: lload #11
    //   1269: lxor
    //   1270: <illegal opcode> m : (IJ)I
    //   1275: iastore
    //   1276: dup
    //   1277: iconst_1
    //   1278: sipush #23539
    //   1281: ldc2_w 9019120843756148443
    //   1284: lload #11
    //   1286: lxor
    //   1287: <illegal opcode> m : (IJ)I
    //   1292: iastore
    //   1293: dup
    //   1294: iconst_2
    //   1295: sipush #24822
    //   1298: ldc2_w 2452816316723506642
    //   1301: lload #11
    //   1303: lxor
    //   1304: <illegal opcode> m : (IJ)I
    //   1309: iastore
    //   1310: dup
    //   1311: iconst_3
    //   1312: sipush #1384
    //   1315: ldc2_w 2995593106538612838
    //   1318: lload #11
    //   1320: lxor
    //   1321: <illegal opcode> m : (IJ)I
    //   1326: iastore
    //   1327: dup
    //   1328: iconst_4
    //   1329: sipush #29549
    //   1332: ldc2_w 8988011461057920617
    //   1335: lload #11
    //   1337: lxor
    //   1338: <illegal opcode> m : (IJ)I
    //   1343: iastore
    //   1344: dup
    //   1345: iconst_5
    //   1346: sipush #3956
    //   1349: ldc2_w 680071301438227041
    //   1352: lload #11
    //   1354: lxor
    //   1355: <illegal opcode> m : (IJ)I
    //   1360: iastore
    //   1361: dup
    //   1362: sipush #16844
    //   1365: ldc2_w 2221164614227430650
    //   1368: lload #11
    //   1370: lxor
    //   1371: <illegal opcode> m : (IJ)I
    //   1376: sipush #13063
    //   1379: ldc2_w 7907722188065439284
    //   1382: lload #11
    //   1384: lxor
    //   1385: <illegal opcode> m : (IJ)I
    //   1390: iastore
    //   1391: dup
    //   1392: sipush #889
    //   1395: ldc2_w 7655579865507319373
    //   1398: lload #11
    //   1400: lxor
    //   1401: <illegal opcode> m : (IJ)I
    //   1406: sipush #20941
    //   1409: ldc2_w 5782005481684180162
    //   1412: lload #11
    //   1414: lxor
    //   1415: <illegal opcode> m : (IJ)I
    //   1420: iastore
    //   1421: dup
    //   1422: sipush #30552
    //   1425: ldc2_w 6893723637587196522
    //   1428: lload #11
    //   1430: lxor
    //   1431: <illegal opcode> m : (IJ)I
    //   1436: sipush #23800
    //   1439: ldc2_w 3318973222732698013
    //   1442: lload #11
    //   1444: lxor
    //   1445: <illegal opcode> m : (IJ)I
    //   1450: iastore
    //   1451: dup
    //   1452: sipush #29578
    //   1455: ldc2_w 7597085190645628581
    //   1458: lload #11
    //   1460: lxor
    //   1461: <illegal opcode> m : (IJ)I
    //   1466: sipush #6601
    //   1469: ldc2_w 3581599102319088813
    //   1472: lload #11
    //   1474: lxor
    //   1475: <illegal opcode> m : (IJ)I
    //   1480: iastore
    //   1481: dup
    //   1482: sipush #1380
    //   1485: ldc2_w 6651427785749776462
    //   1488: lload #11
    //   1490: lxor
    //   1491: <illegal opcode> m : (IJ)I
    //   1496: sipush #31180
    //   1499: ldc2_w 6726066788069356752
    //   1502: lload #11
    //   1504: lxor
    //   1505: <illegal opcode> m : (IJ)I
    //   1510: iastore
    //   1511: dup
    //   1512: sipush #25941
    //   1515: ldc2_w 4911417500823136338
    //   1518: lload #11
    //   1520: lxor
    //   1521: <illegal opcode> m : (IJ)I
    //   1526: sipush #13400
    //   1529: ldc2_w 486441827898423611
    //   1532: lload #11
    //   1534: lxor
    //   1535: <illegal opcode> m : (IJ)I
    //   1540: iastore
    //   1541: dup
    //   1542: sipush #12903
    //   1545: ldc2_w 821237964892962575
    //   1548: lload #11
    //   1550: lxor
    //   1551: <illegal opcode> m : (IJ)I
    //   1556: sipush #24611
    //   1559: ldc2_w 7594524742368308512
    //   1562: lload #11
    //   1564: lxor
    //   1565: <illegal opcode> m : (IJ)I
    //   1570: iastore
    //   1571: dup
    //   1572: sipush #2898
    //   1575: ldc2_w 5162370906727269947
    //   1578: lload #11
    //   1580: lxor
    //   1581: <illegal opcode> m : (IJ)I
    //   1586: sipush #22467
    //   1589: ldc2_w 2806348697670752978
    //   1592: lload #11
    //   1594: lxor
    //   1595: <illegal opcode> m : (IJ)I
    //   1600: iastore
    //   1601: dup
    //   1602: sipush #29583
    //   1605: ldc2_w 8151035816285683346
    //   1608: lload #11
    //   1610: lxor
    //   1611: <illegal opcode> m : (IJ)I
    //   1616: sipush #28550
    //   1619: ldc2_w 7421101798941729510
    //   1622: lload #11
    //   1624: lxor
    //   1625: <illegal opcode> m : (IJ)I
    //   1630: iastore
    //   1631: dup
    //   1632: sipush #15341
    //   1635: ldc2_w 8093966058317444821
    //   1638: lload #11
    //   1640: lxor
    //   1641: <illegal opcode> m : (IJ)I
    //   1646: sipush #28479
    //   1649: ldc2_w 3621647651523207686
    //   1652: lload #11
    //   1654: lxor
    //   1655: <illegal opcode> m : (IJ)I
    //   1660: iastore
    //   1661: putstatic wtf/opal/kc.s : [I
    //   1664: iconst_0
    //   1665: putstatic wtf/opal/kc.h : I
    //   1668: iconst_0
    //   1669: putstatic wtf/opal/kc.J : I
    //   1672: iconst_0
    //   1673: putstatic wtf/opal/kc.a : I
    //   1676: iconst_0
    //   1677: putstatic wtf/opal/kc.K : I
    //   1680: iconst_0
    //   1681: putstatic wtf/opal/kc.u : I
    //   1684: iconst_0
    //   1685: putstatic wtf/opal/kc.R : I
    //   1688: new java/util/HashMap
    //   1691: dup
    //   1692: invokespecial <init> : ()V
    //   1695: putstatic wtf/opal/kc.W : Ljava/util/Map;
    //   1698: new java/util/HashMap
    //   1701: dup
    //   1702: invokespecial <init> : ()V
    //   1705: putstatic wtf/opal/kc.I : Ljava/util/Map;
    //   1708: iconst_0
    //   1709: putstatic wtf/opal/kc.S : I
    //   1712: iconst_0
    //   1713: putstatic wtf/opal/kc.N : I
    //   1716: iconst_0
    //   1717: putstatic wtf/opal/kc.d : I
    //   1720: iconst_0
    //   1721: putstatic wtf/opal/kc.Q : I
    //   1724: iconst_0
    //   1725: putstatic wtf/opal/kc.X : I
    //   1728: iconst_0
    //   1729: putstatic wtf/opal/kc.e : I
    //   1732: iconst_4
    //   1733: newarray int
    //   1735: putstatic wtf/opal/kc.t : [I
    //   1738: iconst_4
    //   1739: newarray int
    //   1741: putstatic wtf/opal/kc.Z : [I
    //   1744: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x53B4;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = c[i];
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
        throw new RuntimeException("wtf/opal/kc", exception);
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
    //   65: ldc_w 'wtf/opal/kc'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */