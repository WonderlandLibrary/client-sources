package host.kix.uzi.utilities.minecraft.alt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public enum SkinComponent {
	HEAD(8, 8, 8, 8, false), HAT(40, 8, 8, 8, false), BODY(20, 20, 8, 12, false), LEFT_LEG(4, 20, 4, 12, true), LEFT_ARM(44, 20, 4, 12, true), RIGHT_LEG(4, 20, 4, 12, false), RIGHT_ARM(44, 20, 4, 12, false);

	private int posX;
	private int posY;
	private int width;
	private int height;
	private boolean flip;

	private SkinComponent(int posX, int posY, int width, int height, boolean flip) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.flip = flip;
	}

	public ResourceLocation crop(BufferedImage bufferImage, String resourceName) {
		BufferedImage cropped = bufferImage.getSubimage(this.posX, this.posY, this.width, this.height);
		if (this.flip) {
			cropped = this.flip(cropped);
		}
		return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(String.format(resourceName, this.name()), new DynamicTexture(cropped));
	}

	private BufferedImage flip(BufferedImage bufferedImage) {
		AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
		transform.translate(-bufferedImage.getWidth(), 0);
		AffineTransformOp flip = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return flip.filter(bufferedImage, null);
	}

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
}
