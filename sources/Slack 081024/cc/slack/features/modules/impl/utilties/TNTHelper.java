package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.other.ResolutionUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

@ModuleInfo(
        name = "TNTHelper",
        category = Category.UTILITIES
)
public class TNTHelper extends Module {

    double damage = 0;

    @Override
    public void onEnable() {
        damage = 0;
    }

    @Listen
    public void onRender (RenderEvent event) {
        if (event.getState() == RenderEvent.State.RENDER_3D) {
            damage = 0;
            RenderUtil.drawTNTExplosionRange(damage);
        }
        if (event.getState() == RenderEvent.State.RENDER_2D) {
            final ResolutionUtil resolution = ResolutionUtil.getResolution();
            if (damage != 0) {
                Fonts.apple20.drawStringWithShadow ("§c" + damage, (float) resolution.width / 2 + 5, (float) resolution.height / 2 + 5, -1);
            }
        }
    }

}
