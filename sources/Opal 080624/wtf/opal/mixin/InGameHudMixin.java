package wtf.opal.mixin;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_266;
import net.minecraft.class_268;
import net.minecraft.class_269;
import net.minecraft.class_270;
import net.minecraft.class_327;
import net.minecraft.class_329;
import net.minecraft.class_332;
import net.minecraft.class_5250;
import net.minecraft.class_5348;
import net.minecraft.class_9011;
import net.minecraft.class_9022;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.d1;
import wtf.opal.je;
import wtf.opal.jz;
import wtf.opal.kc;
import wtf.opal.lg;
import wtf.opal.on;
import wtf.opal.uj;
import wtf.opal.x5;

@Mixin({class_329.class})
public abstract class InGameHudMixin {
  @Final
  @Shadow
  private static Comparator<class_9011> field_47550;
  
  @Final
  @Shadow
  private static String field_32171;
  
  private static final long a = on.a(-1859413355904749619L, -4551386058679960336L, MethodHandles.lookup().lookupClass()).a(88513048725747L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  private static final long h;
  
  @Overwrite
  private void method_1757(class_332 paramclass_332, class_266 paramclass_266) {
    // Byte code:
    //   0: getstatic wtf/opal/mixin/InGameHudMixin.a : J
    //   3: ldc2_w 88743988617356
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 38115205777706
    //   13: lxor
    //   14: lstore #5
    //   16: pop2
    //   17: iconst_0
    //   18: anewarray java/lang/Object
    //   21: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   24: iconst_0
    //   25: anewarray java/lang/Object
    //   28: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   31: ldc wtf/opal/ja
    //   33: iconst_1
    //   34: anewarray java/lang/Object
    //   37: dup_x1
    //   38: swap
    //   39: iconst_0
    //   40: swap
    //   41: aastore
    //   42: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   45: checkcast wtf/opal/ja
    //   48: astore #7
    //   50: aload #7
    //   52: iconst_0
    //   53: anewarray java/lang/Object
    //   56: invokevirtual D : ([Ljava/lang/Object;)Z
    //   59: ifne -> 67
    //   62: return
    //   63: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   66: athrow
    //   67: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   70: getfield field_1772 : Lnet/minecraft/class_327;
    //   73: astore #8
    //   75: aload_2
    //   76: invokevirtual method_1117 : ()Lnet/minecraft/class_269;
    //   79: astore #10
    //   81: aload_2
    //   82: getstatic net/minecraft/class_9025.field_47567 : Lnet/minecraft/class_9025;
    //   85: invokevirtual method_55380 : (Lnet/minecraft/class_9022;)Lnet/minecraft/class_9022;
    //   88: astore #11
    //   90: aload #10
    //   92: aload_2
    //   93: invokevirtual method_1184 : (Lnet/minecraft/class_266;)Ljava/util/Collection;
    //   96: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   101: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   106: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   111: getstatic wtf/opal/mixin/InGameHudMixin.field_47550 : Ljava/util/Comparator;
    //   114: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
    //   119: getstatic wtf/opal/mixin/InGameHudMixin.h : J
    //   122: invokeinterface limit : (J)Ljava/util/stream/Stream;
    //   127: aload #10
    //   129: aload #11
    //   131: aload #8
    //   133: <illegal opcode> apply : (Lnet/minecraft/class_269;Lnet/minecraft/class_9022;Lnet/minecraft/class_327;)Ljava/util/function/Function;
    //   138: invokeinterface map : (Ljava/util/function/Function;)Ljava/util/stream/Stream;
    //   143: <illegal opcode> apply : ()Ljava/util/function/IntFunction;
    //   148: invokeinterface toArray : (Ljava/util/function/IntFunction;)[Ljava/lang/Object;
    //   153: checkcast [Lwtf/opal/lg;
    //   156: astore #12
    //   158: aload_2
    //   159: invokevirtual method_1114 : ()Lnet/minecraft/class_2561;
    //   162: astore #13
    //   164: aload #8
    //   166: aload #13
    //   168: invokevirtual method_27525 : (Lnet/minecraft/class_5348;)I
    //   171: dup
    //   172: istore #9
    //   174: istore #14
    //   176: aload #8
    //   178: getstatic wtf/opal/mixin/InGameHudMixin.field_32171 : Ljava/lang/String;
    //   181: invokevirtual method_1727 : (Ljava/lang/String;)I
    //   184: istore #15
    //   186: aload #12
    //   188: astore #16
    //   190: aload #16
    //   192: arraylength
    //   193: istore #17
    //   195: iconst_0
    //   196: istore #18
    //   198: iload #18
    //   200: iload #17
    //   202: if_icmpge -> 260
    //   205: aload #16
    //   207: iload #18
    //   209: aaload
    //   210: astore #19
    //   212: iload #14
    //   214: aload #8
    //   216: aload #19
    //   218: invokevirtual M : ()Lnet/minecraft/class_2561;
    //   221: invokevirtual method_27525 : (Lnet/minecraft/class_5348;)I
    //   224: aload #19
    //   226: invokevirtual o : ()I
    //   229: ifle -> 247
    //   232: iload #15
    //   234: aload #19
    //   236: invokevirtual o : ()I
    //   239: iadd
    //   240: goto -> 248
    //   243: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   246: athrow
    //   247: iconst_0
    //   248: iadd
    //   249: invokestatic max : (II)I
    //   252: istore #14
    //   254: iinc #18, 1
    //   257: goto -> 198
    //   260: iload #14
    //   262: istore #16
    //   264: aload #7
    //   266: iconst_0
    //   267: anewarray java/lang/Object
    //   270: invokevirtual w : ([Ljava/lang/Object;)Z
    //   273: istore #17
    //   275: aload #7
    //   277: iconst_0
    //   278: anewarray java/lang/Object
    //   281: invokevirtual Z : ([Ljava/lang/Object;)Z
    //   284: istore #18
    //   286: aload #7
    //   288: iconst_0
    //   289: anewarray java/lang/Object
    //   292: invokevirtual D : ([Ljava/lang/Object;)F
    //   295: fstore #19
    //   297: aload #7
    //   299: lload #5
    //   301: iconst_1
    //   302: anewarray java/lang/Object
    //   305: dup_x2
    //   306: dup_x2
    //   307: pop
    //   308: invokestatic valueOf : (J)Ljava/lang/Long;
    //   311: iconst_0
    //   312: swap
    //   313: aastore
    //   314: invokevirtual X : ([Ljava/lang/Object;)I
    //   317: istore #20
    //   319: iconst_0
    //   320: anewarray java/lang/Object
    //   323: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   326: iconst_0
    //   327: anewarray java/lang/Object
    //   330: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   333: ldc wtf/opal/ju
    //   335: iconst_1
    //   336: anewarray java/lang/Object
    //   339: dup_x1
    //   340: swap
    //   341: iconst_0
    //   342: swap
    //   343: aastore
    //   344: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   347: checkcast wtf/opal/ju
    //   350: astore #21
    //   352: aload #21
    //   354: iconst_0
    //   355: anewarray java/lang/Object
    //   358: invokevirtual D : ([Ljava/lang/Object;)Z
    //   361: ifeq -> 396
    //   364: aload #21
    //   366: getfield g : Lwtf/opal/ke;
    //   369: invokevirtual z : ()Ljava/lang/Object;
    //   372: checkcast java/lang/Boolean
    //   375: invokevirtual booleanValue : ()Z
    //   378: ifeq -> 396
    //   381: goto -> 388
    //   384: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   387: athrow
    //   388: iconst_1
    //   389: goto -> 397
    //   392: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   395: athrow
    //   396: iconst_0
    //   397: istore #22
    //   399: aload #21
    //   401: iconst_0
    //   402: anewarray java/lang/Object
    //   405: invokevirtual D : ([Ljava/lang/Object;)Z
    //   408: ifeq -> 443
    //   411: aload #21
    //   413: getfield r : Lwtf/opal/ke;
    //   416: invokevirtual z : ()Ljava/lang/Object;
    //   419: checkcast java/lang/Boolean
    //   422: invokevirtual booleanValue : ()Z
    //   425: ifeq -> 443
    //   428: goto -> 435
    //   431: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   434: athrow
    //   435: iconst_1
    //   436: goto -> 444
    //   439: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   442: athrow
    //   443: iconst_0
    //   444: istore #23
    //   446: aload_1
    //   447: aload #12
    //   449: aload_1
    //   450: fload #19
    //   452: iload #16
    //   454: aload #8
    //   456: iload #20
    //   458: aload #13
    //   460: iload #9
    //   462: iload #17
    //   464: iload #22
    //   466: iload #23
    //   468: iload #18
    //   470: <illegal opcode> run : ([Lwtf/opal/lg;Lnet/minecraft/class_332;FILnet/minecraft/class_327;ILnet/minecraft/class_2561;IZZZZ)Ljava/lang/Runnable;
    //   475: invokevirtual method_51741 : (Ljava/lang/Runnable;)V
    //   478: return
    // Exception table:
    //   from	to	target	type
    //   50	63	63	wtf/opal/x5
    //   212	243	243	wtf/opal/x5
    //   352	381	384	wtf/opal/x5
    //   364	392	392	wtf/opal/x5
    //   399	428	431	wtf/opal/x5
    //   411	439	439	wtf/opal/x5
  }
  
  @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"), method = {"renderHotbar"})
  public class_1799 hideOffhandSlot(class_1657 paramclass_1657) {
    class_1799 class_1799 = paramclass_1657.method_6079();
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (je.D(new Object[0]))
        try {
          if (class_1799.method_7909() instanceof net.minecraft.class_1819)
            try {
              if (!((Boolean)je.m(new Object[0]).z()).booleanValue()) {
                try {
                  if (((Boolean)je.w(new Object[0]).z()).booleanValue())
                    try {
                      if (paramclass_1657.method_6047().method_7909() instanceof net.minecraft.class_1829)
                        return class_1799.field_8037; 
                    } catch (x5 x5) {
                      throw a(null);
                    }  
                } catch (x5 x5) {
                  throw a(null);
                } 
              } else {
                return class_1799.field_8037;
              } 
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return class_1799;
  }
  
  @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/LayeredDrawer;render(Lnet/minecraft/client/gui/DrawContext;F)V", shift = At.Shift.AFTER)})
  private void hookRender(class_332 paramclass_332, float paramFloat, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x4EE8C3C07B7DL;
    long l2 = l1 ^ 0x31381783C77FL;
    long l3 = l1 ^ 0x5CBADCEA0444L;
    long l4 = l1 ^ 0x6EF1F2F910F4L;
    long l5 = l1 ^ 0x47C6DA471C3AL;
    long l6 = l1 ^ 0xBDD4E1EE4FDL;
    long l7 = (l1 ^ 0x44BB4325746BL) >>> 32L;
    int i = (int)((l1 ^ 0x44BB4325746BL) << 32L >>> 32L);
    l1 ^ 0x44BB4325746BL;
    new Object[1];
    kc.S(new Object[] { Long.valueOf(l6) });
    jz jz = (jz)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jz.class });
    try {
      if (jz.D(new Object[0])) {
        new Object[2];
        jz.F(new Object[] { null, Long.valueOf(l3), Float.valueOf(paramFloat) });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    uj uj = new uj(paramclass_332, paramFloat, l7, i);
    (new Object[2])[1] = uj;
    new Object[2];
    d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l4) });
    (new Object[2])[1] = Boolean.valueOf(false);
    new Object[2];
    d1.q(new Object[0]).z(new Object[0]).l(new Object[] { Long.valueOf(l2) });
    new Object[1];
    kc.I(new Object[] { Long.valueOf(l5) });
  }
  
  private static void lambda$renderScoreboardSidebar$3(lg[] paramArrayOflg, class_332 paramclass_332, float paramFloat, int paramInt1, class_327 paramclass_327, int paramInt2, class_2561 paramclass_2561, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    // Byte code:
    //   0: getstatic wtf/opal/mixin/InGameHudMixin.a : J
    //   3: ldc2_w 70288014493331
    //   6: lxor
    //   7: lstore #12
    //   9: aload_0
    //   10: arraylength
    //   11: istore #14
    //   13: iconst_3
    //   14: istore #15
    //   16: aload_1
    //   17: invokevirtual method_51421 : ()I
    //   20: i2f
    //   21: fload_2
    //   22: fdiv
    //   23: iload_3
    //   24: i2f
    //   25: fsub
    //   26: ldc_w 3.0
    //   29: fsub
    //   30: f2i
    //   31: istore #16
    //   33: aload_1
    //   34: invokevirtual method_51421 : ()I
    //   37: i2f
    //   38: fload_2
    //   39: fdiv
    //   40: ldc_w 3.0
    //   43: fsub
    //   44: fconst_2
    //   45: fadd
    //   46: f2i
    //   47: istore #17
    //   49: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   52: getfield field_1690 : Lnet/minecraft/class_315;
    //   55: ldc_w 0.3
    //   58: invokevirtual method_19345 : (F)I
    //   61: istore #18
    //   63: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   66: getfield field_1690 : Lnet/minecraft/class_315;
    //   69: ldc_w 0.4
    //   72: invokevirtual method_19345 : (F)I
    //   75: istore #19
    //   77: aload_1
    //   78: invokevirtual method_51443 : ()I
    //   81: i2f
    //   82: fload_2
    //   83: fdiv
    //   84: fconst_2
    //   85: fdiv
    //   86: iload #14
    //   88: aload #4
    //   90: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   93: pop
    //   94: sipush #32662
    //   97: ldc2_w 2745074394904190090
    //   100: lload #12
    //   102: lxor
    //   103: <illegal opcode> o : (IJ)I
    //   108: imul
    //   109: i2f
    //   110: ldc_w 3.0
    //   113: fdiv
    //   114: fadd
    //   115: f2i
    //   116: istore #20
    //   118: iload #20
    //   120: iload #14
    //   122: aload #4
    //   124: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   127: pop
    //   128: sipush #4743
    //   131: ldc2_w 8889166819293933976
    //   134: lload #12
    //   136: lxor
    //   137: <illegal opcode> o : (IJ)I
    //   142: imul
    //   143: isub
    //   144: istore #21
    //   146: iload #5
    //   148: ifeq -> 227
    //   151: iload #5
    //   153: sipush #2096
    //   156: ldc2_w 3359258581688724267
    //   159: lload #12
    //   161: lxor
    //   162: <illegal opcode> o : (IJ)I
    //   167: iadd
    //   168: iload #21
    //   170: if_icmple -> 227
    //   173: goto -> 180
    //   176: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   179: athrow
    //   180: iload #5
    //   182: sipush #21502
    //   185: ldc2_w 568320389840662752
    //   188: lload #12
    //   190: lxor
    //   191: <illegal opcode> o : (IJ)I
    //   196: iadd
    //   197: istore #21
    //   199: iload #21
    //   201: iload #14
    //   203: aload #4
    //   205: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   208: pop
    //   209: sipush #4743
    //   212: ldc2_w 8889166819293933976
    //   215: lload #12
    //   217: lxor
    //   218: <illegal opcode> o : (IJ)I
    //   223: imul
    //   224: iadd
    //   225: istore #20
    //   227: aload_1
    //   228: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   231: invokevirtual method_22903 : ()V
    //   234: aload_1
    //   235: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   238: fload_2
    //   239: fload_2
    //   240: fconst_1
    //   241: invokevirtual method_22905 : (FFF)V
    //   244: aload_1
    //   245: iload #16
    //   247: iconst_2
    //   248: isub
    //   249: iload #21
    //   251: aload #4
    //   253: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   256: pop
    //   257: sipush #4743
    //   260: ldc2_w 8889166819293933976
    //   263: lload #12
    //   265: lxor
    //   266: <illegal opcode> o : (IJ)I
    //   271: isub
    //   272: iconst_1
    //   273: isub
    //   274: iload #17
    //   276: iload #21
    //   278: iconst_1
    //   279: isub
    //   280: iload #19
    //   282: invokevirtual method_25294 : (IIIII)V
    //   285: aload_1
    //   286: iload #16
    //   288: iconst_2
    //   289: isub
    //   290: iload #21
    //   292: iconst_1
    //   293: isub
    //   294: iload #17
    //   296: iload #20
    //   298: iload #18
    //   300: invokevirtual method_25294 : (IIIII)V
    //   303: aload_1
    //   304: aload #4
    //   306: aload #6
    //   308: iload #16
    //   310: iload_3
    //   311: iconst_2
    //   312: idiv
    //   313: iadd
    //   314: iload #7
    //   316: iconst_2
    //   317: idiv
    //   318: isub
    //   319: iload #21
    //   321: aload #4
    //   323: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   326: pop
    //   327: sipush #4743
    //   330: ldc2_w 8889166819293933976
    //   333: lload #12
    //   335: lxor
    //   336: <illegal opcode> o : (IJ)I
    //   341: isub
    //   342: iconst_m1
    //   343: iload #8
    //   345: invokevirtual method_51439 : (Lnet/minecraft/class_327;Lnet/minecraft/class_2561;IIIZ)I
    //   348: pop
    //   349: iconst_0
    //   350: istore #22
    //   352: iload #22
    //   354: iload #14
    //   356: if_icmpge -> 735
    //   359: aload_0
    //   360: iload #22
    //   362: aaload
    //   363: astore #23
    //   365: iload #20
    //   367: iload #14
    //   369: iload #22
    //   371: isub
    //   372: aload #4
    //   374: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   377: pop
    //   378: sipush #4743
    //   381: ldc2_w 8889166819293933976
    //   384: lload #12
    //   386: lxor
    //   387: <illegal opcode> o : (IJ)I
    //   392: imul
    //   393: isub
    //   394: istore #24
    //   396: aload #23
    //   398: invokevirtual M : ()Lnet/minecraft/class_2561;
    //   401: astore #25
    //   403: aload #25
    //   405: invokeinterface getString : ()Ljava/lang/String;
    //   410: astore #26
    //   412: iload #9
    //   414: ifeq -> 546
    //   417: iload #22
    //   419: ifne -> 546
    //   422: goto -> 429
    //   425: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   428: athrow
    //   429: aload #26
    //   431: sipush #23576
    //   434: ldc2_w 469968553254420229
    //   437: lload #12
    //   439: lxor
    //   440: <illegal opcode> o : (IJ)I
    //   445: invokevirtual indexOf : (I)I
    //   448: iconst_m1
    //   449: if_icmpeq -> 546
    //   452: goto -> 459
    //   455: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   458: athrow
    //   459: aload #26
    //   461: sipush #3715
    //   464: ldc2_w 7424697785980477813
    //   467: lload #12
    //   469: lxor
    //   470: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   475: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   478: ifeq -> 546
    //   481: goto -> 488
    //   484: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   487: athrow
    //   488: aload #26
    //   490: sipush #16087
    //   493: ldc2_w 7784541824571396388
    //   496: lload #12
    //   498: lxor
    //   499: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   504: sipush #30252
    //   507: ldc2_w 2249515008869865950
    //   510: lload #12
    //   512: lxor
    //   513: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   518: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   521: sipush #23108
    //   524: ldc2_w 1467850742880220593
    //   527: lload #12
    //   529: lxor
    //   530: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   535: swap
    //   536: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   541: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   544: astore #25
    //   546: iload #10
    //   548: ifeq -> 676
    //   551: iload #22
    //   553: iload #14
    //   555: iconst_1
    //   556: isub
    //   557: if_icmpne -> 676
    //   560: goto -> 567
    //   563: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   566: athrow
    //   567: aload #26
    //   569: sipush #18736
    //   572: ldc2_w 6942005545424022058
    //   575: lload #12
    //   577: lxor
    //   578: <illegal opcode> o : (IJ)I
    //   583: invokevirtual indexOf : (I)I
    //   586: iconst_m1
    //   587: if_icmpeq -> 676
    //   590: goto -> 597
    //   593: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   596: athrow
    //   597: aload #26
    //   599: sipush #18148
    //   602: ldc2_w 7092582138197977365
    //   605: lload #12
    //   607: lxor
    //   608: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   613: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   616: ifeq -> 647
    //   619: goto -> 626
    //   622: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   625: athrow
    //   626: sipush #11765
    //   629: ldc2_w 7056420669174792705
    //   632: lload #12
    //   634: lxor
    //   635: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   640: goto -> 661
    //   643: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   646: athrow
    //   647: sipush #12574
    //   650: ldc2_w 2161046918840770281
    //   653: lload #12
    //   655: lxor
    //   656: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   661: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   664: aload #25
    //   666: invokeinterface method_10866 : ()Lnet/minecraft/class_2583;
    //   671: invokevirtual method_27696 : (Lnet/minecraft/class_2583;)Lnet/minecraft/class_5250;
    //   674: astore #25
    //   676: aload_1
    //   677: aload #4
    //   679: aload #25
    //   681: iload #16
    //   683: iload #24
    //   685: iconst_m1
    //   686: iload #8
    //   688: invokevirtual method_51439 : (Lnet/minecraft/class_327;Lnet/minecraft/class_2561;IIIZ)I
    //   691: pop
    //   692: iload #11
    //   694: ifne -> 729
    //   697: aload_1
    //   698: aload #4
    //   700: aload #23
    //   702: invokevirtual a : ()Lnet/minecraft/class_2561;
    //   705: iload #17
    //   707: aload #23
    //   709: invokevirtual o : ()I
    //   712: isub
    //   713: iload #24
    //   715: iconst_m1
    //   716: iload #8
    //   718: invokevirtual method_51439 : (Lnet/minecraft/class_327;Lnet/minecraft/class_2561;IIIZ)I
    //   721: pop
    //   722: goto -> 729
    //   725: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   728: athrow
    //   729: iinc #22, 1
    //   732: goto -> 352
    //   735: aload_1
    //   736: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   739: invokevirtual method_22909 : ()V
    //   742: return
    // Exception table:
    //   from	to	target	type
    //   146	173	176	wtf/opal/x5
    //   412	422	425	wtf/opal/x5
    //   417	452	455	wtf/opal/x5
    //   429	481	484	wtf/opal/x5
    //   546	560	563	wtf/opal/x5
    //   551	590	593	wtf/opal/x5
    //   567	619	622	wtf/opal/x5
    //   597	643	643	wtf/opal/x5
    //   676	722	725	wtf/opal/x5
  }
  
  private static lg[] lambda$renderScoreboardSidebar$2(int paramInt) {
    return new lg[paramInt];
  }
  
  private static lg lambda$renderScoreboardSidebar$1(class_269 paramclass_269, class_9022 paramclass_9022, class_327 paramclass_327, class_9011 paramclass_9011) {
    class_268 class_268 = paramclass_269.method_1164(paramclass_9011.comp_2127());
    class_2561 class_2561 = paramclass_9011.method_55387();
    class_5250 class_52501 = class_268.method_1142((class_270)class_268, class_2561);
    class_5250 class_52502 = paramclass_9011.method_55386(paramclass_9022);
    return new lg((class_2561)class_52501, (class_2561)class_52502, paramclass_327.method_27525((class_5348)class_52502));
  }
  
  private static boolean lambda$renderScoreboardSidebar$0(class_9011 paramclass_9011) {
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return !paramclass_9011.method_55385();
  }
  
  static {
    long l = a ^ 0x1A38E7059E31L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[7];
    boolean bool = false;
    String str;
    int i = (str = "$i¿\033¶ë¬L£\022ï\033Æ¼\032\007Âg\000\020óV´Ü²@D¤\021\001úG\020¹ST»¶T[S|¶\004Ö\035Î@î\030Ý\n\036ä².4\t\013\004¤F3\023íó1\020Po·QüpÕRo$i\026²").length();
    byte b2 = 24;
    byte b = -1;
    while (true);
  }
  
  private static x5 a(x5 paramx5) {
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4EC4;
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
        throw new RuntimeException("wtf/opal/mixin/InGameHudMixin", exception);
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
    //   66: ldc_w 'wtf/opal/mixin/InGameHudMixin'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x22D;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])g.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/mixin/InGameHudMixin", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
  }
  
  private static int b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = b(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite b(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
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
    //   66: ldc_w 'wtf/opal/mixin/InGameHudMixin'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\InGameHudMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */