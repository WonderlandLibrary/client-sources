package win.sightclient.module.render;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.render.EventRender2D;
import win.sightclient.event.events.render.EventRender3D;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.combat.Killaura;
import win.sightclient.utils.management.Manager;
import win.sightclient.utils.minecraft.RenderUtils;

public class HitEffects extends Module {

	private ArrayList<hit> hits = new ArrayList<hit>();
	private float lastHealth;
	private EntityLivingBase lastTarget = null;
	
	public HitEffects() {
		super("HitEffects", Category.RENDER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EntityLivingBase target = Killaura.target;
			
			if (target == null && System.currentTimeMillis() - Manager.lastAttack <= 1000 && Manager.lastAttacked != null && Manager.lastAttacked instanceof EntityLivingBase) {
				target = (EntityLivingBase) Manager.lastAttacked;
			}
			
			if (target == null) {
				this.lastHealth = 20;
				lastTarget = null;
				return;
			}
			
			if (this.lastTarget == null || target != this.lastTarget) {
				this.lastTarget = target;
				this.lastHealth = target.getHealth();
				return;
			}
			
			if (target.getHealth() != this.lastHealth) {
				if (target.getHealth() < this.lastHealth) {
					this.hits.add(new hit(target.getPosition().add(ThreadLocalRandom.current().nextDouble(-0.25, 0.25), ThreadLocalRandom.current().nextDouble(1, 1.5), ThreadLocalRandom.current().nextDouble(-0.25, 0.25)), this.lastHealth - target.getHealth(), System.currentTimeMillis() - mc.thePlayer.lastCrit <= 250));
				}
				this.lastHealth = target.getHealth();
			}
		} else if (e instanceof EventRender3D) {
			for (int i = 0; i < hits.size(); i++) {
				hit h = this.hits.get(i);
				if (h.isFinished()) {
					hits.remove(h);
				} else {
					h.onRender();
				}
			}
		} else if (e instanceof EventRender2D) {

		}
	}
}

class hit {
	
	private long startTime = System.currentTimeMillis();
	private BlockPos pos;
	private double healthVal;
	
	private long maxTime = 1000;
	public boolean crit = false;
	
	public hit(BlockPos pos, double healthVal, boolean crit) {
		this.startTime = System.currentTimeMillis();
		this.pos = pos;
		this.healthVal = healthVal;
		this.crit = crit;
	}
	
	public void onRender() {
		final double x = this.pos.getX() + (this.pos.getX() - this.pos.getX()) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        final double y = this.pos.getY() + (this.pos.getY() - this.pos.getY()) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        final double z = this.pos.getZ() + (this.pos.getZ() - this.pos.getZ()) * Minecraft.getMinecraft().timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        
        final float var10001 = (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f;
        final double size = (1.5);
        GL11.glPushMatrix();
        RenderUtils.startDrawing();
        GL11.glTranslated(x, y, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, var10001, 0.0f, 0.0f);
        GL11.glScaled(-0.01666666753590107 * size, -0.01666666753590107 * size, 0.01666666753590107 * size);
        float sizePercentage = 1;
        long timeLeft = (this.startTime + this.maxTime) - System.currentTimeMillis();
        float yPercentage = 0;
        if (timeLeft < 75) {
        	sizePercentage = Math.min((float)timeLeft / 75F, 1F);
        	yPercentage = Math.min((float)timeLeft / 75F, 1F);
        } else {
        	sizePercentage = Math.min((float)(System.currentTimeMillis() - this.startTime) / 300F, 1F);
        	yPercentage = Math.min((float)(System.currentTimeMillis() - this.startTime) / 600F, 1F);
        }
        GlStateManager.scale(2 * sizePercentage, 2 * sizePercentage, 2 * sizePercentage);
        Gui.drawRect(-100, -100, 100, 100, new Color(255, 0, 0, 0).getRGB());
    
        
        Color render = new Color(0, 255, 0);
        if (this.healthVal < 3 && this.healthVal > 1) {
        	render = new Color(255, 255, 0);
        } else if (this.healthVal <= 1) {
        	render = new Color(255, 0, 0);
        }
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow((crit ? EnumChatFormatting.UNDERLINE : "") + new DecimalFormat("#.#").format(this.healthVal), 0, -(yPercentage * 4), render.getRGB());
        
        RenderUtils.stopDrawing();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
	}
	
	public boolean isFinished() {
		return System.currentTimeMillis() - this.startTime >= maxTime;
	}
}
