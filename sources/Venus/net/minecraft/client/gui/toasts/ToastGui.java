/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.toasts;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.Deque;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class ToastGui
extends AbstractGui {
    private final Minecraft mc;
    private final ToastInstance<?>[] visible = new ToastInstance[5];
    private final Deque<IToast> toastsQueue = Queues.newArrayDeque();

    public ToastGui(Minecraft minecraft) {
        this.mc = minecraft;
    }

    public void func_238541_a_(MatrixStack matrixStack) {
        if (!this.mc.gameSettings.hideGUI) {
            for (int i = 0; i < this.visible.length; ++i) {
                ToastInstance<?> toastInstance = this.visible[i];
                if (toastInstance != null && toastInstance.render(this.mc.getMainWindow().getScaledWidth(), i, matrixStack)) {
                    this.visible[i] = null;
                }
                if (this.visible[i] != null || this.toastsQueue.isEmpty()) continue;
                this.visible[i] = new ToastInstance(this, this.toastsQueue.removeFirst());
            }
        }
    }

    @Nullable
    public <T extends IToast> T getToast(Class<? extends T> clazz, Object object) {
        for (ToastInstance<?> toastInstance : this.visible) {
            if (toastInstance == null || !clazz.isAssignableFrom(toastInstance.getToast().getClass()) || !toastInstance.getToast().getType().equals(object)) continue;
            return (T)toastInstance.getToast();
        }
        for (IToast iToast : this.toastsQueue) {
            if (!clazz.isAssignableFrom(iToast.getClass()) || !iToast.getType().equals(object)) continue;
            return (T)iToast;
        }
        return (T)((IToast)null);
    }

    public void clear() {
        Arrays.fill(this.visible, null);
        this.toastsQueue.clear();
    }

    public void add(IToast iToast) {
        this.toastsQueue.add(iToast);
    }

    public Minecraft getMinecraft() {
        return this.mc;
    }

    class ToastInstance<T extends IToast> {
        private final T toast;
        private long animationTime;
        private long visibleTime;
        private IToast.Visibility visibility;
        final ToastGui this$0;

        private ToastInstance(T t) {
            this.this$0 = var1_1;
            this.animationTime = -1L;
            this.visibleTime = -1L;
            this.visibility = IToast.Visibility.SHOW;
            this.toast = t;
        }

        public T getToast() {
            return this.toast;
        }

        private float getVisibility(long l) {
            float f = MathHelper.clamp((float)(l - this.animationTime) / 600.0f, 0.0f, 1.0f);
            f *= f;
            return this.visibility == IToast.Visibility.HIDE ? 1.0f - f : f;
        }

        public boolean render(int n, int n2, MatrixStack matrixStack) {
            long l = Util.milliTime();
            if (this.animationTime == -1L) {
                this.animationTime = l;
                this.visibility.playSound(this.this$0.mc.getSoundHandler());
            }
            if (this.visibility == IToast.Visibility.SHOW && l - this.animationTime <= 600L) {
                this.visibleTime = l;
            }
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)n - (float)this.toast.func_230445_a_() * this.getVisibility(l), n2 * this.toast.func_238540_d_(), 800 + n2);
            IToast.Visibility visibility = this.toast.func_230444_a_(matrixStack, this.this$0, l - this.visibleTime);
            RenderSystem.popMatrix();
            if (visibility != this.visibility) {
                this.animationTime = l - (long)((int)((1.0f - this.getVisibility(l)) * 600.0f));
                this.visibility = visibility;
                this.visibility.playSound(this.this$0.mc.getSoundHandler());
            }
            return this.visibility == IToast.Visibility.HIDE && l - this.animationTime > 600L;
        }
    }
}

