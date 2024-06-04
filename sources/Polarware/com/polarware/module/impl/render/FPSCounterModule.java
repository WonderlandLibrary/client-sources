package com.polarware.module.impl.render;


import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.util.render.RenderUtil;
import com.polarware.util.vector.Vector2d;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.DragValue;
import net.minecraft.client.Minecraft;

import java.awt.*;

@ModuleInfo(name = "module.render.fpscounter.name", description = "module.render.fpscounter.description", category = Category.RENDER)
public final class FPSCounterModule extends Module  {

    private final BooleanValue showTitle = new BooleanValue("Title", this, false);
    private final DragValue position = new DragValue("Position", this, new Vector2d(200, 200));

    private final Vector2f scale = new Vector2f(RenderUtil.GENERIC_SCALE, RenderUtil.GENERIC_SCALE);

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        Vector2d position = this.position.position;

        final String titleString = showTitle.getValue() ? "FPS  " : "";
        final String fpsString = "[" + Minecraft.getDebugFPS() + "]";
        final float titleWidth = nunitoNormal.width(titleString);
        scale.x = titleWidth + nunitoNormal.width(fpsString);
        final double textX = position.x + 3.0F;
        final double textY = position.y + scale.y / 2.0F - nunitoNormal.height() / 4.0F;
        mc.fontRendererObj.drawStringWithShadow(titleString, textX, textY, Color.WHITE.getRGB());
        mc.fontRendererObj.drawStringWithShadow(fpsString, textX + titleWidth, textY, Color.WHITE.getRGB());

    };
}
