package lol.main.events;

import lol.base.addons.EventAddon;
import lombok.AllArgsConstructor;
import net.minecraft.client.gui.ScaledResolution;

@AllArgsConstructor
public class Render2DEvent extends EventAddon {
    public ScaledResolution scaledResolution;
    public float partialTicks;
}
