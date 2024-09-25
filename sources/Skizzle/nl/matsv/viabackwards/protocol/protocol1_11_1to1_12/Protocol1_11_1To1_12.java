/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_11_1to1_12;

import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.entities.storage.EntityTracker;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.packets.EntityPackets1_12;
import nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.viaversion.libs.gson.JsonElement;

public class Protocol1_11_1To1_12
extends BackwardsProtocol<ClientboundPackets1_12, ClientboundPackets1_9_3, ServerboundPackets1_12, ServerboundPackets1_9_3> {
    private EntityPackets1_12 entityPackets;
    private BlockItemPackets1_12 blockItemPackets;

    public Protocol1_11_1To1_12() {
        super(ClientboundPackets1_12.class, ClientboundPackets1_9_3.class, ServerboundPackets1_12.class, ServerboundPackets1_9_3.class);
    }

    @Override
    protected void registerPackets() {
        this.entityPackets = new EntityPackets1_12(this);
        this.entityPackets.register();
        this.blockItemPackets = new BlockItemPackets1_12(this);
        this.blockItemPackets.register();
        new SoundPackets1_12(this).register();
        new ChatPackets1_12(this).register();
        this.registerOutgoing(ClientboundPackets1_12.TITLE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int action = wrapper.passthrough(Type.VAR_INT);
                    if (action >= 0 && action <= 2) {
                        JsonElement component = wrapper.read(Type.COMPONENT);
                        wrapper.write(Type.COMPONENT, Protocol1_9To1_8.fixJson(component.toString()));
                    }
                });
            }
        });
        this.cancelOutgoing(ClientboundPackets1_12.ADVANCEMENTS);
        this.cancelOutgoing(ClientboundPackets1_12.UNLOCK_RECIPES);
        this.cancelOutgoing(ClientboundPackets1_12.SELECT_ADVANCEMENTS_TAB);
    }

    @Override
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        if (!user.has(EntityTracker.class)) {
            user.put(new EntityTracker(user));
        }
        user.put(new ShoulderTracker(user));
        user.get(EntityTracker.class).initProtocol(this);
    }

    public EntityPackets1_12 getEntityPackets() {
        return this.entityPackets;
    }

    public BlockItemPackets1_12 getBlockItemPackets() {
        return this.blockItemPackets;
    }
}

