package com.boomer.client.module.modules.other;

import java.awt.Color;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.game.TickEvent;
import com.boomer.client.module.Module;

import net.minecraft.entity.item.EntityItem;

/**
 * made by Xen for BoomerWare
 *
 * @since 6/10/2019
 **/
public class NoRender extends Module {

    public NoRender() {
        super("NoRender", Category.OTHER, new Color(0).getRGB());
        setDescription("Dont render items");
        setRenderlabel("No Render");
    }

    @Handler
    public void onTick(TickEvent event) {
        if (mc.theWorld != null) {
            mc.theWorld.loadedEntityList.forEach(e -> {
            	if (e instanceof EntityItem) {
            		mc.theWorld.removeEntity(e);
            	}
            });
        }
    }
}
