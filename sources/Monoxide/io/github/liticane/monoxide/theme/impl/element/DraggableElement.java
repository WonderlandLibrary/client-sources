package io.github.liticane.monoxide.theme.impl.element;

import java.util.function.Supplier;
import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.theme.ThemeObject;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjglx.input.Mouse;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;

public abstract class DraggableElement extends ThemeObject {

    private NumberValue<Float> x, y, width, height;
    private Class linkedModule;
    private float startX, startY;
    private BooleanValue locked;
    private float draggingX, draggingY;
    private boolean dragging = false;

    public DraggableElement(float x, float y, float width, float height, Supplier<Boolean> showWidthAndHeight, Class linkedModule) {
        startX = x;
        startY = y;
        this.locked = new BooleanValue("Locked", this, true);
        this.x = new NumberValue<>("X", this, x, 0f, 999999999f, 1, new Supplier[]{() -> !locked.getValue()});
        this.y = new NumberValue<>("Y", this, y, 0f, 999999999f, 1, new Supplier[]{() -> !locked.getValue()});
        this.width = new NumberValue<>("Width", this, width, 0f, 999999999f, 0, new Supplier[]{() -> !locked.getValue(), showWidthAndHeight == null ? () -> false : showWidthAndHeight});
        this.height = new NumberValue<>("Height", this, height, 0f, 999999999f, 0, new Supplier[]{() -> !locked.getValue(), showWidthAndHeight == null ? () -> false : showWidthAndHeight});
        this.linkedModule = linkedModule;
    }

    public void onDrag(int mouseX, int mouseY) {
        if(this.locked.getValue()) {
            this.x.setValue(startX);
            this.y.setValue(startY);
        } else if(Mouse.isButtonDown(0)) {
            if(!dragging) {
                draggingX = mouseX - x.getValue();
                draggingY = mouseY - y.getValue();
            }
            x.setValue(mouseX - draggingX);
            y.setValue(mouseY - draggingY);
            dragging = true;
        } else {
            dragging = false;
        }
    }

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, Object[] params) {
        this.x.setMaximum((float)scaledResolution.getScaledWidth());
        this.y.setMaximum((float)scaledResolution.getScaledHeight());

        if(this.locked.getValue()) {
            this.x.setValue(startX);
            this.y.setValue(startY);
        }
        onDraw(scaledResolution, partialTicks, leftY, rightY);
    }

    @Override
    public void onEnable() {
        Module linkedModule = ModuleManager.getInstance().getModule(this.linkedModule);
        for(Value value : ValueManager.getInstance().getValues(this)) {
            ValueManager.getInstance().addLinkedValues(linkedModule, value);
        }
    }

    @Override
    public void onDisable() {
        Module linkedModule = ModuleManager.getInstance().getModule(this.linkedModule);
        for(Value value : ValueManager.getInstance().getValues(this)) {
            ValueManager.getInstance().removeLinkedValues(linkedModule, value);
        }
    }

    public abstract void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY);


    public NumberValue<Float> getPosX() {
        return x;
    }

    public NumberValue<Float> getPosY() {
        return y;
    }

    public NumberValue<Float> getWidth() {
        return width;
    }

    public NumberValue<Float> getHeight() {
        return height;
    }

    public BooleanValue getLocked() {
        return locked;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }
}
