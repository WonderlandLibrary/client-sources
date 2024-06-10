package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;

public final class jk extends d {
  private final List<class_2596<?>> m;
  
  private final kr L;
  
  private final ke v;
  
  private final kt w;
  
  private final gm<lb> u;
  
  private final gm<b6> D;
  
  private static final long a = on.a(-1258443123021527095L, -2043219786581067378L, MethodHandles.lookup().lookupClass()).a(273700138020213L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public jk(int paramInt, short paramShort1, short paramShort2) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #32
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #48
    //   9: lshl
    //   10: bipush #32
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/jk.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 128644012933380
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 55092925706872
    //   42: lxor
    //   43: lstore #8
    //   45: dup2
    //   46: ldc2_w 31223342197549
    //   49: lxor
    //   50: lstore #10
    //   52: pop2
    //   53: aload_0
    //   54: sipush #7701
    //   57: ldc2_w 5265630499544875766
    //   60: lload #4
    //   62: lxor
    //   63: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   68: lload #10
    //   70: sipush #20754
    //   73: ldc2_w 2873344697964957168
    //   76: lload #4
    //   78: lxor
    //   79: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   84: getstatic wtf/opal/kn.OTHER : Lwtf/opal/kn;
    //   87: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   90: aload_0
    //   91: new java/util/concurrent/CopyOnWriteArrayList
    //   94: dup
    //   95: invokespecial <init> : ()V
    //   98: putfield m : Ljava/util/List;
    //   101: aload_0
    //   102: new wtf/opal/kr
    //   105: dup
    //   106: invokespecial <init> : ()V
    //   109: putfield L : Lwtf/opal/kr;
    //   112: aload_0
    //   113: new wtf/opal/ke
    //   116: dup
    //   117: sipush #15707
    //   120: ldc2_w 3847241711910347194
    //   123: lload #4
    //   125: lxor
    //   126: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   131: iconst_0
    //   132: invokespecial <init> : (Ljava/lang/String;Z)V
    //   135: putfield v : Lwtf/opal/ke;
    //   138: aload_0
    //   139: new wtf/opal/kt
    //   142: dup
    //   143: sipush #5488
    //   146: ldc2_w 3966800219476380048
    //   149: lload #4
    //   151: lxor
    //   152: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   157: ldc2_w 500.0
    //   160: ldc2_w 50.0
    //   163: ldc2_w 10000.0
    //   166: dconst_1
    //   167: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   170: putfield w : Lwtf/opal/kt;
    //   173: aload_0
    //   174: aload_0
    //   175: <illegal opcode> H : (Lwtf/opal/jk;)Lwtf/opal/gm;
    //   180: putfield u : Lwtf/opal/gm;
    //   183: aload_0
    //   184: aload_0
    //   185: <illegal opcode> H : (Lwtf/opal/jk;)Lwtf/opal/gm;
    //   190: putfield D : Lwtf/opal/gm;
    //   193: invokestatic z : ()Ljava/lang/String;
    //   196: aload_0
    //   197: getfield w : Lwtf/opal/kt;
    //   200: aload_0
    //   201: getfield v : Lwtf/opal/ke;
    //   204: aload_0
    //   205: <illegal opcode> test : (Lwtf/opal/jk;)Ljava/util/function/Predicate;
    //   210: lload #6
    //   212: dup2_x1
    //   213: pop2
    //   214: iconst_3
    //   215: anewarray java/lang/Object
    //   218: dup_x1
    //   219: swap
    //   220: iconst_2
    //   221: swap
    //   222: aastore
    //   223: dup_x2
    //   224: dup_x2
    //   225: pop
    //   226: invokestatic valueOf : (J)Ljava/lang/Long;
    //   229: iconst_1
    //   230: swap
    //   231: aastore
    //   232: dup_x1
    //   233: swap
    //   234: iconst_0
    //   235: swap
    //   236: aastore
    //   237: invokevirtual C : ([Ljava/lang/Object;)V
    //   240: astore #12
    //   242: aload_0
    //   243: iconst_2
    //   244: anewarray wtf/opal/k3
    //   247: dup
    //   248: iconst_0
    //   249: aload_0
    //   250: getfield v : Lwtf/opal/ke;
    //   253: aastore
    //   254: dup
    //   255: iconst_1
    //   256: aload_0
    //   257: getfield w : Lwtf/opal/kt;
    //   260: aastore
    //   261: lload #8
    //   263: dup2_x1
    //   264: pop2
    //   265: iconst_2
    //   266: anewarray java/lang/Object
    //   269: dup_x1
    //   270: swap
    //   271: iconst_1
    //   272: swap
    //   273: aastore
    //   274: dup_x2
    //   275: dup_x2
    //   276: pop
    //   277: invokestatic valueOf : (J)Ljava/lang/Long;
    //   280: iconst_0
    //   281: swap
    //   282: aastore
    //   283: invokevirtual o : ([Ljava/lang/Object;)V
    //   286: invokestatic D : ()[Lwtf/opal/d;
    //   289: ifnull -> 304
    //   292: ldc 'auOzUc'
    //   294: invokestatic G : (Ljava/lang/String;)V
    //   297: goto -> 304
    //   300: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   303: athrow
    //   304: return
    // Exception table:
    //   from	to	target	type
    //   242	297	300	wtf/opal/x5
  }
  
  protected void z(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    Objects.requireNonNull(b9.c.method_1562().method_48296());
    this.m.forEach(b9.c.method_1562().method_48296()::method_10743);
    this.m.clear();
    new Object[1];
    super.z(new Object[] { Long.valueOf(l2) });
  }
  
  private void lambda$new$2(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/jk.a : J
    //   3: ldc2_w 71908012020186
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 82638110108343
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic z : ()Ljava/lang/String;
    //   20: astore #6
    //   22: aload_0
    //   23: getfield v : Lwtf/opal/ke;
    //   26: invokevirtual z : ()Ljava/lang/Object;
    //   29: checkcast java/lang/Boolean
    //   32: invokevirtual booleanValue : ()Z
    //   35: aload #6
    //   37: ifnonnull -> 65
    //   40: ifeq -> 80
    //   43: goto -> 50
    //   46: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   49: athrow
    //   50: aload_1
    //   51: iconst_0
    //   52: anewarray java/lang/Object
    //   55: invokevirtual W : ([Ljava/lang/Object;)Z
    //   58: goto -> 65
    //   61: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: aload #6
    //   67: ifnonnull -> 146
    //   70: ifne -> 81
    //   73: goto -> 80
    //   76: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   79: athrow
    //   80: return
    //   81: aload_0
    //   82: aload #6
    //   84: ifnonnull -> 185
    //   87: getfield L : Lwtf/opal/kr;
    //   90: aload_0
    //   91: getfield w : Lwtf/opal/kt;
    //   94: invokevirtual z : ()Ljava/lang/Object;
    //   97: checkcast java/lang/Double
    //   100: invokevirtual longValue : ()J
    //   103: lload #4
    //   105: iconst_1
    //   106: iconst_3
    //   107: anewarray java/lang/Object
    //   110: dup_x1
    //   111: swap
    //   112: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   115: iconst_2
    //   116: swap
    //   117: aastore
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
    //   136: invokevirtual v : ([Ljava/lang/Object;)Z
    //   139: goto -> 146
    //   142: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   145: athrow
    //   146: ifeq -> 193
    //   149: aload_0
    //   150: getfield m : Ljava/util/List;
    //   153: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   156: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   159: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   162: dup
    //   163: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   166: pop
    //   167: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   172: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   177: aload_0
    //   178: goto -> 185
    //   181: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: getfield m : Ljava/util/List;
    //   188: invokeinterface clear : ()V
    //   193: return
    // Exception table:
    //   from	to	target	type
    //   22	43	46	wtf/opal/x5
    //   40	58	61	wtf/opal/x5
    //   65	73	76	wtf/opal/x5
    //   81	139	142	wtf/opal/x5
    //   146	178	181	wtf/opal/x5
  }
  
  private void lambda$new$1(lb paramlb) {
    paramlb.Z(new Object[0]);
    this.m.add(paramlb.J(new Object[0]));
  }
  
  private boolean lambda$new$0(ke paramke) {
    return this.v.z().booleanValue();
  }
  
  static {
    long l = a ^ 0x554DA8EAA963L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[4];
    boolean bool = false;
    String str;
    int i = (str = "OËûP,69Ie÷Ã;5zWËRLÒÏqgß»Yl°«\nÑTtpÜLc<r·êbÚÄ¾\017_\fø,ÏN0;ö59\016\"\bZdhG7¨+~n÷\f\r¬0Àîéi¡è\020ö\027\000/lÔæ3f( ¥\020D)ÊúC´\032AÙ¿Â}Ë\024Ùã").length();
    byte b2 = 104;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6DA6;
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
        throw new RuntimeException("wtf/opal/jk", exception);
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
    //   66: ldc_w 'wtf/opal/jk'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */