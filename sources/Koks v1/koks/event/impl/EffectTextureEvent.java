package koks.event.impl;

import koks.event.Event;
import net.minecraft.util.ResourceLocation;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 18:36
 */
public class EffectTextureEvent extends Event {


    public ResourceLocation texture;

    public EffectTextureEvent(ResourceLocation texture) {
        this.texture = texture;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }
}
