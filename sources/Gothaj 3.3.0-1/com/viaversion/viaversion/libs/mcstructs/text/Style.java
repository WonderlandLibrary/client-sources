package com.viaversion.viaversion.libs.mcstructs.text;

import com.viaversion.viaversion.libs.mcstructs.core.ICopyable;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import java.util.Objects;

public class Style implements ICopyable<Style> {
   private Style parent;
   private TextFormatting color;
   private Boolean obfuscated;
   private Boolean bold;
   private Boolean strikethrough;
   private Boolean underlined;
   private Boolean italic;
   private ClickEvent clickEvent;
   private AHoverEvent hoverEvent;
   private String insertion;
   private Identifier font;

   public Style setParent(Style style) {
      this.parent = style;
      return this;
   }

   public Style getParent() {
      return this.parent;
   }

   public Style setFormatting(TextFormatting formatting) {
      if (formatting == null) {
         return this;
      } else {
         if (formatting.isColor()) {
            this.color = formatting;
         } else if (TextFormatting.OBFUSCATED.equals(formatting)) {
            this.obfuscated = true;
         } else if (TextFormatting.BOLD.equals(formatting)) {
            this.bold = true;
         } else if (TextFormatting.STRIKETHROUGH.equals(formatting)) {
            this.strikethrough = true;
         } else if (TextFormatting.UNDERLINE.equals(formatting)) {
            this.underlined = true;
         } else if (TextFormatting.ITALIC.equals(formatting)) {
            this.italic = true;
         } else {
            if (!TextFormatting.RESET.equals(formatting)) {
               throw new IllegalArgumentException("Invalid TextFormatting " + formatting);
            }

            this.color = null;
            this.obfuscated = null;
            this.bold = null;
            this.strikethrough = null;
            this.underlined = null;
            this.italic = null;
            this.clickEvent = null;
            this.hoverEvent = null;
            this.insertion = null;
            this.font = null;
         }

         return this;
      }
   }

   public Style setFormatting(TextFormatting... formattings) {
      for (TextFormatting formatting : formattings) {
         this.setFormatting(formatting);
      }

      return this;
   }

   public Style setColor(int rgb) {
      this.color = new TextFormatting(rgb);
      return this;
   }

   public TextFormatting getColor() {
      return this.color == null && this.parent != null ? this.parent.getColor() : this.color;
   }

   public Style setBold(Boolean bold) {
      this.bold = bold;
      return this;
   }

   public Boolean getBold() {
      return this.bold == null && this.parent != null ? this.parent.getBold() : this.bold;
   }

   public boolean isBold() {
      Boolean bold = this.getBold();
      return bold != null && bold;
   }

   public Style setItalic(Boolean italic) {
      this.italic = italic;
      return this;
   }

   public Boolean getItalic() {
      return this.italic == null && this.parent != null ? this.parent.getItalic() : this.italic;
   }

   public boolean isItalic() {
      Boolean italic = this.getItalic();
      return italic != null && italic;
   }

   public Style setUnderlined(Boolean underlined) {
      this.underlined = underlined;
      return this;
   }

   public Boolean getUnderlined() {
      return this.underlined == null && this.parent != null ? this.parent.getUnderlined() : this.underlined;
   }

   public boolean isUnderlined() {
      Boolean underlined = this.getUnderlined();
      return underlined != null && underlined;
   }

   public Style setStrikethrough(Boolean strikethrough) {
      this.strikethrough = strikethrough;
      return this;
   }

   public Boolean getStrikethrough() {
      return this.strikethrough == null && this.parent != null ? this.parent.getStrikethrough() : this.strikethrough;
   }

   public boolean isStrikethrough() {
      Boolean strikethrough = this.getStrikethrough();
      return strikethrough != null && strikethrough;
   }

   public Style setObfuscated(Boolean obfuscated) {
      this.obfuscated = obfuscated;
      return this;
   }

   public Boolean getObfuscated() {
      return this.obfuscated == null && this.parent != null ? this.parent.getObfuscated() : this.obfuscated;
   }

   public boolean isObfuscated() {
      Boolean obfuscated = this.getObfuscated();
      return obfuscated != null && obfuscated;
   }

   public Style setClickEvent(ClickEvent clickEvent) {
      this.clickEvent = clickEvent;
      return this;
   }

   public ClickEvent getClickEvent() {
      return this.clickEvent == null && this.parent != null ? this.parent.getClickEvent() : this.clickEvent;
   }

   public Style setHoverEvent(AHoverEvent hoverEvent) {
      this.hoverEvent = hoverEvent;
      return this;
   }

   public AHoverEvent getHoverEvent() {
      return this.hoverEvent == null && this.parent != null ? this.parent.getHoverEvent() : this.hoverEvent;
   }

   public Style setInsertion(String insertion) {
      this.insertion = insertion;
      return this;
   }

   public String getInsertion() {
      return this.insertion == null && this.parent != null ? this.parent.getInsertion() : this.insertion;
   }

   public Style setFont(Identifier font) {
      this.font = font;
      return this;
   }

   public Identifier getFont() {
      return this.font == null && this.parent != null ? this.parent.getFont() : this.font;
   }

   public boolean isEmpty() {
      return this.getColor() == null
         && this.getBold() == null
         && this.getItalic() == null
         && this.getUnderlined() == null
         && this.getStrikethrough() == null
         && this.getObfuscated() == null
         && this.getClickEvent() == null
         && this.getHoverEvent() == null
         && this.getInsertion() == null
         && this.getFont() == null;
   }

   public Style copy() {
      Style style = new Style();
      style.parent = this.parent;
      style.color = this.color;
      style.bold = this.bold;
      style.italic = this.italic;
      style.underlined = this.underlined;
      style.strikethrough = this.strikethrough;
      style.obfuscated = this.obfuscated;
      style.clickEvent = this.clickEvent;
      style.hoverEvent = this.hoverEvent;
      style.insertion = this.insertion;
      style.font = this.font;
      return style;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Style style = (Style)o;
         return Objects.equals(this.parent, style.parent)
            && Objects.equals(this.color, style.color)
            && Objects.equals(this.obfuscated, style.obfuscated)
            && Objects.equals(this.bold, style.bold)
            && Objects.equals(this.strikethrough, style.strikethrough)
            && Objects.equals(this.underlined, style.underlined)
            && Objects.equals(this.italic, style.italic)
            && Objects.equals(this.clickEvent, style.clickEvent)
            && Objects.equals(this.hoverEvent, style.hoverEvent)
            && Objects.equals(this.insertion, style.insertion)
            && Objects.equals(this.font, style.font);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.parent,
         this.color,
         this.obfuscated,
         this.bold,
         this.strikethrough,
         this.underlined,
         this.italic,
         this.clickEvent,
         this.hoverEvent,
         this.insertion,
         this.font
      );
   }

   @Override
   public String toString() {
      return "Style{parent="
         + this.parent
         + ", color="
         + this.color
         + ", obfuscated="
         + this.obfuscated
         + ", bold="
         + this.bold
         + ", strikethrough="
         + this.strikethrough
         + ", underlined="
         + this.underlined
         + ", italic="
         + this.italic
         + ", clickEvent="
         + this.clickEvent
         + ", hoverEvent="
         + this.hoverEvent
         + ", insertion='"
         + this.insertion
         + '\''
         + ", font="
         + this.font
         + '}';
   }
}
