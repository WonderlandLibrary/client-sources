package club.bluezenith.module.modules.misc;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.module.value.types.StringValue;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.render.ColorUtil.stripFormatting;
import static java.lang.String.format;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class AutoRegister extends Module { //todo refactor code
    private final StringValue password = new StringValue("Password", "sigmaclient.info").setIndex(1);
    private final ModeValue mode = new ModeValue("Mode", "Normal", "Normal", "BlocksMC").setIndex(-1);
    public final IntegerValue delay = new IntegerValue("Delay", 500, 0, 5000, 50).setIndex(2).showIf(() -> mode.is("Normal"));
    private final LinkedBlockingQueue<String> z = new LinkedBlockingQueue<>();

    public AutoRegister() {
        super("AutoRegister", ModuleCategory.MISC);
    }

    @Listener
    public void onPacket(PacketEvent e) throws InterruptedException {
        Packet<?> packet = e.packet;
        if (packet instanceof S45PacketTitle) {
            final S45PacketTitle p = (S45PacketTitle) packet;
            if ((p.getType().equals(S45PacketTitle.Type.TITLE) || p.getType().equals(S45PacketTitle.Type.SUBTITLE))) {
                final String str = p.getMessage().getUnformattedText();
                addStringToList(stripFormatting(str));
            }
        }
        if (packet instanceof S02PacketChat) {
            final S02PacketChat p = (S02PacketChat) packet;
            if (p.getChatComponent().getUnformattedText().length() > 3)
                addStringToList(stripFormatting(p.getChatComponent().getUnformattedText()));
        }
        if (!z.isEmpty() && mc.thePlayer != null && mc.thePlayer.ticksExisted > 10) {
            switch (mode.get()) {
                case "Normal":
                    BlueZenith.scheduledExecutorService.schedule(() -> {
                        try {
                            player.sendChatMessage(z.take());
                        } catch (InterruptedException ignored) {
                        }
                        z.clear();
                    }, delay.get(), TimeUnit.MILLISECONDS);
                    break;

                case "BlocksMC":
                    final String str = z.take();
                    if(str.startsWith("/login")) {
                        BlueZenith.scheduledExecutorService.schedule(() -> {
                            for (int i = 0; i < 60; i++) {
                                PacketUtil.sendSilent(new C03PacketPlayer());
                            }
                            mc.thePlayer.sendChatMessage(str);
                        }, 1, TimeUnit.MILLISECONDS);
                    } else BlueZenith.scheduledExecutorService.schedule(() -> mc.thePlayer.sendChatMessage(str), 2000, TimeUnit.MILLISECONDS);
                    z.clear();
                break;
            }
        }
    }

    private void addStringToList(String str) {
        if(str.contains("/login") || str.matches("\\/(l|L)ogin <.*>")){
            if(delay.get() > 100) {
                getBlueZenith().getNotificationPublisher().postSuccess(
                        displayName,
                        "Logging you in, please wait...",
                        2300
                );
            }
            z.add(format("/login %s",  password.get()));
        } else if(str.contains("/register") || str.matches("\\/(r|R)egister <.*> <.*>")){
            if(delay.get() > 100) {
                getBlueZenith().getNotificationPublisher().postSuccess(
                        displayName,
                        "Logging you in, please wait...",
                        2300
                );
            }
            z.add(format("/register %s %s", password.get(), password.get()));
        }
    }
}
