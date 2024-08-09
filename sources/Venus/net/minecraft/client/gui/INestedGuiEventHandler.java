/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.gui.IGuiEventListener;

public interface INestedGuiEventHandler
extends IGuiEventListener {
    public List<? extends IGuiEventListener> getEventListeners();

    default public Optional<IGuiEventListener> getEventListenerForPos(double d, double d2) {
        for (IGuiEventListener iGuiEventListener : this.getEventListeners()) {
            if (!iGuiEventListener.isMouseOver(d, d2)) continue;
            return Optional.of(iGuiEventListener);
        }
        return Optional.empty();
    }

    @Override
    default public boolean mouseClicked(double d, double d2, int n) {
        for (IGuiEventListener iGuiEventListener : this.getEventListeners()) {
            if (!iGuiEventListener.mouseClicked(d, d2, n)) continue;
            this.setListener(iGuiEventListener);
            if (n == 0) {
                this.setDragging(true);
            }
            return false;
        }
        return true;
    }

    @Override
    default public boolean mouseReleased(double d, double d2, int n) {
        this.setDragging(false);
        return this.getEventListenerForPos(d, d2).filter(arg_0 -> INestedGuiEventHandler.lambda$mouseReleased$0(d, d2, n, arg_0)).isPresent();
    }

    @Override
    default public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        return this.getListener() != null && this.isDragging() && n == 0 ? this.getListener().mouseDragged(d, d2, n, d3, d4) : false;
    }

    public boolean isDragging();

    public void setDragging(boolean var1);

    @Override
    default public boolean mouseScrolled(double d, double d2, double d3) {
        return this.getEventListenerForPos(d, d2).filter(arg_0 -> INestedGuiEventHandler.lambda$mouseScrolled$1(d, d2, d3, arg_0)).isPresent();
    }

    @Override
    default public boolean keyPressed(int n, int n2, int n3) {
        return this.getListener() != null && this.getListener().keyPressed(n, n2, n3);
    }

    @Override
    default public boolean keyReleased(int n, int n2, int n3) {
        return this.getListener() != null && this.getListener().keyReleased(n, n2, n3);
    }

    @Override
    default public boolean charTyped(char c, int n) {
        return this.getListener() != null && this.getListener().charTyped(c, n);
    }

    @Nullable
    public IGuiEventListener getListener();

    public void setListener(@Nullable IGuiEventListener var1);

    default public void setFocusedDefault(@Nullable IGuiEventListener iGuiEventListener) {
        this.setListener(iGuiEventListener);
        iGuiEventListener.changeFocus(true);
    }

    default public void setListenerDefault(@Nullable IGuiEventListener iGuiEventListener) {
        this.setListener(iGuiEventListener);
    }

    @Override
    default public boolean changeFocus(boolean bl) {
        Supplier<IGuiEventListener> supplier;
        BooleanSupplier booleanSupplier;
        boolean bl2;
        IGuiEventListener iGuiEventListener = this.getListener();
        boolean bl3 = bl2 = iGuiEventListener != null;
        if (bl2 && iGuiEventListener.changeFocus(bl)) {
            return false;
        }
        List<? extends IGuiEventListener> list = this.getEventListeners();
        int n = list.indexOf(iGuiEventListener);
        int n2 = bl2 && n >= 0 ? n + (bl ? 1 : 0) : (bl ? 0 : list.size());
        ListIterator<? extends IGuiEventListener> listIterator2 = list.listIterator(n2);
        BooleanSupplier booleanSupplier2 = bl ? listIterator2::hasNext : (booleanSupplier = listIterator2::hasPrevious);
        Supplier<IGuiEventListener> supplier2 = bl ? listIterator2::next : (supplier = listIterator2::previous);
        while (booleanSupplier.getAsBoolean()) {
            IGuiEventListener iGuiEventListener2 = supplier.get();
            if (!iGuiEventListener2.changeFocus(bl)) continue;
            this.setListener(iGuiEventListener2);
            return false;
        }
        this.setListener(null);
        return true;
    }

    private static boolean lambda$mouseScrolled$1(double d, double d2, double d3, IGuiEventListener iGuiEventListener) {
        return iGuiEventListener.mouseScrolled(d, d2, d3);
    }

    private static boolean lambda$mouseReleased$0(double d, double d2, int n, IGuiEventListener iGuiEventListener) {
        return iGuiEventListener.mouseReleased(d, d2, n);
    }
}

