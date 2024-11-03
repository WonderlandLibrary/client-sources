package dev.stephen.nexus.mixin.accesors;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BipedEntityModel.class)
public interface BipedEntityModelAccessor {
    @Accessor
    ModelPart getRightArm();

    @Mutable
    @Accessor
    void setRightArm(ModelPart rightArm);

    @Accessor
    ModelPart getLeftArm();

    @Mutable
    @Accessor
    void setLeftArm(ModelPart leftArm);
}
