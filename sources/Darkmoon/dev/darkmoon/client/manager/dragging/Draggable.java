package dev.darkmoon.client.manager.dragging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.utility.render.RenderUtility;
import lombok.Getter;
import lombok.Setter;

public class Draggable {
    @Expose
    @Getter
    @Setter
    @SerializedName("x")
    private int x;
    @Expose
    @Getter
    @Setter
    @SerializedName("y")
    private int y;

    public int initialXVal;
    public int initialYVal;

    private int startX, startY;
    private boolean dragging;

    @Getter
    @Setter
    private float width, height;

    @Expose
    @Getter
    @SerializedName("name")
    private String name;

    @Getter
    private final Module module;

    public Draggable(Module module, String name, int initialXVal, int initialYVal) {
        this.module = module;
        this.name = name;
        this.x = initialXVal;
        this.y = initialYVal;
        this.initialXVal = initialXVal;
        this.initialYVal = initialYVal;
    }

    public final void onDraw(int mouseX, int mouseY) {
        if (dragging) {
            x = (mouseX - startX);
            y = (mouseY - startY);
        }
    }

    public final void onClick(int mouseX, int mouseY, int button) {
        if (button == 0 && RenderUtility.isHovered(mouseX, mouseY, x, y, width, height)) {
            dragging = true;
            startX = mouseX - x;
            startY = mouseY - y;
        }
    }

    public final void onRelease(int button) {
        if (button == 0) dragging = false;
    }
}
