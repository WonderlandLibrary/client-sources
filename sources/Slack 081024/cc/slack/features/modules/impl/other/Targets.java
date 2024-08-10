// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.other;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.impl.render.SessionInfo;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.handshake.client.C00Handshake;

@ModuleInfo(
        name = "Targets",
        category = Category.OTHER
)
public class Targets extends Module {

    public final BooleanValue teams = new BooleanValue("Teams", true);
    public final BooleanValue playerTarget = new BooleanValue("Players", true);
    public final BooleanValue animalTarget = new BooleanValue("Animals", false);
    public final BooleanValue mobsTarget = new BooleanValue("Mobs", false);
    public final BooleanValue friendsTarget = new BooleanValue("Friends", false);

    public Targets() {
        addSettings(teams, playerTarget, animalTarget, mobsTarget, friendsTarget);
    }


    @Listen
    public void SystemCheck (PacketEvent event) {
        if (event.getPacket() instanceof C00Handshake) {
            Slack.getInstance().getModuleManager().getInstance(SessionInfo.class).currentTime = System.currentTimeMillis();
            Slack.getInstance().getModuleManager().getInstance(SessionInfo.class).currentTime = 0L;
            Slack.getInstance().getModuleManager().getInstance(SessionInfo.class).timeJoined = System.currentTimeMillis();
        }
    }
}
