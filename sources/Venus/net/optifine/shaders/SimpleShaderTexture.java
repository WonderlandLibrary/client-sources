/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.IResourceManager;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.Shaders;
import org.apache.commons.io.IOUtils;

public class SimpleShaderTexture
extends Texture {
    private String texturePath;

    public SimpleShaderTexture(String string) {
        this.texturePath = string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void loadTexture(IResourceManager iResourceManager) throws IOException {
        this.deleteGlTexture();
        InputStream inputStream = Shaders.getShaderPackResourceStream(this.texturePath);
        if (inputStream == null) {
            throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
        }
        try {
            NativeImage nativeImage = NativeImage.read(inputStream);
            TextureMetadataSection textureMetadataSection = SimpleShaderTexture.loadTextureMetadataSection(this.texturePath, new TextureMetadataSection(false, false));
            TextureUtil.prepareImage(this.getGlTextureId(), nativeImage.getWidth(), nativeImage.getHeight());
            nativeImage.uploadTextureSub(0, 0, 0, 0, 0, nativeImage.getWidth(), nativeImage.getHeight(), textureMetadataSection.getTextureBlur(), textureMetadataSection.getTextureClamp(), false, false);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static TextureMetadataSection loadTextureMetadataSection(String string, TextureMetadataSection textureMetadataSection) {
        String string2 = string + ".mcmeta";
        String string3 = "texture";
        InputStream inputStream = Shaders.getShaderPackResourceStream(string2);
        if (inputStream != null) {
            TextureMetadataSection textureMetadataSection2;
            Object object;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                JsonObject jsonObject = new JsonParser().parse(bufferedReader).getAsJsonObject();
                object = jsonObject.getAsJsonObject(string3);
                if (object == null) {
                    TextureMetadataSection textureMetadataSection3 = textureMetadataSection;
                    return textureMetadataSection3;
                }
                TextureMetadataSection textureMetadataSection4 = TextureMetadataSection.SERIALIZER.deserialize((JsonObject)object);
                if (textureMetadataSection4 == null) {
                    TextureMetadataSection textureMetadataSection5 = textureMetadataSection;
                    return textureMetadataSection5;
                }
                textureMetadataSection2 = textureMetadataSection4;
            } catch (RuntimeException runtimeException) {
                SMCLog.warning("Error reading metadata: " + string2);
                SMCLog.warning(runtimeException.getClass().getName() + ": " + runtimeException.getMessage());
                object = textureMetadataSection;
                return object;
            } finally {
                IOUtils.closeQuietly(bufferedReader);
                IOUtils.closeQuietly(inputStream);
            }
            return textureMetadataSection2;
        }
        return textureMetadataSection;
    }
}

