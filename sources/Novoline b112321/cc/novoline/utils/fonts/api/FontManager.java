package cc.novoline.utils.fonts.api;

import cc.novoline.utils.fonts.api.FontFamily;
import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.api.FontType;

@FunctionalInterface
public interface FontManager {
   FontFamily fontFamily(FontType var1);

   default FontRenderer font(FontType var1, int var2) {
      return this.fontFamily(var1).ofSize(var2);
   }
}
