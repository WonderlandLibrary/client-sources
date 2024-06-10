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

public final class xa extends x6 {
  private static final long b = on.a(-5209719093502600386L, -784136195413289915L, MethodHandles.lookup().lookupClass()).a(182214556489252L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  public xa(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xa.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 51468818048716
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: lload_3
    //   16: sipush #12178
    //   19: ldc2_w 5915496162910608560
    //   22: lload_1
    //   23: lxor
    //   24: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   29: sipush #25980
    //   32: ldc2_w 3520481843533403741
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   42: iconst_1
    //   43: anewarray java/lang/String
    //   46: dup
    //   47: iconst_0
    //   48: sipush #30141
    //   51: ldc2_w 5083232124372043416
    //   54: lload_1
    //   55: lxor
    //   56: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   61: aastore
    //   62: invokespecial <init> : (JLjava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V
    //   65: return
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
    //   20: sipush #10882
    //   23: ldc2_w 373846804645694481
    //   26: lload_3
    //   27: lxor
    //   28: <illegal opcode> x : (IJ)Ljava/lang/String;
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
    //   0: getstatic wtf/opal/xa.b : J
    //   3: ldc2_w 14589567381940
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 95997721313955
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: invokestatic V : ()[Lwtf/opal/d;
    //   19: aload_0
    //   20: sipush #6370
    //   23: ldc2_w 1291844672304911198
    //   26: lload_1
    //   27: lxor
    //   28: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   33: ldc wtf/opal/d
    //   35: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   38: checkcast wtf/opal/d
    //   41: astore #6
    //   43: astore #5
    //   45: aload #6
    //   47: aload #6
    //   49: iconst_0
    //   50: anewarray java/lang/Object
    //   53: invokevirtual P : ([Ljava/lang/Object;)Z
    //   56: aload #5
    //   58: ifnull -> 72
    //   61: ifne -> 75
    //   64: goto -> 71
    //   67: invokestatic a : (Lcom/mojang/brigadier/exceptions/CommandSyntaxException;)Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
    //   70: athrow
    //   71: iconst_1
    //   72: goto -> 76
    //   75: iconst_0
    //   76: iconst_1
    //   77: anewarray java/lang/Object
    //   80: dup_x1
    //   81: swap
    //   82: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   85: iconst_0
    //   86: swap
    //   87: aastore
    //   88: invokevirtual j : ([Ljava/lang/Object;)V
    //   91: aload #6
    //   93: iconst_0
    //   94: anewarray java/lang/Object
    //   97: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   100: aload #6
    //   102: iconst_0
    //   103: anewarray java/lang/Object
    //   106: invokevirtual P : ([Ljava/lang/Object;)Z
    //   109: ifeq -> 132
    //   112: sipush #23492
    //   115: ldc2_w 4519406342833393788
    //   118: lload_1
    //   119: lxor
    //   120: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   125: goto -> 145
    //   128: invokestatic a : (Lcom/mojang/brigadier/exceptions/CommandSyntaxException;)Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
    //   131: athrow
    //   132: sipush #17283
    //   135: ldc2_w 1112766374977652794
    //   138: lload_1
    //   139: lxor
    //   140: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   145: sipush #16366
    //   148: ldc2_w 2582554101511271505
    //   151: lload_1
    //   152: lxor
    //   153: <illegal opcode> x : (IJ)Ljava/lang/String;
    //   158: swap
    //   159: ldc '!'
    //   161: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   166: lload_3
    //   167: dup2_x1
    //   168: pop2
    //   169: iconst_2
    //   170: anewarray java/lang/Object
    //   173: dup_x1
    //   174: swap
    //   175: iconst_1
    //   176: swap
    //   177: aastore
    //   178: dup_x2
    //   179: dup_x2
    //   180: pop
    //   181: invokestatic valueOf : (J)Ljava/lang/Long;
    //   184: iconst_0
    //   185: swap
    //   186: aastore
    //   187: invokestatic g : ([Ljava/lang/Object;)V
    //   190: iconst_1
    //   191: ireturn
    // Exception table:
    //   from	to	target	type
    //   45	64	67	com/mojang/brigadier/exceptions/CommandSyntaxException
    //   76	128	128	com/mojang/brigadier/exceptions/CommandSyntaxException
  }
  
  static {
    long l = b ^ 0x3C081B349401L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "8BL/<Ï.£ä.¯ø(\024R \003CTI\013âT{ó S°ËÖÇOùæÜ·\017a\006SÍ\007\020Q½\000j\025W¼Ý/2ïâz=@\020ù^.ã³ ñ=mu°Q(\020ù\"\003\023é¹ -CÙÿÊ\022\034\017 ¾#tp!]u\013ÓzåX\000\0366Çô¿eî\\¸ÐpW \013àôk").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6B23;
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
        throw new RuntimeException("wtf/opal/xa", exception);
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
    //   66: ldc_w 'wtf/opal/xa'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xa.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */