package net.optifine.shaders.client;

public interface ICustomTexture {
    int getTextureId();

    int getTextureUnit();

    void deleteTexture();
}
