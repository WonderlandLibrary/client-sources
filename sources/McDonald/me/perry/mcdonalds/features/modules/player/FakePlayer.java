// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import me.perry.mcdonalds.features.command.Command;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import java.util.Objects;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import com.google.gson.JsonParser;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.perry.mcdonalds.features.modules.Module;

public class FakePlayer extends Module
{
    private final String name = "McDonalds";
    private EntityOtherPlayerMP _fakePlayer;
    
    public FakePlayer() {
        super("FakePlayer", "Spawns a FakePlayer for testing", Category.PLAYER, false, false, false);
    }
    
    public static String getUuid(final String name) {
        final JsonParser parser = new JsonParser();
        final String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try {
            final String UUIDJson = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            if (UUIDJson.isEmpty()) {
                return "invalid name";
            }
            final JsonObject UUIDObject = (JsonObject)parser.parse(UUIDJson);
            return reformatUuid(UUIDObject.get("id").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    private static String reformatUuid(final String uuid) {
        String longUuid = "";
        longUuid = longUuid + uuid.substring(1, 9) + "-";
        longUuid = longUuid + uuid.substring(9, 13) + "-";
        longUuid = longUuid + uuid.substring(13, 17) + "-";
        longUuid = longUuid + uuid.substring(17, 21) + "-";
        longUuid += uuid.substring(21, 33);
        return longUuid;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.disable();
            return;
        }
        this._fakePlayer = null;
        if (FakePlayer.mc.player != null) {
            try {
                final WorldClient world = FakePlayer.mc.world;
                Objects.requireNonNull(this);
                final UUID fromString = UUID.fromString(getUuid("McDonalds"));
                Objects.requireNonNull(this);
                this._fakePlayer = new EntityOtherPlayerMP((World)world, new GameProfile(fromString, "McDonalds"));
            }
            catch (Exception e) {
                final WorldClient world2 = FakePlayer.mc.world;
                final UUID fromString2 = UUID.fromString("c46feba4-46ce-456d-a07b-b2d9da413265");
                Objects.requireNonNull(this);
                this._fakePlayer = new EntityOtherPlayerMP((World)world2, new GameProfile(fromString2, "McDonalds"));
                Command.sendMessage("Failed to load uuid, setting another one.");
            }
            final String format = "%s has been spawned.";
            final Object[] args = { null };
            final int n = 0;
            Objects.requireNonNull(this);
            args[n] = "McDonalds";
            Command.sendMessage(String.format(format, args));
            this._fakePlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
            this._fakePlayer.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
            FakePlayer.mc.world.addEntityToWorld(-100, (Entity)this._fakePlayer);
        }
    }
    
    @Override
    public void onDisable() {
        if (FakePlayer.mc.world != null && FakePlayer.mc.player != null) {
            super.onDisable();
            FakePlayer.mc.world.removeEntity((Entity)this._fakePlayer);
        }
    }
}
