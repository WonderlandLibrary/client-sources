package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;

public final class pc extends u_<j4> {
  private final List<class_2596<?>> h = new ArrayList<>();
  
  private int T;
  
  private boolean N;
  
  private final gm<lb> W = this::lambda$new$0;
  
  private final gm<b6> M = this::lambda$new$1;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  public pc(long paramLong, j4 paramj4) {
    super(paramj4);
    boolean bool = pw.y();
    try {
      if (d.D() != null) {
        try {
        
        } catch (x5 x5) {
          throw b(null);
        } 
        pw.W(!bool);
      } 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.h.clear();
    this.N = false;
    new Object[1];
    super.s(new Object[] { Long.valueOf(l2) });
  }
  
  public void X() {
    long l = a ^ 0x197C5A4E50F1L;
    Objects.requireNonNull(b9.c.method_1562().method_48296());
    this.h.forEach(b9.c.method_1562().method_48296()::method_10743);
    this.h.clear();
    this.N = false;
    boolean bool = pw.F();
    try {
      super.X();
      if (!bool)
        d.p(new d[4]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return km.BLINK;
  }
  
  private void lambda$new$1(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/pc.a : J
    //   3: ldc2_w 83039727246410
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 138512231916760
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic F : ()Z
    //   20: istore #6
    //   22: aload_1
    //   23: iconst_0
    //   24: anewarray java/lang/Object
    //   27: invokevirtual W : ([Ljava/lang/Object;)Z
    //   30: iload #6
    //   32: ifeq -> 55
    //   35: ifne -> 46
    //   38: goto -> 45
    //   41: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: return
    //   46: aload_0
    //   47: getfield h : Ljava/util/List;
    //   50: invokeinterface isEmpty : ()Z
    //   55: iload #6
    //   57: ifeq -> 173
    //   60: ifne -> 127
    //   63: goto -> 70
    //   66: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   69: athrow
    //   70: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   73: getfield field_1724 : Lnet/minecraft/class_746;
    //   76: invokevirtual method_24828 : ()Z
    //   79: iload #6
    //   81: ifeq -> 173
    //   84: goto -> 91
    //   87: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   90: athrow
    //   91: ifeq -> 127
    //   94: goto -> 101
    //   97: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: aload_0
    //   102: iload #6
    //   104: ifeq -> 324
    //   107: goto -> 114
    //   110: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: getfield N : Z
    //   117: ifne -> 279
    //   120: goto -> 127
    //   123: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   126: athrow
    //   127: sipush #29720
    //   130: ldc2_w 6175349826479307057
    //   133: lload_2
    //   134: lxor
    //   135: <illegal opcode> u : (IJ)I
    //   140: lload #4
    //   142: iconst_2
    //   143: anewarray java/lang/Object
    //   146: dup_x2
    //   147: dup_x2
    //   148: pop
    //   149: invokestatic valueOf : (J)Ljava/lang/Long;
    //   152: iconst_1
    //   153: swap
    //   154: aastore
    //   155: dup_x1
    //   156: swap
    //   157: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   160: iconst_0
    //   161: swap
    //   162: aastore
    //   163: invokestatic b : ([Ljava/lang/Object;)Z
    //   166: goto -> 173
    //   169: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   172: athrow
    //   173: iload #6
    //   175: ifeq -> 264
    //   178: ifne -> 219
    //   181: goto -> 188
    //   184: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   187: athrow
    //   188: aload_0
    //   189: getfield h : Ljava/util/List;
    //   192: iload #6
    //   194: ifeq -> 318
    //   197: goto -> 204
    //   200: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   203: athrow
    //   204: invokeinterface isEmpty : ()Z
    //   209: ifeq -> 279
    //   212: goto -> 219
    //   215: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   218: athrow
    //   219: iconst_0
    //   220: anewarray java/lang/Object
    //   223: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   226: iconst_0
    //   227: anewarray java/lang/Object
    //   230: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   233: ldc wtf/opal/xw
    //   235: iconst_1
    //   236: anewarray java/lang/Object
    //   239: dup_x1
    //   240: swap
    //   241: iconst_0
    //   242: swap
    //   243: aastore
    //   244: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   247: checkcast wtf/opal/xw
    //   250: iconst_0
    //   251: anewarray java/lang/Object
    //   254: invokevirtual D : ([Ljava/lang/Object;)Z
    //   257: goto -> 264
    //   260: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   263: athrow
    //   264: iload #6
    //   266: ifeq -> 337
    //   269: ifeq -> 328
    //   272: goto -> 279
    //   275: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   278: athrow
    //   279: aload_0
    //   280: getfield h : Ljava/util/List;
    //   283: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   286: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   289: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   292: dup
    //   293: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   296: pop
    //   297: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   302: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   307: aload_0
    //   308: getfield h : Ljava/util/List;
    //   311: goto -> 318
    //   314: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   317: athrow
    //   318: invokeinterface clear : ()V
    //   323: aload_0
    //   324: iconst_0
    //   325: putfield N : Z
    //   328: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   331: getfield field_1724 : Lnet/minecraft/class_746;
    //   334: invokevirtual method_24828 : ()Z
    //   337: ifeq -> 357
    //   340: aload_0
    //   341: iconst_0
    //   342: putfield T : I
    //   345: iload #6
    //   347: ifne -> 374
    //   350: goto -> 357
    //   353: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   356: athrow
    //   357: aload_0
    //   358: dup
    //   359: getfield T : I
    //   362: iconst_1
    //   363: iadd
    //   364: putfield T : I
    //   367: goto -> 374
    //   370: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   373: athrow
    //   374: return
    // Exception table:
    //   from	to	target	type
    //   22	38	41	wtf/opal/x5
    //   55	63	66	wtf/opal/x5
    //   60	84	87	wtf/opal/x5
    //   70	94	97	wtf/opal/x5
    //   91	107	110	wtf/opal/x5
    //   101	120	123	wtf/opal/x5
    //   114	166	169	wtf/opal/x5
    //   173	181	184	wtf/opal/x5
    //   178	197	200	wtf/opal/x5
    //   188	212	215	wtf/opal/x5
    //   204	257	260	wtf/opal/x5
    //   264	272	275	wtf/opal/x5
    //   269	311	314	wtf/opal/x5
    //   337	350	353	wtf/opal/x5
    //   340	367	370	wtf/opal/x5
  }
  
  private void lambda$new$0(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/pc.a : J
    //   3: ldc2_w 101214910227120
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 117038862670370
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic y : ()Z
    //   20: aload_1
    //   21: iconst_0
    //   22: anewarray java/lang/Object
    //   25: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   28: astore #8
    //   30: istore #6
    //   32: aload #8
    //   34: instanceof net/minecraft/class_2828
    //   37: iload #6
    //   39: ifne -> 184
    //   42: ifeq -> 145
    //   45: goto -> 52
    //   48: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: aload #8
    //   54: checkcast net/minecraft/class_2828
    //   57: astore #7
    //   59: aload #7
    //   61: checkcast wtf/opal/mixin/PlayerMoveC2SPacketAccessor
    //   64: astore #8
    //   66: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   69: getfield field_1724 : Lnet/minecraft/class_746;
    //   72: getfield field_6017 : F
    //   75: ldc_w 3.0
    //   78: fcmpl
    //   79: iload #6
    //   81: ifne -> 184
    //   84: iflt -> 145
    //   87: goto -> 94
    //   90: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: aload_0
    //   95: getfield N : Z
    //   98: iload #6
    //   100: ifne -> 184
    //   103: goto -> 110
    //   106: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: ifeq -> 145
    //   113: goto -> 120
    //   116: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   119: athrow
    //   120: aload #8
    //   122: iconst_1
    //   123: invokeinterface setOnGround : (Z)V
    //   128: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   131: getfield field_1724 : Lnet/minecraft/class_746;
    //   134: fconst_0
    //   135: putfield field_6017 : F
    //   138: goto -> 145
    //   141: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   144: athrow
    //   145: sipush #2730
    //   148: ldc2_w 1533337539716430200
    //   151: lload_2
    //   152: lxor
    //   153: <illegal opcode> u : (IJ)I
    //   158: lload #4
    //   160: iconst_2
    //   161: anewarray java/lang/Object
    //   164: dup_x2
    //   165: dup_x2
    //   166: pop
    //   167: invokestatic valueOf : (J)Ljava/lang/Long;
    //   170: iconst_1
    //   171: swap
    //   172: aastore
    //   173: dup_x1
    //   174: swap
    //   175: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   178: iconst_0
    //   179: swap
    //   180: aastore
    //   181: invokestatic b : ([Ljava/lang/Object;)Z
    //   184: iload #6
    //   186: ifne -> 327
    //   189: ifeq -> 323
    //   192: goto -> 199
    //   195: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   198: athrow
    //   199: iconst_3
    //   200: lload #4
    //   202: iconst_2
    //   203: anewarray java/lang/Object
    //   206: dup_x2
    //   207: dup_x2
    //   208: pop
    //   209: invokestatic valueOf : (J)Ljava/lang/Long;
    //   212: iconst_1
    //   213: swap
    //   214: aastore
    //   215: dup_x1
    //   216: swap
    //   217: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   220: iconst_0
    //   221: swap
    //   222: aastore
    //   223: invokestatic b : ([Ljava/lang/Object;)Z
    //   226: iload #6
    //   228: ifne -> 327
    //   231: goto -> 238
    //   234: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   237: athrow
    //   238: ifne -> 323
    //   241: goto -> 248
    //   244: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: aload_0
    //   249: getfield T : I
    //   252: iload #6
    //   254: ifne -> 327
    //   257: goto -> 264
    //   260: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   263: athrow
    //   264: iconst_1
    //   265: if_icmpne -> 323
    //   268: goto -> 275
    //   271: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   274: athrow
    //   275: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   278: getfield field_1724 : Lnet/minecraft/class_746;
    //   281: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   284: invokevirtual method_10214 : ()D
    //   287: dconst_0
    //   288: dcmpg
    //   289: iload #6
    //   291: ifne -> 327
    //   294: goto -> 301
    //   297: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   300: athrow
    //   301: ifge -> 323
    //   304: goto -> 311
    //   307: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   310: athrow
    //   311: aload_0
    //   312: iconst_1
    //   313: putfield N : Z
    //   316: goto -> 323
    //   319: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   322: athrow
    //   323: aload_0
    //   324: getfield N : Z
    //   327: iload #6
    //   329: ifne -> 374
    //   332: ifeq -> 375
    //   335: goto -> 342
    //   338: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   341: athrow
    //   342: aload_1
    //   343: iconst_0
    //   344: anewarray java/lang/Object
    //   347: invokevirtual Z : ([Ljava/lang/Object;)V
    //   350: aload_0
    //   351: getfield h : Ljava/util/List;
    //   354: aload_1
    //   355: iconst_0
    //   356: anewarray java/lang/Object
    //   359: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   362: invokeinterface add : (Ljava/lang/Object;)Z
    //   367: goto -> 374
    //   370: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   373: athrow
    //   374: pop
    //   375: return
    // Exception table:
    //   from	to	target	type
    //   32	45	48	wtf/opal/x5
    //   66	87	90	wtf/opal/x5
    //   84	103	106	wtf/opal/x5
    //   94	113	116	wtf/opal/x5
    //   110	138	141	wtf/opal/x5
    //   184	192	195	wtf/opal/x5
    //   189	231	234	wtf/opal/x5
    //   199	241	244	wtf/opal/x5
    //   238	257	260	wtf/opal/x5
    //   248	268	271	wtf/opal/x5
    //   264	294	297	wtf/opal/x5
    //   275	304	307	wtf/opal/x5
    //   301	316	319	wtf/opal/x5
    //   327	335	338	wtf/opal/x5
    //   332	367	370	wtf/opal/x5
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 5081762936568236215
    //   3: ldc2_w -6725926602823966352
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 268474146144992
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/pc.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/pc.d : Ljava/util/Map;
    //   38: getstatic wtf/opal/pc.a : J
    //   41: ldc2_w 122899667093234
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
    //   135: ldc_w '-¦Wú\\t^ æìÚì'
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
    //   292: putstatic wtf/opal/pc.b : [J
    //   295: iconst_2
    //   296: anewarray java/lang/Integer
    //   299: putstatic wtf/opal/pc.c : [Ljava/lang/Integer;
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
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4CDA;
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
        throw new RuntimeException("wtf/opal/pc", exception);
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
    //   66: ldc_w 'wtf/opal/pc'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */