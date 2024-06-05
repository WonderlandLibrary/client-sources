package win.sightclient.module.combat;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.event.events.render.EventRender3D;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.utils.RotationUtils;
import win.sightclient.utils.TimerUtils;
import win.sightclient.utils.minecraft.MoveUtils;

public class TargetStrafe extends Module {

	private int direction = -1;
	private BooleanSetting space = new BooleanSetting("onSpace", this, true);
	private BooleanSetting render = new BooleanSetting("Render", this, true);
	private NumberSetting renderheight = new NumberSetting("RenderHeight", this, 0.05, 0.005, 1, false);
	private NumberSetting range = new NumberSetting("Range", this, 1.6, 0.1, 3, false);
	private TimerUtils switchTimer = new TimerUtils();
	
	public TargetStrafe() {
		super("TargetStrafe", Category.COMBAT);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			if (eu.isPre() && mc.thePlayer.isCollidedHorizontally && switchTimer.hasReached(200)) {
				this.direction = -this.direction;
				switchTimer.reset();
			}
		} else if (e instanceof EventMove) {
			EventMove em = (EventMove)e;
			if (this.canStrafe()) {
				this.strafe(em, MoveUtils.getBaseSpeed());
			}
		} else if (e instanceof EventRender3D) {
			if (this.canStrafe()) {
				this.drawRadius(Killaura.target, ((EventRender3D) e).getPartialTicks(), this.range.getValue());
			}
		}
	}
	
	public void strafe(EventMove e, double moveSpeed) {
		float[] rots = RotationUtils.getRotations(Killaura.target);
		double dist = mc.thePlayer.getDistanceToEntity(Killaura.target);
		if (dist >= range.getValue()) {
			MoveUtils.setMotionWithValues(e, moveSpeed, rots[0], 1, this.direction);
		} else {
			MoveUtils.setMotionWithValues(e, moveSpeed, rots[0], 0, this.direction);
		}
	}
	
    private void drawRadius(final Entity entity, final float partialTicks, final double rad) {
    	float points = 90F;
    	GlStateManager.enableDepth();
    	int count = 0;
    	for (double il = 0; il < (this.renderheight.getValue() <= 0.005 ? 0.001 : this.renderheight.getValue()); il += 0.01) {
        	count++;
    		GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glEnable(2881);
            GL11.glEnable(2832);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
            GL11.glHint(3153, 4354);
            GL11.glDisable(2929);
            GL11.glLineWidth(6.0f);
            GL11.glBegin(3);
            final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
            final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
            final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;
            final double pix2 = 6.283185307179586;
            float speed = 5000f;
            float baseHue = System.currentTimeMillis() % (int)speed;
    		while (baseHue > speed) {
    			baseHue -= speed;
    		}
    		baseHue /= speed;
            for (int i = 0; i <= 90; ++i) {
            	float max = ((float) i + (float)(il * 8)) / points;
        		float hue = max + baseHue ;
        		while (hue > 1) {
        			hue -= 1;
        		}
                final float r = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 1F)).getRed();
                final float g = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 1F)).getGreen();
                final float b = 0.003921569f * new Color(Color.HSBtoRGB(hue, 0.75F, 1F)).getBlue();
                GL11.glColor3f(r, g, b);
                GL11.glVertex3d(x + rad * Math.cos(i * pix2 / points), y + il, z + rad * Math.sin(i * pix2 / points));
            }
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(2881);
            GL11.glEnable(2832);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
            GlStateManager.color(255, 255, 255);
    	}
    }
	
	public boolean canStrafe() {
		if (this.space.getValue() && !mc.gameSettings.keyBindJump.isKeyDown()) {
			return false;
		}
		return this.isToggled() && MoveUtils.isMoving() && Killaura.target != null && MoveUtils.isBlockUnderneath(Killaura.target.getPosition()) && (Sight.instance.mm.isEnabled("Speed") || Sight.instance.mm.isEnabled("Flight"));
	}
}
