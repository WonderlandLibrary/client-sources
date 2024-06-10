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

public final class j3 extends d {
  private final kt D;
  
  private final gm<b6> r;
  
  private final gm<lu> s;
  
  private static final long a = on.a(-5876045178963819784L, 6967719393085013940L, MethodHandles.lookup().lookupClass()).a(230814577249751L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public j3(int paramInt1, int paramInt2, int paramInt3) {
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
    //   23: getstatic wtf/opal/j3.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 22669929883309
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 64737293533176
    //   42: lxor
    //   43: lstore #8
    //   45: pop2
    //   46: aload_0
    //   47: sipush #19358
    //   50: ldc2_w 8201496157812888536
    //   53: lload #4
    //   55: lxor
    //   56: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   61: lload #8
    //   63: sipush #22259
    //   66: ldc2_w 4083535225664540343
    //   69: lload #4
    //   71: lxor
    //   72: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   77: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   80: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   83: aload_0
    //   84: new wtf/opal/kt
    //   87: dup
    //   88: sipush #2553
    //   91: ldc2_w 7942447728068907454
    //   94: lload #4
    //   96: lxor
    //   97: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   102: ldc2_w 1000.0
    //   105: dconst_0
    //   106: ldc2_w 23460.0
    //   109: dconst_1
    //   110: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   113: putfield D : Lwtf/opal/kt;
    //   116: aload_0
    //   117: aload_0
    //   118: <illegal opcode> H : (Lwtf/opal/j3;)Lwtf/opal/gm;
    //   123: putfield r : Lwtf/opal/gm;
    //   126: aload_0
    //   127: <illegal opcode> H : ()Lwtf/opal/gm;
    //   132: putfield s : Lwtf/opal/gm;
    //   135: aload_0
    //   136: iconst_1
    //   137: anewarray wtf/opal/k3
    //   140: dup
    //   141: iconst_0
    //   142: aload_0
    //   143: getfield D : Lwtf/opal/kt;
    //   146: aastore
    //   147: lload #6
    //   149: dup2_x1
    //   150: pop2
    //   151: iconst_2
    //   152: anewarray java/lang/Object
    //   155: dup_x1
    //   156: swap
    //   157: iconst_1
    //   158: swap
    //   159: aastore
    //   160: dup_x2
    //   161: dup_x2
    //   162: pop
    //   163: invokestatic valueOf : (J)Ljava/lang/Long;
    //   166: iconst_0
    //   167: swap
    //   168: aastore
    //   169: invokevirtual o : ([Ljava/lang/Object;)V
    //   172: return
  }
  
  private static void lambda$new$1(lu paramlu) {
    long l = a ^ 0x7B1F1F36CE39L;
    String str = jt.S();
    try {
      if (str == null)
        try {
          if (paramlu.g(new Object[0]) instanceof net.minecraft.class_2761) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    paramlu.Z(new Object[0]);
  }
  
  private void lambda$new$0(b6 paramb6) {
    b9.c.field_1687.method_8435(this.D.z().longValue());
  }
  
  static {
    long l = a ^ 0x2EADB83B8753L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "$\020ÿgzeªJ\001TÀÝ´&¤z4L!5\020RMÅ{.ÑÛ¼só\007\016ìj0J\036øÂ§ÒèÔ\005S\016>-z×³\rF«dåJ\024\024[ºH=§Â_³=É\001\037\035ßG 6WÞ").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x7DD7;
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
        throw new RuntimeException("wtf/opal/j3", exception);
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
    //   66: ldc_w 'wtf/opal/j3'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */