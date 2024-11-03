package com.viaversion.viaversion.libs.mcstructs.text;

import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import java.awt.Color;

public class TextComponentBuilder {
   public static ATextComponent build(Object... parts) {
      StringComponent out = new StringComponent("");
      StringComponent current = null;
      Style style = new Style();

      for (Object part : parts) {
         if (part == null) {
            checkAppend(out, current, style);
            current = null;
            style = new Style();
         } else if (part instanceof TextFormatting) {
            style.setFormatting((TextFormatting)part);
         } else if (part instanceof Color) {
            style.setFormatting(new TextFormatting(((Color)part).getRGB()));
         } else if (part instanceof ClickEvent) {
            style.setClickEvent((ClickEvent)part);
         } else if (part instanceof AHoverEvent) {
            style.setHoverEvent((AHoverEvent)part);
         } else if (part instanceof Style) {
            style = (Style)part;
         } else if (part instanceof ATextComponent) {
            if (checkAppend(out, current, style)) {
               current = null;
               style = new Style();
            }

            if (current == null) {
               current = (StringComponent)part;
            } else {
               current.append((ATextComponent)part);
            }
         } else {
            if (checkAppend(out, current, style)) {
               current = null;
               style = new Style();
            }

            if (current == null) {
               current = new StringComponent(part.toString());
            } else {
               current.append(part.toString());
            }
         }
      }

      if (current != null) {
         if (!style.isEmpty()) {
            current.setStyle(style);
         }

         out.append(current);
      }

      return (ATextComponent)(out.getSiblings().size() == 1 ? out.getSiblings().get(0) : out);
   }

   private static boolean checkAppend(ATextComponent out, ATextComponent current, Style style) {
      if (current == null) {
         return !style.isEmpty();
      } else if (style.isEmpty()) {
         return false;
      } else {
         out.append(current.setStyle(style));
         return true;
      }
   }
}
