package dev.star.module.impl.render;

import dev.star.module.impl.display.HUDMod;
import dev.star.utils.tuples.Pair;
import dev.star.event.impl.render.RenderChestEvent;
import dev.star.event.impl.render.RenderModelEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.MultipleBoolSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Chams extends Module {


    private final MultipleBoolSetting entities = new MultipleBoolSetting("Entities", "Players", "Animals", "Mobs", "Chests");

    private final BooleanSetting lighting = new BooleanSetting("Lighting", true);


    public Chams() {
        super("Chams", Category.RENDER, "See people through walls");
        addSettings(entities, lighting);
    }


    @Override
    public void onRenderChestEvent(RenderChestEvent event) {
        if (!entities.isEnabled("Chests")) return;
        Pair<Color, Color> colors = HUDMod.getClientColors();



        glPushMatrix();
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glDisable(GL_TEXTURE_2D);


        if (!lighting.isEnabled()) {
            glDisable(GL_LIGHTING);
        }

        event.drawChest();

        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);

       // RenderUtil.resetColor();


        glEnable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);
        glPolygonOffset(1.0f, -1000000.0f);
        glDisable(GL_POLYGON_OFFSET_LINE);
        glPopMatrix();
    }

    @Override
    public void onRenderModelEvent(RenderModelEvent event) {
        if (!isValidEntity(event.getEntity())) return;

//
//        if (event.isPre()) {
//
//            GL11.glEnable(32823);
//            GL11.glPolygonOffset(1.0f, -1100000.0f);
//
//        } else {
//            GL11.glDisable(32823);
//            GL11.glPolygonOffset(1.0f, 1100000.0f);
//        }

    }

    private boolean isValidEntity(Entity entity) {
        return entities.isEnabled("Players") && entity instanceof EntityPlayer ||
                entities.isEnabled("Animals") && entity instanceof EntityAnimal ||
                entities.isEnabled("Mobs") && entity instanceof EntityMob;
    }


}
