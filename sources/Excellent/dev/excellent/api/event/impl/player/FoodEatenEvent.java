package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.item.ItemStack;

@Getter
@AllArgsConstructor
public class FoodEatenEvent extends Event {
    private final ItemStack stack;
}