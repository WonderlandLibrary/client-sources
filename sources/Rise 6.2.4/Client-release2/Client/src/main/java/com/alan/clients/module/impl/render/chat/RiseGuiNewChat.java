package com.alan.clients.module.impl.render.chat;

import com.alan.clients.Client;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.render.UnlimitedChat;
import com.alan.clients.util.font.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;

import java.util.List;

public class RiseGuiNewChat extends GuiNewChat {
    private Chat chat;

    public RiseGuiNewChat(Minecraft mcIn) {
        super(mcIn);
    }

    @Override
    public void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
        if (chat == null) chat = Client.INSTANCE.getModuleManager().get(Chat.class);

        if (chatLineId != 0) {
            this.deleteChatLine(chatLineId);
        }

        final int width = (int) (chat.getStructure().scale.x - 19);
        final List<IChatComponent> list = GuiUtilRenderComponents.splitText(chatComponent, width, this.mc.fontRendererObj, false, false);
        final boolean flag = this.getChatOpen();


        for (final IChatComponent ichatcomponent : list) {
            if (flag && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }

            this.drawnChatLines.addFirst(new ChatLine(updateCounter, ichatcomponent, chatLineId));
        }

        final Module unlimitedChat = Client.INSTANCE.getModuleManager().get(UnlimitedChat.class);
        int maxSize = unlimitedChat == null || !unlimitedChat.isEnabled() ? 200 : 10000;

        while (this.drawnChatLines.size() > maxSize) {
            this.drawnChatLines.removeLast();
        }

        if (!displayOnly) {
            this.chatLines.addFirst(new ChatLine(updateCounter, chatComponent, chatLineId));

            while (this.chatLines.size() > maxSize) {
                this.chatLines.removeLast();
            }
        }
    }
//    public void setChatLgine(final IChatComponent chatComponent, final int p_146237_2_, final int p_146237_3_, final boolean p_146237_4_) {
//        if (chat == null) chat = Client.INSTANCE.getModuleManager().get(Chat.class);
//
//        double maxWidth = chat.getStructure().scale.x - 19;
//        Font font = chat.getChatFont();
//        String text = chatComponent.getUnformattedText();
//
//        /*if (text.contains("\n")) {
//            System.out.println("Detected");
//            super.setChatLine(chatComponent, p_146237_2_, p_146237_3_, p_146237_4_);
//        } else */if (font.width(text) > maxWidth) {
//            double width = 0;
//            StringBuilder replacement = new StringBuilder(chatComponent.getChatStyle().getFormattingCode());
//
//            for (Character character : text.toCharArray()) {
//                double characterWidth = font.width(character.toString());
//
//                if (width + characterWidth > maxWidth) {
//                    super.setChatLine(new ChatComponentText(replacement.toString()), p_146237_2_, p_146237_3_, p_146237_4_);
//
//                    replacement = new StringBuilder();
//                    width = 0;
//                }
//
//                replacement.append(character);
//                width += characterWidth;
//            }
//
//            if (replacement.length() > 0) {
//                super.setChatLine(new ChatComponentText(replacement.toString()), p_146237_2_, p_146237_3_, p_146237_4_);
//            }
//        } else {
//            super.setChatLine(chatComponent, p_146237_2_, p_146237_3_, p_146237_4_);
//        }
//    }
}