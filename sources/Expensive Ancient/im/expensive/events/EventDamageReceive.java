package im.expensive.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EventDamageReceive {
    private final DamageType damageType;

    public enum DamageType {
        FALL,
        ARROW,
        ENDER_PEARL
    }
}