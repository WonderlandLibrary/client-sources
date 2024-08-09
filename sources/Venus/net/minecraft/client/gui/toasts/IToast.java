/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.toasts;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public interface IToast {
    public static final ResourceLocation TEXTURE_TOASTS = new ResourceLocation("textures/gui/toasts.png");
    public static final Object NO_TOKEN = new Object();

    public Visibility func_230444_a_(MatrixStack var1, ToastGui var2, long var3);

    default public Object getType() {
        return NO_TOKEN;
    }

    default public int func_230445_a_() {
        return 1;
    }

    default public int func_238540_d_() {
        return 1;
    }

    public static enum Visibility {
        SHOW(SoundEvents.UI_TOAST_IN),
        HIDE(SoundEvents.UI_TOAST_OUT);

        private final SoundEvent sound;

        private Visibility(SoundEvent soundEvent) {
            this.sound = soundEvent;
        }

        public void playSound(SoundHandler soundHandler) {
            soundHandler.play(SimpleSound.master(this.sound, 1.0f, 1.0f));
        }
    }
}

