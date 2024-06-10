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

public final class i extends d {
  private final kt o;
  
  private final kt E;
  
  private final kt I;
  
  private final ke P;
  
  private final ke W;
  
  private final ke L;
  
  private static final long a = on.a(-1069705936410539732L, 224107467355319808L, MethodHandles.lookup().lookupClass()).a(33721719130515L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long g;
  
  public i(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/i.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 30931056669699
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 100307422971775
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 128935827136042
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #25849
    //   32: ldc2_w 6425940801314681416
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #20489
    //   47: ldc2_w 5461911313091217082
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.COMBAT : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/kt
    //   67: dup
    //   68: sipush #22930
    //   71: ldc2_w 99387801417928484
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   81: ldc2_w 3.1
    //   84: ldc2_w 3.0
    //   87: ldc2_w 6.0
    //   90: ldc2_w 0.05
    //   93: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   96: putfield o : Lwtf/opal/kt;
    //   99: aload_0
    //   100: new wtf/opal/kt
    //   103: dup
    //   104: sipush #2346
    //   107: ldc2_w 2525479948753248148
    //   110: lload_1
    //   111: lxor
    //   112: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   117: ldc2_w 3.2
    //   120: ldc2_w 3.0
    //   123: ldc2_w 6.0
    //   126: ldc2_w 0.05
    //   129: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   132: putfield E : Lwtf/opal/kt;
    //   135: aload_0
    //   136: new wtf/opal/kt
    //   139: dup
    //   140: sipush #11688
    //   143: ldc2_w 5099238949303532319
    //   146: lload_1
    //   147: lxor
    //   148: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   153: ldc2_w 3.0
    //   156: ldc2_w 3.0
    //   159: ldc2_w 6.0
    //   162: ldc2_w 0.1
    //   165: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   168: putfield I : Lwtf/opal/kt;
    //   171: aload_0
    //   172: new wtf/opal/ke
    //   175: dup
    //   176: sipush #20647
    //   179: ldc2_w 6517532674978825747
    //   182: lload_1
    //   183: lxor
    //   184: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   189: iconst_0
    //   190: invokespecial <init> : (Ljava/lang/String;Z)V
    //   193: putfield P : Lwtf/opal/ke;
    //   196: aload_0
    //   197: new wtf/opal/ke
    //   200: dup
    //   201: sipush #449
    //   204: ldc2_w 1483380860209764209
    //   207: lload_1
    //   208: lxor
    //   209: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   214: iconst_0
    //   215: invokespecial <init> : (Ljava/lang/String;Z)V
    //   218: putfield W : Lwtf/opal/ke;
    //   221: aload_0
    //   222: new wtf/opal/ke
    //   225: dup
    //   226: sipush #23118
    //   229: ldc2_w 3171169367459056891
    //   232: lload_1
    //   233: lxor
    //   234: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   239: iconst_0
    //   240: invokespecial <init> : (Ljava/lang/String;Z)V
    //   243: putfield L : Lwtf/opal/ke;
    //   246: aload_0
    //   247: getfield I : Lwtf/opal/kt;
    //   250: aload_0
    //   251: getfield L : Lwtf/opal/ke;
    //   254: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   259: lload_3
    //   260: dup2_x1
    //   261: pop2
    //   262: iconst_3
    //   263: anewarray java/lang/Object
    //   266: dup_x1
    //   267: swap
    //   268: iconst_2
    //   269: swap
    //   270: aastore
    //   271: dup_x2
    //   272: dup_x2
    //   273: pop
    //   274: invokestatic valueOf : (J)Ljava/lang/Long;
    //   277: iconst_1
    //   278: swap
    //   279: aastore
    //   280: dup_x1
    //   281: swap
    //   282: iconst_0
    //   283: swap
    //   284: aastore
    //   285: invokevirtual C : ([Ljava/lang/Object;)V
    //   288: aload_0
    //   289: getstatic wtf/opal/i.g : J
    //   292: l2i
    //   293: anewarray wtf/opal/k3
    //   296: dup
    //   297: iconst_0
    //   298: aload_0
    //   299: getfield o : Lwtf/opal/kt;
    //   302: aastore
    //   303: dup
    //   304: iconst_1
    //   305: aload_0
    //   306: getfield E : Lwtf/opal/kt;
    //   309: aastore
    //   310: dup
    //   311: iconst_2
    //   312: aload_0
    //   313: getfield I : Lwtf/opal/kt;
    //   316: aastore
    //   317: dup
    //   318: iconst_3
    //   319: aload_0
    //   320: getfield P : Lwtf/opal/ke;
    //   323: aastore
    //   324: dup
    //   325: iconst_4
    //   326: aload_0
    //   327: getfield W : Lwtf/opal/ke;
    //   330: aastore
    //   331: dup
    //   332: iconst_5
    //   333: aload_0
    //   334: getfield L : Lwtf/opal/ke;
    //   337: aastore
    //   338: lload #5
    //   340: dup2_x1
    //   341: pop2
    //   342: iconst_2
    //   343: anewarray java/lang/Object
    //   346: dup_x1
    //   347: swap
    //   348: iconst_1
    //   349: swap
    //   350: aastore
    //   351: dup_x2
    //   352: dup_x2
    //   353: pop
    //   354: invokestatic valueOf : (J)Ljava/lang/Long;
    //   357: iconst_0
    //   358: swap
    //   359: aastore
    //   360: invokevirtual o : ([Ljava/lang/Object;)V
    //   363: return
  }
  
  public String o(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: aload_0
    //   13: getfield o : Lwtf/opal/kt;
    //   16: invokevirtual z : ()Ljava/lang/Object;
    //   19: checkcast java/lang/Double
    //   22: invokevirtual doubleValue : ()D
    //   25: dstore #4
    //   27: aload_0
    //   28: getfield E : Lwtf/opal/kt;
    //   31: invokevirtual z : ()Ljava/lang/Object;
    //   34: checkcast java/lang/Double
    //   37: invokevirtual doubleValue : ()D
    //   40: dstore #6
    //   42: dload #4
    //   44: dload #6
    //   46: dcmpl
    //   47: ifne -> 65
    //   50: getstatic wtf/opal/b9.Z : Ljava/text/DecimalFormat;
    //   53: dload #4
    //   55: invokevirtual format : (D)Ljava/lang/String;
    //   58: goto -> 100
    //   61: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: getstatic wtf/opal/b9.Z : Ljava/text/DecimalFormat;
    //   68: dload #4
    //   70: invokevirtual format : (D)Ljava/lang/String;
    //   73: getstatic wtf/opal/b9.Z : Ljava/text/DecimalFormat;
    //   76: dload #6
    //   78: invokevirtual format : (D)Ljava/lang/String;
    //   81: sipush #21384
    //   84: ldc2_w 8729993799907206792
    //   87: lload_2
    //   88: lxor
    //   89: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   94: swap
    //   95: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   100: areturn
    // Exception table:
    //   from	to	target	type
    //   42	61	61	wtf/opal/x5
  }
  
  public double s(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/i.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 67016938305441
    //   23: lxor
    //   24: lstore #4
    //   26: dup2
    //   27: ldc2_w 108206687176387
    //   30: lxor
    //   31: lstore #6
    //   33: dup2
    //   34: ldc2_w 9580588910738
    //   37: lxor
    //   38: lstore #8
    //   40: pop2
    //   41: invokestatic y : ()I
    //   44: istore #10
    //   46: aload_0
    //   47: getfield P : Lwtf/opal/ke;
    //   50: invokevirtual z : ()Ljava/lang/Object;
    //   53: checkcast java/lang/Boolean
    //   56: invokevirtual booleanValue : ()Z
    //   59: iload #10
    //   61: ifeq -> 152
    //   64: ifeq -> 120
    //   67: goto -> 74
    //   70: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: lload #6
    //   76: iconst_1
    //   77: anewarray java/lang/Object
    //   80: dup_x2
    //   81: dup_x2
    //   82: pop
    //   83: invokestatic valueOf : (J)Ljava/lang/Long;
    //   86: iconst_0
    //   87: swap
    //   88: aastore
    //   89: invokestatic I : ([Ljava/lang/Object;)Z
    //   92: lload_2
    //   93: lconst_0
    //   94: lcmp
    //   95: iflt -> 152
    //   98: iload #10
    //   100: ifeq -> 152
    //   103: goto -> 110
    //   106: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: ifeq -> 189
    //   113: goto -> 120
    //   116: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   119: athrow
    //   120: aload_0
    //   121: getfield W : Lwtf/opal/ke;
    //   124: invokevirtual z : ()Ljava/lang/Object;
    //   127: checkcast java/lang/Boolean
    //   130: iload #10
    //   132: ifeq -> 204
    //   135: goto -> 142
    //   138: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   141: athrow
    //   142: invokevirtual booleanValue : ()Z
    //   145: goto -> 152
    //   148: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   151: athrow
    //   152: lload_2
    //   153: lconst_0
    //   154: lcmp
    //   155: iflt -> 179
    //   158: ifeq -> 197
    //   161: lload #4
    //   163: iconst_1
    //   164: anewarray java/lang/Object
    //   167: dup_x2
    //   168: dup_x2
    //   169: pop
    //   170: invokestatic valueOf : (J)Ljava/lang/Long;
    //   173: iconst_0
    //   174: swap
    //   175: aastore
    //   176: invokestatic B : ([Ljava/lang/Object;)Z
    //   179: ifeq -> 197
    //   182: goto -> 189
    //   185: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   188: athrow
    //   189: ldc2_w -1.0
    //   192: dreturn
    //   193: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   196: athrow
    //   197: aload_0
    //   198: getfield o : Lwtf/opal/kt;
    //   201: invokevirtual z : ()Ljava/lang/Object;
    //   204: checkcast java/lang/Double
    //   207: invokevirtual doubleValue : ()D
    //   210: dstore #11
    //   212: aload_0
    //   213: getfield E : Lwtf/opal/kt;
    //   216: invokevirtual z : ()Ljava/lang/Object;
    //   219: checkcast java/lang/Double
    //   222: invokevirtual doubleValue : ()D
    //   225: dstore #13
    //   227: dload #11
    //   229: dload #13
    //   231: dcmpl
    //   232: lload_2
    //   233: lconst_0
    //   234: lcmp
    //   235: ifle -> 277
    //   238: iload #10
    //   240: ifeq -> 277
    //   243: ifne -> 260
    //   246: goto -> 253
    //   249: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   252: athrow
    //   253: dload #11
    //   255: dreturn
    //   256: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   259: athrow
    //   260: dload #11
    //   262: iload #10
    //   264: ifeq -> 356
    //   267: dload #13
    //   269: dcmpl
    //   270: goto -> 277
    //   273: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   276: athrow
    //   277: ifle -> 346
    //   280: aload_0
    //   281: getfield E : Lwtf/opal/kt;
    //   284: dload #11
    //   286: invokestatic valueOf : (D)Ljava/lang/Double;
    //   289: iconst_1
    //   290: anewarray java/lang/Object
    //   293: dup_x1
    //   294: swap
    //   295: iconst_0
    //   296: swap
    //   297: aastore
    //   298: invokevirtual V : ([Ljava/lang/Object;)V
    //   301: aload_0
    //   302: getfield o : Lwtf/opal/kt;
    //   305: dload #13
    //   307: invokestatic valueOf : (D)Ljava/lang/Double;
    //   310: iconst_1
    //   311: anewarray java/lang/Object
    //   314: dup_x1
    //   315: swap
    //   316: iconst_0
    //   317: swap
    //   318: aastore
    //   319: invokevirtual V : ([Ljava/lang/Object;)V
    //   322: aload_0
    //   323: lload #8
    //   325: iconst_1
    //   326: anewarray java/lang/Object
    //   329: dup_x2
    //   330: dup_x2
    //   331: pop
    //   332: invokestatic valueOf : (J)Ljava/lang/Long;
    //   335: iconst_0
    //   336: swap
    //   337: aastore
    //   338: invokevirtual s : ([Ljava/lang/Object;)D
    //   341: dreturn
    //   342: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   345: athrow
    //   346: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   349: dload #11
    //   351: dload #13
    //   353: invokevirtual nextDouble : (DD)D
    //   356: dreturn
    // Exception table:
    //   from	to	target	type
    //   46	67	70	wtf/opal/x5
    //   64	103	106	wtf/opal/x5
    //   74	113	116	wtf/opal/x5
    //   110	135	138	wtf/opal/x5
    //   120	145	148	wtf/opal/x5
    //   152	182	185	wtf/opal/x5
    //   161	193	193	wtf/opal/x5
    //   227	246	249	wtf/opal/x5
    //   243	256	256	wtf/opal/x5
    //   260	270	273	wtf/opal/x5
    //   277	342	342	wtf/opal/x5
  }
  
  public double F(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    int j = q.F();
    try {
      if (j == 0)
        try {
          if (this.L.z().booleanValue()) {
          
          } else {
            return -1.0D;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  static {
    long l = a ^ 0x700131E9C381L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[9];
    boolean bool = false;
    String str;
    int j = (str = "É±^ßÿ$æ|rJNø\004?¡g½Kg. ±\022n²\005\\iä \031´\033ià>}ÂQ·d\013\024nÆÿ2ª(×\004¤ØíN¥¨Å]wÛ ®j\004\025z2Øòþ%NßEÿËcZµ\033Øèä\0308k.<ày8#PS¶\007{\"eÕÌú§\020úûÕ²V·©<ucZú\023(\005¦\034k¿¡®\fÚé\025Ç\b\b¢©òC÷Q]N\031\007÷ømÁ\037D°Ð!VV^®(¦1×6ôÕ\024\027QÔ;²\024Ä[\036¼Yr\017\002ð\030_¡/wq28?<\033o\020").length();
    byte b2 = 24;
    byte b = -1;
    while (true);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    byte b1 = 0;
    int j;
    char[] arrayOfChar = new char[j = paramArrayOfbyte.length];
    for (byte b2 = 0; b2 < j; b2++) {
      int k;
      if ((k = 0xFF & paramArrayOfbyte[b2]) < 192) {
        arrayOfChar[b1++] = (char)k;
      } else if (k < 224) {
        char c = (char)((char)(k & 0x1F) << 6);
        k = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(k & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b2 < j - 2) {
        char c = (char)((char)(k & 0xF) << 12);
        k = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(k & 0x3F) << 6);
        k = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(k & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
  
  private static String a(int paramInt, long paramLong) {
    int j = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x12F5;
    if (d[j] == null) {
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
        throw new RuntimeException("wtf/opal/i", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[j].getBytes("ISO-8859-1");
      d[j] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return d[j];
  }
  
  private static Object a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int j = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    String str = a(j, l);
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
    //   65: ldc_w 'wtf/opal/i'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\i.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */