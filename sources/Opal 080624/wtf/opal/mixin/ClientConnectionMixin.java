package wtf.opal.mixin;

import io.netty.channel.ChannelHandlerContext;
import java.lang.invoke.MethodHandles;
import net.minecraft.class_2535;
import net.minecraft.class_2596;
import net.minecraft.class_2598;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.d1;
import wtf.opal.lu;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin({class_2535.class})
public final class ClientConnectionMixin {
  @Shadow
  @Final
  private class_2598 field_11643;
  
  private static final long a = on.a(8584839316705025123L, -1819290591695790017L, MethodHandles.lookup().lookupClass()).a(159650580804588L);
  
  @Inject(at = {@At("HEAD")}, method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V"}, cancellable = true)
  private void channelRead0(ChannelHandlerContext paramChannelHandlerContext, class_2596<?> paramclass_2596, CallbackInfo paramCallbackInfo) {
    long l1 = a ^ 0x2101C5C69420L;
    long l2 = l1 ^ 0x47B820B62826L;
    long l3 = l1 ^ 0x2ED3500F5371L;
    if (this.field_11643 == class_2598.field_11942) {
      lu lu = new lu(l3, paramclass_2596);
      try {
        (new Object[2])[1] = lu;
        new Object[2];
        d1.q(new Object[0]).q(new Object[0]).N(new Object[] { Long.valueOf(l2) });
        if (lu.X(new Object[0]))
          paramCallbackInfo.cancel(); 
      } catch (x5 x5) {
        throw a(null);
      } 
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ClientConnectionMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */