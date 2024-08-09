/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraftforge.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.world.ClientWorld;

@FunctionalInterface
public interface IWeatherRenderHandler {
    public void render(int var1, float var2, ClientWorld var3, Minecraft var4, LightTexture var5, double var6, double var8, double var10);
}

