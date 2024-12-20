/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.spectator;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class PlayerMenuObject
implements ISpectatorMenuObject {
    private final GameProfile profile;
    private final ResourceLocation resourceLocation;

    public PlayerMenuObject(GameProfile profileIn) {
        this.profile = profileIn;
        this.resourceLocation = AbstractClientPlayer.getLocationSkin(profileIn.getName());
        AbstractClientPlayer.getDownloadImageSkin(this.resourceLocation, profileIn.getName());
    }

    @Override
    public void selectItem(SpectatorMenu menu) {
        Minecraft.getMinecraft().getConnection().sendPacket(new CPacketSpectate(this.profile.getId()));
    }

    @Override
    public ITextComponent getSpectatorName() {
        return new TextComponentString(this.profile.getName());
    }

    @Override
    public void renderIcon(float p_178663_1_, int alpha) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        GlStateManager.color(1.0f, 1.0f, 1.0f, (float)alpha / 255.0f);
        Gui.drawScaledCustomSizeModalRect(2.0f, 2.0f, 8.0f, 8.0f, 8.0f, 8.0f, 12.0f, 12.0f, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect(2.0f, 2.0f, 40.0f, 8.0f, 8.0f, 8.0f, 12.0f, 12.0f, 64.0f, 64.0f);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

