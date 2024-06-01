package com.polarware.module.impl.render;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.render.keystrokes.KeyStroke;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.util.render.RenderUtil;
import com.polarware.util.vector.Vector2d;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.DragValue;

import java.util.ArrayList;

@ModuleInfo(name = "module.render.keystrokes.name", description = "module.render.keystrokes.description", category = Category.RENDER)
public final class KeyStrokesModule extends Module {

    private final DragValue position = new DragValue("Position", this, new Vector2d(100, 100), false);
    private final BooleanValue space = new BooleanValue("Space", this, true);
    private boolean lastSpace;
    private final int gap = 3;

    private ArrayList<KeyStroke> keyStrokes = new ArrayList<KeyStroke>();

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (mc.currentScreen != null) {
            if (lastSpace != space.getValue()) {
                keyStrokes = new ArrayList<KeyStroke>() {{
                    add(new KeyStroke(new Vector2f(RenderUtil.GENERIC_SCALE + gap, 0), mc.gameSettings.keyBindForward));
                    add(new KeyStroke(new Vector2f(0, RenderUtil.GENERIC_SCALE + gap), mc.gameSettings.keyBindLeft));
                    add(new KeyStroke(new Vector2f(RenderUtil.GENERIC_SCALE * 2 + gap * 2, RenderUtil.GENERIC_SCALE + gap), mc.gameSettings.keyBindRight));
                    add(new KeyStroke(new Vector2f(RenderUtil.GENERIC_SCALE + gap, RenderUtil.GENERIC_SCALE + gap), mc.gameSettings.keyBindBack));
                    if (space.getValue()) {
                        add(new KeyStroke(new Vector2f(RenderUtil.GENERIC_SCALE * 3 + gap * 2, RenderUtil.GENERIC_SCALE), new Vector2f(0, (RenderUtil.GENERIC_SCALE + gap) * 2), "Space", mc.gameSettings.keyBindJump));
                    }
                }};
            }

            lastSpace = space.getValue();
        }

        // Setting scale for draggable element
        position.setScale(new Vector2d(RenderUtil.GENERIC_SCALE * 3 + gap * 2, RenderUtil.GENERIC_SCALE * 3 + gap * 2));

        NORMAL_RENDER_RUNNABLES.add(() -> keyStrokes.forEach(keyStroke -> keyStroke.render(position.position)));
        NORMAL_BLUR_RUNNABLES.add(() -> keyStrokes.forEach(keyStroke -> keyStroke.blur(position.position)));
        NORMAL_POST_BLOOM_RUNNABLES.add(() -> keyStrokes.forEach(keyStroke -> keyStroke.bloom(position.position)));
    };
}