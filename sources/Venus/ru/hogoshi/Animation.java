/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.hogoshi;

import ru.hogoshi.AnimationType;
import ru.hogoshi.bezier.Bezier;
import ru.hogoshi.bezier.list.CubicBezier;
import ru.hogoshi.util.Easing;
import ru.hogoshi.util.Easings;

public class Animation {
    private long start;
    private double duration;
    private double fromValue;
    private double toValue;
    private double value;
    private Easing easing = Easings.NONE;
    private Bezier bezier = new CubicBezier();
    private AnimationType type = AnimationType.EASING;
    private boolean debug = false;

    public Animation animate(double d, double d2) {
        return this.animate(d, d2, Easings.NONE, true);
    }

    public Animation animate(double d, double d2, Easing easing) {
        return this.animate(d, d2, easing, true);
    }

    public Animation animate(double d, double d2, Bezier bezier) {
        return this.animate(d, d2, bezier, true);
    }

    public Animation animate(double d, double d2, boolean bl) {
        return this.animate(d, d2, Easings.NONE, bl);
    }

    public Animation animate(double d, double d2, Easing easing, boolean bl) {
        if (this.check(bl, d)) {
            if (this.isDebug()) {
                System.out.println("Animate cancelled due to target val equals from val");
            }
            return this;
        }
        this.setType(AnimationType.EASING).setEasing(easing).setDuration(d2 * 1000.0).setStart(System.currentTimeMillis()).setFromValue(this.getValue()).setToValue(d);
        if (this.isDebug()) {
            System.out.println("#animate {\n    to value: " + this.getToValue() + "\n    from value: " + this.getValue() + "\n    duration: " + this.getDuration() + "\n}");
        }
        return this;
    }

    public Animation animate(double d, double d2, Bezier bezier, boolean bl) {
        if (this.check(bl, d)) {
            if (this.isDebug()) {
                System.out.println("Animate cancelled due to target val equals from val");
            }
            return this;
        }
        this.setType(AnimationType.BEZIER).setBezier(bezier).setDuration(d2 * 1000.0).setStart(System.currentTimeMillis()).setFromValue(this.getValue()).setToValue(d);
        if (this.isDebug()) {
            System.out.println("#animate {\n    to value: " + this.getToValue() + "\n    from value: " + this.getValue() + "\n    duration: " + this.getDuration() + "\n    type: " + this.getType().name() + "\n}");
        }
        return this;
    }

    public boolean update() {
        boolean bl = this.isAlive();
        if (bl) {
            if (this.getType().equals((Object)AnimationType.BEZIER)) {
                this.setValue(this.interpolate(this.getFromValue(), this.getToValue(), this.getBezier().getValue(this.calculatePart())));
            } else {
                this.setValue(this.interpolate(this.getFromValue(), this.getToValue(), this.getEasing().ease(this.calculatePart())));
            }
        } else {
            this.setStart(0L);
            this.setValue(this.getToValue());
        }
        return bl;
    }

    public boolean isAlive() {
        return !this.isDone();
    }

    public boolean isDone() {
        return this.calculatePart() >= 1.0;
    }

    public double calculatePart() {
        return (double)(System.currentTimeMillis() - this.getStart()) / this.getDuration();
    }

    public boolean check(boolean bl, double d) {
        return bl && this.isAlive() && (d == this.getFromValue() || d == this.getToValue() || d == this.getValue());
    }

    public double interpolate(double d, double d2, double d3) {
        return d + (d2 - d) * d3;
    }

    public long getStart() {
        return this.start;
    }

    public double getDuration() {
        return this.duration;
    }

    public double getFromValue() {
        return this.fromValue;
    }

    public double getToValue() {
        return this.toValue;
    }

    public double getValue() {
        return this.value;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public AnimationType getType() {
        return this.type;
    }

    public Easing getEasing() {
        return this.easing;
    }

    public Bezier getBezier() {
        return this.bezier;
    }

    public Animation setStart(long l) {
        this.start = l;
        return this;
    }

    public Animation setDuration(double d) {
        this.duration = d;
        return this;
    }

    public Animation setFromValue(double d) {
        this.fromValue = d;
        return this;
    }

    public Animation setToValue(double d) {
        this.toValue = d;
        return this;
    }

    public Animation setValue(double d) {
        this.value = d;
        return this;
    }

    public Animation setEasing(Easing easing) {
        this.easing = easing;
        return this;
    }

    public Animation setDebug(boolean bl) {
        this.debug = bl;
        return this;
    }

    public Animation setBezier(Bezier bezier) {
        this.bezier = bezier;
        return this;
    }

    public Animation setType(AnimationType animationType) {
        this.type = animationType;
        return this;
    }
}

