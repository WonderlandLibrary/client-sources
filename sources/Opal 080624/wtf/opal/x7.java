package wtf.opal;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
import net.minecraft.class_2561;
import net.minecraft.class_2568;
import net.minecraft.class_2583;

public final class x7 extends x6 {
  private static int[] q;
  
  private static final long b = on.a(-2611662928573229678L, 8450868020368360324L, MethodHandles.lookup().lookupClass()).a(52872743642743L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  public x7(int paramInt1, int paramInt2, short paramShort) {
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
    //   23: getstatic wtf/opal/x7.b : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 45140677570484
    //   35: lxor
    //   36: dup2
    //   37: bipush #48
    //   39: lushr
    //   40: l2i
    //   41: istore #6
    //   43: dup2
    //   44: bipush #16
    //   46: lshl
    //   47: bipush #48
    //   49: lushr
    //   50: l2i
    //   51: istore #7
    //   53: dup2
    //   54: bipush #32
    //   56: lshl
    //   57: bipush #32
    //   59: lushr
    //   60: l2i
    //   61: istore #8
    //   63: pop2
    //   64: pop2
    //   65: aload_0
    //   66: sipush #18998
    //   69: ldc2_w 7314864804697334873
    //   72: lload #4
    //   74: lxor
    //   75: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   80: iload #6
    //   82: i2c
    //   83: sipush #1557
    //   86: ldc2_w 5575669713188563067
    //   89: lload #4
    //   91: lxor
    //   92: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   97: iload #7
    //   99: i2c
    //   100: iload #8
    //   102: invokespecial <init> : (Ljava/lang/String;CLjava/lang/String;CI)V
    //   105: return
  }
  
  protected void Z(Object[] paramArrayOfObject) {
    LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)paramArrayOfObject[0];
    long l = ((Long)paramArrayOfObject[1]).longValue();
    literalArgumentBuilder.executes(x7::lambda$onCommand$2);
  }
  
  private static int lambda$onCommand$2(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/x7.b : J
    //   3: ldc2_w 45884681399180
    //   6: lxor
    //   7: lstore_1
    //   8: getstatic wtf/opal/p7.Y : Lwtf/opal/xf;
    //   11: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   14: sipush #27575
    //   17: ldc2_w 2357362478561861718
    //   20: lload_1
    //   21: lxor
    //   22: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   27: swap
    //   28: sipush #26408
    //   31: ldc2_w 3964120303013309646
    //   34: lload_1
    //   35: lxor
    //   36: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   41: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   46: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   49: astore_3
    //   50: ldc ''
    //   52: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   55: astore #4
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   64: iconst_0
    //   65: anewarray java/lang/Object
    //   68: invokevirtual F : ([Ljava/lang/Object;)Lwtf/opal/uw;
    //   71: iconst_0
    //   72: anewarray java/lang/Object
    //   75: invokevirtual p : ([Ljava/lang/Object;)Ljava/util/List;
    //   78: aload #4
    //   80: <illegal opcode> accept : (Lnet/minecraft/class_2561;)Ljava/util/function/Consumer;
    //   85: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   90: aload_3
    //   91: invokeinterface method_10855 : ()Ljava/util/List;
    //   96: aload #4
    //   98: invokeinterface add : (Ljava/lang/Object;)Z
    //   103: pop
    //   104: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   107: getfield field_1705 : Lnet/minecraft/class_329;
    //   110: invokevirtual method_1743 : ()Lnet/minecraft/class_338;
    //   113: aload_3
    //   114: invokevirtual method_1812 : (Lnet/minecraft/class_2561;)V
    //   117: iconst_1
    //   118: ireturn
  }
  
  private static void lambda$onCommand$1(class_2561 paramclass_2561, x6 paramx6) {
    // Byte code:
    //   0: getstatic wtf/opal/x7.b : J
    //   3: ldc2_w 66819896515527
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic H : ()[I
    //   11: sipush #1873
    //   14: ldc2_w 2766263845247614202
    //   17: lload_2
    //   18: lxor
    //   19: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   24: iconst_2
    //   25: anewarray java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: aload_1
    //   31: invokevirtual o : ()Ljava/lang/String;
    //   34: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   37: aastore
    //   38: dup
    //   39: iconst_1
    //   40: aload_1
    //   41: iconst_0
    //   42: anewarray java/lang/Object
    //   45: invokevirtual s : ([Ljava/lang/Object;)Ljava/lang/String;
    //   48: aastore
    //   49: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   52: astore #5
    //   54: astore #4
    //   56: aload_1
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokevirtual Q : ([Ljava/lang/Object;)Ljava/util/List;
    //   64: aload #4
    //   66: ifnonnull -> 212
    //   69: ifnull -> 199
    //   72: goto -> 79
    //   75: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   78: athrow
    //   79: aload_1
    //   80: iconst_0
    //   81: anewarray java/lang/Object
    //   84: invokevirtual Q : ([Ljava/lang/Object;)Ljava/util/List;
    //   87: invokeinterface isEmpty : ()Z
    //   92: aload #4
    //   94: ifnonnull -> 222
    //   97: goto -> 104
    //   100: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: ifne -> 199
    //   107: goto -> 114
    //   110: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: sipush #11203
    //   117: ldc2_w 3230574666629664874
    //   120: lload_2
    //   121: lxor
    //   122: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   127: iconst_1
    //   128: anewarray java/lang/Object
    //   131: dup
    //   132: iconst_0
    //   133: sipush #5454
    //   136: ldc2_w 1309344184631324390
    //   139: lload_2
    //   140: lxor
    //   141: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   146: aload_1
    //   147: iconst_0
    //   148: anewarray java/lang/Object
    //   151: invokevirtual Q : ([Ljava/lang/Object;)Ljava/util/List;
    //   154: invokestatic join : (Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;
    //   157: aastore
    //   158: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   161: astore #6
    //   163: aload #5
    //   165: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   168: aload #6
    //   170: <illegal opcode> apply : (Ljava/lang/String;)Ljava/util/function/UnaryOperator;
    //   175: invokevirtual method_27694 : (Ljava/util/function/UnaryOperator;)Lnet/minecraft/class_5250;
    //   178: astore #7
    //   180: aload_0
    //   181: invokeinterface method_10855 : ()Ljava/util/List;
    //   186: aload #7
    //   188: invokeinterface add : (Ljava/lang/Object;)Z
    //   193: pop
    //   194: aload #4
    //   196: ifnull -> 223
    //   199: aload_0
    //   200: invokeinterface method_10855 : ()Ljava/util/List;
    //   205: goto -> 212
    //   208: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: aload #5
    //   214: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   217: invokeinterface add : (Ljava/lang/Object;)Z
    //   222: pop
    //   223: return
    // Exception table:
    //   from	to	target	type
    //   56	72	75	wtf/opal/x5
    //   69	97	100	wtf/opal/x5
    //   79	107	110	wtf/opal/x5
    //   180	205	208	wtf/opal/x5
  }
  
  private static class_2583 lambda$onCommand$0(String paramString, class_2583 paramclass_2583) {
    return paramclass_2583.method_10949(new class_2568(class_2568.class_5247.field_24342, class_2561.method_43470(paramString)));
  }
  
  public static void A(int[] paramArrayOfint) {
    q = paramArrayOfint;
  }
  
  public static int[] H() {
    return q;
  }
  
  static {
    long l = b ^ 0x53F3EEED4334L;
    A(null);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "¡ÑÔ~\037~\nõW¼ýf»ª¬ÛÆoxtÆßÖ(\0277Ö>I\007¤\031©_T@06¾j\030L@¬¯,»ö±¬U\017ÁÃævCVó´v¾¹L\\7ø:¸´S(IE,]yzÓ\037×·\tÇÍ½(äÇèÝÍG\"·ø%\030íÛ/'Y¬\036W¡Ô\rÜ_\002+ýü6B²Í¸»¼>Á±\020O?\034\007>#¹½n1&B|6@êÀÐÈ®7]4Ê%MÿðÏ?n»âÓV\024:\027<?û­êÅ\t\030i0\037\005´ì¨¾\021¾/\r7Fº¢2N'ËÛ_ö\026æ§í").length();
    byte b2 = 40;
    byte b = -1;
    while (true);
  }
  
  private static x5 b(x5 paramx5) {
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x16B4;
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
        throw new RuntimeException("wtf/opal/x7", exception);
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
    //   66: ldc_w 'wtf/opal/x7'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\x7.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */