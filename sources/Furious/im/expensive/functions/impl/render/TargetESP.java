package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.events.EventDisplay;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.impl.combat.KillAura;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.projections.ProjectionUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "TargetESP", type = Category.Render)
public class TargetESP extends Function {

    private final KillAura killAura;

    public TargetESP(KillAura killAura) {
        this.killAura = killAura;
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (e.getType() != EventDisplay.Type.PRE) {
            return;
        }
        if (killAura.isState() && killAura.getTarget() != null) {
            double sin = Math.sin(System.currentTimeMillis() / 1000.0);
            float size = 120f;

            Vector3d interpolated = killAura.getTarget().getPositon(e.getPartialTicks());
            Vector2f pos = ProjectionUtil.project(interpolated.x, interpolated.y + killAura.getTarget().getHeight() / 2f, interpolated.z);
            GlStateManager.pushMatrix();
            GlStateManager.translatef(pos.x, pos.y, 0);
            GlStateManager.rotatef((float) sin * 360, 0, 0, 1);
            GlStateManager.translatef(-pos.x, -pos.y, 0);
            DisplayUtils.drawImage(new ResourceLocation("expensive/images/target.png"), pos.x - size / 2f, pos.y - size / 2f, size, size, new Vector4i(
                    ColorUtils.setAlpha(HUD.getColor(0, 1), 220),
                    ColorUtils.setAlpha(HUD.getColor(90, 1), 220),
                    ColorUtils.setAlpha(HUD.getColor(180, 1), 220),
                    ColorUtils.setAlpha(HUD.getColor(270, 1), 220)
            ));
            GlStateManager.popMatrix();
        }
    }

}
