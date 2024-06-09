package me.travis.wurstplus.module.modules.chat;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.travis.wurstplus.event.events.PacketEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketChat;

@Module.Info(name="AntiSpam", category=Module.Category.CHAT)
public class AntiSpam extends Module {
    private Setting<Boolean> greenText = this.register(Settings.b("Green Text", true));
    private Setting<Boolean> discordLinks = this.register(Settings.b("Discord Links", true));
    private Setting<Boolean> webLinks = this.register(Settings.b("Web Links", true));
    private Setting<Boolean> announcers = this.register(Settings.b("Announcers", true));
    private Setting<Boolean> tradeChat = this.register(Settings.b("Trade Chat", true));
    private Setting<Boolean> wwe = this.register(Settings.b("Fuck WWE", false));
    private Setting<Boolean> duplicates = this.register(Settings.b("Duplicates", true));
    private Setting<Integer> duplicatesTimeout = this.register(Settings.integerBuilder("Duplicates Timeout").withMinimum(1).withValue(10).withMaximum(600).build());
    private Setting<Boolean> skipOwn = this.register(Settings.b("Skip Own", true));
    private ConcurrentHashMap<String, Long> messageHistory;
    @EventHandler
    public Listener<PacketEvent.Receive> listener = new Listener<PacketEvent.Receive>(event -> {
        if (AntiSpam.mc.player == null || this.isDisabled()) {
            return;
        }
        if (!(event.getPacket() instanceof SPacketChat)) {
            return;
        }
        SPacketChat sPacketChat = (SPacketChat)event.getPacket();
        if (this.detectSpam(sPacketChat.getChatComponent().getUnformattedText())) {
            event.cancel();
        }
    }, new Predicate[0]);

    @Override
    public void onEnable() {
        this.messageHistory = new ConcurrentHashMap();
    }

    @Override
    public void onDisable() {
        this.messageHistory = null;
    }

    private boolean detectSpam(String message) {
        if (this.skipOwn.getValue().booleanValue() && this.findPatterns(FilterPatterns.ownMessage, message)) {
            return false;
        }
        if (this.greenText.getValue().booleanValue() && this.findPatterns(FilterPatterns.greenText, message)) {
            return true;
        }
        if (this.discordLinks.getValue().booleanValue() && this.findPatterns(FilterPatterns.discord, message)) {
            return true;
        }
        if (this.webLinks.getValue().booleanValue() && this.findPatterns(FilterPatterns.webLink, message)) {
            return true;
        }
        if (this.tradeChat.getValue().booleanValue() && this.findPatterns(FilterPatterns.tradeChat, message)) {
            return true;
        }
        if (this.wwe.getValue().booleanValue() && this.findPatterns(FilterPatterns.wwe, message)) {
            return true;
        }
        if (this.announcers.getValue().booleanValue() && this.findPatterns(FilterPatterns.announcer, message)) {
            return true;
        }
        if (this.duplicates.getValue().booleanValue()) {
            boolean isDuplicate = false;
            if (this.messageHistory.containsKey(message) && (System.currentTimeMillis() - this.messageHistory.get(message)) / 1000L < (long)this.duplicatesTimeout.getValue().intValue()) {
                isDuplicate = true;
            }
            this.messageHistory.put(message, System.currentTimeMillis());
            if (isDuplicate) {
                return true;
            }
        }
        return false;
    }

    private boolean findPatterns(String[] patterns, String string) {
        for (String pattern : patterns) {
            if (!Pattern.compile(pattern).matcher(string).find()) continue;
            return true;
        }
        return false;
    }

    static /* synthetic */ Minecraft access$600() {
        return mc;
    }

    private static class FilterPatterns {
        private static final String[] announcer = new String[]{"I just walked .+ feet!", "I just placed a .+!", "I just attacked .+ with a .+!", "I just dropped a .+!", "I just opened chat!", "I just opened my console!", "I just opened my GUI!", "I just went into full screen mode!", "I just paused my game!", "I just opened my inventory!", "I just looked at the player list!", "I just took a screen shot!", "I just swaped hands!", "I just ducked!", "I just changed perspectives!", "I just jumped!", "I just ate a .+!", "I just crafted .+ .+!", "I just picked up a .+!", "I just smelted .+ .+!", "I just respawned!", "I just attacked .+ with my hands", "I just broke a .+!"};
        private static final String[] discord = new String[]{"discord.gg"};
        private static final String[] greenText = new String[]{"<.+> >"};
        private static final String[] ownMessage = new String[]{"<" + AntiSpam.access$600().player.getName() + ">"};
        private static final String[] tradeChat = new String[]{"buy", "sell"};
        private static final String[] wwe = new String[]{"WWE", "THEREALWWEFAN231"};
        private static final String[] webLink = new String[]{"http:\\/\\/", "https:\\/\\/"};

        private FilterPatterns() {
        }
    }

}

