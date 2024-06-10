package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;

public final class x9 extends class_437 {
  private final pa j = d1.q(new Object[0]).z(new Object[0]);
  
  private final bu g = d1.q(new Object[0]).h(new Object[0]);
  
  private final List<ld> S = new ArrayList<>();
  
  public static boolean Q;
  
  private final da r;
  
  private static d[] V;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  public x9(long paramLong) {
    super((class_2561)class_2561.method_43470(""));
    d[] arrayOfD = V();
    try {
      this.r = new da(l);
      if (d.D() != null)
        P(new d[1]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void method_25394(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: getstatic wtf/opal/x9.a : J
    //   3: ldc2_w 6550114152168
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 135280844374132
    //   15: lxor
    //   16: lstore #7
    //   18: dup2
    //   19: ldc2_w 57094866071320
    //   22: lxor
    //   23: lstore #9
    //   25: dup2
    //   26: ldc2_w 26145685424408
    //   29: lxor
    //   30: lstore #11
    //   32: pop2
    //   33: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   36: invokevirtual z : ()Ljava/lang/Object;
    //   39: checkcast java/lang/Double
    //   42: invokevirtual floatValue : ()F
    //   45: fstore #14
    //   47: invokestatic V : ()[Lwtf/opal/d;
    //   50: aload_0
    //   51: getfield r : Lwtf/opal/da;
    //   54: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   57: invokevirtual z : ()Ljava/lang/Object;
    //   60: checkcast java/lang/Double
    //   63: invokevirtual floatValue : ()F
    //   66: lload #7
    //   68: sipush #22010
    //   71: ldc2_w 7330706058479706531
    //   74: lload #5
    //   76: lxor
    //   77: <illegal opcode> r : (IJ)I
    //   82: iconst_3
    //   83: anewarray java/lang/Object
    //   86: dup_x1
    //   87: swap
    //   88: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   91: iconst_2
    //   92: swap
    //   93: aastore
    //   94: dup_x2
    //   95: dup_x2
    //   96: pop
    //   97: invokestatic valueOf : (J)Ljava/lang/Long;
    //   100: iconst_1
    //   101: swap
    //   102: aastore
    //   103: dup_x1
    //   104: swap
    //   105: invokestatic valueOf : (F)Ljava/lang/Float;
    //   108: iconst_0
    //   109: swap
    //   110: aastore
    //   111: invokevirtual e : ([Ljava/lang/Object;)V
    //   114: astore #13
    //   116: aload_0
    //   117: getfield j : Lwtf/opal/pa;
    //   120: aload_0
    //   121: aload_1
    //   122: iload_2
    //   123: fload #14
    //   125: iload_3
    //   126: fload #4
    //   128: <illegal opcode> run : (Lwtf/opal/x9;Lnet/minecraft/class_332;IFIF)Ljava/lang/Runnable;
    //   133: lload #11
    //   135: iconst_2
    //   136: anewarray java/lang/Object
    //   139: dup_x2
    //   140: dup_x2
    //   141: pop
    //   142: invokestatic valueOf : (J)Ljava/lang/Long;
    //   145: iconst_1
    //   146: swap
    //   147: aastore
    //   148: dup_x1
    //   149: swap
    //   150: iconst_0
    //   151: swap
    //   152: aastore
    //   153: invokevirtual F : ([Ljava/lang/Object;)V
    //   156: getstatic wtf/opal/j8.X : Lwtf/opal/ke;
    //   159: invokevirtual z : ()Ljava/lang/Object;
    //   162: checkcast java/lang/Boolean
    //   165: invokevirtual booleanValue : ()Z
    //   168: aload #13
    //   170: ifnull -> 186
    //   173: ifeq -> 214
    //   176: goto -> 183
    //   179: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   182: athrow
    //   183: getstatic wtf/opal/x9.Q : Z
    //   186: ifne -> 214
    //   189: lload #9
    //   191: iconst_1
    //   192: anewarray java/lang/Object
    //   195: dup_x2
    //   196: dup_x2
    //   197: pop
    //   198: invokestatic valueOf : (J)Ljava/lang/Long;
    //   201: iconst_0
    //   202: swap
    //   203: aastore
    //   204: invokestatic m : ([Ljava/lang/Object;)V
    //   207: goto -> 214
    //   210: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   213: athrow
    //   214: aload #13
    //   216: ifnonnull -> 233
    //   219: iconst_1
    //   220: anewarray wtf/opal/d
    //   223: invokestatic p : ([Lwtf/opal/d;)V
    //   226: goto -> 233
    //   229: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   232: athrow
    //   233: return
    // Exception table:
    //   from	to	target	type
    //   116	176	179	wtf/opal/x5
    //   186	207	210	wtf/opal/x5
    //   214	226	229	wtf/opal/x5
  }
  
  public boolean method_25402(double paramDouble1, double paramDouble2, int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   3: invokevirtual z : ()Ljava/lang/Object;
    //   6: checkcast java/lang/Double
    //   9: invokevirtual floatValue : ()F
    //   12: fstore #6
    //   14: aload_0
    //   15: getfield S : Ljava/util/List;
    //   18: dload_1
    //   19: fload #6
    //   21: dload_3
    //   22: iload #5
    //   24: <illegal opcode> accept : (DFDI)Ljava/util/function/Consumer;
    //   29: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   34: iconst_1
    //   35: ireturn
  }
  
  public boolean method_25406(double paramDouble1, double paramDouble2, int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   3: invokevirtual z : ()Ljava/lang/Object;
    //   6: checkcast java/lang/Double
    //   9: invokevirtual floatValue : ()F
    //   12: fstore #6
    //   14: aload_0
    //   15: getfield S : Ljava/util/List;
    //   18: dload_1
    //   19: fload #6
    //   21: dload_3
    //   22: iload #5
    //   24: <illegal opcode> accept : (DFDI)Ljava/util/function/Consumer;
    //   29: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   34: iconst_1
    //   35: ireturn
  }
  
  public boolean method_25401(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    // Byte code:
    //   0: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   3: invokevirtual z : ()Ljava/lang/Object;
    //   6: checkcast java/lang/Double
    //   9: invokevirtual floatValue : ()F
    //   12: fstore #9
    //   14: aload_0
    //   15: getfield S : Ljava/util/List;
    //   18: dload_1
    //   19: fload #9
    //   21: dload_3
    //   22: dload #5
    //   24: dload #7
    //   26: <illegal opcode> accept : (DFDDD)Ljava/util/function/Consumer;
    //   31: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   36: iconst_1
    //   37: ireturn
  }
  
  public boolean method_25404(int paramInt1, int paramInt2, int paramInt3) {
    // Byte code:
    //   0: getstatic wtf/opal/x9.a : J
    //   3: ldc2_w 80589067132940
    //   6: lxor
    //   7: lstore #4
    //   9: lload #4
    //   11: dup2
    //   12: ldc2_w 119183726475887
    //   15: lxor
    //   16: lstore #6
    //   18: pop2
    //   19: invokestatic V : ()[Lwtf/opal/d;
    //   22: aload_0
    //   23: getfield S : Ljava/util/List;
    //   26: iload_1
    //   27: iload_2
    //   28: iload_3
    //   29: <illegal opcode> accept : (III)Ljava/util/function/Consumer;
    //   34: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   39: astore #8
    //   41: invokestatic method_25441 : ()Z
    //   44: aload #8
    //   46: ifnull -> 255
    //   49: ifeq -> 248
    //   52: goto -> 59
    //   55: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: iload_1
    //   60: sipush #21810
    //   63: ldc2_w 4339533312817508236
    //   66: lload #4
    //   68: lxor
    //   69: <illegal opcode> r : (IJ)I
    //   74: aload #8
    //   76: ifnull -> 190
    //   79: goto -> 86
    //   82: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   85: athrow
    //   86: if_icmpne -> 156
    //   89: goto -> 96
    //   92: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   95: athrow
    //   96: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   99: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   102: invokevirtual z : ()Ljava/lang/Object;
    //   105: checkcast java/lang/Double
    //   108: invokevirtual doubleValue : ()D
    //   111: ldc2_w 0.1
    //   114: dadd
    //   115: lload #6
    //   117: dup2_x2
    //   118: pop2
    //   119: iconst_2
    //   120: anewarray java/lang/Object
    //   123: dup_x2
    //   124: dup_x2
    //   125: pop
    //   126: invokestatic valueOf : (D)Ljava/lang/Double;
    //   129: iconst_1
    //   130: swap
    //   131: aastore
    //   132: dup_x2
    //   133: dup_x2
    //   134: pop
    //   135: invokestatic valueOf : (J)Ljava/lang/Long;
    //   138: iconst_0
    //   139: swap
    //   140: aastore
    //   141: invokevirtual r : ([Ljava/lang/Object;)V
    //   144: aload #8
    //   146: ifnonnull -> 248
    //   149: goto -> 156
    //   152: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   155: athrow
    //   156: iload_1
    //   157: aload #8
    //   159: ifnull -> 255
    //   162: goto -> 169
    //   165: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   168: athrow
    //   169: sipush #21530
    //   172: ldc2_w 6200255306577784485
    //   175: lload #4
    //   177: lxor
    //   178: <illegal opcode> r : (IJ)I
    //   183: goto -> 190
    //   186: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   189: athrow
    //   190: if_icmpne -> 248
    //   193: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   196: getstatic wtf/opal/j8.T : Lwtf/opal/kt;
    //   199: invokevirtual z : ()Ljava/lang/Object;
    //   202: checkcast java/lang/Double
    //   205: invokevirtual doubleValue : ()D
    //   208: ldc2_w 0.1
    //   211: dsub
    //   212: lload #6
    //   214: dup2_x2
    //   215: pop2
    //   216: iconst_2
    //   217: anewarray java/lang/Object
    //   220: dup_x2
    //   221: dup_x2
    //   222: pop
    //   223: invokestatic valueOf : (D)Ljava/lang/Double;
    //   226: iconst_1
    //   227: swap
    //   228: aastore
    //   229: dup_x2
    //   230: dup_x2
    //   231: pop
    //   232: invokestatic valueOf : (J)Ljava/lang/Long;
    //   235: iconst_0
    //   236: swap
    //   237: aastore
    //   238: invokevirtual r : ([Ljava/lang/Object;)V
    //   241: goto -> 248
    //   244: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: aload_0
    //   249: iload_1
    //   250: iload_2
    //   251: iload_3
    //   252: invokespecial method_25404 : (III)Z
    //   255: ireturn
    // Exception table:
    //   from	to	target	type
    //   41	52	55	wtf/opal/x5
    //   49	79	82	wtf/opal/x5
    //   59	89	92	wtf/opal/x5
    //   86	149	152	wtf/opal/x5
    //   96	162	165	wtf/opal/x5
    //   156	183	186	wtf/opal/x5
    //   190	241	244	wtf/opal/x5
  }
  
  public boolean method_25400(char paramChar, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield S : Ljava/util/List;
    //   4: iload_1
    //   5: iload_2
    //   6: <illegal opcode> accept : (CI)Ljava/util/function/Consumer;
    //   11: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   16: iconst_1
    //   17: ireturn
  }
  
  public void method_25419() {
    this.S.forEach(ld::s);
  }
  
  protected void method_25426() {
    long l1 = a ^ 0x3F9113AF1E05L;
    long l2 = l1 ^ 0x1BFE528AA949L;
    d[] arrayOfD = V();
    try {
      if (arrayOfD != null)
        if (this.S.isEmpty()) {
          byte b = 0;
          while (true) {
            if (b < (kn.values()).length) {
              try {
                this.S.add(new ld(l2, kn.values()[b], b));
                b++;
                if (arrayOfD != null) {
                  if (arrayOfD == null)
                    break; 
                  continue;
                } 
              } catch (x5 x5) {
                throw a(null);
              } 
            } else {
              break;
            } 
            try {
              if (d1.q(new Object[0]).C(new Object[0]).c(new Object[0]) == null)
                System.exit(0); 
            } catch (x5 x5) {
              throw a(null);
            } 
            return;
          } 
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    this.S.forEach(ld::u);
    try {
      if (d1.q(new Object[0]).C(new Object[0]).c(new Object[0]) == null)
        System.exit(0); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public boolean method_25421() {
    return false;
  }
  
  private static void lambda$charTyped$7(char paramChar, int paramInt, ld paramld) {
    long l1 = a ^ 0x4BB35316BB5DL;
    long l2 = l1 ^ 0x462677E47016L;
    new Object[3];
    paramld.d(new Object[] { null, null, Long.valueOf(l2), Integer.valueOf(paramInt), Integer.valueOf(paramChar) });
  }
  
  private static void lambda$keyPressed$6(int paramInt1, int paramInt2, int paramInt3, ld paramld) {
    long l1 = a ^ 0x5D4E4D47C97AL;
    long l2 = l1 ^ 0x4D94D17185EL;
    (new Object[4])[3] = Integer.valueOf(paramInt3);
    (new Object[4])[2] = Integer.valueOf(paramInt2);
    (new Object[4])[1] = Integer.valueOf(paramInt1);
    new Object[4];
    paramld.o(new Object[] { Long.valueOf(l2) });
  }
  
  private static void lambda$mouseScrolled$5(double paramDouble1, float paramFloat, double paramDouble2, double paramDouble3, double paramDouble4, ld paramld) {
    long l1 = a ^ 0x113DBD64264AL;
    long l2 = l1 ^ 0x7ACC40215F62L;
    new Object[5];
    (new Object[5])[4] = Double.valueOf(paramDouble4);
    new Object[5];
    (new Object[5])[3] = Long.valueOf(l2);
    new Object[5];
    (new Object[5])[2] = Double.valueOf(paramDouble3);
    new Object[5];
    (new Object[5])[1] = Double.valueOf(paramDouble2 / paramFloat);
    new Object[5];
    paramld.w(new Object[] { Double.valueOf(paramDouble1 / paramFloat) });
  }
  
  private static void lambda$mouseReleased$4(double paramDouble1, float paramFloat, double paramDouble2, int paramInt, ld paramld) {
    long l1 = a ^ 0x18832DBA6B0CL;
    long l2 = l1 ^ 0x238E257531E8L;
    (new Object[4])[3] = Integer.valueOf(paramInt);
    new Object[4];
    (new Object[4])[2] = Double.valueOf(paramDouble2 / paramFloat);
    new Object[4];
    (new Object[4])[1] = Double.valueOf(paramDouble1 / paramFloat);
    new Object[4];
    paramld.c(new Object[] { Long.valueOf(l2) });
  }
  
  private static void lambda$mouseClicked$3(double paramDouble1, float paramFloat, double paramDouble2, int paramInt, ld paramld) {
    long l1 = a ^ 0x2B65BBE5E242L;
    long l2 = l1 ^ 0x69070E3E059AL;
    (new Object[4])[3] = Integer.valueOf(paramInt);
    new Object[4];
    (new Object[4])[2] = Long.valueOf(l2);
    new Object[4];
    (new Object[4])[1] = Double.valueOf(paramDouble2 / paramFloat);
    new Object[4];
    paramld.e(new Object[] { Double.valueOf(paramDouble1 / paramFloat) });
  }
  
  private void lambda$render$2(class_332 paramclass_332, int paramInt1, float paramFloat1, int paramInt2, float paramFloat2) {
    // Byte code:
    //   0: getstatic wtf/opal/x9.a : J
    //   3: ldc2_w 14773162825586
    //   6: lxor
    //   7: lstore #6
    //   9: lload #6
    //   11: dup2
    //   12: ldc2_w 72019000562325
    //   15: lxor
    //   16: lstore #8
    //   18: pop2
    //   19: aload_0
    //   20: getfield j : Lwtf/opal/pa;
    //   23: aload_0
    //   24: getfield r : Lwtf/opal/da;
    //   27: lload #8
    //   29: iconst_1
    //   30: anewarray java/lang/Object
    //   33: dup_x2
    //   34: dup_x2
    //   35: pop
    //   36: invokestatic valueOf : (J)Ljava/lang/Long;
    //   39: iconst_0
    //   40: swap
    //   41: aastore
    //   42: invokevirtual s : ([Ljava/lang/Object;)F
    //   45: fconst_0
    //   46: fconst_0
    //   47: fconst_0
    //   48: fconst_0
    //   49: aload_0
    //   50: aload_1
    //   51: iload_2
    //   52: fload_3
    //   53: iload #4
    //   55: fload #5
    //   57: <illegal opcode> run : (Lwtf/opal/x9;Lnet/minecraft/class_332;IFIF)Ljava/lang/Runnable;
    //   62: bipush #6
    //   64: anewarray java/lang/Object
    //   67: dup_x1
    //   68: swap
    //   69: iconst_5
    //   70: swap
    //   71: aastore
    //   72: dup_x1
    //   73: swap
    //   74: invokestatic valueOf : (F)Ljava/lang/Float;
    //   77: iconst_4
    //   78: swap
    //   79: aastore
    //   80: dup_x1
    //   81: swap
    //   82: invokestatic valueOf : (F)Ljava/lang/Float;
    //   85: iconst_3
    //   86: swap
    //   87: aastore
    //   88: dup_x1
    //   89: swap
    //   90: invokestatic valueOf : (F)Ljava/lang/Float;
    //   93: iconst_2
    //   94: swap
    //   95: aastore
    //   96: dup_x1
    //   97: swap
    //   98: invokestatic valueOf : (F)Ljava/lang/Float;
    //   101: iconst_1
    //   102: swap
    //   103: aastore
    //   104: dup_x1
    //   105: swap
    //   106: invokestatic valueOf : (F)Ljava/lang/Float;
    //   109: iconst_0
    //   110: swap
    //   111: aastore
    //   112: invokevirtual r : ([Ljava/lang/Object;)V
    //   115: return
  }
  
  private void lambda$render$1(class_332 paramclass_332, int paramInt1, float paramFloat1, int paramInt2, float paramFloat2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield g : Lwtf/opal/bu;
    //   4: iconst_0
    //   5: putfield t : Z
    //   8: aload_0
    //   9: getfield S : Ljava/util/List;
    //   12: aload_1
    //   13: iload_2
    //   14: fload_3
    //   15: iload #4
    //   17: fload #5
    //   19: <illegal opcode> accept : (Lnet/minecraft/class_332;IFIF)Ljava/util/function/Consumer;
    //   24: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   29: aload_0
    //   30: getfield g : Lwtf/opal/bu;
    //   33: iconst_1
    //   34: putfield t : Z
    //   37: return
  }
  
  private static void lambda$render$0(class_332 paramclass_332, int paramInt1, float paramFloat1, int paramInt2, float paramFloat2, ld paramld) {
    long l1 = a ^ 0x5692F86EEBD7L;
    long l2 = l1 ^ 0x2860773C1C87L;
    (new Object[5])[4] = Float.valueOf(paramFloat2);
    new Object[5];
    paramld.g(new Object[] { null, null, null, Long.valueOf(l2), Integer.valueOf((int)(paramInt2 / paramFloat1)), Integer.valueOf((int)(paramInt1 / paramFloat1)), paramclass_332 });
  }
  
  public static void P(d[] paramArrayOfd) {
    V = paramArrayOfd;
  }
  
  public static d[] V() {
    return V;
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 21960208894510339
    //   3: ldc2_w 4783231443178349204
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 259807336913107
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/x9.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/x9.d : Ljava/util/Map;
    //   38: iconst_3
    //   39: anewarray wtf/opal/d
    //   42: getstatic wtf/opal/x9.a : J
    //   45: ldc2_w 31361943133597
    //   48: lxor
    //   49: lstore_0
    //   50: invokestatic P : ([Lwtf/opal/d;)V
    //   53: ldc_w 'DES/CBC/NoPadding'
    //   56: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   59: dup
    //   60: astore_2
    //   61: iconst_2
    //   62: ldc_w 'DES'
    //   65: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   68: bipush #8
    //   70: newarray byte
    //   72: dup
    //   73: iconst_0
    //   74: lload_0
    //   75: bipush #56
    //   77: lushr
    //   78: l2i
    //   79: i2b
    //   80: bastore
    //   81: iconst_1
    //   82: istore_3
    //   83: iload_3
    //   84: bipush #8
    //   86: if_icmpge -> 109
    //   89: dup
    //   90: iload_3
    //   91: lload_0
    //   92: iload_3
    //   93: bipush #8
    //   95: imul
    //   96: lshl
    //   97: bipush #56
    //   99: lushr
    //   100: l2i
    //   101: i2b
    //   102: bastore
    //   103: iinc #3, 1
    //   106: goto -> 83
    //   109: new javax/crypto/spec/DESKeySpec
    //   112: dup_x1
    //   113: swap
    //   114: invokespecial <init> : ([B)V
    //   117: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   120: new javax/crypto/spec/IvParameterSpec
    //   123: dup
    //   124: bipush #8
    //   126: newarray byte
    //   128: invokespecial <init> : ([B)V
    //   131: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   134: iconst_3
    //   135: newarray long
    //   137: astore #8
    //   139: iconst_0
    //   140: istore #5
    //   142: ldc_w '&"\\r¤XëÄ¶B«Ó Ãø=o\'
    //   145: dup
    //   146: astore #6
    //   148: invokevirtual length : ()I
    //   151: istore #7
    //   153: iconst_0
    //   154: istore #4
    //   156: aload #6
    //   158: iload #4
    //   160: iinc #4, 8
    //   163: iload #4
    //   165: invokevirtual substring : (II)Ljava/lang/String;
    //   168: ldc_w 'ISO-8859-1'
    //   171: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   174: astore #9
    //   176: aload #8
    //   178: iload #5
    //   180: iinc #5, 1
    //   183: aload #9
    //   185: iconst_0
    //   186: baload
    //   187: i2l
    //   188: ldc2_w 255
    //   191: land
    //   192: bipush #56
    //   194: lshl
    //   195: aload #9
    //   197: iconst_1
    //   198: baload
    //   199: i2l
    //   200: ldc2_w 255
    //   203: land
    //   204: bipush #48
    //   206: lshl
    //   207: lor
    //   208: aload #9
    //   210: iconst_2
    //   211: baload
    //   212: i2l
    //   213: ldc2_w 255
    //   216: land
    //   217: bipush #40
    //   219: lshl
    //   220: lor
    //   221: aload #9
    //   223: iconst_3
    //   224: baload
    //   225: i2l
    //   226: ldc2_w 255
    //   229: land
    //   230: bipush #32
    //   232: lshl
    //   233: lor
    //   234: aload #9
    //   236: iconst_4
    //   237: baload
    //   238: i2l
    //   239: ldc2_w 255
    //   242: land
    //   243: bipush #24
    //   245: lshl
    //   246: lor
    //   247: aload #9
    //   249: iconst_5
    //   250: baload
    //   251: i2l
    //   252: ldc2_w 255
    //   255: land
    //   256: bipush #16
    //   258: lshl
    //   259: lor
    //   260: aload #9
    //   262: bipush #6
    //   264: baload
    //   265: i2l
    //   266: ldc2_w 255
    //   269: land
    //   270: bipush #8
    //   272: lshl
    //   273: lor
    //   274: aload #9
    //   276: bipush #7
    //   278: baload
    //   279: i2l
    //   280: ldc2_w 255
    //   283: land
    //   284: lor
    //   285: iconst_m1
    //   286: goto -> 312
    //   289: lastore
    //   290: iload #4
    //   292: iload #7
    //   294: if_icmplt -> 156
    //   297: aload #8
    //   299: putstatic wtf/opal/x9.b : [J
    //   302: iconst_3
    //   303: anewarray java/lang/Integer
    //   306: putstatic wtf/opal/x9.c : [Ljava/lang/Integer;
    //   309: goto -> 514
    //   312: dup_x2
    //   313: pop
    //   314: lstore #10
    //   316: bipush #8
    //   318: newarray byte
    //   320: dup
    //   321: iconst_0
    //   322: lload #10
    //   324: bipush #56
    //   326: lushr
    //   327: l2i
    //   328: i2b
    //   329: bastore
    //   330: dup
    //   331: iconst_1
    //   332: lload #10
    //   334: bipush #48
    //   336: lushr
    //   337: l2i
    //   338: i2b
    //   339: bastore
    //   340: dup
    //   341: iconst_2
    //   342: lload #10
    //   344: bipush #40
    //   346: lushr
    //   347: l2i
    //   348: i2b
    //   349: bastore
    //   350: dup
    //   351: iconst_3
    //   352: lload #10
    //   354: bipush #32
    //   356: lushr
    //   357: l2i
    //   358: i2b
    //   359: bastore
    //   360: dup
    //   361: iconst_4
    //   362: lload #10
    //   364: bipush #24
    //   366: lushr
    //   367: l2i
    //   368: i2b
    //   369: bastore
    //   370: dup
    //   371: iconst_5
    //   372: lload #10
    //   374: bipush #16
    //   376: lushr
    //   377: l2i
    //   378: i2b
    //   379: bastore
    //   380: dup
    //   381: bipush #6
    //   383: lload #10
    //   385: bipush #8
    //   387: lushr
    //   388: l2i
    //   389: i2b
    //   390: bastore
    //   391: dup
    //   392: bipush #7
    //   394: lload #10
    //   396: l2i
    //   397: i2b
    //   398: bastore
    //   399: aload_2
    //   400: swap
    //   401: invokevirtual doFinal : ([B)[B
    //   404: astore #12
    //   406: aload #12
    //   408: iconst_0
    //   409: baload
    //   410: i2l
    //   411: ldc2_w 255
    //   414: land
    //   415: bipush #56
    //   417: lshl
    //   418: aload #12
    //   420: iconst_1
    //   421: baload
    //   422: i2l
    //   423: ldc2_w 255
    //   426: land
    //   427: bipush #48
    //   429: lshl
    //   430: lor
    //   431: aload #12
    //   433: iconst_2
    //   434: baload
    //   435: i2l
    //   436: ldc2_w 255
    //   439: land
    //   440: bipush #40
    //   442: lshl
    //   443: lor
    //   444: aload #12
    //   446: iconst_3
    //   447: baload
    //   448: i2l
    //   449: ldc2_w 255
    //   452: land
    //   453: bipush #32
    //   455: lshl
    //   456: lor
    //   457: aload #12
    //   459: iconst_4
    //   460: baload
    //   461: i2l
    //   462: ldc2_w 255
    //   465: land
    //   466: bipush #24
    //   468: lshl
    //   469: lor
    //   470: aload #12
    //   472: iconst_5
    //   473: baload
    //   474: i2l
    //   475: ldc2_w 255
    //   478: land
    //   479: bipush #16
    //   481: lshl
    //   482: lor
    //   483: aload #12
    //   485: bipush #6
    //   487: baload
    //   488: i2l
    //   489: ldc2_w 255
    //   492: land
    //   493: bipush #8
    //   495: lshl
    //   496: lor
    //   497: aload #12
    //   499: bipush #7
    //   501: baload
    //   502: i2l
    //   503: ldc2_w 255
    //   506: land
    //   507: lor
    //   508: dup2_x1
    //   509: pop2
    //   510: pop
    //   511: goto -> 289
    //   514: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1770;
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
        throw new RuntimeException("wtf/opal/x9", exception);
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
    //   66: ldc_w 'wtf/opal/x9'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\x9.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */