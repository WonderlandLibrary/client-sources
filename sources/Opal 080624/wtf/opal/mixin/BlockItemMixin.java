package wtf.opal.mixin;

import net.minecraft.class_1747;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import wtf.opal.x5;

@Mixin({class_1747.class})
public final class BlockItemMixin {
  @Redirect(method = {"place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemPlacementContext;getStack()Lnet/minecraft/item/ItemStack;"))
  private class_1799 place(class_1750 paramclass_1750) {
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
    //   31: astore_2
    //   32: aload_2
    //   33: iconst_0
    //   34: anewarray java/lang/Object
    //   37: invokevirtual D : ([Ljava/lang/Object;)Z
    //   40: ifeq -> 88
    //   43: aload_2
    //   44: iconst_0
    //   45: anewarray java/lang/Object
    //   48: invokevirtual G : ([Ljava/lang/Object;)Z
    //   51: ifeq -> 88
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: aload_2
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
    //   89: istore_3
    //   90: iload_3
    //   91: ifeq -> 139
    //   94: aload_1
    //   95: invokevirtual method_8036 : ()Lnet/minecraft/class_1657;
    //   98: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   101: getfield field_1724 : Lnet/minecraft/class_746;
    //   104: if_acmpne -> 139
    //   107: goto -> 114
    //   110: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: aload_1
    //   115: invokevirtual method_8036 : ()Lnet/minecraft/class_1657;
    //   118: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   121: aload_2
    //   122: iconst_0
    //   123: anewarray java/lang/Object
    //   126: invokevirtual H : ([Ljava/lang/Object;)I
    //   129: invokevirtual method_5438 : (I)Lnet/minecraft/class_1799;
    //   132: goto -> 143
    //   135: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   138: athrow
    //   139: aload_1
    //   140: invokevirtual method_8041 : ()Lnet/minecraft/class_1799;
    //   143: areturn
    // Exception table:
    //   from	to	target	type
    //   32	54	57	wtf/opal/x5
    //   43	73	76	wtf/opal/x5
    //   61	84	84	wtf/opal/x5
    //   90	107	110	wtf/opal/x5
    //   94	135	135	wtf/opal/x5
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\BlockItemMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */