package best.actinium.module.impl.combat;

import best.actinium.Actinium;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.render.Render3DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.module.impl.visual.HudModule;
import best.actinium.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.Comparator;

@ModuleInfo(
        name = "Path Finder",
        description = "Automatcly Find The Path To The Nearest Player.",
        category = ModuleCategory.COMBAT
)
public class PathFinderModule extends Module {
    private EntityLivingBase target;
    @Callback
    public void onMotion(MotionEvent event) {
        this.target = mc.theWorld.getLoadedEntityList().stream()
                .filter(entity -> entity instanceof EntityPlayer && entity != mc.thePlayer).map(entity -> (EntityPlayer) entity)
                .min(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity))).orElse(null);

        if (this.target == null || event.getType() == EventType.POST) {
            return;
        }

        //if(mc.thePlayer.getDistanceToEntity(target) < 3.0) {
        //            mc.thePlayer.swingItem();
        //            mc.playerController.attackEntity(mc.thePlayer, target);
        //        }

        if (mc.thePlayer.hurtTime == 0) {
            mc.gameSettings.keyBindForward.pressed = mc.thePlayer.getDistanceToEntity(target) > 1.0;
        }

        mc.thePlayer.rotationYaw = mc.thePlayer.isCollidedHorizontally ? mc.thePlayer.rotationYaw - 180 : mc.thePlayer.rotationYaw;
        mc.gameSettings.keyBindJump.pressed = mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isInWater();
    }

    @Callback
    public void onRender3D(Render3DEvent event) {
        if(target == null) {
            return;
        }

        RenderUtil.drawLine(RenderUtil.getX(mc.thePlayer), RenderUtil.getY(mc.thePlayer), RenderUtil.getZ(mc.thePlayer), RenderUtil.getX(target),
                RenderUtil.getY(target), RenderUtil.getZ(target),1);
    }

}
