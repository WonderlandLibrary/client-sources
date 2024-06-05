package io.github.nevalackin.radium.module.impl.render;

import io.github.nevalackin.radium.event.impl.packet.PacketReceiveEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

@ModuleInfo(label = "Better Chat", category = ModuleCategory.RENDER)
public final class BetterChat extends Module {

    private String lastMessage = "";
    private int amount;
    private int line;

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent e) {
        final Packet<?> packet = e.getPacket();
        if (packet instanceof S2EPacketCloseWindow) {
            if (isTypingInChat()) e.setCancelled();
        } else if (packet instanceof S02PacketChat) {
            S02PacketChat s02PacketChat = (S02PacketChat) packet;
            if (s02PacketChat.getType() == 0) {
                IChatComponent message = s02PacketChat.getChatComponent();
                String rawMessage = message.getFormattedText();
                GuiNewChat chatGui = Wrapper.getMinecraft().ingameGUI.getChatGUI();
                if (lastMessage.equals(rawMessage)) {
                    chatGui.deleteChatLine(line);
                    amount++;
                    s02PacketChat.getChatComponent().appendText(EnumChatFormatting.GRAY + " [x" + amount + "]");
                } else {
                    amount = 1;
                }
                line++;
                lastMessage = rawMessage;
                chatGui.printChatMessageWithOptionalDeletion(message, line);

                if (this.line > 256) {
                    this.line = 0;
                }

                e.setCancelled();
            }
        }
    }

    private boolean isTypingInChat() {
        return Wrapper.getCurrentScreen() instanceof GuiChat;
    }
}
