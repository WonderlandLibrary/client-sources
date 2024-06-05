package net.minecraft.util;

import java.util.List;
import net.aSv;
import net.minecraft.util.ChatStyle;

public interface IChatComponent extends Iterable {
   IChatComponent setChatStyle(ChatStyle var1);

   ChatStyle getChatStyle();

   IChatComponent appendText(String var1);

   IChatComponent appendSibling(IChatComponent var1);

   String getUnformattedTextForChat();

   @aSv
   String getUnformattedText();

   @aSv
   String getFormattedText();

   List getSiblings();

   IChatComponent createCopy();
}
