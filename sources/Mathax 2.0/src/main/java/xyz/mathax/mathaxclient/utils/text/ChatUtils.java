package xyz.mathax.mathaxclient.utils.text;

import baritone.api.BaritoneAPI;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.mixininterface.IChatHud;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class ChatUtils {

    // Player

    public static void sendMessageAsPlayer(String message) {
        mc.inGameHud.getChatHud().addToMessageHistory(message);

        if (message.startsWith("/")) {
            mc.player.networkHandler.sendCommand(message.substring(1));
        } else {
            mc.player.networkHandler.sendChatMessage(message);
        }
    }

    // Default

    public static void info(String message, Object... args) {
        sendMessage(Formatting.GRAY, message, args);
    }

    public static void info(String prefix, String message, Object... args) {
        sendMessage(0, prefix, Formatting.LIGHT_PURPLE, Formatting.GRAY, message, args);
    }

    // Warning

    public static void warning(String message, Object... args) {
        sendMessage(Formatting.YELLOW, message, args);
    }

    public static void warning(String prefix, String message, Object... args) {
        sendMessage(0, prefix, Formatting.LIGHT_PURPLE, Formatting.YELLOW, message, args);
    }

    // Error

    public static void error(String message, Object... args) {
        sendMessage(Formatting.RED, message, args);
    }

    public static void error(String prefix, String message, Object... args) {
        sendMessage(0, prefix, Formatting.LIGHT_PURPLE, Formatting.RED, message, args);
    }

    // Misc

    public static void sendMessage(Text message) {
        sendMessage(null, message);
    }

    public static void sendMessage(String prefix, Text message) {
        sendMessage(0, prefix, Formatting.LIGHT_PURPLE, message);
    }

    public static void sendMessage(Formatting color, String message, Object... args) {
        sendMessage(0, null, null, color, message, args);
    }

    public static void sendMessage(int id, Formatting color, String message, Object... args) {
        sendMessage(id, null, null, color, message, args);
    }

    public static void sendMessage(int id, @Nullable String prefixTitle, @Nullable Formatting prefixColor, Formatting messageColor, String messageContent, Object... args) {
        sendMessage(id, prefixTitle, prefixColor, formatMsg(messageContent, messageColor, args), messageColor);
    }

    public static void sendMessage(int id, @Nullable String prefixTitle, @Nullable Formatting prefixColor, String messageContent, Formatting messageColor) {
        MutableText message = Text.literal(messageContent);
        message.setStyle(message.getStyle().withFormatting(messageColor));
        sendMessage(id, prefixTitle, prefixColor, message);
    }

    public static void sendMessage(int id, @Nullable String prefixTitle, @Nullable Formatting prefixColor, Text msg) {
        if (mc.world == null) {
            return;
        }

        MutableText message = Text.literal("");
        message.append(getPrefix());
        if (prefixTitle != null) {
            message.append(getCustomPrefix(prefixTitle, prefixColor));
        }

        message.append(msg);

        if (!Config.get().deleteChatFeedbackSetting.get()) {
            id = 0;
        }

        ((IChatHud) mc.inGameHud.getChatHud()).add(message, id);
    }

    // Prefix

    private static MutableText getCustomPrefix(String prefixTitle, Formatting prefixColor) {
        MutableText prefix = Text.literal("");
        prefix.setStyle(prefix.getStyle().withFormatting(Formatting.GRAY));

        prefix.append("[");

        MutableText moduleTitle = Text.literal(prefixTitle);
        moduleTitle.setStyle(moduleTitle.getStyle().withFormatting(prefixColor));
        prefix.append(moduleTitle);

        prefix.append("] ");

        return prefix;
    }

    private static MutableText getPrefix() {
        MutableText text = Text.literal(MatHax.NAME);
        text.setStyle(text.getStyle().withColor(Color.MATHAX.getPacked()));

        MutableText prefix = Text.literal("");
        prefix.setStyle(prefix.getStyle().withFormatting(Formatting.GRAY));
        prefix.append("[");
        prefix.append(text);
        prefix.append("] ");

        return prefix;
    }

    // Formatting

    private static String formatMsg(String format, Formatting defaultColor, Object... args) {
        String msg = String.format(format, args);
        msg = msg.replace("(default)", defaultColor.toString());
        msg = msg.replace("(highlight)", Formatting.WHITE.toString());
        msg = msg.replace("(underline)", Formatting.UNDERLINE.toString());

        return msg;
    }

    public static MutableText formatCoords(Vec3d pos) {
        String coordsString = String.format("(highlight)(underline)%.0f, %.0f, %.0f(default)", pos.x, pos.y, pos.z);
        coordsString = formatMsg(coordsString, Formatting.GRAY);
        MutableText coordsText = Text.literal(coordsString);
        coordsText.setStyle(coordsText.getStyle()
                .withFormatting(Formatting.BOLD)
                .withClickEvent(new ClickEvent(
                        ClickEvent.Action.RUN_COMMAND,
                        String.format("%sgoto %d %d %d", BaritoneAPI.getSettings().prefix.value, (int) pos.x, (int) pos.y, (int) pos.z)
                ))
                .withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT,
                        Text.literal("Set as Baritone goal")
                ))
        );

        return coordsText;
    }
}
