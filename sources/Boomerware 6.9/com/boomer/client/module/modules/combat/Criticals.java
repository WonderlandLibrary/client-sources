package com.boomer.client.module.modules.combat;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.value.impl.EnumValue;
import com.sun.xml.internal.ws.util.StringUtils;

import java.awt.*;

public class Criticals extends Module {

    public EnumValue<mode> Mode = new EnumValue<>("Mode",mode.HYPIXEL);
    public Criticals() {
        super("Criticals", Category.COMBAT, new Color(0xA4A29E).getRGB());
        setDescription("crit those sigma users");
        addValues(Mode);
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        setSuffix(StringUtils.capitalize(Mode.getValue().name().toLowerCase()));
    }

    public enum mode {
        HYPIXEL,NCP,AREA51
    }
}
