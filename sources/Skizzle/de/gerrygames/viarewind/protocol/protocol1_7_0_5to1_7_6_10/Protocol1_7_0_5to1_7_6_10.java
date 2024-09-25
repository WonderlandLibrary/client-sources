/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_7_0_5to1_7_6_10;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import java.util.ArrayList;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;

public class Protocol1_7_0_5to1_7_6_10
extends Protocol {
    public static final ValueTransformer<String, String> REMOVE_DASHES = new ValueTransformer<String, String>(Type.STRING){

        @Override
        public String transform(PacketWrapper packetWrapper, String s) {
            return s.replace("-", "");
        }
    };

    @Override
    protected void registerPackets() {
        this.registerOutgoing(State.LOGIN, 2, 2, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING, REMOVE_DASHES);
                this.map(Type.STRING);
            }
        });
        this.registerOutgoing(State.PLAY, 12, 12, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.STRING, REMOVE_DASHES);
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int size = packetWrapper.read(Type.VAR_INT);
                        for (int i = 0; i < size * 3; ++i) {
                            packetWrapper.read(Type.STRING);
                        }
                    }
                });
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BYTE);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Types1_7_6_10.METADATA_LIST);
            }
        });
        this.registerOutgoing(State.PLAY, 62, 62, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        byte mode = packetWrapper.get(Type.BYTE, 0);
                        if (mode == 0 || mode == 2) {
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.STRING);
                            packetWrapper.passthrough(Type.BYTE);
                        }
                        if (mode == 0 || mode == 3 || mode == 4) {
                            int size = packetWrapper.read(Type.SHORT).shortValue();
                            ArrayList<String> entryList = new ArrayList<String>();
                            for (int i = 0; i < size; ++i) {
                                String entry = packetWrapper.read(Type.STRING);
                                if (entry == null) continue;
                                if (entry.length() > 16) {
                                    entry = entry.substring(0, 16);
                                }
                                if (entryList.contains(entry)) continue;
                                entryList.add(entry);
                            }
                            packetWrapper.write(Type.SHORT, (short)entryList.size());
                            for (String entry : entryList) {
                                packetWrapper.write(Type.STRING, entry);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
    }
}

