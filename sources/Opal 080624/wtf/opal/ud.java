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

public final class ud extends u_<s> {
  public final kt w;
  
  public final kt u;
  
  private final gm<b6> R;
  
  private static final long a = on.a(634425804819531827L, 3518406329026408752L, MethodHandles.lookup().lookupClass()).a(257223926805910L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public ud(short paramShort1, int paramInt, short paramShort2, s params) {
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
    //   23: getstatic wtf/opal/ud.a : J
    //   26: lxor
    //   27: lstore #5
    //   29: lload #5
    //   31: dup2
    //   32: ldc2_w 123140699070919
    //   35: lxor
    //   36: lstore #7
    //   38: pop2
    //   39: invokestatic R : ()Ljava/lang/String;
    //   42: aload_0
    //   43: aload #4
    //   45: invokespecial <init> : (Lwtf/opal/d;)V
    //   48: astore #9
    //   50: aload_0
    //   51: new wtf/opal/kt
    //   54: dup
    //   55: sipush #9830
    //   58: ldc2_w 6244563615746960950
    //   61: lload #5
    //   63: lxor
    //   64: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   69: aload_0
    //   70: dconst_1
    //   71: ldc2_w 0.1
    //   74: ldc2_w 9.0
    //   77: ldc2_w 0.1
    //   80: lload #7
    //   82: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/u_;DDDDJ)V
    //   85: putfield w : Lwtf/opal/kt;
    //   88: aload_0
    //   89: new wtf/opal/kt
    //   92: dup
    //   93: sipush #21773
    //   96: ldc2_w 6790469167538756956
    //   99: lload #5
    //   101: lxor
    //   102: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   107: aload_0
    //   108: dconst_1
    //   109: ldc2_w 0.1
    //   112: ldc2_w 9.0
    //   115: ldc2_w 0.1
    //   118: lload #7
    //   120: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/u_;DDDDJ)V
    //   123: putfield u : Lwtf/opal/kt;
    //   126: aload_0
    //   127: aload_0
    //   128: <illegal opcode> H : (Lwtf/opal/ud;)Lwtf/opal/gm;
    //   133: putfield R : Lwtf/opal/gm;
    //   136: aload #9
    //   138: ifnonnull -> 155
    //   141: iconst_3
    //   142: anewarray wtf/opal/d
    //   145: invokestatic p : ([Lwtf/opal/d;)V
    //   148: goto -> 155
    //   151: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   154: athrow
    //   155: return
    // Exception table:
    //   from	to	target	type
    //   50	148	151	wtf/opal/x5
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return kz.VANILLA;
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/ud.a : J
    //   3: ldc2_w 93109988536921
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 111282513893517
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 86538460885017
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic R : ()Ljava/lang/String;
    //   27: astore #8
    //   29: aload_1
    //   30: iconst_0
    //   31: anewarray java/lang/Object
    //   34: invokevirtual W : ([Ljava/lang/Object;)Z
    //   37: ifne -> 45
    //   40: return
    //   41: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   48: getfield field_1724 : Lnet/minecraft/class_746;
    //   51: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   54: getfield field_1724 : Lnet/minecraft/class_746;
    //   57: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   60: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   63: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   66: getfield field_1690 : Lnet/minecraft/class_315;
    //   69: getfield field_1903 : Lnet/minecraft/class_304;
    //   72: invokevirtual method_1434 : ()Z
    //   75: aload #8
    //   77: ifnull -> 134
    //   80: ifeq -> 110
    //   83: goto -> 90
    //   86: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: aload_0
    //   91: getfield u : Lwtf/opal/kt;
    //   94: invokevirtual z : ()Ljava/lang/Object;
    //   97: checkcast java/lang/Double
    //   100: invokevirtual doubleValue : ()D
    //   103: goto -> 162
    //   106: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   113: getfield field_1690 : Lnet/minecraft/class_315;
    //   116: getfield field_1832 : Lnet/minecraft/class_304;
    //   119: aload #8
    //   121: ifnull -> 151
    //   124: invokevirtual method_1434 : ()Z
    //   127: goto -> 134
    //   130: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   133: athrow
    //   134: ifeq -> 161
    //   137: aload_0
    //   138: getfield u : Lwtf/opal/kt;
    //   141: invokevirtual z : ()Ljava/lang/Object;
    //   144: goto -> 151
    //   147: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   150: athrow
    //   151: checkcast java/lang/Double
    //   154: invokevirtual doubleValue : ()D
    //   157: dneg
    //   158: goto -> 162
    //   161: dconst_0
    //   162: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   165: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   168: aload #8
    //   170: ifnull -> 250
    //   173: lload #4
    //   175: iconst_1
    //   176: anewarray java/lang/Object
    //   179: dup_x2
    //   180: dup_x2
    //   181: pop
    //   182: invokestatic valueOf : (J)Ljava/lang/Long;
    //   185: iconst_0
    //   186: swap
    //   187: aastore
    //   188: invokestatic I : ([Ljava/lang/Object;)Z
    //   191: ifeq -> 255
    //   194: goto -> 201
    //   197: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   200: athrow
    //   201: aload_0
    //   202: getfield w : Lwtf/opal/kt;
    //   205: invokevirtual z : ()Ljava/lang/Object;
    //   208: checkcast java/lang/Double
    //   211: invokevirtual doubleValue : ()D
    //   214: lload #6
    //   216: dup2_x2
    //   217: pop2
    //   218: iconst_2
    //   219: anewarray java/lang/Object
    //   222: dup_x2
    //   223: dup_x2
    //   224: pop
    //   225: invokestatic valueOf : (D)Ljava/lang/Double;
    //   228: iconst_1
    //   229: swap
    //   230: aastore
    //   231: dup_x2
    //   232: dup_x2
    //   233: pop
    //   234: invokestatic valueOf : (J)Ljava/lang/Long;
    //   237: iconst_0
    //   238: swap
    //   239: aastore
    //   240: invokestatic k : ([Ljava/lang/Object;)V
    //   243: goto -> 250
    //   246: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   249: athrow
    //   250: aload #8
    //   252: ifnonnull -> 290
    //   255: lload #6
    //   257: dconst_0
    //   258: iconst_2
    //   259: anewarray java/lang/Object
    //   262: dup_x2
    //   263: dup_x2
    //   264: pop
    //   265: invokestatic valueOf : (D)Ljava/lang/Double;
    //   268: iconst_1
    //   269: swap
    //   270: aastore
    //   271: dup_x2
    //   272: dup_x2
    //   273: pop
    //   274: invokestatic valueOf : (J)Ljava/lang/Long;
    //   277: iconst_0
    //   278: swap
    //   279: aastore
    //   280: invokestatic k : ([Ljava/lang/Object;)V
    //   283: goto -> 290
    //   286: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   289: athrow
    //   290: return
    // Exception table:
    //   from	to	target	type
    //   29	41	41	wtf/opal/x5
    //   45	83	86	wtf/opal/x5
    //   80	106	106	wtf/opal/x5
    //   110	127	130	wtf/opal/x5
    //   134	144	147	wtf/opal/x5
    //   162	194	197	wtf/opal/x5
    //   173	243	246	wtf/opal/x5
    //   250	283	286	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x7E4DDF751B69L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "úV\027öôâr:\007Áö\035\020·M5I\025jøh:ËãðÕn(«.\032g¹c\016ÁËA>®2A³WW\025éLrFñ2ª.Í¶Ìê\006§pe").length();
    byte b2 = 32;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4FC;
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
        throw new RuntimeException("wtf/opal/ud", exception);
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
    //   66: ldc_w 'wtf/opal/ud'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\ud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */