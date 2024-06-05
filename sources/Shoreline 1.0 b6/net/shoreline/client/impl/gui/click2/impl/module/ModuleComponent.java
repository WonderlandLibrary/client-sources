package net.shoreline.client.impl.gui.click2.impl.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec2f;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.gui.click2.component.AbstractComponent;

import java.awt.*;

/**
 * @author xgraza
 * @since 03/30/24
 */
public final class ModuleComponent extends AbstractComponent {

    private final Module module;

    public ModuleComponent(Module module) {
        this.module = module;
    }

    @Override
    public void draw(DrawContext ctx, Vec2f mouse, float tickDelta) {
        boolean enabled = true;
        if (module instanceof ToggleModule toggle) {
            enabled = toggle.isEnabled();
        }

        drawRoundedRectangle((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + getHeight()), 3.5f, (enabled ? new Color(101, 64, 152) : new Color(44, 43, 43)));

        ctx.drawTextWithShadow(mc.textRenderer, module.getName(), (int) (getX() + 2.0f), (int) (getY() + 4.0f), -1);
    }

    @Override
    public void mouseClicked(Vec2f mouse, int button) {

    }

    @Override
    public void mouseReleased(Vec2f mouse, int button) {

    }
}
