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
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector4d;
import java.awt.*;
import java.util.List;

import static com.alan.clients.layer.Layers.BLUR;
import static com.alan.clients.util.render.RenderUtil.*;

@ModuleInfo(aliases = {"module.render.2desp.name"}, description = "module.render.projectionesp.description", category = Category.RENDER)
public class ProjectionESP extends Module {
    public ModeValue box = new ModeValue("Box Mode", this)
            .add(new SubMode("Standard"))
            .add(new SubMode("Infill"))
            //.add(new SubMode("Blur"))
            .add(new SubMode("None"))
            .setDefault("Standard");
    private final ModeValue healthbBar = new ModeValue("Health Bar Mode", this)
            .add(new SubMode("Standard"))
            .add(new SubMode("Gradient"))
            .add(new SubMode("None"))
            .setDefault("None");
    private final BooleanValue moggage = new BooleanValue("Moggage...", this, true);
    private final BooleanValue showTargets = new BooleanValue("Targets", this, false);
    public final BooleanValue player = new BooleanValue("Player", this, true, () -> !showTargets.getValue());
    public final BooleanValue invisibles = new BooleanValue("Invisibles", this, false, () -> !showTargets.getValue());
    public final BooleanValue animals = new BooleanValue("Animals", this, false, () -> !showTargets.getValue());
    public final BooleanValue mobs = new BooleanValue("Mobs", this, false, () -> !showTargets.getValue());
    public final BooleanValue teams = new BooleanValue("Player Teammates", this, true, () -> !showTargets.getValue());
    double offset = 0.5;

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        List<EntityLivingBase> entitys = TargetComponent.getTargets(this.getClass(), this.player.getValue(), this.invisibles.getValue(), this.animals.getValue(), this.mobs.getValue(), this.teams.getValue());
        for (EntityLivingBase player : entitys) {
            if (mc.getRenderManager() == null || !isInViewFrustrum(player) || player.isDead || player.isInvisible()) {
                continue;
            }

            Vector4d pos = ProjectionComponent.get(player);

            if (pos == null) {
                continue;
            }

            // Main ESP
            Runnable runnable = () -> {
                if (!box.getValue().getName().equals("None")) {
                    final Vector2d first = new Vector2d(0, 0), second = new Vector2d(0, 500);

                    //background
                    rectangle(pos.x, pos.y, pos.z - pos.x, 1.5, Color.BLACK); // Top
                    rectangle(pos.x, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Left
                    rectangle(pos.z, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK); // Right
                    rectangle(pos.x, pos.w, pos.z - pos.x, 1.5, Color.BLACK); // Bottom

                    //main esp
                    horizontalGradient(pos.x + offset, pos.y + offset, pos.z - pos.x, 0.5, // Top
                            this.getTheme().getAccentColor(first), this.getTheme().getAccentColor(second));
                    verticalGradient(pos.x + offset, pos.y + offset, 0.5, pos.w - pos.y + 0.5, // Left
                            this.getTheme().getAccentColor(first), this.getTheme().getAccentColor(second));
                    verticalGradient(pos.z + offset, pos.y + offset, 0.5, pos.w - pos.y + 0.5, // Right
                            this.getTheme().getAccentColor(second), this.getTheme().getAccentColor(first));
                    horizontalGradient(pos.x + offset, pos.w + offset, pos.z - pos.x, 0.5, // Bottom
                            this.getTheme().getAccentColor(second), this.getTheme().getAccentColor(first));

                    //optional modes
                    switch (box.getValue().getName()) {
                        case "Infill":
                            rectangle(pos.z, pos.y + 1.5, pos.x - pos.z + 1.5, pos.w - pos.y - 1.5, getTheme().getBackgroundShade());
                            break;
                        case "Blur":
                            getLayer(BLUR).add(() -> rectangle(pos.z, pos.y + 1.5, pos.x - pos.z + 1.5, pos.w - pos.y - 1.5, ColorUtil.withAlpha(Color.black, 230)));
                            break;

                    }
                }

                if (this.moggage.getValue()) {
                    RenderUtil.image(new ResourceLocation("rise/images/Alan.png"), pos.x + 2, pos.y + 2, pos.z - pos.x - 3, pos.w - pos.y - 3, Color.WHITE);
                }

                //healthbars
                if (player instanceof EntityPlayer) {
                    double height = pos.w - pos.y + 1;
                    double health = player.getHealth() / player.getMaxHealth();

                    switch (healthbBar.getValue().getName()) {

                        case "Gradient":
                            if (health > 1) health = 1;
                            RenderUtil.rectangle(pos.x - 3, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK);
                            if (health > 0.5) {
                                height /= 2;
                                RenderUtil.verticalGradient(pos.x - 2.5, pos.y + offset + (height - height * (1 - (1 - health) * 2)), offset, height * (1 - (1 - health) * 2), ColorUtil.mixColors(Color.green, Color.yellow, health), Color.yellow);
                                RenderUtil.verticalGradient(pos.x - 2.5, pos.y + height, offset, height, Color.yellow, Color.red);
                            } else {
                                RenderUtil.verticalGradient(pos.x - 2.5, pos.y + height + height * -health, offset, height * health, ColorUtil.mixColors(Color.yellow, Color.red, health * 2), Color.red);
                            }
                            break;

                        case "Standard":
                            final double healthbar = (pos.w - pos.y) * player.getHealth() / player.getMaxHealth();
                            Color healthColor = health > 0.5 ? ColorUtil.mixColors(Color.GREEN, Color.YELLOW, (health - 0.5) * 2) : ColorUtil.mixColors(Color.YELLOW, Color.RED, health * 2);

                            RenderUtil.rectangle(pos.x - 3, pos.y, 1.5, pos.w - pos.y + 1.5, Color.BLACK);
                            RenderUtil.rectangle(pos.x - 2.5, pos.w - healthbar + offset, offset, healthbar + offset, healthColor);
                    }
                }
            };
            runnable.run();
        }
    };
}


