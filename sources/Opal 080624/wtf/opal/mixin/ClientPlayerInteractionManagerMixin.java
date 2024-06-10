package wtf.opal.mixin;

import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_636;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.opal.x5;

@Mixin({class_636.class})
public final class ClientPlayerInteractionManagerMixin {
  @Redirect(method = {"interactBlockInternal"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
  private class_1799 redirectInteractBlockInternal(class_746 paramclass_746, class_1268 paramclass_1268) {
    // Byte code:
    //   0: iconst_0
    //   1: anewarray java/lang/Object
    //   4: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   7: iconst_0
    //   8: anewarray java/lang/Object
    //   11: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   14: ldc wtf/opal/xw
    //   16: iconst_1
    //   17: anewarray java/lang/Object
    //   20: dup_x1
    //   21: swap
    //   22: iconst_0
    //   23: swap
    //   24: aastore
    //   25: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   28: checkcast wtf/opal/xw
    //   31: astore_3
    //   32: aload_3
    //   33: iconst_0
    //   34: anewarray java/lang/Object
    //   37: invokevirtual D : ([Ljava/lang/Object;)Z
    //   40: ifeq -> 88
    //   43: aload_3
    //   44: iconst_0
    //   45: anewarray java/lang/Object
    //   48: invokevirtual G : ([Ljava/lang/Object;)Z
    //   51: ifeq -> 88
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: aload_3
    //   62: iconst_0
    //   63: anewarray java/lang/Object
    //   66: invokevirtual H : ([Ljava/lang/Object;)I
    //   69: iconst_m1
    //   70: if_icmpeq -> 88
    //   73: goto -> 80
    //   76: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   79: athrow
    //   80: iconst_1
    //   81: goto -> 89
    //   84: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: iconst_0
    //   89: istore #4
    //   91: iload #4
    //   93: ifeq -> 118
    //   96: aload_1
    //   97: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   100: aload_3
    //   101: iconst_0
    //   102: anewarray java/lang/Object
    //   105: invokevirtual H : ([Ljava/lang/Object;)I
    //   108: invokevirtual method_5438 : (I)Lnet/minecraft/class_1799;
    //   111: goto -> 123
    //   114: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   117: athrow
    //   118: aload_1
    //   119: aload_2
    //   120: invokevirtual method_5998 : (Lnet/minecraft/class_1268;)Lnet/minecraft/class_1799;
    //   123: areturn
    // Exception table:
    //   from	to	target	type
    //   32	54	57	wtf/opal/x5
    //   43	73	76	wtf/opal/x5
    //   61	84	84	wtf/opal/x5
    //   91	114	114	wtf/opal/x5
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ClientPlayerInteractionManagerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */