package best.actinium.module.impl.visual;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.event.impl.render.Render3DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.NumberProperty;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
@ModuleInfo(
        name = "Ambience",
        description = "Changes the world time.",
        category = ModuleCategory.VISUAL
)
public class AmbienceModule extends Module {
    private final NumberProperty time = new NumberProperty("Time", this, 0, 0, 22999, 1);
    private final NumberProperty speed = new NumberProperty("Time Speed", this, 0, 0, 20, 1);

    @Override
    public void onEnable() {
        mc.theWorld.setRainStrength(0);
        mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
        mc.theWorld.getWorldInfo().setRainTime(0);
        mc.theWorld.getWorldInfo().setThunderTime(0);
        mc.theWorld.getWorldInfo().setRaining(false);
        mc.theWorld.getWorldInfo().setThundering(false);
        super.onEnable();
    }

    @Callback
    public void onRender3D(Render3DEvent event) {
        mc.theWorld.setWorldTime((time.getValue().intValue() + (System.currentTimeMillis() * speed.getValue().intValue())));
    }

    @Callback
    public void onMotion(MotionEvent event) {
        if (mc.thePlayer.ticksExisted % 20 == 0) {
            mc.theWorld.setRainStrength(0);
            mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
            mc.theWorld.getWorldInfo().setRainTime(0);
            mc.theWorld.getWorldInfo().setThunderTime(0);
            mc.theWorld.getWorldInfo().setRaining(false);
            mc.theWorld.getWorldInfo().setThundering(false);
        }
    }

    @Callback
    public void onPacket(PacketEvent event) {
        if(event.getType() == EventType.INCOMING) {
            if (event.getPacket() instanceof S03PacketTimeUpdate) {
                event.setCancelled(true);
            } else if (event.getPacket() instanceof S2BPacketChangeGameState) {
                S2BPacketChangeGameState s2b = (S2BPacketChangeGameState) event.getPacket();

                if (s2b.getGameState() == 1 || s2b.getGameState() == 2) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
