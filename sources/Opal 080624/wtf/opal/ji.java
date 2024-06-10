package wtf.opal;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1735;
import net.minecraft.class_1738;
import net.minecraft.class_1799;
import net.minecraft.class_3545;

public final class ji extends d {
  private final kr m;
  
  private final kt G;
  
  private final ke A;
  
  private final gm<lz> T;
  
  private static final long a = on.a(213404465268117053L, 8327527398344088964L, MethodHandles.lookup().lookupClass()).a(35127813041462L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] g;
  
  private static final Integer[] k;
  
  private static final Map l;
  
  public ji(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/ji.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 84792308575106
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 109266871710423
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #5587
    //   25: ldc2_w 7480342193885476352
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #13825
    //   40: ldc2_w 4845153124104099280
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/kr
    //   60: dup
    //   61: invokespecial <init> : ()V
    //   64: putfield m : Lwtf/opal/kr;
    //   67: aload_0
    //   68: new wtf/opal/kt
    //   71: dup
    //   72: sipush #29447
    //   75: ldc2_w 3575973376551194837
    //   78: lload_1
    //   79: lxor
    //   80: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   85: ldc2_w 50.0
    //   88: dconst_0
    //   89: ldc2_w 500.0
    //   92: dconst_1
    //   93: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   96: putfield G : Lwtf/opal/kt;
    //   99: aload_0
    //   100: new wtf/opal/ke
    //   103: dup
    //   104: sipush #7527
    //   107: ldc2_w 7699320345476562615
    //   110: lload_1
    //   111: lxor
    //   112: <illegal opcode> p : (IJ)Ljava/lang/String;
    //   117: iconst_1
    //   118: invokespecial <init> : (Ljava/lang/String;Z)V
    //   121: putfield A : Lwtf/opal/ke;
    //   124: aload_0
    //   125: aload_0
    //   126: <illegal opcode> H : (Lwtf/opal/ji;)Lwtf/opal/gm;
    //   131: putfield T : Lwtf/opal/gm;
    //   134: aload_0
    //   135: iconst_2
    //   136: anewarray wtf/opal/k3
    //   139: dup
    //   140: iconst_0
    //   141: aload_0
    //   142: getfield G : Lwtf/opal/kt;
    //   145: aastore
    //   146: dup
    //   147: iconst_1
    //   148: aload_0
    //   149: getfield A : Lwtf/opal/ke;
    //   152: aastore
    //   153: lload_3
    //   154: dup2_x1
    //   155: pop2
    //   156: iconst_2
    //   157: anewarray java/lang/Object
    //   160: dup_x1
    //   161: swap
    //   162: iconst_1
    //   163: swap
    //   164: aastore
    //   165: dup_x2
    //   166: dup_x2
    //   167: pop
    //   168: invokestatic valueOf : (J)Ljava/lang/Long;
    //   171: iconst_0
    //   172: swap
    //   173: aastore
    //   174: invokevirtual o : ([Ljava/lang/Object;)V
    //   177: return
  }
  
  private List l(Object[] paramArrayOfObject) {
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
    //   14: checkcast net/minecraft/class_1723
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/ji.a : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: lload_3
    //   26: dup2
    //   27: ldc2_w 22456926152345
    //   30: lxor
    //   31: lstore #5
    //   33: pop2
    //   34: new java/util/ArrayList
    //   37: dup
    //   38: invokespecial <init> : ()V
    //   41: astore #8
    //   43: invokestatic n : ()Z
    //   46: aload_2
    //   47: getfield field_7761 : Lnet/minecraft/class_2371;
    //   50: invokevirtual iterator : ()Ljava/util/Iterator;
    //   53: astore #9
    //   55: istore #7
    //   57: aload #9
    //   59: invokeinterface hasNext : ()Z
    //   64: ifeq -> 127
    //   67: aload #9
    //   69: invokeinterface next : ()Ljava/lang/Object;
    //   74: checkcast net/minecraft/class_1735
    //   77: astore #10
    //   79: aload #10
    //   81: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   84: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   87: instanceof net/minecraft/class_1738
    //   90: iload #7
    //   92: ifne -> 121
    //   95: ifeq -> 122
    //   98: goto -> 105
    //   101: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: aload #8
    //   107: aload #10
    //   109: invokeinterface add : (Ljava/lang/Object;)Z
    //   114: goto -> 121
    //   117: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   120: athrow
    //   121: pop
    //   122: iload #7
    //   124: ifeq -> 57
    //   127: new java/util/HashMap
    //   130: dup
    //   131: invokespecial <init> : ()V
    //   134: lload_3
    //   135: lconst_0
    //   136: lcmp
    //   137: ifle -> 74
    //   140: astore #9
    //   142: aload #8
    //   144: invokeinterface iterator : ()Ljava/util/Iterator;
    //   149: astore #10
    //   151: aload #10
    //   153: invokeinterface hasNext : ()Z
    //   158: ifeq -> 270
    //   161: aload #10
    //   163: invokeinterface next : ()Ljava/lang/Object;
    //   168: checkcast net/minecraft/class_1735
    //   171: astore #11
    //   173: aload #11
    //   175: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   178: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   181: checkcast net/minecraft/class_1738
    //   184: invokevirtual method_48398 : ()Lnet/minecraft/class_1738$class_8051;
    //   187: astore #12
    //   189: aload #9
    //   191: aload #12
    //   193: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   198: invokeinterface computeIfAbsent : (Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;
    //   203: pop
    //   204: aload #9
    //   206: aload #12
    //   208: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   213: checkcast java/util/List
    //   216: new net/minecraft/class_3545
    //   219: dup
    //   220: aload #11
    //   222: aload_0
    //   223: aload #11
    //   225: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   228: lload #5
    //   230: dup2_x1
    //   231: pop2
    //   232: iconst_2
    //   233: anewarray java/lang/Object
    //   236: dup_x1
    //   237: swap
    //   238: iconst_1
    //   239: swap
    //   240: aastore
    //   241: dup_x2
    //   242: dup_x2
    //   243: pop
    //   244: invokestatic valueOf : (J)Ljava/lang/Long;
    //   247: iconst_0
    //   248: swap
    //   249: aastore
    //   250: invokevirtual Y : ([Ljava/lang/Object;)F
    //   253: invokestatic valueOf : (F)Ljava/lang/Float;
    //   256: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   259: invokeinterface add : (Ljava/lang/Object;)Z
    //   264: pop
    //   265: iload #7
    //   267: ifeq -> 151
    //   270: new java/util/ArrayList
    //   273: dup
    //   274: invokespecial <init> : ()V
    //   277: lload_3
    //   278: lconst_0
    //   279: lcmp
    //   280: iflt -> 168
    //   283: astore #10
    //   285: invokestatic values : ()[Lnet/minecraft/class_1738$class_8051;
    //   288: astore #11
    //   290: aload #11
    //   292: arraylength
    //   293: istore #12
    //   295: iconst_0
    //   296: istore #13
    //   298: iload #13
    //   300: iload #12
    //   302: if_icmpge -> 410
    //   305: aload #11
    //   307: iload #13
    //   309: aaload
    //   310: astore #14
    //   312: aload #9
    //   314: aload #14
    //   316: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   321: checkcast java/util/List
    //   324: astore #15
    //   326: iload #7
    //   328: lload_3
    //   329: lconst_0
    //   330: lcmp
    //   331: iflt -> 407
    //   334: ifne -> 405
    //   337: aload #15
    //   339: ifnull -> 402
    //   342: goto -> 349
    //   345: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   348: athrow
    //   349: aload #15
    //   351: <illegal opcode> compare : ()Ljava/util/Comparator;
    //   356: invokeinterface sort : (Ljava/util/Comparator;)V
    //   361: aload #10
    //   363: new net/minecraft/class_3545
    //   366: dup
    //   367: aload #14
    //   369: aload #15
    //   371: iconst_0
    //   372: invokeinterface get : (I)Ljava/lang/Object;
    //   377: checkcast net/minecraft/class_3545
    //   380: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   383: checkcast net/minecraft/class_1735
    //   386: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   389: invokeinterface add : (Ljava/lang/Object;)Z
    //   394: pop
    //   395: goto -> 402
    //   398: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   401: athrow
    //   402: iinc #13, 1
    //   405: iload #7
    //   407: ifeq -> 298
    //   410: aload #10
    //   412: areturn
    // Exception table:
    //   from	to	target	type
    //   79	98	101	wtf/opal/x5
    //   95	114	117	wtf/opal/x5
    //   326	342	345	wtf/opal/x5
    //   337	395	398	wtf/opal/x5
  }
  
  private float Y(Object[] paramArrayOfObject) {
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
    //   14: checkcast net/minecraft/class_1799
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/ji.a : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: invokestatic k : ()Z
    //   28: aload_2
    //   29: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   32: astore #7
    //   34: istore #5
    //   36: aload #7
    //   38: iload #5
    //   40: ifeq -> 65
    //   43: instanceof net/minecraft/class_1738
    //   46: ifeq -> 75
    //   49: goto -> 56
    //   52: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   55: athrow
    //   56: aload #7
    //   58: goto -> 65
    //   61: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: checkcast net/minecraft/class_1738
    //   68: astore #6
    //   70: iload #5
    //   72: ifne -> 81
    //   75: fconst_0
    //   76: freturn
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: aload_2
    //   82: invokestatic method_57532 : (Lnet/minecraft/class_1799;)Lnet/minecraft/class_9304;
    //   85: invokevirtual method_57539 : ()Ljava/util/Set;
    //   88: astore #7
    //   90: new java/util/concurrent/atomic/AtomicInteger
    //   93: dup
    //   94: aload #6
    //   96: invokevirtual method_7687 : ()I
    //   99: sipush #24691
    //   102: ldc2_w 3348719994287013557
    //   105: lload_3
    //   106: lxor
    //   107: <illegal opcode> i : (IJ)I
    //   112: imul
    //   113: invokespecial <init> : (I)V
    //   116: astore #8
    //   118: aload #7
    //   120: aload #8
    //   122: <illegal opcode> accept : (Ljava/util/concurrent/atomic/AtomicInteger;)Ljava/util/function/Consumer;
    //   127: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   132: aload #8
    //   134: aload_2
    //   135: invokevirtual method_7936 : ()I
    //   138: sipush #7154
    //   141: ldc2_w 4742457826843968823
    //   144: lload_3
    //   145: lxor
    //   146: <illegal opcode> i : (IJ)I
    //   151: idiv
    //   152: aload_2
    //   153: invokevirtual method_7919 : ()I
    //   156: sipush #25208
    //   159: ldc2_w 2822276616888990905
    //   162: lload_3
    //   163: lxor
    //   164: <illegal opcode> i : (IJ)I
    //   169: idiv
    //   170: isub
    //   171: invokevirtual addAndGet : (I)I
    //   174: i2f
    //   175: freturn
    // Exception table:
    //   from	to	target	type
    //   36	49	52	wtf/opal/x5
    //   43	58	61	wtf/opal/x5
    //   70	77	77	wtf/opal/x5
  }
  
  private static void lambda$getArmorValue$5(AtomicInteger paramAtomicInteger, Object2IntMap.Entry paramEntry) {
    // Byte code:
    //   0: getstatic wtf/opal/ji.a : J
    //   3: ldc2_w 42165950463727
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic n : ()Z
    //   11: aload_1
    //   12: invokeinterface getKey : ()Ljava/lang/Object;
    //   17: checkcast net/minecraft/class_6880
    //   20: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   25: checkcast net/minecraft/class_1887
    //   28: astore #5
    //   30: aload_1
    //   31: invokeinterface getIntValue : ()I
    //   36: istore #6
    //   38: istore #4
    //   40: iconst_0
    //   41: istore #7
    //   43: aload #5
    //   45: getstatic net/minecraft/class_1893.field_9111 : Lnet/minecraft/class_1887;
    //   48: iload #4
    //   50: ifne -> 101
    //   53: if_acmpne -> 89
    //   56: goto -> 63
    //   59: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   62: athrow
    //   63: iload #7
    //   65: iload #6
    //   67: sipush #11169
    //   70: ldc2_w 1871487806609190355
    //   73: lload_2
    //   74: lxor
    //   75: <illegal opcode> i : (IJ)I
    //   80: imul
    //   81: iadd
    //   82: istore #7
    //   84: iload #4
    //   86: ifeq -> 243
    //   89: aload #5
    //   91: getstatic net/minecraft/class_1893.field_9097 : Lnet/minecraft/class_1887;
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: iload #4
    //   103: ifne -> 154
    //   106: if_acmpne -> 142
    //   109: goto -> 116
    //   112: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   115: athrow
    //   116: iload #7
    //   118: iload #6
    //   120: sipush #30503
    //   123: ldc2_w 6743918168590987603
    //   126: lload_2
    //   127: lxor
    //   128: <illegal opcode> i : (IJ)I
    //   133: imul
    //   134: iadd
    //   135: istore #7
    //   137: iload #4
    //   139: ifeq -> 243
    //   142: aload #5
    //   144: getstatic net/minecraft/class_1893.field_9096 : Lnet/minecraft/class_1887;
    //   147: goto -> 154
    //   150: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   153: athrow
    //   154: iload #4
    //   156: ifne -> 207
    //   159: if_acmpne -> 195
    //   162: goto -> 169
    //   165: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   168: athrow
    //   169: iload #7
    //   171: iload #6
    //   173: sipush #4832
    //   176: ldc2_w 7193822956856333463
    //   179: lload_2
    //   180: lxor
    //   181: <illegal opcode> i : (IJ)I
    //   186: imul
    //   187: iadd
    //   188: istore #7
    //   190: iload #4
    //   192: ifeq -> 243
    //   195: aload #5
    //   197: getstatic net/minecraft/class_1893.field_9119 : Lnet/minecraft/class_1887;
    //   200: goto -> 207
    //   203: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   206: athrow
    //   207: if_acmpne -> 236
    //   210: iload #7
    //   212: iload #6
    //   214: sipush #27162
    //   217: ldc2_w 3717429367266520169
    //   220: lload_2
    //   221: lxor
    //   222: <illegal opcode> i : (IJ)I
    //   227: imul
    //   228: iadd
    //   229: istore #7
    //   231: iload #4
    //   233: ifeq -> 243
    //   236: iload #7
    //   238: iload #6
    //   240: iadd
    //   241: istore #7
    //   243: aload_0
    //   244: iload #7
    //   246: invokevirtual addAndGet : (I)I
    //   249: pop
    //   250: return
    // Exception table:
    //   from	to	target	type
    //   43	56	59	wtf/opal/x5
    //   84	94	97	wtf/opal/x5
    //   101	109	112	wtf/opal/x5
    //   137	147	150	wtf/opal/x5
    //   154	162	165	wtf/opal/x5
    //   190	200	203	wtf/opal/x5
  }
  
  private static int lambda$getBestArmor$4(class_3545 paramclass_35451, class_3545 paramclass_35452) {
    return Float.compare(((Float)paramclass_35452.method_15441()).floatValue(), ((Float)paramclass_35451.method_15441()).floatValue());
  }
  
  private static List lambda$getBestArmor$3(class_1738.class_8051 paramclass_8051) {
    return new ArrayList();
  }
  
  private void lambda$new$2(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/ji.a : J
    //   3: ldc2_w 68539991441370
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 108035365147146
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 86649687374764
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 16143863889692
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: iconst_0
    //   32: anewarray java/lang/Object
    //   35: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   38: iconst_0
    //   39: anewarray java/lang/Object
    //   42: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   45: ldc_w wtf/opal/q
    //   48: iconst_1
    //   49: anewarray java/lang/Object
    //   52: dup_x1
    //   53: swap
    //   54: iconst_0
    //   55: swap
    //   56: aastore
    //   57: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   60: checkcast wtf/opal/q
    //   63: astore #11
    //   65: invokestatic k : ()Z
    //   68: iconst_0
    //   69: anewarray java/lang/Object
    //   72: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   75: iconst_0
    //   76: anewarray java/lang/Object
    //   79: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   82: ldc_w wtf/opal/xw
    //   85: iconst_1
    //   86: anewarray java/lang/Object
    //   89: dup_x1
    //   90: swap
    //   91: iconst_0
    //   92: swap
    //   93: aastore
    //   94: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   97: checkcast wtf/opal/xw
    //   100: astore #12
    //   102: iconst_0
    //   103: anewarray java/lang/Object
    //   106: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   109: iconst_0
    //   110: anewarray java/lang/Object
    //   113: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   116: ldc_w wtf/opal/o
    //   119: iconst_1
    //   120: anewarray java/lang/Object
    //   123: dup_x1
    //   124: swap
    //   125: iconst_0
    //   126: swap
    //   127: aastore
    //   128: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   131: checkcast wtf/opal/o
    //   134: astore #13
    //   136: istore #10
    //   138: aload_0
    //   139: getfield A : Lwtf/opal/ke;
    //   142: invokevirtual z : ()Ljava/lang/Object;
    //   145: checkcast java/lang/Boolean
    //   148: invokevirtual booleanValue : ()Z
    //   151: iload #10
    //   153: ifeq -> 213
    //   156: ifne -> 197
    //   159: goto -> 166
    //   162: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   165: athrow
    //   166: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   169: getfield field_1755 : Lnet/minecraft/class_437;
    //   172: instanceof net/minecraft/class_490
    //   175: iload #10
    //   177: ifeq -> 213
    //   180: goto -> 187
    //   183: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   186: athrow
    //   187: ifeq -> 278
    //   190: goto -> 197
    //   193: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   196: athrow
    //   197: aload #11
    //   199: iconst_0
    //   200: anewarray java/lang/Object
    //   203: invokevirtual D : ([Ljava/lang/Object;)Z
    //   206: goto -> 213
    //   209: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   212: athrow
    //   213: iload #10
    //   215: ifeq -> 263
    //   218: ifeq -> 247
    //   221: goto -> 228
    //   224: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   227: athrow
    //   228: aload #11
    //   230: iconst_0
    //   231: anewarray java/lang/Object
    //   234: invokevirtual U : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   237: ifnonnull -> 278
    //   240: goto -> 247
    //   243: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   246: athrow
    //   247: aload #12
    //   249: iconst_0
    //   250: anewarray java/lang/Object
    //   253: invokevirtual D : ([Ljava/lang/Object;)Z
    //   256: goto -> 263
    //   259: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   262: athrow
    //   263: iload #10
    //   265: ifeq -> 299
    //   268: ifeq -> 279
    //   271: goto -> 278
    //   274: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   277: athrow
    //   278: return
    //   279: aload #13
    //   281: lload #4
    //   283: iconst_1
    //   284: anewarray java/lang/Object
    //   287: dup_x2
    //   288: dup_x2
    //   289: pop
    //   290: invokestatic valueOf : (J)Ljava/lang/Long;
    //   293: iconst_0
    //   294: swap
    //   295: aastore
    //   296: invokevirtual A : ([Ljava/lang/Object;)Z
    //   299: iload #10
    //   301: ifeq -> 361
    //   304: ifne -> 346
    //   307: goto -> 314
    //   310: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   313: athrow
    //   314: aload #13
    //   316: iconst_0
    //   317: anewarray java/lang/Object
    //   320: invokevirtual D : ([Ljava/lang/Object;)Z
    //   323: iload #10
    //   325: ifeq -> 361
    //   328: goto -> 335
    //   331: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   334: athrow
    //   335: ifeq -> 346
    //   338: goto -> 345
    //   341: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   344: athrow
    //   345: return
    //   346: aload_0
    //   347: getfield G : Lwtf/opal/kt;
    //   350: invokevirtual z : ()Ljava/lang/Object;
    //   353: checkcast java/lang/Double
    //   356: invokevirtual doubleValue : ()D
    //   359: dconst_0
    //   360: dcmpl
    //   361: iload #10
    //   363: ifeq -> 436
    //   366: ifeq -> 439
    //   369: goto -> 376
    //   372: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   375: athrow
    //   376: aload_0
    //   377: getfield m : Lwtf/opal/kr;
    //   380: aload_0
    //   381: getfield G : Lwtf/opal/kt;
    //   384: invokevirtual z : ()Ljava/lang/Object;
    //   387: checkcast java/lang/Double
    //   390: invokevirtual longValue : ()J
    //   393: lload #6
    //   395: iconst_0
    //   396: iconst_3
    //   397: anewarray java/lang/Object
    //   400: dup_x1
    //   401: swap
    //   402: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   405: iconst_2
    //   406: swap
    //   407: aastore
    //   408: dup_x2
    //   409: dup_x2
    //   410: pop
    //   411: invokestatic valueOf : (J)Ljava/lang/Long;
    //   414: iconst_1
    //   415: swap
    //   416: aastore
    //   417: dup_x2
    //   418: dup_x2
    //   419: pop
    //   420: invokestatic valueOf : (J)Ljava/lang/Long;
    //   423: iconst_0
    //   424: swap
    //   425: aastore
    //   426: invokevirtual v : ([Ljava/lang/Object;)Z
    //   429: goto -> 436
    //   432: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   435: athrow
    //   436: ifeq -> 914
    //   439: new java/util/ArrayList
    //   442: dup
    //   443: invokespecial <init> : ()V
    //   446: astore #14
    //   448: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   451: getfield field_1724 : Lnet/minecraft/class_746;
    //   454: getfield field_7498 : Lnet/minecraft/class_1723;
    //   457: getfield field_7761 : Lnet/minecraft/class_2371;
    //   460: invokevirtual iterator : ()Ljava/util/Iterator;
    //   463: astore #15
    //   465: aload #15
    //   467: invokeinterface hasNext : ()Z
    //   472: ifeq -> 582
    //   475: aload #15
    //   477: invokeinterface next : ()Ljava/lang/Object;
    //   482: checkcast net/minecraft/class_1735
    //   485: astore #16
    //   487: iload #10
    //   489: ifeq -> 914
    //   492: aload #16
    //   494: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   497: invokevirtual method_7960 : ()Z
    //   500: iload #10
    //   502: ifeq -> 545
    //   505: goto -> 512
    //   508: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   511: athrow
    //   512: ifne -> 577
    //   515: goto -> 522
    //   518: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   521: athrow
    //   522: aload #16
    //   524: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   527: invokevirtual method_7964 : ()Lnet/minecraft/class_2561;
    //   530: invokeinterface method_10866 : ()Lnet/minecraft/class_2583;
    //   535: invokevirtual method_10967 : ()Z
    //   538: goto -> 545
    //   541: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   544: athrow
    //   545: iload #10
    //   547: ifeq -> 576
    //   550: ifeq -> 577
    //   553: goto -> 560
    //   556: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   559: athrow
    //   560: aload #14
    //   562: aload #16
    //   564: invokeinterface add : (Ljava/lang/Object;)Z
    //   569: goto -> 576
    //   572: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   575: athrow
    //   576: pop
    //   577: iload #10
    //   579: ifne -> 465
    //   582: aload_0
    //   583: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   586: getfield field_1724 : Lnet/minecraft/class_746;
    //   589: getfield field_7498 : Lnet/minecraft/class_1723;
    //   592: lload #8
    //   594: dup2_x1
    //   595: pop2
    //   596: iconst_2
    //   597: anewarray java/lang/Object
    //   600: dup_x1
    //   601: swap
    //   602: iconst_1
    //   603: swap
    //   604: aastore
    //   605: dup_x2
    //   606: dup_x2
    //   607: pop
    //   608: invokestatic valueOf : (J)Ljava/lang/Long;
    //   611: iconst_0
    //   612: swap
    //   613: aastore
    //   614: invokevirtual l : ([Ljava/lang/Object;)Ljava/util/List;
    //   617: astore #15
    //   619: aload #14
    //   621: invokeinterface iterator : ()Ljava/util/Iterator;
    //   626: astore #16
    //   628: aload #16
    //   630: invokeinterface hasNext : ()Z
    //   635: ifeq -> 782
    //   638: aload #16
    //   640: invokeinterface next : ()Ljava/lang/Object;
    //   645: checkcast net/minecraft/class_1735
    //   648: astore #17
    //   650: aload #17
    //   652: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   655: astore #18
    //   657: aload #18
    //   659: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   662: instanceof net/minecraft/class_1738
    //   665: iload #10
    //   667: ifeq -> 798
    //   670: iload #10
    //   672: ifeq -> 723
    //   675: goto -> 682
    //   678: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   681: athrow
    //   682: ifne -> 704
    //   685: goto -> 692
    //   688: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   691: athrow
    //   692: iload #10
    //   694: ifne -> 628
    //   697: goto -> 704
    //   700: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   703: athrow
    //   704: aload #15
    //   706: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   711: aload #18
    //   713: <illegal opcode> test : (Lnet/minecraft/class_1799;)Ljava/util/function/Predicate;
    //   718: invokeinterface noneMatch : (Ljava/util/function/Predicate;)Z
    //   723: iload #10
    //   725: ifeq -> 750
    //   728: ifeq -> 777
    //   731: goto -> 738
    //   734: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   737: athrow
    //   738: aload #17
    //   740: getfield field_7874 : I
    //   743: goto -> 750
    //   746: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   749: athrow
    //   750: iconst_1
    //   751: anewarray java/lang/Object
    //   754: dup_x1
    //   755: swap
    //   756: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   759: iconst_0
    //   760: swap
    //   761: aastore
    //   762: invokestatic J : ([Ljava/lang/Object;)V
    //   765: aload_0
    //   766: getfield m : Lwtf/opal/kr;
    //   769: iconst_0
    //   770: anewarray java/lang/Object
    //   773: invokevirtual z : ([Ljava/lang/Object;)V
    //   776: return
    //   777: iload #10
    //   779: ifne -> 628
    //   782: aload #15
    //   784: invokeinterface iterator : ()Ljava/util/Iterator;
    //   789: astore #16
    //   791: aload #16
    //   793: invokeinterface hasNext : ()Z
    //   798: ifeq -> 914
    //   801: aload #16
    //   803: invokeinterface next : ()Ljava/lang/Object;
    //   808: checkcast net/minecraft/class_3545
    //   811: astore #17
    //   813: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   816: getfield field_1724 : Lnet/minecraft/class_746;
    //   819: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   822: getfield field_7548 : Lnet/minecraft/class_2371;
    //   825: invokevirtual stream : ()Ljava/util/stream/Stream;
    //   828: aload #17
    //   830: <illegal opcode> test : (Lnet/minecraft/class_3545;)Ljava/util/function/Predicate;
    //   835: invokeinterface noneMatch : (Ljava/util/function/Predicate;)Z
    //   840: iload #10
    //   842: ifeq -> 873
    //   845: ifeq -> 909
    //   848: goto -> 855
    //   851: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   854: athrow
    //   855: aload #17
    //   857: invokevirtual method_15441 : ()Ljava/lang/Object;
    //   860: checkcast net/minecraft/class_1735
    //   863: getfield field_7874 : I
    //   866: goto -> 873
    //   869: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   872: athrow
    //   873: iconst_0
    //   874: iconst_2
    //   875: anewarray java/lang/Object
    //   878: dup_x1
    //   879: swap
    //   880: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   883: iconst_1
    //   884: swap
    //   885: aastore
    //   886: dup_x1
    //   887: swap
    //   888: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   891: iconst_0
    //   892: swap
    //   893: aastore
    //   894: invokestatic E : ([Ljava/lang/Object;)V
    //   897: aload_0
    //   898: getfield m : Lwtf/opal/kr;
    //   901: iconst_0
    //   902: anewarray java/lang/Object
    //   905: invokevirtual z : ([Ljava/lang/Object;)V
    //   908: return
    //   909: iload #10
    //   911: ifne -> 791
    //   914: return
    // Exception table:
    //   from	to	target	type
    //   138	159	162	wtf/opal/x5
    //   156	180	183	wtf/opal/x5
    //   166	190	193	wtf/opal/x5
    //   187	206	209	wtf/opal/x5
    //   213	221	224	wtf/opal/x5
    //   218	240	243	wtf/opal/x5
    //   228	256	259	wtf/opal/x5
    //   263	271	274	wtf/opal/x5
    //   299	307	310	wtf/opal/x5
    //   304	328	331	wtf/opal/x5
    //   314	338	341	wtf/opal/x5
    //   361	369	372	wtf/opal/x5
    //   366	429	432	wtf/opal/x5
    //   487	505	508	wtf/opal/x5
    //   492	515	518	wtf/opal/x5
    //   512	538	541	wtf/opal/x5
    //   545	553	556	wtf/opal/x5
    //   550	569	572	wtf/opal/x5
    //   657	675	678	wtf/opal/x5
    //   670	685	688	wtf/opal/x5
    //   682	697	700	wtf/opal/x5
    //   723	731	734	wtf/opal/x5
    //   728	743	746	wtf/opal/x5
    //   813	848	851	wtf/opal/x5
    //   845	866	869	wtf/opal/x5
  }
  
  private static boolean lambda$new$1(class_3545 paramclass_3545, class_1799 paramclass_1799) {
    long l = a ^ 0x6133FBA371C7L;
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return (((class_1735)paramclass_3545.method_15441()).method_7677() == paramclass_1799);
  }
  
  private static boolean lambda$new$0(class_1799 paramclass_1799, class_3545 paramclass_3545) {
    long l = a ^ 0x3C8DF6A18EABL;
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return (((class_1735)paramclass_3545.method_15441()).method_7677() == paramclass_1799);
  }
  
  static {
    long l = a ^ 0x109A65579EBFL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[4];
    boolean bool = false;
    String str;
    int i = (str = "èQÜã]rJãNI»ÂiÔH.DÀä°\024åËïýBê3a\0136ø¶sÊ\n æ;a|ç×\027Éï1XdX~Lq&\002^¬;¹ãz|¼0X-E\006â\013eeÙ¶æßBº¨").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4B6E;
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
        throw new RuntimeException("wtf/opal/ji", exception);
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
    //   65: ldc_w 'wtf/opal/ji'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6231;
    if (k[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = g[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])l.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          l.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/ji", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      k[i] = Integer.valueOf(j);
    } 
    return k[i].intValue();
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
    //   65: ldc_w 'wtf/opal/ji'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ji.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */