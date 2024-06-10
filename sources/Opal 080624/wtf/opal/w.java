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

public final class w extends d {
  private final gm<b6> f;
  
  private static final long a = on.a(2891899933615892838L, -3053874165557022973L, MethodHandles.lookup().lookupClass()).a(226286809099710L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map g = new HashMap<>(13);
  
  public w(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/w.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 107596493292390
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: sipush #6271
    //   18: ldc2_w 6489578311318888169
    //   21: lload_1
    //   22: lxor
    //   23: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   28: lload_3
    //   29: sipush #25763
    //   32: ldc2_w 1771936571409506868
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   42: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   45: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   48: aload_0
    //   49: <illegal opcode> H : ()Lwtf/opal/gm;
    //   54: putfield f : Lwtf/opal/gm;
    //   57: return
  }
  
  private static void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/w.a : J
    //   3: ldc2_w 67214922464525
    //   6: lxor
    //   7: lstore_1
    //   8: invokestatic P : ()Ljava/lang/String;
    //   11: astore_3
    //   12: aload_0
    //   13: iconst_0
    //   14: anewarray java/lang/Object
    //   17: invokevirtual W : ([Ljava/lang/Object;)Z
    //   20: aload_3
    //   21: ifnonnull -> 55
    //   24: ifne -> 35
    //   27: goto -> 34
    //   30: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   33: athrow
    //   34: return
    //   35: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   38: getfield field_1724 : Lnet/minecraft/class_746;
    //   41: aload_3
    //   42: ifnonnull -> 71
    //   45: invokevirtual method_52535 : ()Z
    //   48: goto -> 55
    //   51: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   54: athrow
    //   55: ifeq -> 123
    //   58: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   61: getfield field_1724 : Lnet/minecraft/class_746;
    //   64: goto -> 71
    //   67: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   70: athrow
    //   71: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   74: getfield field_1724 : Lnet/minecraft/class_746;
    //   77: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   80: invokevirtual method_10216 : ()D
    //   83: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   86: getfield field_1724 : Lnet/minecraft/class_746;
    //   89: getfield field_5976 : Z
    //   92: ifeq -> 105
    //   95: ldc2_w 0.1
    //   98: goto -> 108
    //   101: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: ldc2_w 0.005
    //   108: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   111: getfield field_1724 : Lnet/minecraft/class_746;
    //   114: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   117: invokevirtual method_10215 : ()D
    //   120: invokevirtual method_18800 : (DDD)V
    //   123: return
    // Exception table:
    //   from	to	target	type
    //   12	27	30	wtf/opal/x5
    //   35	48	51	wtf/opal/x5
    //   55	64	67	wtf/opal/x5
    //   71	101	101	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x281A271BABC2L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "Å­\005ÊS\027 W¬\024!n·öØóS\003ÞÀv?vÕCmÖÁ úûô\017çi¨^9£\020½O©\n²ÖV\\Ñ@").length();
    byte b2 = 48;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1F98;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])g.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/w", exception);
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
    //   66: ldc_w 'wtf/opal/w'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\w.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */