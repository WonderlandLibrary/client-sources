package dev.excellent.client.notification;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.mojang.blaze3d.matrix.MatrixStack;

@Getter
@RequiredArgsConstructor
public abstract class Notification implements IAccess {

    private final String content;
    private final long init = System.currentTimeMillis(), delay;
    protected final Font font = Fonts.INTER_BOLD.get(14);
    protected final Animation alphaAnimation = new Animation(Easing.LINEAR, 250);
    protected final Animation animationY = new Animation(Easing.EASE_OUT_QUAD, 250);
    protected final Animation chatOffset = new Animation(Easing.EASE_OUT_QUAD, 50);
    protected boolean end;
    protected float margin = 4;

    public abstract void render(MatrixStack matrixStack, int multiplierY);

    public abstract boolean hasExpired();
}
