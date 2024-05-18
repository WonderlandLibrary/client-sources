package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import wwe.modules.render.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { RenderGlobal.class }, priority = 9999)
public class MixinRenderGlobal
{
    public MixinRenderGlobal() {
        super();
    }
    
    @Inject(method = { "renderEntities" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;preRenderDamagedBlocks()V", shift = At.Shift.BEFORE) })
    public void renderEntities(final Entity entity, final ICamera camera, final float n, final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<OutlineESP>)bOmb.class).IuiN() && vlwv.hNxW.qHPF((Class<OutlineESP>)bOmb.class).mode.OnGi().equalsIgnoreCase("Outline")) {
            GL11.glPushMatrix();
            OutlineESP.uuKQ(n);
            SWNL.VZWQ();
            OutlineESP.uuKQ(n);
            SWNL.JLYv();
            OutlineESP.mmPS(n);
            SWNL.feKn();
            SWNL.mptE(null);
            OutlineESP.mmPS(n);
            SWNL.VdOT();
            GL11.glPopMatrix();
        }
        if (vlwv.hNxW.qHPF((Class<ChestESP>)zDFA.class).IuiN() && vlwv.hNxW.qHPF((Class<ChestESP>)zDFA.class).mode.OnGi().equalsIgnoreCase("Outline")) {
            ChestESP.AUkB(n);
            SWNL.VZWQ();
            ChestESP.AUkB(n);
            SWNL.JLYv();
            ChestESP.tCsD(n);
            SWNL.feKn();
            SWNL.mptE(null);
            ChestESP.tCsD(n);
            SWNL.VdOT();
        }
    }
}
