// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.network.PacketUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.GameSettings;
import cc.slack.utils.other.TimeUtil;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@ModuleInfo(
        name = "Wtap",
        category = Category.GHOST
)
public class Wtap extends Module {

    private int ticks;
    private final TimeUtil wtapTimer = new TimeUtil();

    private final ModeValue<String> wtapMode = new ModeValue<>("Wtap", new String[]{"Wtap", "Stap", "Shift tap", "Packet", "Legit"});

    public Wtap() {
        addSettings(wtapMode);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onAttack(AttackEvent event) {
        if (wtapTimer.hasReached(500L)) {
            wtapTimer.reset();
            ticks = 2;
        }
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate(UpdateEvent event) {
        switch (ticks) {
            case 2:
                switch (wtapMode.getValue().toLowerCase()) {
                    case "wtap":
                        mc.gameSettings.keyBindForward.pressed = false;
                        break;
                    case "stap":
                        mc.gameSettings.keyBindForward.pressed = false;
                        mc.gameSettings.keyBindBack.pressed = true;
                        break;
                    case "shift tap":
                        mc.gameSettings.keyBindSneak.pressed = true;
                        break;
                    case "packet":
                        PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                        break;
                    case "legit":
                        mc.thePlayer.setSprinting(false);
                        break;
                }
                ticks--;
                break;
            case 1:
                switch (wtapMode.getValue().toLowerCase()) {
                    case "wtap":
                        mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindForward);
                        break;
                    case "stap":
                        mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindForward);
                        mc.gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindBack);
                        break;
                    case "shift tap":
                        mc.gameSettings.keyBindSneak.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindSneak);
                        break;
                    case "packet":
                        PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                        break;
                    case "legit":
                        mc.thePlayer.setSprinting(true);
                        break;
                }
                ticks--;
                break;
        }
    }
}
