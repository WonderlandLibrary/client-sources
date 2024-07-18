package com.alan.clients.module.impl.render.nametags;

import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.component.impl.render.ProjectionComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.impl.render.NameTags;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.font.impl.minecraft.FontRenderer;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.entity.EntityLivingBase;

import javax.vecmath.Vector4d;
import java.awt.*;

import static com.alan.clients.layer.Layers.*;

public class ModernNameTags extends Mode<NameTags> {
    public ModernNameTags(String name, NameTags parent) {
        super(name, parent);
    }

    private final BooleanValue health = new BooleanValue("Show Health", this, true);

    private final Font nunitoLight14 = Fonts.MAIN.get(14, Weight.LIGHT);

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        for (EntityLivingBase entity : TargetComponent.getTargets(this.getClass(), getParent().player.getValue(), getParent().invisibles.getValue(), getParent().animals.getValue(), getParent().mobs.getValue(), getParent().teams.getValue())) {
            if (entity == mc.thePlayer || !RenderUtil.isInViewFrustrum(entity)) {
                continue;
            }

            entity.hideNameTag();

            Vector4d position = ProjectionComponent.get(entity);

            if (position == null) {
                continue;
            }

            final String text = entity.getCommandSenderName();
            final double nameWidth = getParent().getWidth(text, Fonts.MAIN.get(20, Weight.LIGHT));

            final double posX = (position.x + (position.z - position.x) / 2);
            final double posY = position.y - 2;
            final double margin = 2;

            final int multiplier = 2;
            final double nH = Fonts.MAIN.get(20, Weight.LIGHT).height() + (this.health.getValue() ? nunitoLight14.height() : 0) + margin * multiplier;
            final double nY = posY - nH;

            getLayer(BLOOM).add(() -> {
                RenderUtil.roundedRectangle((posX - margin - nameWidth / 2) + 0.5F, nY + 0.5F, (nameWidth + margin * multiplier) - 1, nH - 1, getTheme().getRound() + 1, Color.BLACK);
            });

            getLayer(REGULAR).add(() -> {
                RenderUtil.roundedRectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getRound(), getTheme().getBackgroundShade());
                Fonts.MAIN.get(20, Weight.LIGHT).drawCentered(text, posX, nY + margin * 2, getTheme().getFirstColor().getRGB());

                if (this.health.getValue()) {
                    nunitoLight14.drawCentered(String.valueOf((int) ((EntityLivingBase) entity).getHealth()), posX, posY + 1 + 3 - margin - FontRenderer.FONT_HEIGHT, Color.WHITE.hashCode());
                }
            });

            getLayer(BLUR).add(() -> {
                RenderUtil.roundedRectangle(posX - margin - nameWidth / 2, nY, nameWidth + margin * multiplier, nH, getTheme().getRound(), Color.BLACK);
            });
        }
    };
}
