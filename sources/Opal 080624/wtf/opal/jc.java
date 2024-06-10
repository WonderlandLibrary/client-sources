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
import net.minecraft.class_2350;
import net.minecraft.class_2596;
import net.minecraft.class_2708;
import net.minecraft.class_2828;
import wtf.opal.mixin.PlayerMoveC2SPacketAccessor;

public final class jc extends d {
  private int D;
  
  private final gm<b6> o;
  
  private final gm<lu> U;
  
  private final gm<lb> k;
  
  private final gm<p> Y;
  
  private static final long a = on.a(-1694509246179735185L, -808812589399072573L, MethodHandles.lookup().lookupClass()).a(102948566337419L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long g;
  
  public jc(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jc.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 42893691367395
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: sipush #13849
    //   18: ldc2_w 8481776361375620794
    //   21: lload_1
    //   22: lxor
    //   23: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   28: lload_3
    //   29: sipush #9217
    //   32: ldc2_w 2768528583230344355
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   42: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   45: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   48: aload_0
    //   49: aload_0
    //   50: <illegal opcode> H : (Lwtf/opal/jc;)Lwtf/opal/gm;
    //   55: putfield o : Lwtf/opal/gm;
    //   58: aload_0
    //   59: aload_0
    //   60: <illegal opcode> H : (Lwtf/opal/jc;)Lwtf/opal/gm;
    //   65: putfield U : Lwtf/opal/gm;
    //   68: aload_0
    //   69: <illegal opcode> H : ()Lwtf/opal/gm;
    //   74: putfield k : Lwtf/opal/gm;
    //   77: aload_0
    //   78: <illegal opcode> H : ()Lwtf/opal/gm;
    //   83: putfield Y : Lwtf/opal/gm;
    //   86: return
  }
  
  protected void K(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    new Object[1];
    super.K(new Object[] { Long.valueOf(l2) });
  }
  
  private static void lambda$new$3(p paramp) {}
  
  private static void lambda$new$2(lb paramlb) {
    long l = a ^ 0x66AED3ED074EL;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    String str = jm.P();
    try {
      if (str == null)
        try {
          if (class_2596 instanceof class_2828) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_2828 class_2828 = (class_2828)class_2596;
    PlayerMoveC2SPacketAccessor playerMoveC2SPacketAccessor = (PlayerMoveC2SPacketAccessor)class_2828;
  }
  
  private void lambda$new$1(lu paramlu) {
    long l = a ^ 0x429CAF9E240CL;
    class_2596 class_2596 = paramlu.g(new Object[0]);
    String str = jm.P();
    try {
      if (str == null)
        try {
          if (class_2596 instanceof class_2708) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_2708 class_2708 = (class_2708)class_2596;
    paramlu.Z(new Object[0]);
    b9.c.method_1562().method_48296().method_10743((class_2596)new class_2828.class_2830(class_2708.method_11734(), class_2708.method_11735(), class_2708.method_11738(), class_2708.method_11736(), class_2708.method_11739(), false));
    b9.c.field_1724.method_30634(class_2708.method_11734(), class_2708.method_11735(), class_2708.method_11738());
    b9.c.field_1724.method_18799(b9.c.field_1724.method_18798().method_38499(class_2350.class_2351.field_11052, 0.41999998688697815D));
    this.D = 0;
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/jc.a : J
    //   3: ldc2_w 28116977080030
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic P : ()Ljava/lang/String;
    //   11: astore #4
    //   13: aload_1
    //   14: iconst_0
    //   15: anewarray java/lang/Object
    //   18: invokevirtual W : ([Ljava/lang/Object;)Z
    //   21: aload #4
    //   23: ifnonnull -> 58
    //   26: ifne -> 37
    //   29: goto -> 36
    //   32: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   35: athrow
    //   36: return
    //   37: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   40: getfield field_1724 : Lnet/minecraft/class_746;
    //   43: aload #4
    //   45: ifnonnull -> 79
    //   48: invokevirtual method_24828 : ()Z
    //   51: goto -> 58
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: ifeq -> 87
    //   61: aload_0
    //   62: iconst_0
    //   63: putfield D : I
    //   66: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   69: getfield field_1724 : Lnet/minecraft/class_746;
    //   72: goto -> 79
    //   75: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   78: athrow
    //   79: invokevirtual method_6043 : ()V
    //   82: aload #4
    //   84: ifnull -> 104
    //   87: aload_0
    //   88: dup
    //   89: getfield D : I
    //   92: iconst_1
    //   93: iadd
    //   94: putfield D : I
    //   97: goto -> 104
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: aload_0
    //   105: getfield D : I
    //   108: getstatic wtf/opal/jc.g : J
    //   111: l2i
    //   112: if_icmple -> 147
    //   115: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   118: getfield field_1724 : Lnet/minecraft/class_746;
    //   121: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   124: getfield field_1724 : Lnet/minecraft/class_746;
    //   127: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   130: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   133: dconst_0
    //   134: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   137: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   140: goto -> 147
    //   143: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   146: athrow
    //   147: return
    // Exception table:
    //   from	to	target	type
    //   13	29	32	wtf/opal/x5
    //   37	51	54	wtf/opal/x5
    //   58	72	75	wtf/opal/x5
    //   79	97	100	wtf/opal/x5
    //   104	140	143	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x61894E78C9F7L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "ä×¤\020Þ@EÞÙJÕ\000@K!úY\035ý*\001PÉ0´\017ö+8k\n4îqRquá<©7\036*\024Ï*¤ÔÞå\021tÁãñ£\016àº\036_Äµò³måMK¦¸").length();
    byte b2 = 16;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1D29;
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
        throw new RuntimeException("wtf/opal/jc", exception);
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
    //   66: ldc_w 'wtf/opal/jc'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */