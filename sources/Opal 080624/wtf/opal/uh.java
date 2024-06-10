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
import net.minecraft.class_276;
import net.minecraft.class_310;

public final class uh {
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  public static void j(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    class_276 class_276 = (class_276)paramArrayOfObject[1];
    l = a ^ l;
    boolean bool = lt.h();
    try {
      if (bool)
        if (class_276 != null) {
        
        } else {
          return;
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (l >= 0L)
        if (bool)
          try {
            if (class_276.method_30278() > -1) {
            
            } else {
              return;
            } 
          } catch (x5 x5) {
            throw a(null);
          }   
    } catch (x5 x5) {
      throw a(null);
    } 
    I(new Object[] { class_276 });
  }
  
  public static void I(Object[] paramArrayOfObject) {
    class_276 class_276 = (class_276)paramArrayOfObject[0];
    class_276.method_1231(b9.c.method_22683().method_4480(), b9.c.method_22683().method_4507(), class_310.field_1703);
  }
  
  public static void u(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/uh.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: lload_1
    //   19: dup2
    //   20: ldc2_w 127011498233871
    //   23: lxor
    //   24: lstore_3
    //   25: pop2
    //   26: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   29: invokevirtual method_1522 : ()Lnet/minecraft/class_276;
    //   32: iconst_0
    //   33: invokevirtual method_1235 : (Z)V
    //   36: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   39: invokevirtual method_1522 : ()Lnet/minecraft/class_276;
    //   42: lload_3
    //   43: dup2_x1
    //   44: pop2
    //   45: iconst_2
    //   46: anewarray java/lang/Object
    //   49: dup_x1
    //   50: swap
    //   51: iconst_1
    //   52: swap
    //   53: aastore
    //   54: dup_x2
    //   55: dup_x2
    //   56: pop
    //   57: invokestatic valueOf : (J)Ljava/lang/Long;
    //   60: iconst_0
    //   61: swap
    //   62: aastore
    //   63: invokestatic j : ([Ljava/lang/Object;)V
    //   66: sipush #11592
    //   69: ldc2_w 5623841326722818825
    //   72: lload_1
    //   73: lxor
    //   74: <illegal opcode> n : (IJ)I
    //   79: invokestatic glClear : (I)V
    //   82: invokestatic u : ()Z
    //   85: sipush #13754
    //   88: ldc2_w 6727620998957612026
    //   91: lload_1
    //   92: lxor
    //   93: <illegal opcode> n : (IJ)I
    //   98: invokestatic glEnable : (I)V
    //   101: sipush #16794
    //   104: ldc2_w 5949685567463411672
    //   107: lload_1
    //   108: lxor
    //   109: <illegal opcode> n : (IJ)I
    //   114: iconst_1
    //   115: iconst_1
    //   116: invokestatic glStencilFunc : (III)V
    //   119: sipush #6459
    //   122: ldc2_w 19594586762221430
    //   125: lload_1
    //   126: lxor
    //   127: <illegal opcode> n : (IJ)I
    //   132: sipush #5846
    //   135: ldc2_w 3189833511088494736
    //   138: lload_1
    //   139: lxor
    //   140: <illegal opcode> n : (IJ)I
    //   145: sipush #5846
    //   148: ldc2_w 3189833511088494736
    //   151: lload_1
    //   152: lxor
    //   153: <illegal opcode> n : (IJ)I
    //   158: invokestatic glStencilOp : (III)V
    //   161: iconst_0
    //   162: iconst_0
    //   163: iconst_0
    //   164: iconst_0
    //   165: invokestatic glColorMask : (ZZZZ)V
    //   168: istore #5
    //   170: iload #5
    //   172: ifeq -> 189
    //   175: iconst_5
    //   176: anewarray wtf/opal/d
    //   179: invokestatic p : ([Lwtf/opal/d;)V
    //   182: goto -> 189
    //   185: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   188: athrow
    //   189: return
    // Exception table:
    //   from	to	target	type
    //   170	182	185	wtf/opal/x5
  }
  
  public static void Y(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_3
    //   21: pop
    //   22: getstatic wtf/opal/uh.a : J
    //   25: lload_1
    //   26: lxor
    //   27: lstore_1
    //   28: iconst_1
    //   29: iconst_1
    //   30: iconst_1
    //   31: iconst_1
    //   32: invokestatic glColorMask : (ZZZZ)V
    //   35: sipush #29474
    //   38: ldc2_w 7278461534498725515
    //   41: lload_1
    //   42: lxor
    //   43: <illegal opcode> n : (IJ)I
    //   48: iload_3
    //   49: iconst_1
    //   50: invokestatic glStencilFunc : (III)V
    //   53: invokestatic h : ()Z
    //   56: sipush #18125
    //   59: ldc2_w 3523858064607193958
    //   62: lload_1
    //   63: lxor
    //   64: <illegal opcode> n : (IJ)I
    //   69: sipush #14594
    //   72: ldc2_w 7383291265288361133
    //   75: lload_1
    //   76: lxor
    //   77: <illegal opcode> n : (IJ)I
    //   82: sipush #14594
    //   85: ldc2_w 7383291265288361133
    //   88: lload_1
    //   89: lxor
    //   90: <illegal opcode> n : (IJ)I
    //   95: invokestatic glStencilOp : (III)V
    //   98: istore #4
    //   100: invokestatic D : ()[Lwtf/opal/d;
    //   103: ifnull -> 130
    //   106: iload #4
    //   108: ifeq -> 126
    //   111: goto -> 118
    //   114: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   117: athrow
    //   118: iconst_0
    //   119: goto -> 127
    //   122: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   125: athrow
    //   126: iconst_1
    //   127: invokestatic d : (Z)V
    //   130: return
    // Exception table:
    //   from	to	target	type
    //   100	111	114	wtf/opal/x5
    //   106	122	122	wtf/opal/x5
  }
  
  public static void J(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_3
    //   21: pop
    //   22: lload_1
    //   23: bipush #32
    //   25: lshl
    //   26: iload_3
    //   27: i2l
    //   28: bipush #32
    //   30: lshl
    //   31: bipush #32
    //   33: lushr
    //   34: lor
    //   35: getstatic wtf/opal/uh.a : J
    //   38: lxor
    //   39: lstore #4
    //   41: sipush #23658
    //   44: ldc2_w 1813785681821803169
    //   47: lload #4
    //   49: lxor
    //   50: <illegal opcode> n : (IJ)I
    //   55: invokestatic glDisable : (I)V
    //   58: return
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -9069726240348773950
    //   3: ldc2_w -3096056678572054892
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 184886705834769
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/uh.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/uh.d : Ljava/util/Map;
    //   38: getstatic wtf/opal/uh.a : J
    //   41: ldc2_w 7554958026678
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
    //   125: bipush #9
    //   127: newarray long
    //   129: astore #8
    //   131: iconst_0
    //   132: istore #5
    //   134: ldc '¼:Ó ÍµÊmjüàIé~q·4\\næãøc½ùqâ>Rûçò:\\fÁ,rj©O£ÔDÕq'
    //   136: dup
    //   137: astore #6
    //   139: invokevirtual length : ()I
    //   142: istore #7
    //   144: iconst_0
    //   145: istore #4
    //   147: aload #6
    //   149: iload #4
    //   151: iinc #4, 8
    //   154: iload #4
    //   156: invokevirtual substring : (II)Ljava/lang/String;
    //   159: ldc 'ISO-8859-1'
    //   161: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   164: astore #9
    //   166: aload #8
    //   168: iload #5
    //   170: iinc #5, 1
    //   173: aload #9
    //   175: iconst_0
    //   176: baload
    //   177: i2l
    //   178: ldc2_w 255
    //   181: land
    //   182: bipush #56
    //   184: lshl
    //   185: aload #9
    //   187: iconst_1
    //   188: baload
    //   189: i2l
    //   190: ldc2_w 255
    //   193: land
    //   194: bipush #48
    //   196: lshl
    //   197: lor
    //   198: aload #9
    //   200: iconst_2
    //   201: baload
    //   202: i2l
    //   203: ldc2_w 255
    //   206: land
    //   207: bipush #40
    //   209: lshl
    //   210: lor
    //   211: aload #9
    //   213: iconst_3
    //   214: baload
    //   215: i2l
    //   216: ldc2_w 255
    //   219: land
    //   220: bipush #32
    //   222: lshl
    //   223: lor
    //   224: aload #9
    //   226: iconst_4
    //   227: baload
    //   228: i2l
    //   229: ldc2_w 255
    //   232: land
    //   233: bipush #24
    //   235: lshl
    //   236: lor
    //   237: aload #9
    //   239: iconst_5
    //   240: baload
    //   241: i2l
    //   242: ldc2_w 255
    //   245: land
    //   246: bipush #16
    //   248: lshl
    //   249: lor
    //   250: aload #9
    //   252: bipush #6
    //   254: baload
    //   255: i2l
    //   256: ldc2_w 255
    //   259: land
    //   260: bipush #8
    //   262: lshl
    //   263: lor
    //   264: aload #9
    //   266: bipush #7
    //   268: baload
    //   269: i2l
    //   270: ldc2_w 255
    //   273: land
    //   274: lor
    //   275: iconst_m1
    //   276: goto -> 456
    //   279: lastore
    //   280: iload #4
    //   282: iload #7
    //   284: if_icmplt -> 147
    //   287: ldc '[<Uù]yã&-È2Ôþ^g'
    //   289: dup
    //   290: astore #6
    //   292: invokevirtual length : ()I
    //   295: istore #7
    //   297: iconst_0
    //   298: istore #4
    //   300: aload #6
    //   302: iload #4
    //   304: iinc #4, 8
    //   307: iload #4
    //   309: invokevirtual substring : (II)Ljava/lang/String;
    //   312: ldc 'ISO-8859-1'
    //   314: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   317: astore #9
    //   319: aload #8
    //   321: iload #5
    //   323: iinc #5, 1
    //   326: aload #9
    //   328: iconst_0
    //   329: baload
    //   330: i2l
    //   331: ldc2_w 255
    //   334: land
    //   335: bipush #56
    //   337: lshl
    //   338: aload #9
    //   340: iconst_1
    //   341: baload
    //   342: i2l
    //   343: ldc2_w 255
    //   346: land
    //   347: bipush #48
    //   349: lshl
    //   350: lor
    //   351: aload #9
    //   353: iconst_2
    //   354: baload
    //   355: i2l
    //   356: ldc2_w 255
    //   359: land
    //   360: bipush #40
    //   362: lshl
    //   363: lor
    //   364: aload #9
    //   366: iconst_3
    //   367: baload
    //   368: i2l
    //   369: ldc2_w 255
    //   372: land
    //   373: bipush #32
    //   375: lshl
    //   376: lor
    //   377: aload #9
    //   379: iconst_4
    //   380: baload
    //   381: i2l
    //   382: ldc2_w 255
    //   385: land
    //   386: bipush #24
    //   388: lshl
    //   389: lor
    //   390: aload #9
    //   392: iconst_5
    //   393: baload
    //   394: i2l
    //   395: ldc2_w 255
    //   398: land
    //   399: bipush #16
    //   401: lshl
    //   402: lor
    //   403: aload #9
    //   405: bipush #6
    //   407: baload
    //   408: i2l
    //   409: ldc2_w 255
    //   412: land
    //   413: bipush #8
    //   415: lshl
    //   416: lor
    //   417: aload #9
    //   419: bipush #7
    //   421: baload
    //   422: i2l
    //   423: ldc2_w 255
    //   426: land
    //   427: lor
    //   428: iconst_0
    //   429: goto -> 456
    //   432: lastore
    //   433: iload #4
    //   435: iload #7
    //   437: if_icmplt -> 300
    //   440: aload #8
    //   442: putstatic wtf/opal/uh.b : [J
    //   445: bipush #9
    //   447: anewarray java/lang/Integer
    //   450: putstatic wtf/opal/uh.c : [Ljava/lang/Integer;
    //   453: goto -> 672
    //   456: dup_x2
    //   457: pop
    //   458: lstore #10
    //   460: bipush #8
    //   462: newarray byte
    //   464: dup
    //   465: iconst_0
    //   466: lload #10
    //   468: bipush #56
    //   470: lushr
    //   471: l2i
    //   472: i2b
    //   473: bastore
    //   474: dup
    //   475: iconst_1
    //   476: lload #10
    //   478: bipush #48
    //   480: lushr
    //   481: l2i
    //   482: i2b
    //   483: bastore
    //   484: dup
    //   485: iconst_2
    //   486: lload #10
    //   488: bipush #40
    //   490: lushr
    //   491: l2i
    //   492: i2b
    //   493: bastore
    //   494: dup
    //   495: iconst_3
    //   496: lload #10
    //   498: bipush #32
    //   500: lushr
    //   501: l2i
    //   502: i2b
    //   503: bastore
    //   504: dup
    //   505: iconst_4
    //   506: lload #10
    //   508: bipush #24
    //   510: lushr
    //   511: l2i
    //   512: i2b
    //   513: bastore
    //   514: dup
    //   515: iconst_5
    //   516: lload #10
    //   518: bipush #16
    //   520: lushr
    //   521: l2i
    //   522: i2b
    //   523: bastore
    //   524: dup
    //   525: bipush #6
    //   527: lload #10
    //   529: bipush #8
    //   531: lushr
    //   532: l2i
    //   533: i2b
    //   534: bastore
    //   535: dup
    //   536: bipush #7
    //   538: lload #10
    //   540: l2i
    //   541: i2b
    //   542: bastore
    //   543: aload_2
    //   544: swap
    //   545: invokevirtual doFinal : ([B)[B
    //   548: astore #12
    //   550: aload #12
    //   552: iconst_0
    //   553: baload
    //   554: i2l
    //   555: ldc2_w 255
    //   558: land
    //   559: bipush #56
    //   561: lshl
    //   562: aload #12
    //   564: iconst_1
    //   565: baload
    //   566: i2l
    //   567: ldc2_w 255
    //   570: land
    //   571: bipush #48
    //   573: lshl
    //   574: lor
    //   575: aload #12
    //   577: iconst_2
    //   578: baload
    //   579: i2l
    //   580: ldc2_w 255
    //   583: land
    //   584: bipush #40
    //   586: lshl
    //   587: lor
    //   588: aload #12
    //   590: iconst_3
    //   591: baload
    //   592: i2l
    //   593: ldc2_w 255
    //   596: land
    //   597: bipush #32
    //   599: lshl
    //   600: lor
    //   601: aload #12
    //   603: iconst_4
    //   604: baload
    //   605: i2l
    //   606: ldc2_w 255
    //   609: land
    //   610: bipush #24
    //   612: lshl
    //   613: lor
    //   614: aload #12
    //   616: iconst_5
    //   617: baload
    //   618: i2l
    //   619: ldc2_w 255
    //   622: land
    //   623: bipush #16
    //   625: lshl
    //   626: lor
    //   627: aload #12
    //   629: bipush #6
    //   631: baload
    //   632: i2l
    //   633: ldc2_w 255
    //   636: land
    //   637: bipush #8
    //   639: lshl
    //   640: lor
    //   641: aload #12
    //   643: bipush #7
    //   645: baload
    //   646: i2l
    //   647: ldc2_w 255
    //   650: land
    //   651: lor
    //   652: dup2_x1
    //   653: pop2
    //   654: tableswitch default -> 279, 0 -> 432
    //   672: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x43DB;
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
        throw new RuntimeException("wtf/opal/uh", exception);
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
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/uh'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\uh.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */