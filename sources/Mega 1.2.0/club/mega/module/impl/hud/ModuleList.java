package club.mega.module.impl.hud;

import club.mega.Mega;
import club.mega.event.impl.EventRender2D;
import club.mega.event.impl.EventResize;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.BooleanSetting;
import club.mega.util.*;
import org.lwjgl.opengl.GL11;
import rip.hippo.lwjeb.annotation.Handler;

import java.awt.Color;

@Module.ModuleInfo(name = "ModuleList", description = "Render List of Modules", category = Category.HUD)
public class ModuleList extends Module {

   private final BooleanSetting sort = new BooleanSetting("Sort list", this, false);
   private final BooleanSetting randomGradient = new BooleanSetting("Random gradient", this, false);

    @Handler
    public final void render2d(EventRender2D event) {
        int index = 0;
        double offset = 1;

        for (final Module module : Mega.INSTANCE.getModuleManager().getMLModules(sort.get()))
        {
            if (!module.isToggled() && module.getCurrentWidth() >= module.getTargetWidth()) continue;

            module.setCurrentHeight(AnimationUtil.animate(module.getCurrentHeight(), module.getTargetHeight(), 0.3));
            module.setCurrentWidth(AnimationUtil.animate(module.getCurrentWidth(), module.getTargetWidth(), 0.3));

            RenderUtil.drawRect(module.getCurrentWidth() - 1, offset - 1, 1 + Mega.INSTANCE.getFontManager().getFont("Arial 22").getWidth(module.getName()), 13, new Color(1,1,1,190));
            Mega.INSTANCE.getFontManager().getFont("Arial 22").drawString(module.getName(), module.getCurrentWidth(), offset, ColorUtil.getGradientOffset(ColorUtil.getMainColor().darker(), ColorUtil.getMainColor().brighter(), !randomGradient.get() ? index * 10.5D : index / 4.5D));
            GL11.glDisable(GL11.GL_BLEND);
            index--;
            offset += module.getCurrentHeight();
        }
    }

    @Handler
    public final void resize(final EventResize event) {
        for (final Module module : Mega.INSTANCE.getModuleManager().getMLModules(sort.get()))
        {
            module.setTargetWidth(module.isToggled() ? RenderUtil.getScaledResolution().getScaledWidth() - Mega.INSTANCE.getFontManager().getFont("Arial 22").getWidth(module.getName()) : RenderUtil.getScaledResolution().getScaledWidth());
            module.setCurrentWidth(module.getTargetWidth());
            module.setTargetHeight(module.isToggled() ? 13 : 0);
            module.setCurrentHeight(module.getTargetHeight());
        }
    }

    public final int getHeight() {
        int height = 0;
        for (final Module module : Mega.INSTANCE.getModuleManager().getModules())
        {
            if (!module.isToggled() && module.getCurrentWidth() >= module.getTargetWidth()) continue;
            height += module.getCurrentHeight();
        }
        return height;
    }

}
