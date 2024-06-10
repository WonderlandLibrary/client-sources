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

public final class xo extends x6 {
  private static d[] N;
  
  private static final long b = on.a(-8777671216153262100L, 1627338730182597403L, MethodHandles.lookup().lookupClass()).a(166100164186505L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  public xo(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xo.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 118759806627095
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: invokestatic V : ()[Lwtf/opal/d;
    //   17: aload_0
    //   18: lload_3
    //   19: sipush #31085
    //   22: ldc2_w 3325419978213649465
    //   25: lload_1
    //   26: lxor
    //   27: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   32: sipush #478
    //   35: ldc2_w 5540381244722123915
    //   38: lload_1
    //   39: lxor
    //   40: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   45: iconst_1
    //   46: anewarray java/lang/String
    //   49: dup
    //   50: iconst_0
    //   51: ldc 'v'
    //   53: aastore
    //   54: invokespecial <init> : (JLjava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
    //   57: astore #5
    //   59: invokestatic D : ()[Lwtf/opal/d;
    //   62: ifnull -> 79
    //   65: iconst_5
    //   66: anewarray wtf/opal/d
    //   69: invokestatic g : ([Lwtf/opal/d;)V
    //   72: goto -> 79
    //   75: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   78: athrow
    //   79: return
    // Exception table:
    //   from	to	target	type
    //   59	72	75	wtf/opal/x5
  }
  
  protected void Z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast com/mojang/brigadier/builder/LiteralArgumentBuilder
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: aload #4
    //   22: sipush #5834
    //   25: ldc2_w 6167221065557073393
    //   28: lload_2
    //   29: lxor
    //   30: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   35: invokestatic floatArg : ()Lcom/mojang/brigadier/arguments/FloatArgumentType;
    //   38: iconst_2
    //   39: anewarray java/lang/Object
    //   42: dup_x1
    //   43: swap
    //   44: iconst_1
    //   45: swap
    //   46: aastore
    //   47: dup_x1
    //   48: swap
    //   49: iconst_0
    //   50: swap
    //   51: aastore
    //   52: invokestatic Y : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
    //   55: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   60: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   63: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   66: pop
    //   67: return
  }
  
  private static int lambda$onCommand$0(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xo.b : J
    //   3: ldc2_w 122655130168496
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 81970685033980
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: aload_0
    //   17: sipush #7383
    //   20: ldc2_w 5036851780319348122
    //   23: lload_1
    //   24: lxor
    //   25: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   30: invokestatic getFloat : (Lcom/mojang/brigadier/context/CommandContext;Ljava/lang/String;)F
    //   33: f2d
    //   34: dstore #5
    //   36: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   39: getfield field_1724 : Lnet/minecraft/class_746;
    //   42: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   45: getfield field_1724 : Lnet/minecraft/class_746;
    //   48: invokevirtual method_23317 : ()D
    //   51: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   54: getfield field_1724 : Lnet/minecraft/class_746;
    //   57: invokevirtual method_23318 : ()D
    //   60: dload #5
    //   62: dadd
    //   63: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   66: getfield field_1724 : Lnet/minecraft/class_746;
    //   69: invokevirtual method_23321 : ()D
    //   72: invokevirtual method_5814 : (DDD)V
    //   75: dload #5
    //   77: dload #5
    //   79: dconst_1
    //   80: dcmpl
    //   81: ifne -> 93
    //   84: ldc ''
    //   86: goto -> 95
    //   89: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   92: athrow
    //   93: ldc 's'
    //   95: astore #7
    //   97: dstore #8
    //   99: sipush #10560
    //   102: ldc2_w 5728870006817337353
    //   105: lload_1
    //   106: lxor
    //   107: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   112: dload #8
    //   114: sipush #31536
    //   117: ldc2_w 3912053840396640888
    //   120: lload_1
    //   121: lxor
    //   122: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   127: aload #7
    //   129: ldc '!'
    //   131: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   136: lload_3
    //   137: dup2_x1
    //   138: pop2
    //   139: iconst_2
    //   140: anewarray java/lang/Object
    //   143: dup_x1
    //   144: swap
    //   145: iconst_1
    //   146: swap
    //   147: aastore
    //   148: dup_x2
    //   149: dup_x2
    //   150: pop
    //   151: invokestatic valueOf : (J)Ljava/lang/Long;
    //   154: iconst_0
    //   155: swap
    //   156: aastore
    //   157: invokestatic g : ([Ljava/lang/Object;)V
    //   160: iconst_1
    //   161: ireturn
    // Exception table:
    //   from	to	target	type
    //   36	89	89	com/mojang/brigadier/exceptions/CommandSyntaxException
  }
  
  public static void g(d[] paramArrayOfd) {
    N = paramArrayOfd;
  }
  
  public static d[] V() {
    return N;
  }
  
  static {
    long l = b ^ 0x6C7645C2B0BEL;
    g(new d[1]);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[6];
    boolean bool = false;
    String str;
    int i = (str = "EØïfYËù¬tòäqu\002xÚ\032/c÷Ý\"\035\\È\007ÒS ñ2\0023ûÏú\027é\035%2\003`Óaê«\017[\r}y#á\0262Ì8v¬bCz[ºá­\034\f¿¾onÉv¸Æ\025ÐÛéÕ\004\fF\027Ö\035>$Óâl@Dæz\016c\004ÇCu´ç9Ö\020{¢Ëöf==ê^@b").length();
    byte b2 = 32;
    byte b = -1;
    while (true);
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4688;
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
        throw new RuntimeException("wtf/opal/xo", exception);
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
    //   66: ldc_w 'wtf/opal/xo'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */