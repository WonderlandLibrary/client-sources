package im.expensive.events;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.minecraft.item.Item;


@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
public class EventCooldown {
    Item item;
    CooldownType cooldownType;


    public boolean isAdded() {
        return cooldownType == CooldownType.ADDED;
    }

    public boolean isRemoved() {
        return cooldownType == CooldownType.REMOVED;
    }

    public enum CooldownType {
        ADDED, REMOVED
    }
}
