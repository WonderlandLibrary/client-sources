package im.expensive.ui.styles;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StyleManager {
    final List<Style> styleList;

    @Setter
    Style currentStyle;

    public StyleManager(List<Style> styleList, Style currentStyle) {
        this.styleList = styleList;
        this.currentStyle = currentStyle;
    }
}
