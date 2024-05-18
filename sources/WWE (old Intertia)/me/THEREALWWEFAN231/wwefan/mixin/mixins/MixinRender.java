package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;
import wwe.utils.*;
import wwe.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.scoreboard.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(value = { Render.class }, priority = 9999)
public abstract class MixinRender<T extends Entity>
{
    public MixinRender() {
        super();
    }
    
    @Shadow
    protected abstract boolean func_180548_c(final T p0);
    
    @Inject(method = { "renderLivingLabel" }, at = { @At("HEAD") }, cancellable = true)
    public void renderLivingLabel(final T t, final String s, final double n, final double n2, final double n3, final int n4, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<NameTags>)tdNb.class).IuiN() && t instanceof EntityPlayer && t != Wrapper.mc.getRenderViewEntity() && t.isEntityAlive() && !t.getName().startsWith("Body #")) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "doRender" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/Render;renderOutlines:Z"))
    public boolean doRender(final Render render) {
        return vlwv.hNxW.qHPF((Class<OutlineESP>)bOmb.class).IuiN() && OutlineESP.krOE;
    }
    
    @Inject(method = { "getTeamColor" }, at = { @At("RETURN") }, cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void getTeamColor(final Entity entity, final CallbackInfoReturnable<Integer> callbackInfoReturnable, final int n, final ScorePlayerTeam scorePlayerTeam) {
        final ZONL zonl = new ZONL(entity, n);
        hSNE.Seqo(zonl);
        if (zonl.CEMd()) {
            callbackInfoReturnable.setReturnValue(zonl.YDEv);
        }
    }
}
