package maxstats.weave.mixin;

import java.awt.Color;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.entities.Utils;
import me.sleepyfish.smok.rats.impl.visual.Chams;
import me.sleepyfish.smok.utils.render.GlUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
@Mixin({RenderManager.class})
public abstract class MixinRenderManager {

    @Inject(method = {"doRenderEntity"}, at = {@At("HEAD")})
    public void doRenderEntityStart(Entity target, double x, double y, double z, float entityYaw, float partialTicks, boolean hideDebugBox, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof EntityPlayer && !Utils.inInv() && Smok.inst.ratManager.getRatByClass(Chams.class).isEnabled()) {
            Color c = new Color((int) Chams.red.getValue(), (int) Chams.green.getValue(), (int) Chams.blue.getValue());

            if (c.getRed() != 0 || c.getGreen() != 0 || c.getBlue() != 0) {
                GlStateManager.color(255.0F / (255.0F - (float) c.getRed()), 255.0F / (255.0F - (float) c.getGreen()), 255.0F - (float) c.getBlue(), 150.0F);
            }

            GlUtils.enableChamsSee();
        }
    }

    @Inject(method = {"doRenderEntity"}, at = {@At("TAIL")})
    public void doRenderEntityEnd(Entity target, double x, double y, double z, float entityYaw, float partialTicks, boolean hideDebugBox, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof EntityPlayer && !Utils.inInv() && Smok.inst.ratManager.getRatByClass(Chams.class).isEnabled()) {
            GlUtils.disableChamsSee();
            Color c = new Color((int) Chams.red.getValue(), (int) Chams.green.getValue(), (int) Chams.blue.getValue());

            if (c.getRed() != 0 || c.getGreen() != 0 || c.getBlue() != 0) {
                ColorUtils.setColor(Color.cyan);
                ColorUtils.clearColor();
            }
        }
    }

}