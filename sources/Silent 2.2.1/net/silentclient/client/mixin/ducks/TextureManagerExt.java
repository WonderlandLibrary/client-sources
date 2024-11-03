package net.silentclient.client.mixin.ducks;

import net.silentclient.client.cosmetics.StaticResourceLocation;

public interface TextureManagerExt {
    boolean waitBindTexture(StaticResourceLocation resource);
    boolean waitBindTexture(StaticResourceLocation resource, StaticResourceLocation saveTexture);
    boolean waitBindTexture(StaticResourceLocation resource, StaticResourceLocation saveTexture, int timeout);

    void setBinding(boolean a);
    boolean isBinding();
}
