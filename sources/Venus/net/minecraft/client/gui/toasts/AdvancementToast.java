/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.toasts;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class AdvancementToast
implements IToast {
    private final Advancement advancement;
    private boolean hasPlayedSound;

    public AdvancementToast(Advancement advancement) {
        this.advancement = advancement;
    }

    @Override
    public IToast.Visibility func_230444_a_(MatrixStack matrixStack, ToastGui toastGui, long l) {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        DisplayInfo displayInfo = this.advancement.getDisplay();
        toastGui.blit(matrixStack, 0, 0, 0, 0, this.func_230445_a_(), this.func_238540_d_());
        if (displayInfo != null) {
            int n;
            List<IReorderingProcessor> list = toastGui.getMinecraft().fontRenderer.trimStringToWidth(displayInfo.getTitle(), 125);
            int n2 = n = displayInfo.getFrame() == FrameType.CHALLENGE ? 0xFF88FF : 0xFFFF00;
            if (list.size() == 1) {
                toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, displayInfo.getFrame().getTranslatedToast(), 30.0f, 7.0f, n | 0xFF000000);
                toastGui.getMinecraft().fontRenderer.func_238422_b_(matrixStack, list.get(0), 30.0f, 18.0f, -1);
            } else {
                int n3 = 1500;
                float f = 300.0f;
                if (l < 1500L) {
                    int n4 = MathHelper.floor(MathHelper.clamp((float)(1500L - l) / 300.0f, 0.0f, 1.0f) * 255.0f) << 24 | 0x4000000;
                    toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, displayInfo.getFrame().getTranslatedToast(), 30.0f, 11.0f, n | n4);
                } else {
                    int n5 = MathHelper.floor(MathHelper.clamp((float)(l - 1500L) / 300.0f, 0.0f, 1.0f) * 252.0f) << 24 | 0x4000000;
                    int n6 = this.func_238540_d_() / 2 - list.size() * 9 / 2;
                    for (IReorderingProcessor iReorderingProcessor : list) {
                        toastGui.getMinecraft().fontRenderer.func_238422_b_(matrixStack, iReorderingProcessor, 30.0f, n6, 0xFFFFFF | n5);
                        n6 += 9;
                    }
                }
            }
            if (!this.hasPlayedSound && l > 0L) {
                this.hasPlayedSound = true;
                if (displayInfo.getFrame() == FrameType.CHALLENGE) {
                    toastGui.getMinecraft().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f));
                }
            }
            toastGui.getMinecraft().getItemRenderer().renderItemAndEffectIntoGuiWithoutEntity(displayInfo.getIcon(), 8, 8);
            return l >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
        }
        return IToast.Visibility.HIDE;
    }
}

