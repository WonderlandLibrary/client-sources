/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_1to1_12_2;

import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.protocol.protocol1_12_1to1_12_2.KeepAliveTracker;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;

public class Protocol1_12_1To1_12_2
extends BackwardsProtocol<ClientboundPackets1_12_1, ClientboundPackets1_12_1, ServerboundPackets1_12_1, ServerboundPackets1_12_1> {
    public Protocol1_12_1To1_12_2() {
        super(ClientboundPackets1_12_1.class, ClientboundPackets1_12_1.class, ServerboundPackets1_12_1.class, ServerboundPackets1_12_1.class);
    }

    @Override
    protected void registerPackets() {
        this.registerOutgoing(ClientboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        Long keepAlive = packetWrapper.read(Type.LONG);
                        packetWrapper.user().get(KeepAliveTracker.class).setKeepAlive(keepAlive);
                        packetWrapper.write(Type.VAR_INT, keepAlive.hashCode());
                    }
                });
            }
        });
        this.registerIncoming(ServerboundPackets1_12_1.KEEP_ALIVE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        long realKeepAlive;
                        int keepAlive = packetWrapper.read(Type.VAR_INT);
                        if (keepAlive != Long.hashCode(realKeepAlive = packetWrapper.user().get(KeepAliveTracker.class).getKeepAlive())) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.write(Type.LONG, realKeepAlive);
                        packetWrapper.user().get(KeepAliveTracker.class).setKeepAlive(Integer.MAX_VALUE);
                    }
                });
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new KeepAliveTracker(userConnection));
    }
}

