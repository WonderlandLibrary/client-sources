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

public final class xu extends x6 {
  private static final long b = on.a(3710228818309212437L, -8921774666656305503L, MethodHandles.lookup().lookupClass()).a(93196219171759L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  public xu(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xu.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 94022515807493
    //   11: lxor
    //   12: dup2
    //   13: bipush #48
    //   15: lushr
    //   16: l2i
    //   17: istore_3
    //   18: dup2
    //   19: bipush #16
    //   21: lshl
    //   22: bipush #48
    //   24: lushr
    //   25: l2i
    //   26: istore #4
    //   28: dup2
    //   29: bipush #32
    //   31: lshl
    //   32: bipush #32
    //   34: lushr
    //   35: l2i
    //   36: istore #5
    //   38: pop2
    //   39: pop2
    //   40: aload_0
    //   41: sipush #29088
    //   44: ldc2_w 6701549280648477221
    //   47: lload_1
    //   48: lxor
    //   49: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   54: iload_3
    //   55: i2c
    //   56: sipush #12395
    //   59: ldc2_w 6511020605327735789
    //   62: lload_1
    //   63: lxor
    //   64: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   69: iload #4
    //   71: i2c
    //   72: iload #5
    //   74: invokespecial <init> : (Ljava/lang/String;CLjava/lang/String;CI)V
    //   77: return
  }
  
  protected void Z(Object[] paramArrayOfObject) {
    LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)paramArrayOfObject[0];
    long l = ((Long)paramArrayOfObject[1]).longValue();
    literalArgumentBuilder.executes(xu::lambda$onCommand$0);
  }
  
  private static int lambda$onCommand$0(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xu.b : J
    //   3: ldc2_w 51538513783357
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 57902189738177
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   19: getfield field_1774 : Lnet/minecraft/class_309;
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: getfield field_1724 : Lnet/minecraft/class_746;
    //   28: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   31: invokeinterface method_54160 : ()Ljava/lang/String;
    //   36: invokevirtual method_1455 : (Ljava/lang/String;)V
    //   39: lload_3
    //   40: sipush #27628
    //   43: ldc2_w 6565694011657025789
    //   46: lload_1
    //   47: lxor
    //   48: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   53: iconst_2
    //   54: anewarray java/lang/Object
    //   57: dup_x1
    //   58: swap
    //   59: iconst_1
    //   60: swap
    //   61: aastore
    //   62: dup_x2
    //   63: dup_x2
    //   64: pop
    //   65: invokestatic valueOf : (J)Ljava/lang/Long;
    //   68: iconst_0
    //   69: swap
    //   70: aastore
    //   71: invokestatic g : ([Ljava/lang/Object;)V
    //   74: iconst_1
    //   75: ireturn
  }
  
  static {
    long l = b ^ 0xC0FDBF022CAL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "z6·|Anò¬@½%ÍüÄ\036Óª\024\001¾ÒÞÌ\002§cyÝN«8¶:ï¾$ Ãîº8@\005bIî&r\013ô/-» yZfÚIOG(\nX)#Ò¾l>.úLÉ3*Z¬¿'ª§@y°5§\020!æG'w×Kº#F®öw4").length();
    byte b2 = 48;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x9E8;
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
        throw new RuntimeException("wtf/opal/xu", exception);
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
    //   65: ldc_w 'wtf/opal/xu'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xu.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */