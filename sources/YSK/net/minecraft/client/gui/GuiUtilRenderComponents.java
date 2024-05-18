package net.minecraft.client.gui;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class GuiUtilRenderComponents
{
    private static final String[] I;
    
    public static List<IChatComponent> func_178908_a(final IChatComponent chatComponent, final int n, final FontRenderer fontRenderer, final boolean b, final boolean b2) {
        int n2 = "".length();
        ChatComponentText chatComponentText = new ChatComponentText(GuiUtilRenderComponents.I["".length()]);
        final ArrayList arrayList = Lists.newArrayList();
        final ArrayList arrayList2 = Lists.newArrayList((Iterable)chatComponent);
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < arrayList2.size()) {
            final IChatComponent chatComponent2 = arrayList2.get(i);
            String s = chatComponent2.getUnformattedTextForChat();
            int n3 = "".length();
            if (s.contains(GuiUtilRenderComponents.I[" ".length()])) {
                final int index = s.indexOf(0x2F ^ 0x25);
                final String substring = s.substring(index + " ".length());
                s = s.substring("".length(), index + " ".length());
                final ChatComponentText chatComponentText2 = new ChatComponentText(substring);
                chatComponentText2.setChatStyle(chatComponent2.getChatStyle().createShallowCopy());
                arrayList2.add(i + " ".length(), chatComponentText2);
                n3 = " ".length();
            }
            final String func_178909_a = func_178909_a(String.valueOf(chatComponent2.getChatStyle().getFormattingCode()) + s, b2);
            String substring2;
            if (func_178909_a.endsWith(GuiUtilRenderComponents.I["  ".length()])) {
                substring2 = func_178909_a.substring("".length(), func_178909_a.length() - " ".length());
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                substring2 = func_178909_a;
            }
            final String s2 = substring2;
            int n4 = fontRenderer.getStringWidth(s2);
            ChatComponentText chatComponentText3 = new ChatComponentText(s2);
            chatComponentText3.setChatStyle(chatComponent2.getChatStyle().createShallowCopy());
            if (n2 + n4 > n) {
                String s3 = fontRenderer.trimStringToWidth(func_178909_a, n - n2, "".length() != 0);
                String substring3;
                if (s3.length() < func_178909_a.length()) {
                    substring3 = func_178909_a.substring(s3.length());
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                }
                else {
                    substring3 = null;
                }
                String substring4 = substring3;
                if (substring4 != null && substring4.length() > 0) {
                    int lastIndex = s3.lastIndexOf(GuiUtilRenderComponents.I["   ".length()]);
                    if (lastIndex >= 0 && fontRenderer.getStringWidth(func_178909_a.substring("".length(), lastIndex)) > 0) {
                        s3 = func_178909_a.substring("".length(), lastIndex);
                        if (b) {
                            ++lastIndex;
                        }
                        substring4 = func_178909_a.substring(lastIndex);
                        "".length();
                        if (4 < 4) {
                            throw null;
                        }
                    }
                    else if (n2 > 0 && !func_178909_a.contains(GuiUtilRenderComponents.I[0x4B ^ 0x4F])) {
                        s3 = GuiUtilRenderComponents.I[0x5E ^ 0x5B];
                        substring4 = func_178909_a;
                    }
                    final ChatComponentText chatComponentText4 = new ChatComponentText(substring4);
                    chatComponentText4.setChatStyle(chatComponent2.getChatStyle().createShallowCopy());
                    arrayList2.add(i + " ".length(), chatComponentText4);
                }
                n4 = fontRenderer.getStringWidth(s3);
                chatComponentText3 = new ChatComponentText(s3);
                chatComponentText3.setChatStyle(chatComponent2.getChatStyle().createShallowCopy());
                n3 = " ".length();
            }
            if (n2 + n4 <= n) {
                n2 += n4;
                chatComponentText.appendSibling(chatComponentText3);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                n3 = " ".length();
            }
            if (n3 != 0) {
                arrayList.add(chatComponentText);
                n2 = "".length();
                chatComponentText = new ChatComponentText(GuiUtilRenderComponents.I[0x85 ^ 0x83]);
            }
            ++i;
        }
        arrayList.add(chatComponentText);
        return (List<IChatComponent>)arrayList;
    }
    
    public static String func_178909_a(final String s, final boolean b) {
        String textWithoutFormattingCodes;
        if (!b && !Minecraft.getMinecraft().gameSettings.chatColours) {
            textWithoutFormattingCodes = EnumChatFormatting.getTextWithoutFormattingCodes(s);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            textWithoutFormattingCodes = s;
        }
        return textWithoutFormattingCodes;
    }
    
    private static void I() {
        (I = new String[0x1E ^ 0x19])["".length()] = I("", "sBvKP");
        GuiUtilRenderComponents.I[" ".length()] = I("e", "owzpu");
        GuiUtilRenderComponents.I["  ".length()] = I("c", "iEoox");
        GuiUtilRenderComponents.I["   ".length()] = I("v", "VJxcP");
        GuiUtilRenderComponents.I[0x66 ^ 0x62] = I("u", "UnEsI");
        GuiUtilRenderComponents.I[0x6E ^ 0x6B] = I("", "yCnPx");
        GuiUtilRenderComponents.I[0xD ^ 0xB] = I("", "vTLmA");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
