package studio.dreamys.mixin.gui;

import net.minecraft.client.gui.GuiOverlayDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.dreamys.near;

import java.util.List;

@Mixin(GuiOverlayDebug.class)
public class MixinGuiOverlayDebug {
    public MixinGuiOverlayDebug() {
        near.cullTask.requestCull = true;
    }
    
    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(method = "call", at = @At("RETURN"))
    public List<String> getLeftText(CallbackInfoReturnable<List<String>> callback) {
        List<String> list = callback.getReturnValue();
        list.add("[Culling] Last pass: " + near.cullTask.lastTime + "ms");
        list.add("[Culling] Rendered Block Entities: " + near.renderedBlockEntities + " Skipped: " + near.skippedBlockEntities);
        list.add("[Culling] Rendered Entities: " + near.renderedEntities + " Skipped: " + near.skippedEntities);
        
        near.renderedBlockEntities = 0;
        near.skippedBlockEntities = 0;
        near.renderedEntities = 0;
        near.skippedEntities = 0;

        return list;
    }
}
