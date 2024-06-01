package best.actinium.util.render.drag;

import best.actinium.module.Module;
import best.actinium.util.IAccess;
import best.actinium.util.render.RenderUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class Draggable implements IAccess {
    @Expose
    @SerializedName("x")
    @Getter
    @Setter
    private float xPos;
    @Expose
    @SerializedName("y")
    @Getter
    @Setter
    private float yPos;

    public float initialXVal;
    public float initialYVal;

    private float startX, startY;
    private boolean dragging;
    @Setter
    @Getter
    public static float width, height;

    @Getter
    @Expose
    @SerializedName("name")
    private String name;

    @Getter
    private final Module module;

    public Draggable(Module module, String name, float initialXVal, float initialYVal) {
        this.module = module;
        this.name = name;
        this.xPos = initialXVal;
        this.yPos = initialYVal;
        this.initialXVal = initialXVal;
        this.initialYVal = initialYVal;
    }

    public final void onDraw(int mouseX, int mouseY) {
        if (dragging) {
            xPos = mouseX - startX;
            yPos = mouseY - startY;
        }
    }

    public final void onClick(int mouseX, int mouseY, int button) {
        boolean canDrag = RenderUtil.isHovering(xPos, yPos, width, height, mouseX, mouseY);
        if (button == 0 && canDrag) {
            dragging = true;
            startX = (int) (mouseX - xPos);
            startY = (int) (mouseY - yPos);
        }
    }

    public final void onRelease(int button) {
        if (button == 0) dragging = false;
    }
}
