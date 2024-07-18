package com.alan.clients.module.impl.render.chat;

import com.alan.clients.Client;
import com.alan.clients.util.font.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class RiseGuiNewChat extends GuiNewChat {
    private Chat chat;

    public RiseGuiNewChat(Minecraft mcIn) {
        super(mcIn);
    }

    @Override
    public void setChatLine(final IChatComponent chatComponent, final int p_146237_2_, final int p_146237_3_, final boolean p_146237_4_) {
        if (chat == null) chat = Client.INSTANCE.getModuleManager().get(Chat.class);

        double maxWidth = chat.getStructure().scale.x - 19;
        Font font = chat.getChatFont();
        String text = chatComponent.getUnformattedText();

        /*if (text.contains("\n")) {
            System.out.println("Detected");
            super.setChatLine(chatComponent, p_146237_2_, p_146237_3_, p_146237_4_);
        } else */if (font.width(text) > maxWidth) {
            double width = 0;
            StringBuilder replacement = new StringBuilder(chatComponent.getChatStyle().getFormattingCode());

            for (Character character : text.toCharArray()) {
                double characterWidth = font.width(character.toString());

                if (width + characterWidth > maxWidth) {
                    super.setChatLine(new ChatComponentText(replacement.toString()), p_146237_2_, p_146237_3_, p_146237_4_);

                    replacement = new StringBuilder();
                    width = 0;
                }

                replacement.append(character);
                width += characterWidth;
            }

            if (replacement.length() > 0) {
                super.setChatLine(new ChatComponentText(replacement.toString()), p_146237_2_, p_146237_3_, p_146237_4_);
            }
        } else {
            super.setChatLine(chatComponent, p_146237_2_, p_146237_3_, p_146237_4_);
        }
    }
}
