package cc.slack.features.modules.impl.other;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.utils.other.PrintUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;


@ModuleInfo(
        name = "Warns",
        category = Category.OTHER
)
public class Warns extends Module {

    private final BooleanValue healthWarn = new BooleanValue("Health Warn",true);
    private final NumberValue<Integer> healthValue = new NumberValue<>("Health", 9, 1, 20, 1);
    private final BooleanValue lightningDetector = new BooleanValue("Lightning Detector", false);

    boolean isHealthLow = true;

    public Warns() {
        addSettings(
               healthWarn ,healthValue, // HealthWarn
                lightningDetector // Lightning
        );
    }

    @Override
    public void onEnable() {
        isHealthLow = true;
    }

    @Override
    public void onDisable() {
        isHealthLow = true;
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        if (healthWarn.getValue()) {
            if (mc.thePlayer.getHealth() < healthValue.getValue()) {
                if (!isHealthLow) {
                    Slack.getInstance().getModuleManager().getInstance(Interface.class).addNotification("HealthWarn:  YOU ARE AT LOW HP!", "", 4500L, Slack.NotificationStyle.WARN);
                    isHealthLow = true;
                }
            } else {
                isHealthLow = false;
            }
        }
    }
    @Listen
    public void onPacket (PacketEvent event) {
        if (lightningDetector.getValue()) {
            if (event.getDirection() == PacketDirection.INCOMING) {
                if (event.getPacket() instanceof S2CPacketSpawnGlobalEntity) {
                    S2CPacketSpawnGlobalEntity S2C = event.getPacket();
                    if (S2C.func_149053_g() == 1) {
                        double d0 = (double) S2C.func_149051_d() / 32.0D;
                        double d1 = (double) S2C.func_149050_e() / 32.0D;
                        double d2 = (double) S2C.func_149049_f() / 32.0D;
                        PrintUtil.message("Detected lightning strike at " + d0 + ", " + d1 + ", " + d2);
                        Slack.getInstance().getModuleManager().getInstance(Interface.class).addNotification("Lightning Detector:  Detected lightning strike at " + d0 + ", " + d1 + ", " + d2, "", 4500L, Slack.NotificationStyle.WARN);
                    }
                }
            }
        }
    }
}
