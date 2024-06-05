package digital.rbq.addon.api.event.events;

import lombok.Getter;
import lombok.Setter;
import digital.rbq.addon.api.event.AddonEvent;

public class Event2DRender extends AddonEvent {
    @Getter @Setter
    private float particalTicks;
}
