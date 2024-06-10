package wtf.opal;

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

public final class kp {
  private int p;
  
  private int r;
  
  private static final long a = on.a(1157120937067162112L, -8803976376303508665L, MethodHandles.lookup().lookupClass()).a(163783378475766L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public kp(String paramString1, long paramLong, String paramString2) {
    // Byte code:
    //   0: getstatic wtf/opal/kp.a : J
    //   3: lload_2
    //   4: lxor
    //   5: lstore_2
    //   6: lload_2
    //   7: dup2
    //   8: ldc2_w 81292077002615
    //   11: lxor
    //   12: lstore #5
    //   14: pop2
    //   15: aload_0
    //   16: invokespecial <init> : ()V
    //   19: invokestatic getInstance : ()Lnet/fabricmc/loader/api/FabricLoader;
    //   22: sipush #5757
    //   25: ldc2_w 1447800425263697906
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   35: invokeinterface getModContainer : (Ljava/lang/String;)Ljava/util/Optional;
    //   40: aload_1
    //   41: <illegal opcode> apply : (Ljava/lang/String;)Ljava/util/function/Function;
    //   46: invokevirtual flatMap : (Ljava/util/function/Function;)Ljava/util/Optional;
    //   49: aconst_null
    //   50: invokevirtual orElse : (Ljava/lang/Object;)Ljava/lang/Object;
    //   53: checkcast java/nio/file/Path
    //   56: astore #7
    //   58: invokestatic getInstance : ()Lnet/fabricmc/loader/api/FabricLoader;
    //   61: sipush #12471
    //   64: ldc2_w 3955474621145098553
    //   67: lload_2
    //   68: lxor
    //   69: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   74: invokeinterface getModContainer : (Ljava/lang/String;)Ljava/util/Optional;
    //   79: aload #4
    //   81: <illegal opcode> apply : (Ljava/lang/String;)Ljava/util/function/Function;
    //   86: invokevirtual flatMap : (Ljava/util/function/Function;)Ljava/util/Optional;
    //   89: aconst_null
    //   90: invokevirtual orElse : (Ljava/lang/Object;)Ljava/lang/Object;
    //   93: checkcast java/nio/file/Path
    //   96: astore #8
    //   98: aload_0
    //   99: aload #8
    //   101: iconst_0
    //   102: anewarray java/nio/file/OpenOption
    //   105: invokestatic newInputStream : (Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;
    //   108: sipush #10277
    //   111: ldc2_w 6568683642258605117
    //   114: lload_2
    //   115: lxor
    //   116: <illegal opcode> s : (IJ)I
    //   121: lload #5
    //   123: iconst_3
    //   124: anewarray java/lang/Object
    //   127: dup_x2
    //   128: dup_x2
    //   129: pop
    //   130: invokestatic valueOf : (J)Ljava/lang/Long;
    //   133: iconst_2
    //   134: swap
    //   135: aastore
    //   136: dup_x1
    //   137: swap
    //   138: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   141: iconst_1
    //   142: swap
    //   143: aastore
    //   144: dup_x1
    //   145: swap
    //   146: iconst_0
    //   147: swap
    //   148: aastore
    //   149: invokevirtual Z : ([Ljava/lang/Object;)I
    //   152: istore #9
    //   154: aload_0
    //   155: aload #7
    //   157: iconst_0
    //   158: anewarray java/nio/file/OpenOption
    //   161: invokestatic newInputStream : (Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;
    //   164: sipush #13719
    //   167: ldc2_w 5833462396999457165
    //   170: lload_2
    //   171: lxor
    //   172: <illegal opcode> s : (IJ)I
    //   177: lload #5
    //   179: iconst_3
    //   180: anewarray java/lang/Object
    //   183: dup_x2
    //   184: dup_x2
    //   185: pop
    //   186: invokestatic valueOf : (J)Ljava/lang/Long;
    //   189: iconst_2
    //   190: swap
    //   191: aastore
    //   192: dup_x1
    //   193: swap
    //   194: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   197: iconst_1
    //   198: swap
    //   199: aastore
    //   200: dup_x1
    //   201: swap
    //   202: iconst_0
    //   203: swap
    //   204: aastore
    //   205: invokevirtual Z : ([Ljava/lang/Object;)I
    //   208: istore #10
    //   210: aload_0
    //   211: invokestatic glCreateProgram : ()I
    //   214: putfield p : I
    //   217: aload_0
    //   218: getfield p : I
    //   221: iload #9
    //   223: invokestatic glAttachShader : (II)V
    //   226: aload_0
    //   227: getfield p : I
    //   230: iload #10
    //   232: invokestatic glAttachShader : (II)V
    //   235: aload_0
    //   236: getfield p : I
    //   239: invokestatic glLinkProgram : (I)V
    //   242: aload_0
    //   243: getfield p : I
    //   246: invokestatic glValidateProgram : (I)V
    //   249: aload_0
    //   250: invokestatic glGenTextures : ()I
    //   253: putfield r : I
    //   256: goto -> 266
    //   259: astore #9
    //   261: aload #9
    //   263: invokevirtual printStackTrace : ()V
    //   266: return
    // Exception table:
    //   from	to	target	type
    //   98	256	259	java/io/IOException
  }
  
  public int R(Object[] paramArrayOfObject) {
    return this.r;
  }
  
  private int Z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/io/InputStream
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Integer
    //   15: invokevirtual intValue : ()I
    //   18: istore #5
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/Long
    //   26: invokevirtual longValue : ()J
    //   29: lstore_2
    //   30: pop
    //   31: getstatic wtf/opal/kp.a : J
    //   34: lload_2
    //   35: lxor
    //   36: lstore_2
    //   37: lload_2
    //   38: dup2
    //   39: ldc2_w 44744628246547
    //   42: lxor
    //   43: lstore #6
    //   45: pop2
    //   46: invokestatic h : ()Z
    //   49: iload #5
    //   51: invokestatic glCreateShader : (I)I
    //   54: istore #9
    //   56: iload #9
    //   58: lload #6
    //   60: aload #4
    //   62: iconst_2
    //   63: anewarray java/lang/Object
    //   66: dup_x1
    //   67: swap
    //   68: iconst_1
    //   69: swap
    //   70: aastore
    //   71: dup_x2
    //   72: dup_x2
    //   73: pop
    //   74: invokestatic valueOf : (J)Ljava/lang/Long;
    //   77: iconst_0
    //   78: swap
    //   79: aastore
    //   80: invokestatic o : ([Ljava/lang/Object;)Ljava/lang/String;
    //   83: invokestatic glShaderSource : (ILjava/lang/CharSequence;)V
    //   86: istore #8
    //   88: iload #9
    //   90: invokestatic glCompileShader : (I)V
    //   93: iload #9
    //   95: sipush #25140
    //   98: ldc2_w 7319143528887549245
    //   101: lload_2
    //   102: lxor
    //   103: <illegal opcode> s : (IJ)I
    //   108: invokestatic glGetShaderi : (II)I
    //   111: iload #8
    //   113: ifeq -> 168
    //   116: ifne -> 166
    //   119: goto -> 126
    //   122: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   125: athrow
    //   126: new java/lang/IllegalStateException
    //   129: dup
    //   130: sipush #16156
    //   133: ldc2_w 6821656254077851011
    //   136: lload_2
    //   137: lxor
    //   138: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   143: iconst_1
    //   144: anewarray java/lang/Object
    //   147: dup
    //   148: iconst_0
    //   149: iload #5
    //   151: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   154: aastore
    //   155: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   158: invokespecial <init> : (Ljava/lang/String;)V
    //   161: athrow
    //   162: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   165: athrow
    //   166: iload #9
    //   168: ireturn
    // Exception table:
    //   from	to	target	type
    //   88	119	122	java/lang/IllegalStateException
    //   116	162	162	java/lang/IllegalStateException
  }
  
  private static Optional lambda$new$1(String paramString, ModContainer paramModContainer) {
    return paramModContainer.findPath(paramString);
  }
  
  private static Optional lambda$new$0(String paramString, ModContainer paramModContainer) {
    return paramModContainer.findPath(paramString);
  }
  
  static {
    long l = a ^ 0x65E8BDEB1A63L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "÷øSÂAHx«Å¶3)°Uaf\020\023ª!ltB*!â©¤§ÇÚ8y÷\0028(K#ÃÄêì,Ûl¹9\"zg\035\035=TÊ´ñ¬î{\få'\030x&-vü\"Úä¬\036\013©ª\022jv¡").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static IllegalStateException a(IllegalStateException paramIllegalStateException) {
    return paramIllegalStateException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x71A1;
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
        throw new RuntimeException("wtf/opal/kp", exception);
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
    //   65: ldc_w 'wtf/opal/kp'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x434;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])g.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/kp", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
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
    //   65: ldc_w 'wtf/opal/kp'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\kp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */