package net.minecraft.client.main.neptune.Mod.Collection.Render;

import java.util.List;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Wrapper;
import net.minecraft.client.main.neptune.Events.EventRender3D;
import net.minecraft.client.main.neptune.Events.EventTick;
import net.minecraft.client.main.neptune.Mod.BoolOption;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Mod.Collection.Combat.Tigrboat;
import net.minecraft.client.main.neptune.Utils.TimeMeme;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Tracers extends Mod {
	
	private BoolOption lines = new BoolOption("Tracer Lines");

	boolean tags;
	public TimeMeme timer;
	
	public Tracers() {
		super("Tracers", Category.HACKS);
		this.timer = new TimeMeme();
	}
	
	@Override
	public void onDisable() {
		Memeager.unregister(this);
	}
	
	@Override
	public void onEnable() {
		if(Neptune.getWinter().theMods.getMod(Tags.class).isEnabled()) {
			tags = true;
			Neptune.getWinter().theMods.getMod(Tags.class).setEnabled(false);
		}
		Memeager.register(this);
	}
	
	@Memetarget
	public void onRender(EventRender3D event) {
		if(tags && !Neptune.getWinter().theMods.getMod(Tags.class).isEnabled()) {
			Neptune.getWinter().theMods.getMod(Tags.class).setEnabled(true);
			tags = false;
		}
        Wrapper.mc.gameSettings.viewBobbing = false;
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        final List<?> players = (List<?>)Wrapper.mc.theWorld.playerEntities;
        for (int i = 0; i < players.size(); ++i) {
            final Entity entity = (Entity)players.get(i);
            if (entity instanceof EntityPlayer && entity != Wrapper.mc.thePlayer && !entity.isInvisible()) {
                this.drawTracer(entity);
            }
        }
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
    }

private void drawTracer(final Entity entity) {
    double d = entity.getDistanceToEntity(Wrapper.mc.thePlayer) / 70.0f;
    if (d < 0.0) {
        d = 0.0;
    }
    if (d > 1.0) {
        d = 1.0;
    }
    GL11.glColor3d(1.0 - d, d, 0.0);
    final double x = entity.posX - RenderManager.renderPosX;
    final double y = entity.posY - RenderManager.renderPosY;
    final double z = entity.posZ - RenderManager.renderPosZ;
    if(this.lines.isEnabled()) {
	    GL11.glBegin(1);
	    GL11.glVertex3d(0.0, Wrapper.mc.thePlayer.getEyeHeight(), 0.0);
	    GL11.glVertex3d(x, y, z);
	    GL11.glEnd();
    }
    this.drawBox(entity, x, y, z);
}

private void drawBox(final Entity entity, final double x, final double y, final double z) {
    final double xAdd = -0.1;
    final double yAdd = 0.1;
    final double minX = x - entity.width - xAdd;
    final double minZ = z - entity.width - xAdd;
    final double maxX = x + entity.width + xAdd;
    final double maxY = y + entity.height + yAdd;
    final double maxZ = z + entity.width + xAdd;
    for (int i = 0; i < 2; ++i) {
        final double yPos = (i == 0) ? y : maxY;
        if (Neptune.getWinter().friendUtils.isFriend(entity.getName())) {
            GL11.glColor4f(0.0f, 215.0f, 0.0f, 1.0f);
        }
        else {
            GL11.glColor4f(255.0f, 255.0f, 255.0f, 1.0f);
        }
        GL11.glBegin(2);
        GL11.glVertex3d(minX, yPos, minZ);
        GL11.glVertex3d(maxX, yPos, minZ);
        GL11.glVertex3d(maxX, yPos, maxZ);
        GL11.glVertex3d(minX, yPos, maxZ);
        if (Neptune.getWinter().friendUtils.isFriend(entity.getName())) {
            GL11.glColor4f(0.0f, 215.0f, 0.0f, 1.0f);
        }
        else {
            GL11.glColor4f(255.0f, 255.0f, 255.0f, 1.0f);
        }
        GL11.glEnd();
    }
    GL11.glBegin(1);
    for (int i = 0; i < 20; ++i) {
        for (int j = 0; j < 2; ++j) {
            GL11.glVertex3d((i % 2 == 0) ? minX : maxX, (j == 0) ? y : maxY, (i < 2) ? minZ : maxZ);
        }
    }
    GL11.glEnd();
}
}
