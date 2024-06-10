package wtf.opal;

import java.io.InputStream;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.nio.ByteBuffer;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class u2 {
  private final pa i;
  
  private final long R;
  
  private final String Z;
  
  private final ByteBuffer y;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  public u2(long paramLong, String paramString, InputStream paramInputStream) {
    // Byte code:
    //   0: getstatic wtf/opal/u2.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 116479688718784
    //   11: lxor
    //   12: lstore #5
    //   14: pop2
    //   15: aload_0
    //   16: invokespecial <init> : ()V
    //   19: aload_0
    //   20: iconst_0
    //   21: anewarray java/lang/Object
    //   24: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   27: iconst_0
    //   28: anewarray java/lang/Object
    //   31: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   34: putfield i : Lwtf/opal/pa;
    //   37: aload_0
    //   38: iconst_0
    //   39: anewarray java/lang/Object
    //   42: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   45: iconst_0
    //   46: anewarray java/lang/Object
    //   49: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   52: iconst_0
    //   53: anewarray java/lang/Object
    //   56: invokevirtual y : ([Ljava/lang/Object;)J
    //   59: putfield R : J
    //   62: aload_0
    //   63: aload_3
    //   64: putfield Z : Ljava/lang/String;
    //   67: aload_0
    //   68: aload #4
    //   70: lload #5
    //   72: sipush #11665
    //   75: ldc2_w 8074298289876010173
    //   78: lload_1
    //   79: lxor
    //   80: <illegal opcode> o : (IJ)I
    //   85: iconst_3
    //   86: anewarray java/lang/Object
    //   89: dup_x1
    //   90: swap
    //   91: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   94: iconst_2
    //   95: swap
    //   96: aastore
    //   97: dup_x2
    //   98: dup_x2
    //   99: pop
    //   100: invokestatic valueOf : (J)Ljava/lang/Long;
    //   103: iconst_1
    //   104: swap
    //   105: aastore
    //   106: dup_x1
    //   107: swap
    //   108: iconst_0
    //   109: swap
    //   110: aastore
    //   111: invokestatic v : ([Ljava/lang/Object;)Ljava/nio/ByteBuffer;
    //   114: putfield y : Ljava/nio/ByteBuffer;
    //   117: aload_0
    //   118: getfield R : J
    //   121: aload_0
    //   122: getfield Z : Ljava/lang/String;
    //   125: aload_0
    //   126: getfield y : Ljava/nio/ByteBuffer;
    //   129: iconst_0
    //   130: invokestatic nvgCreateFontMem : (JLjava/lang/CharSequence;Ljava/nio/ByteBuffer;Z)I
    //   133: pop
    //   134: return
  }
  
  public void C(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Float
    //   14: invokevirtual floatValue : ()F
    //   17: fstore_3
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast java/lang/Float
    //   24: invokevirtual floatValue : ()F
    //   27: fstore #5
    //   29: dup
    //   30: iconst_3
    //   31: aaload
    //   32: checkcast java/lang/Long
    //   35: invokevirtual longValue : ()J
    //   38: lstore #7
    //   40: dup
    //   41: iconst_4
    //   42: aaload
    //   43: checkcast java/lang/Float
    //   46: invokevirtual floatValue : ()F
    //   49: fstore #4
    //   51: dup
    //   52: iconst_5
    //   53: aaload
    //   54: checkcast java/lang/Integer
    //   57: invokevirtual intValue : ()I
    //   60: istore #6
    //   62: pop
    //   63: getstatic wtf/opal/u2.a : J
    //   66: lload #7
    //   68: lxor
    //   69: lstore #7
    //   71: lload #7
    //   73: dup2
    //   74: ldc2_w 43605677506352
    //   77: lxor
    //   78: lstore #9
    //   80: pop2
    //   81: aload_0
    //   82: aload_2
    //   83: fload_3
    //   84: fload #5
    //   86: fload #4
    //   88: iload #6
    //   90: lload #9
    //   92: iconst_0
    //   93: iconst_0
    //   94: sipush #14231
    //   97: ldc2_w 2705158579378537938
    //   100: lload #7
    //   102: lxor
    //   103: <illegal opcode> o : (IJ)I
    //   108: bipush #9
    //   110: anewarray java/lang/Object
    //   113: dup_x1
    //   114: swap
    //   115: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   118: bipush #8
    //   120: swap
    //   121: aastore
    //   122: dup_x1
    //   123: swap
    //   124: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   127: bipush #7
    //   129: swap
    //   130: aastore
    //   131: dup_x1
    //   132: swap
    //   133: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   136: bipush #6
    //   138: swap
    //   139: aastore
    //   140: dup_x2
    //   141: dup_x2
    //   142: pop
    //   143: invokestatic valueOf : (J)Ljava/lang/Long;
    //   146: iconst_5
    //   147: swap
    //   148: aastore
    //   149: dup_x1
    //   150: swap
    //   151: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   154: iconst_4
    //   155: swap
    //   156: aastore
    //   157: dup_x1
    //   158: swap
    //   159: invokestatic valueOf : (F)Ljava/lang/Float;
    //   162: iconst_3
    //   163: swap
    //   164: aastore
    //   165: dup_x1
    //   166: swap
    //   167: invokestatic valueOf : (F)Ljava/lang/Float;
    //   170: iconst_2
    //   171: swap
    //   172: aastore
    //   173: dup_x1
    //   174: swap
    //   175: invokestatic valueOf : (F)Ljava/lang/Float;
    //   178: iconst_1
    //   179: swap
    //   180: aastore
    //   181: dup_x1
    //   182: swap
    //   183: iconst_0
    //   184: swap
    //   185: aastore
    //   186: invokevirtual w : ([Ljava/lang/Object;)V
    //   189: return
  }
  
  public void b(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore #8
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Float
    //   15: invokevirtual floatValue : ()F
    //   18: fstore #6
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/Float
    //   26: invokevirtual floatValue : ()F
    //   29: fstore #9
    //   31: dup
    //   32: iconst_3
    //   33: aaload
    //   34: checkcast java/lang/Float
    //   37: invokevirtual floatValue : ()F
    //   40: fstore #5
    //   42: dup
    //   43: iconst_4
    //   44: aaload
    //   45: checkcast java/lang/Integer
    //   48: invokevirtual intValue : ()I
    //   51: istore #4
    //   53: dup
    //   54: iconst_5
    //   55: aaload
    //   56: checkcast java/lang/Boolean
    //   59: invokevirtual booleanValue : ()Z
    //   62: istore #7
    //   64: dup
    //   65: bipush #6
    //   67: aaload
    //   68: checkcast java/lang/Long
    //   71: invokevirtual longValue : ()J
    //   74: lstore_2
    //   75: pop
    //   76: getstatic wtf/opal/u2.a : J
    //   79: lload_2
    //   80: lxor
    //   81: lstore_2
    //   82: lload_2
    //   83: dup2
    //   84: ldc2_w 80561760742047
    //   87: lxor
    //   88: lstore #10
    //   90: pop2
    //   91: aload_0
    //   92: aload #8
    //   94: fload #6
    //   96: fload #9
    //   98: fload #5
    //   100: iload #4
    //   102: lload #10
    //   104: iload #7
    //   106: iconst_0
    //   107: sipush #14231
    //   110: ldc2_w 2705196115283546237
    //   113: lload_2
    //   114: lxor
    //   115: <illegal opcode> o : (IJ)I
    //   120: bipush #9
    //   122: anewarray java/lang/Object
    //   125: dup_x1
    //   126: swap
    //   127: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   130: bipush #8
    //   132: swap
    //   133: aastore
    //   134: dup_x1
    //   135: swap
    //   136: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   139: bipush #7
    //   141: swap
    //   142: aastore
    //   143: dup_x1
    //   144: swap
    //   145: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   148: bipush #6
    //   150: swap
    //   151: aastore
    //   152: dup_x2
    //   153: dup_x2
    //   154: pop
    //   155: invokestatic valueOf : (J)Ljava/lang/Long;
    //   158: iconst_5
    //   159: swap
    //   160: aastore
    //   161: dup_x1
    //   162: swap
    //   163: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   166: iconst_4
    //   167: swap
    //   168: aastore
    //   169: dup_x1
    //   170: swap
    //   171: invokestatic valueOf : (F)Ljava/lang/Float;
    //   174: iconst_3
    //   175: swap
    //   176: aastore
    //   177: dup_x1
    //   178: swap
    //   179: invokestatic valueOf : (F)Ljava/lang/Float;
    //   182: iconst_2
    //   183: swap
    //   184: aastore
    //   185: dup_x1
    //   186: swap
    //   187: invokestatic valueOf : (F)Ljava/lang/Float;
    //   190: iconst_1
    //   191: swap
    //   192: aastore
    //   193: dup_x1
    //   194: swap
    //   195: iconst_0
    //   196: swap
    //   197: aastore
    //   198: invokevirtual w : ([Ljava/lang/Object;)V
    //   201: return
  }
  
  public void e(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    float f2 = ((Float)paramArrayOfObject[1]).floatValue();
    float f1 = ((Float)paramArrayOfObject[2]).floatValue();
    float f3 = ((Float)paramArrayOfObject[3]).floatValue();
    int j = ((Integer)paramArrayOfObject[4]).intValue();
    long l1 = ((Long)paramArrayOfObject[5]).longValue();
    boolean bool = ((Boolean)paramArrayOfObject[6]).booleanValue();
    int i = ((Integer)paramArrayOfObject[7]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x636AD1F94FCCL;
    (new Object[9])[8] = Integer.valueOf(i);
    (new Object[9])[7] = Integer.valueOf(0);
    (new Object[9])[6] = Boolean.valueOf(bool);
    new Object[9];
    w(new Object[] { 
          null, null, null, null, null, Long.valueOf(l2), Integer.valueOf(j), Float.valueOf(f3), Float.valueOf(f1), Float.valueOf(f2), 
          str });
  }
  
  public void w(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore #11
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Float
    //   15: invokevirtual floatValue : ()F
    //   18: fstore #9
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/Float
    //   26: invokevirtual floatValue : ()F
    //   29: fstore #4
    //   31: dup
    //   32: iconst_3
    //   33: aaload
    //   34: checkcast java/lang/Float
    //   37: invokevirtual floatValue : ()F
    //   40: fstore_2
    //   41: dup
    //   42: iconst_4
    //   43: aaload
    //   44: checkcast java/lang/Integer
    //   47: invokevirtual intValue : ()I
    //   50: istore #7
    //   52: dup
    //   53: iconst_5
    //   54: aaload
    //   55: checkcast java/lang/Long
    //   58: invokevirtual longValue : ()J
    //   61: lstore #5
    //   63: dup
    //   64: bipush #6
    //   66: aaload
    //   67: checkcast java/lang/Boolean
    //   70: invokevirtual booleanValue : ()Z
    //   73: istore_3
    //   74: dup
    //   75: bipush #7
    //   77: aaload
    //   78: checkcast java/lang/Integer
    //   81: invokevirtual intValue : ()I
    //   84: istore #8
    //   86: dup
    //   87: bipush #8
    //   89: aaload
    //   90: checkcast java/lang/Integer
    //   93: invokevirtual intValue : ()I
    //   96: istore #10
    //   98: pop
    //   99: getstatic wtf/opal/u2.a : J
    //   102: lload #5
    //   104: lxor
    //   105: lstore #5
    //   107: lload #5
    //   109: dup2
    //   110: ldc2_w 69781343000045
    //   113: lxor
    //   114: lstore #12
    //   116: dup2
    //   117: ldc2_w 68281571420258
    //   120: lxor
    //   121: lstore #14
    //   123: pop2
    //   124: invokestatic n : ()[Lwtf/opal/d;
    //   127: iconst_0
    //   128: anewarray java/lang/Object
    //   131: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   134: iconst_0
    //   135: anewarray java/lang/Object
    //   138: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   141: ldc wtf/opal/ju
    //   143: iconst_1
    //   144: anewarray java/lang/Object
    //   147: dup_x1
    //   148: swap
    //   149: iconst_0
    //   150: swap
    //   151: aastore
    //   152: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   155: checkcast wtf/opal/ju
    //   158: astore #17
    //   160: astore #16
    //   162: aload #17
    //   164: iconst_0
    //   165: anewarray java/lang/Object
    //   168: invokevirtual D : ([Ljava/lang/Object;)Z
    //   171: aload #16
    //   173: ifnull -> 245
    //   176: ifeq -> 215
    //   179: goto -> 186
    //   182: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   185: athrow
    //   186: aload #17
    //   188: lload #12
    //   190: aload #11
    //   192: iconst_2
    //   193: anewarray java/lang/Object
    //   196: dup_x1
    //   197: swap
    //   198: iconst_1
    //   199: swap
    //   200: aastore
    //   201: dup_x2
    //   202: dup_x2
    //   203: pop
    //   204: invokestatic valueOf : (J)Ljava/lang/Long;
    //   207: iconst_0
    //   208: swap
    //   209: aastore
    //   210: invokevirtual S : ([Ljava/lang/Object;)Ljava/lang/String;
    //   213: astore #11
    //   215: aload_0
    //   216: getfield R : J
    //   219: aload_0
    //   220: getfield Z : Ljava/lang/String;
    //   223: invokestatic nvgFontFace : (JLjava/lang/CharSequence;)V
    //   226: aload_0
    //   227: getfield R : J
    //   230: fload_2
    //   231: invokestatic nvgFontSize : (JF)V
    //   234: aload_0
    //   235: getfield R : J
    //   238: iload #10
    //   240: invokestatic nvgTextAlign : (JI)V
    //   243: iload #8
    //   245: lload #5
    //   247: lconst_0
    //   248: lcmp
    //   249: ifle -> 460
    //   252: aload #16
    //   254: ifnull -> 460
    //   257: ifle -> 459
    //   260: goto -> 267
    //   263: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   266: athrow
    //   267: iload #7
    //   269: sipush #20539
    //   272: ldc2_w 2936938998172349000
    //   275: lload #5
    //   277: lxor
    //   278: <illegal opcode> o : (IJ)I
    //   283: ishr
    //   284: sipush #2311
    //   287: ldc2_w 3167435395892751229
    //   290: lload #5
    //   292: lxor
    //   293: <illegal opcode> o : (IJ)I
    //   298: iand
    //   299: istore #18
    //   301: iload #18
    //   303: sipush #29882
    //   306: ldc2_w 4642059263811606218
    //   309: lload #5
    //   311: lxor
    //   312: <illegal opcode> o : (IJ)I
    //   317: ishl
    //   318: iload #7
    //   320: sipush #11765
    //   323: ldc2_w 8635907629086314368
    //   326: lload #5
    //   328: lxor
    //   329: <illegal opcode> o : (IJ)I
    //   334: iand
    //   335: ior
    //   336: istore #19
    //   338: iconst_0
    //   339: istore #20
    //   341: iload #20
    //   343: iconst_2
    //   344: if_icmpgt -> 451
    //   347: iload #20
    //   349: iload #8
    //   351: imul
    //   352: i2f
    //   353: fstore #21
    //   355: aload_0
    //   356: getfield R : J
    //   359: fload #21
    //   361: invokestatic nvgFontBlur : (JF)V
    //   364: aload_0
    //   365: getfield R : J
    //   368: aload_0
    //   369: getfield i : Lwtf/opal/pa;
    //   372: iload #19
    //   374: lload #14
    //   376: iconst_2
    //   377: anewarray java/lang/Object
    //   380: dup_x2
    //   381: dup_x2
    //   382: pop
    //   383: invokestatic valueOf : (J)Ljava/lang/Long;
    //   386: iconst_1
    //   387: swap
    //   388: aastore
    //   389: dup_x1
    //   390: swap
    //   391: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   394: iconst_0
    //   395: swap
    //   396: aastore
    //   397: invokevirtual Y : ([Ljava/lang/Object;)Lorg/lwjgl/nanovg/NVGColor;
    //   400: invokestatic nvgFillColor : (JLorg/lwjgl/nanovg/NVGColor;)V
    //   403: aload_0
    //   404: getfield R : J
    //   407: fload #9
    //   409: fload #4
    //   411: aload #11
    //   413: invokestatic nvgText : (JFFLjava/lang/CharSequence;)F
    //   416: pop
    //   417: iinc #20, 1
    //   420: aload #16
    //   422: lload #5
    //   424: lconst_0
    //   425: lcmp
    //   426: iflt -> 434
    //   429: ifnull -> 459
    //   432: aload #16
    //   434: ifnonnull -> 341
    //   437: lload #5
    //   439: lconst_0
    //   440: lcmp
    //   441: ifle -> 420
    //   444: goto -> 451
    //   447: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   450: athrow
    //   451: aload_0
    //   452: getfield R : J
    //   455: fconst_0
    //   456: invokestatic nvgFontBlur : (JF)V
    //   459: iload_3
    //   460: ifeq -> 564
    //   463: aload_0
    //   464: getfield R : J
    //   467: aload_0
    //   468: getfield i : Lwtf/opal/pa;
    //   471: iload #7
    //   473: sipush #30571
    //   476: ldc2_w 6428975533104780570
    //   479: lload #5
    //   481: lxor
    //   482: <illegal opcode> o : (IJ)I
    //   487: iand
    //   488: iconst_2
    //   489: ishr
    //   490: iload #7
    //   492: sipush #4135
    //   495: ldc2_w 2528875154804748883
    //   498: lload #5
    //   500: lxor
    //   501: <illegal opcode> o : (IJ)I
    //   506: iand
    //   507: ior
    //   508: lload #14
    //   510: iconst_2
    //   511: anewarray java/lang/Object
    //   514: dup_x2
    //   515: dup_x2
    //   516: pop
    //   517: invokestatic valueOf : (J)Ljava/lang/Long;
    //   520: iconst_1
    //   521: swap
    //   522: aastore
    //   523: dup_x1
    //   524: swap
    //   525: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   528: iconst_0
    //   529: swap
    //   530: aastore
    //   531: invokevirtual Y : ([Ljava/lang/Object;)Lorg/lwjgl/nanovg/NVGColor;
    //   534: invokestatic nvgFillColor : (JLorg/lwjgl/nanovg/NVGColor;)V
    //   537: aload_0
    //   538: getfield R : J
    //   541: fload #9
    //   543: ldc 0.5
    //   545: fadd
    //   546: fload #4
    //   548: ldc 0.5
    //   550: fadd
    //   551: aload #11
    //   553: invokestatic nvgText : (JFFLjava/lang/CharSequence;)F
    //   556: pop
    //   557: goto -> 564
    //   560: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   563: athrow
    //   564: aload_0
    //   565: getfield R : J
    //   568: aload_0
    //   569: getfield i : Lwtf/opal/pa;
    //   572: iload #7
    //   574: lload #14
    //   576: iconst_2
    //   577: anewarray java/lang/Object
    //   580: dup_x2
    //   581: dup_x2
    //   582: pop
    //   583: invokestatic valueOf : (J)Ljava/lang/Long;
    //   586: iconst_1
    //   587: swap
    //   588: aastore
    //   589: dup_x1
    //   590: swap
    //   591: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   594: iconst_0
    //   595: swap
    //   596: aastore
    //   597: invokevirtual Y : ([Ljava/lang/Object;)Lorg/lwjgl/nanovg/NVGColor;
    //   600: invokestatic nvgFillColor : (JLorg/lwjgl/nanovg/NVGColor;)V
    //   603: aload_0
    //   604: getfield R : J
    //   607: fload #9
    //   609: fload #4
    //   611: aload #11
    //   613: invokestatic nvgText : (JFFLjava/lang/CharSequence;)F
    //   616: pop
    //   617: invokestatic D : ()[Lwtf/opal/d;
    //   620: lload #5
    //   622: lconst_0
    //   623: lcmp
    //   624: ifle -> 634
    //   627: ifnull -> 644
    //   630: iconst_1
    //   631: anewarray wtf/opal/d
    //   634: invokestatic Y : ([Lwtf/opal/d;)V
    //   637: goto -> 644
    //   640: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   643: athrow
    //   644: return
    // Exception table:
    //   from	to	target	type
    //   162	179	182	wtf/opal/x5
    //   245	260	263	wtf/opal/x5
    //   355	437	447	wtf/opal/x5
    //   460	557	560	wtf/opal/x5
    //   564	637	640	wtf/opal/x5
  }
  
  public float M(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore #6
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_3
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Float
    //   25: invokevirtual floatValue : ()F
    //   28: fstore #5
    //   30: dup
    //   31: iconst_3
    //   32: aaload
    //   33: checkcast java/lang/Integer
    //   36: invokevirtual intValue : ()I
    //   39: istore_2
    //   40: pop
    //   41: getstatic wtf/opal/u2.a : J
    //   44: lload_3
    //   45: lxor
    //   46: lstore_3
    //   47: aload_0
    //   48: getfield R : J
    //   51: aload_0
    //   52: getfield Z : Ljava/lang/String;
    //   55: invokestatic nvgFontFace : (JLjava/lang/CharSequence;)V
    //   58: invokestatic n : ()[Lwtf/opal/d;
    //   61: aload_0
    //   62: getfield R : J
    //   65: fload #5
    //   67: invokestatic nvgFontSize : (JF)V
    //   70: astore #7
    //   72: aload_0
    //   73: getfield R : J
    //   76: iload_2
    //   77: invokestatic nvgTextAlign : (JI)V
    //   80: invokestatic stackPush : ()Lorg/lwjgl/system/MemoryStack;
    //   83: astore #8
    //   85: aload #8
    //   87: iconst_4
    //   88: invokevirtual mallocFloat : (I)Ljava/nio/FloatBuffer;
    //   91: astore #9
    //   93: aload_0
    //   94: getfield R : J
    //   97: fconst_0
    //   98: fconst_0
    //   99: aload #6
    //   101: aload #9
    //   103: invokestatic nvgTextBounds : (JFFLjava/lang/CharSequence;Ljava/nio/FloatBuffer;)F
    //   106: fstore #10
    //   108: aload #8
    //   110: aload #7
    //   112: ifnull -> 127
    //   115: ifnull -> 130
    //   118: goto -> 125
    //   121: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   124: athrow
    //   125: aload #8
    //   127: invokevirtual close : ()V
    //   130: fload #10
    //   132: freturn
    //   133: astore #9
    //   135: lload_3
    //   136: lconst_0
    //   137: lcmp
    //   138: iflt -> 163
    //   141: aload #8
    //   143: aload #7
    //   145: ifnull -> 160
    //   148: ifnull -> 175
    //   151: goto -> 158
    //   154: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   157: athrow
    //   158: aload #8
    //   160: invokevirtual close : ()V
    //   163: goto -> 175
    //   166: astore #10
    //   168: aload #9
    //   170: aload #10
    //   172: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   175: aload #9
    //   177: athrow
    // Exception table:
    //   from	to	target	type
    //   85	108	133	java/lang/Throwable
    //   108	118	121	java/lang/Throwable
    //   135	151	154	java/lang/Throwable
    //   158	163	166	java/lang/Throwable
  }
  
  public float q(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore #5
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Float
    //   25: invokevirtual floatValue : ()F
    //   28: fstore_2
    //   29: pop
    //   30: getstatic wtf/opal/u2.a : J
    //   33: lload_3
    //   34: lxor
    //   35: lstore_3
    //   36: lload_3
    //   37: dup2
    //   38: ldc2_w 112997144872325
    //   41: lxor
    //   42: lstore #6
    //   44: pop2
    //   45: aload_0
    //   46: aload #5
    //   48: lload #6
    //   50: fload_2
    //   51: sipush #14231
    //   54: ldc2_w 2705179125176895785
    //   57: lload_3
    //   58: lxor
    //   59: <illegal opcode> o : (IJ)I
    //   64: iconst_4
    //   65: anewarray java/lang/Object
    //   68: dup_x1
    //   69: swap
    //   70: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   73: iconst_3
    //   74: swap
    //   75: aastore
    //   76: dup_x1
    //   77: swap
    //   78: invokestatic valueOf : (F)Ljava/lang/Float;
    //   81: iconst_2
    //   82: swap
    //   83: aastore
    //   84: dup_x2
    //   85: dup_x2
    //   86: pop
    //   87: invokestatic valueOf : (J)Ljava/lang/Long;
    //   90: iconst_1
    //   91: swap
    //   92: aastore
    //   93: dup_x1
    //   94: swap
    //   95: iconst_0
    //   96: swap
    //   97: aastore
    //   98: invokevirtual M : ([Ljava/lang/Object;)F
    //   101: freturn
  }
  
  public float n(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore #4
    //   12: dup
    //   13: iconst_1
    //   14: aaload
    //   15: checkcast java/lang/String
    //   18: astore_2
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Float
    //   25: invokevirtual floatValue : ()F
    //   28: fstore_3
    //   29: pop
    //   30: getstatic wtf/opal/u2.a : J
    //   33: lload #4
    //   35: lxor
    //   36: lstore #4
    //   38: lload #4
    //   40: dup2
    //   41: ldc2_w 140046272098764
    //   44: lxor
    //   45: lstore #6
    //   47: pop2
    //   48: aload_0
    //   49: aload_2
    //   50: fload_3
    //   51: lload #6
    //   53: sipush #8289
    //   56: ldc2_w 1595031318495250018
    //   59: lload #4
    //   61: lxor
    //   62: <illegal opcode> o : (IJ)I
    //   67: iconst_4
    //   68: anewarray java/lang/Object
    //   71: dup_x1
    //   72: swap
    //   73: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   76: iconst_3
    //   77: swap
    //   78: aastore
    //   79: dup_x2
    //   80: dup_x2
    //   81: pop
    //   82: invokestatic valueOf : (J)Ljava/lang/Long;
    //   85: iconst_2
    //   86: swap
    //   87: aastore
    //   88: dup_x1
    //   89: swap
    //   90: invokestatic valueOf : (F)Ljava/lang/Float;
    //   93: iconst_1
    //   94: swap
    //   95: aastore
    //   96: dup_x1
    //   97: swap
    //   98: iconst_0
    //   99: swap
    //   100: aastore
    //   101: invokevirtual w : ([Ljava/lang/Object;)F
    //   104: freturn
  }
  
  public float w(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Float
    //   14: invokevirtual floatValue : ()F
    //   17: fstore #4
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Long
    //   25: invokevirtual longValue : ()J
    //   28: lstore #5
    //   30: dup
    //   31: iconst_3
    //   32: aaload
    //   33: checkcast java/lang/Integer
    //   36: invokevirtual intValue : ()I
    //   39: istore_2
    //   40: pop
    //   41: getstatic wtf/opal/u2.a : J
    //   44: lload #5
    //   46: lxor
    //   47: lstore #5
    //   49: aload_0
    //   50: getfield R : J
    //   53: aload_0
    //   54: getfield Z : Ljava/lang/String;
    //   57: invokestatic nvgFontFace : (JLjava/lang/CharSequence;)V
    //   60: invokestatic n : ()[Lwtf/opal/d;
    //   63: aload_0
    //   64: getfield R : J
    //   67: fload #4
    //   69: invokestatic nvgFontSize : (JF)V
    //   72: astore #7
    //   74: aload_0
    //   75: getfield R : J
    //   78: iload_2
    //   79: invokestatic nvgTextAlign : (JI)V
    //   82: invokestatic stackPush : ()Lorg/lwjgl/system/MemoryStack;
    //   85: astore #8
    //   87: aload #8
    //   89: iconst_4
    //   90: invokevirtual mallocFloat : (I)Ljava/nio/FloatBuffer;
    //   93: astore #9
    //   95: aload_0
    //   96: getfield R : J
    //   99: fconst_0
    //   100: fconst_0
    //   101: aload_3
    //   102: aload #9
    //   104: invokestatic nvgTextBounds : (JFFLjava/lang/CharSequence;Ljava/nio/FloatBuffer;)F
    //   107: pop
    //   108: aload #9
    //   110: iconst_3
    //   111: invokevirtual get : (I)F
    //   114: aload #9
    //   116: iconst_1
    //   117: invokevirtual get : (I)F
    //   120: fsub
    //   121: fstore #10
    //   123: aload #8
    //   125: aload #7
    //   127: ifnull -> 142
    //   130: ifnull -> 145
    //   133: goto -> 140
    //   136: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   139: athrow
    //   140: aload #8
    //   142: invokevirtual close : ()V
    //   145: fload #10
    //   147: freturn
    //   148: astore #9
    //   150: lload #5
    //   152: lconst_0
    //   153: lcmp
    //   154: ifle -> 179
    //   157: aload #8
    //   159: aload #7
    //   161: ifnull -> 176
    //   164: ifnull -> 191
    //   167: goto -> 174
    //   170: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   173: athrow
    //   174: aload #8
    //   176: invokevirtual close : ()V
    //   179: goto -> 191
    //   182: astore #10
    //   184: aload #9
    //   186: aload #10
    //   188: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   191: aload #9
    //   193: athrow
    // Exception table:
    //   from	to	target	type
    //   87	123	148	java/lang/Throwable
    //   123	133	136	java/lang/Throwable
    //   150	167	170	java/lang/Throwable
    //   174	179	182	java/lang/Throwable
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -8831150634879453909
    //   3: ldc2_w -1798280616716904256
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 260516822332270
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/u2.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/u2.d : Ljava/util/Map;
    //   38: getstatic wtf/opal/u2.a : J
    //   41: ldc2_w 49520253981293
    //   44: lxor
    //   45: lstore_0
    //   46: ldc_w 'DES/CBC/NoPadding'
    //   49: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   52: dup
    //   53: astore_2
    //   54: iconst_2
    //   55: ldc_w 'DES'
    //   58: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   61: bipush #8
    //   63: newarray byte
    //   65: dup
    //   66: iconst_0
    //   67: lload_0
    //   68: bipush #56
    //   70: lushr
    //   71: l2i
    //   72: i2b
    //   73: bastore
    //   74: iconst_1
    //   75: istore_3
    //   76: iload_3
    //   77: bipush #8
    //   79: if_icmpge -> 102
    //   82: dup
    //   83: iload_3
    //   84: lload_0
    //   85: iload_3
    //   86: bipush #8
    //   88: imul
    //   89: lshl
    //   90: bipush #56
    //   92: lushr
    //   93: l2i
    //   94: i2b
    //   95: bastore
    //   96: iinc #3, 1
    //   99: goto -> 76
    //   102: new javax/crypto/spec/DESKeySpec
    //   105: dup_x1
    //   106: swap
    //   107: invokespecial <init> : ([B)V
    //   110: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   113: new javax/crypto/spec/IvParameterSpec
    //   116: dup
    //   117: bipush #8
    //   119: newarray byte
    //   121: invokespecial <init> : ([B)V
    //   124: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   127: bipush #9
    //   129: newarray long
    //   131: astore #8
    //   133: iconst_0
    //   134: istore #5
    //   136: ldc_w 'Çvi/Âì5Û)èìsYÅº¬²Â:¬ðhYØWáÅÚvAf·2¡Q]7[~À'
    //   139: dup
    //   140: astore #6
    //   142: invokevirtual length : ()I
    //   145: istore #7
    //   147: iconst_0
    //   148: istore #4
    //   150: aload #6
    //   152: iload #4
    //   154: iinc #4, 8
    //   157: iload #4
    //   159: invokevirtual substring : (II)Ljava/lang/String;
    //   162: ldc_w 'ISO-8859-1'
    //   165: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   168: astore #9
    //   170: aload #8
    //   172: iload #5
    //   174: iinc #5, 1
    //   177: aload #9
    //   179: iconst_0
    //   180: baload
    //   181: i2l
    //   182: ldc2_w 255
    //   185: land
    //   186: bipush #56
    //   188: lshl
    //   189: aload #9
    //   191: iconst_1
    //   192: baload
    //   193: i2l
    //   194: ldc2_w 255
    //   197: land
    //   198: bipush #48
    //   200: lshl
    //   201: lor
    //   202: aload #9
    //   204: iconst_2
    //   205: baload
    //   206: i2l
    //   207: ldc2_w 255
    //   210: land
    //   211: bipush #40
    //   213: lshl
    //   214: lor
    //   215: aload #9
    //   217: iconst_3
    //   218: baload
    //   219: i2l
    //   220: ldc2_w 255
    //   223: land
    //   224: bipush #32
    //   226: lshl
    //   227: lor
    //   228: aload #9
    //   230: iconst_4
    //   231: baload
    //   232: i2l
    //   233: ldc2_w 255
    //   236: land
    //   237: bipush #24
    //   239: lshl
    //   240: lor
    //   241: aload #9
    //   243: iconst_5
    //   244: baload
    //   245: i2l
    //   246: ldc2_w 255
    //   249: land
    //   250: bipush #16
    //   252: lshl
    //   253: lor
    //   254: aload #9
    //   256: bipush #6
    //   258: baload
    //   259: i2l
    //   260: ldc2_w 255
    //   263: land
    //   264: bipush #8
    //   266: lshl
    //   267: lor
    //   268: aload #9
    //   270: bipush #7
    //   272: baload
    //   273: i2l
    //   274: ldc2_w 255
    //   277: land
    //   278: lor
    //   279: iconst_m1
    //   280: goto -> 462
    //   283: lastore
    //   284: iload #4
    //   286: iload #7
    //   288: if_icmplt -> 150
    //   291: ldc_w 'èX=TÛË#Ìtâæ'
    //   294: dup
    //   295: astore #6
    //   297: invokevirtual length : ()I
    //   300: istore #7
    //   302: iconst_0
    //   303: istore #4
    //   305: aload #6
    //   307: iload #4
    //   309: iinc #4, 8
    //   312: iload #4
    //   314: invokevirtual substring : (II)Ljava/lang/String;
    //   317: ldc_w 'ISO-8859-1'
    //   320: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   323: astore #9
    //   325: aload #8
    //   327: iload #5
    //   329: iinc #5, 1
    //   332: aload #9
    //   334: iconst_0
    //   335: baload
    //   336: i2l
    //   337: ldc2_w 255
    //   340: land
    //   341: bipush #56
    //   343: lshl
    //   344: aload #9
    //   346: iconst_1
    //   347: baload
    //   348: i2l
    //   349: ldc2_w 255
    //   352: land
    //   353: bipush #48
    //   355: lshl
    //   356: lor
    //   357: aload #9
    //   359: iconst_2
    //   360: baload
    //   361: i2l
    //   362: ldc2_w 255
    //   365: land
    //   366: bipush #40
    //   368: lshl
    //   369: lor
    //   370: aload #9
    //   372: iconst_3
    //   373: baload
    //   374: i2l
    //   375: ldc2_w 255
    //   378: land
    //   379: bipush #32
    //   381: lshl
    //   382: lor
    //   383: aload #9
    //   385: iconst_4
    //   386: baload
    //   387: i2l
    //   388: ldc2_w 255
    //   391: land
    //   392: bipush #24
    //   394: lshl
    //   395: lor
    //   396: aload #9
    //   398: iconst_5
    //   399: baload
    //   400: i2l
    //   401: ldc2_w 255
    //   404: land
    //   405: bipush #16
    //   407: lshl
    //   408: lor
    //   409: aload #9
    //   411: bipush #6
    //   413: baload
    //   414: i2l
    //   415: ldc2_w 255
    //   418: land
    //   419: bipush #8
    //   421: lshl
    //   422: lor
    //   423: aload #9
    //   425: bipush #7
    //   427: baload
    //   428: i2l
    //   429: ldc2_w 255
    //   432: land
    //   433: lor
    //   434: iconst_0
    //   435: goto -> 462
    //   438: lastore
    //   439: iload #4
    //   441: iload #7
    //   443: if_icmplt -> 305
    //   446: aload #8
    //   448: putstatic wtf/opal/u2.b : [J
    //   451: bipush #9
    //   453: anewarray java/lang/Integer
    //   456: putstatic wtf/opal/u2.c : [Ljava/lang/Integer;
    //   459: goto -> 680
    //   462: dup_x2
    //   463: pop
    //   464: lstore #10
    //   466: bipush #8
    //   468: newarray byte
    //   470: dup
    //   471: iconst_0
    //   472: lload #10
    //   474: bipush #56
    //   476: lushr
    //   477: l2i
    //   478: i2b
    //   479: bastore
    //   480: dup
    //   481: iconst_1
    //   482: lload #10
    //   484: bipush #48
    //   486: lushr
    //   487: l2i
    //   488: i2b
    //   489: bastore
    //   490: dup
    //   491: iconst_2
    //   492: lload #10
    //   494: bipush #40
    //   496: lushr
    //   497: l2i
    //   498: i2b
    //   499: bastore
    //   500: dup
    //   501: iconst_3
    //   502: lload #10
    //   504: bipush #32
    //   506: lushr
    //   507: l2i
    //   508: i2b
    //   509: bastore
    //   510: dup
    //   511: iconst_4
    //   512: lload #10
    //   514: bipush #24
    //   516: lushr
    //   517: l2i
    //   518: i2b
    //   519: bastore
    //   520: dup
    //   521: iconst_5
    //   522: lload #10
    //   524: bipush #16
    //   526: lushr
    //   527: l2i
    //   528: i2b
    //   529: bastore
    //   530: dup
    //   531: bipush #6
    //   533: lload #10
    //   535: bipush #8
    //   537: lushr
    //   538: l2i
    //   539: i2b
    //   540: bastore
    //   541: dup
    //   542: bipush #7
    //   544: lload #10
    //   546: l2i
    //   547: i2b
    //   548: bastore
    //   549: aload_2
    //   550: swap
    //   551: invokevirtual doFinal : ([B)[B
    //   554: astore #12
    //   556: aload #12
    //   558: iconst_0
    //   559: baload
    //   560: i2l
    //   561: ldc2_w 255
    //   564: land
    //   565: bipush #56
    //   567: lshl
    //   568: aload #12
    //   570: iconst_1
    //   571: baload
    //   572: i2l
    //   573: ldc2_w 255
    //   576: land
    //   577: bipush #48
    //   579: lshl
    //   580: lor
    //   581: aload #12
    //   583: iconst_2
    //   584: baload
    //   585: i2l
    //   586: ldc2_w 255
    //   589: land
    //   590: bipush #40
    //   592: lshl
    //   593: lor
    //   594: aload #12
    //   596: iconst_3
    //   597: baload
    //   598: i2l
    //   599: ldc2_w 255
    //   602: land
    //   603: bipush #32
    //   605: lshl
    //   606: lor
    //   607: aload #12
    //   609: iconst_4
    //   610: baload
    //   611: i2l
    //   612: ldc2_w 255
    //   615: land
    //   616: bipush #24
    //   618: lshl
    //   619: lor
    //   620: aload #12
    //   622: iconst_5
    //   623: baload
    //   624: i2l
    //   625: ldc2_w 255
    //   628: land
    //   629: bipush #16
    //   631: lshl
    //   632: lor
    //   633: aload #12
    //   635: bipush #6
    //   637: baload
    //   638: i2l
    //   639: ldc2_w 255
    //   642: land
    //   643: bipush #8
    //   645: lshl
    //   646: lor
    //   647: aload #12
    //   649: bipush #7
    //   651: baload
    //   652: i2l
    //   653: ldc2_w 255
    //   656: land
    //   657: lor
    //   658: dup2_x1
    //   659: pop2
    //   660: tableswitch default -> 283, 0 -> 438
    //   680: return
  }
  
  private static Throwable a(Throwable paramThrowable) {
    return paramThrowable;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x68BF;
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
        throw new RuntimeException("wtf/opal/u2", exception);
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
    //   65: ldc_w 'wtf/opal/u2'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */