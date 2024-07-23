package io.github.liticane.monoxide.listener.event.minecraft.player.movement;

import io.github.liticane.monoxide.listener.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class PlayerJumpEvent extends Event {
    private float yaw;
    private double height;
}