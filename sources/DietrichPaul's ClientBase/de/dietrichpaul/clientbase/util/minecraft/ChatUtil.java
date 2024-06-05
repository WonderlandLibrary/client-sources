/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.util.minecraft;

import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ChatUtil {    
    public final static MutableText PREFIX;

    static {
        PREFIX = Text.literal("").append(Text.literal("[").formatted(Formatting.DARK_GRAY)).append(Text.literal(ClientBase.NAME).formatted(Formatting.RED)).append(Text.literal("]").formatted(Formatting.DARK_GRAY));
    }

    public static void sendChatMessage(Text text) {
        text = Text.literal("").append(text).formatted(Formatting.WHITE); // &r -> &f
        Text line = Text.literal("").append(PREFIX).append(" ").append(text); // prefix + " " + text
        
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(line);
    }

    public static void sendChatMessageToServer(final String text) {
        ChatScreen chat = new ChatScreen("");
        chat.init(MinecraftClient.getInstance(), 0, 0);

        chat.sendMessage(text, false);
    }

    public static MutableText i18n(String key, Object... args) {
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            if (obj instanceof Text text) {
                args[i] = Text.literal("").append(text).formatted(Formatting.GRAY);
            } else {
                args[i] = Text.literal(String.valueOf(obj)).formatted(Formatting.GRAY);
            }
        }
        return Text.translatable(key, args);
    }

    public static void sendI18n(String key, Object... args) {
        sendChatMessage(i18n(key, args));
    }
}
