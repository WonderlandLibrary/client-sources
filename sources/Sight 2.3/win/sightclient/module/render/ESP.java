package win.sightclient.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.render.EventName;
import win.sightclient.event.events.render.EventRender3D;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.combat.AntiBot;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.utils.minecraft.CombatUtils;
import win.sightclient.utils.minecraft.RenderUtils;

public class ESP extends Module {

	public static ModeSetting mode;
	
	private BooleanSetting health = new BooleanSetting("Health", this, true);
	private BooleanSetting name = new BooleanSetting("Name", this, false);
	private BooleanSetting box = new BooleanSetting("Box", this, false);
	
	private NumberSetting red = new NumberSetting("Red", this, 255, 0, 255, true);
	private NumberSetting green = new NumberSetting("Green", this, 255, 0, 255, true);
	private NumberSetting blue = new NumberSetting("Blue", this, 255, 0, 255, true);
	
	public static boolean thruWall = false;
	
	public ESP() {
		super("ESP", Category.RENDER);
		mode = new ModeSetting("Mode", this, new String[] {"2D", "Shader"});
	}
	
	@Override
	public void updateSettings() {
		this.health.setVisible(this.mode.getValue().equalsIgnoreCase("2D"));
		this.name.setVisible(this.health.isVisible());
		this.box.setVisible(this.health.isVisible());
		
		this.red.setVisible(this.mode.getValue().equalsIgnoreCase("Fill"));
		this.green.setVisible(this.red.isVisible());
		this.blue.setVisible(this.red.isVisible());
		if (!this.isToggled()) {
			thruWall = false;
		}
	}

	@Override
	public void onEvent(Event e) {
		this.setSuffix(this.mode.getValue());
		
		if (!this.mode.getValue().equalsIgnoreCase("Shader")) {
			int type = this.mode.getValue().equalsIgnoreCase("2D") ? 0 : 1;
			thruWall = type == 1;
			if (e instanceof EventRender3D) {
				for (int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
					Entity ep = mc.theWorld.loadedEntityList.get(i);
					if (CombatUtils.isValid(ep, false)) {
						if (ep instanceof EntityPlayer) {
							if (ep == mc.thePlayer && mc.gameSettings.thirdPersonView != 0) {
								this.renderEntity(ep, type);
							} else if (ep != mc.thePlayer && !AntiBot.isBot((EntityPlayer) ep)) {
								this.renderEntity(ep, type);
							}
						} else {
							this.renderEntity(ep, type);
						}
					}
				}
			} else if (e instanceof EventName && this.name.getValue()) {
				EventName en = (EventName)e;
				en.setCancelled();
			}
		} else {
			thruWall = false;
		}
	}
	
	private void renderEntity(final Entity e, int type) {
        if (e == null || !(e instanceof EntityLivingBase)) {
            return;
        }
        if (type == 0) {
        	GL11.glPushMatrix();
    		final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
            final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
            final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
            GL11.glTranslated(x, y, z);
            GL11.glScalef(0.03f, 0.03f, 0.03f);
            GL11.glRotated((-Minecraft.getMinecraft().getRenderManager().playerViewY), 0, 1f, 0);
            GlStateManager.disableDepth();
        	int realColor = Color.white.getRGB();
        	int thickness = 1;
        	int bottomPosY = -1;
        	float topPosY = e.getEyeHeight() * 40;
        	if (this.box.getValue()) {
                Gui.drawRect(-20, bottomPosY, -20 - thickness, topPosY, realColor);
                Gui.drawRect(20, bottomPosY, 20 + thickness, topPosY, realColor);
                Gui.drawRect(-21, bottomPosY, 21, bottomPosY + thickness, realColor);
                Gui.drawRect(-21, topPosY, 21, topPosY + thickness, realColor);
        	}
            
        	if (this.health.getValue()) {
        		Gui.drawRect(23, bottomPosY, 24, bottomPosY - ((bottomPosY - topPosY) * 1F) + 1, Color.black.getRGB());
                EntityLivingBase elb = (EntityLivingBase) e;
                float healthPercentage = elb.getHealth() / elb.getMaxHealth();
                Gui.drawRect(23, bottomPosY, 24, bottomPosY - ((bottomPosY - topPosY) * healthPercentage) + 1, Color.green.getRGB());
                GL11.glColor3f(1F, 1F, 1F);
        	}
        	
        	if (this.name.getValue()) {
                GL11.glRotatef(180, 0, 0, 0);
                float multiplier = 2f;
                GlStateManager.scale(1 / multiplier, 1 / multiplier, 1 / multiplier);
                mc.fontRendererObj.drawStringWithShadow((Sight.instance.mm.isEnabled("StreamerMode") ? "�k" : "") + e.getName(), (0 - mc.fontRendererObj.getStringWidth(e.getName()) / (multiplier * 2)) * multiplier, (bottomPosY - topPosY - (mc.fontRendererObj.FONT_HEIGHT / 2) - 2.0f) * multiplier, realColor);
                GlStateManager.scale(multiplier, multiplier, multiplier);
        	}
            GlStateManager.enableDepth();
            GL11.glPopMatrix();
        } else {
        	GlStateManager.disableDepth();
			GlStateManager.pushMatrix();
			GlStateManager.disableTexture2D();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GL11.glColor4f(this.red.getValueFloat() / 255F, this.green.getValueFloat() / 255F, this.blue.getValueFloat() / 255F, 0.3F);
			final AxisAlignedBB box = e.boundingBox.offset(-mc.getRenderManager().viewerPosX,
					-mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
			final AxisAlignedBB box2 = box.expand(0.1, 0.1, 0.1);
			RenderUtils.drawFilledBoundingBox(box2);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
			GlStateManager.enableDepth();
        }
	}
}
