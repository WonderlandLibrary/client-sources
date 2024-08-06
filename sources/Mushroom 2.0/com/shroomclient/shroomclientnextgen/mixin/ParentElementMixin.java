package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenClickedEvent;
import net.minecraft.client.gui.ParentElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParentElement.class)
public interface ParentElementMixin {
    @Inject(at = @At("TAIL"), method = "mouseClicked")
    default void mouseClicked(
        double mouseX,
        double mouseY,
        int button,
        CallbackInfoReturnable<Boolean> cir
    ) {
        Bus.post(new ScreenClickedEvent(mouseX, mouseY, button, true));
    }

    @Inject(at = @At("TAIL"), method = "mouseReleased")
    default void mouseReleased(
        double mouseX,
        double mouseY,
        int button,
        CallbackInfoReturnable<Boolean> cir
    ) {
        Bus.post(new ScreenClickedEvent(mouseX, mouseY, button, false));
    }
}
