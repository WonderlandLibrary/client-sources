package cc.novoline.utils.fonts.impl;

import cc.novoline.utils.fonts.api.FontFamily;
import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.api.FontType;
import cc.novoline.utils.fonts.impl.SimpleFontRenderer;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import java.awt.Font;
import java.util.function.IntFunction;

public final class SimpleFontFamily extends Int2ObjectAVLTreeMap implements FontFamily {
   private final FontType fontType;
   private final Font awtFont;

   private SimpleFontFamily(FontType var1, Font var2) {
      this.fontType = var1;
      this.awtFont = var2;
   }

   public static FontFamily create(FontType var0, Font var1) {
      return new SimpleFontFamily(var0, var1);
   }

   public FontRenderer ofSize(int var1) {
      return (FontRenderer)this.computeIfAbsent(var1, this::lambda$ofSize$0);
   }

   public FontType font() {
      return this.fontType;
   }

   private FontRenderer lambda$ofSize$0(int var1, int var2) {
      return SimpleFontRenderer.create(this.awtFont.deriveFont(0, (float)var1));
   }
}
