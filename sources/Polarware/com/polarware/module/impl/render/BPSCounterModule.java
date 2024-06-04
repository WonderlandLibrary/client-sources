package com.polarware.module.impl.render;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PostStrafeEvent;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.util.math.MathUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.util.vector.Vector2d;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.DragValue;

import java.awt.*;

@ModuleInfo(name = "module.render.bpscounter.name", description = "module.render.bpscounter.description", category = Category.RENDER)
public final class BPSCounterModule extends Module {

    private final BooleanValue showTitle = new BooleanValue("Title", this, false);
    private final DragValue position = new DragValue("Position", this, new Vector2d(200, 200));

    private final Vector2f scale = new Vector2f(RenderUtil.GENERIC_SCALE, RenderUtil.GENERIC_SCALE);
    private String speed = "";

    @EventLink()
    public final Listener<PostStrafeEvent> onPostStrafe = event -> {
        speed = MathUtil.round((MoveUtil.speed() * 20) * mc.timer.timerSpeed, 1) + "";
    };

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {

        Vector2d position = this.position.position;

        final String titleString = showTitle.getValue() ? "BPS " : "";
        final String bpsString = "[" +speed + "]";
        
        final float titleWidth = mc.fontRendererObj.width(titleString);
        scale.x = titleWidth + mc.fontRendererObj.width(bpsString);
        
        this.position.setScale(new Vector2d(scale.x + 6, scale.y - 1));

        final double textX = position.x + 3.0F;
        final double textY = position.y + scale.y / 2.0F - mc.fontRendererObj.height() / 4.0F;
        mc.fontRendererObj.drawStringWithShadow(titleString, textX, textY, Color.WHITE.getRGB());
        mc.fontRendererObj.drawStringWithShadow(bpsString, textX + titleWidth, textY, Color.WHITE.getRGB());
        
    };
}