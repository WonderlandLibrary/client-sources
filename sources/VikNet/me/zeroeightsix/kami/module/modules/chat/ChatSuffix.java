package me.zeroeightsix.kami.module.modules.chat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.event.events.PacketEvent;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ChatTextUtils;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.Random;

/**
 * @author 086
 * @since 2018-8-4
 */
@Module.Info(name = "ChatSuffix", category = Module.Category.CHAT, description = "Custom Chat Suffix")
public class ChatSuffix extends Module {

    private Setting<Boolean> ignoreCommands = register(Settings.b("IgnoreCommands", true));
    private Setting<Boolean> ignoreSpecial = register(Settings.b("IgnoreSpecial", true));
    private Setting<SuffixMode> suffixMode = register(Settings.e("SuffixMode", SuffixMode.DEFAULT));
    private Setting<Boolean> deco = register(Settings.booleanBuilder().withName("Deco").withValue(true).withVisibility(v -> suffixMode.getValue().equals(SuffixMode.RANDOM)).build());
    private Setting<Boolean> domain = register(Settings.booleanBuilder().withName("Domain").withValue(true).withVisibility(v -> suffixMode.getValue().equals(SuffixMode.RANDOM)).build());

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {

        if (!(event.getPacket() instanceof CPacketChatMessage)) {
            return;
        }

        String message = ((CPacketChatMessage) event.getPacket()).getMessage();

        if (ignoreCommands.getValue() && message.startsWith("/")) {
            return;
        }

        if (ignoreSpecial.getValue() && (!Character.isLetter(message.charAt(0)) && !Character.isDigit(message.charAt(0)))) {
            if (!(message.charAt(0) == '>' || message.charAt(0) == '^')) {
                return;
            }
        }

        if (suffixMode.getValue().equals(SuffixMode.DEFAULT)) {
            message = ChatTextUtils.appendChatSuffix(message);
        }

        if (suffixMode.getValue().equals(SuffixMode.RANDOM)) {

            Random random = new Random();

            boolean hasDeco = random.nextBoolean();
            boolean hasDecoDouble = random.nextBoolean();
            String decoChar = "";

            boolean hasDomain = random.nextBoolean();
            boolean hasDecoAppendix = random.nextBoolean();

            StringBuilder suffix = new StringBuilder();

            suffix.append(" \u23D0 ");

            if (deco.getValue()) {

                decoChar = getRandomStringFromArray(NameParts.deco);

                if (hasDeco && hasDecoDouble) {
                    suffix.append(decoChar).append(" ");
                }

            }

            suffix.append(getRandomStringFromArray(NameParts.pre));

            if (!hasDomain && random.nextBoolean()) {
                suffix.append(" ");
            }

            suffix.append(getRandomStringFromArray(NameParts.mid));

            if (hasDomain && domain.getValue()) {
                suffix.append(getRandomStringFromArray(NameParts.domain));
            }

            if (deco.getValue()) {

                if (hasDecoAppendix) {
                    suffix.append(getRandomStringFromArray(NameParts.decoAppendix));
                }

                if (hasDeco) {
                    suffix.append(" ").append(decoChar);
                }

            }

            message = ChatTextUtils.appendChatSuffix(message, ChatTextUtils.transformPlainToFancy(suffix.toString()));

        }

        if (suffixMode.getValue().equals(SuffixMode.DUMB)) {
            message = ChatTextUtils.appendChatSuffix(message, NameParts.dumb);
        }

        ((CPacketChatMessage) event.getPacket()).message = message.replaceAll(ChatTextUtils.SECTIONSIGN, "");

    });

    private enum SuffixMode {
        DEFAULT, RANDOM, DUMB
    }

    private String getRandomStringFromArray(String[] array) {
        return array[new Random().nextInt(array.length)];
    }

    private static class NameParts {

        private static final String[] pre =
                {
                        "alpha",
                        "amazon",
                        "aristois",
                        "asuna",
                        "axiom",
                        "back",
                        "bald",
                        "bleach",
                        "bonn\u278a\u2790",
                        "butterfly",
                        "computer",
                        "crystal",
                        "dot",
                        "drip",
                        "drop",
                        "ebic",
                        "face",
                        "facebook",
                        "fake",
                        "fit",
                        "forge",
                        "future",
                        "gaben",
                        "gallium",
                        "generic",
                        "genocide",
                        "gfunk",
                        "ghost",
                        "glow",
                        "google",
                        "hause",
                        "heph",
                        "high",
                        "huzuni",
                        "impact",
                        "kami",
                        "keemy",
                        "low",
                        "mouse",
                        "neverlack",
                        "nodus",
                        "nou",
                        "obi",
                        "optifine",
                        "phobos",
                        "raion",
                        "root",
                        "ruhama",
                        "rusher",
                        "seppuku",
                        "scicraft",
                        "scifi",
                        "solar",
                        "starbust",
                        "surreal",
                        "trailer",
                        "urmom",
                        "voco",
                        "void",
                        "waizy",
                        "wurst",
                        "wwe",
                        "xor",
                };

        private static final String[] mid =
                {
                        "blue",
                        "client",
                        "clone",
                        "dead",
                        "desync",
                        "doored",
                        "elite",
                        "fag",
                        "fork",
                        "god",
                        "green",
                        "grid",
                        "grind",
                        "hack",
                        "hacks",
                        "hax",
                        "kek",
                        "krypt",
                        "lag",
                        "land",
                        "leak",
                        "life",
                        "memed",
                        "mule",
                        "net",
                        "nut",
                        "punch",
                        "rat",
                        "skid",
                        "top",
                };

        private static final String[] domain =
                {
                        ".cc",
                        ".edu",
                        ".eu",
                        ".ez",
                        ".gay",
                        ".gg",
                        ".in",
                        ".ru",
                };

        private static final String[] deco =
                {
                        "\u262d", // hammer and sickle ☭
                        "\u0fc9", // nut symbol ࿉
                        "\u2620", // skull ☠
                        "\u2623", // biohazard ☣
                        "\u2654", // brown ♔
                        "\u2764", // heart
                        "\u267f", // wheelchair ♿
                        "\u262f", // yingyang ☯
                };

        private static final String[] decoAppendix =
                {
                        "\u2122", // trademark ™
                };

        private static final String dumb = " \u23d0 \uff10\uff12\uff17\uff28\uff41\uff43\uff4b \u23d0 \u1d00\u1d18\u1d0f\u029f\u029f\u028f\u1d0f\u0274 \u23d0 \u1d00\u1d04\u1d07 \u029c\u1d00\u1d04\u1d0b \u23d0 \u0299\u1d00\u1d04\u1d0b\u1d05\u1d0f\u1d0f\u0280\u1d07\u1d05 \u23d0 \u0299\u1d00\u029f\u1d05\u029c\u1d00\u1d04\u1d0b \u23d0 \u1d07\u029f\u1d07\u1d0d\u1d07\u0274\u1d1b\u1d00\ua731\uff0e\u1d04\u1d0f\u1d0d \u23d0 \u0493\u1d1c\u0280\u0280\u028f\u1d21\u1d00\u0280\u1d07 \u23d0 \u0262\u1d00\u028f \u23d0 \u041d\u03b5\u13ae\u043d\u15e9\u03b5\u0455\u01ad\u03c5\u0455 \u23d0 \u1d0b\u1d07\u1d07\u1d0d\u028f\uff0e\u1d04\u1d04\u30c4 \u23d0 \u1d0d\u028f\u0455\u1d1b\u026a\u1d04.\u1d04\u1d04 \u23d0 \u0274\u1d1c\u1d1b\u0262\u1d0f\u1d05\uff0e\u1d04\u1d04 \u0fc9 \u23d0 \uff30\uff25\uff2e\uff29\uff33 \u23d0 \u1d18\u029c\u1d0f\u0299\u1d0f\u0455.\u1d07\u1d1c \u23d0 \uff30\uff4c\uff49\uff56\uff49\uff44\uff0e\uff43\uff43 \u23d0 \u02b3\u1d58\u02e2\u02b0\u1d49\u02b3\u02b0\u1d43\u1d9c\u1d4f \u23d0 \u0455\u026a\u0274\u0262\u1d1c\u029f\u1d00\u0280\u026a\u1d1b\u028f\u029c\u1d00\u1d04\u1d0b\u2122 \u23d0 \u0e23\u0e4f\u0e22\u05e7\u0452\u0e04\u03c2\u043a \u23d0 \u1d1b\u0280\u026a\u1d18\u029f\ua731\u02e2\u026a\u02e3 \u23d0 \u166d\uff4f\u1587\uff0d\u1455\u14aa\uff49\u4e47\u144e\u3112";

    }

}
