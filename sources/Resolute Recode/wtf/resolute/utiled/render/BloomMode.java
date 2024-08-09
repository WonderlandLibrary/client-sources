package wtf.resolute.utiled.render;

import net.minecraft.util.ResourceLocation;

public enum BloomMode {
    Rectangle("resolute/images/bloom.png"),
    Circle("nova/icons/visuals/circleglow2.png");
    public final String link;
    public ResourceLocation namespace;

    BloomMode(String link) {
        this.link = link;
        this.namespace = new ResourceLocation(link);
    }
}
