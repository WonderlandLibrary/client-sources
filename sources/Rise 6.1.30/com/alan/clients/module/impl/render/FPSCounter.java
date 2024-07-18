package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.DragValue;
import net.minecraft.client.Minecraft;

import java.awt.*;

import static com.alan.clients.layer.Layers.*;

@ModuleInfo(aliases = {"module.render.fpscounter.name"}, description = "module.render.fpscounter.description", category = Category.RENDER)
public final class FPSCounter extends Module {

    private final BooleanValue showTitle = new BooleanValue("Title", this, false);
    private final DragValue position = new DragValue("Position", this, new Vector2d(200, 200));

    private final Vector2f scale = new Vector2f(RenderUtil.GENERIC_SCALE, RenderUtil.GENERIC_SCALE);
    private int lastFPS;

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        Vector2d position = this.position.position;

        final String titleString = showTitle.getValue() ? "FPS " : "";
        final String fpsString = Minecraft.getDebugFPS() + "";
        final float titleWidth = Fonts.MAIN.get(20, Weight.BOLD).width(titleString);

        if (Minecraft.getDebugFPS() != lastFPS) {
            scale.x = titleWidth + Fonts.MAIN.get(20, Weight.REGULAR).width(fpsString);
        }

        lastFPS = Minecraft.getDebugFPS();

        getLayer(REGULAR, 1).add(() -> {
            RenderUtil.roundedRectangle(position.x, position.y, scale.x + 6, scale.y - 1, getTheme().getRound(), getTheme().getBackgroundShade());

            this.position.setScale(new Vector2d(scale.x + 6, scale.y - 1));

            final double textX = position.x + 3.0F;
            final double textY = position.y + scale.y / 2.0F - Fonts.MAIN.get(20, Weight.REGULAR).height() / 4.0F;
            Fonts.MAIN.get(20, Weight.BOLD).drawWithShadow(titleString, textX, textY, getTheme().getFirstColor().getRGB());
            Fonts.MAIN.get(20, Weight.REGULAR).drawWithShadow(fpsString, textX + titleWidth, textY, Color.WHITE.getRGB());
        });

        getLayer(BLUR).add(() -> RenderUtil.roundedRectangle(position.x, position.y, scale.x + 6, scale.y - 1, getTheme().getRound() + 1, Color.BLACK));
        getLayer(BLOOM).add(() -> RenderUtil.roundedRectangle(position.x + 0.5F, position.y + 0.5F, scale.x + 6 - 1, scale.y - 2, getTheme().getRound() + 1, getTheme().getDropShadow()));
    };
}
