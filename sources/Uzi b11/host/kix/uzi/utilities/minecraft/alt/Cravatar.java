package host.kix.uzi.utilities.minecraft.alt;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class Cravatar {

	private ResourceLocation resourceLocation;

	private boolean skinLoaded;

	private boolean cravatarDown;

	public void setPlayerSkin(BufferedImage skin, String resourceLocation) {
		if (skin == null || resourceLocation == null) {
			this.cravatarDown = true;
			return;
		}
		this.resourceLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(resourceLocation,
				new DynamicTexture(skin));
		this.skinLoaded = true;
	}

	public void renderComponent(int posX, int posY, int width, int height) {
		if (this.skinLoaded) {
			GL11.glColor3d(1, 1, 1);
			ImageRenderer.GENERIC_RENDERER.renderImage(this.resourceLocation, posX, posY, width, height);
		}
	}

	public boolean isSkinLoaded() {
		return this.skinLoaded;
	}

	public boolean isCravatarDown() {
		return this.cravatarDown;
	}
}
