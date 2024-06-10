package wtf.opal.mixin;

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
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1839;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import net.minecraft.class_7833;
import net.minecraft.class_811;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.je;
import wtf.opal.k0;
import wtf.opal.on;
import wtf.opal.q;
import wtf.opal.x5;

@Mixin({class_759.class})
public abstract class HeldItemRendererMixin {
  @Shadow
  private float field_4043;
  
  @Shadow
  private class_1799 field_4047;
  
  private static final long a = on.a(5994166690684707923L, -5376016371895538189L, MethodHandles.lookup().lookupClass()).a(79392782770526L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  @Shadow
  protected abstract void method_3224(class_4587 paramclass_4587, class_1306 paramclass_1306, float paramFloat);
  
  @Shadow
  public abstract void method_3233(class_1309 paramclass_1309, class_1799 paramclass_1799, class_811 paramclass_811, boolean paramBoolean, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt);
  
  @Shadow
  protected abstract void method_3218(class_4587 paramclass_4587, float paramFloat, class_1306 paramclass_1306, class_1799 paramclass_1799);
  
  @Shadow
  protected abstract void method_3217(class_4587 paramclass_4587, class_1306 paramclass_1306, float paramFloat);
  
  @Inject(method = {"renderFirstPersonItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER)})
  private void hookRenderFirstPersonItem(class_742 paramclass_742, float paramFloat1, float paramFloat2, class_1268 paramclass_1268, float paramFloat3, class_1799 paramclass_1799, float paramFloat4, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt, CallbackInfo paramCallbackInfo) {
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (je.D(new Object[0])) {
        try {
          if (class_1268.field_5808 == paramclass_1268)
            try {
              if (je.o(new Object[0])) {
                paramclass_4587.method_22904(((Double)je.t(new Object[0]).z()).doubleValue(), ((Double)je.S(new Object[0]).z()).doubleValue(), ((Double)je.K(new Object[0]).z()).doubleValue());
                paramclass_4587.method_22907(class_7833.field_40714.rotationDegrees(((Double)je.d(new Object[0]).z()).floatValue()));
                paramclass_4587.method_22907(class_7833.field_40716.rotationDegrees(((Double)je.O(new Object[0]).z()).floatValue()));
                paramclass_4587.method_22907(class_7833.field_40718.rotationDegrees(((Double)je.L(new Object[0]).z()).floatValue()));
                return;
              } 
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        } 
        try {
          if (!je.o(new Object[0])) {
            paramclass_4587.method_22904(((Double)je.b(new Object[0]).z()).doubleValue(), ((Double)je.Z(new Object[0]).z()).doubleValue(), ((Double)je.E(new Object[0]).z()).doubleValue());
            paramclass_4587.method_22907(class_7833.field_40714.rotationDegrees(((Double)je.P(new Object[0]).z()).floatValue()));
            paramclass_4587.method_22907(class_7833.field_40716.rotationDegrees(((Double)je.r(new Object[0]).z()).floatValue()));
            paramclass_4587.method_22907(class_7833.field_40718.rotationDegrees(((Double)je.h(new Object[0]).z()).floatValue()));
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  @Inject(method = {"renderFirstPersonItem"}, at = {@At("HEAD")}, cancellable = true)
  private void hideShield(class_742 paramclass_742, float paramFloat1, float paramFloat2, class_1268 paramclass_1268, float paramFloat3, class_1799 paramclass_1799, float paramFloat4, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt, CallbackInfo paramCallbackInfo) {
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (paramclass_1268 == class_1268.field_5810) {
        try {
          if (paramclass_1799.method_7909() instanceof net.minecraft.class_1819) {
            try {
              if (je.D(new Object[0])) {
                try {
                  if (!((Boolean)je.m(new Object[0]).z()).booleanValue()) {
                    try {
                      if (((Boolean)je.w(new Object[0]).z()).booleanValue())
                        try {
                          if (paramclass_742.method_5998(class_1268.field_5808).method_7909() instanceof net.minecraft.class_1829) {
                            paramCallbackInfo.cancel();
                            return;
                          } 
                        } catch (x5 x5) {
                          throw a(null);
                        }  
                    } catch (x5 x5) {
                      throw a(null);
                    } 
                    return;
                  } 
                } catch (x5 x5) {
                  throw a(null);
                } 
              } else {
                return;
              } 
            } catch (x5 x5) {
              throw a(null);
            } 
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
      } else {
        return;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    paramCallbackInfo.cancel();
  }
  
  @Redirect(method = {"renderFirstPersonItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingItem()Z", ordinal = 1))
  private boolean hookIsUseItem(class_742 paramclass_742) {
    long l = a ^ 0x11D745DEDF11L;
    int i = (int)((l ^ 0x2791282BB572L) >>> 48L);
    int j = (int)((l ^ 0x2791282BB572L) << 16L >>> 32L);
    int k = (int)((l ^ 0x2791282BB572L) << 48L >>> 48L);
    l ^ 0x2791282BB572L;
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    class_1792 class_1792 = paramclass_742.method_6047().method_7909();
    try {
      if (class_1792 instanceof net.minecraft.class_1829)
        try {
          if (je.D(new Object[0]))
            try {
              if (((Boolean)je.w(new Object[0]).z()).booleanValue())
                try {
                  if (k0.J(new Object[] { null, null, null, Integer.valueOf(k), Integer.valueOf(j), Integer.valueOf((short)i), paramclass_742 }))
                    return true; 
                } catch (x5 x5) {
                  throw a(null);
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
    return paramclass_742.method_6115();
  }
  
  @Redirect(method = {"renderFirstPersonItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getItemUseTimeLeft()I", ordinal = 1))
  private int hookItemUseItem(class_742 paramclass_742) {
    // Byte code:
    //   0: getstatic wtf/opal/mixin/HeldItemRendererMixin.a : J
    //   3: ldc2_w 34002258382309
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 17516801161509
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 44713453102982
    //   20: lxor
    //   21: dup2
    //   22: bipush #48
    //   24: lushr
    //   25: l2i
    //   26: istore #6
    //   28: dup2
    //   29: bipush #16
    //   31: lshl
    //   32: bipush #32
    //   34: lushr
    //   35: l2i
    //   36: istore #7
    //   38: dup2
    //   39: bipush #48
    //   41: lshl
    //   42: bipush #48
    //   44: lushr
    //   45: l2i
    //   46: istore #8
    //   48: pop2
    //   49: pop2
    //   50: iconst_0
    //   51: anewarray java/lang/Object
    //   54: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   64: ldc wtf/opal/je
    //   66: iconst_1
    //   67: anewarray java/lang/Object
    //   70: dup_x1
    //   71: swap
    //   72: iconst_0
    //   73: swap
    //   74: aastore
    //   75: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   78: checkcast wtf/opal/je
    //   81: astore #9
    //   83: aload_1
    //   84: invokevirtual method_6047 : ()Lnet/minecraft/class_1799;
    //   87: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   90: astore #10
    //   92: aload #10
    //   94: instanceof net/minecraft/class_1829
    //   97: ifeq -> 259
    //   100: aload #9
    //   102: iconst_0
    //   103: anewarray java/lang/Object
    //   106: invokevirtual D : ([Ljava/lang/Object;)Z
    //   109: ifeq -> 259
    //   112: goto -> 119
    //   115: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: aload #9
    //   121: iconst_0
    //   122: anewarray java/lang/Object
    //   125: invokevirtual w : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   128: invokevirtual z : ()Ljava/lang/Object;
    //   131: checkcast java/lang/Boolean
    //   134: invokevirtual booleanValue : ()Z
    //   137: ifeq -> 259
    //   140: goto -> 147
    //   143: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   146: athrow
    //   147: aload_1
    //   148: iload #6
    //   150: i2s
    //   151: iload #7
    //   153: iload #8
    //   155: iconst_4
    //   156: anewarray java/lang/Object
    //   159: dup_x1
    //   160: swap
    //   161: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   164: iconst_3
    //   165: swap
    //   166: aastore
    //   167: dup_x1
    //   168: swap
    //   169: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   172: iconst_2
    //   173: swap
    //   174: aastore
    //   175: dup_x1
    //   176: swap
    //   177: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   180: iconst_1
    //   181: swap
    //   182: aastore
    //   183: dup_x1
    //   184: swap
    //   185: iconst_0
    //   186: swap
    //   187: aastore
    //   188: invokestatic J : ([Ljava/lang/Object;)Z
    //   191: ifeq -> 259
    //   194: goto -> 201
    //   197: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   200: athrow
    //   201: iconst_0
    //   202: anewarray java/lang/Object
    //   205: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   208: iconst_0
    //   209: anewarray java/lang/Object
    //   212: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   215: lload #4
    //   217: sipush #32028
    //   220: ldc2_w 5887908653214679645
    //   223: lload_2
    //   224: lxor
    //   225: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   230: iconst_2
    //   231: anewarray java/lang/Object
    //   234: dup_x1
    //   235: swap
    //   236: iconst_1
    //   237: swap
    //   238: aastore
    //   239: dup_x2
    //   240: dup_x2
    //   241: pop
    //   242: invokestatic valueOf : (J)Ljava/lang/Long;
    //   245: iconst_0
    //   246: swap
    //   247: aastore
    //   248: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   251: invokestatic parseInt : (Ljava/lang/String;)I
    //   254: ireturn
    //   255: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   258: athrow
    //   259: aload_1
    //   260: invokevirtual method_6014 : ()I
    //   263: ireturn
    // Exception table:
    //   from	to	target	type
    //   92	112	115	wtf/opal/x5
    //   100	140	143	wtf/opal/x5
    //   119	194	197	wtf/opal/x5
    //   147	255	255	wtf/opal/x5
  }
  
  @Redirect(method = {"renderFirstPersonItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getActiveHand()Lnet/minecraft/util/Hand;", ordinal = 1))
  private class_1268 hookActiveHand(class_742 paramclass_742) {
    long l = a ^ 0x3E3F94BA437DL;
    int i = (int)((l ^ 0x879F94F291EL) >>> 48L);
    int j = (int)((l ^ 0x879F94F291EL) << 16L >>> 32L);
    int k = (int)((l ^ 0x879F94F291EL) << 48L >>> 48L);
    l ^ 0x879F94F291EL;
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    class_1792 class_1792 = paramclass_742.method_6047().method_7909();
    try {
      if (class_1792 instanceof net.minecraft.class_1829)
        try {
          if (je.D(new Object[0]))
            try {
              if (((Boolean)je.w(new Object[0]).z()).booleanValue())
                try {
                  if (k0.J(new Object[] { null, null, null, Integer.valueOf(k), Integer.valueOf(j), Integer.valueOf((short)i), paramclass_742 }))
                    return class_1268.field_5808; 
                } catch (x5 x5) {
                  throw a(null);
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
    return paramclass_742.method_6058();
  }
  
  @Redirect(method = {"renderFirstPersonItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;", ordinal = 0))
  private class_1839 hookUseAction(class_1799 paramclass_1799) {
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    q q = (q)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { q.class });
    class_1792 class_1792 = paramclass_1799.method_7909();
    try {
      if (class_1792 instanceof net.minecraft.class_1829)
        try {
          if (je.D(new Object[0]))
            try {
              if (((Boolean)je.w(new Object[0]).z()).booleanValue())
                try {
                  if (q.D(new Object[0]))
                    try {
                      if (q.K(new Object[0]))
                        try {
                          if (q.Y(new Object[0]))
                            return class_1839.field_8949; 
                        } catch (x5 x5) {
                          throw a(null);
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
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return paramclass_1799.method_7976();
  }
  
  @ModifyArg(method = {"renderFirstPersonItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V", ordinal = 4), index = 2)
  private float applyEquipOffset(float paramFloat) {
    // Byte code:
    //   0: getstatic wtf/opal/mixin/HeldItemRendererMixin.a : J
    //   3: ldc2_w 77505095847598
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 96203937774190
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: iconst_0
    //   18: anewarray java/lang/Object
    //   21: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   24: iconst_0
    //   25: anewarray java/lang/Object
    //   28: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   31: ldc wtf/opal/je
    //   33: iconst_1
    //   34: anewarray java/lang/Object
    //   37: dup_x1
    //   38: swap
    //   39: iconst_0
    //   40: swap
    //   41: aastore
    //   42: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   45: checkcast wtf/opal/je
    //   48: astore #6
    //   50: aload #6
    //   52: iconst_0
    //   53: anewarray java/lang/Object
    //   56: invokevirtual D : ([Ljava/lang/Object;)Z
    //   59: ifeq -> 150
    //   62: aload #6
    //   64: iconst_0
    //   65: anewarray java/lang/Object
    //   68: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   71: invokevirtual z : ()Ljava/lang/Object;
    //   74: checkcast java/lang/Boolean
    //   77: invokevirtual booleanValue : ()Z
    //   80: ifne -> 150
    //   83: goto -> 90
    //   86: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: iconst_0
    //   91: anewarray java/lang/Object
    //   94: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   97: iconst_0
    //   98: anewarray java/lang/Object
    //   101: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   104: lload #4
    //   106: sipush #3981
    //   109: ldc2_w 3079386542360834950
    //   112: lload_2
    //   113: lxor
    //   114: <illegal opcode> g : (IJ)Ljava/lang/String;
    //   119: iconst_2
    //   120: anewarray java/lang/Object
    //   123: dup_x1
    //   124: swap
    //   125: iconst_1
    //   126: swap
    //   127: aastore
    //   128: dup_x2
    //   129: dup_x2
    //   130: pop
    //   131: invokestatic valueOf : (J)Ljava/lang/Long;
    //   134: iconst_0
    //   135: swap
    //   136: aastore
    //   137: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   140: invokestatic parseFloat : (Ljava/lang/String;)F
    //   143: fconst_1
    //   144: fadd
    //   145: freturn
    //   146: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   149: athrow
    //   150: fload_1
    //   151: freturn
    // Exception table:
    //   from	to	target	type
    //   50	83	86	wtf/opal/x5
    //   62	146	146	wtf/opal/x5
  }
  
  @Inject(method = {"renderFirstPersonItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V", ordinal = 3, shift = At.Shift.AFTER)})
  private void applyEatingAndDrinkingOffset(class_742 paramclass_742, float paramFloat1, float paramFloat2, class_1268 paramclass_1268, float paramFloat3, class_1799 paramclass_1799, float paramFloat4, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt, CallbackInfo paramCallbackInfo) {
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (b9.c.field_1724 != null)
        try {
          if (je.D(new Object[0]))
            try {
              if (((Boolean)je.w(new Object[0]).z()).booleanValue()) {
                try {
                  if (paramclass_742.field_6252) {
                    try {
                    
                    } catch (x5 x5) {
                      throw a(null);
                    } 
                    method_3217(paramclass_4587, (paramclass_742.method_6058() == class_1268.field_5808) ? paramclass_742.method_6068() : paramclass_742.method_6068().method_5928(), paramFloat3);
                  } 
                } catch (x5 x5) {
                  throw a(null);
                } 
                return;
              } 
              return;
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  @Inject(method = {"renderFirstPersonItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V", ordinal = 5, shift = At.Shift.AFTER)})
  private void applyBowOffset(class_742 paramclass_742, float paramFloat1, float paramFloat2, class_1268 paramclass_1268, float paramFloat3, class_1799 paramclass_1799, float paramFloat4, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt, CallbackInfo paramCallbackInfo) {
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (b9.c.field_1724 != null)
        try {
          if (je.D(new Object[0]))
            try {
              if (((Boolean)je.w(new Object[0]).z()).booleanValue()) {
                try {
                
                } catch (x5 x5) {
                  throw a(null);
                } 
                method_3217(paramclass_4587, (paramclass_742.method_6058() == class_1268.field_5808) ? paramclass_742.method_6068() : paramclass_742.method_6068().method_5928(), paramFloat3);
                return;
              } 
              return;
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  @Inject(method = {"renderFirstPersonItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")})
  private void applyItemTransformation(class_742 paramclass_742, float paramFloat1, float paramFloat2, class_1268 paramclass_1268, float paramFloat3, class_1799 paramclass_1799, float paramFloat4, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt, CallbackInfo paramCallbackInfo) {
    long l = a ^ 0x14E42268CF40L;
    int i = (int)((l ^ 0x22A24F9DA523L) >>> 48L);
    int j = (int)((l ^ 0x22A24F9DA523L) << 16L >>> 32L);
    int k = (int)((l ^ 0x22A24F9DA523L) << 48L >>> 48L);
    l ^ 0x22A24F9DA523L;
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (!je.D(new Object[0]))
        return; 
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (((Boolean)je.c(new Object[0]).z()).booleanValue()) {
        try {
          if (paramclass_1799.method_7909() instanceof net.minecraft.class_1753) {
            paramclass_4587.method_46416(0.0F, 0.05F, 0.04F);
            paramclass_4587.method_22905(0.93F, 1.0F, 1.0F);
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
        try {
          if (paramclass_1799.method_7909() instanceof net.minecraft.class_1787) {
            paramclass_4587.method_46416(0.08F, -0.027F, -0.33F);
            paramclass_4587.method_22905(0.93F, 1.0F, 1.0F);
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (((Boolean)je.w(new Object[0]).z()).booleanValue())
        try {
          if (paramclass_1799.method_7909() instanceof net.minecraft.class_1829)
            try {
              if (k0.J(new Object[] { null, null, null, Integer.valueOf(k), Integer.valueOf(j), Integer.valueOf((short)i), paramclass_742 })) {
                paramclass_4587.method_46416(-0.05F, 0.039F, -0.04F);
              } else {
                paramclass_4587.method_46416(-0.02F, 0.0F, 0.0F);
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
  }
  
  @Inject(method = {"renderFirstPersonItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")})
  private void applySwordBlockingTransformation(class_742 paramclass_742, float paramFloat1, float paramFloat2, class_1268 paramclass_1268, float paramFloat3, class_1799 paramclass_1799, float paramFloat4, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x6FF618EFB6A1L;
    long l2 = l1 ^ 0x6A852DE5D30FL;
    long l3 = l1 ^ 0x6F5F06A12FB7L;
    int i = (int)((l1 ^ 0x59B0751ADCC2L) >>> 48L);
    int j = (int)((l1 ^ 0x59B0751ADCC2L) << 16L >>> 32L);
    int k = (int)((l1 ^ 0x59B0751ADCC2L) << 48L >>> 48L);
    l1 ^ 0x59B0751ADCC2L;
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (je.D(new Object[0])) {
        try {
          if (!((Boolean)je.w(new Object[0]).z()).booleanValue())
            return; 
        } catch (x5 x5) {
          throw a(null);
        } 
      } else {
        return;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (!(paramclass_742.method_31548().method_7391().method_7909() instanceof net.minecraft.class_1829))
        return; 
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (k0.J(new Object[] { null, null, null, Integer.valueOf(k), Integer.valueOf(j), Integer.valueOf((short)i), paramclass_742 }))
        try {
          if (paramclass_742.method_5998(paramclass_1268).method_7909() instanceof net.minecraft.class_1829) {
            try {
              (new Object[3])[2] = paramclass_4587;
              (new Object[3])[1] = paramclass_742;
              new Object[3];
              je.g(new Object[] { Long.valueOf(l3) });
            } catch (x5 x5) {
              throw a(null);
            } 
            (new Object[4])[3] = Float.valueOf(paramFloat3);
            (new Object[4])[2] = (paramclass_1268 == class_1268.field_5808) ? paramclass_742.method_6068() : paramclass_742.method_6068().method_5928();
            new Object[4];
            je.w(new Object[] { null, Long.valueOf(l2), paramclass_4587 });
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (paramclass_742.method_6030().method_7909() instanceof net.minecraft.class_1819) {
        try {
          if (paramclass_1268 != class_1268.field_5808)
            return; 
        } catch (x5 x5) {
          throw a(null);
        } 
        (new Object[3])[2] = paramclass_4587;
        (new Object[3])[1] = paramclass_742;
        new Object[3];
        je.g(new Object[] { Long.valueOf(l3) });
        (new Object[4])[3] = Float.valueOf(paramFloat3);
        (new Object[4])[2] = paramclass_742.method_6068();
        new Object[4];
        je.w(new Object[] { null, Long.valueOf(l2), paramclass_4587 });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  @Inject(at = {@At("HEAD")}, cancellable = true, method = {"renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"})
  private void hideShield(class_1309 paramclass_1309, class_1799 paramclass_1799, class_811 paramclass_811, boolean paramBoolean, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt, CallbackInfo paramCallbackInfo) {
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (je.D(new Object[0])) {
        try {
          if (paramclass_1799.method_7909() instanceof net.minecraft.class_1819) {
            try {
              if (!((Boolean)je.m(new Object[0]).z()).booleanValue()) {
                try {
                  if (((Boolean)je.w(new Object[0]).z()).booleanValue())
                    try {
                      if (paramclass_1309.method_6047().method_7909() instanceof net.minecraft.class_1829) {
                        paramCallbackInfo.cancel();
                        return;
                      } 
                    } catch (x5 x5) {
                      throw a(null);
                    }  
                } catch (x5 x5) {
                  throw a(null);
                } 
                return;
              } 
            } catch (x5 x5) {
              throw a(null);
            } 
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
      } else {
        return;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    paramCallbackInfo.cancel();
  }
  
  @ModifyArg(method = {"updateHeldItems"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F", ordinal = 2), index = 0)
  private float modifyEquipProgressMainhand(float paramFloat) {
    // Byte code:
    //   0: getstatic wtf/opal/mixin/HeldItemRendererMixin.a : J
    //   3: ldc2_w 133871722684520
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 87441734928907
    //   13: lxor
    //   14: dup2
    //   15: bipush #48
    //   17: lushr
    //   18: l2i
    //   19: istore #4
    //   21: dup2
    //   22: bipush #16
    //   24: lshl
    //   25: bipush #32
    //   27: lushr
    //   28: l2i
    //   29: istore #5
    //   31: dup2
    //   32: bipush #48
    //   34: lshl
    //   35: bipush #48
    //   37: lushr
    //   38: l2i
    //   39: istore #6
    //   41: pop2
    //   42: pop2
    //   43: iconst_0
    //   44: anewarray java/lang/Object
    //   47: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   50: iconst_0
    //   51: anewarray java/lang/Object
    //   54: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   57: ldc wtf/opal/je
    //   59: iconst_1
    //   60: anewarray java/lang/Object
    //   63: dup_x1
    //   64: swap
    //   65: iconst_0
    //   66: swap
    //   67: aastore
    //   68: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   71: checkcast wtf/opal/je
    //   74: astore #7
    //   76: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   79: getfield field_1724 : Lnet/minecraft/class_746;
    //   82: fconst_1
    //   83: invokevirtual method_7261 : (F)F
    //   86: fstore #8
    //   88: aload #7
    //   90: iconst_0
    //   91: anewarray java/lang/Object
    //   94: invokevirtual D : ([Ljava/lang/Object;)Z
    //   97: ifeq -> 195
    //   100: aload #7
    //   102: iconst_0
    //   103: anewarray java/lang/Object
    //   106: invokevirtual w : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   109: invokevirtual z : ()Ljava/lang/Object;
    //   112: checkcast java/lang/Boolean
    //   115: invokevirtual booleanValue : ()Z
    //   118: ifeq -> 195
    //   121: goto -> 128
    //   124: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   127: athrow
    //   128: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   131: getfield field_1724 : Lnet/minecraft/class_746;
    //   134: iload #4
    //   136: i2s
    //   137: iload #5
    //   139: iload #6
    //   141: iconst_4
    //   142: anewarray java/lang/Object
    //   145: dup_x1
    //   146: swap
    //   147: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   150: iconst_3
    //   151: swap
    //   152: aastore
    //   153: dup_x1
    //   154: swap
    //   155: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   158: iconst_2
    //   159: swap
    //   160: aastore
    //   161: dup_x1
    //   162: swap
    //   163: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   166: iconst_1
    //   167: swap
    //   168: aastore
    //   169: dup_x1
    //   170: swap
    //   171: iconst_0
    //   172: swap
    //   173: aastore
    //   174: invokestatic J : ([Ljava/lang/Object;)Z
    //   177: ifeq -> 195
    //   180: goto -> 187
    //   183: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   186: athrow
    //   187: fconst_1
    //   188: goto -> 203
    //   191: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   194: athrow
    //   195: fload #8
    //   197: fload #8
    //   199: fmul
    //   200: fload #8
    //   202: fmul
    //   203: fstore #9
    //   205: aload_0
    //   206: getfield field_4047 : Lnet/minecraft/class_1799;
    //   209: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   212: getfield field_1724 : Lnet/minecraft/class_746;
    //   215: invokevirtual method_6047 : ()Lnet/minecraft/class_1799;
    //   218: if_acmpne -> 230
    //   221: fload #9
    //   223: goto -> 231
    //   226: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   229: athrow
    //   230: fconst_0
    //   231: aload_0
    //   232: getfield field_4043 : F
    //   235: fsub
    //   236: freturn
    // Exception table:
    //   from	to	target	type
    //   88	121	124	wtf/opal/x5
    //   100	180	183	wtf/opal/x5
    //   128	191	191	wtf/opal/x5
    //   205	226	226	wtf/opal/x5
  }
  
  @Inject(method = {"resetEquipProgress"}, at = {@At("HEAD")}, cancellable = true)
  private void hookResetEquipProgress(CallbackInfo paramCallbackInfo) {
    long l = a ^ 0x36DDFD885404L;
    int i = (int)((l ^ 0x9B907D3E67L) >>> 48L);
    int j = (int)((l ^ 0x9B907D3E67L) << 16L >>> 32L);
    int k = (int)((l ^ 0x9B907D3E67L) << 48L >>> 48L);
    l ^ 0x9B907D3E67L;
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (je.D(new Object[0]))
        try {
          if (((Boolean)je.w(new Object[0]).z()).booleanValue()) {
            try {
              if (k0.J(new Object[] { null, null, null, Integer.valueOf(k), Integer.valueOf(j), Integer.valueOf((short)i), b9.c.field_1724 }))
                paramCallbackInfo.cancel(); 
            } catch (x5 x5) {
              throw a(null);
            } 
            return;
          } 
          return;
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  static {
    long l = a ^ 0x3D9EED983A5EL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "~âfj9Îc\fla°#é¶dà\020\033p¬VæÐpv+Î=iA ").length();
    byte b2 = 16;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2CF8;
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
        throw new RuntimeException("wtf/opal/mixin/HeldItemRendererMixin", exception);
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
    //   66: ldc_w 'wtf/opal/mixin/HeldItemRendererMixin'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\HeldItemRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */