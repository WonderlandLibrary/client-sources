/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_9_4to1_10;

import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.entities.storage.EntityTracker;
import nl.matsv.viabackwards.protocol.protocol1_9_4to1_10.packets.BlockItemPackets1_10;
import nl.matsv.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10;
import nl.matsv.viabackwards.protocol.protocol1_9_4to1_10.packets.SoundPackets1_10;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class Protocol1_9_4To1_10
extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
    private EntityPackets1_10 entityPackets;
    private BlockItemPackets1_10 blockItemPackets;

    public Protocol1_9_4To1_10() {
        super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        new SoundPackets1_10(this).register();
        this.entityPackets = new EntityPackets1_10(this);
        this.entityPackets.register();
        this.blockItemPackets = new BlockItemPackets1_10(this);
        this.blockItemPackets.register();
        this.registerIncoming(ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING, Type.NOTHING);
                this.map(Type.VAR_INT);
            }
        });
    }

    @Override
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        if (!user.has(EntityTracker.class)) {
            user.put(new EntityTracker(user));
        }
        user.get(EntityTracker.class).initProtocol(this);
    }

    public EntityPackets1_10 getEntityPackets() {
        return this.entityPackets;
    }

    public BlockItemPackets1_10 getBlockItemPackets() {
        return this.blockItemPackets;
    }
}

