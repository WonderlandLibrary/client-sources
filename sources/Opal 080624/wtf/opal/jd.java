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

public final class jd extends d {
  private static final long a = on.a(7137817043313592538L, 3532324274838346735L, MethodHandles.lookup().lookupClass()).a(36085669088884L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public jd(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jd.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 20849169681456
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: sipush #25480
    //   18: ldc2_w 8506472485017215109
    //   21: lload_1
    //   22: lxor
    //   23: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   28: lload_3
    //   29: sipush #27708
    //   32: ldc2_w 1571454801556107056
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   42: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   45: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   48: return
  }
  
  static {
    long l = a ^ 0x1C620097EAB3L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "å&Òù¬ðhóð\b\007¦È×D±@¼>­¥ïm[EÞ\017eÅé­6\nó\033 _u\002k¹\033yEÇÇ\\\"\005è\027ïõ.*\005´­ÁdÎsÒ/Vñþ.AE\004\013æD").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1554;
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
        throw new RuntimeException("wtf/opal/jd", exception);
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
    //   10: ldc
    //   12: ldc [Ljava/lang/Object;
    //   14: aload_2
    //   15: invokevirtual parameterCount : ()I
    //   18: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   21: iconst_0
    //   22: iconst_3
    //   23: anewarray java/lang/Object
    //   26: dup
    //   27: iconst_0
    //   28: aload_0
    //   29: aastore
    //   30: dup
    //   31: iconst_1
    //   32: aload_3
    //   33: aastore
    //   34: dup
    //   35: iconst_2
    //   36: aload_1
    //   37: aastore
    //   38: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   41: aload_2
    //   42: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   45: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   48: goto -> 101
    //   51: astore #4
    //   53: new java/lang/RuntimeException
    //   56: dup
    //   57: new java/lang/StringBuilder
    //   60: dup
    //   61: invokespecial <init> : ()V
    //   64: ldc 'wtf/opal/jd'
    //   66: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: ldc_w ' : '
    //   72: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: aload_1
    //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: ldc_w ' : '
    //   82: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   85: aload_2
    //   86: invokevirtual toString : ()Ljava/lang/String;
    //   89: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: invokevirtual toString : ()Ljava/lang/String;
    //   95: aload #4
    //   97: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   100: athrow
    //   101: aload_3
    //   102: areturn
    // Exception table:
    //   from	to	target	type
    //   9	48	51	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jd.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */