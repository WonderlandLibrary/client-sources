package wtf.opal;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2172;

public class b implements ArgumentType<String> {
  private static final b l;
  
  private static final DynamicCommandExceptionType H;
  
  private static final Collection<String> g;
  
  private static final long a = on.a(2312946694132032946L, 5003773080405690944L, MethodHandles.lookup().lookupClass()).a(261386914337384L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public static b Y(Object[] paramArrayOfObject) {
    return l;
  }
  
  public static String J(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast com/mojang/brigadier/context/CommandContext
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_1
    //   18: pop
    //   19: getstatic wtf/opal/b.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: aload_3
    //   26: sipush #23241
    //   29: ldc2_w 7426594168206059210
    //   32: lload_1
    //   33: lxor
    //   34: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   39: ldc java/lang/String
    //   41: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   44: checkcast java/lang/String
    //   47: areturn
  }
  
  public String Y(Object[] paramArrayOfObject) throws CommandSyntaxException {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast com/mojang/brigadier/StringReader
    //   7: astore #4
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_2
    //   19: pop
    //   20: getstatic wtf/opal/b.a : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: invokestatic e : ()[Lwtf/opal/d;
    //   29: aload #4
    //   31: invokevirtual readString : ()Ljava/lang/String;
    //   34: astore #6
    //   36: astore #5
    //   38: new java/io/File
    //   41: dup
    //   42: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   45: getfield field_1697 : Ljava/io/File;
    //   48: aload #6
    //   50: sipush #479
    //   53: ldc2_w 20472061183892977
    //   56: lload_2
    //   57: lxor
    //   58: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   63: swap
    //   64: sipush #7183
    //   67: ldc2_w 7282359280438181924
    //   70: lload_2
    //   71: lxor
    //   72: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   77: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   82: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   85: invokevirtual exists : ()Z
    //   88: ifeq -> 120
    //   91: aload #6
    //   93: aload #5
    //   95: ifnonnull -> 119
    //   98: goto -> 105
    //   101: invokestatic a : (Lcom/mojang/brigadier/exceptions/CommandSyntaxException;)Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
    //   104: athrow
    //   105: iconst_4
    //   106: anewarray wtf/opal/d
    //   109: invokestatic p : ([Lwtf/opal/d;)V
    //   112: goto -> 119
    //   115: invokestatic a : (Lcom/mojang/brigadier/exceptions/CommandSyntaxException;)Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
    //   118: athrow
    //   119: areturn
    //   120: getstatic wtf/opal/b.H : Lcom/mojang/brigadier/exceptions/DynamicCommandExceptionType;
    //   123: aload #6
    //   125: invokevirtual create : (Ljava/lang/Object;)Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
    //   128: athrow
    // Exception table:
    //   from	to	target	type
    //   38	98	101	com/mojang/brigadier/exceptions/CommandSyntaxException
    //   91	112	115	com/mojang/brigadier/exceptions/CommandSyntaxException
  }
  
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> paramCommandContext, SuggestionsBuilder paramSuggestionsBuilder) {
    long l1 = a ^ 0x5D19EF48F45FL;
    long l2 = l1 ^ 0x193BF5D59D33L;
    new Object[1];
    return class_2172.method_9265(d1.q(new Object[0]).c(new Object[0]).I(new Object[] { Long.valueOf(l2) }, ), paramSuggestionsBuilder);
  }
  
  public Collection<String> getExamples() {
    return g;
  }
  
  private static Message lambda$static$0(Object paramObject) {
    // Byte code:
    //   0: getstatic wtf/opal/b.a : J
    //   3: ldc2_w 58765139182799
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   12: sipush #26110
    //   15: ldc2_w 5438196887856196435
    //   18: lload_1
    //   19: lxor
    //   20: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   25: swap
    //   26: sipush #4316
    //   29: ldc2_w 2539687124865867376
    //   32: lload_1
    //   33: lxor
    //   34: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   39: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   44: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   47: areturn
  }
  
  static {
    (new byte[8])[0] = (byte)(int)(l1 >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l1 << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = " §@¹\033Åê\001õ\036\004ø½\020\fkÙ±Óì\030§ ¯øâY§u(Ä9L\02215Ç¬5\b\022ï´Gòïý\\jû\033ï.H7?÷7ó\f+»­").length();
    byte b3 = 16;
    byte b2 = -1;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x327B;
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
        throw new RuntimeException("wtf/opal/b", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b1 = 1; b1 < 8; b1++)
        arrayOfByte1[b1] = (byte)(int)(paramLong << b1 * 8 >>> 56L); 
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
    //   65: ldc_w 'wtf/opal/b'
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
  
  static {
    long l1 = a ^ 0x1F7679F0220EL;
    long l2 = l1 ^ 0x5B54636D4B62L;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */