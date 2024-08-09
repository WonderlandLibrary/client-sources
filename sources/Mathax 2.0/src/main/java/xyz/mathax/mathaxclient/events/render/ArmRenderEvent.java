package xyz.mathax.mathaxclient.events.render;


import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;

public class ArmRenderEvent {
    public static ArmRenderEvent INSTANCE = new ArmRenderEvent();

    public MatrixStack matrixStack;
    public Hand hand;

    public static ArmRenderEvent get(Hand hand, MatrixStack matrixStack) {
        INSTANCE.matrixStack = matrixStack;
        INSTANCE.hand = hand;

        return INSTANCE;
    }
}
