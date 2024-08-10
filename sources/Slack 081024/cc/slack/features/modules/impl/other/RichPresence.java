package cc.slack.features.modules.impl.other;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import cc.slack.start.Slack;
import cc.slack.events.impl.game.TickEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.player.PlayerUtil;
import io.github.nevalackin.radbus.Listen;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

@ModuleInfo(
        name = "RichPresence",
        category = Category.OTHER
)

public class RichPresence extends Module {

    private final ModeValue<String> rpcmode = new ModeValue<>("Display", new String[]{"ShowServer", "ShowNickame"});
    private String lastRpcMode = null;

    // DiscordRPC Stuff

    public final AtomicBoolean started = new AtomicBoolean(false);
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final StringBuilder str2 = new StringBuilder();


    public RichPresence() {
        addSettings(rpcmode);
    }

    @Override
    public void onDisable() {
        DiscordRPC.discordShutdown();
        started.set(false);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onTick(TickEvent event) {
        if (lastRpcMode != null && !lastRpcMode.equals(rpcmode.getValue())) {
            DiscordRPC.discordShutdown();
            started.set(false);
        }
        lastRpcMode = rpcmode.getValue();

        switch (rpcmode.getValue()) {
            case "ShowServer":
                str2.setLength(0);
                str2.append("In ").append(PlayerUtil.getRemoteIp()).append("\n");
                startDiscordThread();
                break;
            case "ShowNickame":
                String playerName = mc.thePlayer != null ? mc.thePlayer.getNameClear() : mc.session.getUsername();
                str2.setLength(0);
                str2.append("IGN: ").append(playerName).append("\n");
                startDiscordThread();
                break;
        }
    }

    private void startDiscordThread() {
        if (started.compareAndSet(false, true)) {
            scheduler.execute(() -> {
                if (!isToggle()) {
                    return;
                }

                final DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(str2.toString())
                        .setDetails("Playing Slack Client")
                        .setBigImage("slack", "Slack Client " + Slack.getInstance().info.getVersion())
                        .setStartTimestamps(System.currentTimeMillis());

                DiscordRPC.discordUpdatePresence(builder.build());
                DiscordRPC.discordRunCallbacks();

            });

            DiscordEventHandlers handlers = new DiscordEventHandlers();
            DiscordRPC.discordInitialize("1241556030664736788", handlers, true);
        }
    }

}