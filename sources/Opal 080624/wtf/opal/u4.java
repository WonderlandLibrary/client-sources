package wtf.opal;

import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class u4 {
  public static final kv t;
  
  public static final kv h;
  
  public static final kv s;
  
  private static final long a = on.a(7487471756155392085L, 3917784791964253146L, MethodHandles.lookup().lookupClass()).a(261963832864756L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public static kv C(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_1
    //   21: pop
    //   22: getstatic wtf/opal/u4.a : J
    //   25: lload_2
    //   26: lxor
    //   27: lstore_2
    //   28: lload_2
    //   29: dup2
    //   30: ldc2_w 63453018434980
    //   33: lxor
    //   34: lstore #4
    //   36: pop2
    //   37: new wtf/opal/k5
    //   40: dup
    //   41: iload_1
    //   42: sipush #25075
    //   45: ldc2_w 996544213726216775
    //   48: lload_2
    //   49: lxor
    //   50: <illegal opcode> j : (IJ)I
    //   55: invokestatic toString : (II)Ljava/lang/String;
    //   58: lload #4
    //   60: dup2_x1
    //   61: pop2
    //   62: invokespecial <init> : (JLjava/lang/String;)V
    //   65: areturn
  }
  
  public static kv V(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_3
    //   21: pop
    //   22: getstatic wtf/opal/u4.a : J
    //   25: lload_3
    //   26: lxor
    //   27: lstore_3
    //   28: lload_3
    //   29: dup2
    //   30: ldc2_w 65860250019307
    //   33: lxor
    //   34: lstore #5
    //   36: pop2
    //   37: new wtf/opal/k5
    //   40: dup
    //   41: lload_1
    //   42: sipush #7427
    //   45: ldc2_w 2456863507175028473
    //   48: lload_3
    //   49: lxor
    //   50: <illegal opcode> j : (IJ)I
    //   55: invokestatic toString : (JI)Ljava/lang/String;
    //   58: lload #5
    //   60: dup2_x1
    //   61: pop2
    //   62: invokespecial <init> : (JLjava/lang/String;)V
    //   65: areturn
  }
  
  public static kv T(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Float
    //   7: invokevirtual floatValue : ()F
    //   10: fstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_1
    //   21: pop
    //   22: getstatic wtf/opal/u4.a : J
    //   25: lload_1
    //   26: lxor
    //   27: lstore_1
    //   28: lload_1
    //   29: dup2
    //   30: ldc2_w 126307706921931
    //   33: lxor
    //   34: lstore #4
    //   36: dup2
    //   37: ldc2_w 39286180387204
    //   40: lxor
    //   41: lstore #6
    //   43: pop2
    //   44: invokestatic T : ()I
    //   47: istore #8
    //   49: fload_3
    //   50: invokestatic isInfinite : (F)Z
    //   53: iload #8
    //   55: ifne -> 79
    //   58: ifne -> 82
    //   61: goto -> 68
    //   64: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   67: athrow
    //   68: fload_3
    //   69: invokestatic isNaN : (F)Z
    //   72: goto -> 79
    //   75: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   78: athrow
    //   79: ifeq -> 107
    //   82: new java/lang/IllegalArgumentException
    //   85: dup
    //   86: sipush #3200
    //   89: ldc2_w 2060149577239411373
    //   92: lload_1
    //   93: lxor
    //   94: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   99: invokespecial <init> : (Ljava/lang/String;)V
    //   102: athrow
    //   103: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   106: athrow
    //   107: new wtf/opal/k5
    //   110: dup
    //   111: fload_3
    //   112: invokestatic toString : (F)Ljava/lang/String;
    //   115: lload #6
    //   117: dup2_x1
    //   118: pop2
    //   119: iconst_2
    //   120: anewarray java/lang/Object
    //   123: dup_x1
    //   124: swap
    //   125: iconst_1
    //   126: swap
    //   127: aastore
    //   128: dup_x2
    //   129: dup_x2
    //   130: pop
    //   131: invokestatic valueOf : (J)Ljava/lang/Long;
    //   134: iconst_0
    //   135: swap
    //   136: aastore
    //   137: invokestatic Y : ([Ljava/lang/Object;)Ljava/lang/String;
    //   140: lload #4
    //   142: dup2_x1
    //   143: pop2
    //   144: invokespecial <init> : (JLjava/lang/String;)V
    //   147: areturn
    // Exception table:
    //   from	to	target	type
    //   49	61	64	java/lang/IllegalArgumentException
    //   58	72	75	java/lang/IllegalArgumentException
    //   79	103	103	java/lang/IllegalArgumentException
  }
  
  public static kv n(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Double
    //   7: invokevirtual doubleValue : ()D
    //   10: dstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_3
    //   21: pop
    //   22: getstatic wtf/opal/u4.a : J
    //   25: lload_3
    //   26: lxor
    //   27: lstore_3
    //   28: lload_3
    //   29: dup2
    //   30: ldc2_w 78149315548962
    //   33: lxor
    //   34: lstore #5
    //   36: dup2
    //   37: ldc2_w 24501830167917
    //   40: lxor
    //   41: lstore #7
    //   43: pop2
    //   44: invokestatic T : ()I
    //   47: istore #9
    //   49: dload_1
    //   50: invokestatic isInfinite : (D)Z
    //   53: iload #9
    //   55: ifne -> 79
    //   58: ifne -> 82
    //   61: goto -> 68
    //   64: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   67: athrow
    //   68: dload_1
    //   69: invokestatic isNaN : (D)Z
    //   72: goto -> 79
    //   75: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   78: athrow
    //   79: ifeq -> 107
    //   82: new java/lang/IllegalArgumentException
    //   85: dup
    //   86: sipush #15304
    //   89: ldc2_w 656596856851839246
    //   92: lload_3
    //   93: lxor
    //   94: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   99: invokespecial <init> : (Ljava/lang/String;)V
    //   102: athrow
    //   103: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   106: athrow
    //   107: new wtf/opal/k5
    //   110: dup
    //   111: dload_1
    //   112: invokestatic toString : (D)Ljava/lang/String;
    //   115: lload #7
    //   117: dup2_x1
    //   118: pop2
    //   119: iconst_2
    //   120: anewarray java/lang/Object
    //   123: dup_x1
    //   124: swap
    //   125: iconst_1
    //   126: swap
    //   127: aastore
    //   128: dup_x2
    //   129: dup_x2
    //   130: pop
    //   131: invokestatic valueOf : (J)Ljava/lang/Long;
    //   134: iconst_0
    //   135: swap
    //   136: aastore
    //   137: invokestatic Y : ([Ljava/lang/Object;)Ljava/lang/String;
    //   140: lload #5
    //   142: dup2_x1
    //   143: pop2
    //   144: invokespecial <init> : (JLjava/lang/String;)V
    //   147: areturn
    // Exception table:
    //   from	to	target	type
    //   49	61	64	java/lang/IllegalArgumentException
    //   58	72	75	java/lang/IllegalArgumentException
    //   79	103	103	java/lang/IllegalArgumentException
  }
  
  public static kv L(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    String str = (String)paramArrayOfObject[1];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x56CAEC649192L;
    try {
    
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    return (str == null) ? t : new k4(str, l2);
  }
  
  public static kv J(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    boolean bool = ((Boolean)paramArrayOfObject[1]).booleanValue();
    l = a ^ l;
    try {
    
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    return bool ? h : s;
  }
  
  public static k1 C(Object[] paramArrayOfObject) {
    return new k1();
  }
  
  public static k1 v(Object... paramVarArgs) {
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
    //   14: checkcast [I
    //   17: astore_1
    //   18: pop
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: lload_2
    //   26: dup2
    //   27: ldc2_w 61242124050880
    //   30: lxor
    //   31: lstore #4
    //   33: pop2
    //   34: invokestatic T : ()I
    //   37: istore #6
    //   39: aload_1
    //   40: ifnonnull -> 68
    //   43: new java/lang/NullPointerException
    //   46: dup
    //   47: sipush #8516
    //   50: ldc2_w 3374681596769710348
    //   53: lload_2
    //   54: lxor
    //   55: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: athrow
    //   64: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   67: athrow
    //   68: new wtf/opal/k1
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: astore #7
    //   77: aload_1
    //   78: astore #8
    //   80: aload #8
    //   82: arraylength
    //   83: istore #9
    //   85: iconst_0
    //   86: istore #10
    //   88: iload #10
    //   90: iload #9
    //   92: if_icmpge -> 165
    //   95: aload #8
    //   97: iload #10
    //   99: iaload
    //   100: istore #11
    //   102: lload_2
    //   103: lconst_0
    //   104: lcmp
    //   105: ifle -> 147
    //   108: aload #7
    //   110: lload #4
    //   112: iload #11
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup_x1
    //   119: swap
    //   120: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   123: iconst_1
    //   124: swap
    //   125: aastore
    //   126: dup_x2
    //   127: dup_x2
    //   128: pop
    //   129: invokestatic valueOf : (J)Ljava/lang/Long;
    //   132: iconst_0
    //   133: swap
    //   134: aastore
    //   135: invokevirtual u : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   138: iload #6
    //   140: ifne -> 167
    //   143: pop
    //   144: iinc #10, 1
    //   147: iload #6
    //   149: ifeq -> 88
    //   152: lload_2
    //   153: lconst_0
    //   154: lcmp
    //   155: iflt -> 102
    //   158: goto -> 165
    //   161: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   164: athrow
    //   165: aload #7
    //   167: areturn
    // Exception table:
    //   from	to	target	type
    //   39	64	64	java/lang/IllegalArgumentException
    //   102	152	161	java/lang/IllegalArgumentException
  }
  
  public static k1 f(Object... paramVarArgs) {
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
    //   14: checkcast [J
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 99508848163673
    //   30: lxor
    //   31: lstore #4
    //   33: pop2
    //   34: invokestatic T : ()I
    //   37: istore #6
    //   39: aload_3
    //   40: ifnonnull -> 68
    //   43: new java/lang/NullPointerException
    //   46: dup
    //   47: sipush #29035
    //   50: ldc2_w 3319467439996552565
    //   53: lload_1
    //   54: lxor
    //   55: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: athrow
    //   64: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   67: athrow
    //   68: new wtf/opal/k1
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: astore #7
    //   77: aload_3
    //   78: astore #8
    //   80: aload #8
    //   82: arraylength
    //   83: istore #9
    //   85: iconst_0
    //   86: istore #10
    //   88: iload #10
    //   90: iload #9
    //   92: if_icmpge -> 166
    //   95: aload #8
    //   97: iload #10
    //   99: laload
    //   100: lstore #11
    //   102: lload_1
    //   103: lconst_0
    //   104: lcmp
    //   105: iflt -> 148
    //   108: aload #7
    //   110: lload #11
    //   112: lload #4
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup_x2
    //   119: dup_x2
    //   120: pop
    //   121: invokestatic valueOf : (J)Ljava/lang/Long;
    //   124: iconst_1
    //   125: swap
    //   126: aastore
    //   127: dup_x2
    //   128: dup_x2
    //   129: pop
    //   130: invokestatic valueOf : (J)Ljava/lang/Long;
    //   133: iconst_0
    //   134: swap
    //   135: aastore
    //   136: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   139: iload #6
    //   141: ifne -> 168
    //   144: pop
    //   145: iinc #10, 1
    //   148: iload #6
    //   150: ifeq -> 88
    //   153: lload_1
    //   154: lconst_0
    //   155: lcmp
    //   156: ifle -> 102
    //   159: goto -> 166
    //   162: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   165: athrow
    //   166: aload #7
    //   168: areturn
    // Exception table:
    //   from	to	target	type
    //   39	64	64	java/lang/IllegalArgumentException
    //   102	153	162	java/lang/IllegalArgumentException
  }
  
  public static k1 H(Object... paramVarArgs) {
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
    //   14: checkcast [F
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 45758529758450
    //   30: lxor
    //   31: lstore #4
    //   33: pop2
    //   34: invokestatic O : ()I
    //   37: istore #6
    //   39: aload_3
    //   40: ifnonnull -> 68
    //   43: new java/lang/NullPointerException
    //   46: dup
    //   47: sipush #29035
    //   50: ldc2_w 3319510585193006758
    //   53: lload_1
    //   54: lxor
    //   55: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: athrow
    //   64: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   67: athrow
    //   68: new wtf/opal/k1
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: astore #7
    //   77: aload_3
    //   78: astore #8
    //   80: aload #8
    //   82: arraylength
    //   83: istore #9
    //   85: iconst_0
    //   86: istore #10
    //   88: iload #10
    //   90: iload #9
    //   92: if_icmpge -> 165
    //   95: aload #8
    //   97: iload #10
    //   99: faload
    //   100: fstore #11
    //   102: lload_1
    //   103: lconst_0
    //   104: lcmp
    //   105: ifle -> 147
    //   108: aload #7
    //   110: lload #4
    //   112: fload #11
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup_x1
    //   119: swap
    //   120: invokestatic valueOf : (F)Ljava/lang/Float;
    //   123: iconst_1
    //   124: swap
    //   125: aastore
    //   126: dup_x2
    //   127: dup_x2
    //   128: pop
    //   129: invokestatic valueOf : (J)Ljava/lang/Long;
    //   132: iconst_0
    //   133: swap
    //   134: aastore
    //   135: invokevirtual P : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   138: iload #6
    //   140: ifeq -> 167
    //   143: pop
    //   144: iinc #10, 1
    //   147: iload #6
    //   149: ifne -> 88
    //   152: lload_1
    //   153: lconst_0
    //   154: lcmp
    //   155: ifle -> 102
    //   158: goto -> 165
    //   161: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   164: athrow
    //   165: aload #7
    //   167: areturn
    // Exception table:
    //   from	to	target	type
    //   39	64	64	java/lang/IllegalArgumentException
    //   102	152	161	java/lang/IllegalArgumentException
  }
  
  public static k1 D(Object... paramVarArgs) {
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
    //   14: checkcast [D
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 118896925886509
    //   30: lxor
    //   31: lstore #4
    //   33: pop2
    //   34: invokestatic O : ()I
    //   37: istore #6
    //   39: aload_3
    //   40: ifnonnull -> 68
    //   43: new java/lang/NullPointerException
    //   46: dup
    //   47: sipush #29035
    //   50: ldc2_w 3319440941724319115
    //   53: lload_1
    //   54: lxor
    //   55: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: athrow
    //   64: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   67: athrow
    //   68: new wtf/opal/k1
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: astore #7
    //   77: aload_3
    //   78: astore #8
    //   80: aload #8
    //   82: arraylength
    //   83: istore #9
    //   85: iconst_0
    //   86: istore #10
    //   88: iload #10
    //   90: iload #9
    //   92: if_icmpge -> 166
    //   95: aload #8
    //   97: iload #10
    //   99: daload
    //   100: dstore #11
    //   102: lload_1
    //   103: lconst_0
    //   104: lcmp
    //   105: iflt -> 148
    //   108: aload #7
    //   110: dload #11
    //   112: lload #4
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup_x2
    //   119: dup_x2
    //   120: pop
    //   121: invokestatic valueOf : (J)Ljava/lang/Long;
    //   124: iconst_1
    //   125: swap
    //   126: aastore
    //   127: dup_x2
    //   128: dup_x2
    //   129: pop
    //   130: invokestatic valueOf : (D)Ljava/lang/Double;
    //   133: iconst_0
    //   134: swap
    //   135: aastore
    //   136: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   139: iload #6
    //   141: ifeq -> 168
    //   144: pop
    //   145: iinc #10, 1
    //   148: iload #6
    //   150: ifne -> 88
    //   153: lload_1
    //   154: lconst_0
    //   155: lcmp
    //   156: ifle -> 102
    //   159: goto -> 166
    //   162: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   165: athrow
    //   166: aload #7
    //   168: areturn
    // Exception table:
    //   from	to	target	type
    //   39	64	64	java/lang/IllegalArgumentException
    //   102	153	162	java/lang/IllegalArgumentException
  }
  
  public static k1 e(Object... paramVarArgs) {
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
    //   14: checkcast [Z
    //   17: astore_1
    //   18: pop
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: lload_2
    //   26: dup2
    //   27: ldc2_w 6645790229560
    //   30: lxor
    //   31: lstore #4
    //   33: pop2
    //   34: invokestatic O : ()I
    //   37: istore #6
    //   39: aload_1
    //   40: ifnonnull -> 68
    //   43: new java/lang/NullPointerException
    //   46: dup
    //   47: sipush #29035
    //   50: ldc2_w 3319488179557762013
    //   53: lload_2
    //   54: lxor
    //   55: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: athrow
    //   64: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   67: athrow
    //   68: new wtf/opal/k1
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: astore #7
    //   77: aload_1
    //   78: astore #8
    //   80: aload #8
    //   82: arraylength
    //   83: istore #9
    //   85: iconst_0
    //   86: istore #10
    //   88: iload #10
    //   90: iload #9
    //   92: if_icmpge -> 165
    //   95: aload #8
    //   97: iload #10
    //   99: baload
    //   100: istore #11
    //   102: lload_2
    //   103: lconst_0
    //   104: lcmp
    //   105: ifle -> 147
    //   108: aload #7
    //   110: iload #11
    //   112: lload #4
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup_x2
    //   119: dup_x2
    //   120: pop
    //   121: invokestatic valueOf : (J)Ljava/lang/Long;
    //   124: iconst_1
    //   125: swap
    //   126: aastore
    //   127: dup_x1
    //   128: swap
    //   129: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   132: iconst_0
    //   133: swap
    //   134: aastore
    //   135: invokevirtual E : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   138: iload #6
    //   140: ifeq -> 167
    //   143: pop
    //   144: iinc #10, 1
    //   147: iload #6
    //   149: ifne -> 88
    //   152: lload_2
    //   153: lconst_0
    //   154: lcmp
    //   155: iflt -> 102
    //   158: goto -> 165
    //   161: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   164: athrow
    //   165: aload #7
    //   167: areturn
    // Exception table:
    //   from	to	target	type
    //   39	64	64	java/lang/IllegalArgumentException
    //   102	152	161	java/lang/IllegalArgumentException
  }
  
  public static k1 u(Object... paramVarArgs) {
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
    //   14: checkcast [Ljava/lang/String;
    //   17: astore_1
    //   18: pop
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: lload_2
    //   26: dup2
    //   27: ldc2_w 63781922399847
    //   30: lxor
    //   31: lstore #4
    //   33: pop2
    //   34: invokestatic O : ()I
    //   37: istore #6
    //   39: aload_1
    //   40: ifnonnull -> 68
    //   43: new java/lang/NullPointerException
    //   46: dup
    //   47: sipush #29035
    //   50: ldc2_w 3319549875441366360
    //   53: lload_2
    //   54: lxor
    //   55: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: athrow
    //   64: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   67: athrow
    //   68: new wtf/opal/k1
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: astore #7
    //   77: aload_1
    //   78: astore #8
    //   80: aload #8
    //   82: arraylength
    //   83: istore #9
    //   85: iconst_0
    //   86: istore #10
    //   88: iload #10
    //   90: iload #9
    //   92: if_icmpge -> 162
    //   95: aload #8
    //   97: iload #10
    //   99: aaload
    //   100: astore #11
    //   102: lload_2
    //   103: lconst_0
    //   104: lcmp
    //   105: ifle -> 144
    //   108: aload #7
    //   110: aload #11
    //   112: lload #4
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup_x2
    //   119: dup_x2
    //   120: pop
    //   121: invokestatic valueOf : (J)Ljava/lang/Long;
    //   124: iconst_1
    //   125: swap
    //   126: aastore
    //   127: dup_x1
    //   128: swap
    //   129: iconst_0
    //   130: swap
    //   131: aastore
    //   132: invokevirtual Q : ([Ljava/lang/Object;)Lwtf/opal/k1;
    //   135: iload #6
    //   137: ifeq -> 164
    //   140: pop
    //   141: iinc #10, 1
    //   144: iload #6
    //   146: ifne -> 88
    //   149: lload_2
    //   150: lconst_0
    //   151: lcmp
    //   152: ifle -> 102
    //   155: goto -> 162
    //   158: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   161: athrow
    //   162: aload #7
    //   164: areturn
    // Exception table:
    //   from	to	target	type
    //   39	64	64	java/lang/IllegalArgumentException
    //   102	149	158	java/lang/IllegalArgumentException
  }
  
  public static kq C(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x2B3BBFCDE4B0L;
    return new kq(l2);
  }
  
  public static kv z(Object[] paramArrayOfObject) {
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
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 43009375237386
    //   30: lxor
    //   31: dup2
    //   32: bipush #32
    //   34: lushr
    //   35: l2i
    //   36: istore #4
    //   38: dup2
    //   39: bipush #32
    //   41: lshl
    //   42: bipush #48
    //   44: lushr
    //   45: l2i
    //   46: istore #5
    //   48: dup2
    //   49: bipush #48
    //   51: lshl
    //   52: bipush #48
    //   54: lushr
    //   55: l2i
    //   56: istore #6
    //   58: pop2
    //   59: dup2
    //   60: ldc2_w 135641353245495
    //   63: lxor
    //   64: lstore #7
    //   66: pop2
    //   67: aload_3
    //   68: ifnonnull -> 96
    //   71: new java/lang/NullPointerException
    //   74: dup
    //   75: sipush #11753
    //   78: ldc2_w 2828499964034760632
    //   81: lload_1
    //   82: lxor
    //   83: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   88: invokespecial <init> : (Ljava/lang/String;)V
    //   91: athrow
    //   92: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   95: athrow
    //   96: new wtf/opal/l4
    //   99: dup
    //   100: invokespecial <init> : ()V
    //   103: astore #9
    //   105: new wtf/opal/d7
    //   108: dup
    //   109: aload #9
    //   111: iload #4
    //   113: iload #5
    //   115: iload #6
    //   117: invokespecial <init> : (Lwtf/opal/lq;III)V
    //   120: aload_3
    //   121: lload #7
    //   123: iconst_2
    //   124: anewarray java/lang/Object
    //   127: dup_x2
    //   128: dup_x2
    //   129: pop
    //   130: invokestatic valueOf : (J)Ljava/lang/Long;
    //   133: iconst_1
    //   134: swap
    //   135: aastore
    //   136: dup_x1
    //   137: swap
    //   138: iconst_0
    //   139: swap
    //   140: aastore
    //   141: invokevirtual K : ([Ljava/lang/Object;)V
    //   144: aload #9
    //   146: iconst_0
    //   147: anewarray java/lang/Object
    //   150: invokevirtual Q : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   153: areturn
    // Exception table:
    //   from	to	target	type
    //   67	92	92	java/lang/IllegalArgumentException
  }
  
  public static kv i(Object[] paramArrayOfObject) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/io/Reader
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_1
    //   18: pop
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 103990399558787
    //   30: lxor
    //   31: dup2
    //   32: bipush #32
    //   34: lushr
    //   35: l2i
    //   36: istore #4
    //   38: dup2
    //   39: bipush #32
    //   41: lshl
    //   42: bipush #48
    //   44: lushr
    //   45: l2i
    //   46: istore #5
    //   48: dup2
    //   49: bipush #48
    //   51: lshl
    //   52: bipush #48
    //   54: lushr
    //   55: l2i
    //   56: istore #6
    //   58: pop2
    //   59: dup2
    //   60: ldc2_w 5850426927670
    //   63: lxor
    //   64: lstore #7
    //   66: pop2
    //   67: aload_3
    //   68: ifnonnull -> 96
    //   71: new java/lang/NullPointerException
    //   74: dup
    //   75: sipush #11523
    //   78: ldc2_w 7046344795489283806
    //   81: lload_1
    //   82: lxor
    //   83: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   88: invokespecial <init> : (Ljava/lang/String;)V
    //   91: athrow
    //   92: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   95: athrow
    //   96: new wtf/opal/l4
    //   99: dup
    //   100: invokespecial <init> : ()V
    //   103: astore #9
    //   105: new wtf/opal/d7
    //   108: dup
    //   109: aload #9
    //   111: iload #4
    //   113: iload #5
    //   115: iload #6
    //   117: invokespecial <init> : (Lwtf/opal/lq;III)V
    //   120: aload_3
    //   121: lload #7
    //   123: iconst_2
    //   124: anewarray java/lang/Object
    //   127: dup_x2
    //   128: dup_x2
    //   129: pop
    //   130: invokestatic valueOf : (J)Ljava/lang/Long;
    //   133: iconst_1
    //   134: swap
    //   135: aastore
    //   136: dup_x1
    //   137: swap
    //   138: iconst_0
    //   139: swap
    //   140: aastore
    //   141: invokevirtual X : ([Ljava/lang/Object;)V
    //   144: aload #9
    //   146: iconst_0
    //   147: anewarray java/lang/Object
    //   150: invokevirtual Q : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   153: areturn
    // Exception table:
    //   from	to	target	type
    //   67	92	92	java/io/IOException
  }
  
  private static String Y(Object[] paramArrayOfObject) {
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
    //   19: getstatic wtf/opal/u4.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: invokestatic O : ()I
    //   28: istore #4
    //   30: aload_3
    //   31: iload #4
    //   33: ifeq -> 79
    //   36: sipush #20517
    //   39: ldc2_w 7608665701531309462
    //   42: lload_1
    //   43: lxor
    //   44: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   49: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   52: ifeq -> 78
    //   55: goto -> 62
    //   58: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   61: athrow
    //   62: aload_3
    //   63: iconst_0
    //   64: aload_3
    //   65: invokevirtual length : ()I
    //   68: iconst_2
    //   69: isub
    //   70: invokevirtual substring : (II)Ljava/lang/String;
    //   73: areturn
    //   74: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   77: athrow
    //   78: aload_3
    //   79: areturn
    // Exception table:
    //   from	to	target	type
    //   30	55	58	java/lang/IllegalArgumentException
    //   36	74	74	java/lang/IllegalArgumentException
  }
  
  static {
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[10];
    boolean bool = false;
    String str;
    int i = (str = "ÔZóßj\026-Æ\033UÎ\bv}b À\034;¶+H\004pöubSUß¾5\fÒ:ëùàÕHj\024¯\në5\rrb\034/úö#3%eW\026ÜÁ\013\f@þa6ý\\\022\017óÝv\033õòÑúV`¸\036\034ÜS\020\022t\f\032Pm¨hðÞT!´\013-PSwáFÆB²\024²~\no\023\fd·\r¢EjÎ\021þÅ\020þ\013®Gÿbw¡\026½H(1&\"\030hS.|\033-ë\025\030\035îâõ\003y«ñÖ¸\003\001%lôT+ þuþK\020Â^&\036ÕFv\037(­@\030¯3uË Ìïëú®\001f¶!¾iìï!À\013¥ód.¯\036õ·ò¡N>PÙ\030\007Ì0èÍ|Ù¥YEüßÐMK\030UØ9·¡ m:\017Ó\\\bSÜNxà@Çjé ¶<\"²En²\021W").length();
    byte b2 = 24;
    byte b = -1;
    while (true);
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2A14;
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
        throw new RuntimeException("wtf/opal/u4", exception);
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
    //   65: ldc_w 'wtf/opal/u4'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x71E0;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
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
        throw new RuntimeException("wtf/opal/u4", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
  }
  
  private static int b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = b(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite b(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
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
    //   65: ldc_w 'wtf/opal/u4'
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
  
  static {
    long l = a ^ 0x140790C32905L;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u4.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */