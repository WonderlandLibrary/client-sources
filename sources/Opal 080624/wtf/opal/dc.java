package wtf.opal;

import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.fabricmc.loader.api.ModContainer;

public final class dc {
  private final HashMap<String, u2> f = new HashMap<>();
  
  private static final long a = on.a(-5253643809460839217L, 5005668316099106773L, MethodHandles.lookup().lookupClass()).a(73530530085043L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public u2 W(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: getstatic wtf/opal/dc.a : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: lload_2
    //   27: dup2
    //   28: ldc2_w 15347065985400
    //   31: lxor
    //   32: lstore #5
    //   34: pop2
    //   35: invokestatic n : ()[Lwtf/opal/d;
    //   38: astore #7
    //   40: aload_0
    //   41: getfield f : Ljava/util/HashMap;
    //   44: aload #7
    //   46: ifnull -> 116
    //   49: aload #4
    //   51: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   54: ifeq -> 81
    //   57: goto -> 64
    //   60: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   63: athrow
    //   64: aload_0
    //   65: getfield f : Ljava/util/HashMap;
    //   68: aload #4
    //   70: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   73: checkcast wtf/opal/u2
    //   76: areturn
    //   77: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   80: athrow
    //   81: invokestatic getInstance : ()Lnet/fabricmc/loader/api/FabricLoader;
    //   84: sipush #31282
    //   87: ldc2_w 7986606692092420121
    //   90: lload_2
    //   91: lxor
    //   92: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   97: invokeinterface getModContainer : (Ljava/lang/String;)Ljava/util/Optional;
    //   102: aload #4
    //   104: <illegal opcode> apply : (Ljava/lang/String;)Ljava/util/function/Function;
    //   109: invokevirtual flatMap : (Ljava/util/function/Function;)Ljava/util/Optional;
    //   112: aconst_null
    //   113: invokevirtual orElse : (Ljava/lang/Object;)Ljava/lang/Object;
    //   116: checkcast java/nio/file/Path
    //   119: astore #8
    //   121: aload_0
    //   122: getfield f : Ljava/util/HashMap;
    //   125: aload #4
    //   127: new wtf/opal/u2
    //   130: dup
    //   131: lload #5
    //   133: aload #4
    //   135: aload #8
    //   137: iconst_0
    //   138: anewarray java/nio/file/OpenOption
    //   141: invokestatic newInputStream : (Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;
    //   144: invokespecial <init> : (JLjava/lang/String;Ljava/io/InputStream;)V
    //   147: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   150: pop
    //   151: aload_0
    //   152: getfield f : Ljava/util/HashMap;
    //   155: aload #4
    //   157: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   160: checkcast wtf/opal/u2
    //   163: areturn
    //   164: astore #9
    //   166: aload #9
    //   168: invokevirtual printStackTrace : ()V
    //   171: aconst_null
    //   172: areturn
    // Exception table:
    //   from	to	target	type
    //   40	57	60	java/io/IOException
    //   49	77	77	java/io/IOException
    //   121	163	164	java/io/IOException
  }
  
  private static Optional lambda$getFont$0(String paramString, ModContainer paramModContainer) {
    // Byte code:
    //   0: getstatic wtf/opal/dc.a : J
    //   3: ldc2_w 140373339143911
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 118993692956209
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: aload_1
    //   18: iconst_0
    //   19: anewarray java/lang/Object
    //   22: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   25: iconst_0
    //   26: anewarray java/lang/Object
    //   29: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   32: lload #4
    //   34: sipush #11053
    //   37: ldc2_w 3225666996655356481
    //   40: lload_2
    //   41: lxor
    //   42: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   47: iconst_2
    //   48: anewarray java/lang/Object
    //   51: dup_x1
    //   52: swap
    //   53: iconst_1
    //   54: swap
    //   55: aastore
    //   56: dup_x2
    //   57: dup_x2
    //   58: pop
    //   59: invokestatic valueOf : (J)Ljava/lang/Long;
    //   62: iconst_0
    //   63: swap
    //   64: aastore
    //   65: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   68: aload_0
    //   69: sipush #22618
    //   72: ldc2_w 8049979935234689335
    //   75: lload_2
    //   76: lxor
    //   77: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   82: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   87: invokeinterface findPath : (Ljava/lang/String;)Ljava/util/Optional;
    //   92: areturn
  }
  
  static {
    long l = a ^ 0x4255DA8D86L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "X\026oSãW¥\030ª¨$XS§¯\020-Þq»³\001÷ò!Í>Ê\030U\020þÝÐRÁµ\002âB=g,\024 ~").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static IOException a(IOException paramIOException) {
    return paramIOException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2DC1;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])d.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/dc", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      c[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return c[i];
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
    //   65: ldc_w 'wtf/opal/dc'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */