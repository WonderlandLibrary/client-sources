package dev.excellent.client.notification.impl;

import dev.excellent.client.notification.Notification;
import net.mojang.blaze3d.matrix.MatrixStack;

public final class WarningNotification extends Notification {

    public WarningNotification(final String content, final long delay) {
        super(content, delay);
    }

    @Override
    public void render(MatrixStack matrixStack, final int multiplierY) {
    }

    @Override
    public boolean hasExpired() {
        return this.animationY.isFinished() && this.end;
    }
}
