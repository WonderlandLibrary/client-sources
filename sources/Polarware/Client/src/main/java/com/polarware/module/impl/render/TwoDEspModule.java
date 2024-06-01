package com.polarware.module.impl.render;

import com.polarware.component.impl.render.ProjectionComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.util.vector.Vector2d;
import com.polarware.value.impl.BooleanValue;
import net.minecraft.entity.player.EntityPlayer;

import javax.vecmath.Vector4d;
import java.awt.*;

import static com.polarware.util.render.RenderUtil.*;

@ModuleInfo(name = "module.render.2desp.name", description = "module.render.projectionesp.description", category = Category.RENDER)
public class TwoDEspModule extends Module {

    public BooleanValue glow = new BooleanValue("Glow", this, true);

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (shouldSkipRendering(player)) {
                continue;
            }

            Vector4d pos = ProjectionComponent.get(player);

            if (pos == null) {
                continue;
            }

            double healthBarWidth = pos.z - pos.x;

            // Draw black outline
            rectangle(pos.x, pos.y, healthBarWidth, 1.5, Color.BLACK); // Top
            rectangle(pos.x, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Left
            rectangle(pos.z, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Right
            rectangle(pos.x, pos.w, healthBarWidth, 1.5, Color.BLACK); // Bottom
            rectangle(pos.x - 5, pos.y, 1, pos.w - pos.y + 1.5, Color.BLACK); // Left

            // Draw main ESP
            Runnable runnable = () -> {
                horizontalGradient(pos.x + 0.5, pos.y + 0.5, healthBarWidth, 0.5, Color.WHITE, Color.WHITE); // Top
                verticalGradient(pos.x + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, Color.WHITE, Color.WHITE); // Left
                verticalGradient(pos.z + 0.5, pos.y + 0.5, 0.5, pos.w - pos.y + 0.5, Color.WHITE, Color.WHITE); // Right
                horizontalGradient(pos.x + 0.5, pos.w + 0.5, healthBarWidth, 0.5, Color.WHITE, Color.WHITE); // Bottom
                verticalGradient(pos.x - 5, pos.y + 0.5, 0.5, calculateGradientSize(player), calculateGradientColor(player.getHealth()), calculateGradientColor(player.getHealth())); // Left
            };

            runnable.run();
            if (this.glow.getValue()) {
                NORMAL_POST_BLOOM_RUNNABLES.add(runnable);
            }
        }
    };

    private boolean shouldSkipRendering(EntityPlayer player) {
        return mc.getRenderManager() == null || player == mc.thePlayer ||
                !isInViewFrustrum(player) || player.isDead || player.isInvisible();
    }

    private Color calculateGradientColor(float health) {
        float normalizedHealth = Math.min(1.0f, health / 20.0f);
        return new Color(1.0f - normalizedHealth, normalizedHealth, 0.0f);
    }

    private double calculateGradientSize(EntityPlayer player) {
        return Math.max(10, player.getHealth() / 20.0);
    }
}
