package com.alan.clients.event.impl.render;

import com.alan.clients.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.item.ItemStack;

@Getter
@Setter
@AllArgsConstructor
public final class ModelVisibilityEvent implements Event {
    private final ItemStack itemStack;
    private int heldItemRight;
}
