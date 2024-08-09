/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.GameVersion;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftVersion
implements GameVersion {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final GameVersion GAME_VERSION = new MinecraftVersion();
    private final String id;
    private final String name;
    private final boolean stable;
    private final int worldVersion;
    private final int protocolVersion;
    private final int packVersion;
    private final Date buildTime;
    private final String releaseTarget;

    private MinecraftVersion() {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.name = "1.16.5";
        this.stable = true;
        this.worldVersion = 2586;
        this.protocolVersion = SharedConstants.func_244709_b();
        this.packVersion = 6;
        this.buildTime = new Date();
        this.releaseTarget = "1.16.5";
    }

    private MinecraftVersion(JsonObject jsonObject) {
        this.id = JSONUtils.getString(jsonObject, "id");
        this.name = JSONUtils.getString(jsonObject, "name");
        this.releaseTarget = JSONUtils.getString(jsonObject, "release_target");
        this.stable = JSONUtils.getBoolean(jsonObject, "stable");
        this.worldVersion = JSONUtils.getInt(jsonObject, "world_version");
        this.protocolVersion = JSONUtils.getInt(jsonObject, "protocol_version");
        this.packVersion = JSONUtils.getInt(jsonObject, "pack_version");
        this.buildTime = Date.from(ZonedDateTime.parse(JSONUtils.getString(jsonObject, "build_time")).toInstant());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static GameVersion load() {
        try (InputStream inputStream = MinecraftVersion.class.getResourceAsStream("/version.json");){
            MinecraftVersion minecraftVersion;
            if (inputStream == null) {
                LOGGER.warn("Missing version information!");
                GameVersion gameVersion = GAME_VERSION;
                return gameVersion;
            }
            try (Object object = new InputStreamReader(inputStream);){
                minecraftVersion = new MinecraftVersion(JSONUtils.fromJson((Reader)object));
            }
            object = minecraftVersion;
            return object;
        } catch (JsonParseException | IOException exception) {
            throw new IllegalStateException("Game version information is corrupt", exception);
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getReleaseTarget() {
        return this.releaseTarget;
    }

    @Override
    public int getWorldVersion() {
        return this.worldVersion;
    }

    @Override
    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    @Override
    public int getPackVersion() {
        return this.packVersion;
    }

    @Override
    public Date getBuildTime() {
        return this.buildTime;
    }

    @Override
    public boolean isStable() {
        return this.stable;
    }
}

