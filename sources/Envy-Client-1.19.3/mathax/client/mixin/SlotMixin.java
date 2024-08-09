package mathax.client.mixin;

import mathax.client.mixininterface.ISlot;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Slot.class)
public class SlotMixin implements ISlot {
    @Shadow
    public int id;

    @Shadow
    @Final
    private int index;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
