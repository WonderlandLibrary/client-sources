package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1294;
import net.minecraft.class_243;
import net.minecraft.class_5611;

public final class l_ {
  private static String[] O;
  
  private static final long a = on.a(-2210318694231107809L, 60543408075252923L, MethodHandles.lookup().lookupClass()).a(232749627800210L);
  
  public static double z(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x6B09B4136D5DL;
    (new Object[2])[1] = Float.valueOf(b9.c.field_1724.method_36454());
    new Object[2];
    return Q(new Object[] { Long.valueOf(l2) });
  }
  
  public static double Q(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Float
    //   17: invokevirtual floatValue : ()F
    //   20: fstore_1
    //   21: pop
    //   22: getstatic wtf/opal/l_.a : J
    //   25: lload_2
    //   26: lxor
    //   27: lstore_2
    //   28: invokestatic B : ()[Ljava/lang/String;
    //   31: astore #4
    //   33: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   36: getfield field_1724 : Lnet/minecraft/class_746;
    //   39: getfield field_6250 : F
    //   42: aload #4
    //   44: ifnull -> 65
    //   47: fconst_0
    //   48: fcmpg
    //   49: ifge -> 64
    //   52: goto -> 59
    //   55: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: fload_1
    //   60: ldc 180.0
    //   62: fadd
    //   63: fstore_1
    //   64: fconst_1
    //   65: fstore #5
    //   67: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   70: getfield field_1724 : Lnet/minecraft/class_746;
    //   73: getfield field_6250 : F
    //   76: fconst_0
    //   77: fcmpg
    //   78: aload #4
    //   80: lload_2
    //   81: lconst_0
    //   82: lcmp
    //   83: iflt -> 134
    //   86: ifnull -> 132
    //   89: ifge -> 114
    //   92: goto -> 99
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: ldc -0.5
    //   101: lload_2
    //   102: lconst_0
    //   103: lcmp
    //   104: iflt -> 166
    //   107: fstore #5
    //   109: aload #4
    //   111: ifnonnull -> 157
    //   114: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   117: getfield field_1724 : Lnet/minecraft/class_746;
    //   120: getfield field_6250 : F
    //   123: fconst_0
    //   124: fcmpl
    //   125: goto -> 132
    //   128: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   131: athrow
    //   132: aload #4
    //   134: lload_2
    //   135: lconst_0
    //   136: lcmp
    //   137: ifle -> 170
    //   140: ifnull -> 168
    //   143: ifle -> 157
    //   146: goto -> 153
    //   149: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   152: athrow
    //   153: ldc 0.5
    //   155: fstore #5
    //   157: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   160: getfield field_1724 : Lnet/minecraft/class_746;
    //   163: getfield field_6212 : F
    //   166: fconst_0
    //   167: fcmpl
    //   168: aload #4
    //   170: ifnull -> 232
    //   173: ifle -> 202
    //   176: goto -> 183
    //   179: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   182: athrow
    //   183: fload_1
    //   184: ldc 90.0
    //   186: fload #5
    //   188: fmul
    //   189: fsub
    //   190: fstore_1
    //   191: lload_2
    //   192: lconst_0
    //   193: lcmp
    //   194: iflt -> 202
    //   197: aload #4
    //   199: ifnonnull -> 243
    //   202: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   205: getfield field_1724 : Lnet/minecraft/class_746;
    //   208: getfield field_6212 : F
    //   211: aload #4
    //   213: ifnull -> 244
    //   216: goto -> 223
    //   219: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   222: athrow
    //   223: fconst_0
    //   224: fcmpg
    //   225: goto -> 232
    //   228: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   231: athrow
    //   232: ifge -> 243
    //   235: fload_1
    //   236: ldc 90.0
    //   238: fload #5
    //   240: fmul
    //   241: fadd
    //   242: fstore_1
    //   243: fload_1
    //   244: f2d
    //   245: invokestatic toRadians : (D)D
    //   248: dreturn
    // Exception table:
    //   from	to	target	type
    //   33	52	55	wtf/opal/x5
    //   67	92	95	wtf/opal/x5
    //   109	125	128	wtf/opal/x5
    //   132	146	149	wtf/opal/x5
    //   168	176	179	wtf/opal/x5
    //   191	216	219	wtf/opal/x5
    //   202	225	228	wtf/opal/x5
  }
  
  public static double y(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Float
    //   7: invokevirtual floatValue : ()F
    //   10: fstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore_2
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast java/lang/Double
    //   27: invokevirtual doubleValue : ()D
    //   30: dstore #4
    //   32: dup
    //   33: iconst_3
    //   34: aaload
    //   35: checkcast java/lang/Long
    //   38: invokevirtual longValue : ()J
    //   41: lstore #6
    //   43: pop
    //   44: getstatic wtf/opal/l_.a : J
    //   47: lload #6
    //   49: lxor
    //   50: lstore #6
    //   52: invokestatic B : ()[Ljava/lang/String;
    //   55: astore #8
    //   57: dload_2
    //   58: dconst_0
    //   59: dcmpg
    //   60: ifge -> 68
    //   63: fload_1
    //   64: ldc 180.0
    //   66: fadd
    //   67: fstore_1
    //   68: fconst_1
    //   69: fstore #9
    //   71: dload_2
    //   72: dconst_0
    //   73: dcmpg
    //   74: aload #8
    //   76: lload #6
    //   78: lconst_0
    //   79: lcmp
    //   80: iflt -> 117
    //   83: ifnull -> 115
    //   86: ifge -> 105
    //   89: goto -> 96
    //   92: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   95: athrow
    //   96: ldc -0.5
    //   98: fstore #9
    //   100: aload #8
    //   102: ifnonnull -> 141
    //   105: dload_2
    //   106: dconst_0
    //   107: dcmpl
    //   108: goto -> 115
    //   111: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: aload #8
    //   117: lload #6
    //   119: lconst_0
    //   120: lcmp
    //   121: iflt -> 147
    //   124: ifnull -> 145
    //   127: ifle -> 141
    //   130: goto -> 137
    //   133: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   136: athrow
    //   137: ldc 0.5
    //   139: fstore #9
    //   141: dload #4
    //   143: dconst_0
    //   144: dcmpl
    //   145: aload #8
    //   147: ifnull -> 184
    //   150: ifle -> 168
    //   153: goto -> 160
    //   156: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   159: athrow
    //   160: fload_1
    //   161: ldc 90.0
    //   163: fload #9
    //   165: fmul
    //   166: fsub
    //   167: fstore_1
    //   168: dload #4
    //   170: aload #8
    //   172: ifnull -> 200
    //   175: dconst_0
    //   176: dcmpg
    //   177: goto -> 184
    //   180: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   183: athrow
    //   184: ifge -> 195
    //   187: fload_1
    //   188: ldc 90.0
    //   190: fload #9
    //   192: fmul
    //   193: fadd
    //   194: fstore_1
    //   195: fload_1
    //   196: f2d
    //   197: invokestatic toRadians : (D)D
    //   200: dreturn
    // Exception table:
    //   from	to	target	type
    //   71	89	92	wtf/opal/x5
    //   100	108	111	wtf/opal/x5
    //   115	130	133	wtf/opal/x5
    //   145	153	156	wtf/opal/x5
    //   168	177	180	wtf/opal/x5
  }
  
  public static void k(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore_3
    //   21: pop
    //   22: getstatic wtf/opal/l_.a : J
    //   25: lload_1
    //   26: lxor
    //   27: lstore_1
    //   28: lload_1
    //   29: dup2
    //   30: ldc2_w 132857575242000
    //   33: lxor
    //   34: lstore #5
    //   36: pop2
    //   37: invokestatic B : ()[Ljava/lang/String;
    //   40: iconst_0
    //   41: anewarray java/lang/Object
    //   44: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   47: iconst_0
    //   48: anewarray java/lang/Object
    //   51: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   54: ldc wtf/opal/jn
    //   56: iconst_1
    //   57: anewarray java/lang/Object
    //   60: dup_x1
    //   61: swap
    //   62: iconst_0
    //   63: swap
    //   64: aastore
    //   65: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   68: checkcast wtf/opal/jn
    //   71: astore #8
    //   73: astore #7
    //   75: aload #8
    //   77: iconst_0
    //   78: anewarray java/lang/Object
    //   81: invokevirtual D : ([Ljava/lang/Object;)Z
    //   84: aload #7
    //   86: ifnull -> 133
    //   89: ifeq -> 148
    //   92: goto -> 99
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: aload #8
    //   101: aload #7
    //   103: lload_1
    //   104: lconst_0
    //   105: lcmp
    //   106: ifle -> 142
    //   109: ifnull -> 138
    //   112: goto -> 119
    //   115: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: iconst_0
    //   120: anewarray java/lang/Object
    //   123: invokevirtual e : ([Ljava/lang/Object;)Z
    //   126: goto -> 133
    //   129: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   132: athrow
    //   133: ifeq -> 148
    //   136: aload #8
    //   138: iconst_0
    //   139: anewarray java/lang/Object
    //   142: invokevirtual Z : ([Ljava/lang/Object;)F
    //   145: goto -> 157
    //   148: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   151: getfield field_1724 : Lnet/minecraft/class_746;
    //   154: invokevirtual method_36454 : ()F
    //   157: lload #5
    //   159: dup2_x1
    //   160: pop2
    //   161: iconst_2
    //   162: anewarray java/lang/Object
    //   165: dup_x1
    //   166: swap
    //   167: invokestatic valueOf : (F)Ljava/lang/Float;
    //   170: iconst_1
    //   171: swap
    //   172: aastore
    //   173: dup_x2
    //   174: dup_x2
    //   175: pop
    //   176: invokestatic valueOf : (J)Ljava/lang/Long;
    //   179: iconst_0
    //   180: swap
    //   181: aastore
    //   182: invokestatic Q : ([Ljava/lang/Object;)D
    //   185: dstore #9
    //   187: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   190: getfield field_1724 : Lnet/minecraft/class_746;
    //   193: dload #9
    //   195: d2f
    //   196: invokestatic method_15374 : (F)F
    //   199: fneg
    //   200: f2d
    //   201: dload_3
    //   202: dmul
    //   203: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   206: getfield field_1724 : Lnet/minecraft/class_746;
    //   209: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   212: invokevirtual method_10214 : ()D
    //   215: dload #9
    //   217: d2f
    //   218: invokestatic method_15362 : (F)F
    //   221: f2d
    //   222: dload_3
    //   223: dmul
    //   224: invokevirtual method_18800 : (DDD)V
    //   227: return
    // Exception table:
    //   from	to	target	type
    //   75	92	95	wtf/opal/x5
    //   89	112	115	wtf/opal/x5
    //   99	126	129	wtf/opal/x5
  }
  
  public static class_243 H(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore_3
    //   21: pop
    //   22: getstatic wtf/opal/l_.a : J
    //   25: lload_1
    //   26: lxor
    //   27: lstore_1
    //   28: lload_1
    //   29: dup2
    //   30: ldc2_w 68847105919394
    //   33: lxor
    //   34: lstore #5
    //   36: pop2
    //   37: invokestatic B : ()[Ljava/lang/String;
    //   40: iconst_0
    //   41: anewarray java/lang/Object
    //   44: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   47: iconst_0
    //   48: anewarray java/lang/Object
    //   51: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   54: ldc wtf/opal/jn
    //   56: iconst_1
    //   57: anewarray java/lang/Object
    //   60: dup_x1
    //   61: swap
    //   62: iconst_0
    //   63: swap
    //   64: aastore
    //   65: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   68: checkcast wtf/opal/jn
    //   71: astore #8
    //   73: astore #7
    //   75: aload #8
    //   77: iconst_0
    //   78: anewarray java/lang/Object
    //   81: invokevirtual D : ([Ljava/lang/Object;)Z
    //   84: aload #7
    //   86: ifnull -> 133
    //   89: ifeq -> 148
    //   92: goto -> 99
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: aload #8
    //   101: aload #7
    //   103: lload_1
    //   104: lconst_0
    //   105: lcmp
    //   106: ifle -> 142
    //   109: ifnull -> 138
    //   112: goto -> 119
    //   115: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: iconst_0
    //   120: anewarray java/lang/Object
    //   123: invokevirtual e : ([Ljava/lang/Object;)Z
    //   126: goto -> 133
    //   129: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   132: athrow
    //   133: ifeq -> 148
    //   136: aload #8
    //   138: iconst_0
    //   139: anewarray java/lang/Object
    //   142: invokevirtual Z : ([Ljava/lang/Object;)F
    //   145: goto -> 157
    //   148: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   151: getfield field_1724 : Lnet/minecraft/class_746;
    //   154: invokevirtual method_36454 : ()F
    //   157: lload #5
    //   159: dup2_x1
    //   160: pop2
    //   161: iconst_2
    //   162: anewarray java/lang/Object
    //   165: dup_x1
    //   166: swap
    //   167: invokestatic valueOf : (F)Ljava/lang/Float;
    //   170: iconst_1
    //   171: swap
    //   172: aastore
    //   173: dup_x2
    //   174: dup_x2
    //   175: pop
    //   176: invokestatic valueOf : (J)Ljava/lang/Long;
    //   179: iconst_0
    //   180: swap
    //   181: aastore
    //   182: invokestatic Q : ([Ljava/lang/Object;)D
    //   185: dstore #9
    //   187: new net/minecraft/class_243
    //   190: dup
    //   191: dload #9
    //   193: d2f
    //   194: invokestatic method_15374 : (F)F
    //   197: fneg
    //   198: f2d
    //   199: dload_3
    //   200: dmul
    //   201: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   204: getfield field_1724 : Lnet/minecraft/class_746;
    //   207: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   210: invokevirtual method_10214 : ()D
    //   213: dload #9
    //   215: d2f
    //   216: invokestatic method_15362 : (F)F
    //   219: f2d
    //   220: dload_3
    //   221: dmul
    //   222: invokespecial <init> : (DDD)V
    //   225: areturn
    // Exception table:
    //   from	to	target	type
    //   75	92	95	wtf/opal/x5
    //   89	112	115	wtf/opal/x5
    //   99	126	129	wtf/opal/x5
  }
  
  public static double m(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    double d1 = 0.48D;
    String[] arrayOfString = B();
    try {
      if (arrayOfString != null)
        try {
          if (b9.c.field_1724.method_6059(class_1294.field_5904)) {
          
          } else {
            return d1;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    double d2 = (b9.c.field_1724.method_6059(class_1294.field_5904) >= true) ? 0.063D : 0.039D;
    d1 += (b9.c.field_1724.method_6112(class_1294.field_5904).method_5578() + 1) * d2;
    return d1;
  }
  
  public static void H(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    double d1 = ((Double)paramArrayOfObject[1]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[2]).doubleValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x71DCFD80D1C6L;
    long l3 = l1 ^ 0x5A5DC90D7952L;
    d2 /= 100.0D;
    d2 = Math.min(1.0D, Math.max(0.0D, d2));
    double d3 = b9.c.field_1724.method_18798().method_10216();
    double d4 = b9.c.field_1724.method_18798().method_10215();
    try {
      new Object[2];
      (new Object[2])[1] = Double.valueOf(d1);
      new Object[2];
      k(new Object[] { Long.valueOf(l3) });
      new Object[1];
      if (I(new Object[] { Long.valueOf(l2) }))
        b9.c.field_1724.method_18800(d3 + (b9.c.field_1724.method_18798().method_10216() - d3) * d2, b9.c.field_1724.method_18798().method_10214(), d4 + (b9.c.field_1724.method_18798().method_10215() - d4) * d2); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public static boolean I(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/l_.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: invokestatic B : ()[Ljava/lang/String;
    //   21: astore_3
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: getfield field_1724 : Lnet/minecraft/class_746;
    //   28: getfield field_6250 : F
    //   31: fconst_0
    //   32: fcmpl
    //   33: aload_3
    //   34: ifnull -> 80
    //   37: ifne -> 79
    //   40: goto -> 47
    //   43: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   46: athrow
    //   47: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   50: getfield field_1724 : Lnet/minecraft/class_746;
    //   53: getfield field_6212 : F
    //   56: fconst_0
    //   57: fcmpl
    //   58: aload_3
    //   59: ifnull -> 80
    //   62: goto -> 69
    //   65: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: ifeq -> 83
    //   72: goto -> 79
    //   75: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   78: athrow
    //   79: iconst_1
    //   80: goto -> 84
    //   83: iconst_0
    //   84: ireturn
    // Exception table:
    //   from	to	target	type
    //   22	40	43	wtf/opal/x5
    //   37	62	65	wtf/opal/x5
    //   47	72	75	wtf/opal/x5
  }
  
  public static float U(Object[] paramArrayOfObject) {
    return (float)Math.sqrt(b9.c.field_1724.method_18798().method_10216() * b9.c.field_1724.method_18798().method_10216() + b9.c.field_1724.method_18798().method_10215() * b9.c.field_1724.method_18798().method_10215());
  }
  
  public static float d(Object[] paramArrayOfObject) {
    class_243 class_243 = (class_243)paramArrayOfObject[0];
    return (float)Math.sqrt(class_243.method_10216() * class_243.method_10216() + class_243.method_10215() * class_243.method_10215());
  }
  
  public static float E(Object[] paramArrayOfObject) {
    return (float)Math.sqrt(Math.abs(b9.c.field_1724.method_18798().method_10216()) * Math.abs(b9.c.field_1724.method_18798().method_10216()) + Math.abs(b9.c.field_1724.method_18798().method_10215()) * Math.abs(b9.c.field_1724.method_18798().method_10215()));
  }
  
  public static float T(Object[] paramArrayOfObject) {
    class_5611 class_56111 = new class_5611((float)b9.c.field_1724.field_6014, (float)b9.c.field_1724.field_5969);
    class_5611 class_56112 = new class_5611((float)b9.c.field_1724.method_23317(), (float)b9.c.field_1724.method_23321());
    class_5611 class_56113 = new class_5611(class_56112.method_32118() - class_56111.method_32118(), class_56112.method_32119() - class_56111.method_32119());
    return (float)Math.toDegrees((Math.atan2(-class_56113.method_32118(), class_56113.method_32119()) + 6.2831854820251465D) % 6.2831854820251465D);
  }
  
  public static double H(Object[] paramArrayOfObject) {
    double d = Math.hypot(b9.c.field_1724.method_23317() - b9.c.field_1724.field_6014, b9.c.field_1724.method_23321() - b9.c.field_1724.field_5969) * d1.q(new Object[0]).l(new Object[0]).x(new Object[0]) * 20.0D;
    return Math.round(d * 100.0D) / 100.0D;
  }
  
  public static boolean B(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/l_.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: invokestatic B : ()[Ljava/lang/String;
    //   21: astore_3
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: getfield field_1724 : Lnet/minecraft/class_746;
    //   28: invokevirtual method_5799 : ()Z
    //   31: aload_3
    //   32: ifnull -> 76
    //   35: ifne -> 75
    //   38: goto -> 45
    //   41: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   48: getfield field_1724 : Lnet/minecraft/class_746;
    //   51: invokevirtual method_5771 : ()Z
    //   54: aload_3
    //   55: ifnull -> 76
    //   58: goto -> 65
    //   61: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: ifeq -> 79
    //   68: goto -> 75
    //   71: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: iconst_1
    //   76: goto -> 80
    //   79: iconst_0
    //   80: ireturn
    // Exception table:
    //   from	to	target	type
    //   22	38	41	wtf/opal/x5
    //   35	58	61	wtf/opal/x5
    //   45	68	71	wtf/opal/x5
  }
  
  public static void q(String[] paramArrayOfString) {
    O = paramArrayOfString;
  }
  
  public static String[] B() {
    return O;
  }
  
  static {
    if (B() == null)
      q(new String[3]); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\l_.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */