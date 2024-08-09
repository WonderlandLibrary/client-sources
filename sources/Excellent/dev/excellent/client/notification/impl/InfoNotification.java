package dev.excellent.client.notification.impl;

import dev.excellent.client.notification.Notification;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import net.mojang.blaze3d.matrix.MatrixStack;

public final class InfoNotification extends Notification {

    public InfoNotification(final String content, final long delay) {
        super(content, delay);
        animationY.setValue(scaled().y);
        alphaAnimation.setValue(0);
    }

    @Override
    public void render(MatrixStack matrixStack, final int multiplierY) {

        this.end = ((this.getInit() + this.getDelay()) - System.currentTimeMillis()) <= (this.getDelay() - 500) - this.getDelay();
        if (mc.currentScreen instanceof ChatScreen) {
            chatOffset.run(Fonts.INTER_BOLD.get(12).getHeight() + 4 + 12);
        } else {
            chatOffset.run(Fonts.INTER_BOLD.get(12).getHeight() + 4);
        }

        float contentWidth = font.getWidth(getContent());

        float x, y, width, height;

        width = margin + contentWidth + margin;
        height = (margin / 2F) + font.getHeight() + (margin / 2F);

        x = (float) (scaled().x - width - margin);
        y = (float) ((scaled().y - height) - (height * multiplierY) - (multiplierY * 4) - chatOffset.getValue());

        alphaAnimation.run(this.end ? 0 : 1);
        animationY.run(this.end ? scaled().y : y);

        float posX = x;
        float posY = (float) ((y + animationY.getValue()) - y);

        RenderUtil.renderClientRect(matrixStack, posX, posY, width, height, false, height);
        font.drawOutline(matrixStack, getContent(), posX + margin, posY + margin / 2F, ColorUtil.getColor(255, 255, 255, (int) alphaAnimation.getValue() * 255));

    }

    @Override
    public boolean hasExpired() {
        return this.animationY.isFinished() && this.end;
    }
}
