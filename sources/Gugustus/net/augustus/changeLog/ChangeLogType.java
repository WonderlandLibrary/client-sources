package net.augustus.changeLog;

import java.awt.*;

public enum ChangeLogType {
    REMOVE(new Color(255, 0, 0, 150)),
    ADD(new Color(0, 255, 0, 150)),
    FIX(new Color(255, 255, 0, 150));

    final Color color;

    ChangeLogType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
