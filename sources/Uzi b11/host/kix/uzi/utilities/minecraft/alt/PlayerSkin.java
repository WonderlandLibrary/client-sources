package host.kix.uzi.utilities.minecraft.alt;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class PlayerSkin {
	private static final double SKIN_HEIGHT = 32D;

	private Map<SkinComponent, ResourceLocation> skinLocations = new HashMap<SkinComponent, ResourceLocation>();

	private boolean skinLoaded;

	public void setPlayerSkin(BufferedImage skin, String resourceLocation) {
		for (SkinComponent skinComponent : SkinComponent.values()) {
			this.skinLocations.put(skinComponent, skinComponent.crop(skin, resourceLocation));
		}
		this.skinLoaded = true;
	}

	public void renderComponent(SkinComponent skinComponent, int posX, int posY, int width, int height) {
		if (this.skinLoaded) {
			GL11.glColor3d(1, 1, 1);
			ResourceLocation skinLocation = this.skinLocations.get(skinComponent);
			ImageRenderer.GENERIC_RENDERER.renderImage(skinLocation, posX, posY, width, height);
		} else if (skinComponent != SkinComponent.HAT) {
			Gui.drawRect(posX, posY, posX + width, posY + height, 0x00505050);
		}
	}

	private void renderComponent(SkinComponent skinComponent, int posX, int posY) {
		this.renderComponent(skinComponent, posX, posY, skinComponent.getWidth(), skinComponent.getHeight());
	}

	public void renderSkin(double posX, double posY, double height) {
		double scale = height / SKIN_HEIGHT;
		GL11.glPushMatrix();
		GL11.glTranslated(posX, posY, 0);
		GL11.glScaled(scale, scale, 1);
		this.renderComponent(SkinComponent.HEAD, 4, 0);
		this.renderComponent(SkinComponent.HAT, 4, 0);
		this.renderComponent(SkinComponent.LEFT_ARM, 0, 8);
		this.renderComponent(SkinComponent.RIGHT_ARM, 12, 8);
		this.renderComponent(SkinComponent.BODY, 4, 8);
		this.renderComponent(SkinComponent.LEFT_LEG, 8, 20);
		this.renderComponent(SkinComponent.RIGHT_LEG, 4, 20);
		GL11.glPopMatrix();
	}

	public boolean isSkinLoaded() {
		return this.skinLoaded;
	}

}
