package alphentus.utils;

import net.minecraft.client.gui.Gui;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class Scrollbar {

    public float startX, startY, endY, currentScroll, maxScroll, minScroll;

    public Scrollbar() {
    }

    public void drawScrollBar(int mouseX, int mouseY) {
        maxScroll = maxScroll + currentScroll / 14;
        double current = ((currentScroll - minScroll) / (maxScroll - minScroll));

        RenderUtils.relativeRect(startX, startY, startX + 2, startY + endY, Integer.MIN_VALUE);
        RenderUtils.relativeRect(startX, (float) (startY + current * endY), startX + 2, (float) (startY + 25 + (current * endY)), Integer.MAX_VALUE);
    }

    public void setInformation(float startX, float startY, float endY, float currentScroll, float maxScroll, float minScroll) {
        this.startX = startX;
        this.startY = startY;
        this.endY = endY;
        this.currentScroll = currentScroll;
        this.maxScroll = maxScroll;
        this.minScroll = minScroll;
    }

    public float getStartX() {
        return startX;
    }


    public float getStartY() {
        return startY;
    }


    public float getEndY() {
        return endY;
    }

    public float getCurrentScroll() {
        return currentScroll;
    }

    public float getMaxScroll() {
        return maxScroll;
    }

    public float getMinScroll() {
        return minScroll;
    }

}
