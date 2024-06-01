package best.actinium.event.impl.render;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@Setter
@AllArgsConstructor
public class Render2DEvent extends Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;
}
