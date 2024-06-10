package wtf.opal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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

public final class xb extends x6 {
  private static final long b = on.a(-5569723191422142842L, 5801618156045860630L, MethodHandles.lookup().lookupClass()).a(187812548599956L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  public xb(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xb.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 29966884953514
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: lload_3
    //   16: sipush #6043
    //   19: ldc2_w 1872047858554356348
    //   22: lload_1
    //   23: lxor
    //   24: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   29: sipush #20662
    //   32: ldc2_w 2460981572483908950
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   42: iconst_1
    //   43: anewarray java/lang/String
    //   46: dup
    //   47: iconst_0
    //   48: ldc 't'
    //   50: aastore
    //   51: invokespecial <init> : (JLjava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
    //   54: return
  }
  
  protected void Z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast com/mojang/brigadier/builder/LiteralArgumentBuilder
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_3
    //   18: pop
    //   19: aload_2
    //   20: sipush #25534
    //   23: ldc2_w 968286739704076428
    //   26: lload_3
    //   27: lxor
    //   28: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   33: iconst_0
    //   34: anewarray java/lang/Object
    //   37: invokestatic i : ([Ljava/lang/Object;)Lwtf/opal/d_;
    //   40: iconst_2
    //   41: anewarray java/lang/Object
    //   44: dup_x1
    //   45: swap
    //   46: iconst_1
    //   47: swap
    //   48: aastore
    //   49: dup_x1
    //   50: swap
    //   51: iconst_0
    //   52: swap
    //   53: aastore
    //   54: invokestatic Y : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
    //   57: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   62: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   65: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   68: pop
    //   69: return
  }
  
  private static int lambda$onCommand$0(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xb.b : J
    //   3: ldc2_w 46261178468088
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 26206193641722
    //   13: lxor
    //   14: lstore_3
    //   15: dup2
    //   16: ldc2_w 95656259516767
    //   19: lxor
    //   20: dup2
    //   21: bipush #48
    //   23: lushr
    //   24: l2i
    //   25: istore #5
    //   27: dup2
    //   28: bipush #16
    //   30: lshl
    //   31: bipush #48
    //   33: lushr
    //   34: l2i
    //   35: istore #6
    //   37: dup2
    //   38: bipush #32
    //   40: lshl
    //   41: bipush #32
    //   43: lushr
    //   44: l2i
    //   45: istore #7
    //   47: pop2
    //   48: pop2
    //   49: aload_0
    //   50: sipush #21030
    //   53: ldc2_w 2633591293318098532
    //   56: lload_1
    //   57: lxor
    //   58: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   63: ldc wtf/opal/d
    //   65: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   68: checkcast wtf/opal/d
    //   71: astore #8
    //   73: aload #8
    //   75: iload #5
    //   77: i2s
    //   78: iload #6
    //   80: i2s
    //   81: iload #7
    //   83: iconst_3
    //   84: anewarray java/lang/Object
    //   87: dup_x1
    //   88: swap
    //   89: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   92: iconst_2
    //   93: swap
    //   94: aastore
    //   95: dup_x1
    //   96: swap
    //   97: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   100: iconst_1
    //   101: swap
    //   102: aastore
    //   103: dup_x1
    //   104: swap
    //   105: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   108: iconst_0
    //   109: swap
    //   110: aastore
    //   111: invokevirtual D : ([Ljava/lang/Object;)V
    //   114: aload #8
    //   116: iconst_0
    //   117: anewarray java/lang/Object
    //   120: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   123: aload #8
    //   125: iconst_0
    //   126: anewarray java/lang/Object
    //   129: invokevirtual D : ([Ljava/lang/Object;)Z
    //   132: ifeq -> 155
    //   135: sipush #24306
    //   138: ldc2_w 3359303956031070899
    //   141: lload_1
    //   142: lxor
    //   143: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   148: goto -> 168
    //   151: invokestatic a : (Lcom/mojang/brigadier/exceptions/CommandSyntaxException;)Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
    //   154: athrow
    //   155: sipush #10480
    //   158: ldc2_w 7582506034841982134
    //   161: lload_1
    //   162: lxor
    //   163: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   168: sipush #18493
    //   171: ldc2_w 306601797317653625
    //   174: lload_1
    //   175: lxor
    //   176: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   181: swap
    //   182: ldc '!'
    //   184: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   189: lload_3
    //   190: dup2_x1
    //   191: pop2
    //   192: iconst_2
    //   193: anewarray java/lang/Object
    //   196: dup_x1
    //   197: swap
    //   198: iconst_1
    //   199: swap
    //   200: aastore
    //   201: dup_x2
    //   202: dup_x2
    //   203: pop
    //   204: invokestatic valueOf : (J)Ljava/lang/Long;
    //   207: iconst_0
    //   208: swap
    //   209: aastore
    //   210: invokestatic g : ([Ljava/lang/Object;)V
    //   213: iconst_1
    //   214: ireturn
    // Exception table:
    //   from	to	target	type
    //   73	151	151	com/mojang/brigadier/exceptions/CommandSyntaxException
  }
  
  static {
    long l = b ^ 0x61FA7355835FL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "\\T:\004-þÒF\031r¨ÃJF¾;\020[o³ý5V?VnvZ\000I[@Ê\031o¸B«Ù\035ûÕæý¨j\r9\0254Á½\037>X¶)º\024YíÌ²AsÈÛ.\032Mã§ùé\024çRÚãO\bÑu­ª\020MÆ°¢,ÜÏ\004öÛªºÙZ Ô\t\030åüxé\032¶îÁ\017ù\b`[AB\001UxÔ{í4ª¹").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static CommandSyntaxException a(CommandSyntaxException paramCommandSyntaxException) {
    return paramCommandSyntaxException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x3680;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])e.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xb", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = c[i].getBytes("ISO-8859-1");
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
    //   66: ldc_w 'wtf/opal/xb'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */