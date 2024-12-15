package com.alan.clients.ui.click.standard.components.value.impl;

import com.alan.clients.ui.click.standard.components.value.ValueComponent;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Value;

import java.awt.*;
import java.util.ArrayList;

import static com.alan.clients.module.impl.other.data.Equations.EUCLIDEAN_DISTANCE;

public class CurveValueComponent extends ValueComponent {

    private final ArrayList<Vector2d> points = new ArrayList<>();
    private Vector2d selected = null;

    public CurveValueComponent(final Value<?> value) {
        super(value);

        points.clear();
        for (int i = 0; i <= 250; i += 10) {
            points.add(new Vector2d(i, 45));
        }
    }

    @Override
    public void draw(Vector2d position, int mouseX, int mouseY, float partialTicks) {
        this.position = position;
        this.position.setX(this.position.getX() + 2.5);

        if (selected != null) {
//            selected.setX(mouseX - position.getX());
            selected.setY(mouseY - position.getY());
            selected.setX(Math.min(Math.max(0, selected.getX()), height * 5));
            selected.setY(Math.min(Math.max(0, selected.getY()), height - 7));
        }

        render();

        this.height = 100;
    }

    private void render() {
        Color first = ColorUtil.withAlpha(getTheme().getFirstColor(), getOpacity());
        Color second = ColorUtil.withAlpha(getTheme().getSecondColor(), getOpacity());

        points.forEach(point -> {
            RenderUtil.circle(position.getX() + point.getX(), position.getY() + point.getY(),
                    2.5f, first);
        });

        for (float percentage = 0; percentage <= 1; percentage += 0.1f) {
            ArrayList<Vector2d> interpolation = new ArrayList<>(points);
            ArrayList<Vector2d> toRemove = new ArrayList<>();
            ArrayList<Vector2d> toAdd = new ArrayList<>();

            while (interpolation.size() > 1) {
                for (int i = 0; i < Math.ceil((interpolation.size() - 1) / 2f); i += 2) {
                    doInterpolation(interpolation, toRemove, toAdd, percentage, i);
                }

                for (int i = interpolation.size() - 2; i >= Math.ceil((interpolation.size() - 1) / 2f); i -= 2) {
                    doInterpolation(interpolation, toRemove, toAdd, percentage, i);
                }

                interpolation.addAll(toAdd);
                toAdd.clear();

                interpolation.removeAll(toRemove);
                toRemove.clear();
            }

            RenderUtil.circle(interpolation.get(0).getX() + position.getX(), interpolation.get(0).getY() + position.getY(),
                    1.5f, ColorUtil.withAlpha(second, Math.min(200, second.getAlpha())));
        }
    }

    private void doInterpolation(ArrayList<Vector2d> interpolation, ArrayList<Vector2d> toRemove, ArrayList<Vector2d> toAdd, float percentage, int i) {
        Vector2d first = interpolation.get(i);
        Vector2d second = interpolation.get(i + 1);

        toRemove.add(first);
        toRemove.add(second);

        toAdd.add(new Vector2d(
                first.getX() + (second.getX() - first.getX()) * percentage,
                first.getY() + (second.getY() - first.getY()) * percentage)
        );
    }

    @Override
    public boolean click(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.position == null) {
            return false;
        }

        if (!MouseUtil.isHovered(position.getX(), position.getY(), 400, height)) {
            return false;
        }

        ArrayList<Vector2d> sorted = new ArrayList<>(points);
        sorted.sort((one, two) -> (int) (EUCLIDEAN_DISTANCE.run(one.getX() + position.getX() - mouseX, one.getY() + position.getY() - mouseY) -
                EUCLIDEAN_DISTANCE.run(two.getX() + position.getX() - mouseX, two.getY() + position.getY() - mouseY)));
        selected = sorted.stream().findFirst().get();

        return true;
    }

    @Override
    public void released() {
        selected = null;
    }

    @Override
    public void bloom() {
        render();
    }

    @Override
    public void key(final char typedChar, final int keyCode) {
        if (this.position == null) {
            return;
        }

    }
}
