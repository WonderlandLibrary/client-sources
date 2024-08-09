package xyz.mathax.mathaxclient.events.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;

public class HeldItemRendererEvent {
    private static final HeldItemRendererEvent INSTANCE = new HeldItemRendererEvent();

    public Hand hand;
    public MatrixStack matrixStack;

    public static HeldItemRendererEvent get(Hand hand, MatrixStack matrixStack) {
        INSTANCE.hand = hand;
        INSTANCE.matrixStack = matrixStack;
        return INSTANCE;
    }
}
