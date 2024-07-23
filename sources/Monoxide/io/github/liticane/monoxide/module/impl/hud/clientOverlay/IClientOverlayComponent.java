package io.github.liticane.monoxide.module.impl.hud.clientOverlay;

import io.github.liticane.monoxide.listener.event.minecraft.render.Render2DEvent;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;

public interface IClientOverlayComponent {

    void draw(Render2DEvent render2DEvent, AtomicFloat leftY, AtomicFloat rightY);

    int getPriority();
}
