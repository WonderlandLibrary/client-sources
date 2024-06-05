package cc.novoline.utils.fonts.api;

import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.api.FontType;

public interface FontFamily {
   FontRenderer ofSize(int var1);

   FontType font();
}
