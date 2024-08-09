package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import net.minecraft.resources.IResourceManager;
import net.optifine.Config;
import net.optifine.shaders.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.CallbackI;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DynamicTexture extends Texture
{
    private static final Logger field_243504_d = LogManager.getLogger();
    @Nullable
    private NativeImage dynamicTextureData;

    public DynamicTexture(NativeImage nativeImageIn)
    {
        this.dynamicTextureData = nativeImageIn;

        if (!RenderSystem.isOnRenderThread())
        {
            RenderSystem.recordRenderCall(() ->
            {
                TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());
                this.updateDynamicTexture();

                if (Config.isShaders())
                {
                    ShadersTex.initDynamicTextureNS(this);
                }
            });
        }
        else
        {
            TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());
            this.updateDynamicTexture();

            if (Config.isShaders())
            {
                ShadersTex.initDynamicTextureNS(this);
            }
        }
    }

    public DynamicTexture(int widthIn, int heightIn, boolean clearIn)
    {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        this.dynamicTextureData = new NativeImage(widthIn, heightIn, clearIn);
        TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());

        if (Config.isShaders())
        {
            ShadersTex.initDynamicTextureNS(this);
        }
    }

    public static ByteBuffer convertImageData(BufferedImage img) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            byte[] bytes = baos.toByteArray();

            ByteBuffer data = BufferUtils.createByteBuffer(bytes.length).put(bytes);
            data.flip();
            return data;
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return  null;
    }


    public void loadTexture(IResourceManager manager)
    {
    }

    public void updateDynamicTexture()
    {
        if (this.dynamicTextureData != null)
        {
            this.bindTexture();
            this.dynamicTextureData.uploadTextureSub(0, 0, 0, false);
        }
        else
        {
            field_243504_d.warn("Trying to upload disposed texture {}", (int)this.getGlTextureId());
        }
    }

    @Nullable
    public NativeImage getTextureData()
    {
        return this.dynamicTextureData;
    }

    public void setTextureData(NativeImage nativeImageIn)
    {
        if (this.dynamicTextureData != null)
        {
            this.dynamicTextureData.close();
        }

        this.dynamicTextureData = nativeImageIn;
    }

    public void close()
    {
        if (this.dynamicTextureData != null)
        {
            this.dynamicTextureData.close();
            this.deleteGlTexture();
            this.dynamicTextureData = null;
        }
    }
}
