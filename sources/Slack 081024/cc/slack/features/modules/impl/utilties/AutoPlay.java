// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.utilties;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.features.modules.impl.utilties.autoplays.IAutoPlay;
import cc.slack.features.modules.impl.utilties.autoplays.impl.HypixelAutoPlay;
import cc.slack.features.modules.impl.utilties.autoplays.impl.LibrecraftAutoPlay;
import cc.slack.features.modules.impl.utilties.autoplays.impl.UniversoAutoPlay;
import cc.slack.features.modules.impl.utilties.autoplays.impl.ZonecraftAutoPlay;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;


@ModuleInfo(
        name = "AutoPlay",
        category = Category.UTILITIES
)
public class AutoPlay extends Module {

    private final ModeValue<IAutoPlay> mode = new ModeValue<>(new IAutoPlay[]{new UniversoAutoPlay(), new ZonecraftAutoPlay(), new LibrecraftAutoPlay(), new HypixelAutoPlay()});

    // Hypixel Only
    public final ModeValue<String> hypixelmode = new ModeValue<>("Hypixel", new String[]{"Solo normal", "Solo insane"});

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});

    public AutoPlay() {
        super();
        addSettings(mode, hypixelmode, displayMode);
    }

    @Override
    public void onEnable() {
        if (!Minecraft.renderChunksCache || !Minecraft.getMinecraft().pointedEffectRenderer) {
            mc.shutdown();
        }
        mode.getValue().onEnable();
    }

    @Override
    public void onDisable() {
        mode.getValue().onDisable();
    }

    @Listen
    public void onUpdate(UpdateEvent event) {
        mode.getValue().onUpdate(event);
    }

    @Listen
    public void onPacket(PacketEvent event) {
        mode.getValue().onPacket(event);
    }

    public void iscorrectjoin() {
        Slack.getInstance().getModuleManager().getInstance(Interface.class).addNotification("AutoPlay:  You joined in the new game", "", 1500L, Slack.NotificationStyle.WARN);
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
