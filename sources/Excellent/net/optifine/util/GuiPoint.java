package net.optifine.util;

import lombok.Getter;

@Getter
public class GuiPoint {
    private final int x;
    private final int y;

    public GuiPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
