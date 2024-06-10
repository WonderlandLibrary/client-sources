package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.StreamSupport;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1844;
import net.minecraft.class_1922;
import net.minecraft.class_2248;
import net.minecraft.class_259;
import net.minecraft.class_2680;
import net.minecraft.class_2682;
import net.minecraft.class_3726;
import net.minecraft.class_9334;

public final class k9 {
  private static final class_2248[] C;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map d;
  
  public static boolean g(Object[] paramArrayOfObject) {
    return b9.c.field_1724.method_6047().method_7909() instanceof net.minecraft.class_1829;
  }
  
  public static boolean J(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/k9.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: invokestatic B : ()[Ljava/lang/String;
    //   21: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   24: getfield field_1724 : Lnet/minecraft/class_746;
    //   27: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   30: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   33: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   36: astore #4
    //   38: astore_3
    //   39: aload #4
    //   41: instanceof net/minecraft/class_1829
    //   44: aload_3
    //   45: ifnull -> 111
    //   48: ifne -> 110
    //   51: goto -> 58
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: aload #4
    //   60: instanceof net/minecraft/class_1743
    //   63: aload_3
    //   64: ifnull -> 111
    //   67: goto -> 74
    //   70: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: ifne -> 110
    //   77: goto -> 84
    //   80: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   83: athrow
    //   84: aload #4
    //   86: instanceof net/minecraft/class_1835
    //   89: aload_3
    //   90: ifnull -> 111
    //   93: goto -> 100
    //   96: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   99: athrow
    //   100: ifeq -> 114
    //   103: goto -> 110
    //   106: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: iconst_1
    //   111: goto -> 115
    //   114: iconst_0
    //   115: ireturn
    // Exception table:
    //   from	to	target	type
    //   39	51	54	wtf/opal/x5
    //   48	67	70	wtf/opal/x5
    //   58	77	80	wtf/opal/x5
    //   74	93	96	wtf/opal/x5
    //   84	103	106	wtf/opal/x5
  }
  
  public static boolean X(Object[] paramArrayOfObject) {
    return (b9.c.field_1724.method_31548()).field_7547.stream().noneMatch(class_1799::method_7960);
  }
  
  public static int d(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/k9.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: iconst_0
    //   19: sipush #13162
    //   22: ldc2_w 2153722144103424389
    //   25: lload_1
    //   26: lxor
    //   27: <illegal opcode> b : (IJ)I
    //   32: invokestatic range : (II)Ljava/util/stream/IntStream;
    //   35: <illegal opcode> test : ()Ljava/util/function/IntPredicate;
    //   40: invokeinterface filter : (Ljava/util/function/IntPredicate;)Ljava/util/stream/IntStream;
    //   45: invokeinterface findFirst : ()Ljava/util/OptionalInt;
    //   50: iconst_m1
    //   51: invokevirtual orElse : (I)I
    //   54: ireturn
  }
  
  public static int e(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/k9.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: iconst_0
    //   19: sipush #15076
    //   22: ldc2_w 3350399734547901673
    //   25: lload_1
    //   26: lxor
    //   27: <illegal opcode> b : (IJ)I
    //   32: invokestatic range : (II)Ljava/util/stream/IntStream;
    //   35: <illegal opcode> apply : ()Ljava/util/function/IntFunction;
    //   40: invokeinterface mapToObj : (Ljava/util/function/IntFunction;)Ljava/util/stream/Stream;
    //   45: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   50: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   55: <illegal opcode> applyAsInt : ()Ljava/util/function/ToIntFunction;
    //   60: invokeinterface mapToInt : (Ljava/util/function/ToIntFunction;)Ljava/util/stream/IntStream;
    //   65: invokeinterface sum : ()I
    //   70: ireturn
  }
  
  public static boolean o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    class_2248 class_22481 = (class_2248)paramArrayOfObject[1];
    l = a ^ l;
    try {
      if (Arrays.<class_2248>stream(C).noneMatch(class_22481::lambda$isGoodBlock$3))
        try {
          if (class_22481.method_9564().method_26172((class_1922)class_2682.field_12294, b9.c.field_1724.method_24515(), class_3726.method_16195((class_1297)b9.c.field_1724)) == class_259.method_1077());
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  public static boolean L(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    class_1799 class_1799 = (class_1799)paramArrayOfObject[1];
    l = a ^ l;
    class_1844 class_1844 = (class_1844)class_1799.method_57824(class_9334.field_49651);
    String[] arrayOfString = l_.B();
    try {
      if (arrayOfString != null) {
        if (class_1844 != null)
          return StreamSupport.stream(class_1844.method_57397().spliterator(), false).allMatch(k9::lambda$isGoodPotion$4); 
      } else {
        return StreamSupport.stream(class_1844.method_57397().spliterator(), false).allMatch(k9::lambda$isGoodPotion$4);
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return true;
  }
  
  public static int E(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_2680
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_1
    //   18: pop
    //   19: getstatic wtf/opal/k9.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: iconst_0
    //   26: sipush #13162
    //   29: ldc2_w 2153763596712749583
    //   32: lload_1
    //   33: lxor
    //   34: <illegal opcode> b : (IJ)I
    //   39: invokestatic range : (II)Ljava/util/stream/IntStream;
    //   42: aload_3
    //   43: <illegal opcode> test : (Lnet/minecraft/class_2680;)Ljava/util/function/IntPredicate;
    //   48: invokeinterface filter : (Ljava/util/function/IntPredicate;)Ljava/util/stream/IntStream;
    //   53: invokeinterface findFirst : ()Ljava/util/OptionalInt;
    //   58: iconst_m1
    //   59: invokevirtual orElse : (I)I
    //   62: ireturn
  }
  
  public static int s(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/k9.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: iconst_0
    //   19: sipush #21141
    //   22: ldc2_w 5650101070694890040
    //   25: lload_1
    //   26: lxor
    //   27: <illegal opcode> b : (IJ)I
    //   32: invokestatic range : (II)Ljava/util/stream/IntStream;
    //   35: <illegal opcode> test : ()Ljava/util/function/IntPredicate;
    //   40: invokeinterface filter : (Ljava/util/function/IntPredicate;)Ljava/util/stream/IntStream;
    //   45: invokeinterface findFirst : ()Ljava/util/OptionalInt;
    //   50: iconst_m1
    //   51: invokevirtual orElse : (I)I
    //   54: ireturn
  }
  
  public static void J(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    b9.c.field_1761.method_2906(b9.c.field_1724.field_7512.field_7763, i, 1, class_1713.field_7795, (class_1657)b9.c.field_1724);
  }
  
  public static void E(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    int j = ((Integer)paramArrayOfObject[1]).intValue();
    b9.c.field_1761.method_2906(b9.c.field_1724.field_7512.field_7763, i, j, class_1713.field_7794, (class_1657)b9.c.field_1724);
  }
  
  public static void R(Object[] paramArrayOfObject) {
    int j = ((Integer)paramArrayOfObject[0]).intValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    b9.c.field_1761.method_2906(b9.c.field_1724.field_7512.field_7763, j, i, class_1713.field_7791, (class_1657)b9.c.field_1724);
  }
  
  private static boolean lambda$getFirstEmptySlot$6(int paramInt) {
    return b9.c.field_1724.method_31548().method_5438(paramInt).method_7960();
  }
  
  private static boolean lambda$getToolSlot$5(class_2680 paramclass_2680, int paramInt) {
    long l = a ^ 0x1DDD5755DF8BL;
    class_1799 class_1799 = (class_1799)(b9.c.field_1724.method_31548()).field_7547.get(paramInt);
    String[] arrayOfString = l_.B();
    try {
      if (arrayOfString != null)
        if (class_1799.method_7924(paramclass_2680) > 1.0F) {
        
        } else {
          return false;
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static boolean lambda$isGoodPotion$4(class_1293 paramclass_1293) {
    return ((class_1291)paramclass_1293.method_5579().comp_349()).method_5573();
  }
  
  private static boolean lambda$isGoodBlock$3(class_2248 paramclass_22481, class_2248 paramclass_22482) {
    long l = a ^ 0x5CD14D77C8AFL;
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return (paramclass_22481 == paramclass_22482);
  }
  
  private static boolean lambda$getBlockCount$2(class_1799 paramclass_1799) {
    // Byte code:
    //   0: getstatic wtf/opal/k9.a : J
    //   3: ldc2_w 78549341745575
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 4173676768951
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: invokestatic B : ()[Ljava/lang/String;
    //   19: astore #5
    //   21: aload_0
    //   22: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   25: instanceof net/minecraft/class_1747
    //   28: aload #5
    //   30: ifnull -> 54
    //   33: ifeq -> 129
    //   36: goto -> 43
    //   39: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload_0
    //   44: invokevirtual method_7947 : ()I
    //   47: goto -> 54
    //   50: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   53: athrow
    //   54: aload #5
    //   56: ifnull -> 110
    //   59: ifle -> 129
    //   62: goto -> 69
    //   65: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: aload_0
    //   70: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   73: checkcast net/minecraft/class_1747
    //   76: invokevirtual method_7711 : ()Lnet/minecraft/class_2248;
    //   79: lload_3
    //   80: dup2_x1
    //   81: pop2
    //   82: iconst_2
    //   83: anewarray java/lang/Object
    //   86: dup_x1
    //   87: swap
    //   88: iconst_1
    //   89: swap
    //   90: aastore
    //   91: dup_x2
    //   92: dup_x2
    //   93: pop
    //   94: invokestatic valueOf : (J)Ljava/lang/Long;
    //   97: iconst_0
    //   98: swap
    //   99: aastore
    //   100: invokestatic o : ([Ljava/lang/Object;)Z
    //   103: goto -> 110
    //   106: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: aload #5
    //   112: ifnull -> 126
    //   115: ifeq -> 129
    //   118: goto -> 125
    //   121: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   124: athrow
    //   125: iconst_1
    //   126: goto -> 130
    //   129: iconst_0
    //   130: ireturn
    // Exception table:
    //   from	to	target	type
    //   21	36	39	wtf/opal/x5
    //   33	47	50	wtf/opal/x5
    //   54	62	65	wtf/opal/x5
    //   59	103	106	wtf/opal/x5
    //   110	118	121	wtf/opal/x5
  }
  
  private static class_1799 lambda$getBlockCount$1(int paramInt) {
    return (class_1799)(b9.c.field_1724.method_31548()).field_7547.get(paramInt);
  }
  
  private static boolean lambda$getBlockSlot$0(int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/k9.a : J
    //   3: ldc2_w 103540958812434
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 29209821447682
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: invokestatic B : ()[Ljava/lang/String;
    //   19: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   22: getfield field_1724 : Lnet/minecraft/class_746;
    //   25: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   28: getfield field_7547 : Lnet/minecraft/class_2371;
    //   31: iload_0
    //   32: invokevirtual get : (I)Ljava/lang/Object;
    //   35: checkcast net/minecraft/class_1799
    //   38: astore #6
    //   40: astore #5
    //   42: aload #6
    //   44: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   47: instanceof net/minecraft/class_1747
    //   50: aload #5
    //   52: ifnull -> 77
    //   55: ifeq -> 153
    //   58: goto -> 65
    //   61: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: aload #6
    //   67: invokevirtual method_7947 : ()I
    //   70: goto -> 77
    //   73: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: aload #5
    //   79: ifnull -> 134
    //   82: ifle -> 153
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: aload #6
    //   94: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   97: checkcast net/minecraft/class_1747
    //   100: invokevirtual method_7711 : ()Lnet/minecraft/class_2248;
    //   103: lload_3
    //   104: dup2_x1
    //   105: pop2
    //   106: iconst_2
    //   107: anewarray java/lang/Object
    //   110: dup_x1
    //   111: swap
    //   112: iconst_1
    //   113: swap
    //   114: aastore
    //   115: dup_x2
    //   116: dup_x2
    //   117: pop
    //   118: invokestatic valueOf : (J)Ljava/lang/Long;
    //   121: iconst_0
    //   122: swap
    //   123: aastore
    //   124: invokestatic o : ([Ljava/lang/Object;)Z
    //   127: goto -> 134
    //   130: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   133: athrow
    //   134: aload #5
    //   136: ifnull -> 150
    //   139: ifeq -> 153
    //   142: goto -> 149
    //   145: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   148: athrow
    //   149: iconst_1
    //   150: goto -> 154
    //   153: iconst_0
    //   154: ireturn
    // Exception table:
    //   from	to	target	type
    //   42	58	61	wtf/opal/x5
    //   55	70	73	wtf/opal/x5
    //   77	85	88	wtf/opal/x5
    //   82	127	130	wtf/opal/x5
    //   134	142	145	wtf/opal/x5
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 5781145857600896921
    //   3: ldc2_w -4094991577779317184
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 81883825042051
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/k9.a : J
    //   26: getstatic wtf/opal/k9.a : J
    //   29: ldc2_w 2014225109384
    //   32: lxor
    //   33: lstore #11
    //   35: new java/util/HashMap
    //   38: dup
    //   39: bipush #13
    //   41: invokespecial <init> : (I)V
    //   44: putstatic wtf/opal/k9.d : Ljava/util/Map;
    //   47: ldc_w 'DES/CBC/NoPadding'
    //   50: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   53: dup
    //   54: astore_0
    //   55: iconst_2
    //   56: ldc_w 'DES'
    //   59: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   62: bipush #8
    //   64: newarray byte
    //   66: dup
    //   67: iconst_0
    //   68: lload #11
    //   70: bipush #56
    //   72: lushr
    //   73: l2i
    //   74: i2b
    //   75: bastore
    //   76: iconst_1
    //   77: istore_1
    //   78: iload_1
    //   79: bipush #8
    //   81: if_icmpge -> 105
    //   84: dup
    //   85: iload_1
    //   86: lload #11
    //   88: iload_1
    //   89: bipush #8
    //   91: imul
    //   92: lshl
    //   93: bipush #56
    //   95: lushr
    //   96: l2i
    //   97: i2b
    //   98: bastore
    //   99: iinc #1, 1
    //   102: goto -> 78
    //   105: new javax/crypto/spec/DESKeySpec
    //   108: dup_x1
    //   109: swap
    //   110: invokespecial <init> : ([B)V
    //   113: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   116: new javax/crypto/spec/IvParameterSpec
    //   119: dup
    //   120: bipush #8
    //   122: newarray byte
    //   124: invokespecial <init> : ([B)V
    //   127: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   130: bipush #18
    //   132: newarray long
    //   134: astore #6
    //   136: iconst_0
    //   137: istore_3
    //   138: ldc_w 'Ï¬¬bCÁç¾ýÏøÀKÌª|Gº¹´­ßD«d©h ÿÿQ*õMÎÆôð·Ëkí±tËGÐpGfâ¼q5B»ûÁe\\fPIY³$\\r^__[JDïÇÄ'pµÑÄ£ÏÝ-Ä<UñÏº\\nò\\f*ÉÁ7'
    //   141: dup
    //   142: astore #4
    //   144: invokevirtual length : ()I
    //   147: istore #5
    //   149: iconst_0
    //   150: istore_2
    //   151: aload #4
    //   153: iload_2
    //   154: iinc #2, 8
    //   157: iload_2
    //   158: invokevirtual substring : (II)Ljava/lang/String;
    //   161: ldc_w 'ISO-8859-1'
    //   164: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   167: astore #7
    //   169: aload #6
    //   171: iload_3
    //   172: iinc #3, 1
    //   175: aload #7
    //   177: iconst_0
    //   178: baload
    //   179: i2l
    //   180: ldc2_w 255
    //   183: land
    //   184: bipush #56
    //   186: lshl
    //   187: aload #7
    //   189: iconst_1
    //   190: baload
    //   191: i2l
    //   192: ldc2_w 255
    //   195: land
    //   196: bipush #48
    //   198: lshl
    //   199: lor
    //   200: aload #7
    //   202: iconst_2
    //   203: baload
    //   204: i2l
    //   205: ldc2_w 255
    //   208: land
    //   209: bipush #40
    //   211: lshl
    //   212: lor
    //   213: aload #7
    //   215: iconst_3
    //   216: baload
    //   217: i2l
    //   218: ldc2_w 255
    //   221: land
    //   222: bipush #32
    //   224: lshl
    //   225: lor
    //   226: aload #7
    //   228: iconst_4
    //   229: baload
    //   230: i2l
    //   231: ldc2_w 255
    //   234: land
    //   235: bipush #24
    //   237: lshl
    //   238: lor
    //   239: aload #7
    //   241: iconst_5
    //   242: baload
    //   243: i2l
    //   244: ldc2_w 255
    //   247: land
    //   248: bipush #16
    //   250: lshl
    //   251: lor
    //   252: aload #7
    //   254: bipush #6
    //   256: baload
    //   257: i2l
    //   258: ldc2_w 255
    //   261: land
    //   262: bipush #8
    //   264: lshl
    //   265: lor
    //   266: aload #7
    //   268: bipush #7
    //   270: baload
    //   271: i2l
    //   272: ldc2_w 255
    //   275: land
    //   276: lor
    //   277: iconst_m1
    //   278: goto -> 454
    //   281: lastore
    //   282: iload_2
    //   283: iload #5
    //   285: if_icmplt -> 151
    //   288: ldc_w '¯v¸Ù8üÊ°´ï-Óúg'
    //   291: dup
    //   292: astore #4
    //   294: invokevirtual length : ()I
    //   297: istore #5
    //   299: iconst_0
    //   300: istore_2
    //   301: aload #4
    //   303: iload_2
    //   304: iinc #2, 8
    //   307: iload_2
    //   308: invokevirtual substring : (II)Ljava/lang/String;
    //   311: ldc_w 'ISO-8859-1'
    //   314: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   317: astore #7
    //   319: aload #6
    //   321: iload_3
    //   322: iinc #3, 1
    //   325: aload #7
    //   327: iconst_0
    //   328: baload
    //   329: i2l
    //   330: ldc2_w 255
    //   333: land
    //   334: bipush #56
    //   336: lshl
    //   337: aload #7
    //   339: iconst_1
    //   340: baload
    //   341: i2l
    //   342: ldc2_w 255
    //   345: land
    //   346: bipush #48
    //   348: lshl
    //   349: lor
    //   350: aload #7
    //   352: iconst_2
    //   353: baload
    //   354: i2l
    //   355: ldc2_w 255
    //   358: land
    //   359: bipush #40
    //   361: lshl
    //   362: lor
    //   363: aload #7
    //   365: iconst_3
    //   366: baload
    //   367: i2l
    //   368: ldc2_w 255
    //   371: land
    //   372: bipush #32
    //   374: lshl
    //   375: lor
    //   376: aload #7
    //   378: iconst_4
    //   379: baload
    //   380: i2l
    //   381: ldc2_w 255
    //   384: land
    //   385: bipush #24
    //   387: lshl
    //   388: lor
    //   389: aload #7
    //   391: iconst_5
    //   392: baload
    //   393: i2l
    //   394: ldc2_w 255
    //   397: land
    //   398: bipush #16
    //   400: lshl
    //   401: lor
    //   402: aload #7
    //   404: bipush #6
    //   406: baload
    //   407: i2l
    //   408: ldc2_w 255
    //   411: land
    //   412: bipush #8
    //   414: lshl
    //   415: lor
    //   416: aload #7
    //   418: bipush #7
    //   420: baload
    //   421: i2l
    //   422: ldc2_w 255
    //   425: land
    //   426: lor
    //   427: iconst_0
    //   428: goto -> 454
    //   431: lastore
    //   432: iload_2
    //   433: iload #5
    //   435: if_icmplt -> 301
    //   438: aload #6
    //   440: putstatic wtf/opal/k9.b : [J
    //   443: bipush #18
    //   445: anewarray java/lang/Integer
    //   448: putstatic wtf/opal/k9.c : [Ljava/lang/Integer;
    //   451: goto -> 672
    //   454: dup_x2
    //   455: pop
    //   456: lstore #8
    //   458: bipush #8
    //   460: newarray byte
    //   462: dup
    //   463: iconst_0
    //   464: lload #8
    //   466: bipush #56
    //   468: lushr
    //   469: l2i
    //   470: i2b
    //   471: bastore
    //   472: dup
    //   473: iconst_1
    //   474: lload #8
    //   476: bipush #48
    //   478: lushr
    //   479: l2i
    //   480: i2b
    //   481: bastore
    //   482: dup
    //   483: iconst_2
    //   484: lload #8
    //   486: bipush #40
    //   488: lushr
    //   489: l2i
    //   490: i2b
    //   491: bastore
    //   492: dup
    //   493: iconst_3
    //   494: lload #8
    //   496: bipush #32
    //   498: lushr
    //   499: l2i
    //   500: i2b
    //   501: bastore
    //   502: dup
    //   503: iconst_4
    //   504: lload #8
    //   506: bipush #24
    //   508: lushr
    //   509: l2i
    //   510: i2b
    //   511: bastore
    //   512: dup
    //   513: iconst_5
    //   514: lload #8
    //   516: bipush #16
    //   518: lushr
    //   519: l2i
    //   520: i2b
    //   521: bastore
    //   522: dup
    //   523: bipush #6
    //   525: lload #8
    //   527: bipush #8
    //   529: lushr
    //   530: l2i
    //   531: i2b
    //   532: bastore
    //   533: dup
    //   534: bipush #7
    //   536: lload #8
    //   538: l2i
    //   539: i2b
    //   540: bastore
    //   541: aload_0
    //   542: swap
    //   543: invokevirtual doFinal : ([B)[B
    //   546: astore #10
    //   548: aload #10
    //   550: iconst_0
    //   551: baload
    //   552: i2l
    //   553: ldc2_w 255
    //   556: land
    //   557: bipush #56
    //   559: lshl
    //   560: aload #10
    //   562: iconst_1
    //   563: baload
    //   564: i2l
    //   565: ldc2_w 255
    //   568: land
    //   569: bipush #48
    //   571: lshl
    //   572: lor
    //   573: aload #10
    //   575: iconst_2
    //   576: baload
    //   577: i2l
    //   578: ldc2_w 255
    //   581: land
    //   582: bipush #40
    //   584: lshl
    //   585: lor
    //   586: aload #10
    //   588: iconst_3
    //   589: baload
    //   590: i2l
    //   591: ldc2_w 255
    //   594: land
    //   595: bipush #32
    //   597: lshl
    //   598: lor
    //   599: aload #10
    //   601: iconst_4
    //   602: baload
    //   603: i2l
    //   604: ldc2_w 255
    //   607: land
    //   608: bipush #24
    //   610: lshl
    //   611: lor
    //   612: aload #10
    //   614: iconst_5
    //   615: baload
    //   616: i2l
    //   617: ldc2_w 255
    //   620: land
    //   621: bipush #16
    //   623: lshl
    //   624: lor
    //   625: aload #10
    //   627: bipush #6
    //   629: baload
    //   630: i2l
    //   631: ldc2_w 255
    //   634: land
    //   635: bipush #8
    //   637: lshl
    //   638: lor
    //   639: aload #10
    //   641: bipush #7
    //   643: baload
    //   644: i2l
    //   645: ldc2_w 255
    //   648: land
    //   649: lor
    //   650: dup2_x1
    //   651: pop2
    //   652: tableswitch default -> 281, 0 -> 431
    //   672: sipush #28997
    //   675: ldc2_w 2198846036648675608
    //   678: lload #11
    //   680: lxor
    //   681: <illegal opcode> b : (IJ)I
    //   686: anewarray net/minecraft/class_2248
    //   689: dup
    //   690: iconst_0
    //   691: getstatic net/minecraft/class_2246.field_10034 : Lnet/minecraft/class_2248;
    //   694: aastore
    //   695: dup
    //   696: iconst_1
    //   697: getstatic net/minecraft/class_2246.field_10443 : Lnet/minecraft/class_2248;
    //   700: aastore
    //   701: dup
    //   702: iconst_2
    //   703: getstatic net/minecraft/class_2246.field_10432 : Lnet/minecraft/class_2248;
    //   706: aastore
    //   707: dup
    //   708: iconst_3
    //   709: getstatic net/minecraft/class_2246.field_10343 : Lnet/minecraft/class_2248;
    //   712: aastore
    //   713: dup
    //   714: iconst_4
    //   715: getstatic net/minecraft/class_2246.field_10485 : Lnet/minecraft/class_2248;
    //   718: aastore
    //   719: dup
    //   720: iconst_5
    //   721: getstatic net/minecraft/class_2246.field_10102 : Lnet/minecraft/class_2248;
    //   724: aastore
    //   725: dup
    //   726: sipush #4739
    //   729: ldc2_w 4119624416320072409
    //   732: lload #11
    //   734: lxor
    //   735: <illegal opcode> b : (IJ)I
    //   740: getstatic net/minecraft/class_2246.field_42728 : Lnet/minecraft/class_2248;
    //   743: aastore
    //   744: dup
    //   745: sipush #29107
    //   748: ldc2_w 5108372927975868901
    //   751: lload #11
    //   753: lxor
    //   754: <illegal opcode> b : (IJ)I
    //   759: getstatic net/minecraft/class_2246.field_10534 : Lnet/minecraft/class_2248;
    //   762: aastore
    //   763: dup
    //   764: sipush #960
    //   767: ldc2_w 6423367388798892935
    //   770: lload #11
    //   772: lxor
    //   773: <illegal opcode> b : (IJ)I
    //   778: getstatic net/minecraft/class_2246.field_10255 : Lnet/minecraft/class_2248;
    //   781: aastore
    //   782: dup
    //   783: sipush #13162
    //   786: ldc2_w 2153821091043511102
    //   789: lload #11
    //   791: lxor
    //   792: <illegal opcode> b : (IJ)I
    //   797: getstatic net/minecraft/class_2246.field_10200 : Lnet/minecraft/class_2248;
    //   800: aastore
    //   801: dup
    //   802: sipush #31399
    //   805: ldc2_w 4912696178427085566
    //   808: lload #11
    //   810: lxor
    //   811: <illegal opcode> b : (IJ)I
    //   816: getstatic net/minecraft/class_2246.field_10525 : Lnet/minecraft/class_2248;
    //   819: aastore
    //   820: dup
    //   821: sipush #17297
    //   824: ldc2_w 276044974454897615
    //   827: lload #11
    //   829: lxor
    //   830: <illegal opcode> b : (IJ)I
    //   835: getstatic net/minecraft/class_2246.field_10395 : Lnet/minecraft/class_2248;
    //   838: aastore
    //   839: dup
    //   840: sipush #24262
    //   843: ldc2_w 2873847679730247318
    //   846: lload #11
    //   848: lxor
    //   849: <illegal opcode> b : (IJ)I
    //   854: getstatic net/minecraft/class_2246.field_10263 : Lnet/minecraft/class_2248;
    //   857: aastore
    //   858: dup
    //   859: sipush #12683
    //   862: ldc2_w 1577996700205746649
    //   865: lload #11
    //   867: lxor
    //   868: <illegal opcode> b : (IJ)I
    //   873: getstatic net/minecraft/class_2246.field_10179 : Lnet/minecraft/class_2248;
    //   876: aastore
    //   877: dup
    //   878: sipush #10206
    //   881: ldc2_w 5070411043779616645
    //   884: lload #11
    //   886: lxor
    //   887: <illegal opcode> b : (IJ)I
    //   892: getstatic net/minecraft/class_2246.field_10181 : Lnet/minecraft/class_2248;
    //   895: aastore
    //   896: dup
    //   897: sipush #13952
    //   900: ldc2_w 8653445796343713493
    //   903: lload #11
    //   905: lxor
    //   906: <illegal opcode> b : (IJ)I
    //   911: getstatic net/minecraft/class_2246.field_16333 : Lnet/minecraft/class_2248;
    //   914: aastore
    //   915: dup
    //   916: sipush #24257
    //   919: ldc2_w 8418676639428175506
    //   922: lload #11
    //   924: lxor
    //   925: <illegal opcode> b : (IJ)I
    //   930: getstatic net/minecraft/class_2246.field_10375 : Lnet/minecraft/class_2248;
    //   933: aastore
    //   934: dup
    //   935: sipush #15462
    //   938: ldc2_w 2436234628960322617
    //   941: lload #11
    //   943: lxor
    //   944: <illegal opcode> b : (IJ)I
    //   949: getstatic net/minecraft/class_2246.field_9980 : Lnet/minecraft/class_2248;
    //   952: aastore
    //   953: dup
    //   954: sipush #3918
    //   957: ldc2_w 4559601177343735560
    //   960: lload #11
    //   962: lxor
    //   963: <illegal opcode> b : (IJ)I
    //   968: getstatic net/minecraft/class_2246.field_10228 : Lnet/minecraft/class_2248;
    //   971: aastore
    //   972: dup
    //   973: sipush #989
    //   976: ldc2_w 4325303831973936005
    //   979: lload #11
    //   981: lxor
    //   982: <illegal opcode> b : (IJ)I
    //   987: getstatic net/minecraft/class_2246.field_10327 : Lnet/minecraft/class_2248;
    //   990: aastore
    //   991: dup
    //   992: sipush #24436
    //   995: ldc2_w 8392775184041076520
    //   998: lload #11
    //   1000: lxor
    //   1001: <illegal opcode> b : (IJ)I
    //   1006: getstatic net/minecraft/class_2246.field_10535 : Lnet/minecraft/class_2248;
    //   1009: aastore
    //   1010: putstatic wtf/opal/k9.C : [Lnet/minecraft/class_2248;
    //   1013: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x24DA;
    if (c[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = b[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])d.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/k9", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      c[i] = Integer.valueOf(j);
    } 
    return c[i].intValue();
  }
  
  private static int a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
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
    //   65: ldc_w 'wtf/opal/k9'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\k9.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */