package dev.excellent.impl.value.impl;

import dev.excellent.client.module.api.Module;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.mode.Mode;
import dev.luvbeeq.animation.util.Easings;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2d;

import java.util.List;
import java.util.function.BooleanSupplier;

@Getter
@Setter
public class DragValue extends Value<Vector2d> {

    public Vector2d position = new Vector2d(100, 100), targetPosition = new Vector2d(100, 100), size = new Vector2d(100, 100), lastScale = new Vector2d(-1, -1);
    public Animation animationPosition = new Animation(Easing.LINEAR, 300), smoothAnimation = new Animation(Easing.EASE_OUT_EXPO, 150);
    public dev.luvbeeq.animation.Animation ans = new dev.luvbeeq.animation.Animation();
    public boolean render = true, structure;

    public DragValue(final String name, final Module parent, final Vector2d defaultValue) {
        super(name, parent, defaultValue);
        ans.run(1, 100, Easings.QUAD_OUT);
    }

    public DragValue(final String name, final Module parent, final Vector2d defaultValue, final boolean render) {
        super(name, parent, defaultValue);
        this.render = render;
    }

    public DragValue(final String name, final Module parent, final Vector2d defaultValue, final boolean render, final boolean structure) {
        super(name, parent, defaultValue);
        this.render = render && !structure;
        this.structure = structure;
    }

    public DragValue(final String name, final Mode<?> parent, final Vector2d defaultValue) {
        super(name, parent, defaultValue);
    }

    public DragValue(final String name, final Module parent, final Vector2d defaultValue, final BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
    }

    public DragValue(final String name, final Mode<?> parent, final Vector2d defaultValue, final BooleanSupplier hideIf) {
        super(name, parent, defaultValue, hideIf);
    }

    @Override
    public List<Value<?>> getSubValues() {
        return null;
    }

    public void setSize(Vector2d size) {
        this.size = size;
        if (lastScale.x == -1 && lastScale.y == -1) {
            this.lastScale = this.size;
        }
        int width = (int) scaled().x;
        int height = (int) scaled().y;

        if (this.position.x > width / 2f) {
            this.targetPosition.x += this.lastScale.x - this.size.x;
            this.position = targetPosition;
        }

        if (this.position.y > height / 2f) {
            this.targetPosition.y += this.lastScale.y - this.size.y;
            this.position = targetPosition;
        }

        this.lastScale = size;
    }
}