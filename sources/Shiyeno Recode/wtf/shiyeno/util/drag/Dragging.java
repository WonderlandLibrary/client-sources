package wtf.shiyeno.util.drag;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.client.MainWindow;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.ScaleMath;
import wtf.shiyeno.util.render.Vec2i;

import java.util.Objects;

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
    private String name;

    private final Function module;


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
        Vec2i fixed = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();

        if (dragging) {
            xPos = (mouseX - startX);
            yPos = (mouseY - startY);

            float gap = 3f;

            if (Math.abs(res.getScaledHeight() / 2f - (yPos + height / 2)) <= 5) {
                RenderUtil.Render2D.drawRect(0, res.getScaledHeight() / 2f - 0.25f, res.getScaledWidth(), 0.5f, ColorUtil.rgba(255, 255, 255, 255 / 2));
                yPos = res.getScaledHeight() / 2f - height / 2;
            }

            if (Math.abs(res.getScaledWidth() / 2f - (xPos + width / 2)) <= 5) {
                RenderUtil.Render2D.drawRect(res.getScaledWidth() / 2f - 0.25f, 0, 0.5f, res.getScaledHeight(), ColorUtil.rgba(255, 255, 255, 255 / 2));
                xPos = res.getScaledWidth() / 2f - width / 2;
            }

            for (Dragging draggable : DragManager.draggables.values().stream().filter(d -> !Objects.equals(d.name, this.name)).toList()) {
                boolean sameWidth = width == draggable.width;
                boolean aligned = false;

                if (!sameWidth)
                    gap = Math.min(Math.abs(width - draggable.width) / 3, 3);

                // start X in range
                if (areCoordsInRange(draggable.getX(), xPos, gap)) {
                    if (sameWidth || !aligned) {
                        RenderUtil.Render2D.drawRect(
                                draggable.getX() - 0.25f,
                                Math.min(draggable.getY(), yPos),
                                0.5f,
                                Math.max(draggable.getY(), yPos) - Math.min(draggable.getY(), yPos) + (draggable.getY() > yPos ? draggable.height : height),
                                ColorUtil.rgba(255, 255, 255, 255 / 2));
                        xPos = draggable.getX();
                        aligned = true;
                    }
                }

                // center X in range
                if (areCoordsInRange(draggable.getX() + draggable.width / 2, xPos + width / 2, gap)) {
                    if (sameWidth || !aligned) {
                        float top = Math.min(draggable.getY() + draggable.height / 2, yPos + height / 2);
                        float bottom = Math.max(draggable.getY() + draggable.height / 2, yPos + height / 2);

                        RenderUtil.Render2D.drawRect(
                                draggable.getX() + draggable.width / 2 - 0.25f,
                                top,
                                0.5f,
                                bottom - top,
                                ColorUtil.rgba(255, 255, 255, 255 / 2));
                        xPos = draggable.getX() - Math.abs(width - draggable.width) / 2 * (draggable.width > width ? -1 : 1);
                        aligned = true;
                    }
                }

                // end X in range
                if (areCoordsInRange(draggable.getX() + draggable.width, xPos + width, gap)) {
                    if (sameWidth || !aligned) {
                        RenderUtil.Render2D.drawRect(
                                draggable.getX() + draggable.width - 0.25f,
                                Math.min(draggable.getY(), yPos),
                                0.5f,
                                Math.max(draggable.getY(), yPos) - Math.min(draggable.getY(), yPos) + (draggable.getY() > yPos ? draggable.height : height),
                                ColorUtil.rgba(255, 255, 255, 255 / 2));
                        xPos = draggable.getX() + draggable.width - width;
                        aligned = true;
                    }
                }

                boolean sameHeight = height == draggable.height;
                aligned = false;

                if (!sameHeight)
                    gap = Math.min(Math.abs(height - draggable.height) / 3, 3);

                // start Y in range
                if (areCoordsInRange(draggable.getY(), yPos, gap)) {
                    if (sameHeight || !aligned) {
                        RenderUtil.Render2D.drawRect(
                                Math.min(draggable.getX(), xPos),
                                draggable.getY() - 0.25f,
                                Math.max(draggable.getX(), xPos) - Math.min(draggable.getX(), xPos) + (draggable.getX() > xPos ? draggable.width : width),
                                0.5f,
                                ColorUtil.rgba(255, 255, 255, 255 / 2));
                        yPos = draggable.getY();
                        aligned = true;
                    }
                }

                // center Y in range
                if (areCoordsInRange(draggable.getY() + draggable.height / 2, yPos + height / 2, gap)) {
                    if (sameHeight || !aligned) {
                        float top = Math.min(draggable.getX() + draggable.width / 2, xPos + width / 2);
                        float bottom = Math.max(draggable.getX() + draggable.width / 2, xPos + width / 2);

                        RenderUtil.Render2D.drawRect(
                                top,
                                draggable.getY() + draggable.height / 2 - 0.25f,
                                bottom - top,
                                0.5f,
                                ColorUtil.rgba(255, 255, 255, 255 / 2));
                        yPos = draggable.getY() - Math.abs(height - draggable.height) / 2 * (draggable.height > height ? -1 : 1);
                        aligned = true;
                    }
                }

                // end Y in range
                if (areCoordsInRange(draggable.getY() + draggable.height, yPos + height, gap)) {
                    if (sameHeight || !aligned) {
                        RenderUtil.Render2D.drawRect(
                                Math.min(draggable.getX(), xPos),
                                draggable.getY() + draggable.height - 0.25f,
                                Math.max(draggable.getX(), xPos) - Math.min(draggable.getX(), xPos) + (draggable.getX() > xPos ? draggable.width : width),
                                0.5f,
                                ColorUtil.rgba(255, 255, 255, 255 / 2));
                        yPos = draggable.getY() + draggable.height - height;
                        aligned = true;
                    }
                }
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
        }
    }

    public final boolean areCoordsInRange(float coord1, float coord2, float gap) {
        return Math.abs(coord1 - coord2) <= gap;
    }
    public final void onClick(double mouseX, double mouseY, int button) {

        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();
        if (button == 0 && RenderUtil.isInRegion(mouseX, mouseY, xPos, yPos, width, height)) {

            dragging = true;
            startX = (int) (mouseX - xPos);
            startY = (int) (mouseY - yPos);
        }
    }

    public final void onRelease(int button) {
        if (button == 0) dragging = false;
    }
}