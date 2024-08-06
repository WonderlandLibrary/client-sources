package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.NameTags;
import net.minecraft.client.render.entity.MobEntityRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntityRenderer.class)
public abstract class MobEntityRendererMixin {

    @Inject(
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;targetedEntity:Lnet/minecraft/entity/Entity;",
            opcode = Opcodes.GETFIELD,
            ordinal = 0
        ),
        method = "hasLabel(Lnet/minecraft/entity/mob/MobEntity;)Z",
        cancellable = true
    )
    private void onHasLabel(CallbackInfoReturnable<Boolean> cir) {
        if (ModuleManager.isEnabled(NameTags.class)) cir.setReturnValue(true);
    }
}
