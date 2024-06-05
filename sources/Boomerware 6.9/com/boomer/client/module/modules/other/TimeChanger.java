package com.boomer.client.module.modules.other;

import java.awt.Color;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.game.TickEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.value.impl.BooleanValue;
import com.boomer.client.utils.value.impl.NumberValue;

/**
 * made by oHare for BoomerWare
 *
 * @since 7/18/2019
 **/
public class TimeChanger extends Module {
    public NumberValue<Integer> time = new NumberValue<>("Time", 18400, 0, 24000, 100);
    public BooleanValue cool = new BooleanValue("Cool", false);
    public NumberValue<Integer> coolinc = new NumberValue<>("CoolInc", 100, 0, 1000, 10);
    public int cooltime;

    public TimeChanger() {
        super("TimeChanger", Category.OTHER, new Color(0x477AFF).getRGB());
        setRenderlabel("Time Changer");
        addValues(time, cool,coolinc);
    }

    @Handler
    public void onTick(TickEvent event) {
        if (mc.theWorld != null) {
            if (cool.isEnabled()) {
                if (cooltime < 24000) {
                    cooltime += coolinc.getValue();
                } else {
                    cooltime = 0;
                }
            }
        }
    }

    @Override
    public void onEnable() {
        cooltime = 0;
    }
}
