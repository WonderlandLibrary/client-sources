package net.shoreline.client.impl.gui.click2.impl.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec2f;
import net.shoreline.client.api.file.ConfigFile;
import net.shoreline.client.impl.gui.click2.component.AbstractComponent;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

/**
 * @author xgraza
 * @since 03/30/24
 */
public final class ConfigComponent extends AbstractComponent {

    private final ConfigFile config;

    public ConfigComponent(ConfigFile config) {
        this.config = config;
    }

    @Override
    public void draw(DrawContext ctx, Vec2f mouse, float tickDelta) {

    }

    @Override
    public void mouseClicked(Vec2f mouse, int button) {
        if (button == GLFW_MOUSE_BUTTON_1 && isMouseOver(mouse)) {

        }
    }
}
