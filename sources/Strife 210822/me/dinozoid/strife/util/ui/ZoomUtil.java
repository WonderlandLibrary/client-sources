package me.dinozoid.strife.util.ui;

import me.dinozoid.strife.util.MinecraftUtil;
import me.dinozoid.strife.util.render.RenderUtil;
import me.dinozoid.strife.util.system.TimerUtil;

public class ZoomUtil extends MinecraftUtil {

    private float originalX, originalY, originalWidth, originalHeight;
    private float x, y, width, height;

    private float speed, zoomFactor;
    private long nextUpdateTime;

    private TimerUtil timer = new TimerUtil();

    public ZoomUtil(float x, float y, float width, float height, long nextUpdateTime, float speed, float zoomFactor) {
        this.originalX = x;
        this.originalY = y;
        this.originalWidth = width;
        this.originalHeight = height;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.zoomFactor = zoomFactor;
        this.nextUpdateTime = nextUpdateTime;
    }

    public void update(int mouseX, int mouseY) {
        if (RenderUtil.isHovered(x, y, width, height, mouseX, mouseY)) {
            if (timer.hasElapsed(nextUpdateTime)) {
                x = RenderUtil.animate(originalX - zoomFactor / 2, x, speed) - 0.1f;
                y = RenderUtil.animate(originalY - zoomFactor / 2, y, speed) - 0.1f;
                width = RenderUtil.animate(originalWidth + zoomFactor, width, speed) - 0.1f;
                height = RenderUtil.animate(originalHeight + zoomFactor, height, speed) - 0.1f;
                timer.reset();
            }
        } else if (timer.hasElapsed(nextUpdateTime)) {
                x = RenderUtil.animate(originalX, x, speed) - 0.1f;
                y = RenderUtil.animate(originalY, y, speed) - 0.1f;
                width = RenderUtil.animate(originalWidth, width, speed) - 0.1f;
                height = RenderUtil.animate(originalHeight, height, speed) - 0.1f;
                timer.reset();
            }
    }

    public void setPosition(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getOriginalX() {
        return originalX;
    }

    public float getOriginalY() {
        return originalY;
    }

    public float getOriginalWidth() {
        return originalWidth;
    }

    public float getOriginalHeight() {
        return originalHeight;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }

    public float getZoomFactor() {
        return zoomFactor;
    }

    public long getNextUpdateTime() {
        return nextUpdateTime;
    }

    public TimerUtil getTimer() {
        return timer;
    }
}
