/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import java.util.Collection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class StitcherException
extends RuntimeException {
    private final Collection<TextureAtlasSprite.Info> spriteInfos;

    public StitcherException(TextureAtlasSprite.Info info, Collection<TextureAtlasSprite.Info> collection) {
        super(String.format("Unable to fit: %s - size: %dx%d - Maybe try a lower resolution resourcepack?", info.getSpriteLocation(), info.getSpriteWidth(), info.getSpriteHeight()));
        this.spriteInfos = collection;
    }

    public Collection<TextureAtlasSprite.Info> getSpriteInfos() {
        return this.spriteInfos;
    }

    public StitcherException(TextureAtlasSprite.Info info, Collection<TextureAtlasSprite.Info> collection, int n, int n2, int n3, int n4) {
        super(String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", "" + info.getSpriteLocation(), info.getSpriteWidth(), info.getSpriteHeight(), n, n2, n3, n4));
        this.spriteInfos = collection;
    }
}

