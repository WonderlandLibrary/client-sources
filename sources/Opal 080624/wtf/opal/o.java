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

public final class o extends d {
  private final ug o;
  
  public final ky<xk> Y;
  
  private static String z;
  
  private static final long a = on.a(-3435614618182286787L, 1675201274943434692L, MethodHandles.lookup().lookupClass()).a(51853368448732L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public o(short paramShort, int paramInt1, int paramInt2) {
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
    //   23: getstatic wtf/opal/o.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 21808468428817
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 2759479981325
    //   42: lxor
    //   43: lstore #8
    //   45: dup2
    //   46: ldc2_w 49464864582744
    //   49: lxor
    //   50: lstore #10
    //   52: dup2
    //   53: ldc2_w 114333348104755
    //   56: lxor
    //   57: lstore #12
    //   59: dup2
    //   60: ldc2_w 112977805068316
    //   63: lxor
    //   64: lstore #14
    //   66: dup2
    //   67: ldc2_w 38142314888626
    //   70: lxor
    //   71: lstore #16
    //   73: pop2
    //   74: invokestatic n : ()Ljava/lang/String;
    //   77: aload_0
    //   78: sipush #7745
    //   81: ldc2_w 214280692786040551
    //   84: lload #4
    //   86: lxor
    //   87: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   92: lload #10
    //   94: sipush #21387
    //   97: ldc2_w 6446559007137121071
    //   100: lload #4
    //   102: lxor
    //   103: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   108: getstatic wtf/opal/kn.EXPLOIT : Lwtf/opal/kn;
    //   111: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   114: aload_0
    //   115: new wtf/opal/ky
    //   118: dup
    //   119: sipush #29463
    //   122: ldc2_w 8520812703787901874
    //   125: lload #4
    //   127: lxor
    //   128: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   133: aload_0
    //   134: getstatic wtf/opal/xk.WATCHDOG_BLINK_INV : Lwtf/opal/xk;
    //   137: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   140: putfield Y : Lwtf/opal/ky;
    //   143: aload_0
    //   144: iconst_1
    //   145: anewarray wtf/opal/k3
    //   148: dup
    //   149: iconst_0
    //   150: aload_0
    //   151: getfield Y : Lwtf/opal/ky;
    //   154: aastore
    //   155: lload #8
    //   157: dup2_x1
    //   158: pop2
    //   159: iconst_2
    //   160: anewarray java/lang/Object
    //   163: dup_x1
    //   164: swap
    //   165: iconst_1
    //   166: swap
    //   167: aastore
    //   168: dup_x2
    //   169: dup_x2
    //   170: pop
    //   171: invokestatic valueOf : (J)Ljava/lang/Long;
    //   174: iconst_0
    //   175: swap
    //   176: aastore
    //   177: invokevirtual o : ([Ljava/lang/Object;)V
    //   180: aload_0
    //   181: new wtf/opal/ug
    //   184: dup
    //   185: aload_0
    //   186: lload #14
    //   188: invokespecial <init> : (Lwtf/opal/o;J)V
    //   191: putfield o : Lwtf/opal/ug;
    //   194: astore #18
    //   196: aload_0
    //   197: aload_0
    //   198: getfield Y : Lwtf/opal/ky;
    //   201: iconst_4
    //   202: anewarray wtf/opal/u_
    //   205: dup
    //   206: iconst_0
    //   207: new wtf/opal/u3
    //   210: dup
    //   211: lload #12
    //   213: aload_0
    //   214: invokespecial <init> : (JLwtf/opal/o;)V
    //   217: aastore
    //   218: dup
    //   219: iconst_1
    //   220: aload_0
    //   221: getfield o : Lwtf/opal/ug;
    //   224: aastore
    //   225: dup
    //   226: iconst_2
    //   227: new wtf/opal/u8
    //   230: dup
    //   231: lload #6
    //   233: aload_0
    //   234: invokespecial <init> : (JLwtf/opal/o;)V
    //   237: aastore
    //   238: dup
    //   239: iconst_3
    //   240: new wtf/opal/ue
    //   243: dup
    //   244: lload #16
    //   246: aload_0
    //   247: invokespecial <init> : (JLwtf/opal/o;)V
    //   250: aastore
    //   251: iconst_2
    //   252: anewarray java/lang/Object
    //   255: dup_x1
    //   256: swap
    //   257: iconst_1
    //   258: swap
    //   259: aastore
    //   260: dup_x1
    //   261: swap
    //   262: iconst_0
    //   263: swap
    //   264: aastore
    //   265: invokevirtual Y : ([Ljava/lang/Object;)V
    //   268: aload #18
    //   270: ifnull -> 287
    //   273: iconst_4
    //   274: anewarray wtf/opal/d
    //   277: invokestatic p : ([Lwtf/opal/d;)V
    //   280: goto -> 287
    //   283: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   286: athrow
    //   287: return
    // Exception table:
    //   from	to	target	type
    //   196	280	283	wtf/opal/x5
  }
  
  public boolean A(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/o.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 40090289850922
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic n : ()Ljava/lang/String;
    //   30: astore #6
    //   32: aload_0
    //   33: aload #6
    //   35: ifnonnull -> 65
    //   38: getfield Y : Lwtf/opal/ky;
    //   41: invokevirtual z : ()Ljava/lang/Object;
    //   44: getstatic wtf/opal/xk.WATCHDOG_BLINK_INV : Lwtf/opal/xk;
    //   47: if_acmpne -> 101
    //   50: goto -> 57
    //   53: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   56: athrow
    //   57: aload_0
    //   58: goto -> 65
    //   61: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: getfield o : Lwtf/opal/ug;
    //   68: lload #4
    //   70: iconst_1
    //   71: anewarray java/lang/Object
    //   74: dup_x2
    //   75: dup_x2
    //   76: pop
    //   77: invokestatic valueOf : (J)Ljava/lang/Long;
    //   80: iconst_0
    //   81: swap
    //   82: aastore
    //   83: invokevirtual d : ([Ljava/lang/Object;)Z
    //   86: aload #6
    //   88: ifnonnull -> 102
    //   91: ifeq -> 105
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: iconst_1
    //   102: goto -> 106
    //   105: iconst_0
    //   106: ireturn
    // Exception table:
    //   from	to	target	type
    //   32	50	53	wtf/opal/x5
    //   38	58	61	wtf/opal/x5
    //   65	94	97	wtf/opal/x5
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return ((xk)this.Y.z()).toString();
  }
  
  public static void J(String paramString) {
    z = paramString;
  }
  
  public static String n() {
    return z;
  }
  
  static {
    J(null);
    long l = a ^ 0x3635908C6268L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "\027÷âï8\031\036.\020i8m\025Ú\007\nFä¿»\\¥O}o4ÞSð`w¢ØÌrÐ¨w2F\000Þ\022kÝÁ×ü5Ìæ@ß\\C\tãOGü±\032]wíeó\004°³¨\025\020Þ\021¶²X<¿!\032²?×ôV>õ\030\031AoM²_q-\022UÐÇ/\025~]K\004^\037,L").length();
    byte b2 = 88;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6695;
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
        throw new RuntimeException("wtf/opal/o", exception);
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
    //   65: ldc_w 'wtf/opal/o'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\o.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */