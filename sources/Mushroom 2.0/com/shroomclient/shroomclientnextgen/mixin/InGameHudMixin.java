package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScoreboardEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.ScoreBoard;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(at = @At(value = "TAIL"), method = "render")
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (C.p() != null && C.isInGame()) Bus.post(
            new RenderTickEvent(context.getMatrices(), context)
        );
    }

    @Inject(
        at = @At(value = "HEAD"),
        method = "renderScoreboardSidebar",
        cancellable = true
    )
    private void renderScoreboardSidebar(
        DrawContext context,
        ScoreboardObjective objective,
        CallbackInfo ci
    ) {
        if (ModuleManager.isEnabled(ScoreBoard.class)) {
            Bus.post(new ScoreboardEvent(context, objective));
            ci.cancel();
        }
    }
}
