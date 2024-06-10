package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1309;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import org.joml.Matrix4f;
import org.joml.Vector4d;
import wtf.opal.mixin.GameRendererAccessor;

public class p6 {
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  public static Vector4d n(Object[] paramArrayOfObject) {
    class_1309 class_1309 = (class_1309)paramArrayOfObject[0];
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x76E9FF4D0416L;
    long l3 = l1 ^ 0x79F124753A1FL;
    int[] arrayOfInt = { 0, 0, b9.c.method_22683().method_4489(), b9.c.method_22683().method_4506() };
    (new Object[2])[1] = Float.valueOf(f1);
    new Object[2];
    class_4587 class_4587 = X(new Object[] { Long.valueOf(l2) });
    Matrix4f matrix4f = class_4587.method_23760().method_23761();
    class_243 class_243 = x1.F(new Object[] { null, Float.valueOf(f1), class_1309 });
    float f2 = class_1309.method_17681() / 2.0F;
    String[] arrayOfString = l_.B();
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    float f3 = class_1309.method_17682() + (class_1309.method_5715() ? 0.1F : 0.2F);
    class_238 class_238 = new class_238(class_243.field_1352 - f2, class_243.field_1351, class_243.field_1350 - f2, class_243.field_1352 + f2, class_243.field_1351 + f3, class_243.field_1350 + f2);
    new Object[4];
    Vector4d vector4d = S(new Object[] { null, null, null, Long.valueOf(l3), class_238, matrix4f, arrayOfInt });
    try {
      if (l1 > 0L && arrayOfString != null) {
        try {
          if (vector4d == null)
            return null; 
        } catch (x5 x5) {
          throw a(null);
        } 
        vector4d.div(b9.c.method_22683().method_4495());
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (l1 > 0L && d.D() != null)
        l_.q(new String[4]); 
    } catch (x5 x5) {
      throw a(null);
    } 
    return vector4d;
  }
  
  public static Vector4d S(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast [I
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast org/joml/Matrix4f
    //   14: astore_1
    //   15: dup
    //   16: iconst_2
    //   17: aaload
    //   18: checkcast net/minecraft/class_238
    //   21: astore_2
    //   22: dup
    //   23: iconst_3
    //   24: aaload
    //   25: checkcast java/lang/Long
    //   28: invokevirtual longValue : ()J
    //   31: lstore #4
    //   33: pop
    //   34: getstatic wtf/opal/p6.a : J
    //   37: lload #4
    //   39: lxor
    //   40: lstore #4
    //   42: lload #4
    //   44: dup2
    //   45: ldc2_w 77669684007674
    //   48: lxor
    //   49: dup2
    //   50: bipush #32
    //   52: lushr
    //   53: l2i
    //   54: istore #6
    //   56: dup2
    //   57: bipush #32
    //   59: lshl
    //   60: bipush #48
    //   62: lushr
    //   63: l2i
    //   64: istore #7
    //   66: dup2
    //   67: bipush #48
    //   69: lshl
    //   70: bipush #48
    //   72: lushr
    //   73: l2i
    //   74: istore #8
    //   76: pop2
    //   77: pop2
    //   78: invokestatic B : ()[Ljava/lang/String;
    //   81: new org/joml/Vector4f
    //   84: dup
    //   85: invokespecial <init> : ()V
    //   88: astore #10
    //   90: iload #6
    //   92: aload_2
    //   93: iload #7
    //   95: i2s
    //   96: iload #8
    //   98: iconst_4
    //   99: anewarray java/lang/Object
    //   102: dup_x1
    //   103: swap
    //   104: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   107: iconst_3
    //   108: swap
    //   109: aastore
    //   110: dup_x1
    //   111: swap
    //   112: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   115: iconst_2
    //   116: swap
    //   117: aastore
    //   118: dup_x1
    //   119: swap
    //   120: iconst_1
    //   121: swap
    //   122: aastore
    //   123: dup_x1
    //   124: swap
    //   125: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   128: iconst_0
    //   129: swap
    //   130: aastore
    //   131: invokestatic D : ([Ljava/lang/Object;)Ljava/util/List;
    //   134: astore #11
    //   136: astore #9
    //   138: aconst_null
    //   139: astore #12
    //   141: aload #11
    //   143: invokeinterface iterator : ()Ljava/util/Iterator;
    //   148: astore #13
    //   150: aload #13
    //   152: invokeinterface hasNext : ()Z
    //   157: ifeq -> 383
    //   160: aload #13
    //   162: invokeinterface next : ()Ljava/lang/Object;
    //   167: checkcast net/minecraft/class_243
    //   170: astore #14
    //   172: aload_1
    //   173: aload #14
    //   175: invokevirtual method_46409 : ()Lorg/joml/Vector3f;
    //   178: aload_3
    //   179: aload #10
    //   181: invokevirtual project : (Lorg/joml/Vector3fc;[ILorg/joml/Vector4f;)Lorg/joml/Vector4f;
    //   184: pop
    //   185: aload #10
    //   187: aload_3
    //   188: iconst_3
    //   189: iaload
    //   190: i2f
    //   191: aload #10
    //   193: getfield y : F
    //   196: fsub
    //   197: putfield y : F
    //   200: lload #4
    //   202: lconst_0
    //   203: lcmp
    //   204: ifle -> 229
    //   207: aload #10
    //   209: getfield w : F
    //   212: fconst_1
    //   213: fcmpl
    //   214: ifeq -> 229
    //   217: aload #9
    //   219: ifnonnull -> 383
    //   222: goto -> 229
    //   225: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   228: athrow
    //   229: lload #4
    //   231: lconst_0
    //   232: lcmp
    //   233: iflt -> 290
    //   236: aload #12
    //   238: aload #9
    //   240: ifnull -> 288
    //   243: goto -> 250
    //   246: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   249: athrow
    //   250: ifnonnull -> 302
    //   253: goto -> 260
    //   256: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   259: athrow
    //   260: new org/joml/Vector4d
    //   263: dup
    //   264: aload #10
    //   266: getfield x : F
    //   269: f2d
    //   270: aload #10
    //   272: getfield y : F
    //   275: f2d
    //   276: dconst_0
    //   277: dconst_0
    //   278: invokespecial <init> : (DDDD)V
    //   281: goto -> 288
    //   284: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   287: athrow
    //   288: astore #12
    //   290: aload #9
    //   292: lload #4
    //   294: lconst_0
    //   295: lcmp
    //   296: ifle -> 380
    //   299: ifnonnull -> 378
    //   302: aload #10
    //   304: getfield x : F
    //   307: f2d
    //   308: dstore #15
    //   310: aload #10
    //   312: getfield y : F
    //   315: f2d
    //   316: dstore #17
    //   318: aload #12
    //   320: dload #15
    //   322: aload #12
    //   324: getfield x : D
    //   327: invokestatic min : (DD)D
    //   330: putfield x : D
    //   333: aload #12
    //   335: dload #17
    //   337: aload #12
    //   339: getfield y : D
    //   342: invokestatic min : (DD)D
    //   345: putfield y : D
    //   348: aload #12
    //   350: dload #15
    //   352: aload #12
    //   354: getfield z : D
    //   357: invokestatic max : (DD)D
    //   360: putfield z : D
    //   363: aload #12
    //   365: dload #17
    //   367: aload #12
    //   369: getfield w : D
    //   372: invokestatic max : (DD)D
    //   375: putfield w : D
    //   378: aload #9
    //   380: ifnonnull -> 150
    //   383: aload #12
    //   385: lload #4
    //   387: lconst_0
    //   388: lcmp
    //   389: iflt -> 167
    //   392: areturn
    // Exception table:
    //   from	to	target	type
    //   172	222	225	wtf/opal/x5
    //   217	243	246	wtf/opal/x5
    //   229	253	256	wtf/opal/x5
    //   250	281	284	wtf/opal/x5
  }
  
  public static class_4587 X(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    l = a ^ l;
    GameRendererAccessor gameRendererAccessor = (GameRendererAccessor)b9.c.field_1773;
    class_4587 class_4587 = new class_4587();
    class_4184 class_4184 = b9.c.field_1773.method_19418();
    String[] arrayOfString = l_.B();
    float f2 = (float)gameRendererAccessor.callGetFov(class_4184, f1, true);
    try {
      class_4587.method_34425(b9.c.field_1773.method_22973(f2));
      gameRendererAccessor.callTiltViewWhenHurt(class_4587, class_4184.method_55437());
      if (arrayOfString != null) {
        try {
          if (((Boolean)b9.c.field_1690.method_42448().method_41753()).booleanValue())
            gameRendererAccessor.callBobView(class_4587, class_4184.method_55437()); 
        } catch (x5 x5) {
          throw a(null);
        } 
        class_4587.method_22907(class_7833.field_40714.rotationDegrees(class_4184.method_19329()));
        class_4587.method_22907(class_7833.field_40716.rotationDegrees(class_4184.method_19330() + 180.0F));
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return class_4587;
  }
  
  public static List D(Object[] paramArrayOfObject) {
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
    //   14: checkcast net/minecraft/class_238
    //   17: astore_1
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast java/lang/Integer
    //   24: invokevirtual intValue : ()I
    //   27: istore_2
    //   28: dup
    //   29: iconst_3
    //   30: aaload
    //   31: checkcast java/lang/Integer
    //   34: invokevirtual intValue : ()I
    //   37: istore #4
    //   39: pop
    //   40: iload_3
    //   41: i2l
    //   42: bipush #32
    //   44: lshl
    //   45: iload_2
    //   46: i2l
    //   47: bipush #48
    //   49: lshl
    //   50: bipush #32
    //   52: lushr
    //   53: lor
    //   54: iload #4
    //   56: i2l
    //   57: bipush #48
    //   59: lshl
    //   60: bipush #48
    //   62: lushr
    //   63: lor
    //   64: getstatic wtf/opal/p6.a : J
    //   67: lxor
    //   68: lstore #5
    //   70: invokestatic B : ()[Ljava/lang/String;
    //   73: astore #7
    //   75: sipush #20014
    //   78: ldc2_w 6541057986051342539
    //   81: lload #5
    //   83: lxor
    //   84: <illegal opcode> o : (IJ)I
    //   89: anewarray net/minecraft/class_243
    //   92: dup
    //   93: iconst_0
    //   94: new net/minecraft/class_243
    //   97: dup
    //   98: aload_1
    //   99: getfield field_1323 : D
    //   102: aload_1
    //   103: getfield field_1322 : D
    //   106: aload_1
    //   107: getfield field_1321 : D
    //   110: invokespecial <init> : (DDD)V
    //   113: aastore
    //   114: dup
    //   115: iconst_1
    //   116: new net/minecraft/class_243
    //   119: dup
    //   120: aload_1
    //   121: getfield field_1323 : D
    //   124: aload_1
    //   125: getfield field_1325 : D
    //   128: aload_1
    //   129: getfield field_1321 : D
    //   132: invokespecial <init> : (DDD)V
    //   135: aastore
    //   136: dup
    //   137: iconst_2
    //   138: new net/minecraft/class_243
    //   141: dup
    //   142: aload_1
    //   143: getfield field_1320 : D
    //   146: aload_1
    //   147: getfield field_1322 : D
    //   150: aload_1
    //   151: getfield field_1321 : D
    //   154: invokespecial <init> : (DDD)V
    //   157: aastore
    //   158: dup
    //   159: iconst_3
    //   160: new net/minecraft/class_243
    //   163: dup
    //   164: aload_1
    //   165: getfield field_1320 : D
    //   168: aload_1
    //   169: getfield field_1325 : D
    //   172: aload_1
    //   173: getfield field_1321 : D
    //   176: invokespecial <init> : (DDD)V
    //   179: aastore
    //   180: dup
    //   181: iconst_4
    //   182: new net/minecraft/class_243
    //   185: dup
    //   186: aload_1
    //   187: getfield field_1323 : D
    //   190: aload_1
    //   191: getfield field_1322 : D
    //   194: aload_1
    //   195: getfield field_1324 : D
    //   198: invokespecial <init> : (DDD)V
    //   201: aastore
    //   202: dup
    //   203: iconst_5
    //   204: new net/minecraft/class_243
    //   207: dup
    //   208: aload_1
    //   209: getfield field_1323 : D
    //   212: aload_1
    //   213: getfield field_1325 : D
    //   216: aload_1
    //   217: getfield field_1324 : D
    //   220: invokespecial <init> : (DDD)V
    //   223: aastore
    //   224: dup
    //   225: sipush #31433
    //   228: ldc2_w 8011906775989040175
    //   231: lload #5
    //   233: lxor
    //   234: <illegal opcode> o : (IJ)I
    //   239: new net/minecraft/class_243
    //   242: dup
    //   243: aload_1
    //   244: getfield field_1320 : D
    //   247: aload_1
    //   248: getfield field_1322 : D
    //   251: aload_1
    //   252: getfield field_1324 : D
    //   255: invokespecial <init> : (DDD)V
    //   258: aastore
    //   259: dup
    //   260: sipush #3872
    //   263: ldc2_w 1357004336097051079
    //   266: lload #5
    //   268: lxor
    //   269: <illegal opcode> o : (IJ)I
    //   274: new net/minecraft/class_243
    //   277: dup
    //   278: aload_1
    //   279: getfield field_1320 : D
    //   282: aload_1
    //   283: getfield field_1325 : D
    //   286: aload_1
    //   287: getfield field_1324 : D
    //   290: invokespecial <init> : (DDD)V
    //   293: aastore
    //   294: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
    //   297: aload #7
    //   299: ifnonnull -> 316
    //   302: iconst_3
    //   303: anewarray wtf/opal/d
    //   306: invokestatic p : ([Lwtf/opal/d;)V
    //   309: goto -> 316
    //   312: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   315: athrow
    //   316: areturn
    // Exception table:
    //   from	to	target	type
    //   75	309	312	wtf/opal/x5
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 2356286416336287476
    //   3: ldc2_w 4835441471519799018
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 247722139215087
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/p6.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/p6.d : Ljava/util/Map;
    //   38: getstatic wtf/opal/p6.a : J
    //   41: ldc2_w 121032201379252
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
    //   127: iconst_3
    //   128: newarray long
    //   130: astore #8
    //   132: iconst_0
    //   133: istore #5
    //   135: ldc_w 'ÕøWÆe°äB©IG}¦íÃ_Â».'
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
    //   292: putstatic wtf/opal/p6.b : [J
    //   295: iconst_3
    //   296: anewarray java/lang/Integer
    //   299: putstatic wtf/opal/p6.c : [Ljava/lang/Integer;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1ADE;
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
        throw new RuntimeException("wtf/opal/p6", exception);
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
    //   65: ldc_w 'wtf/opal/p6'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\p6.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */