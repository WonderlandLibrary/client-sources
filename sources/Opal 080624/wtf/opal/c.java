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
import net.minecraft.class_2596;
import net.minecraft.class_2846;

public final class c extends d {
  private boolean J;
  
  private final gm<lz> p;
  
  private final gm<lb> n;
  
  private static final long a = on.a(-5586346245651161215L, 2814816325718527631L, MethodHandles.lookup().lookupClass()).a(213003385538049L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] g;
  
  private static final Integer[] k;
  
  private static final Map l;
  
  public c(int paramInt1, int paramInt2, char paramChar) {
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
    //   23: getstatic wtf/opal/c.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 139881234269412
    //   35: lxor
    //   36: lstore #6
    //   38: pop2
    //   39: aload_0
    //   40: sipush #15896
    //   43: ldc2_w 6832537703894790109
    //   46: lload #4
    //   48: lxor
    //   49: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   54: lload #6
    //   56: sipush #21077
    //   59: ldc2_w 545473643315020689
    //   62: lload #4
    //   64: lxor
    //   65: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   70: getstatic wtf/opal/kn.EXPLOIT : Lwtf/opal/kn;
    //   73: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   76: aload_0
    //   77: aload_0
    //   78: <illegal opcode> H : (Lwtf/opal/c;)Lwtf/opal/gm;
    //   83: putfield p : Lwtf/opal/gm;
    //   86: aload_0
    //   87: <illegal opcode> H : ()Lwtf/opal/gm;
    //   92: putfield n : Lwtf/opal/gm;
    //   95: return
  }
  
  private static void lambda$new$1(lb paramlb) {
    long l = a ^ 0x1C695E58F2FDL;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    String str = o.n();
    try {
      if (str == null)
        try {
          if (class_2596 instanceof class_2846) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_2846 class_2846 = (class_2846)class_2596;
    if (class_2846.method_12363().equals(class_2846.class_2847.field_12974));
  }
  
  private void lambda$new$0(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/c.a : J
    //   3: ldc2_w 100772078148416
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic n : ()Ljava/lang/String;
    //   11: astore #4
    //   13: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   16: getfield field_1724 : Lnet/minecraft/class_746;
    //   19: invokevirtual method_6115 : ()Z
    //   22: aload #4
    //   24: ifnonnull -> 60
    //   27: ifeq -> 164
    //   30: goto -> 37
    //   33: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   36: athrow
    //   37: aload_0
    //   38: aload #4
    //   40: ifnonnull -> 172
    //   43: goto -> 50
    //   46: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   49: athrow
    //   50: getfield J : Z
    //   53: goto -> 60
    //   56: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   59: athrow
    //   60: ifne -> 164
    //   63: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   66: getfield field_1724 : Lnet/minecraft/class_746;
    //   69: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   72: getfield field_7545 : I
    //   75: iconst_1
    //   76: iadd
    //   77: sipush #13356
    //   80: ldc2_w 3370680061641081980
    //   83: lload_2
    //   84: lxor
    //   85: <illegal opcode> k : (IJ)I
    //   90: irem
    //   91: istore #5
    //   93: iconst_0
    //   94: istore #6
    //   96: iload #6
    //   98: sipush #27652
    //   101: ldc2_w 1693666534981835861
    //   104: lload_2
    //   105: lxor
    //   106: <illegal opcode> k : (IJ)I
    //   111: if_icmpge -> 154
    //   114: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   117: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   120: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   123: new net/minecraft/class_2828$class_5911
    //   126: dup
    //   127: iconst_1
    //   128: invokespecial <init> : (Z)V
    //   131: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   134: iinc #6, 1
    //   137: aload #4
    //   139: ifnonnull -> 159
    //   142: aload #4
    //   144: ifnull -> 96
    //   147: goto -> 154
    //   150: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   153: athrow
    //   154: aload_0
    //   155: iconst_1
    //   156: putfield J : Z
    //   159: aload #4
    //   161: ifnull -> 176
    //   164: aload_0
    //   165: goto -> 172
    //   168: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   171: athrow
    //   172: iconst_0
    //   173: putfield J : Z
    //   176: return
    // Exception table:
    //   from	to	target	type
    //   13	30	33	wtf/opal/x5
    //   27	43	46	wtf/opal/x5
    //   37	53	56	wtf/opal/x5
    //   114	147	150	wtf/opal/x5
    //   159	165	168	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x291B97CFF79DL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "ºfk(É\032\003\002¬\037¸Ç ãA\025NQýNF#ßÚ«\020gÇöi¶:\030\036Ò+Ý)\001¹K~3¶¨kËº/ðS¬Æ").length();
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
        char c1 = (char)((char)(j & 0x1F) << 6);
        j = paramArrayOfbyte[++b2];
        c1 = (char)(c1 | (char)(j & 0x3F));
        arrayOfChar[b1++] = c1;
      } else if (b2 < i - 2) {
        char c1 = (char)((char)(j & 0xF) << 12);
        j = paramArrayOfbyte[++b2];
        c1 = (char)(c1 | (char)(j & 0x3F) << 6);
        j = paramArrayOfbyte[++b2];
        c1 = (char)(c1 | (char)(j & 0x3F));
        arrayOfChar[b1++] = c1;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4F49;
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
        throw new RuntimeException("wtf/opal/c", exception);
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
    //   66: ldc_w 'wtf/opal/c'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x23AA;
    if (k[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = g[i];
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
        throw new RuntimeException("wtf/opal/c", exception);
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
    //   66: ldc_w 'wtf/opal/c'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\c.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */