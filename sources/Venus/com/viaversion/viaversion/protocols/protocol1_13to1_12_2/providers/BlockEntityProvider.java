/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.BannerHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.BedHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.CommandBlockHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.FlowerPotHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.SkullHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities.SpawnerHandler;
import java.util.HashMap;
import java.util.Map;

public class BlockEntityProvider
implements Provider {
    private final Map<String, BlockEntityHandler> handlers = new HashMap<String, BlockEntityHandler>();

    public BlockEntityProvider() {
        this.handlers.put("minecraft:flower_pot", new FlowerPotHandler());
        this.handlers.put("minecraft:bed", new BedHandler());
        this.handlers.put("minecraft:banner", new BannerHandler());
        this.handlers.put("minecraft:skull", new SkullHandler());
        this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
        this.handlers.put("minecraft:command_block", new CommandBlockHandler());
    }

    public int transform(UserConnection userConnection, Position position, CompoundTag compoundTag, boolean bl) throws Exception {
        Object t = compoundTag.get("id");
        if (t == null) {
            return 1;
        }
        String string = (String)((Tag)t).getValue();
        BlockEntityHandler blockEntityHandler = this.handlers.get(string);
        if (blockEntityHandler == null) {
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Unhandled BlockEntity " + string + " full tag: " + compoundTag);
            }
            return 1;
        }
        int n = blockEntityHandler.transform(userConnection, compoundTag);
        if (bl && n != -1) {
            this.sendBlockChange(userConnection, position, n);
        }
        return n;
    }

    private void sendBlockChange(UserConnection userConnection, Position position, int n) throws Exception {
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_13.BLOCK_CHANGE, null, userConnection);
        packetWrapper.write(Type.POSITION, position);
        packetWrapper.write(Type.VAR_INT, n);
        packetWrapper.send(Protocol1_13To1_12_2.class);
    }

    public static interface BlockEntityHandler {
        public int transform(UserConnection var1, CompoundTag var2);
    }
}

