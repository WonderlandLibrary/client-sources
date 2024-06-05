package net.shoreline.client.impl.gui.click2.component;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec2f;
import net.shoreline.client.util.Globals;

/**
 * @author xgraza
 * @since 03/30/24
 */
public interface Component extends Globals {
    void draw(final DrawContext ctx, final Vec2f mouse, final float tickDelta);
    default void mouseClicked(final Vec2f mouse, final int button) {
    }
    default void mouseReleased(final Vec2f mouse, final int button) {

    }
    default void keyPressed() {

    }
}
