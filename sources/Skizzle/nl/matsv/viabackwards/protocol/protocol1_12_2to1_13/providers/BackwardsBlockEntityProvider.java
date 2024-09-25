/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.providers;

import java.util.HashMap;
import java.util.Map;
import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.BannerHandler;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.BedHandler;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.PistonHandler;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.SkullHandler;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.SpawnerHandler;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.platform.providers.Provider;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

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

    public boolean isHandled(String key) {
        return this.handlers.containsKey(key);
    }

    public CompoundTag transform(UserConnection user, Position position, CompoundTag tag) throws Exception {
        String id = (String)((Tag)tag.get("id")).getValue();
        BackwardsBlockEntityHandler handler = this.handlers.get(id);
        if (handler == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Unhandled BlockEntity " + id + " full tag: " + tag);
            }
            return tag;
        }
        BackwardsBlockStorage storage = user.get(BackwardsBlockStorage.class);
        Integer blockId = storage.get(position);
        if (blockId == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Handled BlockEntity does not have a stored block :( " + id + " full tag: " + tag);
            }
            return tag;
        }
        return handler.transform(user, blockId, tag);
    }

    public CompoundTag transform(UserConnection user, Position position, String id) throws Exception {
        CompoundTag tag = new CompoundTag("");
        tag.put(new StringTag("id", id));
        tag.put(new IntTag("x", Math.toIntExact(position.getX())));
        tag.put(new IntTag("y", Math.toIntExact(position.getY())));
        tag.put(new IntTag("z", Math.toIntExact(position.getZ())));
        return this.transform(user, position, tag);
    }

    public static interface BackwardsBlockEntityHandler {
        public CompoundTag transform(UserConnection var1, int var2, CompoundTag var3);
    }
}

