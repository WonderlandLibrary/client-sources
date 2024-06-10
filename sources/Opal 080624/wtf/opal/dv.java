package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public enum dv {
  MINECRAFT, GEIST_SANS, UBUNTU_SANS, PRODUCT_SANS, SOURCE_SANS_3, BR_COBANE, COMFORTAA, MANROPE, GREYCLIFF_CF, SF_UI_DISPLAY;
  
  private final String g;
  
  private final float a;
  
  private final boolean O;
  
  private static final dv[] e;
  
  private static final long b = on.a(85172206267709342L, -4265918135892514860L, MethodHandles.lookup().lookupClass()).a(222981957581910L);
  
  private static final long[] c;
  
  private static final Integer[] d;
  
  private static final Map f;
  
  dv(String paramString1, float paramFloat, boolean paramBoolean) {
    this.g = paramString1;
    this.a = paramFloat;
    this.O = paramBoolean;
  }
  
  public float b(Object[] paramArrayOfObject) {
    return this.a;
  }
  
  public boolean e(Object[] paramArrayOfObject) {
    return this.O;
  }
  
  public String toString() {
    return this.g;
  }
  
  private static dv[] G(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/dv.b : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: sipush #25810
    //   21: ldc2_w 4470357018934380474
    //   24: lload_1
    //   25: lxor
    //   26: <illegal opcode> d : (IJ)I
    //   31: anewarray wtf/opal/dv
    //   34: dup
    //   35: iconst_0
    //   36: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   39: aastore
    //   40: dup
    //   41: iconst_1
    //   42: getstatic wtf/opal/dv.GEIST_SANS : Lwtf/opal/dv;
    //   45: aastore
    //   46: dup
    //   47: iconst_2
    //   48: getstatic wtf/opal/dv.UBUNTU_SANS : Lwtf/opal/dv;
    //   51: aastore
    //   52: dup
    //   53: iconst_3
    //   54: getstatic wtf/opal/dv.PRODUCT_SANS : Lwtf/opal/dv;
    //   57: aastore
    //   58: dup
    //   59: iconst_4
    //   60: getstatic wtf/opal/dv.SOURCE_SANS_3 : Lwtf/opal/dv;
    //   63: aastore
    //   64: dup
    //   65: iconst_5
    //   66: getstatic wtf/opal/dv.BR_COBANE : Lwtf/opal/dv;
    //   69: aastore
    //   70: dup
    //   71: sipush #8263
    //   74: ldc2_w 8364552525732889389
    //   77: lload_1
    //   78: lxor
    //   79: <illegal opcode> d : (IJ)I
    //   84: getstatic wtf/opal/dv.COMFORTAA : Lwtf/opal/dv;
    //   87: aastore
    //   88: dup
    //   89: sipush #6768
    //   92: ldc2_w 8460971900144875807
    //   95: lload_1
    //   96: lxor
    //   97: <illegal opcode> d : (IJ)I
    //   102: getstatic wtf/opal/dv.MANROPE : Lwtf/opal/dv;
    //   105: aastore
    //   106: dup
    //   107: sipush #413
    //   110: ldc2_w 650518234452746995
    //   113: lload_1
    //   114: lxor
    //   115: <illegal opcode> d : (IJ)I
    //   120: getstatic wtf/opal/dv.GREYCLIFF_CF : Lwtf/opal/dv;
    //   123: aastore
    //   124: dup
    //   125: sipush #3257
    //   128: ldc2_w 848259812788334546
    //   131: lload_1
    //   132: lxor
    //   133: <illegal opcode> d : (IJ)I
    //   138: getstatic wtf/opal/dv.SF_UI_DISPLAY : Lwtf/opal/dv;
    //   141: aastore
    //   142: areturn
  }
  
  static {
    long l1 = b ^ 0x39417DE5A69CL;
    long l2 = l1 ^ 0x3BD16CAA8DC7L;
    (new byte[8])[0] = (byte)(int)(l1 >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l1 << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[20];
    boolean bool = false;
    String str;
    int i = (str = "K¨q\0131¬O©16´+¢¡\0207×\f?µå\\_mý_¿w´\020¢%\000ÿ*=\030¬\001?å|ì\tö&\buÒ°ÛW¿IS\020q\025EÌ\020\027¶eªJõa¨÷\020ÃQíy\006Ó(²ñBÝ3ßí\020°×=¹:Hr¦´éÍ5\0202`\fï}ç£ý\005ÿq²Ó>\020¥¸<Öe~giFPÒ{¾öK\020\031µàkù]\032UðAJ\016\020Ú5äóYx\rf¥aW©\031\032\020\016¿bLuä±ÏZV]%\020ð.ì]\023±¯ÆÔXÙÀÂC\r\020Ð\002>ç,çÇÀ')\024aA¬®\020JBÄA¹}dÍÅÞÑ\026M!\020¹&\b¶td¿á¥¢$ÃJ\020Õª£\nTB)øûu\b\026\036 Y\020¸Sþ\b\002·{¿Æ2=çM\034").length();
    byte b2 = 16;
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
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4832;
    if (d[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = c[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])f.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          f.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/dv", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      d[i] = Integer.valueOf(j);
    } 
    return d[i].intValue();
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
    //   66: ldc_w 'wtf/opal/dv'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dv.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */