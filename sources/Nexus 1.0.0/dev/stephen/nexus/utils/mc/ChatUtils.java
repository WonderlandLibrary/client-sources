package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.utils.Utils;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChatUtils implements Utils {

    public static void addMessageToChat(String text) {
        Text logo = Text.literal("[")
                .append(Text.literal("N").styled(style -> style.withColor(Formatting.RED)))
                .append(Text.literal("] "));
        Text message = Text.literal(text);
        Text combinedMessage = Text.literal("").append(logo).append(message);
        mc.inGameHud.getChatHud().addMessage(combinedMessage);
    }

    public static void addMessageToChat(int text) {
        Text logo = Text.literal("[")
                .append(Text.literal("N").styled(style -> style.withColor(Formatting.RED)))
                .append(Text.literal("] "));
        Text message = Text.literal(String.valueOf(text));
        Text combinedMessage = Text.literal("").append(logo).append(message);
        mc.inGameHud.getChatHud().addMessage(combinedMessage);
    }

    public static void addMessageToChat(double text) {
        Text logo = Text.literal("[")
                .append(Text.literal("N").styled(style -> style.withColor(Formatting.RED)))
                .append(Text.literal("] "));
        Text message = Text.literal(String.valueOf(text));
        Text combinedMessage = Text.literal("").append(logo).append(message);
        mc.inGameHud.getChatHud().addMessage(combinedMessage);
    }

    public static void addMessageToChat(float text) {
        Text logo = Text.literal("[")
                .append(Text.literal("N").styled(style -> style.withColor(Formatting.RED)))
                .append(Text.literal("] "));
        Text message = Text.literal(String.valueOf(text));
        Text combinedMessage = Text.literal("").append(logo).append(message);
        mc.inGameHud.getChatHud().addMessage(combinedMessage);
    }

    public static void addACMessageToChat(String text) {
        Text logo = Text.literal("[")
                .append(Text.literal("AC").styled(style -> style.withColor(Formatting.AQUA)))
                .append(Text.literal("] "));
        Text message = Text.literal(text);
        Text combinedMessage = Text.literal("").append(logo).append(message);
        mc.inGameHud.getChatHud().addMessage(combinedMessage);
    }

    public static void addACMessageToChat(int text) {
        Text logo = Text.literal("[")
                .append(Text.literal("AC").styled(style -> style.withColor(Formatting.AQUA)))
                .append(Text.literal("] "));
        Text message = Text.literal(String.valueOf(text));
        Text combinedMessage = Text.literal("").append(logo).append(message);
        mc.inGameHud.getChatHud().addMessage(combinedMessage);
    }

    public static void addACMessageToChat(double text) {
        Text logo = Text.literal("[")
                .append(Text.literal("AC").styled(style -> style.withColor(Formatting.AQUA)))
                .append(Text.literal("] "));
        Text message = Text.literal(String.valueOf(text));
        Text combinedMessage = Text.literal("").append(logo).append(message);
        mc.inGameHud.getChatHud().addMessage(combinedMessage);
    }

    public static void addACMessageToChat(float text) {
        Text logo = Text.literal("[")
                .append(Text.literal("AC").styled(style -> style.withColor(Formatting.AQUA)))
                .append(Text.literal("] "));
        Text message = Text.literal(String.valueOf(text));
        Text combinedMessage = Text.literal("").append(logo).append(message);
        mc.inGameHud.getChatHud().addMessage(combinedMessage);
    }
}
