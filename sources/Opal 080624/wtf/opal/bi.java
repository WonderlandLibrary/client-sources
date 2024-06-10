package wtf.opal;

public final class bi {
  public static final byte F = 0;
  
  public static final byte O = 1;
  
  public static final byte a;
  
  public static final byte Z;
  
  public static final byte s;
  
  private static int l;
  
  public static void w(int paramInt) {
    l = paramInt;
  }
  
  public static int p() {
    return l;
  }
  
  public static int g() {
    int i = p();
    try {
      if (i == 0)
        return 17; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -3158272081750021067
    //   3: ldc2_w 8129781683529381835
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 129184663993524
    //   18: invokeinterface a : (J)J
    //   23: invokestatic p : ()I
    //   26: ifeq -> 34
    //   29: bipush #24
    //   31: invokestatic w : (I)V
    //   34: ldc2_w 94285553048887
    //   37: lxor
    //   38: lstore_1
    //   39: ldc 'DES/CBC/NoPadding'
    //   41: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   44: dup
    //   45: astore_3
    //   46: iconst_2
    //   47: ldc 'DES'
    //   49: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   52: bipush #8
    //   54: newarray byte
    //   56: dup
    //   57: iconst_0
    //   58: lload_1
    //   59: bipush #56
    //   61: lushr
    //   62: l2i
    //   63: i2b
    //   64: bastore
    //   65: iconst_1
    //   66: istore #4
    //   68: iload #4
    //   70: bipush #8
    //   72: if_icmpge -> 97
    //   75: dup
    //   76: iload #4
    //   78: lload_1
    //   79: iload #4
    //   81: bipush #8
    //   83: imul
    //   84: lshl
    //   85: bipush #56
    //   87: lushr
    //   88: l2i
    //   89: i2b
    //   90: bastore
    //   91: iinc #4, 1
    //   94: goto -> 68
    //   97: new javax/crypto/spec/DESKeySpec
    //   100: dup_x1
    //   101: swap
    //   102: invokespecial <init> : ([B)V
    //   105: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   108: new javax/crypto/spec/IvParameterSpec
    //   111: dup
    //   112: bipush #8
    //   114: newarray byte
    //   116: invokespecial <init> : ([B)V
    //   119: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   122: iconst_3
    //   123: newarray long
    //   125: astore_0
    //   126: iconst_0
    //   127: istore #6
    //   129: ldc 'FH`ÅNsâ½e;_îã5 -['
    //   131: dup
    //   132: astore #7
    //   134: invokevirtual length : ()I
    //   137: istore #8
    //   139: iconst_0
    //   140: istore #5
    //   142: aload #7
    //   144: iload #5
    //   146: iinc #5, 8
    //   149: iload #5
    //   151: invokevirtual substring : (II)Ljava/lang/String;
    //   154: ldc 'ISO-8859-1'
    //   156: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   159: astore #9
    //   161: aload_0
    //   162: iload #6
    //   164: iinc #6, 1
    //   167: aload #9
    //   169: iconst_0
    //   170: baload
    //   171: i2l
    //   172: ldc2_w 255
    //   175: land
    //   176: bipush #56
    //   178: lshl
    //   179: aload #9
    //   181: iconst_1
    //   182: baload
    //   183: i2l
    //   184: ldc2_w 255
    //   187: land
    //   188: bipush #48
    //   190: lshl
    //   191: lor
    //   192: aload #9
    //   194: iconst_2
    //   195: baload
    //   196: i2l
    //   197: ldc2_w 255
    //   200: land
    //   201: bipush #40
    //   203: lshl
    //   204: lor
    //   205: aload #9
    //   207: iconst_3
    //   208: baload
    //   209: i2l
    //   210: ldc2_w 255
    //   213: land
    //   214: bipush #32
    //   216: lshl
    //   217: lor
    //   218: aload #9
    //   220: iconst_4
    //   221: baload
    //   222: i2l
    //   223: ldc2_w 255
    //   226: land
    //   227: bipush #24
    //   229: lshl
    //   230: lor
    //   231: aload #9
    //   233: iconst_5
    //   234: baload
    //   235: i2l
    //   236: ldc2_w 255
    //   239: land
    //   240: bipush #16
    //   242: lshl
    //   243: lor
    //   244: aload #9
    //   246: bipush #6
    //   248: baload
    //   249: i2l
    //   250: ldc2_w 255
    //   253: land
    //   254: bipush #8
    //   256: lshl
    //   257: lor
    //   258: aload #9
    //   260: bipush #7
    //   262: baload
    //   263: i2l
    //   264: ldc2_w 255
    //   267: land
    //   268: lor
    //   269: iconst_m1
    //   270: goto -> 307
    //   273: lastore
    //   274: iload #5
    //   276: iload #8
    //   278: if_icmplt -> 142
    //   281: aload_0
    //   282: dup
    //   283: iconst_1
    //   284: laload
    //   285: l2i
    //   286: putstatic wtf/opal/bi.a : B
    //   289: dup
    //   290: iconst_2
    //   291: laload
    //   292: l2i
    //   293: putstatic wtf/opal/bi.Z : B
    //   296: dup
    //   297: iconst_0
    //   298: laload
    //   299: l2i
    //   300: putstatic wtf/opal/bi.s : B
    //   303: pop
    //   304: goto -> 509
    //   307: dup_x2
    //   308: pop
    //   309: lstore #10
    //   311: bipush #8
    //   313: newarray byte
    //   315: dup
    //   316: iconst_0
    //   317: lload #10
    //   319: bipush #56
    //   321: lushr
    //   322: l2i
    //   323: i2b
    //   324: bastore
    //   325: dup
    //   326: iconst_1
    //   327: lload #10
    //   329: bipush #48
    //   331: lushr
    //   332: l2i
    //   333: i2b
    //   334: bastore
    //   335: dup
    //   336: iconst_2
    //   337: lload #10
    //   339: bipush #40
    //   341: lushr
    //   342: l2i
    //   343: i2b
    //   344: bastore
    //   345: dup
    //   346: iconst_3
    //   347: lload #10
    //   349: bipush #32
    //   351: lushr
    //   352: l2i
    //   353: i2b
    //   354: bastore
    //   355: dup
    //   356: iconst_4
    //   357: lload #10
    //   359: bipush #24
    //   361: lushr
    //   362: l2i
    //   363: i2b
    //   364: bastore
    //   365: dup
    //   366: iconst_5
    //   367: lload #10
    //   369: bipush #16
    //   371: lushr
    //   372: l2i
    //   373: i2b
    //   374: bastore
    //   375: dup
    //   376: bipush #6
    //   378: lload #10
    //   380: bipush #8
    //   382: lushr
    //   383: l2i
    //   384: i2b
    //   385: bastore
    //   386: dup
    //   387: bipush #7
    //   389: lload #10
    //   391: l2i
    //   392: i2b
    //   393: bastore
    //   394: aload_3
    //   395: swap
    //   396: invokevirtual doFinal : ([B)[B
    //   399: astore #12
    //   401: aload #12
    //   403: iconst_0
    //   404: baload
    //   405: i2l
    //   406: ldc2_w 255
    //   409: land
    //   410: bipush #56
    //   412: lshl
    //   413: aload #12
    //   415: iconst_1
    //   416: baload
    //   417: i2l
    //   418: ldc2_w 255
    //   421: land
    //   422: bipush #48
    //   424: lshl
    //   425: lor
    //   426: aload #12
    //   428: iconst_2
    //   429: baload
    //   430: i2l
    //   431: ldc2_w 255
    //   434: land
    //   435: bipush #40
    //   437: lshl
    //   438: lor
    //   439: aload #12
    //   441: iconst_3
    //   442: baload
    //   443: i2l
    //   444: ldc2_w 255
    //   447: land
    //   448: bipush #32
    //   450: lshl
    //   451: lor
    //   452: aload #12
    //   454: iconst_4
    //   455: baload
    //   456: i2l
    //   457: ldc2_w 255
    //   460: land
    //   461: bipush #24
    //   463: lshl
    //   464: lor
    //   465: aload #12
    //   467: iconst_5
    //   468: baload
    //   469: i2l
    //   470: ldc2_w 255
    //   473: land
    //   474: bipush #16
    //   476: lshl
    //   477: lor
    //   478: aload #12
    //   480: bipush #6
    //   482: baload
    //   483: i2l
    //   484: ldc2_w 255
    //   487: land
    //   488: bipush #8
    //   490: lshl
    //   491: lor
    //   492: aload #12
    //   494: bipush #7
    //   496: baload
    //   497: i2l
    //   498: ldc2_w 255
    //   501: land
    //   502: lor
    //   503: dup2_x1
    //   504: pop2
    //   505: pop
    //   506: goto -> 273
    //   509: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */