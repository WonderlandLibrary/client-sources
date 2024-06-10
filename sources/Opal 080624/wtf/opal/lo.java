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

public final class lo {
  private final HashMap<String, dq> j = new HashMap<>();
  
  private static final long a = on.a(5779448847252502994L, 2381645717378758934L, MethodHandles.lookup().lookupClass()).a(246942782441715L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public dq l(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/lo.a : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: lload_3
    //   26: dup2
    //   27: ldc2_w 7819742747984
    //   30: lxor
    //   31: lstore #5
    //   33: pop2
    //   34: invokestatic k : ()I
    //   37: istore #7
    //   39: aload_0
    //   40: getfield j : Ljava/util/HashMap;
    //   43: iload #7
    //   45: ifeq -> 112
    //   48: aload_2
    //   49: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   52: ifeq -> 78
    //   55: goto -> 62
    //   58: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   61: athrow
    //   62: aload_0
    //   63: getfield j : Ljava/util/HashMap;
    //   66: aload_2
    //   67: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   70: checkcast wtf/opal/dq
    //   73: areturn
    //   74: invokestatic a : (Ljava/io/IOException;)Ljava/io/IOException;
    //   77: athrow
    //   78: invokestatic getInstance : ()Lnet/fabricmc/loader/api/FabricLoader;
    //   81: sipush #6290
    //   84: ldc2_w 442573852049024511
    //   87: lload_3
    //   88: lxor
    //   89: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   94: invokeinterface getModContainer : (Ljava/lang/String;)Ljava/util/Optional;
    //   99: aload_2
    //   100: <illegal opcode> apply : (Ljava/lang/String;)Ljava/util/function/Function;
    //   105: invokevirtual flatMap : (Ljava/util/function/Function;)Ljava/util/Optional;
    //   108: aconst_null
    //   109: invokevirtual orElse : (Ljava/lang/Object;)Ljava/lang/Object;
    //   112: checkcast java/nio/file/Path
    //   115: astore #8
    //   117: aload_0
    //   118: getfield j : Ljava/util/HashMap;
    //   121: aload_2
    //   122: new wtf/opal/dq
    //   125: dup
    //   126: aload #8
    //   128: iconst_0
    //   129: anewarray java/nio/file/OpenOption
    //   132: invokestatic newInputStream : (Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;
    //   135: lload #5
    //   137: dup2_x1
    //   138: pop2
    //   139: invokespecial <init> : (JLjava/io/InputStream;)V
    //   142: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   145: pop
    //   146: aload_0
    //   147: getfield j : Ljava/util/HashMap;
    //   150: aload_2
    //   151: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   154: checkcast wtf/opal/dq
    //   157: areturn
    //   158: astore #9
    //   160: aload #9
    //   162: invokevirtual printStackTrace : ()V
    //   165: aconst_null
    //   166: areturn
    // Exception table:
    //   from	to	target	type
    //   39	55	58	java/io/IOException
    //   48	74	74	java/io/IOException
    //   117	157	158	java/io/IOException
  }
  
  private static Optional lambda$getImage$0(String paramString, ModContainer paramModContainer) {
    // Byte code:
    //   0: getstatic wtf/opal/lo.a : J
    //   3: ldc2_w 32837593047627
    //   6: lxor
    //   7: lstore_2
    //   8: aload_1
    //   9: aload_0
    //   10: sipush #27429
    //   13: ldc2_w 6896589456107176748
    //   16: lload_2
    //   17: lxor
    //   18: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   23: swap
    //   24: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   29: invokeinterface findPath : (Ljava/lang/String;)Ljava/util/Optional;
    //   34: areturn
  }
  
  static {
    long l = a ^ 0x4CD19DADF806L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "c2eJT¸ê.ðè{Õ( øùLø¥¶­5ã8Úwó\002E\023Îýèp¸L'¾°").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x7B08;
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
        throw new RuntimeException("wtf/opal/lo", exception);
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
    //   65: ldc_w 'wtf/opal/lo'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */