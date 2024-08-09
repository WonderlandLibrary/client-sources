package src.Wiksi.utils.drag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.api.Function;
import src.Wiksi.utils.client.ClientUtil;
import src.Wiksi.utils.client.Vec2i;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.DisplayUtils;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.ResourceLocation;

import static src.Wiksi.utils.client.IMinecraft.mc;

public class Dragging {
    @Expose
    @SerializedName("x")
    private float xPos;
    @Expose
    @SerializedName("y")
    private float yPos;

    public float initialXVal;
    public float initialYVal;

    private float startX, startY;
    private boolean dragging;

    private float width, height;

    @Expose
    @SerializedName("name")
    private final String name;

    private final Function module;

    private static final float CENTER_LINE_WIDTH = 1.0f;
    private static final float SNAP_THRESHOLD = 10.0f;

    private float lineAlpha = 0.0f;
    private long lastUpdateTime;

    public Dragging(Function module, String name, float initialXVal, float initialYVal) {
        this.module = module;
        this.name = name;
        this.xPos = initialXVal;
        this.yPos = initialYVal;
        this.initialXVal = initialXVal;
        this.initialYVal = initialYVal;
    }

    public Function getModule() {
        return module;
    }

    public String getName() {
        return name;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return xPos;
    }

    public void setX(float x) {
        this.xPos = x;
    }

    public float getY() {
        return yPos;
    }

    public void setY(float y) {
        this.yPos = y;
    }

    public final void onDraw(int mouseX, int mouseY, MainWindow res) {
        Vec2i fixed = ClientUtil.getMouse(mouseX, mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();

        float centerX = res.scaledWidth() / 2.0f;
        float centerY = res.scaledHeight() / 2.0f;

        if (dragging) {
            xPos = (mouseX - startX);
            yPos = (mouseY - startY);

            if (Math.abs(xPos + width / 2.0f - centerX) < SNAP_THRESHOLD) {
                xPos = centerX - width / 2.0f;
            }

            if (Math.abs(yPos + height / 2.0f - centerY) < SNAP_THRESHOLD) {
                yPos = centerY - height / 2.0f;
            }

            if (xPos + width > res.scaledWidth()) {
                xPos = res.scaledWidth() - width;
            }
            if (yPos + height > res.scaledHeight()) {
                yPos = res.scaledHeight() - height;
            }
            if (xPos < 0) {
                xPos = 0;
            }
            if (yPos < 0) {
                yPos = 0;
            }

            updateLineAlpha(true);
        } else {
            updateLineAlpha(false);
        }

        drawCenterLines(res);
    }

    private void updateLineAlpha(boolean increasing) {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;
        lastUpdateTime = currentTime;

        if (increasing) {
            lineAlpha += deltaTime * 2.0f;
            if (lineAlpha > 1.0f) {
                lineAlpha = 1.0f;
            }
        } else {
            lineAlpha -= deltaTime * 2.0f;
            if (lineAlpha < 0.0f) {
                lineAlpha = 0.0f;
            }
        }
    }

    private void drawCenterLines(MainWindow res) {
        if (lineAlpha > 0.0f) {
            float centerX = res.scaledWidth() / 2.0f;
            float centerY = res.scaledHeight() / 2.0f;

            int color = (int) (lineAlpha * 255) << 24 | 0xFFFFFF;

            DisplayUtils.drawRoundedRect(centerX - CENTER_LINE_WIDTH / 2.0f, 0, CENTER_LINE_WIDTH, res.scaledHeight(), 1, color);
            DisplayUtils.drawRoundedRect(0, centerY - CENTER_LINE_WIDTH / 2.0f, res.scaledWidth(), CENTER_LINE_WIDTH, 1, color);
        }
    }

    public final boolean onClick(double mouseX, double mouseY, int button) {
        Vec2i fixed = ClientUtil.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();
        if (button == 0 && MathUtil.isHovered((float) mouseX, (float) mouseY, xPos, yPos, width, height)) {
            dragging = true;
            startX = (int) (mouseX - xPos);
            startY = (int) (mouseY - yPos);
            lastUpdateTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public final void onRelease(int button) {
        if (button == 0) dragging = false;
    }
}