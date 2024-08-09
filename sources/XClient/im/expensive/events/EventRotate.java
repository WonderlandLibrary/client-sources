package im.expensive.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRotate extends CancelEvent {
    private double yaw, pitch;
}
