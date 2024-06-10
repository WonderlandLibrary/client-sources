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

public final class s extends d {
  public final ky<kz> u;
  
  private static final long a = on.a(5117237781297474623L, -3169390860565135335L, MethodHandles.lookup().lookupClass()).a(172052250751996L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public s(int paramInt1, byte paramByte, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #32
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #56
    //   9: lshl
    //   10: bipush #32
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #40
    //   18: lshl
    //   19: bipush #40
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/s.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 92826054485718
    //   35: lxor
    //   36: dup2
    //   37: bipush #48
    //   39: lushr
    //   40: l2i
    //   41: istore #6
    //   43: dup2
    //   44: bipush #16
    //   46: lshl
    //   47: bipush #32
    //   49: lushr
    //   50: l2i
    //   51: istore #7
    //   53: dup2
    //   54: bipush #48
    //   56: lshl
    //   57: bipush #48
    //   59: lushr
    //   60: l2i
    //   61: istore #8
    //   63: pop2
    //   64: dup2
    //   65: ldc2_w 65881350886249
    //   68: lxor
    //   69: lstore #9
    //   71: dup2
    //   72: ldc2_w 42479715556552
    //   75: lxor
    //   76: lstore #11
    //   78: dup2
    //   79: ldc2_w 9741089064349
    //   82: lxor
    //   83: lstore #13
    //   85: pop2
    //   86: aload_0
    //   87: sipush #13559
    //   90: ldc2_w 6327928541051527402
    //   93: lload #4
    //   95: lxor
    //   96: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   101: lload #13
    //   103: sipush #1562
    //   106: ldc2_w 4659968166786372102
    //   109: lload #4
    //   111: lxor
    //   112: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   117: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   120: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   123: aload_0
    //   124: new wtf/opal/ky
    //   127: dup
    //   128: sipush #21159
    //   131: ldc2_w 2592510620132787897
    //   134: lload #4
    //   136: lxor
    //   137: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   142: aload_0
    //   143: getstatic wtf/opal/kz.VANILLA : Lwtf/opal/kz;
    //   146: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   149: putfield u : Lwtf/opal/ky;
    //   152: aload_0
    //   153: aload_0
    //   154: getfield u : Lwtf/opal/ky;
    //   157: iconst_3
    //   158: anewarray wtf/opal/u_
    //   161: dup
    //   162: iconst_0
    //   163: new wtf/opal/ud
    //   166: dup
    //   167: iload #6
    //   169: i2s
    //   170: iload #7
    //   172: iload #8
    //   174: i2s
    //   175: aload_0
    //   176: invokespecial <init> : (SISLwtf/opal/s;)V
    //   179: aastore
    //   180: dup
    //   181: iconst_1
    //   182: new wtf/opal/ut
    //   185: dup
    //   186: aload_0
    //   187: invokespecial <init> : (Lwtf/opal/s;)V
    //   190: aastore
    //   191: dup
    //   192: iconst_2
    //   193: new wtf/opal/uy
    //   196: dup
    //   197: lload #9
    //   199: aload_0
    //   200: invokespecial <init> : (JLwtf/opal/s;)V
    //   203: aastore
    //   204: iconst_2
    //   205: anewarray java/lang/Object
    //   208: dup_x1
    //   209: swap
    //   210: iconst_1
    //   211: swap
    //   212: aastore
    //   213: dup_x1
    //   214: swap
    //   215: iconst_0
    //   216: swap
    //   217: aastore
    //   218: invokevirtual Y : ([Ljava/lang/Object;)V
    //   221: aload_0
    //   222: iconst_1
    //   223: anewarray wtf/opal/k3
    //   226: dup
    //   227: iconst_0
    //   228: aload_0
    //   229: getfield u : Lwtf/opal/ky;
    //   232: aastore
    //   233: lload #11
    //   235: dup2_x1
    //   236: pop2
    //   237: iconst_2
    //   238: anewarray java/lang/Object
    //   241: dup_x1
    //   242: swap
    //   243: iconst_1
    //   244: swap
    //   245: aastore
    //   246: dup_x2
    //   247: dup_x2
    //   248: pop
    //   249: invokestatic valueOf : (J)Ljava/lang/Long;
    //   252: iconst_0
    //   253: swap
    //   254: aastore
    //   255: invokevirtual o : ([Ljava/lang/Object;)V
    //   258: return
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return ((kz)this.u.z()).toString();
  }
  
  static {
    long l = a ^ 0x2226BADBF03BL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "o\004.\002Ãº,Èñ\022ES^[mæå]\036Í\020ß¬}K\006\t´*ÖPºÚ6³#\020RFñéIM¸¶úþ\"\020\000").length();
    byte b2 = 24;
    byte b = -1;
    while (true);
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xBE8;
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
        throw new RuntimeException("wtf/opal/s", exception);
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
    //   49: goto -> 102
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc 'wtf/opal/s'
    //   67: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: ldc_w ' : '
    //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: aload_1
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: ldc_w ' : '
    //   83: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: aload_2
    //   87: invokevirtual toString : ()Ljava/lang/String;
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: invokevirtual toString : ()Ljava/lang/String;
    //   96: aload #4
    //   98: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   101: athrow
    //   102: aload_3
    //   103: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\s.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */