package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.movement.Timer;
import dev.darkmoon.client.module.impl.render.Hud;
import dev.darkmoon.client.utility.math.MathUtility;
import dev.darkmoon.client.utility.misc.TimerHelper;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketTimeUpdate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;

@ModuleAnnotation(name = "TPSSync", category = Category.UTIL)
public class TPSSync extends Module {
    private final TimerHelper timeDelay = new TimerHelper();
    private final ArrayDeque<Float> tpsResult = new ArrayDeque<>(20);
    private long time;
    private static long tickTime;
    public static float TICK_TIMER = 1f;
    private static float tps;

    public static float getTPS() {
        return round2(tps);
    }

    public static float getTPS2() {
        return round2(20.0f * ((float) tickTime / 1000f));
    }

    public static float round2(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    @EventTarget
    public void onPacketReceive(EventReceivePacket event) {
        if (!(event.getPacket() instanceof SPacketChat)) {
            timeDelay.reset();
        }
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            if (time != 0L) {
                tickTime = System.currentTimeMillis() - time;

                if (tpsResult.size() > 20)
                    tpsResult.poll();

                tpsResult.add(20.0f * (1000.0f / (float) (tickTime)));

                float average = 0.0f;

                for (Float value : tpsResult) average += MathUtility.clamp(value, 0f, 20f);

                tps = average / (float) tpsResult.size();
            }
            time = System.currentTimeMillis();
        }
    }
    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (DarkMoon.getInstance().getModuleManager().getModule(Timer.class).isEnabled()) return;
        if (getTPS() > 1)
            TICK_TIMER = getTPS() / 20f;
        else TICK_TIMER = 1f;
    }

    @Override
    public void onDisable() {
        TICK_TIMER = 1f;
        Hud.TPSSyncMessageDisplayed = false;
        super.onDisable();
    }
}
