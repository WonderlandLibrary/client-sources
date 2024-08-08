package lol.point.returnclient.events.impl.render;

import lol.point.returnclient.events.Event;
import lombok.AllArgsConstructor;
import net.minecraft.client.renderer.ItemRenderer;

@AllArgsConstructor
public class EventRenderSwordUse extends Event {
    public ItemRenderer itemRenderer;
    public float equippedProgress, renderSwingProgress, realSwingProgress;
}
