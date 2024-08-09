package mathax.client.utils.misc;

import baritone.api.BaritoneAPI;
import mathax.client.mixininterface.IChatHud;
import mathax.client.systems.config.Config;
import mathax.client.systems.modules.Modules;
import mathax.client.MatHax;
import mathax.client.mixin.ChatHudAccessor;
import mathax.client.systems.modules.client.ClientSpoof;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import static mathax.client.MatHax.mc;

public class ChatUtils {
    // Default
    public static void info(String message, Object... args) {
        sendMsg(Formatting.GRAY, message, args);
    }

    public static void info(String prefix, String message, Object... args) {
        sendMsg(0, prefix, Formatting.DARK_RED, Formatting.GRAY, message, args);
    }

    // Warning
    public static void warning(String message, Object... args) {
        sendMsg(Formatting.YELLOW, message, args);
    }

    public static void warning(String prefix, String message, Object... args) {
        sendMsg(0, prefix, Formatting.DARK_RED, Formatting.YELLOW, message, args);
    }

    // Error
    public static void error(String message, Object... args) {
        sendMsg(Formatting.RED, message, args);
    }

    public static void error(String prefix, String message, Object... args) {
        sendMsg(0, prefix, Formatting.DARK_RED, Formatting.RED, message, args);
    }

    // Misc
    public static void sendMsg(Text message) {
        sendMsg(null, message);
    }

    public static void sendMsg(String prefix, Text message) {
        sendMsg(0, prefix, Formatting.DARK_RED, message);
    }

    public static void sendMsg(Formatting color, String message, Object... args) {
        sendMsg(0, null, null, color, message, args);
    }

    public static void sendMsg(int id, Formatting color, String message, Object... args) {
        sendMsg(id, null, null, color, message, args);
    }

    public static void sendMsg(int id, @Nullable String prefixTitle, @Nullable Formatting prefixColor, Formatting messageColor, String messageContent, Object... args) {
        sendMsg(id, prefixTitle, prefixColor, formatMsg(messageContent, messageColor, args), messageColor);
    }

    public static void sendMsg(int id, @Nullable String prefixTitle, @Nullable Formatting prefixColor, String messageContent, Formatting messageColor) {
        MutableText message = Text.literal(messageContent);
        message.setStyle(message.getStyle().withFormatting(messageColor));
        sendMsg(id, prefixTitle, prefixColor, message);
    }

    public static void sendMsg(int id, @Nullable String prefixTitle, @Nullable Formatting prefixColor, Text msg) {
        if (mc.world == null) return;

        MutableText message = Text.literal("");
        message.append(getMatHaxPrefix());
        if (prefixTitle != null) message.append(getCustomPrefix(prefixTitle, prefixColor));
        message.append(msg);

        if (!Config.get().deleteChatFeedback.get()) id = 0;

        ((IChatHud) mc.inGameHud.getChatHud()).add(message, id);
    }

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

    private static MutableText getMatHaxPrefix() {
        ClientSpoof cs = Modules.get().get(ClientSpoof.class);

        MutableText text = Text.literal("Envy Client");
        if (cs.changeChatFeedback()) text = Text.literal(cs.chatFeedbackText.get());
        MutableText prefix = Text.literal("");

        if (cs.changeChatFeedbackColor()) text.setStyle(text.getStyle().withColor(cs.chatFeedbackTextColor.get().getPacked()));
        else text.setStyle(text.getStyle().withColor(MatHax.INSTANCE.MATHAX_COLOR.getPacked()));

        prefix.setStyle(prefix.getStyle().withFormatting(Formatting.GRAY));
        prefix.append("[");
        prefix.append(text);
        prefix.append("] ");

        return prefix;
    }

    private static String formatMsg(String format, Formatting defaultColor, Object... args) {
        String msg = String.format(format, args);
        msg = msg.replaceAll("\\(default\\)", defaultColor.toString());
        msg = msg.replaceAll("\\(reset\\)", Formatting.RESET.toString());
        msg = msg.replaceAll("\\(highlight\\)", Formatting.WHITE.toString());
        msg = msg.replaceAll("\\(bold\\)", Formatting.BOLD.toString());
        msg = msg.replaceAll("\\(underline\\)", Formatting.UNDERLINE.toString());

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
