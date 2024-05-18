package de.dietrichpaul.clientbase.injection.mixin.rotation;

import de.dietrichpaul.clientbase.injection.accessor.IGameRenderer;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements IGameRenderer {

    @Unique
    private double range;

    @Unique
    private boolean customRaytrace;

    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 3.0))
    public double changeRange(double constant) {
        return range;
    }

    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 9.0))
    public double changeRangeSq(double constant) {
        return range * range;
    }

    @Override
    public void setRange(double range) {
        this.range = range;
    }

    @Override
    public void setCustomRaytrace(boolean customRaytrace) {
        this.customRaytrace = customRaytrace;
    }

    @Override
    public boolean isCustomRaytrace() {
        return customRaytrace;
    }
}
