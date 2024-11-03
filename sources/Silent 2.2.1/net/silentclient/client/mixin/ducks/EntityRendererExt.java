package net.silentclient.client.mixin.ducks;

import net.silentclient.client.cosmetics.StaticResourceLocation;

public interface EntityRendererExt {
    void silent$loadShader(StaticResourceLocation location);
}
