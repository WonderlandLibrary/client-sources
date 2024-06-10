package wtf.opal.mixin;

import net.minecraft.class_636;
import net.minecraft.class_638;
import net.minecraft.class_7204;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({class_636.class})
public interface ClientPlayerInteractionManagerAccessor {
  @Invoker
  void callSendSequencedPacket(class_638 paramclass_638, class_7204 paramclass_7204);
  
  @Invoker
  void callSyncSelectedSlot();
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\ClientPlayerInteractionManagerAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */