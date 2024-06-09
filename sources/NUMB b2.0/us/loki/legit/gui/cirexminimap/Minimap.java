package us.loki.legit.gui.cirexminimap;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import us.loki.legit.Client;
import us.loki.legit.utils.Scissor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class Minimap {

	private int scale = 15;

	public void draw() {

		GL11.glPushMatrix();
		int width = (int) Client.instance.setmgr.getSettingByName("MiniMapWidth").getValDouble();
		int height = (int) Client.instance.setmgr.getSettingByName("MiniMapHeight").getValDouble();

		int y = (int) Client.instance.setmgr.getSettingByName("MiniMapY").getValDouble();
		int x = (int) Client.instance.setmgr.getSettingByName("MiniMapX").getValDouble();

		scale = 2;
		Scissor.enableScissoring();
		Scissor.doGlScissor(x - 2, y - 2, width + 3, height + 3);
		Gui.drawRect(x - 1, y - 1, x + width + 1, y + height + 1,Color.GREEN.hashCode());
		Gui.drawRect(x, y, x + width, y + height, Color.BLACK.hashCode());

		Point playerpos = new Point(x + width / 2, y + height / 2);

		for (EntityPlayer p : getPlayers(width)) {

			if (p.isInvisible()) {
				continue;
			}

			if (p == mc.thePlayer) {
				continue;

			}
			float var2 = (float) (mc.thePlayer.posX - p.posX);
			float var3 = (float) (mc.thePlayer.posZ - p.posZ);

			Gui.drawFullCircle(playerpos.getX() + var2 * scale, playerpos.getY() + (var3) * scale, 2.5,
					p.hurtTime != 0 ? Color.RED.hashCode() : Integer.MAX_VALUE * 2);
		}
		mc.fontRendererObj.drawString(mc.thePlayer.func_174811_aO().name(),
				x + (width / 2) - (mc.fontRendererObj.getStringWidth(mc.thePlayer.func_174811_aO().name()) / 2),
				y + mc.fontRendererObj.FONT_HEIGHT / 2, Integer.MAX_VALUE);
		Gui.drawFullCircle(playerpos.getX() + 1.5, playerpos.getY() + 1.5, 3, Integer.MIN_VALUE);

		Scissor.disableScissoring();
		GL11.glPopMatrix();
	}

	private Minecraft mc = Minecraft.getMinecraft();

	public ArrayList<EntityPlayer> getPlayers(float distance) {
		ArrayList<EntityPlayer> players = Lists.newArrayList();
		for (Object o : mc.theWorld.playerEntities) {
			if (o instanceof EntityPlayer) {
				EntityPlayer e = (EntityPlayer) o;
				if (mc.thePlayer.getDistanceToEntity(e) <= distance) {
					players.add(e);
				}
			}
		}
		return players;
	}

}