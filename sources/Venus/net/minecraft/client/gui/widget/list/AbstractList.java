/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.list;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

public abstract class AbstractList<E extends AbstractListEntry<E>>
extends FocusableGui
implements IRenderable {
    protected final Minecraft minecraft;
    protected final int itemHeight;
    private final List<E> children = new SimpleArrayList(this);
    protected int width;
    protected int height;
    protected int y0;
    protected int y1;
    protected int x1;
    protected int x0;
    protected boolean centerListVertically = true;
    private double scrollAmount;
    private boolean renderSelection = true;
    private boolean renderHeader;
    protected int headerHeight;
    private boolean scrolling;
    private E selected;
    private boolean field_244603_t = true;
    private boolean field_244604_u = true;

    public AbstractList(Minecraft minecraft, int n, int n2, int n3, int n4, int n5) {
        this.minecraft = minecraft;
        this.width = n;
        this.height = n2;
        this.y0 = n3;
        this.y1 = n4;
        this.itemHeight = n5;
        this.x0 = 0;
        this.x1 = n;
    }

    public void setRenderSelection(boolean bl) {
        this.renderSelection = bl;
    }

    protected void setRenderHeader(boolean bl, int n) {
        this.renderHeader = bl;
        this.headerHeight = n;
        if (!bl) {
            this.headerHeight = 0;
        }
    }

    public int getRowWidth() {
        return 1;
    }

    @Nullable
    public E getSelected() {
        return this.selected;
    }

    public void setSelected(@Nullable E e) {
        this.selected = e;
    }

    public void func_244605_b(boolean bl) {
        this.field_244603_t = bl;
    }

    public void func_244606_c(boolean bl) {
        this.field_244604_u = bl;
    }

    @Nullable
    public E getListener() {
        return (E)((AbstractListEntry)super.getListener());
    }

    public final List<E> getEventListeners() {
        return this.children;
    }

    protected final void clearEntries() {
        this.children.clear();
    }

    protected void replaceEntries(Collection<E> collection) {
        this.children.clear();
        this.children.addAll(collection);
    }

    protected E getEntry(int n) {
        return (E)((AbstractListEntry)this.getEventListeners().get(n));
    }

    protected int addEntry(E e) {
        this.children.add(e);
        return this.children.size() - 1;
    }

    protected int getItemCount() {
        return this.getEventListeners().size();
    }

    protected boolean isSelectedItem(int n) {
        return Objects.equals(this.getSelected(), this.getEventListeners().get(n));
    }

    @Nullable
    protected final E getEntryAtPosition(double d, double d2) {
        int n = this.getRowWidth() / 2;
        int n2 = this.x0 + this.width / 2;
        int n3 = n2 - n;
        int n4 = n2 + n;
        int n5 = MathHelper.floor(d2 - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
        int n6 = n5 / this.itemHeight;
        return (E)(d < (double)this.getScrollbarPosition() && d >= (double)n3 && d <= (double)n4 && n6 >= 0 && n5 >= 0 && n6 < this.getItemCount() ? (AbstractListEntry)this.getEventListeners().get(n6) : null);
    }

    public void updateSize(int n, int n2, int n3, int n4) {
        this.width = n;
        this.height = n2;
        this.y0 = n3;
        this.y1 = n4;
        this.x0 = 0;
        this.x1 = n;
    }

    public void setLeftPos(int n) {
        this.x0 = n;
        this.x1 = n + this.width;
    }

    protected int getMaxPosition() {
        return this.getItemCount() * this.itemHeight + this.headerHeight;
    }

    protected void clickedHeader(int n, int n2) {
    }

    protected void renderHeader(MatrixStack matrixStack, int n, int n2, Tessellator tessellator) {
    }

    protected void renderBackground(MatrixStack matrixStack) {
    }

    protected void renderDecorations(MatrixStack matrixStack, int n, int n2) {
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        int n3;
        int n4;
        int n5;
        this.renderBackground(matrixStack);
        int n6 = this.getScrollbarPosition();
        int n7 = n6 + 6;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        if (this.field_244603_t) {
            this.minecraft.getTextureManager().bindTexture(AbstractGui.BACKGROUND_LOCATION);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            float f2 = 32.0f;
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(this.x0, this.y1, 0.0).tex((float)this.x0 / 32.0f, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0f).color(32, 32, 32, 255).endVertex();
            bufferBuilder.pos(this.x1, this.y1, 0.0).tex((float)this.x1 / 32.0f, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0f).color(32, 32, 32, 255).endVertex();
            bufferBuilder.pos(this.x1, this.y0, 0.0).tex((float)this.x1 / 32.0f, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0f).color(32, 32, 32, 255).endVertex();
            bufferBuilder.pos(this.x0, this.y0, 0.0).tex((float)this.x0 / 32.0f, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0f).color(32, 32, 32, 255).endVertex();
            tessellator.draw();
        }
        int n8 = this.getRowLeft();
        int n9 = this.y0 + 4 - (int)this.getScrollAmount();
        if (this.renderHeader) {
            this.renderHeader(matrixStack, n8, n9, tessellator);
        }
        this.renderList(matrixStack, n8, n9, n, n2, f);
        if (this.field_244604_u) {
            this.minecraft.getTextureManager().bindTexture(AbstractGui.BACKGROUND_LOCATION);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(519);
            float f3 = 32.0f;
            n5 = -100;
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(this.x0, this.y0, -100.0).tex(0.0f, (float)this.y0 / 32.0f).color(64, 64, 64, 255).endVertex();
            bufferBuilder.pos(this.x0 + this.width, this.y0, -100.0).tex((float)this.width / 32.0f, (float)this.y0 / 32.0f).color(64, 64, 64, 255).endVertex();
            bufferBuilder.pos(this.x0 + this.width, 0.0, -100.0).tex((float)this.width / 32.0f, 0.0f).color(64, 64, 64, 255).endVertex();
            bufferBuilder.pos(this.x0, 0.0, -100.0).tex(0.0f, 0.0f).color(64, 64, 64, 255).endVertex();
            bufferBuilder.pos(this.x0, this.height, -100.0).tex(0.0f, (float)this.height / 32.0f).color(64, 64, 64, 255).endVertex();
            bufferBuilder.pos(this.x0 + this.width, this.height, -100.0).tex((float)this.width / 32.0f, (float)this.height / 32.0f).color(64, 64, 64, 255).endVertex();
            bufferBuilder.pos(this.x0 + this.width, this.y1, -100.0).tex((float)this.width / 32.0f, (float)this.y1 / 32.0f).color(64, 64, 64, 255).endVertex();
            bufferBuilder.pos(this.x0, this.y1, -100.0).tex(0.0f, (float)this.y1 / 32.0f).color(64, 64, 64, 255).endVertex();
            tessellator.draw();
            RenderSystem.depthFunc(515);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            RenderSystem.disableAlphaTest();
            RenderSystem.shadeModel(7425);
            RenderSystem.disableTexture();
            n4 = 4;
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(this.x0, this.y0 + 4, 0.0).tex(0.0f, 1.0f).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos(this.x1, this.y0 + 4, 0.0).tex(1.0f, 1.0f).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos(this.x1, this.y0, 0.0).tex(1.0f, 0.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(this.x0, this.y0, 0.0).tex(0.0f, 0.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(this.x0, this.y1, 0.0).tex(0.0f, 1.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(this.x1, this.y1, 0.0).tex(1.0f, 1.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(this.x1, this.y1 - 4, 0.0).tex(1.0f, 0.0f).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos(this.x0, this.y1 - 4, 0.0).tex(0.0f, 0.0f).color(0, 0, 0, 0).endVertex();
            tessellator.draw();
        }
        if ((n3 = this.getMaxScroll()) > 0) {
            RenderSystem.disableTexture();
            n5 = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            n5 = MathHelper.clamp(n5, 32, this.y1 - this.y0 - 8);
            n4 = (int)this.getScrollAmount() * (this.y1 - this.y0 - n5) / n3 + this.y0;
            if (n4 < this.y0) {
                n4 = this.y0;
            }
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(n6, this.y1, 0.0).tex(0.0f, 1.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(n7, this.y1, 0.0).tex(1.0f, 1.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(n7, this.y0, 0.0).tex(1.0f, 0.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(n6, this.y0, 0.0).tex(0.0f, 0.0f).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(n6, n4 + n5, 0.0).tex(0.0f, 1.0f).color(128, 128, 128, 255).endVertex();
            bufferBuilder.pos(n7, n4 + n5, 0.0).tex(1.0f, 1.0f).color(128, 128, 128, 255).endVertex();
            bufferBuilder.pos(n7, n4, 0.0).tex(1.0f, 0.0f).color(128, 128, 128, 255).endVertex();
            bufferBuilder.pos(n6, n4, 0.0).tex(0.0f, 0.0f).color(128, 128, 128, 255).endVertex();
            bufferBuilder.pos(n6, n4 + n5 - 1, 0.0).tex(0.0f, 1.0f).color(192, 192, 192, 255).endVertex();
            bufferBuilder.pos(n7 - 1, n4 + n5 - 1, 0.0).tex(1.0f, 1.0f).color(192, 192, 192, 255).endVertex();
            bufferBuilder.pos(n7 - 1, n4, 0.0).tex(1.0f, 0.0f).color(192, 192, 192, 255).endVertex();
            bufferBuilder.pos(n6, n4, 0.0).tex(0.0f, 0.0f).color(192, 192, 192, 255).endVertex();
            tessellator.draw();
        }
        this.renderDecorations(matrixStack, n, n2);
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7424);
        RenderSystem.enableAlphaTest();
        RenderSystem.disableBlend();
    }

    protected void centerScrollOn(E e) {
        this.setScrollAmount(this.getEventListeners().indexOf(e) * this.itemHeight + this.itemHeight / 2 - (this.y1 - this.y0) / 2);
    }

    protected void ensureVisible(E e) {
        int n;
        int n2 = this.getRowTop(this.getEventListeners().indexOf(e));
        int n3 = n2 - this.y0 - 4 - this.itemHeight;
        if (n3 < 0) {
            this.scroll(n3);
        }
        if ((n = this.y1 - n2 - this.itemHeight - this.itemHeight) < 0) {
            this.scroll(-n);
        }
    }

    private void scroll(int n) {
        this.setScrollAmount(this.getScrollAmount() + (double)n);
    }

    public double getScrollAmount() {
        return this.scrollAmount;
    }

    public void setScrollAmount(double d) {
        this.scrollAmount = MathHelper.clamp(d, 0.0, (double)this.getMaxScroll());
    }

    public int getMaxScroll() {
        return Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4));
    }

    protected void updateScrollingState(double d, double d2, int n) {
        this.scrolling = n == 0 && d >= (double)this.getScrollbarPosition() && d < (double)(this.getScrollbarPosition() + 6);
    }

    protected int getScrollbarPosition() {
        return this.width / 2 + 124;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        this.updateScrollingState(d, d2, n);
        if (!this.isMouseOver(d, d2)) {
            return true;
        }
        E e = this.getEntryAtPosition(d, d2);
        if (e != null) {
            if (e.mouseClicked(d, d2, n)) {
                this.setListener((IGuiEventListener)e);
                this.setDragging(false);
                return false;
            }
        } else if (n == 0) {
            this.clickedHeader((int)(d - (double)(this.x0 + this.width / 2 - this.getRowWidth() / 2)), (int)(d2 - (double)this.y0) + (int)this.getScrollAmount() - 4);
            return false;
        }
        return this.scrolling;
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        if (this.getListener() != null) {
            this.getListener().mouseReleased(d, d2, n);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (super.mouseDragged(d, d2, n, d3, d4)) {
            return false;
        }
        if (n == 0 && this.scrolling) {
            if (d2 < (double)this.y0) {
                this.setScrollAmount(0.0);
            } else if (d2 > (double)this.y1) {
                this.setScrollAmount(this.getMaxScroll());
            } else {
                double d5 = Math.max(1, this.getMaxScroll());
                int n2 = this.y1 - this.y0;
                int n3 = MathHelper.clamp((int)((float)(n2 * n2) / (float)this.getMaxPosition()), 32, n2 - 8);
                double d6 = Math.max(1.0, d5 / (double)(n2 - n3));
                this.setScrollAmount(this.getScrollAmount() + d4 * d6);
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        this.setScrollAmount(this.getScrollAmount() - d3 * (double)this.itemHeight / 2.0);
        return false;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (n == 264) {
            this.moveSelection(Ordering.DOWN);
            return false;
        }
        if (n == 265) {
            this.moveSelection(Ordering.UP);
            return false;
        }
        return true;
    }

    protected void moveSelection(Ordering ordering) {
        this.func_241572_a_(ordering, AbstractList::lambda$moveSelection$0);
    }

    protected void func_241574_n_() {
        E e = this.getSelected();
        if (e != null) {
            this.setSelected(e);
            this.ensureVisible(e);
        }
    }

    protected void func_241572_a_(Ordering ordering, Predicate<E> predicate) {
        int n;
        int n2 = n = ordering == Ordering.UP ? -1 : 1;
        if (!this.getEventListeners().isEmpty()) {
            int n3;
            int n4 = this.getEventListeners().indexOf(this.getSelected());
            while (n4 != (n3 = MathHelper.clamp(n4 + n, 0, this.getItemCount() - 1))) {
                AbstractListEntry abstractListEntry = (AbstractListEntry)this.getEventListeners().get(n3);
                if (predicate.test(abstractListEntry)) {
                    this.setSelected(abstractListEntry);
                    this.ensureVisible(abstractListEntry);
                    break;
                }
                n4 = n3;
            }
        }
    }

    @Override
    public boolean isMouseOver(double d, double d2) {
        return d2 >= (double)this.y0 && d2 <= (double)this.y1 && d >= (double)this.x0 && d <= (double)this.x1;
    }

    protected void renderList(MatrixStack matrixStack, int n, int n2, int n3, int n4, float f) {
        int n5 = this.getItemCount();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        for (int i = 0; i < n5; ++i) {
            int n6;
            int n7 = this.getRowTop(i);
            int n8 = this.getRowBottom(i);
            if (n8 < this.y0 || n7 > this.y1) continue;
            int n9 = n2 + i * this.itemHeight + this.headerHeight;
            int n10 = this.itemHeight - 4;
            E e = this.getEntry(i);
            int n11 = this.getRowWidth();
            if (this.renderSelection && this.isSelectedItem(i)) {
                n6 = this.x0 + this.width / 2 - n11 / 2;
                int n12 = this.x0 + this.width / 2 + n11 / 2;
                RenderSystem.disableTexture();
                float f2 = this.isFocused() ? 1.0f : 0.5f;
                RenderSystem.color4f(f2, f2, f2, 1.0f);
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
                bufferBuilder.pos(n6, n9 + n10 + 2, 0.0).endVertex();
                bufferBuilder.pos(n12, n9 + n10 + 2, 0.0).endVertex();
                bufferBuilder.pos(n12, n9 - 2, 0.0).endVertex();
                bufferBuilder.pos(n6, n9 - 2, 0.0).endVertex();
                tessellator.draw();
                RenderSystem.color4f(0.0f, 0.0f, 0.0f, 1.0f);
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
                bufferBuilder.pos(n6 + 1, n9 + n10 + 1, 0.0).endVertex();
                bufferBuilder.pos(n12 - 1, n9 + n10 + 1, 0.0).endVertex();
                bufferBuilder.pos(n12 - 1, n9 - 1, 0.0).endVertex();
                bufferBuilder.pos(n6 + 1, n9 - 1, 0.0).endVertex();
                tessellator.draw();
                RenderSystem.enableTexture();
            }
            n6 = this.getRowLeft();
            ((AbstractListEntry)e).render(matrixStack, i, n7, n6, n11, n10, n3, n4, this.isMouseOver(n3, n4) && Objects.equals(this.getEntryAtPosition(n3, n4), e), f);
        }
    }

    public int getRowLeft() {
        return this.x0 + this.width / 2 - this.getRowWidth() / 2 + 2;
    }

    public int func_244736_r() {
        return this.getRowLeft() + this.getRowWidth();
    }

    protected int getRowTop(int n) {
        return this.y0 + 4 - (int)this.getScrollAmount() + n * this.itemHeight + this.headerHeight;
    }

    private int getRowBottom(int n) {
        return this.getRowTop(n) + this.itemHeight;
    }

    protected boolean isFocused() {
        return true;
    }

    protected E remove(int n) {
        AbstractListEntry abstractListEntry = (AbstractListEntry)this.children.get(n);
        return (E)(this.removeEntry((AbstractListEntry)this.children.get(n)) ? abstractListEntry : null);
    }

    protected boolean removeEntry(E e) {
        boolean bl = this.children.remove(e);
        if (bl && e == this.getSelected()) {
            this.setSelected(null);
        }
        return bl;
    }

    private void func_238480_f_(AbstractListEntry<E> abstractListEntry) {
        abstractListEntry.list = this;
    }

    @Override
    @Nullable
    public IGuiEventListener getListener() {
        return this.getListener();
    }

    private static boolean lambda$moveSelection$0(AbstractListEntry abstractListEntry) {
        return false;
    }

    class SimpleArrayList
    extends java.util.AbstractList<E> {
        private final List<E> field_216871_b;
        final AbstractList this$0;

        private SimpleArrayList(AbstractList abstractList) {
            this.this$0 = abstractList;
            this.field_216871_b = Lists.newArrayList();
        }

        @Override
        public E get(int n) {
            return (AbstractListEntry)this.field_216871_b.get(n);
        }

        @Override
        public int size() {
            return this.field_216871_b.size();
        }

        @Override
        public E set(int n, E e) {
            AbstractListEntry abstractListEntry = (AbstractListEntry)this.field_216871_b.set(n, e);
            this.this$0.func_238480_f_(e);
            return abstractListEntry;
        }

        @Override
        public void add(int n, E e) {
            this.field_216871_b.add(n, e);
            this.this$0.func_238480_f_(e);
        }

        @Override
        public E remove(int n) {
            return (AbstractListEntry)this.field_216871_b.remove(n);
        }

        @Override
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        public void add(int n, Object object) {
            this.add(n, (E)((AbstractListEntry)object));
        }

        @Override
        public Object set(int n, Object object) {
            return this.set(n, (E)((AbstractListEntry)object));
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    public static abstract class AbstractListEntry<E extends AbstractListEntry<E>>
    implements IGuiEventListener {
        @Deprecated
        private AbstractList<E> list;

        public abstract void render(MatrixStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10);

        @Override
        public boolean isMouseOver(double d, double d2) {
            return Objects.equals(this.list.getEntryAtPosition(d, d2), this);
        }
    }

    public static enum Ordering {
        UP,
        DOWN;

    }
}

