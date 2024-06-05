package digital.rbq.addon.api.event.events;

import lombok.Getter;
import lombok.Setter;
import digital.rbq.addon.api.event.AddonEvent;

public class EventPreUpdate extends AddonEvent {
    @Getter @Setter
    private double x, y, z;
    @Getter @Setter
    private float yaw, pitch;
    @Getter @Setter
    private boolean onGround;
}
