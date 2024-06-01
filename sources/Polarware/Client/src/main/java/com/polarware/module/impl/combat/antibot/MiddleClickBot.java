package com.polarware.module.impl.combat.antibot;

import com.polarware.Client;
import com.polarware.component.impl.target.BotComponent;
import com.polarware.module.impl.combat.AntiBotModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

public final class MiddleClickBot extends Mode<AntiBotModule>  {

    private boolean down;

    public MiddleClickBot(String name, AntiBotModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (Mouse.isButtonDown(2)) {
            if (down) return;
            down = true;

            if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                BotComponent botComponent = Client.INSTANCE.getBotComponent();
                Entity entity = mc.objectMouseOver.entityHit;

                if (botComponent.contains(entity)) {
                    Client.INSTANCE.getBotComponent().remove(entity);
                } else {
                    Client.INSTANCE.getBotComponent().add(entity);
                }
            }
        } else down = false;
    };
}