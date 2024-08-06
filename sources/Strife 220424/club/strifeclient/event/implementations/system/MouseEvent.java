package club.strifeclient.event.implementations.system;

import club.strifeclient.event.Event;
import lombok.AllArgsConstructor;
import net.minecraft.util.MovingObjectPosition;

@AllArgsConstructor
public class MouseEvent extends Event {
    public final int button;
    public MovingObjectPosition objectMouseOver;
}
