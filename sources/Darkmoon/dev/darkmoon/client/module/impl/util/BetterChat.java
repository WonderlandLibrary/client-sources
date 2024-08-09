package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

@ModuleAnnotation(name = "BetterChat", category = Category.UTIL)
public class BetterChat extends Module {
    public static BooleanSetting antiSpam = new BooleanSetting("Anti-Spam", false);
    public static BooleanSetting chatHistory = new BooleanSetting("Save History", false);
    private String lastMessage = "";
    private int amount;
    private int line;

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (!antiSpam.get()) return;
        if (event.getPacket() instanceof SPacketChat && ((SPacketChat) event.getPacket()).getType().equals(ChatType.CHAT)) {
            SPacketChat chatPacket = ((SPacketChat) event.getPacket());
            ITextComponent message = chatPacket.getChatComponent();
            String rawMessage = message.getFormattedText();
            GuiNewChat chatGui = mc.ingameGUI.getChatGUI();
            if (lastMessage.equals(rawMessage)) {
                chatGui.deleteChatLine(line);
                ++amount;
                chatPacket.getChatComponent().appendText(ChatFormatting.GRAY + " [x" + this.amount + "]");
            } else {
                amount = 1;
            }
            ++line;
            lastMessage = rawMessage;
            chatGui.printChatMessageWithOptionalDeletion(message, line);
            if (line > 256) {
                line = 0;
            }
            event.setCancelled(true);
        }
    }
}
