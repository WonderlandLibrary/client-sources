package com.viaversion.viaversion.libs.mcstructs.text.serializer;

import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import java.util.function.Function;

public class LegacyStringDeserializer {
   public static ATextComponent parse(String s, boolean unknownWhite) {
      return parse(s, '§', unknownWhite);
   }

   public static ATextComponent parse(String s, char colorChar, boolean unknownWhite) {
      return parse(s, colorChar, c -> {
         TextFormatting formatting = TextFormatting.getByCode(c);
         if (formatting == null) {
            return unknownWhite ? TextFormatting.WHITE : null;
         } else {
            return formatting;
         }
      });
   }

   public static ATextComponent parse(String s, char colorChar, Function<Character, TextFormatting> formattingResolver) {
      char[] chars = s.toCharArray();
      Style style = new Style();
      StringBuilder currentPart = new StringBuilder();
      ATextComponent out = new StringComponent("");

      for (int i = 0; i < chars.length; i++) {
         char c = chars[i];
         if (c == colorChar) {
            if (i + 1 < chars.length) {
               char format = chars[++i];
               TextFormatting formatting = formattingResolver.apply(format);
               if (formatting != null) {
                  if (currentPart.length() != 0) {
                     out.append(new StringComponent(currentPart.toString()).setStyle(style.copy()));
                     currentPart = new StringBuilder();
                     if (formatting.isColor() || TextFormatting.RESET.equals(formatting)) {
                        style = new Style();
                     }
                  }

                  style.setFormatting(formatting);
               }
            }
         } else {
            currentPart.append(c);
         }
      }

      if (currentPart.length() != 0) {
         out.append(new StringComponent(currentPart.toString()).setStyle(style));
      }

      return out.getSiblings().size() == 1 ? out.getSiblings().get(0) : out;
   }
}
