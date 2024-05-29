package dev.client.tenacity.utils.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.render.ArraylistMod;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.misc.StringUtils;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import dev.utils.Utils;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.List;

public class Dragging implements Utils {
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

    private final Module module;

    public Animation hoverAnimation = new DecelerateAnimation(250, 1, Direction.BACKWARDS);

    public Dragging(Module module, String name, float initialXVal, float initialYVal) {
        this.module = module;
        this.name = name;
        this.xPos = initialXVal;
        this.yPos = initialYVal;
        this.initialXVal = initialXVal;
        this.initialYVal = initialYVal;
    }

    public Module getModule() {
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


    private String longestModule;

    public final void onDraw(int mouseX, int mouseY) {
        boolean hovering = HoveringUtil.isHovering(xPos, yPos, width, height, mouseX, mouseY);
        if (dragging) {
            xPos = (mouseX - startX);
            yPos = (mouseY - startY);
        }
        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);
        if (!hoverAnimation.isDone() || hoverAnimation.finished(Direction.FORWARDS)) {
            RoundedUtil.drawRoundOutline(xPos - 4, yPos - 4, width + 8, height + 8, 10, 2,
                    ColorUtil.applyOpacity(Color.WHITE, 0), ColorUtil.applyOpacity(Color.WHITE, (float) hoverAnimation.getOutput()));
        }
    }

    public final void onDrawArraylist(ArraylistMod arraylistMod, int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(mc);

        List<Module> modules = StringUtils.getToggledModules(arraylistMod.modules);

        String longest = getLongestModule(arraylistMod);

        width = (float) (FontUtil.tenacityFont20.getStringWidth(longest) + 5);
        height = (float) ((arraylistMod.height.getValue() + 1) * modules.size());

        float textVal = (float) FontUtil.tenacityFont20.getStringWidth(longest);
        float xVal = sr.getScaledWidth() - (textVal + 8 + xPos);

        if (sr.getScaledWidth() - xPos <= sr.getScaledWidth() / 2f) {
            xVal += textVal - 2;
        }

        boolean hovering = HoveringUtil.isHovering(xVal, yPos, width, height, mouseX, mouseY);

        if (dragging) {
            xPos = -(mouseX - startX);
            yPos = (mouseY - startY);
        }
        hoverAnimation.setDirection(hovering ? Direction.FORWARDS : Direction.BACKWARDS);

        if (!hoverAnimation.isDone() || hoverAnimation.finished(Direction.FORWARDS)) {
            RoundedUtil.drawRoundOutline(xVal, yPos - 8, width + 20, height + 16, 10, 2,
                    ColorUtil.applyOpacity(Color.BLACK, (float) (0f * hoverAnimation.getOutput())), ColorUtil.applyOpacity(Color.WHITE, (float) hoverAnimation.getOutput()));
        }

    }

    public final void onClick(int mouseX, int mouseY, int button) {
        boolean canDrag = HoveringUtil.isHovering(xPos, yPos, width, height, mouseX, mouseY);
        if (button == 0 && canDrag) {
            dragging = true;
            startX = (int) (mouseX - xPos);
            startY = (int) (mouseY - yPos);
        }
    }

    public final void onClickArraylist(ArraylistMod arraylistMod, int mouseX, int mouseY, int button) {
        ScaledResolution sr = new ScaledResolution(mc);

        String longest = getLongestModule(arraylistMod);

        float textVal = (float) FontUtil.tenacityFont20.getStringWidth(longest);
        float xVal = sr.getScaledWidth() - (textVal + 8 + xPos);

        if (sr.getScaledWidth() - xPos <= sr.getScaledWidth() / 2f) {
            xVal += textVal - 16;
        }

        boolean canDrag = HoveringUtil.isHovering(xVal, yPos, width, height, mouseX, mouseY);

        if (button == 0 && canDrag) {
            dragging = true;
            startX = (int) (mouseX + xPos);
            startY = (int) (mouseY - yPos);
        }
    }

    public final void onRelease(int button) {
        if (button == 0) dragging = false;
    }


    private String getLongestModule(ArraylistMod arraylistMod) {
        return arraylistMod.longest;
    }


}
