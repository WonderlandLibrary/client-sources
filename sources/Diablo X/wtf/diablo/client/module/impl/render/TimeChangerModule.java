package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.client.renderering.WorldTimeEvent;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(
        name = "Time Changer",
        description = "Changes the time of day.",
        category = ModuleCategoryEnum.RENDER)
public final class TimeChangerModule extends AbstractModule {
    private final NumberSetting<Long> time = new NumberSetting<>("Time", 20000L, 0L, 24000L, 500L);

    public TimeChangerModule() {
        this.registerSettings(time);
    }

    @EventHandler
    private final Listener<WorldTimeEvent> updateListener = e -> {
        this.setSuffix(time.getValue().toString());
        e.setTime(time.getValue());
    };

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        if (e.getEventType() == EventTypeEnum.PRE) {
            mc.theWorld.setWorldTime(time.getValue());
        }
    };

    @EventHandler
    private final Listener<RecievePacketEvent> packetEventListener = e -> {
        if (e.getPacket() instanceof S03PacketTimeUpdate) {
            e.setCancelled(true);
        }
    };

}
