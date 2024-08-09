package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.other.SwingAnimationEvent;
import dev.excellent.api.event.impl.render.RenderItemEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.util.HandSide;
import net.mojang.blaze3d.matrix.MatrixStack;

@ModuleInfo(name = "Animations", description = "Изменяет положение рук, а так же анимацию удара.", category = Category.RENDER)
public class Animations extends Module {
    public static Singleton<Animations> singleton = Singleton.create(() -> Module.link(Animations.class));

    private final BooleanValue translate = new BooleanValue("Оффсеты рук", this, true);
    private final NumberValue offsetX = new NumberValue("Дистанция по X", this, 0.0F, -1F, 1F, 0.05F, () -> !translate.getValue());
    private final NumberValue offsetY = new NumberValue("Дистанция по Y", this, 0.0F, -0.5F, 1F, 0.05F, () -> !translate.getValue());
    private final NumberValue offsetZ = new NumberValue("Дистанция по Z", this, 0.0F, -1F, 0F, 0.05F, () -> !translate.getValue());
    private final BooleanValue auraOnly = new BooleanValue("Только при киллке", this, false);
    public final ModeValue swingMode = new ModeValue("Анимации", this)
            .add(
                    new SubMode("Swipe"),
                    new SubMode("Swipe Back"),
                    new SubMode("Smooth Old"),
                    new SubMode("Smooth New"),
                    new SubMode("Slap"),
                    new SubMode("DeadCode"),
                    new SubMode("Knife"),
                    new SubMode("Lower Power"),
                    new SubMode("Pinch"),
                    new SubMode("Knock"),
                    new SubMode("Surf"),
                    new SubMode("Destroy"),
                    new SubMode("Back Feast"),
                    new SubMode("Без анимации")
            ).setDefault("Без анимации");

    @Override
    public String getSuffix() {
        return swingMode.getValue().getName();
    }

    private final NumberValue speed = new NumberValue("Скорость анимации", this, 1F, 0.5F, 3.0F, 0.05F);

    private final Listener<SwingAnimationEvent> onSwing = event ->
            event.setAnimation((int) (event.getAnimation() * speed.getValue().floatValue()));
    private final Listener<RenderItemEvent> onRenderItem = event -> {
        boolean rightHand = event.getHandSide().equals(HandSide.RIGHT);
        MatrixStack matrix = event.getMatrix();
        if (translate.getValue()) {
            if (rightHand) {
                matrix.translate(
                        offsetX.getValue().floatValue(),
                        offsetY.getValue().floatValue(),
                        offsetZ.getValue().floatValue()
                );
            } else {
                matrix.translate(
                        -offsetX.getValue().floatValue(),
                        offsetY.getValue().floatValue(),
                        offsetZ.getValue().floatValue()
                );

            }
        }
    };


    public boolean auraCheck() {
        KillAura aura = KillAura.singleton.get();
        return !auraOnly.getValue() || aura.isEnabled() && aura.getTarget() != null;
    }
}