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

public final class r extends d {
  private boolean g;
  
  private final gm<u0> o;
  
  private static final long a = on.a(-8319731504713840219L, -4255110641237808660L, MethodHandles.lookup().lookupClass()).a(80085304126367L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long k;
  
  public r(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/r.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 9311606400929
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: sipush #2897
    //   18: ldc2_w 5561156190176349541
    //   21: lload_1
    //   22: lxor
    //   23: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   28: lload_3
    //   29: sipush #3124
    //   32: ldc2_w 3549749427307317761
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   42: getstatic wtf/opal/kn.EXPLOIT : Lwtf/opal/kn;
    //   45: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   48: aload_0
    //   49: aload_0
    //   50: <illegal opcode> H : (Lwtf/opal/r;)Lwtf/opal/gm;
    //   55: putfield o : Lwtf/opal/gm;
    //   58: return
  }
  
  protected void z(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    new Object[1];
    super.z(new Object[] { Long.valueOf(l2) });
  }
  
  protected void K(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.g = false;
    new Object[1];
    super.K(new Object[] { Long.valueOf(l2) });
  }
  
  private void lambda$new$0(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/r.a : J
    //   3: ldc2_w 57434759774598
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic n : ()Ljava/lang/String;
    //   11: astore #4
    //   13: aload_1
    //   14: iconst_0
    //   15: anewarray java/lang/Object
    //   18: invokevirtual K : ([Ljava/lang/Object;)Z
    //   21: aload #4
    //   23: ifnonnull -> 144
    //   26: ifne -> 121
    //   29: goto -> 36
    //   32: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   35: athrow
    //   36: aload_0
    //   37: getfield g : Z
    //   40: aload #4
    //   42: ifnonnull -> 88
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: ifeq -> 190
    //   55: goto -> 62
    //   58: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   61: athrow
    //   62: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   65: getfield field_1724 : Lnet/minecraft/class_746;
    //   68: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   71: getfield field_7545 : I
    //   74: iconst_1
    //   75: iadd
    //   76: getstatic wtf/opal/r.k : J
    //   79: l2i
    //   80: irem
    //   81: goto -> 88
    //   84: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: istore #5
    //   90: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   93: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   96: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   99: new net/minecraft/class_2868
    //   102: dup
    //   103: iload #5
    //   105: invokespecial <init> : (I)V
    //   108: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   111: aload_0
    //   112: iconst_0
    //   113: putfield g : Z
    //   116: aload #4
    //   118: ifnull -> 190
    //   121: aload_0
    //   122: aload #4
    //   124: ifnonnull -> 186
    //   127: goto -> 134
    //   130: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   133: athrow
    //   134: getfield g : Z
    //   137: goto -> 144
    //   140: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   143: athrow
    //   144: ifne -> 190
    //   147: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   150: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   153: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   156: new net/minecraft/class_2868
    //   159: dup
    //   160: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   163: getfield field_1724 : Lnet/minecraft/class_746;
    //   166: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   169: getfield field_7545 : I
    //   172: invokespecial <init> : (I)V
    //   175: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   178: aload_0
    //   179: goto -> 186
    //   182: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   185: athrow
    //   186: iconst_1
    //   187: putfield g : Z
    //   190: return
    // Exception table:
    //   from	to	target	type
    //   13	29	32	wtf/opal/x5
    //   26	45	48	wtf/opal/x5
    //   36	55	58	wtf/opal/x5
    //   52	81	84	wtf/opal/x5
    //   90	127	130	wtf/opal/x5
    //   121	137	140	wtf/opal/x5
    //   144	179	182	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x16A084A0A7FAL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "\032¬l¿ìt¯[+ô¡\003£×Èµ+2øÚ7¹áäÿj\002øø~(.\023ãFå#u\022\032ÕÔ*«|+ì\022g3¦óP ß\007`¤\\ÑÙôâ").length();
    byte b2 = 32;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x3BFC;
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
        throw new RuntimeException("wtf/opal/r", exception);
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
    //   66: ldc_w 'wtf/opal/r'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\r.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */