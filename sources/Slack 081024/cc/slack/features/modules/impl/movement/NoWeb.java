// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.impl.movement.nowebs.INoWeb;
import cc.slack.features.modules.impl.movement.nowebs.impl.FastFallNoWeb;
import cc.slack.features.modules.impl.movement.nowebs.impl.VanillaNoWeb;
import cc.slack.features.modules.impl.movement.nowebs.impl.VerusNoWeb;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;

@ModuleInfo(
        name = "NoWeb",
        category = Category.MOVEMENT
)
public class NoWeb extends Module {

    private final ModeValue<INoWeb> mode = new ModeValue<>(new INoWeb[]{new VanillaNoWeb(), new FastFallNoWeb(), new VerusNoWeb()});

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});

    public NoWeb() {
        super();
        addSettings(mode, displayMode);
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
        mode.getValue().onMove(event);
    }
    @Listen
    public void onPacket(PacketEvent event) {
        mode.getValue().onPacket(event);
    }

    @Listen
    public void onCollide(CollideEvent event) {
        mode.getValue().onCollide(event);
    }

    @Listen
    public void onMotion(MotionEvent event) {
        mode.getValue().onMotion(event);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate(UpdateEvent event) {
        if (!mc.thePlayer.isInWeb) {
            return;
        }

        mode.getValue().onUpdate(event);

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
