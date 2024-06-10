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
import net.minecraft.class_156;
import net.minecraft.class_2583;

public final class xl extends x6 {
  private static final long b = on.a(7723700520910022928L, 3818029336616702755L, MethodHandles.lookup().lookupClass()).a(157209103046647L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  public xl(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/xl.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 100388007945353
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: lload_3
    //   16: sipush #22623
    //   19: ldc2_w 2615909501182789586
    //   22: lload_1
    //   23: lxor
    //   24: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   29: sipush #7037
    //   32: ldc2_w 2669782851362653425
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   42: iconst_1
    //   43: anewarray java/lang/String
    //   46: dup
    //   47: iconst_0
    //   48: ldc 'c'
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
    //   20: sipush #23155
    //   23: ldc2_w 7508517148044939786
    //   26: lload_3
    //   27: lxor
    //   28: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   33: iconst_1
    //   34: anewarray java/lang/Object
    //   37: dup_x1
    //   38: swap
    //   39: iconst_0
    //   40: swap
    //   41: aastore
    //   42: invokestatic i : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;
    //   45: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   50: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   53: checkcast com/mojang/brigadier/builder/LiteralArgumentBuilder
    //   56: sipush #6337
    //   59: ldc2_w 4452023030989164710
    //   62: lload_3
    //   63: lxor
    //   64: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   69: iconst_0
    //   70: anewarray java/lang/Object
    //   73: invokestatic Y : ([Ljava/lang/Object;)Lwtf/opal/b;
    //   76: iconst_2
    //   77: anewarray java/lang/Object
    //   80: dup_x1
    //   81: swap
    //   82: iconst_1
    //   83: swap
    //   84: aastore
    //   85: dup_x1
    //   86: swap
    //   87: iconst_0
    //   88: swap
    //   89: aastore
    //   90: invokestatic Y : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
    //   93: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   98: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   101: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   104: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   107: pop
    //   108: invokestatic H : ()[I
    //   111: aload_2
    //   112: sipush #3035
    //   115: ldc2_w 6241557098523984817
    //   118: lload_3
    //   119: lxor
    //   120: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   125: iconst_1
    //   126: anewarray java/lang/Object
    //   129: dup_x1
    //   130: swap
    //   131: iconst_0
    //   132: swap
    //   133: aastore
    //   134: invokestatic i : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;
    //   137: sipush #6337
    //   140: ldc2_w 4452023030989164710
    //   143: lload_3
    //   144: lxor
    //   145: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   150: invokestatic word : ()Lcom/mojang/brigadier/arguments/StringArgumentType;
    //   153: iconst_2
    //   154: anewarray java/lang/Object
    //   157: dup_x1
    //   158: swap
    //   159: iconst_1
    //   160: swap
    //   161: aastore
    //   162: dup_x1
    //   163: swap
    //   164: iconst_0
    //   165: swap
    //   166: aastore
    //   167: invokestatic Y : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;
    //   170: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   175: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   178: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   181: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   184: pop
    //   185: aload_2
    //   186: sipush #28001
    //   189: ldc2_w 1701340893633275148
    //   192: lload_3
    //   193: lxor
    //   194: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   199: iconst_1
    //   200: anewarray java/lang/Object
    //   203: dup_x1
    //   204: swap
    //   205: iconst_0
    //   206: swap
    //   207: aastore
    //   208: invokestatic i : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;
    //   211: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   216: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   219: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   222: pop
    //   223: astore #5
    //   225: aload_2
    //   226: sipush #25249
    //   229: ldc2_w 7209551485478136527
    //   232: lload_3
    //   233: lxor
    //   234: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   239: iconst_1
    //   240: anewarray java/lang/Object
    //   243: dup_x1
    //   244: swap
    //   245: iconst_0
    //   246: swap
    //   247: aastore
    //   248: invokestatic i : ([Ljava/lang/Object;)Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;
    //   251: <illegal opcode> run : ()Lcom/mojang/brigadier/Command;
    //   256: invokevirtual executes : (Lcom/mojang/brigadier/Command;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   259: invokevirtual then : (Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;
    //   262: pop
    //   263: lload_3
    //   264: lconst_0
    //   265: lcmp
    //   266: ifle -> 281
    //   269: aload #5
    //   271: ifnull -> 288
    //   274: iconst_4
    //   275: anewarray wtf/opal/d
    //   278: invokestatic p : ([Lwtf/opal/d;)V
    //   281: goto -> 288
    //   284: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   287: athrow
    //   288: return
    // Exception table:
    //   from	to	target	type
    //   225	281	284	wtf/opal/x5
  }
  
  private static int lambda$onCommand$11(CommandContext paramCommandContext) throws CommandSyntaxException {
    class_156.method_668().method_673(d1.q(new Object[0]).c(new Object[0]).s(new Object[0]).toURI());
    return 1;
  }
  
  private static int lambda$onCommand$10(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xl.b : J
    //   3: ldc2_w 90709388717508
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 18316280989735
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: iconst_0
    //   17: anewarray java/lang/Object
    //   20: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   23: iconst_0
    //   24: anewarray java/lang/Object
    //   27: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/gc;
    //   30: lload_3
    //   31: iconst_1
    //   32: anewarray java/lang/Object
    //   35: dup_x2
    //   36: dup_x2
    //   37: pop
    //   38: invokestatic valueOf : (J)Ljava/lang/Long;
    //   41: iconst_0
    //   42: swap
    //   43: aastore
    //   44: invokevirtual q : ([Ljava/lang/Object;)Ljava/util/List;
    //   47: astore #6
    //   49: invokestatic H : ()[I
    //   52: sipush #5659
    //   55: ldc2_w 1171047621618462101
    //   58: lload_1
    //   59: lxor
    //   60: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   65: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   68: astore #7
    //   70: astore #5
    //   72: ldc ''
    //   74: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   77: astore #8
    //   79: aload #6
    //   81: invokeinterface iterator : ()Ljava/util/Iterator;
    //   86: astore #9
    //   88: aload #9
    //   90: invokeinterface hasNext : ()Z
    //   95: ifeq -> 183
    //   98: aload #9
    //   100: invokeinterface next : ()Ljava/lang/Object;
    //   105: checkcast oshi/util/tuples/Pair
    //   108: astore #10
    //   110: sipush #11314
    //   113: ldc2_w 1581611810681542591
    //   116: lload_1
    //   117: lxor
    //   118: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   123: iconst_2
    //   124: anewarray java/lang/Object
    //   127: dup
    //   128: iconst_0
    //   129: aload #10
    //   131: invokevirtual getA : ()Ljava/lang/Object;
    //   134: aastore
    //   135: dup
    //   136: iconst_1
    //   137: aload #10
    //   139: invokevirtual getB : ()Ljava/lang/Object;
    //   142: aastore
    //   143: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   146: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   149: astore #11
    //   151: aload #8
    //   153: invokeinterface method_10855 : ()Ljava/util/List;
    //   158: aload #11
    //   160: invokeinterface add : (Ljava/lang/Object;)Z
    //   165: pop
    //   166: aload #5
    //   168: ifnonnull -> 212
    //   171: aload #5
    //   173: ifnull -> 88
    //   176: goto -> 183
    //   179: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   182: athrow
    //   183: aload #7
    //   185: invokeinterface method_10855 : ()Ljava/util/List;
    //   190: aload #8
    //   192: invokeinterface add : (Ljava/lang/Object;)Z
    //   197: pop
    //   198: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   201: getfield field_1705 : Lnet/minecraft/class_329;
    //   204: invokevirtual method_1743 : ()Lnet/minecraft/class_338;
    //   207: aload #7
    //   209: invokevirtual method_1812 : (Lnet/minecraft/class_2561;)V
    //   212: iconst_1
    //   213: ireturn
    // Exception table:
    //   from	to	target	type
    //   151	176	179	com/mojang/brigadier/exceptions/CommandSyntaxException
  }
  
  private static int lambda$onCommand$9(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xl.b : J
    //   3: ldc2_w 92739497627181
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 66974477975109
    //   13: lxor
    //   14: lstore_3
    //   15: dup2
    //   16: ldc2_w 59317194194823
    //   19: lxor
    //   20: lstore #5
    //   22: pop2
    //   23: aload_0
    //   24: sipush #6337
    //   27: ldc2_w 4452003211346346154
    //   30: lload_1
    //   31: lxor
    //   32: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   37: ldc java/lang/String
    //   39: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   42: checkcast java/lang/String
    //   45: astore #7
    //   47: iconst_0
    //   48: anewarray java/lang/Object
    //   51: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   54: iconst_0
    //   55: anewarray java/lang/Object
    //   58: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/gc;
    //   61: aload #7
    //   63: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   66: lload_3
    //   67: dup2_x1
    //   68: pop2
    //   69: iconst_2
    //   70: anewarray java/lang/Object
    //   73: dup_x1
    //   74: swap
    //   75: iconst_1
    //   76: swap
    //   77: aastore
    //   78: dup_x2
    //   79: dup_x2
    //   80: pop
    //   81: invokestatic valueOf : (J)Ljava/lang/Long;
    //   84: iconst_0
    //   85: swap
    //   86: aastore
    //   87: invokevirtual P : ([Ljava/lang/Object;)V
    //   90: aload #7
    //   92: sipush #11294
    //   95: ldc2_w 8665000055286150248
    //   98: lload_1
    //   99: lxor
    //   100: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   105: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   110: lload #5
    //   112: dup2_x1
    //   113: pop2
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup_x1
    //   119: swap
    //   120: iconst_1
    //   121: swap
    //   122: aastore
    //   123: dup_x2
    //   124: dup_x2
    //   125: pop
    //   126: invokestatic valueOf : (J)Ljava/lang/Long;
    //   129: iconst_0
    //   130: swap
    //   131: aastore
    //   132: invokestatic g : ([Ljava/lang/Object;)V
    //   135: iconst_1
    //   136: ireturn
  }
  
  private static int lambda$onCommand$8(CommandContext paramCommandContext) throws CommandSyntaxException {
    // Byte code:
    //   0: getstatic wtf/opal/xl.b : J
    //   3: ldc2_w 76136086825506
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 9401901320486
    //   13: lxor
    //   14: lstore_3
    //   15: dup2
    //   16: ldc2_w 40220572588936
    //   19: lxor
    //   20: lstore #5
    //   22: pop2
    //   23: aload_0
    //   24: sipush #3227
    //   27: ldc2_w 81598227158364409
    //   30: lload_1
    //   31: lxor
    //   32: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   37: ldc java/lang/String
    //   39: invokevirtual getArgument : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   42: checkcast java/lang/String
    //   45: astore #7
    //   47: iconst_0
    //   48: anewarray java/lang/Object
    //   51: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   54: iconst_0
    //   55: anewarray java/lang/Object
    //   58: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/gc;
    //   61: aload #7
    //   63: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   66: lload_3
    //   67: iconst_2
    //   68: anewarray java/lang/Object
    //   71: dup_x2
    //   72: dup_x2
    //   73: pop
    //   74: invokestatic valueOf : (J)Ljava/lang/Long;
    //   77: iconst_1
    //   78: swap
    //   79: aastore
    //   80: dup_x1
    //   81: swap
    //   82: iconst_0
    //   83: swap
    //   84: aastore
    //   85: invokevirtual I : ([Ljava/lang/Object;)V
    //   88: aload #7
    //   90: sipush #4490
    //   93: ldc2_w 8687111686440021487
    //   96: lload_1
    //   97: lxor
    //   98: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   103: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   108: lload #5
    //   110: dup2_x1
    //   111: pop2
    //   112: iconst_2
    //   113: anewarray java/lang/Object
    //   116: dup_x1
    //   117: swap
    //   118: iconst_1
    //   119: swap
    //   120: aastore
    //   121: dup_x2
    //   122: dup_x2
    //   123: pop
    //   124: invokestatic valueOf : (J)Ljava/lang/Long;
    //   127: iconst_0
    //   128: swap
    //   129: aastore
    //   130: invokestatic g : ([Ljava/lang/Object;)V
    //   133: sipush #11227
    //   136: ldc2_w 5708823978146289571
    //   139: lload_1
    //   140: lxor
    //   141: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   146: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   149: aload #7
    //   151: <illegal opcode> apply : (Ljava/lang/String;)Ljava/util/function/UnaryOperator;
    //   156: invokevirtual method_27694 : (Ljava/util/function/UnaryOperator;)Lnet/minecraft/class_5250;
    //   159: <illegal opcode> apply : ()Ljava/util/function/UnaryOperator;
    //   164: invokevirtual method_27694 : (Ljava/util/function/UnaryOperator;)Lnet/minecraft/class_5250;
    //   167: astore #8
    //   169: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   172: getfield field_1705 : Lnet/minecraft/class_329;
    //   175: invokevirtual method_1743 : ()Lnet/minecraft/class_338;
    //   178: aload #8
    //   180: invokevirtual method_1812 : (Lnet/minecraft/class_2561;)V
    //   183: iconst_1
    //   184: ireturn
  }
  
  private static class_2583 lambda$onCommand$7(class_2583 paramclass_2583) {
    // Byte code:
    //   0: getstatic wtf/opal/xl.b : J
    //   3: ldc2_w 12970844937099
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: new net/minecraft/class_2568
    //   12: dup
    //   13: getstatic net/minecraft/class_2568$class_5247.field_24342 : Lnet/minecraft/class_2568$class_5247;
    //   16: sipush #26539
    //   19: ldc2_w 8887061869239183971
    //   22: lload_1
    //   23: lxor
    //   24: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   29: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   32: invokespecial <init> : (Lnet/minecraft/class_2568$class_5247;Ljava/lang/Object;)V
    //   35: invokevirtual method_10949 : (Lnet/minecraft/class_2568;)Lnet/minecraft/class_2583;
    //   38: areturn
  }
  
  private static class_2583 lambda$onCommand$6(String paramString, class_2583 paramclass_2583) {
    return paramclass_2583.method_10958(new uq(paramString::lambda$onCommand$5));
  }
  
  private static void lambda$onCommand$5(String paramString) {
    long l1 = b ^ 0x27944B5813A7L;
    long l2 = l1 ^ 0x7448E56DB8B8L;
    (new Object[3])[2] = Boolean.valueOf(true);
    (new Object[3])[1] = paramString.toLowerCase();
    new Object[3];
    d1.q(new Object[0]).c(new Object[0]).J(new Object[] { Long.valueOf(l2) });
  }
  
  private static int lambda$onCommand$4(CommandContext paramCommandContext) throws CommandSyntaxException {
    (new Thread(xl::lambda$onCommand$3)).start();
    return 1;
  }
  
  private static void lambda$onCommand$3() {
    // Byte code:
    //   0: getstatic wtf/opal/xl.b : J
    //   3: ldc2_w 139606751140382
    //   6: lxor
    //   7: lstore_0
    //   8: lload_0
    //   9: dup2
    //   10: ldc2_w 56398932785434
    //   13: lxor
    //   14: lstore_2
    //   15: dup2
    //   16: ldc2_w 34441314872244
    //   19: lxor
    //   20: lstore #4
    //   22: pop2
    //   23: invokestatic H : ()[I
    //   26: invokestatic stackPush : ()Lorg/lwjgl/system/MemoryStack;
    //   29: astore #9
    //   31: astore #6
    //   33: aload #9
    //   35: iconst_1
    //   36: invokevirtual mallocPointer : (I)Lorg/lwjgl/PointerBuffer;
    //   39: astore #7
    //   41: aload #7
    //   43: aload #9
    //   45: sipush #10792
    //   48: ldc2_w 904410451988637299
    //   51: lload_0
    //   52: lxor
    //   53: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   58: invokevirtual UTF8 : (Ljava/lang/CharSequence;)Ljava/nio/ByteBuffer;
    //   61: invokevirtual put : (Ljava/nio/ByteBuffer;)Lorg/lwjgl/PointerBuffer;
    //   64: pop
    //   65: aload #7
    //   67: invokevirtual flip : ()Lorg/lwjgl/system/CustomBuffer;
    //   70: pop
    //   71: sipush #26775
    //   74: ldc2_w 4066109352028579021
    //   77: lload_0
    //   78: lxor
    //   79: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   84: iconst_0
    //   85: anewarray java/lang/Object
    //   88: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   91: iconst_0
    //   92: anewarray java/lang/Object
    //   95: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/gc;
    //   98: iconst_0
    //   99: anewarray java/lang/Object
    //   102: invokevirtual s : ([Ljava/lang/Object;)Ljava/io/File;
    //   105: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   108: aload #7
    //   110: sipush #23779
    //   113: ldc2_w 8714553180989755568
    //   116: lload_0
    //   117: lxor
    //   118: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   123: iconst_0
    //   124: invokestatic tinyfd_openFileDialog : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;Lorg/lwjgl/PointerBuffer;Ljava/lang/CharSequence;Z)Ljava/lang/String;
    //   127: astore #8
    //   129: aload #9
    //   131: aload #6
    //   133: ifnonnull -> 148
    //   136: ifnull -> 193
    //   139: goto -> 146
    //   142: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   145: athrow
    //   146: aload #9
    //   148: invokevirtual close : ()V
    //   151: goto -> 193
    //   154: astore #10
    //   156: aload #9
    //   158: aload #6
    //   160: ifnonnull -> 175
    //   163: ifnull -> 190
    //   166: goto -> 173
    //   169: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   172: athrow
    //   173: aload #9
    //   175: invokevirtual close : ()V
    //   178: goto -> 190
    //   181: astore #11
    //   183: aload #10
    //   185: aload #11
    //   187: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   190: aload #10
    //   192: athrow
    //   193: aload #8
    //   195: aload #6
    //   197: ifnonnull -> 212
    //   200: ifnull -> 230
    //   203: goto -> 210
    //   206: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   209: athrow
    //   210: aload #8
    //   212: aload #6
    //   214: ifnonnull -> 237
    //   217: invokevirtual isEmpty : ()Z
    //   220: ifeq -> 235
    //   223: goto -> 230
    //   226: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   229: athrow
    //   230: return
    //   231: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   234: athrow
    //   235: aload #8
    //   237: sipush #30546
    //   240: ldc2_w 8748268627815971586
    //   243: lload_0
    //   244: lxor
    //   245: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   250: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   253: astore #9
    //   255: aload #9
    //   257: aload #9
    //   259: arraylength
    //   260: iconst_1
    //   261: isub
    //   262: aaload
    //   263: sipush #14975
    //   266: ldc2_w 7379766831302571555
    //   269: lload_0
    //   270: lxor
    //   271: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   276: ldc ''
    //   278: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   281: astore #10
    //   283: iconst_0
    //   284: anewarray java/lang/Object
    //   287: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   290: iconst_0
    //   291: anewarray java/lang/Object
    //   294: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/gc;
    //   297: aload #10
    //   299: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   302: lload_2
    //   303: iconst_2
    //   304: anewarray java/lang/Object
    //   307: dup_x2
    //   308: dup_x2
    //   309: pop
    //   310: invokestatic valueOf : (J)Ljava/lang/Long;
    //   313: iconst_1
    //   314: swap
    //   315: aastore
    //   316: dup_x1
    //   317: swap
    //   318: iconst_0
    //   319: swap
    //   320: aastore
    //   321: invokevirtual I : ([Ljava/lang/Object;)V
    //   324: aload #10
    //   326: sipush #29592
    //   329: ldc2_w 533085979624292319
    //   332: lload_0
    //   333: lxor
    //   334: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   339: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   344: lload #4
    //   346: dup2_x1
    //   347: pop2
    //   348: iconst_2
    //   349: anewarray java/lang/Object
    //   352: dup_x1
    //   353: swap
    //   354: iconst_1
    //   355: swap
    //   356: aastore
    //   357: dup_x2
    //   358: dup_x2
    //   359: pop
    //   360: invokestatic valueOf : (J)Ljava/lang/Long;
    //   363: iconst_0
    //   364: swap
    //   365: aastore
    //   366: invokestatic g : ([Ljava/lang/Object;)V
    //   369: sipush #1633
    //   372: ldc2_w 2514704156849162807
    //   375: lload_0
    //   376: lxor
    //   377: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   382: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   385: aload #10
    //   387: <illegal opcode> apply : (Ljava/lang/String;)Ljava/util/function/UnaryOperator;
    //   392: invokevirtual method_27694 : (Ljava/util/function/UnaryOperator;)Lnet/minecraft/class_5250;
    //   395: <illegal opcode> apply : ()Ljava/util/function/UnaryOperator;
    //   400: invokevirtual method_27694 : (Ljava/util/function/UnaryOperator;)Lnet/minecraft/class_5250;
    //   403: astore #11
    //   405: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   408: getfield field_1705 : Lnet/minecraft/class_329;
    //   411: invokevirtual method_1743 : ()Lnet/minecraft/class_338;
    //   414: aload #11
    //   416: invokevirtual method_1812 : (Lnet/minecraft/class_2561;)V
    //   419: return
    // Exception table:
    //   from	to	target	type
    //   33	129	154	java/lang/Throwable
    //   129	139	142	java/lang/Throwable
    //   156	166	169	java/lang/Throwable
    //   173	178	181	java/lang/Throwable
    //   193	203	206	java/lang/Throwable
    //   212	223	226	java/lang/Throwable
    //   217	231	231	java/lang/Throwable
  }
  
  private static class_2583 lambda$onCommand$2(class_2583 paramclass_2583) {
    // Byte code:
    //   0: getstatic wtf/opal/xl.b : J
    //   3: ldc2_w 108233809957208
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: new net/minecraft/class_2568
    //   12: dup
    //   13: getstatic net/minecraft/class_2568$class_5247.field_24342 : Lnet/minecraft/class_2568$class_5247;
    //   16: sipush #32097
    //   19: ldc2_w 5641205915212577400
    //   22: lload_1
    //   23: lxor
    //   24: <illegal opcode> i : (IJ)Ljava/lang/String;
    //   29: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   32: invokespecial <init> : (Lnet/minecraft/class_2568$class_5247;Ljava/lang/Object;)V
    //   35: invokevirtual method_10949 : (Lnet/minecraft/class_2568;)Lnet/minecraft/class_2583;
    //   38: areturn
  }
  
  private static class_2583 lambda$onCommand$1(String paramString, class_2583 paramclass_2583) {
    return paramclass_2583.method_10958(new uq(paramString::lambda$onCommand$0));
  }
  
  private static void lambda$onCommand$0(String paramString) {
    long l1 = b ^ 0x3FF5F851F662L;
    long l2 = l1 ^ 0x6C2956645D7DL;
    (new Object[3])[2] = Boolean.valueOf(true);
    (new Object[3])[1] = paramString.toLowerCase();
    new Object[3];
    d1.q(new Object[0]).c(new Object[0]).J(new Object[] { Long.valueOf(l2) });
  }
  
  static {
    long l = b ^ 0x64B92FB6D822L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[22];
    boolean bool = false;
    String str;
    int i = (str = "xú\006jjÑ×;qÚZËdð# óÃ&?\f!$æîw2êÿâGä\006£wxÜô\026\"\004ßa ¥\022§Ä]®ç+Øª\002éR-R\024(Ä\034tâÖ\\\035\033-¯ðXv¶Èõ³ÌòoõÂq¹ÍüO¨=¬\033üO\020ËùÎ%ÚÂã¾\026\0224\toc,Ù\t³ü«{°8SSHwÖ¢÷Õ²õ\027ªáûu¢WhþÞü=\027öÿO\025ø0ïÑ\020éN¼¸!\n\002\027$\025\017·§\033y\020åÅòÑ}*)(G·\nðSÜ `iyQÆì¯ÀbÂÎ¨¾ø\021Â$,KPwl?%Pd\020cj6\001q\017¯p?Òdèß±ÎÕÕÃÔÖ\020d*çôpv=}ÿÔë¾PêÁõîUùóÚñ½ÌÓö¢\\\023ä\030QÝß9G$fç\023#&tQA;q­fþ\006{}ð\001O%-E¹Z\025²Ty'\021\035Ìu2soI@öU\036Ó¥Ù\"ØJtìµ|\002L\004ü'\023J`ÿ\016\\°®}\020>ÀàøU\017u©\004ãÙÑSz9Ë\001Y6\004ù¡«euEQ¯72ix\001ñrJ|jP\001×Ãg.yM8Å7F$5_µ6Ð¶2/ZÉ\017Ï\021\032Ý÷^þ\021\036fÿ÷¬¤©h*,\002\021;ÌøÔ~Â=#D5u\f\006\034XÌ£æ¯ÎçÌ§~Î\036Ø6\r¦ .\013)çCDË)\034\020ãäV ¢\022@°\024T1HÎg\000~º\024îñ\007¦È\013-áR{(\002Ì'òé±ã\013ºSéðËñ~ÕÇ(R2+7\\0ä\030öLÌFF\001å©Ka\030Ú¥*J\032È\016à³)Ë´Ç\b¥À0$yí\007÷\020Ñ½Z\037\003J¡JD\002¤Ì¥4 \"\020\027³{B%±Þo\b\013A'Ñy0\013\rl«0\022!sòm¿}pÕñT¶åy\005\034\017\036ß5\032Â±0Ý\006dwÔêp¹Å§Kn­üYÚ\017â\\3)ð)\035×\036¨g\032^Ò\036v±Ü¤\020³°ìú\031?\024î\\Ñ,ù2Tn´\016¹¹hhï\035qK\003º=Ì\001\016n4\004\003TB\016\006ÞLñ¹6\005M\000Ûö\000)\004\036<ÛaP$#%ìPèyÑtÃÃIIÞÜkÂ±uÒ¬ÐôdRX\000Aa\tO=ì5³¾Ñ*f£e¡gØ röÒ§½êBâ`Ù\024QÃÛõ65Q´lõ«ÜöeÅî½»(!eÁQ|ÝÓé\024 üµ{ÉàµE\001\004^\023@·.ûq:\003#à×<Ò§À\t`o\020c£}Î0*b¾\006ç\031é").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static Throwable a(Throwable paramThrowable) {
    return paramThrowable;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5D9;
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
        throw new RuntimeException("wtf/opal/xl", exception);
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
    //   66: ldc_w 'wtf/opal/xl'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xl.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */