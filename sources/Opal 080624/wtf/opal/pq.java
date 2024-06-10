package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_332;
import net.minecraft.class_437;

public final class pq extends class_437 {
  private final pa g;
  
  private final lo D;
  
  private final bu G;
  
  private final List<xp> z;
  
  private final int s;
  
  private final int U;
  
  private boolean h;
  
  private final SortedMap<ki, List<String>> S;
  
  private static boolean y;
  
  private static final long a = on.a(-4498612277481088237L, -1960982778555108646L, MethodHandles.lookup().lookupClass()).a(160711413724538L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map i;
  
  public pq(short paramShort, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #48
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #32
    //   9: lshl
    //   10: bipush #16
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/pq.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: aload_0
    //   30: ldc ''
    //   32: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   35: invokespecial <init> : (Lnet/minecraft/class_2561;)V
    //   38: aload_0
    //   39: iconst_0
    //   40: anewarray java/lang/Object
    //   43: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   46: iconst_0
    //   47: anewarray java/lang/Object
    //   50: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   53: putfield g : Lwtf/opal/pa;
    //   56: aload_0
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   64: iconst_0
    //   65: anewarray java/lang/Object
    //   68: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/lo;
    //   71: putfield D : Lwtf/opal/lo;
    //   74: aload_0
    //   75: iconst_0
    //   76: anewarray java/lang/Object
    //   79: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   82: iconst_0
    //   83: anewarray java/lang/Object
    //   86: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/bu;
    //   89: putfield G : Lwtf/opal/bu;
    //   92: aload_0
    //   93: new java/util/ArrayList
    //   96: dup
    //   97: invokespecial <init> : ()V
    //   100: putfield z : Ljava/util/List;
    //   103: aload_0
    //   104: sipush #3357
    //   107: ldc2_w 2941712195896361303
    //   110: lload #4
    //   112: lxor
    //   113: <illegal opcode> j : (IJ)I
    //   118: putfield s : I
    //   121: aload_0
    //   122: sipush #28309
    //   125: ldc2_w 2070443422427914966
    //   128: lload #4
    //   130: lxor
    //   131: <illegal opcode> j : (IJ)I
    //   136: putfield U : I
    //   139: aload_0
    //   140: new java/util/TreeMap
    //   143: dup
    //   144: invokespecial <init> : ()V
    //   147: putfield S : Ljava/util/SortedMap;
    //   150: return
  }
  
  public void method_25394(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: getstatic wtf/opal/pq.a : J
    //   3: ldc2_w 67614411488273
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 16120739343572
    //   15: lxor
    //   16: lstore #7
    //   18: dup2
    //   19: ldc2_w 32753148133274
    //   22: lxor
    //   23: lstore #9
    //   25: pop2
    //   26: invokestatic h : ()Z
    //   29: aload_0
    //   30: aload_1
    //   31: iload_2
    //   32: iload_3
    //   33: fload #4
    //   35: invokespecial method_25394 : (Lnet/minecraft/class_332;IIF)V
    //   38: istore #11
    //   40: aload_0
    //   41: getfield G : Lwtf/opal/bu;
    //   44: iconst_1
    //   45: putfield R : Z
    //   48: aload_0
    //   49: iload #11
    //   51: ifne -> 186
    //   54: getfield h : Z
    //   57: ifne -> 124
    //   60: goto -> 67
    //   63: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   66: athrow
    //   67: iconst_0
    //   68: anewarray java/lang/Object
    //   71: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   74: iconst_0
    //   75: anewarray java/lang/Object
    //   78: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   81: iconst_0
    //   82: anewarray java/lang/Object
    //   85: invokevirtual y : ([Ljava/lang/Object;)Z
    //   88: ifeq -> 124
    //   91: goto -> 98
    //   94: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   97: athrow
    //   98: aload_0
    //   99: lload #7
    //   101: iconst_1
    //   102: anewarray java/lang/Object
    //   105: dup_x2
    //   106: dup_x2
    //   107: pop
    //   108: invokestatic valueOf : (J)Ljava/lang/Long;
    //   111: iconst_0
    //   112: swap
    //   113: aastore
    //   114: invokevirtual b : ([Ljava/lang/Object;)V
    //   117: goto -> 124
    //   120: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   123: athrow
    //   124: aload_0
    //   125: getfield g : Lwtf/opal/pa;
    //   128: aload_0
    //   129: aload_1
    //   130: <illegal opcode> run : (Lwtf/opal/pq;Lnet/minecraft/class_332;)Ljava/lang/Runnable;
    //   135: iconst_1
    //   136: anewarray java/lang/Object
    //   139: dup_x1
    //   140: swap
    //   141: iconst_0
    //   142: swap
    //   143: aastore
    //   144: invokevirtual T : ([Ljava/lang/Object;)V
    //   147: aload_0
    //   148: getfield z : Ljava/util/List;
    //   151: iload_2
    //   152: iload_3
    //   153: <illegal opcode> accept : (II)Ljava/util/function/Consumer;
    //   158: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   163: aload_0
    //   164: getfield g : Lwtf/opal/pa;
    //   167: lload #9
    //   169: iconst_1
    //   170: anewarray java/lang/Object
    //   173: dup_x2
    //   174: dup_x2
    //   175: pop
    //   176: invokestatic valueOf : (J)Ljava/lang/Long;
    //   179: iconst_0
    //   180: swap
    //   181: aastore
    //   182: invokevirtual J : ([Ljava/lang/Object;)V
    //   185: aload_0
    //   186: getfield G : Lwtf/opal/bu;
    //   189: iconst_0
    //   190: putfield R : Z
    //   193: invokestatic D : ()[Lwtf/opal/d;
    //   196: ifnull -> 223
    //   199: iload #11
    //   201: ifeq -> 219
    //   204: goto -> 211
    //   207: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   210: athrow
    //   211: iconst_0
    //   212: goto -> 220
    //   215: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   218: athrow
    //   219: iconst_1
    //   220: invokestatic H : (Z)V
    //   223: return
    // Exception table:
    //   from	to	target	type
    //   40	60	63	java/lang/IllegalStateException
    //   54	91	94	java/lang/IllegalStateException
    //   67	117	120	java/lang/IllegalStateException
    //   186	204	207	java/lang/IllegalStateException
    //   199	215	215	java/lang/IllegalStateException
  }
  
  public boolean method_25402(double paramDouble1, double paramDouble2, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield z : Ljava/util/List;
    //   4: aload_0
    //   5: dload_1
    //   6: dload_3
    //   7: <illegal opcode> accept : (Lwtf/opal/pq;DD)Ljava/util/function/Consumer;
    //   12: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   17: aload_0
    //   18: dload_1
    //   19: dload_3
    //   20: iload #5
    //   22: invokespecial method_25402 : (DDI)Z
    //   25: ireturn
  }
  
  public void method_25420(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat) {
    method_57728(paramclass_332, paramFloat);
    method_57734(paramFloat);
    method_57734(paramFloat);
    method_57735(paramclass_332);
  }
  
  protected void method_25426() {
    // Byte code:
    //   0: getstatic wtf/opal/pq.a : J
    //   3: ldc2_w 133306643200475
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 95768120357939
    //   13: lxor
    //   14: dup2
    //   15: bipush #48
    //   17: lushr
    //   18: l2i
    //   19: istore_3
    //   20: dup2
    //   21: bipush #16
    //   23: lshl
    //   24: bipush #32
    //   26: lushr
    //   27: l2i
    //   28: istore #4
    //   30: dup2
    //   31: bipush #48
    //   33: lshl
    //   34: bipush #48
    //   36: lushr
    //   37: l2i
    //   38: istore #5
    //   40: pop2
    //   41: pop2
    //   42: invokestatic D : ()Z
    //   45: aload_0
    //   46: invokespecial method_25426 : ()V
    //   49: istore #6
    //   51: aload_0
    //   52: getfield z : Ljava/util/List;
    //   55: invokeinterface clear : ()V
    //   60: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   63: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   66: invokevirtual method_4486 : ()I
    //   69: sipush #31198
    //   72: ldc2_w 2202518039061352650
    //   75: lload_1
    //   76: lxor
    //   77: <illegal opcode> j : (IJ)I
    //   82: isub
    //   83: i2f
    //   84: fconst_2
    //   85: fdiv
    //   86: ldc 5.0
    //   88: fadd
    //   89: fstore #7
    //   91: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   94: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   97: invokevirtual method_4502 : ()I
    //   100: sipush #14352
    //   103: ldc2_w 5336739163950223618
    //   106: lload_1
    //   107: lxor
    //   108: <illegal opcode> j : (IJ)I
    //   113: isub
    //   114: i2f
    //   115: fconst_2
    //   116: fdiv
    //   117: ldc_w 41.25
    //   120: fadd
    //   121: ldc_w 24.0
    //   124: fadd
    //   125: fstore #8
    //   127: iconst_3
    //   128: anewarray java/lang/String
    //   131: dup
    //   132: iconst_0
    //   133: sipush #21528
    //   136: ldc2_w 3296353549349253892
    //   139: lload_1
    //   140: lxor
    //   141: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   146: aastore
    //   147: dup
    //   148: iconst_1
    //   149: sipush #28680
    //   152: ldc2_w 6473218437473326871
    //   155: lload_1
    //   156: lxor
    //   157: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   162: aastore
    //   163: dup
    //   164: iconst_2
    //   165: sipush #26056
    //   168: ldc2_w 6210969216390972100
    //   171: lload_1
    //   172: lxor
    //   173: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   178: aastore
    //   179: astore #9
    //   181: iconst_0
    //   182: istore #10
    //   184: iload #10
    //   186: aload #9
    //   188: arraylength
    //   189: if_icmpge -> 277
    //   192: aload_0
    //   193: getfield z : Ljava/util/List;
    //   196: new wtf/opal/xp
    //   199: dup
    //   200: aload #9
    //   202: iload #10
    //   204: aaload
    //   205: fload #7
    //   207: fload #8
    //   209: iload #10
    //   211: sipush #16408
    //   214: ldc2_w 1837150105357969677
    //   217: lload_1
    //   218: lxor
    //   219: <illegal opcode> j : (IJ)I
    //   224: imul
    //   225: i2f
    //   226: fadd
    //   227: ldc_w 190.0
    //   230: iload_3
    //   231: i2c
    //   232: ldc_w 20.0
    //   235: iconst_m1
    //   236: iload #4
    //   238: iload #5
    //   240: i2c
    //   241: invokespecial <init> : (Ljava/lang/String;FFFCFIIC)V
    //   244: invokeinterface add : (Ljava/lang/Object;)Z
    //   249: pop
    //   250: iinc #10, 1
    //   253: iload #6
    //   255: ifeq -> 389
    //   258: iload #6
    //   260: ifne -> 184
    //   263: goto -> 270
    //   266: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   269: athrow
    //   270: iconst_3
    //   271: anewarray wtf/opal/d
    //   274: invokestatic p : ([Lwtf/opal/d;)V
    //   277: aload_0
    //   278: getfield z : Ljava/util/List;
    //   281: new wtf/opal/xp
    //   284: dup
    //   285: sipush #16373
    //   288: ldc2_w 1720220445960476901
    //   291: lload_1
    //   292: lxor
    //   293: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   298: fload #7
    //   300: fload #8
    //   302: ldc_w 75.0
    //   305: fadd
    //   306: ldc_w 92.5
    //   309: iload_3
    //   310: i2c
    //   311: ldc_w 20.0
    //   314: iconst_m1
    //   315: iload #4
    //   317: iload #5
    //   319: i2c
    //   320: invokespecial <init> : (Ljava/lang/String;FFFCFIIC)V
    //   323: invokeinterface add : (Ljava/lang/Object;)Z
    //   328: pop
    //   329: aload_0
    //   330: getfield z : Ljava/util/List;
    //   333: new wtf/opal/xp
    //   336: dup
    //   337: sipush #1932
    //   340: ldc2_w 1924233730289049751
    //   343: lload_1
    //   344: lxor
    //   345: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   350: fload #7
    //   352: ldc_w 95.0
    //   355: fadd
    //   356: ldc_w 2.5
    //   359: fadd
    //   360: fload #8
    //   362: ldc_w 75.0
    //   365: fadd
    //   366: ldc_w 92.5
    //   369: iload_3
    //   370: i2c
    //   371: ldc_w 20.0
    //   374: iconst_m1
    //   375: iload #4
    //   377: iload #5
    //   379: i2c
    //   380: invokespecial <init> : (Ljava/lang/String;FFFCFIIC)V
    //   383: invokeinterface add : (Ljava/lang/Object;)Z
    //   388: pop
    //   389: return
    // Exception table:
    //   from	to	target	type
    //   192	263	266	java/lang/IllegalStateException
  }
  
  private void b(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/pq.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 80054328703577
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic D : ()Z
    //   30: aload_0
    //   31: getfield S : Ljava/util/SortedMap;
    //   34: getstatic wtf/opal/ki.ADDITION : Lwtf/opal/ki;
    //   37: new java/util/ArrayList
    //   40: dup
    //   41: invokespecial <init> : ()V
    //   44: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   49: pop
    //   50: istore #6
    //   52: aload_0
    //   53: getfield S : Ljava/util/SortedMap;
    //   56: getstatic wtf/opal/ki.IMPROVEMENT_OR_FIX : Lwtf/opal/ki;
    //   59: new java/util/ArrayList
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   71: pop
    //   72: aload_0
    //   73: getfield S : Ljava/util/SortedMap;
    //   76: getstatic wtf/opal/ki.REMOVED : Lwtf/opal/ki;
    //   79: new java/util/ArrayList
    //   82: dup
    //   83: invokespecial <init> : ()V
    //   86: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   91: pop
    //   92: iconst_0
    //   93: anewarray java/lang/Object
    //   96: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   99: iconst_0
    //   100: anewarray java/lang/Object
    //   103: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   106: lload #4
    //   108: sipush #13148
    //   111: ldc2_w 3522088237358933395
    //   114: lload_2
    //   115: lxor
    //   116: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   121: iconst_2
    //   122: anewarray java/lang/Object
    //   125: dup_x1
    //   126: swap
    //   127: iconst_1
    //   128: swap
    //   129: aastore
    //   130: dup_x2
    //   131: dup_x2
    //   132: pop
    //   133: invokestatic valueOf : (J)Ljava/lang/Long;
    //   136: iconst_0
    //   137: swap
    //   138: aastore
    //   139: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   142: ldc_w '\\n'
    //   145: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   148: astore #7
    //   150: aload #7
    //   152: astore #8
    //   154: aload #8
    //   156: arraylength
    //   157: istore #9
    //   159: iconst_0
    //   160: istore #10
    //   162: iload #10
    //   164: iload #9
    //   166: if_icmpge -> 376
    //   169: aload #8
    //   171: iload #10
    //   173: aaload
    //   174: astore #11
    //   176: aload #11
    //   178: iconst_1
    //   179: invokevirtual substring : (I)Ljava/lang/String;
    //   182: invokevirtual strip : ()Ljava/lang/String;
    //   185: astore #12
    //   187: iload #6
    //   189: lload_2
    //   190: lconst_0
    //   191: lcmp
    //   192: ifle -> 200
    //   195: ifeq -> 387
    //   198: iload #6
    //   200: lload_2
    //   201: lconst_0
    //   202: lcmp
    //   203: ifle -> 373
    //   206: ifeq -> 371
    //   209: goto -> 216
    //   212: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   215: athrow
    //   216: aload #11
    //   218: iconst_0
    //   219: invokevirtual charAt : (I)C
    //   222: lload_2
    //   223: lconst_0
    //   224: lcmp
    //   225: ifle -> 293
    //   228: lookupswitch default -> 368, 33 -> 303, 43 -> 268, 45 -> 338
    //   264: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   267: athrow
    //   268: aload_0
    //   269: getfield S : Ljava/util/SortedMap;
    //   272: getstatic wtf/opal/ki.ADDITION : Lwtf/opal/ki;
    //   275: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   280: checkcast java/util/List
    //   283: aload #12
    //   285: invokeinterface add : (Ljava/lang/Object;)Z
    //   290: pop
    //   291: iload #6
    //   293: ifne -> 368
    //   296: goto -> 303
    //   299: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   302: athrow
    //   303: aload_0
    //   304: getfield S : Ljava/util/SortedMap;
    //   307: getstatic wtf/opal/ki.IMPROVEMENT_OR_FIX : Lwtf/opal/ki;
    //   310: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   315: checkcast java/util/List
    //   318: aload #12
    //   320: invokeinterface add : (Ljava/lang/Object;)Z
    //   325: pop
    //   326: iload #6
    //   328: ifne -> 368
    //   331: goto -> 338
    //   334: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   337: athrow
    //   338: aload_0
    //   339: getfield S : Ljava/util/SortedMap;
    //   342: getstatic wtf/opal/ki.REMOVED : Lwtf/opal/ki;
    //   345: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   350: checkcast java/util/List
    //   353: aload #12
    //   355: invokeinterface add : (Ljava/lang/Object;)Z
    //   360: pop
    //   361: goto -> 368
    //   364: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   367: athrow
    //   368: iinc #10, 1
    //   371: iload #6
    //   373: ifne -> 162
    //   376: aload_0
    //   377: iconst_1
    //   378: putfield h : Z
    //   381: lload_2
    //   382: lconst_0
    //   383: lcmp
    //   384: ifle -> 387
    //   387: return
    // Exception table:
    //   from	to	target	type
    //   187	209	212	java/lang/IllegalStateException
    //   198	264	264	java/lang/IllegalStateException
    //   216	296	299	java/lang/IllegalStateException
    //   268	331	334	java/lang/IllegalStateException
    //   303	361	364	java/lang/IllegalStateException
  }
  
  private void I(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_332
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_3
    //   18: pop
    //   19: getstatic wtf/opal/pq.a : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: lload_3
    //   26: dup2
    //   27: ldc2_w 115366924941390
    //   30: lxor
    //   31: lstore #5
    //   33: dup2
    //   34: ldc2_w 5046862509026
    //   37: lxor
    //   38: lstore #7
    //   40: dup2
    //   41: ldc2_w 66663913765300
    //   44: lxor
    //   45: lstore #9
    //   47: pop2
    //   48: invokestatic D : ()Z
    //   51: sipush #31955
    //   54: ldc2_w 5991425459252169273
    //   57: lload_3
    //   58: lxor
    //   59: <illegal opcode> j : (IJ)I
    //   64: istore #12
    //   66: sipush #315
    //   69: ldc2_w 402114030730672085
    //   72: lload_3
    //   73: lxor
    //   74: <illegal opcode> j : (IJ)I
    //   79: istore #13
    //   81: istore #11
    //   83: ldc_w 6.0
    //   86: fstore #14
    //   88: ldc_w 6.0
    //   91: fstore #15
    //   93: aload_0
    //   94: getfield S : Ljava/util/SortedMap;
    //   97: invokeinterface entrySet : ()Ljava/util/Set;
    //   102: invokeinterface iterator : ()Ljava/util/Iterator;
    //   107: astore #16
    //   109: aload #16
    //   111: invokeinterface hasNext : ()Z
    //   116: ifeq -> 711
    //   119: aload #16
    //   121: invokeinterface next : ()Ljava/lang/Object;
    //   126: checkcast java/util/Map$Entry
    //   129: astore #17
    //   131: aload #17
    //   133: invokeinterface getKey : ()Ljava/lang/Object;
    //   138: checkcast wtf/opal/ki
    //   141: astore #18
    //   143: aload_0
    //   144: getfield G : Lwtf/opal/bu;
    //   147: aload_2
    //   148: aload #18
    //   150: invokevirtual toString : ()Ljava/lang/String;
    //   153: sipush #25999
    //   156: ldc2_w 6247041427113680224
    //   159: lload_3
    //   160: lxor
    //   161: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   166: swap
    //   167: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   172: lload #5
    //   174: fload #14
    //   176: fload #15
    //   178: iload #12
    //   180: i2f
    //   181: iconst_m1
    //   182: bipush #7
    //   184: anewarray java/lang/Object
    //   187: dup_x1
    //   188: swap
    //   189: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   192: bipush #6
    //   194: swap
    //   195: aastore
    //   196: dup_x1
    //   197: swap
    //   198: invokestatic valueOf : (F)Ljava/lang/Float;
    //   201: iconst_5
    //   202: swap
    //   203: aastore
    //   204: dup_x1
    //   205: swap
    //   206: invokestatic valueOf : (F)Ljava/lang/Float;
    //   209: iconst_4
    //   210: swap
    //   211: aastore
    //   212: dup_x1
    //   213: swap
    //   214: invokestatic valueOf : (F)Ljava/lang/Float;
    //   217: iconst_3
    //   218: swap
    //   219: aastore
    //   220: dup_x2
    //   221: dup_x2
    //   222: pop
    //   223: invokestatic valueOf : (J)Ljava/lang/Long;
    //   226: iconst_2
    //   227: swap
    //   228: aastore
    //   229: dup_x1
    //   230: swap
    //   231: iconst_1
    //   232: swap
    //   233: aastore
    //   234: dup_x1
    //   235: swap
    //   236: iconst_0
    //   237: swap
    //   238: aastore
    //   239: invokevirtual R : ([Ljava/lang/Object;)V
    //   242: fload #15
    //   244: aload_0
    //   245: getfield G : Lwtf/opal/bu;
    //   248: aload #18
    //   250: invokevirtual toString : ()Ljava/lang/String;
    //   253: lload #7
    //   255: dup2_x1
    //   256: pop2
    //   257: iload #12
    //   259: i2f
    //   260: iconst_3
    //   261: anewarray java/lang/Object
    //   264: dup_x1
    //   265: swap
    //   266: invokestatic valueOf : (F)Ljava/lang/Float;
    //   269: iconst_2
    //   270: swap
    //   271: aastore
    //   272: dup_x1
    //   273: swap
    //   274: iconst_1
    //   275: swap
    //   276: aastore
    //   277: dup_x2
    //   278: dup_x2
    //   279: pop
    //   280: invokestatic valueOf : (J)Ljava/lang/Long;
    //   283: iconst_0
    //   284: swap
    //   285: aastore
    //   286: invokevirtual A : ([Ljava/lang/Object;)F
    //   289: ldc_w 3.0
    //   292: fadd
    //   293: fadd
    //   294: fstore #15
    //   296: aload #17
    //   298: invokeinterface getValue : ()Ljava/lang/Object;
    //   303: checkcast java/util/List
    //   306: invokeinterface iterator : ()Ljava/util/Iterator;
    //   311: astore #19
    //   313: aload #19
    //   315: invokeinterface hasNext : ()Z
    //   320: ifeq -> 692
    //   323: aload #19
    //   325: invokeinterface next : ()Ljava/lang/Object;
    //   330: checkcast java/lang/String
    //   333: astore #20
    //   335: aload #18
    //   337: iconst_0
    //   338: anewarray java/lang/Object
    //   341: invokevirtual C : ([Ljava/lang/Object;)C
    //   344: iload #11
    //   346: ifeq -> 116
    //   349: lload_3
    //   350: lconst_0
    //   351: lcmp
    //   352: iflt -> 320
    //   355: lookupswitch default -> 440, 33 -> 408, 43 -> 388, 45 -> 424
    //   388: sipush #31455
    //   391: ldc2_w 3461893155411296824
    //   394: lload_3
    //   395: lxor
    //   396: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   401: goto -> 476
    //   404: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   407: athrow
    //   408: sipush #9619
    //   411: ldc2_w 8212101804816489831
    //   414: lload_3
    //   415: lxor
    //   416: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   421: goto -> 476
    //   424: sipush #29190
    //   427: ldc2_w 4478285222792268525
    //   430: lload_3
    //   431: lxor
    //   432: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   437: goto -> 476
    //   440: new java/lang/IllegalStateException
    //   443: dup
    //   444: aload #18
    //   446: iconst_0
    //   447: anewarray java/lang/Object
    //   450: invokevirtual C : ([Ljava/lang/Object;)C
    //   453: sipush #1162
    //   456: ldc2_w 8724906226981731430
    //   459: lload_3
    //   460: lxor
    //   461: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   466: swap
    //   467: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;C)Ljava/lang/String;
    //   472: invokespecial <init> : (Ljava/lang/String;)V
    //   475: athrow
    //   476: astore #21
    //   478: aload #21
    //   480: aload #18
    //   482: iconst_0
    //   483: anewarray java/lang/Object
    //   486: invokevirtual C : ([Ljava/lang/Object;)C
    //   489: sipush #11730
    //   492: ldc2_w 1263843234013599034
    //   495: lload_3
    //   496: lxor
    //   497: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   502: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;CLjava/lang/String;)Ljava/lang/String;
    //   507: astore #21
    //   509: aload_0
    //   510: getfield G : Lwtf/opal/bu;
    //   513: aload_2
    //   514: aload #21
    //   516: aload #20
    //   518: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   523: fload #14
    //   525: fload #15
    //   527: iload #13
    //   529: i2f
    //   530: iconst_m1
    //   531: ldc_w 0.9
    //   534: iconst_2
    //   535: anewarray java/lang/Object
    //   538: dup_x1
    //   539: swap
    //   540: invokestatic valueOf : (F)Ljava/lang/Float;
    //   543: iconst_1
    //   544: swap
    //   545: aastore
    //   546: dup_x1
    //   547: swap
    //   548: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   551: iconst_0
    //   552: swap
    //   553: aastore
    //   554: invokestatic X : ([Ljava/lang/Object;)I
    //   557: iconst_1
    //   558: lload #9
    //   560: bipush #8
    //   562: anewarray java/lang/Object
    //   565: dup_x2
    //   566: dup_x2
    //   567: pop
    //   568: invokestatic valueOf : (J)Ljava/lang/Long;
    //   571: bipush #7
    //   573: swap
    //   574: aastore
    //   575: dup_x1
    //   576: swap
    //   577: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   580: bipush #6
    //   582: swap
    //   583: aastore
    //   584: dup_x1
    //   585: swap
    //   586: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   589: iconst_5
    //   590: swap
    //   591: aastore
    //   592: dup_x1
    //   593: swap
    //   594: invokestatic valueOf : (F)Ljava/lang/Float;
    //   597: iconst_4
    //   598: swap
    //   599: aastore
    //   600: dup_x1
    //   601: swap
    //   602: invokestatic valueOf : (F)Ljava/lang/Float;
    //   605: iconst_3
    //   606: swap
    //   607: aastore
    //   608: dup_x1
    //   609: swap
    //   610: invokestatic valueOf : (F)Ljava/lang/Float;
    //   613: iconst_2
    //   614: swap
    //   615: aastore
    //   616: dup_x1
    //   617: swap
    //   618: iconst_1
    //   619: swap
    //   620: aastore
    //   621: dup_x1
    //   622: swap
    //   623: iconst_0
    //   624: swap
    //   625: aastore
    //   626: invokevirtual B : ([Ljava/lang/Object;)V
    //   629: fload #15
    //   631: aload_0
    //   632: getfield G : Lwtf/opal/bu;
    //   635: aload #21
    //   637: aload #20
    //   639: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   644: lload #7
    //   646: dup2_x1
    //   647: pop2
    //   648: iload #13
    //   650: i2f
    //   651: iconst_3
    //   652: anewarray java/lang/Object
    //   655: dup_x1
    //   656: swap
    //   657: invokestatic valueOf : (F)Ljava/lang/Float;
    //   660: iconst_2
    //   661: swap
    //   662: aastore
    //   663: dup_x1
    //   664: swap
    //   665: iconst_1
    //   666: swap
    //   667: aastore
    //   668: dup_x2
    //   669: dup_x2
    //   670: pop
    //   671: invokestatic valueOf : (J)Ljava/lang/Long;
    //   674: iconst_0
    //   675: swap
    //   676: aastore
    //   677: invokevirtual A : ([Ljava/lang/Object;)F
    //   680: ldc_w 3.0
    //   683: fadd
    //   684: fadd
    //   685: fstore #15
    //   687: iload #11
    //   689: ifne -> 313
    //   692: fload #15
    //   694: ldc_w 10.0
    //   697: fadd
    //   698: fstore #15
    //   700: iload #11
    //   702: lload_3
    //   703: lconst_0
    //   704: lcmp
    //   705: iflt -> 116
    //   708: ifne -> 109
    //   711: return
    // Exception table:
    //   from	to	target	type
    //   349	404	404	java/lang/IllegalStateException
  }
  
  private void lambda$mouseClicked$2(double paramDouble1, double paramDouble2, xp paramxp) {
    // Byte code:
    //   0: getstatic wtf/opal/pq.a : J
    //   3: ldc2_w 25030243869756
    //   6: lxor
    //   7: lstore #6
    //   9: lload #6
    //   11: dup2
    //   12: ldc2_w 23482023358181
    //   15: lxor
    //   16: lstore #8
    //   18: dup2
    //   19: ldc2_w 108134683032485
    //   22: lxor
    //   23: dup2
    //   24: bipush #32
    //   26: lushr
    //   27: l2i
    //   28: istore #10
    //   30: dup2
    //   31: bipush #32
    //   33: lshl
    //   34: bipush #48
    //   36: lushr
    //   37: l2i
    //   38: istore #11
    //   40: dup2
    //   41: bipush #48
    //   43: lshl
    //   44: bipush #48
    //   46: lushr
    //   47: l2i
    //   48: istore #12
    //   50: pop2
    //   51: pop2
    //   52: invokestatic D : ()Z
    //   55: istore #13
    //   57: aload #5
    //   59: iload #13
    //   61: ifeq -> 191
    //   64: iconst_0
    //   65: anewarray java/lang/Object
    //   68: invokevirtual K : ([Ljava/lang/Object;)F
    //   71: lload #8
    //   73: dup2_x1
    //   74: pop2
    //   75: aload #5
    //   77: iconst_0
    //   78: anewarray java/lang/Object
    //   81: invokevirtual N : ([Ljava/lang/Object;)F
    //   84: aload #5
    //   86: iconst_0
    //   87: anewarray java/lang/Object
    //   90: invokevirtual H : ([Ljava/lang/Object;)F
    //   93: aload #5
    //   95: iconst_0
    //   96: anewarray java/lang/Object
    //   99: invokevirtual t : ([Ljava/lang/Object;)F
    //   102: dload_1
    //   103: dload_3
    //   104: bipush #7
    //   106: anewarray java/lang/Object
    //   109: dup_x2
    //   110: dup_x2
    //   111: pop
    //   112: invokestatic valueOf : (D)Ljava/lang/Double;
    //   115: bipush #6
    //   117: swap
    //   118: aastore
    //   119: dup_x2
    //   120: dup_x2
    //   121: pop
    //   122: invokestatic valueOf : (D)Ljava/lang/Double;
    //   125: iconst_5
    //   126: swap
    //   127: aastore
    //   128: dup_x1
    //   129: swap
    //   130: invokestatic valueOf : (F)Ljava/lang/Float;
    //   133: iconst_4
    //   134: swap
    //   135: aastore
    //   136: dup_x1
    //   137: swap
    //   138: invokestatic valueOf : (F)Ljava/lang/Float;
    //   141: iconst_3
    //   142: swap
    //   143: aastore
    //   144: dup_x1
    //   145: swap
    //   146: invokestatic valueOf : (F)Ljava/lang/Float;
    //   149: iconst_2
    //   150: swap
    //   151: aastore
    //   152: dup_x1
    //   153: swap
    //   154: invokestatic valueOf : (F)Ljava/lang/Float;
    //   157: iconst_1
    //   158: swap
    //   159: aastore
    //   160: dup_x2
    //   161: dup_x2
    //   162: pop
    //   163: invokestatic valueOf : (J)Ljava/lang/Long;
    //   166: iconst_0
    //   167: swap
    //   168: aastore
    //   169: invokestatic Z : ([Ljava/lang/Object;)Z
    //   172: ifeq -> 673
    //   175: goto -> 182
    //   178: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   181: athrow
    //   182: aload #5
    //   184: goto -> 191
    //   187: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   190: athrow
    //   191: iconst_0
    //   192: anewarray java/lang/Object
    //   195: invokevirtual w : ([Ljava/lang/Object;)Ljava/lang/String;
    //   198: astore #14
    //   200: iconst_m1
    //   201: istore #15
    //   203: aload #14
    //   205: invokevirtual hashCode : ()I
    //   208: iload #13
    //   210: ifeq -> 510
    //   213: lookupswitch default -> 508, -2064742086 -> 317, -1657361418 -> 366, -1500504759 -> 268, 2528879 -> 464, 415178366 -> 415
    //   264: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   267: athrow
    //   268: aload #14
    //   270: sipush #21132
    //   273: ldc2_w 6021901699918211187
    //   276: lload #6
    //   278: lxor
    //   279: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   284: invokevirtual equals : (Ljava/lang/Object;)Z
    //   287: iload #13
    //   289: ifeq -> 510
    //   292: goto -> 299
    //   295: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   298: athrow
    //   299: ifeq -> 508
    //   302: goto -> 309
    //   305: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   308: athrow
    //   309: iconst_0
    //   310: istore #15
    //   312: iload #13
    //   314: ifne -> 508
    //   317: aload #14
    //   319: sipush #22701
    //   322: ldc2_w 4796997956052263492
    //   325: lload #6
    //   327: lxor
    //   328: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   333: invokevirtual equals : (Ljava/lang/Object;)Z
    //   336: iload #13
    //   338: ifeq -> 510
    //   341: goto -> 348
    //   344: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   347: athrow
    //   348: ifeq -> 508
    //   351: goto -> 358
    //   354: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   357: athrow
    //   358: iconst_1
    //   359: istore #15
    //   361: iload #13
    //   363: ifne -> 508
    //   366: aload #14
    //   368: sipush #4671
    //   371: ldc2_w 3628370490713073874
    //   374: lload #6
    //   376: lxor
    //   377: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   382: invokevirtual equals : (Ljava/lang/Object;)Z
    //   385: iload #13
    //   387: ifeq -> 510
    //   390: goto -> 397
    //   393: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   396: athrow
    //   397: ifeq -> 508
    //   400: goto -> 407
    //   403: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   406: athrow
    //   407: iconst_2
    //   408: istore #15
    //   410: iload #13
    //   412: ifne -> 508
    //   415: aload #14
    //   417: sipush #17357
    //   420: ldc2_w 8783239783609612601
    //   423: lload #6
    //   425: lxor
    //   426: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   431: invokevirtual equals : (Ljava/lang/Object;)Z
    //   434: iload #13
    //   436: ifeq -> 510
    //   439: goto -> 446
    //   442: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   445: athrow
    //   446: ifeq -> 508
    //   449: goto -> 456
    //   452: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   455: athrow
    //   456: iconst_3
    //   457: istore #15
    //   459: iload #13
    //   461: ifne -> 508
    //   464: aload #14
    //   466: sipush #10464
    //   469: ldc2_w 4783143899048235550
    //   472: lload #6
    //   474: lxor
    //   475: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   480: invokevirtual equals : (Ljava/lang/Object;)Z
    //   483: iload #13
    //   485: ifeq -> 510
    //   488: goto -> 495
    //   491: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   494: athrow
    //   495: ifeq -> 508
    //   498: goto -> 505
    //   501: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   504: athrow
    //   505: iconst_4
    //   506: istore #15
    //   508: iload #15
    //   510: tableswitch default -> 673, 0 -> 544, 1 -> 570, 2 -> 596, 3 -> 628, 4 -> 660
    //   544: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   547: new net/minecraft/class_526
    //   550: dup
    //   551: aload_0
    //   552: invokespecial <init> : (Lnet/minecraft/class_437;)V
    //   555: invokevirtual method_1507 : (Lnet/minecraft/class_437;)V
    //   558: iload #13
    //   560: ifne -> 673
    //   563: goto -> 570
    //   566: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   569: athrow
    //   570: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   573: new net/minecraft/class_500
    //   576: dup
    //   577: aload_0
    //   578: invokespecial <init> : (Lnet/minecraft/class_437;)V
    //   581: invokevirtual method_1507 : (Lnet/minecraft/class_437;)V
    //   584: iload #13
    //   586: ifne -> 673
    //   589: goto -> 596
    //   592: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   595: athrow
    //   596: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   599: new wtf/opal/xq
    //   602: dup
    //   603: iload #10
    //   605: iload #11
    //   607: iload #12
    //   609: i2c
    //   610: invokespecial <init> : (IIC)V
    //   613: invokevirtual method_1507 : (Lnet/minecraft/class_437;)V
    //   616: iload #13
    //   618: ifne -> 673
    //   621: goto -> 628
    //   624: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   627: athrow
    //   628: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   631: new net/minecraft/class_429
    //   634: dup
    //   635: aload_0
    //   636: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   639: getfield field_1690 : Lnet/minecraft/class_315;
    //   642: invokespecial <init> : (Lnet/minecraft/class_437;Lnet/minecraft/class_315;)V
    //   645: invokevirtual method_1507 : (Lnet/minecraft/class_437;)V
    //   648: iload #13
    //   650: ifne -> 673
    //   653: goto -> 660
    //   656: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   659: athrow
    //   660: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   663: invokevirtual method_1490 : ()V
    //   666: goto -> 673
    //   669: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   672: athrow
    //   673: return
    // Exception table:
    //   from	to	target	type
    //   57	175	178	java/lang/IllegalStateException
    //   64	184	187	java/lang/IllegalStateException
    //   203	264	264	java/lang/IllegalStateException
    //   213	292	295	java/lang/IllegalStateException
    //   268	302	305	java/lang/IllegalStateException
    //   312	341	344	java/lang/IllegalStateException
    //   317	351	354	java/lang/IllegalStateException
    //   361	390	393	java/lang/IllegalStateException
    //   366	400	403	java/lang/IllegalStateException
    //   410	439	442	java/lang/IllegalStateException
    //   415	449	452	java/lang/IllegalStateException
    //   459	488	491	java/lang/IllegalStateException
    //   464	498	501	java/lang/IllegalStateException
    //   510	563	566	java/lang/IllegalStateException
    //   544	589	592	java/lang/IllegalStateException
    //   570	621	624	java/lang/IllegalStateException
    //   596	653	656	java/lang/IllegalStateException
    //   628	666	669	java/lang/IllegalStateException
  }
  
  private static void lambda$render$1(int paramInt1, int paramInt2, xp paramxp) {
    // Byte code:
    //   0: getstatic wtf/opal/pq.a : J
    //   3: ldc2_w 90689539084264
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 53451210587562
    //   13: lxor
    //   14: lstore #5
    //   16: dup2
    //   17: ldc2_w 18749174339906
    //   20: lxor
    //   21: lstore #7
    //   23: pop2
    //   24: aload_2
    //   25: iconst_0
    //   26: anewarray java/lang/Object
    //   29: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   32: iconst_0
    //   33: anewarray java/lang/Object
    //   36: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   39: lload #5
    //   41: sipush #11075
    //   44: ldc2_w 1399202409814997607
    //   47: lload_3
    //   48: lxor
    //   49: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   54: iconst_2
    //   55: anewarray java/lang/Object
    //   58: dup_x1
    //   59: swap
    //   60: iconst_1
    //   61: swap
    //   62: aastore
    //   63: dup_x2
    //   64: dup_x2
    //   65: pop
    //   66: invokestatic valueOf : (J)Ljava/lang/Long;
    //   69: iconst_0
    //   70: swap
    //   71: aastore
    //   72: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   75: iconst_1
    //   76: anewarray java/lang/Object
    //   79: dup_x1
    //   80: swap
    //   81: iconst_0
    //   82: swap
    //   83: aastore
    //   84: invokevirtual i : ([Ljava/lang/Object;)V
    //   87: aload_2
    //   88: iconst_0
    //   89: iconst_1
    //   90: anewarray java/lang/Object
    //   93: dup_x1
    //   94: swap
    //   95: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   98: iconst_0
    //   99: swap
    //   100: aastore
    //   101: invokevirtual V : ([Ljava/lang/Object;)V
    //   104: aload_2
    //   105: ldc_w 9.0
    //   108: iconst_1
    //   109: anewarray java/lang/Object
    //   112: dup_x1
    //   113: swap
    //   114: invokestatic valueOf : (F)Ljava/lang/Float;
    //   117: iconst_0
    //   118: swap
    //   119: aastore
    //   120: invokevirtual D : ([Ljava/lang/Object;)V
    //   123: aload_2
    //   124: iconst_m1
    //   125: iconst_1
    //   126: anewarray java/lang/Object
    //   129: dup_x1
    //   130: swap
    //   131: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   134: iconst_0
    //   135: swap
    //   136: aastore
    //   137: invokevirtual F : ([Ljava/lang/Object;)V
    //   140: aload_2
    //   141: iconst_1
    //   142: iconst_1
    //   143: anewarray java/lang/Object
    //   146: dup_x1
    //   147: swap
    //   148: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   151: iconst_0
    //   152: swap
    //   153: aastore
    //   154: invokevirtual v : ([Ljava/lang/Object;)V
    //   157: aload_2
    //   158: ldc_w 4.0
    //   161: iconst_1
    //   162: anewarray java/lang/Object
    //   165: dup_x1
    //   166: swap
    //   167: invokestatic valueOf : (F)Ljava/lang/Float;
    //   170: iconst_0
    //   171: swap
    //   172: aastore
    //   173: invokevirtual e : ([Ljava/lang/Object;)V
    //   176: aload_2
    //   177: iconst_m1
    //   178: ldc_w 0.9
    //   181: iconst_2
    //   182: anewarray java/lang/Object
    //   185: dup_x1
    //   186: swap
    //   187: invokestatic valueOf : (F)Ljava/lang/Float;
    //   190: iconst_1
    //   191: swap
    //   192: aastore
    //   193: dup_x1
    //   194: swap
    //   195: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   198: iconst_0
    //   199: swap
    //   200: aastore
    //   201: invokestatic X : ([Ljava/lang/Object;)I
    //   204: iconst_1
    //   205: anewarray java/lang/Object
    //   208: dup_x1
    //   209: swap
    //   210: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   213: iconst_0
    //   214: swap
    //   215: aastore
    //   216: invokevirtual F : ([Ljava/lang/Object;)V
    //   219: aload_2
    //   220: sipush #27849
    //   223: ldc2_w 8462360194581984235
    //   226: lload_3
    //   227: lxor
    //   228: <illegal opcode> j : (IJ)I
    //   233: iconst_1
    //   234: anewarray java/lang/Object
    //   237: dup_x1
    //   238: swap
    //   239: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   242: iconst_0
    //   243: swap
    //   244: aastore
    //   245: invokevirtual y : ([Ljava/lang/Object;)V
    //   248: aload_2
    //   249: iload_0
    //   250: iload_1
    //   251: lload #7
    //   253: iconst_3
    //   254: anewarray java/lang/Object
    //   257: dup_x2
    //   258: dup_x2
    //   259: pop
    //   260: invokestatic valueOf : (J)Ljava/lang/Long;
    //   263: iconst_2
    //   264: swap
    //   265: aastore
    //   266: dup_x1
    //   267: swap
    //   268: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   271: iconst_1
    //   272: swap
    //   273: aastore
    //   274: dup_x1
    //   275: swap
    //   276: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   279: iconst_0
    //   280: swap
    //   281: aastore
    //   282: invokevirtual Q : ([Ljava/lang/Object;)V
    //   285: return
  }
  
  private void lambda$render$0(class_332 paramclass_332) {
    // Byte code:
    //   0: getstatic wtf/opal/pq.a : J
    //   3: ldc2_w 24424372704058
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 69766806882564
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 18582543265222
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 67900115825492
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 8836044848330
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 128440588525944
    //   41: lxor
    //   42: lstore #12
    //   44: dup2
    //   45: ldc2_w 50759053894187
    //   48: lxor
    //   49: lstore #14
    //   51: dup2
    //   52: ldc2_w 105001870486726
    //   55: lxor
    //   56: lstore #16
    //   58: dup2
    //   59: ldc2_w 116827494579411
    //   62: lxor
    //   63: lstore #18
    //   65: pop2
    //   66: aload_0
    //   67: aload_1
    //   68: lload #6
    //   70: iconst_2
    //   71: anewarray java/lang/Object
    //   74: dup_x2
    //   75: dup_x2
    //   76: pop
    //   77: invokestatic valueOf : (J)Ljava/lang/Long;
    //   80: iconst_1
    //   81: swap
    //   82: aastore
    //   83: dup_x1
    //   84: swap
    //   85: iconst_0
    //   86: swap
    //   87: aastore
    //   88: invokevirtual I : ([Ljava/lang/Object;)V
    //   91: aload_0
    //   92: getfield g : Lwtf/opal/pa;
    //   95: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   98: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   101: invokevirtual method_4486 : ()I
    //   104: sipush #31198
    //   107: ldc2_w 2202406967433394731
    //   110: lload_2
    //   111: lxor
    //   112: <illegal opcode> j : (IJ)I
    //   117: isub
    //   118: i2f
    //   119: fconst_2
    //   120: fdiv
    //   121: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   124: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   127: invokevirtual method_4502 : ()I
    //   130: sipush #14352
    //   133: ldc2_w 5336634673319197667
    //   136: lload_2
    //   137: lxor
    //   138: <illegal opcode> j : (IJ)I
    //   143: isub
    //   144: i2f
    //   145: fconst_2
    //   146: fdiv
    //   147: ldc_w 200.0
    //   150: ldc_w 165.0
    //   153: ldc_w 6.0
    //   156: sipush #19158
    //   159: ldc2_w 6495730497512261921
    //   162: lload_2
    //   163: lxor
    //   164: <illegal opcode> j : (IJ)I
    //   169: lload #14
    //   171: bipush #7
    //   173: anewarray java/lang/Object
    //   176: dup_x2
    //   177: dup_x2
    //   178: pop
    //   179: invokestatic valueOf : (J)Ljava/lang/Long;
    //   182: bipush #6
    //   184: swap
    //   185: aastore
    //   186: dup_x1
    //   187: swap
    //   188: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   191: iconst_5
    //   192: swap
    //   193: aastore
    //   194: dup_x1
    //   195: swap
    //   196: invokestatic valueOf : (F)Ljava/lang/Float;
    //   199: iconst_4
    //   200: swap
    //   201: aastore
    //   202: dup_x1
    //   203: swap
    //   204: invokestatic valueOf : (F)Ljava/lang/Float;
    //   207: iconst_3
    //   208: swap
    //   209: aastore
    //   210: dup_x1
    //   211: swap
    //   212: invokestatic valueOf : (F)Ljava/lang/Float;
    //   215: iconst_2
    //   216: swap
    //   217: aastore
    //   218: dup_x1
    //   219: swap
    //   220: invokestatic valueOf : (F)Ljava/lang/Float;
    //   223: iconst_1
    //   224: swap
    //   225: aastore
    //   226: dup_x1
    //   227: swap
    //   228: invokestatic valueOf : (F)Ljava/lang/Float;
    //   231: iconst_0
    //   232: swap
    //   233: aastore
    //   234: invokevirtual M : ([Ljava/lang/Object;)V
    //   237: aload_0
    //   238: getfield g : Lwtf/opal/pa;
    //   241: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   244: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   247: invokevirtual method_4486 : ()I
    //   250: sipush #31198
    //   253: ldc2_w 2202406967433394731
    //   256: lload_2
    //   257: lxor
    //   258: <illegal opcode> j : (IJ)I
    //   263: isub
    //   264: i2f
    //   265: fconst_2
    //   266: fdiv
    //   267: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   270: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   273: invokevirtual method_4502 : ()I
    //   276: sipush #14352
    //   279: ldc2_w 5336634673319197667
    //   282: lload_2
    //   283: lxor
    //   284: <illegal opcode> j : (IJ)I
    //   289: isub
    //   290: i2f
    //   291: fconst_2
    //   292: fdiv
    //   293: ldc_w 200.0
    //   296: ldc_w 165.0
    //   299: lload #16
    //   301: ldc_w 6.0
    //   304: fconst_1
    //   305: iconst_0
    //   306: anewarray java/lang/Object
    //   309: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   312: iconst_0
    //   313: anewarray java/lang/Object
    //   316: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   319: lload #12
    //   321: sipush #5480
    //   324: ldc2_w 6970317335066155138
    //   327: lload_2
    //   328: lxor
    //   329: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   334: iconst_2
    //   335: anewarray java/lang/Object
    //   338: dup_x1
    //   339: swap
    //   340: iconst_1
    //   341: swap
    //   342: aastore
    //   343: dup_x2
    //   344: dup_x2
    //   345: pop
    //   346: invokestatic valueOf : (J)Ljava/lang/Long;
    //   349: iconst_0
    //   350: swap
    //   351: aastore
    //   352: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   355: invokestatic parseInt : (Ljava/lang/String;)I
    //   358: bipush #8
    //   360: anewarray java/lang/Object
    //   363: dup_x1
    //   364: swap
    //   365: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   368: bipush #7
    //   370: swap
    //   371: aastore
    //   372: dup_x1
    //   373: swap
    //   374: invokestatic valueOf : (F)Ljava/lang/Float;
    //   377: bipush #6
    //   379: swap
    //   380: aastore
    //   381: dup_x1
    //   382: swap
    //   383: invokestatic valueOf : (F)Ljava/lang/Float;
    //   386: iconst_5
    //   387: swap
    //   388: aastore
    //   389: dup_x2
    //   390: dup_x2
    //   391: pop
    //   392: invokestatic valueOf : (J)Ljava/lang/Long;
    //   395: iconst_4
    //   396: swap
    //   397: aastore
    //   398: dup_x1
    //   399: swap
    //   400: invokestatic valueOf : (F)Ljava/lang/Float;
    //   403: iconst_3
    //   404: swap
    //   405: aastore
    //   406: dup_x1
    //   407: swap
    //   408: invokestatic valueOf : (F)Ljava/lang/Float;
    //   411: iconst_2
    //   412: swap
    //   413: aastore
    //   414: dup_x1
    //   415: swap
    //   416: invokestatic valueOf : (F)Ljava/lang/Float;
    //   419: iconst_1
    //   420: swap
    //   421: aastore
    //   422: dup_x1
    //   423: swap
    //   424: invokestatic valueOf : (F)Ljava/lang/Float;
    //   427: iconst_0
    //   428: swap
    //   429: aastore
    //   430: invokevirtual G : ([Ljava/lang/Object;)V
    //   433: aload_0
    //   434: getfield D : Lwtf/opal/lo;
    //   437: lload #10
    //   439: sipush #3748
    //   442: ldc2_w 8425438030841512792
    //   445: lload_2
    //   446: lxor
    //   447: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   452: iconst_2
    //   453: anewarray java/lang/Object
    //   456: dup_x1
    //   457: swap
    //   458: iconst_1
    //   459: swap
    //   460: aastore
    //   461: dup_x2
    //   462: dup_x2
    //   463: pop
    //   464: invokestatic valueOf : (J)Ljava/lang/Long;
    //   467: iconst_0
    //   468: swap
    //   469: aastore
    //   470: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/dq;
    //   473: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   476: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   479: invokevirtual method_4486 : ()I
    //   482: sipush #31198
    //   485: ldc2_w 2202406967433394731
    //   488: lload_2
    //   489: lxor
    //   490: <illegal opcode> j : (IJ)I
    //   495: isub
    //   496: i2f
    //   497: fconst_2
    //   498: fdiv
    //   499: ldc_w 100.0
    //   502: fadd
    //   503: ldc_w 16.0
    //   506: fsub
    //   507: f2i
    //   508: i2f
    //   509: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   512: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   515: invokevirtual method_4502 : ()I
    //   518: sipush #14352
    //   521: ldc2_w 5336634673319197667
    //   524: lload_2
    //   525: lxor
    //   526: <illegal opcode> j : (IJ)I
    //   531: isub
    //   532: i2f
    //   533: fconst_2
    //   534: fdiv
    //   535: ldc_w 9.0
    //   538: fadd
    //   539: f2i
    //   540: i2f
    //   541: lload #4
    //   543: dup2_x1
    //   544: pop2
    //   545: ldc_w 32.0
    //   548: ldc_w 32.0
    //   551: iconst_5
    //   552: anewarray java/lang/Object
    //   555: dup_x1
    //   556: swap
    //   557: invokestatic valueOf : (F)Ljava/lang/Float;
    //   560: iconst_4
    //   561: swap
    //   562: aastore
    //   563: dup_x1
    //   564: swap
    //   565: invokestatic valueOf : (F)Ljava/lang/Float;
    //   568: iconst_3
    //   569: swap
    //   570: aastore
    //   571: dup_x1
    //   572: swap
    //   573: invokestatic valueOf : (F)Ljava/lang/Float;
    //   576: iconst_2
    //   577: swap
    //   578: aastore
    //   579: dup_x2
    //   580: dup_x2
    //   581: pop
    //   582: invokestatic valueOf : (J)Ljava/lang/Long;
    //   585: iconst_1
    //   586: swap
    //   587: aastore
    //   588: dup_x1
    //   589: swap
    //   590: invokestatic valueOf : (F)Ljava/lang/Float;
    //   593: iconst_0
    //   594: swap
    //   595: aastore
    //   596: invokevirtual q : ([Ljava/lang/Object;)V
    //   599: aload_0
    //   600: getfield g : Lwtf/opal/pa;
    //   603: iconst_0
    //   604: anewarray java/lang/Object
    //   607: invokevirtual y : ([Ljava/lang/Object;)J
    //   610: lstore #20
    //   612: aload_0
    //   613: getfield G : Lwtf/opal/bu;
    //   616: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   619: sipush #19360
    //   622: ldc2_w 2626173784250861147
    //   625: lload_2
    //   626: lxor
    //   627: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   632: ldc_w 12.0
    //   635: lload #8
    //   637: iconst_4
    //   638: anewarray java/lang/Object
    //   641: dup_x2
    //   642: dup_x2
    //   643: pop
    //   644: invokestatic valueOf : (J)Ljava/lang/Long;
    //   647: iconst_3
    //   648: swap
    //   649: aastore
    //   650: dup_x1
    //   651: swap
    //   652: invokestatic valueOf : (F)Ljava/lang/Float;
    //   655: iconst_2
    //   656: swap
    //   657: aastore
    //   658: dup_x1
    //   659: swap
    //   660: iconst_1
    //   661: swap
    //   662: aastore
    //   663: dup_x1
    //   664: swap
    //   665: iconst_0
    //   666: swap
    //   667: aastore
    //   668: invokevirtual p : ([Ljava/lang/Object;)F
    //   671: fstore #22
    //   673: lload #20
    //   675: ldc_w -0.3
    //   678: invokestatic nvgTextLetterSpacing : (JF)V
    //   681: aload_0
    //   682: getfield G : Lwtf/opal/bu;
    //   685: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   688: aload_1
    //   689: sipush #21663
    //   692: ldc2_w 143698217201654122
    //   695: lload_2
    //   696: lxor
    //   697: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   702: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   705: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   708: invokevirtual method_4486 : ()I
    //   711: sipush #31198
    //   714: ldc2_w 2202406967433394731
    //   717: lload_2
    //   718: lxor
    //   719: <illegal opcode> j : (IJ)I
    //   724: isub
    //   725: i2f
    //   726: fconst_2
    //   727: fdiv
    //   728: ldc_w 100.0
    //   731: fadd
    //   732: fload #22
    //   734: fconst_2
    //   735: fdiv
    //   736: fsub
    //   737: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   740: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   743: invokevirtual method_4502 : ()I
    //   746: sipush #14352
    //   749: ldc2_w 5336634673319197667
    //   752: lload_2
    //   753: lxor
    //   754: <illegal opcode> j : (IJ)I
    //   759: isub
    //   760: i2f
    //   761: fconst_2
    //   762: fdiv
    //   763: ldc_w 44.0
    //   766: fadd
    //   767: lload #18
    //   769: dup2_x1
    //   770: pop2
    //   771: ldc_w 12.0
    //   774: iconst_m1
    //   775: iconst_1
    //   776: bipush #9
    //   778: anewarray java/lang/Object
    //   781: dup_x1
    //   782: swap
    //   783: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   786: bipush #8
    //   788: swap
    //   789: aastore
    //   790: dup_x1
    //   791: swap
    //   792: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   795: bipush #7
    //   797: swap
    //   798: aastore
    //   799: dup_x1
    //   800: swap
    //   801: invokestatic valueOf : (F)Ljava/lang/Float;
    //   804: bipush #6
    //   806: swap
    //   807: aastore
    //   808: dup_x1
    //   809: swap
    //   810: invokestatic valueOf : (F)Ljava/lang/Float;
    //   813: iconst_5
    //   814: swap
    //   815: aastore
    //   816: dup_x2
    //   817: dup_x2
    //   818: pop
    //   819: invokestatic valueOf : (J)Ljava/lang/Long;
    //   822: iconst_4
    //   823: swap
    //   824: aastore
    //   825: dup_x1
    //   826: swap
    //   827: invokestatic valueOf : (F)Ljava/lang/Float;
    //   830: iconst_3
    //   831: swap
    //   832: aastore
    //   833: dup_x1
    //   834: swap
    //   835: iconst_2
    //   836: swap
    //   837: aastore
    //   838: dup_x1
    //   839: swap
    //   840: iconst_1
    //   841: swap
    //   842: aastore
    //   843: dup_x1
    //   844: swap
    //   845: iconst_0
    //   846: swap
    //   847: aastore
    //   848: invokevirtual H : ([Ljava/lang/Object;)V
    //   851: lload #20
    //   853: fconst_0
    //   854: invokestatic nvgTextLetterSpacing : (JF)V
    //   857: return
  }
  
  public static void H(boolean paramBoolean) {
    y = paramBoolean;
  }
  
  public static boolean D() {
    return y;
  }
  
  public static boolean h() {
    boolean bool = D();
    try {
      if (!bool)
        return true; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    H(true);
    long l = a ^ 0x69E7E32D7EC2L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[22];
    boolean bool = false;
    String str;
    int i = (str = "rv%H\031;à!\031~g±;ñ º2ø\023K{\bãèrM®C\0274\031\t\033Dþýb@ÙÀ0Á ü&32l#\004\031yÁ<}14\0031¦ô1Þx¾\006w\024ìá8~¢H\017­\025\002i´eí æ*¨/¶¢:zsbÙy´\035'»ñ'ñU\023Ð¾T³\005\003D\025þþÊ\027­áÕÕ}\027\020*\003?ó!õý\0232TÛK$\026ï\020U\017\013]ÅL>`\bd¤¼¹ºb\030n£u¾á :\004zY­;½\f\007«C&ð2·|Õ\020Â³O\004e®/J[þvà\013&\020õÌåE®Sl\034hX¡?\000\022z\020PE\034&M¢§'\036ñ-\rW\020&\bT\007µ\007ÑYëùÁ\nphw0.ÆË.ÕÀ\035ëø½$7ØoyXb±óNáC\f\005a\037Ü'÷f£1èØì1c]Ï.\036\020Ùà÷«ö3ré-Ñåá1e\020=lò\032qä4\026Óõ`\020y4ò\020\006~Riû´o¼­°ÁÀd0\020ì¥Ös($\036Ø»\001JN?Úë V}!üäÄ¢»N<?M\024EÉÀÎ\021:×x3v¥§\r5P Aã¬ø<\013tÜ,\rPê\bqex\023ôóÚ~á{æÜÐ)ñ\030czwÉ%t\031còq±¬®(§£2ZÝ\023[\020Ì\"ñc\000`\031r\037·é¾W6").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static IllegalStateException a(IllegalStateException paramIllegalStateException) {
    return paramIllegalStateException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x61B;
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
        throw new RuntimeException("wtf/opal/pq", exception);
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
    //   66: ldc_w 'wtf/opal/pq'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x814;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])i.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          i.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/pq", exception);
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
    //   66: ldc_w 'wtf/opal/pq'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pq.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */