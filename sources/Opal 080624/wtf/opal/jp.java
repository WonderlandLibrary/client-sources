package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1268;
import net.minecraft.class_1293;
import net.minecraft.class_1661;
import net.minecraft.class_1799;
import net.minecraft.class_1844;
import net.minecraft.class_2596;
import net.minecraft.class_2886;
import net.minecraft.class_9334;

public final class jp extends d {
  private final kt J;
  
  private final kt q;
  
  private final ke r;
  
  private final ke y;
  
  private final ke E;
  
  private final kr p;
  
  private final gm<u0> l;
  
  private static final long a = on.a(3266773414969800497L, 4007457707954533973L, MethodHandles.lookup().lookupClass()).a(229205098867520L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long g;
  
  public jp(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jp.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 67029184873153
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 136259518640573
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 94093980526824
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #2648
    //   32: ldc2_w 5617480169708121198
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #8918
    //   47: ldc2_w 2423941692856452322
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/kt
    //   67: dup
    //   68: sipush #32032
    //   71: ldc2_w 8201709067655125781
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   81: ldc2_w 750.0
    //   84: dconst_0
    //   85: ldc2_w 2000.0
    //   88: ldc2_w 50.0
    //   91: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   94: putfield J : Lwtf/opal/kt;
    //   97: aload_0
    //   98: new wtf/opal/kt
    //   101: dup
    //   102: sipush #4097
    //   105: ldc2_w 8584283076529780273
    //   108: lload_1
    //   109: lxor
    //   110: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   115: ldc2_w 12.0
    //   118: dconst_1
    //   119: ldc2_w 20.0
    //   122: ldc2_w 0.5
    //   125: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   128: putfield q : Lwtf/opal/kt;
    //   131: aload_0
    //   132: new wtf/opal/ke
    //   135: dup
    //   136: sipush #28854
    //   139: ldc2_w 8785844881130002055
    //   142: lload_1
    //   143: lxor
    //   144: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   149: iconst_1
    //   150: invokespecial <init> : (Ljava/lang/String;Z)V
    //   153: putfield r : Lwtf/opal/ke;
    //   156: aload_0
    //   157: new wtf/opal/ke
    //   160: dup
    //   161: sipush #4266
    //   164: ldc2_w 2174358214593214104
    //   167: lload_1
    //   168: lxor
    //   169: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   174: iconst_1
    //   175: invokespecial <init> : (Ljava/lang/String;Z)V
    //   178: putfield y : Lwtf/opal/ke;
    //   181: aload_0
    //   182: new wtf/opal/ke
    //   185: dup
    //   186: sipush #8789
    //   189: ldc2_w 2923452056492721254
    //   192: lload_1
    //   193: lxor
    //   194: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   199: iconst_0
    //   200: invokespecial <init> : (Ljava/lang/String;Z)V
    //   203: putfield E : Lwtf/opal/ke;
    //   206: invokestatic n : ()Z
    //   209: aload_0
    //   210: new wtf/opal/kr
    //   213: dup
    //   214: invokespecial <init> : ()V
    //   217: putfield p : Lwtf/opal/kr;
    //   220: aload_0
    //   221: aload_0
    //   222: <illegal opcode> H : (Lwtf/opal/jp;)Lwtf/opal/gm;
    //   227: putfield l : Lwtf/opal/gm;
    //   230: aload_0
    //   231: getfield q : Lwtf/opal/kt;
    //   234: aload_0
    //   235: getfield y : Lwtf/opal/ke;
    //   238: aload_0
    //   239: <illegal opcode> test : (Lwtf/opal/jp;)Ljava/util/function/Predicate;
    //   244: lload_3
    //   245: dup2_x1
    //   246: pop2
    //   247: iconst_3
    //   248: anewarray java/lang/Object
    //   251: dup_x1
    //   252: swap
    //   253: iconst_2
    //   254: swap
    //   255: aastore
    //   256: dup_x2
    //   257: dup_x2
    //   258: pop
    //   259: invokestatic valueOf : (J)Ljava/lang/Long;
    //   262: iconst_1
    //   263: swap
    //   264: aastore
    //   265: dup_x1
    //   266: swap
    //   267: iconst_0
    //   268: swap
    //   269: aastore
    //   270: invokevirtual C : ([Ljava/lang/Object;)V
    //   273: aload_0
    //   274: getfield E : Lwtf/opal/ke;
    //   277: aload_0
    //   278: getfield r : Lwtf/opal/ke;
    //   281: aload_0
    //   282: <illegal opcode> test : (Lwtf/opal/jp;)Ljava/util/function/Predicate;
    //   287: lload_3
    //   288: dup2_x1
    //   289: pop2
    //   290: iconst_3
    //   291: anewarray java/lang/Object
    //   294: dup_x1
    //   295: swap
    //   296: iconst_2
    //   297: swap
    //   298: aastore
    //   299: dup_x2
    //   300: dup_x2
    //   301: pop
    //   302: invokestatic valueOf : (J)Ljava/lang/Long;
    //   305: iconst_1
    //   306: swap
    //   307: aastore
    //   308: dup_x1
    //   309: swap
    //   310: iconst_0
    //   311: swap
    //   312: aastore
    //   313: invokevirtual C : ([Ljava/lang/Object;)V
    //   316: aload_0
    //   317: iconst_5
    //   318: anewarray wtf/opal/k3
    //   321: dup
    //   322: iconst_0
    //   323: aload_0
    //   324: getfield y : Lwtf/opal/ke;
    //   327: aastore
    //   328: dup
    //   329: iconst_1
    //   330: aload_0
    //   331: getfield r : Lwtf/opal/ke;
    //   334: aastore
    //   335: dup
    //   336: iconst_2
    //   337: aload_0
    //   338: getfield J : Lwtf/opal/kt;
    //   341: aastore
    //   342: dup
    //   343: iconst_3
    //   344: aload_0
    //   345: getfield q : Lwtf/opal/kt;
    //   348: aastore
    //   349: dup
    //   350: iconst_4
    //   351: aload_0
    //   352: getfield E : Lwtf/opal/ke;
    //   355: aastore
    //   356: lload #5
    //   358: dup2_x1
    //   359: pop2
    //   360: iconst_2
    //   361: anewarray java/lang/Object
    //   364: dup_x1
    //   365: swap
    //   366: iconst_1
    //   367: swap
    //   368: aastore
    //   369: dup_x2
    //   370: dup_x2
    //   371: pop
    //   372: invokestatic valueOf : (J)Ljava/lang/Long;
    //   375: iconst_0
    //   376: swap
    //   377: aastore
    //   378: invokevirtual o : ([Ljava/lang/Object;)V
    //   381: istore #9
    //   383: invokestatic D : ()[Lwtf/opal/d;
    //   386: ifnull -> 413
    //   389: iload #9
    //   391: ifeq -> 409
    //   394: goto -> 401
    //   397: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   400: athrow
    //   401: iconst_0
    //   402: goto -> 410
    //   405: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   408: athrow
    //   409: iconst_1
    //   410: invokestatic W : (Z)V
    //   413: return
    // Exception table:
    //   from	to	target	type
    //   383	394	397	wtf/opal/x5
    //   389	405	405	wtf/opal/x5
  }
  
  private boolean e(Object[] paramArrayOfObject) {
    class_1799 class_1799 = (class_1799)paramArrayOfObject[0];
    return ((Stream)Optional.<class_1799>ofNullable(class_1799).filter(jp::lambda$isSpeedPotion$6).map(jp::lambda$isSpeedPotion$7).map(class_1844::method_57397).map(jp::lambda$isSpeedPotion$8).orElseGet(Stream::empty)).anyMatch(this::lambda$isSpeedPotion$9);
  }
  
  private boolean v(Object[] paramArrayOfObject) {
    class_1799 class_1799 = (class_1799)paramArrayOfObject[0];
    return ((Stream)Optional.<class_1799>ofNullable(class_1799).filter(jp::lambda$isHealthPotion$10).map(jp::lambda$isHealthPotion$11).map(class_1844::method_57397).map(jp::lambda$isHealthPotion$12).orElseGet(Stream::empty)).anyMatch(jp::lambda$isHealthPotion$13);
  }
  
  private static boolean lambda$isHealthPotion$13(class_1293 paramclass_1293) {
    // Byte code:
    //   0: getstatic wtf/opal/jp.a : J
    //   3: ldc2_w 65848516009036
    //   6: lxor
    //   7: lstore_1
    //   8: invokestatic k : ()Z
    //   11: istore_3
    //   12: aload_0
    //   13: invokevirtual method_5579 : ()Lnet/minecraft/class_6880;
    //   16: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   21: getstatic net/minecraft/class_1294.field_5924 : Lnet/minecraft/class_6880;
    //   24: iload_3
    //   25: ifeq -> 57
    //   28: if_acmpeq -> 93
    //   31: goto -> 38
    //   34: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   37: athrow
    //   38: aload_0
    //   39: invokevirtual method_5579 : ()Lnet/minecraft/class_6880;
    //   42: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   47: getstatic net/minecraft/class_1294.field_5915 : Lnet/minecraft/class_6880;
    //   50: goto -> 57
    //   53: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   56: athrow
    //   57: iload_3
    //   58: ifeq -> 90
    //   61: if_acmpeq -> 93
    //   64: goto -> 71
    //   67: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   70: athrow
    //   71: aload_0
    //   72: invokevirtual method_5579 : ()Lnet/minecraft/class_6880;
    //   75: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   80: getstatic net/minecraft/class_1294.field_5914 : Lnet/minecraft/class_6880;
    //   83: goto -> 90
    //   86: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: if_acmpne -> 101
    //   93: iconst_1
    //   94: goto -> 102
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: iconst_0
    //   102: ireturn
    // Exception table:
    //   from	to	target	type
    //   12	31	34	wtf/opal/x5
    //   28	50	53	wtf/opal/x5
    //   57	64	67	wtf/opal/x5
    //   61	83	86	wtf/opal/x5
    //   90	97	97	wtf/opal/x5
  }
  
  private static Stream lambda$isHealthPotion$12(Iterable<?> paramIterable) {
    return StreamSupport.stream(paramIterable.spliterator(), false);
  }
  
  private static class_1844 lambda$isHealthPotion$11(class_1799 paramclass_1799) {
    return (class_1844)paramclass_1799.method_57824(class_9334.field_49651);
  }
  
  private static boolean lambda$isHealthPotion$10(class_1799 paramclass_1799) {
    return paramclass_1799.method_7909() instanceof net.minecraft.class_1828;
  }
  
  private boolean lambda$isSpeedPotion$9(class_1293 paramclass_1293) {
    // Byte code:
    //   0: getstatic wtf/opal/jp.a : J
    //   3: ldc2_w 107461558479487
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic k : ()Z
    //   11: istore #4
    //   13: aload_0
    //   14: getfield E : Lwtf/opal/ke;
    //   17: invokevirtual z : ()Ljava/lang/Object;
    //   20: checkcast java/lang/Boolean
    //   23: iload #4
    //   25: ifeq -> 69
    //   28: invokevirtual booleanValue : ()Z
    //   31: ifeq -> 81
    //   34: goto -> 41
    //   37: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   40: athrow
    //   41: aload_1
    //   42: invokevirtual method_5579 : ()Lnet/minecraft/class_6880;
    //   45: iload #4
    //   47: ifeq -> 85
    //   50: goto -> 57
    //   53: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   56: athrow
    //   57: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   62: goto -> 69
    //   65: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: getstatic net/minecraft/class_1294.field_5913 : Lnet/minecraft/class_6880;
    //   72: if_acmpne -> 81
    //   75: iconst_0
    //   76: ireturn
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: aload_1
    //   82: invokevirtual method_5579 : ()Lnet/minecraft/class_6880;
    //   85: getstatic net/minecraft/class_1294.field_5904 : Lnet/minecraft/class_6880;
    //   88: if_acmpne -> 99
    //   91: iconst_1
    //   92: goto -> 100
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: iconst_0
    //   100: ireturn
    // Exception table:
    //   from	to	target	type
    //   13	34	37	wtf/opal/x5
    //   28	50	53	wtf/opal/x5
    //   41	62	65	wtf/opal/x5
    //   69	77	77	wtf/opal/x5
    //   85	95	95	wtf/opal/x5
  }
  
  private static Stream lambda$isSpeedPotion$8(Iterable<?> paramIterable) {
    return StreamSupport.stream(paramIterable.spliterator(), false);
  }
  
  private static class_1844 lambda$isSpeedPotion$7(class_1799 paramclass_1799) {
    return (class_1844)paramclass_1799.method_57824(class_9334.field_49651);
  }
  
  private static boolean lambda$isSpeedPotion$6(class_1799 paramclass_1799) {
    return paramclass_1799.method_7909() instanceof net.minecraft.class_1828;
  }
  
  private void lambda$new$5(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/jp.a : J
    //   3: ldc2_w 31900121650253
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 57657004345561
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 108897520165433
    //   20: lxor
    //   21: dup2
    //   22: bipush #48
    //   24: lushr
    //   25: l2i
    //   26: istore #6
    //   28: dup2
    //   29: bipush #16
    //   31: lshl
    //   32: bipush #32
    //   34: lushr
    //   35: l2i
    //   36: istore #7
    //   38: dup2
    //   39: bipush #48
    //   41: lshl
    //   42: bipush #48
    //   44: lushr
    //   45: l2i
    //   46: istore #8
    //   48: pop2
    //   49: pop2
    //   50: invokestatic n : ()Z
    //   53: istore #9
    //   55: aload_1
    //   56: iconst_0
    //   57: anewarray java/lang/Object
    //   60: invokevirtual K : ([Ljava/lang/Object;)Z
    //   63: iload #9
    //   65: ifne -> 94
    //   68: ifne -> 338
    //   71: goto -> 78
    //   74: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   77: athrow
    //   78: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   81: getfield field_1724 : Lnet/minecraft/class_746;
    //   84: invokevirtual method_24828 : ()Z
    //   87: goto -> 94
    //   90: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: iload #9
    //   96: ifne -> 169
    //   99: ifeq -> 338
    //   102: goto -> 109
    //   105: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   108: athrow
    //   109: aload_0
    //   110: getfield p : Lwtf/opal/kr;
    //   113: aload_0
    //   114: getfield J : Lwtf/opal/kt;
    //   117: invokevirtual z : ()Ljava/lang/Object;
    //   120: checkcast java/lang/Double
    //   123: invokevirtual longValue : ()J
    //   126: lload #4
    //   128: iconst_0
    //   129: iconst_3
    //   130: anewarray java/lang/Object
    //   133: dup_x1
    //   134: swap
    //   135: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   138: iconst_2
    //   139: swap
    //   140: aastore
    //   141: dup_x2
    //   142: dup_x2
    //   143: pop
    //   144: invokestatic valueOf : (J)Ljava/lang/Long;
    //   147: iconst_1
    //   148: swap
    //   149: aastore
    //   150: dup_x2
    //   151: dup_x2
    //   152: pop
    //   153: invokestatic valueOf : (J)Ljava/lang/Long;
    //   156: iconst_0
    //   157: swap
    //   158: aastore
    //   159: invokevirtual v : ([Ljava/lang/Object;)Z
    //   162: goto -> 169
    //   165: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   168: athrow
    //   169: iload #9
    //   171: ifne -> 200
    //   174: ifeq -> 338
    //   177: goto -> 184
    //   180: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   183: athrow
    //   184: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   187: getfield field_1724 : Lnet/minecraft/class_746;
    //   190: invokevirtual method_6115 : ()Z
    //   193: goto -> 200
    //   196: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   199: athrow
    //   200: iload #9
    //   202: ifne -> 283
    //   205: ifne -> 338
    //   208: goto -> 215
    //   211: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   218: getfield field_1724 : Lnet/minecraft/class_746;
    //   221: iload #9
    //   223: ifne -> 299
    //   226: goto -> 233
    //   229: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   232: athrow
    //   233: iload #6
    //   235: i2s
    //   236: iload #7
    //   238: iload #8
    //   240: iconst_4
    //   241: anewarray java/lang/Object
    //   244: dup_x1
    //   245: swap
    //   246: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   249: iconst_3
    //   250: swap
    //   251: aastore
    //   252: dup_x1
    //   253: swap
    //   254: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   257: iconst_2
    //   258: swap
    //   259: aastore
    //   260: dup_x1
    //   261: swap
    //   262: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   265: iconst_1
    //   266: swap
    //   267: aastore
    //   268: dup_x1
    //   269: swap
    //   270: iconst_0
    //   271: swap
    //   272: aastore
    //   273: invokestatic J : ([Ljava/lang/Object;)Z
    //   276: goto -> 283
    //   279: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   282: athrow
    //   283: ifne -> 338
    //   286: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   289: getfield field_1724 : Lnet/minecraft/class_746;
    //   292: goto -> 299
    //   295: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   298: athrow
    //   299: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   302: astore #10
    //   304: iconst_0
    //   305: getstatic wtf/opal/jp.g : J
    //   308: l2i
    //   309: invokestatic range : (II)Ljava/util/stream/IntStream;
    //   312: aload_0
    //   313: aload #10
    //   315: <illegal opcode> test : (Lwtf/opal/jp;Lnet/minecraft/class_1661;)Ljava/util/function/IntPredicate;
    //   320: invokeinterface filter : (Ljava/util/function/IntPredicate;)Ljava/util/stream/IntStream;
    //   325: aload_0
    //   326: aload #10
    //   328: <illegal opcode> accept : (Lwtf/opal/jp;Lnet/minecraft/class_1661;)Ljava/util/function/IntConsumer;
    //   333: invokeinterface forEach : (Ljava/util/function/IntConsumer;)V
    //   338: return
    // Exception table:
    //   from	to	target	type
    //   55	71	74	wtf/opal/x5
    //   68	87	90	wtf/opal/x5
    //   94	102	105	wtf/opal/x5
    //   99	162	165	wtf/opal/x5
    //   169	177	180	wtf/opal/x5
    //   174	193	196	wtf/opal/x5
    //   200	208	211	wtf/opal/x5
    //   205	226	229	wtf/opal/x5
    //   215	276	279	wtf/opal/x5
    //   283	292	295	wtf/opal/x5
  }
  
  private void lambda$new$4(class_1661 paramclass_1661, int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/jp.a : J
    //   3: ldc2_w 66889154707562
    //   6: lxor
    //   7: lstore_3
    //   8: invokestatic n : ()Z
    //   11: istore #5
    //   13: aload_0
    //   14: aload_1
    //   15: iload_2
    //   16: invokevirtual method_5438 : (I)Lnet/minecraft/class_1799;
    //   19: iconst_1
    //   20: anewarray java/lang/Object
    //   23: dup_x1
    //   24: swap
    //   25: iconst_0
    //   26: swap
    //   27: aastore
    //   28: invokevirtual e : ([Ljava/lang/Object;)Z
    //   31: iload #5
    //   33: ifne -> 111
    //   36: ifeq -> 81
    //   39: goto -> 46
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   49: getfield field_1724 : Lnet/minecraft/class_746;
    //   52: getstatic net/minecraft/class_1294.field_5904 : Lnet/minecraft/class_6880;
    //   55: invokevirtual method_6059 : (Lnet/minecraft/class_6880;)Z
    //   58: iload #5
    //   60: ifne -> 111
    //   63: goto -> 70
    //   66: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   69: athrow
    //   70: ifeq -> 81
    //   73: goto -> 80
    //   76: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   79: athrow
    //   80: return
    //   81: aload_0
    //   82: iload #5
    //   84: ifne -> 300
    //   87: aload_1
    //   88: iload_2
    //   89: invokevirtual method_5438 : (I)Lnet/minecraft/class_1799;
    //   92: iconst_1
    //   93: anewarray java/lang/Object
    //   96: dup_x1
    //   97: swap
    //   98: iconst_0
    //   99: swap
    //   100: aastore
    //   101: invokevirtual v : ([Ljava/lang/Object;)Z
    //   104: goto -> 111
    //   107: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   110: athrow
    //   111: ifeq -> 165
    //   114: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   117: iload #5
    //   119: ifne -> 274
    //   122: goto -> 129
    //   125: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   128: athrow
    //   129: getfield field_1724 : Lnet/minecraft/class_746;
    //   132: invokevirtual method_6032 : ()F
    //   135: f2d
    //   136: aload_0
    //   137: getfield q : Lwtf/opal/kt;
    //   140: invokevirtual z : ()Ljava/lang/Object;
    //   143: checkcast java/lang/Double
    //   146: invokevirtual doubleValue : ()D
    //   149: dcmpl
    //   150: ifle -> 165
    //   153: goto -> 160
    //   156: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   159: athrow
    //   160: return
    //   161: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   164: athrow
    //   165: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   168: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   171: new net/minecraft/class_2868
    //   174: dup
    //   175: iload_2
    //   176: invokespecial <init> : (I)V
    //   179: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   182: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   185: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   188: new net/minecraft/class_2828$class_2830
    //   191: dup
    //   192: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   195: getfield field_1724 : Lnet/minecraft/class_746;
    //   198: invokevirtual method_23317 : ()D
    //   201: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   204: getfield field_1724 : Lnet/minecraft/class_746;
    //   207: invokevirtual method_23318 : ()D
    //   210: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   213: getfield field_1724 : Lnet/minecraft/class_746;
    //   216: invokevirtual method_23321 : ()D
    //   219: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   222: getfield field_1724 : Lnet/minecraft/class_746;
    //   225: invokevirtual method_36454 : ()F
    //   228: ldc_w 90.0
    //   231: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   234: getfield field_1724 : Lnet/minecraft/class_746;
    //   237: invokevirtual method_24828 : ()Z
    //   240: invokespecial <init> : (DDDFFZ)V
    //   243: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   246: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   249: getfield field_1761 : Lnet/minecraft/class_636;
    //   252: checkcast wtf/opal/mixin/ClientPlayerInteractionManagerAccessor
    //   255: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   258: getfield field_1687 : Lnet/minecraft/class_638;
    //   261: <illegal opcode> predict : ()Lnet/minecraft/class_7204;
    //   266: invokeinterface callSendSequencedPacket : (Lnet/minecraft/class_638;Lnet/minecraft/class_7204;)V
    //   271: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   274: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   277: new net/minecraft/class_2868
    //   280: dup
    //   281: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   284: getfield field_1724 : Lnet/minecraft/class_746;
    //   287: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   290: getfield field_7545 : I
    //   293: invokespecial <init> : (I)V
    //   296: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   299: aload_0
    //   300: getfield p : Lwtf/opal/kr;
    //   303: iconst_0
    //   304: anewarray java/lang/Object
    //   307: invokevirtual z : ([Ljava/lang/Object;)V
    //   310: return
    // Exception table:
    //   from	to	target	type
    //   13	39	42	wtf/opal/x5
    //   36	63	66	wtf/opal/x5
    //   46	73	76	wtf/opal/x5
    //   81	104	107	wtf/opal/x5
    //   111	122	125	wtf/opal/x5
    //   114	153	156	wtf/opal/x5
    //   129	161	161	wtf/opal/x5
  }
  
  private static class_2596 lambda$new$3(int paramInt) {
    return (class_2596)new class_2886(class_1268.field_5808, paramInt);
  }
  
  private boolean lambda$new$2(class_1661 paramclass_1661, int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/jp.a : J
    //   3: ldc2_w 67298449222134
    //   6: lxor
    //   7: lstore_3
    //   8: invokestatic n : ()Z
    //   11: istore #5
    //   13: aload_0
    //   14: aload_1
    //   15: iload_2
    //   16: invokevirtual method_5438 : (I)Lnet/minecraft/class_1799;
    //   19: iconst_1
    //   20: anewarray java/lang/Object
    //   23: dup_x1
    //   24: swap
    //   25: iconst_0
    //   26: swap
    //   27: aastore
    //   28: invokevirtual e : ([Ljava/lang/Object;)Z
    //   31: iload #5
    //   33: ifne -> 87
    //   36: ifne -> 86
    //   39: goto -> 46
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: aload_0
    //   47: aload_1
    //   48: iload_2
    //   49: invokevirtual method_5438 : (I)Lnet/minecraft/class_1799;
    //   52: iconst_1
    //   53: anewarray java/lang/Object
    //   56: dup_x1
    //   57: swap
    //   58: iconst_0
    //   59: swap
    //   60: aastore
    //   61: invokevirtual v : ([Ljava/lang/Object;)Z
    //   64: iload #5
    //   66: ifne -> 87
    //   69: goto -> 76
    //   72: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   75: athrow
    //   76: ifeq -> 90
    //   79: goto -> 86
    //   82: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   85: athrow
    //   86: iconst_1
    //   87: goto -> 91
    //   90: iconst_0
    //   91: ireturn
    // Exception table:
    //   from	to	target	type
    //   13	39	42	wtf/opal/x5
    //   36	69	72	wtf/opal/x5
    //   46	79	82	wtf/opal/x5
  }
  
  private boolean lambda$new$1(ke paramke) {
    return this.r.z().booleanValue();
  }
  
  private boolean lambda$new$0(ke paramke) {
    return this.y.z().booleanValue();
  }
  
  static {
    long l = a ^ 0x1AB117FC3213L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "gqX\003s¢B&òS½6ÞR\030£c«\004#ÛÒ\"[§Öy{oðoéÕO é»\004Å\0130ocæ4Ð\037\fèÃ¬\024Þß/Yj!Êpx¾f7\003(¨¡]¾M»Ú7Þ;Ggbt¹/BJ½Ù¦Ó\b\0076£F\026\026ã?Í\"dAl´p@ÄOïÛ\036ªÌwÔ½òêÄ·§è\002-ï _Æ­N~G\003æ%ÉnlÂÁ-{~-X½Ìdnu\022Óp2Ðñ\n\024Âj%").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
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
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x54B1;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])f.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          f.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jp", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      d[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return d[i];
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
    //   66: ldc_w 'wtf/opal/jp'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */