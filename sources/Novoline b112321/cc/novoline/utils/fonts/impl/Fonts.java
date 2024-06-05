package cc.novoline.utils.fonts.impl;

import cc.novoline.Novoline;
import cc.novoline.utils.fonts.api.FontManager;

public interface Fonts {
   FontManager FONT_MANAGER = Novoline.getInstance().getFontManager();
}
