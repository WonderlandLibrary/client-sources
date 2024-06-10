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

public class d_ implements ArgumentType<d> {
  private static final d_ j;
  
  private static final DynamicCommandExceptionType w;
  
  private static final Collection<String> q;
  
  private static d[] L;
  
  private static final long a = on.a(7023150205112481476L, 1279456870156160988L, MethodHandles.lookup().lookupClass()).a(251514582156219L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public static d_ i(Object[] paramArrayOfObject) {
    return j;
  }
  
  public static d O(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast com/mojang/brigadier/context/CommandContext
    //   17: astore_1
    //   18: pop
    //   19: getstatic wtf/opal/d_.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: aload_1
    //   26: sipush #19709
    //   29: ldc2_w 6288612983130190080
    //   32: lload_2
    //   33: lxor
    //   34: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   39: ldc wtf/opal/d
    //   41: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   44: checkcast wtf/opal/d
    //   47: areturn
  }
  
  public d K(Object[] paramArrayOfObject) throws CommandSyntaxException {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    StringReader stringReader = (StringReader)paramArrayOfObject[1];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x3319DFE7AB1CL;
    String str = stringReader.readString();
    try {
      (new Object[2])[1] = str;
      new Object[2];
      return d1.q(new Object[0]).x(new Object[0]).j(new Object[] { Long.valueOf(l2) });
    } catch (by by) {
      throw w.create(str);
    } 
  }
  
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> paramCommandContext, SuggestionsBuilder paramSuggestionsBuilder) {
    return class_2172.method_9264(d1.q(new Object[0]).x(new Object[0]).g(new Object[0]).stream().map(d::k), paramSuggestionsBuilder);
  }
  
  public Collection<String> getExamples() {
    return q;
  }
  
  private static Message lambda$static$0(Object paramObject) {
    // Byte code:
    //   0: getstatic wtf/opal/d_.a : J
    //   3: ldc2_w 85319760357726
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   12: sipush #11379
    //   15: ldc2_w 7231202884016106341
    //   18: lload_1
    //   19: lxor
    //   20: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   25: swap
    //   26: sipush #13547
    //   29: ldc2_w 8411498530087695358
    //   32: lload_1
    //   33: lxor
    //   34: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   39: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   44: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   47: areturn
  }
  
  static {
    t(new d[1]);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "¿/B×&\"z3_ÞÙNo\017ÿ(Å\032åÂVLÞ¨Æ\025¢I«\023  HÚôÒmt\013«`iFko»0\006 þ ÛSuàvI{¥:c\024s%/ÿ&½] `÷Ôb¼^\r").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  public static void t(d[] paramArrayOfd) {
    L = paramArrayOfd;
  }
  
  public static d[] e() {
    return L;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4754;
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
        throw new RuntimeException("wtf/opal/d_", exception);
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
    //   65: ldc_w 'wtf/opal/d_'
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
    long l = a ^ 0x11B3F80502D3L;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\d_.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */