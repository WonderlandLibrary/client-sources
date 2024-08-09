package ez.h.features.visual;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class MotionGraph extends Feature
{
    public static OptionColor color;
    ez.h.ui.hudeditor.registry.MotionGraph motionGraph;
    
    public MotionGraph() {
        super("MotionGraph", "\u0412\u0438\u0437\u0443\u0430\u043b\u0438\u0437\u0438\u0440\u0443\u0435\u0442 \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u043d\u0430 \u044d\u043a\u0440\u0430\u043d\u0435.", Category.VISUAL);
        this.motionGraph = new ez.h.ui.hudeditor.registry.MotionGraph();
        MotionGraph.color = new OptionColor(this, "Color", new Color(8216384 + 12457002 - 6409331 + 2447880));
        this.addOptions(MotionGraph.color);
    }
    
    @EventTarget
    public void render(final EventRenderDraggable eventRenderDraggable) {
        this.motionGraph.render(eventRenderDraggable.mouseX, eventRenderDraggable.mouseY, eventRenderDraggable.ticks, false);
    }
}
