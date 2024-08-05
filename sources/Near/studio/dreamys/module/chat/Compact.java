package studio.dreamys.module.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;

public class Compact extends Module {
    private String lastMessage = "";
    private int line, amount;

    public Compact() {
        super("Compact", Category.CHAT);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void chat(ClientChatReceivedEvent e) {
        if (e.type == 0) {
            GuiNewChat guiNewChat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
            if (lastMessage.equals(e.message.getUnformattedText())) {
                guiNewChat.deleteChatLine(line);
                amount++;
                lastMessage = e.message.getUnformattedText();
                e.message.appendText(EnumChatFormatting.GRAY + " (" + amount + ")");
            }
            else {
                amount = 1;
                lastMessage = e.message.getUnformattedText();
            }
            line++;
            if (!e.isCanceled()) guiNewChat.printChatMessageWithOptionalDeletion(e.message, line);
            if (line > 256) line = 0;
            e.setCanceled(true);
        }
    }
}
