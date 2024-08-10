// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.combat;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.*;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.impl.combat.criticals.ICriticals;
import cc.slack.features.modules.impl.combat.criticals.impl.*;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S40PacketDisconnect;

@ModuleInfo(
        name = "Criticals",
        category = Category.COMBAT
)
public class Criticals extends Module {

    public final ModeValue<ICriticals> mode = new ModeValue<>(new ICriticals[] {new EditCriticals(), new VulcanCriticals(), new JumpCriticals(), new PacketCriticals(), new MiniCriticals()});

    public final BooleanValue vulcanTimer = new BooleanValue("Vulcan Timer", true);
    public final BooleanValue onlyGround = new BooleanValue("Only Ground", true);
    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});
    
    public Criticals() {
        super();
        addSettings(mode, vulcanTimer,onlyGround, displayMode);
    }

    @Override
    public void onEnable() {
        mode.getValue().onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
        mode.getValue().onDisable();
    }

    @Listen
    public void onMove(MoveEvent event) {
        if (onlyGround.getValue() && !mc.thePlayer.onGround) return;
        mode.getValue().onMove(event);
    }

    @Listen
    public void onUpdate(UpdateEvent event) {
        if (onlyGround.getValue() && !mc.thePlayer.onGround) return;

        mode.getValue().onUpdate(event);
    }

    @Listen
    public void onPacket(PacketEvent event) {
        if (mc.thePlayer == null) return;
        if (onlyGround.getValue() && !mc.thePlayer.onGround) return;

        if (event.getPacket() instanceof S37PacketStatistics || event.getPacket() instanceof S40PacketDisconnect) {
            toggle();
        }

        mode.getValue().onPacket(event);
    }

    @Listen
    public void onCollide(CollideEvent event) {
        if (onlyGround.getValue() && !mc.thePlayer.onGround) return;
        mode.getValue().onCollide(event);
    }

    @Listen
    public void onMotion(MotionEvent event) {
        if (onlyGround.getValue() && !mc.thePlayer.onGround) return;
        mode.getValue().onMotion(event);
    }

    @Listen
    public void onAttack(AttackEvent event) {
        if (onlyGround.getValue() && !mc.thePlayer.onGround) return;
        mode.getValue().onAttack(event);
    }


    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return mode.getValue().toString();
        }
        return null;
    }

}
