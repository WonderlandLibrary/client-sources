package me.kaimson.melonclient.gui.utils;

import org.lwjgl.input.*;

public abstract class GuiScrolling extends awi
{
    public GuiScrolling(final ave mcIn, final int width, final int height, final int topIn, final int bottomIn, final int slotHeightIn) {
        super(mcIn, width, height, topIn, bottomIn, slotHeightIn);
    }
    
    public void a(final int widthIn, final int heightIn, final int topIn, final int bottomIn) {
        this.b = widthIn;
        this.c = heightIn;
        this.d = topIn;
        this.e = bottomIn;
        this.g = 0;
        this.f = widthIn;
    }
    
    public void b(final boolean showSelectionBoxIn) {
        this.r = showSelectionBoxIn;
    }
    
    protected void a(final boolean hasListHeaderIn, final int headerPaddingIn) {
        this.s = hasListHeaderIn;
        this.t = headerPaddingIn;
        if (!hasListHeaderIn) {
            this.t = 0;
        }
    }
    
    protected abstract int b();
    
    protected abstract void a(final int p0, final boolean p1, final int p2, final int p3);
    
    protected abstract boolean a(final int p0);
    
    public int k() {
        return this.b() * this.h + this.t;
    }
    
    protected abstract void a();
    
    protected void a(final int p_178040_1_, final int p_178040_2_, final int p_178040_3_) {
    }
    
    protected abstract void a(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    protected void a(final int p_148129_1_, final int p_148129_2_, final bfx p_148129_3_) {
    }
    
    protected void a(final int p_148132_1_, final int p_148132_2_) {
    }
    
    public void b(final int p_148142_1_, final int p_148142_2_) {
    }
    
    public int c(final int p_148124_1_, final int p_148124_2_) {
        final int i = this.g + this.b / 2 - this.c() / 2;
        final int j = this.g + this.b / 2 + this.c() / 2;
        final int k = p_148124_2_ - this.d - this.t + (int)this.n - 4;
        final int l = k / this.h;
        return (p_148124_1_ < this.d() && p_148124_1_ >= i && p_148124_1_ <= j && l >= 0 && k >= 0 && l < this.b()) ? l : -1;
    }
    
    public int m() {
        return Math.max(0, this.k() - (this.e - this.d - 4));
    }
    
    public int n() {
        return (int)this.n;
    }
    
    public boolean g(final int p_148141_1_) {
        return p_148141_1_ >= this.d && p_148141_1_ <= this.e && this.i >= this.g && this.i <= this.f;
    }
    
    public void h(final int amount) {
        this.n += amount;
        this.l();
        this.l = -2;
    }
    
    public void a(final int mouseXIn, final int mouseYIn, final float p_148128_3_) {
        if (this.q) {
            this.i = mouseXIn;
            this.j = mouseYIn;
            this.a();
            final int i = this.d();
            final int j = i + 6;
            this.l();
            bfl.f();
            bfl.n();
            final bfx tessellator = bfx.a();
            final bfd worldrenderer = tessellator.c();
            this.c(this.d, this.e, 100, 100);
            final int k = this.g + this.b / 2 - this.c() / 2 + 2;
            final int l = this.d + 4 - (int)this.n;
            if (this.s) {
                this.a(k, l, tessellator);
            }
            this.b(k, l, mouseXIn, mouseYIn);
            bfl.i();
            final int i2 = 4;
            this.c(0, this.d, 255, 255);
            this.c(this.e, this.c, 255, 255);
            bfl.l();
            bfl.a(770, 771, 0, 1);
            bfl.c();
            bfl.j(7425);
            bfl.x();
            worldrenderer.a(7, bms.i);
            worldrenderer.b((double)this.g, (double)(this.d + i2), 0.0).a(0.0, 1.0).b(0, 0, 0, 0).d();
            worldrenderer.b((double)this.f, (double)(this.d + i2), 0.0).a(1.0, 1.0).b(0, 0, 0, 0).d();
            worldrenderer.b((double)this.f, (double)this.d, 0.0).a(1.0, 0.0).b(0, 0, 0, 255).d();
            worldrenderer.b((double)this.g, (double)this.d, 0.0).a(0.0, 0.0).b(0, 0, 0, 255).d();
            tessellator.b();
            worldrenderer.a(7, bms.i);
            worldrenderer.b((double)this.g, (double)this.e, 0.0).a(0.0, 1.0).b(0, 0, 0, 255).d();
            worldrenderer.b((double)this.f, (double)this.e, 0.0).a(1.0, 1.0).b(0, 0, 0, 255).d();
            worldrenderer.b((double)this.f, (double)(this.e - i2), 0.0).a(1.0, 0.0).b(0, 0, 0, 0).d();
            worldrenderer.b((double)this.g, (double)(this.e - i2), 0.0).a(0.0, 0.0).b(0, 0, 0, 0).d();
            tessellator.b();
            final int j2 = this.m();
            this.drawScroll(i, j);
        }
        this.b(mouseXIn, mouseYIn);
    }
    
    public void drawScroll(final int i, final int j) {
        final bfx tessellator = bfx.a();
        final bfd buffer = tessellator.c();
        final int scrollbarPositionMinX = this.d();
        final int scrollbarPositionMaxX = scrollbarPositionMinX + 6;
        final int maxScroll = this.m();
        final int contentHeight = this.k();
        if (maxScroll > 0) {
            int height = (this.e - this.d) * (this.e - this.d) / contentHeight;
            height = ns.a(height, 32, this.e - this.d - 8);
            height -= (int)Math.min((this.n < 0.0) ? ((double)(int)(-this.n)) : ((double)((this.n > this.m()) ? ((int)this.n - this.m()) : 0)), height * 0.75);
            final int minY = Math.min(Math.max(this.n() * (this.e - this.d - height) / maxScroll + this.d, this.d), this.e - height);
            buffer.a(7, bms.i);
            buffer.b((double)scrollbarPositionMinX, (double)this.e, 0.0).a(0.0, 1.0).b(0, 0, 0, 255).d();
            buffer.b((double)scrollbarPositionMaxX, (double)this.e, 0.0).a(1.0, 1.0).b(0, 0, 0, 255).d();
            buffer.b((double)scrollbarPositionMaxX, (double)this.d, 0.0).a(1.0, 0.0).b(0, 0, 0, 255).d();
            buffer.b((double)scrollbarPositionMinX, (double)this.d, 0.0).a(0.0, 0.0).b(0, 0, 0, 255).d();
            tessellator.b();
            buffer.a(7, bms.i);
            buffer.b((double)scrollbarPositionMinX, (double)(minY + height), 0.0).a(0.0, 1.0).b(128, 128, 128, 255).d();
            buffer.b((double)scrollbarPositionMaxX, (double)(minY + height), 0.0).a(1.0, 1.0).b(128, 128, 128, 255).d();
            buffer.b((double)scrollbarPositionMaxX, (double)minY, 0.0).a(1.0, 0.0).b(128, 128, 128, 255).d();
            buffer.b((double)scrollbarPositionMinX, (double)minY, 0.0).a(0.0, 0.0).b(128, 128, 128, 255).d();
            tessellator.b();
            buffer.a(7, bms.i);
            buffer.b((double)scrollbarPositionMinX, (double)(minY + height - 1), 0.0).a(0.0, 1.0).b(192, 192, 192, 255).d();
            buffer.b((double)(scrollbarPositionMaxX - 1), (double)(minY + height - 1), 0.0).a(1.0, 1.0).b(192, 192, 192, 255).d();
            buffer.b((double)(scrollbarPositionMaxX - 1), (double)minY, 0.0).a(1.0, 0.0).b(192, 192, 192, 255).d();
            buffer.b((double)scrollbarPositionMinX, (double)minY, 0.0).a(0.0, 0.0).b(192, 192, 192, 255).d();
            tessellator.b();
        }
        this.b(this.i, this.j);
        bfl.w();
        bfl.j(7424);
        bfl.d();
        bfl.k();
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
                }
            }
            int i2 = Mouse.getEventDWheel();
            if (i2 != 0) {
                if (i2 > 0) {
                    i2 = -1;
                }
                else if (i2 < 0) {
                    i2 = 1;
                }
                this.n += i2 * this.h / 2;
            }
        }
    }
    
    public int c() {
        return 220;
    }
    
    protected void b(final int p_148120_1_, final int p_148120_2_, final int mouseXIn, final int mouseYIn) {
        final int i = this.b();
        final bfx tessellator = bfx.a();
        final bfd worldrenderer = tessellator.c();
        for (int j = 0; j < i; ++j) {
            final int k = p_148120_2_ + j * this.h + this.t;
            final int l = this.h - 4;
            if (k > this.e || k + l < this.d) {
                this.a(j, p_148120_1_, k);
            }
            if (this.r && this.a(j)) {
                final int i2 = this.g + (this.b / 2 - this.c() / 2);
                final int j2 = this.g + this.b / 2 + this.c() / 2;
                bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
                bfl.x();
                worldrenderer.a(7, bms.i);
                worldrenderer.b((double)i2, (double)(k + l + 2), 0.0).a(0.0, 1.0).b(128, 128, 128, 255).d();
                worldrenderer.b((double)j2, (double)(k + l + 2), 0.0).a(1.0, 1.0).b(128, 128, 128, 255).d();
                worldrenderer.b((double)j2, (double)(k - 2), 0.0).a(1.0, 0.0).b(128, 128, 128, 255).d();
                worldrenderer.b((double)i2, (double)(k - 2), 0.0).a(0.0, 0.0).b(128, 128, 128, 255).d();
                worldrenderer.b((double)(i2 + 1), (double)(k + l + 1), 0.0).a(0.0, 1.0).b(0, 0, 0, 255).d();
                worldrenderer.b((double)(j2 - 1), (double)(k + l + 1), 0.0).a(1.0, 1.0).b(0, 0, 0, 255).d();
                worldrenderer.b((double)(j2 - 1), (double)(k - 1), 0.0).a(1.0, 0.0).b(0, 0, 0, 255).d();
                worldrenderer.b((double)(i2 + 1), (double)(k - 1), 0.0).a(0.0, 0.0).b(0, 0, 0, 255).d();
                tessellator.b();
                bfl.w();
            }
            this.a(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
        }
    }
    
    public int d() {
        return this.b / 2 + 124;
    }
    
    protected void c(final int startY, final int endY, final int startAlpha, final int endAlpha) {
        final bfx tessellator = bfx.a();
        final bfd worldrenderer = tessellator.c();
        this.a.P().a(avp.b);
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        final float f = 32.0f;
        worldrenderer.a(7, bms.i);
        worldrenderer.b((double)this.g, (double)endY, 0.0).a(0.0, (double)(endY / 32.0f)).b(64, 64, 64, endAlpha).d();
        worldrenderer.b((double)(this.g + this.b), (double)endY, 0.0).a((double)(this.b / 32.0f), (double)(endY / 32.0f)).b(64, 64, 64, endAlpha).d();
        worldrenderer.b((double)(this.g + this.b), (double)startY, 0.0).a((double)(this.b / 32.0f), (double)(startY / 32.0f)).b(64, 64, 64, startAlpha).d();
        worldrenderer.b((double)this.g, (double)startY, 0.0).a(0.0, (double)(startY / 32.0f)).b(64, 64, 64, startAlpha).d();
        tessellator.b();
    }
    
    public void i(final int leftIn) {
        this.g = leftIn;
        this.f = leftIn + this.b;
    }
    
    public int r() {
        return this.h;
    }
}
