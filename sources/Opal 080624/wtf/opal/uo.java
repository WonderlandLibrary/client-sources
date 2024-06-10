package wtf.opal;

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
import net.minecraft.class_2596;
import net.minecraft.class_2743;
import net.minecraft.class_2828;
import wtf.opal.mixin.PlayerMoveC2SPacketAccessor;

public final class uo extends u_<jm> {
  public final kt b;
  
  public final kt f;
  
  public final ke i;
  
  public final ke D;
  
  public final ke d;
  
  public final ke M;
  
  public final ke e;
  
  public int h;
  
  public int x;
  
  public int W;
  
  public int r;
  
  private boolean R;
  
  private double n;
  
  private final gm<b6> I;
  
  private final gm<lu> s;
  
  private final gm<lb> o;
  
  private final gm<la> y;
  
  private final gm<l3> w;
  
  private static int u;
  
  private static final long a = on.a(3851301314204173965L, -4063429169726952608L, MethodHandles.lookup().lookupClass()).a(56141059951241L);
  
  private static final String[] c;
  
  private static final String[] g;
  
  private static final Map j = new HashMap<>(13);
  
  private static final long[] k;
  
  private static final Integer[] m;
  
  private static final Map p;
  
  public uo(short paramShort, char paramChar, jm paramjm, int paramInt) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #48
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #48
    //   9: lshl
    //   10: bipush #16
    //   12: lushr
    //   13: lor
    //   14: iload #4
    //   16: i2l
    //   17: bipush #32
    //   19: lshl
    //   20: bipush #32
    //   22: lushr
    //   23: lor
    //   24: getstatic wtf/opal/uo.a : J
    //   27: lxor
    //   28: lstore #5
    //   30: lload #5
    //   32: dup2
    //   33: ldc2_w 124309007405724
    //   36: lxor
    //   37: lstore #7
    //   39: dup2
    //   40: ldc2_w 139571052384182
    //   43: lxor
    //   44: lstore #9
    //   46: dup2
    //   47: ldc2_w 88606552215459
    //   50: lxor
    //   51: lstore #11
    //   53: pop2
    //   54: invokestatic k : ()I
    //   57: aload_0
    //   58: aload_3
    //   59: invokespecial <init> : (Lwtf/opal/d;)V
    //   62: aload_0
    //   63: new wtf/opal/kt
    //   66: dup
    //   67: sipush #24195
    //   70: ldc2_w 4761832007405622606
    //   73: lload #5
    //   75: lxor
    //   76: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   81: aload_0
    //   82: ldc2_w 4.0
    //   85: dconst_0
    //   86: ldc2_w 8.0
    //   89: ldc2_w 0.5
    //   92: lload #7
    //   94: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/u_;DDDDJ)V
    //   97: putfield b : Lwtf/opal/kt;
    //   100: aload_0
    //   101: new wtf/opal/kt
    //   104: dup
    //   105: sipush #19964
    //   108: ldc2_w 8556671343817803315
    //   111: lload #5
    //   113: lxor
    //   114: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   119: aload_0
    //   120: ldc2_w 0.7
    //   123: ldc2_w 0.1
    //   126: dconst_1
    //   127: ldc2_w 0.05
    //   130: lload #7
    //   132: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/u_;DDDDJ)V
    //   135: putfield f : Lwtf/opal/kt;
    //   138: aload_0
    //   139: new wtf/opal/ke
    //   142: dup
    //   143: lload #11
    //   145: sipush #11653
    //   148: ldc2_w 8085571423308359245
    //   151: lload #5
    //   153: lxor
    //   154: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   159: aload_0
    //   160: iconst_0
    //   161: invokespecial <init> : (JLjava/lang/String;Lwtf/opal/u_;Z)V
    //   164: putfield i : Lwtf/opal/ke;
    //   167: istore #13
    //   169: aload_0
    //   170: new wtf/opal/ke
    //   173: dup
    //   174: lload #11
    //   176: sipush #20163
    //   179: ldc2_w 2399380146622022920
    //   182: lload #5
    //   184: lxor
    //   185: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   190: aload_0
    //   191: iconst_0
    //   192: invokespecial <init> : (JLjava/lang/String;Lwtf/opal/u_;Z)V
    //   195: putfield D : Lwtf/opal/ke;
    //   198: aload_0
    //   199: new wtf/opal/ke
    //   202: dup
    //   203: lload #11
    //   205: sipush #20403
    //   208: ldc2_w 2326656603315848319
    //   211: lload #5
    //   213: lxor
    //   214: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   219: aload_0
    //   220: iconst_0
    //   221: invokespecial <init> : (JLjava/lang/String;Lwtf/opal/u_;Z)V
    //   224: putfield d : Lwtf/opal/ke;
    //   227: aload_0
    //   228: new wtf/opal/ke
    //   231: dup
    //   232: lload #11
    //   234: sipush #26812
    //   237: ldc2_w 2283815977143493493
    //   240: lload #5
    //   242: lxor
    //   243: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   248: aload_0
    //   249: iconst_0
    //   250: invokespecial <init> : (JLjava/lang/String;Lwtf/opal/u_;Z)V
    //   253: putfield M : Lwtf/opal/ke;
    //   256: aload_0
    //   257: new wtf/opal/ke
    //   260: dup
    //   261: lload #11
    //   263: sipush #27761
    //   266: ldc2_w 6638695962230843323
    //   269: lload #5
    //   271: lxor
    //   272: <illegal opcode> m : (IJ)Ljava/lang/String;
    //   277: aload_0
    //   278: iconst_0
    //   279: invokespecial <init> : (JLjava/lang/String;Lwtf/opal/u_;Z)V
    //   282: putfield e : Lwtf/opal/ke;
    //   285: aload_0
    //   286: aload_0
    //   287: <illegal opcode> H : (Lwtf/opal/uo;)Lwtf/opal/gm;
    //   292: putfield I : Lwtf/opal/gm;
    //   295: aload_0
    //   296: aload_0
    //   297: <illegal opcode> H : (Lwtf/opal/uo;)Lwtf/opal/gm;
    //   302: putfield s : Lwtf/opal/gm;
    //   305: aload_0
    //   306: aload_0
    //   307: <illegal opcode> H : (Lwtf/opal/uo;)Lwtf/opal/gm;
    //   312: putfield o : Lwtf/opal/gm;
    //   315: aload_0
    //   316: aload_0
    //   317: <illegal opcode> H : (Lwtf/opal/uo;)Lwtf/opal/gm;
    //   322: putfield y : Lwtf/opal/gm;
    //   325: aload_0
    //   326: aload_0
    //   327: <illegal opcode> H : (Lwtf/opal/uo;)Lwtf/opal/gm;
    //   332: putfield w : Lwtf/opal/gm;
    //   335: aload_0
    //   336: getfield b : Lwtf/opal/kt;
    //   339: aload_0
    //   340: getfield D : Lwtf/opal/ke;
    //   343: aload_0
    //   344: <illegal opcode> test : (Lwtf/opal/uo;)Ljava/util/function/Predicate;
    //   349: lload #9
    //   351: dup2_x1
    //   352: pop2
    //   353: iconst_3
    //   354: anewarray java/lang/Object
    //   357: dup_x1
    //   358: swap
    //   359: iconst_2
    //   360: swap
    //   361: aastore
    //   362: dup_x2
    //   363: dup_x2
    //   364: pop
    //   365: invokestatic valueOf : (J)Ljava/lang/Long;
    //   368: iconst_1
    //   369: swap
    //   370: aastore
    //   371: dup_x1
    //   372: swap
    //   373: iconst_0
    //   374: swap
    //   375: aastore
    //   376: invokevirtual C : ([Ljava/lang/Object;)V
    //   379: aload_0
    //   380: getfield f : Lwtf/opal/kt;
    //   383: aload_0
    //   384: getfield e : Lwtf/opal/ke;
    //   387: aload_0
    //   388: <illegal opcode> test : (Lwtf/opal/uo;)Ljava/util/function/Predicate;
    //   393: lload #9
    //   395: dup2_x1
    //   396: pop2
    //   397: iconst_3
    //   398: anewarray java/lang/Object
    //   401: dup_x1
    //   402: swap
    //   403: iconst_2
    //   404: swap
    //   405: aastore
    //   406: dup_x2
    //   407: dup_x2
    //   408: pop
    //   409: invokestatic valueOf : (J)Ljava/lang/Long;
    //   412: iconst_1
    //   413: swap
    //   414: aastore
    //   415: dup_x1
    //   416: swap
    //   417: iconst_0
    //   418: swap
    //   419: aastore
    //   420: invokevirtual C : ([Ljava/lang/Object;)V
    //   423: iload #13
    //   425: ifne -> 442
    //   428: iconst_5
    //   429: anewarray wtf/opal/d
    //   432: invokestatic p : ([Lwtf/opal/d;)V
    //   435: goto -> 442
    //   438: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   441: athrow
    //   442: return
    // Exception table:
    //   from	to	target	type
    //   169	435	438	wtf/opal/x5
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return bn.WATCHDOG;
  }
  
  public void s(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: lload_2
    //   13: dup2
    //   14: ldc2_w 0
    //   17: lxor
    //   18: lstore #4
    //   20: pop2
    //   21: invokestatic k : ()I
    //   24: aload_0
    //   25: iconst_0
    //   26: putfield h : I
    //   29: istore #6
    //   31: aload_0
    //   32: sipush #5841
    //   35: ldc2_w 4322046502158734738
    //   38: lload_2
    //   39: lxor
    //   40: <illegal opcode> q : (IJ)I
    //   45: putfield W : I
    //   48: aload_0
    //   49: sipush #27616
    //   52: ldc2_w 7239535356732139691
    //   55: lload_2
    //   56: lxor
    //   57: <illegal opcode> q : (IJ)I
    //   62: putfield x : I
    //   65: aload_0
    //   66: sipush #27616
    //   69: ldc2_w 7239535356732139691
    //   72: lload_2
    //   73: lxor
    //   74: <illegal opcode> q : (IJ)I
    //   79: putfield r : I
    //   82: aload_0
    //   83: ldc2_w 1.0E-10
    //   86: putfield n : D
    //   89: aload_0
    //   90: iconst_0
    //   91: putfield R : Z
    //   94: aload_0
    //   95: lload #4
    //   97: iconst_1
    //   98: anewarray java/lang/Object
    //   101: dup_x2
    //   102: dup_x2
    //   103: pop
    //   104: invokestatic valueOf : (J)Ljava/lang/Long;
    //   107: iconst_0
    //   108: swap
    //   109: aastore
    //   110: invokespecial s : ([Ljava/lang/Object;)V
    //   113: invokestatic D : ()[Lwtf/opal/d;
    //   116: ifnull -> 134
    //   119: iinc #6, 1
    //   122: iload #6
    //   124: invokestatic g : (I)V
    //   127: goto -> 134
    //   130: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   133: athrow
    //   134: return
    // Exception table:
    //   from	to	target	type
    //   31	127	130	wtf/opal/x5
  }
  
  public boolean Y(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/uo.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic e : ()I
    //   21: istore #4
    //   23: aload_0
    //   24: getfield i : Lwtf/opal/ke;
    //   27: invokevirtual z : ()Ljava/lang/Object;
    //   30: checkcast java/lang/Boolean
    //   33: invokevirtual booleanValue : ()Z
    //   36: iload #4
    //   38: ifne -> 70
    //   41: ifeq -> 89
    //   44: goto -> 51
    //   47: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   50: athrow
    //   51: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   54: getfield field_1724 : Lnet/minecraft/class_746;
    //   57: getstatic net/minecraft/class_1294.field_5913 : Lnet/minecraft/class_6880;
    //   60: invokevirtual method_6059 : (Lnet/minecraft/class_6880;)Z
    //   63: goto -> 70
    //   66: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   69: athrow
    //   70: iload #4
    //   72: ifne -> 86
    //   75: ifne -> 89
    //   78: goto -> 85
    //   81: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: iconst_1
    //   86: goto -> 90
    //   89: iconst_0
    //   90: ireturn
    // Exception table:
    //   from	to	target	type
    //   23	44	47	wtf/opal/x5
    //   41	63	66	wtf/opal/x5
    //   70	78	81	wtf/opal/x5
  }
  
  private void lambda$new$6(l3 paraml3) {
    // Byte code:
    //   0: getstatic wtf/opal/uo.a : J
    //   3: ldc2_w 58053595484264
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 56109603541612
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 86557781122006
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic e : ()I
    //   27: istore #8
    //   29: aload_0
    //   30: getfield G : Lwtf/opal/d;
    //   33: checkcast wtf/opal/jm
    //   36: iload #8
    //   38: ifne -> 103
    //   41: lload #4
    //   43: iconst_1
    //   44: anewarray java/lang/Object
    //   47: dup_x2
    //   48: dup_x2
    //   49: pop
    //   50: invokestatic valueOf : (J)Ljava/lang/Long;
    //   53: iconst_0
    //   54: swap
    //   55: aastore
    //   56: invokevirtual g : ([Ljava/lang/Object;)Z
    //   59: ifne -> 74
    //   62: goto -> 69
    //   65: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: return
    //   70: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: iconst_0
    //   75: anewarray java/lang/Object
    //   78: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   81: iconst_0
    //   82: anewarray java/lang/Object
    //   85: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   88: ldc_w wtf/opal/xw
    //   91: iconst_1
    //   92: anewarray java/lang/Object
    //   95: dup_x1
    //   96: swap
    //   97: iconst_0
    //   98: swap
    //   99: aastore
    //   100: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   103: checkcast wtf/opal/xw
    //   106: astore #9
    //   108: iconst_0
    //   109: anewarray java/lang/Object
    //   112: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   115: iconst_0
    //   116: anewarray java/lang/Object
    //   119: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   122: ldc_w wtf/opal/jw
    //   125: iconst_1
    //   126: anewarray java/lang/Object
    //   129: dup_x1
    //   130: swap
    //   131: iconst_0
    //   132: swap
    //   133: aastore
    //   134: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   137: checkcast wtf/opal/jw
    //   140: astore #10
    //   142: aload_0
    //   143: lload #6
    //   145: iconst_1
    //   146: anewarray java/lang/Object
    //   149: dup_x2
    //   150: dup_x2
    //   151: pop
    //   152: invokestatic valueOf : (J)Ljava/lang/Long;
    //   155: iconst_0
    //   156: swap
    //   157: aastore
    //   158: invokevirtual Y : ([Ljava/lang/Object;)Z
    //   161: iload #8
    //   163: ifne -> 181
    //   166: ifne -> 177
    //   169: goto -> 176
    //   172: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: return
    //   177: aload_0
    //   178: getfield r : I
    //   181: iload #8
    //   183: ifne -> 223
    //   186: sipush #2917
    //   189: ldc2_w 223949313524744975
    //   192: lload_2
    //   193: lxor
    //   194: <illegal opcode> q : (IJ)I
    //   199: if_icmpge -> 214
    //   202: goto -> 209
    //   205: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   208: athrow
    //   209: return
    //   210: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   213: athrow
    //   214: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   217: getfield field_1724 : Lnet/minecraft/class_746;
    //   220: getfield field_6235 : I
    //   223: iload #8
    //   225: ifne -> 362
    //   228: ifeq -> 358
    //   231: goto -> 238
    //   234: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   237: athrow
    //   238: aload_0
    //   239: iload #8
    //   241: ifne -> 341
    //   244: goto -> 251
    //   247: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   250: athrow
    //   251: getfield D : Lwtf/opal/ke;
    //   254: invokevirtual z : ()Ljava/lang/Object;
    //   257: checkcast java/lang/Boolean
    //   260: invokevirtual booleanValue : ()Z
    //   263: ifne -> 340
    //   266: goto -> 273
    //   269: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   272: athrow
    //   273: aload #10
    //   275: iconst_0
    //   276: anewarray java/lang/Object
    //   279: invokevirtual D : ([Ljava/lang/Object;)Z
    //   282: iload #8
    //   284: ifne -> 362
    //   287: goto -> 294
    //   290: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   293: athrow
    //   294: ifeq -> 358
    //   297: goto -> 304
    //   300: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   303: athrow
    //   304: aload #10
    //   306: getfield R : Lwtf/opal/ke;
    //   309: invokevirtual z : ()Ljava/lang/Object;
    //   312: checkcast java/lang/Boolean
    //   315: invokevirtual booleanValue : ()Z
    //   318: iload #8
    //   320: ifne -> 362
    //   323: goto -> 330
    //   326: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   329: athrow
    //   330: ifne -> 358
    //   333: goto -> 340
    //   336: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   339: athrow
    //   340: aload_0
    //   341: sipush #27616
    //   344: ldc2_w 7239485351239590796
    //   347: lload_2
    //   348: lxor
    //   349: <illegal opcode> q : (IJ)I
    //   354: putfield x : I
    //   357: return
    //   358: aload_0
    //   359: getfield h : I
    //   362: iload #8
    //   364: ifne -> 399
    //   367: iconst_1
    //   368: if_icmplt -> 414
    //   371: goto -> 378
    //   374: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   377: athrow
    //   378: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   381: getfield field_1724 : Lnet/minecraft/class_746;
    //   384: dconst_0
    //   385: ldc2_w 1.5
    //   388: dconst_0
    //   389: invokevirtual method_5654 : (DDD)Z
    //   392: goto -> 399
    //   395: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   398: athrow
    //   399: iload #8
    //   401: ifne -> 424
    //   404: ifne -> 415
    //   407: goto -> 414
    //   410: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   413: athrow
    //   414: return
    //   415: aload #9
    //   417: iconst_0
    //   418: anewarray java/lang/Object
    //   421: invokevirtual D : ([Ljava/lang/Object;)Z
    //   424: iload #8
    //   426: ifne -> 757
    //   429: ifeq -> 746
    //   432: goto -> 439
    //   435: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   438: athrow
    //   439: aload #9
    //   441: iconst_0
    //   442: anewarray java/lang/Object
    //   445: invokevirtual s : ([Ljava/lang/Object;)Z
    //   448: iload #8
    //   450: ifne -> 757
    //   453: goto -> 460
    //   456: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   459: athrow
    //   460: ifne -> 746
    //   463: goto -> 470
    //   466: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   469: athrow
    //   470: aload_0
    //   471: getfield x : I
    //   474: iconst_3
    //   475: iload #8
    //   477: ifne -> 558
    //   480: goto -> 487
    //   483: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   486: athrow
    //   487: if_icmpne -> 553
    //   490: goto -> 497
    //   493: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   496: athrow
    //   497: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   500: getfield field_1724 : Lnet/minecraft/class_746;
    //   503: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   506: getfield field_1724 : Lnet/minecraft/class_746;
    //   509: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   512: invokevirtual method_10216 : ()D
    //   515: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   518: getfield field_1724 : Lnet/minecraft/class_746;
    //   521: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   524: invokevirtual method_10214 : ()D
    //   527: ldc2_w 0.004999999888241291
    //   530: dsub
    //   531: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   534: getfield field_1724 : Lnet/minecraft/class_746;
    //   537: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   540: invokevirtual method_10215 : ()D
    //   543: invokevirtual method_18800 : (DDD)V
    //   546: goto -> 553
    //   549: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   552: athrow
    //   553: aload_0
    //   554: getfield x : I
    //   557: iconst_4
    //   558: iload #8
    //   560: ifne -> 646
    //   563: if_icmpne -> 629
    //   566: goto -> 573
    //   569: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   572: athrow
    //   573: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   576: getfield field_1724 : Lnet/minecraft/class_746;
    //   579: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   582: getfield field_1724 : Lnet/minecraft/class_746;
    //   585: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   588: invokevirtual method_10216 : ()D
    //   591: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   594: getfield field_1724 : Lnet/minecraft/class_746;
    //   597: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   600: invokevirtual method_10214 : ()D
    //   603: ldc2_w 0.03500000014901161
    //   606: dsub
    //   607: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   610: getfield field_1724 : Lnet/minecraft/class_746;
    //   613: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   616: invokevirtual method_10215 : ()D
    //   619: invokevirtual method_18800 : (DDD)V
    //   622: goto -> 629
    //   625: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   628: athrow
    //   629: aload_0
    //   630: getfield x : I
    //   633: iload #8
    //   635: ifne -> 682
    //   638: iconst_5
    //   639: goto -> 646
    //   642: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   645: athrow
    //   646: if_icmpne -> 1186
    //   649: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   652: getfield field_1724 : Lnet/minecraft/class_746;
    //   655: dconst_0
    //   656: ldc2_w -1.0
    //   659: dconst_0
    //   660: iload #8
    //   662: ifne -> 738
    //   665: goto -> 672
    //   668: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   671: athrow
    //   672: invokevirtual method_5654 : (DDD)Z
    //   675: goto -> 682
    //   678: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   681: athrow
    //   682: ifne -> 1186
    //   685: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   688: getfield field_1724 : Lnet/minecraft/class_746;
    //   691: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   694: getfield field_1724 : Lnet/minecraft/class_746;
    //   697: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   700: invokevirtual method_10216 : ()D
    //   703: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   706: getfield field_1724 : Lnet/minecraft/class_746;
    //   709: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   712: invokevirtual method_10214 : ()D
    //   715: ldc2_w 100.0
    //   718: dsub
    //   719: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   722: getfield field_1724 : Lnet/minecraft/class_746;
    //   725: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   728: invokevirtual method_10215 : ()D
    //   731: goto -> 738
    //   734: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   737: athrow
    //   738: invokevirtual method_18800 : (DDD)V
    //   741: iload #8
    //   743: ifeq -> 1186
    //   746: aload_0
    //   747: getfield x : I
    //   750: goto -> 757
    //   753: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   756: athrow
    //   757: iload #8
    //   759: ifne -> 982
    //   762: tableswitch default -> 978, 3 -> 800, 4 -> 800, 5 -> 978, 6 -> 861, 7 -> 922
    //   796: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   799: athrow
    //   800: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   803: getfield field_1724 : Lnet/minecraft/class_746;
    //   806: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   809: getfield field_1724 : Lnet/minecraft/class_746;
    //   812: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   815: invokevirtual method_10216 : ()D
    //   818: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   821: getfield field_1724 : Lnet/minecraft/class_746;
    //   824: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   827: invokevirtual method_10214 : ()D
    //   830: ldc2_w 0.014999999664723873
    //   833: dsub
    //   834: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   837: getfield field_1724 : Lnet/minecraft/class_746;
    //   840: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   843: invokevirtual method_10215 : ()D
    //   846: invokevirtual method_18800 : (DDD)V
    //   849: iload #8
    //   851: ifeq -> 978
    //   854: goto -> 861
    //   857: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   860: athrow
    //   861: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   864: getfield field_1724 : Lnet/minecraft/class_746;
    //   867: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   870: getfield field_1724 : Lnet/minecraft/class_746;
    //   873: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   876: invokevirtual method_10216 : ()D
    //   879: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   882: getfield field_1724 : Lnet/minecraft/class_746;
    //   885: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   888: invokevirtual method_10214 : ()D
    //   891: ldc2_w 0.2150000035762787
    //   894: dsub
    //   895: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   898: getfield field_1724 : Lnet/minecraft/class_746;
    //   901: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   904: invokevirtual method_10215 : ()D
    //   907: invokevirtual method_18800 : (DDD)V
    //   910: iload #8
    //   912: ifeq -> 978
    //   915: goto -> 922
    //   918: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   921: athrow
    //   922: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   925: getfield field_1724 : Lnet/minecraft/class_746;
    //   928: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   931: getfield field_1724 : Lnet/minecraft/class_746;
    //   934: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   937: invokevirtual method_10216 : ()D
    //   940: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   943: getfield field_1724 : Lnet/minecraft/class_746;
    //   946: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   949: invokevirtual method_10214 : ()D
    //   952: ldc2_w 0.004900000058114529
    //   955: dsub
    //   956: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   959: getfield field_1724 : Lnet/minecraft/class_746;
    //   962: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   965: invokevirtual method_10215 : ()D
    //   968: invokevirtual method_18800 : (DDD)V
    //   971: goto -> 978
    //   974: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   977: athrow
    //   978: aload_0
    //   979: getfield x : I
    //   982: iconst_3
    //   983: iload #8
    //   985: ifne -> 1010
    //   988: if_icmpeq -> 1186
    //   991: goto -> 998
    //   994: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   997: athrow
    //   998: aload_0
    //   999: getfield x : I
    //   1002: iconst_4
    //   1003: goto -> 1010
    //   1006: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1009: athrow
    //   1010: iload #8
    //   1012: ifne -> 1049
    //   1015: if_icmpeq -> 1186
    //   1018: goto -> 1025
    //   1021: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1024: athrow
    //   1025: aload_0
    //   1026: getfield x : I
    //   1029: sipush #14663
    //   1032: ldc2_w 6911350067561034025
    //   1035: lload_2
    //   1036: lxor
    //   1037: <illegal opcode> q : (IJ)I
    //   1042: goto -> 1049
    //   1045: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1048: athrow
    //   1049: iload #8
    //   1051: ifne -> 1088
    //   1054: if_icmpeq -> 1186
    //   1057: goto -> 1064
    //   1060: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1063: athrow
    //   1064: aload_0
    //   1065: getfield x : I
    //   1068: sipush #22304
    //   1071: ldc2_w 6523031822864555848
    //   1074: lload_2
    //   1075: lxor
    //   1076: <illegal opcode> q : (IJ)I
    //   1081: goto -> 1088
    //   1084: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1087: athrow
    //   1088: iload #8
    //   1090: ifne -> 1127
    //   1093: if_icmpeq -> 1186
    //   1096: goto -> 1103
    //   1099: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1102: athrow
    //   1103: aload_0
    //   1104: getfield x : I
    //   1107: sipush #28753
    //   1110: ldc2_w 6775767965391403057
    //   1113: lload_2
    //   1114: lxor
    //   1115: <illegal opcode> q : (IJ)I
    //   1120: goto -> 1127
    //   1123: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1126: athrow
    //   1127: if_icmpge -> 1186
    //   1130: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1133: getfield field_1724 : Lnet/minecraft/class_746;
    //   1136: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1139: getfield field_1724 : Lnet/minecraft/class_746;
    //   1142: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1145: invokevirtual method_10216 : ()D
    //   1148: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1151: getfield field_1724 : Lnet/minecraft/class_746;
    //   1154: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1157: invokevirtual method_10214 : ()D
    //   1160: ldc2_w 5.5E-5
    //   1163: dsub
    //   1164: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1167: getfield field_1724 : Lnet/minecraft/class_746;
    //   1170: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1173: invokevirtual method_10215 : ()D
    //   1176: invokevirtual method_18800 : (DDD)V
    //   1179: goto -> 1186
    //   1182: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1185: athrow
    //   1186: return
    // Exception table:
    //   from	to	target	type
    //   29	62	65	wtf/opal/x5
    //   41	70	70	wtf/opal/x5
    //   142	169	172	wtf/opal/x5
    //   181	202	205	wtf/opal/x5
    //   186	210	210	wtf/opal/x5
    //   223	231	234	wtf/opal/x5
    //   228	244	247	wtf/opal/x5
    //   238	266	269	wtf/opal/x5
    //   251	287	290	wtf/opal/x5
    //   273	297	300	wtf/opal/x5
    //   294	323	326	wtf/opal/x5
    //   304	333	336	wtf/opal/x5
    //   362	371	374	wtf/opal/x5
    //   367	392	395	wtf/opal/x5
    //   399	407	410	wtf/opal/x5
    //   424	432	435	wtf/opal/x5
    //   429	453	456	wtf/opal/x5
    //   439	463	466	wtf/opal/x5
    //   460	480	483	wtf/opal/x5
    //   470	490	493	wtf/opal/x5
    //   487	546	549	wtf/opal/x5
    //   558	566	569	wtf/opal/x5
    //   563	622	625	wtf/opal/x5
    //   629	639	642	wtf/opal/x5
    //   646	665	668	wtf/opal/x5
    //   649	675	678	wtf/opal/x5
    //   682	731	734	wtf/opal/x5
    //   738	750	753	wtf/opal/x5
    //   757	796	796	wtf/opal/x5
    //   762	854	857	wtf/opal/x5
    //   800	915	918	wtf/opal/x5
    //   861	971	974	wtf/opal/x5
    //   982	991	994	wtf/opal/x5
    //   988	1003	1006	wtf/opal/x5
    //   1010	1018	1021	wtf/opal/x5
    //   1015	1042	1045	wtf/opal/x5
    //   1049	1057	1060	wtf/opal/x5
    //   1054	1081	1084	wtf/opal/x5
    //   1088	1096	1099	wtf/opal/x5
    //   1093	1120	1123	wtf/opal/x5
    //   1127	1179	1182	wtf/opal/x5
  }
  
  private void lambda$new$5(la paramla) {
    this.r = 0;
  }
  
  private void lambda$new$4(lb paramlb) {
    long l1 = a ^ 0x5194DD754509L;
    long l2 = l1 ^ 0x2BE137514EB7L;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    int i = e();
    try {
      if (i == 0)
        try {
          if (class_2596 instanceof class_2828) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    class_2828 class_2828 = (class_2828)class_2596;
    try {
      new Object[1];
      if (i == 0)
        try {
          if (Y(new Object[] { Long.valueOf(l2) })) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    if (Y(new Object[] { Long.valueOf(l2) }) > 5) {
      PlayerMoveC2SPacketAccessor playerMoveC2SPacketAccessor = (PlayerMoveC2SPacketAccessor)class_2828;
      try {
        if (b9.c.field_1724.method_24828())
          playerMoveC2SPacketAccessor.setY(class_2828.method_12268(b9.c.field_1724.method_23318()) + 1.0E-7D); 
      } catch (x5 x5) {
        throw b(null);
      } 
    } 
  }
  
  private void lambda$new$3(lu paramlu) {
    long l1 = a ^ 0x68F3FEF78F6EL;
    long l2 = l1 ^ 0x6F3751E48D6AL;
    long l3 = l1 ^ 0x28C5F71CED0CL;
    int i = e();
    try {
      if (i == 0)
        try {
          new Object[1];
          if (!this.G.g(new Object[] { Long.valueOf(l2) }))
            return; 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    jw jw = (jw)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jw.class });
    class_2596 class_2596 = paramlu.g(new Object[0]);
    try {
      if (i == 0)
        try {
          if (class_2596 instanceof class_2743) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    class_2743 class_2743 = (class_2743)class_2596;
    try {
      if (i == 0)
        try {
          if (class_2743.method_11818() == b9.c.field_1724.method_5628()) {
            this.W = 0;
            new Object[1];
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    try {
      if (i == 0)
        try {
          if (class_2743.method_11818() == 0) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    try {
      if (class_2743.method_11818() != 0) {
        paramlu.Z(new Object[0]);
        b9.c.field_1724.method_18800(b9.c.field_1724.method_18798().method_10216(), class_2743.method_11816() / 8000.0D, b9.c.field_1724.method_18798().method_10215());
      } 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  private void lambda$new$2(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/uo.a : J
    //   3: ldc2_w 23968009566756
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 110596354848485
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 19829119596576
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 94534198974534
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 122842886058394
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 139067850551506
    //   41: lxor
    //   42: lstore #12
    //   44: pop2
    //   45: invokestatic k : ()I
    //   48: istore #14
    //   50: aload_0
    //   51: getfield G : Lwtf/opal/d;
    //   54: checkcast wtf/opal/jm
    //   57: lload #6
    //   59: iconst_1
    //   60: anewarray java/lang/Object
    //   63: dup_x2
    //   64: dup_x2
    //   65: pop
    //   66: invokestatic valueOf : (J)Ljava/lang/Long;
    //   69: iconst_0
    //   70: swap
    //   71: aastore
    //   72: invokevirtual g : ([Ljava/lang/Object;)Z
    //   75: iload #14
    //   77: ifeq -> 99
    //   80: ifne -> 91
    //   83: goto -> 90
    //   86: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: return
    //   91: aload_1
    //   92: iconst_0
    //   93: anewarray java/lang/Object
    //   96: invokevirtual W : ([Ljava/lang/Object;)Z
    //   99: iload #14
    //   101: ifeq -> 156
    //   104: ifne -> 115
    //   107: goto -> 114
    //   110: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: return
    //   115: aload_0
    //   116: dup
    //   117: getfield r : I
    //   120: iconst_1
    //   121: iadd
    //   122: putfield r : I
    //   125: aload_0
    //   126: dup
    //   127: getfield W : I
    //   130: iconst_1
    //   131: iadd
    //   132: putfield W : I
    //   135: iload #14
    //   137: ifeq -> 470
    //   140: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   143: getfield field_1724 : Lnet/minecraft/class_746;
    //   146: invokevirtual method_24828 : ()Z
    //   149: goto -> 156
    //   152: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   155: athrow
    //   156: ifeq -> 453
    //   159: lload #8
    //   161: iconst_1
    //   162: anewarray java/lang/Object
    //   165: dup_x2
    //   166: dup_x2
    //   167: pop
    //   168: invokestatic valueOf : (J)Ljava/lang/Long;
    //   171: iconst_0
    //   172: swap
    //   173: aastore
    //   174: invokestatic I : ([Ljava/lang/Object;)Z
    //   177: iload #14
    //   179: ifeq -> 222
    //   182: goto -> 189
    //   185: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   188: athrow
    //   189: ifeq -> 2423
    //   192: goto -> 199
    //   195: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   198: athrow
    //   199: aload_0
    //   200: iload #14
    //   202: ifeq -> 429
    //   205: goto -> 212
    //   208: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: getfield h : I
    //   215: goto -> 222
    //   218: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   221: athrow
    //   222: ifne -> 365
    //   225: aload_0
    //   226: iload #14
    //   228: ifeq -> 429
    //   231: goto -> 238
    //   234: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   237: athrow
    //   238: lload #10
    //   240: iconst_1
    //   241: anewarray java/lang/Object
    //   244: dup_x2
    //   245: dup_x2
    //   246: pop
    //   247: invokestatic valueOf : (J)Ljava/lang/Long;
    //   250: iconst_0
    //   251: swap
    //   252: aastore
    //   253: invokevirtual Y : ([Ljava/lang/Object;)Z
    //   256: ifeq -> 365
    //   259: goto -> 266
    //   262: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   265: athrow
    //   266: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   269: getfield field_1724 : Lnet/minecraft/class_746;
    //   272: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   275: getfield field_1724 : Lnet/minecraft/class_746;
    //   278: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   281: invokevirtual method_10216 : ()D
    //   284: ldc2_w 0.035
    //   287: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   290: getfield field_1724 : Lnet/minecraft/class_746;
    //   293: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   296: invokevirtual method_10215 : ()D
    //   299: invokevirtual method_18800 : (DDD)V
    //   302: lload #4
    //   304: iconst_1
    //   305: anewarray java/lang/Object
    //   308: dup_x2
    //   309: dup_x2
    //   310: pop
    //   311: invokestatic valueOf : (J)Ljava/lang/Long;
    //   314: iconst_0
    //   315: swap
    //   316: aastore
    //   317: invokestatic m : ([Ljava/lang/Object;)D
    //   320: ldc2_w 0.6
    //   323: dmul
    //   324: lload #12
    //   326: dup2_x2
    //   327: pop2
    //   328: iconst_2
    //   329: anewarray java/lang/Object
    //   332: dup_x2
    //   333: dup_x2
    //   334: pop
    //   335: invokestatic valueOf : (D)Ljava/lang/Double;
    //   338: iconst_1
    //   339: swap
    //   340: aastore
    //   341: dup_x2
    //   342: dup_x2
    //   343: pop
    //   344: invokestatic valueOf : (J)Ljava/lang/Long;
    //   347: iconst_0
    //   348: swap
    //   349: aastore
    //   350: invokestatic k : ([Ljava/lang/Object;)V
    //   353: iload #14
    //   355: ifne -> 433
    //   358: goto -> 365
    //   361: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   364: athrow
    //   365: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   368: getfield field_1724 : Lnet/minecraft/class_746;
    //   371: invokevirtual method_6043 : ()V
    //   374: lload #4
    //   376: iconst_1
    //   377: anewarray java/lang/Object
    //   380: dup_x2
    //   381: dup_x2
    //   382: pop
    //   383: invokestatic valueOf : (J)Ljava/lang/Long;
    //   386: iconst_0
    //   387: swap
    //   388: aastore
    //   389: invokestatic m : ([Ljava/lang/Object;)D
    //   392: lload #12
    //   394: dup2_x2
    //   395: pop2
    //   396: iconst_2
    //   397: anewarray java/lang/Object
    //   400: dup_x2
    //   401: dup_x2
    //   402: pop
    //   403: invokestatic valueOf : (D)Ljava/lang/Double;
    //   406: iconst_1
    //   407: swap
    //   408: aastore
    //   409: dup_x2
    //   410: dup_x2
    //   411: pop
    //   412: invokestatic valueOf : (J)Ljava/lang/Long;
    //   415: iconst_0
    //   416: swap
    //   417: aastore
    //   418: invokestatic k : ([Ljava/lang/Object;)V
    //   421: aload_0
    //   422: goto -> 429
    //   425: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   428: athrow
    //   429: iconst_0
    //   430: putfield R : Z
    //   433: aload_0
    //   434: dup
    //   435: getfield h : I
    //   438: iconst_1
    //   439: iadd
    //   440: putfield h : I
    //   443: aload_0
    //   444: iconst_0
    //   445: putfield x : I
    //   448: iload #14
    //   450: ifne -> 2423
    //   453: aload_0
    //   454: dup
    //   455: getfield x : I
    //   458: iconst_1
    //   459: iadd
    //   460: putfield x : I
    //   463: goto -> 470
    //   466: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   469: athrow
    //   470: iconst_0
    //   471: anewarray java/lang/Object
    //   474: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   477: iconst_0
    //   478: anewarray java/lang/Object
    //   481: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   484: ldc_w wtf/opal/jw
    //   487: iconst_1
    //   488: anewarray java/lang/Object
    //   491: dup_x1
    //   492: swap
    //   493: iconst_0
    //   494: swap
    //   495: aastore
    //   496: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   499: checkcast wtf/opal/jw
    //   502: astore #15
    //   504: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   507: getfield field_1724 : Lnet/minecraft/class_746;
    //   510: getfield field_6235 : I
    //   513: iload #14
    //   515: ifeq -> 620
    //   518: ifeq -> 595
    //   521: goto -> 528
    //   524: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   527: athrow
    //   528: aload #15
    //   530: iconst_0
    //   531: anewarray java/lang/Object
    //   534: invokevirtual D : ([Ljava/lang/Object;)Z
    //   537: iload #14
    //   539: ifeq -> 1083
    //   542: goto -> 549
    //   545: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   548: athrow
    //   549: ifeq -> 1044
    //   552: goto -> 559
    //   555: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   558: athrow
    //   559: aload #15
    //   561: getfield R : Lwtf/opal/ke;
    //   564: invokevirtual z : ()Ljava/lang/Object;
    //   567: checkcast java/lang/Boolean
    //   570: invokevirtual booleanValue : ()Z
    //   573: iload #14
    //   575: ifeq -> 1083
    //   578: goto -> 585
    //   581: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   584: athrow
    //   585: ifeq -> 1044
    //   588: goto -> 595
    //   591: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   594: athrow
    //   595: lload #8
    //   597: iconst_1
    //   598: anewarray java/lang/Object
    //   601: dup_x2
    //   602: dup_x2
    //   603: pop
    //   604: invokestatic valueOf : (J)Ljava/lang/Long;
    //   607: iconst_0
    //   608: swap
    //   609: aastore
    //   610: invokestatic I : ([Ljava/lang/Object;)Z
    //   613: goto -> 620
    //   616: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   619: athrow
    //   620: iload #14
    //   622: ifeq -> 1083
    //   625: ifeq -> 1044
    //   628: goto -> 635
    //   631: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   634: athrow
    //   635: aload_0
    //   636: getfield x : I
    //   639: iconst_1
    //   640: iload #14
    //   642: ifeq -> 729
    //   645: goto -> 652
    //   648: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   651: athrow
    //   652: if_icmpne -> 706
    //   655: goto -> 662
    //   658: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   661: athrow
    //   662: iconst_0
    //   663: anewarray java/lang/Object
    //   666: invokestatic U : ([Ljava/lang/Object;)F
    //   669: f2d
    //   670: lload #12
    //   672: dup2_x2
    //   673: pop2
    //   674: iconst_2
    //   675: anewarray java/lang/Object
    //   678: dup_x2
    //   679: dup_x2
    //   680: pop
    //   681: invokestatic valueOf : (D)Ljava/lang/Double;
    //   684: iconst_1
    //   685: swap
    //   686: aastore
    //   687: dup_x2
    //   688: dup_x2
    //   689: pop
    //   690: invokestatic valueOf : (J)Ljava/lang/Long;
    //   693: iconst_0
    //   694: swap
    //   695: aastore
    //   696: invokestatic k : ([Ljava/lang/Object;)V
    //   699: goto -> 706
    //   702: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   705: athrow
    //   706: aload_0
    //   707: getfield x : I
    //   710: aload_0
    //   711: lload #10
    //   713: iconst_1
    //   714: anewarray java/lang/Object
    //   717: dup_x2
    //   718: dup_x2
    //   719: pop
    //   720: invokestatic valueOf : (J)Ljava/lang/Long;
    //   723: iconst_0
    //   724: swap
    //   725: aastore
    //   726: invokevirtual Y : ([Ljava/lang/Object;)Z
    //   729: iload #14
    //   731: ifeq -> 790
    //   734: ifeq -> 877
    //   737: goto -> 744
    //   740: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   743: athrow
    //   744: iconst_0
    //   745: anewarray java/lang/Object
    //   748: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   751: iconst_0
    //   752: anewarray java/lang/Object
    //   755: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   758: ldc_w wtf/opal/xw
    //   761: iconst_1
    //   762: anewarray java/lang/Object
    //   765: dup_x1
    //   766: swap
    //   767: iconst_0
    //   768: swap
    //   769: aastore
    //   770: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   773: checkcast wtf/opal/xw
    //   776: iconst_0
    //   777: anewarray java/lang/Object
    //   780: invokevirtual D : ([Ljava/lang/Object;)Z
    //   783: goto -> 790
    //   786: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   789: athrow
    //   790: iload #14
    //   792: ifeq -> 826
    //   795: ifeq -> 813
    //   798: goto -> 805
    //   801: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   804: athrow
    //   805: iconst_5
    //   806: goto -> 890
    //   809: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   812: athrow
    //   813: aload_0
    //   814: getfield M : Lwtf/opal/ke;
    //   817: invokevirtual z : ()Ljava/lang/Object;
    //   820: checkcast java/lang/Boolean
    //   823: invokevirtual booleanValue : ()Z
    //   826: iload #14
    //   828: ifeq -> 874
    //   831: ifeq -> 861
    //   834: goto -> 841
    //   837: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   840: athrow
    //   841: sipush #4469
    //   844: ldc2_w 3986918556666835796
    //   847: lload_2
    //   848: lxor
    //   849: <illegal opcode> q : (IJ)I
    //   854: goto -> 890
    //   857: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   860: athrow
    //   861: sipush #22042
    //   864: ldc2_w 8412546755965940788
    //   867: lload_2
    //   868: lxor
    //   869: <illegal opcode> q : (IJ)I
    //   874: goto -> 890
    //   877: sipush #14517
    //   880: ldc2_w 7391513221496248986
    //   883: lload_2
    //   884: lxor
    //   885: <illegal opcode> q : (IJ)I
    //   890: if_icmpne -> 1044
    //   893: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   896: getfield field_1724 : Lnet/minecraft/class_746;
    //   899: dconst_0
    //   900: ldc2_w -1.0
    //   903: dconst_0
    //   904: invokevirtual method_5654 : (DDD)Z
    //   907: iload #14
    //   909: ifeq -> 1083
    //   912: goto -> 919
    //   915: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   918: athrow
    //   919: ifne -> 1044
    //   922: goto -> 929
    //   925: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   928: athrow
    //   929: aload_0
    //   930: getfield d : Lwtf/opal/ke;
    //   933: invokevirtual z : ()Ljava/lang/Object;
    //   936: checkcast java/lang/Boolean
    //   939: invokevirtual booleanValue : ()Z
    //   942: iload #14
    //   944: ifeq -> 1083
    //   947: goto -> 954
    //   950: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   953: athrow
    //   954: ifne -> 1044
    //   957: goto -> 964
    //   960: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   963: athrow
    //   964: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   967: getfield field_1724 : Lnet/minecraft/class_746;
    //   970: dconst_0
    //   971: ldc2_w 1.5
    //   974: dconst_0
    //   975: invokevirtual method_5654 : (DDD)Z
    //   978: iload #14
    //   980: ifeq -> 1083
    //   983: goto -> 990
    //   986: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   989: athrow
    //   990: ifeq -> 1044
    //   993: goto -> 1000
    //   996: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   999: athrow
    //   1000: iconst_0
    //   1001: anewarray java/lang/Object
    //   1004: invokestatic U : ([Ljava/lang/Object;)F
    //   1007: f2d
    //   1008: lload #12
    //   1010: dup2_x2
    //   1011: pop2
    //   1012: iconst_2
    //   1013: anewarray java/lang/Object
    //   1016: dup_x2
    //   1017: dup_x2
    //   1018: pop
    //   1019: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1022: iconst_1
    //   1023: swap
    //   1024: aastore
    //   1025: dup_x2
    //   1026: dup_x2
    //   1027: pop
    //   1028: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1031: iconst_0
    //   1032: swap
    //   1033: aastore
    //   1034: invokestatic k : ([Ljava/lang/Object;)V
    //   1037: goto -> 1044
    //   1040: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1043: athrow
    //   1044: iconst_0
    //   1045: anewarray java/lang/Object
    //   1048: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   1051: iconst_0
    //   1052: anewarray java/lang/Object
    //   1055: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   1058: ldc_w wtf/opal/xw
    //   1061: iconst_1
    //   1062: anewarray java/lang/Object
    //   1065: dup_x1
    //   1066: swap
    //   1067: iconst_0
    //   1068: swap
    //   1069: aastore
    //   1070: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   1073: checkcast wtf/opal/xw
    //   1076: iconst_0
    //   1077: anewarray java/lang/Object
    //   1080: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1083: iload #14
    //   1085: ifeq -> 1851
    //   1088: ifne -> 1838
    //   1091: goto -> 1098
    //   1094: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1097: athrow
    //   1098: aload_0
    //   1099: getfield M : Lwtf/opal/ke;
    //   1102: invokevirtual z : ()Ljava/lang/Object;
    //   1105: checkcast java/lang/Boolean
    //   1108: invokevirtual booleanValue : ()Z
    //   1111: iload #14
    //   1113: ifeq -> 1851
    //   1116: goto -> 1123
    //   1119: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1122: athrow
    //   1123: ifeq -> 1838
    //   1126: goto -> 1133
    //   1129: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1132: athrow
    //   1133: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1136: getfield field_1724 : Lnet/minecraft/class_746;
    //   1139: getfield field_6235 : I
    //   1142: iload #14
    //   1144: ifeq -> 1252
    //   1147: goto -> 1154
    //   1150: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1153: athrow
    //   1154: ifeq -> 1231
    //   1157: goto -> 1164
    //   1160: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1163: athrow
    //   1164: aload #15
    //   1166: iconst_0
    //   1167: anewarray java/lang/Object
    //   1170: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1173: iload #14
    //   1175: ifeq -> 1851
    //   1178: goto -> 1185
    //   1181: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1184: athrow
    //   1185: ifeq -> 1838
    //   1188: goto -> 1195
    //   1191: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1194: athrow
    //   1195: aload #15
    //   1197: getfield R : Lwtf/opal/ke;
    //   1200: invokevirtual z : ()Ljava/lang/Object;
    //   1203: checkcast java/lang/Boolean
    //   1206: invokevirtual booleanValue : ()Z
    //   1209: iload #14
    //   1211: ifeq -> 1851
    //   1214: goto -> 1221
    //   1217: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1220: athrow
    //   1221: ifeq -> 1838
    //   1224: goto -> 1231
    //   1227: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1230: athrow
    //   1231: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1234: getfield field_1724 : Lnet/minecraft/class_746;
    //   1237: dconst_0
    //   1238: ldc2_w -1.0
    //   1241: dconst_0
    //   1242: invokevirtual method_5654 : (DDD)Z
    //   1245: goto -> 1252
    //   1248: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1251: athrow
    //   1252: iload #14
    //   1254: ifeq -> 1851
    //   1257: ifne -> 1838
    //   1260: goto -> 1267
    //   1263: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1266: athrow
    //   1267: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1270: getfield field_1724 : Lnet/minecraft/class_746;
    //   1273: dconst_0
    //   1274: ldc2_w 1.5
    //   1277: dconst_0
    //   1278: invokevirtual method_5654 : (DDD)Z
    //   1281: iload #14
    //   1283: ifeq -> 1851
    //   1286: goto -> 1293
    //   1289: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1292: athrow
    //   1293: ifeq -> 1838
    //   1296: goto -> 1303
    //   1299: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1302: athrow
    //   1303: aload_0
    //   1304: getfield x : I
    //   1307: aload_0
    //   1308: getfield i : Lwtf/opal/ke;
    //   1311: invokevirtual z : ()Ljava/lang/Object;
    //   1314: checkcast java/lang/Boolean
    //   1317: invokevirtual booleanValue : ()Z
    //   1320: iload #14
    //   1322: ifeq -> 1355
    //   1325: goto -> 1332
    //   1328: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1331: athrow
    //   1332: ifeq -> 1358
    //   1335: goto -> 1342
    //   1338: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1341: athrow
    //   1342: sipush #32188
    //   1345: ldc2_w 1090601146184785817
    //   1348: lload_2
    //   1349: lxor
    //   1350: <illegal opcode> q : (IJ)I
    //   1355: goto -> 1371
    //   1358: sipush #22223
    //   1361: ldc2_w 3987531591568585957
    //   1364: lload_2
    //   1365: lxor
    //   1366: <illegal opcode> q : (IJ)I
    //   1371: if_icmpeq -> 1516
    //   1374: aload_0
    //   1375: getfield x : I
    //   1378: aload_0
    //   1379: getfield i : Lwtf/opal/ke;
    //   1382: invokevirtual z : ()Ljava/lang/Object;
    //   1385: checkcast java/lang/Boolean
    //   1388: invokevirtual booleanValue : ()Z
    //   1391: iload #14
    //   1393: ifeq -> 1426
    //   1396: goto -> 1403
    //   1399: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1402: athrow
    //   1403: ifeq -> 1429
    //   1406: goto -> 1413
    //   1409: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1412: athrow
    //   1413: sipush #16308
    //   1416: ldc2_w 269300362024230303
    //   1419: lload_2
    //   1420: lxor
    //   1421: <illegal opcode> q : (IJ)I
    //   1426: goto -> 1442
    //   1429: sipush #23306
    //   1432: ldc2_w 4548509958608419117
    //   1435: lload_2
    //   1436: lxor
    //   1437: <illegal opcode> q : (IJ)I
    //   1442: if_icmpeq -> 1516
    //   1445: aload_0
    //   1446: getfield x : I
    //   1449: aload_0
    //   1450: getfield i : Lwtf/opal/ke;
    //   1453: invokevirtual z : ()Ljava/lang/Object;
    //   1456: checkcast java/lang/Boolean
    //   1459: invokevirtual booleanValue : ()Z
    //   1462: iload #14
    //   1464: ifeq -> 1497
    //   1467: goto -> 1474
    //   1470: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1473: athrow
    //   1474: ifeq -> 1500
    //   1477: goto -> 1484
    //   1480: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1483: athrow
    //   1484: sipush #15643
    //   1487: ldc2_w 5911331426572705586
    //   1490: lload_2
    //   1491: lxor
    //   1492: <illegal opcode> q : (IJ)I
    //   1497: goto -> 1513
    //   1500: sipush #13569
    //   1503: ldc2_w 8353506134424131372
    //   1506: lload_2
    //   1507: lxor
    //   1508: <illegal opcode> q : (IJ)I
    //   1513: if_icmpne -> 1838
    //   1516: aload_0
    //   1517: getfield x : I
    //   1520: aload_0
    //   1521: getfield i : Lwtf/opal/ke;
    //   1524: invokevirtual z : ()Ljava/lang/Object;
    //   1527: checkcast java/lang/Boolean
    //   1530: invokevirtual booleanValue : ()Z
    //   1533: iload #14
    //   1535: ifeq -> 1568
    //   1538: goto -> 1545
    //   1541: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1544: athrow
    //   1545: ifeq -> 1571
    //   1548: goto -> 1555
    //   1551: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1554: athrow
    //   1555: sipush #22304
    //   1558: ldc2_w 6522997735232435460
    //   1561: lload_2
    //   1562: lxor
    //   1563: <illegal opcode> q : (IJ)I
    //   1568: goto -> 1584
    //   1571: sipush #2917
    //   1574: ldc2_w 223915225354086723
    //   1577: lload_2
    //   1578: lxor
    //   1579: <illegal opcode> q : (IJ)I
    //   1584: if_icmpne -> 1639
    //   1587: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1590: getfield field_1724 : Lnet/minecraft/class_746;
    //   1593: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1596: getfield field_1724 : Lnet/minecraft/class_746;
    //   1599: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1602: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   1605: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1608: getfield field_1724 : Lnet/minecraft/class_746;
    //   1611: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1614: invokevirtual method_10214 : ()D
    //   1617: ldc2_w 0.08
    //   1620: dadd
    //   1621: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   1624: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   1627: aload_0
    //   1628: iconst_1
    //   1629: putfield R : Z
    //   1632: goto -> 1639
    //   1635: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1638: athrow
    //   1639: lload #8
    //   1641: iconst_1
    //   1642: anewarray java/lang/Object
    //   1645: dup_x2
    //   1646: dup_x2
    //   1647: pop
    //   1648: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1651: iconst_0
    //   1652: swap
    //   1653: aastore
    //   1654: invokestatic I : ([Ljava/lang/Object;)Z
    //   1657: iload #14
    //   1659: ifeq -> 1851
    //   1662: ifeq -> 1838
    //   1665: goto -> 1672
    //   1668: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1671: athrow
    //   1672: aload_0
    //   1673: getfield R : Z
    //   1676: iload #14
    //   1678: ifeq -> 1851
    //   1681: goto -> 1688
    //   1684: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1687: athrow
    //   1688: ifeq -> 1838
    //   1691: goto -> 1698
    //   1694: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1697: athrow
    //   1698: aload_0
    //   1699: getfield e : Lwtf/opal/ke;
    //   1702: invokevirtual z : ()Ljava/lang/Object;
    //   1705: checkcast java/lang/Boolean
    //   1708: invokevirtual booleanValue : ()Z
    //   1711: ifeq -> 1794
    //   1714: goto -> 1721
    //   1717: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1720: athrow
    //   1721: lload #4
    //   1723: iconst_1
    //   1724: anewarray java/lang/Object
    //   1727: dup_x2
    //   1728: dup_x2
    //   1729: pop
    //   1730: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1733: iconst_0
    //   1734: swap
    //   1735: aastore
    //   1736: invokestatic m : ([Ljava/lang/Object;)D
    //   1739: aload_0
    //   1740: getfield f : Lwtf/opal/kt;
    //   1743: invokevirtual z : ()Ljava/lang/Object;
    //   1746: checkcast java/lang/Double
    //   1749: invokevirtual doubleValue : ()D
    //   1752: dmul
    //   1753: lload #12
    //   1755: dup2_x2
    //   1756: pop2
    //   1757: iconst_2
    //   1758: anewarray java/lang/Object
    //   1761: dup_x2
    //   1762: dup_x2
    //   1763: pop
    //   1764: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1767: iconst_1
    //   1768: swap
    //   1769: aastore
    //   1770: dup_x2
    //   1771: dup_x2
    //   1772: pop
    //   1773: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1776: iconst_0
    //   1777: swap
    //   1778: aastore
    //   1779: invokestatic k : ([Ljava/lang/Object;)V
    //   1782: iload #14
    //   1784: ifne -> 1838
    //   1787: goto -> 1794
    //   1790: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1793: athrow
    //   1794: iconst_0
    //   1795: anewarray java/lang/Object
    //   1798: invokestatic U : ([Ljava/lang/Object;)F
    //   1801: f2d
    //   1802: lload #12
    //   1804: dup2_x2
    //   1805: pop2
    //   1806: iconst_2
    //   1807: anewarray java/lang/Object
    //   1810: dup_x2
    //   1811: dup_x2
    //   1812: pop
    //   1813: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1816: iconst_1
    //   1817: swap
    //   1818: aastore
    //   1819: dup_x2
    //   1820: dup_x2
    //   1821: pop
    //   1822: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1825: iconst_0
    //   1826: swap
    //   1827: aastore
    //   1828: invokestatic k : ([Ljava/lang/Object;)V
    //   1831: goto -> 1838
    //   1834: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1837: athrow
    //   1838: aload_0
    //   1839: getfield d : Lwtf/opal/ke;
    //   1842: invokevirtual z : ()Ljava/lang/Object;
    //   1845: checkcast java/lang/Boolean
    //   1848: invokevirtual booleanValue : ()Z
    //   1851: iload #14
    //   1853: ifeq -> 2289
    //   1856: ifeq -> 2276
    //   1859: goto -> 1866
    //   1862: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1865: athrow
    //   1866: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1869: getfield field_1724 : Lnet/minecraft/class_746;
    //   1872: getfield field_6235 : I
    //   1875: iload #14
    //   1877: ifeq -> 1985
    //   1880: goto -> 1887
    //   1883: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1886: athrow
    //   1887: ifeq -> 1964
    //   1890: goto -> 1897
    //   1893: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1896: athrow
    //   1897: aload #15
    //   1899: iconst_0
    //   1900: anewarray java/lang/Object
    //   1903: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1906: iload #14
    //   1908: ifeq -> 2289
    //   1911: goto -> 1918
    //   1914: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1917: athrow
    //   1918: ifeq -> 2276
    //   1921: goto -> 1928
    //   1924: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1927: athrow
    //   1928: aload #15
    //   1930: getfield R : Lwtf/opal/ke;
    //   1933: invokevirtual z : ()Ljava/lang/Object;
    //   1936: checkcast java/lang/Boolean
    //   1939: invokevirtual booleanValue : ()Z
    //   1942: iload #14
    //   1944: ifeq -> 2289
    //   1947: goto -> 1954
    //   1950: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1953: athrow
    //   1954: ifeq -> 2276
    //   1957: goto -> 1964
    //   1960: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1963: athrow
    //   1964: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1967: getfield field_1724 : Lnet/minecraft/class_746;
    //   1970: dconst_0
    //   1971: ldc2_w -1.0
    //   1974: dconst_0
    //   1975: invokevirtual method_5654 : (DDD)Z
    //   1978: goto -> 1985
    //   1981: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1984: athrow
    //   1985: iload #14
    //   1987: ifeq -> 2289
    //   1990: ifne -> 2276
    //   1993: goto -> 2000
    //   1996: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1999: athrow
    //   2000: aload_0
    //   2001: getfield x : I
    //   2004: aload_0
    //   2005: getfield i : Lwtf/opal/ke;
    //   2008: invokevirtual z : ()Ljava/lang/Object;
    //   2011: checkcast java/lang/Boolean
    //   2014: invokevirtual booleanValue : ()Z
    //   2017: iload #14
    //   2019: ifeq -> 2052
    //   2022: goto -> 2029
    //   2025: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2028: athrow
    //   2029: ifeq -> 2055
    //   2032: goto -> 2039
    //   2035: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2038: athrow
    //   2039: sipush #16308
    //   2042: ldc2_w 269300362024230303
    //   2045: lload_2
    //   2046: lxor
    //   2047: <illegal opcode> q : (IJ)I
    //   2052: goto -> 2068
    //   2055: sipush #2917
    //   2058: ldc2_w 223915225354086723
    //   2061: lload_2
    //   2062: lxor
    //   2063: <illegal opcode> q : (IJ)I
    //   2068: if_icmpne -> 2276
    //   2071: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   2074: getfield field_1724 : Lnet/minecraft/class_746;
    //   2077: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   2080: getfield field_1724 : Lnet/minecraft/class_746;
    //   2083: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   2086: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   2089: dconst_0
    //   2090: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   2093: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   2096: lload #8
    //   2098: iconst_1
    //   2099: anewarray java/lang/Object
    //   2102: dup_x2
    //   2103: dup_x2
    //   2104: pop
    //   2105: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2108: iconst_0
    //   2109: swap
    //   2110: aastore
    //   2111: invokestatic I : ([Ljava/lang/Object;)Z
    //   2114: iload #14
    //   2116: ifeq -> 2289
    //   2119: goto -> 2126
    //   2122: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2125: athrow
    //   2126: ifeq -> 2276
    //   2129: goto -> 2136
    //   2132: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2135: athrow
    //   2136: aload_0
    //   2137: getfield e : Lwtf/opal/ke;
    //   2140: invokevirtual z : ()Ljava/lang/Object;
    //   2143: checkcast java/lang/Boolean
    //   2146: invokevirtual booleanValue : ()Z
    //   2149: ifeq -> 2232
    //   2152: goto -> 2159
    //   2155: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2158: athrow
    //   2159: lload #4
    //   2161: iconst_1
    //   2162: anewarray java/lang/Object
    //   2165: dup_x2
    //   2166: dup_x2
    //   2167: pop
    //   2168: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2171: iconst_0
    //   2172: swap
    //   2173: aastore
    //   2174: invokestatic m : ([Ljava/lang/Object;)D
    //   2177: aload_0
    //   2178: getfield f : Lwtf/opal/kt;
    //   2181: invokevirtual z : ()Ljava/lang/Object;
    //   2184: checkcast java/lang/Double
    //   2187: invokevirtual doubleValue : ()D
    //   2190: dmul
    //   2191: lload #12
    //   2193: dup2_x2
    //   2194: pop2
    //   2195: iconst_2
    //   2196: anewarray java/lang/Object
    //   2199: dup_x2
    //   2200: dup_x2
    //   2201: pop
    //   2202: invokestatic valueOf : (D)Ljava/lang/Double;
    //   2205: iconst_1
    //   2206: swap
    //   2207: aastore
    //   2208: dup_x2
    //   2209: dup_x2
    //   2210: pop
    //   2211: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2214: iconst_0
    //   2215: swap
    //   2216: aastore
    //   2217: invokestatic k : ([Ljava/lang/Object;)V
    //   2220: iload #14
    //   2222: ifne -> 2276
    //   2225: goto -> 2232
    //   2228: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2231: athrow
    //   2232: iconst_0
    //   2233: anewarray java/lang/Object
    //   2236: invokestatic U : ([Ljava/lang/Object;)F
    //   2239: f2d
    //   2240: lload #12
    //   2242: dup2_x2
    //   2243: pop2
    //   2244: iconst_2
    //   2245: anewarray java/lang/Object
    //   2248: dup_x2
    //   2249: dup_x2
    //   2250: pop
    //   2251: invokestatic valueOf : (D)Ljava/lang/Double;
    //   2254: iconst_1
    //   2255: swap
    //   2256: aastore
    //   2257: dup_x2
    //   2258: dup_x2
    //   2259: pop
    //   2260: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2263: iconst_0
    //   2264: swap
    //   2265: aastore
    //   2266: invokestatic k : ([Ljava/lang/Object;)V
    //   2269: goto -> 2276
    //   2272: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2275: athrow
    //   2276: aload_0
    //   2277: getfield D : Lwtf/opal/ke;
    //   2280: invokevirtual z : ()Ljava/lang/Object;
    //   2283: checkcast java/lang/Boolean
    //   2286: invokevirtual booleanValue : ()Z
    //   2289: iload #14
    //   2291: ifeq -> 2315
    //   2294: ifeq -> 2423
    //   2297: goto -> 2304
    //   2300: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2303: athrow
    //   2304: aload_0
    //   2305: getfield W : I
    //   2308: goto -> 2315
    //   2311: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2314: athrow
    //   2315: iload #14
    //   2317: ifeq -> 2356
    //   2320: iconst_2
    //   2321: if_icmpne -> 2423
    //   2324: goto -> 2331
    //   2327: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2330: athrow
    //   2331: lload #8
    //   2333: iconst_1
    //   2334: anewarray java/lang/Object
    //   2337: dup_x2
    //   2338: dup_x2
    //   2339: pop
    //   2340: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2343: iconst_0
    //   2344: swap
    //   2345: aastore
    //   2346: invokestatic I : ([Ljava/lang/Object;)Z
    //   2349: goto -> 2356
    //   2352: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2355: athrow
    //   2356: ifeq -> 2423
    //   2359: iconst_0
    //   2360: anewarray java/lang/Object
    //   2363: invokestatic E : ([Ljava/lang/Object;)F
    //   2366: f2d
    //   2367: dconst_1
    //   2368: aload_0
    //   2369: getfield b : Lwtf/opal/kt;
    //   2372: invokevirtual z : ()Ljava/lang/Object;
    //   2375: checkcast java/lang/Double
    //   2378: invokevirtual doubleValue : ()D
    //   2381: ldc2_w 0.01
    //   2384: dmul
    //   2385: dadd
    //   2386: dmul
    //   2387: lload #12
    //   2389: dup2_x2
    //   2390: pop2
    //   2391: iconst_2
    //   2392: anewarray java/lang/Object
    //   2395: dup_x2
    //   2396: dup_x2
    //   2397: pop
    //   2398: invokestatic valueOf : (D)Ljava/lang/Double;
    //   2401: iconst_1
    //   2402: swap
    //   2403: aastore
    //   2404: dup_x2
    //   2405: dup_x2
    //   2406: pop
    //   2407: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2410: iconst_0
    //   2411: swap
    //   2412: aastore
    //   2413: invokestatic k : ([Ljava/lang/Object;)V
    //   2416: goto -> 2423
    //   2419: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2422: athrow
    //   2423: return
    // Exception table:
    //   from	to	target	type
    //   50	83	86	wtf/opal/x5
    //   99	107	110	wtf/opal/x5
    //   115	149	152	wtf/opal/x5
    //   156	182	185	wtf/opal/x5
    //   159	192	195	wtf/opal/x5
    //   189	205	208	wtf/opal/x5
    //   199	215	218	wtf/opal/x5
    //   222	231	234	wtf/opal/x5
    //   225	259	262	wtf/opal/x5
    //   238	358	361	wtf/opal/x5
    //   266	422	425	wtf/opal/x5
    //   433	463	466	wtf/opal/x5
    //   504	521	524	wtf/opal/x5
    //   518	542	545	wtf/opal/x5
    //   528	552	555	wtf/opal/x5
    //   549	578	581	wtf/opal/x5
    //   559	588	591	wtf/opal/x5
    //   585	613	616	wtf/opal/x5
    //   620	628	631	wtf/opal/x5
    //   625	645	648	wtf/opal/x5
    //   635	655	658	wtf/opal/x5
    //   652	699	702	wtf/opal/x5
    //   729	737	740	wtf/opal/x5
    //   734	783	786	wtf/opal/x5
    //   790	798	801	wtf/opal/x5
    //   795	809	809	wtf/opal/x5
    //   826	834	837	wtf/opal/x5
    //   831	857	857	wtf/opal/x5
    //   890	912	915	wtf/opal/x5
    //   893	922	925	wtf/opal/x5
    //   919	947	950	wtf/opal/x5
    //   929	957	960	wtf/opal/x5
    //   954	983	986	wtf/opal/x5
    //   964	993	996	wtf/opal/x5
    //   990	1037	1040	wtf/opal/x5
    //   1083	1091	1094	wtf/opal/x5
    //   1088	1116	1119	wtf/opal/x5
    //   1098	1126	1129	wtf/opal/x5
    //   1123	1147	1150	wtf/opal/x5
    //   1133	1157	1160	wtf/opal/x5
    //   1154	1178	1181	wtf/opal/x5
    //   1164	1188	1191	wtf/opal/x5
    //   1185	1214	1217	wtf/opal/x5
    //   1195	1224	1227	wtf/opal/x5
    //   1221	1245	1248	wtf/opal/x5
    //   1252	1260	1263	wtf/opal/x5
    //   1257	1286	1289	wtf/opal/x5
    //   1267	1296	1299	wtf/opal/x5
    //   1293	1325	1328	wtf/opal/x5
    //   1303	1335	1338	wtf/opal/x5
    //   1371	1396	1399	wtf/opal/x5
    //   1374	1406	1409	wtf/opal/x5
    //   1442	1467	1470	wtf/opal/x5
    //   1445	1477	1480	wtf/opal/x5
    //   1513	1538	1541	wtf/opal/x5
    //   1516	1548	1551	wtf/opal/x5
    //   1584	1632	1635	wtf/opal/x5
    //   1639	1665	1668	wtf/opal/x5
    //   1662	1681	1684	wtf/opal/x5
    //   1672	1691	1694	wtf/opal/x5
    //   1688	1714	1717	wtf/opal/x5
    //   1698	1787	1790	wtf/opal/x5
    //   1721	1831	1834	wtf/opal/x5
    //   1851	1859	1862	wtf/opal/x5
    //   1856	1880	1883	wtf/opal/x5
    //   1866	1890	1893	wtf/opal/x5
    //   1887	1911	1914	wtf/opal/x5
    //   1897	1921	1924	wtf/opal/x5
    //   1918	1947	1950	wtf/opal/x5
    //   1928	1957	1960	wtf/opal/x5
    //   1954	1978	1981	wtf/opal/x5
    //   1985	1993	1996	wtf/opal/x5
    //   1990	2022	2025	wtf/opal/x5
    //   2000	2032	2035	wtf/opal/x5
    //   2068	2119	2122	wtf/opal/x5
    //   2071	2129	2132	wtf/opal/x5
    //   2126	2152	2155	wtf/opal/x5
    //   2136	2225	2228	wtf/opal/x5
    //   2159	2269	2272	wtf/opal/x5
    //   2289	2297	2300	wtf/opal/x5
    //   2294	2308	2311	wtf/opal/x5
    //   2315	2324	2327	wtf/opal/x5
    //   2320	2349	2352	wtf/opal/x5
    //   2356	2416	2419	wtf/opal/x5
  }
  
  private boolean lambda$new$1(ke paramke) {
    return this.e.z().booleanValue();
  }
  
  private boolean lambda$new$0(ke paramke) {
    return this.D.z().booleanValue();
  }
  
  public static void g(int paramInt) {
    u = paramInt;
  }
  
  public static int e() {
    return u;
  }
  
  public static int k() {
    int i = e();
    try {
      if (i == 0)
        return 91; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return 0;
  }
  
  static {
    long l = a ^ 0x1391DCA98857L;
    g(0);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "\037=X£Îü5ªV}Tc\002\017,Õ\030çî.gµ;²\026\021ûèÔøQPGÒw+\005 L9\022\rZ\036ÌÂ{Ë\016>RLä(\007W4i¦V1Ï,©â£¸þË ;¾5ÙL[BC¯rFÒ²Íã\024Nf+Ó²üýY®\017 \nM0z°RøHIûR\031£Ñ\0059\b#fv§KVÌFG àÀ \024\0007týàM\"\030:\rDk|").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static x5 b(x5 paramx5) {
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x343F;
    if (g[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])j.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          j.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/uo", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = c[i].getBytes("ISO-8859-1");
      g[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return g[i];
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
    //   66: ldc_w 'wtf/opal/uo'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6531;
    if (m[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = k[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])p.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          p.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/uo", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      m[i] = Integer.valueOf(j);
    } 
    return m[i].intValue();
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
    //   66: ldc_w 'wtf/opal/uo'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\uo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */