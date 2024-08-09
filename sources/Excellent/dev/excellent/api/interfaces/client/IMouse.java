package dev.excellent.api.interfaces.client;

public interface IMouse {

    default boolean isHover(final float mouseX, final float mouseY, final float x, final float y, final float width, final float height) {
        return (mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height));
    }

    default boolean isHover(final double mouseX, final double mouseY, final double x, final double y, final double width, final double height) {
        return (mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height));
    }

    default boolean isLClick(int button) {
        return button == 0;
    }

    default boolean isRClick(int button) {
        return button == 1;
    }

    default boolean isMClick(int button) {
        return button == 2;
    }


}
