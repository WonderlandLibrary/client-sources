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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2172;

public class ku implements ArgumentType<String> {
  private static final ku D;
  
  private static final DynamicCommandExceptionType N;
  
  private final List<String> e;
  
  private static final Collection<String> i;
  
  private static final long a = on.a(-7499670294014626610L, -6667577507775695232L, MethodHandles.lookup().lookupClass()).a(61389734183456L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long f;
  
  public static ku D(Object[] paramArrayOfObject) {
    return D;
  }
  
  public static String y(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast com/mojang/brigadier/context/CommandContext
    //   7: astore_1
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_2
    //   18: pop
    //   19: getstatic wtf/opal/ku.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: aload_1
    //   26: sipush #8847
    //   29: ldc2_w 6865597388448571743
    //   32: lload_2
    //   33: lxor
    //   34: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   39: ldc java/lang/String
    //   41: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   44: checkcast java/lang/String
    //   47: areturn
  }
  
  private ku(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/ku.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: aload_0
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: new java/util/ArrayList
    //   14: dup
    //   15: invokespecial <init> : ()V
    //   18: putfield e : Ljava/util/List;
    //   21: aload_0
    //   22: getfield e : Ljava/util/List;
    //   25: sipush #18299
    //   28: ldc2_w 5992811750641127630
    //   31: lload_1
    //   32: lxor
    //   33: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   38: invokeinterface add : (Ljava/lang/Object;)Z
    //   43: pop
    //   44: ldc org/lwjgl/glfw/GLFW
    //   46: invokevirtual getDeclaredFields : ()[Ljava/lang/reflect/Field;
    //   49: astore_3
    //   50: aload_3
    //   51: arraylength
    //   52: istore #4
    //   54: iconst_0
    //   55: istore #5
    //   57: iload #5
    //   59: iload #4
    //   61: if_icmpge -> 138
    //   64: aload_3
    //   65: iload #5
    //   67: aaload
    //   68: astore #6
    //   70: lload_1
    //   71: lconst_0
    //   72: lcmp
    //   73: iflt -> 132
    //   76: aload #6
    //   78: invokevirtual getName : ()Ljava/lang/String;
    //   81: sipush #14853
    //   84: ldc2_w 4054897650629062068
    //   87: lload_1
    //   88: lxor
    //   89: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   94: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   97: ifeq -> 129
    //   100: aload_0
    //   101: getfield e : Ljava/util/List;
    //   104: aload #6
    //   106: invokevirtual getName : ()Ljava/lang/String;
    //   109: getstatic wtf/opal/ku.f : J
    //   112: l2i
    //   113: invokevirtual substring : (I)Ljava/lang/String;
    //   116: invokeinterface add : (Ljava/lang/Object;)Z
    //   121: pop
    //   122: goto -> 129
    //   125: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   128: athrow
    //   129: iinc #5, 1
    //   132: lload_1
    //   133: lconst_0
    //   134: lcmp
    //   135: ifge -> 57
    //   138: return
    // Exception table:
    //   from	to	target	type
    //   70	122	125	wtf/opal/x5
  }
  
  public String b(Object[] paramArrayOfObject) throws CommandSyntaxException {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    StringReader stringReader = (StringReader)paramArrayOfObject[1];
    l = a ^ l;
    String str = stringReader.readString();
    d[] arrayOfD = d_.e();
    try {
      if (!this.e.contains(str.toUpperCase()))
        throw N.create(str); 
    } catch (CommandSyntaxException commandSyntaxException) {
      throw a(null);
    } 
    try {
      if (l >= 0L)
        if (d.D() != null) {
        
        } else {
          return str;
        }  
      d_.t(d.D());
    } catch (CommandSyntaxException commandSyntaxException) {
      throw a(null);
    } 
    return str;
  }
  
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> paramCommandContext, SuggestionsBuilder paramSuggestionsBuilder) {
    return class_2172.method_9265(this.e, paramSuggestionsBuilder);
  }
  
  public Collection<String> getExamples() {
    return i;
  }
  
  private static Message lambda$static$0(Object paramObject) {
    // Byte code:
    //   0: getstatic wtf/opal/ku.a : J
    //   3: ldc2_w 11310197827614
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   12: sipush #29234
    //   15: ldc2_w 8073199755640227427
    //   18: lload_1
    //   19: lxor
    //   20: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   25: swap
    //   26: sipush #7505
    //   29: ldc2_w 4350805718271502594
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
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "'\027Ñ\013pØ*Hø\017²-Á*`«\\ÞÓ®áÉ(ÝÉ¢\005\f\\tGË'Ô\fâ½X8ëgÊkê¬¾\030f\b\007ï\t7A\035âÝô(\020\002ÜòEñVÃÍÌòc,Ù )È-BÉ¶$WàÕ´pOIZ.\017\b|Qõ±\b=C¬\024\n6¹m\020÷mÄÝ\030¸S¨ hGyÌ\007").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xC49;
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
        throw new RuntimeException("wtf/opal/ku", exception);
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
    //   65: ldc_w 'wtf/opal/ku'
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
    long l1 = a ^ 0x7ED9826BA3C4L;
    long l2 = l1 ^ 0x254E2A7E383AL;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ku.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */