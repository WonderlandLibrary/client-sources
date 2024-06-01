package com.polarware.module.impl.render;


import com.polarware.Client;
import com.polarware.component.impl.render.ProjectionComponent;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.annotations.EventLink;
import com.polarware.util.font.Font;
import com.polarware.util.font.FontManager;
import com.polarware.util.font.impl.minecraft.FontRenderer;
import com.polarware.util.render.RenderUtil;
import com.polarware.value.impl.BooleanValue;
import net.minecraft.entity.player.EntityPlayer;

import javax.vecmath.Vector4d;
import java.awt.*;
import java.util.stream.Collectors;

@ModuleInfo(name = "module.render.nametags.name", description = "module.render.nametags.description", category = Category.RENDER)
public final class NameTagsModule extends Module {

    private final BooleanValue health = new BooleanValue("Show Health", this, false);
    private boolean renderNameTags = true;

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        Font nunitoLight14 = FontManager.getNunitoLight(14);

        for (EntityPlayer entity : Client.INSTANCE.getTargetComponent().stream()
                .filter(entity -> entity instanceof EntityPlayer)
                .map(entity -> (EntityPlayer) entity)
                .collect(Collectors.toList())
        ) {
            entity.hideNameTag();

            Vector4d position = ProjectionComponent.get(entity);

            if (position == null) {
                continue;
            }

            final String text = entity.getCommandSenderName() + "Hp " + entity.getHealth();
            final double nameWidth = mc.fontRendererObj.width(text);

            final double posX = (position.x + (position.z - position.x) / 2);
            final double posY = position.y - 2;
            final double margin = 2;

            final int multiplier = 2;
            final double nH = mc.fontRendererObj.height() + (this.health.getValue() ? nunitoLight14.height() : 0) + margin * multiplier;
            final double nY = posY - nH;
            //make them round if needed
            NORMAL_POST_BLOOM_RUNNABLES.add(() -> {
                RenderUtil.rectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getDropShadow());
            });

            NORMAL_RENDER_RUNNABLES.add(() -> {
                RenderUtil.rectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getBackgroundShade());
                mc.fontRendererObj.drawCenteredString(text, posX, nY + margin * 2, getTheme().getFirstColor().getRGB());

                if (this.health.getValue()) {
                    nunitoLight14.drawCenteredString(String.valueOf((int) entity.getHealth()), posX, posY + 1 + 3 - margin - FontRenderer.FONT_HEIGHT, Color.WHITE.hashCode());
                }
            });

            NORMAL_BLUR_RUNNABLES.add(() -> {
                RenderUtil.rectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, Color.BLACK);
            });
        }
    };

    public void toggleRendering(boolean enable) {
        renderNameTags = enable;
    }
}