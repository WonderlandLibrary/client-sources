package wtf.opal;

import com.google.common.util.concurrent.AtomicDouble;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_332;

public final class ld implements kx {
  private final kn Q;
  
  private final List<pr> i;
  
  private final d2 W;
  
  public static int a;
  
  private final int S;
  
  private final int c;
  
  private final boolean g;
  
  private float y;
  
  private static d[] K;
  
  private static final long b = on.a(-4157075531443239978L, 6024393506253480916L, MethodHandles.lookup().lookupClass()).a(280199340090185L);
  
  private static final String[] d;
  
  private static final String[] e;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] j;
  
  private static final Integer[] k;
  
  private static final Map l;
  
  public ld(long paramLong, kn paramkn, int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/ld.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 28537528405729
    //   11: lxor
    //   12: dup2
    //   13: bipush #48
    //   15: lushr
    //   16: l2i
    //   17: istore #5
    //   19: dup2
    //   20: bipush #16
    //   22: lshl
    //   23: bipush #48
    //   25: lushr
    //   26: l2i
    //   27: istore #6
    //   29: dup2
    //   30: bipush #32
    //   32: lshl
    //   33: bipush #32
    //   35: lushr
    //   36: l2i
    //   37: istore #7
    //   39: pop2
    //   40: pop2
    //   41: aload_0
    //   42: invokespecial <init> : ()V
    //   45: invokestatic g : ()[Lwtf/opal/d;
    //   48: aload_0
    //   49: new java/util/ArrayList
    //   52: dup
    //   53: invokespecial <init> : ()V
    //   56: putfield i : Ljava/util/List;
    //   59: astore #8
    //   61: aload_0
    //   62: sipush #7799
    //   65: ldc2_w 309016899797623394
    //   68: lload_1
    //   69: lxor
    //   70: <illegal opcode> a : (IJ)I
    //   75: putfield S : I
    //   78: aload_0
    //   79: sipush #28265
    //   82: ldc2_w 943771346049094259
    //   85: lload_1
    //   86: lxor
    //   87: <illegal opcode> a : (IJ)I
    //   92: putfield c : I
    //   95: aload_0
    //   96: ldc 16.0
    //   98: putfield y : F
    //   101: aload_0
    //   102: aload_3
    //   103: putfield Q : Lwtf/opal/kn;
    //   106: aload_0
    //   107: iload #4
    //   109: aload #8
    //   111: ifnonnull -> 138
    //   114: invokestatic values : ()[Lwtf/opal/kn;
    //   117: arraylength
    //   118: iconst_1
    //   119: isub
    //   120: if_icmpne -> 141
    //   123: goto -> 130
    //   126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   129: athrow
    //   130: iconst_1
    //   131: goto -> 138
    //   134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: goto -> 142
    //   141: iconst_0
    //   142: putfield g : Z
    //   145: aload_0
    //   146: new wtf/opal/d2
    //   149: dup
    //   150: sipush #3779
    //   153: ldc2_w 5985632248004621012
    //   156: lload_1
    //   157: lxor
    //   158: <illegal opcode> a : (IJ)I
    //   163: iload #4
    //   165: sipush #1069
    //   168: ldc2_w 5997221400073451577
    //   171: lload_1
    //   172: lxor
    //   173: <illegal opcode> a : (IJ)I
    //   178: imul
    //   179: iadd
    //   180: iload #5
    //   182: i2s
    //   183: swap
    //   184: dconst_1
    //   185: iload #6
    //   187: i2c
    //   188: iload #7
    //   190: fconst_1
    //   191: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   194: invokespecial <init> : (SIDCIFLwtf/opal/lp;)V
    //   197: putfield W : Lwtf/opal/d2;
    //   200: invokestatic D : ()[Lwtf/opal/d;
    //   203: lload_1
    //   204: lconst_0
    //   205: lcmp
    //   206: iflt -> 216
    //   209: ifnull -> 226
    //   212: iconst_5
    //   213: anewarray wtf/opal/d
    //   216: invokestatic M : ([Lwtf/opal/d;)V
    //   219: goto -> 226
    //   222: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   225: athrow
    //   226: return
    // Exception table:
    //   from	to	target	type
    //   61	123	126	wtf/opal/x5
    //   114	131	134	wtf/opal/x5
    //   142	219	222	wtf/opal/x5
  }
  
  public void g(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_332
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Integer
    //   15: invokevirtual intValue : ()I
    //   18: istore #7
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/Integer
    //   26: invokevirtual intValue : ()I
    //   29: istore_3
    //   30: dup
    //   31: iconst_3
    //   32: aaload
    //   33: checkcast java/lang/Long
    //   36: invokevirtual longValue : ()J
    //   39: lstore #5
    //   41: dup
    //   42: iconst_4
    //   43: aaload
    //   44: checkcast java/lang/Float
    //   47: invokevirtual floatValue : ()F
    //   50: fstore_2
    //   51: pop
    //   52: lload #5
    //   54: dup2
    //   55: ldc2_w 67491935032125
    //   58: lxor
    //   59: lstore #8
    //   61: dup2
    //   62: ldc2_w 135688353470910
    //   65: lxor
    //   66: lstore #10
    //   68: dup2
    //   69: ldc2_w 53439933111312
    //   72: lxor
    //   73: lstore #12
    //   75: pop2
    //   76: invokestatic g : ()[Lwtf/opal/d;
    //   79: astore #14
    //   81: aload #14
    //   83: ifnonnull -> 379
    //   86: aload_0
    //   87: getfield g : Z
    //   90: ifeq -> 204
    //   93: goto -> 100
    //   96: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   99: athrow
    //   100: aload_0
    //   101: getfield W : Lwtf/opal/d2;
    //   104: iconst_0
    //   105: anewarray java/lang/Object
    //   108: invokevirtual H : ([Ljava/lang/Object;)Lwtf/opal/lp;
    //   111: lload #8
    //   113: iconst_1
    //   114: anewarray java/lang/Object
    //   117: dup_x2
    //   118: dup_x2
    //   119: pop
    //   120: invokestatic valueOf : (J)Ljava/lang/Long;
    //   123: iconst_0
    //   124: swap
    //   125: aastore
    //   126: invokevirtual A : ([Ljava/lang/Object;)Z
    //   129: lload #5
    //   131: lconst_0
    //   132: lcmp
    //   133: iflt -> 187
    //   136: aload #14
    //   138: ifnonnull -> 187
    //   141: goto -> 148
    //   144: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   147: athrow
    //   148: ifeq -> 204
    //   151: goto -> 158
    //   154: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   157: athrow
    //   158: aload_0
    //   159: getfield W : Lwtf/opal/d2;
    //   162: lload #12
    //   164: iconst_1
    //   165: anewarray java/lang/Object
    //   168: dup_x2
    //   169: dup_x2
    //   170: pop
    //   171: invokestatic valueOf : (J)Ljava/lang/Long;
    //   174: iconst_0
    //   175: swap
    //   176: aastore
    //   177: invokevirtual H : ([Ljava/lang/Object;)Z
    //   180: goto -> 187
    //   183: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   186: athrow
    //   187: ifeq -> 204
    //   190: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   193: aconst_null
    //   194: invokevirtual method_1507 : (Lnet/minecraft/class_437;)V
    //   197: goto -> 204
    //   200: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   203: athrow
    //   204: getstatic wtf/opal/ld.h : Lwtf/opal/pa;
    //   207: iconst_0
    //   208: anewarray java/lang/Object
    //   211: invokevirtual y : ([Ljava/lang/Object;)J
    //   214: aload_0
    //   215: getfield W : Lwtf/opal/d2;
    //   218: lload #10
    //   220: iconst_1
    //   221: anewarray java/lang/Object
    //   224: dup_x2
    //   225: dup_x2
    //   226: pop
    //   227: invokestatic valueOf : (J)Ljava/lang/Long;
    //   230: iconst_0
    //   231: swap
    //   232: aastore
    //   233: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   236: invokevirtual floatValue : ()F
    //   239: invokestatic nvgGlobalAlpha : (JF)V
    //   242: getstatic wtf/opal/ld.h : Lwtf/opal/pa;
    //   245: aload_0
    //   246: getfield W : Lwtf/opal/d2;
    //   249: lload #10
    //   251: iconst_1
    //   252: anewarray java/lang/Object
    //   255: dup_x2
    //   256: dup_x2
    //   257: pop
    //   258: invokestatic valueOf : (J)Ljava/lang/Long;
    //   261: iconst_0
    //   262: swap
    //   263: aastore
    //   264: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   267: invokevirtual floatValue : ()F
    //   270: aload_0
    //   271: getfield Q : Lwtf/opal/kn;
    //   274: iconst_0
    //   275: anewarray java/lang/Object
    //   278: invokevirtual Q : ([Ljava/lang/Object;)I
    //   281: i2f
    //   282: aload_0
    //   283: getfield Q : Lwtf/opal/kn;
    //   286: iconst_0
    //   287: anewarray java/lang/Object
    //   290: invokevirtual A : ([Ljava/lang/Object;)I
    //   293: i2f
    //   294: ldc 100.0
    //   296: aload_0
    //   297: getfield y : F
    //   300: aload_0
    //   301: aload #4
    //   303: iload #7
    //   305: iload_3
    //   306: fload_2
    //   307: <illegal opcode> run : (Lwtf/opal/ld;Lnet/minecraft/class_332;IIF)Ljava/lang/Runnable;
    //   312: bipush #6
    //   314: anewarray java/lang/Object
    //   317: dup_x1
    //   318: swap
    //   319: iconst_5
    //   320: swap
    //   321: aastore
    //   322: dup_x1
    //   323: swap
    //   324: invokestatic valueOf : (F)Ljava/lang/Float;
    //   327: iconst_4
    //   328: swap
    //   329: aastore
    //   330: dup_x1
    //   331: swap
    //   332: invokestatic valueOf : (F)Ljava/lang/Float;
    //   335: iconst_3
    //   336: swap
    //   337: aastore
    //   338: dup_x1
    //   339: swap
    //   340: invokestatic valueOf : (F)Ljava/lang/Float;
    //   343: iconst_2
    //   344: swap
    //   345: aastore
    //   346: dup_x1
    //   347: swap
    //   348: invokestatic valueOf : (F)Ljava/lang/Float;
    //   351: iconst_1
    //   352: swap
    //   353: aastore
    //   354: dup_x1
    //   355: swap
    //   356: invokestatic valueOf : (F)Ljava/lang/Float;
    //   359: iconst_0
    //   360: swap
    //   361: aastore
    //   362: invokevirtual r : ([Ljava/lang/Object;)V
    //   365: getstatic wtf/opal/ld.h : Lwtf/opal/pa;
    //   368: iconst_0
    //   369: anewarray java/lang/Object
    //   372: invokevirtual y : ([Ljava/lang/Object;)J
    //   375: fconst_1
    //   376: invokestatic nvgGlobalAlpha : (JF)V
    //   379: return
    // Exception table:
    //   from	to	target	type
    //   81	93	96	wtf/opal/x5
    //   86	141	144	wtf/opal/x5
    //   100	151	154	wtf/opal/x5
    //   148	180	183	wtf/opal/x5
    //   187	197	200	wtf/opal/x5
  }
  
  public void e(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Double
    //   7: invokevirtual doubleValue : ()D
    //   10: dstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore #4
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Long
    //   28: invokevirtual longValue : ()J
    //   31: lstore #6
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore #8
    //   44: pop
    //   45: aload_0
    //   46: getfield i : Ljava/util/List;
    //   49: dload_2
    //   50: dload #4
    //   52: iload #8
    //   54: <illegal opcode> accept : (DDI)Ljava/util/function/Consumer;
    //   59: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   64: return
  }
  
  public void c(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore #4
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Double
    //   28: invokevirtual doubleValue : ()D
    //   31: dstore #6
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore #8
    //   44: pop
    //   45: aload_0
    //   46: getfield i : Ljava/util/List;
    //   49: dload #4
    //   51: dload #6
    //   53: iload #8
    //   55: <illegal opcode> accept : (DDI)Ljava/util/function/Consumer;
    //   60: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   65: return
  }
  
  public void d(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_3
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast java/lang/Long
    //   27: invokevirtual longValue : ()J
    //   30: lstore #4
    //   32: pop
    //   33: aload_0
    //   34: getfield i : Ljava/util/List;
    //   37: iload_2
    //   38: iload_3
    //   39: <illegal opcode> accept : (CI)Ljava/util/function/Consumer;
    //   44: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   49: return
  }
  
  public void o(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
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
    //   20: istore #6
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Integer
    //   28: invokevirtual intValue : ()I
    //   31: istore #5
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore #4
    //   44: pop
    //   45: aload_0
    //   46: getfield i : Ljava/util/List;
    //   49: iload #6
    //   51: iload #5
    //   53: iload #4
    //   55: <illegal opcode> accept : (III)Ljava/util/function/Consumer;
    //   60: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   65: return
  }
  
  public void w(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Double
    //   7: invokevirtual doubleValue : ()D
    //   10: dstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore #4
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Double
    //   28: invokevirtual doubleValue : ()D
    //   31: dstore #6
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Long
    //   39: invokevirtual longValue : ()J
    //   42: lstore #8
    //   44: dup
    //   45: iconst_4
    //   46: aaload
    //   47: checkcast java/lang/Double
    //   50: invokevirtual doubleValue : ()D
    //   53: dstore #10
    //   55: pop
    //   56: aload_0
    //   57: getfield i : Ljava/util/List;
    //   60: dload_2
    //   61: dload #4
    //   63: dload #6
    //   65: dload #10
    //   67: <illegal opcode> accept : (DDDD)Ljava/util/function/Consumer;
    //   72: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   77: return
  }
  
  public void u() {
    // Byte code:
    //   0: getstatic wtf/opal/ld.b : J
    //   3: ldc2_w 126511445469355
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 102608860815062
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: invokestatic g : ()[Lwtf/opal/d;
    //   19: astore #5
    //   21: aload_0
    //   22: getfield i : Ljava/util/List;
    //   25: aload #5
    //   27: ifnonnull -> 246
    //   30: invokeinterface isEmpty : ()Z
    //   35: ifeq -> 242
    //   38: goto -> 45
    //   41: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: iconst_0
    //   46: anewarray java/lang/Object
    //   49: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   52: iconst_0
    //   53: anewarray java/lang/Object
    //   56: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   59: iconst_0
    //   60: anewarray java/lang/Object
    //   63: invokevirtual g : ([Ljava/lang/Object;)Ljava/util/Collection;
    //   66: invokeinterface iterator : ()Ljava/util/Iterator;
    //   71: astore #6
    //   73: aload #6
    //   75: invokeinterface hasNext : ()Z
    //   80: ifeq -> 155
    //   83: aload #6
    //   85: invokeinterface next : ()Ljava/lang/Object;
    //   90: checkcast wtf/opal/d
    //   93: astore #7
    //   95: aload #7
    //   97: iconst_0
    //   98: anewarray java/lang/Object
    //   101: invokevirtual X : ([Ljava/lang/Object;)Lwtf/opal/kn;
    //   104: aload #5
    //   106: ifnonnull -> 223
    //   109: aload_0
    //   110: getfield Q : Lwtf/opal/kn;
    //   113: if_acmpne -> 150
    //   116: goto -> 123
    //   119: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   122: athrow
    //   123: aload_0
    //   124: getfield i : Ljava/util/List;
    //   127: new wtf/opal/pr
    //   130: dup
    //   131: lload_3
    //   132: aload #7
    //   134: invokespecial <init> : (JLwtf/opal/d;)V
    //   137: invokeinterface add : (Ljava/lang/Object;)Z
    //   142: pop
    //   143: goto -> 150
    //   146: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   149: athrow
    //   150: aload #5
    //   152: ifnull -> 73
    //   155: aload_0
    //   156: getfield i : Ljava/util/List;
    //   159: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   164: invokestatic comparing : (Ljava/util/function/Function;)Ljava/util/Comparator;
    //   167: invokeinterface sort : (Ljava/util/Comparator;)V
    //   172: aload_0
    //   173: getfield i : Ljava/util/List;
    //   176: aload #5
    //   178: ifnonnull -> 246
    //   181: invokeinterface isEmpty : ()Z
    //   186: ifne -> 242
    //   189: goto -> 196
    //   192: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   195: athrow
    //   196: aload_0
    //   197: getfield i : Ljava/util/List;
    //   200: aload_0
    //   201: getfield i : Ljava/util/List;
    //   204: invokeinterface size : ()I
    //   209: iconst_1
    //   210: isub
    //   211: invokeinterface get : (I)Ljava/lang/Object;
    //   216: goto -> 223
    //   219: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   222: athrow
    //   223: checkcast wtf/opal/pr
    //   226: iconst_1
    //   227: iconst_1
    //   228: anewarray java/lang/Object
    //   231: dup_x1
    //   232: swap
    //   233: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   236: iconst_0
    //   237: swap
    //   238: aastore
    //   239: invokevirtual k : ([Ljava/lang/Object;)V
    //   242: aload_0
    //   243: getfield i : Ljava/util/List;
    //   246: <illegal opcode> accept : ()Ljava/util/function/Consumer;
    //   251: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   256: return
    // Exception table:
    //   from	to	target	type
    //   21	38	41	wtf/opal/x5
    //   95	116	119	wtf/opal/x5
    //   109	143	146	wtf/opal/x5
    //   155	189	192	wtf/opal/x5
    //   181	216	219	wtf/opal/x5
  }
  
  public void s() {
    // Byte code:
    //   0: getstatic wtf/opal/ld.b : J
    //   3: ldc2_w 106773224741007
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 68607928867669
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: aload_0
    //   17: getfield W : Lwtf/opal/d2;
    //   20: lload_3
    //   21: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   24: iconst_2
    //   25: anewarray java/lang/Object
    //   28: dup_x1
    //   29: swap
    //   30: iconst_1
    //   31: swap
    //   32: aastore
    //   33: dup_x2
    //   34: dup_x2
    //   35: pop
    //   36: invokestatic valueOf : (J)Ljava/lang/Long;
    //   39: iconst_0
    //   40: swap
    //   41: aastore
    //   42: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   45: pop
    //   46: aload_0
    //   47: getfield W : Lwtf/opal/d2;
    //   50: sipush #16710
    //   53: ldc2_w 5012824434605973336
    //   56: lload_1
    //   57: lxor
    //   58: <illegal opcode> a : (IJ)I
    //   63: iconst_1
    //   64: anewarray java/lang/Object
    //   67: dup_x1
    //   68: swap
    //   69: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   72: iconst_0
    //   73: swap
    //   74: aastore
    //   75: invokevirtual C : ([Ljava/lang/Object;)V
    //   78: return
  }
  
  private static String lambda$init$7(pr parampr) {
    return parampr.D(new Object[0]).m(new Object[0]);
  }
  
  private static void lambda$mouseScrolled$6(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, pr parampr) {
    long l1 = b ^ 0x4BCFB98D4BFFL;
    long l2 = l1 ^ 0x3E9578D71F47L;
    new Object[5];
    (new Object[5])[4] = Double.valueOf(paramDouble4);
    new Object[5];
    (new Object[5])[3] = Long.valueOf(l2);
    new Object[5];
    (new Object[5])[2] = Double.valueOf(paramDouble3);
    new Object[5];
    (new Object[5])[1] = Double.valueOf(paramDouble2);
    new Object[5];
    parampr.w(new Object[] { Double.valueOf(paramDouble1) });
  }
  
  private static void lambda$keyPressed$5(int paramInt1, int paramInt2, int paramInt3, pr parampr) {
    long l1 = b ^ 0x2EA52DD5CD12L;
    long l2 = l1 ^ 0x6999119A31A6L;
    (new Object[4])[3] = Integer.valueOf(paramInt3);
    (new Object[4])[2] = Integer.valueOf(paramInt2);
    (new Object[4])[1] = Integer.valueOf(paramInt1);
    new Object[4];
    parampr.o(new Object[] { Long.valueOf(l2) });
  }
  
  private static void lambda$charTyped$4(char paramChar, int paramInt, pr parampr) {
    long l1 = b ^ 0x57D4CE6C1DB3L;
    long l2 = l1 ^ 0x44EAD681FB68L;
    new Object[3];
    parampr.d(new Object[] { null, null, Long.valueOf(l2), Integer.valueOf(paramInt), Integer.valueOf(paramChar) });
  }
  
  private static void lambda$mouseReleased$3(double paramDouble1, double paramDouble2, int paramInt, pr parampr) {
    long l1 = b ^ 0x56639FB3D101L;
    long l2 = l1 ^ 0x73C5AB63A675L;
    (new Object[4])[3] = Integer.valueOf(paramInt);
    new Object[4];
    (new Object[4])[2] = Double.valueOf(paramDouble2);
    new Object[4];
    (new Object[4])[1] = Double.valueOf(paramDouble1);
    new Object[4];
    parampr.c(new Object[] { Long.valueOf(l2) });
  }
  
  private static void lambda$mouseClicked$2(double paramDouble1, double paramDouble2, int paramInt, pr parampr) {
    long l1 = b ^ 0x7249E0F22E20L;
    long l2 = l1 ^ 0x2E806936E468L;
    (new Object[4])[3] = Integer.valueOf(paramInt);
    new Object[4];
    (new Object[4])[2] = Long.valueOf(l2);
    new Object[4];
    (new Object[4])[1] = Double.valueOf(paramDouble2);
    new Object[4];
    parampr.e(new Object[] { Double.valueOf(paramDouble1) });
  }
  
  private void lambda$render$1(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: getstatic wtf/opal/ld.b : J
    //   3: ldc2_w 60657867730592
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 49425795922062
    //   15: lxor
    //   16: lstore #7
    //   18: dup2
    //   19: ldc2_w 105314954205790
    //   22: lxor
    //   23: lstore #9
    //   25: dup2
    //   26: ldc2_w 97270999196783
    //   29: lxor
    //   30: lstore #11
    //   32: dup2
    //   33: ldc2_w 2804875634492
    //   36: lxor
    //   37: lstore #13
    //   39: dup2
    //   40: ldc2_w 109318455081809
    //   43: lxor
    //   44: lstore #15
    //   46: dup2
    //   47: ldc2_w 65925851328161
    //   50: lxor
    //   51: lstore #17
    //   53: dup2
    //   54: ldc2_w 125025445491078
    //   57: lxor
    //   58: lstore #19
    //   60: dup2
    //   61: ldc2_w 77954892544452
    //   64: lxor
    //   65: lstore #21
    //   67: pop2
    //   68: getstatic wtf/opal/ld.h : Lwtf/opal/pa;
    //   71: aload_0
    //   72: getfield Q : Lwtf/opal/kn;
    //   75: iconst_0
    //   76: anewarray java/lang/Object
    //   79: invokevirtual Q : ([Ljava/lang/Object;)I
    //   82: i2f
    //   83: aload_0
    //   84: getfield Q : Lwtf/opal/kn;
    //   87: iconst_0
    //   88: anewarray java/lang/Object
    //   91: invokevirtual A : ([Ljava/lang/Object;)I
    //   94: i2f
    //   95: ldc 100.0
    //   97: aload_0
    //   98: getfield y : F
    //   101: fconst_1
    //   102: fadd
    //   103: ldc_w 5.0
    //   106: sipush #9360
    //   109: ldc2_w 8505388795534560418
    //   112: lload #5
    //   114: lxor
    //   115: <illegal opcode> a : (IJ)I
    //   120: lload #13
    //   122: bipush #7
    //   124: anewarray java/lang/Object
    //   127: dup_x2
    //   128: dup_x2
    //   129: pop
    //   130: invokestatic valueOf : (J)Ljava/lang/Long;
    //   133: bipush #6
    //   135: swap
    //   136: aastore
    //   137: dup_x1
    //   138: swap
    //   139: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   142: iconst_5
    //   143: swap
    //   144: aastore
    //   145: dup_x1
    //   146: swap
    //   147: invokestatic valueOf : (F)Ljava/lang/Float;
    //   150: iconst_4
    //   151: swap
    //   152: aastore
    //   153: dup_x1
    //   154: swap
    //   155: invokestatic valueOf : (F)Ljava/lang/Float;
    //   158: iconst_3
    //   159: swap
    //   160: aastore
    //   161: dup_x1
    //   162: swap
    //   163: invokestatic valueOf : (F)Ljava/lang/Float;
    //   166: iconst_2
    //   167: swap
    //   168: aastore
    //   169: dup_x1
    //   170: swap
    //   171: invokestatic valueOf : (F)Ljava/lang/Float;
    //   174: iconst_1
    //   175: swap
    //   176: aastore
    //   177: dup_x1
    //   178: swap
    //   179: invokestatic valueOf : (F)Ljava/lang/Float;
    //   182: iconst_0
    //   183: swap
    //   184: aastore
    //   185: invokevirtual M : ([Ljava/lang/Object;)V
    //   188: ldc_w 8.0
    //   191: getstatic wtf/opal/ld.M : Lwtf/opal/dc;
    //   194: sipush #14280
    //   197: ldc2_w 3156807387683354865
    //   200: lload #5
    //   202: lxor
    //   203: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   208: lload #15
    //   210: iconst_2
    //   211: anewarray java/lang/Object
    //   214: dup_x2
    //   215: dup_x2
    //   216: pop
    //   217: invokestatic valueOf : (J)Ljava/lang/Long;
    //   220: iconst_1
    //   221: swap
    //   222: aastore
    //   223: dup_x1
    //   224: swap
    //   225: iconst_0
    //   226: swap
    //   227: aastore
    //   228: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   231: aload_0
    //   232: getfield Q : Lwtf/opal/kn;
    //   235: iconst_0
    //   236: anewarray java/lang/Object
    //   239: invokevirtual R : ([Ljava/lang/Object;)Ljava/lang/String;
    //   242: ldc_w 11.0
    //   245: lload #19
    //   247: sipush #1346
    //   250: ldc2_w 5543047470706453874
    //   253: lload #5
    //   255: lxor
    //   256: <illegal opcode> a : (IJ)I
    //   261: iconst_4
    //   262: anewarray java/lang/Object
    //   265: dup_x1
    //   266: swap
    //   267: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   270: iconst_3
    //   271: swap
    //   272: aastore
    //   273: dup_x2
    //   274: dup_x2
    //   275: pop
    //   276: invokestatic valueOf : (J)Ljava/lang/Long;
    //   279: iconst_2
    //   280: swap
    //   281: aastore
    //   282: dup_x1
    //   283: swap
    //   284: invokestatic valueOf : (F)Ljava/lang/Float;
    //   287: iconst_1
    //   288: swap
    //   289: aastore
    //   290: dup_x1
    //   291: swap
    //   292: iconst_0
    //   293: swap
    //   294: aastore
    //   295: invokevirtual w : ([Ljava/lang/Object;)F
    //   298: fconst_2
    //   299: fdiv
    //   300: fsub
    //   301: fstore #24
    //   303: invokestatic g : ()[Lwtf/opal/d;
    //   306: getstatic wtf/opal/ld.M : Lwtf/opal/dc;
    //   309: sipush #22475
    //   312: ldc2_w 4325703210987362544
    //   315: lload #5
    //   317: lxor
    //   318: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   323: lload #15
    //   325: iconst_2
    //   326: anewarray java/lang/Object
    //   329: dup_x2
    //   330: dup_x2
    //   331: pop
    //   332: invokestatic valueOf : (J)Ljava/lang/Long;
    //   335: iconst_1
    //   336: swap
    //   337: aastore
    //   338: dup_x1
    //   339: swap
    //   340: iconst_0
    //   341: swap
    //   342: aastore
    //   343: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   346: aload_0
    //   347: getfield Q : Lwtf/opal/kn;
    //   350: iconst_0
    //   351: anewarray java/lang/Object
    //   354: invokevirtual R : ([Ljava/lang/Object;)Ljava/lang/String;
    //   357: aload_0
    //   358: getfield Q : Lwtf/opal/kn;
    //   361: iconst_0
    //   362: anewarray java/lang/Object
    //   365: invokevirtual Q : ([Ljava/lang/Object;)I
    //   368: iconst_4
    //   369: iadd
    //   370: i2f
    //   371: aload_0
    //   372: getfield Q : Lwtf/opal/kn;
    //   375: iconst_0
    //   376: anewarray java/lang/Object
    //   379: invokevirtual A : ([Ljava/lang/Object;)I
    //   382: i2f
    //   383: fload #24
    //   385: fadd
    //   386: ldc_w 11.0
    //   389: iconst_m1
    //   390: iconst_0
    //   391: lload #17
    //   393: bipush #7
    //   395: anewarray java/lang/Object
    //   398: dup_x2
    //   399: dup_x2
    //   400: pop
    //   401: invokestatic valueOf : (J)Ljava/lang/Long;
    //   404: bipush #6
    //   406: swap
    //   407: aastore
    //   408: dup_x1
    //   409: swap
    //   410: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   413: iconst_5
    //   414: swap
    //   415: aastore
    //   416: dup_x1
    //   417: swap
    //   418: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   421: iconst_4
    //   422: swap
    //   423: aastore
    //   424: dup_x1
    //   425: swap
    //   426: invokestatic valueOf : (F)Ljava/lang/Float;
    //   429: iconst_3
    //   430: swap
    //   431: aastore
    //   432: dup_x1
    //   433: swap
    //   434: invokestatic valueOf : (F)Ljava/lang/Float;
    //   437: iconst_2
    //   438: swap
    //   439: aastore
    //   440: dup_x1
    //   441: swap
    //   442: invokestatic valueOf : (F)Ljava/lang/Float;
    //   445: iconst_1
    //   446: swap
    //   447: aastore
    //   448: dup_x1
    //   449: swap
    //   450: iconst_0
    //   451: swap
    //   452: aastore
    //   453: invokevirtual b : ([Ljava/lang/Object;)V
    //   456: astore #23
    //   458: ldc_w 8.0
    //   461: getstatic wtf/opal/ld.Z : Lwtf/opal/bu;
    //   464: lload #9
    //   466: getstatic wtf/opal/lx.MEDIUM : Lwtf/opal/lx;
    //   469: aload_0
    //   470: getfield Q : Lwtf/opal/kn;
    //   473: iconst_0
    //   474: anewarray java/lang/Object
    //   477: invokevirtual S : ([Ljava/lang/Object;)Ljava/lang/String;
    //   480: ldc_w 9.0
    //   483: sipush #7215
    //   486: ldc2_w 1030702898864092187
    //   489: lload #5
    //   491: lxor
    //   492: <illegal opcode> a : (IJ)I
    //   497: iconst_5
    //   498: anewarray java/lang/Object
    //   501: dup_x1
    //   502: swap
    //   503: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   506: iconst_4
    //   507: swap
    //   508: aastore
    //   509: dup_x1
    //   510: swap
    //   511: invokestatic valueOf : (F)Ljava/lang/Float;
    //   514: iconst_3
    //   515: swap
    //   516: aastore
    //   517: dup_x1
    //   518: swap
    //   519: iconst_2
    //   520: swap
    //   521: aastore
    //   522: dup_x1
    //   523: swap
    //   524: iconst_1
    //   525: swap
    //   526: aastore
    //   527: dup_x2
    //   528: dup_x2
    //   529: pop
    //   530: invokestatic valueOf : (J)Ljava/lang/Long;
    //   533: iconst_0
    //   534: swap
    //   535: aastore
    //   536: invokevirtual e : ([Ljava/lang/Object;)F
    //   539: fconst_2
    //   540: fdiv
    //   541: fsub
    //   542: fstore #25
    //   544: iconst_0
    //   545: anewarray java/lang/Object
    //   548: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   551: iconst_0
    //   552: anewarray java/lang/Object
    //   555: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   558: ldc_w wtf/opal/jt
    //   561: iconst_1
    //   562: anewarray java/lang/Object
    //   565: dup_x1
    //   566: swap
    //   567: iconst_0
    //   568: swap
    //   569: aastore
    //   570: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   573: checkcast wtf/opal/jt
    //   576: astore #26
    //   578: aload #26
    //   580: lload #7
    //   582: iconst_1
    //   583: anewarray java/lang/Object
    //   586: dup_x2
    //   587: dup_x2
    //   588: pop
    //   589: invokestatic valueOf : (J)Ljava/lang/Long;
    //   592: iconst_0
    //   593: swap
    //   594: aastore
    //   595: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   598: getstatic wtf/opal/dv.GEIST_SANS : Lwtf/opal/dv;
    //   601: invokevirtual equals : (Ljava/lang/Object;)Z
    //   604: aload #23
    //   606: ifnonnull -> 863
    //   609: ifeq -> 693
    //   612: goto -> 619
    //   615: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   618: athrow
    //   619: getstatic wtf/opal/ld.h : Lwtf/opal/pa;
    //   622: iconst_0
    //   623: anewarray java/lang/Object
    //   626: invokevirtual y : ([Ljava/lang/Object;)J
    //   629: iconst_0
    //   630: anewarray java/lang/Object
    //   633: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   636: iconst_0
    //   637: anewarray java/lang/Object
    //   640: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   643: lload #11
    //   645: sipush #21528
    //   648: ldc2_w 7503482914997082912
    //   651: lload #5
    //   653: lxor
    //   654: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   659: iconst_2
    //   660: anewarray java/lang/Object
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
    //   677: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   680: invokestatic parseFloat : (Ljava/lang/String;)F
    //   683: invokestatic nvgTextLetterSpacing : (JF)V
    //   686: goto -> 693
    //   689: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   692: athrow
    //   693: getstatic wtf/opal/ld.Z : Lwtf/opal/bu;
    //   696: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   699: aload_1
    //   700: aload_0
    //   701: getfield Q : Lwtf/opal/kn;
    //   704: iconst_0
    //   705: anewarray java/lang/Object
    //   708: invokevirtual S : ([Ljava/lang/Object;)Ljava/lang/String;
    //   711: aload_0
    //   712: getfield Q : Lwtf/opal/kn;
    //   715: iconst_0
    //   716: anewarray java/lang/Object
    //   719: invokevirtual Q : ([Ljava/lang/Object;)I
    //   722: sipush #234
    //   725: ldc2_w 2422978881678010589
    //   728: lload #5
    //   730: lxor
    //   731: <illegal opcode> a : (IJ)I
    //   736: iadd
    //   737: i2f
    //   738: aload_0
    //   739: getfield Q : Lwtf/opal/kn;
    //   742: iconst_0
    //   743: anewarray java/lang/Object
    //   746: invokevirtual A : ([Ljava/lang/Object;)I
    //   749: i2f
    //   750: fload #25
    //   752: fadd
    //   753: lload #21
    //   755: dup2_x1
    //   756: pop2
    //   757: ldc_w 9.0
    //   760: iconst_m1
    //   761: iconst_0
    //   762: bipush #9
    //   764: anewarray java/lang/Object
    //   767: dup_x1
    //   768: swap
    //   769: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   772: bipush #8
    //   774: swap
    //   775: aastore
    //   776: dup_x1
    //   777: swap
    //   778: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   781: bipush #7
    //   783: swap
    //   784: aastore
    //   785: dup_x1
    //   786: swap
    //   787: invokestatic valueOf : (F)Ljava/lang/Float;
    //   790: bipush #6
    //   792: swap
    //   793: aastore
    //   794: dup_x1
    //   795: swap
    //   796: invokestatic valueOf : (F)Ljava/lang/Float;
    //   799: iconst_5
    //   800: swap
    //   801: aastore
    //   802: dup_x2
    //   803: dup_x2
    //   804: pop
    //   805: invokestatic valueOf : (J)Ljava/lang/Long;
    //   808: iconst_4
    //   809: swap
    //   810: aastore
    //   811: dup_x1
    //   812: swap
    //   813: invokestatic valueOf : (F)Ljava/lang/Float;
    //   816: iconst_3
    //   817: swap
    //   818: aastore
    //   819: dup_x1
    //   820: swap
    //   821: iconst_2
    //   822: swap
    //   823: aastore
    //   824: dup_x1
    //   825: swap
    //   826: iconst_1
    //   827: swap
    //   828: aastore
    //   829: dup_x1
    //   830: swap
    //   831: iconst_0
    //   832: swap
    //   833: aastore
    //   834: invokevirtual H : ([Ljava/lang/Object;)V
    //   837: aload #26
    //   839: lload #7
    //   841: iconst_1
    //   842: anewarray java/lang/Object
    //   845: dup_x2
    //   846: dup_x2
    //   847: pop
    //   848: invokestatic valueOf : (J)Ljava/lang/Long;
    //   851: iconst_0
    //   852: swap
    //   853: aastore
    //   854: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   857: getstatic wtf/opal/dv.GEIST_SANS : Lwtf/opal/dv;
    //   860: invokevirtual equals : (Ljava/lang/Object;)Z
    //   863: ifeq -> 887
    //   866: getstatic wtf/opal/ld.h : Lwtf/opal/pa;
    //   869: iconst_0
    //   870: anewarray java/lang/Object
    //   873: invokevirtual y : ([Ljava/lang/Object;)J
    //   876: fconst_0
    //   877: invokestatic nvgTextLetterSpacing : (JF)V
    //   880: goto -> 887
    //   883: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   886: athrow
    //   887: new com/google/common/util/concurrent/AtomicDouble
    //   890: dup
    //   891: invokespecial <init> : ()V
    //   894: astore #27
    //   896: aload_0
    //   897: getfield i : Ljava/util/List;
    //   900: aload_0
    //   901: aload #27
    //   903: aload_1
    //   904: iload_2
    //   905: iload_3
    //   906: fload #4
    //   908: <illegal opcode> accept : (Lwtf/opal/ld;Lcom/google/common/util/concurrent/AtomicDouble;Lnet/minecraft/class_332;IIF)Ljava/util/function/Consumer;
    //   913: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   918: aload_0
    //   919: ldc2_w 16.0
    //   922: aload #27
    //   924: invokevirtual get : ()D
    //   927: dadd
    //   928: d2f
    //   929: putfield y : F
    //   932: return
    // Exception table:
    //   from	to	target	type
    //   578	612	615	wtf/opal/x5
    //   609	686	689	wtf/opal/x5
    //   863	880	883	wtf/opal/x5
  }
  
  private void lambda$render$0(AtomicDouble paramAtomicDouble, class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat, pr parampr) {
    // Byte code:
    //   0: getstatic wtf/opal/ld.b : J
    //   3: ldc2_w 127733782754785
    //   6: lxor
    //   7: lstore #7
    //   9: lload #7
    //   11: dup2
    //   12: ldc2_w 122172725606047
    //   15: lxor
    //   16: lstore #9
    //   18: dup2
    //   19: ldc2_w 22496601632545
    //   22: lxor
    //   23: lstore #11
    //   25: pop2
    //   26: aload #6
    //   28: aload_0
    //   29: getfield Q : Lwtf/opal/kn;
    //   32: iconst_0
    //   33: anewarray java/lang/Object
    //   36: invokevirtual Q : ([Ljava/lang/Object;)I
    //   39: iconst_1
    //   40: iadd
    //   41: iconst_1
    //   42: anewarray java/lang/Object
    //   45: dup_x1
    //   46: swap
    //   47: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   50: iconst_0
    //   51: swap
    //   52: aastore
    //   53: invokevirtual B : ([Ljava/lang/Object;)V
    //   56: aload #6
    //   58: aload_0
    //   59: getfield Q : Lwtf/opal/kn;
    //   62: iconst_0
    //   63: anewarray java/lang/Object
    //   66: invokevirtual A : ([Ljava/lang/Object;)I
    //   69: sipush #1563
    //   72: ldc2_w 3434599444426454377
    //   75: lload #7
    //   77: lxor
    //   78: <illegal opcode> a : (IJ)I
    //   83: iadd
    //   84: i2d
    //   85: aload_1
    //   86: invokevirtual get : ()D
    //   89: dadd
    //   90: d2i
    //   91: iconst_1
    //   92: anewarray java/lang/Object
    //   95: dup_x1
    //   96: swap
    //   97: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   100: iconst_0
    //   101: swap
    //   102: aastore
    //   103: invokevirtual I : ([Ljava/lang/Object;)V
    //   106: aload #6
    //   108: sipush #14632
    //   111: ldc2_w 7598449569407912543
    //   114: lload #7
    //   116: lxor
    //   117: <illegal opcode> a : (IJ)I
    //   122: iconst_1
    //   123: anewarray java/lang/Object
    //   126: dup_x1
    //   127: swap
    //   128: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   131: iconst_0
    //   132: swap
    //   133: aastore
    //   134: invokevirtual U : ([Ljava/lang/Object;)V
    //   137: aload #6
    //   139: sipush #1563
    //   142: ldc2_w 3434599444426454377
    //   145: lload #7
    //   147: lxor
    //   148: <illegal opcode> a : (IJ)I
    //   153: iconst_1
    //   154: anewarray java/lang/Object
    //   157: dup_x1
    //   158: swap
    //   159: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   162: iconst_0
    //   163: swap
    //   164: aastore
    //   165: invokevirtual f : ([Ljava/lang/Object;)V
    //   168: aload #6
    //   170: aload_2
    //   171: iload_3
    //   172: iload #4
    //   174: lload #11
    //   176: fload #5
    //   178: iconst_5
    //   179: anewarray java/lang/Object
    //   182: dup_x1
    //   183: swap
    //   184: invokestatic valueOf : (F)Ljava/lang/Float;
    //   187: iconst_4
    //   188: swap
    //   189: aastore
    //   190: dup_x2
    //   191: dup_x2
    //   192: pop
    //   193: invokestatic valueOf : (J)Ljava/lang/Long;
    //   196: iconst_3
    //   197: swap
    //   198: aastore
    //   199: dup_x1
    //   200: swap
    //   201: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   204: iconst_2
    //   205: swap
    //   206: aastore
    //   207: dup_x1
    //   208: swap
    //   209: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   212: iconst_1
    //   213: swap
    //   214: aastore
    //   215: dup_x1
    //   216: swap
    //   217: iconst_0
    //   218: swap
    //   219: aastore
    //   220: invokevirtual g : ([Ljava/lang/Object;)V
    //   223: aload_1
    //   224: ldc2_w 16.0
    //   227: aload #6
    //   229: iconst_0
    //   230: anewarray java/lang/Object
    //   233: invokevirtual P : ([Ljava/lang/Object;)F
    //   236: f2d
    //   237: aload #6
    //   239: iconst_0
    //   240: anewarray java/lang/Object
    //   243: invokevirtual o : ([Ljava/lang/Object;)Lwtf/opal/dk;
    //   246: lload #9
    //   248: iconst_1
    //   249: anewarray java/lang/Object
    //   252: dup_x2
    //   253: dup_x2
    //   254: pop
    //   255: invokestatic valueOf : (J)Ljava/lang/Long;
    //   258: iconst_0
    //   259: swap
    //   260: aastore
    //   261: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   264: invokevirtual doubleValue : ()D
    //   267: dmul
    //   268: dadd
    //   269: invokevirtual addAndGet : (D)D
    //   272: pop2
    //   273: return
  }
  
  public static void M(d[] paramArrayOfd) {
    K = paramArrayOfd;
  }
  
  public static d[] g() {
    return K;
  }
  
  static {
    M(null);
    long l = b ^ 0x9E96A59192BL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "+\000'£+ù\005ÊCKÁÐ\013äÎ8q~4\032\0079ü\0245\032s;É\027ÿªÓ6\027\020?Ê6zÌ\021'÷1ðåv\026Ý|\025(\006Ý Á0 WíûóÃ=.^ò°dÐ( ¨¼Ä..î\006kÈiÜÝ ¤´>").length();
    byte b2 = 40;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1CA;
    if (e[i] == null) {
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
        throw new RuntimeException("wtf/opal/ld", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = d[i].getBytes("ISO-8859-1");
      e[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return e[i];
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
    //   65: ldc_w 'wtf/opal/ld'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x26C1;
    if (k[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = j[i];
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
        throw new RuntimeException("wtf/opal/ld", exception);
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
    //   65: ldc_w 'wtf/opal/ld'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ld.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */