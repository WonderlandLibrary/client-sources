package net.silentclient.client.cosmetics;

public class ShieldData {
	private final String model;
    private final AnimatedResourceLocation texture;

    public ShieldData(AnimatedResourceLocation texture, String model) {
        this.model = model;
        this.texture = texture;
    }

    public String getModel() {
        return model;
    }

    public AnimatedResourceLocation getTexture() {
        return texture;
    }
}
