/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.BannerHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.BedHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.PistonHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.SkullHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.SpawnerHandler;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.HashMap;
import java.util.Map;

public class BackwardsBlockEntityProvider
implements Provider {
    private final Map<String, BackwardsBlockEntityHandler> handlers = new HashMap<String, BackwardsBlockEntityHandler>();

    public BackwardsBlockEntityProvider() {
        this.handlers.put("minecraft:flower_pot", new FlowerPotHandler());
        this.handlers.put("minecraft:bed", new BedHandler());
        this.handlers.put("minecraft:banner", new BannerHandler());
        this.handlers.put("minecraft:skull", new SkullHandler());
        this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
        this.handlers.put("minecraft:piston", new PistonHandler());
    }

    public boolean isHandled(String string) {
        return this.handlers.containsKey(string);
    }

    public CompoundTag transform(UserConnection userConnection, Position position, CompoundTag compoundTag) throws Exception {
        Object t = compoundTag.get("id");
        if (!(t instanceof StringTag)) {
            return compoundTag;
        }
        String string = (String)((Tag)t).getValue();
        BackwardsBlockEntityHandler backwardsBlockEntityHandler = this.handlers.get(string);
        if (backwardsBlockEntityHandler == null) {
            return compoundTag;
        }
        BackwardsBlockStorage backwardsBlockStorage = userConnection.get(BackwardsBlockStorage.class);
        Integer n = backwardsBlockStorage.get(position);
        if (n == null) {
            return compoundTag;
        }
        return backwardsBlockEntityHandler.transform(userConnection, n, compoundTag);
    }

    public CompoundTag transform(UserConnection userConnection, Position position, String string) throws Exception {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("id", new StringTag(string));
        compoundTag.put("x", new IntTag(Math.toIntExact(position.x())));
        compoundTag.put("y", new IntTag(Math.toIntExact(position.y())));
        compoundTag.put("z", new IntTag(Math.toIntExact(position.z())));
        return this.transform(userConnection, position, compoundTag);
    }

    public static interface BackwardsBlockEntityHandler {
        public CompoundTag transform(UserConnection var1, int var2, CompoundTag var3);
    }
}

