package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.item.ItemStack;

@Getter
@AllArgsConstructor
public class HandInteractionEvent extends CancellableEvent {
    private final ItemStack stack;
}
