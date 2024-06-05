/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.lwjgl.opengl.GL11
 */
package cc.diablo.module.impl.render;

import cc.diablo.event.impl.Render3DEvent;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class Tracers
extends Module {
    public Tracers() {
        super("Tracers", "Draw the funny line to players", 0, Category.Render);
    }

    @Subscribe
    public void onRender3D(Render3DEvent e) {
        GL11.glPushMatrix();
        for (Entity ent : Minecraft.theWorld.loadedEntityList) {
            if (!(ent instanceof EntityPlayer)) continue;
            RenderUtils.drawLineToPosition(new BlockPos((double)(ent.getPosition().getX() - 1), (double)ent.getPosition().getY() - 0.5, (double)(ent.getPosition().getZ() - 1)), new Color(255, 255, 255).getRGB());
        }
        GL11.glPopMatrix();
    }
}

