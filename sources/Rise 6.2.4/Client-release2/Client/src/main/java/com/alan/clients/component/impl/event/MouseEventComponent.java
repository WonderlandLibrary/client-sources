package com.alan.clients.component.impl.event;

import com.alan.clients.Client;
import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MouseInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import org.lwjgl.input.Mouse;

public class MouseEventComponent extends Component {
    int[] inputs = {0, 1, 2, 3, 4, 5};
    boolean[] downs = {false, false, false, false, false, false};

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        for (int input : inputs) {
            if (Mouse.isButtonDown(input)) {
                if (!downs[input]) Client.INSTANCE.getEventBus().handle(new MouseInputEvent(input));
                downs[input] = true;
            } else {
                downs[input] = false;
            }
        }
    };
}
