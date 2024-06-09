package us.loki.legit.gui.cirexminimap;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import us.loki.legit.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class MiniMapScreen extends GuiScreen {

	Minimap map = new Minimap();

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		double speed = 1D;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (Client.instance.setmgr.getSettingByName("MiniMapX").getValDouble() > 0)
				Client.instance.setmgr.getSettingByName("MiniMapX")
						.setValDouble(Client.instance.setmgr.getSettingByName("MiniMapX").getValDouble() - speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {

			Client.instance.setmgr.getSettingByName("MiniMapX")
					.setValDouble(Client.instance.setmgr.getSettingByName("MiniMapX").getValDouble() + speed);

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (Client.instance.setmgr.getSettingByName("MiniMapY").getValDouble() > 0)
				Client.instance.setmgr.getSettingByName("MiniMapY")
						.setValDouble(Client.instance.setmgr.getSettingByName("MiniMapY").getValDouble() - speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			Client.instance.setmgr.getSettingByName("MiniMapY")
					.setValDouble(Client.instance.setmgr.getSettingByName("MiniMapY").getValDouble() + speed);
		}
		map.draw();

		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	@Override
	public void initGui() {
		if (OpenGlHelper.shadersSupported && mc.func_175606_aa() instanceof EntityPlayer) {
			if (mc.entityRenderer.theShaderGroup != null) {
				mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			}
			mc.entityRenderer.func_175069_a(new ResourceLocation("shaders/post/blur.json"));
		}
		super.initGui();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		float speed = 2.5F;

		if (keyCode == Keyboard.KEY_ESCAPE) {
			mc.thePlayer.closeScreen();
		}

		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void onGuiClosed() {

		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}
		super.onGuiClosed();
	}

}