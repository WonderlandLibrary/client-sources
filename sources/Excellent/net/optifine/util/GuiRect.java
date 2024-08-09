package net.optifine.util;

import lombok.Getter;

@Getter
public class GuiRect {
    private final int left;
    private final int top;
    private final int right;
    private final int bottom;

    public GuiRect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

}
