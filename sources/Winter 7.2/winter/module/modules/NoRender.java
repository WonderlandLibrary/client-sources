/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class NoRender
extends Module {
    public NoRender() {
        super("NoRender", Module.Category.Render, -8039);
        this.setBind(0);
    }

    @Override
    public void onDisable() {
        this.mc.renderGlobal.loadRenderers();
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        for (Object o : this.mc.theWorld.loadedEntityList) {
            if (!(o instanceof EntityItem)) continue;
            EntityItem i2 = (EntityItem)o;
            this.mc.theWorld.removeEntity(i2);
        }
    }
}

