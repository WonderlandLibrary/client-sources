package dev.vertic.event.impl.render;

import dev.vertic.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public class Render2DEvent extends Event {

    private float partialTicks;

}
