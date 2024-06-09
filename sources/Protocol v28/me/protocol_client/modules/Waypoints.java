package me.protocol_client.modules;

import java.awt.Color;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.module.modUtils.Waypoint;
import me.protocol_client.thanks_slicky.properties.Value;
import me.protocol_client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.Render3DEvent;

public class Waypoints extends Module {
	public final Value<Boolean>	tracer	= new Value<>("waypoints_tracer", true);
	public final Value<Boolean>	label	= new Value<>("waypoints_label", true);
	public static int spin;
	public Waypoints() {
		super("Waypoints", "waypoints", 0, Category.RENDER, new String[] { "waypoint", "waypoints", "wp", "wayp" });
	}

	private RenderItem	itemRenderer	= null;

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onRender(Render3DEvent render) {
		if ((Wrapper.getWorld() != null) && (itemRenderer == null)) {
			try {
				itemRenderer = new RenderItem(Wrapper.mc().renderEngine, Wrapper.mc().modelManager);
			} catch (NullPointerException localNullPointerException) {
			}
		}
		spin++;
		for (Waypoint point : Protocol.point.getWaypoints()) {
			if(tracer.getValue()){
			renderTracer(point.getX(), point.getY(), point.getZ(), point.getR(), point.getG(), point.getZ());
			}
			renderWaypoints(point.getX(), point.getY(), point.getZ(), point.getR(), point.getG(), point.getZ());
		}
		for (Waypoint point : Protocol.point.getWaypoints()) {
			if(label.getValue()){
			//renderNametag(point.getName(), point.getX(), point.getY(), point.getZ(), point.getR(), point.getG(), point.getZ());
		}
		}
	}

	public static void renderNametag(String name, int x, int y, int z, int r, int g, int b) {
		GL11.glPushMatrix();
		FontRenderer var13 = mc.fontRendererObj;

		GL11.glTranslated((float) x, (float) y + 2.5D, (float) z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-0.020666668F * getNametagSize(x, y, z), -0.020666668F * getNametagSize(x, y, z), 0.020666668F * getNametagSize(x, y, z));

		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GL11.glDisable(2929);
		int width = var13.getStringWidth(name) / 2;
		Wrapper.drawBorderRect(-width - 2, 0, width + 1, 9.5f, new Color(r, g, b).getRGB(), 0x60000000, 0.2f);
		Wrapper.fr().drawString(name, -width, 1, new Color(r, g, b).getRGB());
		GL11.glEnable(2929);
		GlStateManager.depthMask(true);
		GlStateManager.enableLighting();

		GL11.glPopMatrix();
	}

	public static float getNametagSize(int x, int y, int z) {
		return getDistance(x, y, z) / 4.0F <= 2.0F ? 2.0F : getDistance(x, y, z) / 4.0F;
	}

	public static float getDistance(int x, int y, int z) {
		float var2 = (float) (Wrapper.getPlayer().posX - x);
		float var3 = (float) (Wrapper.getPlayer().posY - y);
		float var4 = (float) (Wrapper.getPlayer().posZ - z);
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}

	public static void renderTracer(int x, int y, int z, int r, int g, int b) {
		double d = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double d1 = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double d2 = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		boolean temp = Wrapper.mc().gameSettings.viewBobbing;
		Wrapper.mc().gameSettings.viewBobbing = false;
		Wrapper.mc().entityRenderer.setupCameraTransform(Wrapper.mc().timer.renderPartialTicks, 2);
		Wrapper.mc().gameSettings.viewBobbing = temp;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(1);
		GL11.glColor4f(r, g, b, 1);
		GL11.glBegin(2);
		GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
		GL11.glVertex3d(d, d1 + 0.5, d2);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void renderWaypoints(int x, int y, int z, int r, int g, int b) {
		double d = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double d1 = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double d2 = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(r, g, b -255, 0.25f);
		RenderUtils.drawBoundingBox(new AxisAlignedBB(d - 0.3, d1 + 0.2, d2 + 0.3, d + 0.3, d1 + 0.8, d2 - 0.3));
		GL11.glLineWidth(1);
		GL11.glColor4f(r, g, b -255, 1);
		RenderUtils.ProtocolBoundingBox(new AxisAlignedBB(d - 0.3, d1 + 0.2, d2 + 0.3, d + 0.3, d1 + 0.8, d2 - 0.3));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public void runCmd(String s) {
		try {
			String[] args = s.split(" ");
			if (s.startsWith("add")) {
				Waypoint.addWaypoint(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
				Wrapper.tellPlayer("\2477New waypoint named: " + Protocol.primColor + args[1] + " \2477added at: " + Protocol.primColor + args[2] + "\2477, " + Protocol.primColor + args[3] + "\2477, " + Protocol.primColor + args[4] + "\2477.");
				return;
			}
			if (s.startsWith("del") || s.startsWith("remove")) {
				if(Waypoint.getWaypointbyName(args[1]) == null){
					Wrapper.tellPlayer("\2477That waypoint does not exist.");
					return;
				}
				Waypoint.getWaypoints().remove(Waypoint.getWaypointbyName(args[1]));
				Wrapper.tellPlayer("\2477Waypoint named: " + Protocol.primColor + args[1] + " \2477has been deleted.");
				return;
			}
		} catch (Exception e) {
			Wrapper.tellPlayer("\2477Invalid command for" + Protocol.primColor + " Waypoints\2477.");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "Waypoint\2477 add <Name> <X> <Y> <Z> <Red> <Green> <Blue>");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "Waypoint\2477 del <Name>");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "Waypoint \2477<tracer\2477/" + Protocol.primColor + "label\2477>");
		}
	}
}
