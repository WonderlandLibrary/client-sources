package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.render.Render3DEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SUpdateTimePacket;

@ModuleInfo(name = "World Time", description = "Изменяет время суток на сервере.", category = Category.RENDER)
public final class WorldTime extends Module {

    private final NumberValue time = new NumberValue("Время", this, 0, 0, 24000, 1);
    private final NumberValue speed = new NumberValue("Ускорение времени", this, 0, 0, 20, 1);

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.world.setRainStrength(0);
        mc.world.getWorldInfo().setRaining(false);
    }

    private final Listener<Render3DEvent> onRenderWorld = event -> {
        mc.world.setDayTime((time.getValue().intValue() + (System.currentTimeMillis() * speed.getValue().intValue())));
    };

    public final Listener<MotionEvent> onMotion = event -> {
        if (mc.player.ticksExisted % 20 == 0) {
            mc.world.setRainStrength(0);
            mc.world.getWorldInfo().setRaining(false);
        }
    };

    public final Listener<PacketEvent> onPacket = event -> {
        if (event.isReceive()) {
            if (event.getPacket() instanceof SUpdateTimePacket) {
                event.setCancelled(true);
            } else if (event.getPacket() instanceof SChangeGameStatePacket s2b) {
                if (s2b.getState() == SChangeGameStatePacket.field_241765_b_ || s2b.getState() == SChangeGameStatePacket.field_241766_c_) {
                    event.setCancelled(true);
                }
            }
        }
    };
}