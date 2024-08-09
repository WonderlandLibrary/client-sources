/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.spectator;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CSpectatePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class PlayerMenuObject
implements ISpectatorMenuObject {
    private final GameProfile profile;
    private final ResourceLocation resourceLocation;
    private final StringTextComponent field_243475_c;

    public PlayerMenuObject(GameProfile gameProfile) {
        this.profile = gameProfile;
        Minecraft minecraft = Minecraft.getInstance();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(gameProfile);
        this.resourceLocation = map.containsKey((Object)MinecraftProfileTexture.Type.SKIN) ? minecraft.getSkinManager().loadSkin(map.get((Object)MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN) : DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(gameProfile));
        this.field_243475_c = new StringTextComponent(gameProfile.getName());
    }

    @Override
    public void selectItem(SpectatorMenu spectatorMenu) {
        Minecraft.getInstance().getConnection().sendPacket(new CSpectatePacket(this.profile.getId()));
    }

    @Override
    public ITextComponent getSpectatorName() {
        return this.field_243475_c;
    }

    @Override
    public void func_230485_a_(MatrixStack matrixStack, float f, int n) {
        Minecraft.getInstance().getTextureManager().bindTexture(this.resourceLocation);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, (float)n / 255.0f);
        AbstractGui.blit(matrixStack, 2, 2, 12, 12, 8.0f, 8.0f, 8, 8, 64, 64);
        AbstractGui.blit(matrixStack, 2, 2, 12, 12, 40.0f, 8.0f, 8, 8, 64, 64);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

