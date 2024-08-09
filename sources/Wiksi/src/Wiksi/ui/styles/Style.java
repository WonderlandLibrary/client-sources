package src.Wiksi.ui.styles;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.List;

@Data
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Style {
    String styleName;
    Color firstColor;
    Color secondColor;
    Color tree;
    Color four;
    java.util.List<Color> colors; // Добавлено поле для списка цветов
    public Color getColor(int index) {
        return index == 0 ? firstColor : secondColor;
    }
    // Конструктор для двух цветов
    public Style(String styleName, Color firstColor, Color secondColor) {
        this.styleName = styleName;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
    }

    // Конструктор для списка цветов
    public Style(String styleName, List<Color> colors) {
        this.styleName = styleName;
        this.colors = colors;
    }
}

