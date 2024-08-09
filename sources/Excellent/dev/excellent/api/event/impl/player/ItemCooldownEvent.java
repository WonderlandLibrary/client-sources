package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.CancellableEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.item.Item;

@Getter
@RequiredArgsConstructor
public class ItemCooldownEvent extends CancellableEvent {
    public final Item item;
    @Setter
    public float cooldown;
}
