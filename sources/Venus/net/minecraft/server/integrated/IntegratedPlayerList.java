/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.integrated;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.PlayerData;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntegratedPlayerList
extends PlayerList {
    private CompoundNBT hostPlayerData;

    public IntegratedPlayerList(IntegratedServer integratedServer, DynamicRegistries.Impl impl, PlayerData playerData) {
        super(integratedServer, impl, playerData, 8);
        this.setViewDistance(10);
    }

    @Override
    protected void writePlayerData(ServerPlayerEntity serverPlayerEntity) {
        if (serverPlayerEntity.getName().getString().equals(this.getServer().getServerOwner())) {
            this.hostPlayerData = serverPlayerEntity.writeWithoutTypeId(new CompoundNBT());
        }
        super.writePlayerData(serverPlayerEntity);
    }

    @Override
    public ITextComponent canPlayerLogin(SocketAddress socketAddress, GameProfile gameProfile) {
        return gameProfile.getName().equalsIgnoreCase(this.getServer().getServerOwner()) && this.getPlayerByUsername(gameProfile.getName()) != null ? new TranslationTextComponent("multiplayer.disconnect.name_taken") : super.canPlayerLogin(socketAddress, gameProfile);
    }

    @Override
    public IntegratedServer getServer() {
        return (IntegratedServer)super.getServer();
    }

    @Override
    public CompoundNBT getHostPlayerData() {
        return this.hostPlayerData;
    }

    @Override
    public MinecraftServer getServer() {
        return this.getServer();
    }
}

