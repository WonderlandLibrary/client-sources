package net.shoreline.client.impl.gui.click2.impl.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec2f;
import net.shoreline.client.Shoreline;
import net.shoreline.client.api.file.ConfigFile;
import net.shoreline.client.impl.gui.click2.component.CategoryFrame;

/**
 * @author xgraza
 * @since 03/30/24
 */
public final class ConfigCategoryFrame extends CategoryFrame {
    public ConfigCategoryFrame() {
        super("Configs", 16.0f);
    }

    public void populateChildren() {
        clearChildren();
        // Get all configuration names
        for (final ConfigFile config : Shoreline.CONFIG.getFiles()) {
            addChild(new ConfigComponent(config));
        }
    }

    @Override
    public void draw(DrawContext ctx, Vec2f mouse, float tickDelta) {

    }
}
