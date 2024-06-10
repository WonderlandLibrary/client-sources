package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_124;
import net.minecraft.class_2583;
import net.minecraft.class_5224;

public final class d5 implements class_5224 {
  private final StringBuilder q = new StringBuilder();
  
  private class_124 C = null;
  
  private static final long a = on.a(8381090403822512114L, 4870682419082015817L, MethodHandles.lookup().lookupClass()).a(209966481087855L);
  
  public boolean accept(int paramInt1, class_2583 paramclass_2583, int paramInt2) {
    // Byte code:
    //   0: getstatic wtf/opal/d5.a : J
    //   3: ldc2_w 107362129854219
    //   6: lxor
    //   7: lstore #4
    //   9: invokestatic Y : ()Z
    //   12: istore #6
    //   14: aload_2
    //   15: iload #6
    //   17: ifne -> 59
    //   20: invokevirtual method_10984 : ()Z
    //   23: ifeq -> 58
    //   26: goto -> 33
    //   29: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   32: athrow
    //   33: aload_0
    //   34: getfield q : Ljava/lang/StringBuilder;
    //   37: getstatic net/minecraft/class_124.field_1067 : Lnet/minecraft/class_124;
    //   40: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload_0
    //   45: getstatic net/minecraft/class_124.field_1067 : Lnet/minecraft/class_124;
    //   48: putfield C : Lnet/minecraft/class_124;
    //   51: goto -> 58
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: aload_2
    //   59: invokevirtual method_10973 : ()Lnet/minecraft/class_5251;
    //   62: ifnull -> 220
    //   65: invokestatic values : ()[Lnet/minecraft/class_124;
    //   68: astore #7
    //   70: aload #7
    //   72: arraylength
    //   73: istore #8
    //   75: iconst_0
    //   76: istore #9
    //   78: iload #9
    //   80: iload #8
    //   82: if_icmpge -> 220
    //   85: aload #7
    //   87: iload #9
    //   89: aaload
    //   90: astore #10
    //   92: iload #6
    //   94: ifne -> 215
    //   97: aload #10
    //   99: invokevirtual method_543 : ()Z
    //   102: iload #6
    //   104: ifne -> 240
    //   107: goto -> 114
    //   110: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: ifeq -> 205
    //   117: goto -> 124
    //   120: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   123: athrow
    //   124: aload #10
    //   126: iload #6
    //   128: ifne -> 170
    //   131: goto -> 138
    //   134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: invokevirtual method_532 : ()Ljava/lang/Integer;
    //   141: invokevirtual intValue : ()I
    //   144: aload_2
    //   145: invokevirtual method_10973 : ()Lnet/minecraft/class_5251;
    //   148: invokevirtual method_27716 : ()I
    //   151: if_icmpne -> 205
    //   154: goto -> 161
    //   157: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   160: athrow
    //   161: aload #10
    //   163: goto -> 170
    //   166: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   169: athrow
    //   170: aload_0
    //   171: getfield C : Lnet/minecraft/class_124;
    //   174: if_acmpeq -> 205
    //   177: aload_0
    //   178: getfield q : Ljava/lang/StringBuilder;
    //   181: aload #10
    //   183: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   186: pop
    //   187: aload_0
    //   188: aload #10
    //   190: putfield C : Lnet/minecraft/class_124;
    //   193: iload #6
    //   195: ifeq -> 220
    //   198: goto -> 205
    //   201: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   204: athrow
    //   205: iinc #9, 1
    //   208: goto -> 215
    //   211: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: iload #6
    //   217: ifeq -> 78
    //   220: aload_0
    //   221: getfield q : Ljava/lang/StringBuilder;
    //   224: new java/lang/String
    //   227: dup
    //   228: iload_3
    //   229: invokestatic toChars : (I)[C
    //   232: invokespecial <init> : ([C)V
    //   235: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: iconst_1
    //   240: ireturn
    // Exception table:
    //   from	to	target	type
    //   14	26	29	wtf/opal/x5
    //   20	51	54	wtf/opal/x5
    //   92	107	110	wtf/opal/x5
    //   97	117	120	wtf/opal/x5
    //   114	131	134	wtf/opal/x5
    //   124	154	157	wtf/opal/x5
    //   138	163	166	wtf/opal/x5
    //   170	198	201	wtf/opal/x5
    //   177	208	211	wtf/opal/x5
  }
  
  public String z(Object[] paramArrayOfObject) {
    return this.q.toString();
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\d5.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */