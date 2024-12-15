package com.alan.clients.module.impl.render;

import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.component.impl.render.ProjectionComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import javax.vecmath.Vector4d;
import java.awt.*;
import java.util.List;

import static com.alan.clients.util.render.RenderUtil.*;

@ModuleInfo(aliases = {"module.render.2desp.name"}, description = "module.render.projectionesp.description", category = Category.RENDER)
public class ProjectionESP extends Module {
    private final ModeValue box = new ModeValue("Box", this)
            .add(new SubMode("Normal"))
            .add(new SubMode("CS"))
            .add(new SubMode("None")).setDefault("Normal");

    private final ModeValue healthBar = new ModeValue("Health Bar Mode", this)
            .add(new SubMode("Health"))
            .add(new SubMode("Standard"))
            .add(new SubMode("Gradient"))
            .add(new SubMode("None")).setDefault("Health");

    private final BooleanValue armorBar = new BooleanValue("Armor Bar", this, false);

    private final BooleanValue showTargets = new BooleanValue("Targets", this, false);
    private final BooleanValue player = new BooleanValue("Player", this, true, () -> !showTargets.getValue());
    private final BooleanValue invisibles = new BooleanValue("Invisibles", this, false, () -> !showTargets.getValue());
    private final BooleanValue animals = new BooleanValue("Animals", this, false, () -> !showTargets.getValue());
    private final BooleanValue mobs = new BooleanValue("Mobs", this, false, () -> !showTargets.getValue());
    private final BooleanValue teams = new BooleanValue("Player Teammates", this, true, () -> !showTargets.getValue());

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        List<EntityLivingBase> entities = TargetComponent.getTargets(this.player.getValue(), this.invisibles.getValue(), this.animals.getValue(), this.mobs.getValue(), this.teams.getValue(), true);

        if (mc.gameSettings.thirdPersonView != 0) {
            entities.add(mc.thePlayer);
        }

        for (EntityLivingBase entity : entities) {
            if (mc.getRenderManager() == null || !isInViewFrustrum(entity) || entity.isDead || entity.isInvisible()) {
                continue;
            }

            Vector4d pos = ProjectionComponent.get(entity);

            if (pos == null) {
                continue;
            }

            double x = pos.x;
            double y = pos.y;
            double x2 = pos.z;
            double y2 = pos.w;
            double width = x2 - x;
            double height = y2 - y;

            Vector2d first = new Vector2d(0, 0), second = new Vector2d(0, 500);
            Color firstColor = this.getTheme().getAccentColor(first);
            Color secondColor = this.getTheme().getAccentColor(second);
            Color outlineColor = Color.BLACK;

            switch (box.getValue().getName()) {
                case "Normal":
                    rectangle(x - 0.5f, y + 1f, 1.5f, height - 1.5f, outlineColor);
                    rectangle(x - 0.5f, y - 0.5f, width + 1.5f, 1.5f, outlineColor);
                    rectangle(x2 - 0.5f, y + 1, 1.5f, height, outlineColor);
                    rectangle(x - 0.5f, y2 - 0.5f, width, 1.5f, outlineColor);

                    horizontalGradient(x, y + 0.5f, 0.5f, height - 0.5f, firstColor, secondColor);
                    horizontalGradient(x, y, width, 0.5f, firstColor, secondColor);
                    horizontalGradient(x2, y, 0.5f, height, firstColor, secondColor);
                    horizontalGradient(x, y2, width + 0.5f, 0.5f, firstColor, secondColor);
                    break;
                case "CS":
                    float adjustedWidth = (float) (width / 4);
                    float adjustedHeight = (float) (height / 4);

                    rectangle(x - 0.5f, y + 1f, 1.5f, adjustedHeight, outlineColor);
                    rectangle(x - 0.5f, y2 - adjustedHeight - 0.5f, 1.5f, adjustedHeight, outlineColor);
                    rectangle(x - 0.5f, y - 0.5f, adjustedWidth + 1, 1.5f, outlineColor);
                    rectangle(x2 - adjustedWidth - 0.5f, y - 0.5f, adjustedWidth + 1.5f, 1.5f, outlineColor);
                    rectangle(x2 - 0.5f, y + 1, 1.5f, adjustedHeight, outlineColor);
                    rectangle(x2 - 0.5f, y2 - adjustedHeight - 0.5f, 1.5f, adjustedHeight, outlineColor);
                    rectangle(x - 0.5f, y2 - 0.5f, adjustedWidth + 1, 1.5f, outlineColor);
                    rectangle(x2 - adjustedWidth - 0.5f, y2 - 0.5f, adjustedWidth + 1.5f, 1.5f, outlineColor);

                    rectangle(x, y + 0.5f, 0.5f, adjustedHeight, secondColor);
                    rectangle(x, y2 - adjustedHeight, 0.5f, adjustedHeight, secondColor);
                    rectangle(x, y, adjustedWidth, 0.5f, secondColor);
                    rectangle(x2 - adjustedWidth, y, adjustedWidth, 0.5f, secondColor);
                    rectangle(x2, y, 0.5f, adjustedHeight + 0.5f, secondColor);
                    rectangle(x2, y2 - adjustedHeight, 0.5f, adjustedHeight + 0.5f, secondColor);
                    rectangle(x, y2, adjustedWidth, 0.5f, secondColor);
                    rectangle(x2 - adjustedWidth, y2, adjustedWidth, 0.5f, secondColor);
                    break;
                case "None":
                    break;
            }

            if (entity instanceof EntityLivingBase entityLivingBase) {
                if (!healthBar.getValue().getName().equals("None")) {
                    Color backgroundColor = new Color(0, 0, 0, 180);
                    rectangle(x - 2.5f, y - 0.5f, 1.5f, height + 1.5f, backgroundColor);

                    float healthPercentage = MathHelper.clamp_float(entityLivingBase.getHealth() / entityLivingBase.getMaxHealth(), 0.0f, 1.0f);
                    double health = (y2 - y - 2) * (1.0f - healthPercentage);

                    switch (healthBar.getValue().getName()) {
                        case "Health":
                            int healthColor = Color.HSBtoRGB(entityLivingBase.getHealth() / entityLivingBase.getMaxHealth() / 3.0f, 1.0f, 1.0f);
                            rectangle(x - 2f, y + health, 0.5f, y2 - y - health + 0.5f, new Color(healthColor));
                            break;
                        case "Standard":
                            rectangle(x - 2f, y + health, 0.5f, y2 - y - health + 0.5f, this.getTheme().getAccentColor(first));
                            break;
                        case "Gradient":
                            Color interpolatedColor = new Color(ColorUtil.mixColors(this.getTheme().getFirstColor(), this.getTheme().getSecondColor(), healthPercentage).getRGB());
                            verticalGradient(x - 2f, y + health, 0.5f, y2 - y - health + 0.5f, interpolatedColor, this.getTheme().getSecondColor());
                            break;
                    }
                }

                if (armorBar.getValue()) {
                    float armorDamageReduction = entityLivingBase.getTotalArmorValue() / (float) 20;
                    if (armorDamageReduction > 0) {
                        rectangle(x - 0.5f, y2 + 1.5f, x2 - x + 1.5f, 1.5f, new Color(0, 0, 0, 180));
                        horizontalGradient(x, y2 + 2f, (width + 0.5f) * armorDamageReduction, 0.5f, this.getTheme().getFirstColor(), ColorUtil.mixColors(this.getTheme().getFirstColor(), this.getTheme().getSecondColor(), armorDamageReduction));
                    }
                }
            }
        }
    };
}


