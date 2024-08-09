package xyz.mathax.mathaxclient.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.mathax.mathaxclient.MatHax;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class MatHaxToast implements Toast {
    public static final int TITLE_COLOR = Color.fromRGBA(145, 61, 226, 255);
    public static final int TEXT_COLOR = Color.fromRGBA(220, 220, 220, 255);

    private ItemStack icon;
    private Text title, text;
    private boolean justUpdated = true, playedSound;
    private long start, duration;

    public MatHaxToast(@Nullable Item item, @NotNull String title, @Nullable String text, long duration) {
        this.icon = item != null ? item.getDefaultStack() : null;
        this.title = Text.literal(title).setStyle(Style.EMPTY.withColor(new TextColor(TITLE_COLOR)));
        this.text = text != null ? Text.literal(text).setStyle(Style.EMPTY.withColor(new TextColor(TEXT_COLOR))) : null;
        this.duration = duration;
    }

    public MatHaxToast(@Nullable Item item, @NotNull String title, @Nullable String text) {
        this.icon = item != null ? item.getDefaultStack() : null;
        this.title = Text.literal(title).setStyle(Style.EMPTY.withColor(new TextColor(TITLE_COLOR)));
        this.text = text != null ? Text.literal(text).setStyle(Style.EMPTY.withColor(new TextColor(TEXT_COLOR))) : null;
        this.duration = 6000;
    }

    public Visibility draw(MatrixStack matrixStack, ToastManager toastManager, long currentTime) {
        if (justUpdated) {
            start = currentTime;
            justUpdated = false;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        toastManager.drawTexture(matrixStack, 0, 0, 0, 0, getWidth(), getHeight());

        int x = icon != null ? 28 : 12;
        int titleY = 12;

        if (text != null) {
            mc.textRenderer.draw(matrixStack, text, x, 18, TEXT_COLOR);
            titleY = 7;
        }

        mc.textRenderer.draw(matrixStack, title, x, titleY, TITLE_COLOR);

        if (icon != null) {
            mc.getItemRenderer().renderInGui(icon, 8, 8);
        }

        if (!playedSound) {
            mc.getSoundManager().play(getSound());
            playedSound = true;
        }

        return currentTime - start >= duration ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    public void setIcon(@Nullable Item item) {
        this.icon = item != null ? item.getDefaultStack() : null;
        justUpdated = true;
    }

    public void setTitle(@NotNull String title) {
        this.title = Text.literal(title).setStyle(Style.EMPTY.withColor(new TextColor(TITLE_COLOR)));
        justUpdated = true;
    }

    public void setText(@Nullable String text) {
        this.text = text != null ? Text.literal(text).setStyle(Style.EMPTY.withColor(new TextColor(TEXT_COLOR))) : null;
        justUpdated = true;
    }

    public void setDuration(long duration) {
        this.duration = duration;
        justUpdated = true;
    }

    public SoundInstance getSound() {
        return PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), 1.2f, 1);
    }
}
