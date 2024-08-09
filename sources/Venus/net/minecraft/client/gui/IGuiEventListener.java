/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

public interface IGuiEventListener {
    default public void mouseMoved(double d, double d2) {
    }

    default public boolean mouseClicked(double d, double d2, int n) {
        return true;
    }

    default public boolean mouseReleased(double d, double d2, int n) {
        return true;
    }

    default public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        return true;
    }

    default public boolean mouseScrolled(double d, double d2, double d3) {
        return true;
    }

    default public boolean keyPressed(int n, int n2, int n3) {
        return true;
    }

    default public boolean keyReleased(int n, int n2, int n3) {
        return true;
    }

    default public boolean charTyped(char c, int n) {
        return true;
    }

    default public boolean changeFocus(boolean bl) {
        return true;
    }

    default public boolean isMouseOver(double d, double d2) {
        return true;
    }
}

