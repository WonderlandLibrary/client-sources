package club.bluezenith.module.modules.fun;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.NoObf;
import club.bluezenith.util.render.RenderUtil;
import club.bluezenith.events.Listener;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class DamageImpact extends Module {
    // this is a test module lmao
    public DamageImpact() {
        super("DamageImpact", ModuleCategory.FUN);
        this.displayName = "Damage Impact";
    }
    int alpha = 0;

    @Listener
    public void onUpdate(UpdateEvent e){
        if(mc.thePlayer.hurtTime == 9){
            alpha = 255;
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("hi")));
        }
    }
    @NoObf
    @Listener
    public void onRender2D(Render2DEvent e){
        if(alpha > 1){
            RenderUtil.drawImage(new ResourceLocation("club/bluezenith/fun/the.png"), 0, 0, e.resolution.getScaledWidth(), e.resolution.getScaledHeight(), alpha / 255f);
        }
        if(alpha > 2){
            alpha -= 0.3 * RenderUtil.delta;
        }
    }
}
