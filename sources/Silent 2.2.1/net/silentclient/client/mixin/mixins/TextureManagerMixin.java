package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.StaticResourceLocation;
import net.silentclient.client.mixin.ducks.TextureManagerExt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(TextureManager.class)
public abstract class TextureManagerMixin implements TextureManagerExt {
    @Shadow public abstract boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj);

    @Shadow public abstract void bindTexture(ResourceLocation resource);

    @Shadow @Final private Map<ResourceLocation, ITextureObject> mapTextureObjects;
    @Unique boolean binding = false;

    @Override
    public boolean waitBindTexture(StaticResourceLocation resource) {
        return this.waitBindTexture(resource, new StaticResourceLocation(Client.getInstance().getBindingTexture().getResourcePath()), 500);
    }

    @Override
    public boolean waitBindTexture(StaticResourceLocation resource, StaticResourceLocation saveTexture) {
        return this.waitBindTexture(resource, saveTexture, 500);
    }

    @Override
    public boolean waitBindTexture(StaticResourceLocation resource, StaticResourceLocation saveTexture, int timeout)
    {
        ITextureObject itextureobject = this.mapTextureObjects.get(resource.getLocation());

        if (itextureobject == null)
        {
            if(((TextureManagerExt) Minecraft.getMinecraft().getTextureManager()).isBinding()) {
                this.bindTexture(saveTexture.getLocation());
                return true;
            }
            ((TextureManagerExt) Minecraft.getMinecraft().getTextureManager()).setBinding(true);
            itextureobject = new SimpleTexture(resource.getLocation());
            loadTexture(resource.getLocation(), itextureobject);
            (new Thread("waitBinding") {
                public void run() {
                    try {
                        Thread.sleep(timeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((TextureManagerExt) Minecraft.getMinecraft().getTextureManager()).setBinding(false);
                }
            }).start();
        }
        GlStateManager.bindTexture(itextureobject.getGlTextureId());

        return false;
    }

    @Override
    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    @Override
    public boolean isBinding() {
        return binding;
    }
}
