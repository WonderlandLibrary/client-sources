/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.optifine.player.CapeUtils;

public class CapeImageBuffer
implements Runnable {
    private AbstractClientPlayerEntity player;
    private ResourceLocation resourceLocation;
    private boolean elytraOfCape;

    public CapeImageBuffer(AbstractClientPlayerEntity abstractClientPlayerEntity, ResourceLocation resourceLocation) {
        this.player = abstractClientPlayerEntity;
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void run() {
    }

    public NativeImage parseUserSkin(NativeImage nativeImage) {
        NativeImage nativeImage2 = CapeUtils.parseCape(nativeImage);
        this.elytraOfCape = CapeUtils.isElytraCape(nativeImage, nativeImage2);
        return nativeImage2;
    }

    public void skinAvailable() {
        if (this.player != null) {
            this.player.setLocationOfCape(this.resourceLocation);
            this.player.setElytraOfCape(this.elytraOfCape);
        }
        this.cleanup();
    }

    public void cleanup() {
        this.player = null;
    }

    public boolean isElytraOfCape() {
        return this.elytraOfCape;
    }
}

