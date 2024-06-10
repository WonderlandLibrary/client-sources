package me.kaimson.melonclient.gui;

import java.util.function.*;
import org.lwjgl.input.*;

public abstract class GuiScrolling extends awi
{
    protected float target;
    protected long start;
    protected long duration;
    private static final Function<Double, Double> easingMethod;
    
    public GuiScrolling(final ave mcIn, final int width, final int height, final int topIn, final int bottomIn, final int slotHeightIn) {
        super(mcIn, width, height, topIn, bottomIn, slotHeightIn);
    }
    
    public void drawScroll(final int i, final int j) {
    }
    
    public void p() {
        if (this.g(this.j)) {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.j >= this.d && this.j <= this.e) {
                final int i2 = (this.b - this.c()) / 2;
                final int j2 = (this.b + this.c()) / 2;
                final int k2 = this.j - this.d - this.t + (int)this.n - 4;
                final int l2 = k2 / this.h;
                if (l2 < this.b() && this.i >= i2 && this.i <= j2 && l2 >= 0 && k2 >= 0) {
                    this.a(l2, false, this.i, this.j);
                    this.o = l2;
                }
                else if (this.i >= i2 && this.i <= j2 && k2 < 0) {
                    this.a(this.i - i2, this.j - this.d + (int)this.n - 4);
                }
            }
            if (Mouse.isButtonDown(0) && this.q()) {
                if (this.l != -1) {
                    if (this.l >= 0) {
                        this.n -= (this.j - this.l) * this.m;
                        this.l = this.j;
                    }
                }
                else {
                    boolean flag1 = true;
                    if (this.j >= this.d && this.j <= this.e) {
                        final int j2 = (this.b - this.c()) / 2;
                        final int k2 = (this.b + this.c()) / 2;
                        final int l2 = this.j - this.d - this.t + (int)this.n - 4;
                        final int i3 = l2 / this.h;
                        if (i3 < this.b() && this.i >= j2 && this.i <= k2 && i3 >= 0 && l2 >= 0) {
                            final boolean flag2 = i3 == this.o && ave.J() - this.p < 250L;
                            this.a(i3, flag2, this.i, this.j);
                            this.o = i3;
                            this.p = ave.J();
                        }
                        else if (this.i >= j2 && this.i <= k2 && l2 < 0) {
                            this.a(this.i - j2, this.j - this.d + (int)this.n - 4);
                            flag1 = false;
                        }
                        final int i4 = this.d();
                        final int j3 = i4 + 6;
                        if (this.i >= i4 && this.i <= j3) {
                            this.m = -1.0f;
                            int k3 = this.m();
                            if (k3 < 1) {
                                k3 = 1;
                            }
                            int l3 = (int)((this.e - this.d) * (this.e - this.d) / (float)this.k());
                            l3 = ns.a(l3, 32, this.e - this.d - 8);
                            this.m /= (this.e - this.d - l3) / (float)k3;
                        }
                        else {
                            this.m = 1.0f;
                        }
                        if (flag1) {
                            this.l = this.j;
                        }
                        else {
                            this.l = -2;
                        }
                    }
                    else {
                        this.l = -2;
                    }
                }
            }
            else {
                this.l = -1;
            }
            if (!Mouse.isButtonDown(0)) {
                int wheel = Mouse.getEventDWheel();
                if (wheel != 0) {
                    if (wheel > 0) {
                        wheel = -1;
                    }
                    else if (wheel < 0) {
                        wheel = 1;
                    }
                    this.offset(19.0f * wheel, true);
                }
            }
        }
    }
    
    public void a(final int mouseXIn, final int mouseYIn, final float p_148128_3_) {
        final float[] target = { this.target };
        this.n = this.handleScrollingPos(target, this.n, (float)this.m(), 20.0f / ave.ai(), (double)this.start, (double)this.duration);
        this.target = target[0];
        this.n = ns.a(this.n, -300.0f, (float)(this.m() + 300));
        this.target = ns.a(this.target, -300.0f, (float)(this.m() + 300));
        this.l();
        this.drawScroll(this.d(), this.d() + 3);
    }
    
    public void offset(final float value, final boolean animated) {
        this.scrollTo(this.target + value, animated);
    }
    
    public void scrollTo(final float value, final boolean animated) {
        this.scrollTo(value, animated, 600L);
    }
    
    public void scrollTo(final float value, final boolean animated, final long duration) {
        this.target = ns.a(value, -300.0f, (float)(this.m() + 300));
        if (animated) {
            this.start = System.currentTimeMillis();
            this.duration = duration;
        }
        else {
            this.n = this.target;
        }
    }
    
    private float handleScrollingPos(final float[] target, final float scroll, final float maxScroll, final float delta, final double start, final double duration) {
        final float bounce = 0.44f;
        target[0] = ns.a(target[0], -300.0f, maxScroll + 300.0f);
        if (target[0] < 0.0f) {
            final int n = 0;
            final int n2 = 0;
            target[n2] -= target[0] * (1.0f - bounce) * delta / 3.0f;
        }
        else if (target[0] > maxScroll) {
            target[0] = (target[0] - maxScroll) * (1.0f - (1.0f - bounce) * delta / 3.0f) + maxScroll;
        }
        if (!Precision.almostEquals(scroll, target[0], 0.001f)) {
            return expoEase(scroll, target[0], Math.min((System.currentTimeMillis() - start) / duration * delta * 3.0, 1.0));
        }
        return target[0];
    }
    
    public static float expoEase(final float start, final float end, final double amount) {
        return start + (end - start) * GuiScrolling.easingMethod.apply(amount).floatValue();
    }
    
    static {
        easingMethod = (v -> v);
    }
    
    private static class Precision
    {
        public static final float FLOAT_EPSILON = 0.001f;
        public static final double DOUBLE_EPSILON = 1.0E-7;
        
        public static boolean almostEquals(final float value1, final float value2, final float acceptableDifference) {
            return Math.abs(value1 - value2) <= acceptableDifference;
        }
        
        public static boolean almostEquals(final double value1, final double value2, final double acceptableDifference) {
            return Math.abs(value1 - value2) <= acceptableDifference;
        }
    }
}
