package com.alan.clients.ui.click.standard.screen;

public interface Screen {

    default void onRender(final int mouseX, final int mouseY, final float partialTicks) {

    }

    default void onKey(final char typedChar, final int keyCode) {

    }

    default void onClick(final int mouseX, final int mouseY, final int mouseButton) {

    }

    default void onMouseRelease() {

    }

    default void onBloom() {

    }

    default void onInit() {

    }

    default boolean automaticSearchSwitching() {
        return true;
    }

    default boolean hideSideBar() {
        return false;
    }
}
