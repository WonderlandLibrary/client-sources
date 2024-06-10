package wtf.opal;

import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_243;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_293;
import net.minecraft.class_4184;
import org.joml.Matrix4f;

public final class bc {
  public static boolean U;
  
  public static boolean L;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  public static class_243 h(Object[] paramArrayOfObject) {
    class_243 class_2431 = (class_243)paramArrayOfObject[0];
    class_4184 class_4184 = b9.c.field_1773.method_19418();
    class_243 class_2432 = class_4184.method_19326();
    return class_2431.method_1020(class_2432);
  }
  
  public static void T(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_4587
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Integer
    //   14: invokevirtual intValue : ()I
    //   17: istore #4
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Integer
    //   25: invokevirtual intValue : ()I
    //   28: istore #7
    //   30: dup
    //   31: iconst_3
    //   32: aaload
    //   33: checkcast net/minecraft/class_243
    //   36: astore_1
    //   37: dup
    //   38: iconst_4
    //   39: aaload
    //   40: checkcast net/minecraft/class_243
    //   43: astore_3
    //   44: dup
    //   45: iconst_5
    //   46: aaload
    //   47: checkcast java/lang/Long
    //   50: invokevirtual longValue : ()J
    //   53: lstore #5
    //   55: pop
    //   56: getstatic wtf/opal/bc.a : J
    //   59: lload #5
    //   61: lxor
    //   62: lstore #5
    //   64: lload #5
    //   66: dup2
    //   67: ldc2_w 85109699534871
    //   70: lxor
    //   71: lstore #8
    //   73: dup2
    //   74: ldc2_w 44072947299511
    //   77: lxor
    //   78: lstore #10
    //   80: pop2
    //   81: aload_2
    //   82: invokevirtual method_23760 : ()Lnet/minecraft/class_4587$class_4665;
    //   85: invokevirtual method_23761 : ()Lorg/joml/Matrix4f;
    //   88: astore #13
    //   90: iload #4
    //   92: lload #8
    //   94: iconst_2
    //   95: anewarray java/lang/Object
    //   98: dup_x2
    //   99: dup_x2
    //   100: pop
    //   101: invokestatic valueOf : (J)Ljava/lang/Long;
    //   104: iconst_1
    //   105: swap
    //   106: aastore
    //   107: dup_x1
    //   108: swap
    //   109: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   112: iconst_0
    //   113: swap
    //   114: aastore
    //   115: invokestatic J : ([Ljava/lang/Object;)[I
    //   118: astore #14
    //   120: iload #7
    //   122: lload #8
    //   124: iconst_2
    //   125: anewarray java/lang/Object
    //   128: dup_x2
    //   129: dup_x2
    //   130: pop
    //   131: invokestatic valueOf : (J)Ljava/lang/Long;
    //   134: iconst_1
    //   135: swap
    //   136: aastore
    //   137: dup_x1
    //   138: swap
    //   139: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   142: iconst_0
    //   143: swap
    //   144: aastore
    //   145: invokestatic J : ([Ljava/lang/Object;)[I
    //   148: astore #15
    //   150: invokestatic Y : ()Z
    //   153: aload_1
    //   154: iconst_1
    //   155: anewarray java/lang/Object
    //   158: dup_x1
    //   159: swap
    //   160: iconst_0
    //   161: swap
    //   162: aastore
    //   163: invokestatic h : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   166: astore #16
    //   168: aload #16
    //   170: aload_3
    //   171: invokevirtual method_1019 : (Lnet/minecraft/class_243;)Lnet/minecraft/class_243;
    //   174: astore #17
    //   176: aload #16
    //   178: getfield field_1352 : D
    //   181: d2f
    //   182: fstore #18
    //   184: aload #16
    //   186: getfield field_1351 : D
    //   189: d2f
    //   190: fstore #19
    //   192: aload #16
    //   194: getfield field_1350 : D
    //   197: d2f
    //   198: fstore #20
    //   200: istore #12
    //   202: aload #17
    //   204: getfield field_1352 : D
    //   207: d2f
    //   208: fstore #21
    //   210: aload #17
    //   212: getfield field_1351 : D
    //   215: d2f
    //   216: fstore #22
    //   218: aload #17
    //   220: getfield field_1350 : D
    //   223: d2f
    //   224: fstore #23
    //   226: aload #14
    //   228: iconst_0
    //   229: iaload
    //   230: i2f
    //   231: ldc 255.0
    //   233: fdiv
    //   234: fstore #24
    //   236: aload #14
    //   238: iconst_1
    //   239: iaload
    //   240: i2f
    //   241: ldc 255.0
    //   243: fdiv
    //   244: fstore #25
    //   246: aload #14
    //   248: iconst_2
    //   249: iaload
    //   250: i2f
    //   251: ldc 255.0
    //   253: fdiv
    //   254: fstore #26
    //   256: aload #14
    //   258: iconst_3
    //   259: iaload
    //   260: i2f
    //   261: ldc 255.0
    //   263: fdiv
    //   264: fstore #27
    //   266: aload #15
    //   268: iconst_0
    //   269: iaload
    //   270: i2f
    //   271: ldc 255.0
    //   273: fdiv
    //   274: fstore #28
    //   276: aload #15
    //   278: iconst_1
    //   279: iaload
    //   280: i2f
    //   281: ldc 255.0
    //   283: fdiv
    //   284: fstore #29
    //   286: aload #15
    //   288: iconst_2
    //   289: iaload
    //   290: i2f
    //   291: ldc 255.0
    //   293: fdiv
    //   294: fstore #30
    //   296: aload #15
    //   298: iconst_3
    //   299: iaload
    //   300: i2f
    //   301: ldc 255.0
    //   303: fdiv
    //   304: fstore #31
    //   306: getstatic net/minecraft/class_293$class_5596.field_27382 : Lnet/minecraft/class_293$class_5596;
    //   309: getstatic net/minecraft/class_290.field_1576 : Lnet/minecraft/class_293;
    //   312: <illegal opcode> get : ()Ljava/util/function/Supplier;
    //   317: lload #10
    //   319: dup2_x1
    //   320: pop2
    //   321: aload #13
    //   323: fload #18
    //   325: fload #22
    //   327: fload #20
    //   329: fload #24
    //   331: fload #25
    //   333: fload #26
    //   335: fload #27
    //   337: fload #23
    //   339: fload #21
    //   341: fload #19
    //   343: <illegal opcode> accept : (Lorg/joml/Matrix4f;FFFFFFFFFF)Ljava/util/function/Consumer;
    //   348: iconst_5
    //   349: anewarray java/lang/Object
    //   352: dup_x1
    //   353: swap
    //   354: iconst_4
    //   355: swap
    //   356: aastore
    //   357: dup_x1
    //   358: swap
    //   359: iconst_3
    //   360: swap
    //   361: aastore
    //   362: dup_x2
    //   363: dup_x2
    //   364: pop
    //   365: invokestatic valueOf : (J)Ljava/lang/Long;
    //   368: iconst_2
    //   369: swap
    //   370: aastore
    //   371: dup_x1
    //   372: swap
    //   373: iconst_1
    //   374: swap
    //   375: aastore
    //   376: dup_x1
    //   377: swap
    //   378: iconst_0
    //   379: swap
    //   380: aastore
    //   381: invokestatic w : ([Ljava/lang/Object;)V
    //   384: getstatic net/minecraft/class_293$class_5596.field_29344 : Lnet/minecraft/class_293$class_5596;
    //   387: getstatic net/minecraft/class_290.field_1576 : Lnet/minecraft/class_293;
    //   390: <illegal opcode> get : ()Ljava/util/function/Supplier;
    //   395: lload #10
    //   397: dup2_x1
    //   398: pop2
    //   399: aload #13
    //   401: fload #18
    //   403: fload #19
    //   405: fload #20
    //   407: fload #28
    //   409: fload #29
    //   411: fload #30
    //   413: fload #31
    //   415: fload #23
    //   417: fload #21
    //   419: fload #22
    //   421: <illegal opcode> accept : (Lorg/joml/Matrix4f;FFFFFFFFFF)Ljava/util/function/Consumer;
    //   426: iconst_5
    //   427: anewarray java/lang/Object
    //   430: dup_x1
    //   431: swap
    //   432: iconst_4
    //   433: swap
    //   434: aastore
    //   435: dup_x1
    //   436: swap
    //   437: iconst_3
    //   438: swap
    //   439: aastore
    //   440: dup_x2
    //   441: dup_x2
    //   442: pop
    //   443: invokestatic valueOf : (J)Ljava/lang/Long;
    //   446: iconst_2
    //   447: swap
    //   448: aastore
    //   449: dup_x1
    //   450: swap
    //   451: iconst_1
    //   452: swap
    //   453: aastore
    //   454: dup_x1
    //   455: swap
    //   456: iconst_0
    //   457: swap
    //   458: aastore
    //   459: invokestatic w : ([Ljava/lang/Object;)V
    //   462: iload #12
    //   464: ifeq -> 481
    //   467: iconst_1
    //   468: anewarray wtf/opal/d
    //   471: invokestatic p : ([Lwtf/opal/d;)V
    //   474: goto -> 481
    //   477: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   480: athrow
    //   481: return
    // Exception table:
    //   from	to	target	type
    //   306	474	477	wtf/opal/x5
  }
  
  private static void m(Object[] paramArrayOfObject) {
    RenderSystem.enableCull();
    RenderSystem.disableBlend();
  }
  
  private static void w(Object[] paramArrayOfObject) {
    class_293.class_5596 class_5596 = (class_293.class_5596)paramArrayOfObject[0];
    class_293 class_293 = (class_293)paramArrayOfObject[1];
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    Supplier supplier = (Supplier)paramArrayOfObject[3];
    Consumer<class_287> consumer = (Consumer)paramArrayOfObject[4];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x27A8E8A16D4AL;
    class_289 class_289 = class_289.method_1348();
    class_287 class_287 = class_289.method_1349();
    class_287.method_1328(class_5596, class_293);
    consumer.accept(class_287);
    new Object[1];
    K(new Object[] { Long.valueOf(l2) });
    RenderSystem.setShader(supplier);
    bk.t(new Object[] { class_287 });
    m(new Object[0]);
  }
  
  private static void K(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/bc.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: invokestatic P : ()Z
    //   21: invokestatic enableBlend : ()V
    //   24: istore_3
    //   25: fconst_1
    //   26: fconst_1
    //   27: fconst_1
    //   28: fconst_1
    //   29: invokestatic setShaderColor : (FFFF)V
    //   32: invokestatic enableDepthTest : ()V
    //   35: getstatic wtf/opal/bc.U : Z
    //   38: iload_3
    //   39: ifeq -> 65
    //   42: ifeq -> 68
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: sipush #31583
    //   55: ldc2_w 3958350471246545136
    //   58: lload_1
    //   59: lxor
    //   60: <illegal opcode> q : (IJ)I
    //   65: goto -> 81
    //   68: sipush #5628
    //   71: ldc2_w 8114383273555357266
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> q : (IJ)I
    //   81: invokestatic depthFunc : (I)V
    //   84: return
    // Exception table:
    //   from	to	target	type
    //   25	45	48	wtf/opal/x5
  }
  
  private static void lambda$renderEdged$1(Matrix4f paramMatrix4f, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, class_287 paramclass_287) {
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
  }
  
  private static void lambda$renderEdged$0(Matrix4f paramMatrix4f, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, class_287 paramclass_287) {
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat2, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat3).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat9, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
    paramclass_287.method_22918(paramMatrix4f, paramFloat1, paramFloat10, paramFloat8).method_22915(paramFloat4, paramFloat5, paramFloat6, paramFloat7).method_1344();
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 4821772449952637617
    //   3: ldc2_w -8884678968015929128
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 84707108443460
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/bc.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/bc.d : Ljava/util/Map;
    //   38: getstatic wtf/opal/bc.a : J
    //   41: ldc2_w 13510402147602
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
    //   127: iconst_2
    //   128: newarray long
    //   130: astore #8
    //   132: iconst_0
    //   133: istore #5
    //   135: ldc_w 'qbE¸ÂÃ'¾KWn@á©'
    //   138: dup
    //   139: astore #6
    //   141: invokevirtual length : ()I
    //   144: istore #7
    //   146: iconst_0
    //   147: istore #4
    //   149: aload #6
    //   151: iload #4
    //   153: iinc #4, 8
    //   156: iload #4
    //   158: invokevirtual substring : (II)Ljava/lang/String;
    //   161: ldc_w 'ISO-8859-1'
    //   164: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   167: astore #9
    //   169: aload #8
    //   171: iload #5
    //   173: iinc #5, 1
    //   176: aload #9
    //   178: iconst_0
    //   179: baload
    //   180: i2l
    //   181: ldc2_w 255
    //   184: land
    //   185: bipush #56
    //   187: lshl
    //   188: aload #9
    //   190: iconst_1
    //   191: baload
    //   192: i2l
    //   193: ldc2_w 255
    //   196: land
    //   197: bipush #48
    //   199: lshl
    //   200: lor
    //   201: aload #9
    //   203: iconst_2
    //   204: baload
    //   205: i2l
    //   206: ldc2_w 255
    //   209: land
    //   210: bipush #40
    //   212: lshl
    //   213: lor
    //   214: aload #9
    //   216: iconst_3
    //   217: baload
    //   218: i2l
    //   219: ldc2_w 255
    //   222: land
    //   223: bipush #32
    //   225: lshl
    //   226: lor
    //   227: aload #9
    //   229: iconst_4
    //   230: baload
    //   231: i2l
    //   232: ldc2_w 255
    //   235: land
    //   236: bipush #24
    //   238: lshl
    //   239: lor
    //   240: aload #9
    //   242: iconst_5
    //   243: baload
    //   244: i2l
    //   245: ldc2_w 255
    //   248: land
    //   249: bipush #16
    //   251: lshl
    //   252: lor
    //   253: aload #9
    //   255: bipush #6
    //   257: baload
    //   258: i2l
    //   259: ldc2_w 255
    //   262: land
    //   263: bipush #8
    //   265: lshl
    //   266: lor
    //   267: aload #9
    //   269: bipush #7
    //   271: baload
    //   272: i2l
    //   273: ldc2_w 255
    //   276: land
    //   277: lor
    //   278: iconst_m1
    //   279: goto -> 305
    //   282: lastore
    //   283: iload #4
    //   285: iload #7
    //   287: if_icmplt -> 149
    //   290: aload #8
    //   292: putstatic wtf/opal/bc.b : [J
    //   295: iconst_2
    //   296: anewarray java/lang/Integer
    //   299: putstatic wtf/opal/bc.c : [Ljava/lang/Integer;
    //   302: goto -> 507
    //   305: dup_x2
    //   306: pop
    //   307: lstore #10
    //   309: bipush #8
    //   311: newarray byte
    //   313: dup
    //   314: iconst_0
    //   315: lload #10
    //   317: bipush #56
    //   319: lushr
    //   320: l2i
    //   321: i2b
    //   322: bastore
    //   323: dup
    //   324: iconst_1
    //   325: lload #10
    //   327: bipush #48
    //   329: lushr
    //   330: l2i
    //   331: i2b
    //   332: bastore
    //   333: dup
    //   334: iconst_2
    //   335: lload #10
    //   337: bipush #40
    //   339: lushr
    //   340: l2i
    //   341: i2b
    //   342: bastore
    //   343: dup
    //   344: iconst_3
    //   345: lload #10
    //   347: bipush #32
    //   349: lushr
    //   350: l2i
    //   351: i2b
    //   352: bastore
    //   353: dup
    //   354: iconst_4
    //   355: lload #10
    //   357: bipush #24
    //   359: lushr
    //   360: l2i
    //   361: i2b
    //   362: bastore
    //   363: dup
    //   364: iconst_5
    //   365: lload #10
    //   367: bipush #16
    //   369: lushr
    //   370: l2i
    //   371: i2b
    //   372: bastore
    //   373: dup
    //   374: bipush #6
    //   376: lload #10
    //   378: bipush #8
    //   380: lushr
    //   381: l2i
    //   382: i2b
    //   383: bastore
    //   384: dup
    //   385: bipush #7
    //   387: lload #10
    //   389: l2i
    //   390: i2b
    //   391: bastore
    //   392: aload_2
    //   393: swap
    //   394: invokevirtual doFinal : ([B)[B
    //   397: astore #12
    //   399: aload #12
    //   401: iconst_0
    //   402: baload
    //   403: i2l
    //   404: ldc2_w 255
    //   407: land
    //   408: bipush #56
    //   410: lshl
    //   411: aload #12
    //   413: iconst_1
    //   414: baload
    //   415: i2l
    //   416: ldc2_w 255
    //   419: land
    //   420: bipush #48
    //   422: lshl
    //   423: lor
    //   424: aload #12
    //   426: iconst_2
    //   427: baload
    //   428: i2l
    //   429: ldc2_w 255
    //   432: land
    //   433: bipush #40
    //   435: lshl
    //   436: lor
    //   437: aload #12
    //   439: iconst_3
    //   440: baload
    //   441: i2l
    //   442: ldc2_w 255
    //   445: land
    //   446: bipush #32
    //   448: lshl
    //   449: lor
    //   450: aload #12
    //   452: iconst_4
    //   453: baload
    //   454: i2l
    //   455: ldc2_w 255
    //   458: land
    //   459: bipush #24
    //   461: lshl
    //   462: lor
    //   463: aload #12
    //   465: iconst_5
    //   466: baload
    //   467: i2l
    //   468: ldc2_w 255
    //   471: land
    //   472: bipush #16
    //   474: lshl
    //   475: lor
    //   476: aload #12
    //   478: bipush #6
    //   480: baload
    //   481: i2l
    //   482: ldc2_w 255
    //   485: land
    //   486: bipush #8
    //   488: lshl
    //   489: lor
    //   490: aload #12
    //   492: bipush #7
    //   494: baload
    //   495: i2l
    //   496: ldc2_w 255
    //   499: land
    //   500: lor
    //   501: dup2_x1
    //   502: pop2
    //   503: pop
    //   504: goto -> 282
    //   507: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x153A;
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
        throw new RuntimeException("wtf/opal/bc", exception);
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
    //   65: ldc_w 'wtf/opal/bc'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */