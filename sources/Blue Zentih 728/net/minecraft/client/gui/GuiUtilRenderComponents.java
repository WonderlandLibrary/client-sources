package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiUtilRenderComponents
{
    public static String removeChatColors(String toRemove, boolean p_178909_1_)
    {
        return !p_178909_1_ && !Minecraft.getMinecraft().gameSettings.chatColours ? EnumChatFormatting.getTextWithoutFormattingCodes(toRemove) : toRemove;
    }

    public static List<IChatComponent> wrapComponent(IChatComponent toWrap, int chatWidth, FontRenderer fontRenderer, boolean unknownOption1, boolean unknownOption2)
    {
        int i = 0;
        IChatComponent ichatcomponent = new ChatComponentText("");
        List<IChatComponent> list = Lists.<IChatComponent>newArrayList();
        List<IChatComponent> list1 = Lists.newArrayList(toWrap);

        for (int j = 0; j < list1.size(); ++j)
        {
            IChatComponent currentComponent = list1.get(j);
            String firstLine = currentComponent.getUnformattedTextForChat();
            boolean hasNewlineChar = false;

            if (firstLine.contains("\n"))
            {
                int newlineIndex = firstLine.indexOf('\n');
                String nextLine = firstLine.substring(newlineIndex + 1);
                firstLine = firstLine.substring(0, newlineIndex + 1);

                final Matcher matcher = Pattern.compile("§([0-9]|[A-z])").matcher(firstLine); //find the control codes in the first line

                StringBuilder formatting = new StringBuilder();
                while (matcher.find()) {
                    formatting.append(matcher.group()); //and append them to the next line, because copying chat style doesn't work for some reason
                }
                nextLine = formatting.append(nextLine).toString();

                ChatComponentText nextLineComponent = new ChatComponentText(nextLine);

                nextLineComponent.setChatStyle(currentComponent.getChatStyle().createShallowCopy());
                list1.add(j + 1, nextLineComponent);
                hasNewlineChar = true;
            }

            String _content = removeChatColors(currentComponent.getChatStyle().getFormattingCode() + firstLine, unknownOption2);
            String content = _content.endsWith("\n") ? _content.substring(0, _content.length() - 1) : _content;

            int contentWidth = fontRenderer.getStringWidth(content);
            ChatComponentText contentComponent = new ChatComponentText(content);
            contentComponent.setChatStyle(currentComponent.getChatStyle().createShallowCopy());

            if (i + contentWidth > chatWidth)
            {
                String trimmed = fontRenderer.trimStringToWidth(content, chatWidth - i, false);
                String leftOut = trimmed.length() < content.length() ? content.substring(trimmed.length()) : null;

                if (leftOut != null && leftOut.length() > 0)
                {
                    int lastSpaceIndex = trimmed.lastIndexOf(" ");

                    if (lastSpaceIndex >= 0 && fontRenderer.getStringWidth(content.substring(0, lastSpaceIndex)) > 0)
                    {
                        trimmed = content.substring(0, lastSpaceIndex);

                        if (unknownOption1)
                        {
                            ++lastSpaceIndex;
                        }

                        leftOut = content.substring(lastSpaceIndex);
                    }
                    else if (i > 0 && !content.contains(" "))
                    {
                        trimmed = "";
                        leftOut = content;
                    }

                    /*
                     final Matcher matcher = Pattern.compile("§([0-9]|[A-z])").matcher(firstLine); //find the control codes in the first line

                StringBuilder formatting = new StringBuilder();
                while (matcher.find()) {
                    formatting.append(matcher.group()); //and append them to the next line, because copying chat style doesn't work for some reason
                }
                nextLine = formatting.append(nextLine).toString();
                     */

                    final Matcher matcher = Pattern.compile("§([0-9]|[A-z])").matcher(content.substring(0, content.length() - leftOut.length())); //find the control codes in the first line
                    StringBuilder formatting = new StringBuilder();
                    while (matcher.find()) {
                        formatting.append(matcher.group()); //and append them to the next line, because copying chat style doesn't work for some reason
                    }

                    leftOut = formatting.append(leftOut).toString();
                    ChatComponentText chatcomponenttext2 = new ChatComponentText(leftOut);
                    chatcomponenttext2.setChatStyle(currentComponent.getChatStyle().createShallowCopy());
                    list1.add(j + 1, chatcomponenttext2);
                }

                contentWidth = fontRenderer.getStringWidth(trimmed);
                contentComponent = new ChatComponentText(trimmed);
                contentComponent.setChatStyle(currentComponent.getChatStyle().createShallowCopy());
                hasNewlineChar = true;
            }

            if (i + contentWidth <= chatWidth)
            {
                i += contentWidth;
                ichatcomponent.appendSibling(contentComponent);
            }
            else
            {
                hasNewlineChar = true;
            }

            if (hasNewlineChar)
            {
                list.add(ichatcomponent);
                i = 0;
                ichatcomponent = new ChatComponentText("");
            }
        }

        list.add(ichatcomponent);
        return list;
    }
}
