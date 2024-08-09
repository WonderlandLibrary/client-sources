package dev.darkmoon.client.event.render;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventOverlay extends EventCancellable {
    private final OverlayType overlayType;
    public enum OverlayType {
        TOTEM_ANIMATION, FIRE, BOSS_BAR, FOG, SCOREBOARD, HURT_CAM, WEATHER, ARMOR, PARTICLES, SKYLIGHT, CAMERA_CLIP
    }
}
