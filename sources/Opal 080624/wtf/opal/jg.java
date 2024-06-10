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
import net.minecraft.class_2960;

public final class jg extends d {
  private final ky<pb> J;
  
  private static final long a = on.a(-5279979772451643704L, -7939795190688399509L, MethodHandles.lookup().lookupClass()).a(112265011068737L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public jg(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jg.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 95584376891340
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 51937762580898
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 1383543587063
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #9126
    //   32: ldc2_w 1769056999648864326
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #15466
    //   47: ldc2_w 243032122220296077
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/ky
    //   67: dup
    //   68: sipush #27135
    //   71: ldc2_w 5380432860240503325
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   81: getstatic wtf/opal/pb.OPAL : Lwtf/opal/pb;
    //   84: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   87: putfield J : Lwtf/opal/ky;
    //   90: aload_0
    //   91: iconst_1
    //   92: anewarray wtf/opal/k3
    //   95: dup
    //   96: iconst_0
    //   97: aload_0
    //   98: getfield J : Lwtf/opal/ky;
    //   101: aastore
    //   102: lload #5
    //   104: dup2_x1
    //   105: pop2
    //   106: iconst_2
    //   107: anewarray java/lang/Object
    //   110: dup_x1
    //   111: swap
    //   112: iconst_1
    //   113: swap
    //   114: aastore
    //   115: dup_x2
    //   116: dup_x2
    //   117: pop
    //   118: invokestatic valueOf : (J)Ljava/lang/Long;
    //   121: iconst_0
    //   122: swap
    //   123: aastore
    //   124: invokevirtual o : ([Ljava/lang/Object;)V
    //   127: aload_0
    //   128: iconst_1
    //   129: lload_3
    //   130: iconst_2
    //   131: anewarray java/lang/Object
    //   134: dup_x2
    //   135: dup_x2
    //   136: pop
    //   137: invokestatic valueOf : (J)Ljava/lang/Long;
    //   140: iconst_1
    //   141: swap
    //   142: aastore
    //   143: dup_x1
    //   144: swap
    //   145: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   148: iconst_0
    //   149: swap
    //   150: aastore
    //   151: invokevirtual Q : ([Ljava/lang/Object;)V
    //   154: return
  }
  
  public class_2960 L(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/jg.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: new net/minecraft/class_2960
    //   21: dup
    //   22: aload_0
    //   23: getfield J : Lwtf/opal/ky;
    //   26: invokevirtual z : ()Ljava/lang/Object;
    //   29: checkcast wtf/opal/pb
    //   32: invokevirtual toString : ()Ljava/lang/String;
    //   35: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   38: sipush #14397
    //   41: ldc2_w 3795965752239140715
    //   44: lload_2
    //   45: lxor
    //   46: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   51: swap
    //   52: sipush #29712
    //   55: ldc2_w 1584225618306917188
    //   58: lload_2
    //   59: lxor
    //   60: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   65: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   70: invokespecial <init> : (Ljava/lang/String;)V
    //   73: areturn
  }
  
  static {
    long l = a ^ 0x14D7625DA214L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "Ýñþs\035c\024Å.#âÙa\020`-rvð¼¼|íÀ÷'\fá \002C_öM1Xÿ`V!/´Ú®HõÄ|ÕGû?Ã").length();
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
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x117D;
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
        throw new RuntimeException("wtf/opal/jg", exception);
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
    //   66: ldc_w 'wtf/opal/jg'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */